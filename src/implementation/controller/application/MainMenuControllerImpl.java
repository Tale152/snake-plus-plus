package implementation.controller.application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import design.controller.application.ClassicController;
import design.controller.application.MainMenuController;
import implementation.view.application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class MainMenuControllerImpl implements MainMenuController, Initializable {

	private static double TEXT_PERCENTAGE = 0.4;
	@FXML private Button classic;
	@FXML private Button level;
	@FXML private Label snakeppLabel;
	@FXML private AnchorPane root;
	@FXML private MenuItem pack1;
	@FXML private MenuItem defaultPack;
	
	//default skin pack that can be change
	private String skinPackPath;
	
	@FXML
	public void goToClassicMode() throws IOException {
		new SettingsControllerImpl();
		FXMLLoader root = new FXMLLoader(getClass().getResource("/implementation/view/application/ClassicView.fxml"));
		Main.getScene().setRoot(root.load());
		ClassicController controller = root.getController();
		controller.setSkinPackPath(skinPackPath);
	}
	
	@FXML
	public void goToLevelMode() {
		
	}
	
	@FXML
	public void selectDefaultSkinPack(){
		String defaultName = this.defaultPack.getText();
		this.skinPackPath = "res" + File.separator + "resources" + File.separator + defaultName.replaceAll("\\s", "");
	}
	
	@FXML
	public void selectSkinPack1(){
		String pack1Name = this.pack1.getText();
		this.skinPackPath = "res" + File.separator + "resources" + File.separator + pack1Name.replaceAll("\\s", "");
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
		
		selectDefaultSkinPack();

	}
	
	private void changeFontSize(Labeled labeled, double percentage) {
		labeled.setStyle(String.format("-fx-font-size: %dpx;", (int)(labeled.getHeight()*percentage)));
	}
}
