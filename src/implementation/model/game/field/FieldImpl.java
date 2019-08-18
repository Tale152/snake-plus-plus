package implementation.model.game.field;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import design.model.game.BodyPart;
import design.model.game.Collidable;
import design.model.game.Field;
import design.model.game.Item;
import design.model.game.Snake;
import design.model.game.Wall;
import implementation.controller.game.loader.FieldDeserializer;

@JsonDeserialize(using = FieldDeserializer.class)
public class FieldImpl implements Field {

    private final int width;
    private final int height;

    private final List<Item> items;
    private final List<Wall> walls;
    private final List<BodyPart> bodyParts;

    private final List<Snake> snakes;
    private final List<Thread> threads;

    private final List<Item> removedItems;

    private boolean begun;

    public FieldImpl(final Point dimensions) {
        if (dimensions.x <= 0 || dimensions.y <= 0) {
            throw new IllegalArgumentException();
        }
        items = Collections.synchronizedList(new ArrayList<Item>());
        walls = Collections.synchronizedList(new ArrayList<Wall>());
        bodyParts = Collections.synchronizedList(new ArrayList<BodyPart>());
        snakes = Collections.synchronizedList(new ArrayList<Snake>());
        removedItems = Collections.synchronizedList(new ArrayList<Item>());
        threads = Collections.synchronizedList(new ArrayList<Thread>());

        begun = false;

        this.width = (int) dimensions.getX();
        this.height = (int) dimensions.getY();
    }

    private synchronized boolean contains(final Collidable item) {
        final Point coord = item.getPoint();
        final int x = (int) coord.getX();
        final int y = (int) coord.getY();
        return (x >= 0 && x < this.width && y >= 0 && y <= this.height);
    }

    private synchronized boolean isCollidableAddable(final Collidable item, final List<? extends Collidable> list) {
        if (list.contains(item)) {
            return false;
        } else {
            if (this.contains(item)) {
                return true;
            } else {
                throw new IllegalStateException();
            }
        }
    }

    private synchronized void addThread(final Runnable runnable) {
        final Thread thread = new Thread(runnable);
        thread.start();
        threads.add(thread);
    }

    @Override
    public final synchronized int getWidth() {
        return width;
    }

    @Override
    public final synchronized int getHeight() {
        return height;
    }

    @Override
    public final synchronized List<Item> getItems() {
        return new ArrayList<Item>(items);
    }

    @Override
    public final synchronized boolean removeItem(final Item item) {
        if (items.remove(item)) {
            removedItems.add(item);
            return true;
        }
        return false;
    }

    @Override
    public final synchronized boolean addItem(final Item item) throws IllegalStateException {
        if (this.isCollidableAddable(item, items)) {
            items.add(item);
            if (begun) {
                addThread(item);
            }
            return true;
        }
        return false;
    }

    @Override
    public final synchronized List<Collidable> getCell(final Point point) {
        if (point.x < 0 || point.x >= width || point.y < 0 || point.y >= height) {
            return new ArrayList<Collidable>();
        }
        final List<Collidable> cellList = new ArrayList<Collidable>();
        items.stream().filter(i -> i.getPoint().equals(point)).forEach(Item -> cellList.add(Item));
        walls.stream().filter(i -> i.getPoint().equals(point)).forEach(Wall -> cellList.add(Wall));
        bodyParts.stream().filter(i -> i.getPoint().equals(point)).forEach(BodyPart -> cellList.add(BodyPart));
        return cellList;
    }

    @Override
    public final synchronized void begin() {
        for (final Snake snake : snakes) {
            addThread(snake);
        }

        for (final Item item : items) {
            addThread(item);
        }

        begun = true;
    }

    @Override
    public final synchronized List<Item> getEliminatedItems() {
        final List<Item> returnedList = new ArrayList<Item>(removedItems);
        removedItems.clear();
        return returnedList;
    }

    @Override
    public final synchronized List<Wall> getWalls() {
        return new ArrayList<Wall>(walls);
    }

    @Override
    public final synchronized boolean addWall(final Wall wall) throws IllegalStateException {
        if (this.isCollidableAddable(wall, walls)) {
            walls.add(wall);
            return true;
        }
        return false;
    }

    @Override
    public final synchronized List<BodyPart> getBodyParts() {
        return new ArrayList<BodyPart>(bodyParts);
    }

    @Override
    public final synchronized boolean addBodyPart(final BodyPart bodyPart) throws IllegalStateException {
        if (this.isCollidableAddable(bodyPart, bodyParts)) {
            bodyParts.add(bodyPart);
            return true;
        }
        return false;
    }

    @Override
    public final synchronized boolean removeBodyPart(final BodyPart bodyPart) {
        return bodyParts.remove(bodyPart);
    }

    @Override
    public final synchronized List<Snake> getSnakes() {
        return new ArrayList<Snake>(snakes);
    }

    @Override
    public final synchronized boolean addSnake(final Snake snake) {
        if (snakes.contains(snake)) {
            return false;
        }
        return snakes.add(snake);
    }

    @Override
    public final Snake removeSnake(final int i) {
        for (final BodyPart b : snakes.get(i).getBodyParts()) {
            removeBodyPart(b);
        }
        return snakes.remove(i);
    }
}
