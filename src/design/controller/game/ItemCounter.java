package design.controller.game;

import design.model.game.Effect;

public interface ItemCounter {
	
	void increase(Class<? extends Effect> effect);
	
	void decrease(Class<? extends Effect> effect);
	
	int getCurrent(Class<? extends Effect> effect);
	
	int getMax(Class<? extends Effect> effect);
	
	boolean isMax(Class<? extends Effect> effect);

}
