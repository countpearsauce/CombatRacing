package weapons;
/**
 * 
 * @author Jeremy
 * @version 5/21/18
 *  
 *   Represents the weapon of the Ai Car
 * 
 */
public class AiWeapon extends Weapon {
	
	public AiWeapon(double x, double y, int carNum) {
		super("machineGun", x, y, 88, 3, 10, 500, 13, Math.PI * 3 / 2, 2, carNum);
	}
}
