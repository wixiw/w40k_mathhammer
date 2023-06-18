/**
 * 
 */
package wix.w40k_v10.model.diceRolls;

/**
 * @author wix This class statistically roll n-faces dices. All dices in the
 *         roll have the same number of faces
 */
public class StatisticalRoll {

    /** Number of faces of dices in the roll */
    private int faces;

    /** Hit modifier */
    protected int mod;
    
    /** Minimal value of a dice to define a success */
    protected Skill success;

    /**
     * 
     * @param diceFacesCount
     */
    public StatisticalRoll(int diceFacesCount) {
	faces = diceFacesCount;
    }

    /**
     * Dice result modifier (+1, -1, ...)
     * 
     * @param modifier
     */
    public void setModifier(int modifier) {
	mod = modifier;
    }

    /**
     * Set the minimal value of the dice to define a success
     * @param s
     */
    public void setSuccess(Skill s) {
	success = s;
    }
    
    /**
     * Roll several dices. Depending on previous configuration, some dices may
     * be rerolled against a success value, then the results can be modified
     * (after the rerolls)
     */
    public DiceRoll roll(double diceCount) {
	return new DiceRoll(faces);
    }

}
