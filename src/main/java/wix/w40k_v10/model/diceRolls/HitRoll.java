package wix.w40k_v10.model.diceRolls;

import wix.w40k_v10.model.AttackingUnit;
import wix.w40k_v10.model.Model;

/**
 * @author wix This class represent a hit roll sequence (either for cloce combat
 *         or distance)
 */
public class HitRoll {
    // Data representing the attacking group of models
    protected AttackingUnit att;
    // Data representing the defensing unit
    protected Model def;

    public HitRoll(AttackingUnit attacker, Model defenser){
        att = attacker;
        def = defenser;
    }

    /**
     * Roll the dices cooresponding to the hit rolls
     * 
     * @return the number of successfull hits
     */
    public double roll() {
        // Check that minimal configuration has been done
        if (attacks == 0 || skill.getValue() == 0)
            throw new IllegalArgumentException();

        // Roll dices a first time
        DiceRoll dices = DiceRoll.statsRollD6(attacks);

        // If allowed, reroll failed dices and add them to first Roll
        _rerollDices(dices);

        // If allowed create additional attacks
        if (explAttCount != 0) {
            // Roll extra attacks
            double extraAttackCount = explAttCount * dices.countSuccess(explAttValue - mod);
            DiceRoll extraAttacks = DiceRoll.statsRollD6(extraAttackCount);

            // If allowed, reroll additional attacks failed dices
            _rerollDices(extraAttacks);
            // Add extra attacks to first roll
            dices.combineRolls(extraAttacks);
        }

        // Count success with modifier
        double success = dices.countSuccess(skill.getValue() - mod);

        // If allowed, create additional hits (like tesla)
        if (extraHitsCount != 0)
            success += extraHitsCount * dices.countSuccess(extraHitsValue - mod);

        return success;
    }
}
