package implementation.controller.game.gameLoader;

import java.awt.Point;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import design.model.game.Wall;
import implementation.model.game.items.WallImpl;

@SuppressWarnings("serial")
public class WallDeserializer extends StdDeserializer<Wall> {
	
	public WallDeserializer() {
		this(null);
	}

	public WallDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Wall deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException, JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		return new WallImpl(om.readValue(deserializer.readValue(parser, JsonNode.class).get("point").traverse(), Point.class));
	}

}
