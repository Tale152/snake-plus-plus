package design.controller.application;

import design.model.game.Direction;
import design.model.game.PlayerNumber;

public interface Action {
	
	public PlayerNumber getPlayerNumber();
	
	public Direction getDirection();
	
}
