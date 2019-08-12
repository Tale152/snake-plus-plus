package implementation.view.application;
import java.io.File;

import implementation.controller.Path;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application{
	
  private static final String MAIN_MENU_THEME_PATH = Path.THEMES + "Main_menu_theme.mp3";
  private static Scene scene = new Scene(new BorderPane());
  public static MediaPlayer mediaPlayer;
  
  public static void main(String[] args) {
    Application.launch(Main.class, args);
  }
  
  public static Scene getScene() {
	  return scene;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
	  Media media = new Media(new File(MAIN_MENU_THEME_PATH).toURI().toString()); 
      mediaPlayer = new MediaPlayer(media); 
      mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
      mediaPlayer.play();
	  primaryStage.setOnCloseRequest(e -> {System.exit(0);});
	  try {
		  	AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("MainMenuView.fxml"));
			scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			scene.setOnKeyPressed(new EventHandler<KeyEvent>(){

				@Override
				public void handle(KeyEvent arg0) {
					if (arg0.getCode().equals(KeyCode.ESCAPE)) {
						System.exit(0);
					}
					
				}
				
			});
			primaryStage.setFullScreenExitHint("");
			primaryStage.setFullScreen(true);
			primaryStage.setResizable(false);
			primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
			primaryStage.setTitle("Snake++");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
  }

}