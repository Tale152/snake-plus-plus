package implementation.controller.game;

import java.awt.Point;
import java.io.IOException;
import java.util.*;

import design.controller.game.*;
import design.model.game.*;
import design.view.game.ResourcesLoader;
import implementation.controller.game.gameLoader.GameLoaderJSON;
import implementation.model.game.items.*;
import implementation.view.game.GameViewImpl;

public class GameControllerImpl implements GameController {
	
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
	
	private long gameTime;
	
	
	public GameControllerImpl(String stage, List<String> playerNames, GameViewImpl view, ResourcesLoader resources) throws IOException {
		this.gameModel = new GameLoaderJSON(stage, playerNames).getGameModel();
		this.itemFactory = new ItemFactory(this.gameModel.getField());
		this.counter = new ItemCounterImpl(this.gameModel.getField(), this.gameModel.getGameRules());
		this.gameView = view;
		this.resources = resources;
		this.controls = new EventTranslatorImpl();
		initView();	
	}
	
	private String getTimeFormat() {
		return Long.toString(gameTime / 1000L);
	}

	private void initView() {
		gameTime = gameModel.getGameRules().getInitialTime();
		for(Wall w: this.gameModel.getField().getWalls()) {
			String wallName = wallSpriteName(w, this.gameModel.getField().getWalls());
			this.gameView.getField().addWallSprite(w.getPoint(), this.resources.getWall(wallName));
		}
		for(Item i: this.gameModel.getField().getItems()) {
			this.gameView.getField().addItemSprite(i.getPoint(), this.resources.getItem(i.getEffectClass().getSimpleName()));
		}
		int i = 0;
		for(Snake s : this.gameModel.getField().getSnakes()) {
			if(s.isAlive()) {
				for(BodyPart b : s.getBodyParts()) {
					this.gameView.getField().addBodyPart(s.getPlayer().getPlayerNumber().ordinal(), b.getPoint(), this.resources.getBodyPart(snakeSpriteName(b, s)));
				}
				gameView.getHUD().getPlayerHUDs().get(i).setName(s.getPlayer().getName());
				gameView.getHUD().getPlayerHUDs().get(i).setScore(Integer.toString(s.getPlayer().getScore()));
			}
			++i;
		}
		gameView.getHUD().setTime(getTimeFormat());
	}
	
	@Override
	public void run() {
		this.gameModel.getField().begin();
		this.gameView.startRendering();
		while(!isGameEnded()) {
			updateDeletedItems();
			spawnItems();
			snakeView();
			try {
				long timeBeforeSleep = System.currentTimeMillis();
				Thread.sleep(1000/60);
				if (gameModel.getGameRules().isTimeGoingForward()) {
					gameTime += System.currentTimeMillis() - timeBeforeSleep;
				} else {
					gameTime -= System.currentTimeMillis() - timeBeforeSleep;
				}
				gameView.getHUD().setTime(getTimeFormat());
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		this.gameView.stopRendering();
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
			if(this.gameModel.getField().getCell(p).isEmpty()) {
				break;
			}
		}
		return p;
	}
	
	private void snakeView() {
		List<Snake> snakes = this.gameModel.getField().getSnakes();
		int i = 0;
		for(Snake s : snakes) {
			if(s.isAlive()) {
				if (s.hasMoved()) {
					this.gameView.getField().resetSnakeSprites(i);
					for(BodyPart b : s.getBodyParts()) {
						this.gameView.getField().addBodyPart(s.getPlayer().getPlayerNumber().ordinal(), b.getPoint(), this.resources.getBodyPart(snakeSpriteName(b, s)));
						gameView.getHUD().getPlayerHUDs().get(i).setScore(Integer.toString(s.getPlayer().getScore()));
					}
				}
			}
			else {
				this.gameView.getField().resetSnakeSprites(i);
				gameView.getHUD().getPlayerHUDs().get(i).setAlive(false);
				}
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
