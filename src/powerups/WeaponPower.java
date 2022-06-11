package powerups;
import obstacles.Car;
import processing.core.PApplet;
import weapons.Weapon;


/**
 * 
 * @author Vishnu
 * @version 5/21/2018
 * Weapon power increases weapon fire rate
 * 
 */
public class WeaponPower extends Effect {

	private int originalFireRate;
	private int originalDamage;
	private Weapon w;

	public WeaponPower(int duration, double damageModifier, double fireRateModifier, Weapon t) {
		super(duration);
		w = t;
		
		originalFireRate = w.getFireRate();
		originalDamage = w.getDamage();
		
		
		w.setFireRate((int)(w.getFireRate() / fireRateModifier));
		
		w.setDamage((int)(w.getDamage() / damageModifier));
		
		
	}

	public boolean act(Car c) {

		if(super.act(c)) {
			w.setFireRate(originalFireRate);
			w.setDamage(originalDamage);
			return true;
			
		}
		return false;

	}

	@Override
	public void draw(double x, double y, PApplet drawer) {
		drawer.noFill();
		drawer.stroke(0,255,0);
		drawer.ellipseMode(drawer.CENTER);
		drawer.ellipse((float)x, (float)y, 36,36);
		drawer.stroke(0);

	}
}
