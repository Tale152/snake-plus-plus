package implementation.controller.game;

import java.awt.Point;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import design.controller.game.*;
import design.model.game.*;
import design.view.game.ResourcesLoader;
import implementation.controller.game.gameLoader.GameLoaderJSON;
import implementation.model.game.items.*;
import implementation.view.game.GameViewImpl;

public class GameControllerImpl implements GameController {
	
	private final static long CONTROLLER_REFRESH_RATE = 1000/60;
	private final static long MINIMUM_SPAWN_DISTANCE = 2;
	
	private final static String HEAD = "head_";
	private final static String BODY = "body_";
	private final static String TAIL = "tail_";
	private final static String WALL = "wall_";
	
	private final ItemCounter counter;
	private final GameViewImpl gameView;
	private final GameModel gameModel;
	private final ResourcesLoader resources;
	private final EventTranslator controls;
	private final ItemFactory itemFactory;
	private final WinConditions win;
	private final LossConditions loss;
	
	private long gameTime;
	
	
	public GameControllerImpl(String stage, List<String> playerNames, GameViewImpl view, ResourcesLoader resources) throws IOException {
		gameModel = new GameLoaderJSON(stage, playerNames).getGameModel();
		win = gameModel.getGameRules().getWinConditions();
		loss = gameModel.getGameRules().getLossConditions();
		this.itemFactory = new ItemFactory(this.gameModel.getField());
		this.counter = new ItemCounterImpl(this.gameModel.getField(), this.gameModel.getGameRules());
		this.gameView = view;
		this.resources = resources;
		this.controls = new EventTranslatorImpl();
		initView();	
	}
	
	private void initView() {
		initTime();
		initWallSprites();
		initItemSprites();
		initSnakeSpritesAndHUD();
	}
	
	private void initTime() {
		gameTime = gameModel.getGameRules().getInitialTime();
		gameView.getHUD().setTime(convertGameTime());
	}
	
	private String convertGameTime() {
		long minutes = TimeUnit.MILLISECONDS.toMinutes(gameTime);
		  long seconds = TimeUnit.MILLISECONDS.toSeconds(gameTime) - (60 * minutes);
		  return (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
	}
	
	private void initWallSprites() {
		for(Wall w: this.gameModel.getField().getWalls()) {
			String wallName = wallSpriteName(w, this.gameModel.getField().getWalls());
			this.gameView.getField().addWallSprite(w.getPoint(), this.resources.getWall(wallName));
		}
	}
	
	public static String wallSpriteName(Wall wall, List<Wall> allWalls) {
		String s = WALL;
		s += collide(wall, allWalls, new Point(wall.getPoint().x, wall.getPoint().y - 1));
		s += collide(wall, allWalls, new Point(wall.getPoint().x + 1, wall.getPoint().y));
		s += collide(wall, allWalls, new Point(wall.getPoint().x, wall.getPoint().y + 1));
		s += collide(wall, allWalls, new Point(wall.getPoint().x - 1, wall.getPoint().y));
		return s;
		
	}
	
	private static String collide(Wall wall, List<Wall> allWalls, Point point) {
		return allWalls.stream().anyMatch(e -> {
			return e.getPoint().equals(point);
		}) ? "1" : "0";
	}
	
	private void initItemSprites() {
		for(Item i: this.gameModel.getField().getItems()) {
			this.gameView.getField().addItemSprite(i.getPoint(), this.resources.getItem(i.getEffectClass().getSimpleName()));
		}
	}
	
	private void initSnakeSpritesAndHUD() {
		int i = 0;
		for(Snake s : this.gameModel.getField().getSnakes()) {
			if(s.isAlive()) {
				gameView.getField().initNewSnakeMap(i);
				for(BodyPart b : s.getBodyParts()) {
					this.gameView.getField().addBodyPart(s.getPlayer().getPlayerNumber().ordinal(), b.getPoint(), this.resources.getBodyPart(snakeSpriteName(b, s)));
				}
				gameView.getField().endNewSnakeMap(i);
				gameView.getHUD().getPlayerHUDs().get(i).setName(s.getPlayer().getName());
				gameView.getHUD().getPlayerHUDs().get(i).setScore(Integer.toString(s.getPlayer().getScore()));
				gameView.getHUD().getPlayerHUDs().get(i).setSnakeSprite(resources.getBodyPart("P" + (i + 1) + "_head_tail_DOWN"));
			}
			++i;
		}
	}
	
	private String snakeSpriteName(BodyPart b, Snake snake) {
		String s = "P" + Integer.toString(snake.getPlayer().getPlayerNumber().ordinal() + 1) + "_";
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
	
	@Override
	public void run() {
		this.gameModel.getField().begin();
		this.gameView.startRendering();
		while(!isGameEnded()) {
			updateDeletedItems();
			spawnItems();
			updateSnakes();
			waitAndUpdateTime();
		}
		this.gameView.stopRendering();
	}
	
	private boolean isGameEnded() {
		List<Snake> snakes = this.gameModel.getField().getSnakes();
		return loss.checkSnakes(snakes) || loss.checkTime(gameTime) ||
				win.checkScore(snakes) || win.checkSnakeLength(snakes) || win.checkTime(gameTime);
	}
	
	private void updateDeletedItems() {
		List<Item> deletedItems = this.gameModel.getField().getEliminatedItems();
		for(Item i : deletedItems) {
			this.counter.decrease(i.getEffectClass());
			this.gameView.getField().removeItemSprite(i.getPoint(), this.resources.getItem(i.getEffectClass().getSimpleName()));
		}
	}
	
	private void spawnItems() {
		long time = System.currentTimeMillis();
		for(ItemRule rule : this.gameModel.getGameRules().getItemRules()) {
			if(!this.counter.isMax(rule.getEffectClass())) {
				if(time - this.counter.getLastSpawnAttempt(rule.getEffectClass()) >= rule.getSpawnDelta()) {
					this.counter.setLastSpawnAttempt(rule.getEffectClass(), time);
					if(Math.random() <= rule.getSpawnChance()) {
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
			if(this.gameModel.getField().getCell(p).isEmpty() &&
					!checkRandomPointToSnakeCloseness(p)) {
				break;
			}
		}
		return p;
	}
	
	/**
	 * Returns true if the random generated points is inside a range in front of any of the currently alive snakes
	 * @param point
	 * @return
	 */
	private boolean checkRandomPointToSnakeCloseness(Point point) {
		for (Snake snake : gameModel.getField().getSnakes()) {
			if (snake.isAlive() && checkIntoNextTwoCells(snake, point)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns true if the random generated points is inside a range in front of given snake
	 * @param point
	 * @return
	 */
	private boolean checkIntoNextTwoCells(Snake snake, Point point) {
		switch(snake.getProperties().getDirectionProperty().getDirection()) {
			case LEFT:
			case RIGHT:	return Math.abs(snake.getBodyParts().get(0).getPoint().getX() - point.getX()) <= MINIMUM_SPAWN_DISTANCE;
			case UP:
			case DOWN:return Math.abs(snake.getBodyParts().get(0).getPoint().getY() - point.getY()) <= MINIMUM_SPAWN_DISTANCE;
			default: return true;
		}
	}
	
	private void updateSnakes() {
		List<Snake> snakes = this.gameModel.getField().getSnakes();
		for(Snake s : snakes) {
			int nPlayer = s.getPlayer().getPlayerNumber().ordinal();
			if(s.isAlive()) {
				if (s.hasMoved()) {
					this.gameView.getField().initNewSnakeMap(nPlayer);
					for(BodyPart b : s.getBodyParts()) {
						this.gameView.getField().addBodyPart(s.getPlayer().getPlayerNumber().ordinal(), b.getPoint(), this.resources.getBodyPart(snakeSpriteName(b, s)));
						gameView.getHUD().getPlayerHUDs().get(nPlayer).setScore(Integer.toString(s.getPlayer().getScore()));
					}
					this.gameView.getField().endNewSnakeMap(nPlayer);
				}
				gameView.getHUD().getPlayerHUDs().get(nPlayer).newEffectSpriteList();
				for (Effect effect : s.getEffects()) {
					gameView.getHUD().getPlayerHUDs().get(nPlayer).addEffectSprite(resources.getItem(effect.getClass().getSimpleName()));
				}
				gameView.getHUD().getPlayerHUDs().get(nPlayer).endEffectSpriteList();
			} 
			else {
				gameView.getHUD().getPlayerHUDs().get(nPlayer).newEffectSpriteList();
				gameView.getHUD().getPlayerHUDs().get(nPlayer).endEffectSpriteList();
				this.gameView.getField().initNewSnakeMap(nPlayer);
				this.gameView.getField().endNewSnakeMap(nPlayer);
				gameView.getHUD().getPlayerHUDs().get(nPlayer).setAlive(false);
				}
			}
		}
	
	private void waitAndUpdateTime() {
		try {
			long timeBeforeSleep = System.currentTimeMillis();
			Thread.sleep(CONTROLLER_REFRESH_RATE);
			if (gameModel.getGameRules().isTimeGoingForward()) {
				gameTime += System.currentTimeMillis() - timeBeforeSleep;
			} else {
				gameTime -= System.currentTimeMillis() - timeBeforeSleep;
			}
			gameView.getHUD().setTime(convertGameTime());
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
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

}
