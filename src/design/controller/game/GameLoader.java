package design.controller.game;

import design.model.game.*;

public interface GameLoader {
	
	public InitialGameState getInitialGameState();
	
	public GameRules getGameRules();
	
	public WinConditions getWinConditions();
	
	public LossConditions getLossConditions();
	
}
