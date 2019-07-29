import java.io.File;
import java.util.Arrays;
import implementation.controller.application.SettingsControllerImpl;
import implementation.view.game.GameViewImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{

  public static int NCELLWIDTH = 30;
  public static int NCELLHEIGHT = 20;
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
      GameViewImpl gw = new GameViewImpl(scene, "res/stages/classic/1.json", PATH, Arrays.asList("LASERPE"), NCELLWIDTH, NCELLHEIGHT);
      primaryStage.setScene(scene);
      primaryStage.setMaximized(true);
      primaryStage.show();
  }

}