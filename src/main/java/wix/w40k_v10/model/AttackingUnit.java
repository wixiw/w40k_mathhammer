package wix.w40k_v10.model;

import java.util.ArrayList;

/**
 * Represent a group of model making an attack
 */
public class AttackingUnit {
    //Groups of the same weapon type
    private ArrayList groups;

    public AttackingUnit(){

    }

    public void addWeaponGroup(Weapon weapon, int nbWeapons){
        groups.add(new ArrayList(weapon, nbWeapons));
    }
}
