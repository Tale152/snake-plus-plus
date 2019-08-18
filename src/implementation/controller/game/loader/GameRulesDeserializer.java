package implementation.controller.game.loader;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import design.model.game.GameRules;
import design.model.game.ItemRule;
import design.model.game.LossConditions;
import design.model.game.WinConditions;
import implementation.model.game.rules.GameRulesImpl;

//Deserializers are not to be serialized or deserialized. Serial field is unnecessary.
@SuppressWarnings("serial")
class GameRulesDeserializer extends StdDeserializer<GameRules> {

    protected GameRulesDeserializer() {
        this(null);
    }

    protected GameRulesDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public final GameRules deserialize(final JsonParser parser, final DeserializationContext deserializer)
            throws IOException, JsonProcessingException {
        final JsonNode node = deserializer.readValue(parser, JsonNode.class);
        final ObjectMapper mapper = new ObjectMapper();
        final SimpleModule deserializerModule = new SimpleModule();
        deserializerModule.addDeserializer(ItemRule.class, new ItemRuleDeserializer());
        deserializerModule.addDeserializer(WinConditions.class, new WinConditionsDeserializer());
        deserializerModule.addDeserializer(LossConditions.class, new LossConditionsDeserializer());
        mapper.registerModules(deserializerModule);
        final long initialSnakeDelta = node.get("initialSnakeDelta").asLong();
        final double initialSnakeMultiplier = node.get("initialSnakeMultiplier").asDouble();
        final boolean timeForward = node.get("timeGoingForward").asBoolean();
        final long initialTime = node.get("initialTime").asLong();
        final List<ItemRule> itemRules = mapper.readValue(node.get("itemRules").traverse(), new TypeReference<List<ItemRule>>() { });
        final LossConditions lc = mapper.readValue(node.get("lossConditions").traverse(), LossConditions.class);
        final WinConditions wc = mapper.readValue(node.get("winConditions").traverse(), WinConditions.class);

        return new GameRulesImpl(wc, lc, itemRules, initialSnakeDelta, initialSnakeMultiplier, initialTime, timeForward);
    }

}
