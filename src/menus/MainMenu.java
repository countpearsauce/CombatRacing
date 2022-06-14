package menus;
import java.util.ArrayList;

import drawing.DrawingSurface;

import java.awt.Color;

import processing.core.PApplet;

/**
 * 
 * @author Jeremy
 * @version 5/19/18 11:20PM
 * 
 * Screen found as soon as game launches, where players can select what to do
 *
 */
public class MainMenu extends Panel {
	
	
	
	public MainMenu(int width, int height) {
		super(width, height);
		
		setBackground(DrawingSurface.getPImage("background"));
		
		ArrayList<Button> buttons = getButtons();
		
		buttons.add(new Button(width / 2, 400, 180, 40, "ONE PLAYER RACE"));
		buttons.add(new Button(width / 2, 480, 180, 40, "TWO PLAYER RACE"));
		buttons.add(new Button(width / 2, 560, 180, 40, "INSTRUCTIONS"));
		
		
		
		for (Button b : buttons) {
			b.setBorderColor(Color.WHITE);
			b.setTextColor(Color.WHITE);
		}
		//	DrawingSurface.setTrack(3, 3);
	}
	
	public void draw(PApplet drawer) {
		super.draw(drawer);
		
		drawer.textSize(80);
		drawer.text("COMBAT RACING", getWidth()/2, 120);
		
	}
	
	public void click(double mouseX, double mouseY) {
	
		if(getButtons().get(0).click(mouseX, mouseY)) {
			Menu.setPanel(new TrackSelectionPanel(getWidth(), getHeight(), new String[] {"1", "2", "3"}, false));
			
		} else if(getButtons().get(1).click(mouseX, mouseY)) {
			Menu.setPanel(new TrackSelectionPanel(getWidth(), getHeight(), new String[] {"1", "2", "3"}, true));
			
		} 
		else if (getButtons().get(2).getShape().contains(mouseX, mouseY)) {
			Menu.setPanel(new InstructionPanel(getWidth(), getHeight()));
		}
		
	}
	
	public void keyPressed(char key, int keyCode) {
		
	}
}
