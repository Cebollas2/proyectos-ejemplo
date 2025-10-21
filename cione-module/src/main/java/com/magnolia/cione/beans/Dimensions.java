package com.magnolia.cione.beans;

public class Dimensions{
	
	private int w;
	private int h;
    
	public Dimensions(Integer width, Integer height) {
		this.w = width;
		this.h = height;
	}

	public int getW() {
		return w;
	}
	
	public void setW(int w) {
		this.w = w;
	}
	
	public int getH() {
		return h;
	}
	
	public void setH(int h) {
		this.h = h;
	}
}
