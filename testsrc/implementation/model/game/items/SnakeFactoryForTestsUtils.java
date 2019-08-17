package implementation.model.game.items;

import java.awt.Point;
import java.util.List;

import design.model.game.Direction;
import design.model.game.Field;
import design.model.game.PlayerNumber;
import design.model.game.Snake;
import implementation.model.game.snake.SnakeImpl;

/**
 * Factory to create all possible kind of snakes to use them into tests.
 */
public final class SnakeFactoryForTestsUtils {

    private SnakeFactoryForTestsUtils() { }

    /**
     * @param points snake's body part's point
     * @param field where to put the snake
     * @return a snake having all collision properties set to false
     */
    public static Snake baseSnake(final List<Point> points, final Field field) {
        return new SnakeImpl(PlayerNumber.PLAYER1, "Player", Direction.RIGHT, 1000L, 1.0, field, points);
    }

    /**
     * @param points snake's body part's point
     * @param field where to put the snake
     * @return a snake having intangibility true
     */
    public static Snake ghostSnake(final List<Point> points, final Field field) {
        final Snake snake = baseSnake(points, field);
        snake.getProperties().getCollisionProperty().setIntangibility(true);
        return snake;
    }

    /**
     * @param points snake's body part's point
     * @param field where to put the snake
     * @return a snake having invincibility true
     */
    public static Snake godSnake(final List<Point> points, final Field field) {
        final Snake snake = baseSnake(points, field);
        snake.getProperties().getCollisionProperty().setInvincibility(true);
        return snake;
    }

    /**
     * @param points snake's body part's point
     * @param field where to put the snake
     * @return a snake having spring true
     */
    public static Snake springSnake(final List<Point> points, final Field field) {
        final Snake snake = baseSnake(points, field);
        snake.getProperties().getCollisionProperty().setSpring(true);
        return snake;
    }

}
