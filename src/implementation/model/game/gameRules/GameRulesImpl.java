package implementation.model.game.gameRules;

import java.util.ArrayList;
import java.util.List;

import design.model.game.GameRules;
import design.model.game.ItemRule;
import design.model.game.LossConditions;
import design.model.game.WinConditions;

public class GameRulesImpl implements GameRules {

	private final WinConditions win;
	private final LossConditions loss;
	private final List<ItemRule> itemRules;
	private final long initialSnakeDelta;
	private final double initialSnakeMultiplier;
	private final long initialTime;
	private final boolean timeGoingForward;
	
	public GameRulesImpl(WinConditions win, LossConditions loss, List<ItemRule> itemRules, 
			long initialSnakeDelta, double initialSnakeMultiplier, long initialTime, boolean timeGoingForward) {
		this.win = win;
		this.loss = loss;
		this.itemRules = itemRules;
		this.initialSnakeDelta = initialSnakeDelta;
		this.initialSnakeMultiplier = initialSnakeMultiplier;
		this.initialTime = initialTime;
		this.timeGoingForward = timeGoingForward;
	}
	
	@Override
	public WinConditions getWinConditions() {
		return win;
	}

	@Override
	public LossConditions getLossConditions() {
		return loss;
	}

	@Override
	public List<ItemRule> getItemRules() {
		return new ArrayList<>(itemRules);
	}

	@Override
	public long getInitialSnakeDelta() {
		return initialSnakeDelta;
	}

	@Override
	public double getInitialSnakeMultiplier() {
		return initialSnakeMultiplier;
	}

	@Override
	public long getInitialTime() {
		return initialTime;
	}
	
	@Override
	public boolean isTimeGoingForward() {
		return timeGoingForward;
	}

}
