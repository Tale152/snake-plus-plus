package implementation.model.game.snake;

import design.model.game.SpeedProperty;

public class SpeedPropertyImpl implements SpeedProperty {
	
	private long deltaT;
	private double multiplier;
	private long lastUpdate;
	
	public SpeedPropertyImpl(long deltaT, double speedMultiplier, long lastUpdate) {
		checkDelta(deltaT);
		this.deltaT = deltaT;
		checkInitMultiplier(speedMultiplier);
		this.multiplier = speedMultiplier;
		checkLastUpdate(lastUpdate);
		this.lastUpdate = lastUpdate;
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

	@Override
	public long getLastUpdate() {
		return this.lastUpdate;
	}

	@Override
	public void setLastUpdate(long updateTime) {
		this.lastUpdate = updateTime;
	}

	@Override
	public long getNextUpdate() {
		return this.lastUpdate + (long)(this.deltaT*this.multiplier);
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
	
	private void checkLastUpdate(long lastUpdate) {
		if(lastUpdate < 0) {
			throw new IllegalArgumentException();
		}
	}
	
	public String toString() {
		return "Last update: " + this.lastUpdate + "\n"
				+ "Multiplier: " + this.lastUpdate + "\n"
				+ "Delta: " + this.deltaT + "\n"
				+ "Next update: " + getNextUpdate() + "\n";
	}

}
