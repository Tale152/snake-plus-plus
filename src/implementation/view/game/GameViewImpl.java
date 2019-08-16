package implementation.view.game;

import design.controller.game.GameController;
import design.model.game.GameModel;
import design.model.game.Snake;
import design.view.game.GameField;
import design.view.game.GameHud;
import design.view.game.GameView;
import design.view.game.ResourcesLoader;
import design.view.game.Sprite;
import implementation.controller.game.GameControllerImpl;
import implementation.controller.game.InputEventFX;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * @see GameView
 */
public class GameViewImpl implements GameView {

    private static final double PLAYER_HEIGHT_PERCENTAGE = 0.9;
    private static final double TIME_HEIGHT_PERCENTAGE = 0.9;
    private static final double MIN_HUD_PERCENTAGE = 0.1;
    private static final double DELTA_HUD_PERCENTAGE = 0.005;
    private static final double HUD_ERROR_PERCENTAGE = 0.5;
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    private static final double SPACING_BETWEEN_HUD_SPRITES = 0.25;
    private static final double BOTTOM_HUD_SUB_SPACES = 5;
    private static final double SCORE_SPACING_Y_POSITIONING = 2.5;
    private static final double NAMES_SPACING_Y_POSITIONING = 4;
    private static final double HUD_SPRITES_SPACING_Y_POSITIONING = 1.5;

    private final GameHud hud;
    private final GameField field;
    private final double hudPercentage;
    private final ResourcesLoader loader;

    private final BackgroundPane root;
    private double labelY;
    private double timeLabelX;
    private double playerSpacingX;
    private double scoreSpacingY;
    private double namesSpacingY;
    private double hudSpritesSpacingY;
    private double hudSpritesDimension;

    private Font timeFont;
    private Font playerFont;

    private final AnimationTimer animationTimer;
    private final GameController gameController;

    /**
     * @param scene where displaying the game view
     * @param resourcesPath where to find sprite and graphical resources 
     * @param gameModel containing the game to play
     * @throws FileNotFoundException if there are problems regarding resourcesPath
     * @throws IOException if there are problems regarding resourcesPath
     */
    public GameViewImpl(final Scene scene, final String resourcesPath, final GameModel gameModel) throws FileNotFoundException, IOException {
        final List<String> playerNames = new ArrayList<>();
        for (final Snake s : gameModel.getField().getSnakes()) {
            playerNames.add(s.getPlayer().getName());
        }
        loader = new ResourcesLoaderFromFile(resourcesPath, gameModel.getField().getWidth(), gameModel.getField().getHeight());
        hudPercentage = calculateHudPercentage(gameModel.getField().getWidth(), gameModel.getField().getHeight());
        hud = new GameHudImpl(playerNames.size(), loader);
        field = new GameFieldImpl(playerNames.size(), loader);
        root = initRoot(scene, playerNames.size(), gameModel.getField().getWidth(), gameModel.getField().getHeight());
        animationTimer = initAnimationTimer(playerNames.size());
        gameController = initGameController(scene, gameModel);
        setWidthProperties(playerNames.size());
        setHeightProperies();
    }

    @Override
    public final GameHud getHUD() {
        return hud;
    }

    @Override
    public final GameField getField() {
        return field;
    }

    @Override
    public final void startRendering() {
        animationTimer.start();
    }

    @Override
    public final void stopRendering() {
        animationTimer.stop();
    }

    private AnimationTimer initAnimationTimer(final int nPlayers) {
        return new AnimationTimer() {
            public void handle(final long currentNanoTime) {
                drawBg(root.getBackgroundGraphicsContext(), root.getBackgroundCanvas(), (Image) loader.getHudBackground().getBackground());
                drawBg(root.getFieldGraphicsContext(), root.getFieldCanvas(), (Image) loader.getFieldBackground().getBackground());
                drawField(field, root.getFieldGraphicsContext(), root.getSpriteSize(), nPlayers);
                drawHud(nPlayers);
            }
        };
    }

    private BackgroundPane initRoot(final Scene scene, final int nPlayers, 
            final int nCellWidth, final int nCellHeight) throws FileNotFoundException, IOException {
        final BackgroundPane root = new BackgroundPane(hudPercentage, nCellWidth, nCellHeight);
        scene.setRoot(root);
        root.widthProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(final ObservableValue<? extends Object> observable, final Object oldValue, final Object newValue) {
                setWidthProperties(nPlayers);
            }
        });
        root.heightProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(final ObservableValue<? extends Object> observable, final Object oldValue, final Object newValue) {
                setHeightProperies();
            }
        });
        timeFont = new Font(root.getBackgroundGraphicsContext().getFont().getName(), 
                root.getWidth() * hudPercentage * TIME_HEIGHT_PERCENTAGE);
        playerFont = new Font(root.getBackgroundGraphicsContext().getFont().getName(), 
                root.getHeight() * hudPercentage * PLAYER_HEIGHT_PERCENTAGE / 3);
        root.getBackgroundGraphicsContext().setTextAlign(TextAlignment.CENTER);
        root.getBackgroundGraphicsContext().setTextBaseline(VPos.CENTER);
        root.getBackgroundGraphicsContext().setFill(Color.BLACK);
        return root;
    }

    private void setWidthProperties(final int nPlayers) {
        timeLabelX = root.getWidth() / 2; 
        playerSpacingX = root.getWidth() / (nPlayers + 1);
    }

    private void setHeightProperies() {
        labelY = (root.getHeight() * hudPercentage) / 2;
        final double bottomHudSubSpaceHeight = ((root.getHeight() * hudPercentage) / BOTTOM_HUD_SUB_SPACES);
        scoreSpacingY = root.getHeight() - (bottomHudSubSpaceHeight * SCORE_SPACING_Y_POSITIONING);
        namesSpacingY = root.getHeight() - (bottomHudSubSpaceHeight * NAMES_SPACING_Y_POSITIONING);
        hudSpritesSpacingY = root.getHeight() - (bottomHudSubSpaceHeight * HUD_SPRITES_SPACING_Y_POSITIONING);
        hudSpritesDimension = ((root.getHeight() * hudPercentage)) / 4;
        timeFont = new Font(root.getBackgroundGraphicsContext().getFont().getName(), 
                root.getHeight() * hudPercentage * TIME_HEIGHT_PERCENTAGE);
        playerFont = new Font(root.getBackgroundGraphicsContext().getFont().getName(), 
                root.getHeight() * hudPercentage * PLAYER_HEIGHT_PERCENTAGE / 3);
    }

    private GameController initGameController(final Scene scene, final GameModel gameModel) throws IOException {
        final GameController controller = new GameControllerImpl(gameModel, this, loader);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            controller.playerInput(new InputEventFX(key));
        });
        final Thread t  = new Thread(controller);
        t.start();
        return controller;
    }

    private void drawBg(final GraphicsContext gc, final Canvas canvas, final Image bg) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(bg, 0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void drawField(final GameField field, final GraphicsContext fieldGC, 
            final double spriteLen, final int nPlayer) {
        for (final Entry<Point, Sprite> entry : field.getItemSprites().entrySet()) {
            drawSprite(fieldGC, (Image) entry.getValue().getSprite(), entry.getKey(), spriteLen);
        }
        for (final Entry<Point, Sprite> entry : field.getWallSprites().entrySet()) {
            drawSprite(fieldGC, (Image) entry.getValue().getSprite(), entry.getKey(), spriteLen);
        }
        for (int i = 0; i < nPlayer; i++) {
            for (final Entry<Point, List<Sprite>> entry : field.getSnakeSprites(i).entrySet()) {
                for (int j = entry.getValue().size() - 1; 0 <= j; j--) {
                    drawSprite(fieldGC, (Image) entry.getValue().get(j).getSprite(), entry.getKey(), spriteLen);
                }
            }
        }
    }

    private void drawSprite(final GraphicsContext fieldGC, final Image sprite, 
            final Point point, final double spriteLen) {
        fieldGC.drawImage(sprite, point.x * spriteLen, point.y * spriteLen, spriteLen, spriteLen);
    }

    private void drawHud(final int nPlayers) {
        root.getBackgroundGraphicsContext().setFont(timeFont);
        root.getBackgroundGraphicsContext().fillText(hud.getTime(), timeLabelX, labelY);
        root.getBackgroundGraphicsContext().setFont(playerFont);
        for (int i = 0; i < nPlayers; i++) {
            root.getBackgroundGraphicsContext().fillText(hud.getPlayerHUDs().get(i).getName(), 
                    (playerSpacingX * i) + playerSpacingX,
                    namesSpacingY);
            root.getBackgroundGraphicsContext().fillText(hud.getPlayerHUDs().get(i).getScore(), 
                    (playerSpacingX * i) + playerSpacingX,
                    scoreSpacingY);
            drawPlayerHeadAndActiveItems(i);
            if (!hud.getPlayerHUDs().get(i).isAlive()) {
                final Double deadDim = root.getHeight() * hudPercentage;
                root.getBackgroundGraphicsContext().drawImage((Image) loader.getDeadPlayerIndicator().getSprite(),
                        (playerSpacingX * i) + playerSpacingX - (deadDim / 2), root.getHeight() - deadDim, 
                        deadDim, deadDim);
            }
        }
    }

    private void drawPlayerHeadAndActiveItems(final int nPlayer) {
        final List<Sprite> spriteList = hud.getPlayerHUDs().get(nPlayer).getSpriteList();
        final double totalSpaceSprites = ((spriteList.size() * hudSpritesDimension) + ((hudSpritesDimension * SPACING_BETWEEN_HUD_SPRITES) * (spriteList.size() - 1)));
        final double leftCorner = (playerSpacingX * nPlayer) + playerSpacingX - (totalSpaceSprites / 2);
        int i = 0;
        for (final Sprite sprite : spriteList) {
            final double xSpacing = leftCorner + (hudSpritesDimension * (1 + SPACING_BETWEEN_HUD_SPRITES) * i);
            root.getBackgroundGraphicsContext().drawImage((Image) sprite.getSprite(), xSpacing, hudSpritesSpacingY, 
                    hudSpritesDimension, hudSpritesDimension);
            ++i;
        }
    }

    private double calculateHudPercentage(final int nCellWidth, final int nCellHeight) {
        double percentage = MIN_HUD_PERCENTAGE;
        while (true) {
            if (percentage >= HUD_ERROR_PERCENTAGE) {
                throw new IllegalStateException("Cannot size screen with this nCellWidth and nCellHeight");
            }
            final double hudHeight = SCREEN_SIZE.getHeight() * percentage;
            final double fieldHeight = SCREEN_SIZE.getHeight() - (hudHeight * 2);
            final double cellSize = fieldHeight / nCellHeight;
            if (cellSize * nCellWidth < SCREEN_SIZE.getWidth()) {
                break;
            } else {
                percentage += DELTA_HUD_PERCENTAGE;
            }
        }
        return percentage;
    }

    @Override
    public final GameController getGameController() {
        return gameController;
    }

}
