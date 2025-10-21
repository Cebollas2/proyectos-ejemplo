package com.magnolia.cione.dto;

public class RepuestoPostDTO {
	private boolean varillaDerecha;
	private boolean varillaIzquierda;
	private boolean varillaFrente;
	private String marca;
	private String modelo;
	private String color;
	private String calibre;
	private String comentarios;
	private String destinatarios;
	private String usuario;
	
	public boolean isVarillaDerecha() {
		return varillaDerecha;
	}
	public void setVarillaDerecha(boolean varillaDerecha) {
		this.varillaDerecha = varillaDerecha;
	}
	public boolean isVarillaIzquierda() {
		return varillaIzquierda;
	}
	public void setVarillaIzquierda(boolean varillaIzquierda) {
		this.varillaIzquierda = varillaIzquierda;
	}
	public boolean isVarillaFrente() {
		return varillaFrente;
	}
	public void setVarillaFrente(boolean varillaFrente) {
		this.varillaFrente = varillaFrente;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public String getDestinatarios() {
		return destinatarios;
	}
	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getCalibre() {
		return calibre;
	}
	public void setCalibre(String calibre) {
		this.calibre = calibre;
	}

}
