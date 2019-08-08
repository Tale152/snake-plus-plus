package implementation.model.game.snake;

import design.model.game.SpeedProperty;

public class SpeedPropertyImpl implements SpeedProperty {
	
	private static final double DEFAULTLENGHTMULTIPLIER = 0;
	
	private long deltaT;
	private double multiplier;
	private double lenghtValue;

	
	public SpeedPropertyImpl(long deltaT, double speedMultiplier) {
		checkDelta(deltaT);
		this.deltaT = deltaT;
		checkMultiplier(speedMultiplier);
		this.multiplier = speedMultiplier;
		this.lenghtValue = DEFAULTLENGHTMULTIPLIER;
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
		if (this.multiplier + mul < 0 ) {
			throw new IllegalStateException();
		};
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
	
	private void checkMultiplier(double multiplier) {
		if(multiplier < 0) {
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public void applyLenghtSpeedValue(double val) {
		this.lenghtValue = val;
	}

	@Override
	public double getLenghtSpeedValue() {
		return this.lenghtValue;
	}
	
	public String toString() {
		return "Multiplier: " + this.multiplier + "\n"
				+ "Delta: " + this.deltaT + "\n";
	}
}
