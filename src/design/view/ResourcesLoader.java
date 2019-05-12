package design.view;

public interface ResourcesLoader {

	public Sprite getItem(String name);
	
	public Sprite getWall(String name);
	
	public Sprite getBodyPart(String name); 
	
	public Background getFieldBg();
	
	public Background getTopHudBg();
	
	public Background getRightHudBg();
	
	public Background getBottomHudBg();
	
	public Background getLeftHudBg();
	
}

