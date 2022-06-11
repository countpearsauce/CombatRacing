package powerups;
import obstacles.MoveableObject;

/***
 * 
 * @author Vishnu
 * @version 5/21/2018
 * 
 * Represents the effects of the weapon power up and the increased shooting rate which follows
 * 
 */
public class WeaponPowerUp extends PowerUp {
	private double fireRateModifier;
	private double damageModifier;
	private int duration;

	public WeaponPowerUp(String imgName, double x, double y, int duration, double fireRateModifier, double damageModifier) {
		super(imgName, x, y);
		this.duration = duration;
		this.fireRateModifier = fireRateModifier;
		this.damageModifier = damageModifier;
		
	}

	
	public void effect(MoveableObject c) {
		c.addEffect(new WeaponPower(duration, damageModifier, fireRateModifier, c.getWeapon()));
		duration = 0;
		fireRateModifier = 1;
		damageModifier = 1;
	}
}
