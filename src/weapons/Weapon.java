package weapons;
import java.util.ArrayList;

import obstacles.Car;
import obstacles.InteractiveObject;
import processing.core.PApplet;
import processing.core.PImage;
/**
 * 
 * @author Jeremy and Vishnu
 * @version 5/14/19 9:27PM
 * 
 * This class represents the turret mounted on each car
 *
 */
@SuppressWarnings("serial")
public class Weapon extends InteractiveObject {

	private int carNum;
	private double accuracy;
	private int damage;
	private int fireRate;
	private int cooldown;
	private double bulletVelocity;
	private double bulletRange;
	private ArrayList<Bullet> bullets;
	private static final int width = 16;
	private static final int height = 16;
	private int bulletSize;

	public Weapon(String object, double x, double y, double accuracy, int damage, int fireRate, double bulletRange, double bulletVelocity, double angle, int bulletSize, int carNum) {
		super(object, x, y, width, height, true, false);
		setAngle(angle);
		this.accuracy = accuracy;
		this.bulletRange = bulletRange;
		this.bulletVelocity = bulletVelocity;
		this.damage = damage;
		this.fireRate = fireRate;
		this.carNum = carNum;
		bullets = new ArrayList<Bullet>();
		cooldown = 0;
		this.bulletSize = bulletSize;
	}
	
	public Weapon(String object, double x, double y, double accuracy, int damage, int fireRate, int carNum) {
		super(object, x, y, width, height, true, false);
		setAngle(Math.PI/2 * 3);
		this.accuracy = accuracy;
		this.bulletRange = 500;
		this.bulletVelocity = 10;
		this.damage = damage;
		this.fireRate = fireRate;
		this.carNum = carNum;
		bullets = new ArrayList<Bullet>();
		cooldown = 0;
		this.bulletSize = 2;
	}
	
	public ArrayList<Bullet> getBullets(){
		return bullets;
	}
	
	public void shoot(){
		if(cooldown == 0) {
		bullets.add(new Bullet(carNum, getXCenter(), getYCenter(), damage, bulletVelocity, getAngle() + 2*Math.PI*(Math.random() - 0.5)*(100 - accuracy)/100.0, bulletRange, bulletSize));
		cooldown = fireRate;
		}
	}
	
	public void setFireRate(int a) {
		fireRate = a;
	}
	
	public int getFireRate() {
		return fireRate;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void slowFireRate(double a) {
		fireRate /= a;
	}
	
	public void act(ArrayList<InteractiveObject> o, ArrayList<Car> c) {
		if(cooldown > 0)
			cooldown --;
		
		for(int i = 0; i < bullets.size(); i++) {
			if(bullets.get(i).act(o, c))
				bullets.remove(i);
		}
	}
	
	public void draw(PApplet drawer) {
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(drawer);
		}
		super.draw(drawer);
	}

}
