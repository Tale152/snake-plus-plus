package implementation.controller.application;

import java.util.Optional;

import design.controller.application.DeviceType;
import design.controller.application.InputEvent;
import javafx.scene.input.KeyEvent;

public class InputEventFX implements InputEvent {
	
	private final String key;

	@Override
	public DeviceType getDeviceType() {
		return DeviceType.KEYBOARD;
	}

	@Override
	public Optional<Long> getDeviceID() {
		return Optional.empty();
	}

	@Override
	public String getInput() {
		return key;
	}
	
	public InputEventFX(KeyEvent e) {
		this.key = e.getText();
	}

}
