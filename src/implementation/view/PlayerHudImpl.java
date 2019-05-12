package implementation.view;

import design.view.PlayerHud;

public class PlayerHudImpl implements PlayerHud {

	private String name;
	private int score;
	private boolean alive;
	
	public PlayerHudImpl() {
		name = "";
		score = 0;
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
	public int getScore() {
		return score;
	}

	@Override
	public void setScore(int score) {
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
