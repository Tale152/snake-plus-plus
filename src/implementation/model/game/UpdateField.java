package implementation.model.game;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import design.model.game.*;
import design.model.game.GameRules.ItemRule;
import implementation.model.game.items.*;

public class UpdateField {
	
	private  UpdateField() {}
	
	public static void updateField(Field field, long gameTime, List<Item> differences, GameRules gameRules, ItemCounter itemCounter){
		expireItems(field, gameTime, itemCounter, differences);
		spawnItems(field, gameTime, itemCounter, differences, gameRules);
	}
	
	private static void expireItems(Field field, long currentTime, ItemCounter itemCounter, List<Item> differences) {
		for (Item item : field.getItems()) {
			if (hasToExpire(item, currentTime)) {
				itemCounter.applyQuantity(item.getClass(), -1);
				field.removeItem(item);
				differences.add(item);
			}
		}
	}
	
	private static boolean hasToExpire(Item item, long currentTime) {
		return item.getDuration().isPresent() && item.getDuration().get() <= currentTime;
	}
	
	private static void spawnItems(Field field, long currentTime, ItemCounter itemCounter, List<Item> differences, GameRules gameRules) {
		for (ItemRule rule : gameRules.getItemRules()) {
			if (canSpawn(itemCounter, rule, currentTime)) { 
				tryToSpawn(itemCounter, rule, currentTime, differences, field);
			}
		}
	}
	
	private static boolean canSpawn(ItemCounter itemCounter, ItemRule itemRule, long currentTime) {
		return itemCounter.getLastSpawnAttempt(itemRule.getItemClass()) + itemRule.getSpawnDelta() >= currentTime &&
				!(itemRule.getItemClass().equals(BodyPart.class) || itemRule.getItemClass().equals(Wall.class));
	}
	
	private static void tryToSpawn(ItemCounter itemCounter, ItemRule rule, long currentTime, List<Item> differences, Field field) {
		int totCells = field.getHeight() * field.getWidth();
		for (int i =  itemCounter.getCounter(rule.getItemClass()); i < rule.getMax() && itemCounter.getTotal() < totCells; ++i){
			if (isLucky(rule)) {
				spawn(rule, itemCounter, currentTime, differences, field);
			}
		}
		itemCounter.setLastSpawnAttempt(rule.getItemClass(), itemCounter.getLastSpawnAttempt(rule.getItemClass()) + rule.getSpawnDelta());
	}
	
	private static boolean isLucky(ItemRule rule) {
		return new Random().nextDouble() <= rule.getSpawnChance();
	}
	
	private static void spawn(ItemRule rule, ItemCounter itemCounter, long currentTime, List<Item> differences, Field field) {
		try {
			Method methodCreate = ItemFactory.class.getMethod("create" + rule.getItemClass().getSimpleName(), Point.class, Optional.class, Optional.class);
			Item item = (Item)methodCreate.invoke(null, generatePoint(field), rule.getItemDuration(), rule.getEffectDuration());
			differences.add(item);
			itemCounter.applyQuantity(rule.getItemClass(), 1);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	private static Point generatePoint(Field field) {
		Random r = new Random();
		while (true) {
			Point point = new Point(r.nextInt(field.getWidth()), r.nextInt(field.getHeight()));
			if (!field.getCell(point).isPresent()) {
				return point;
			}
		}
	}
	
}
