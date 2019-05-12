package implementation.view;

import java.awt.Point;
import java.util.*;
import design.view.*;

public class GameFieldImpl implements GameField {

	private Background bg;
	private final Map<Point, Sprite> spritesMap;
	private final List<Map<Point, List<Sprite>>> snakeSprites;
	private final GameView gw;
	
	public GameFieldImpl(GameView gw, int nPlayer, ResourcesLoader loader) {
		this.gw = gw;
		bg = loader.getFieldBg();
		spritesMap = new HashMap<>();
		snakeSprites = new ArrayList<>();
		for (int i = 0; i < nPlayer; ++i) {
			snakeSprites.add(new HashMap<>());
		}
	}

	@Override
	public Background getBackground() {
		return bg;
	}

	@Override
	public Map<Point, Sprite> getItemSprites() {
		return new HashMap<>(spritesMap);
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
		if (!spritesMap.containsKey(point)) {
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
	public Map<Point, List<Sprite>> getSnakeSprites(int playerNumber) {
		return new HashMap<>(snakeSprites.get(playerNumber));
	}

	@Override
	public void addBodyPart(int playerNumber, Point point, Sprite sprite) {
		gw.setDirty();
		if (!snakeSprites.get(playerNumber).containsKey(point)) {
			snakeSprites.get(playerNumber).put(point, new ArrayList<>());
		}
		snakeSprites.get(playerNumber).get(point).add(sprite);
	}

	@Override
	public void resetSnakeSprites(int playerNumber) {
		gw.setDirty();
		snakeSprites.get(playerNumber).clear();
	}
	
}
