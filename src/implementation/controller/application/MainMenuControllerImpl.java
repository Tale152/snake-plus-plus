package implementation.controller.application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import design.controller.application.MainMenuController;
import design.controller.application.StageSelectionController;
import implementation.controller.Path;
import implementation.view.application.Main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * @see MainMenuController
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 */
public class MainMenuControllerImpl implements MainMenuController, Initializable {

    private static final String DEFAULT_PACK = "Default Pack";
    private static final double TEXT_PERCENTAGE = 0.4;
    @FXML private Button classic;
    @FXML private Button level;
    @FXML private AnchorPane root;
    @FXML private MenuButton skinPacks;
    @FXML private ImageView snakeppImageView;

    private String skinPackPath;
    private final Map<String, String> itemButtonMap = new HashMap<>();
    private static final String MAIN_MENU_THEME_PATH = Path.THEMES + "Main_menu_theme.mp3";
    private static MediaPlayer mediaPlayer;
    
    private static final String CLASSIC_VIEW = "/implementation/view/application/ClassicView.fxml";
    private static final String WORLD_VIEW = "/implementation/view/application/WorldSelectionView.fxml";

    /**
     * @author Alessandro Talmi
     */
    @Override
    @FXML
    public final void goToClassicMode() throws IOException {
        goToMode(CLASSIC_VIEW);
    }

    @Override
    @FXML
    public void goToLevelMode() throws IOException {
    	goToMode(WORLD_VIEW);
    }
    
    private void goToMode(String FXMLPath) throws IOException {
    	FXMLLoader root = new FXMLLoader(getClass().getResource(FXMLPath));
        Main.getScene().setRoot(root.load());
        StageSelectionController controller = root.getController();
        controller.setSkinPackPath(skinPackPath);
    }

    /**
     * @author Alessandro Talmi
     */
    @Override
    public final void initialize(final URL arg0, final ResourceBundle arg1) {
        final File folder = new File("res" + File.separator + "resources");
        listFiles(folder);
        initializeMenuItem();
        root.heightProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(final ObservableValue<?> observable, final Object oldValue, final Object newValue) {
                changeFontSize(level, TEXT_PERCENTAGE);
                changeFontSize(classic, TEXT_PERCENTAGE);
            }
        });
        final Media media = new Media(new File(MAIN_MENU_THEME_PATH).toURI().toString()); 
        mediaPlayer = new MediaPlayer(media); 
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        System.out.println(new File("/home/elisa/Desktop/snekpp/res/menu").exists());
        snakeppImageView.setImage(
                new Image(
                        new File("/home/elisa/Desktop/snekpp/res/menu").toURI().toString()));
    }

    /**This method read all the directory in the current directory that are put in a map
    *with the path and the name. The first directory is a random pack, used if the default pack
    does not exist. If there are any directory in the folder, the game will stop running.
    @author Elisa Tronetti*/
    private void listFiles(final File folder) {
        String randomPack = "";
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                this.itemButtonMap.put(fileEntry.getName().replace("_", " "), fileEntry.getAbsolutePath());
                if (randomPack.isEmpty()) {
                    randomPack = fileEntry.getAbsolutePath();
                }
            }
        }
        if (this.itemButtonMap.isEmpty()) {
            System.out.println("There are no skin packs");
            System.exit(1);
        } else if (this.itemButtonMap.containsKey(DEFAULT_PACK)) {
            this.skinPackPath = this.itemButtonMap.get(DEFAULT_PACK);
        } else {
            this.skinPackPath = randomPack;
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
     * @author Alessandro Talmi
     */
    private void changeFontSize(final Labeled labeled, final double percentage) {
        labeled.setStyle(String.format("-fx-font-size: %dpx;", (int) (labeled.getHeight() * percentage)));
    }

    /**
     * stop javafx's music.
     */
    public static final void stopMusic() {
        mediaPlayer.stop();
    }
}
