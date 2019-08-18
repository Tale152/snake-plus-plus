package design.controller.game;

/**
 * Enum representing various types of device. Useful for input differentiation.
 * @see EventTranslator
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 */
public enum DeviceType {
    /**
     * Physical keyboard.
     */
    KEYBOARD,
    /**
     * Controller or other game input device.
     */
    CONTROLLER,
    /**
     * Controls on a touch screen.
     */
    TOUCH_CONTROLS;
}
