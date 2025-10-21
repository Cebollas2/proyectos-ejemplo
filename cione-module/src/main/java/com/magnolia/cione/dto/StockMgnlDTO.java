package com.magnolia.cione.dto;

public class StockMgnlDTO {
	
	private float stock;
	private float stockCANAR;
	private float stockCTRAL;
	private String almacen;
	
	public float getStockCTRAL() {
		return stockCTRAL;
	}
	public void setStockCTRAL(float stockCTRAL) {
		this.stockCTRAL = stockCTRAL;
	}
	public float getStock() {
		return stock;
	}
	public void setStock(float stock) {
		this.stock = stock;
	}
	public float getStockCANAR() {
		return stockCANAR;
	}
	public void setStockCANAR(float stockCANAR) {
		this.stockCANAR = stockCANAR;
	}
	public String getAlmacen() {
		return almacen;
	}
	public void setAlmacen(String almacen) {
		this.almacen = almacen;
	}

}
