package design.view;

import java.util.List;
import java.util.Optional;

public interface GameHud {
	
	public void setTopBackground(Background bg);
	
	public Optional<Background> getTopBackground();
	
	public void setBottomBackground(Background bg);
	
	public Optional<Background> getBottomBackground();
	
	public void setLeftBackground(Background bg);
	
	public Optional<Background> getLeftBackground();
	
	public void setRightBackground(Background bg);
	
	public Optional<Background> getRightBackground();
	
	public void setTime(Long time);
	
	public Long getTime();
	
	public void setPlayerName(int playerNumber, String name);
	
	public List<String> getPlayerNames();
	
	public void setPlayerScore(int playerNumber, int score);
	
	public List<Integer> getPlayerScores();
	
	public void setPlayerDeadStatus(int playerNumber, boolean deadStatus);
	
	public List<Boolean> getPlayerDeadStatus();
	
}
