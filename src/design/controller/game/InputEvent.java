package design.controller.game;

import java.util.Optional;

public interface InputEvent {

    DeviceType getDeviceType();

    Optional<Long> getDeviceID();

    String getInput();

}
