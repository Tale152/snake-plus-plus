package design.controller.game;

import java.util.NoSuchElementException;
import design.model.game.Effect;

public interface ItemCounter {
	
	boolean increase(Class<? extends Effect> effect) throws NoSuchElementException;
	
	boolean decrease(Class<? extends Effect> effect) throws NoSuchElementException;
	
	int getCurrent(Class<? extends Effect> effect) throws NoSuchElementException;
	
	int getMax(Class<? extends Effect> effect) throws NoSuchElementException;
	
	boolean isMax(Class<? extends Effect> effect) throws NoSuchElementException;

}