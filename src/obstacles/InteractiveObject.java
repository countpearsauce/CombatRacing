package obstacles;
import java.io.Serializable;
import java.util.ArrayList;

import drawing.DrawingSurface;
import drawing.HitLine;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * 
 * @author Jeremy Lei
 * @version 5/19/18 11:20PM
 *
 * Represents things on the track that interact with each other
 *
 */
public class InteractiveObject extends PApplet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient PImage img;
	private transient ArrayList<HitLine> hitLines;
	private String object;
	private int count;
	private double xCenter;
	private double yCenter;
	private double width;
	private double height;
	private boolean isPermanent;
	private double rotation;
	private boolean draw;
	private boolean isSolid;

	public InteractiveObject(String object, double xCenter, double yCenter, double width, double height,
			boolean permanent, boolean isSolid) {
		count = 1;
		this.object = object;
		initializeImage(DrawingSurface.getPImage(object));

		hitLines = new ArrayList<HitLine>();
		isPermanent = permanent;
		rotation = 0;
		this.width = width;
		this.height = height;
		this.xCenter = xCenter;
		this.yCenter = yCenter;
		draw = true;
		this.isSolid = isSolid;
		recalculateLines();

	}

	public InteractiveObject(String object, double xCenter, double yCenter, double width, double height,
			boolean permanent, boolean isSolid, int count) {
		this.count = count;
		this.object = object;

		this.width = width * count;
		this.height = height;

		initializeImage(DrawingSurface.getPImage(object));

		hitLines = new ArrayList<HitLine>();
		isPermanent = permanent;
		this.isSolid = isSolid;
		rotation = 0;
		draw = true;

		this.xCenter = xCenter;
		this.yCenter = yCenter;

		recalculateLines();

	}

	public void initializeImage(PImage temp) {
		PImage img = new PImage(temp.width * count, temp.height);

		for (int i = 0; i < count; i++) {
			img.set(0 + i * temp.width, 0, temp);
		}

		int a = img.get(0, 0);
		img.pixels[0] = 0x01000000;
		img.pixels[0] = a;

		this.img = img;
	}

	public void initialize() {
		initializeImage(DrawingSurface.getPImage(object));
		hitLines = new ArrayList<HitLine>();
		recalculateLines();
	}

	public double getWidth() {
		return width;
	}

	public void setDraw(boolean a) {
		draw = a;
	}

	public double getHeight() {
		return height;
	}

	public PImage getImage() {
		return img;
	}

	public ArrayList<HitLine> getLines() {
		return hitLines;
	}

	public double getXCenter() {
		return xCenter;
	}

	public double getYCenter() {
		return yCenter;
	}

	protected void setCenter(double x, double y) {
		adjustLines(x - xCenter, y - yCenter);
		xCenter = x;
		yCenter = y;

	}

	public void setXCenter(double x) {
		adjustLines(x - xCenter, 0);
		xCenter = x;
	}

	public void setYCenter(double y) {
		adjustLines(y - yCenter, 0);
		yCenter = y;
	}

	public void rotate(double amount) {
		rotation += amount;
		recalculateLines();
	}

	public void setAngle(double amount) {
		rotation = amount;
		recalculateLines();
	}

	public double getAngle() {
		return rotation;
	}

	private void recalculateLines() {
		hitLines.clear();

		HitLine drawer = new HitLine(xCenter, yCenter, xCenter + (width / 2), yCenter + height / 2);
		double original = drawer.getBearing();
		double bearing = original + rotation;
		drawer.setBearing(bearing);

		for (int i = 1; i <= 6; i++) {

			HitLine a = new HitLine(drawer.getX2(), drawer.getY2(), 0, 0);

			if (i % 3 == 1)
				drawer = new HitLine(xCenter, yCenter, xCenter + width / 2, yCenter);
			else
				drawer = new HitLine(xCenter, yCenter, xCenter + (width / 2), yCenter + height / 2);

			if (i % 3 == 1)
				bearing += (Math.PI / 2 - original);
			else if (i % 3 == 2) {
				bearing += (Math.PI / 2 - original);
			} else
				bearing = bearing + 2 * original;
			drawer.setBearing(bearing);
			a.setPoint2(drawer.getX2(), drawer.getY2());

			hitLines.add(a);

		}
	}

	public void adjustLines(double x, double y) {
		for (int i = 0; i < 6; i++) {
			hitLines.get(i).moveBy(x, y);
		}
	}

	public boolean isCloseTo(InteractiveObject e) {
		double a = Math.sqrt(Math.pow(getWidth(), 2) + Math.pow(getHeight(), 2));

		double b = Math.sqrt(Math.pow(e.getWidth(), 2) + Math.pow(e.getHeight(), 2));

		double x = Math.abs(getXCenter() - e.getXCenter());
		double y = Math.abs(getYCenter() - e.getYCenter());
		// if (x < (a + b + 30) / 2 || y < (a + b + 30) / 2)
		if (Math.sqrt(x * x + y * y) < (a + b + 50) / 2)
			return true;
		else
			return false;

	}

	public void effect(MoveableObject e) {

	}

	public boolean isSolid() {
		return isSolid;
	}

	public void draw(PApplet drawer) {

		drawer.pushMatrix();
		drawer.translate((float) xCenter, (float) yCenter);
		drawer.rotate((float) rotation);

		if (img != null && draw)
			drawer.image(img, -(float) width / 2, -(float) height / 2, (float) width, (float) height);

		drawer.popMatrix();

		/*
		 * drawer.stroke(0); for (int i = 0; i < hitLines.size(); i++) {
		 * 
		 * hitLines.get(i).draw(drawer); }
		 */

	}

	public boolean isPermanent() {
		return isPermanent;
	}

}
