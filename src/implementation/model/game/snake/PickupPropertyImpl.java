package implementation.model.game.snake;

import design.model.game.PickupProperty;
/**
 * This class is part of the snake properties, here there are the method to use
 * to set snake's pick up radius, which is how many cell he collide with in front 
 * of him.
 */
public class PickupPropertyImpl implements PickupProperty {

    private static final int RADIUS = 1;

    private int radius;

    /**
     * Set the basic radius, which is 1.
     */
    public PickupPropertyImpl() {
        this.radius = RADIUS;
    }

    @Override
    public final void setPickupRadius(final int radius) {
        checkRadius(radius);
        this.radius = radius;
    }

    @Override
    public final int getPickupRadius() {
        return this.radius;
    }

    private void checkRadius(final int radius) {
        if (radius < 1) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return a string that contains the current pick up radius.
     */
    public String toString() {
        return "Radius: " + this.radius + "\n";
    }

}
