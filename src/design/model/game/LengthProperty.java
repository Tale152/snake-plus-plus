package design.model.game;

/**
 * A set of method for managing snake's length. <p>
 * Here snake's length is intended like a logical size.
 * For instance on screen a snake can be long 3 but in here is 5, that means that
 * in 2 movements the actual size of the snake will reach it's logical size.
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 */
public interface LengthProperty {

    /**
     * @return get current logical snake's length
     */
    int getLength();

    /**
     * @param n how much to increase snake's length
     */
    void lengthen(int n);

    /**
     * @param n how much to decrease snake's length
     */
    void shorten(int n);

}
