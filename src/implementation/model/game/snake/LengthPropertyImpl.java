package implementation.model.game.snake;

import design.model.game.LengthProperty;
/**
 * This class is part of the snake properties, here there are the method to use
 * to set all the length properties and to get snake current length.
 * @author Elisa Tronetti
 */
public class LengthPropertyImpl implements LengthProperty {

    private static final int LENGTH = 1;

    private int length;

    /**
     * Set snake default length (1 right now).
     */
    public LengthPropertyImpl() {
        this.length = LENGTH;
    }

    @Override
    public final int getLength() {
        return this.length;
    }

    @Override
    public final void lengthen(final int n) {
        checkLength(n);
        this.length += n;
    }

    @Override
    public final void shorten(final int n) {
        checkLength(n);
        if (this.length - n > 1) {
            this.length -= n;
        } else {
            this.length = 1;
        }
    }

    private void checkLength(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return a string that contains snake current length
     */
    public String toString() {
        return "Snake length: " + this.length + "\n";
    }

}
