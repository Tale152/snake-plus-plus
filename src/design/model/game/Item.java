package design.model.game;

public interface Item extends Collidable, Runnable{

	public void setEaten();
	
	public Class<? extends Effect> getEffectClass();
	
}
