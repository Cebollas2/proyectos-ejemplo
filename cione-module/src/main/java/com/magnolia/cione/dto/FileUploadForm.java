package com.magnolia.cione.dto;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class FileUploadForm {

	public FileUploadForm() {
	}

	private String tema;
	private String sugerencia;
	private byte[] data;
	@FormParam("nameFile")
	private String nameFile;
	@FormParam("to")
	private String to;
	@FormParam("checksum")
	private String checksum;
	@FormParam("from")
	private String from;
	
	@FormParam("myFile")
	private byte[] myFile;
	@FormParam("baseFile")
	private String baseFile;
	@FormParam("refTaller")
	private String refTaller;
	
	public String getTema() {
		return tema;
	}

	@FormParam("tema")
	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getSugerencia() {
		return sugerencia;
	}

	@FormParam("sugerencia")
	public void setSugerencia(String sugerencia) {
		this.sugerencia = sugerencia;
	}

	public byte[] getData() {
		return data;
	}

	@FormParam("file")
	@PartType("application/octet-stream")
	public void setData(byte[] data) {
		this.data = data;
	}

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	public byte[] getMyFile() {
		return myFile;
	}

	public void setMyFile(byte[] myFile) {
		this.myFile = myFile;
	}

	public String getBaseFile() {
		return baseFile;
	}

	public void setBaseFile(String baseFile) {
		this.baseFile = baseFile;
	}
	
	public String getRefTaller() {
		return refTaller;
	}

	public void setRefTaller(String refTaller) {
		this.refTaller = refTaller;
	}

}
