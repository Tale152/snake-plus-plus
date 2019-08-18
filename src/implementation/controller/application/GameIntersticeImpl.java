package implementation.controller.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import design.controller.application.GameEndReason;
import design.controller.application.GameInterstice;
import design.controller.game.GameController;
import design.controller.game.GameLoader;
import design.model.game.GameModel;
import implementation.controller.PathUtils;
import implementation.view.application.Main;
import implementation.view.game.GameViewImpl;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * @see GameInterstice
 * @author Nicola Orlando
 */
public class GameIntersticeImpl implements GameInterstice {

    private enum GameMode {
        CLASSIC,
        WORLD;
    }

    private static final String WIN = "You won!";
    private static final String LOSS = "You lost!";
    private static final String ERROR = "I am error";

    private static final String[][] REASONS = {
            {"", ""},
            {WIN, "You reached the required length!"},
            {WIN, "You survived until the end!"},
            {WIN, "You reached the required score!"},

            {LOSS, "You died!"},
            {LOSS, "You failed to complete the objective in time!"},

            {ERROR, ".: :;"}
    };

    private static final String START_WORLD_BUTTON = "Start World";
    private static final String WON_LEVEL_BUTTON = "Next Level";
    private static final String LOST_LEVEL_BUTTON = "Retry Level";

    private String skinPackPath;
    private final List<GameLoader> levels;
    private int currentLevel;
    private int playerNumber = 1;
    private MediaPlayer mediaPlayer;
    private GameMode gameMode;

    private Node root;
    @FXML
    private Label titleLabel;
    @FXML
    private Label reasonLabel;
    @FXML
    private Label nextLevelNameLabel;
    @FXML
    private Label nextLevelDescriptionLabel;
    @FXML
    private Button nextLevelButton;
    @FXML
    private Button mainMenuButton;
    @FXML
    private VBox layoutBox;
    /** Instantiate a GameInterstice for Classic mode.
     * 
     * @param level The level to load.
     * @param skinPackPath The path to the skin pack to be loaded. Needed to instantiate a GameView.
     * @param players The number of players in the game.
     * @throws IOException 
     */
    public GameIntersticeImpl(final GameLoader level, final String skinPackPath, final int players) throws IOException {
        this(Arrays.asList(level), skinPackPath);
        playerNumber = players;
    }

    /** Instantiate a GameInterstice for World mode, providing the levels contained by the world.
     * 
     * @param levels List of levels contained in the world.
     * @param skinPackPath The path to the skin pack to be loaded. Needed to instantiate a GameView.
     * @throws IOException 
     */
    public GameIntersticeImpl(final List<GameLoader> levels, final String skinPackPath) throws IOException {
        currentLevel = 0;
        this.levels = levels;
        this.skinPackPath = skinPackPath;
        gameMode = (levels.size() == 1) ? GameMode.CLASSIC : GameMode.WORLD;
    }

    @Override
    public final void setGameEndReason(final GameEndReason reason) throws IOException {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("/implementation/view/application/GameIntersticeView.fxml"));
        loader.setController(this);
        root = loader.load();

        final int n = reason.ordinal();
        final String[] reasonStrings = REASONS[n];
        Platform.runLater(() -> {
            titleLabel.setText(reasonStrings[0]);
            reasonLabel.setText(reasonStrings[1]);
        });
        switch (reason) {
        case START:
            gameStart();
            break;
        case WON_TIME:
        case WON_SCORE:
        case WON_LENGTH:
            gameWon();
            break;
        case LOST_TIME:
        case LOST_DEATH:
            gameLost();
            break;
        case ERROR:
        default:
            iAmError();
        }
    }

    private void gameStart() {
        layoutBox.getChildren().remove(titleLabel);
        layoutBox.getChildren().remove(reasonLabel);
        populateNextLevelLabels();
        nextLevelButton.setText(START_WORLD_BUTTON);
    }

    private void gameWon() {
        currentLevel += 1;
        nextLevelButton.setText(WON_LEVEL_BUTTON);
        if (currentLevel == levels.size()) {
            worldEnd();
        } else {
            populateNextLevelLabels();
        }
    }

    private void worldEnd() {
        nextLevelButton.setDisable(true);
        nextLevelButton.setVisible(false);
        hideNextLabels();
    }

    private void gameLost() {
        switch (gameMode) {
        case CLASSIC:
            nextLevelButton.setText(LOST_LEVEL_BUTTON);
            break;
        case WORLD:
            worldEnd();
            break;
        default:
            break;
        }

        hideNextLabels();
    }

    private void iAmError() {
        gameLost();
    }

    private void hideNextLabels() {
        layoutBox.getChildren().remove(nextLevelNameLabel);
        layoutBox.getChildren().remove(nextLevelDescriptionLabel);
    }

    private void populateNextLevelLabels() {
        nextLevelNameLabel.setText("Next level: " + levels.get(currentLevel).getLevelName());
        nextLevelDescriptionLabel.setText(levels.get(currentLevel).getLevelDescription());
    }

    @Override
    public final void showInterstice() {
        Platform.runLater(() -> {
            Main.getScene().setRoot((Parent) root);
        });
    }

    @Override
    @FXML
    public final void nextLevel() throws FileNotFoundException, IOException {
        if (currentLevel == 0) {
            MainMenuControllerImpl.stopMusic();
        }
        this.stopMusic();

        startLevelMusic();
        levels.get(currentLevel).reset();
        final GameModel model = levels.get(currentLevel).getGameModel();
        for (int i = playerNumber; i < levels.get(currentLevel).getMaxPlayers(); i++) {
            model.getField().removeSnake(playerNumber);
        }
        final GameController gameController = 
                new GameViewImpl(Main.getScene(), this.skinPackPath, model).getGameController();
        gameController.setInterstice(this);
    }

    /**
     * Method called to display the main menu. Only to be used by FXML.
     */
    @FXML
    public void goToMainMenu() {
        stopMusic();
        final FXMLLoader mainMenu = new FXMLLoader(
                getClass().getResource("/implementation/view/application/MainMenuView.fxml"));
        try {
            Main.getScene().setRoot(mainMenu.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopMusic() {
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.stop();
    }

    private void startLevelMusic() {
        final File[] themesList = new File(PathUtils.THEMES).listFiles();
        if (themesList == null) {
            System.err.println("Themes folder not found. Aborting music.");
            return;
        }
        final int nFile = themesList.length;
        final Random randomGenerator = new Random();
        final int randomInt = randomGenerator.nextInt(nFile - 1);
        final Media media = new Media(new File(
                PathUtils.THEMES + PathUtils.GAME_THEMES_PREFIX + Integer.toString(randomInt) 
                + PathUtils.GAME_THEMES_TYPE).toURI().toString()); 
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

}
