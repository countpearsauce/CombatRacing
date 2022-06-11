package main;
import java.awt.Dimension;

import javax.swing.JFrame;

import drawing.DrawingSurface;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

/**
 * 
 * @author Vishnu
 * @version 5/13/12:39PM
 * 
 * Contains main method and constructs a DrawingSurface
 *
 */
public class Main {

	public static void main(String args[]) {
		DrawingSurface drawing = new DrawingSurface();
		PApplet.runSketch(new String[]{""}, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame)canvas.getFrame();

		
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);

		window.setVisible(true);
	}
	
}
