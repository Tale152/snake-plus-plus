package design.controller.game;

import java.util.NoSuchElementException;
import design.model.game.Effect;

public interface ItemCounter {
	
	public boolean increase(Class<? extends Effect> effect) throws NoSuchElementException;
	
	public boolean decrease(Class<? extends Effect> effect) throws NoSuchElementException;
	
	public int getCurrent(Class<? extends Effect> effect) throws NoSuchElementException;
	
	public int getMax(Class<? extends Effect> effect) throws NoSuchElementException;
	
	public boolean isMax(Class<? extends Effect> effect) throws NoSuchElementException;

	public long getLastSpawnAttempt(Class<? extends Effect> effect);
	
	public void setLastSpawnAttempt(Class<? extends Effect> effect, long time);
	
}
