package com.magnolia.cione.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class EmployeeDTO {

	@NotEmpty
	@NotNull
	private String id;
	
	private String token;
	private String name;
	private String profile;
	private String psw;
	private String mail;
	private String address;
	private String rolprecio;
	private String rolpreciopantalla;
	
	public String getRolpreciopantalla() {
		return rolpreciopantalla;
	}

	public void setRolpreciopantalla(String rolpreciopantalla) {
		this.rolpreciopantalla = rolpreciopantalla;
	}

	private List<String> roles;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPsw() {
		return psw;
	}
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return id + ": " + name;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getRolprecio() {
		return rolprecio;
	}

	public void setRolprecio(String rolprecio) {
		this.rolprecio = rolprecio;
	}
}
