package design.model.game;

/**
 * An Item represents something in the field that you can collide with and will have some sort of effect
 * but it's not a Wall or a snake's BodyPart.
 */
public interface Item extends Collidable, Runnable {

    /**
     * Returns the class effect that defines the behavior of this item.
     * @return class effect associated with this item
     */
    Class<? extends Effect> getEffectClass();

}
