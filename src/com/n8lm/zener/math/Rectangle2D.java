package com.n8lm.zener.math;

public class Rectangle2D {
	
	public float x0;
	public float x1;
	public float y0;
	public float y1;
	
	public Rectangle2D(int x, int y, int w, int h) {
		x0 = x;
		y0 = y;
		x1 = x + w;
		y1 = y + h;
	}
}
