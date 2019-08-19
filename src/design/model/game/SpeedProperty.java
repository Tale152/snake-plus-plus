package design.model.game;

/**
 * A set of properties useful to handle snake's speed.
 */
public interface SpeedProperty {

    /**
     * @return the delta (milliseconds) between two snake movements
     */
    long getDeltaT();

    /**
     * @param deltaT (milliseconds) representing the time between two snake movements
     */
    void setDeltaT(long deltaT);

    /**
     * @param mul multiplier that will be added to the current speed multiplier
     */
    void applySpeedMultiplier(double mul);

    /**
     * @return the speed multiplier that will be applied to snake's deltaT
     */
    double getSpeedMultiplier();

    /**
     * @param val the value that will be applied to delta to determinate snake's speed based on length
     */
    void applyLengthSpeedValue(double val);

    /**
     * @return the value that will be applied to delta to determinate snake's speed based on length
     */
    double getLengthSpeedValue();

}
