package wix.w40k_v10.model;

import java.util.ArrayList;
import org.javatuples.Pair;

/**
 * Represent a group of model making an attack
 */
public class AttackingUnit {
    //Groups of the same weapon type
    private ArrayList<Pair<Weapon, Integer>> groups = new ArrayList<Pair<Weapon, Integer>>();

    public AttackingUnit(){

    }

    public void addWeaponGroup(Weapon weapon, int nbWeapons){
        Pair<Weapon, Integer> weaponGroup = Pair.with(weapon, nbWeapons);
        weaponGroup.add(weapon);
        weaponGroup.add(nbWeapons);
        groups.add(weaponGroup);
    }

    public ArrayList<Pair<Weapon, Integer>> getWeaponGroups(){;
        return groups;
    }
}
