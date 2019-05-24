package implementation.controller.game;

import java.util.List;
import java.util.Optional;

import design.controller.game.Action;
import design.controller.game.EventTranslator;
import design.controller.game.GameController;
import design.controller.game.GameLoader;
import design.controller.game.InputEvent;
import design.controller.game.ItemCounter;
import design.model.game.DirectionProperty;
import design.model.game.GameModel;
import design.model.game.Snake;
import design.view.game.GameView;
import design.view.game.ResourcesLoader;

public class GameControllerImpl implements GameController {
	
	private final ItemCounter gameItems;
	private final GameView gameView;
	private final GameModel gameModel;
	private final ResourcesLoader resources;
	private final EventTranslator controls;
	
	
	public GameControllerImpl(String stage, List<String> playerNames, GameView view, ResourcesLoader resources) {
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void playerInput(InputEvent input) {
		// TODO Auto-generated method stub
		Optional<Action> action = controls.getEventBinding(input);
		if (action.isPresent()) {
			Snake target = gameModel.getField().getSnakes().get(action.get().getPlayerNumber().ordinal());
			DirectionProperty direction = target.getProperties().getDirectionProperty();
			direction.setDirection(action.get().getDirection());
		}
	}

}
