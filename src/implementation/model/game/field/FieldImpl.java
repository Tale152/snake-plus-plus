package implementation.model.game.field;

import java.awt.Point;
import java.util.*;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import design.model.game.*;

import implementation.controller.game.gameLoader.*;

@JsonDeserialize(using = FieldDeserializer.class)
public class FieldImpl implements Field {
	
	private int width;
	private int height;
	
	// TODO: maybe use Sets instead of Lists? order is irrelevant and stuff can't be repeated
	final private List<Item> items;
	final private List<Wall> walls;
	final private List<BodyPart> bodyParts;
	
	final private List<Snake> snakes;
	final private List<Thread> threads;
	
	final private List<Item> removedItems;
	
	private boolean begun;

	public FieldImpl(Point dimensions) {
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
	
	private synchronized boolean contains(Collidable item) {
		Point coord = item.getPoint();
		int x = (int) coord.getX();
		int y = (int) coord.getY();
		if (x >= 0 && x < this.width && y >= 0 && y <= this.height) {
			return true;
		}
		return false;
	}
	
	private synchronized boolean isCollidableAddable(Collidable item, List<? extends Collidable> list) {
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
	
	private synchronized void addThread(Runnable runnable) {
		Thread thread = new Thread(runnable);
		thread.start();
		threads.add(thread);
	}

	@Override
	public synchronized int getWidth() {
		return width;
	}

	@Override
	public synchronized int getHeight() {
		return height;
	}

	@Override
	public synchronized List<Item> getItems() {
		return new ArrayList<Item>(items);
	}

	@Override
	public synchronized boolean removeItem(Item item) {
		if (items.remove(item)) {
			removedItems.add(item);
			return true;
		}
		return false;
	}

	@Override
	public synchronized boolean addItem(Item item) throws IllegalStateException {
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
	public synchronized List<Collidable> getCell(Point point) {
		List<Collidable> cellList = new ArrayList<Collidable>();
		items.stream().filter(i -> i.getPoint().equals(point)).forEach(Item -> cellList.add(Item));
		walls.stream().filter(i -> i.getPoint().equals(point)).forEach(Wall -> cellList.add(Wall));
		bodyParts.stream().filter(i -> i.getPoint().equals(point)).forEach(BodyPart -> cellList.add(BodyPart));
		return cellList;
	}

	@Override
	public synchronized void begin() {
		for (Snake snake : snakes) {
			addThread(snake);
		}
		
		for (Item item : items) {
			addThread(item);
		}
		
		begun = true;
	}

	@Override
	public synchronized List<Item> getEliminatedItems() {
		List<Item> returnedList = new ArrayList<Item>(removedItems);
		removedItems.clear();
		return returnedList;
	}

	@Override
	public synchronized List<Wall> getWalls() {
		return new ArrayList<Wall>(walls);
	}

	@Override
	public synchronized boolean addWall(Wall wall) throws IllegalStateException {
		if (this.isCollidableAddable(wall, walls)) {
			walls.add(wall);
			return true;
		}
		return false;
	}

	@Override
	public synchronized List<BodyPart> getBodyParts() {
		return new ArrayList<BodyPart>(bodyParts);
	}

	@Override
	public synchronized boolean addBodyPart(BodyPart bodyPart) throws IllegalStateException {
		if (this.isCollidableAddable(bodyPart, bodyParts)) {
			bodyParts.add(bodyPart);
			return true;
		}
		return false;
	}

	@Override
	public synchronized boolean removeBodyPart(BodyPart bodyPart) {
		return bodyParts.remove(bodyPart);
	}

	@Override
	public synchronized List<Snake> getSnakes() {
		return new ArrayList<Snake>(snakes);
	}

	@Override
	public synchronized boolean addSnake(Snake snake) {
		if (snakes.contains(snake)) {
			return false;
		}
		return snakes.add(snake);
	}

	@Override
	public Snake removeSnake(int i) {
		return snakes.remove(i);
	}
}
