package implementation.view;

import java.awt.Point;
import java.util.*;
import design.view.GameField;
import javafx.scene.image.Image;

public class GameFieldImpl implements GameField {

	private Optional<Image> bg;
	private final Map<Point, List<Image>> spritesMap;
	private final GameViewImpl gw;
	
	public GameFieldImpl(GameViewImpl gw) {
		this.gw = gw;
		bg = Optional.empty();
		spritesMap = new HashMap<>();
	}
	
	@Override
	public void setBackground(Image image) {
		bg = Optional.of(image);
		gw.setDirty();
	}
	
	protected Optional<Image> getBackground() {
		return bg;
	}

	protected Map<Point, List<Image>> getSpritesMap(){
		return spritesMap;
	}
	
	@Override
	public List<Image> getCell(Point point) {
		if (spritesMap.containsKey(point)) {
			return spritesMap.get(point);
		}
		return new ArrayList<>();
	}

	@Override
	public void addSprite(Point point, Image image) {
		gw.setDirty();
		if (spritesMap.containsKey(point)){
			spritesMap.get(point).add(image);
		}
		else {
			spritesMap.put(point, new ArrayList<Image>(Arrays.asList(image)));
		}
	}

	@Override
	public void removeSprite(Point point, Image image) {
		gw.setDirty();
		if (spritesMap.containsKey(point)) {
			spritesMap.get(point).remove(image);
			if (spritesMap.get(point).isEmpty()) {
				spritesMap.remove(point);
			}
		}
	}

}
