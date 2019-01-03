package wix_tests.w40k.model.diceRolls;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import wix.w40k.model.diceRolls.DiceResults;
import wix.w40k.model.diceRolls.HitRoll;
import wix.w40k.model.diceRolls.Skill;

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
    
    //Simple roll
    @Test
    public void roll_simple() {
	HitRoll hit1 = new HitRoll();
	hit1.setSkill(new Skill(3));
	hit1.setAttacks(6);
	assertEquals(4, hit1.roll(), 0.01);
    }
}
