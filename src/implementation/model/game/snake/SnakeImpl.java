package implementation.model.game.snake;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import design.model.game.BodyPart;
import design.model.game.Direction;
import design.model.game.Effect;
import design.model.game.Player;
import design.model.game.PlayerNumber;
import design.model.game.Properties;
import design.model.game.Snake;
import implementation.model.game.items.BodyPartImpl;

public class SnakeImpl implements Snake{

	private Player player;
	private Properties properties;
	private List<Effect> effects;
	private boolean isAlive;
	private List<BodyPart> bodyPart;
	private BodyPart firstPart;
	
	public SnakeImpl(List<Point> point, PlayerNumber playerNumber, String playerName, Direction direction, long deltaT, double speedMultiplier, long lastUpdate) {
		this.player = new PlayerImpl(playerNumber, playerName);
		this.properties = new PropertiesImpl(direction, deltaT, speedMultiplier);
		this.effects = new ArrayList<>();
		this.isAlive = true;
		this.bodyPart = new ArrayList<>(); 
		
		checkPoint(point);
		for(int i = 0; i <= point.size() - 1; i++) {
			this.firstPart = new BodyPartImpl(point.get(i));
			this.bodyPart.add(firstPart);
		}
		this.properties.getLengthProperty().lengthen(point.size() - 1); //update length properties, the beginning length is the number of point - 1 (the first is supposed to exist
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
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
		this.effects.add(effect);
	}

	@Override
	public boolean removeEffect(Effect effect) {
		return this.effects.remove(effect);
	}

	@Override
	public List<Effect> getEffects() {
		return new ArrayList<>(effects);
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
		Direction direction;
		if(this.bodyPart.size() > 1) {
			//le coordinate della testa sono le coordinate della coda ecc
			//inverte le coordinate dei pezzi 
			//e poi setta la direzione dello snake 
			Point p1 = this.bodyPart.get(this.bodyPart.size() - 2).getPoint();
			Point p2 = this.bodyPart.get(this.bodyPart.size() - 1).getPoint();
			//determino e setto la nuova direzione dello snake 
			direction = determinateDirection(p1, p2);
			Collections.reverse(this.bodyPart);
		} else {
			direction = determinateOppositeDirection(this.properties.getDirectionProperty().getDirection()); //calcolo la direzione opposta se snake ha lunghezza 1
		}
		this.properties.getDirectionProperty().forceDirection(direction);

	}

	@Override
	public List<BodyPart> getBodyParts() {
		return new ArrayList<BodyPart>(this.bodyPart);
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
	
	
	private void checkPoint(List<Point> point) {
		if(point.size() < 0) {
			throw new IllegalArgumentException(); 
		}
	}
	
	public String toString() {
		return "Snake is alive: " + this.isAlive + "\n";
	}
	
//LA MOVE È DA RIFARE
	
//	private List<Item> move(Point point) {
//		final int currentLength = this.properties.getLengthProperty().getLength();
//		final Point oldTail;
//		List<Item> differences = new ArrayList<>();
//		
//		//if Snake is longer than the size he supposed to be
//		if (this.bodyPart.size() > currentLength) {
//			while(this.bodyPart.size() != currentLength) {
//				differences.add(this.bodyPart.get(this.bodyPart.size() - 1));
//				this.bodyPart.remove(this.bodyPart.size() - 1);
//			}
//		}
//		//calcola le nuove posizioni di ogni pezzo ma si salva la vecchia posizione della coda 
//		oldTail = new Point(this.bodyPart.get(this.bodyPart.size() - 1).getPoint().x, this.bodyPart.get(this.bodyPart.size() - 1).getPoint().y);
//		for(int i = this.bodyPart.size() - 1; i < 0; i--) {
//			this.bodyPart.get(i).getPoint().move(this.bodyPart.get(i-1).getPoint().x, this.bodyPart.get(i-1).getPoint().y);
//		}
//		
//		this.bodyPart.get(0).getPoint().move(point.x, point.y);
//		
//		//crea al massimo un nuovo pezzo assegnandogli le coordinate della vecchia coda
//		if(this.bodyPart.size() < currentLength) {
//			BodyPart b = (BodyPart) ItemFactory.createBodyPart(oldTail, this);
//			this.bodyPart.add(b);
//			differences.add(this.bodyPart.get(this.bodyPart.size() - 1));
//		}
//		
//		return differences;
//	}
}
