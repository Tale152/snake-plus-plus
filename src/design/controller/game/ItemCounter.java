package design.controller.game;

import java.util.NoSuchElementException;
import design.model.game.Effect;

interface ItemCounter {

    boolean increase(Class<? extends Effect> effect) throws NoSuchElementException;

    boolean decrease(Class<? extends Effect> effect) throws NoSuchElementException;

    int getCurrent(Class<? extends Effect> effect) throws NoSuchElementException;

    int getMax(Class<? extends Effect> effect) throws NoSuchElementException;

    boolean isMax(Class<? extends Effect> effect) throws NoSuchElementException;

    long getLastSpawnAttempt(Class<? extends Effect> effect);

    void setLastSpawnAttempt(Class<? extends Effect> effect, long time);

}
