package implementation.controller.game.loader;

import java.awt.Point;
import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import design.model.game.Effect;
import design.model.game.Field;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.items.ItemFactory;
import implementation.model.game.items.WallImpl;

/**
 * Deserialize a field from a json file.
 */
//Deserializers are not to be serialized or deserialized. Serial field is unnecessary.
@SuppressWarnings("serial")
public class FieldDeserializer extends StdDeserializer<Field> {

    /**
     * Only to be used by Jackson.
     */
    public FieldDeserializer() {
        this(null);
    }

    /**
     * Only to be used by Jackson.
     * @param vc 
     */
    public FieldDeserializer(final Class<?> vc) {
        super(vc);
    }

    /**
     * Only to be used by Jackson.
     * @param parser A configured JsonParser.
     * @param deserializer A configured DeserializationContext.
     * @throws IOException Throws an IOException if a Field is malformed.
     * @return a Field.
     */
    public Field deserialize(final JsonParser parser, final DeserializationContext deserializer) throws IOException {
        final ObjectMapper om = new ObjectMapper();
        final JsonNode node = deserializer.readValue(parser, JsonNode.class);

        final Point fieldSize = om.readValue(node.get("dimensions").traverse(), Point.class);
        final Field f = new FieldImpl(fieldSize);
        final ItemFactory itemFactory = new ItemFactory(f);

        final JsonNode walls = node.get("walls");
        for (final JsonNode wall : walls) {
            f.addWall(om.readValue(wall.traverse(), WallImpl.class));
        }

        final JsonNode items = node.get("items");

        for (final JsonNode item : items) {
            final Point position = om.readValue(item.get("position").traverse(), Point.class);

            final Class<? extends Effect> effect = item.get("effect").traverse().readValueAs(new TypeReference<Class<? extends Effect>>() { });

            Optional<Long> itemDuration;
            itemDuration = item.get("itemDuration").isLong() ? Optional.of(item.get("itemDuration").asLong()) : Optional.empty();

            Optional<Long> effectDuration;
            effectDuration = item.get("effectDuration").isLong() ? Optional.of(item.get("effectDuration").asLong()) : Optional.empty();

            itemFactory.createItem(position, effect, itemDuration, effectDuration);
        }

        return f;
    }
}
