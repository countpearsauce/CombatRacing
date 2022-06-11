package menus;
import java.awt.Color;
import java.util.ArrayList;

import drawing.DrawingSurface;
import processing.core.PApplet;

/**
 * 
 * @author Vishnu
 * @version 5/20/18 12:12AM This class represents the Map selection
 *          screen
 */
public class TrackSelectionPanel extends Panel {
	
	private String[] tracks;
	private int index = 0;
	private int laps = 3;
	private boolean twoPlayer;

	public TrackSelectionPanel(int width, int height, String[] tracks, boolean twoPlayer) {
		super(width, height);

		setBackground(DrawingSurface.getPImage("background"));
		
		this.twoPlayer = twoPlayer;
		
		
		this.tracks = new String[tracks.length];
				
				
		int num = 0;
		for(int i = 0; i < tracks.length; i++) {
			
				this.tracks[num] = tracks[i];
				num++;
				
		}
		
		
		getButtons().add(new Button(getWidth() / 2, 120, 560, 40, "Choose The Track"));
		
		getButtons().add(new Button(getWidth() / 2, 840, 560, 40, "Change Laps: Left/Right"));
		
		getButtons().add(new Button(getWidth() / 2, 880, 560, 40, "Switch Tracks: A/D"));
		
		getButtons().add(new Button(getWidth() / 2, 920, 560, 40, "Next: S"));
		
		Button exitToMenu = new Button(70, 1060, 140, 40, "Exit To Main");
		
		getButtons().add(exitToMenu);
		
		for (Button b : getButtons()) {
			b.setBorderColor(Color.WHITE);
			b.setTextColor(Color.WHITE);
		}

	}

	public void draw(PApplet drawer) {

		super.draw(drawer);
		
	
		drawer.textSize(50);
		if(laps != 10)
		drawer.text("Laps: " + laps, getWidth()/2, 775);
		else
			drawer.text("Laps: Unlimited", getWidth()/2, 700);
		
		drawer.image(DrawingSurface.getPImage(("track" + tracks[index].charAt(0))), getWidth()/2 - 445,getHeight()/2 - 370, 890, 500);

		drawer.text("Track: " + tracks[index], getWidth()/2, 700);

		

	}

	public void keyPressed(char key, int keyCode) {
		if (key == 'a') {
			index--;
			if (index < 0)
				index = tracks.length - 1;
		} else if (key == 'd') {
			index++;
			if (index == tracks.length)
				index = 0;
		} else if(keyCode == RIGHT) {
			if(laps < 10)
			laps ++;
			else
				laps = 1;
		} else if(keyCode == LEFT) {
			if(laps == 1)
			laps = 10;
			else
				laps--;
		} else if(key == 's') {
			if(laps != 10) {
				DrawingSurface.setTrack(tracks[index], laps);
				} else
					DrawingSurface.setTrack(tracks[index], 99);
				
				Menu.setPanel(new CarSelectionPanel(getWidth(), getHeight(), twoPlayer));
		}
		
		

	}

	public void click(double x, double y) {
		ArrayList<Button> box = getButtons();

		if (box.get(4).click(x, y)) {
			DrawingSurface.reset();
			Menu.setPanel(new MainMenu(getWidth(), getHeight()));
		}

	}
}
