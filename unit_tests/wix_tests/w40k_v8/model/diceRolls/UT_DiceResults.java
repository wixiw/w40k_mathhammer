/**
 * 
 */
package wix_tests.w40k_v8.model.diceRolls;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import wix.w40k_v8.model.diceRolls.DiceResults;

/**
 * @author wix
 *
 */
public class UT_DiceResults {
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    /**
     * Test method for {@link wix.w40k_v8.model.diceRolls.DiceResults#getResultsForFace(int)}.
     */
    @Test
    public void getResultsForFace() {
	
	DiceResults dices = new DiceResults(4);
	
	assertEquals(dices.getResultsForFace(1), 0, 0.01);
	assertEquals(dices.getResultsForFace(2), 0, 0.01);
	assertEquals(dices.getResultsForFace(3), 0, 0.01);
	assertEquals(dices.getResultsForFace(4), 0, 0.01);

	exception.expect(ArrayIndexOutOfBoundsException.class);
	dices.getResultsForFace(5);
	dices.getResultsForFace(0);
    }
    
    /**
     * Test method for {@link wix.w40k_v8.model.diceRolls.DiceResults#setResultsForFace(int, double)}.
     */
    @Test
    public void setResultsForFace() {
	DiceResults dices = new DiceResults(4);
	
	dices.setResultsForFace(1, 7);
	dices.setResultsForFace(2, 9);
	dices.setResultsForFace(3, 11);
	dices.setResultsForFace(4, 13);
	
	assertEquals(dices.getResultsForFace(1), 7, 0.01);
	assertEquals(dices.getResultsForFace(2), 9, 0.01);
	assertEquals(dices.getResultsForFace(3), 11, 0.01);
	assertEquals(dices.getResultsForFace(4), 13, 0.01);
	
	exception.expect(ArrayIndexOutOfBoundsException.class);
	dices.setResultsForFace(0, 2);
	dices.setResultsForFace(5, 2);
    }

    /**
     * Test method for {@link wix.w40k_v8.model.diceRolls.DiceResults#addRolls(DiceResults)}.
     */
    @Test
    public void addRolls() {
	DiceResults dicesA = new DiceResults(new double[]{1, 2, 5, 11, 23, 47});
	DiceResults dicesB = new DiceResults(new double[]{1, 2, 5, 11, 23, 47});

	//Check that dicesA has been summed
	dicesA.addRolls(dicesB);
	assertEquals(dicesA.getResultsForFace(1),    2, 0.01);
	assertEquals(dicesA.getResultsForFace(2),    4, 0.01);
	assertEquals(dicesA.getResultsForFace(3),    10, 0.01);
	assertEquals(dicesA.getResultsForFace(4),    22, 0.01);
	assertEquals(dicesA.getResultsForFace(5),    46, 0.01);
	assertEquals(dicesA.getResultsForFace(6),    94, 0.01);
	//Check that dicesB didn't changed
	assertEquals(dicesB.getResultsForFace(1),    1, 0.01);
	assertEquals(dicesB.getResultsForFace(2),    2, 0.01);
	assertEquals(dicesB.getResultsForFace(3),    5, 0.01);
	assertEquals(dicesB.getResultsForFace(4),    11, 0.01);
	assertEquals(dicesB.getResultsForFace(5),    23, 0.01);
	assertEquals(dicesB.getResultsForFace(6),    47, 0.01);
	
	//Check exception on different dice type addition
	exception.expect(InvalidParameterException.class);
	DiceResults dices4 = new DiceResults(4);
	DiceResults dices5 = new DiceResults(5);
	dices4.addRolls(dices5);
	dices5.addRolls(dices4);
    }
    
    /**
     * Test method for {@link wix.w40k_v8.model.diceRolls.DiceResults#setValueForAtLeast(int, double)}.
     */
    @Test
    public void setValueForAtLeast() {
	DiceResults dices = new DiceResults(new double[]{3, 3, 3, 3, 3, 3});
	dices.setValueForAtLeast(4, 1.7);

	assertEquals(dices.getResultsForFace(1),    0, 0.01);
	assertEquals(dices.getResultsForFace(2),    0, 0.01);
	assertEquals(dices.getResultsForFace(3),    0, 0.01);
	assertEquals(dices.getResultsForFace(4),  1.7, 0.01);
	assertEquals(dices.getResultsForFace(5),  1.7, 0.01);
	assertEquals(dices.getResultsForFace(6),  1.7, 0.01);
    }

    /**
     * Test method for {@link wix.w40k_v8.model.diceRolls.DiceResults#countSuccess(int)}.
     */
    @Test
    public void countSuccess() {
	DiceResults dices = new DiceResults(new double[]{1, 2, 5, 11, 23, 47});
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
     * Test method for {@link wix.w40k_v8.model.diceRolls.DiceResults#countFailures(int)}.
     */
    @Test
    public void countFailures() {
	DiceResults dices = new DiceResults(new double[]{1, 2, 5, 11, 23, 47});
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
     * Test method for {@link wix.w40k_v8.model.diceRolls.DiceResults#rerollDicesBelow(int)}.
     */
    @Test
    public void rerollMisses() {
	DiceResults dices = new DiceResults(new double[]{1, 1, 1, 1, 1, 1});
	assertEquals(2, dices.rerollDicesBelow(3), 0.01);
	
	assertEquals(dices.getResultsForFace(1),    0.333, 0.01);
	assertEquals(dices.getResultsForFace(2),    0.333, 0.01);
	assertEquals(dices.getResultsForFace(3),    1.333, 0.01);
	assertEquals(dices.getResultsForFace(4),    1.333, 0.01);
	assertEquals(dices.getResultsForFace(5),    1.333, 0.01);
	assertEquals(dices.getResultsForFace(6),    1.333, 0.01);
    }
    
    /**
     * Test method for {@link wix.w40k_v8.model.diceRolls.DiceResults#statsRollD6(double)}.
     */
    @Test
    public void statsRollD6() {
	DiceResults dices0 = DiceResults.statsRollD6(0);
	assertEquals(dices0.getResultsForFace(1),    0, 0.01);
	assertEquals(dices0.getResultsForFace(2),    0, 0.01);
	assertEquals(dices0.getResultsForFace(3),    0, 0.01);
	assertEquals(dices0.getResultsForFace(4),    0, 0.01);
	assertEquals(dices0.getResultsForFace(5),    0, 0.01);
	assertEquals(dices0.getResultsForFace(6),    0, 0.01);
	
	DiceResults dices = DiceResults.statsRollD6(6);
	assertEquals(dices.getResultsForFace(1),    1, 0.01);
	assertEquals(dices.getResultsForFace(2),    1, 0.01);
	assertEquals(dices.getResultsForFace(3),    1, 0.01);
	assertEquals(dices.getResultsForFace(4),    1, 0.01);
	assertEquals(dices.getResultsForFace(5),    1, 0.01);
	assertEquals(dices.getResultsForFace(6),    1, 0.01);
	
	DiceResults dices2 = DiceResults.statsRollD6(3);
	assertEquals(dices2.getResultsForFace(1),  0.5, 0.01);
	assertEquals(dices2.getResultsForFace(2),  0.5, 0.01);
	assertEquals(dices2.getResultsForFace(3),  0.5, 0.01);
	assertEquals(dices2.getResultsForFace(4),  0.5, 0.01);
	assertEquals(dices2.getResultsForFace(5),  0.5, 0.01);
	assertEquals(dices2.getResultsForFace(6),  0.5, 0.01);
    }

}
