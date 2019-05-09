package implementation.controller.game;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import design.model.game.Direction;
import design.model.game.Item;
import design.model.game.Snake;

public class GameLoopStatic {

	private final static String PRE_WALL = "Wall_";
	private final static String POST_WALL = ".png";
	private final static String SINGLE_HEAD_UP = "single_head_up.png";
	private final static String SINGLE_HEAD_DOWN = "single_head_down.png";
	private final static String SINGLE_HEAD_LEFT = "single_head_left.png";
	private final static String SINGLE_HEAD_RIGHT = "single_head_right.png";
	
	private final static String PRE_HEAD = "head_";
	private final static String POST_HEAD = ".png";
	
	private final static String PRE_BODY = "body_";
	private final static String POST_BODY = ".png";
	
	private final static String PRE_TAIL = "tail_";
	private final static String POST_TAIL = ".png";
	
	private final static String POST_ITEM = ".png";
	
	
	public static Map<Point, String> computeAllWalls(List<Item> allWalls){
		Map<Point, String> res = new HashMap<>();
		for(Item wall : allWalls) {
			res.put(wall.getPoint(), PRE_WALL + computeWall(wall, allWalls) + POST_WALL);
		}
		return res;
	}
	
	private static String computeWall(Item wall, List<Item> allWalls) {
		
		return isWallNear(wall, allWalls, 0, -1)
		+ isWallNear(wall, allWalls, 1, 0)
		+ isWallNear(wall, allWalls, 0, 1)
		+ isWallNear(wall, allWalls, -1, 0);
	}
	
	
	private static String isWallNear(Item wall, List<Item> allWalls, int dX, int dY) {
		
		return allWalls.stream().anyMatch(w -> {
			return w.getPoint().equals(new Point(wall.getPoint().x+dX, wall.getPoint().y+dY));
		}) ? "1" : "0";		
	}
	
	public static Map<Point, String> computeASnake(Snake snake){
		Map<Point, String> res = new HashMap<>();
		
		if(snake.getBodyParts().size() == 1) {
			res.put(snake.getBodyParts().get(0).getPoint(), computeHead(snake.getBodyParts().get(0), Optional.empty(), snake.getProperties().getDirection().getDirection()));
		} else {
			for(int i = 1; i < snake.getBodyParts().size() -1; i++) {
				res.put(snake.getBodyParts().get(i).getPoint(), 
						computeBody(snake.getBodyParts().get(i), snake.getBodyParts().get(i+1), snake.getBodyParts().get(i-1)));
			}
			res.put(snake.getBodyParts().get(snake.getBodyParts().size()-1).getPoint(),
					computeTail(snake.getBodyParts().get(snake.getBodyParts().size()-1), snake.getBodyParts().get(snake.getBodyParts().size()-2)));
		}

		return res;
	}
	
	private static String computeHead(Item head, Optional<Item> next, Direction direction) {
		String res = PRE_HEAD; 
		
		if(next.isPresent()) {
			int index = nextPieceDirection(head, next.get());
			for(int i = 0; i < 4; i++){
				if(i == index) {
					res += "1";
				} else {
					res += "0";
				}	
			}	
		} else {
			switch(direction) {
			case UP : return SINGLE_HEAD_UP; 
			case DOWN : return SINGLE_HEAD_DOWN; 
			case LEFT : return SINGLE_HEAD_LEFT;
			case RIGHT : return SINGLE_HEAD_RIGHT;
			}
			
		}
		return res + POST_HEAD;
	}
	
	
	private static String computeBody(Item bodyPart, Item nextBodyPart, Item prevBodyPart) {
		String res = PRE_BODY; 
		int pre = nextPieceDirection(bodyPart, prevBodyPart);
		int next = nextPieceDirection(bodyPart, nextBodyPart);
	
		for(int i = 0; i < 4; i++){
			if(i == pre || i == next) {
				res += "1";
			} else {
				res += "0";
			}	
		}
		return res + POST_BODY;	
	}
	
	
	private static String computeTail(Item bodyPart, Item prevBodyPart) {
		String res = PRE_TAIL; 
		int pre = nextPieceDirection(bodyPart, prevBodyPart);
		for(int i = 0; i < 4; i++){
			if(i == pre) {
				res += "1";
			} else {
				res += "0";
			}	
		}
		return res + POST_TAIL;	
	}
	
	
	
	private static int nextPieceDirection(Item bodyPart, Item nextBodyPart) {
		int dX = bodyPart.getPoint().x-nextBodyPart.getPoint().x;
		if(dX != 0) {
			if(dX == 1 || dX < -1) {
				return 3;
			} else {
				return 1;
			}
		} 
		int dY = bodyPart.getPoint().y-nextBodyPart.getPoint().y;
		if(dY == 1 || dY < -1) {
			return 0;
		} else {
			return 2;
		}
	}
	
	public static String computeItem(Item item) {
		return item.getClass().getSimpleName() + POST_ITEM;
	}
	
	
	
	
	
	
}
