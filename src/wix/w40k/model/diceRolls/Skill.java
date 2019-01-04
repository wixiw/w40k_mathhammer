/**
 * 
 */
package wix.w40k.model.diceRolls;

/**
 * @author wix
 *
 * A Skill represent a characteristic of a model. It is the minimal result
 * a D6 should reach (at least) to validate the action
 * 
 * A skill can be associated to reroll of some values
 */
public class Skill {
    /** Skill characteristic value*/
    private int value;
    
    /** Constant representing "Reroll nothing" */
    public static final int RN = 0; 
    /** Constant representing "Reroll ones" */
    public static final int R1 = 1; 
    /** Constant representing "Reroll missed" */
    public static final int RE = 2;
    /** Memorize if the skill allows to reroll */
    private int rerolls;
    
    /** set to true if the reroll capacity is optional */
    private boolean rerollOptional;
    
    /**
     * Constructor
     * @param skillValue : the dice value to reach at least (or equal) to define a success
     * @param skillRerolls : the capacity to reroll fail tests, see RN, R1, RE
     * @param optionalReroll : the capacity to choose to reroll or not (may be important with bonus)
     */
    public Skill(int skillValue, int skillRerolls, boolean optionalReroll) {
	value = skillValue;
	rerolls = skillRerolls;
	rerollOptional = optionalReroll;
    }
    
    /**
     * Constructor
     * @param skillValue : the dice value to reach at least (or equal) to define a success
     * @param skillRerolls : the capacity to reroll fail tests, see RN, R1, RE
     */
    public Skill(int skillValue, int skillRerolls) {
	this(skillValue, skillRerolls, true);
    }
    
    /**
     * Constructor
     * @param skillValue : the dice value to reach at least (or equal) to define a success
     */
    public Skill(int skillValue) {
	this(skillValue, RN, true);
    }

    public int getValue() {
	return value;
    }
    
    public int getRerolls() {
	return rerolls;
    }
    
    public boolean isRerollOptional() {
	return rerollOptional;
    }

}
