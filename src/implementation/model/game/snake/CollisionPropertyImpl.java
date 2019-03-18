package implementation.model.game.snake;

import design.model.game.CollisionProperty;

public class CollisionPropertyImpl implements CollisionProperty{

	private boolean invincible;
	private boolean intangible;
	private boolean spring;
	
	public CollisionPropertyImpl(boolean invincible, boolean intangible, boolean spring) {
		this.invincible = invincible;
		this.intangible = intangible;
		this.spring = spring;
	}
	
	@Override
	public void setInvincibility(boolean inv) {
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
		return "Stato invincibility: " + this.invincible + "\n"
				+ "Stato intangibility: " + this.intangible + "\n"
				+ "Stato spring: " + this.spring + "\n";
	}

}
