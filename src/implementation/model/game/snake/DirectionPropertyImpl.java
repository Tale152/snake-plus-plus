package implementation.model.game.snake;

import java.util.Optional;

import design.model.game.Direction;
import design.model.game.DirectionProperty;

public class DirectionPropertyImpl implements DirectionProperty{
	
	private Direction direction;
	private Optional<Direction> nextInputedDirection;
	private boolean reversed;
	private boolean canChangeDirection;
	
	public DirectionPropertyImpl(Direction direction) {
		checkDirection(direction);
		this.direction = direction;
		this.reversed = false;
		this.canChangeDirection = true;
		nextInputedDirection = Optional.empty();
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
			this.direction = directionCase(direction);
		} else if (!nextInputedDirection.isPresent()) {
			Direction nextPossibleDirection = directionCase(direction);
			if (nextPossibleDirection != this.direction) {
				nextInputedDirection = Optional.of(nextPossibleDirection);
			}
		}
		return this.canChangeDirection;
	}
	
	private Direction directionCase(Direction inputDirection) {
		if(!this.direction.equals(reversedDirection(inputDirection)) && !this.reversed) { 
			return inputDirection; 
		} else if(!this.direction.equals(inputDirection) && this.reversed){
			return reversedDirection(inputDirection);
		}
		return this.direction;
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

	@Override
	public boolean hasNextValidDirection() {
		return nextInputedDirection.isPresent();
	}

	@Override
	public Direction getNextValidDirection() {
		if (nextInputedDirection.isPresent()){
			Direction result = nextInputedDirection.get();
			nextInputedDirection = Optional.empty();
			return result;
		}
		return direction;
	}

}
