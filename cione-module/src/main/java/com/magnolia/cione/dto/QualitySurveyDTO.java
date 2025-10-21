package com.magnolia.cione.dto;

public class QualitySurveyDTO {
	private String codSocio;
	private String socio;
	private String pregunta;
	private String puntuacion;
	private String comentario;
	private String fecha; 

	public String getCodSocio() {
		return codSocio;
	}

	public void setCodSocio(String codSocio) {
		this.codSocio = codSocio;
	}

	public String getSocio() {
		return socio;
	}

	public void setSocio(String socio) {
		this.socio = socio;
	}

	public String getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(String puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
