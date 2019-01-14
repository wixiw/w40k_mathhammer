package wix_tests.w40k_v8.model.diceRolls;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import wix.w40k_v8.model.diceRolls.HitRoll;
import wix.w40k_v8.model.diceRolls.Skill;
import wix.w40k_v8.model.diceRolls.DiceResults;

public class UT_HitRoll {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    //Check that roll() is impossible if a minimal configuration is not made
    @Test 
    public void roll_constructor_1() {
	HitRoll hit1 = new HitRoll();
	
	exception.expect(IllegalArgumentException.class);
	hit1.roll();
    }
    
    //Check that roll() is impossible if a minimal configuration is not made
    @Test
    public void roll_constructor_2() {
	HitRoll hit1 = new HitRoll();
	hit1.setSkill(new Skill(3));
	exception.expect(IllegalArgumentException.class);
	hit1.roll();
    }
    
    //Check that roll() is impossible if a minimal configuration is not made
    @Test
    public void roll_constructor_3() {
	HitRoll hit1 = new HitRoll();
	hit1.setSkill(new Skill(3));
	hit1.setAttacks(6);
	assertEquals(4, hit1.roll(), 0.01);
    }
    
    //Simple roll 6 attacks on 3+ => 4 success
    @Test
    public void roll_simple() {
	HitRoll hit1 = new HitRoll();
	hit1.setSkill(new Skill(3));
	hit1.setAttacks(6);
	assertEquals(4, hit1.roll(), 0.01);
    }
    
    //Simple roll 6 attacks on 3+, reroll all => 5.333 success
    @Test
    public void roll_simpleRE() {
	HitRoll hit1 = new HitRoll();
	hit1.setSkill(new Skill(3, DiceResults.Reroll.MayAll));
	hit1.setAttacks(6);
	assertEquals(4+2*2/3., hit1.roll(), 0.01);
    }
    
    //Simple roll 6 attacks on 3+, reroll ones => 4.666 success
    @Test
    public void roll_simpleR1() {
	HitRoll hit1 = new HitRoll();
	hit1.setSkill(new Skill(3, DiceResults.Reroll.Ones));
	hit1.setAttacks(6);
	assertEquals(4+1*2/3., hit1.roll(), 0.01);
    }
    
    //Simple roll 6 attacks on 2+ : check that R1 is equivalent of RE
    @Test
    public void roll_simple_2R1is2RE() {
	HitRoll hit1 = new HitRoll();
	hit1.setSkill(new Skill(2, DiceResults.Reroll.Ones));
	hit1.setAttacks(6);
	double roll1 = hit1.roll();
	
	HitRoll hit2 = new HitRoll();
	hit2.setSkill(new Skill(2, DiceResults.Reroll.MayAll));
	hit2.setAttacks(6);
	double roll2 = hit2.roll();
	
	assertEquals(roll1, roll2, 0.01);
    }
    
    //Modified roll 6 attacks on 3+:
    //  mod -1 => 3 success
    //  mod +1 => 5 success
    @Test
    public void roll_mod() {
	HitRoll hit1 = new HitRoll();
	hit1.setSkill(new Skill(3));
	hit1.setAttacks(6);
	
	hit1.setModifier(-1);
	assertEquals(3, hit1.roll(), 0.01);
	
	hit1.setModifier(+1);
	assertEquals(5, hit1.roll(), 0.01);
    }
    
    //Modified roll 6 attacks on 3+, reroll all compulsory:
    //  mod -1 => 4 success
    //  mod +1 => 5.666 success
    @Test
    public void roll_modRE_comp() {
	HitRoll hit1 = new HitRoll();
	hit1.setSkill(new Skill(3, DiceResults.Reroll.MustAll));
	hit1.setAttacks(6);

	hit1.setModifier(-1);
	assertEquals(4, hit1.roll(), 0.01);
	
	hit1.setModifier(+1);
	assertEquals(5.666, hit1.roll(), 0.01);
    }
    
    //Modified roll 6 attacks on 3+, reroll all optional:
    //  mod -1 => 4 success
    //  mod +1 => 5.666 success
    @Test
    public void roll_modRE_opt() {
	HitRoll hit1 = new HitRoll();
	hit1.setSkill(new Skill(3, DiceResults.Reroll.MayAll));
	hit1.setAttacks(6);

	hit1.setModifier(-1);
	assertEquals(4, hit1.roll(), 0.01);
	
	hit1.setModifier(+1);
	assertEquals(5.833, hit1.roll(), 0.01);
    }

    //Modified roll 6 attacks on 3+ of tesla (on 6+ generates extra hits):
    //  mod -1, tesla inactive => 3 success
    //  mod 0, tesla on 6+ => 6 success
    //  mod +1, tesla on 5+ => 8 success
    @Test
    public void roll_tesla() {
	HitRoll hit1 = new HitRoll();
	hit1.setSkill(new Skill(3));
	hit1.setAttacks(6);
	hit1.setAdditionnalHits(6, 2);
	
	hit1.setModifier(-1);
	assertEquals(3, hit1.roll(), 0.01);
	
	hit1.setModifier(0);
	assertEquals(6, hit1.roll(), 0.01);
	
	hit1.setModifier(+1);
	assertEquals(9, hit1.roll(), 0.01);
    }
    
    //Modified roll 6 attacks on 3+ of tesla with rerolls (on 6+ generates extra hits):
    //  mod -1, tesla inactive => 3 success
    //  mod 0, tesla on 6+ => 6 success
    //  mod +1, tesla on 5+ => 9 success
    @Test
    public void roll_teslaRE() {
	HitRoll hit1 = new HitRoll();
	hit1.setSkill(new Skill(3, DiceResults.Reroll.MayAll));
	hit1.setAttacks(6);
	hit1.setAdditionnalHits(6, 2);
	
	hit1.setModifier(-1);
	assertEquals(4, hit1.roll(), 0.01);
	
	hit1.setModifier(0);
	assertEquals(8, hit1.roll(), 0.01);
	
	hit1.setModifier(+1);
	assertEquals(10.5, hit1.roll(), 0.01);
    }
    
    //Roll 6 attacks on 3+ of explosive attacks (on 6+ generates extra 1 hit):
    //  mod -1, explo inactive => 3 success
    //  mod 0, explo on 6+ => 4.66 success
    //  mod +1, explo on 5+ => 6.66 success
    @Test
    public void roll_explosive() {
	HitRoll hit1 = new HitRoll();
	hit1.setSkill(new Skill(3));
	hit1.setAttacks(6);
	hit1.setAdditionnalAttacks(6, 1);
	
	hit1.setModifier(-1);
	assertEquals(3, hit1.roll(), 0.01);
	
	hit1.setModifier(0);
	assertEquals(4.666, hit1.roll(), 0.01);
	
	hit1.setModifier(+1);
	assertEquals(6.66, hit1.roll(), 0.01);
    }
    
    //TODO test explo + reroll
    //TODO test explo + tesla
    //TODO test optional reroll on hit bonus
}
