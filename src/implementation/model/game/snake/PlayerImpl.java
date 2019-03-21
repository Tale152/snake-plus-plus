package implementation.model.game.snake;

import design.model.game.Player;
import design.model.game.PlayerNumber;

public class PlayerImpl implements Player{

	private static final int MULTIPLIER = 1;
	private static final int SCORE = 0;
	
	private PlayerNumber playerNumber;
	private String playerName;
	private int score;
	private double scoreMultiplier;
	
	public PlayerImpl(PlayerNumber playerNumber, String playerName) {
		checkPlayerNumber(playerNumber);
		this.playerNumber = playerNumber;
		checkName(playerName);
		this.playerName = playerName;
		this.score = SCORE;
		this.scoreMultiplier = MULTIPLIER;
	}
	
	
	@Override
	public PlayerNumber getPlayerNumber() {
		return this.playerNumber;
	}

	@Override
	public String getName() {
		return this.playerName;
	}

	@Override
	public void addScore(int score) {
		checkScore(score);
		this.score += (score * this.scoreMultiplier);
	}

	@Override
	public void reduceScore(int score) {
		checkScore(score);
		if(this.score-(score * this.scoreMultiplier) > 0) {
			this.score -= (score * this.scoreMultiplier);
		} else {
			this.score = 0;
		}
	}

	@Override
	public void applyScoreMultiplier(double mult) {
		this.scoreMultiplier = mult;	
	}

	@Override
	public double getScoreMultiplier() {
		return this.scoreMultiplier;
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
	
	private void checkName(String name) {
		if(name.equals(null)) {
			throw new IllegalArgumentException();
		}
	}
	
	public String toString() {
		return "Player number: " + this.playerNumber + "\n" 
				+ "Player name: " + this.playerName + "\n"
				+ "Player score: " + this.score + "\n"
				+ "Player multiplier: " + this.scoreMultiplier + "\n";
	}

}
