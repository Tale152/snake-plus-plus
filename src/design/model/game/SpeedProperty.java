package design.model.game;

public interface SpeedProperty {

    long getDeltaT();

    void setDeltaT(long deltaT);

    void applySpeedMultiplier(double mul);

    double getSpeedMultiplier();

    void applyLenghtSpeedValue(double val);

    double getLenghtSpeedValue();

}
