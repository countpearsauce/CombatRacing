package drawing;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import processing.core.PApplet;

/**
 * 
 * @author Jeremy
 * @version 5/9/18 10:32PM
 *
 * Represents a line object used for collision detection with the track objects
 *
 */

public class HitLine {

	private Line2D.Double line;

	public HitLine(double x, double y, double x2, double y2) {
		line = new Line2D.Double(x, y, x2, y2);
	}

	public HitLine(HitLine other) {
		line = new Line2D.Double(other.getLine().x1, other.getLine().y1, other.getLine().x2, other.getLine().y2);
	}

	public double getX1() {
		return line.getX1();
	}

	public double getX2() {
		return line.getX2();
	}

	public double getY1() {
		return line.getY1();
	}

	public double getY2() {
		return line.getY2();
	}

	public Line2D.Double getLine() {
		return line;
	}

	public double getLength() {
		return Math.sqrt(Math.pow(line.getX2() - line.getX1(), 2) + Math.pow(line.getY2() - line.getY1(), 2));
	}

	public void draw(PApplet p) {
		p.line((int) line.getX1(), (int) line.getY1(), (int) line.getX2(), (int) line.getY2());

	}

	public Point2D.Double findIntersection(HitLine other) {
		Line2D.Double o = other.getLine();

		double slope1 = 0;
		double slope2 = 0;
		boolean hasslope1 = false;
		boolean hasslope2 = false;

		if (line.x2 != line.x1) {
			slope1 = (line.y2 - line.y1) / (line.x2 - line.x1);
			hasslope1 = true;
		}
		if (o.x2 != o.x1) {
			slope2 = (o.y2 - o.y1) / (o.x2 - o.x1);
			hasslope2 = true;
		}

		if (!hasslope1 && !hasslope2) {
			return null;
		}
		double xIntersect;
		double yIntersect;
		if (!hasslope1) {
			xIntersect = line.x1;
			yIntersect = slope2 * (xIntersect - o.x1) + o.y1;
		} else if (!hasslope2) {
			xIntersect = o.x1;
			yIntersect = slope1 * (xIntersect - line.x1) + line.y1;
		} else {

			xIntersect = (slope1 * line.x1 - slope2 * o.x1 + o.y1 - line.y1) / (slope1 - slope2);
			yIntersect = slope1 * (xIntersect - line.x1) + line.y1;
		}

		return new Point2D.Double(xIntersect, yIntersect);
	}

	public void moveBy(double xAmount, double yAmount) {
		line.setLine(line.getX1() + xAmount, line.getY1() + yAmount, line.getX2() + xAmount, line.getY2() + yAmount);
	}

	public double getBearing() {
		double a = 0;
		double dy = line.getY2() - line.getY1();
		double dx = line.getX2() - line.getX1();

		if (dy >= 0 && dx > 0) {
			a = Math.PI - Math.atan(dx / dy);
		} else if (dy < 0 && dx > 0) {
			a = Math.atan(dx / -dy);
		} else if (dy >= 0 && dx <= 0) {
			a = Math.PI - Math.atan(dx / dy);
		} else if (dy < 0 && dx <= 0) {
			a = 2 * Math.PI - Math.atan(dx / dy);
		}

		return a;
	}

	public void setBearing(double bearing) {
		double length = getLength();
		line.setLine(line.getX1(), line.getY1(), line.getX1() + length * Math.sin(bearing),
				line.getY1() - length * Math.cos(bearing));
	}

	public void setPoint2(double mouseX, double mouseY) {
		line.x2 = mouseX;
		line.y2 = mouseY;
	}

	public void setPoint1(Point2D.Double p) {
		line.x1 = p.getX();
		line.y1 = p.getY();
	}

	public void setPoint2(Point2D.Double p) {
		line.x2 = p.getX();
		line.y2 = p.getY();
	}

	public void setPoint1(double mouseX, double mouseY) {
		line.x1 = mouseX;
		line.y1 = mouseY;
	}
	

}
