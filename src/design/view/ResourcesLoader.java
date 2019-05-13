package design.view;

public interface ResourcesLoader {

	public Sprite getItem(String name);
	
	public Sprite getWall(String name);
	
	public Sprite getBodyPart(String name); 
	
	public Background getFieldBackground();
	
	public Background getHudBackground();
	
}

