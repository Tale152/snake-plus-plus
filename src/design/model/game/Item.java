package design.model.game;

public interface Item extends Collidable, Runnable{

	public Class<? extends Effect> getEffectClass();
	
}
