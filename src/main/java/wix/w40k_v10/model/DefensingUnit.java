package wix.w40k_v10.model;

/**
 * Represent the unit which is the target of attacks
 */
public class DefensingUnit {
    public int toughness;
    public int armorSave;
    public int invSave;

    public boolean hasCover = false;
    public boolean hasStealth = false;

    private DefensingUnit(){};

    public DefensingUnit(int T, int Svg, int Inv){
        toughness = T;
        armorSave = Svg;
        invSave = Inv;
    }
}

