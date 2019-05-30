package implementation.controller.game;

import java.util.List;
import java.util.Optional;

import design.controller.game.Action;
import design.controller.game.EventTranslator;
import design.controller.game.GameController;
import design.controller.game.InputEvent;
import design.controller.game.ItemCounter;
import design.model.game.DirectionProperty;
import design.model.game.GameModel;
import design.model.game.Item;
import design.model.game.ItemRule;
import design.model.game.LossConditions;
import design.model.game.Snake;
import design.model.game.WinConditions;
import design.view.game.GameView;
import design.view.game.ResourcesLoader;
import implementation.model.game.items.ItemFactory;

public class GameControllerImpl implements GameController {
	
	private final ItemCounter counter;
	private final GameView gameView;
	private final GameModel gameModel;
	private final ResourcesLoader resources;
	private final EventTranslator controls;
	private final ItemFactory itemFactory;
	
	
	public GameControllerImpl(String stage, List<String> playerNames, GameView view, ResourcesLoader resources) {
		
		
	}

	@Override
	public void run() {
		this.gameModel.getField().begin();
		while(!isGameEnded()) {
			updateDeletedItems();
			spawnItems();
		}

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
	
	private boolean isGameEnded() {
		WinConditions win = this.gameModel.getGameRules().getWinConditions();
		LossConditions loss = this.gameModel.getGameRules().getLossConditions();
		List<Snake> snakes = this.gameModel.getField().getSnakes();
		Long time = System.currentTimeMillis();
		return loss.checkSnakes(snakes) || loss.checkTime(time) ||
				win.checkScore(snakes) || win.checkSnakeLength(snakes) || win.checkTime(time);
	}
	
	private void updateDeletedItems() {
		List<Item> deletedItems = this.gameModel.getField().getEliminatedItems();
		for(Item i : deletedItems) {
			this.counter.decrease(i.getEffectClass());
			this.gameView.getField().removeItemSprite(i.getPoint(), this.resources.getItem(i.getClass().getSimpleName()));
		}
	}
	
	private void spawnItems() {
		for(ItemRule rule : this.gameModel.getGameRules().getItemRules()) {
			if(!this.counter.isMax(rule.getEffectClass())) {
				
			}
		}
	}

}
