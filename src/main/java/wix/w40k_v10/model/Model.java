package wix.w40k_v10.model;

import java.util.ArrayList;

/**
 * Represent the unit which is the target of attacks
 */
public class Model {
    public int toughness;
    public int armorSave;
    public int invSave;

    public boolean hasCover = false;
    public boolean hasStealth = false;

    public ArrayList<Weapon> weapons = new ArrayList<Weapon>();

    // Prevent unexpected default constructor
    private Model(){};

    public Model(int T, int Svg, int Inv){
        toughness = T;
        armorSave = Svg;
        invSave = Inv;
    }
}

