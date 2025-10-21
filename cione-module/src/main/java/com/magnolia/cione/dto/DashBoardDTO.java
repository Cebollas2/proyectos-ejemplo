package com.magnolia.cione.dto;

import java.util.ArrayList;

public class DashBoardDTO {
	
    public ChartInfo consumptionActualLevelOwnProduct;
    public ChartInfo consumptionActualLevelSupplier;
    public ChartInfo consumptionNextLevelOwnProduct;
    public ChartInfo consumptionNextLevelSupplier;
    public String actualLevel;
    public String nextLevel;
    public String eurocioneDisponibles;
	public ChartInfo currentStatusDirect;
    public ChartInfo nextStateProduct;
    public ChartInfo nextStateDirect;
    public ChartInfo monthly;
    public ChartInfo annualConsumption;
    public ChartInfo lastTwelveMonthsConsumption;
    public ChartInfo consumptionByType;
    public ChartInfo mySavings;
    
    //chart donnuts
    public ArrayList<ChartInfo> consumptionCharts;
    
//    public EyeglassFrames eyeglassFrames;
//    public OphthalmicLens ophthalmicLens;
//    public ContactLenses contactLenses;
//    public Audiology audiology;
//    
	public ChartInfo getConsumptionActualLevelOwnProduct() {
		return consumptionActualLevelOwnProduct;
	}
	public void setConsumptionActualLevelOwnProduct(ChartInfo consumptionActualLevelOwnProduct) {
		this.consumptionActualLevelOwnProduct = consumptionActualLevelOwnProduct;
	}
	public ChartInfo getConsumptionActualLevelSupplier() {
		return consumptionActualLevelSupplier;
	}
	public void setConsumptionActualLevelSupplier(ChartInfo consumptionActualLevelSupplier) {
		this.consumptionActualLevelSupplier = consumptionActualLevelSupplier;
	}
	public ChartInfo getConsumptionNextLevelOwnProduct() {
		return consumptionNextLevelOwnProduct;
	}
	public void setConsumptionNextLevelOwnProduct(ChartInfo consumptionNextLevelOwnProduct) {
		this.consumptionNextLevelOwnProduct = consumptionNextLevelOwnProduct;
	}
	public ChartInfo getConsumptionNextLevelSupplier() {
		return consumptionNextLevelSupplier;
	}
	public void setConsumptionNextLevelSupplier(ChartInfo consumptionNextLevelSupplier) {
		this.consumptionNextLevelSupplier = consumptionNextLevelSupplier;
	}
	public String getActualLevel() {
		return actualLevel;
	}
	public void setActualLevel(String actualLevel) {
		this.actualLevel = actualLevel;
	}
	public String getNextLevel() {
		return nextLevel;
	}
	public void setNextLevel(String nextLevel) {
		this.nextLevel = nextLevel;
	}
	public String getEurocioneDisponibles() {
		return eurocioneDisponibles;
	}
	public void setEurocioneDisponibles(String eurocioneDisponibles) {
		this.eurocioneDisponibles = eurocioneDisponibles;
	}
	public ChartInfo getCurrentStatusDirect() {
		return currentStatusDirect;
	}
	public void setCurrentStatusDirect(ChartInfo currentStatusDirect) {
		this.currentStatusDirect = currentStatusDirect;
	}
	public ChartInfo getNextStateProduct() {
		return nextStateProduct;
	}
	public void setNextStateProduct(ChartInfo nextStateProduct) {
		this.nextStateProduct = nextStateProduct;
	}
	public ChartInfo getNextStateDirect() {
		return nextStateDirect;
	}
	public void setNextStateDirect(ChartInfo nextStateDirect) {
		this.nextStateDirect = nextStateDirect;
	}
	public ChartInfo getMonthly() {
		return monthly;
	}
	public void setMonthly(ChartInfo monthly) {
		this.monthly = monthly;
	}
	public ChartInfo getAnnualConsumption() {
		return annualConsumption;
	}
	public void setAnnualConsumption(ChartInfo annualConsumption) {
		this.annualConsumption = annualConsumption;
	}
	public ChartInfo getLastTwelveMonthsConsumption() {
		return lastTwelveMonthsConsumption;
	}
	public void setLastTwelveMonthsConsumption(ChartInfo lastTwelveMonthsConsumption) {
		this.lastTwelveMonthsConsumption = lastTwelveMonthsConsumption;
	}
	public ChartInfo getConsumptionByType() {
		return consumptionByType;
	}
	public void setConsumptionByType(ChartInfo consumptionByType) {
		this.consumptionByType = consumptionByType;
	}
	public ChartInfo getMySavings() {
		return mySavings;
	}
	public void setMySavings(ChartInfo mySavings) {
		this.mySavings = mySavings;
	}
	public ArrayList<ChartInfo> getConsumptionCharts() {
		return consumptionCharts;
	}
	public void setConsumptionCharts(ArrayList<ChartInfo> consumptionCharts) {
		this.consumptionCharts = consumptionCharts;
	}

}
