package design.model.game;

public interface DirectionProperty {

    Direction getDirection();

    boolean setDirection(Direction direction);

    void setReverseDirection(boolean reverse);

    boolean getReverseDirection();

    boolean forceDirection(Direction direction);

    void allowChangeDirection();

    boolean hasNextValidDirection();

    public Direction getNextValidDirection();

}
