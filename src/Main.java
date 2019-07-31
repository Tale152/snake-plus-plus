import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import design.controller.game.GameLoader;
import implementation.controller.application.SettingsControllerImpl;
import implementation.controller.game.gameLoader.GameLoaderJSON;
import implementation.view.game.GameViewImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{

  public static String PATH = "res" + File.separator + "resources" + File.separator + "TestPack";
  public static Scene scene = new Scene(new BorderPane());
  
  public static void main(String[] args) {
    Application.launch(Main.class, args);
  }
  
  public static Scene getScene() {
	  return scene;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
	  new SettingsControllerImpl();
	  List<String> playerNames = new ArrayList<>(Arrays.asList("Ale", "Orla"));
	  String levelPath = "res/stages/classic/1.json";
	  GameLoader gl = new GameLoaderJSON(levelPath, playerNames);
      GameViewImpl gw = new GameViewImpl(scene, levelPath, PATH, playerNames, 
    		  gl.getGameModel().getField().getWidth(), gl.getGameModel().getField().getHeight());
      primaryStage.setScene(scene);
      primaryStage.setMaximized(true);
      primaryStage.show();
  }

}