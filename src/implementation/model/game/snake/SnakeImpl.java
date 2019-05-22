package implementation.model.game.snake;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import design.model.game.BodyPart;
import design.model.game.Direction;
import design.model.game.Effect;
import design.model.game.Field;
import design.model.game.Player;
import design.model.game.PlayerNumber;
import design.model.game.Properties;
import design.model.game.Snake;
import implementation.model.game.items.BodyPartImpl;

public class SnakeImpl implements Snake{

	private final Player player;
	private final Properties properties;
	private final Field field;
	private List<Effect> effects;
	private List<BodyPart> bodyPart;
	private boolean isAlive;
	
	public SnakeImpl(PlayerNumber playerNumber, String playerName, Direction direction, 
			long deltaT, double speedMultiplier, Field field, List<Point> point) {
		this.player = new PlayerImpl(playerNumber, playerName);
		this.properties = new PropertiesImpl(direction, deltaT, speedMultiplier);
		this.effects = new ArrayList<>();
		this.field = field;
		this.effects = new ArrayList<>();
		this.bodyPart = new ArrayList<>();
		this.isAlive = true;

		for(int i = point.size() - 1; i >= 0; i--) {
			insertNewHead(point.get(i));
		}
		
		properties.getLengthProperty().lengthen(point.size() - 1); 
	}
	
	private void stampamiTutto() {
		for(BodyPart b : this.bodyPart) {
			System.out.println( "Punto: " + b.getPoint() + "\n" 
					+ "Is Head: " + b.isHead() +  "\n"
					+ "Is Tail: " + b.isTail() + "\n"
					+ "Is Body: " + b.isBody() + "\n"
					+ "Is connected on top: " + b.isCombinedOnTop() + "\n"
					+ "Is connected on right: " + b.isCombinedOnRight() + "\n"
					+ "Is connected on bottom: " + b.isCombinedOnBottom() + "\n"
					+ "Is connected on left: " + b.isCombinedOnLeft() + "\n\n");
		}
	}
	@Override
	public void run() {
		while(isAlive) {
			stampamiTutto();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Point next = obtainNextPoint();
			move(next);
			stampamiTutto();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	

	}

	@Override
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public Properties getProperties() {
		return this.properties;
	}

	@Override
	public void addEffect(Effect effect) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean removeEffect(Effect effect) {
		return this.effects.remove(effect);
	}

	@Override
	public List<Effect> getEffects() {
		return new ArrayList<>(this.effects);
	}

	@Override
	public boolean isAlive() {
		return this.isAlive;
	}

	@Override
	public void kill() {
		this.isAlive = false;
		
	}

	@Override
	public void reverse() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BodyPart> getBodyParts() {
		return new ArrayList<>(this.bodyPart);
	}

	private Direction determinateOppositeDirection(Direction d) {
		Direction updatedDirection;
		
		switch(d) {
		case UP: updatedDirection = Direction.DOWN; break;
		case DOWN: updatedDirection = Direction.UP; break;
		case RIGHT: updatedDirection = Direction.LEFT; break;
		case LEFT: updatedDirection = Direction.RIGHT; break;
		default: throw new IllegalStateException();	
		}
		
		return updatedDirection;
	}
	
	
	private Direction determinateDirection(Point p1, Point p2) {
		//if x are the same Snake is moving up or down
		if(p1.x == p2.x) {
			if(p1.y > p2.y) {
				if(p1.y - p2.y == 1) {
					return Direction.DOWN;
				}else {
					return Direction.UP;
				}
			} else {
				if(p1.y - p2.y == -1) {
					return Direction.UP;
				}else {
					return Direction.DOWN;
				}		
			}
		}
		
		//in this case Snake is moving right or left
		if(p1.y == p2.y) {
			
			if(p1.x > p2.x) {
				if(p1.x - p2.x == 1) {
					return Direction.RIGHT;
				}else {
					return Direction.LEFT;
				}
			} else {
				if(p1.x - p2.x == -1) {
					return Direction.LEFT;
				}else {
					return Direction.RIGHT;
				}		
			}
		}
		throw new IllegalStateException();
	}

	private void insertNewHead(Point point) {
		int size = this.bodyPart.size();
		BodyPart p = new BodyPartImpl(point);
		if(size == 0) {
			p.setHead(true);
			p.setTail(true);
		} else if (size == 1) {
			p.setHead(true);
			this.bodyPart.get(0).setHead(false);;
			bodyPartCombination(p);
		} else {
			p.setHead(true);
			bodyPartCombination(p);
			this.bodyPart.get(0).setHead(false);
			this.bodyPart.get(0).setBody(true);
			
		}
		this.bodyPart.add(0, p);
	}
	
	private void removeTail() {
		if(this.bodyPart.size() > 1) {
			int last = this.bodyPart.size() - 1;
			BodyPart oldTail = this.bodyPart.remove(last);
			last = last - 1;
			this.bodyPart.get(last).setTail(true);
			this.bodyPart.get(last).setBody(false);
			switch(determinateDirection(oldTail.getPoint(), this.bodyPart.get(last).getPoint())) {
				case UP : 
					this.bodyPart.get(last).setCombinedOnTop(false); 
					break;
				case DOWN : 
					this.bodyPart.get(last).setCombinedOnBottom(false); 
					break;
				case LEFT :
					this.bodyPart.get(last).setCombinedOnLeft(false);
					break;
				case RIGHT : 
					this.bodyPart.get(last).setCombinedOnRight(false); 
					break;
			}
		}
	}
	
	private void bodyPartCombination(BodyPart part) {
		switch(determinateDirection(this.bodyPart.get(0).getPoint(), part.getPoint())) {
		case UP : 
			this.bodyPart.get(0).setCombinedOnBottom(true);
			part.setCombinedOnTop(true);
			break;
		case DOWN : 
			this.bodyPart.get(0).setCombinedOnTop(true);
			part.setCombinedOnBottom(true);
			break;
		case LEFT :
			this.bodyPart.get(0).setCombinedOnRight(true);
			part.setCombinedOnLeft(true);
			break;
		case RIGHT :
			this.bodyPart.get(0).setCombinedOnLeft(true);
			part.setCombinedOnRight(true);
			break;
		}
	}

	private Point obtainNextPoint() {
		Point next = null;
		Point head = bodyPart.get(0).getPoint();
		switch(properties.getDirectionProperty().getDirection()) {
			case UP: next = new Point(head.x, head.y - 1); break;
			case DOWN: next = new Point(head.x, head.y + 1); break;
			case LEFT: next = new Point(head.x - 1, head.y); break;
			case RIGHT: next = new Point(head.x + 1, head.y); break;
		}
		if (next.x < 0) {
			next = new Point(field.getWidth() - 1, next.y);
		} 
		else if (next.x >= field.getWidth()) {
			next = new Point(0, next.y);
		}
		else if (next.y < 0) {
			next = new Point(next.x, field.getHeight() -1);
		}
		else if (next.y >= field.getHeight()) {
			next = new Point (next.x, 0);
		}
		return next;
	}
	
	private void move(Point next) {
		insertNewHead(next);
		while(this.bodyPart.size() > this.properties.getLengthProperty().getLength()){
			removeTail();
		}
	}
}
