package design.controller.application;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ClassicController extends StageSelectionController {
    void selectPrev() throws FileNotFoundException, IOException;
    void selectNext() throws FileNotFoundException, IOException;
    void removePlayer();
    void addPlayer();
    void startSelectedLevel() throws IOException;
}
