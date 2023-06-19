/**
 * 
 */
package wix.w40k_v10.model.diceRolls;

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
    
    /** Memorize if the skill allows to reroll */
    private DiceRoll.RerollType rerolls;
    
    /**
     * Constructor
     * @param skillValue : the dice value to reach at least (or equal) to define a success
     * @param skillRerolls : the capacity to reroll fail tests, see RN, R1, RE
     * @throw IllegalArgumentException if skill value is out of [2;6]
     */
    public Skill(int skillValue, DiceRoll.RerollType skillRerolls) {
	if( skillValue < 2  && 6 < skillValue)
		throw new IllegalArgumentException();
	value = skillValue;
	rerolls = skillRerolls;
    }
    
    /**
     * Constructor
     * @param skillValue : the dice value to reach at least (or equal) to define a success
     */
    public Skill(int skillValue) {
	this(skillValue, DiceRoll.RerollType.Nothing);
    }

    public int getValue() {
	return value;
    }
    
    public DiceRoll.RerollType getRerolltype() {
	return rerolls;
    }
}
