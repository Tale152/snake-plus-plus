package implementation.model.game.items;

import java.util.Optional;
import design.model.game.Field;
import design.model.game.Snake;

public class Apple extends EffectAbstract{

	public Apple(Optional<Long> dEffectDuration) {
		super(dEffectDuration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void instantaneousEffect(Snake target) {
		//TODO 
	}

	@Override
	public void expirationEffect(Field field) {
		//does nothing
	}

	@Override
	protected void behaviorOnLastingEffectStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void behaviorOnLastingEffectEnd() {
		// TODO Auto-generated method stub
		
	}
}
