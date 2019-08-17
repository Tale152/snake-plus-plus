package design.controller.application;

public interface StageSelectionController {
    /**
     * Sets the skin that will be used for snakes, walls and items.
     * @param path where to find the graphical resources to load
     */
    void setSkinPackPath(String path);
}
