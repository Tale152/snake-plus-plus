package implementation.controller.game.gameLoader;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import design.controller.game.GameLoader;
import design.model.game.Field;
import design.model.game.GameModel;
import design.model.game.GameRules;
import design.model.game.ItemRule;
import design.model.game.LossConditions;
import design.model.game.WinConditions;
import implementation.model.game.GameModelImpl;
import implementation.model.game.field.FieldImpl;
import implementation.model.game.gameRules.GameRulesImpl;
import implementation.model.game.gameRules.ItemRuleImpl;
import implementation.model.game.gameRules.LossConditionsImpl;
import implementation.model.game.gameRules.WinConditionsImpl;
import implementation.model.game.items.Apple;
import implementation.model.game.items.WallImpl;

public class GameLoaderJSON implements GameLoader {
	
	private GameModel gameModel;
	
	private ObjectMapper objectMapper;
	
	private static long L = (long) 10e9;

	@Override
	public GameModel getGameModel() {
		return this.gameModel;
	}
	
	private String readJSON(String path) throws IOException {
		String json = new String(Files.readAllBytes(Paths.get(path)));
		
		return json;
	}
	
	private Field loadField(JsonNode json) throws JsonParseException, JsonMappingException, IOException {
		Point size = objectMapper.readValue(json.get("dimensions").traverse(), Point.class);
		Field field = new FieldImpl(size);
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
	
	public GameLoaderJSON(String stagePath) throws IOException {
		objectMapper = new ObjectMapper();
		String json = readJSON(stagePath);
		
		JsonNode loader = objectMapper.readTree(json);
		Field field = loadField(loader.get("field"));
		
		GameRules rules = objectMapper.readValue(loader.get("rules").traverse(), GameRules.class);
		
		this.gameModel = new GameModelImpl(field, rules);
	}
	
	
	public static void main(String arg[]) throws IOException {
		String line;
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Snake length to win: ");
		line = stdin.readLine();
		Optional<Integer> length = Optional.ofNullable(line.length() > 0 ? Integer.parseInt(line) : null);
		
		System.out.print("Score to reach: ");
		line = stdin.readLine();
		Optional<Integer> score = Optional.ofNullable(line.length() > 0 ? Integer.parseInt(line) : null);
		
		System.out.print("Time to reach: ");
		line = stdin.readLine();
		Optional<Long> time = Optional.ofNullable(line.length() > 0 ? Long.parseLong(line) : null);
		
		System.out.print("Time goes forward? ");
		line = stdin.readLine();
		boolean forward = line.length() > 0 ? Boolean.valueOf(line) : true;
		
		WinConditions wc = new WinConditionsImpl(length, score, time, forward);
		
		LossConditions lc = new LossConditionsImpl(true, Optional.of(L*24*3600), true);
		
		List<ItemRule> items = new ArrayList<ItemRule>();
		items.add(new ItemRuleImpl(Apple.class, 1000, 1, 3, Optional.of(L*5), Optional.empty()));
		
		GameRules rules = new GameRulesImpl(wc, lc, items, L, 1.0, true);
		
		ObjectMapper om = new ObjectMapper();
		om.registerModule(new Jdk8Module());
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		om.writeValue(new File("/tmp/rules.json"), rules);
		om.writeValue(new File("/tmp/wc.json"), wc);
		om.writeValue(new File("/tmp/lc.json"), lc);
		
		rules = om.readValue(new File("/tmp/rules.json"), GameRulesImpl.class);
	}

}
