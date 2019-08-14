package design.model.game;

import java.awt.Point;
import java.util.List;

public interface Field {

    void begin();

    List<Item> getEliminatedItems();

    int getWidth();

    int getHeight();

    List<Collidable> getCell(Point point);

    List<Item> getItems();

    boolean addItem(Item item) throws IllegalStateException;

    boolean removeItem(Item item);

    List<Wall> getWalls();

    boolean addWall(Wall wall) throws IllegalStateException;

    List<BodyPart> getBodyParts();

    boolean addBodyPart(BodyPart bodyPart) throws IllegalStateException;

    boolean removeBodyPart(BodyPart bodyPart);

    List<Snake> getSnakes();

    boolean addSnake(Snake snake);

    Snake removeSnake(int i);

}
