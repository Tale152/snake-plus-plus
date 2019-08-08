package implementation.controller.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

import design.controller.application.ClassicController;
import design.controller.application.MainMenuController;

import design.view.game.Sprite;
import implementation.view.application.Main;
import implementation.view.game.SpriteImpl;

import implementation.view.application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class MainMenuControllerImpl implements MainMenuController, Initializable {

	private static double TEXT_PERCENTAGE = 0.4;
	@FXML private Button classic;
	@FXML private Button level;
	@FXML private Label snakeppLabel;
	@FXML private AnchorPane root;
	@FXML private MenuButton skinPacks;
	
	//default skin pack that can be change
	private String skinPackPath;
	private final Map<String, String> itemButtonMap = new HashMap<>();
	
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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		final File folder = new File("res" + File.separator + "resources");
		listFiles(folder);
        
		for(Entry<String, String> s : this.itemButtonMap.entrySet()) {
			MenuItem m = new MenuItem(s.getValue());
			EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
	            public void handle(ActionEvent e) { 
	            	skinPackPath = s.getKey();
	            	skinPacks.setText(m.getText());
	            } 
	        }; 
			m.setOnAction(event);
			this.skinPacks.getItems().add(m);
		}	
		
		root.heightProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				changeFontSize(level, TEXT_PERCENTAGE);
				changeFontSize(classic, TEXT_PERCENTAGE);
				changeFontSize(snakeppLabel, TEXT_PERCENTAGE);
			}
			
		});
	}
	
	private void listFiles(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	this.itemButtonMap.put(fileEntry.getAbsolutePath(), fileEntry.getName().replace("_", " "));
	        }
	    }
	}
	
	private void changeFontSize(Labeled labeled, double percentage) {
		labeled.setStyle(String.format("-fx-font-size: %dpx;", (int)(labeled.getHeight()*percentage)));
	}
}
