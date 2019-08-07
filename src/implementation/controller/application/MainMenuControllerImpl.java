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
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class MainMenuControllerImpl implements MainMenuController {

	public static String PATH = "res" + File.separator + "resources" + File.separator + "TestPack";
	
	@FXML
	public void goToClassicMode() throws IOException {
		new SettingsControllerImpl();
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/implementation/view/application/ClassicView.fxml"));
		Main.getScene().setRoot(root);
	}
	
	@FXML
	public void goToLevelMode() {
		
	}
	
	@FXML
	public void goToSettings() {
		
	}
}
