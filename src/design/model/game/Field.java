package design.model.game;

import java.awt.Point;
import java.util.List;

public interface Field {
	
	public void begin();
	
	public List<Item> getEliminatedItems();
	
	public int getWidth();

	public int getHeight();
	
	public List<Collidable> getCell(Point point);
	
	public List<Item> getItems();

	public boolean addItem(Item item) throws IllegalStateException;
	
	public boolean removeItem(Item item);
	
	public List<Wall> getWalls();
	
	public boolean addWall(Wall wall) throws IllegalStateException;
	
	public List<BodyPart> getBodyParts();
	
	public boolean addBodyPart(BodyPart bodyPart) throws IllegalStateException;
	
	public boolean removeBodyPart(BodyPart bodyPart);
	
	public List<Snake> getSnakes();
	
	public boolean addSnake(Snake snake);
	
}
