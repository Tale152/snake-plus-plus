package implementation.controller.game.gameLoader;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import implementation.model.game.gameRules.GameRulesImpl;
import implementation.model.game.gameRules.ItemRuleImpl;
import implementation.model.game.gameRules.LossConditionsImpl;
import implementation.model.game.gameRules.WinConditionsImpl;
import implementation.model.game.items.Apple;
import implementation.model.game.items.Beer;
import implementation.model.game.snake.SnakeImpl;

public class GameLoaderJSON implements GameLoader {

    private final GameModel gameModel;

    private final ObjectMapper objectMapper;

    private final String name;

    private final String description;

    private final int maxPlayers;

    private static long L = (long) 10e9;

    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    private String readJSON(String path) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(path)));
        return json;
    }

    public GameLoaderJSON(String stagePath, List<String> names) throws IOException {
        objectMapper = new ObjectMapper();
        SimpleModule deserializerModule = new SimpleModule();
        deserializerModule.addDeserializer(WinConditions.class, new WinConditionsDeserializer());
        deserializerModule.addDeserializer(LossConditions.class, new LossConditionsDeserializer());
        deserializerModule.addDeserializer(GameRules.class, new GameRulesDeserializer());
        deserializerModule.addDeserializer(ItemRule.class, new ItemRuleDeserializer());
        deserializerModule.addDeserializer(Field.class, new FieldDeserializer());
        objectMapper.registerModule(deserializerModule);

        objectMapper.registerModule(new Jdk8Module());

        String json = readJSON(stagePath);

        JsonNode loader = objectMapper.readTree(json);
        //Field field = loader.get("field").traverse().readValueAs(Field.class);
        Field field = objectMapper.readValue(loader.get("field").traverse(), Field.class);

        GameRules rules = objectMapper.readValue(loader.get("rules").traverse(), GameRules.class);

        List<List<Point>> snakes = objectMapper.readValue(loader.get("snakes").traverse(), new TypeReference<List<List<Point>>>() { });
        List<Direction> directions = objectMapper.readValue(loader.get("directions").traverse(), new TypeReference<List<Direction>>() { });

        for (int i = 0; i < snakes.size(); i++) {
            String name = names.get(i);
            List<Point> points = snakes.get(i);
            Snake snake = new SnakeImpl(PlayerNumber.values()[i], name, directions.get(i), rules.getInitialSnakeDelta(), rules.getInitialSnakeMultiplier(), field, points);
            field.addSnake(snake);
        }

        this.gameModel = new GameModelImpl(field, rules);

        this.maxPlayers = snakes.size();

        this.name = loader.get("name").asText();
        this.description = loader.get("description").asText();
    }

    public static void main(String arg[]) throws IOException {
        Optional<Integer> length = Optional.ofNullable(10);
        Optional<Integer> score = Optional.ofNullable(null);
        Optional<Long> time = Optional.ofNullable(null);
        boolean forward = true;

        WinConditions wc = new WinConditionsImpl(length, score, time, forward);
        LossConditions lc = new LossConditionsImpl(true, Optional.of(L * 24 * 3600), true);

        List<ItemRule> items = new ArrayList<ItemRule>();
        items.add(new ItemRuleImpl(Apple.class, 1000, 1, 3, Optional.of(L * 5), Optional.empty()));
        items.add(new ItemRuleImpl(Beer.class, 1000, 1, 3, Optional.of(L * 5), Optional.of(L)));

        GameRules rules = new GameRulesImpl(wc, lc, items, L, 1.0, 0, true);

        ObjectMapper om = new ObjectMapper();

        SimpleModule deserializerModule = new SimpleModule();
        deserializerModule.addDeserializer(WinConditions.class, new WinConditionsDeserializer());
        deserializerModule.addDeserializer(LossConditions.class, new LossConditionsDeserializer());
        deserializerModule.addDeserializer(GameRules.class, new GameRulesDeserializer());
        deserializerModule.addDeserializer(ItemRule.class, new ItemRuleDeserializer());
        om.registerModule(deserializerModule);

        om.registerModule(new Jdk8Module());

        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        om.writeValue(new File("/tmp/rules.json"), rules);
        om.writeValue(new File("/tmp/wc.json"), wc);
        om.writeValue(new File("/tmp/lc.json"), lc);

        om.writeValue(new File("/tmp/ir.json"), items.get(0));
        om.readValue(new File("/tmp/ir.json"), ItemRule.class);

        rules = om.readValue(new File("/tmp/rules.json"), GameRules.class);

        om.writeValue(new File("/tmp/2rules.json"), rules);

        om.writeValue(new File("/tmp/directions.json"), Arrays.asList(Direction.RIGHT));
    }

    @Override
    public final String getLevelName() {
        return name;
    }

    @Override
    public final String getLevelDescription() {
        return description;
    }

    @Override
    public final int getMaxPlayers() {
        return maxPlayers;
    }

}
