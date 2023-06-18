/**
 * 
 */
package wix_tests.w40k_v10.model.diceRolls;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import wix.w40k_v10.model.diceRolls.DiceRoll;
import wix.w40k_v10.model.diceRolls.WoundRoll;

/**
 * @author wix
 *
 */
public class UT_WoundRoll {

    class WoundRollAccessor extends WoundRoll {
	public int getWoundValue() {
	    return woundValue;
	}
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    /**
     * Test method for
     * {@link wix.w40k_v10.model.diceRolls.WoundRoll#setHitCount(double)}.
     */
    @Test
    public void testSetAttacks() {
	WoundRoll dices = new WoundRoll();
	dices.setHitCount(2);

	exception.expect(IllegalArgumentException.class);
	dices.setHitCount(0);
	dices.setHitCount(-2);
    }

    /**
     * Test method for
     * {@link wix.w40k_v10.model.diceRolls.WoundRoll#setModels(int, int, wix.w40k_v10.model.diceRolls.Skill.Redices)}.
     */
    @Test
    public void testSetModelsIntIntRedices() {
	WoundRollAccessor dices = new WoundRollAccessor();

	// Double Strength 2+
	dices.setModels(7, 3);
	assertEquals(2, dices.getWoundValue());
	dices.setModels(6, 3);
	assertEquals(2, dices.getWoundValue());

	// Better Strength 3+
	dices.setModels(5, 3);
	assertEquals(3, dices.getWoundValue());
	dices.setModels(4, 3);
	assertEquals(3, dices.getWoundValue());

	// Equal S and T 4+
	dices.setModels(3, 3);
	assertEquals(4, dices.getWoundValue());

	// Better Toughness 5+
	dices.setModels(2, 3);
	assertEquals(5, dices.getWoundValue());

	// Double Toughness 6+
	dices.setModels(1, 3);
	assertEquals(6, dices.getWoundValue());

	exception.expect(IllegalArgumentException.class);
	dices.setModels(0, 4);
	dices.setModels(-1, 4);
	dices.setModels(2, 0);
	dices.setModels(2, -2);
	dices.setModels(-1, -2);
    }

    /**
     * Test method for
     * {@link wix.w40k_v10.model.diceRolls.WoundRoll#setMortalWounds(int, int, boolean)}.
     */
    @Test
    public void testSetAdditionnalHits() {
	WoundRoll dices = new WoundRoll();
	dices.setMortalWounds(5, 2, true);

	exception.expect(IllegalArgumentException.class);
	dices.setMortalWounds(1, 2, true);
	dices.setMortalWounds(0, 2, true);
	dices.setMortalWounds(-1, 2, true);
	dices.setMortalWounds(5, 0, true);
	dices.setMortalWounds(5, -1, true);
    }

    /**
     * Test method for {@link wix.w40k_v10.model.diceRolls.WoundRoll#dices()}.
     */
    @Test
    public void testRoll_noConfig() {
	WoundRoll dices = new WoundRoll();
	exception.expect(IllegalArgumentException.class);
	dices.roll();
    }

    /**
     * Test method for {@link wix.w40k_v10.model.diceRolls.WoundRoll#dices()}.
     * Simple roll with no modifier, no rerolls and no extra mortal wounds
     */
    @Test
    public void testRoll_simple() {
	WoundRoll dices = new WoundRoll();
	dices.setHitCount(6);

	// S == T : 3 success on 6 dices
	dices.setModels(4, 4);
	assertEquals(3, dices.roll().normal, 0.01);
	assertEquals(0, dices.roll().mortals, 0.01);

	// T < S : 4 success on 6 dices
	dices.setModels(4, 3);
	assertEquals(4, dices.roll().normal, 0.01);
	assertEquals(0, dices.roll().mortals, 0.01);

	// S < T : 2 success on 6 dices
	dices.setModels(4, 5);
	assertEquals(2, dices.roll().normal, 0.01);
	assertEquals(0, dices.roll().mortals, 0.01);

	// 2S <= T : 1 success on 6 dices
	dices.setModels(4, 8);
	assertEquals(1, dices.roll().normal, 0.01);
	assertEquals(0, dices.roll().mortals, 0.01);

	// 2T < S : 5 success on 6 dices
	dices.setModels(4, 2);
	assertEquals(5, dices.roll().normal, 0.01);
	assertEquals(0, dices.roll().mortals, 0.01);
    }
    
    /**
     * Test method for {@link wix.w40k_v10.model.diceRolls.WoundRoll#dices()}.
     * Roll with extra mortals
     */
    @Test
    public void testRoll_extraMortals() {
	WoundRoll dices = new WoundRoll();
	dices.setHitCount(6);
	dices.setModels(4, 4);

	//On 6+ proc 2 mortals that replace the normal success
	dices.setMortalWounds(6, 2, true);
	assertEquals(2, dices.roll().normal, 0.01);
	assertEquals(2, dices.roll().mortals, 0.01);

	//On 6+ proc 2 mortals that do not replace the normal success
	dices.setMortalWounds(6, 2, false);
	assertEquals(3, dices.roll().normal, 0.01);
	assertEquals(2, dices.roll().mortals, 0.01);

    }
    
    /**
     * Test method for {@link wix.w40k_v10.model.diceRolls.WoundRoll#dices()}.
     * Roll with rerolls
     */
    @Test
    public void testRoll_reroll() {
	WoundRoll dices = new WoundRoll();
	dices.setHitCount(6);

	//On 4+ reroll ones, 3 success first time, 1 reroll thats gives 0.5 for a total of 3.5
	dices.setModels(4, 4, DiceRoll.RerollType.Ones);
	assertEquals(3.5, dices.roll().normal, 0.01);
	assertEquals(0, dices.roll().mortals, 0.01);

	//On 4+ reroll all, 3 success first time, 3 reroll thats gives 1.5 for a total of 4.5
	dices.setModels(4, 4, DiceRoll.RerollType.MayAll);
	assertEquals(4.5, dices.roll().normal, 0.01);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Idem with "must" instead of "may", as there is no mod, it's the same result
	dices.setModels(4, 4, DiceRoll.RerollType.MustAll);
	assertEquals(4.5, dices.roll().normal, 0.01);
	assertEquals(0, dices.roll().mortals, 0.01);
    }
    
    /**
     * Test method for {@link wix.w40k_v10.model.diceRolls.WoundRoll#dices()}.
     * Roll with modifiers
     */
    @Test
    public void testRoll_modifiers() {
	WoundRoll dices = new WoundRoll();
	dices.setHitCount(6);
	dices.setModels(4, 4);
	
	//With equal S and T a malus (-1) give 5+, hence 2 success
	dices.setModifier(-1);	
	assertEquals(2, dices.roll().normal, 0.01);
	assertEquals(0, dices.roll().mortals, 0.01);

	//With equal S and T a bonus (+1) give 3+, hence 4 success
	dices.setModifier(+1);	
	assertEquals(4, dices.roll().normal, 0.01);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//With equal S and T
	dices.setModifier(0);	
	assertEquals(3, dices.roll().normal, 0.01);
	assertEquals(0, dices.roll().mortals, 0.01);
    }
    
    /**
     * Test method for {@link wix.w40k_v10.model.diceRolls.WoundRoll#dices()}.
     * Roll with poison 
     */
    @Test
    public void testRoll_poison() {
	WoundRoll dices = new WoundRoll();
	dices.setHitCount(6);
	dices.setModels(3, 8);
	
	//normal throw
	assertEquals(1, dices.roll().normal, 0.01);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//poisonned throw 5+
	dices.setPoison(5);
	assertEquals(2, dices.roll().normal, 0.01);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//poisonned throw 4+
	dices.setPoison(4);
	assertEquals(3, dices.roll().normal, 0.01);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//useless poison at 6+
	dices.setPoison(6);
	assertEquals(1, dices.roll().normal, 0.01);
	assertEquals(0, dices.roll().mortals, 0.01);
    }
    
    /**
     * Test method for {@link wix.w40k_v10.model.diceRolls.WoundRoll#dices()}.
     * Roll with all modifiers (reroll ones)
     */
    @Test
    public void testRoll_allMods_R1() {
	WoundRoll dices = new WoundRoll();
	dices.setHitCount(6);

	//normal throw 6+ to wound => 1 success
	dices.setModels(3, 8);
	assertEquals(1, dices.roll().normal, 0.01);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Normal throw 6+ to wound, with reroll ones => 1+1*1/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.Ones);
	assertEquals(7/6., dices.roll().normal , 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Poisonned throw, 5+ to wound, with reroll ones => 2+1*2/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.Ones);
	dices.setPoison(5);
	assertEquals(14/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Poisonned + Malus -1 to wound, 5+ to wound, with reroll ones => 2+1*2/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.Ones);
	dices.setPoison(5);
	dices.setModifier(-1);
	assertEquals(14/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Bonus to wound, 5+ to wound, with reroll ones => 2+1*2/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.Ones);
	dices.setModifier(+1);
	assertEquals(14/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Poison + Bonus +1 to wound, 5+ to wound, with reroll ones => 2+1*2/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.Ones);
	dices.setModifier(+1);
	dices.setPoison(5);
	assertEquals(14/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Bonus +2 to wound, 4+ to wound, with reroll ones => 3+1*3/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.Ones);
	dices.setModifier(+2);
	dices.setPoison(0);
	assertEquals(21/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Poison + Bonus +2 to wound, 4+ to wound, with reroll ones => 2+1*2/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.Ones);
	dices.setModifier(+2);
	dices.setPoison(5);
	assertEquals(14/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Poison throw, 4+ to wound, with reroll ones, 1 extra mortal on 6+ => 3+1*3/6 success and 1+1*1/6 mortals
	dices.setModels(3, 8, DiceRoll.RerollType.Ones);
	dices.setPoison(4);
	dices.setModifier(0);
	dices.setMortalWounds(6, 1, false);
	assertEquals(21/6., dices.roll().normal, 0.001);
	assertEquals(7/6., dices.roll().mortals, 0.01);
	
	//Bonus +1, 5+ to wound, with reroll ones, 1 extra mortal on 6+ (becomes 5+) => 2+1*2/6 success and mortals
	dices.setModels(3, 8, DiceRoll.RerollType.Ones);
	dices.setPoison(0);
	dices.setModifier(+1);
	dices.setMortalWounds(6, 1, false);
	assertEquals(14/6., dices.roll().normal, 0.001);
	assertEquals(14/6., dices.roll().mortals, 0.01);
    }
    
    /**
     * Test method for {@link wix.w40k_v10.model.diceRolls.WoundRoll#dices()}.
     * Roll with all modifiers (may reroll all)
     */
    @Test
    public void testRoll_allMods_MayRerollAll() {
	WoundRoll dices = new WoundRoll();
	dices.setHitCount(6);
	
	//Malus -1 to wound, 5+ to wound, with reroll all but 4 => 2+3*2/6 success
	dices.setModels(4, 4, DiceRoll.RerollType.MustAll);
	dices.setModifier(-1);
	assertEquals(18/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Malus +1 to wound, 3+ to wound, with reroll all => 3+3*4/6 success
	dices.setModels(4, 4, DiceRoll.RerollType.MustAll);
	dices.setModifier(+1);
	assertEquals(30/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	

	//Normal throw 6+ to wound, with reroll all => 1+5*1/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.MayAll);
	dices.setModifier(0);
	assertEquals(11/6., dices.roll().normal , 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Poisonned throw, 5+ to wound, with reroll all => 2+4*2/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.MayAll);
	dices.setPoison(5);
	assertEquals(20/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Poisonned + Malus -1 to wound, 5+ to wound, with reroll all => 2+4*2/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.MayAll);
	dices.setPoison(5);
	dices.setModifier(-1);
	assertEquals(20/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Bonus to wound, 5+ to wound, with reroll all => 2+4*2/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.MayAll);
	dices.setModifier(+1);
	assertEquals(20/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Poison + Bonus +1 to wound, 5+ to wound, with reroll all => 2+4*2/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.MayAll);
	dices.setModifier(+1);
	dices.setPoison(5);
	assertEquals(20/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Bonus +2 to wound, 4+ to wound, with reroll all => 3+3*3/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.MayAll);
	dices.setModifier(+2);
	dices.setPoison(0);
	assertEquals(27/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Poison + Bonus +2 to wound, 5+ to wound, with reroll all => 2+4*2/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.MayAll);
	dices.setModifier(+2);
	dices.setPoison(5);
	assertEquals(20/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Poison throw, 4+ to wound, with reroll all, 1 extra mortal on 6+ => 3+3*3/6 success and 1+3*1/6 mortals
	dices.setModels(3, 8, DiceRoll.RerollType.MayAll);
	dices.setPoison(4);
	dices.setModifier(0);
	dices.setMortalWounds(6, 1, false);
	assertEquals(27/6., dices.roll().normal, 0.001);
	assertEquals(9/6., dices.roll().mortals, 0.01);
	
	//Bonus +1, 5+ to wound, with reroll all, 1 extra mortal on 6+ (becomes 5+) => 2+4*2/6 success and mortals
	dices.setModels(3, 8, DiceRoll.RerollType.MayAll);
	dices.setPoison(0);
	dices.setModifier(+1);
	dices.setMortalWounds(6, 1, false);
	assertEquals(20/6., dices.roll().normal, 0.001);
	assertEquals(20/6., dices.roll().mortals, 0.01);
    }
    
    /**
     * Test method for {@link wix.w40k_v10.model.diceRolls.WoundRoll#dices()}.
     * Roll with all modifiers (must reroll all)
     */
    @Test
    public void testRoll_allMods_MustRerollAll() {
	WoundRoll dices = new WoundRoll();
	dices.setHitCount(6);

	//Malus -1 to wound, 5+ to wound, with reroll all but 4 => 2+3*2/6 success
	dices.setModels(4, 4, DiceRoll.RerollType.MustAll);
	dices.setModifier(-1);
	assertEquals(18/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Malus +1 to wound, 3+ to wound, with reroll all => 3+3*4/6 success
	dices.setModels(4, 4, DiceRoll.RerollType.MustAll);
	dices.setModifier(+1);
	assertEquals(30/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Poison Malus -1 to wound, 4+ to wound, with reroll all => 3+3*3/6 success
	dices.setModels(4, 4, DiceRoll.RerollType.MustAll);
	dices.setModifier(-1);
	dices.setPoison(4);
	assertEquals(27/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Poison Bonus +1 to wound, 4+ to wound, with reroll all => 3+3*3/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.MustAll);
	dices.setModifier(-1);
	dices.setPoison(4);
	assertEquals(27/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	
	//Normal throw 6+ to wound, with reroll all => 1+5*1/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.MustAll);
	dices.setModifier(0);
	dices.setPoison(0);
	assertEquals(11/6., dices.roll().normal , 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Poisonned throw, 5+ to wound, with reroll all => 2+4*2/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.MustAll);
	dices.setPoison(5);
	assertEquals(20/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Poisonned + Malus -1 to wound, 5+ to wound, with reroll all => 2+4*2/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.MustAll);
	dices.setPoison(5);
	dices.setModifier(-1);
	assertEquals(20/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Bonus to wound, 5+ to wound, with reroll all => 2+4*2/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.MustAll);
	dices.setModifier(+1);
	assertEquals(20/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Poison + Bonus +1 to wound, 5+ to wound, with reroll all => 2+4*2/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.MustAll);
	dices.setModifier(+1);
	dices.setPoison(5);
	assertEquals(20/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Bonus +2 to wound, 4+ to wound, with reroll all (include success) => 1+5*3/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.MustAll);
	dices.setModifier(+2);
	dices.setPoison(0);
	assertEquals(21/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Poison + Bonus +2 to wound, 5+ to wound, with reroll all (include success) => 2+4*2/6 success
	dices.setModels(3, 8, DiceRoll.RerollType.MustAll);
	dices.setModifier(+2);
	dices.setPoison(5);
	assertEquals(20/6., dices.roll().normal, 0.001);
	assertEquals(0, dices.roll().mortals, 0.01);
	
	//Poison throw, 4+ to wound, with reroll all, 1 extra mortal on 6+ => 3+3*3/6 success and 1+3*1/6 mortals
	dices.setModels(3, 8, DiceRoll.RerollType.MustAll);
	dices.setPoison(4);
	dices.setModifier(0);
	dices.setMortalWounds(6, 1, false);
	assertEquals(27/6., dices.roll().normal, 0.001);
	assertEquals(9/6., dices.roll().mortals, 0.01);
	
	//Bonus +1, 5+ to wound, with reroll all, 1 extra mortal on 6+ (becomes 5+) => 1+5*2/6 success and mortals
	dices.setModels(3, 8, DiceRoll.RerollType.MustAll);
	dices.setPoison(0);
	dices.setModifier(+1);
	dices.setMortalWounds(6, 1, false);
	assertEquals(16/6., dices.roll().normal, 0.001);
	assertEquals(16/6., dices.roll().mortals, 0.01);
    }

}
