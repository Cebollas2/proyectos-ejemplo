package com.magnolia.cione.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ColorDTO implements Cloneable, Serializable{
	
	private static final long serialVersionUID = 1L;
	private String idColor; //concatenacion de codigoColor+colorIcono
	private String colorIcono;
	private String codigoColor;
	private String colorMontura;
	private String sku;
	private boolean selected;
	private String calibre;
	private String graduacion;
	private String tamanio;
	private List<ColorDTO> calibres;
	private List<ColorDTO> graduaciones;
	
	@Override
    public ColorDTO clone() {
        try {
        	ColorDTO cloned = (ColorDTO) super.clone();
        	if (this.calibres != null) {
                List<ColorDTO> clonedCalibres = new ArrayList<>();
                for (ColorDTO calibre : this.calibres) {
                    clonedCalibres.add(calibre.clone()); 
                }
                cloned.setCalibres(clonedCalibres);
        	}
        	if (this.graduaciones != null) {
                List<ColorDTO> clonedGraduaciones = new ArrayList<>();
                for (ColorDTO graduacion : this.graduaciones) {
                	clonedGraduaciones.add(graduacion.clone());
                }
                cloned.setGraduaciones(clonedGraduaciones);
        	}
        	return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); 
        }
    }
	
	public String getIdColor() {
		return idColor;
	}
	public void setIdColor(String idColor) {
		this.idColor = idColor;
	}
	public String getColorIcono() {
		return colorIcono;
	}
	public void setColorIcono(String colorIcono) {
		this.colorIcono = colorIcono;
	}
	public String getCodigoColor() {
		return codigoColor;
	}
	public void setCodigoColor(String codigoColor) {
		this.codigoColor = codigoColor;
	}
	public String getColorMontura() {
		return colorMontura;
	}
	public void setColorMontura(String colorMontura) {
		this.colorMontura = colorMontura;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getCalibre() {
		return calibre;
	}
	public void setCalibre(String calibre) {
		this.calibre = calibre;
	}
	public String getGraduacion() {
		return graduacion;
	}
	public void setGraduacion(String graduacion) {
		this.graduacion = graduacion;
	}
	public String getTamanio() {
		return tamanio;
	}
	public void setTamanio(String tamanio) {
		this.tamanio = tamanio;
	}
	public List<ColorDTO> getCalibres() {
		return calibres;
	}
	public void setCalibres(List<ColorDTO> calibres) {
		this.calibres = calibres;
	}
	public List<ColorDTO> getGraduaciones() {
		return graduaciones;
	}
	public void setGraduaciones(List<ColorDTO> graduaciones) {
		this.graduaciones = graduaciones;
	}

}
