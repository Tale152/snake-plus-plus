package design.model.game;

public interface DirectionProperty {
	
	public Direction getDirection();
	
	public boolean setDirection(Direction direction);
	
	public void setReverseDirection(boolean reverse);
	
	public boolean getReverseDirection();
	
	public boolean forceDirection(Direction direction);
}
