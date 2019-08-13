package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;
import design.model.game.Effect;
import design.model.game.Field;
import design.model.game.Item;

/**
 * Allows to create items with any effect that defines them.<p>
 * Having the effect class into arguments allows automates the process
 * of spawning items.
 * @author Alessandro Talmi
 */
public class ItemFactory {

    private final Field field;

    /**
     Instantiate a new ItemFactory.
     * @param field where created items will be placed
     */
    public ItemFactory(final Field field) {
        this.field = field;
    }

    /**
     * Creates an Item with specified features.
     * @param point coordinates in the field
     * @param effectClass that determinate item behavior
     * @param dExpire delta (milliseconds) before item disappears from field
     * (empty if you want it to not disappear)
     * @param dEffectDuration delta (milliseconds) before item's lasting effect ends
     * (empty if you want it to not activate lasting effect)
     * @return desired item
     */
    public final Item createItem(final Point point, final Class<? extends Effect> effectClass,
            final Optional<Long> dExpire, final Optional<Long> dEffectDuration) {
        return new ItemImpl(field, point, effectClass, dExpire, dEffectDuration);
    }

}
