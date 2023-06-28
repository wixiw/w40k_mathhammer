package wix_tests.w40k_v10.model.database;

import wix.w40k_v10.model.Unit;

public class SpaceMarines extends Unit{
    public SpaceMarines(int count){
        for(int i = 0; i < count; i++){
            add(new SpaceMarine());
        }
    }
}
