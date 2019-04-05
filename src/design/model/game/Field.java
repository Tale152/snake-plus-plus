package design.model.game;

import java.awt.Point;
import java.util.List;
import java.util.Optional;

public interface Field{
	
	/**
	 * 
	 * @return 
	 */
	public int getWidth();

	/**
	 * 
	 * @return 
	 */
	public int getHeight();

	/**
	 * 
	 * @return 
	 */
	public List<Item> getItems();

	/**
	 * 
	 * @return 
	 * @param item 
	 */
	public boolean removeItem(Item item);

	/**
	 * 
	 * @return 
	 * @param item 
	 * @throws IllegalStateException 
	 */
	public boolean addItem(Item item) throws IllegalStateException;

	/**
	 * 
	 * @return
	 * @param point
	 */
	public Optional<List<Item>> getCell(Point point);
	
}
