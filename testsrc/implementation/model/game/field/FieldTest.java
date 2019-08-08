package implementation.model.game.field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

public class FieldTest {

	@Test
	public void testInit() {
		Field field;
		
		try{
			field = new FieldImpl(null);
            fail("field dimensions can not be null");
        } catch (NullPointerException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		try{
			field = new FieldImpl(new Point(0,0));
            fail("field dimensions can not be 0,0");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		try{
			field = new FieldImpl(new Point(10,0));
            fail("y axis can not be 0");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		try{
			field = new FieldImpl(new Point(0,10));
            fail("x axis can not be 0");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		try{
			field = new FieldImpl(new Point(-2,-2));
            fail("field dimensions can not be negative");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		try{
			field = new FieldImpl(new Point(-2,0));
            fail("x axis can not be negative");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		try{
			field = new FieldImpl(new Point(0,-2));
            fail("y axeis can not be negative");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		
		field = new FieldImpl(new Point(10,5));
		assertEquals(5, field.getHeight());
		assertEquals(10, field.getWidth());
	}
	
	@Test
	public void testAddItems() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		Item item;
		try{
			field.addItem(null);
            fail("item can not be null");
        } catch (NullPointerException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		try{
			item = itemFactory.createItem(new Point(10,0), Apple.class, Optional.empty(), Optional.empty());
			field.addItem(item);
            fail("Item's coord can not be out of boundaries");
        } catch (IllegalStateException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		
		item = itemFactory.createItem(new Point(5,5), Apple.class, Optional.empty(), Optional.empty());
		field.addItem(item);
		assertEquals(field.getItems().get(0), item);
		field.addItem(item);
		assertEquals(field.getItems().size(), 1);
		
		Item beer = itemFactory.createItem(new Point(5,6), Beer.class, Optional.empty(), Optional.empty());
		field.addItem(beer);
		assertEquals(field.getItems().size(), 2);
		assertTrue(field.getItems().contains(item));
		assertTrue(field.getItems().contains(beer));
	}
	
	@Test
	public void testGetCell() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		try{
			field.getCell(null);
            fail("point can not be null");
        } catch (NullPointerException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		try{
			field.getCell(new Point(10,0));
            fail("Cell's coord can not be out of boundaries");
        } catch (IllegalArgumentException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		assertEquals(0, field.getCell(new Point(1,1)).size());
		Item item = itemFactory.createItem(new Point(1,1), Beer.class, Optional.empty(), Optional.empty());
		assertEquals(1, field.getCell(new Point(1,1)).size());
		assertTrue(field.getCell(new Point(1,1)).contains(item));
		
		Item beer = itemFactory.createItem(new Point(1,1), Beer.class, Optional.empty(), Optional.empty());
		assertEquals(2, field.getCell(new Point(1,1)).size());
		assertTrue(field.getCell(new Point(1,1)).contains(item));
		assertTrue(field.getCell(new Point(1,1)).contains(beer));
		
		Item badApple = itemFactory.createItem(new Point(2,2), BadApple.class, Optional.empty(), Optional.empty());
		assertEquals(2, field.getCell(new Point(1,1)).size());
		assertTrue(field.getCell(new Point(2,2)).contains(badApple));
		
		assertTrue(field.getCell(new Point(1,1)) != field.getCell(new Point(1,1)));
	}
	
	@Test
	public void testRemoveItem() {
		Field field = new FieldImpl(new Point(10,10));
		try{
			field.removeItem(null);
            fail("Item can not be null");
        } catch (NullPointerException e){
        } catch (Exception e){
            fail("wrong exception");
        }
		ItemFactory itemFactory = new ItemFactory(field);
		Item item = itemFactory.createItem(new Point(1,1), Apple.class, Optional.empty(), Optional.empty());
		
		field.addItem(item);
		assertEquals(1, field.getItems().size());
		assertTrue(field.removeItem(item));
		assertEquals(0, field.getItems().size());
		assertFalse(field.removeItem(item));
		assertEquals(0, field.getItems().size());
	}
	
	@Test
	public void testGetItem() {
		Field field = new FieldImpl(new Point(10,10));
		ItemFactory itemFactory = new ItemFactory(field);
		Item item = itemFactory.createItem(new Point(1,1), Apple.class, Optional.empty(), Optional.empty());
		
		field.addItem(item);
		assertTrue(field.getItems().contains(item));
		
		Item badApple = itemFactory.createItem(new Point(2,2), BadApple.class, Optional.empty(), Optional.empty());
		field.addItem(badApple);
		assertEquals(2, field.getItems().size());
		assertTrue(field.getItems().contains(badApple));
		assertTrue(field.getItems().contains(item));
		
		field.removeItem(badApple);
		assertEquals(1, field.getItems().size());
		assertFalse(field.getItems().contains(badApple));
	
		assertTrue(field.getItems() != field.getItems());
	}
		
}
