package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;
import design.model.game.Item;

public class ItemFactory {
	
	public static int SCORE = 100;
	public static int LENGHT_INCREMENT = 1;
	
	public static Item createApple(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		return new Apple(point, expirationTime, effectDuration);
	}
	
	public static Item createBadApple(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		return new BadApple(point, expirationTime, effectDuration);
	}
	
	public static Item createBeer(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		return new Beer(point, expirationTime, effectDuration);
	}

	public static Item createScoreEarning(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		return new ScoreEarning(point, expirationTime, effectDuration);
	}
	
	public static Item createScoreLoss(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		return new ScoreLoss(point, expirationTime, effectDuration);
	}
	
	public static Item createGodMode(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		return new GodMode(point, expirationTime, effectDuration);
	}
	
	public static Item createGhostMode(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		return new GhostMode(point, expirationTime, effectDuration);
	}
	
}
