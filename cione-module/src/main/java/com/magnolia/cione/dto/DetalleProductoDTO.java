package com.magnolia.cione.dto;

import java.util.List;
import java.util.Map;

import com.commercetools.api.models.common.Image;
import com.commercetools.api.models.product.ProductProjection;

public class DetalleProductoDTO {
	private String logoMarca;
	private ProductProjection productProjection;
	private Image masterImage;
	private List<Image> listImages;	
	private String name;
	private String pvo;
	private String pvp;
	private String sku;
	private boolean hasPromocion;
	private List<VariantDTO> variantsList;
	private List<String> colors;
	private List<String> calibre;
	private List<String> graduacion;
	private Map <String, List<String>> color_calibre;
	
	public String getLogoMarca() {
		return logoMarca;
	}
	public void setLogoMarca(String logoMarca) {
		this.logoMarca = logoMarca;
	}
	public ProductProjection getProductProjection() {
		return productProjection;
	}
	public void setProductProjection(ProductProjection productProjection) {
		this.productProjection = productProjection;
	}
	public Image getMasterImage() {
		return masterImage;
	}
	public void setMasterImage(Image masterImage) {
		this.masterImage = masterImage;
	}
	public List<Image> getListImages() {
		return listImages;
	}
	public void setListImages(List<Image> listImages) {
		this.listImages = listImages;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getPvo() {
		return pvo;
	}
	public void setPvo(String pvo) {
		this.pvo = pvo;
	}
	public String getPvp() {
		return pvp;
	}
	public void setPvp(String pvp) {
		this.pvp = pvp;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public boolean isHasPromocion() {
		return hasPromocion;
	}
	public void setHasPromocion(boolean hasPromocion) {
		this.hasPromocion = hasPromocion;
	}
	public List<VariantDTO> getVariantsList() {
		return variantsList;
	}
	public void setVariantsList(List<VariantDTO> variantsList) {
		this.variantsList = variantsList;
	}
	public List<String> getColors() {
		return colors;
	}
	public void setColors(List<String> colors) {
		this.colors = colors;
	}
	public List<String> getCalibre() {
		return calibre;
	}
	public void setCalibre(List<String> calibre) {
		this.calibre = calibre;
	}
	public List<String> getGraduacion() {
		return graduacion;
	}
	public void setGraduacion(List<String> graduacion) {
		this.graduacion = graduacion;
	}
	public Map<String, List<String>> getColor_calibre() {
		return color_calibre;
	}
	public void setColor_calibre(Map<String, List<String>> color_calibre) {
		this.color_calibre = color_calibre;
	}
}
