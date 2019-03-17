package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;
import design.model.game.Item;

public class ItemFactory {
	
	public static Item createApple(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		return new Apple(point, expirationTime, effectDuration);
	}
	
	public static Item createBadApple(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		return new BadApple(point, expirationTime, effectDuration);
	}
	
	public static Item createBeer(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		return new Beer(point, expirationTime, effectDuration);
	}

}
