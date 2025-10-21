package com.magnolia.cione.dto;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserERPCioneDTO implements Serializable {
	
	public UserERPCioneDTO() {
		super();
		this.socio = new Socio();
	}

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(UserERPCioneDTO.class);
	private Socio socio;
	
	

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}
	
	public String getCliente() {
		return socio.getCliente();
	}
	
	public void setCliente(String cliente) {
		socio.setCliente(cliente);
	}
	
	public String getAlmacenDefault() {
		return socio.getAlmacenDefault();
	}

	public String getNivelOM() {
		if (socio.getNivelOM() != null)
			return socio.getNivelOM().toUpperCase();
		else
			return null;
	}
	
	public void setNivelOM(String nivelOM) {
		socio.setNivelOM(nivelOM);
	}

	public String getNumSocio() {
		return socio.getNumSocio();
	}
	
	public void setNumSocio(String numSocio) {
		socio.setNumSocio(numSocio);
	}


	public Date getFecha() {
		return socio.getFecha();
	}

	public Boolean getActivo() {
		return socio.isActivo();
	}

	public String getEmail() {
		return socio.getEmail();
	}
	
	public void setEmail(String email) {
		socio.setEmail(email);
	}

	public String getGrupoPrecio() {
		return socio.getGrupoPrecio();
	}

	public Boolean getConnecta() {
		return socio.isConnecta();
	}

	public Boolean getRutaLuz() {
		return socio.isRutaLuz();
	}

	public Boolean getLooktic() {
		return socio.isLooktic();
	}

	public Boolean getLookticPRO() {
		return socio.isLookticPRO();
	}

	public String getPassword() {
		return socio.getPassword();
	}
	
	public void setPassword(String password) {
		socio.setPassword(password);
	}

	public String getNombreComercial() {
		return socio.getNombreComercial();
	}
	
	public void setNombreComercial(String nombreComercial) {
		socio.setNombreComercial(nombreComercial);
	}

	public String getDireccion() {
		return socio.getDireccion();
	}

	public String getTelefono() {
		return socio.getTelefono();
	}

	public String getMovil() {
		return socio.getMovil();
	}

	public String getNif() {
		return socio.getNif();
	}

	public String getRazonSocial() {
		return socio.getRazonSocial();
	}

	public String getCodigoPostal() {
		return socio.getCodigoPostal();
	}

	public String getPoblacion() {
		return socio.getPoblacion();
	}

	public String getProvincia() {
		return socio.getProvincia();
	}

	public String getPais() {
		return socio.getPais();
	}

	public String getPersonaContacto() {
		return socio.getPersonaContacto();
	}
	
	public void setPersonaContacto(String personaContacto) {
		socio.setPersonaContacto(personaContacto);
	}
	
	public String getIndigitall_pais() {
		return socio.getIndigitall_pais();
	}
	public String getIndigitall_provincia() {
		return socio.getIndigitall_provincia();
	}
	public String getIndigitall_segmento() {
		return socio.getIndigitall_segmento();
	}
	public String getIndigitall_fidelizacion() {
		return socio.getIndigitall_fidelizacion();
	}
	public String getIndigitall_grupo_socios() {
		return socio.getIndigitall_grupo_socios();
	}
	public String getIndigitall_nivel_om() {
		return socio.getIndigitall_nivel_om();
	}

}
