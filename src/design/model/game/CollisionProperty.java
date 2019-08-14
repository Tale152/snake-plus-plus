package design.model.game;

/**
 * A set of properties that can change the result of a collision within the snake and everything else.
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 */
public interface CollisionProperty {

    /**
     * @param inv sets if the snake is invincible or not
     */
    void setInvincibility(boolean inv);

    /**
     * @return if the snake is invincible or not
     */
    boolean getInvincibility();

    /**
     * @param intangibility sets if the snake is intangible or not
     */
    void setIntangibility(boolean intangibility);

    /**
     * @return if the snake is intangible or not
     */
    boolean getIntangibility();

    /**
     * @param spring set if the snake bounces off walls on collision or not
     */
    void setSpring(boolean spring);

    /**
     * @return if the snake bounces off walls on collision or not
     */
    boolean getSpring();

}
