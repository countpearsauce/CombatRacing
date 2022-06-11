package weapons;
/**
 * 
 * @author Jeremy
 * @version 5/19/18 11:20PM
 * 
 * Fast firing Weapon mounted on Cars
 * 
 */
@SuppressWarnings("serial")
public class MachineGun extends Weapon{

	public MachineGun(double x, double y, int carNum) {
		super("machineGun", x, y, 97, 3, 10, 500, 10, Math.PI * 3 / 2, 2, carNum);
	}
}
