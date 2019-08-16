package implementation.model.game.gameRules;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.*;
import org.junit.Test;
import design.model.game.*;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.gamerules.LossConditionsImpl;
import implementation.model.game.items.SnakeFactoryForTests;

public class LossConditionsTest {
	
	private static LossConditions loss;
	
	@Test
	public void testInit() {
		try {
			loss = new LossConditionsImpl(false, null, false);
			fail("NullPointerException expected");
		} catch (NullPointerException e){
		} catch (Exception e ) {
			fail("Wrong exception");
		}
		try {
			loss = new LossConditionsImpl(false, Optional.of(-1L), false);
			fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e){
		} catch (Exception e ) {
			fail("Wrong exception");
		}
	}
	
	@Test
	public void testAllSnakeDiedContitions() {
		loss = new LossConditionsImpl(true, Optional.empty(), true);
		Field field = new FieldImpl(new Point(100,100));
		List<Snake> snakes = new ArrayList<>();
		int nSnakes = 4;
		for (int i = 0; i < nSnakes; ++i) {
			snakes.add(SnakeFactoryForTests.baseSnake(
					new ArrayList<Point>(Arrays.asList(new Point(0,0))), field));
		}
		for (int i = 0; i < nSnakes; ++i) {
			assertFalse(loss.checkSnakes(snakes));
			snakes.get(i).kill();
		}
		assertTrue(loss.checkSnakes(snakes));
	}
	
	@Test
	public void testTimeConditions() {
		long time = 10L;
		loss = new LossConditionsImpl(false, Optional.of(time), true);
		for (long i = 0; i < time; ++i) {
			assertFalse(loss.checkTime(i));
		}
		assertTrue(loss.checkTime(time));
		loss = new LossConditionsImpl(false, Optional.of(0L), false);
		for (long i = time; i > 0; --i) {
			assertFalse(loss.checkTime(i));
		}
		assertTrue(loss.checkTime(0L));
	}
}
