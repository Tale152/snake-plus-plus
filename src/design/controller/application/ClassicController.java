package design.controller.application;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ClassicController {
	public void selectPrev() throws FileNotFoundException, IOException;
	public void selectNext() throws FileNotFoundException, IOException;
	public void removePlayer();
	public void addPlayer();
	public void startSelectedLevel() throws IOException;
}
