package implementation.view;

import java.io.*;
import javafx.beans.value.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.*;

public class BackgroundPane extends Pane{

	private final Canvas backgoundCanvas = new Canvas();
	private final GraphicsContext backgroundGraphicsContext = backgoundCanvas.getGraphicsContext2D();
	
	private final BorderPane borderPane = new BorderPane();
	
	private final Canvas topCanvas = new Canvas();
	private final GraphicsContext topGraphicsContext = topCanvas.getGraphicsContext2D();
	
	private final Canvas fieldCanvas = new Canvas();
	private final GraphicsContext fieldGraphicsContext = fieldCanvas.getGraphicsContext2D();
	
	private final Canvas bottomCanvas = new Canvas();
	private final GraphicsContext bottomGraphicsContext = bottomCanvas.getGraphicsContext2D();
	
	private double spriteSize;
		
	public BackgroundPane(double hudPercentage, int nCellWidth, int nCellHeight) throws FileNotFoundException, IOException {
		super();
		spriteSize = 0;
		this.getChildren().add(backgoundCanvas);
		this.getChildren().add(borderPane);
		borderPane.setCenter(fieldCanvas);
		borderPane.setTop(topCanvas);
		borderPane.setBottom(bottomCanvas);
		
		this.heightProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				backgoundCanvas.setHeight((double)newValue);
				topCanvas.setHeight((double)newValue * hudPercentage);
				bottomCanvas.setHeight(topCanvas.getHeight());
				fieldCanvas.setHeight((double) newValue - topCanvas.getHeight() - bottomCanvas.getHeight());
				spriteSize = fieldCanvas.getHeight() / nCellHeight;
				fieldCanvas.setWidth(spriteSize * nCellWidth); 
			}
		});
		this.widthProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				backgoundCanvas.setWidth((double)newValue);				
				topCanvas.setWidth((double)newValue);
				bottomCanvas.setWidth(topCanvas.getWidth());
				fieldCanvas.setWidth(spriteSize * nCellWidth); 
			}
		});
	}
	
	public double getSpriteSize() {
		return spriteSize;
	}
	
	public GraphicsContext getBackgroundGraphicsContext() {
		return backgroundGraphicsContext;
	}
	
	public Canvas getBackgroundCanvas() {
		return backgoundCanvas;
	}
	
	public GraphicsContext getFieldGraphicsContext() {
		return fieldGraphicsContext;
	}
	
	public Canvas getFieldCanvas() {
		return fieldCanvas;
	}
	
	public GraphicsContext getTopGraphicsContext() {
		return topGraphicsContext;
	}
	
	public GraphicsContext getBottomGraphicsContext() {
		return bottomGraphicsContext;
	}
	
}
