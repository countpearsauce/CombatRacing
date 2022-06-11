package menus;
import processing.core.PApplet;

/**
 * 
 * @author Vishnu
 * @version 5/19/11:20 PM
 * 
 * Represents menu screens
 */
public class Menu extends PApplet {

	private static Panel panel;

	public Menu(int width, int height) {
		this.width = width;
		this.height = height;

		panel = new MainMenu(width, height);
		

	}

	public static void setPanel(Panel a) {
		panel = a;
	}
	
	public Panel getPanel() {
		return panel;
	}
	
	public void draw(PApplet drawer) {
		drawer.background(255);

		panel.draw(drawer);

	}

	public void click(double x, double y) {
		
		panel.click(x, y);
		
	}
	
	public void keyPressed(char key, int keyCode) {
		panel.keyPressed(key, keyCode);
	}
}
