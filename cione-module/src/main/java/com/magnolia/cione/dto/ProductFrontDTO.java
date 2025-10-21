package com.magnolia.cione.dto;

import java.util.ArrayList;
import java.util.List;

import com.magnolia.cione.beans.Dimensions;
import com.magnolia.cione.beans.Image;

public class ProductFrontDTO {
	
	private Double pvp;
	private Double pvo;
	private Double discount;
	private Boolean promo;
	private Boolean recargo;
	private int stock;
	private String calibration;
	private String calibre;
	private String color;
	private String codigocentral;
	private String codigocolor;
	private String colorMontura;
	private String sku;
	private String name;
	private String nombreArticulo;
	private String coleccion;
	private String tamanio;
	private String tipoproducto;
	private String familiaproducto;
	private List<Image> images;
	private String aliasEKON;
	private String statusEkon;
	private boolean ofertaFlash;
	private int amoutdiscount;
	private int delivery;
	private String size;
	private List<String> colorsAudio;
	private boolean repuesto;
	private boolean aMedida;
	private boolean hasSubstitute;
	private Double pvoSinPack;
	private boolean packNavegacion; //deprecated
	private boolean packNavegacionDetalle; //flag para saber si estamos en un listado de packs genericos y tenemos que saltar a la página de detalle de pack
	private boolean vartiantPack;
	private List<String> filtros;
	private String descripcion;
	private Double pvoConDescuentoPack;
	
	private String tipoPrecioPack;
	private String pvoPack;
	private String descripcionPack;
	
	private boolean incluidoEnPack;
	private boolean excludeMasterProductFront;
	
	private boolean gestionStock;
	
	private String idCarritoOculto;

	public ProductFrontDTO(){}
	
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getTamanio() {
		return tamanio;
	}

	public void setTamanio(String tamanio) {
		this.tamanio = tamanio;
	}
	
	public String getTipoproducto() {
		return tipoproducto;
	}

	public void setTipoproducto(String tipoproducto) {
		this.tipoproducto = tipoproducto;
	}
	public String getFamiliaproducto() {
		return familiaproducto;
	}

	public void setFamiliaproducto(String familiaproducto) {
		this.familiaproducto = familiaproducto;
	}
	
	public String getCodigocentral() {
		return codigocentral;
	}

	public void setCodigocentral(String codigocentral) {
		this.codigocentral = codigocentral;
	}

	public String getCodigocolor() {
		return codigocolor;
	}

	public void setCodigocolor(String codigocolor) {
		this.codigocolor = codigocolor;
	}

	public String getColorMontura() {
		return colorMontura;
	}

	public void setColorMontura(String colorMontura) {
		this.colorMontura = colorMontura;
	}

	public int getDelivery() {
		return delivery;
	}

	public void setDelivery(int delivery) {
		this.delivery = delivery;
	}

	public int getAmoutdiscount() {
		return amoutdiscount;
	}

	public void setAmoutdiscount(int amoutdiscount) {
		this.amoutdiscount = amoutdiscount;
	}

	public String getAliasEKON() {
		return aliasEKON;
	}

	public void setAliasEKON(String aliasEKON) {
		this.aliasEKON = aliasEKON;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getNombreArticulo() {
		return nombreArticulo;
	}

	public void setNombreArticulo(String nombreArticulo) {
		this.nombreArticulo = nombreArticulo;
	}

	public String getColeccion() {
		return coleccion;
	}

	public void setColeccion(String coleccion) {
		this.coleccion = coleccion;
	}
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
	public Double getPvp() {
		return pvp;
	}
	public void setPvp(Double pvp) {
		this.pvp = pvp;
	}
	public Double getPvo() {
		return pvo;
	}
	public void setPvo(Double pvo) {
		this.pvo = pvo;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Boolean getPromo() {
		return promo;
	}
	public void setPromo(Boolean promo) {
		this.promo = promo;
	}
	public Boolean getRecargo() {
		return recargo;
	}
	public void setRecargo(Boolean recargo) {
		this.recargo = recargo;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getCalibration() {
		return calibration;
	}
	public void setCalibration(String calibration) {
		this.calibration = calibration;
	}
	public String getCalibre() {
		return calibre;
	}
	public void setCalibre(String calibre) {
		this.calibre = calibre;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public List<String> getColorsAudio() {
		return colorsAudio;
	}

	public void setColorsAudio(List<String> colorsAudio) {
		this.colorsAudio = colorsAudio;
	}
	
	public boolean isRepuesto() {
		return repuesto;
	}

	public void setRepuesto(boolean repuesto) {
		this.repuesto = repuesto;
	}
	
	public boolean isaMedida() {
		return aMedida;
	}

	public void setaMedida(boolean aMedida) {
		this.aMedida = aMedida;
	}
	
	public boolean isHasSubstitute() {
		return hasSubstitute;
	}

	public void setHasSubstitute(boolean hasSubstitute) {
		this.hasSubstitute = hasSubstitute;
	}
	
	public Double getPvoSinPack() {
		return pvoSinPack;
	}

	public void setPvoSinPack(Double pvoSinPack) {
		this.pvoSinPack = pvoSinPack;

	}
	
	public boolean isPackNavegacion() {
		return packNavegacion;
	}

	public void setPackNavegacion(boolean packNavegacion) {
		this.packNavegacion = packNavegacion;
	}
	
	
	public boolean isPackNavegacionDetalle() {
		return packNavegacionDetalle;
	}

	public void setPackNavegacionDetalle(boolean packNavegacionDetalle) {
		this.packNavegacionDetalle = packNavegacionDetalle;
	}
	
	public boolean isVartiantPack() {
		return vartiantPack;
	}

	public void setVartiantPack(boolean vartiantPack) {
		this.vartiantPack = vartiantPack;
	}

	public List<String> getFiltros() {
		return filtros;
	}

	public void setFiltros(List<String> filtros) {
		this.filtros = filtros;
	}
	
	/*
	 * Establece List<Image> images a partir del tipo io.sphere.sdk.products.Image 
	 */
	public void setImages2(List<com.commercetools.api.models.common.Image> images2) {
		List<Image> auxImages = new ArrayList<>();
		
		for(com.commercetools.api.models.common.Image im2: images2) {
			Image im = new Image();
			
			im.setUrl(im2.getUrl());
			
			im.setDimensions(new Dimensions(im2.getDimensions().getW(), im2.getDimensions().getH()));
			
			auxImages.add(im);
		}
		
		this.setImages(auxImages);
	}

	public String getStatusEkon() {
		return statusEkon;
	}

	public void setStatusEkon(String statusEkon) {
		this.statusEkon = statusEkon;
	}

	public boolean getOfertaFlash() {
		return ofertaFlash;
	}

	public void setOfertaFlash(boolean ofertaFlash) {
		this.ofertaFlash = ofertaFlash;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Double getPvoConDescuentoPack() {
		return pvoConDescuentoPack;
	}

	public void setPvoConDescuentoPack(Double pvoConDescuentoPack) {
		this.pvoConDescuentoPack = pvoConDescuentoPack;
	}
	
	public String getTipoPrecioPack() {
		return tipoPrecioPack;
	}

	public void setTipoPrecioPack(String tipoPrecioPack) {
		this.tipoPrecioPack = tipoPrecioPack;
	}

	public String getPvoPack() {
		return pvoPack;
	}

	public void setPvoPack(String pvoPack) {
		this.pvoPack = pvoPack;
	}

	public String getDescripcionPack() {
		return descripcionPack;
	}

	public void setDescripcionPack(String descripcionPack) {
		this.descripcionPack = descripcionPack;
	}
	
	public boolean isIncluidoEnPack() {
		return incluidoEnPack;
	}

	public void setIncluidoEnPack(boolean incluidoEnPack) {
		this.incluidoEnPack = incluidoEnPack;
	}
	
	public boolean isExcludeMasterProductFront() {
		return excludeMasterProductFront;
	}

	public void setExcludeMasterProductFront(boolean excludeMasterProductFront) {
		this.excludeMasterProductFront = excludeMasterProductFront;
	}
	
	public boolean isGestionStock() {
		return gestionStock;
	}

	public void setGestionStock(boolean gestionStock) {
		this.gestionStock = gestionStock;
	}

	public String getIdCarritoOculto() {
		return idCarritoOculto;
	}

	public void setIdCarritoOculto(String idCarritoOculto) {
		this.idCarritoOculto = idCarritoOculto;
	}
	
}
