package implementation.controller.game.gameLoader;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import design.model.game.Effect;
import design.model.game.ItemRule;
import implementation.model.game.gamerules.ItemRuleImpl;

//Deserializers are not to be serialized or deserialized. Serial field is unnecessary.
@SuppressWarnings("serial")
class ItemRuleDeserializer extends StdDeserializer<ItemRule> {

    protected ItemRuleDeserializer() {
        this(null);
    }

    protected ItemRuleDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public ItemRule deserialize(final JsonParser parser, final DeserializationContext deserializer)
            throws IOException, JsonProcessingException {
        JsonNode node = deserializer.readValue(parser, JsonNode.class);
        ObjectMapper om = new ObjectMapper();
        Class<? extends Effect> effect = om.readValue(node.get("effectClass").traverse(), new TypeReference<Class<? extends Effect>>() { });
        long spawnDelta = node.get("spawnDelta").asLong();
        double spawnChance = node.get("spawnChance").asDouble();
        int maximum = node.get("max").asInt();
        Optional<Long> itemDuration = node.get("itemDuration").isIntegralNumber() ? Optional.of(node.get("itemDuration").asLong()) : Optional.empty();
        Optional<Long> effectDuration = node.get("effectDuration").isIntegralNumber() ? Optional.of(node.get("effectDuration").asLong()) : Optional.empty();
        return new ItemRuleImpl(effect, spawnDelta, spawnChance, maximum, itemDuration, effectDuration);
    }

}
