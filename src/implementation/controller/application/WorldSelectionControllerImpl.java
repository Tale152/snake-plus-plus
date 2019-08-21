package implementation.controller.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import design.controller.application.GameEndReason;
import design.controller.application.GameInterstice;
import design.controller.application.StageSelectionController;
import design.controller.application.WorldDescriptor;
import design.controller.game.GameLoader;
import implementation.controller.PathUtils;
import implementation.controller.game.loader.GameLoaderJSON;
import implementation.view.application.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * @see WorldSelectionController
 */
public class WorldSelectionControllerImpl implements StageSelectionController {

    private static final String JSONREGEX = ".json";
    private static final List<String> NAMES = Arrays.asList("Player");
    private int selected;
    private int previous;
    private final List<WorldDescriptor> worlds;
    private Path skinPackPath;
    private static final double DESCRIPTION_PERCENT_HEIGHT = 0.12;

    @FXML
    private AnchorPane root;
    @FXML
    private Label worldName;
    @FXML
    private HBox worldButtons;
    @FXML
    private Text worldDescription;
    @FXML
    private BorderPane descriptionPane;

    /**
     * Creates a WorldSelectionController.
     */
    public WorldSelectionControllerImpl() {
        worlds = new ArrayList<>();
        selected = 0;
        previous = 0;
    }

    /**
     * Initializes the world selection view. Only to be called by FXML.
     * @throws JsonProcessingException If a world descriptor file is invalid.
     * @throws IOException If the folder containing the worlds does not exist, or there's an I/O error.
     */
    public final void initialize() throws JsonProcessingException, IOException {
        final Path worldsFolder = PathUtils.getResourcePath(PathUtils.WORLDS);
        Files.walk(worldsFolder, 1)
                .filter(p -> (!p.equals(worldsFolder) && Files.isRegularFile(p)))
                .map(p -> {
                    try {
                        return parseWorld(p);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(w -> w != null)
                .sorted()
                .forEachOrdered(w -> {
                    worlds.add(w);
                    worldButtons.getChildren().add(newWorldButton(w));
        });

        descriptionPane.minHeightProperty().bind(root.heightProperty().multiply(DESCRIPTION_PERCENT_HEIGHT));
        refreshWorld();
    }

    private Button newWorldButton(final WorldDescriptor world) {
        final Button button = new Button(world.getName());
        final ObservableList<Node> buttons = worldButtons.getChildren();
        final int n = buttons.size();
        button.setOnMouseClicked(e -> {
            selected = n;
            refreshWorld();
        });
        return button;
    }

    private WorldDescriptor parseWorld(final Path p) throws JsonProcessingException, IOException {
        final Path folderPath = p.getFileName();
        if (folderPath == null) {
            return null;
        }
        final ObjectMapper om = new ObjectMapper();
        final JsonNode loader = om.readTree(p.toUri().toURL());
        final String name = loader.get("name").asText();
        final String description = loader.get("description").asText();
        final String folder = folderPath.toString().replaceAll(JSONREGEX, "").replace("/", "");
        return new WorldDescriptorImpl(name, description, folder);
    }

    private void refreshWorld() {
        final int nWorlds = worlds.size();
        WorldDescriptor world;
        final ObservableList<Node> buttons = worldButtons.getChildren();
        while (this.selected < 0) {
            this.selected = this.selected + nWorlds;
        }
        while (this.selected >= nWorlds) {
            this.selected = this.selected - nWorlds;
        }
        buttons.get(previous).setDisable(false);
        buttons.get(selected).setDisable(true);
        world = worlds.get(selected);
        final String description = world.getDescription();
        final String name = world.getName();
        worldDescription.setText(description);
        worldName.setText(name);
        previous = selected;
    }

    /**
     * Selects the previous world. Only to be called by FXML.
     */
    @FXML
    public final void selectPrev() {
        this.selected -= 1;
        refreshWorld();
    }

    /**
     * Selects the next world. Only to be called by FXML.
     */
    @FXML
    public final void selectNext() {
        this.selected += 1;
        refreshWorld();
    }

    /**
     * Starts the game with the selected world. Only to be called by FXML.
     * @throws IOException If the world folder is invalid or a level is malformed.
     */
    @FXML
    public final void startWorld() throws IOException {
        final List<GameLoader> world = new ArrayList<>();
        final Path worldFolder = PathUtils.getResourcePath(PathUtils.WORLDS).resolve(worlds.get(selected).getFolderName());
        Files.walk(worldFolder, 1).filter(p -> !p.equals(worldFolder)).sorted().forEachOrdered(p -> {
            try {
                world.add(new GameLoaderJSON(p, NAMES));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        final GameInterstice interstice = new GameIntersticeImpl(world, skinPackPath);
        interstice.setGameEndReason(GameEndReason.START);
        interstice.showInterstice();
    }

    @Override
    public final void setSkinPackPath(final Path path) {
        skinPackPath = path;
    }

    @FXML
    @Override
    public final void loadMainMenu() throws IOException {
        final FXMLLoader root = new FXMLLoader(getClass().getResource("/implementation/view/application/MainMenuView.fxml"));
        Main.getScene().setRoot(root.load());
    }

}
