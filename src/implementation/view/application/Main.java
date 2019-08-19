package implementation.view.application;
import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Application's entry point.
 */
public class Main extends Application {

    private static final double SCREEN_PERCENTAGE = 0.75;
    private static Scene scene = new Scene(new BorderPane());
    private Dimension stageDimension;

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
            stageDimension = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
            stageDimension.setSize(stageDimension.getWidth() * SCREEN_PERCENTAGE, stageDimension.getHeight() * SCREEN_PERCENTAGE);
            scene = new Scene(root, 
                    stageDimension.getWidth(), 
                    stageDimension.getHeight());
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Snake++");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
