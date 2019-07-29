package design.view.game;

import java.awt.Point;
import java.util.*;

public interface GameField {
	
	public Background getBackground();
	
	public Map<Point, Sprite> getItemSprites();
	
	public List<Sprite> getCell(Point point);
	
	public void addWallSprite(Point point, Sprite sprite);
	
	public Map<Point, Sprite> getWallSprites();
	
	public void addItemSprite(Point point, Sprite sprite);
	
	public void removeItemSprite(Point point, Sprite sprite);
	
	public Map<Point, List<Sprite>> getSnakeSprites(int playerNumber);
	
	public void initNewSnakeMap(int playerNumber);
	
	public void addBodyPart(int playerNumber, Point point, Sprite sprite);
	
	public void endNewSnakeMap(int playerNumber);
	
}
