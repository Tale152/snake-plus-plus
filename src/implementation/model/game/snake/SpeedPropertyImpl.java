package implementation.model.game.snake;

import design.model.game.SpeedProperty;

public class SpeedPropertyImpl implements SpeedProperty {
	
	private long deltaT;
	private double multiplier;
	
	public SpeedPropertyImpl(long deltaT, double speedMultiplier) {
		checkDelta(deltaT);
		this.deltaT = deltaT;
		checkInitMultiplier(speedMultiplier);
		this.multiplier = speedMultiplier;
	}
	
	@Override
	public long getDeltaT() {
		return this.deltaT;
	}

	@Override
	public void setDeltaT(long deltaT) {
		checkDelta(deltaT);
		this.deltaT = deltaT;
	}

	@Override
	public void applySpeedMultiplier(double mul) {
		checkMultiplier(this.multiplier + mul);
		this.multiplier += mul;	
	}

	@Override
	public double getSpeedMultiplier() {
		return this.multiplier;
	}
	
	private void checkDelta(long deltaT) {
		if(deltaT <= 0) {
			throw new IllegalArgumentException();
		}
	}
	
	private void checkInitMultiplier(double multiplier) {
		if(multiplier < 0) {
			throw new IllegalArgumentException();
		}
	}
	
	private void checkMultiplier(double multiplier) {
		if(multiplier < 0) {
			throw new IllegalStateException();
		}
	}
	
	public String toString() {
		return "Multiplier: " + this.multiplier + "\n"
				+ "Delta: " + this.deltaT + "\n";
	}

}
