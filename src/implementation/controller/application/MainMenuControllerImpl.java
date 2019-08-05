package implementation.controller.application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import design.controller.application.MainMenuController;
import design.controller.game.GameLoader;
import implementation.controller.game.gameLoader.GameLoaderJSON;
import implementation.view.application.Main;
import implementation.view.game.GameViewImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.AnchorPane;

public class MainMenuControllerImpl implements MainMenuController, Initializable {

	private static double TEXT_PERCENTAGE = 0.4;
	@FXML private Button classic;
	@FXML private Button level;
	@FXML private Label snakeppLabel;
	@FXML private AnchorPane root;
	
	@FXML
	public void goToClassicMode() throws IOException {
		String PATH = "res" + File.separator + "resources" + File.separator + "TestPack";
		new SettingsControllerImpl();
		List<String> playerNames = new ArrayList<>(Arrays.asList("Ale"));
		String levelPath = "res/stages/classic/HightPoints5MinMedium.json";
		GameLoader gl;
		gl = new GameLoaderJSON(levelPath, playerNames);
		@SuppressWarnings("unused")
		GameViewImpl gw = new GameViewImpl(Main.getScene(), levelPath, PATH, playerNames, 
	    		gl.getGameModel().getField().getWidth(), gl.getGameModel().getField().getHeight());
	}
	
	@FXML
	public void goToLevelMode() {
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		root.heightProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				changeFontSize(level, TEXT_PERCENTAGE);
				changeFontSize(classic, TEXT_PERCENTAGE);
				changeFontSize(snakeppLabel, TEXT_PERCENTAGE);
			}
			
		});

	}
	
	private void changeFontSize(Labeled labeled, double percentage) {
		labeled.setStyle(String.format("-fx-font-size: %dpx;", (int)(labeled.getHeight()*percentage)));
	}
}
