package wix_tests.w40k_v10.model.database;

import wix.w40k_v10.model.AttackingUnit;
import wix.w40k_v10.model.Weapon;

public class SpaceMarines extends AttackingUnit{
    private Weapon bolter = new Weapon(2, 3, 4);
    
    public SpaceMarines(int count){
        addWeaponGroup(bolter, count);
    }
    
}
