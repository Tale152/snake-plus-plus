package implementation.controller.application;

import java.io.IOException;

import design.controller.application.GameEndController;
import design.controller.application.GameEndReason;
import implementation.view.application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

public class GameEndControllerImpl implements GameEndController {
	
	private final static String win = "You won!";
	private final static String loss = "You lost!";
	private final static String error = "I am error";
	
	private final static String reasons[][] = {
			{win, "You reached the required length!"},
			{win, "You survived until the end!"},
			{win, "You reached the required score!"},
			
			{loss, "You died!"},
			{loss, "You failed to complete the objective in time!"},
			
			{error, ""}
	};
	
	@FXML
	private Label title;
	
	@FXML
	private Label reason;

	@Override
	public void setEndReason(GameEndReason endReason) {
		Platform.runLater(() -> {
			title.setText(reasons[endReason.ordinal()][0]);
			reason.setText(reasons[endReason.ordinal()][1]);
		});
	}
	
	public void goToMainMenu() {
		FXMLLoader mainMenu = new FXMLLoader(getClass().getResource("/implementation/view/application/MainMenuView.fxml"));
		try {
			Main.getScene().setRoot(mainMenu.load());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
