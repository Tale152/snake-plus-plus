package implementation.model.game.snake;

import design.model.game.SpeedProperty;
/**
 *  * This class is part of the snake properties, here there are the method to use
 * to set all the speed properties, such us the delta, the speed multiplier and the 
 * multiplier that changes snake speed based on his length.
 * @author Elisa Tronetti
 */
public class SpeedPropertyImpl implements SpeedProperty {

    private static final double DEFAULTLENGTHMULTIPLIER = 0;

    private long deltaT;
    private double multiplier;
    private double lenghtValue;

    /**
     * Set the default speed properties, such as the delta, the speed multiplier and the multiplier
     * base on snake length (which is 0 right now).
     * @param deltaT representing the time between two snake movements
     * @param speedMultiplier the speed multiplier that will be applied to snake's deltaT
     */
    public SpeedPropertyImpl(final long deltaT, final double speedMultiplier) {
        checkDelta(deltaT);
        this.deltaT = deltaT;
        checkMultiplier(speedMultiplier);
        this.multiplier = speedMultiplier;
        this.lenghtValue = DEFAULTLENGTHMULTIPLIER;
    }

    @Override
    public final long getDeltaT() {
        return this.deltaT;
    }

    @Override
    public final void setDeltaT(final long deltaT) {
        checkDelta(deltaT);
        this.deltaT = deltaT;
    }

    @Override
    public final void applySpeedMultiplier(final double mul) {
        if (this.multiplier + mul < 0) {
            throw new IllegalStateException();
            } 
        this.multiplier += mul;
    }

    @Override
    public final double getSpeedMultiplier() {
        return this.multiplier;
    }

    private void checkDelta(final long deltaT) {
        if (deltaT <= 0) {
            throw new IllegalArgumentException();
        }
    }

    private void checkMultiplier(final double multiplier) {
        if (multiplier < 0) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public final void applyLengthSpeedValue(final double val) {
        this.lenghtValue = val;
    }

    @Override
    public final double getLengthSpeedValue() {
        return this.lenghtValue;
    }

    /**
     * @return a string that contains the speed multiplier and the the delta, which is the 
     * time in milliseconds between two snake movement.
     */
    public String toString() {
        return "Multiplier: " + this.multiplier + "\n"
                + "Delta: " + this.deltaT + "\n";
    }

}
