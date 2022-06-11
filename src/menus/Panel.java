package menus;
import java.util.ArrayList;

import drawing.DrawingSurface;
import processing.core.PApplet;
import processing.core.PImage;
/**
 * 
 * @author Jeremy
 * @version 5/19/18 11:20PM
 * 
 * Represents any single menu screen
 *
 */
public abstract class Panel extends PApplet {
	
	private PImage background;
	
	private ArrayList<Button> buttons;
	private int width;
	private int height;
	
	public Panel(int width, int height) {
		buttons = new ArrayList<Button>();
		this.width = width;
		this.height = height;
		
		setBackground(DrawingSurface.getPImage("background"));
		
	}
	
	public ArrayList<Button> getButtons() {
		return buttons;
	}
	
	public void setBackground(PImage bg) {
		background = bg;
	}
	
	public void draw(PApplet drawer) {
		if (background != null) {
			drawer.image(background, 0, 0, width, height);
		}
		
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).draw(drawer);
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public abstract void click(double x, double y);
	
	public abstract void keyPressed(char key, int keyCode);
	
}
