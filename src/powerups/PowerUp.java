package powerups;
import obstacles.InteractiveObject;
import obstacles.MoveableObject;
import processing.core.PImage;

/**
 * @author Vishnu
 * @version 5/10/18 10:20PM
 * 
 * This class represents the power-ups that appear on the track that enhance cars
 * 
 */

public abstract class PowerUp extends InteractiveObject{
	private static final int POWER_UP_SIZE = 16;
	
	public PowerUp (String imgName, double x, double y) {
		super(imgName, x, y, POWER_UP_SIZE, POWER_UP_SIZE, false, false);
		
	}
	
	public abstract void effect(MoveableObject c);
	
	
}
