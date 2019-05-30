package implementation.controller.game.gameLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import design.controller.game.GameLoader;
import design.model.game.Field;
import design.model.game.GameModel;
import design.model.game.GameRules;
import design.model.game.ItemRule;
import design.model.game.LossConditions;
import design.model.game.WinConditions;
import implementation.model.game.GameModelImpl;
import implementation.model.game.gameRules.GameRulesImpl;
import implementation.model.game.gameRules.ItemRuleImpl;
import implementation.model.game.gameRules.LossConditionsImpl;
import implementation.model.game.gameRules.WinConditionsImpl;
import implementation.model.game.items.Apple;

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
	
	
	
	public GameLoaderJSON(String stagePath) throws IOException {
		objectMapper = new ObjectMapper();
		String json = readJSON(stagePath);
		
		JsonNode loader = objectMapper.readTree(json);
		Field field = loader.get("field").traverse().readValueAs(Field.class);
		
		GameRules rules = loader.get("rules").traverse().readValueAs(GameRules.class);
		
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
