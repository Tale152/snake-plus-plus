package implementation.controller.application;

import java.util.Optional;

import design.controller.application.DeviceType;
import design.controller.application.InputEvent;
import javafx.scene.input.KeyEvent;

public class InputEventFX implements InputEvent {

	@Override
	public DeviceType getDeviceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Long> getDeviceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInput() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public InputEventFX(KeyEvent e) {
		
	}

}
