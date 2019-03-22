package implementation.model.game.snake;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import design.model.game.Direction;
import design.model.game.Effect;
import design.model.game.Item;
import design.model.game.Player;
import design.model.game.PlayerNumber;
import design.model.game.Properties;
import design.model.game.Snake;
import implementation.model.game.items.BodyPart;
import implementation.model.game.items.ItemFactory;

public class SnakeImpl implements Snake{

	private Player player;
	private Properties properties;
	private List<Effect> effects;
	private boolean isAlive;
	private List<BodyPart> bodyPart;
	
	public SnakeImpl(PlayerNumber playerNumber, String playerName, Direction direction, long deltaT, double speedMultiplier, long lastUpdate) {
		this.player = new PlayerImpl(playerNumber, playerName);
		this.properties = new PropertiesImpl(direction, deltaT, speedMultiplier, lastUpdate);
		this.effects = new ArrayList<>();
		this.isAlive = true;
		this.bodyPart = new ArrayList<>(); 
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
	public List<Item> move(Point point) {
		final int currentSize = this.properties.getLength().getLength();
		final Point oldTail;
		List<Item> differences = new ArrayList<>();
		
		//if Snake is longer than the size he supposed to be
		if (this.bodyPart.size() > currentSize) {
			while(this.bodyPart.size() != currentSize) {
				differences.add(this.bodyPart.get(this.bodyPart.size()));
				this.bodyPart.remove(this.bodyPart.size());
			}
		}
		//calcola le nuove posizioni di ogni pezzo ma si salva la vecchia posizione della coda 
		oldTail = this.bodyPart.get(this.bodyPart.size()).getPoint();
		for(int i = 1; i <= this.bodyPart.size(); i++) {
				this.bodyPart.get(i).getPoint().move(this.bodyPart.get(i-1).getPoint().x, this.bodyPart.get(i-1).getPoint().y);
		}
		this.bodyPart.get(0).getPoint().move(point.x, point.y);
		
		//crea al massimo un nuovo pezzo assegnandogli le coordinate della vecchia coda
		if(this.bodyPart.size() < this.properties.getLength().getLength()) {
			BodyPart b = (BodyPart) ItemFactory.createBodyPart(oldTail, this);
			this.bodyPart.add(this.bodyPart.size() + 1, b);
			differences.add(this.bodyPart.get(this.bodyPart.size()));
		}
		
		return differences;
	}

	@Override
	public void addEffect(Effect effect) {
		this.effects.add(effect);
	}

	@Override
	public boolean removeEffect(Effect effect) {
		if(this.effects.contains(effect)) {
			this.effects.remove(effect);
		}
		if(this.effects.contains(effect)) {
			return false;
		}
		return true;
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
		//le coordinate della testa sono le coordinate della coda ecc
		//inverte le coordinate dei pezzi 
		//e poi setta la direzione dello snake 
		Point p1 = this.bodyPart.get(this.bodyPart.size() - 1).getPoint();
		Point p2 = this.bodyPart.get(this.bodyPart.size()).getPoint();
		//determino e setto la nuova direzione dello snake 
		Direction direction = determinateDirection(p1, p2);
		this.properties.getDirection().setDirection(direction);
		
		//algoritmo da cambiare in modo migliore
		for(int i = 0, j = this.bodyPart.size(); i < this.bodyPart.size()/2; i++, j--) {
			Point temp = this.bodyPart.get(i).getPoint();
			this.bodyPart.get(i).getPoint().move(this.bodyPart.get(j).getPoint().x, this.bodyPart.get(j).getPoint().y);
			this.bodyPart.get(j).getPoint().move(temp.x, temp.y);
		}
	}

	@Override
	public List<Item> getBodyParts() {
		return new ArrayList<Item>(this.bodyPart);
	}

	private Direction determinateDirection(Point p1, Point p2) {
		//if x are the same Snake is moving up or down
		if(p1.x == p2.x) {
			if(p1.y > p2.y) {
				return Direction.DOWN;
			} 
			return Direction.UP;
		}
		
		//in this case Snake is moving right or left
		if(p1.y == p2.y) {
			if(p1.y > p2.y) {
				return Direction.LEFT;
			}
		}
		return Direction.RIGHT;
	}
	
}