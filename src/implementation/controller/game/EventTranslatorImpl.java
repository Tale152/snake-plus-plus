package implementation.controller.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import design.controller.game.Action;
import design.controller.game.EventTranslator;
import design.controller.game.InputEvent;
import design.model.game.Direction;
import design.model.game.PlayerNumber;
import javafx.scene.input.KeyCode;

/**
 * @see EventTranslator
 * @author Nicola Orlando
 *
 */
public final class EventTranslatorImpl implements EventTranslator {

    private final Map<InputEvent, Action> bindings;

    @Override
    public boolean setBinding(final InputEvent e, final Action a) {
        if (this.getEventBinding(e).isPresent() || this.getActionBinding(a).isPresent()) {
            return false;
        }
        bindings.put(e, a);
        return true;
    }

    @Override
    public void addBindingMap(final Map<InputEvent, Action> m) {
        m.entrySet().stream().forEach(e -> {
            this.setBinding(e.getKey(), e.getValue());
        });
    }

    @Override
    public Optional<Action> getEventBinding(final InputEvent e) {
        return Optional.ofNullable(bindings.get(e));
    }

    @Override
    public Optional<InputEvent> getActionBinding(final Action a) {
        return bindings.entrySet().stream().filter(e -> e.getValue().equals(a)).map(Map.Entry::getKey).findAny();
    }

    @Override
    public Map<InputEvent, Action> getBindingMap() {
        return new HashMap<>(bindings);
    }

    @Override
    public void clearEventBinding(final InputEvent e) {
        bindings.remove(e);
    }

    @Override
    public void clearActionBinding(final Action a) {
        if (this.getActionBinding(a).isPresent()) {
            bindings.remove(this.getActionBinding(a).get());
        }
    }

    @Override
    public void clearBindings() {
        bindings.clear();
    }

    @Override
    public Optional<Action> translateInput(final InputEvent e) {
        return Optional.ofNullable(bindings.get(e));
    }

    /**
     * Creates an EventTranslator with default bindings. For custom controls, clear and add a binding map.
     */
    public EventTranslatorImpl() {
        bindings = new HashMap<InputEvent, Action>();

        setBinding(new InputEventFX(KeyCode.W), new ActionImpl(PlayerNumber.PLAYER1, Direction.UP));
        setBinding(new InputEventFX(KeyCode.S), new ActionImpl(PlayerNumber.PLAYER1, Direction.DOWN));
        setBinding(new InputEventFX(KeyCode.A), new ActionImpl(PlayerNumber.PLAYER1, Direction.LEFT));
        setBinding(new InputEventFX(KeyCode.D), new ActionImpl(PlayerNumber.PLAYER1, Direction.RIGHT));

        setBinding(new InputEventFX(KeyCode.UP), new ActionImpl(PlayerNumber.PLAYER2, Direction.UP));
        setBinding(new InputEventFX(KeyCode.DOWN), new ActionImpl(PlayerNumber.PLAYER2, Direction.DOWN));
        setBinding(new InputEventFX(KeyCode.LEFT), new ActionImpl(PlayerNumber.PLAYER2, Direction.LEFT));
        setBinding(new InputEventFX(KeyCode.RIGHT), new ActionImpl(PlayerNumber.PLAYER2, Direction.RIGHT));

        setBinding(new InputEventFX(KeyCode.I), new ActionImpl(PlayerNumber.PLAYER3, Direction.UP));
        setBinding(new InputEventFX(KeyCode.K), new ActionImpl(PlayerNumber.PLAYER3, Direction.DOWN));
        setBinding(new InputEventFX(KeyCode.J), new ActionImpl(PlayerNumber.PLAYER3, Direction.LEFT));
        setBinding(new InputEventFX(KeyCode.L), new ActionImpl(PlayerNumber.PLAYER3, Direction.RIGHT));

        setBinding(new InputEventFX(KeyCode.NUMPAD8), new ActionImpl(PlayerNumber.PLAYER4, Direction.UP));
        setBinding(new InputEventFX(KeyCode.NUMPAD5), new ActionImpl(PlayerNumber.PLAYER4, Direction.DOWN));
        setBinding(new InputEventFX(KeyCode.NUMPAD4), new ActionImpl(PlayerNumber.PLAYER4, Direction.LEFT));
        setBinding(new InputEventFX(KeyCode.NUMPAD6), new ActionImpl(PlayerNumber.PLAYER4, Direction.RIGHT));
    }
}
