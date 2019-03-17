package design.model.game;

import java.awt.Point;
import java.util.List;

public interface Snake {

	public Player getPlayer();
	
	public Properties getProperties();
	
	public List<Item> move(Point point);
	
	public void addEffect(Effect effect);
	
	public boolean removeEffect(Effect effect);
	
	public List<Effect> getEffects();
	
	public boolean isAlive();
	
	public boolean kill();
	
	public void reverse();
	
}
