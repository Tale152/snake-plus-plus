package design.model.game;

/**
 * A body part is what a snake is composed. It has various properties that determinate which kind of body part 
 * we are dealing with and whom owns it.
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 */
public interface BodyPart extends Collidable {

    /**
     * @return if this body part is the head of the snake
     */
    boolean isHead();

    /**
     * @param head boolean value to set if this body part is the head of the snake
     */
    void setHead(boolean head);

    /**
     * @return if this body part is a body piece of the snake (not head or tail)
     */
    boolean isBody();

    /**
     * @param body boolean value to set if this body part is a body piece of the snake (not head or tail)
     */
    void setBody(boolean body);

    /**
     * @return if this body part is the tail of the snake
     */
    boolean isTail();

    /**
     * @param tail boolean value to set if this body part is the snake tail
     */
    void setTail(boolean tail);

    /**
     * @return if this body part has another body part in the cell directly on top of this one
     */
    boolean isCombinedOnTop();

    /**
     * @param combined set if this body part has another body part in the cell directly on top of this one
     */
    void setCombinedOnTop(boolean combined);

    /**
     * @return if this body part has another body part in the cell directly on bottom of this one
     */
    boolean isCombinedOnBottom();

    /**
     * @param combined set if this body part has another body part in the cell directly on bottom of this one
     */
    void setCombinedOnBottom(boolean combined);

    /**
     * @return if this body part has another body part in the cell directly on left of this one
     */
    boolean isCombinedOnLeft();

    /**
     * @param combined set if this body part has another body part in the cell directly on left of this one
     */
    void setCombinedOnLeft(boolean combined);

    /**
     * @return if this body part has another body part in the cell directly on right of this one
     */
    boolean isCombinedOnRight();

    /**
     * @param combined set if this body part has another body part in the cell directly on right of this one
     */
    void setCombinedOnRight(boolean combined);

    /**
     * @return the snakes that owns this body part
     */
    Snake getOwner();

}
