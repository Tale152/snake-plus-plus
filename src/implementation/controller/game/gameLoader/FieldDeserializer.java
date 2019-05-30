package implementation.controller.game.gameLoader;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import design.model.game.Field;
import design.model.game.Wall;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.items.WallImpl;


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
		
		Point fieldSize = node.get("dimensions").traverse().readValueAs(Point.class);
		Field f = new FieldImpl(fieldSize);
		
		JsonNode walls = node.get("walls");
		for (final JsonNode wall : walls) {
			f.addWall(wall.traverse().readValueAs(Wall.class));
		}
		
		return f;
	}
}
