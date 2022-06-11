package obstacles;
import java.util.ArrayList;

import drawing.DrawingSurface;
import processing.core.PApplet;
/**
 * 
 * @author Jeremy
 * @version 5/19/18 11:20PM
 * 
 *  Represents the track’s finish line and checkpoint
 */
public class FinishLine extends InteractiveObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InteractiveObject checkPoint;
	private int[][] carLaps;
	private ArrayList<Car> c;
	private int laps;
	
	public FinishLine(String object, double x, double y, int width, int height, double x2, double y2, int width2, int height2, ArrayList<Car> c, int laps) {
		super(object, x, y, width, height, true, false);
		checkPoint = new InteractiveObject("finishLine", x2, y2, width2, height2, true, false);
		
		carLaps = new int[c.size()][2];
		this.laps = laps;
		this.c = c;
	}

	public void act() {
		for(int i = 0; i < c.size(); i++) {
			if(carLaps[i][1] == 0 && c.get(i).collision(this)) {
				carLaps[i][1] = 1;
			} else if(carLaps[i][1] == 1 && c.get(i).collision(checkPoint)) {
				carLaps[i][1] = 2;
			} else if(carLaps[i][1] == 2 && c.get(i).collision(this)) {
				carLaps[i][1] = 1;
				carLaps[i][0] ++;
				if(carLaps[i][0] == laps)
					DrawingSurface.winner(i);
			}
		}
	}
	

	
	public InteractiveObject getCheckpoint() {
		return checkPoint;
	}
	
	public void draw(PApplet drawer) {
		super.draw(drawer);
	}
	
	public int[][] getLaps() {
		return carLaps;
	}
}
