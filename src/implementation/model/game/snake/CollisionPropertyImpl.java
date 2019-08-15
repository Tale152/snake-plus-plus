package implementation.model.game.snake;

import design.model.game.CollisionProperty;

/**
 * BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA
 * BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA
 * BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA
 * @see CollisionProperty
 * @author Elisa Tronetti
 */
public class CollisionPropertyImpl implements CollisionProperty{

    private boolean invincible;
    private boolean intangible;
    private boolean spring;

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

    public String toString() {
        return "Invincibility: " + this.invincible + "\n"
                + "Intangibility: " + this.intangible + "\n"
                + "Spring: " + this.spring + "\n";
    }

}
