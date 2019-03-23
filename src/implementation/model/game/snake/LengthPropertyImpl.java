package implementation.model.game.snake;

import design.model.game.LengthProperty;

public class LengthPropertyImpl implements LengthProperty{
	
	private static final int LENGTH = 1;
	
	private int length;
	
	public LengthPropertyImpl() {
		this.length = LENGTH;
	}
	
	@Override
	public int getLength() {
		return this.length;
	}

	@Override
	public void lengthen(int n) {
		checkLength(n);
		this.length += n;
	}

	@Override
	public void shorten(int n) {
		checkLength(n);
		if(this.length - n > 1) {
			this.length -= n;
		} else {
			this.length = 1;
		}
	}
	
	private void checkLength(int n) {
		if(n < 0) {
			throw new IllegalArgumentException();
		}	
	}
	
	public String toString() {
		return "Snake length: " + this.length + "\n";
	}

}
