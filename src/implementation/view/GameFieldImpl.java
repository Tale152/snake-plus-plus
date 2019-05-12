package implementation.view;

import java.awt.Point;
import java.util.*;
import design.view.GameField;
import design.view.Sprite;
import javafx.scene.image.Image;

public class GameFieldImpl implements GameField {

	private Optional<Image> bg;
	private final Map<Point, Sprite> spritesMap;
	private final List<Map<Point, Sprite>> snakeSprites;
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
	
	@Override
	public Optional<Sprite> getItemCell(Point point) {
		if (spritesMap.containsKey(point)) {
			return Optional.of(spritesMap.get(point));
		}
		return Optional.empty();
	}

	@Override
	public void addItemSprite(Point point, Sprite sprite) {
		if (!spritesMap.containsKey(point)){
			gw.setDirty();
			spritesMap.put(point, sprite);
		}
	}

	@Override
	public void removeItemSprite(Point point, Sprite sprite) {
		if (spritesMap.containsKey(point)) {
			gw.setDirty();
			spritesMap.remove(point);
		}
	}

	@Override
	public Map<Point, Sprite> getSnakeSprites(int playerNumber) {
		return new HashMap<>(snakeSprites.get(playerNumber));
	}

	@Override
	public void addBodyPart(int playerNumber, Point point, Sprite sprite) {
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
