package design.controller.application;

import design.model.game.Direction;
import design.model.game.PlayerNumber;

public interface SettingsController {
	//nota per eli: per impostare un binding, puoi aggiungere alla scena un listener/handler;
	//dentro il listener/handler, dopo aver gestito il tasto premuto, puoi fare
	//scene.removeEventHandler(KeyEvent.KEY_PRESSED, this) per far sì che si rimuova da solo
	public boolean setPlayerBinding(PlayerNumber n, Direction dir);
}
