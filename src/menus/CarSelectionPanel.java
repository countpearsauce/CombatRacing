package menus;
import java.awt.Color;
import java.util.ArrayList;

import drawing.DrawingSurface;
import obstacles.InteractiveObject;
import processing.core.PApplet;

/**
 * 
 * @author Jeremy
 * @version 5/20/18 12:12AM
 * 
 * Screen where players can change which weapons they use
 * 
 */
public class CarSelectionPanel extends Panel {

	private int index1;				// stores weapon selection for player 1
	private int index2;				// stores weapon selection for player 2

	private boolean twoPlayer;		// whether second car is player controlled

	private boolean p1ready;		// ready-up for player 1
	private boolean p2ready;		// ready-up for player 2

	private String[] weapons;		// names of each weapon

	private InteractiveObject a;	// displays car 1
	private InteractiveObject b;	// displays car 1's turret

	private InteractiveObject c;	// displays car 2
	private InteractiveObject d;	// displays car 2's turret

	public CarSelectionPanel(int width, int height, boolean twoPlayer) {
		
		// initialize everything
		super(width, height);
		this.twoPlayer = twoPlayer;

		index1 = 0;
		index2 = 0;

		p1ready = false;
		p2ready = false;
		
		weapons = new String[] { "Machine Gun", "Sniper", "Cannon" };

		Button exitToMenu = new Button(70, 1060, 140, 40, "Exit To Main");
		
		getButtons().add(exitToMenu);
		
		// player 1 menu instructions
		getButtons().add(new Button(getWidth() / 2, 420, 560, 40, "Player 1: Left"));
		getButtons().add(new Button(getWidth() / 2 - 140, 460, 280, 40, "Switch Weapon: A/D"));
		getButtons().add(new Button(getWidth() / 2 + 140, 460, 280, 40, ""));
		getButtons().add(new Button(getWidth() / 2, 500, 560, 40, ""));

		// players 2 menu instructions
		if (twoPlayer) {

			getButtons().add(new Button(getWidth() / 2, 780, 560, 40, "Player 2: Right"));
			getButtons().add(new Button(getWidth() / 2 - 140, 820, 280, 40, "Switch Weapon: Left/Right"));
			getButtons().add(new Button(getWidth() / 2 + 140, 820, 280, 40, ""));
			getButtons().add(new Button(getWidth() / 2, 860, 560, 40, ""));
		}
		
		for (Button b : getButtons()) {
			b.setBorderColor(Color.WHITE);
			b.setTextColor(Color.WHITE);
		}
		
	}

	// drawer
	public void draw(PApplet drawer) {

		super.draw(drawer);

		// weapon text display
		String t1 = "";
		String t2 = "";
		if(index1 == 0) 
			t1 = "machineGun";
		else if(index1 == 1)
			t1 = "sniper";
		else if(index1 == 2)
			t1 = "cannon";
		if(index2 == 0) 
			t2 = "machineGun";
		else if(index2 == 1)
			t2 = "sniper";
		else if(index2 == 2)
			t2 = "cannon";
		
		// car and turret display
		a = new InteractiveObject("car1", getWidth() / 2, 320, 50, 93, false, false);
		b = new InteractiveObject(t1, getWidth() / 2, 320, 50, 50, false, false);
		c = new InteractiveObject("car2", getWidth() / 2, 680, 50, 93, false, false);
		d = new InteractiveObject(t2, getWidth() / 2, 680, 50, 50, false, false);
		
		a.draw(drawer);
		b.draw(drawer);

		// weapon choice + ready display
		if (twoPlayer) {
			c.draw(drawer);
			d.draw(drawer);
			drawer.text(weapons[index2], getWidth() / 2 + 140, 820);

			if (p2ready)
				drawer.text("READY", getWidth() / 2, 860);
			else
				drawer.text("PRESS DOWN ARROW WHEN READY", getWidth() / 2, 860);

		}

		drawer.text(weapons[index1], getWidth() / 2 + 140, 460);

		if (p1ready)
			drawer.text("READY", getWidth() / 2, 500);
		else
			drawer.text("PRESS 'S' WHEN READY", getWidth() / 2, 500);

	}

	// interpret key presses to car selection
	public void keyPressed(char key, int keyCode) {
		if (key == 'a') {
			index1--;
			if (index1 < 0)
				index1 = weapons.length - 1;
		} else if (key == 'd') {
			index1++;
			if (index1 == weapons.length)
				index1 = 0;
		} else if (key == 's') {
			p1ready = !p1ready;
		} else if (twoPlayer) {
			if (keyCode == LEFT) {
				index2--;
				if (index2 < 0)
					index2 = weapons.length - 1;
			} else if (keyCode == RIGHT) {
				index2++;
				if (index2 == weapons.length)
					index2 = 0;
			} else if (keyCode == DOWN) {
				p2ready = !p2ready;
			}
		}

		if (p1ready && (p2ready || !twoPlayer)) {
			DrawingSurface.startRace(index1, index2, !twoPlayer);
		}

	}

	// menu clicking
	public void click(double x, double y) {
		ArrayList<Button> box = getButtons();

		if (box.get(0).click(x, y)) {
			DrawingSurface.reset();
			Menu.setPanel(new MainMenu(getWidth(), getHeight()));
		}

	}
}
