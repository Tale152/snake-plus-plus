package implementation.controller.application;

import java.util.Map;
import java.util.Optional;

import design.controller.application.Action;
import design.controller.application.EventTranslator;
import design.controller.application.InputEvent;

public class EventTranslatorImpl implements EventTranslator {

	@Override
	public boolean setBinding(InputEvent e, Action a) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addBindingMap(Map<InputEvent, Action> m) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Action> getEventBinding(InputEvent e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<InputEvent> getActionBinding(Action a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<InputEvent, Action> getBindingMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearEventBinding(InputEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearActionBinding(Action a) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Action> translateInput(InputEvent e) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public EventTranslatorImpl() {
		
	}

}
