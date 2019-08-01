package implementation.controller.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import design.controller.application.MainMenuController;
import design.controller.game.GameLoader;
import implementation.controller.game.gameLoader.GameLoaderJSON;
import implementation.view.application.Main;
import implementation.view.game.GameViewImpl;
import javafx.fxml.FXML;

public class MainMenuControllerImpl implements MainMenuController {

	public static String PATH = "res" + File.separator + "resources" + File.separator + "TestPack";
	
	@FXML
	public void goToClassicMode() throws IOException {
		new SettingsControllerImpl();
		List<String> playerNames = new ArrayList<>(Arrays.asList("Ale"));
		String levelPath = "res/stages/classic/HightPoints5MinMedium.json";
		GameLoader gl;
		gl = new GameLoaderJSON(levelPath, playerNames);
		GameViewImpl gw = new GameViewImpl(Main.getScene(), levelPath, PATH, playerNames, 
	    		gl.getGameModel().getField().getWidth(), gl.getGameModel().getField().getHeight());
	}
	
	@FXML
	public void goToLevelMode() {
		
	}
	
	@FXML
	public void goToSettings() {
		
	}
}
