package wix.w40k_v10.model.diceRolls;

import wix.w40k_v10.model.Model;
import wix.w40k_v10.model.Unit;
import wix.w40k_v10.model.Weapon;

/**
 * Object managing a w40K attacking sequence
 */
public class AttackSequence {
    private Unit att;
    private Model def;

    public AttackSequence(Unit _att, Model _def){
        att = _att;
        def = _def;
    }

    /**
     * Roll hit, wound and saves
     * @return the number of lost models
     * */
    public double statRoll(){
        double lostModels = 0;

        for(Model model: att){
            for(Weapon weapon: model.weapons){
                double hits         = statHitRoll(weapon);
                double wounds       = statWoundRoll(weapon, hits);
                double failedSaves  = statSaveRoll(weapon, wounds);
                double dmg          = statDmgRoll(weapon, failedSaves);
                double hploss       = statFnpRoll(dmg);

                //Check lost models
                    //TODO makes the maths
                lostModels += hploss;
            }
        }

        return lostModels;
    }

    private double statHitRoll(Weapon weapon) {
        DiceRoll hitRoll = DiceRoll.statsRollD6((double)(weapon.nbAtt));
            
        // TODO makes the hit roll options
            
        double hits = hitRoll.countSuccess(weapon.hitSkill);
        return hits;
    };

    private double statWoundRoll(Weapon weapon, double hits) {
        DiceRoll woundRoll = DiceRoll.statsRollD6(hits);

        int scoreForWound = 0;
        int toughness = def.toughness;
        if( toughness <= weapon.strength/2.){
            scoreForWound = 2;
        } else if(weapon.strength <= toughness/2.){
            scoreForWound = 6;
        } else if(weapon.strength == toughness){
            scoreForWound = 4;
        } else if(weapon.strength < toughness){
            scoreForWound = 5;
        } else if(toughness < weapon.strength){
            scoreForWound = 3;
        }
        
            //TODO wound modifiers

        return woundRoll.countSuccess(scoreForWound);
    }

    private double statSaveRoll(Weapon weapon, double wounds) {
        DiceRoll saveRoll = DiceRoll.statsRollD6(wounds);
            //TODO makes the save roll
        int modifiedSave = def.armorSave;

        double failedSaves = saveRoll.countFailures(modifiedSave);
        return failedSaves;
    }

    private double statDmgRoll(Weapon weapon, double failedSaves) {
        //DiceRoll dmgRoll = DiceRoll.statsRollD6(failedSaves);
            //TODO makes the dmg roll
        double dmg = failedSaves;
        return dmg;
    }

    private double statFnpRoll(double dmg) {
        DiceRoll fnpRoll = DiceRoll.statsRollD6(dmg);
            //TODO
        return dmg;
    }
}
