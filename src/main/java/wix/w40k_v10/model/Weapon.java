package wix.w40k_v10.model;

import wix.w40k_v10.model.types.VarDmgType;

public class Weapon{
    //Att count
    public int nbAtt;

    //Hit
    public int hitSkill;
    public int critHit = 6;
    public boolean hasLethalHit = false;
    public boolean isHeavy = false;

    //Wound
    public int strength;
    public int critWound = 6;
    public boolean hasDevaWound = false;

    //Save change
    public int armorPen = 0;

    //Damage
    public int flatDmg = 1;
    public VarDmgType varDmgDice = VarDmgType.Zero;

    public void check(){
        // Check that minimal configuration has been done
        if (nbAtt <= 0 || hitSkill <= 0){
            throw new IllegalArgumentException();
        } 
    }

    public Weapon(int _nbAtt, int skill, int S){
        nbAtt = _nbAtt;
        hitSkill = skill;
        strength = S;
    }

    private Weapon(){

    }
}

