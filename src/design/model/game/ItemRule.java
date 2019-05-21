package design.model.game;

import java.util.Optional;

public interface ItemRule {
		
	public Class<? extends Effect> getEffectClass();
		
	public long getSpawnDelta();
		
	public double getSpawnChance();
		
	public int getMax();
		
	public Optional<Long> getItemDuration();
		
	public Optional<Long> getEffectDuration();
		
}
