package implementation.model.game.snake;

import design.model.game.Direction;
import design.model.game.DirectionProperty;

public class DirectionPropertyImpl implements DirectionProperty{
	
	private Direction direction;
	private boolean reversed;
	private boolean canChangeDirection;
	
	public DirectionPropertyImpl(Direction direction) {
		checkDirection(direction);
		this.direction = direction;
		this.reversed = false;
		this.canChangeDirection = true;
	}
	
	@Override
	public Direction getDirection() {
		return this.direction;
	}

	@Override
	public boolean forceDirection(Direction direction) {
		this.direction = direction;
		return true;
	}
	
	@Override
	public boolean setDirection(Direction direction) {
		checkDirection(direction);
		if(canChangeDirection) {
			this.canChangeDirection = false;
			directionCase(direction);
		}
		return this.canChangeDirection;
	}
	
	private void directionCase(Direction inputDirection) {
		if(!this.direction.equals(reversedDirection(inputDirection)) && !this.reversed) { 
			this.direction = inputDirection; 
		} else if(!this.direction.equals(inputDirection) && this.reversed){
			this.direction = reversedDirection(inputDirection);
		}
	}

	public void allowChangeDirection() {
		this.canChangeDirection = true;
	}
	
	@Override
	public void setReverseDirection(boolean reverse) {
		this.reversed = reverse;
	}

	@Override
	public boolean getReverseDirection() {
		return this.reversed;
	}
	
	private void checkDirection(Direction direction) {
		if(direction == null) {
			throw new IllegalArgumentException();
		}
	}
	
	private Direction reversedDirection(Direction direction) {
		
		switch(direction) {
		case UP:  direction = Direction.DOWN;
		break;
		case DOWN: direction = Direction.UP;
		break;
		case LEFT: direction = Direction.RIGHT;
		break;
		case RIGHT: direction = Direction.LEFT;
		break;
		default: throw new IllegalStateException();
		}
		return direction;
	}
	
	public String toString() {
		return "Current direction: " + this.direction.name() + "\n" 
				+ "Reversed status: " + this.reversed + "\n";
	}

}
