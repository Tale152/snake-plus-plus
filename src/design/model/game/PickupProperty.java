package design.model.game;

/**
 * The pickup property determinate how many cell will be affected while snake is trying to pickup items.
 * For example, having pickup property set to one means that snake will pickup items only from the cell
 * that will occupy (like normally would). Setting the property to 2 instead affect not only the next cell
 * but also everyone surrounding it.
 */
public interface PickupProperty {

    /**
     * @param radius determining how many cells will be affected by snake trying to pickup items
     */
    void setPickupRadius(int radius);

    /**
     * @return the radius of cells affected by the snake trying to pickup items
     */
    int getPickupRadius();

}
