package design.model.game;

import java.util.List;

public interface LossConditions {
	
	public boolean checkSnakes(List<Snake> snakes);
	
	public boolean checkTime(Long time);

}
