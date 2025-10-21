package com.magnolia.cione.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Socio {

	private String cliente; //codigo para consultas ERP
	private Date fecha;
	private boolean activo;
    private String email;
    private String emailMkt;
    private String grupoPrecio;
    private boolean connecta;
    private boolean rutaLuz;
    private boolean looktic;
    private boolean lookticPRO;
    private String nombreComercial;
    private String direccion;
    private String telefono;
    private String movil;
    private String nif;
    private String razonSocial;
    private String codigoPostal;
    private String poblacion;
    private String provincia;
    private String pais;
    private String personaContacto;
    private String nivelOM;
    private String almacenDefault;
    private String indigitall_pais;
    private String indigitall_provincia;
    private String indigitall_segmento;
    private String indigitall_fidelizacion;
    private String indigitall_grupo_socios;
    private String indigitall_nivel_om;
    
	// para uso interno
	private String password;
	private String numSocio; //cliente+00 en caso de socio 01, 02 para empleados
    
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getNumSocio() {
		return numSocio;
	}
	public void setNumSocio(String numSocio) {
		this.numSocio = numSocio;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmailMkt() {
		return emailMkt;
	}
	public void setEmailMkt(String emailMkt) {
		this.emailMkt = emailMkt;
	}
	public String getGrupoPrecio() {
		return grupoPrecio;
	}
	public void setGrupoPrecio(String grupoPrecio) {
		this.grupoPrecio = grupoPrecio;
	}
	public boolean isConnecta() {
		return connecta;
	}
	public void setConnecta(boolean connecta) {
		this.connecta = connecta;
	}
	public boolean isRutaLuz() {
		return rutaLuz;
	}
	public void setRutaLuz(boolean rutaLuz) {
		this.rutaLuz = rutaLuz;
	}
	public boolean isLooktic() {
		return looktic;
	}
	public void setLooktic(boolean looktic) {
		this.looktic = looktic;
	}
	public boolean isLookticPRO() {
		return lookticPRO;
	}
	public void setLookticPRO(boolean lookticPRO) {
		this.lookticPRO = lookticPRO;
	}
	public String getNombreComercial() {
		return nombreComercial;
	}
	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getMovil() {
		return movil;
	}
	public void setMovil(String movil) {
		this.movil = movil;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getPoblacion() {
		return poblacion;
	}
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getPersonaContacto() {
		return personaContacto;
	}
	public void setPersonaContacto(String personaContacto) {
		this.personaContacto = personaContacto;
	}
	public String getNivelOM() {
		if (nivelOM != null)
			return nivelOM.toUpperCase();
		else
			return null;
	}
	public void setNivelOM(String nivelOM) {
		this.nivelOM = nivelOM;
	}
	public String getAlmacenDefault() {
		return almacenDefault;
	}
	public void setAlmacenDefault(String almacenDefault) {
		this.almacenDefault = almacenDefault;
	}
	public String getIndigitall_pais() {
		return indigitall_pais;
	}
	public void setIndigitall_pais(String indigitall_pais) {
		this.indigitall_pais = indigitall_pais;
	}
	public String getIndigitall_provincia() {
		return indigitall_provincia;
	}
	public void setIndigitall_provincia(String indigitall_provincia) {
		this.indigitall_provincia = indigitall_provincia;
	}
	public String getIndigitall_segmento() {
		return indigitall_segmento;
	}
	public void setIndigitall_segmento(String indigitall_segmento) {
		this.indigitall_segmento = indigitall_segmento;
	}
	public String getIndigitall_fidelizacion() {
		return indigitall_fidelizacion;
	}
	public void setIndigitall_fidelizacion(String indigitall_fidelizacion) {
		this.indigitall_fidelizacion = indigitall_fidelizacion;
	}
	public String getIndigitall_grupo_socios() {
		return indigitall_grupo_socios;
	}
	public void setIndigitall_grupo_socios(String indigitall_grupo_socios) {
		this.indigitall_grupo_socios = indigitall_grupo_socios;
	}
	public String getIndigitall_nivel_om() {
		return indigitall_nivel_om;
	}
	public void setIndigitall_nivel_om(String indigitall_nivel_om) {
		this.indigitall_nivel_om = indigitall_nivel_om;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
