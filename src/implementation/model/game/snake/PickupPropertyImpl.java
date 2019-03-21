package implementation.model.game.snake;

import design.model.game.PickupProperty;

public class PickupPropertyImpl implements PickupProperty {
	
	private static final int RADIUS = 1;
	
	private int radius;
	
	public PickupPropertyImpl() {
		this.radius = RADIUS;
	}
	
	@Override
	public void setPickupRadius(int radius) {
		checkRadius(radius);	
		this.radius = radius;	
	}

	@Override
	public int getPickupRadius() {
		return this.radius;
	}
	
	private void checkRadius(int radius) {
		if(radius < 1) {
			throw new IllegalArgumentException();
		}	
	}
	
	public String toString() {
		return "Radius: " + this.radius + "\n";
	}

}
