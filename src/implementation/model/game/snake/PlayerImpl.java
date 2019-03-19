package implementation.model.game.snake;

import design.model.game.Player;
import design.model.game.PlayerNumber;

public class PlayerImpl implements Player{

	private PlayerNumber player;
	private String name;
	private int score;
	private double multiplier;
	
	public PlayerImpl(PlayerNumber player, String name, int score, double multiplier) {
		this.player = player;
		this.name = name;
		checkScore(score);
		this.score = score;
		this.multiplier = multiplier;
	}
	
	
	@Override
	public PlayerNumber getPlayerNumber() {
		return this.player;
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
		checkScore(this.score-score);
		this.score -= (score * this.multiplier);
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
			throw new IllegalStateException();
		}
	}
	
	public String toString() {
		return "Player number: " + this.player + "\n" 
				+ "Player name: " + this.name + "\n"
				+ "Player score: " + this.score + "\n"
				+ "Player multiplier: " + this.multiplier + "\n";
	}

}
