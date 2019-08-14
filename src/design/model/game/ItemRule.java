package design.model.game;

import java.util.Optional;

/**
 * An ItemRule defines how an item will behave into the game.<p>
 * It defines it's spawn rules and whether or not it will disappear from field
 * if not picked and if will activate it's lasting effect. 
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 */
public interface ItemRule {

    /**
     * @return the class of the effect that rules refer to
     */
    Class<? extends Effect> getEffectClass();

    /**
     * @return delta time (milliseconds) between attempts on spawning an item
     */
    long getSpawnDelta();

    /**
     * @return the chance (0 to 1) of spawning this item on spawning attempt
     */
    double getSpawnChance();

    /**
     * @return the maximum number of items containing this effect that can appear simultaneously on field
     */
    int getMax();

    /**
     * @return delta time (milliseconds) before this item disappears from field (empty if it does not disappear)
     */
    Optional<Long> getItemDuration();

    /**
     * @return delta time (milliseconds) that the lasting effect of this item will last (empty if no lasting effect)
     */
    Optional<Long> getEffectDuration();

}
