package implementation.controller.game;

import java.awt.Point;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import design.controller.application.GameEndReason;
import design.controller.application.GameInterstice;

import design.controller.game.Action;
import design.controller.game.EventTranslator;
import design.controller.game.GameController;
import design.controller.game.InputEvent;
import design.controller.game.ItemCounter;
import design.model.game.BodyPart;
import design.model.game.DirectionProperty;
import design.model.game.Effect;
import design.model.game.GameModel;
import design.model.game.Item;
import design.model.game.ItemRule;
import design.model.game.LossConditions;
import design.model.game.Snake;
import design.model.game.Wall;
import design.model.game.WinConditions;
import design.view.game.ResourcesLoader;
import implementation.model.game.items.ItemFactory;
import implementation.view.game.GameViewImpl;
import javafx.application.Platform;

/**
 * @see GameController
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 */
public class GameControllerImpl implements GameController {

    private static final long CONTROLLER_REFRESH_RATE = 1000 / 60;
    private static final long MINIMUM_SPAWN_DISTANCE = 2;

    private static final String HEAD = "head_";
    private static final String BODY = "body_";
    private static final String TAIL = "tail_";
    private static final String WALL = "wall_";

    private final ItemCounter counter;
    private final GameViewImpl gameView;
    private final GameModel gameModel;
    private final ResourcesLoader resources;
    private final EventTranslator controls;
    private final ItemFactory itemFactory;
    private final WinConditions win;
    private final LossConditions loss;
    private GameInterstice interstice;

    private long gameTime;

    /**
     * @param gameModel the game model containing the game to play
     * @param view      the view were the game will be displayed
     * @param resources where to find the graphical resources to print
     * @throws IOException if there are problems loading one of the files
     */
    public GameControllerImpl(final GameModel gameModel, final GameViewImpl view, final ResourcesLoader resources) throws IOException {
        this.gameModel = gameModel;
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
        final long minutes = TimeUnit.MILLISECONDS.toMinutes(gameTime);
        final long seconds = TimeUnit.MILLISECONDS.toSeconds(gameTime) - (60 * minutes);
        return (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
    }

    private void initWallSprites() {
        for (final Wall w : this.gameModel.getField().getWalls()) {
            final String wallName = wallSpriteName(w, this.gameModel.getField().getWalls());
            this.gameView.getField().addWallSprite(w.getPoint(), this.resources.getWall(wallName));
        }
    }

    /**
     * Algorithm to obtain the correct wall sprite name.
     * 
     * @param wall     that you are interested in getting the right name
     * @param allWalls all the walls in the game (the desired one can be in the
     *                 list)
     * @return the correct sprite name
     */
    public static String wallSpriteName(final Wall wall, final List<Wall> allWalls) {
        String s = WALL;
        s += collide(allWalls, new Point(wall.getPoint().x, wall.getPoint().y - 1));
        s += collide(allWalls, new Point(wall.getPoint().x + 1, wall.getPoint().y));
        s += collide(allWalls, new Point(wall.getPoint().x, wall.getPoint().y + 1));
        s += collide(allWalls, new Point(wall.getPoint().x - 1, wall.getPoint().y));
        return s;
    }

    @Override
    public final void run() {
        this.gameModel.getField().begin();
        this.gameView.startRendering();
        while (!isGameEnded()) {
            updateDeletedItems();
            spawnItems();
            updateSnakes();
            waitAndUpdateTime();
        }
        this.gameView.stopRendering();
        try {
            interstice.setGameEndReason(getGameEndReason());
            interstice.showInterstice();
        } catch (IOException e) {
            e.printStackTrace();
            Platform.exit();
        }
    }

    private GameEndReason getGameEndReason() {
        final List<Snake> snakes = this.gameModel.getField().getSnakes();
        if (win.checkSnakeLength(snakes)) {
            return GameEndReason.WON_LENGTH;
        } else if (win.checkTime(gameTime)) {
            return GameEndReason.WON_TIME;
        } else if (win.checkScore(snakes)) {
            return GameEndReason.WON_SCORE;
        } else if (loss.checkSnakes(snakes)) {
            return GameEndReason.LOST_DEATH;
        } else if (loss.checkTime(gameTime)) {
            return GameEndReason.LOST_TIME;
        }
        return GameEndReason.ERROR;
    }

    private static String collide(final List<Wall> allWalls, final Point point) {
        return allWalls.stream().anyMatch(e -> {
            return e.getPoint().equals(point);
        }) ? "1" : "0";
    }

    private void initItemSprites() {
        for (final Item i : this.gameModel.getField().getItems()) {
            this.gameView.getField().addItemSprite(i.getPoint(),
                    this.resources.getItem(i.getEffectClass().getSimpleName()));
        }
    }

    private void initSnakeSpritesAndHUD() {
        int i = 0;
        for (final Snake s : this.gameModel.getField().getSnakes()) {
            if (s.isAlive()) {
                gameView.getField().initNewSnakeMap(i);
                for (final BodyPart b : s.getBodyParts()) {
                    this.gameView.getField().addBodyPart(s.getPlayer().getPlayerNumber().ordinal(), b.getPoint(),
                            this.resources.getBodyPart(snakeSpriteName(b, s)));
                }
                gameView.getField().endNewSnakeMap(i);
                gameView.getHUD().getPlayerHUDs().get(i).setName(s.getPlayer().getName());
                gameView.getHUD().getPlayerHUDs().get(i).setScore(Integer.toString(s.getPlayer().getScore()));
                gameView.getHUD().getPlayerHUDs().get(i)
                        .setSnakeSprite(resources.getBodyPart("P" + (i + 1) + "_head_tail_DOWN"));
            }
            ++i;
        }
    }

    private String snakeSpriteName(final BodyPart b, final Snake snake) {
        String s = "P" + Integer.toString(snake.getPlayer().getPlayerNumber().ordinal() + 1) + "_";
        if (b.isHead()) {
            s += HEAD;
        }
        if (b.isBody()) {
            s += BODY;
        }
        if (b.isTail()) {
            s += TAIL;
        }
        if (b.isHead() && b.isTail()) {
            s += snake.getProperties().getDirectionProperty().getDirection();
            return s;
        } else {
            s += isCombined(b.isCombinedOnTop()) + isCombined(b.isCombinedOnRight())
                    + isCombined(b.isCombinedOnBottom()) + isCombined(b.isCombinedOnLeft());
            return s;
        }
    }

    private String isCombined(final boolean b) {
        return b ? "1" : "0";
    }

    @Override
    public final boolean isGameEnded() {
        final List<Snake> snakes = this.gameModel.getField().getSnakes();
        return loss.checkSnakes(snakes) || loss.checkTime(gameTime) || win.checkScore(snakes)
                || win.checkSnakeLength(snakes) || win.checkTime(gameTime);
    }

    private void updateDeletedItems() {
        final List<Item> deletedItems = this.gameModel.getField().getEliminatedItems();
        for (final Item i : deletedItems) {
            this.counter.decrease(i.getEffectClass());
            this.gameView.getField().removeItemSprite(i.getPoint(),
                    this.resources.getItem(i.getEffectClass().getSimpleName()));
        }
    }

    private void spawnItems() {
        final long time = System.currentTimeMillis();
        for (final ItemRule rule : this.gameModel.getGameRules().getItemRules()) {
            if (!this.counter.isMax(rule.getEffectClass())
                    && time - this.counter.getLastSpawnAttempt(rule.getEffectClass()) >= rule.getSpawnDelta()) {
                this.counter.setLastSpawnAttempt(rule.getEffectClass(), time);
                if (Math.random() <= rule.getSpawnChance()) {
                    final Point p = getRandomPoint();
                    this.itemFactory.createItem(p, rule.getEffectClass(), rule.getItemDuration(),
                            rule.getEffectDuration());
                    this.counter.increase(rule.getEffectClass());
                    this.gameView.getField().addItemSprite(p,
                            this.resources.getItem(rule.getEffectClass().getSimpleName()));
                }
            }
        }
    }

    private Point getRandomPoint() {
        final int height = this.gameModel.getField().getHeight();
        final int width = this.gameModel.getField().getWidth();
        Point p;
        final Random random = new Random();
        while (true) {
            p = new Point(random.nextInt(width), random.nextInt(height));
            if (this.gameModel.getField().getCell(p).isEmpty() && !checkRandomPointToSnakeCloseness(p)) {
                break;
            }
        }
        return p;
    }

    /**
     * Returns true if the random generated points is inside a range in front of any
     * of the currently alive snakes.
     * 
     * @param point to check
     * @return true if it the point is inside a range in front of any of the
     *         currently alive snakes, false otherwise
     */
    private boolean checkRandomPointToSnakeCloseness(final Point point) {
        for (final Snake snake : gameModel.getField().getSnakes()) {
            if (snake.isAlive() && checkIntoNextTwoCells(snake, point)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the random generated points is inside a range in front of
     * given snake.
     * 
     * @param snake the snake to check
     * @param point the point to check
     * @return true if the point is inside a range in front of the given snake,
     *         false otherwise
     */
    private boolean checkIntoNextTwoCells(final Snake snake, final Point point) {
        switch (snake.getProperties().getDirectionProperty().getDirection()) {
        case LEFT:
        case RIGHT:
            return Math.abs(snake.getBodyParts().get(0).getPoint().getX() - point.getX()) <= MINIMUM_SPAWN_DISTANCE;
        case UP:
        case DOWN:
            return Math.abs(snake.getBodyParts().get(0).getPoint().getY() - point.getY()) <= MINIMUM_SPAWN_DISTANCE;
        default:
            return true;
        }
    }

    private void updateSnakes() {
        final List<Snake> snakes = this.gameModel.getField().getSnakes();
        for (final Snake s : snakes) {
            final int nPlayer = s.getPlayer().getPlayerNumber().ordinal();
            if (s.isAlive()) {
                if (s.hasMoved()) {
                    this.gameView.getField().initNewSnakeMap(nPlayer);
                    for (final BodyPart b : s.getBodyParts()) {
                        this.gameView.getField().addBodyPart(s.getPlayer().getPlayerNumber().ordinal(), b.getPoint(),
                                this.resources.getBodyPart(snakeSpriteName(b, s)));
                        gameView.getHUD().getPlayerHUDs().get(nPlayer)
                                .setScore(Integer.toString(s.getPlayer().getScore()));
                    }
                    this.gameView.getField().endNewSnakeMap(nPlayer);
                }
                gameView.getHUD().getPlayerHUDs().get(nPlayer).newEffectSpriteList();
                for (final Effect effect : s.getEffects()) {
                    gameView.getHUD().getPlayerHUDs().get(nPlayer)
                            .addEffectSprite(resources.getItem(effect.getClass().getSimpleName()));
                }
                gameView.getHUD().getPlayerHUDs().get(nPlayer).endEffectSpriteList();
            } else {
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
            final long timeBeforeSleep = System.currentTimeMillis();
            Thread.sleep(CONTROLLER_REFRESH_RATE);
            if (gameModel.getGameRules().isTimeGoingForward()) {
                gameTime += System.currentTimeMillis() - timeBeforeSleep;
            } else {
                gameTime -= System.currentTimeMillis() - timeBeforeSleep;
            }
            gameView.getHUD().setTime(convertGameTime());
        } catch (InterruptedException e) {
            throw new RuntimeException(e.toString());
        }
    }

    @Override
    public final void playerInput(final InputEvent input) {
        final Optional<Action> action = controls.getEventBinding(input);
        if (action.isPresent() 
                && gameModel.getField().getSnakes().size() > action.get().getPlayerNumber().ordinal()) {
            final Snake target = gameModel.getField().getSnakes().get(action.get().getPlayerNumber().ordinal());
            final DirectionProperty direction = target.getProperties().getDirectionProperty();
            direction.setDirection(action.get().getDirection());
        }
    }

    @Override
    public final void setInterstice(final GameInterstice interstice) {
        this.interstice = interstice;
    }

}
