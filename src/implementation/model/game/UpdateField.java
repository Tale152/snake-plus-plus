package implementation.model.game;

import java.util.*;
import design.model.game.*;
import implementation.model.game.items.BodyPart;

public class UpdateField {

	private  UpdateField() {}
	
	public static void updateField(Field field, long gameTime, List<Item> differences, GameRules gameRules, Map<Class<? extends Item>, Integer> itemCounter){
		updateFieldFromSnakeDifferences(field, differences);
		
	}
	
	private static void updateFieldFromSnakeDifferences(Field field, List<Item> differences) {
		for (Item diff : differences) {
			if (diff.getClass() == BodyPart.class) {
				if (!field.removeItem(diff)) {
					field.addItem(diff);
				}
			}
			else {
				field.removeItem(diff);
			}
		}
	}
	
}
