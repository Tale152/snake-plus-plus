package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;
import design.model.game.Item;
import design.model.game.Snake;

public class ItemFactory {
	
	protected static int SCORE = 100;
	protected static int LENGHT_INCREMENT = 1;
	
	public static Item createBodyPart(Point point, Snake owner) {
		return new BodyPart(point, owner);
	}
	
	public static Item createWall(Point point, Optional<Long> expirationTime) {
		return new Wall(point, expirationTime);
	}
	
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
	
	public static Item createSpring(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		return new Spring(point, expirationTime, effectDuration);
	}
	
	public static Item createDoublePoints(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		return new DoublePoints(point, expirationTime, effectDuration);
	}
	
	public static Item createMagnet(Point point, Optional<Long> expirationTime, Optional<Long> effectDuration) {
		return new Magnet(point, expirationTime, effectDuration);
	}
	
}
