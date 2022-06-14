package drawing;
import java.util.ArrayList;

import obstacles.Car;
import processing.core.PApplet;
/**
 * 
 * @author Jeremy
 * @version 6/14/2022
 * 
 * Displays car information for the players to see
 */
public class HUD extends PApplet {

	private int[][] carLaps;	// number of laps completed by cars
	private ArrayList<Car> c;	// stores cars
	private int width;			// hud width
	private int height;			// hud height
	private int laps;			// max laps
	
	// constructor
	public HUD(int[][] carLaps, ArrayList<Car> c, int width, int height, int laps) {
		this.carLaps = carLaps;
		this.c = c;
		this.width = width;
		this.height = height;
		this.laps = laps;
	}
	
	// drawer
	public void draw(PApplet drawer) {
		drawer.textAlign(CENTER);
		drawOne(drawer, width/3, height, 0);
		drawOne(drawer, 2*width/3, height, 1);
		
	}
	
	// draw the lap counter for one car
	private void drawOne(PApplet drawer, double x, double y, int index) {
		
		drawer.textSize(16);
		drawer.fill(0x1F000000);
		drawer.arc((float)x, (float)y, 220, 160, (float)Math.PI, (float)(2*Math.PI));
		drawer.fill(0);
		
		//drawer.textMode(CENTER);
		
		drawer.text("Laps Done: " + (carLaps[index][0]) + " / " + laps, (float)x, (float)y - 48);
		
		drawer.text("Player " + (index + 1) + " Hp: " + c.get(index).getHealth(), (float)x, (float)y - 28);

		drawer.text("Player " + (index + 1) + " Speed: " + c.get(index).getAdjustedSpeed(), (float)x, (float)y - 8);

	}
}
