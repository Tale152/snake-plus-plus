package design.view;

import java.awt.Point;
import java.util.List;

import javafx.scene.image.Image;

public interface GameField {
	
	public void setBackground(Image image);
	
	public List<Image> getCell(Point point);
	
	public void addSprite(Point point, Image image);
	
	public void removeSprite(Point point, Image image);
	
}
