package design.controller.game;

import java.util.Map;
import java.util.Optional;

/**
 * Provides a translation layer between physical input events and actions
 * performed by snakes.
 * @see InputEvent
 * @see Action
 */
public interface EventTranslator {

/**
 * Sets an action to be performed when the corresponding input is received.
 * @param event The physical input event.
 * @param action The action to be performed.
 * @return True if the binding can be set, false otherwise.
 */
    boolean setBinding(InputEvent event, Action action);

/**
 * Provide a map between input events and actions to perform.
 * @param map A map containing the action corresponding to the provided input.
 */
    void addBindingMap(Map<InputEvent, Action> map);

/**
 * Returns the action (if any) performed on the provided event.
 * @param event The physical input event to be checked.
 * @return The action to be performed.
 */
    Optional<Action> getEventBinding(InputEvent event);

/**
 * Returns the event (if any) causing the provided action.
 * @param action The action to be checked.
 * @return The event that triggers it.
 */
    Optional<InputEvent> getActionBinding(Action action);

/**
 * Returns a map containing the input events and actions to perform.
 * @return A map containing the input events and actions to perform.
 */
    Map<InputEvent, Action> getBindingMap();

/**
 * Clear the action (if any) assigned to the provided input event.
 * @param event The input event.
 */
    void clearEventBinding(InputEvent event);

/**
 * Clear the event (if any) causing the provided action.
 * @param action The action.
 */
    void clearActionBinding(Action action);

/**
 * Remove all bindings.
 */
    void clearBindings();

/**
 * Translate the provided input into the action to be performed (if any).
 * @param event The input received.
 * @return The action to be performed.
 */
    Optional<Action> translateInput(InputEvent event);
}
