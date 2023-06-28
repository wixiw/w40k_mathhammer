package wix_tests.w40k_v10.model.database;

import wix.w40k_v10.model.Model;
import wix.w40k_v10.model.Weapon;

public class ImperialGuard extends Model{
    private Weapon lasrifle = new Weapon(1, 4, 3);
    
    public ImperialGuard(){
        super(3, 5, 0);
        weapons.add(lasrifle);
    }
}