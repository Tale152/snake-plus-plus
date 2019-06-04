package implementation.view.game;

import java.awt.Point;
import java.util.*;
import design.view.game.*;

public class GameFieldImpl implements GameField {

	private Background bg;
	private final Map<Point, Sprite> itemMap;
	private final Map<Point, Sprite> wallMap;
	private final List<Map<Point, List<Sprite>>> snakeSprites;
	
	public GameFieldImpl(int nPlayer, ResourcesLoader loader) {
		bg = loader.getFieldBackground();
		itemMap = new HashMap<>();
		wallMap = new HashMap<>();
		snakeSprites = new ArrayList<>();
		for (int i = 0; i < nPlayer; ++i) {
			snakeSprites.add(new HashMap<>());
		}
	}

	@Override
	public synchronized Background getBackground() {
		return bg;
	}

	@Override
	public synchronized Map<Point, Sprite> getItemSprites() {
		return new HashMap<>(itemMap);
	}

	@Override
	public synchronized List<Sprite> getCell(Point point) {
		List<Sprite> res = new ArrayList<>();
		if (itemMap.containsKey(point)) {
			res.add(itemMap.get(point));
		}
		if (wallMap.containsKey(point)) {
			res.add(wallMap.get(point));
		}
		for (Map<Point, List<Sprite>> snake : snakeSprites) {
			if (snake.containsKey(point)){
				res.addAll(snake.get(point));
			}
		}
		return res;
	}

	@Override
	public synchronized void addItemSprite(Point point, Sprite sprite) {
		if (!itemMap.containsKey(point)) {
			itemMap.put(point, sprite);
		}
	}

	@Override
	public synchronized void removeItemSprite(Point point, Sprite sprite) {
		if (itemMap.containsKey(point)) {
			itemMap.remove(point);
		}
	}

	@Override
	public synchronized Map<Point, List<Sprite>> getSnakeSprites(int playerNumber) {
		return new HashMap<>(snakeSprites.get(playerNumber));
	}

	@Override
	public synchronized void addBodyPart(int playerNumber, Point point, Sprite sprite) {
		if (!snakeSprites.get(playerNumber).containsKey(point)) {
			snakeSprites.get(playerNumber).put(point, new ArrayList<>());
		}
		snakeSprites.get(playerNumber).get(point).add(sprite);
	}

	@Override
	public synchronized void resetSnakeSprites(int playerNumber) {
		snakeSprites.get(playerNumber).clear();
	}

	@Override
	public synchronized void addWallSprite(Point point, Sprite sprite) {
		if (!wallMap.containsKey(point)) {
			wallMap.put(point, sprite);
		}
	}

	@Override
	public synchronized Map<Point, Sprite> getWallSprites() {
		return new HashMap<>(wallMap);
	}	
}
