package implementation.model.game.items;

import java.awt.Point;
import java.util.Optional;
import design.model.game.Field;
import design.model.game.Item;

public class ItemFactory {
	private final Field field;

	public ItemFactory(Field field) {
		this.field = field;
	}
	
	public Item createApple(Point point, Optional<Long> dExpire, Optional<Long> dEffectDuration) {
		return new ItemImpl(field, point, Apple.class, dExpire, dEffectDuration);
	}
	
	public Item createBadApple(Point point, Optional<Long> dExpire, Optional<Long> dEffectDuration) {
		return new ItemImpl(field, point, BadApple.class, dExpire, dEffectDuration);
	}
	
	public Item createBeer(Point point, Optional<Long> dExpire, Optional<Long> dEffectDuration) {
		return new ItemImpl(field, point, Beer.class, dExpire, dEffectDuration);
	}
	
	public Item createDoublePoints(Point point, Optional<Long> dExpire, Optional<Long> dEffectDuration) {
		return new ItemImpl(field, point, DoublePoints.class, dExpire, dEffectDuration);
	}
	
	public Item createGhostMode(Point point, Optional<Long> dExpire, Optional<Long> dEffectDuration) {
		return new ItemImpl(field, point, GhostMode.class, dExpire, dEffectDuration);
	}
	
	public Item createGodMode(Point point, Optional<Long> dExpire, Optional<Long> dEffectDuration) {
		return new ItemImpl(field, point, GodMode.class, dExpire, dEffectDuration);
	}
	
	public Item createMagnet(Point point, Optional<Long> dExpire, Optional<Long> dEffectDuration) {
		return new ItemImpl(field, point, Magnet.class, dExpire, dEffectDuration);
	}
	
	public Item createScoreEarning(Point point, Optional<Long> dExpire, Optional<Long> dEffectDuration) {
		return new ItemImpl(field, point, ScoreEarning.class, dExpire, dEffectDuration);
	}
	
	public Item createScoreLoss(Point point, Optional<Long> dExpire, Optional<Long> dEffectDuration) {
		return new ItemImpl(field, point, ScoreLoss.class, dExpire, dEffectDuration);
	}
	
	public Item createSlug(Point point, Optional<Long> dExpire, Optional<Long> dEffectDuration) {
		return new ItemImpl(field, point, Slug.class, dExpire, dEffectDuration);
	}
	
	public Item createSpring(Point point, Optional<Long> dExpire, Optional<Long> dEffectDuration) {
		return new ItemImpl(field, point, Spring.class, dExpire, dEffectDuration);
	}
	
	public Item createTurbo(Point point, Optional<Long> dExpire, Optional<Long> dEffectDuration) {
		return new ItemImpl(field, point, Turbo.class, dExpire, dEffectDuration);
	}
	
}
