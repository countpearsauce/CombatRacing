package weapons;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import drawing.HitLine;
import obstacles.Car;
import obstacles.InteractiveObject;
import processing.core.PApplet;

/**
 * 
 * @author Vishnu
 * @version 5/14/18 9:27PM
 * 
 * Represents the projectile shot by the turrets
 *
 */
public class Bullet extends PApplet {

	private double x;
	private double y;
	private double damage;
	private int carShotBy;
	private double range;
	private double distanceTraveled;
	private double angle;
	private double velocity;
	private int size;
	
	public Bullet(int carNum, double x, double y, double damage, double velocity, double angle, double range) {
		carShotBy = carNum;
		this.x = x;
		this.y = y;
		this.damage = damage;
		this.range = range;
		distanceTraveled = 0;
		this.velocity = velocity;
		this.angle = angle;
		size = 2;

	}
	
	public Bullet(int carNum, double x, double y, double damage, double velocity, double angle, double range, int size) {
		carShotBy = carNum;
		this.x = x;
		this.y = y;
		this.damage = damage;
		this.range = range;
		distanceTraveled = 0;
		this.velocity = velocity;
		this.angle = angle;
		this.size = size;

	}
	
	public boolean isCloseTo(InteractiveObject e) {
		
		double b = Math.sqrt(Math.pow(e.getWidth(),2)+ Math.pow(e.getHeight(),2));

	double x = Math.abs(this.x - e.getXCenter());
	double y = Math.abs(this.y - e.getYCenter());
	if (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) < b/2 + 15)
		return true;
	else
		return false;
	}

	public boolean act(ArrayList<InteractiveObject> e, ArrayList<Car> c) {
		distanceTraveled += velocity;
		if(distanceTraveled > range)
			return true;
		
		Line2D.Double here = new Line2D.Double(x, y, x + velocity * Math.sin(angle),
				y - velocity * Math.cos(angle));
		
		x += velocity * Math.sin(angle);
		y -= velocity * Math.cos(angle);
		
		for (int i = 0; i < e.size(); i++) {
			if (e.get(i).isPermanent() && isCloseTo(e.get(i))) {
				ArrayList<HitLine> other = e.get(i).getLines();
				for (int k = 0; k < other.size(); k++) {
					if (here.intersectsLine(other.get(k).getLine())) {
						e.get(i).effect(null);
						return true;
					}
				}
			}
		}
		for (int i = 0; i < c.size(); i++) {
			if (c.get(i).getCarNum() != carShotBy) {
				ArrayList<HitLine> other = c.get(i).getLines();
				for (int k = 0; k < other.size(); k++) {
					
					if (here.intersectsLine(other.get(k).getLine())) {
						c.get(i).takeHit(damage);
						return true;
					}
				}
			}
		}

		return false;
	}

	public double getDamage() {
		return damage;
	}

	public void draw(PApplet drawer) {
		drawer.ellipse((float) x - size/2, (float) y - size/2, size, size);
	}

}
