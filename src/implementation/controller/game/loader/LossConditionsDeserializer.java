package implementation.controller.game.gameLoader;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import design.model.game.LossConditions;
import implementation.model.game.rules.LossConditionsImpl;

//Deserializers are not to be serialized or deserialized. Serial field is unnecessary.
@SuppressWarnings("serial")
class LossConditionsDeserializer extends StdDeserializer<LossConditions> {
    protected LossConditionsDeserializer() {
        this(null);
    }

    protected LossConditionsDeserializer(final Class<LossConditions> vc) {
        super(vc);
    }

    @Override
    public LossConditions deserialize(final JsonParser parser, final DeserializationContext deserializer)
            throws IOException, JsonProcessingException {
        JsonNode node = deserializer.readValue(parser, JsonNode.class);
        boolean checkAllSnakesDied = node.get("checkAllSnakesDied").asBoolean();
        boolean timeForward = node.get("timeForward").asBoolean();
        Optional<Long> gameTime = node.get("gameTime").isIntegralNumber() ? Optional.of(node.get("gameTime").asLong()) : Optional.empty();
        return new LossConditionsImpl(checkAllSnakesDied, gameTime, timeForward);
    }

}
