/**
 * 
 */
package wix.w40k_v10.model.diceRolls;

/**
 * @author wix This class represent a save roll sequence and associated damages
 *         resolution (either for close combat or distance shooting)
 *
 */
public class SaveRoll {

    /**
     * 
     */
    public SaveRoll() {
	// TODO Auto-generated constructor stub
    }

    /** Number of wounds in the previous roll */
    protected double wounds;

    /** Attacking model armor penetration */
    protected int ap;

    /** Defending model armor save */
    protected Skill svg;

    /** Defending model invulnerable save */
    protected Skill inv;

    /**
     * Configure the number of wounds (dices) in the roll Compulsory call before
     * roll()
     * 
     * @param w
     *            number of wounds shall be positive of throw
     *            IllegalArgumentException
     */
    public void setWounds(double w) {
	if (w < 0)
	    throw new IllegalArgumentException();
	wounds = w;
    }

    /**
     * Configure the armor penetration of the attaking model
     * 
     * @param armorPenetration
     *            shall be negative of throw IllegalArgumentException
     */
    public void setArmorPenetration(int armorPenetration) {
	if (0 < armorPenetration)
	    throw new IllegalArgumentException();
	ap = armorPenetration;
    }

    /**
     * Configure the armor save of the defending model Compulsory call before
     * roll()
     * 
     * @param armorSave
     */
    public void setArmorSave(Skill armorSave) {
	svg = armorSave;
    }

    /**
     * Configure the invulnerable save of the defending model
     * 
     * @param invulnerableSave
     */
    public void setInvulnerableSave(Skill invulnerableSave) {
	inv = invulnerableSave;
    }

}
