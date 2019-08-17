package implementation.view.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import design.view.game.Background;
import design.view.game.GameField;
import design.view.game.ResourcesLoader;
import design.view.game.Sprite;

/**
 * @see GameField
 * @author Alessandro Talmi
 */
public class GameFieldImpl implements GameField {

    private final Background bg;
    private final Map<Point, Sprite> itemMap;
    private final Map<Point, Sprite> wallMap;
    private final List<Map<Point, List<Sprite>>> loadingSnakeSprites;
    private final List<Map<Point, List<Sprite>>> actualSnakeSprites;

    /**
     * @param nPlayer number of player that will be displayed
     * @param loader where to get graphical resources to draw
     */
    public GameFieldImpl(final int nPlayer, final ResourcesLoader loader) {
        bg = loader.getFieldBackground();
        itemMap = new HashMap<>();
        wallMap = new HashMap<>();
        actualSnakeSprites = new ArrayList<>();
        loadingSnakeSprites = new ArrayList<>();
        for (int i = 0; i < nPlayer; ++i) {
            actualSnakeSprites.add(new HashMap<>());
            loadingSnakeSprites.add(new HashMap<>());
        }
    }

    @Override
    public final synchronized Background getBackground() {
        return bg;
    }

    @Override
    public final synchronized Map<Point, Sprite> getItemSprites() {
        return new HashMap<>(itemMap);
    }

    @Override
    public final synchronized List<Sprite> getCell(final Point point) {
        final List<Sprite> res = new ArrayList<>();
        if (itemMap.containsKey(point)) {
            res.add(itemMap.get(point));
        }
        if (wallMap.containsKey(point)) {
            res.add(wallMap.get(point));
        }
        for (final Map<Point, List<Sprite>> snake : actualSnakeSprites) {
            if (snake.containsKey(point)) {
                res.addAll(snake.get(point));
            }
        }
        return res;
    }

    @Override
    public final synchronized void addItemSprite(final Point point, final Sprite sprite) {
        if (!itemMap.containsKey(point)) {
            itemMap.put(point, sprite);
        }
    }

    @Override
    public final synchronized void removeItemSprite(final Point point, final Sprite sprite) {
        if (itemMap.containsKey(point)) {
            itemMap.remove(point);
        }
    }

    @Override
    public final synchronized Map<Point, List<Sprite>> getSnakeSprites(final int playerNumber) {
        return new HashMap<>(actualSnakeSprites.get(playerNumber));
    }

    @Override
    public final void initNewSnakeMap(final int playerNumber) {
        loadingSnakeSprites.set(playerNumber, new HashMap<>());
    }

    @Override
    public final synchronized void addBodyPart(final int playerNumber, final Point point, final Sprite sprite) {
        if (!loadingSnakeSprites.get(playerNumber).containsKey(point)) {
            loadingSnakeSprites.get(playerNumber).put(point, new ArrayList<>());
        }
        loadingSnakeSprites.get(playerNumber).get(point).add(sprite);
    }

    @Override
    public final void endNewSnakeMap(final int playerNumber) {
        actualSnakeSprites.set(playerNumber, loadingSnakeSprites.get(playerNumber));
    }

    @Override
    public final synchronized void addWallSprite(final Point point, final Sprite sprite) {
        if (!wallMap.containsKey(point)) {
            wallMap.put(point, sprite);
        }
    }

    @Override
    public final synchronized Map<Point, Sprite> getWallSprites() {
        return new HashMap<>(wallMap);
    }

}
