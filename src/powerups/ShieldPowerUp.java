package powerups;
import obstacles.MoveableObject;

/***
 * 
 * @author Vishnu
 * @version 5/21/2018
 * 
 * Represents the powerup and the effect of the protection the car has when the shield powerup is enabled
 */

public class ShieldPowerUp extends PowerUp{
	
	private int duration;

	public ShieldPowerUp(String imgName, double x, double y, int duration) {
		super(imgName, x, y);
		this.duration = duration;
		
	}

	
	public void effect(MoveableObject c) {
		c.addEffect(new Shield(duration, c.getHealth()));
		duration = 0;
	}
	
	
}
