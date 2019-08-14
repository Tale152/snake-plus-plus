package design.controller.game;

import java.util.Optional;

public interface InputEvent {
    
    public DeviceType getDeviceType();
    
    public Optional<Long> getDeviceID();
    
    public String getInput();
    
}