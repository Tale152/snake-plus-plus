package implementation.controller.game.loader;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import design.controller.game.GameLoader;
import design.model.game.Direction;
import design.model.game.Field;
import design.model.game.GameModel;
import design.model.game.GameRules;
import design.model.game.ItemRule;
import design.model.game.LossConditions;
import design.model.game.PlayerNumber;
import design.model.game.Snake;
import design.model.game.WinConditions;
import implementation.model.game.GameModelImpl;
import implementation.model.game.snake.SnakeImpl;

/**
 * Loads a level from a JSON file.
 */
public final class GameLoaderJSON implements GameLoader {

    private GameModel gameModel;

    private final String name;
    private final String description;

    private final int maxPlayers;

    private final List<String> names;
    private final JsonNode loader;
    private final ObjectMapper objectMapper;

    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    private String readJSON(final String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    /**
     * Loads a game from a File and a list of names.
     * @param stageFile The file containing the level.
     * @param names The names of the players.
     * @throws IOException If a level is malformed or absent.
     */
    public GameLoaderJSON(final File stageFile, final List<String> names) throws IOException {
        this(stageFile.getAbsolutePath(), names);
    }

    /**
     * Loads a game from a file path and a list of names.
     * @param stagePath The path of the file containing the level.
     * @param names The names of the players.
     * @throws IOException If a level is malformed or absent.
     */
    public GameLoaderJSON(final String stagePath, final List<String> names) throws IOException {
        this.names = names;
        objectMapper = new ObjectMapper();
        final SimpleModule deserializerModule = new SimpleModule();
        deserializerModule.addDeserializer(WinConditions.class, new WinConditionsDeserializer());
        deserializerModule.addDeserializer(LossConditions.class, new LossConditionsDeserializer());
        deserializerModule.addDeserializer(GameRules.class, new GameRulesDeserializer());
        deserializerModule.addDeserializer(ItemRule.class, new ItemRuleDeserializer());
        deserializerModule.addDeserializer(Field.class, new FieldDeserializer());
        objectMapper.registerModule(deserializerModule);

        objectMapper.registerModule(new Jdk8Module());

        final String json = readJSON(stagePath);

        loader = objectMapper.readTree(json);

        generateModel();

        this.maxPlayers = gameModel.getField().getSnakes().size();

        this.name = loader.get("name").asText();
        this.description = loader.get("description").asText();
    }

    private void generateModel() throws IOException {
        final Field field = objectMapper.readValue(loader.get("field").traverse(), Field.class);

        final GameRules rules = objectMapper.readValue(loader.get("rules").traverse(), GameRules.class);

        final List<List<Point>> snakes = objectMapper.readValue(loader.get("snakes").traverse(), new TypeReference<List<List<Point>>>() { });
        final List<Direction> directions = objectMapper.readValue(loader.get("directions").traverse(), new TypeReference<List<Direction>>() { });

        for (int i = 0; i < snakes.size(); i++) {
            final String name = names.get(i);
            final List<Point> points = snakes.get(i);
            final Snake snake = new SnakeImpl(PlayerNumber.values()[i], name, directions.get(i), rules.getInitialSnakeDelta(), rules.getInitialSnakeMultiplier(), field, points);
            field.addSnake(snake);
        }

        this.gameModel = new GameModelImpl(field, rules);
    }

    @Override
    public String getLevelName() {
        return name;
    }

    @Override
    public String getLevelDescription() {
        return description;
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public void reset() throws IOException {
        generateModel();
    }

}
