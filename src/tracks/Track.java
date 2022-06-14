package tracks;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import drawing.DrawingSurface;
import drawing.HitLine;
import obstacles.Car;
import obstacles.FinishLine;
import obstacles.InteractiveObject;
import powerups.HealthPowerUp;
import powerups.ShieldPowerUp;
import powerups.WeaponPowerUp;
import processing.core.PApplet;
import processing.core.PImage;
import weapons.Weapon;

/**
 * 
 * @author Jeremy and Vishnu
 * @version 6/14/2022
 * 
 *          This class represents the race track and contains all track objects
 *
 */
public class Track extends PApplet {

	private int trackNum;
	private PImage track;
	private ArrayList<InteractiveObject> objects;
	private ArrayList<Car> cars;
	private FinishLine finish;

	private boolean lastRight = true;

	public Track(String endPath, int laps) {
		
		Car.reset();

		track = DrawingSurface.getPImage(("track" + endPath.charAt(0)));
		trackNum = Integer.parseInt(endPath.substring(0, 1));
		objects = new ArrayList<InteractiveObject>();

		Car c1 = new Car("car1", 870, 95, 100, 1);
		Car c2 = new Car("car2", 850, 60, 100, 1);
		c1.rotate(Math.PI * 3 / 2);
		c2.rotate(Math.PI * 3 / 2);

		cars = new ArrayList<Car>();
		cars.add(c1);
		cars.add(c2);

		if (trackNum == 1)
			finish = new FinishLine("finishLine", 800, 79, 32, 77, 1050, 1000, 20, 180, cars, laps);
		else if (trackNum == 2) {
			finish = new FinishLine("finishLine", 800, 79, 32, 77, 550, 920, 20, 320, cars, laps);
		} else if (trackNum == 3) {
			finish = new FinishLine("finishLine", 800, 81, 32, 77, 990, 480, 20, 300, cars, laps);
		}

		readData(("tracks\\\\track" + endPath + ".txt"));

	}

	public Track(String endPath, int laps, int weapon1, int weapon2) {
		
		Car.reset();

		track = DrawingSurface.getPImage(("track" + endPath.charAt(0)));
		trackNum = Integer.parseInt(endPath.substring(0, 1));
		objects = new ArrayList<InteractiveObject>();

		Car c1 = new Car("car1", 870, 95, 100, weapon1);
		Car c2 = new Car("car2", 850, 60, 100, weapon2);
		c1.rotate(Math.PI * 3 / 2);
		c2.rotate(Math.PI * 3 / 2);

		cars = new ArrayList<Car>();
		cars.add(c1);
		cars.add(c2);

		if (trackNum == 1)
			finish = new FinishLine("finishLine", 800, 79, 32, 77, 1050, 1000, 20, 180, cars, laps);
		else if (trackNum == 2) {
			finish = new FinishLine("finishLine", 800, 79, 32, 77, 550, 920, 20, 320, cars, laps);
		} else if (trackNum == 3) {
			finish = new FinishLine("finishLine", 800, 81, 32, 77, 990, 480, 20, 300, cars, laps);
		}

		readData(("tracks\\\\track" + endPath + ".txt"));

	}

	public ArrayList<Car> getCars() {
		return cars;
	}

	public Car getCar(int i) {
		if (cars.size() > i)
			return cars.get(i);
		else {
			throw new IndexOutOfBoundsException("index was greater than arraylist size");
		}

	}

	public ArrayList<InteractiveObject> getObjects() {
		return objects;
	}

	public void removeLastObject() {
		if (objects.size() > 1)
			objects.remove(objects.size() - 1);

	}

	public void generatePowerUp() {
		boolean retry = true;

		while (retry) {
			int x = (int) (Math.random() * 1920);
			int y = (int) (Math.random() * 1080);

			if (track.get(x, y) == -3947581) { // regular road
				double r = Math.random();
				if (r > 2.0 / 3.0)
					objects.add(new WeaponPowerUp("weaponPower", x, y, 300, 1.8, 1));
				else if (r > 1 / 3.0)
					objects.add(new ShieldPowerUp("shield", x, y, 300));
				else
					objects.add(new HealthPowerUp("hpBoost", x, y, 20));
				retry = false;
			}
		}
	}

	public void addObject(double x, double y, int width, int height, int type, double angle) {
		InteractiveObject e;
		String name;
		if (type == 1)
			name = "barrel1";
		else if (type == 2)
			name = "barrel2";
		else if (type == 3)
			name = "barrel3";
		else if (type == 4)
			name = "car1";
		else if (type == 5)
			name = "car2";
		else if (type == 6)
			name = "dot";
		else if (type == 7)
			name = "fence";
		else
			name = "make an error";
		e = new InteractiveObject(name, x, y, width, height, true, true);
		e.setAngle(angle);
		objects.add(e);

	}

	public void readData(String file) {
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(file);
			in = new ObjectInputStream(fis);

			objects = (ArrayList<InteractiveObject>) in.readObject();
			System.out.println(file);

			for (int i = 0; i < objects.size(); i++) {
				objects.get(i).initialize();
			}

		} catch (IOException ex) {
			System.out.println("error");
		} catch (ClassNotFoundException ex) {
			System.out.println("error");
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	public void writeData(String file) {

		FileOutputStream fis = null;
		ObjectOutputStream out = null;
		try {
			fis = new FileOutputStream(file);
			out = new ObjectOutputStream(fis);
			out.writeObject(objects);

			System.out.println(file);

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	public void addObject(double x, double y, int width, int height, int type, int count, double angle) {
		InteractiveObject e;
		String name;
		if (type == 1)
			name = "barrel1";
		else if (type == 2)
			name = "barrel2";
		else if (type == 3)
			name = "barrel3";
		else if (type == 4)
			name = "car1";
		else if (type == 5)
			name = "car2";
		else if (type == 6)
			name = "dot";
		else if (type == 7)
			name = "fence";
		else
			name = "make an error";
		e = new InteractiveObject(name, x, y, width, height, true, true, count);
		e.setAngle(angle);
		objects.add(e);
	}

	public void act() {
		

		for (int i = 0; i < cars.size(); i++) {

			cars.get(i).collision(objects);

			cars.get(i).collision(cars, i);

			finish.act();

		}

		for (int i = 0; i < cars.size(); i++) {
			double modifier = 0;
			Car c = cars.get(i);
			ArrayList<HitLine> lines = c.getLines();
			for (int k = 0; k < lines.size(); k++) {

				int a = track.get((int) lines.get(k).getX1(), (int) lines.get(k).getY1());

				if (a == -14503604) // grass
					modifier += 4.5;
				else if (a == -8421505) // dark road
					modifier += 2.5;
				else if (a == -15305678) // dark grass
					modifier += 6;
				else if(a == -16735512){ // water
					modifier += 20;
				}
				else if(a == -3947581){ // road
					
				}
				else {
					
				}
			}
			c.act(modifier);
			c.getWeapon().act(objects, cars);
		}

		int index = -2;
		for (int i = 0; i < cars.size(); i++) {
			if (cars.get(i).getHealth() > 0) {
				if (index != -2) {
					index = -1;
					i = cars.size();
				} else
					index = i;
			}
		}

		if (index == -2)
			DrawingSurface.loser();
		else if (index >= 0)
			DrawingSurface.winner(index);

	}

	public void AiAct(Car AiBot, boolean canShoot, PApplet drawer) {

		ArrayList<HitLine> lines = AiBot.getLines();

		/*
		 * for (int k = 0; k < lines.size(); k++) { double modifier = 0;
		 * 
		 * int a = track.get((int) lines.get(k).getX1(), (int) lines.get(k).getY1());
		 * int b = track.get((int) (lines.get(k).getX1() - AiBot.getWidth()), (int)
		 * lines.get(k).getY1()); int c = track.get((int) lines.get(k).getX1(), (int)
		 * (lines.get(k).getY1() - AiBot.getHeight() * .5));
		 * 
		 * int d = track.get((int) lines.get(k).getX1(), (int) (lines.get(k).getY1() +
		 * AiBot.getWidth() * .5));
		 */
		Weapon turret = AiBot.getWeapon();

		double x1 = cars.get(0).getXCenter();
		double y1 = cars.get(0).getYCenter();
		double x2 = AiBot.getXCenter();
		double y2 = AiBot.getYCenter();
		HitLine l = new HitLine(x2, y2, x1, y1);
		double theta = l.getBearing();
		double currentAngle = turret.getAngle();
		double difference = theta - currentAngle;

		if (difference > Math.PI * 2) {
			turret.rotate(Math.PI * 2);
		} else if (difference < -Math.PI * 2) {
			turret.rotate(-Math.PI * 2);
		} else {

			if (difference > 0.01)
				turret.rotate(Math.PI / 72);
			else if (difference < -0.01)
				turret.rotate(-(Math.PI / 72));
		}

		if (canShoot) {
			turret.shoot();
		}

		double modifier = 0;
		
		double shortBearing = 0.03;
		double MediumBearing = 0.06;
		double LongBearing = 0.12;
		double flankBearing = 0.30;
		

		HitLine right1 = lines.get(1);

		HitLine left1 = lines.get(3);

		HitLine rightMedium = new HitLine(right1.getX2(), right1.getY2(), right1.getX2() + 95, right1.getY2());
		rightMedium.setBearing(right1.getBearing() + MediumBearing);
		HitLine leftMedium = new HitLine(left1.getX1(), left1.getY1(), left1.getX1() + 95, left1.getY1());
		leftMedium.setBearing(left1.getBearing() + Math.PI - MediumBearing);

		HitLine rightShort = new HitLine(right1.getX2(), right1.getY2(), right1.getX2() + 60, right1.getY2());
		rightShort.setBearing(right1.getBearing() + shortBearing);
		HitLine leftShort = new HitLine(left1.getX1(), left1.getY1(), left1.getX1() + 60, left1.getY1());
		leftShort.setBearing(left1.getBearing() + Math.PI - shortBearing);

		HitLine rightLong = new HitLine(right1.getX2(), right1.getY2(), right1.getX2() + 125, right1.getY2());
		rightLong.setBearing(right1.getBearing() + LongBearing);
		HitLine leftLong = new HitLine(left1.getX1(), left1.getY1(), left1.getX1() + 125, left1.getY1());
		leftLong.setBearing(left1.getBearing() + Math.PI - LongBearing);

		HitLine rightWing = new HitLine(right1.getX2(), right1.getY2(), right1.getX2() + 40, right1.getY2());
		rightWing.setBearing(right1.getBearing() + flankBearing);
		HitLine leftWing = new HitLine(left1.getX1(), left1.getY1(), left1.getX1() + 40, left1.getY1());
		leftWing.setBearing(left1.getBearing() + Math.PI - flankBearing);

		int h = track.get((int) rightShort.getX2(), (int) rightShort.getY2());
		int f = track.get((int) leftShort.getX2(), (int) leftShort.getY2());

		int g = track.get((int) rightMedium.getX2(), (int) rightMedium.getY2());
		int j = track.get((int) leftMedium.getX2(), (int) leftMedium.getY2());

		int u = track.get((int) rightLong.getX2(), (int) rightLong.getY2());
		int p = track.get((int) leftLong.getX2(), (int) leftLong.getY2());

		boolean rightS = false;
		boolean leftS = false;

		boolean rightM = false;
		boolean leftM = false;

		boolean rightL = false;
		boolean leftL = false;

		if (h == -14503604 || h == -15305678 || h == 0) {
			rightS = true;
		}

		if (f == -14503604 || f == -15305678 || f == 0) {
			leftS = true;
		}

		if (g == -14503604 || g == -15305678 || g == 0) {
			rightM = true;
		}

		if (j == -14503604 || j == -15305678 || j == 0) {
			leftM = true;
		}
		if (u == -14503604 || u == -15305678 || u == 0) {
			rightL = true;
		}
		if (p == -14503604 || p == -15305678 || p == 0) {
			leftL = true;
		}
		
		double brakeMedium = 1.3;
		double brakeHard = 2.0;
		
		double accelerateNormal = 0.4;
		double accelerateFast = 0.6;
		
		double turnSlow = 0.5;
		double turnNormal = 1.2;
		double turnHard = 1.9;
		
		double speedThresholdTiny = 0.4;
		double speedThresholdSmall = 0.5;
		double speedThresholdMedium = 1.0;
		double speedThresholdHigh = 1.5;
		double speedThresholdFast = 1.8;
		double speedThresholdFastest = 2.0;

		if (!leftM || !rightM && !leftS || !rightS) {

			if (leftM) {
				if (leftM && !leftS) {

					AiBot.turn(true);
					AiBot.slowDown(true);
					if (AiBot.getVelocity() < speedThresholdHigh)
						AiBot.accelerateForward(accelerateNormal);
					else if (AiBot.getVelocity() > speedThresholdFastest)
						AiBot.brake();

					lastRight = true;
				} else if (leftS && !rightS) {
					AiBot.turn(true, turnHard);
					AiBot.slowDown(true);

					if (AiBot.getVelocity() > speedThresholdSmall)
						AiBot.brake();
					else
						AiBot.accelerateForward(accelerateNormal);
					lastRight = true;
				}

			} 
			else if (rightM) {
				if (rightM && !rightS) {
					AiBot.turn(false);
					AiBot.slowDown(true);

					if (AiBot.getVelocity() < speedThresholdHigh)
						AiBot.accelerateForward(accelerateNormal);
					else if (AiBot.getVelocity() > speedThresholdFastest)
						AiBot.brake();

					lastRight = false;
				} else if (rightS && !leftS) {
					AiBot.turn(false, turnHard);
					AiBot.slowDown(true);
					if (AiBot.getVelocity() > speedThresholdSmall)
						AiBot.brake();
					else
						AiBot.accelerateForward(accelerateNormal);
					lastRight = false;
				}
			} 
			else {
				if (rightL && leftL) {

					AiBot.turn(lastRight, turnNormal);
					AiBot.slowDown(true);

					if (AiBot.getVelocity() > speedThresholdFast)
						AiBot.brake();
					else if (AiBot.getVelocity() > speedThresholdHigh) {

					} else {
						AiBot.accelerateForward(accelerateNormal);
					}

				} else {
					if (rightL && !leftL) {
						if (AiBot.getVelocity() > speedThresholdFast)
							AiBot.accelerateForward(0.7);
						else
							AiBot.accelerateForward(0.8);
						if (AiBot.getVelocity() > speedThresholdMedium) {
							AiBot.turn(false, turnSlow);
							lastRight = false;
						}
						AiBot.slowDown(true);
					} else if (leftL && !rightL) {
						if (AiBot.getVelocity() > speedThresholdFast)
							AiBot.accelerateForward(0.7);
						else
							AiBot.accelerateForward(0.8);
						if (AiBot.getVelocity() > speedThresholdMedium) {
							AiBot.turn(true, turnSlow);
							lastRight = true;
						}
						AiBot.slowDown(true);
					} else
						AiBot.accelerateForward(accelerateFast);
				}
			}

		} 
		else {

			if (!leftM && rightM) {

				if (AiBot.getVelocity() > speedThresholdSmall)
					AiBot.brake();
				else if (AiBot.getVelocity() < speedThresholdTiny)
					AiBot.accelerateForward(accelerateNormal);
				AiBot.turn(false);
				AiBot.slowDown(true);

			} 
			else if (!rightM && leftM) {
				if (AiBot.getVelocity() > speedThresholdSmall)
					AiBot.brake();
				else if (AiBot.getVelocity() < speedThresholdTiny)
					AiBot.accelerateForward(accelerateNormal);
				AiBot.turn(true);
				AiBot.slowDown(true);
			} 
			else if (!leftS && rightS) {
				if (AiBot.getVelocity() > speedThresholdSmall)
					AiBot.brake();
				else if (AiBot.getVelocity() < speedThresholdTiny)
					AiBot.accelerateForward(accelerateFast);
				AiBot.turn(false);
				AiBot.slowDown(true);
			} 
			else if (!rightS && leftS) {
				if (AiBot.getVelocity() > speedThresholdSmall)
					AiBot.brake();
				else if (AiBot.getVelocity() < speedThresholdTiny)
					AiBot.accelerateForward(accelerateFast);
				AiBot.turn(true);
				AiBot.slowDown(true);
			} 
			else if (rightM && leftM) {
				if (leftS && rightS) {
					
					if (AiBot.getVelocity() > speedThresholdSmall)
						AiBot.brake(brakeHard);
					else if (AiBot.getVelocity() < speedThresholdTiny)
						AiBot.accelerateForward(accelerateNormal);

					AiBot.turn(lastRight, turnNormal);
					AiBot.slowDown(true);

				} 
				else {
					if (AiBot.getVelocity() > speedThresholdSmall)
						AiBot.brake(brakeMedium);
					else if (AiBot.getVelocity() < speedThresholdTiny)
						AiBot.accelerateForward(accelerateNormal);

					AiBot.turn(lastRight, turnNormal);
					AiBot.slowDown(true);

				}
			} 
			else if (leftS && rightS) {
				if (AiBot.getVelocity() > speedThresholdSmall)
					AiBot.brake();
				else if (AiBot.getVelocity() < speedThresholdTiny)
					AiBot.accelerateForward(accelerateNormal);
				AiBot.turn(true);
			}
		}

		for (int k = 0; k < lines.size(); k++) {

			int a = track.get((int) lines.get(k).getX1(), (int) lines.get(k).getY1());

			if (a == -14503604) // grass
				modifier += 3.5;
			else if (a == -8421505) // dark road
				modifier += 1.5;
			else if (a == -15305678) // dark grass
				modifier += 5.5;
			else {

			}
		}
		AiBot.act(modifier);

		/*
		 * if (a == -14503604 || c == -14503604 || a == -15305678 || c == -15305678) {
		 * // grass AiBot.slowDown(true); AiBot.turn(false); //modifier += -3.5; } else
		 * if (d == -14503604 || d == -15305678) { AiBot.slowDown(true);
		 * AiBot.turn(true); //modifier += -3.5;
		 * 
		 * } else { AiBot.slowDown(true); AiBot.turn(true); //modifier += -3.5;
		 * 
		 * } // AiBot.getWeapon().act(objects, cars);
		 * 
		 * }
		 */

		int index = -2;
		for (int i = 0; i < cars.size(); i++) {
			if (cars.get(i).getHealth() > 0) {
				if (index != -2) {
					index = -1;
					i = cars.size();
				} else
					index = i;
			}
		}

		if (index == -2)
			DrawingSurface.loser();
		else if (index >= 0)
			DrawingSurface.winner(index);

	}

	public FinishLine getFinish() {
		return finish;
	}

	public void draw(PApplet drawer) {
		if (track != null)
			drawer.image(track, 0, 0);

		finish.draw(drawer);

		for (int i = 0; i < cars.size(); i++) {
			cars.get(i).draw(drawer);
		}

		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).draw(drawer);
		}

	}
}
