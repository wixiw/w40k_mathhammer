/**
 * 
 */
package wix.w40k.model.diceRolls;

import java.security.InvalidParameterException;

/**
 * @author wix
 *
 */
public class DiceResults {
    
    public static final DiceResults D6_ROLL = new DiceResults(new double[]{1/6., 1/6., 1/6., 1/6., 1/6., 1/6.});
    
    /**
     * Contain the number of occurrence of each dice face in a dices roll
     */
    private double[] results;

    /**
     * Constructor
     * @param diceFaces
     *            : number of faces of the dice
     */
    public DiceResults(final int diceFaces) {
	results = new double[diceFaces];
    }

    /**
     * Constructor
     * @param values
     *            : a table with each row representing a dice face, and the
     *            result associated to it
     */
    public DiceResults(final double[] values) {
	results = values;
    }

    /**
     * Set the number of time of face appear in the roll
     * @param face : face to set
     * @param result : number of time of apperance to set
     */
    public void setResultsForFace(int face, double result) {
	results[face - 1] = result;
    }

    /**
     * Initialize the DiceResult with 0 if the face is strictly lower than "face"
     * and to "value" if the face is greather or equal to "face"
     * 
     * @param face
     * @param value
     */
    public void setValueForAtLeast(int face, double value) {
	for(int i=1 ; i <= results.length ; i++)
	{
	    if( i < face)
		setResultsForFace(i, 0);
	    else
		setResultsForFace(i, value);
	}
    }

    /**
     * Get the number of success associated to a face after a roll
     * @param face
     * @return the number of times the face appeared
     */
    public double getResultsForFace(int face) {
	return results[face - 1];
    }
    
    /**
     * Merge two dice rolls
     * Throw an exception is dice rolls are not for the same dice types
     * @param the dice roll to add to this one
     */
    public void addRolls(DiceResults roll) {
	if(results.length != roll.results.length)
	    throw new InvalidParameterException();
	
	for(int i=0 ; i < results.length ; i++)
	{
	    results[i] += roll.results[i];
	}
    }
    
    /**
     * Count the number of success, knowing that a 1 is always a failure
     * @param atLeast : the minimal (or equal) value that defines a success
     * @return the number of successful rolls
     */
    public double countSuccess(int atLeast)
    {
	double success = 0;
	//a one is always a failure
	for(int i=2 ; i <= results.length ; i++)
	{
	    if(atLeast <= i)
		success += getResultsForFace(i);
	}
	
	return success;
    }
    
    /**
     * Count the number of failures, knowing that a 1 is always a failure
     * @param atLeast : the minimal (or equal) value that defines a success
     * @return the number of failure rolls (strictly less than 'atLeast' parameter)
     */
    public double countFailures(int atLeast)
    {
	double failures = 0;
	for(int i=1 ; i <= results.length ; i++)
	{
	    if(i < atLeast || i == 1 /*ones are always a failure*/)
		failures += getResultsForFace(i);
	}
	
	return failures;
    }
    
    /**
     * Reroll dices that didn't reach a certain value, knowing that a 1 is always a failure
     * @param atLeast : the minimal (or equal) value that defines a success. Dice below that are rerolled
     * @return the number of failure on first roll (strictly less than 'atLeast' parameter)
     */
    public double rerollDicesBelow(int atLeast)
    {
	double failures = 0;
	
	//Identify failed dices and remove them from the roll
	for(int i=1 ; i <= results.length ; i++)
	{
	    if(i < atLeast || i == 1 /*ones are always a failure*/)
	    {
		failures += getResultsForFace(i);
		setResultsForFace(i,0);
	    }
	}

	//reroll dices
	double rerollChance = failures / results.length;
	for(int i=1 ; i <= results.length ; i++)
	{
	    setResultsForFace(i,getResultsForFace(i)+rerollChance);
	}
	
	return failures;
    }

    /**
     * Roll several D6
     * @param count number of D6 launched
     * @return a *random* evaluation of the launch
     */
    public static DiceResults rollD6(double count) {
	throw new UnsupportedOperationException();// TODO implement associated
    }

    /**
     * Statistic value associated to the roll of several D6
     * @param count number of D6 launched
     * @return a *statistical* evaluation of the launch
     */
    public static DiceResults statsRollD6(double count) {
	return new DiceResults(new double[]{count/6., count/6., count/6., count/6., count/6., count/6.});
    }
}



