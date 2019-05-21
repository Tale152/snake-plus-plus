package design.model.game;

import java.util.List;

public interface Snake extends Runnable{

	public Player getPlayer();
	
	public Properties getProperties();
	
	public void addEffect(Effect effect);
	
	public boolean removeEffect(Effect effect);
	
	public List<Effect> getEffects();
	
	public boolean isAlive();
	
	public void kill();
	
	public void reverse();
	
	public List<BodyPart> getBodyParts();
	
}
