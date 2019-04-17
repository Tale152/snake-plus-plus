package implementation.controller.game;

import java.io.Serializable;

import design.model.game.*;

public class Level implements Serializable {
	
	private static final long serialVersionUID = 7920726586652392385L;
	private final InitialGameState igs;
	private final GameRules gr;
	private final WinConditions win;
	private final LossConditions loss;
	
	public Level(InitialGameState igs, GameRules gr, WinConditions win, LossConditions loss) {
		this.igs = igs;
		this.gr = gr;
		this.win = win;
		this.loss = loss;
	}

	public InitialGameState getInitialGameState() {
		return igs;
	}

	public GameRules getGameRules() {
		return gr;
	}

	public WinConditions getWinConditions() {
		return win;
	}

	public LossConditions getLossConditions() {
		return loss;
	}
	
	
}
