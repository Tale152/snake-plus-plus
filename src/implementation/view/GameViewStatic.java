package implementation.view;

import java.awt.*;
import java.util.List;
import java.util.Map.Entry;
import design.view.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class GameViewStatic{
	
	private static final double MIN_HUD_PERCENTAGE = 0.1;
	private static final double DELTA_HUD_PERCENTAGE = 0.005;
	private static final double HUD_ERROR_PERCENTAGE = 0.5;
	
	protected static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	
	protected static final BorderPane ROOT = new BorderPane();
	
	protected static final Canvas TOP_CANVAS = new Canvas();
    protected static final GraphicsContext TOP_GRAPHICS_CONTEXT = TOP_CANVAS.getGraphicsContext2D();
    
    protected static final Canvas BOTTOM_CANVAS = new Canvas();
    protected static final GraphicsContext BOTTOM_GRAPHICS_CONTEXT = BOTTOM_CANVAS.getGraphicsContext2D();
    
    protected static final Canvas LEFT_CANVAS = new Canvas();
    protected static final GraphicsContext LEFT_GRAPHICS_CONTEXT = LEFT_CANVAS.getGraphicsContext2D();
    
    protected static final Canvas RIGHT_CANVAS = new Canvas();
    protected static final GraphicsContext RIGHT_GRAPHICS_CONTEXT = RIGHT_CANVAS.getGraphicsContext2D();
    
    protected static final Canvas FIELD_CANVAS = new Canvas();
    protected static final GraphicsContext FIELD_GRAPHICS_CONTEXT = FIELD_CANVAS.getGraphicsContext2D();
    
	protected static void resizeCanvas(Canvas canvas, double width, double height) {
    	canvas.setHeight(height);
    	canvas.setWidth(width);
    }
	
    protected static void drawBg(GraphicsContext gc, Canvas canvas, Image bg) {
    	gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.drawImage(bg, 0, 0, canvas.getWidth(), canvas.getHeight());
    }
    
    protected static void drawField(ResourcesLoader loader, GameField field, GraphicsContext fieldGC, double spriteLen, int nPlayer) {
    	for (Entry<Point, Sprite> entry : field.getItemSprites().entrySet()) {
    		drawSprite(fieldGC, (Image) entry.getValue().getSprite(), entry.getKey(), spriteLen);
    	}
    	for (int i = 0; i < nPlayer; i++) {
    		for (Entry<Point, List<Sprite>> entry : field.getSnakeSprites(i).entrySet()) {
    			for (Sprite sprite : entry.getValue()) {
    				drawSprite(fieldGC, (Image) sprite.getSprite(), entry.getKey(), spriteLen);
    			}
    		}
    	}
    }
    
    private static void drawSprite(GraphicsContext fieldGC, Image sprite, Point point, double spriteLen) {
    	fieldGC.drawImage(sprite, point.x * spriteLen, point.y * spriteLen, spriteLen, spriteLen);
    }
    
    protected static double calculateHudPercentage(int nCellWidth, int nCellHeight) {
		double percentage = MIN_HUD_PERCENTAGE;
		while(true) {
			if (percentage >= HUD_ERROR_PERCENTAGE) {
				throw new IllegalStateException("Cannot size screen with this nCellWidth and nCellHeight");
			}
			double hudHeight = SCREEN_SIZE.getHeight() * percentage;
			double fieldHeight = SCREEN_SIZE.getHeight() - (hudHeight * 2);
			double cellSize = fieldHeight / nCellHeight;
			if (cellSize * nCellWidth < SCREEN_SIZE.getWidth()) {
				break;
			}
			else {
				percentage += DELTA_HUD_PERCENTAGE;
			}
		}
		return percentage;
	}
    
}
