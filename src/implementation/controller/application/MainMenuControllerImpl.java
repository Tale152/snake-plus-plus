package implementation.controller.application;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import design.controller.application.MainMenuController;
import design.controller.application.StageSelectionController;
import implementation.controller.PathUtils;
import implementation.view.application.Main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

/**
 * @see MainMenuController
 */
public class MainMenuControllerImpl implements MainMenuController, Initializable {

    private static final String DEFAULT_PACK = "Default Pack";

    @FXML private ImageView classicImageView;
    @FXML private ImageView levelImageView;
    @FXML private AnchorPane root;
    @FXML private MenuButton skinPacks;
    @FXML private ImageView snakeppImageView;

    private static Path skinPackPath;
    private final Map<String, Path> itemButtonMap = new HashMap<>();
    private static final String MAIN_MENU_THEME_PATH = PathUtils.THEMES + "Main_menu_theme.mp3";
    private static MediaPlayer mediaPlayer;

    private static final String CLASSIC_VIEW = "/implementation/view/application/ClassicView.fxml";
    private static final String WORLD_VIEW = "/implementation/view/application/WorldSelectionView.fxml";
    private static final String DESCRIPTION_VIEW = "/implementation/view/application/DescriptionView.fxml";

    @Override
    @FXML
    public final void goToClassicMode() throws IOException {
        goToMode(CLASSIC_VIEW);
    }

    @Override
    @FXML
    public final void goToLevelMode() throws IOException {
        goToMode(WORLD_VIEW);
    }

    @Override
    @FXML
    public final void goToDescriptionMode() throws IOException {
        final FXMLLoader root = new FXMLLoader(getClass().getResource(DESCRIPTION_VIEW));
        try {
            Main.getScene().setRoot(root.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void goToMode(final String fxmlPath) throws IOException {
        final FXMLLoader root = new FXMLLoader(getClass().getResource(fxmlPath));
        Main.getScene().setRoot(root.load());
        final StageSelectionController controller = root.getController();
        controller.setSkinPackPath(skinPackPath);
    }

    @Override
    public final void initialize(final URL arg0, final ResourceBundle arg1) {
        final Path folder = PathUtils.getResourcePath("resources");
        listFiles(folder);
        initializeMenuItem();
        if (mediaPlayer == null) {
            startMusic();
        } else {
            if (!mediaPlayer.getStatus().equals(Status.PLAYING)) {
                startMusic();
            }
        }
        setImage(PathUtils.getResourcePath(PathUtils.MENU).resolve("Snake++" + PathUtils.IMAGE_TYPE), snakeppImageView);
        setImage(PathUtils.getResourcePath(PathUtils.MENU).resolve("Classic" + PathUtils.IMAGE_TYPE), classicImageView);
        setImage(PathUtils.getResourcePath(PathUtils.MENU).resolve("Levels" + PathUtils.IMAGE_TYPE), levelImageView);
    }

    private void startMusic() {
        final Media media = new Media(PathUtils.getResourcePath(MAIN_MENU_THEME_PATH).toUri().toString());
        mediaPlayer = new MediaPlayer(media); 
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    private void setImage(final Path path, final ImageView imageView) {
        imageView.setImage(
                new Image(path.toUri().toString()));
    }

    /**This method read all the directory in the current directory that are put in a map
    *with the path and the name. The first directory is a random pack, used if the default pack
    does not exist. If there are any directory in the folder, the game will stop running.
    */
    private void listFiles(final Path folder) {
        Path randomPack = null;
        try {
            for (final Iterator<Path> iterator = Files.walk(folder,  1).iterator(); iterator.hasNext();) {
                final Path item = iterator.next();
                final Path itemFileName = item.getFileName();
                if (Files.isDirectory(item) && itemFileName != null && !item.equals(folder)) {
                    final String packName = itemFileName.toString().replace("_", " ").replace("/", "");
                    this.itemButtonMap.put(packName, item);
                    if (randomPack == null) {
                        randomPack = item;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("There are problems with directory " + folder);
        }
        if (skinPackPath == null) {
            if (this.itemButtonMap.isEmpty()) {
                throw new RuntimeException("no skin paks found");
            } else if (this.itemButtonMap.containsKey(DEFAULT_PACK)) {
                skinPackPath = this.itemButtonMap.get(DEFAULT_PACK);
            } else {
                skinPackPath = randomPack;
            } 
        }
    }

    /**This method initialize all the menu item in the menu button
    *the name of an item is the name of the directory where there is the skin
    *and when you select a skin the name of the menu button has the name of the skin selected.
    *@author Elisa Tronetti*/
    private void initializeMenuItem() {
        for (final String s : this.itemButtonMap.keySet()) {
            final MenuItem m = new MenuItem(s);
            final EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
                public void handle(final ActionEvent e) { 
                    skinPackPath = itemButtonMap.get(s);
                    skinPacks.setText(m.getText());
                } 
            }; 
            m.setOnAction(event);
            this.skinPacks.getItems().add(m);
        }
    }

    /**
     * stop javafx's music.
     */
    public static final void stopMusic() {
        mediaPlayer.stop();
    }

    @Override
    public final Path getSelectedSkinPack() {
        return skinPackPath;
    }
}
