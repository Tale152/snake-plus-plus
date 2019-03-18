package implementation.model.game.snake;

import design.model.game.PickupProperty;

public class PickupPropertyImpl implements PickupProperty {

	private int radius;
	
	public PickupPropertyImpl(int radius) {
		checkRadius(radius);
		this.radius = radius;
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
