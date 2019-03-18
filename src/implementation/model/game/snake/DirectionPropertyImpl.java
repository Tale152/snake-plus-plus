package implementation.model.game.snake;

import design.model.game.Direction;
import design.model.game.DirectionProperty;

public class DirectionPropertyImpl implements DirectionProperty{
	
	private Direction direction;
	private boolean reversed;
	
	public DirectionPropertyImpl(Direction direction, boolean reversed) {
		this.direction = direction;
		this.reversed = reversed;
		
	}
	
	@Override
	public Direction getDirection() {
		return this.direction;
	}

	@Override
	public boolean setDirection(Direction direction) {
		switch(direction) {
			case UP: if(!this.direction.equals(Direction.DOWN)) { 
				this.direction = direction;
				return true;
				} 
			break;
			case DOWN: if(!this.direction.equals(Direction.UP)) { 
				this.direction = direction; 
				return true;
				} 
			break;
			case LEFT: if(!this.direction.equals(Direction.RIGHT)) { 
				this.direction = direction; 
				return true;
				} 
			break;
			case RIGHT: if(!this.direction.equals(Direction.LEFT)) { 
				this.direction = direction; 
				return true;
				} 
			break;
			default: throw new IllegalStateException();
		
		}
		return false;
	}

	@Override
	public void setReverseDirection(boolean reverse) {
		this.reversed = reverse;
	}

	@Override
	public boolean getReverseDirection() {
		return this.reversed;
	}
	
	public String toString() {
		return this.direction.name();
	}

}
