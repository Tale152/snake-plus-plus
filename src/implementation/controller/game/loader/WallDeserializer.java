package implementation.controller.game.loader;

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

/**
 * Deserialize a wall from a JSON file.
 */
@SuppressWarnings("serial")
public class WallDeserializer extends StdDeserializer<Wall> {

    /**
     * Only to be called from Jackson.
     */
    public WallDeserializer() {
        this(null);
    }

    /**
     * Only to be called from Jackson.
     * @param vc 
     */
    public WallDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public final Wall deserialize(final JsonParser parser, final DeserializationContext deserializer) throws IOException, JsonProcessingException {
        final ObjectMapper om = new ObjectMapper();
        return new WallImpl(om.readValue(deserializer.readValue(parser, JsonNode.class).get("point").traverse(), Point.class));
    }

}
