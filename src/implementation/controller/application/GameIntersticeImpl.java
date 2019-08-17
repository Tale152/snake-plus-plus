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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GameIntersticeImpl implements GameInterstice {

    private static final String WIN = "You won!";
    private static final String LOSS = "You lost!";
    private static final String ERROR = "I am error";

    private static final String[][] REASONS = {
                    {WIN, "You reached the required length!"},
                    {WIN, "You survived until the end!"},
                    {WIN, "You reached the required score!"},

                    {LOSS, "You died!"},
                    {LOSS, "You failed to complete the objective in time!"},

                    {ERROR, ".: :;"}
    };

    private String skinPackPath;
    private final List<GameLoader> levels;
    private int currentLevel;
    private int playerNumber;
    private MediaPlayer mediaPlayer;

    private final Node root;
    @FXML
    private Label titleLabel;
    @FXML
    private Label reasonLabel;
    @FXML
    private Button nextLevelButton;
    @FXML
    private Button mainMenuButton;
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
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("/implementation/view/application/GameIntersticeView.fxml"));
        loader.setController(this);
        root = loader.load();

        currentLevel = 0;
        this.levels = levels;
        this.skinPackPath = skinPackPath;
        playerNumber = 1;
    }

    @FXML
    public void inizialize() {
    }

    @Override
    public final void setGameEndReason(final GameEndReason reason) {
        final int n = reason.ordinal();
        final String[] reasonStrings = REASONS[n];
        Platform.runLater(() -> {
            titleLabel.setText(reasonStrings[0]);
            reasonLabel.setText(reasonStrings[1]);
        });

        currentLevel += 1;
        if (n <= 2) {
            gameWon();
        } else if (n <= 4) {
            gameLost();
        } else {
            iAmError();
        }
    }

    private void gameWon() {
        if (currentLevel == levels.size()) {
            gameLost();
            return;
        }
        mainMenuButton.setDisable(true);
        mainMenuButton.setVisible(false);
        nextLevelButton.setDisable(false);
        nextLevelButton.setVisible(true);
    }

    private void gameLost() {
        mainMenuButton.setDisable(false);
        mainMenuButton.setVisible(true);
        nextLevelButton.setDisable(true);
        nextLevelButton.setVisible(false);
    }

    private void iAmError() {
        mainMenuButton.setDisable(false);
        nextLevelButton.setDisable(true);
        nextLevelButton.setDisable(true);
        nextLevelButton.setVisible(false);
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
        } else {
            this.stopMusic();
        }
        startLevelMusic();
        final GameModel model = levels.get(currentLevel).getGameModel();
        for (int i = playerNumber; i < levels.get(currentLevel).getMaxPlayers(); i++) {
            model.getField().removeSnake(playerNumber);
        }
        final GameController gameController = 
                new GameViewImpl(Main.getScene(), this.skinPackPath, model).getGameController();
        gameController.setInterstice(this);
    }

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
        mediaPlayer.stop();
    }

    private void startLevelMusic() {
        final int nFile = new File(PathUtils.THEMES).listFiles().length;
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
