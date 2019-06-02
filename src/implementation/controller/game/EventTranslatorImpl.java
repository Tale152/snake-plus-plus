package implementation.controller.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import design.controller.game.Action;
import design.controller.game.EventTranslator;
import design.controller.game.InputEvent;
import design.model.game.Direction;
import design.model.game.PlayerNumber;

public class EventTranslatorImpl implements EventTranslator {
	
	private final Map<InputEvent,Action> bindings;

	@Override
	public boolean setBinding(InputEvent e, Action a) {
		if (this.getEventBinding(e).isPresent() | this.getActionBinding(a).isPresent()) {
			return false;
		}
		bindings.put(e, a);
		return true;
	}

	@Override
	public void addBindingMap(Map<InputEvent, Action> m) {
		m.entrySet().stream().forEach(e -> {
			this.setBinding(e.getKey(), e.getValue());
		});

	}

	@Override
	public Optional<Action> getEventBinding(InputEvent e) {
		return Optional.ofNullable(bindings.get(e));
	}

	@Override
	public Optional<InputEvent> getActionBinding(Action a) {
		return bindings.entrySet().stream().filter(e -> e.getValue().equals(a)).map(Map.Entry::getKey).findAny();
	}

	@Override
	public Map<InputEvent, Action> getBindingMap() {
		return new HashMap<>(bindings);
	}

	@Override
	public void clearEventBinding(InputEvent e) {
		bindings.remove(e);
	}

	@Override
	public void clearActionBinding(Action a) {
		if (this.getActionBinding(a).isPresent()) {
			bindings.remove(this.getActionBinding(a).get());
		}
	}

	@Override
	public Optional<Action> translateInput(InputEvent e) {
		return Optional.ofNullable(bindings.get(e));
	}
	
	
	public EventTranslatorImpl() {
		bindings = new HashMap<InputEvent,Action>();
		// Default bindings. TODO: read from file
		setBinding(new InputEventFX("w"), new ActionImpl(PlayerNumber.PLAYER1, Direction.UP));
		setBinding(new InputEventFX("s"), new ActionImpl(PlayerNumber.PLAYER1, Direction.DOWN));
		setBinding(new InputEventFX("a"), new ActionImpl(PlayerNumber.PLAYER1, Direction.LEFT));
		setBinding(new InputEventFX("d"), new ActionImpl(PlayerNumber.PLAYER1, Direction.RIGHT));
	}

}