package design.model.game;

import java.util.List;

public interface GameRules {

	public WinConditions getWinConditions();
	
	public LossConditions getLossConditions();
	
	public List<ItemRules> getItemRules();
	
	public long getInitialSnakeDelta();
	
	public double getInitialSnakeMultiplier();
	
	public boolean isTimeGoingForward();
	
}