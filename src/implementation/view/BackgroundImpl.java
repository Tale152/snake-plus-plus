package implementation.view;

import design.view.Background;
import javafx.scene.image.Image;

public class BackgroundImpl implements Background {

	private final Image bg;
	private double width;
	private double height;
	
	public BackgroundImpl(Image bg, double width, double height) {
		this.bg = bg;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public Image getBackground() {
		return bg;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public double getHeight() {
		return height;
	}

}
