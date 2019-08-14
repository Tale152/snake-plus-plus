package design.view.game;

import java.awt.Point;
import java.util.*;

public interface GameField {

    Background getBackground();

    Map<Point, Sprite> getItemSprites();

    List<Sprite> getCell(Point point);

    void addWallSprite(Point point, Sprite sprite);

    Map<Point, Sprite> getWallSprites();

    void addItemSprite(Point point, Sprite sprite);

    void removeItemSprite(Point point, Sprite sprite);

    Map<Point, List<Sprite>> getSnakeSprites(int playerNumber);

    void initNewSnakeMap(int playerNumber);

    void addBodyPart(int playerNumber, Point point, Sprite sprite);

    void endNewSnakeMap(int playerNumber);

}
