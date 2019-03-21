package implementation.model.game.snake;

import design.model.game.Player;
import design.model.game.PlayerNumber;

public class PlayerImpl implements Player{

	private static final int MULTIPLIER = 1;
	
	private PlayerNumber playerNumber;
	private String name;
	private int score;
	private double multiplier;
	
	public PlayerImpl(PlayerNumber playerNumber, String name, int score) {
		checkPlayerNumber(playerNumber);
		this.playerNumber = playerNumber;
		checkName(name);
		this.name = name;
		checkScore(score);
		this.score = score;
		this.multiplier = MULTIPLIER;
	}
	
	
	@Override
	public PlayerNumber getPlayerNumber() {
		return this.playerNumber;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void addScore(int score) {
		this.score += (score * this.multiplier);
	}

	@Override
	public void reduceScore(int score) {
		if(this.score-(score * this.multiplier) > 0) {
			this.score -= (score * this.multiplier);
		} else {
			this.score = 0;
		}
	}

	@Override
	public void applyScoreMultiplier(double mult) {
		this.multiplier = mult;	
	}

	@Override
	public double getScoreMultiplier() {
		return this.multiplier;
	}

	@Override
	public int getScore() {
		return this.score;
	}

	private void checkScore(int score) {
		if(score < 0) {
			throw new IllegalArgumentException();
		}
	}
	
	private void checkPlayerNumber(PlayerNumber p) {
		if(p.equals(null)) {
			throw new IllegalArgumentException();
		}
	}
	
	private void checkName(String n) {
		if(n.equals(null)) {
			throw new IllegalArgumentException();
		}
	}
	
	public String toString() {
		return "Player number: " + this.playerNumber + "\n" 
				+ "Player name: " + this.name + "\n"
				+ "Player score: " + this.score + "\n"
				+ "Player multiplier: " + this.multiplier + "\n";
	}

}
