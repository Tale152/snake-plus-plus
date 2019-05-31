package implementation.controller.game.gameLoader;

import java.awt.Point;
import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import design.model.game.Effect;
import design.model.game.Field;
import design.model.game.Wall;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.items.ItemFactory;


//Deserializers are not to be serialized or deserialized. Serial field is unnecessary.
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
			
			Class<? extends Effect> effect = item.get("effect").traverse().readValueAs(new TypeReference<Class<? extends Effect>>() {});
			
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
