package wix_tests.w40k_v10.model.diceRolls;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import wix.w40k_v10.model.diceRolls.DiceRoll;
import wix_tests.w40k_v10.model.database.SpaceMarines;
import wix_tests.w40k_v10.model.database.ImperialGuards;
import wix.w40k_v10.model.DefensingUnit;
import wix.w40k_v10.model.diceRolls.AttackSequence;

public class UT_StatHitRoll {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    //Check that roll() is impossible if a minimal configuration is not made
    @Test 
    public void roll_simple_hit() {
	
        SpaceMarines att = new SpaceMarines(5);
        ImperialGuards def = new ImperialGuards(10);
        AttackSequence seq = new AttackSequence(att, def);
        
        double kills = seq.statRoll();

        /*
        * 5 SM with 2 Att per bolter => 10 attacks
        * Hitting on 3 => 10*(6-skill)/6 = 6.667
        * Wounding on 3 hits*4/6 => 4.444
        * No AP, save 5+ wounds*4/6 =>  2.963
        */
        assertEquals(2.963, kills, 0.01);
    }
}
    

