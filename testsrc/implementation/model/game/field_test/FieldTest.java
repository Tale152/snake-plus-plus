package implementation.model.game.field_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.Optional;

import org.junit.Test;

import implementation.model.game.field.FieldImpl;
import implementation.model.game.items.Apple;
import implementation.model.game.items.BadApple;
import implementation.model.game.items.Beer;
import implementation.model.game.items.ItemFactory;
import design.model.game.Field;
import design.model.game.Item;

/**
 * Tests regarding the field status.
 */
public class FieldTest {

    private static final int TWO_DIMENSION = 2;
    private static final int FIVE_DIMENSION = 5;
    private static final int SIX_DIMENSION = 6;

    /**
     * Used to test if the field initialization is done correctly.
     */
    @Test
    public void testInit() {
        Field field;

        assertTrue("Check if giving 0,0 as field dimension throws IllegalArgumenException",
                checkIllegalArgumentFieldDimension(new Point(0, 0)));
        assertTrue("Check if giving 0 as x axis to the field dimension throws IllegalArgumenException",
                checkIllegalArgumentFieldDimension(new Point(10, 0)));
        assertTrue("Check if giving 0 as y axis to the field dimension throws IllegalArgumenException",
                checkIllegalArgumentFieldDimension(new Point(0, 10)));
        assertTrue("Check if giving a negative point as field dimension throws IllegalArgumenException",
                checkIllegalArgumentFieldDimension(new Point(-TWO_DIMENSION, -TWO_DIMENSION)));
        assertTrue("Check if giving a negative x axis as field dimension throws IllegalArgumenException",
                checkIllegalArgumentFieldDimension(new Point(-TWO_DIMENSION, 0)));
        assertTrue("Check if giving a negative y axis as field dimension throws IllegalArgumenException",
                checkIllegalArgumentFieldDimension(new Point(0, -TWO_DIMENSION)));

        field = new FieldImpl(new Point(10, FIVE_DIMENSION));
        assertEquals("Check if the field actual height is 5", FIVE_DIMENSION, field.getHeight());
        assertEquals("Check if the field actual width is 10", 10, field.getWidth());
    }

    /**
     * Used to test if an item is added correctly to the field.
     */
    @Test
    public void testAddItems() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        Item item;

        item = itemFactory.createItem(new Point(FIVE_DIMENSION, FIVE_DIMENSION), Apple.class, Optional.empty(), Optional.empty());
        field.addItem(item);
        assertEquals("Check if the item added to the field is the actual item added", field.getItems().get(0), item);
        field.addItem(item);
        assertEquals("Check if the size of the list of the field item is 1, even if added two times the same item",
                field.getItems().size(), 1);

        final Item beer = itemFactory.createItem(new Point(FIVE_DIMENSION, SIX_DIMENSION), Beer.class, Optional.empty(), Optional.empty());
        field.addItem(beer);
        assertEquals("Check if the size of the list of the field item is 2", field.getItems().size(), 2);
        assertTrue("Check if the list of the field item contains the first item added", field.getItems().contains(item));
        assertTrue("Check if the list of the field item contains the second item added", field.getItems().contains(beer));
    }

    /**
     * Used to test if the field return the list of items contained in a cell correctly.
     */
    @Test
    public void testGetCell() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);

        assertEquals("Check if the size of the list of the collidable contained in the cell is 0",
                0, field.getCell(new Point(1, 1)).size());
        final Item item = itemFactory.createItem(new Point(1, 1), Beer.class, Optional.empty(), Optional.empty());
        assertEquals("Check if the size of the list of the collidable contained in the cell is 1",
                1, field.getCell(new Point(1, 1)).size());
        assertTrue("Check if the list of collidable of a cell contains the item created",
                field.getCell(new Point(1, 1)).contains(item));

        final Item beer = itemFactory.createItem(new Point(1, 1), Beer.class, Optional.empty(), Optional.empty());
        assertEquals("Check if the list of collidable of a cell contains 2 items", 2, field.getCell(new Point(1, 1)).size());
        assertTrue("Check if the list of collidable of a cell contains the first item created",
                field.getCell(new Point(1, 1)).contains(item));
        assertTrue("Check if the list of collidable of a cell contains the second item created",
                field.getCell(new Point(1, 1)).contains(beer));

        final Item badApple = itemFactory.createItem(new Point(2, 2), BadApple.class, Optional.empty(), Optional.empty());
        assertEquals("Check if the list of collidable of the cell is still 2, even though i have added a new item in ",
                2, field.getCell(new Point(1, 1)).size());
        assertTrue("Check if the listo of collidable of the cell contains the new item",
                field.getCell(new Point(2, 2)).contains(badApple));

        assertNotSame("Check if the collidable list of the cell is a safe copy of the internal list",
                field.getCell(new Point(1, 1)), field.getCell(new Point(1, 1)));
    }

    /**
     * Used to test if the field removes correctly items.
     */
    @Test
    public void testRemoveItem() {
        final Field field = new FieldImpl(new Point(10, 10));

        final ItemFactory itemFactory = new ItemFactory(field);
        final Item item = itemFactory.createItem(new Point(1, 1), Apple.class, Optional.empty(), Optional.empty());

        field.addItem(item);
        assertEquals("Check if the size of the item list of the field is 1", 1, field.getItems().size());
        assertTrue("Check if the field removes the item successfully", field.removeItem(item));
        assertEquals("Check if the size of the item list of the field is now 0", 0, field.getItems().size());
        assertFalse("Check if the field can not remove the same item twice", field.removeItem(item));
        assertEquals("Check it the size of the item list of the field is still 0", 0, field.getItems().size());
    }

    /**
     * Used to test if the field return all the items present in it correctly.
     */
    @Test
    public void testGetItem() {
        final Field field = new FieldImpl(new Point(10, 10));
        final ItemFactory itemFactory = new ItemFactory(field);
        final Item item = itemFactory.createItem(new Point(1, 1), Apple.class, Optional.empty(), Optional.empty());

        field.addItem(item);
        assertTrue("Check if the list of the item of the field contains the item just added", field.getItems().contains(item));

        final Item badApple = itemFactory.createItem(new Point(2, 2), BadApple.class, Optional.empty(), Optional.empty());
        field.addItem(badApple);
        assertEquals("Check if the size of the list of the item of the field is 2", 2, field.getItems().size());
        assertTrue("Check if the list of the item of the field contais the first item added", field.getItems().contains(item));
        assertTrue("Check if the list of the item of the field contains the second item added", field.getItems().contains(badApple));

        field.removeItem(badApple);
        assertEquals("Check if the size of the list of the item of the field is now 1", 1, field.getItems().size());
        assertFalse("Check if the list of the field does not contains the item anymore", field.getItems().contains(badApple));

        assertNotSame("Check if the item list of the field is a safe copy of the internal list", field.getItems(), field.getItems());
    }

    private boolean checkIllegalArgumentFieldDimension(final Point point) {
        try {
            new FieldImpl(point);
            } catch (IllegalArgumentException e) {
                return true;
            }
        return false;
    }

}
