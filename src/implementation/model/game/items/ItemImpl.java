package implementation.model.game.items;

import java.awt.Point;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import design.model.game.Effect;
import design.model.game.Field;
import design.model.game.Item;
import design.model.game.Snake;


/**
 * @see Item
 */
public class ItemImpl extends CollidableAbstract implements Item  {

    private boolean eaten;
    private final Field field;
    private final Optional<Long> dExpire;
    private final Optional<Long> dEffectDuration;
    private final Class<? extends Effect> effectClass;

    /**
     * 
     * @param field where item will be placed
     * @param point coordinates in the field
     * @param effectClass that determinate item behavior
     * @param dExpire delta (milliseconds) before item disappears from field
     * (empty if you want it to not disappear)
     * @param dEffectDuration delta (milliseconds) before item's lasting effect ends
     * (empty if you want it to not activate lasting effect)
     */
    protected ItemImpl(final Field field, final Point point, final Class<? extends Effect> effectClass,
            final Optional<Long> dExpire, final Optional<Long> dEffectDuration) {
        super(point);
        field.addItem(this);
        this.field = field;
        this.effectClass = effectClass;
        this.dExpire = dExpire;
        this.dEffectDuration = dEffectDuration;
        eaten = false;
    }

    private boolean isEaten(final Snake collider) {
        return !collider.getProperties().getCollisionProperty().isIntangible() 
                || (collider.getProperties().getCollisionProperty().isIntangible()) && effectClass.equals(GhostMode.class);
    }

    @Override
    public final void onCollision(final Snake collider) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (isEaten(collider)) {
            eaten = true;
            final Constructor<? extends Effect> constructor = effectClass.getConstructor(Optional.class);
            final Effect effect = constructor.newInstance(dEffectDuration);
            effect.instantaneousEffect(collider);
            //if the effect duration is present than that means that lasting effect
            //needs to be activated, so I give it to snake that than decides what to do
            if (dEffectDuration.isPresent()) {
                collider.addEffect(effect);
            }
            field.removeItem(this);
        }
    }


    @Override
    public final void run() {
        //if expire delta is present that means that, after a period of time of not being eaten
        //this item will disappear from field, so it waits until the time comes and, if not been eaten,
        //removes itself from field and activates it's expiration effect (despite the fact that no item has
        //any expiration effect unfortunately for time reasons).
        if (dExpire != null && dExpire.isPresent()) {
            try {
                Thread.sleep(dExpire.get());
                if (!eaten) {
                    field.removeItem(this);
                    final Constructor<? extends Effect> constructor = effectClass.getConstructor(Optional.class);
                    final Effect effect = constructor.newInstance(dEffectDuration);
                    effect.expirationEffect(field);
                }
            } catch (InterruptedException | InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public final Class<? extends Effect> getEffectClass() {
        return effectClass;
    }

}
