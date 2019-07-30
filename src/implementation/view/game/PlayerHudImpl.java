package implementation.view.game;

import java.util.ArrayList;
import java.util.List;

import design.view.game.PlayerHud;
import design.view.game.Sprite;

public class PlayerHudImpl implements PlayerHud {

	private String name;
	private String score;
	private boolean alive;
	private final List<Sprite> effectSprites;
	private Sprite playerSprite;
	
	public PlayerHudImpl() {
		name = "";
		score = "";
		alive = true;
		effectSprites = new ArrayList<>();
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

	@Override
	public void setSnakeSprite(Sprite sprite) {
		playerSprite = sprite;
	}

	@Override
	public void addEffectSprite(Sprite sprite) {
		effectSprites.add(sprite);
	}

	@Override
	public void resetEffectSpriteList() {
		effectSprites.clear();
	}

	@Override
	public List<Sprite> getSpriteList() {
		List<Sprite> res = new ArrayList<Sprite>();
		if (playerSprite != null) {
			res.add(playerSprite);
		}
		res.addAll(effectSprites);
		return res;
	}

}
