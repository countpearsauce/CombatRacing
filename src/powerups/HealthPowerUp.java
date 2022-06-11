package powerups;
import obstacles.MoveableObject;

/***
 * 
 * @author Vishnu
 * @version 5/21/2018
 * 
 * Represents a health powerup which rejuvenates the HP of a car
 * 
 */
public class HealthPowerUp extends PowerUp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int health;
	
	public HealthPowerUp(String imgName, double x, double y, int health) {
		super(imgName, x, y);
		this.health = health;
	}
	
	public void effect(MoveableObject c) {
		c.adjustHealth(health);
		health = 0;
	}
}
