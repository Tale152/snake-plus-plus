package implementation.controller.application;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import design.controller.application.Action;
import design.controller.application.EventTranslator;
import design.controller.application.InputEvent;

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
	}

}