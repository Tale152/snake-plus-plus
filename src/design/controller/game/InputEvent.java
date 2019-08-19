package design.controller.game;

import java.util.Optional;

/**
 * Represents an input to be associated to an action to be performed by a snake.
 * @see DeviceType
 * @see EventTranslator
 */
public interface InputEvent {

    /**
     * @return An enum specifying the kind of device which generated the event.
     */
    DeviceType getDeviceType();

    /**
     * @return An ID for the device, if it exists. Useful to tell different gamepads apart.
     */
    Optional<Long> getDeviceID();

    /**
     * @return The actual (or simulated) key pressed.
     */
    String getInput();

}
