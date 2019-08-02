package implementation.view.application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{

  private static Scene scene = new Scene(new BorderPane());
  
  public static void main(String[] args) {
    Application.launch(Main.class, args);
  }
  
  public static Scene getScene() {
	  return scene;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
	  
	  primaryStage.setOnCloseRequest(e -> {System.exit(0);});
	  try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("MainMenuView.fxml"));
			scene = new Scene(root, 800, 600);
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.setTitle("Snake++");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
  }

}