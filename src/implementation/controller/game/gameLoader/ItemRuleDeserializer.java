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
import implementation.model.game.gameRules.ItemRuleImpl;

//Deserializers are not to be serialized or deserialized. Serial field is unnecessary.
@SuppressWarnings("serial")
class ItemRuleDeserializer extends StdDeserializer<ItemRule> {
	
	protected ItemRuleDeserializer() {
		this(null);
	}

	protected ItemRuleDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ItemRule deserialize(JsonParser parser, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		JsonNode node = om.readTree(parser);
		
		Class<? extends Effect> effect = om.readValue(node.get("effectClass").traverse(), new TypeReference<Class<? extends Effect>>() {});
		long spawnDelta = node.get("spawnDelta").asLong();
		double spawnChance = node.get("spawnChance").asDouble();
		int maximum = node.get("max").asInt();
		Optional<Long> itemDuration = node.get("itemDuration").isIntegralNumber() ? Optional.of(node.get("itemDuration").asLong()) : Optional.empty();
		Optional<Long> effectDuration = node.get("effectDuration").isIntegralNumber() ? Optional.of(node.get("effectDuration").asLong()) : Optional.empty();
		
		return new ItemRuleImpl(effect, spawnDelta, spawnChance, maximum, itemDuration, effectDuration);
	}

}
