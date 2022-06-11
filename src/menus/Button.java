package menus;
import java.awt.geom.Rectangle2D;

import java.awt.Color;

import processing.core.PApplet;
/**
 * 
 * @author Jeremy
 * @version 5/19/18 11:23PM
 * 
 * Represents graphical buttons in the menus that can be clicked
 *
 */
public class Button extends PApplet {

	private Color borderColor, textColor, fillColor;
	
	private Rectangle2D.Double shape;
	private String text;

	public Button(double xCenter, double yCenter, double width, double height, String text) {
		shape = new Rectangle2D.Double(xCenter - width / 2, yCenter - height / 2, width, height);
		this.text = text;
		
		borderColor = Color.BLACK;
		textColor = Color.BLACK;
		fillColor = null;
	}
	
	public void setBorderColor(Color c) {
		borderColor = c;
	}
	
	public void setTextColor(Color c) {
		textColor = c;
	}
	
	public void setFillColor(Color c) {
		fillColor = c;
	}
	
	public boolean click(double x, double y) {
		return shape.contains(x, y);
	}
	
	public Rectangle2D.Double getShape() {
		return shape;
	}
	

	public void draw(PApplet drawer) {
		if(fillColor == null)
		drawer.noFill();
		else
			drawer.fill(fillColor.getRGB());
		drawer.strokeWeight(2);
		drawer.stroke(borderColor.getRed(), borderColor.getGreen(), borderColor.getBlue(), borderColor.getAlpha());
		//drawer.stroke(255);
		
		
		drawer.rect((float) (shape.x), (float) (shape.y), (float) shape.width, (float) shape.height);
		drawer.fill(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), textColor.getAlpha());
		drawer.textAlign(CENTER, CENTER);
		drawer.textSize(20);
		drawer.text(text, (float) shape.getCenterX(), (float) shape.getCenterY()-2);
		drawer.strokeWeight(1);
	}

}
