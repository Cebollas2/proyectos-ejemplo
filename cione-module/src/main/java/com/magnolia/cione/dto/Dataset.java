package com.magnolia.cione.dto;

import java.util.ArrayList;

public class Dataset {
    public ArrayList<Double> data;
    public ArrayList<String> backgroundColor;
    public ArrayList<String> hoverBackgroundColor;
    public String label;
    public String borderColor;
    public int borderWidth;
	public ArrayList<Double> getData() {
		return data;
	}
	public void setData(ArrayList<Double> data) {
		this.data = data;
	}
	public ArrayList<String> getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(ArrayList<String> backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public ArrayList<String> getHoverBackgroundColor() {
		return hoverBackgroundColor;
	}
	public void setHoverBackgroundColor(ArrayList<String> hoverBackgroundColor) {
		this.hoverBackgroundColor = hoverBackgroundColor;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	public int getBorderWidth() {
		return borderWidth;
	}
	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}
}
