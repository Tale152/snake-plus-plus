package implementation.model.game.snake;

import design.model.game.SpeedProperty;

public class SpeedPropertyImpl implements SpeedProperty {

	private long deltaT;
	private double multiplier;
	private long lastUpdate;
	
	public SpeedPropertyImpl(long deltaT, double multiplier, long lastUpdate) {
		this.deltaT = deltaT;
		this.multiplier = multiplier;
		this.lastUpdate = lastUpdate;
	}
	
	@Override
	public long getDeltaT() {
		return this.deltaT;
	}

	@Override
	public void setDeltaT(long deltaT) {
		this.deltaT = deltaT;
	}

	@Override
	public void applySpeedMultiplier(double mul) {
		this.multiplier = mul;	
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
	
	public String toString() {
		return "Last update: " + this.lastUpdate + "\n"
				+ "Multiplier: " + this.lastUpdate + "\n"
				+ "Delta: " + this.deltaT + "\n"
				+ "Next update: " + getNextUpdate() + "\n";
	}

}
