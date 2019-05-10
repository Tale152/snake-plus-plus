package implementation.view;

import java.awt.Point;
import java.util.*;
import design.view.GameField;
import javafx.scene.image.Image;

public class GameFieldImpl implements GameField {

	private Optional<Image> bg;
	private final Map<Point, Image> spritesMap;
	private final List<Map<Point, Image>> snakeSprites;
	private final GameViewImpl gw;
	
	public GameFieldImpl(GameViewImpl gw, int nPlayer) {
		this.gw = gw;
		bg = Optional.empty();
		spritesMap = new HashMap<>();
		snakeSprites = new ArrayList<>();
		for (int i = 0; i < nPlayer; ++i) {
			snakeSprites.add(new HashMap<>());
		}
	}
	
	@Override
	public void setBackground(Image image) {
		bg = Optional.of(image);
		gw.setDirty();
	}
	
	protected Optional<Image> getBackground() {
		return bg;
	}

	protected Map<Point, Image> getSpritesMap(){
		return spritesMap;
	}
	
	@Override
	public Optional<Image> getItemCell(Point point) {
		if (spritesMap.containsKey(point)) {
			return Optional.of(spritesMap.get(point));
		}
		return Optional.empty();
	}

	@Override
	public void addItemSprite(Point point, Image image) {
		if (!spritesMap.containsKey(point)){
			gw.setDirty();
			spritesMap.put(point, image);
		}
	}

	@Override
	public void removeItemSprite(Point point, Image image) {
		if (spritesMap.containsKey(point)) {
			gw.setDirty();
			spritesMap.remove(point);
		}
	}

	@Override
	public Map<Point, Image> getSnakeSprites(int playerNumber) {
		return new HashMap<>(snakeSprites.get(playerNumber));
	}

	@Override
	public void addBodyPart(int playerNumber, Point point, Image sprite) {
		if (!snakeSprites.get(playerNumber).containsKey(point)) {
			gw.setDirty();
			snakeSprites.get(playerNumber).put(point, sprite);
		}
		
	}

	@Override
	public void removeBodyPart(int playerNumer, Point point) {
		if (snakeSprites.get(playerNumer).containsKey(point)) {
			gw.setDirty();
			snakeSprites.get(playerNumer).remove(point);
		}
		
	}

	@Override
	public void resetSnakeSprites(int playerNumber) {
		gw.setDirty();
		snakeSprites.get(playerNumber).clear();
	}

}
