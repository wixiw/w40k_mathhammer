/**
 * 
 */
package wix.w40k_v8.model.diceRolls;

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
    
    /** Constant representing the reroll capacities*/
    public enum Reroll {
	Nothing,
	Ones, 
	MayAll,
	MustAll
    }
    
    /** Memorize if the skill allows to reroll */
    private Reroll rerolls;
    
    /**
     * Constructor
     * @param skillValue : the dice value to reach at least (or equal) to define a success
     * @param skillRerolls : the capacity to reroll fail tests, see RN, R1, RE
     */
    public Skill(int skillValue, Reroll skillRerolls) {
	value = skillValue;
	rerolls = skillRerolls;
    }
    
    /**
     * Constructor
     * @param skillValue : the dice value to reach at least (or equal) to define a success
     */
    public Skill(int skillValue) {
	this(skillValue, Reroll.Nothing);
    }

    public int getValue() {
	return value;
    }
    
    public Reroll getRerolls() {
	return rerolls;
    }
}
