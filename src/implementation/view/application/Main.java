package implementation.view.application;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Application's entry point.
 */
public class Main extends Application {

    private static final double SCREEN_PERCENTAGE = 0.75;
    private static Scene scene = new Scene(new BorderPane());

    /**
     * Main method, launches it's thread.
     */
    public static void main() {
    Application.launch(Main.class);
    }

    /**
     * @return the javafx's scene
     */
    public static Scene getScene() {
        return scene;
    }

    @Override
    public final void start(final Stage primaryStage) throws Exception {
        primaryStage.setOnCloseRequest(e -> {
            System.exit(0); });
        try {
            final BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("MainMenuView.fxml"));
            scene = new Scene(root, 
                    Toolkit.getDefaultToolkit().getScreenSize().getWidth() * SCREEN_PERCENTAGE, 
                    Toolkit.getDefaultToolkit().getScreenSize().getHeight() * SCREEN_PERCENTAGE);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(final KeyEvent arg0) {
                    if (arg0.getCode().equals(KeyCode.ESCAPE)) {
                        System.exit(0);
                    }
                }
            });
            primaryStage.setTitle("Snake++");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
  }

}
