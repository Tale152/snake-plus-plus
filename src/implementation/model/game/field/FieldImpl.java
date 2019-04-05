package implementation.model.game.field;

import java.awt.Point;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import design.model.game.Field;
import design.model.game.Item;

public class FieldImpl implements Field {
	
	private int width;
	private int height;
	private List<Item> items;

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
		return items.remove(item);
	}

	@Override
	public boolean addItem(Item item) throws IllegalStateException { // TODO: make an actual exception
		if (items.contains(item)) {
			return false;
		} else {
			Point coord = item.getPoint();
			double x = coord.getX();
			double y = coord.getY();
			if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
				items.add(item);
			} else {
				throw new IllegalStateException();
			}
		}
		return true;
	}

	@Override
	public Optional<List<Item>> getCell(Point point) {
		Item[] cellArray = items.stream().filter(i -> {return i.getPoint().equals(point);}).toArray(Item[]::new);
		int cellSize = Array.getLength(cellArray);
		if (cellSize == 0) {
			return Optional.empty();
		} else {
			ArrayList<Item> cell = new ArrayList<Item>(Arrays.asList(cellArray));
			return Optional.of(cell);
		}
	}
	
	
	public FieldImpl(Point dimensions) {
		items = new ArrayList<Item>();
		this.width = (int) dimensions.getX();
		this.height = (int) dimensions.getY();
	}
}
