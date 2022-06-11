package obstacles;
import java.util.ArrayList;

import powerups.Effect;
import processing.core.PApplet;
import weapons.Cannon;
import weapons.MachineGun;
import weapons.Sniper;
import weapons.Weapon;

/**
 * 
 * @author Vishnu and Jeremy
 * @version 5/19/18 11:20PM
 * 
 * Represents a car on the track
 *
 */
public class Car extends MoveableObject {

	/**
	 * 
	 */
	private static int carCount = 0;
	private int hp;
	private Weapon turret;
	private int carNum;

	// these might change to not be static finals
	private double maxForwardAcceleration;
	private double maxForwardSpeed;
	private double maxReverseAcceleration;
	private double maxReverseSpeed;
	private double turnSpeedLoss;
	private double driftSpeedLoss;
	private double brakeValue;
	public static final int CAR_WIDTH = 16;
	public static final int CAR_HEIGHT = 29;
	private ArrayList<Effect> effects;
	private double terrainModifier;

	public Car(String object, double x, double y, int hp) {

		super(object, x, y, CAR_WIDTH, CAR_HEIGHT, true, true);
		effects = new ArrayList<Effect>();
		this.hp = hp;
		carNum = carCount;
		carCount++;

		turret = new Sniper(x, y, carNum);

		maxForwardAcceleration = 0.05;
		maxForwardSpeed = 7.0;
		maxReverseAcceleration = 0.03;
		maxReverseSpeed = 5.0;
		turnSpeedLoss = 0.015;
		driftSpeedLoss = 0.015;
		brakeValue = 0.07;
		terrainModifier = 1;

	}

	public Car(String object, double x, double y, int hp, int weapon) {

		super(object, x, y, CAR_WIDTH, CAR_HEIGHT, true, true);
		effects = new ArrayList<Effect>();
		this.hp = hp;
		carNum = carCount;
		carCount++;

		if (weapon == 0)
			turret = new MachineGun(x, y, carNum);
		else if (weapon == 1)
			turret = new Sniper(x, y, carNum);
		else if (weapon == 2)
			turret = new Cannon(x, y, carNum);

		maxForwardAcceleration = 0.05;
		maxForwardSpeed = 7.0;
		maxReverseAcceleration = 0.03;
		maxReverseSpeed = 5.0;
		turnSpeedLoss = 0.006;
		driftSpeedLoss = 0.015;
		brakeValue = 0.07;
		terrainModifier = 1;

	}
	
	public static void reset() {
		
		carCount = 0;
	
	}

	public void takeHit(double damage) {

		hp -= damage;
		if (hp < 0)
			hp = 0;

	}

	public void setHealth(int health) {
		hp = health;
	}

	public void adjustHealth(int amount) {
		hp += amount;
	}

	public int getHealth() {
		return hp;
	}

	public int getCarNum() {
		return carNum;
	}

	public void accelerateForward() {
		if (canMoveForward())
			speedUp(maxForwardAcceleration
					- getVelocity() / (maxForwardSpeed / (terrainModifier * maxForwardAcceleration)));
	}

	public void accelerateForward(double fraction) {
		if (canMoveForward())
			speedUp(fraction * (maxForwardAcceleration
					- getVelocity() / (maxForwardSpeed / (terrainModifier * maxForwardAcceleration))));
	}

	public void slowDown(boolean turning) {
		double amount;
		if (turning)
			amount = turnSpeedLoss * Math.sqrt(terrainModifier);
		else
			amount = driftSpeedLoss * Math.sqrt(terrainModifier);

		if (getVelocity() > 0) {
			super.slowDown(amount);
		}

	}

	public void rotate(double amount) {
		super.rotate(amount);
		turret.rotate(amount);
	}

	public void turn(boolean right) {
		if (canTurn()) {
			double a = 1.5 / (Math.abs(3 / getVelocity()) + 40 + getVelocity() * (1 + 3 * terrainModifier));
			if (right) {
				rotate(a);
			} else {
				rotate(-a);
			}
		}
	}

	public void turn(boolean right, double fraction) {
		if (canTurn()) {
			double a = fraction * 1.5 / (Math.abs(3 / getVelocity()) + 40 + getVelocity() * (1 + 3 * terrainModifier));
			if (right) {
				rotate(a);
			} else {
				rotate(-a);
			}
		}
	}

	public void reverse() {
		if (canMoveBack())
			speedUp(-(maxReverseAcceleration
					- getVelocity() / (maxReverseSpeed / (terrainModifier * maxReverseAcceleration))));
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

	public void brake() {
		if (getForwardVelocity() > 0) {
			speedUp(-brakeValue / terrainModifier);
			if (getForwardVelocity() < 0)
				setForwardVelocity(0);
		} else if (getForwardVelocity() < 0) {
			speedUp(brakeValue / terrainModifier);
			if (getForwardVelocity() > 0)
				setForwardVelocity(0);
		}
	}

	public void brake(double ratio) {
		if (getForwardVelocity() > 0) {
			speedUp(ratio * -brakeValue / terrainModifier);
			if (getForwardVelocity() < 0)
				setForwardVelocity(0);
		} else if (getForwardVelocity() < 0) {
			speedUp(ratio * brakeValue / terrainModifier);
			if (getForwardVelocity() > 0)
				setForwardVelocity(0);
		}
	}

	public Weapon getWeapon() {
		return turret;
	}

	public void setWeapon(Weapon w) {
		turret = w;
	}

	public void act(double terrainModifier) {

		double a = 1 + terrainModifier * 0.05;
		this.terrainModifier = a;

		super.slowStrafe(0.5);

		super.act();
		turret.setCenter(getXCenter(), getYCenter());

		for (int i = 0; i < effects.size(); i++) {
			if (effects.get(i).act(this)) {
				effects.remove(i);
			}
		}

	}

	public double getAdjustedSpeed() {
		double res = getVelocity() * 1000;
		int res1 = (int) res;

		return res1 / 50;

	}

	public void addEffect(Effect e) {
		effects.add(e);
	}

	@Override
	public void draw(PApplet drawer) {
		super.draw(drawer);
		turret.draw(drawer);
		for (int i = 0; i < effects.size(); i++) {
			effects.get(i).draw(getXCenter(), getYCenter(), drawer);
		}
	}

}