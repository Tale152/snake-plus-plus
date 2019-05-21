package implementation.model.game.snake;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
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

		for(int i = 0; i < point.size(); i++) {
			insertNewPiece(point.get(i));
		}
		
		properties.getLengthProperty().lengthen(point.size() - 1); 
	}
	
	private void insertNewPiece(Point point) {
		int size = this.bodyPart.size();
		BodyPart p = new BodyPartImpl(point);
		if(size == 0) {
			p.setHead(true);
			p.setTail(true);
		} else if (size == 1) {
			p.setTail(true);
			this.bodyPart.get(size - 1).setTail(false);
			bodyPartCombination(p);
		} else {
				p.setTail(true);
				bodyPartCombination(p);
				this.bodyPart.get(size - 1).setTail(false);
				this.bodyPart.get(size - 1).setBody(true);
			
		}
		this.bodyPart.add(p);
	}
	
	//una volta controllato l'algoritmo sotto risistema i collegamente di bodypart
	private void bodyPartCombination(BodyPart part) {
		int size = this.bodyPart.size();
		switch(determinateDirection(this.bodyPart.get(size - 1).getPoint(), part.getPoint())) {
		case UP : 
			this.bodyPart.get(size - 1).setCombinedOnTop(true); 
			part.setCombinedOnBottom(true); 
			break;
		case DOWN : 
			this.bodyPart.get(size - 1).setCombinedOnBottom(true); 
			part.setCombinedOnTop(true); 
			break;
		case LEFT :
			this.bodyPart.get(size - 1).setCombinedOnRight(true); 
			part.setCombinedOnLeft(true); 
			break;
		case RIGHT : 
			this.bodyPart.get(size - 1).setCombinedOnLeft(true);
			part.setCombinedOnRight(true);
			break;
		}
	}
	
	
	@Override
	public void run() {
		while(isAlive) {

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
	}

	@Override
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addEffect(Effect effect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeEffect(Effect effect) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Effect> getEffects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reverse() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BodyPart> getBodyParts() {
		// TODO Auto-generated method stub
		return null;
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
	
	
	//TODO controlla l'algoritmo, penso proprio sia sbagliato di base
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
				if(p1.y - p2.y == 1) {
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
					return Direction.LEFT;
				}else {
					return Direction.RIGHT;
				}
			} else {
				if(p1.x - p2.x == 1) {
					return Direction.RIGHT;
				}else {
					return Direction.LEFT;
				}		
			}
		}
		throw new IllegalStateException();
	}
}
