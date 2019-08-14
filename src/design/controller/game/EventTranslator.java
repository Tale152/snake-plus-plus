package design.controller.game;

import java.util.Map;
import java.util.Optional;

/**
 * Provides a translation layer between physical input events and actions
 * performed by snakes.
 * @author Alessandro Talmi
 * @author Elisa Tronetti
 * @author Nicola Orlando
 */
interface EventTranslator {
    /**
     * Sets an action to be performed when the corresponding input is received.
     * @param e The physical input event.
     * @param a The action to be performed.
     * @return True if the binding can be set, false otherwise.
     */
    boolean setBinding(InputEvent e, Action a);

    /**
     * Provide a map between input events and actions to perform.
     * @param m A map containing the action corresponding to the provided input.
     */
    void addBindingMap(Map<InputEvent, Action> m);

    /**
     * Returns the action (if any) performed on the provided event.
     * @param e The physical input event to be checked.
     * @return The action to be performed.
     */
    Optional<Action> getEventBinding(InputEvent e);

    /**
     * Returns the event (if any) causing the provided action.
     * @param a The action to be checked.
     * @return The event that triggers it.
     */
    Optional<InputEvent> getActionBinding(Action a);

    /**
     * Returns a map containing the input events and actions to perform.
     * @return A map containing the input events and actions to perform.
     */
    Map<InputEvent, Action> getBindingMap();

    /**
     * Clear the action (if any) assigned to the provided input event.
     * @param e The input event.
     */
    void clearEventBinding(InputEvent e);

    /**
     * Clear the event (if any) causing the provided action.
     * @param a The action.
     */
    void clearActionBinding(Action a);

    /**
     * Translate the provided input into the action to be performed (if any).
     * @param e The input received.
     * @return The action to be performed.
     */
    Optional<Action> translateInput(InputEvent e);

}
