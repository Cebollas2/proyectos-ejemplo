package com.magnolia.cione.dto;

public class DireccionDTO {
	
	private String id_socio;
	private String id_localizacion; 
	private String nombre;
	private String direccion;
	private String cod_postal;
	private String localidad;
	private String provincia;
	private String comunidad_autonoma;
	private String pais;
	private double latitud;
	private double longitud;
	
	public String getId_localizacion() {
		return id_localizacion;
	}
	
	public void setId_localizacion(String id_localizacion) {
		this.id_localizacion = id_localizacion;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getCod_postal() {
		return cod_postal;
	}
	
	public void setCod_postal(String cod_postal) {
		this.cod_postal = cod_postal;
	}
	
	public String getLocalidad() {
		return localidad;
	}
	
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	
	public String getProvincia() {
		return provincia;
	}
	
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	public String getComunidad_autonoma() {
		return comunidad_autonoma;
	}
	
	public void setComunidad_autonoma(String comunidad_autonoma) {
		this.comunidad_autonoma = comunidad_autonoma;
	}
	
	public String getPais() {
		return pais;
	}
	
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	public double getLatitud() {
		return latitud;
	}
	
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	
	public double getLongitud() {
		return longitud;
	}
	
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public String getId_socio() {
		return id_socio;
	}

	public void setId_socio(String id_socio) {
		this.id_socio = id_socio;
	}
}
