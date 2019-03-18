package implementation.model.game.snake;

import design.model.game.LengthProperty;

public class LengthPropertyImpl implements LengthProperty{

	private int length;
	
	public LengthPropertyImpl(int length) {
		if(length < 1) {
			throw new IllegalArgumentException();
		}
		this.length = length;
	}
	
	@Override
	public int getLength() {
		return this.length;
	}

	@Override
	public void lengthen(int n) {
		this.length += n;
	}

	@Override
	public void shorten(int n) {
		if(this.length - n > 1) {
			this.length -= n;
		} else {
			this.length = 1;
		}
	}
	
	public String toString() {
		return " " + this.length;
	}

}
