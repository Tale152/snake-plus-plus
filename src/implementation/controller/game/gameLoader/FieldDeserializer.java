package implementation.controller.game.gameLoader;

import java.awt.Point;
import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import design.model.game.Effect;
import design.model.game.Field;
import design.model.game.Wall;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.items.ItemFactory;


/*
private Field loadField(JsonNode json) throws JsonParseException, JsonMappingException, IOException {
		String map = json.get("map").asText();
		List<String> lines = Arrays.asList(map.split("\n"));
		
		for (int y = 0; y < lines.size(); y++) {
			String line = lines.get(y);
			while (line.matches("#")) {
				int x = line.indexOf('#');
				field.addWall(new WallImpl(new Point(x, y)));
				line = line.substring(x + 1);
			}
		}
		
		return field;
	}
 */
//FieldDeserializer is not to be serialized or deserialized. Serial field is unnecessary.
@SuppressWarnings("serial")
class FieldDeserializer extends StdDeserializer<Field> {
	
	public FieldDeserializer() {
		this(null);
	}
	
	public FieldDeserializer(Class<?> vc) {
		super(vc);
	}
	
	public Field deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
		JsonNode node = parser.getCodec().readTree(parser);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Jdk8Module());
		
		Point fieldSize = node.get("dimensions").traverse().readValueAs(Point.class);
		Field f = new FieldImpl(fieldSize);
		ItemFactory itemFactory = new ItemFactory(f);
		
		JsonNode walls = node.get("walls");
		for (final JsonNode wall : walls) {
			f.addWall(wall.traverse().readValueAs(Wall.class));
		}
		
		JsonNode items = node.get("items");
		
		for (final JsonNode item : items) {
			Point position = item.get("position").traverse().readValueAs(Point.class);
			
			Class<? extends Effect> effect = item.get("effect").traverse().readValueAs(new TypeReference<Effect>() {});
			
			//TODO: read Optionals instead of ternary
			Optional<Long> itemDuration;
			itemDuration = item.get("itemDuration").isLong() ? Optional.of(item.get("itemDuration").asLong()) : Optional.empty();
			
			Optional<Long> effectDuration;
			effectDuration = item.get("effectDuration").isLong() ? Optional.of(item.get("effectDuration").asLong()) : Optional.empty();
			
			itemFactory.createItem(position, effect, itemDuration, effectDuration);
		}
		
		return f;
	}
}
