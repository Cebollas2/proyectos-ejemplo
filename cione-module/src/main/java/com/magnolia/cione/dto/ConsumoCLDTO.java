package com.magnolia.cione.dto;

public class ConsumoCLDTO {
	
	private String codSocio;
	private String level1_desc;
	private String level2_desc;
	private String level3_desc;
	private String cod_central_desc;
	private float cantidad;
	private float importe;
	
	public String getCodSocio() {
		return codSocio;
	}
	public void setCodSocio(String codSocio) {
		this.codSocio = codSocio;
	}
	public String getLevel1_desc() {
		return level1_desc;
	}
	public void setLevel1_desc(String level1_desc) {
		this.level1_desc = level1_desc;
	}
	public String getLevel2_desc() {
		return level2_desc;
	}
	public void setLevel2_desc(String level2_desc) {
		this.level2_desc = level2_desc;
	}
	public String getLevel3_desc() {
		return level3_desc;
	}
	public void setLevel3_desc(String level3_desc) {
		this.level3_desc = level3_desc;
	}
	public String getCod_central_desc() {
		return cod_central_desc;
	}
	public void setCod_central_desc(String cod_central_desc) {
		this.cod_central_desc = cod_central_desc;
	}
	public float getCantidad() {
		return cantidad;
	}
	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}
	public float getImporte() {
		return importe;
	}
	public void setImporte(float importe) {
		this.importe = importe;
	}
	
}
