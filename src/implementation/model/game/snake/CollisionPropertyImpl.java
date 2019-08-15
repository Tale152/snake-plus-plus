package implementation.model.game.snake;

import design.model.game.CollisionProperty;

/**
 * This class is part of the snake properties, here there are the method to use
 * to set all the collision properties, like intangibility and invincibility.
 * @author Elisa Tronetti
 */
public class CollisionPropertyImpl implements CollisionProperty {

    private boolean invincible;
    private boolean intangible;
    private boolean spring;

    /**
     * At first the properties invincible, intangible and spring are not active.
     */
    public CollisionPropertyImpl() {
        this.invincible = false;
        this.intangible = false;
        this.spring = false;
    }

    @Override
    public final void setInvincibility(final boolean inv) {
        this.invincible = inv;
    }

    @Override
    public final boolean getInvincibility() {
        return this.invincible;
    }

    @Override
    public final void setIntangibility(final boolean intangibility) {
        this.intangible = intangibility;
    }

    @Override
    public final boolean getIntangibility() {
        return this.intangible;
    }

    @Override
    public final void setSpring(final boolean spring) {
        this.spring = spring;
    }

    @Override
    public final boolean getSpring() {
        return this.spring;
    }

    /**
     * @return a string with all the collision properties and if they are active or not.
     */
    public String toString() {
        return "Invincibility: " + this.invincible + "\n"
                + "Intangibility: " + this.intangible + "\n"
                + "Spring: " + this.spring + "\n";
    }

}
