package design.controller.game;

import design.model.game.GameRules;
import design.model.game.InitialGameState;

public interface GameLoader {
	
	public InitialGameState getInitialGameState();
	
	public GameRules getGameRules();
	
}
