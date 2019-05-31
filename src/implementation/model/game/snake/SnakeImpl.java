package implementation.model.game.snake;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

public class SnakeImpl implements Snake{

	private final Player player;
	private final Properties properties;
	private final Field field;
	private List<Effect> effects;
	private List<BodyPart> bodyPart;
	private boolean isAlive;
	private boolean hasMoved;
	
	public SnakeImpl(PlayerNumber playerNumber, String playerName, Direction direction, 
			long deltaT, double speedMultiplier, Field field, List<Point> point) {
		this.player = new PlayerImpl(playerNumber, playerName);
		this.properties = new PropertiesImpl(direction, deltaT, speedMultiplier);
		this.effects = new ArrayList<>();
		this.field = field;
		this.effects = new ArrayList<>();
		this.bodyPart = new ArrayList<>();
		this.isAlive = true;
		this.hasMoved = false;

		for(int i = point.size() - 1; i >= 0; i--) {
			insertNewHead(point.get(i));
		}
		properties.getLengthProperty().lengthen(point.size() - 1); 
	}
	
	@Override
	public void run() {
		while(isAlive) {
			try {
				waitToMove();
				Point next = obtainNextPoint();
				handleCollisions(next);
				move(next);

				
			} catch (InterruptedException | NoSuchMethodException | SecurityException | InstantiationException | 
					IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}	
	}

	@Override
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public Properties getProperties() {
		return this.properties;
	}

	//if the effect is already in snake's list of effect, I just increment the duration of the effect, otherwise I add it in the list
	@Override
	public void addEffect(Effect effect) {
		if(!this.effects.contains(effect)) {
			this.effects.add(effect);
			effect.attachSnake(this);
			new Thread(effect).start();
		} else {
			effect.incrementDuration(effect.getEffectDuration().get());
		}
	}

	@Override
	public boolean removeEffect(Effect effect) {
		return this.effects.remove(effect);
	}

	@Override
	public List<Effect> getEffects() {
		return new ArrayList<>(this.effects);
	}

	@Override
	public boolean isAlive() {
		return this.isAlive;
	}

	@Override
	public void kill() {
		this.isAlive = false;	
	}

	@Override
	public void reverse() {
		Direction direction;
		int snakeSize = this.bodyPart.size();
		if(snakeSize > 1) {
			Point p1 = this.bodyPart.get(snakeSize - 2).getPoint();
			Point p2 = this.bodyPart.get(snakeSize - 1).getPoint();
			direction = determinateDirection(p1, p2);
			Collections.reverse(this.bodyPart);
		} else {
			direction = determinateOppositeDirection(this.properties.getDirectionProperty().getDirection()); //calcolo la direzione opposta se snake ha lunghezza 1
		}
		this.properties.getDirectionProperty().forceDirection(direction);

		
	}

	@Override
	public List<BodyPart> getBodyParts() {
		return new ArrayList<>(this.bodyPart);
	}
	
	//method that determinate the opposite direction of snake
	private Direction determinateOppositeDirection(Direction d) {
		Direction updatedDirection;	
		
		switch(d) {
		case UP: updatedDirection = Direction.DOWN; break;
		case DOWN: updatedDirection = Direction.UP; break;
		case RIGHT: updatedDirection = Direction.LEFT; break;
		case LEFT: updatedDirection = Direction.RIGHT; break;
		default: throw new IllegalStateException();	
		}
		return updatedDirection;
	}
	
	public boolean hasMoved() {
		if(this.hasMoved) {
			this.hasMoved = false;
			return true;
		}
		return false;
	}
	
	//used to determinate the direction of snake, it returns the direction of p1 based on the position of p2
	private Direction determinateDirection(Point p1, Point p2) {
		//if x are the same Snake is moving up or down
		if(p1.x == p2.x) {
			if(p1.y > p2.y) {
				if(p1.y - p2.y == 1) {
					return Direction.DOWN;
				}else {
					return Direction.UP;
				}
			} else {
				if(p1.y - p2.y == -1) {
					return Direction.UP;
				}else {
					return Direction.DOWN;
				}		
			}
		}
		
		//in this case Snake is moving right or left
		if(p1.y == p2.y) {
			
			if(p1.x > p2.x) {
				if(p1.x - p2.x == 1) {
					return Direction.RIGHT;
				}else {
					return Direction.LEFT;
				}
			} else {
				if(p1.x - p2.x == -1) {
					return Direction.LEFT;
				}else {
					return Direction.RIGHT;
				}		
			}
		}
		throw new IllegalStateException();
	}

	//method used to insert a new head and set all the bodypart's properties. Also used to initialize snake for the first time
	private void insertNewHead(Point point) {
		int size = this.bodyPart.size();
		BodyPart p = new BodyPartImpl(point);
		if(size == 0) {
			p.setHead(true);
			p.setTail(true);
		} else if (size == 1) {
			p.setHead(true);
			this.bodyPart.get(0).setHead(false);;
			bodyPartCombination(p);
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
		if(this.bodyPart.size() > 1) {
			int last = this.bodyPart.size() - 1;
			this.field.removeBodyPart(this.bodyPart.get(last));
			BodyPart oldTail = this.bodyPart.remove(last);
			last = last - 1;
			this.bodyPart.get(last).setTail(true);
			this.bodyPart.get(last).setBody(false);
			switch(determinateDirection(oldTail.getPoint(), this.bodyPart.get(last).getPoint())) {
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
			}
		}
	}
	
	//Used to set bodyPart properties, I want to know where are the pieces near the bodypart in input
	private void bodyPartCombination(BodyPart part) {
		switch(determinateDirection(this.bodyPart.get(0).getPoint(), part.getPoint())) {
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
		}
	}

	//i can obtain the next point where snake is going to move
	private Point obtainNextPoint() {
		Point next = new Point();
		Point head = bodyPart.get(0).getPoint();
		switch(properties.getDirectionProperty().getDirection()) {
			case UP: next = new Point(head.x, head.y - 1); break;
			case DOWN: next = new Point(head.x, head.y + 1); break;
			case LEFT: next = new Point(head.x - 1, head.y); break;
			case RIGHT: next = new Point(head.x + 1, head.y); break;
		}
		if (next.x < 0) {
			next = new Point(field.getWidth() - 1, next.y);
		} 
		else if (next.x >= field.getWidth()) {
			next = new Point(0, next.y);
		}
		else if (next.y < 0) {
			next = new Point(next.x, field.getHeight() -1);
		}
		else if (next.y >= field.getHeight()) {
			next = new Point (next.x, 0);
		}
		return next;
	}
	
	//the movement is just an insert of a new head, and I remove the tail until snake have the length he is supposed to have
	private void move(Point next) {
		insertNewHead(next);
		while(this.bodyPart.size() > this.properties.getLengthProperty().getLength()){
			removeTail();
		}
		this.hasMoved = true;
	}
	
	//snake havo to wait to move until it is his time
	private synchronized void waitToMove() throws InterruptedException {
		long startingTime = System.currentTimeMillis();		
		long timeToWait = (long)(properties.getSpeedProperty().getDeltaT() * properties.getSpeedProperty().getSpeedMultiplier());					
		while(true) {
			wait(timeToWait);											
			long deltaT = System.currentTimeMillis() - startingTime;	
			if (deltaT >= timeToWait) {									
				break;													
			}
			else {														
				wait();													
				startingTime = System.currentTimeMillis();				
				timeToWait -= deltaT;									
			}		
		}
	}
	
	//if in the cell where snake is going to move there are some collidable, call on collision on everyone of them
	private void handleCollisions(Point next) throws NoSuchMethodException, SecurityException, InstantiationException, 
				IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<Collidable> cellContent = new ArrayList<>();
		cellContent.addAll(getItemToCollide(next));
		for(Collidable c : this.field.getCell(next)) {
			c.onCollision(this);
		}
		for (Collidable collidable : cellContent) {
			if(collidable instanceof Item) {
				collidable.onCollision(this);
			}
		}
	}
	
	//this method will return all the item to collide
	//i find them using get adjacentpoint, that returns all the cell i have to collide with
	//the handle collision will collide with all the item i return here
	private List<Collidable> getItemToCollide(Point point) {
		List<Point> cells = new ArrayList<>();
		List<Collidable> item = new ArrayList<>();
		List<Point> tmp = new ArrayList<>();
		
		cells.add(point);
		//aggiungo il primo punto e poi nel for calcolo tutti i punti attorno; nel caso
		//al secondo giro calcolo tutti i punti attorno della lista, ma nel metodo sotto evito
		//ddìi aggiungere i punti che sono già presenti
		for(int i = 1; i < this.properties.getPickupProperty().getPickupRadius(); i++) {
			for(Point c : cells) {
				tmp.addAll(getAdjacentPoints(c));
			}
			cells.addAll(tmp);
			tmp.clear();
		}
		
		for(Point c : cells) {
			//System.out.println("Get point: " + c.x + " " + c.y +"\n");
			for(int i = 0; i < this.field.getCell(c).size(); i++) {
				if(!c.equals(point)) {
					item.add(this.field.getCell(c).get(i));
				}
			}
		}
		return item;
	}
	
	//this method calculates all the cell that are adjacent at the cell in input, but just those that don't go outside of the border
	private List<Point> getAdjacentPoints(Point point) {
		List<Point> adjacentPoints = new ArrayList<>();
		if(Math.abs(point.x - (point.x + 1)) == 1) {
			addNotPresentPoint(adjacentPoints, new Point(point.x + 1, point.y));
			if(Math.abs(point.y - (point.y + 1)) == 1) {
				addNotPresentPoint(adjacentPoints, new Point(point.x + 1, point.y + 1));
				addNotPresentPoint(adjacentPoints, new Point(point.x, point.y + 1));
			}
			if(Math.abs(point.y - (point.y - 1)) == 1) {
				addNotPresentPoint(adjacentPoints, new Point(point.x + 1, point.y - 1));
				addNotPresentPoint(adjacentPoints, new Point(point.x, point.y - 1));
			}
		}
		if(Math.abs(point.x - (point.x - 1)) == 1) {
			addNotPresentPoint(adjacentPoints, new Point(point.x - 1, point.y));
			if(Math.abs(point.y - (point.y + 1)) == 1) {
				addNotPresentPoint(adjacentPoints, new Point(point.x - 1, point.y + 1));
			}
			if(Math.abs(point.y - (point.y - 1)) == 1) {
				addNotPresentPoint(adjacentPoints, new Point(point.x - 1, point.y - 1));
			}
		}
		return adjacentPoints;
	}
	
	private void addNotPresentPoint(List<Point> list, Point point) {
		if(!list.contains(point)) {
			list.add(point);
		}
	}
	
	//Useful method to test all the properties of every body part of snake
	private void stampamiTutto() {
		for(BodyPart b : this.bodyPart) {
			System.out.println( "Punto: " + b.getPoint() + "\n" 
					+ "Is Head: " + b.isHead() +  "\n"
					+ "Is Tail: " + b.isTail() + "\n"
					+ "Is Body: " + b.isBody() + "\n"
					+ "Is connected on top: " + b.isCombinedOnTop() + "\n"
					+ "Is connected on right: " + b.isCombinedOnRight() + "\n"
					+ "Is connected on bottom: " + b.isCombinedOnBottom() + "\n"
					+ "Is connected on left: " + b.isCombinedOnLeft() + "\n\n");
		}
	}
}
