package design.model.game;

import java.util.List;
import java.util.Optional;

import design.model.game.Item;

public interface GameRules {

	public interface ItemRule {
		
		public Class<? extends Item> getItemClass();
		
		public long getSpawnDelta();
		
		public double getSpawnChance();
		
		public int getMax();
		
		public Optional<Long> getItemDuration();
		
		public Optional<Long> getEffectDuration();
		
	}
	
	public List<ItemRule> getItemRules();
	
	public long getInitialSnakeDelta();
	
	public double getInitialSnakeMultiplier();
	
}