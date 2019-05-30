package implementation.controller.game;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import design.controller.game.Action;
import design.controller.game.EventTranslator;
import design.controller.game.GameController;
import design.controller.game.GameLoader;
import design.controller.game.InputEvent;
import design.controller.game.ItemCounter;
import design.model.game.BodyPart;
import design.model.game.DirectionProperty;
import design.model.game.GameModel;
import design.model.game.Item;
import design.model.game.ItemRule;
import design.model.game.LossConditions;
import design.model.game.Snake;
import design.model.game.Wall;
import design.model.game.WinConditions;
import design.view.game.GameView;
import design.view.game.ResourcesLoader;
import implementation.controller.game.gameLoader.GameLoaderJSON;
import implementation.model.game.items.ItemFactory;

public class GameControllerImpl implements GameController {
	
	private final static String HEAD = "head_";
	private final static String BODY = "body_";
	private final static String TAIL = "tail_";
	private final static String WALL = "wall_";
	
	private final ItemCounter counter;
	private final GameView gameView;
	private final GameModel gameModel;
	private final ResourcesLoader resources;
	private final EventTranslator controls;
	private final ItemFactory itemFactory;
	
	
	public GameControllerImpl(String stage, List<String> playerNames, GameView view, ResourcesLoader resources) throws IOException {
		this.gameModel = new GameLoaderJSON(stage, playerNames).getGameModel();
		this.counter = new ItemCounterImpl(this.gameModel.getField(), this.gameModel.getGameRules());
		this.gameView = view;
		this.resources = resources;
		this.controls = new EventTranslatorImpl();
		
		
	}

	private void initView() {
		for(Wall w: this.gameModel.getField().getWalls()) {
		}
	}
	
	@Override
	public void run() {
		this.gameModel.getField().begin();
		while(!isGameEnded()) {
			updateDeletedItems();
			spawnItems();
			snakeView();
			this.gameView.update();
		}

	}

	@Override
	public void playerInput(InputEvent input) {
		Optional<Action> action = controls.getEventBinding(input);
		if (action.isPresent()) {
			Snake target = gameModel.getField().getSnakes().get(action.get().getPlayerNumber().ordinal());
			DirectionProperty direction = target.getProperties().getDirectionProperty();
			direction.setDirection(action.get().getDirection());
		}
	}
	
	private boolean isGameEnded() {
		WinConditions win = this.gameModel.getGameRules().getWinConditions();
		LossConditions loss = this.gameModel.getGameRules().getLossConditions();
		List<Snake> snakes = this.gameModel.getField().getSnakes();
		Long time = System.currentTimeMillis();
		return loss.checkSnakes(snakes) || loss.checkTime(time) ||
				win.checkScore(snakes) || win.checkSnakeLength(snakes) || win.checkTime(time);
	}
	
	private void updateDeletedItems() {
		List<Item> deletedItems = this.gameModel.getField().getEliminatedItems();
		for(Item i : deletedItems) {
			this.counter.decrease(i.getEffectClass());
			this.gameView.getField().removeItemSprite(i.getPoint(), this.resources.getItem(i.getClass().getSimpleName()));
		}
	}
	
	private void spawnItems() {
		long time = System.currentTimeMillis();
		for(ItemRule rule : this.gameModel.getGameRules().getItemRules()) {
			if(!this.counter.isMax(rule.getEffectClass())) {
				if(time - this.counter.getLastSpawnAttempt(rule.getEffectClass()) >= rule.getSpawnDelta()) {
					this.counter.setLastSpawnAttempt(rule.getEffectClass(), time);
					if(Math.random() >= rule.getSpawnChance()) {
						Point p = getRandomPoint();
						this.itemFactory.createItem(p, rule.getEffectClass(), rule.getItemDuration(), rule.getEffectDuration());
						this.counter.increase(rule.getEffectClass());
						this.gameView.getField().addItemSprite(p, this.resources.getItem(rule.getEffectClass().getSimpleName()));
					}
				}
			}
		}
	}

	private Point getRandomPoint() {
		int height = this.gameModel.getField().getHeight();
		int width = this.gameModel.getField().getWidth();
		Point p;
		Random random = new Random();
		while(true) {
			p = new Point(random.nextInt(width), random.nextInt(height));
			if(this.gameModel.getField().getCell(p).isEmpty()) {
				break;
			}
		}
		return p;
	}
	
	private void snakeView() {
		List<Snake> snakes = this.gameModel.getField().getSnakes();
		this.gameView.getField().resetSnakeSprites(snakes.size());
		for(Snake s : snakes) {
			if(s.isAlive()) {
				for(BodyPart b : s.getBodyParts()) {
					this.gameView.getField().addBodyPart(s.getPlayer().getPlayerNumber().ordinal(), b.getPoint(), this.resources.getBodyPart(snakeSpriteName(b, s)));
				}
			}
		}
	}
	
	private String snakeSpriteName(BodyPart b, Snake snake) {
		String s = "P" + snake.getPlayer().getPlayerNumber().ordinal() + 1;
		if(b.isHead()) {
			s += HEAD;
		}
		if(b.isBody()) {
			s += BODY;
		}
		if(b.isTail()) {
			s += TAIL;
		}
		if(b.isHead() && b.isTail()) {
			return s += snake.getProperties().getDirectionProperty().getDirection();
		} else {
			return s += isCombined(b.isCombinedOnTop()) + isCombined(b.isCombinedOnRight()) + isCombined(b.isCombinedOnBottom()) + isCombined(b.isCombinedOnLeft());
		}
	}
	
	private String isCombined(boolean b) {
		return b ? "1" : "0";
	}
	
	private String wallSpriteName(Wall wall, List<Wall> allWalls) {
		String s = WALL;
		s += collide(wall, allWalls, new Point(wall.getPoint().x, wall.getPoint().y - 1));
		s += collide(wall, allWalls, new Point(wall.getPoint().x + 1, wall.getPoint().y));
		s += collide(wall, allWalls, new Point(wall.getPoint().x, wall.getPoint().y + 1));
		s += collide(wall, allWalls, new Point(wall.getPoint().x - 1, wall.getPoint().y));
		return s;
		
	}
	
	private String collide(Wall wall, List<Wall> allWalls, Point point) {
		if(point.x < 0 || point.y < 0 || point.x >= this.gameModel.getField().getWidth() || point.y >= this.gameModel.getField().getHeight()) {
			return "0";
		}
		return allWalls.stream().anyMatch(e -> {
			return e.getPoint().equals(point);
		}) ? "1" : "0";
	}
}
