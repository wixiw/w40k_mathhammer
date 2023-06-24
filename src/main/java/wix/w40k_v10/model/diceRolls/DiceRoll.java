/**
 * 
 */
package wix.w40k_v10.model.diceRolls;

import java.security.InvalidParameterException;

import wix.w40k_v10.model.types.RerollBehavior;

/**
 * @author wix This class represent a dice roll of the same dice types.
 *         It represent the dice results under the form of a
 *         n-row table, where N is the number of face of the dice. Each row
 *         represents the number of times a dice value has been rolled. The
 *         number of times may be a floating number to allow statistical
 *         computation.
 * 
 *         For instance, a 3 faces dice (faces 1,2,3) will be represented with
 *         an array of size 3.
 *         The result of 11 rolls that produce 2 times a face "1", 4 times a
 *         face "2", and 5 times a face "3"
 *         is represented as [2,4,5]
 * 
 *         For instance, representing a 6 dice statistics can be done with [1/6,
 *         1/6, 1/6, 1/6, 1/6, 1/6]
 *         Representing the chance to roll 3+ would be the sum of [0, 0, 1/6,
 *         1/6, 1/6]
 */
public class DiceRoll {
    /**
     * Contain the number of occurrence of each dice face in a dices roll
     */
    private double[] results;

    /**
     * Constructor
     * 
     * @param diceFaces
     *                  : number of faces of the dice that can be launched
     */
    public DiceRoll(final int diceFaces) {
        results = new double[diceFaces];
    }

    /**
     * Constructor
     * 
     * @param values
     *               : a table with each row representing a dice face, and the
     *               count of results of that value
     */
    public DiceRoll(final double[] values) {
        results = values;
    }

    /**
     * Set the number of time of face appear in the roll
     * 
     * @param face
     *               : face to set
     * @param result
     *               : number of time of apperance to set
     */
    public void setCountsForFace(int face, double result) {
        results[face - 1] = result;
    }

    /**
     * Initialize the DiceResult with 0 if the face is strictly lower than
     * "face" and to "value" if the face is greather or equal to "face"
     * 
     * @param face
     * @param value
     */
    public void resetForAtLeast(int face, double value) {
        for (int i = 1; i <= results.length; i++) {
            if (i < face)
                setCountsForFace(i, 0);
            else
                setCountsForFace(i, value);
        }
    }

    /**
     * Get the number of success associated to a face after a roll
     * 
     * @param face
     * @return the number of times the face appeared
     */
    public double getCountForFace(int face) {
        return results[face - 1];
    }

    /**
     * Get the list of result for all faces
     * @return
     */
    public double[] getCounts(){
        return results.clone();
    }

    /**
     * Merge two dice rolls Throw an exception is dice rolls are not for the
     * same dice types
     * 
     * @param the
     *            dice roll to add to this one
     */
    public void combineRolls(DiceRoll roll) {
        if (results.length != roll.results.length)
            throw new InvalidParameterException();

        for (int i = 0; i < results.length; i++) {
            results[i] += roll.results[i];
        }
    }

    /**
     * Count the number of success, knowing that a 1 is always a failure
     * 
     * @param atLeast
     *                : the minimal (or equal) value that defines a success
     * @return the number of successful rolls
     */
    public double countSuccess(int atLeast) {
        double success = 0;
        // a one is always a failure
        for (int i = 2; i <= results.length; i++) {
            if (atLeast <= i)
                success += getCountForFace(i);
        }

        return success;
    }

    /**
     * Count the number of failures, knowing that a 1 is always a failure
     * 
     * @param atLeast
     *                : the minimal (or equal) value that defines a success
     * @return the number of failure rolls (strictly less than 'atLeast'
     *         parameter)
     */
    public double countFailures(int atLeast) {
        double failures = 0;
        for (int i = 1; i <= results.length; i++) {
            if (i < atLeast || i == 1 /* ones are always a failure */)
                failures += getCountForFace(i);
        }

        return failures;
    }

    /**
     * Reroll somes dices as configured by behavior
     * @param behavior \see RerollBehavior class
     */
    public void reroll(RerollBehavior behavior) {
        double nbDicesToReroll = 0;
        DiceRoll rerolls;

        // If allowed, reroll additional attacks failed dices
        switch (behavior.method) {
            case ResultOf:
                //Identify rolls of a face and remove them from the roll
                nbDicesToReroll = getCountForFace(behavior.value);
                setCountsForFace(behavior.value, 0);
        
                // reroll dices
                rerolls = statsRollD6(nbDicesToReroll);
                
                //Add rerolled dices to the pool
                combineRolls(rerolls);
                break;

            case FailForAtLeast:
                // Identify failed dices and remove them from the roll
                for (int i = 1; i <= results.length; i++) {
                    if (i < behavior.value || i == 1 /* ones are always a failure */) {
                        nbDicesToReroll += getCountForFace(i);
                        setCountsForFace(i, 0);
                    }
                }
        
                // reroll dices
                rerolls = statsRollD6(nbDicesToReroll);
        
                //Add rerolled dices to the pool
                combineRolls(rerolls);

            case Nothing:
                break;

            default:
                throw new UnsupportedOperationException();
        }
    }

    /**
     * Reroll dices that didn't reach a certain value, knowing that a 1 is
     * always a failure
     * 
     * @param keepValue
     *                : the minimal (or equal) value that defines a kept dice. Dice results striclty
     *                below that value are rerolled
     * @return the number of failure on first roll (strictly less than 'atLeast'
     *         parameter)
     */
    protected double rerollDicesStrictBelow(int keepValue) {
        double failures = 0;

        // Identify failed dices and remove them from the roll
        for (int i = 1; i <= results.length; i++) {
            if (i < keepValue || i == 1 /* ones are always a failure */) {
                failures += getCountForFace(i);
                setCountsForFace(i, 0);
            }
        }

        // reroll dices
        DiceRoll rerolls = statsRollD6(failures);

        //Add rerolled dices to the pool
        combineRolls(rerolls);

        return failures;
    }

    /**
     * Roll several D6 with random results
     * 
     * @param count
     *              number of D6 launched
     * @return a *random* evaluation of the launch
     */
    public static DiceRoll randomRollD6(double count) {
        throw new UnsupportedOperationException();// TODO implement associated
    }

    /**
     * Roll several D6 with uniform results (stat roll)
     * Rolling six dices will prouduce one "1", one "2", one "3", ...
     * 
     * @param count
     *              number of D6 launched
     * @return a *statistical* evaluation of the launch
     */
    public static DiceRoll statsRollD6(double count) {
        return new DiceRoll(new double[] { count / 6., count / 6., count / 6., count / 6., count / 6., count / 6. });
    }
}
