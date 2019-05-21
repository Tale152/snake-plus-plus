package design.model.game;

public interface BodyPart extends Collidable {

	public Snake getOwner();
	
	public boolean isHead();
	
	public void setHead(boolean head);
	
	public boolean isBody();
	
	public void setBody(boolean body);
	
	public boolean isTail();
	
	public void setTail(boolean tail);
	
	public boolean isCombinedOnTop();
	
	public void setCombinedOnTop(boolean combined);
	
	public boolean isCombinedOnBottom();
	
	public void setCombinedOnBottom(boolean combined);
	
	public boolean isCombinedOnLeft();
	
	public void setCombinedOnLeft(boolean combined);
	
	public boolean isCombinedOnRight();
	
	public void setCombinedOnRight(boolean combined);
	
}
