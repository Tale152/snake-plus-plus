package design.model.game;

/**
 * Every snake has some properties, and this is where all of them are contained.
 * When you need to get a snake property, you take it from here.
 */
public interface Properties {

/**
 * @return snake's length property
 */
    LengthProperty getLengthProperty();

/**
 * @return snake's directions property
 */
    DirectionProperty getDirectionProperty();

/**
 * @return snake's pick up property
 */
    PickupProperty getPickupProperty();

/**
 * @return snake's collision property
 */
    CollisionProperty getCollisionProperty();

/**
 * @return snake's speed property
 */
    SpeedProperty getSpeedProperty();

}
