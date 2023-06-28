package wix_tests.w40k_v10.model.diceRolls;

import static org.junit.Assert.*;
import org.junit.Test;

import wix_tests.w40k_v10.model.database.ImperialGuard;
import wix_tests.w40k_v10.model.database.ImperialGuards;
import wix_tests.w40k_v10.model.database.SpaceMarine;
import wix_tests.w40k_v10.model.database.SpaceMarines;
import wix.w40k_v10.model.Model;
import wix.w40k_v10.model.Unit;
import wix.w40k_v10.model.diceRolls.AttackSequence;

public class UT_AttackSequence {
    /*
    * 5 SM attacking GIs
    *
    * 5 SM with 2 Att per bolter => 10 attacks
    * Hitting on 3 => 10*(6-skill)/6 = 6.667
    * Wounding on 3 hits*4/6 => 4.444
    * No AP, save 5+ wounds*4/6 =>  2.963
    */
    @Test 
    public void sm_vs_gi() {
        Unit att = new SpaceMarines(5);
        Model def = new ImperialGuard();
        AttackSequence seq = new AttackSequence(att, def);
        double kills = seq.statRoll();

        assertEquals(2.963, kills, 0.01);
    }

    /*
    * 10 GI attacking GIs
    *
    * 10 GI with 1 Att per lasrifle => 10 attacks
    * Hitting on 4 => 10*(6-skill)/6 = 5
    * Wounding on 4 hits*3/6 => 2.5
    * No AP, save 5+ wounds*4/6 =>  1.667
    */
    @Test 
    public void gi_vs_gi() {
        Unit att = new ImperialGuards(10);
        Model def = new ImperialGuard();
        AttackSequence seq = new AttackSequence(att, def);
        double kills = seq.statRoll();

        assertEquals(1.667, kills, 0.01);
    }


    /*
    * 10 GI attacking SMs
    *
    * 10 GI with 1 Att per lasrifle => 10 attacks
    * Hitting on 4 => 10*(6-skill)/6 = 5 hits
    * Wounding on 5 hits*2/6 => 1.667 wounds
    * No AP, save 3+ wounds*2/6 =>  0.555 hp
    * Nb kills hp / hp_per_model => 0.278
    */
    @Test 
    public void gi_vs_sm() {
        Unit att = new ImperialGuards(10);
        Model def = new SpaceMarine();
        AttackSequence seq = new AttackSequence(att, def);
        double kills = seq.statRoll();

        assertEquals(0.278, kills, 0.01);
    }
}
    

