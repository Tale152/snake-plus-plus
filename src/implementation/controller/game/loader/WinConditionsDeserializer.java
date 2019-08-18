package implementation.controller.game.loader;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import design.model.game.WinConditions;
import implementation.model.game.rules.WinConditionsImpl;

//Deserializers are not to be serialized or deserialized. Serial field is unnecessary.
@SuppressWarnings("serial")
class WinConditionsDeserializer extends StdDeserializer<WinConditions> {

    WinConditionsDeserializer() {
        this(null);
    }

    WinConditionsDeserializer(final Class<WinConditions> vc) {
        super(vc);
    }

    @Override
    public final WinConditions deserialize(final JsonParser parser, final DeserializationContext deserializer)
            throws IOException, JsonProcessingException {

        final JsonNode node = deserializer.readValue(parser, JsonNode.class);
        final Optional<Integer> snakeLength = node.get("snakeLength").isIntegralNumber() ? Optional.of(node.get("snakeLength").asInt()) : Optional.empty();
        final Optional<Integer> scoreGoal = node.get("scoreGoal").isIntegralNumber() ? Optional.of(node.get("scoreGoal").asInt()) : Optional.empty();
        final Optional<Long> timeGoal = node.get("timeGoal").isIntegralNumber() ? Optional.of(node.get("timeGoal").asLong()) : Optional.empty();
        final boolean timeForward = node.get("timeForward").asBoolean();
        return new WinConditionsImpl(snakeLength, scoreGoal, timeGoal, timeForward);
    }

}
