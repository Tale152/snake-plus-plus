package design.model.game;

import java.util.List;

public interface GameRules {

	public WinConditions getWinConditions();
	
	public LossConditions getLossConditions();
	
	public List<ItemRule> getItemRules();
	
	public long getInitialSnakeDelta();
	
	public double getInitialSnakeMultiplier();
	
	public long getInitialTime();
	
	public boolean isTimeGoingForward();
	
}