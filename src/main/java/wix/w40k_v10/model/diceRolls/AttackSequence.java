package wix.w40k_v10.model.diceRolls;

import org.javatuples.Pair;

import wix.w40k_v10.model.AttackingUnit;
import wix.w40k_v10.model.DefensingUnit;
import wix.w40k_v10.model.Weapon;

/**
 * Object managing a w40K attacking sequence
 */
public class AttackSequence {
    private AttackingUnit att;
    private DefensingUnit def;

    public AttackSequence(AttackingUnit _att, DefensingUnit _def){
        att = _att;
        def = _def;
    }

    /**
     * Roll hit, wound and saves
     * @return the number of lost models
     * */
    public double statRoll(){
        double lostModels = 0;

        for(Pair<Weapon, Integer> weaponGroup: att.getWeaponGroups()){
            Weapon weapon = weaponGroup.getValue0();
            int nbWeapons = weaponGroup.getValue1();

            double hits         = statHitRoll(weapon, nbWeapons);
            double wounds       = statWoundRoll(weapon, hits);
            double failedSaves  = statSaveRoll(weapon, wounds);
            double dmg          = statDmgRoll(weapon, failedSaves);
            double hploss       = statFnpRoll(dmg);

            //Check lost models
                //TODO makes the maths
            lostModels += hploss;
        }

        return lostModels;
    }

    private double statHitRoll(Weapon weapon, int nbWeapons) {
        DiceRoll hitRoll = DiceRoll.statsRollD6((double)(nbWeapons * weapon.nbAtt));
            
        // TODO makes the hit roll options
            
        double hits = hitRoll.countSuccess(weapon.hitSkill);
        return hits;
    };

    private double statWoundRoll(Weapon weapon, double hits) {
        DiceRoll woundRoll = DiceRoll.statsRollD6(hits);

        int scoreForWound = 0;
        if(def.toughness <= weapon.strength/2.){
            scoreForWound = 2;
        } else if(weapon.strength <= def.toughness/2.){
            scoreForWound = 6;
        } else if(weapon.strength == def.toughness){
            scoreForWound = 4;
        } else if(weapon.strength < def.toughness){
            scoreForWound = 5;
        } else if(def.toughness < weapon.strength){
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
