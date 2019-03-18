package design.model.game;

public interface SpeedProperty {

	public long getDeltaT();
	
	public void setDeltaT(long deltaT);
	
	public void applySpeedMultiplier(double mul);
	
	public double getSpeedMultiplier();
	
	public long getLastUpdate();
	
	public void setLastUpdate(long updateTime);
	
	public long getNextUpdate();
	
}
