package implementation.model.game.snake;

import design.model.game.CollisionProperty;

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
		return "Invincibility: " + this.invincible + "\n"
				+ "Intangibility: " + this.intangible + "\n"
				+ "Spring: " + this.spring + "\n";
	}

}
