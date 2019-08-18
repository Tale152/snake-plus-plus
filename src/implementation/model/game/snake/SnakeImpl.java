package implementation.model.game.snake;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import design.model.game.BodyPart;
import design.model.game.Collidable;
import design.model.game.Direction;
import design.model.game.Effect;
import design.model.game.Field;
import design.model.game.Item;
import design.model.game.Player;
import design.model.game.PlayerNumber;
import design.model.game.Properties;
import design.model.game.Snake;
import implementation.model.game.items.BodyPartImpl;

/**
 * This class is the main snake class, where is handled snake movement,
 * snake collisions, his field, his owner and the reverse.
 * All the algorithm to handle snake movements are contained here. 
 * @author Elisa Tronetti
 */
public class SnakeImpl implements Snake {

    private static final int SPEEDWITHLENGHTMUL = 1;
    private static final long MAXTIMETOWAIT = 400;
    private static final long MINTIMETOWAIT = 40;

    private final Player player;
    private final Properties properties;
    private final Field field;
    private final List<Effect> effects;
    private final List<BodyPart> bodyPart;
    private boolean isLiving;
    private boolean moved;
    private boolean hasReversed;

    /**
     * Initialize all snake basic parameter.
     * @param playerNumber the number of the player that owns this snake
     * @param playerName the name of the player that owns this snake
     * @param direction the starting snake direction
     * @param deltaT the time in milliseconds between two snake's movements
     * @param speedMultiplier the multiplier to apply to the delta
     * @param field the field where snake is alive in
     * @param point snake's spawn point
     */
    public SnakeImpl(final PlayerNumber playerNumber, final String playerName, final Direction direction, 
            final long deltaT, final double speedMultiplier, final Field field, final List<Point> point) {
        this.player = new PlayerImpl(playerNumber, playerName);
        this.properties = new PropertiesImpl(direction, deltaT, speedMultiplier);
        this.effects = new ArrayList<>();
        this.field = field;
        this.bodyPart = new ArrayList<>();
        this.isLiving = true;
        this.moved = false;
        this.hasReversed = false;
        //initialize snake first body part
        for (int i = point.size() - 1; i >= 0; i--) {
            insertNewHead(point.get(i));
        }
        properties.getLengthProperty().lengthen(point.size() - 1); 
    }

    @Override
    public final void run() {
        boolean lastMovementSettedNextDirection = false;
        while (this.isLiving) {
            try {
                waitToMove();
                if (lastMovementSettedNextDirection) {
                    this.getProperties().getDirectionProperty().forceDirection(
                            this.getProperties().getDirectionProperty().getNextValidDirection());
                }
                final Direction currentDirection = this.properties.getDirectionProperty().getDirection();
                handleCollisions(obtainNextPoint());
                final Direction nextDirection = this.properties.getDirectionProperty().getDirection();
                //if the property hasReversed has been activated, snake's move to the next point and then reverse his body
                if (this.hasReversed) {
                    move(obtainNextPoint());
                    reverse();
                    this.hasReversed = false;
                //if snake current and next direction are not the same, i want him to actually move on the item
                //and then change direction
                } else if (!currentDirection.equals(nextDirection)) {
                    this.properties.getDirectionProperty().forceDirection(currentDirection);
                    move(obtainNextPoint());
                    this.properties.getDirectionProperty().forceDirection(nextDirection);
                } else {
                    //if anything has happened snake just move to the next point 
                    move(obtainNextPoint());
                }

                if (this.getProperties().getDirectionProperty().hasNextValidDirection()) {
                    lastMovementSettedNextDirection = true;
                } else {
                    this.properties.getDirectionProperty().allowChangeDirection();
                    lastMovementSettedNextDirection = false;
                }
            } catch (InterruptedException | NoSuchMethodException | SecurityException | InstantiationException 
                    | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        //clear the field from snake body parts when snake is not alive anymore
        fieldCleaningFromDeadSnake();
    }

    @Override
    public final Player getPlayer() {
        return this.player;
    }

    @Override
    public final Properties getProperties() {
        return this.properties;
    }

    @Override
    public final void addEffect(final Effect effect) {
        final Optional<Effect> activeEffect = this.effects.stream().filter(e -> e.getClass() == effect.getClass()).findFirst();
        //if the effect is already in snake's list of effect, I just increment the duration of the effect, otherwise I add it in the list
        if (!activeEffect.isPresent()) {
            this.effects.add(effect);
            effect.attachSnake(this);
            new Thread(effect).start();
        } else {
            activeEffect.get().incrementDuration(effect.getEffectDuration().get());
        }
    }

    @Override
    public final boolean removeEffect(final Effect effect) {
        return this.effects.remove(effect);
    }

    @Override
    public final List<Effect> getEffects() {
        return new ArrayList<>(this.effects);
    }

    @Override
    public final boolean isAlive() {
        return this.isLiving;
    }

    @Override
    public final void kill() {
        this.isLiving = false;
    }

    @Override
    public final void reverse() {
        //the reverse start only if the property has been activated
        if (this.hasReversed) {
            Direction direction;
            final int snakeSize = this.bodyPart.size();
            //if snake size is bigger than 1, I determinate the new direction using the position of 
            //the two last snake body part
            if (snakeSize > 1) {
                final Point p1 = this.bodyPart.get(snakeSize - 1).getPoint();
                final Point p2 = this.bodyPart.get(snakeSize - 2).getPoint();
                direction = determinateDirection(p1, p2);
                final List<BodyPart> tmp = new ArrayList<>();
                tmp.addAll(this.bodyPart);
                //then I rebuild snake in the new direction
                this.bodyPart.clear();
                for (int i = 0; i <= tmp.size() - 1; i++) {
                    this.field.removeBodyPart(tmp.get(i));
                    insertNewHead(tmp.get(i).getPoint());
                }
            } else {
                //if snake length is just 1, I only have to determinate the opposite direction of the current one. 
                direction = determinateOppositeDirection(this.properties.getDirectionProperty().getDirection()); 
            }
            this.properties.getDirectionProperty().forceDirection(direction);
        }
        this.hasReversed = true;
    }

    @Override
    public final List<BodyPart> getBodyParts() {
        return new ArrayList<>(this.bodyPart);
    }

    //method that determinate the opposite direction of snake
    private Direction determinateOppositeDirection(final Direction d) {
        switch (d) {
        case UP: return Direction.DOWN;
        case DOWN: return Direction.UP;
        case RIGHT: return Direction.LEFT;
        case LEFT: return Direction.RIGHT;
        default: throw new IllegalStateException();
        }
    }

    /**
     * @return true if snake has moved, false otherwise
     */
    public boolean hasMoved() {
        if (this.moved) {
            this.moved = false;
            return true;
        }
        return false;
    }

    //used to determinate the direction of snake, it returns the direction of p1 based on the position of p2
    private Direction determinateDirection(final Point p1, final Point p2) {
        //if x are the same Snake is moving up or down
        if (p1.x == p2.x) {
            if (p1.y > p2.y) {
                if (p1.y - p2.y == 1) {
                    return Direction.DOWN;
                } else {
                    return Direction.UP;
                }
            } else {
                if (p1.y - p2.y == -1) {
                    return Direction.UP;
                } else {
                    return Direction.DOWN;
                }
            }
        }
        //in this case Snake is moving right or left
        if (p1.y == p2.y) {
            if (p1.x > p2.x) {
                if (p1.x - p2.x == 1) {
                    return Direction.RIGHT;
                } else {
                    return Direction.LEFT;
                }
            } else {
                if (p1.x - p2.x == -1) {
                    return Direction.LEFT;
                } else {
                    return Direction.RIGHT;
                }
            }
        }
        throw new IllegalStateException();
    }

    //method used to insert a new head and set all the bodypart's properties. Also used to initialize snake for the first time
    private void insertNewHead(final Point point) {
        final int size = this.bodyPart.size();
        final BodyPart p = new BodyPartImpl(point, this);
        //if the size is 0, the first body part added is for sure head and tail
        if (size == 0) {
            p.setHead(true);
            p.setTail(true);
        //if the size is 1, i'm adding a new head, and the old one is not the head anymore, but only the tail
        } else if (size == 1) {
            p.setHead(true);
            this.bodyPart.get(0).setHead(false);
            bodyPartCombination(p);
        //in all the other case, i'm adding the new and the old one is not head anymore, but it is a body part
        } else {
            p.setHead(true);
            bodyPartCombination(p);
            this.bodyPart.get(0).setHead(false);
            this.bodyPart.get(0).setBody(true);
        }
        this.bodyPart.add(0, p);
        this.field.addBodyPart(p);
    }

    //method that is used to remove the tail and set the new properties of the new tail
    private void removeTail() {
        if (this.bodyPart.size() > 1) {
            int last = this.bodyPart.size() - 1;
            this.field.removeBodyPart(this.bodyPart.get(last));
            final BodyPart oldTail = this.bodyPart.remove(last);
            last = last - 1;
            this.bodyPart.get(last).setTail(true);
            this.bodyPart.get(last).setBody(false);
            switch (determinateDirection(oldTail.getPoint(), this.bodyPart.get(last).getPoint())) {
            case UP : 
                this.bodyPart.get(last).setCombinedOnTop(false); 
                break;
            case DOWN : 
                this.bodyPart.get(last).setCombinedOnBottom(false); 
                break;
            case LEFT :
                this.bodyPart.get(last).setCombinedOnLeft(false);
                break;
            case RIGHT : 
                this.bodyPart.get(last).setCombinedOnRight(false); 
                break;
            default: throw new IllegalStateException();
            }
        }
    }

    //Used to set bodyPart properties, I want to know where are the pieces near the body part in input
    private void bodyPartCombination(final BodyPart part) {
        switch (determinateDirection(this.bodyPart.get(0).getPoint(), part.getPoint())) {
        case UP : 
            this.bodyPart.get(0).setCombinedOnBottom(true);
            part.setCombinedOnTop(true);
            break;
        case DOWN : 
            this.bodyPart.get(0).setCombinedOnTop(true);
            part.setCombinedOnBottom(true);
            break;
        case LEFT :
            this.bodyPart.get(0).setCombinedOnRight(true);
            part.setCombinedOnLeft(true);
            break;
        case RIGHT :
            this.bodyPart.get(0).setCombinedOnLeft(true);
            part.setCombinedOnRight(true);
            break;
        default: throw new IllegalStateException();
        }
    }

    //i can obtain the next point where snake is going to move
    private Point obtainNextPoint() {
        Point next = new Point();
        final Point head = this.bodyPart.get(0).getPoint();
        switch (this.properties.getDirectionProperty().getDirection()) {
        case UP: next = new Point(head.x, head.y - 1); break;
        case DOWN: next = new Point(head.x, head.y + 1); break;
        case LEFT: next = new Point(head.x - 1, head.y); break;
        case RIGHT: next = new Point(head.x + 1, head.y); break;
        default: throw new IllegalStateException();
        }
        //I check if the snake head is going to move out of the field border
        //and i set where he have to move in each case
        if (next.x < 0) {
            next = new Point(this.field.getWidth() - 1, next.y);
        } else if (next.x >= this.field.getWidth()) {
            next = new Point(0, next.y);
        } else if (next.y < 0) {
            next = new Point(next.x, this.field.getHeight() - 1);
        } else if (next.y >= this.field.getHeight()) {
            next = new Point(next.x, 0);
        }
        return next;
    }

    //the movement is just an insert of a new head, and I remove the tail until snake have the length he is supposed to have
    private void move(final Point next) {
        changeSpeedWithLenght();
        insertNewHead(next);
        while (this.bodyPart.size() > this.properties.getLengthProperty().getLength()) {
            removeTail();
        }
        this.moved = true;
    }

    //snake have to wait to move until it is his time
    private synchronized void waitToMove() throws InterruptedException {
        long startingTime = System.currentTimeMillis();
        long timeToWait = (long) ((properties.getSpeedProperty().getDeltaT() + this.properties.getSpeedProperty().getLengthSpeedValue()) 
                * properties.getSpeedProperty().getSpeedMultiplier());
        //timeToWait must be a value between max and minimum, snake can not go faster or slower
        if (timeToWait > MAXTIMETOWAIT) {
            timeToWait = MAXTIMETOWAIT;
        } else if (timeToWait < MINTIMETOWAIT) {
            timeToWait = MINTIMETOWAIT;
        }
        while (true) {
            wait(timeToWait);
            final long deltaT = System.currentTimeMillis() - startingTime;
            if (deltaT >= timeToWait) {
                break;
            } else {
                wait();
                startingTime = System.currentTimeMillis();
                timeToWait -= deltaT;
            }
        }
    }

    //if in the cell where snake is going to move there are some collidable, call on collision on everyone of them
    private void handleCollisions(final Point next) throws NoSuchMethodException, SecurityException, InstantiationException, 
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final List<Collidable> cellContent = new ArrayList<>();
        //i get all the item to collide with
        cellContent.addAll(getItemToCollide(next));
        for (final Collidable c : this.field.getCell(next)) {
            //if in the next cell there is snake's tail, he has to collide only if the tail is going to stay in the same cell
            //in the next movement, and this happen only if snake's supposed length is bigger than snake's actual length on the screen
            if (c instanceof BodyPart && c.getPoint().equals(this.bodyPart.get(this.bodyPart.size() - 1).getPoint())) {
                if (this.properties.getLengthProperty().getLength() > this.bodyPart.size()) {
                    c.onCollision(this);
                }
            } else {
                //if it is not a body part you have to collide with it in any case 
                c.onCollision(this);
            }
        }
        for (final Collidable collidable : cellContent) {
            if (collidable instanceof Item) {
                collidable.onCollision(this);
            }
        }
    }

    //this method will return all the item to collide
    //i find them using get adjacent point, that returns all the cell i have to collide with.
    //And the handle collision will collide with all the item i return here
    private List<Collidable> getItemToCollide(final Point point) {
        final List<Point> cells = new ArrayList<>();
        final List<Collidable> item = new ArrayList<>();
        final List<Point> tmp = new ArrayList<>();

        cells.add(point);
        //i add the first point to collide with, and then in cycle i determinate all the 
        //point adjacent to this one and I add the to the list. Every time i determinate
        //all the adjacent point of all the point in the list
        for (int i = 1; i < this.properties.getPickupProperty().getPickupRadius(); i++) {
            for (final Point c : cells) {
                tmp.addAll(getAdjacentPoints(c));
            }
            cells.addAll(tmp);
            tmp.clear();
        }
        //Snake do not have to collide more than once to the same item in the same cell
        for (final Point c : cells) {
            for (int i = 0; i < this.field.getCell(c).size(); i++) {
                if (!c.equals(point)) {
                    item.add(this.field.getCell(c).get(i));
                }
            }
        }
        return item;
    }

    //this method calculates all the cell that are adjacent at the cell in input, but just those that don't go outside of the border
    //i also check not to add the same cell to the list more than once
    private List<Point> getAdjacentPoints(final Point point) {
        final List<Point> adjacentPoints = new ArrayList<>();
        if (Math.abs(point.x - (point.x + 1)) == 1) {
            addNotPresentPoint(adjacentPoints, new Point(point.x + 1, point.y));
            if (Math.abs(point.y - (point.y + 1)) == 1) {
                addNotPresentPoint(adjacentPoints, new Point(point.x + 1, point.y + 1));
                addNotPresentPoint(adjacentPoints, new Point(point.x, point.y + 1));
            }
            if (Math.abs(point.y - (point.y - 1)) == 1) {
                addNotPresentPoint(adjacentPoints, new Point(point.x + 1, point.y - 1));
                addNotPresentPoint(adjacentPoints, new Point(point.x, point.y - 1));
            }
        }
        if (Math.abs(point.x - (point.x - 1)) == 1) {
            addNotPresentPoint(adjacentPoints, new Point(point.x - 1, point.y));
            if (Math.abs(point.y - (point.y + 1)) == 1) {
                addNotPresentPoint(adjacentPoints, new Point(point.x - 1, point.y + 1));
            }
            if (Math.abs(point.y - (point.y - 1)) == 1) {
                addNotPresentPoint(adjacentPoints, new Point(point.x - 1, point.y - 1));
            }
        }
        return adjacentPoints;
    }

    private void addNotPresentPoint(final List<Point> list, final Point point) {
        if (!list.contains(point)) {
            list.add(point);
        }
    }

    //method to change the speed proportional to the length of snake
    private double changeSpeedWithLenght() {
        int val;
        //if snake length is only 1, i do not have to change his speed
        if (this.properties.getLengthProperty().getLength() == 1) {
            val = 0;
        //if snake is going to increase his length, the multiplier is negative, snake has to wait
        //less time before moving
        } else if (this.properties.getLengthProperty().getLength() >= this.bodyPart.size()) {
            val = -this.getProperties().getLengthProperty().getLength() * SPEEDWITHLENGHTMUL;
        } else {
            val = this.getProperties().getLengthProperty().getLength() * SPEEDWITHLENGHTMUL;
        }
        this.properties.getSpeedProperty().applyLengthSpeedValue(val);
        return val;
    }

    //used to clean the field from snake body parts when snake is dead
    private void fieldCleaningFromDeadSnake() {
        for (final BodyPart b : this.bodyPart) {
            this.field.removeBodyPart(b);
        }
        this.bodyPart.clear();
    }
    //Useful method to test all the properties of every body part of snake
    /*private void printBodyPartProperties() {
        for (final BodyPart b : this.bodyPart) {
            System.out.println("Point: " + b.getPoint() + "\n" 
                    + "Is Head: " + b.isHead() +  "\n"
                    + "Is Tail: " + b.isTail() + "\n"
                    + "Is Body: " + b.isBody() + "\n"
                    + "Is connected on top: " + b.isCombinedOnTop() + "\n"
                    + "Is connected on right: " + b.isCombinedOnRight() + "\n"
                    + "Is connected on bottom: " + b.isCombinedOnBottom() + "\n"
                    + "Is connected on left: " + b.isCombinedOnLeft() + "\n"
                    + "Snake direction: " + this.properties.getDirectionProperty().getDirection() + "\n\n");
        }
    }*/

}
