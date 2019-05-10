package design.view;

import java.awt.Point;
import java.util.Map;
import java.util.Optional;

import javafx.scene.image.Image;

public interface GameField {
	
	public void setBackground(Image image);
	
	public Optional<Image> getItemCell(Point point);
	
	public void addItemSprite(Point point, Image sprite);
	
	public void removeItemSprite(Point point, Image sprite);
	
	public Map<Point, Image> getSnakeSprites(int playerNumber);
	
	public void addBodyPart(int playerNumber, Point point, Image sprite);
	
	public void removeBodyPart(int playerNumer, Point point);
	
	public void resetSnakeSprites(int playerNumber);
	
}
