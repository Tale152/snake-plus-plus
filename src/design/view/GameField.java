package design.view;

import java.awt.Point;
import java.util.Map;
import java.util.Optional;

import javafx.scene.image.Image;

public interface GameField {
	
	public void setBackground(Image image);
	
	public Optional<Sprite> getItemCell(Point point);
	
	public void addItemSprite(Point point, Sprite sprite);
	
	public void removeItemSprite(Point point, Sprite sprite);
	
	public Map<Point, Sprite> getSnakeSprites(int playerNumber);
	
	public void addBodyPart(int playerNumber, Point point, Sprite sprite);
	
	public void removeBodyPart(int playerNumer, Point point);
	
	public void resetSnakeSprites(int playerNumber);
	
}
