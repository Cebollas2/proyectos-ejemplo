package com.magnolia.cione.beans;

public class MarcaProveedor {
	
	private String familia;
	private String marca;
	private String proveedor;
	private String descProveedor;
	private int cantidad;
	
	public MarcaProveedor(String familia, String marca, String proveedor, String descProveedor, int cantidad) {
		super();
		this.familia = familia;
		this.marca = marca;
		this.proveedor = proveedor;
		this.descProveedor = descProveedor;
		this.cantidad = cantidad;
	}
	public String getFamilia() {
		return familia;
	}
	public void setFamilia(String familia) {
		this.familia = familia;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	public String getDescProveedor() {
		return descProveedor;
	}
	public void setDescProveedor(String descProveedor) {
		this.descProveedor = descProveedor;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
