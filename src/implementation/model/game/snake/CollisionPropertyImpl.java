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
    public void setInvincibility(final boolean inv) {
        this.invincible = inv;
    }

	@Override
	public boolean getInvincibility() {
		return this.invincible;
	}

	@Override
	public void setIntangibility(boolean intangibility) {
		this.intangible = intangibility;
	}

	@Override
	public boolean getIntangibility() {
		return this.intangible;
	}

	@Override
	public void setSpring(boolean spring) {
		this.spring = spring;
	}

	@Override
	public boolean getSpring() {
		return this.spring;
	}
	
	public String toString() {
		return "Invincibility: " + this.invincible + "\n"
				+ "Intangibility: " + this.intangible + "\n"
				+ "Spring: " + this.spring + "\n";
	}

}
