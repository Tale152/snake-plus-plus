package implementation.model.game;

import java.util.*;
import design.model.game.*;

public class GameFieldStatic {

	private  GameFieldStatic() {}
	
	protected static List<Item> updateItems(Field field, long gameTime){
		List<Item> differences = new ArrayList<>();
		for (Item item : field.getItems()) {
			if (item.getDuration().isPresent() && item.getDuration().get() <= gameTime) {
				field.removeItem(item);
			}
			//TODO spawn items
		}
		return differences;
	}
	
	
}
