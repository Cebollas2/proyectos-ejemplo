package com.magnolia.cione.dto;

public class MisConsumosDashBoardDTO {
	
	private int level;
	private String description;
	private String anio;
	private int mes;
	private String mes_str;
	private Double consumo_mes;
	private String consumo_mes_str;
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public String getMes_str() {
		return mes_str;
	}
	public void setMes_str(String mes_str) {
		this.mes_str = mes_str;
	}
	public Double getConsumo_mes() {
		return consumo_mes;
	}
	public void setConsumo_mes(Double consumo_mes) {
		this.consumo_mes = consumo_mes;
	}
	public String getConsumo_mes_str() {
		return consumo_mes_str;
	}
	public void setConsumo_mes_str(String consumo_mes_str) {
		this.consumo_mes_str = consumo_mes_str;
	}
	@Override
	public String toString() {
		return "MisConsumosDashBoardDTO [level=" + level + ", description=" + description + ", anio=" + anio + ", + mes= "+ mes +" mes_str=" + mes_str + " consumo_mes=" + consumo_mes + " consumo_mes_str=" + consumo_mes_str + "]";
	}
}
