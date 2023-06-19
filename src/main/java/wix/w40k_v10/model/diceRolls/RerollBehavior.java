package wix.w40k_v10.model.diceRolls;

/**
 * Represent a mean to reroll dices
 */
public class RerollBehavior {
    /** Constant representing the reroll behavior */
    public enum Method {
        Nothing,        //reroll nothing
        ResultOf,       //reroll result of "value"
        FailForAtLeast, //reroll dices strictly below a "value" or plus success
    }

    public Method method;
    public int value;

    public RerollBehavior(Method _method, int _value){
        method = _method;
        value = _value;
    }
}