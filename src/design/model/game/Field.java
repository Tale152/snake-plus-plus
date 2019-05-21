package design.model.game;

import java.awt.Point;
import java.util.List;
import java.util.Optional;

public interface Field {
	
	public void begin();
	
	public List<Item> getEliminatedItems();
	
	public void togglePause();
	
	public int getWidth();

	public int getHeight();
	
	public Optional<List<? extends Collidable>> getCell(Point point);
	
	public List<Item> getItems();

	public boolean addItem(Item item) throws IllegalStateException;
	
	public boolean removeItem(Item item);
	
	public List<Wall> getWalls();
	
	public boolean addWall(Wall wall) throws IllegalStateException;
	
	public boolean removeWall(Wall wall);
	
	public List<BodyPart> getBodyParts();
	
	public boolean addBodyPart(BodyPart bodyPart) throws IllegalStateException;
	
	public boolean removeBodyPart(BodyPart bodyPart);
	
	public List<Snake> getSnakes();
	
	public boolean addSnake(Snake snake);
	
}
