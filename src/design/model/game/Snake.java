package design.model.game;

import java.util.List;

public interface Snake extends Runnable{

    Player getPlayer();

    Properties getProperties();

    void addEffect(Effect effect);

    boolean removeEffect(Effect effect);

    List<Effect> getEffects();

    boolean isAlive();

    void kill();

    void reverse();

    List<BodyPart> getBodyParts();

    boolean hasMoved();

}
