package implementation.view.game;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * This extension of a javafx's Pane create a pane that contains everything that is needed into the gui of a game.
 * That includes:
 * <ul>
 * <li> Canvas and GraphicContext of the hud's background </li>
 * <li> Canvas and GrapicContext of the hud's top part </li>
 * <li> Canvas and GraphicContext of the hud's bottom part </li>
 * <li> Canvas and GraphicContext of the field </li>
 * </ul>
 * Everything is encapsulated into a BorderPane
 */
public class BackgroundPane extends Pane {

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

    /**
     * @param hudPercentage the percentage of the screen that the top and bottom hud have to occupy
     * (example: specifying 0.1 means that top hud will occupy 10% and also bottm hud will occupy the same)
     * @param nCellWidth number of cells composing field's width 
     * @param nCellHeight number of cells composing field's height
     */
    public BackgroundPane(final double hudPercentage, final int nCellWidth, final int nCellHeight) {
        super();
        spriteSize = 0;
        this.getChildren().add(backgoundCanvas);
        this.getChildren().add(borderPane);
        borderPane.setCenter(fieldCanvas);
        borderPane.setTop(topCanvas);
        borderPane.setBottom(bottomCanvas);

        this.heightProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(final ObservableValue<? extends Object> observable, final Object oldValue, final Object newValue) {
                backgoundCanvas.setHeight((double) newValue);
                topCanvas.setHeight((double) newValue * hudPercentage);
                bottomCanvas.setHeight(topCanvas.getHeight());
                fieldCanvas.setHeight((double) newValue - topCanvas.getHeight() - bottomCanvas.getHeight());
                spriteSize = fieldCanvas.getHeight() / nCellHeight;
                fieldCanvas.setWidth(spriteSize * nCellWidth); 
            }
        });
        this.widthProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(final ObservableValue<? extends Object> observable, final Object oldValue, final Object newValue) {
                backgoundCanvas.setWidth((double) newValue);
                topCanvas.setWidth((double) newValue);
                bottomCanvas.setWidth(topCanvas.getWidth());
                fieldCanvas.setWidth(spriteSize * nCellWidth); 
            }
        });
    }

    /**
     * @return the size of a sprite (a sprite is square, so this represents both height and width)
     */
    public double getSpriteSize() {
        return spriteSize;
    }

    /**
     * @return the GraphicsContext of the backround (the hud one)
     */
    public GraphicsContext getBackgroundGraphicsContext() {
        return backgroundGraphicsContext;
    }

    /**
     * @return the canvas of the background (the hud one)
     */
    public final Canvas getBackgroundCanvas() {
        return backgoundCanvas;
    }

    /**
     * 
     * @return the GraphicsContext contained into the game's field
     */
    public final GraphicsContext getFieldGraphicsContext() {
        return fieldGraphicsContext;
    }

    /**
     * @return the canvas contained into the game's field
     */
    public final Canvas getFieldCanvas() {
        return fieldCanvas;
    }

    /**
     * @return the graphic context of the top part of hud
     */
    public final GraphicsContext getTopGraphicsContext() {
        return topGraphicsContext;
    }

    /**
     * @return the graphic context of the bottom part of hud
     */
    public final GraphicsContext getBottomGraphicsContext() {
        return bottomGraphicsContext;
    }

}
