package menus;

import java.awt.Color;
import java.util.ArrayList;

import drawing.DrawingSurface;
import processing.core.PApplet;

/**
 * 
 * @author Jeremy
 * @version 6/14/2022
 * 
 * Screen that displays the game instructions
 * 
 */

public class InstructionPanel extends Panel {
	
	public InstructionPanel(int width, int height) {
		super(width, height);
		
		
		ArrayList<Button> box = getButtons();
		
		Button exitToMenu = new Button(70, 1060, 140, 40, "Exit To Main");
		
		box.add(exitToMenu);
		
		box.add(new Button(width/2, 320, 470, 40, "W/S and Up/Down to Accelerate/Brake"));
		box.add(new Button(width/2, 400, 470, 40, "A/D AND Left/Right to turn"));
		box.add(new Button(width/2, 480, 470, 40, "G and > to shoot"));
		box.add(new Button(width/2, 560, 470, 40, "F,H and <,? to turn turrets"));
		box.add(new Button(width/2, 640, 470, 40, "Collect power ups to gain an advantage"));
		box.add(new Button(width/2, 720, 470, 40, "PLAY ONE PLAYER"));
		box.add(new Button(width/2, 800, 470, 40, "PLAY TWO PLAYER"));
		
		
		
		for (Button b : getButtons()) {
			b.setBorderColor(Color.WHITE);
			b.setTextColor(Color.WHITE);
		}
	}
	
	
	public void draw(PApplet drawer) {

		super.draw(drawer);
	}
	
	public void click(double x, double y) {
		if (getButtons().get(0).click(x, y)) {
			DrawingSurface.reset();
			Menu.setPanel(new MainMenu(getWidth(), getHeight()));
		} else if(getButtons().get(6).click(x,y)) {
			Menu.setPanel(new TrackSelectionPanel(getWidth(), getHeight(), new String[] {"1", "2", "3"}, false));
		}
		else if(getButtons().get(7).click(x,y)) {
			Menu.setPanel(new TrackSelectionPanel(getWidth(), getHeight(), new String[] {"1", "2", "3"}, true));
		}
	}

	public void keyPressed(char key, int keyCode) {
		
	}
	
}
