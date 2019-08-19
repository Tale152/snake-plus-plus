package design.model.game;

/**
 * A game model is used as a container to store everything that a level is composed.<p>
 * In other words this contains how the field is composed, what items will spawn, 
 * what rules will be applied, etc... 
 */
public interface GameModel {

    /**
     * @return the field composing this level
     */
    Field getField();

    /**
     * @return the set of rules governing this level
     */
    GameRules getGameRules();

}
