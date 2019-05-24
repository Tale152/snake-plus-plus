package implementation.view.game;

import design.view.game.PlayerHud;

public class PlayerHudImpl implements PlayerHud {

	private String name;
	private String score;
	private boolean alive;
	
	public PlayerHudImpl() {
		name = "";
		score = "";
		alive = true;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getScore() {
		return score;
	}

	@Override
	public void setScore(String score) {
		this.score = score;
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

	@Override
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

}
