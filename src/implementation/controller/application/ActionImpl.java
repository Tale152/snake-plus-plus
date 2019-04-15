package implementation.controller.application;

import design.controller.application.Action;
import design.model.game.Direction;
import design.model.game.PlayerNumber;

public class ActionImpl implements Action {

	@Override
	public PlayerNumber getPlayerNumber() {
		// TODO Auto-generated method stub
		return PlayerNumber.PLAYER1;
	}

	@Override
	public Direction getDirection() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ActionImpl(PlayerNumber n, Direction dir) {
		
	}
}
