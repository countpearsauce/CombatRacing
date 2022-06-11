package powerups;
import obstacles.Car;
import processing.core.PApplet;

/***
 * 
 *
 * @author Vishnu
 * @version 5/21/2018 
 * 
 * Represents the effects the car undergoes once the powerup is enabled on it
 *
 */

public abstract class Effect {

	private int duration;
	
	public Effect(int duration) {
		this.duration = duration;
	}
	
	public int getDuration;
	
	public boolean act(Car c) {
		duration--;
		return(duration < 1);
			
	}
	
	public abstract void draw(double x, double y, PApplet drawer);
}
