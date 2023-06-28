package wix_tests.w40k_v10.model.database;

import wix.w40k_v10.model.Model;
import wix.w40k_v10.model.Weapon;

public class SpaceMarine extends Model{
    private Weapon bolter = new Weapon(2, 3, 4);
    
    public SpaceMarine(){
        super(4, 3, 0);
        weapons.add(bolter);
    }
}