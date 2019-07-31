package design.model.game;

/**
 * Provides all the snake's length properties.
 * @author elisa
 *
 */
public interface LengthProperty {
	/**
	 * Used to get current length.
	 * @return Snake's current length.
	 */
	public int getLength();
	
	/**
	 * Increase current length.
	 * @param n An integer that specify the increment of the length.
	 */
	public void lengthen(int n);
	
	/**
	 * Decrease current length.
	 * @param n An integer that specify the decrement of the length.
	 */
	public void shorten(int n);
	
}
