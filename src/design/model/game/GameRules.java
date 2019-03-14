package design.model.game;

import java.util.List;
import design.model.game.Item;

public interface GameRules {

	public interface ItemRule {
		
		public Class<? extends Item> getItemClass();
		
		public long getSpawnDelta();
		
		public double getSpawnChance();
		
		public int getMax();
		
		public long getItemDuration();
		
		public long getEffectDuration();
		
		public int getScore();
		
	}
	
	public List<ItemRule> getItemRules();
	
	public long getInitialSnakeDelta();
	
	public long getIncrementalSnakeDelta();
	
}