/**
 * 
 */
package wix_tests.w40k_v10.model.diceRolls;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import wix.w40k_v10.model.diceRolls.DiceRoll;
import wix.w40k_v10.model.types.RerollBehavior;

/**
 * @author wix
 *
 */
public class UT_DiceRoll {
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    /**
     * Test method for {@link wix.w40k_v10.model.diceRolls.DiceRoll#getCountForFace(int)}.
     */
    @Test
    public void getResultsForFace() {
	
	DiceRoll dices = new DiceRoll(4);
	
	assertEquals(dices.getCountForFace(1), 0, 0.01);
	assertEquals(dices.getCountForFace(2), 0, 0.01);
	assertEquals(dices.getCountForFace(3), 0, 0.01);
	assertEquals(dices.getCountForFace(4), 0, 0.01);

	exception.expect(ArrayIndexOutOfBoundsException.class);
	dices.getCountForFace(5);
	dices.getCountForFace(0);
    }
    
    /**
     * Test method for {@link wix.w40k_v10.model.diceRolls.DiceRoll#setCountsForFace(int, double)}.
     */
    @Test
    public void setResultsForFace() {
	DiceRoll dices = new DiceRoll(4);
	
	dices.setCountsForFace(1, 7);
	dices.setCountsForFace(2, 9);
	dices.setCountsForFace(3, 11);
	dices.setCountsForFace(4, 13);
	
	assertEquals(dices.getCountForFace(1), 7, 0.01);
	assertEquals(dices.getCountForFace(2), 9, 0.01);
	assertEquals(dices.getCountForFace(3), 11, 0.01);
	assertEquals(dices.getCountForFace(4), 13, 0.01);
	
	exception.expect(ArrayIndexOutOfBoundsException.class);
	dices.setCountsForFace(0, 2);
	dices.setCountsForFace(5, 2);
    }

    /**
     * Test method for {@link wix.w40k_v10.model.diceRolls.DiceRoll#combineRolls(DiceRoll)}.
     */
    @Test
    public void addRolls() {
	DiceRoll dicesA = new DiceRoll(new double[]{1, 2, 5, 11, 23, 47});
	DiceRoll dicesB = new DiceRoll(new double[]{1, 2, 5, 11, 23, 47});

	//Check that dicesA has been summed
	dicesA.combineRolls(dicesB);
	assertEquals(dicesA.getCountForFace(1),    2, 0.01);
	assertEquals(dicesA.getCountForFace(2),    4, 0.01);
	assertEquals(dicesA.getCountForFace(3),    10, 0.01);
	assertEquals(dicesA.getCountForFace(4),    22, 0.01);
	assertEquals(dicesA.getCountForFace(5),    46, 0.01);
	assertEquals(dicesA.getCountForFace(6),    94, 0.01);
	//Check that dicesB didn't changed
	assertEquals(dicesB.getCountForFace(1),    1, 0.01);
	assertEquals(dicesB.getCountForFace(2),    2, 0.01);
	assertEquals(dicesB.getCountForFace(3),    5, 0.01);
	assertEquals(dicesB.getCountForFace(4),    11, 0.01);
	assertEquals(dicesB.getCountForFace(5),    23, 0.01);
	assertEquals(dicesB.getCountForFace(6),    47, 0.01);
	
	//Check exception on different dice type addition
	exception.expect(InvalidParameterException.class);
	DiceRoll dices4 = new DiceRoll(4);
	DiceRoll dices5 = new DiceRoll(5);
	dices4.combineRolls(dices5);
	dices5.combineRolls(dices4);
    }
    
    /**
     * Test method for {@link wix.w40k_v10.model.diceRolls.DiceRoll#resetForAtLeast(int, double)}.
     */
    @Test
    public void resetForAtLeast() {
	DiceRoll dices = new DiceRoll(new double[]{3, 3, 3, 3, 3, 3});
	dices.resetForAtLeast(4, 1.7);

	assertEquals(dices.getCountForFace(1),    0, 0.01);
	assertEquals(dices.getCountForFace(2),    0, 0.01);
	assertEquals(dices.getCountForFace(3),    0, 0.01);
	assertEquals(dices.getCountForFace(4),  1.7, 0.01);
	assertEquals(dices.getCountForFace(5),  1.7, 0.01);
	assertEquals(dices.getCountForFace(6),  1.7, 0.01);
    }

    /**
     * Test method for {@link wix.w40k_v10.model.diceRolls.DiceRoll#countSuccess(int)}.
     */
    @Test
    public void countSuccess() {
	DiceRoll dices = new DiceRoll(new double[]{1, 2, 5, 11, 23, 47});
	assertEquals(dices.countSuccess(-10),    2+5+11+23+47, 0.01);
	assertEquals(dices.countSuccess(0),      2+5+11+23+47, 0.01);
	assertEquals(dices.countSuccess(1),      2+5+11+23+47, 0.01);
	assertEquals(dices.countSuccess(2),      2+5+11+23+47, 0.01);
	assertEquals(dices.countSuccess(3),        5+11+23+47, 0.01);
	assertEquals(dices.countSuccess(4),          11+23+47, 0.01);
	assertEquals(dices.countSuccess(5),             23+47, 0.01);
	assertEquals(dices.countSuccess(6),                47, 0.01);
	assertEquals(dices.countSuccess(7),                 0, 0.01);
	assertEquals(dices.countSuccess(20),                0, 0.01);
    }

    /**
     * Test method for {@link wix.w40k_v10.model.diceRolls.DiceRoll#countFailures(int)}.
     */
    @Test
    public void countFailures() {
	DiceRoll dices = new DiceRoll(new double[]{1, 2, 5, 11, 23, 47});
	assertEquals(dices.countFailures(-10),  1, 		0.01);
	assertEquals(dices.countFailures(0),    1, 		0.01);
	assertEquals(dices.countFailures(1),    1, 		0.01);
	assertEquals(dices.countFailures(2),    1, 		0.01);
	assertEquals(dices.countFailures(3),    1+2, 		0.01);
	assertEquals(dices.countFailures(4),    1+2+5, 		0.01);
	assertEquals(dices.countFailures(5),    1+2+5+11, 	0.01);
	assertEquals(dices.countFailures(6),    1+2+5+11+23, 	0.01);
	assertEquals(dices.countFailures(7),    1+2+5+11+23+47, 0.01);
	assertEquals(dices.countFailures(20),   1+2+5+11+23+47, 0.01);
    }
    
    /**
     * Test method for {@link wix.w40k_v10.model.diceRolls.DiceRoll#rerollDicesStrictBelow(int)}.
     */
    @Test
    public void rerollDices() {
		//No reroll
	DiceRoll dices = new DiceRoll(new double[]{1, 1, 1, 1, 1, 1});
	dices.reroll(new RerollBehavior(RerollBehavior.Method.Nothing, 0));
	assertEquals(dices.getCountForFace(1),    1, 0.01);
	assertEquals(dices.getCountForFace(2),    1, 0.01);
	assertEquals(dices.getCountForFace(3),    1, 0.01);
	assertEquals(dices.getCountForFace(4),    1, 0.01);
	assertEquals(dices.getCountForFace(5),    1, 0.01);
	assertEquals(dices.getCountForFace(6),    1, 0.01);

		//Reroll two's
	dices = new DiceRoll(new double[]{1, 1, 1, 1, 1, 1});
	dices.reroll(new RerollBehavior(RerollBehavior.Method.ResultOf, 2));
	double face1 = dices.getCountForFace(1);
	double face2 = dices.getCountForFace(2);
	assertEquals(dices.getCountForFace(1),    1+1./6., 0.01);
	assertEquals(dices.getCountForFace(2),    1./6.,   0.01);
	assertEquals(dices.getCountForFace(3),    1+1./6, 0.01);
	assertEquals(dices.getCountForFace(4),    1+1./6, 0.01);
	assertEquals(dices.getCountForFace(5),    1+1./6, 0.01);
	assertEquals(dices.getCountForFace(6),    1+1./6, 0.01);
	
		//FailForAtLeast 4
	dices = new DiceRoll(new double[]{1, 1, 1, 1, 1, 1});
	dices.reroll(new RerollBehavior(RerollBehavior.Method.FailForAtLeast, 4));
	assertEquals(dices.getCountForFace(1),    3./6,   0.01);
	assertEquals(dices.getCountForFace(2),    3./6,   0.01);
	assertEquals(dices.getCountForFace(3),    3./6,   0.01);
	assertEquals(dices.getCountForFace(4),    1+3./6, 0.01);
	assertEquals(dices.getCountForFace(5),    1+3./6, 0.01);
	assertEquals(dices.getCountForFace(6),    1+3./6, 0.01);
    }


	/**
     * Test method for {@link wix.w40k_v10.model.diceRolls.DiceRoll#statsRollD6(double)}.
     */
    @Test
    public void statsRollD6() {
	DiceRoll dices0 = DiceRoll.statsRollD6(0);
	assertEquals(dices0.getCountForFace(1),    0, 0.01);
	assertEquals(dices0.getCountForFace(2),    0, 0.01);
	assertEquals(dices0.getCountForFace(3),    0, 0.01);
	assertEquals(dices0.getCountForFace(4),    0, 0.01);
	assertEquals(dices0.getCountForFace(5),    0, 0.01);
	assertEquals(dices0.getCountForFace(6),    0, 0.01);
	
	DiceRoll dices = DiceRoll.statsRollD6(6);
	assertEquals(dices.getCountForFace(1),    1, 0.01);
	assertEquals(dices.getCountForFace(2),    1, 0.01);
	assertEquals(dices.getCountForFace(3),    1, 0.01);
	assertEquals(dices.getCountForFace(4),    1, 0.01);
	assertEquals(dices.getCountForFace(5),    1, 0.01);
	assertEquals(dices.getCountForFace(6),    1, 0.01);
	
	DiceRoll dices2 = DiceRoll.statsRollD6(3);
	assertEquals(dices2.getCountForFace(1),  0.5, 0.01);
	assertEquals(dices2.getCountForFace(2),  0.5, 0.01);
	assertEquals(dices2.getCountForFace(3),  0.5, 0.01);
	assertEquals(dices2.getCountForFace(4),  0.5, 0.01);
	assertEquals(dices2.getCountForFace(5),  0.5, 0.01);
	assertEquals(dices2.getCountForFace(6),  0.5, 0.01);
    }

}
