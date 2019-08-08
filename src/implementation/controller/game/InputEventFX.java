package implementation.controller.game;

import java.util.Optional;

import design.controller.game.DeviceType;
import design.controller.game.InputEvent;
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
	
	public int hashCode() {
		//TODO: Return a proper hashcode
		return getInput().hashCode();
	}

	public boolean equals(InputEvent input) {
		boolean isKeyEqual = input.getInput().contentEquals(getInput());

		boolean isDeviceTypeEqual = input.getDeviceType().equals(getDeviceType());

		boolean isDeviceIdEqual = false;
		if (getDeviceID().isPresent() && input.getDeviceID().isPresent()) {
			isDeviceIdEqual = getDeviceID().get().equals(input.getDeviceID().get());
		} else if (!getDeviceID().isPresent() && !input.getDeviceID().isPresent()) {
			isDeviceIdEqual = true;
		}
		return  isKeyEqual && isDeviceIdEqual && isDeviceTypeEqual;
	}

	@Override
	public boolean equals(Object o) {
		return o.getClass().equals(this.getClass()) && this.equals((InputEvent) o);
	}

	public InputEventFX(KeyEvent e) {
		this.key = e.getCode().toString();
	}

	public InputEventFX(String k) {
		this.key = k;
	}
}
