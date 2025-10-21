package com.magnolia.cione.dto;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.utils.CioneUtils;

public class FacturaDocDTO implements Comparable<FacturaDocDTO> {
	private String nombreDoc; 
	private String fechaDoc;
	private String enlaceDoc;
	
	public String getNombreDoc() {
		return nombreDoc;
	}
	public void setNombreDoc(String nombreDoc) {
		this.nombreDoc = nombreDoc;
	}
	public String getFechaDoc() {
		return fechaDoc;
	}
	public void setFechaDoc(String fechaDoc) {
		this.fechaDoc = fechaDoc;
	}
	public String getEnlaceDoc() {
		return enlaceDoc;
	}
	public void setEnlaceDoc(String enlaceDoc) {
		this.enlaceDoc = enlaceDoc;
	}
	
	public String getFechaDocView() {
		String result = null;
		if(!CioneUtils.isEmptyOrNull(fechaDoc)) {
			result = CioneUtils.changeDateFormat(fechaDoc, "yyyy-MM-dd", CioneConstants.OUPUT_DATE_FORMAT);
		}
		return result;
	}
	
	@Override
	public int compareTo(FacturaDocDTO f) {
		if (getFechaDoc() == null || f.getFechaDoc() == null) {
			return 0;
		}
		
		return getFechaDoc().compareTo(f.getFechaDoc());	
	}
}
