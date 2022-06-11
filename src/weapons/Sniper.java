package weapons;
/**
 * 
 * @author Jeremy
 * @version 5/19/18 11:20PM
 * This class represents a slow firing, long range weapon
 */
@SuppressWarnings("serial")
public class Sniper extends Weapon{

	public Sniper(double x, double y, int carNum) {
		super("sniper", x, y, 99.5, 15, 60, 1000, 18, Math.PI * 3 / 2, 2, carNum);
	}
	
}
