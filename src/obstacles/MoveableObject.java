package obstacles;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import drawing.HitLine;
import powerups.Effect;
import processing.core.PApplet;
import processing.core.PImage;
import weapons.Weapon;

/**
 * 
 * @author Shelby/Vishnu/Jeremy
 * @version 5/19/18 11:20PM
 * 
 * Represents things on the track that can move and interact with other objects
 * 
 */
public abstract class MoveableObject extends InteractiveObject {

	private double forwardVelocity;
	private double strafeVelocity;
	private boolean canMoveForward = true;
	private boolean canMoveBack = true;
	private boolean canTurn = true;

	// CONSTRUCTORS
	public MoveableObject(String object, double xCenter, double yCenter, double width, double height, boolean permanent,
			boolean solid) {
		super(object, xCenter, yCenter, width, height, permanent, solid);
		forwardVelocity = 0;
		strafeVelocity = 0;
	}

	/*
	 * public MoveableObject(PImage img, boolean solid) { super(img, solid); }
	 */

	// METHODS

	public void moveByAmount(double x, double y) {
		setCenter(getXCenter() + x, getYCenter() - y);
	}

	
	// forward means up for rotation = 0
	public void moveForward(double amount) {
		setCenter(getXCenter() + amount * Math.sin(getAngle()), getYCenter() - amount * Math.cos(getAngle()));

	}

	public double getVelocity() {
		return Math.sqrt(Math.pow(forwardVelocity, 2) + Math.pow(strafeVelocity, 2));
	}

	public void speedUp(double amount) {
		forwardVelocity += amount;

	}

	public boolean canMoveForward() {
		return canMoveForward;
	}

	public boolean canMoveBack() {
		return canMoveBack;
	}

	public boolean canTurn() {
		return canTurn;
	}

	public void slowDown(double amount) {
		if (forwardVelocity > 0) {
			forwardVelocity -= amount;
			if (forwardVelocity < 0)
				forwardVelocity = 0;
		} else {
			forwardVelocity += amount;
			if (forwardVelocity > 0)
				forwardVelocity = 0;
		}

	}

	public double getStrafeVelocity() {
		return strafeVelocity;
	}

	public double getForwardVelocity() {
		return forwardVelocity;
	}

	public void setForwardVelocity(double y) {
		forwardVelocity = y;

	}

	public void setStrafeVelocity(double x) {
		strafeVelocity = x;
	}

	public void slowStrafe(double amount) {
		if (strafeVelocity > 0) {
			strafeVelocity -= amount;
			if (strafeVelocity < 0)
				strafeVelocity = 0;
		} else {
			strafeVelocity += amount;
			if (strafeVelocity > 0)
				strafeVelocity = 0;
		}
	}

	public void act() {
		moveByAmount(strafeVelocity * Math.cos(getAngle()) + forwardVelocity * Math.sin(getAngle()),
				-strafeVelocity * Math.sin(getAngle()) + forwardVelocity * Math.cos(getAngle()));
	}

	public boolean collision(ArrayList<Car> e, int a) {
		for (int j = 0; j < e.size(); j++) {
			if (j != a && isCloseTo(e.get(j))) {

				if (collision(e.get(j)) && !e.get(j).isPermanent())
					e.remove(j);

			}

		}
		return false;
	}

	public boolean collision(InteractiveObject e) {
		ArrayList<HitLine> here = getLines(); // 0:backright 1:frontright 2:front 3:frontleft 4:backleft 5:back
		ArrayList<HitLine> other = e.getLines();

		double angle1 = 0;
		double angle2 = 0;

		boolean right = false;
		boolean front = false;
		boolean left = false;
		boolean back = false;

		boolean result = false;

		for (int i = 0; i < here.size(); i++) {
			for (int k = 0; k < other.size(); k++) {
				if (here.get(i).getLine().intersectsLine(other.get(k).getLine())) {

					
					Point2D.Double p = here.get(i).findIntersection(other.get(k));

					if (p != null) {
						if (e.isSolid()) {
							if (i == 1 || i == 4) {
								angle1 = other.get(k).getBearing();
								angle2 = here.get(i).getBearing();
								
								double dx = p.getX() - here.get(i).getX2();
								double dy = p.getY() - here.get(i).getY2();
								setCenter(getXCenter() + dx, getYCenter() + dy);

							} else if (i == 3 || i == 0) {
								
								angle1 = other.get(k).getBearing();
								angle2 = here.get(i).getBearing();
								
								double dx = p.getX() - here.get(i).getX1();
								double dy = p.getY() - here.get(i).getY1();
								setCenter(getXCenter() + dx, getYCenter() + dy);

							}

							if (i == 0 || i == 1) { // right
								right = true;
							} else if (i == 2) { // front
								front = true;
							} else if (i == 3 || i == 4) { // left
								left = true;
							} else if (i == 5) { // back
								back = true;
							}

						}

						e.effect(this);

						result = true;

					}

				}
			}
		}

		// double truexvel = strafeVelocity * Math.cos(getAngle()) + forwardVelocity *
		// Math.sin(getAngle());
		// double trueyvel = -strafeVelocity*Math.sin(getAngle()) +
		// forwardVelocity*Math.cos(getAngle());

		if (right || front || left || back) {

			double temp = angle1 - angle2;
		while (temp < Math.PI - 0.001)
				temp += Math.PI;
			temp -= Math.PI;
			
			double ratio = Math.abs(Math.sin(temp));

			if (temp < Math.PI / 3)
				rotate(temp / 40);
			else if (temp < Math.PI / 2 - 0.1)
				rotate(-temp / 40);
			else if (temp >= Math.PI / 2 + 0.1 && temp < Math.PI * 2 / 3)
				rotate(temp / 40);
			else if (temp > Math.PI * 2.0 / 3)
				rotate(-temp / 40);


			forwardVelocity *= 0.8;

			if (!(left && right)) {
				if (left) {
					strafeVelocity = 1.5 * (1.00 - ratio);
				}
				if (right) {
					strafeVelocity = 1.5 * (-1.00 + ratio);
				}
			}

		}
		return result;
	}

	public boolean collision(ArrayList<InteractiveObject> e) {

		for (int j = 0; j < e.size(); j++) {
			if (isCloseTo(e.get(j))) {

				
				if (collision(e.get(j)) && !e.get(j).isPermanent())
					e.remove(j);
			}

		}
		return false;
	}

	/*
	 * public void applyWindowLimits(int windowWidth, int windowHeight) { x =
	 * Math.min(x,windowWidth-width); y = Math.min(y,windowHeight-height); x =
	 * Math.max(0,x); y = Math.max(0,y); }
	 */

	@Override
	public void draw(PApplet drawer) {
		super.draw(drawer);
	}
	
	public abstract int getHealth();
	
	public abstract void addEffect(Effect e);
	
	public abstract void takeHit(double damage);

	public abstract void adjustHealth(int amount);
	
	public abstract Weapon getWeapon();

}