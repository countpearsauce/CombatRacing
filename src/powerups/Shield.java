package powerups;
import obstacles.Car;
import processing.core.PApplet;

/***
 * 
 * @author Vishnu
 * @version 5/21/2018
 * 
 *  Represents the physical “shield” or circle around the car when the shield powerup is taken
 */

public class Shield extends Effect {

	private int health;

	public Shield(int duration, int health) {
		super(duration);
		this.health = health;
	}

	@Override
	public boolean act(Car c) {
		
		c.setHealth(health);

		return super.act(c);
	}

	@Override
	public void draw(double x, double y, PApplet drawer) {

		drawer.noFill();
		drawer.stroke(0,0,255);
		drawer.ellipseMode(drawer.CENTER);
		drawer.ellipse((float)x, (float)y, 36,36);
		drawer.stroke(0);
		
	}

}
