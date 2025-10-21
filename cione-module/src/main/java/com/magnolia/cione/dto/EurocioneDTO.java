package com.magnolia.cione.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.magnolia.cione.utils.CioneUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EurocioneDTO {

	//son los disponibles
	/*private String totalAcumulado;
	private String canjeado;
	private String categoria;
	private String acumulado;*/
	
	private String categoria;
	private String ec_siguientepromocion;
	private String ec_generados;
	private String ec_canjeados;
	private String ec_netos;
	private String ec_anteriores;
	private String ec_disponibles;
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getEc_siguientepromocion() {
		return ec_siguientepromocion;
	}
	public void setEc_siguientepromocion(String ec_siguientepromocion) {
		this.ec_siguientepromocion = ec_siguientepromocion;
	}
	public String getEc_generados() {
		return CioneUtils.decimalToView(ec_generados);
	}
	public void setEc_generados(String ec_generados) {
		this.ec_generados = ec_generados;
	}
	public String getEc_canjeados() {
		return CioneUtils.decimalToView(ec_canjeados);
	}
	public void setEc_canjeados(String ec_canjeados) {
		this.ec_canjeados = ec_canjeados;
	}
	public String getEc_netos() {
		return CioneUtils.decimalToView(ec_netos);
	}
	public void setEc_netos(String ec_netos) {
		this.ec_netos = ec_netos;
	}
	public String getEc_anteriores() {
		return CioneUtils.decimalToView(ec_anteriores);
	}
	public void setEc_anteriores(String ec_anteriores) {
		this.ec_anteriores = ec_anteriores;
	}
	public String getEc_disponibles() {
		return CioneUtils.decimalToView(ec_disponibles);
	}
	public void setEc_disponibles(String ec_disponibles) {
		this.ec_disponibles = ec_disponibles;
	}
	
	

	/*public String getTotalAcumulado() {
		return CioneUtils.decimalToView(totalAcumulado);
	}
	
	public String getTotalesAcumuladosOK() {
		String result = "";
		try {
			result = (Double.parseDouble(totalAcumulado) + Double.parseDouble(canjeado)) + "";
			result = CioneUtils.decimalToView(result);
		}catch(Exception e) {
			result = "";
		}
		return result;	
	}

	public void setTotalAcumulado(String totalAcumulado) {
		this.totalAcumulado = totalAcumulado;
	}

	public String getCanjeado() {
		return CioneUtils.decimalToView(canjeado);
	}

	public void setCanjeado(String canjeado) {
		this.canjeado = canjeado;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getAcumulado() {
		return CioneUtils.decimalToView(acumulado);
	}

	public void setAcumulado(String acumulado) {
		this.acumulado = acumulado;
	}*/

}
