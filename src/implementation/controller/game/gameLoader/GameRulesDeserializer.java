package implementation.controller.game.gameLoader;

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
import implementation.model.game.gameRules.GameRulesImpl;

//Deserializers are not to be serialized or deserialized. Serial field is unnecessary.
@SuppressWarnings("serial")
class GameRulesDeserializer extends StdDeserializer<GameRules> {
	
	protected GameRulesDeserializer() {
		this(null);
	}

	protected GameRulesDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public GameRules deserialize(JsonParser parser, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		/*JsonNode node = mapper.readTree(parser);*/
		JsonNode node = parser.getCodec().readTree(parser);
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule deserializerModule = new SimpleModule();
		deserializerModule.addDeserializer(ItemRule.class, new ItemRuleDeserializer());
		deserializerModule.addDeserializer(WinConditions.class, new WinConditionsDeserializer());
		deserializerModule.addDeserializer(LossConditions.class, new LossConditionsDeserializer());
		mapper.registerModules(deserializerModule);
		
		long initialSnakeDelta = node.get("initialSnakeDelta").asLong();
		double initialSnakeMultiplier = node.get("initialSnakeMultiplier").asDouble();
		boolean timeForward = node.get("timeGoingForward").asBoolean();
		List<ItemRule> itemRules = mapper.readValue(node.get("itemRules").traverse(), new TypeReference<List<ItemRule>>() {});
		LossConditions lc = mapper.readValue(node.get("lossConditions").traverse(), LossConditions.class);
		WinConditions wc = mapper.readValue(node.get("winConditions").traverse(), WinConditions.class);

		return new GameRulesImpl(wc, lc, itemRules, initialSnakeDelta, initialSnakeMultiplier, timeForward);
	}

}