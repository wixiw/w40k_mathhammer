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
    
    /**
     * 
     */
    public Skill(int skillValue, int skillRerolls) {
	value = skillValue;
	rerolls = skillRerolls;
    }
    
    public Skill(int skillValue) {
	this(skillValue, RN);
    }

    public int getValue() {
	return value;
    }

}
