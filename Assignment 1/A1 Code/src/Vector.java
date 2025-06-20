/**
 * COMPSCI 1027B Assignment 1
 * Vector.java
 * Due: February 8, 2023
 * 
 * @author Matthew Molloy
 *
 */
public class Vector extends Matrix{
	public Vector(int c) {
		super(1, c);
	}
	
	public Vector(int c, double[] linArr) {
		super(1, c);
		for (int j = 0; j < c; j++) {
			super.setElement(0, j, linArr[j]);
		}
	}
	
	public double getElement(int c) {
		//System.out.println(super.getElement(0, c));
		return super.getElement(0, c);
	}
	
}
