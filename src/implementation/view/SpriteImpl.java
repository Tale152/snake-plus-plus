package implementation.view;

import design.view.Sprite;
import javafx.scene.image.Image;

public class SpriteImpl implements Sprite {

	private final String name;
	private final Image sprite;
	
	public SpriteImpl(String name, Image sprite) {
		this.name = name;
		this.sprite = sprite;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Image getSprite() {
		return sprite;
	}
}
