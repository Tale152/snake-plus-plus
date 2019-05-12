package implementation.view;

import design.view.Background;
import design.view.HudBackgrounds;
import design.view.ResourcesLoader;

public class HudBackgroundsImpl implements HudBackgrounds {

	private final Background top;
	private final Background bottom;
	private final Background left;
	private final Background right;
	
	public HudBackgroundsImpl(ResourcesLoader loader) {
		this.top = loader.getTopHudBg();
		this.right = loader.getRightHudBg();
		this.bottom = loader.getBottomHudBg();
		this.left = loader.getLeftHudBg();
	}

	@Override
	public Background getTop() {
		return top;
	}

	@Override
	public Background getBottom() {
		return bottom;
	}

	@Override
	public Background getLeft() {
		return left;
	}

	@Override
	public Background getRight() {
		return right;
	}
	
}
