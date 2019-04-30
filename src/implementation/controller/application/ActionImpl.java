package implementation.controller.application;

import design.controller.application.Action;
import design.model.game.Direction;
import design.model.game.PlayerNumber;

public class ActionImpl implements Action {
	
	private final PlayerNumber player;
	private final Direction direction;

	@Override
	public PlayerNumber getPlayerNumber() {
		// TODO Auto-generated method stub
		return this.player;
	}

	@Override
	public Direction getDirection() {
		// TODO Auto-generated method stub
		return this.direction;
	}
	
	public ActionImpl(PlayerNumber n, Direction dir) {
		this.player = n;
		this.direction = dir;
	}
}
