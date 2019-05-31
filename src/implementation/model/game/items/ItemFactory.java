package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;

import design.model.game.Effect;
import design.model.game.Field;
import design.model.game.Item;

public class ItemFactory {
	private final Field field;

	public ItemFactory(Field field) {
		this.field = field;
	}
	
	public Item createItem(Point point, Class<? extends Effect> effectClass, Optional<Long> dExpire, Optional<Long> dEffectDuration) {
		return new ItemImpl(field, point, effectClass, dExpire, dEffectDuration);
	}
	
}
