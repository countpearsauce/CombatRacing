package drawing;
import java.awt.Color;
import java.util.HashSet;

import menus.Button;
import menus.MainMenu;
import menus.Menu;
import obstacles.Car;
import processing.core.PApplet;
import processing.core.PImage;
import tracks.Track;
import weapons.AiWeapon;

/**
 * 
 * @author Jeremy
 * @version 5/19/18 11:20PM
 * 
 * Stores objects and calls them at a time interval, and takes user input
 *
 */
public class DrawingSurface extends PApplet {

	private HashSet<Integer> keys = new HashSet<Integer>();

	private Menu menu;
	private static boolean drawMainMenu = true;
	private static String winner = "";

	private static int laps = 3;
	private static String endPath = "1";

	private static boolean Ai = false;

	private static HUD hud = null;

	private static int count = 0;
	private int readySetGo = 200;
	private int gracePeriod = 400;
	
	private static boolean trackM = false;

	private static Track track;
	public static PImage car1Img, car2Img, dot, track1, track2, track3, barrel1, barrel2, barrel3, machineGun, sniper, cannon,
			fence, finishLine, hpBoost, shield, weaponPower, background;
	
	private static Button exitToMenu;

	public DrawingSurface() {

		
		track = null;

		exitToMenu = new Button(70, 1060, 140, 40, "Exit To Main");
		exitToMenu.setFillColor(Color.LIGHT_GRAY);
		
	}

	public static void winner(int i) {
		winner = (i + 1) + "";
	}

	public static void loser() {
		winner = "lose";
	}

	public static void setTrack(String path, int l) {
		
		laps = l;
		endPath = path;
	}

	public static void startRace(boolean ai) {
		drawMainMenu = false;
		track = new Track(endPath, laps);
		hud = new HUD(track.getFinish().getLaps(), track.getCars(), 1920, 1080, laps);
		Ai = ai;
		
		if(Ai) {
			Car a = track.getCar(1);
			a.setWeapon(new AiWeapon(a.getXCenter(), a.getYCenter(), 1));
		}
		
		
	}

	public static void startRace(int weapon1, int weapon2, boolean ai) {
		drawMainMenu = false;
		track = new Track(endPath, laps, weapon1, weapon2);
		hud = new HUD(track.getFinish().getLaps(), track.getCars(), 1920, 1080, laps);
		Ai = ai;
		
		if(Ai) {
			Car a = track.getCar(1);
			a.setWeapon(new AiWeapon(a.getXCenter(), a.getYCenter(), 1));
		}
		
	
	}

	public static void editTrackMode(boolean input) {
		trackM = input;
	}

	@Override
	public void settings() {
		fullScreen();
		// size(1920, 1080);
		menu = new Menu(1920, 1080);
	}

	@Override
	public void setup() {
		
		
		barrel1 = loadImage("img\\barrel_gray.png");
		barrel2 = loadImage("img\\barrel_red.png");
		barrel3 = loadImage("img\\barrel_blue.png");
		car1Img = loadImage("img\\car1.png");
		car2Img = loadImage("img\\car2.png");
		dot = loadImage("img\\dot.png");
		track1 = loadImage("img\\track1.bmp");
		track2 = loadImage("img\\track2.bmp");
		track3 = loadImage("img\\track3.bmp");
		machineGun = loadImage("img\\machineGun.png");
		sniper = loadImage("img\\sniper.png");
		cannon = loadImage("img\\cannon.png");
		fence = loadImage("img\\fence.png");
		finishLine = loadImage("img\\finish.bmp");
		hpBoost = loadImage("img\\poweruphealth.jpg");
		shield = loadImage("img\\shield.png");
		weaponPower = loadImage("img\\greenOrb.png");
		background = loadImage("img\\background.jpg");
		
		menu.getPanel().setBackground(loadImage("img\\background.jpg"));

	}

	public static void reset() {
		trackM = false;
		drawMainMenu = true;
		winner = "";
		count = 0;
		Ai = false;
		track = null;
		
		laps = 3;
		endPath = "1";

		Ai = false;

		hud = null;

		count = 0;
		
	}
	
	@Override
	public void draw() {
		if (drawMainMenu) {
			menu.draw(this);
		} else {
			
			if (count % 300 == 0 && !trackM) 
				track.generatePowerUp();

			Car car1 = track.getCar(0);
			Car car2 = track.getCar(1);

			track.act();

			track.draw(this);

			if (count > readySetGo && Ai == true && winner.equals("")) {
				track.AiAct(car2, count > gracePeriod, this);
			}

			count++;

			if (!winner.equals("")) {
				textSize(50);
				if (winner.equals("lose"))
					text("Everyone Died...", width / 2, height / 2);
				else
					text("player " + winner + " won", width / 2, height / 2);
			}

			// car1
			if (car1.getHealth() > 0 && winner.equals("")) {
				if (count > readySetGo) {
					if (keys.contains((int) 'w') && !keys.contains((int) 's')) {
						car1.accelerateForward();
					} else if (keys.contains((int) 's') && !keys.contains((int) 'w')) {
						if (car1.getForwardVelocity() > 0)
							car1.brake();
						else
							car1.reverse();
					} else
						car1.slowDown(false);
					if (keys.contains((int) 'a') && !keys.contains((int) 'd')) {
						if (car1.getVelocity() != 0) {

							car1.turn(false);
							car1.slowDown(true);
						}
					}
					if (keys.contains((int) 'd') && !keys.contains((int) 'a')) {
						if (car1.getVelocity() != 0) {
							car1.turn(true);
							car1.slowDown(true);
						}
					}

					if (keys.contains((int) 'f') && !keys.contains((int) 'h'))
						car1.getWeapon().rotate(-Math.PI / 72);
					if (keys.contains((int) 'h') && !keys.contains((int) 'f'))
						car1.getWeapon().rotate(Math.PI / 72);

				}
				if (count > gracePeriod && keys.contains((int) 'g'))
					car1.getWeapon().shoot();

			} else
				car1.slowDown(false);
			// car2

			if (car2.getHealth() > 0 && winner.equals("")) {

				if (count > readySetGo) {
					if (keys.contains(UP) && !keys.contains(DOWN)) {
						car2.accelerateForward();
					} else if (keys.contains(DOWN) && !keys.contains(UP)) {
						if (car2.getForwardVelocity() > 0)
							car2.brake();
						else
							car2.reverse();
					} else
						car2.slowDown(false);
					if (keys.contains(LEFT) && !keys.contains(RIGHT)) {
						if (car2.getVelocity() != 0) {

							car2.turn(false);
							car2.slowDown(true);
						}
					}
					if (keys.contains(RIGHT) && !keys.contains(LEFT)) {
						if (car2.getVelocity() != 0) {

							car2.turn(true);
							car2.slowDown(true);
						}
					}
					if (keys.contains((int) ',') && !keys.contains((int) '/'))
						car2.getWeapon().rotate(-Math.PI / 92);
					if (keys.contains((int) '/') && !keys.contains((int) ','))
						car2.getWeapon().rotate(Math.PI / 92);
				}

				if (count > gracePeriod && keys.contains((int) '.'))
					car2.getWeapon().shoot();
			} else
				car2.slowDown(false);

			hud.draw(this);
			
			
			exitToMenu.draw(this);
			
			textSize(50);
			if(count < readySetGo) {
				if(count < readySetGo * 2 / 3) {
					if(count < readySetGo /3) {
						text("3", width/2, height/2);
					}
					else
						text("2", width/2, height/2);
				}
				else
					text("1", width/2, height/2);
			}
			
		}
	}

	public void mousePressed() {
		if (drawMainMenu) {
			menu.click(mouseX, mouseY);
		} else if (!drawMainMenu) {
			
			if(exitToMenu.click(mouseX, mouseY)) {
				reset();
				Menu.setPanel(new MainMenu(width, height));
			}
		}
	}

	@Override
	public void keyPressed() {
		if (drawMainMenu) {
			menu.keyPressed(key, keyCode);
		} else {
			if (key == 'w' || key == 'a' || key == 's' || key == 'd' || key == 'f' || key == 'g' || key == 'h') {
				keys.add((int) key);
			} else if (keyCode == UP || keyCode == DOWN || keyCode == LEFT || keyCode == RIGHT || keyCode == ','
					|| keyCode == '.' || keyCode == '/') {
				keys.add(keyCode);
			} 
		}
	}

	@Override
	public void keyReleased() {

		if (key == 'w' || key == 'a' || key == 's' || key == 'd' || key == 'f' || key == 'g' || key == 'h') {
			keys.remove((int) key);
		}
		if (keyCode == UP || keyCode == LEFT || keyCode == DOWN || keyCode == RIGHT || key == ',' || key == '.'
				|| key == '/') {
			keys.remove(keyCode);
		}

	}

	public static PImage getPImage(String object) {

		if (object == null)
			throw new IllegalArgumentException("String passed in was null");
		else if (object.equals("barrel1"))
			return barrel1;
		else if (object.equals("barrel2"))
			return barrel2;
		else if (object.equals("barrel3"))
			return barrel3;
		else if (object.equals("car1"))
			return car1Img;
		else if (object.equals("car2"))
			return car2Img;
		else if (object.equals("dot"))
			return dot;
		else if (object.equals("track1"))
			return track1;
		else if (object.equals("track2"))
			return track2;
		else if (object.equals("track3"))
			return track3;
		else if (object.equals("machineGun"))
			return machineGun;
		else if (object.equals("sniper"))
			return sniper;
		else if (object.equals("cannon"))
			return cannon;
		else if (object.equals("finishLine"))
			return finishLine;
		else if (object.equals("hpBoost"))
			return hpBoost;
		else if (object.equals("shield"))
			return shield;
		else if (object.equals("weaponPower"))
			return weaponPower;
		else if(object.equals("background"))
			return background;
		else if (object.equals("")) {
			PImage a = new PImage(1, 1);
			a.pixels[0] = 0x10000000;
			return a;
		} else if (object.equals("fence"))
			return fence;
		else
			throw new IllegalArgumentException("No image for " + object + " found");
	}
}
