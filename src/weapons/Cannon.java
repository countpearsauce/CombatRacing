package weapons;
/**
 * 
 * @author Jeremy
 * @verion 5/19/18 11:20PM
 *
 * Slow shooting Weapon mounted on Cars
 */
@SuppressWarnings("serial")
public class Cannon extends Weapon {

	public Cannon(double x, double y, int carNum) {
		super("cannon", x, y, 99, 20, 80, 700, 8, Math.PI * 3 / 2, 5, carNum);
	}
	
}
