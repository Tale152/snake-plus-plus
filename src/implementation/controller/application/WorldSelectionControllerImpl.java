package implementation.controller.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import design.controller.application.WorldDescriptor;
import design.controller.application.WorldSelectionController;
import design.controller.game.GameLoader;
import implementation.controller.Path;
import implementation.controller.game.gameLoader.GameLoaderJSON;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class WorldSelectionControllerImpl implements WorldSelectionController {

    private static final String JSONREGEX = "[.]json$";
    private static final List<String> NAMES = Arrays.asList("Player");
    private int selected;
    private int previous;
    private final List<WorldDescriptor> worlds;
    private String skinPackPath;

    @FXML
    private Label worldName;
    @FXML
    private HBox worldButtons;
    @FXML
    private Text worldDescription;

    public WorldSelectionControllerImpl() throws JsonProcessingException, IOException {
        worlds = new ArrayList<>();
        selected = 0;
        previous = 0;
    }

    public final void initialize() throws JsonProcessingException, IOException {
        File worldsFolder = new File(Path.WORLDS);
        for (File world : worldsFolder.listFiles(f -> f.isFile())) {
            worlds.add(parseWorld(world));
        }
        worlds.sort(null);
        for (WorldDescriptor world : worlds) {
            worldButtons.getChildren().add(newWorldButton(world));
        }
        refreshWorld();
    }

    private Button newWorldButton(final WorldDescriptor world) {
        Button button = new Button(world.getName());
        ObservableList<Node> buttons = worldButtons.getChildren();
        int n = buttons.size();
        button.setOnMouseClicked(e -> {
            selected = n;
            refreshWorld();
        });
        return button;
    }

    private WorldDescriptor parseWorld(final File worldJson) throws JsonProcessingException, IOException {
        ObjectMapper om = new ObjectMapper();
        JsonNode loader = om.readTree(worldJson);
        String name = loader.get("name").asText();
        String description = loader.get("description").asText();
        String folder = worldJson.getName().replaceAll(JSONREGEX, "");
        return new WorldDescriptorImpl(name, description, folder);
    }

    private void refreshWorld() {
        int nWorlds = worlds.size();
        WorldDescriptor world;
        ObservableList<Node> buttons = worldButtons.getChildren();
        while (this.selected < 0) {
            this.selected = this.selected + nWorlds;
        }
        while (this.selected >= nWorlds) {
            this.selected = this.selected - nWorlds;
        }
        buttons.get(previous).setDisable(false);
        buttons.get(selected).setDisable(true);
        world = worlds.get(selected);
        String description = world.getDescription();
        String name = world.getName();
        worldDescription.setText(description);
        worldName.setText(name);
        previous = selected;
    }

    @FXML
    public final void selectPrev() throws FileNotFoundException, IOException {
        this.selected -= 1;
        refreshWorld();
    }

    @FXML
    public final void selectNext() throws FileNotFoundException, IOException {
        this.selected += 1;
        refreshWorld();
    }

    @FXML
    public final void startWorld() throws IOException {
        File worldFolder = new File(Path.WORLDS + File.separator + worlds.get(selected).getFolderName());
        File[] worldFiles = worldFolder.listFiles(f -> f.isFile());
        Arrays.sort(worldFiles);
        List<GameLoader> world = new ArrayList<>();
        for (File level : worldFiles) {
            world.add(new GameLoaderJSON(level, NAMES));
        }
        new GameIntersticeImpl(world, skinPackPath).nextLevel();
    }

    @Override
    public void setSkinPackPath(final String path) {
        skinPackPath = path;
    }

}
