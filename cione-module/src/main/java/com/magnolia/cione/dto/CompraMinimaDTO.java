package com.magnolia.cione.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompraMinimaDTO {
	public int resultado; //1=ok;  0=ko
	public String mensaje;
	public boolean boolCompraMinima;
    public String tipoRestriccion;
    public int udCompraMinima;
    
	public int getResultado() {
		return resultado;
	}
	public void setResultado(int resultado) {
		this.resultado = resultado;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public boolean isBoolCompraMinima() {
		return boolCompraMinima;
	}
	public void setBoolCompraMinima(boolean boolCompraMinima) {
		this.boolCompraMinima = boolCompraMinima;
	}
	public String getTipoRestriccion() {
		return tipoRestriccion;
	}
	public void setTipoRestriccion(String tipoRestriccion) {
		this.tipoRestriccion = tipoRestriccion;
	}
	public int getUdCompraMinima() {
		return udCompraMinima;
	}
	public void setUdCompraMinima(int udCompraMinima) {
		this.udCompraMinima = udCompraMinima;
	}
    


}
