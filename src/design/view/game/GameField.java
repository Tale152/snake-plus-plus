package design.view.game;

import java.awt.Point;
import java.util.*;

public interface GameField {
	
	public Background getBackground();
	
	public Map<Point, Sprite> getItemSprites();
	
	public Optional<Sprite> getItemCell(Point point);
	
	public void addItemSprite(Point point, Sprite sprite);
	
	public void removeItemSprite(Point point, Sprite sprite);
	
	public Map<Point, List<Sprite>> getSnakeSprites(int playerNumber);
	
	public void addBodyPart(int playerNumber, Point point, Sprite sprite);
	
	public void resetSnakeSprites(int playerNumber);
	
}
