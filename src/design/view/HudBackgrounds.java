package design.view;

import java.util.Optional;

public interface HudBackgrounds {

	public void setTop(Background bg);
	
	public Optional<Background> getTop();
	
	public void setBottom(Background bg);
	
	public Optional<Background> getBottom();
	
	public void setLeft(Background bg);
	
	public Optional<Background> getLeft();
	
	public void setRight(Background bg);
	
	public Optional<Background> getRight();
	
}
