package implementation.view.game;

import java.util.ArrayList;
import java.util.List;

import design.view.game.PlayerHud;
import design.view.game.Sprite;

public class PlayerHudImpl implements PlayerHud {

	private String name;
	private String score;
	private boolean alive;
	private final List<Sprite> actualSprites;
	private final List<Sprite> currentSprites;
	private Sprite playerSprite;
	
	public PlayerHudImpl() {
		name = "";
		score = "";
		alive = true;
		actualSprites = new ArrayList<>();
		currentSprites = new ArrayList<>();
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
		currentSprites.add(sprite);
	}

	@Override
	public List<Sprite> getSpriteList() {
		List<Sprite> res = new ArrayList<Sprite>();
		if (playerSprite != null) {
			res.add(playerSprite);
		}
		res.addAll(actualSprites);
		return res;
	}

	@Override
	public void newEffectSpriteList() {
		currentSprites.clear();
	}

	@Override
	public void endEffectSpriteList() {
		actualSprites.clear();
		actualSprites.addAll(currentSprites);
	}

}
