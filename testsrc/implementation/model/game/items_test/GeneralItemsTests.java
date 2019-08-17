package implementation.model.game.items_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import design.model.game.Effect;
import design.model.game.Item;
import design.model.game.Snake;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.items.Apple;
import implementation.model.game.items.BadApple;
import implementation.model.game.items.Beer;
import implementation.model.game.items.DoublePoints;
import implementation.model.game.items.GhostMode;
import implementation.model.game.items.GodMode;
import implementation.model.game.items.ItemFactory;
import implementation.model.game.items.Magnet;
import implementation.model.game.items.ScoreEarning;
import implementation.model.game.items.ScoreLoss;
import implementation.model.game.items.Slug;
import implementation.model.game.items.Spring;
import implementation.model.game.items.Turbo;

/**
 * Here common item behaviors are tested using every effect possible.
 */
public class GeneralItemsTests {

    private final List<Class<? extends Effect>> classesList = new ArrayList<>(Arrays.asList(
            Apple.class,
            BadApple.class,
            Beer.class,
            DoublePoints.class,
            GhostMode.class,
            GodMode.class,
            Magnet.class,
            ScoreEarning.class,
            ScoreLoss.class,
            Slug.class,
            Spring.class,
            Turbo.class
            ));

    /**
     * Tests that no effect attaches to a snake if effect duration is empty.
     */
    @Test
    public void testInstantaneousEffect() {
        for (int i = 0; i < classesList.size(); ++i) {
            final FieldImpl field = new FieldImpl(new Point(classesList.size(), 1));
            final ItemFactory itemFactory = new ItemFactory(field);
            final Item item = itemFactory.createItem(new Point(i, 0), classesList.get(i), Optional.empty(), Optional.empty());
            final Snake snake = SnakeFactoryForTestsUtils.baseSnake(Arrays.asList(new Point(0, 0)), field);
            AppleTest.collide(item, snake);
            if (snake.getEffects().size() != 0) {
                fail("failed at " + classesList.get(i).getSimpleName());
            }
        }
    }

    /**
     * Test timing into lasting effect.
     */
    @Test
    public void testLastingEffect() {
        final long effectDuration = 100;
        for (int i = 0; i < classesList.size(); ++i) {
            final FieldImpl field = new FieldImpl(new Point(classesList.size(), 1));
            final ItemFactory itemFactory = new ItemFactory(field);
            Item item = itemFactory.createItem(new Point(i, 0), classesList.get(i), Optional.empty(), Optional.of(effectDuration));
            final Snake snake = SnakeFactoryForTestsUtils.baseSnake(Arrays.asList(new Point(0, 0)), field);
            AppleTest.collide(item, snake);
            if (snake.getEffects().size() != 1) {
                fail("first effect attachment failed at " + classesList.get(i).getSimpleName());
            }
            assertEquals("checks that the effect duration of active effect into snake is equal to effectDuration variable",
                    Optional.of(effectDuration), snake.getEffects().get(0).getEffectDuration());
            item = itemFactory.createItem(new Point(i, 0), classesList.get(i), Optional.empty(), Optional.of(effectDuration));
            AppleTest.collide(item, snake);
            if (snake.getEffects().size() != 1) {
                fail("second effect attachment failed at " + classesList.get(i).getSimpleName());
            }
            assertEquals("check that effect duration of active effect into the snake has doubled",
                    Optional.of(effectDuration * 2), snake.getEffects().get(0).getEffectDuration());
        }
    }

    /**
     * Test lasting effect timing while snake is on ghost.
     */
    @Test
    public void testOnGhost() {
        classesList.remove(GhostMode.class);
        for (int i = 0; i < classesList.size(); ++i) {
            final FieldImpl field = new FieldImpl(new Point(classesList.size(), 1));
            final ItemFactory itemFactory = new ItemFactory(field);
            final Item item = itemFactory.createItem(new Point(i, 0), classesList.get(i), Optional.empty(), Optional.of(100L));
            final Snake snake = SnakeFactoryForTestsUtils.ghostSnake(Arrays.asList(new Point(0, 0)), field);
            AppleTest.collide(item, snake);
            if (snake.getEffects().size() != 0) {
                fail("effect attachment failed at " + classesList.get(i).getSimpleName());
            }
        }
        classesList.add(GhostMode.class);
        final FieldImpl field = new FieldImpl(new Point(classesList.size(), 1));
        final ItemFactory itemFactory = new ItemFactory(field);
        final Item item = itemFactory.createItem(new Point(0, 0), GhostMode.class, Optional.empty(), Optional.of(100L));
        final Snake snake = SnakeFactoryForTestsUtils.ghostSnake(Arrays.asList(new Point(0, 0)), field);
        AppleTest.collide(item, snake);
        if (snake.getEffects().size() != 1) {
            fail("effect attachment failed at GhostMode");
        }
    }

}
