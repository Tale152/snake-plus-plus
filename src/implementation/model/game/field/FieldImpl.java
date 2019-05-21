package implementation.model.game.field;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import design.model.game.BodyPart;
import design.model.game.Collidable;
import design.model.game.Field;
import design.model.game.Item;
import design.model.game.Snake;
import design.model.game.Wall;

public class FieldImpl implements Field {
	
	private int width;
	private int height;
	private List<Item> items;
	private List<Wall> walls;
	private List<BodyPart> bodyParts;
	
	private List<Snake> snakes;
	
	private List<Item> removedItems;
	
	private boolean contains(Collidable item) {
		Point coord = item.getPoint();
		int x = (int) coord.getX();
		int y = (int) coord.getY();
		if (x >= 0 && x < this.width && y >= 0 && y <= this.height) {
			return true;
		}
		return false;
	}
	
	private boolean isCollidableAddable(Collidable item, List<? extends Collidable> list) {
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

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public List<Item> getItems() {
		return new ArrayList<Item>(items);
	}

	@Override
	public boolean removeItem(Item item) {
		if (items.remove(item)) {
			removedItems.add(item);
			return true;
		}
		return false;
	}

	@Override
	public boolean addItem(Item item) throws IllegalStateException {
		if (this.isCollidableAddable(item, items)) {
			items.add(item);
			return true;
		}
		return false;
	}

	@Override
	public Optional<List<Collidable>> getCell(Point point) {
		List<Collidable> cellList = new ArrayList<Collidable>();
		cellList.addAll(Arrays.asList(items.stream().filter(i -> {return i.getPoint().equals(point);}).toArray(Collidable[]::new)));
		if (cellList.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(cellList);
		}
	}
	
	
	public FieldImpl(Point dimensions) {
		items = new ArrayList<Item>();
		this.width = (int) dimensions.getX();
		this.height = (int) dimensions.getY();
	}

	@Override
	public void begin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Item> getEliminatedItems() {
		List<Item> returnedList = new ArrayList<Item>(removedItems);
		removedItems.clear();
		return returnedList;
	}

	@Override
	public void togglePause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Wall> getWalls() {
		return new ArrayList<Wall>(walls);
	}

	@Override
	public boolean addWall(Wall wall) throws IllegalStateException {
		if (this.isCollidableAddable(wall, walls)) {
			walls.add(wall);
			return true;
		}
		return false;
	}

	@Override
	public List<BodyPart> getBodyParts() {
		return new ArrayList<BodyPart>(bodyParts);
	}

	@Override
	public boolean addBodyPart(BodyPart bodyPart) throws IllegalStateException {
		if (this.isCollidableAddable(bodyPart, bodyParts)) {
			bodyParts.add(bodyPart);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeBodyPart(BodyPart bodyPart) {
		return bodyParts.remove(bodyPart);
	}

	@Override
	public List<Snake> getSnakes() {
		return new ArrayList<Snake>(snakes);
	}

	@Override
	public boolean addSnake(Snake snake) {
		if (snakes.contains(snake)) {
			return false;
		}
		return snakes.add(snake);
	}
}
