package com.magnolia.cione.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.magnolia.cione.constants.MyshopConstants;

import info.magnolia.context.MgnlContext;

public class MasterProductFrontDTO {
	
	private ProductFrontDTO master;
	private List<ProductFrontDTO> variants;
	private List<String> colors;
	private List<ColorDTO> colorsdto;
	private List<String> calibrations;
	private List<String> calibers;
	private List<String> tamanios;
	private List<String> sizes;
	private InfoPackGenericoDTO infoPack;

	private Map <String, VariantDTO> promociones = new HashMap <String, VariantDTO> ();
	
	public MasterProductFrontDTO() {}
	
	public ProductFrontDTO getMaster() {
		return master;
	}

	public void setMaster(ProductFrontDTO master) {
		this.master = master;
	}

	public List<ProductFrontDTO> getVariants() {
		return variants;
	}
	public void setVariants(List<ProductFrontDTO> variants) {
		this.variants = variants;
	}

	public List<String> getColors() {
		return colors;
	}

	public void setColors(List<String> colors) {
		this.colors = colors;
	}
	
	public List<ColorDTO> getColorsdto() {
		return colorsdto;
	}

	public void setColorsdto(List<ColorDTO> colorsdto) {
		this.colorsdto = colorsdto;
	}

	public List<String> getCalibrations() {
		return calibrations;
	}

	public void setCalibrations(List<String> calibrations) {
		this.calibrations = calibrations;
	}

	public List<String> getCalibers() {
		return calibers;
	}

	public void setCalibers(List<String> calibers) {
		this.calibers = calibers;
	}
	
	public List<String> getCalibrationsByColor(String color){
		
		List<String> res = new ArrayList<>();
		
		if(colors.contains(color)) {
			
			if(master.getColor() != null && master.getColor().equals(color) && master.getCalibration() != null) {
				res.add(master.getCalibration());
			}
			
			for(ProductFrontDTO p: variants) {
				if(p.getColor() != null && p.getColor().equals(color) && p.getCalibration() != null && !res.contains(p.getCalibration())) {
					res.add(p.getCalibration());	
					
				}
			}
		}
		
		return res.isEmpty() ? Collections.emptyList() : res;
	}
	
	public List<String> getCalibersByColor(String color){
		
		List<String> res = new ArrayList<>();
		
		if(colors.contains(color)) {
			
			if(master.getColor() != null && master.getColor().equals(color) && master.getCalibre() != null) {
				res.add(master.getCalibre());
			}
			
			for(ProductFrontDTO p: variants) {
				if(p.getColor() != null && p.getColor().equals(color) && p.getCalibre() != null && !res.contains(p.getCalibre())) {
					res.add(p.getCalibre());	
				}
			}
		}
		
		return res.isEmpty() ? Collections.emptyList() : res;
	}
	
	public List<String> getCalibrationsByColorAndCaliber(String color, String caliber){
		
		List<String> res = new ArrayList<>();
		
		if(colors.contains(color) && calibers.contains(caliber)) {
			
			if(master.getColor() != null && master.getColor().equals(color) && master.getCalibration() != null) {
				if(master.getCalibre() != null && master.getCalibre().equals(caliber)) {
					res.add(master.getCalibration());
				}
			}
			
			for(ProductFrontDTO p: variants) {
				if(p.getColor() != null && p.getColor().equals(color) && p.getCalibration() != null && !res.contains(p.getCalibration())) {
					if(p.getCalibre() != null && p.getCalibre().equals(caliber)) {
						res.add(p.getCalibration());	
					}
					
				}
			}
		}
		
		return res.isEmpty() ? Collections.emptyList() : res;
	}
	
	public List<String> getCalibrationsByCaliber(String caliber){
		
		List<String> res = new ArrayList<>();
		
		if(calibers.contains(caliber)) {
			
			if(master.getCalibre() != null && master.getCalibre().equals(caliber) && master.getCalibration() != null) {
				res.add(master.getCalibration());
			}
			
			for(ProductFrontDTO p: variants) {
				if(p.getCalibre() != null && p.getCalibre().equals(caliber) && p.getCalibration() != null && !res.contains(p.getCalibration())) {
					res.add(p.getCalibration());	
				}
			}
		}
		
		return res.isEmpty() ? Collections.emptyList() : res;
	}
	
	public List<String> getItems(){
		
		if (master.getColor() != null) {
			
			List<String> mcolors = colors;
			mcolors.remove(master.getColor());
			return mcolors;
		}
		
		if (master.getCalibre() != null) {
			List<String> mcalibers = calibers;
			mcalibers.remove(master.getCalibre());
			return mcalibers;
		}
		
		if (master.getCalibration()!= null) {
			List<String> mcalibrations = calibrations;
			mcalibrations.remove(master.getCalibre());
			return mcalibrations;
		}
		
		return Collections.emptyList();
	}
	
	public String getSkuByColorAndCaliber(String color, String caliber) {
		
		String res = null;
		try {
			res = "";
			
			String mcolor = (master.getColor() == null || master.getColor().isEmpty()) ? "" : master.getColor();
			String mcaliber = (master.getCalibre() == null || master.getCalibre().isEmpty()) ? "" : master.getCalibre();
			
			if(mcolor.equals(color) && mcaliber.equals(caliber))
				return master.getSku();
			
			for(ProductFrontDTO p: variants) {
				
				String pcolor = (p.getColor() == null || p.getColor().isEmpty()) ? "" : p.getColor();
				String pcaliber = (p.getCalibre() == null || p.getCalibre().isEmpty()) ? "" : p.getCalibre();
				
				if(pcolor.equals(color) && pcaliber.equals(caliber))
					return p.getSku();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}
	
	public String getSkuByColorAndCalibration(String color, String calibration) {
		
		String res = "";
		
		String mcolor = (master.getColor() == null || master.getColor().isEmpty()) ? "" : master.getColor();
		String mcalibration = (master.getCalibration() == null) || master.getCalibration().isEmpty()  ? "" : master.getCalibration();
		
		if(mcolor.equals(color) && mcalibration.equals(calibration))
			return master.getSku();
		
		for(ProductFrontDTO p: variants) {
			
			String pcolor = (p.getColor() == null || p.getColor().isEmpty()) ? "" : p.getColor();
			String pcalibration = (p.getCalibration() == null || p.getCalibration().isEmpty()) ? "" : p.getCalibration();
			
			if(pcolor.equals(color) && pcalibration.equals(calibration))
				return p.getSku();
		}
		
		return res;
	}
	
	public String getSkuByColor(String color) {
		
		String res = "";
		
		String mcolor = (master.getColor() == null || master.getColor().isEmpty()) ? "" : master.getColor();
		
		if(mcolor.equals(color))
			return master.getSku();
		
		for(ProductFrontDTO p: variants) {
			
			String pcolor = (p.getColor() == null || p.getColor().isEmpty()) ? "" : p.getColor();
			
			if(pcolor.equals(color))
				return p.getSku();
		}
		
		return res;
	}
	
	public String getSkuByCaliber(String caliber) {
		
		String res = "";
		
		String mcaliber = (master.getCalibre() == null || master.getCalibre().isEmpty()) ? "" : master.getCalibre();
		
		if(mcaliber.equals(caliber))
			return master.getSku();
		
		for(ProductFrontDTO p: variants) {
			
			String pcaliber = (p.getCalibre() == null || p.getCalibre().isEmpty()) ? "" : p.getCalibre();
			
			if(pcaliber.equals(caliber))
				return p.getSku();
		}
		
		return res;
	}
	
	public String getSkuByCalibration(String calibration) {
		
		String res = "";
		
		String mcalibration = (master.getCalibration() == null || master.getCalibration().isEmpty()) ? "" : master.getCalibration();
		
		if(mcalibration.equals(calibration))
			return master.getSku();
		
		for(ProductFrontDTO p: variants) {
			
			String pcalibration = (p.getCalibration() == null || p.getCalibration().isEmpty()) ? "" : p.getCalibration();
			
			if(pcalibration.equals(calibration))
				return p.getSku();
		}
		
		return res;
	}
	
	public String getSkuByTamanio(String tamanio) {
		
		String res = "";
		
		String mtamanio = (master.getTamanio() == null || master.getTamanio().isEmpty()) ? "" : master.getTamanio();

		if(mtamanio.equals(tamanio))
			return master.getSku();
		
		for(ProductFrontDTO p: variants) {
			
			String ptamanio = (p.getTamanio() == null || p.getTamanio().isEmpty()) ? "" : p.getTamanio();
			
			if(ptamanio.equals(tamanio))
				return p.getSku();
		}
		
		return res;
		
	}
	
	private boolean contains(String aliasEkon, String codigoCentral) {
		
		if (master.getAliasEKON() != null) {
			if (master.getAliasEKON().equals(aliasEkon) && master.getCodigocentral().equals(codigoCentral)) {
				return true;
			}
			
			for(ProductFrontDTO product : variants) {
				if (product.getAliasEKON().equals(aliasEkon) && master.getCodigocentral().equals(codigoCentral)) {
					return true;
				}
			}
		} else {
			//son lentes de contacto por lo que el AliasEkon es *
			if (master.getCodigocentral() != null) {
				if (master.getCodigocentral().equals(codigoCentral)) {
					return true;
				}
				
				for(ProductFrontDTO product : variants) {
					if ((product.getCodigocentral() != null) && product.getCodigocentral().equals(codigoCentral)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean setDiscontByAlias(String aliasEkon, String codigoCentral, Float promo, int tipo_descuento, int amount, String descripcion) {
		
		boolean res = false;
		
		if(contains(aliasEkon, codigoCentral)) {
			
			if ((master.getAliasEKON() != null) && master.getAliasEKON().equals(aliasEkon) && Boolean.TRUE.equals(master.getPromo())
					&& (master.getCodigocentral() != null) && master.getCodigocentral().equals(codigoCentral))  {
				if (amount==1 && master.getPvo() != null) {
					Double discount = setDiscount(master.getPvo(), promo, tipo_descuento);
					master.setDiscount(discount);
				}
				master.setAmoutdiscount(amount);
				master.setDescripcion(descripcion);
				return true;
				
			} else if (aliasEkon.equals("*") && master.getCodigocentral() != null && master.getCodigocentral().equals(codigoCentral) && Boolean.TRUE.equals(master.getPromo())) {
				if (amount==1 && master.getPvo() != null) {
					Double discount = setDiscount(master.getPvo(), promo, tipo_descuento);
					master.setDiscount(discount);
				}
				master.setAmoutdiscount(amount);
				master.setDescripcion(descripcion);
				return true;
			}
			
				
			for(ProductFrontDTO product : variants) {
				if ((product.getAliasEKON() != null && product.getAliasEKON().equals(aliasEkon) && Boolean.TRUE.equals(product.getPromo()))
						&& (product.getCodigocentral() != null && product.getCodigocentral().equals(codigoCentral))) {
					if (amount==1 && master.getPvo() != null) {
						Double discount = setDiscount(product.getPvo(), promo, tipo_descuento);
						product.setDiscount(discount);
					}
					product.setAmoutdiscount(amount);
					product.setDescripcion(descripcion);
					return true;
				} else if (aliasEkon.equals("*") && (master.getCodigocentral() != null) 
						&& master.getCodigocentral().equals(codigoCentral) && Boolean.TRUE.equals(master.getPromo())) {
					if (amount==1 && master.getPvo() != null) {
						Double discount = setDiscount(product.getPvo(), promo, tipo_descuento);
						product.setDiscount(discount);
					}
					product.setAmoutdiscount(amount);
					product.setDescripcion(descripcion);
					return true;
				}
			}
			
		}
		
		return res;
	}
	
	public boolean setSurchargeByAlias(String aliasEkon, String codigoCentral, Float promo, int tipo_descuento) {
		
		boolean res = false;
	
		if(contains(aliasEkon, codigoCentral)) {
			if ((master.getAliasEKON() != null && master.getAliasEKON().equals(aliasEkon))
					&& (master.getCodigocentral() != null && master.getCodigocentral().equals(codigoCentral) 
					&& Boolean.TRUE.equals(master.getRecargo()) )) {
				Double pvo = setSurcharge(master.getPvo(), promo, tipo_descuento);
				master.setPvo(pvo);
				master.setPromo(false);
				
				return true;
			} else if (aliasEkon.equals("*") && master.getCodigocentral() != null && master.getCodigocentral().equals(codigoCentral) 
					&& Boolean.TRUE.equals(master.getRecargo()) ) {
					Double pvo = setSurcharge(master.getPvo(), promo, tipo_descuento);
					master.setPvo(pvo);
					master.setPromo(false);
					
					return true;
			}
			
			for(ProductFrontDTO product : variants) {
				if ((product.getAliasEKON() != null && product.getAliasEKON().equals(aliasEkon))
						&& (product.getCodigocentral() != null && product.getCodigocentral().equals(codigoCentral)
						&& Boolean.TRUE.equals(product.getRecargo())
					)) {
					Double discount = setSurcharge(product.getPvo(), promo, tipo_descuento);
					product.setPvo(discount);
					product.setPromo(false);
					
					return true;
				} else if (aliasEkon.equals("*") && (product.getCodigocentral() != null) 
						&& product.getCodigocentral().equals(codigoCentral)
						&& Boolean.TRUE.equals(product.getRecargo()) ) {
					Double discount = setSurcharge(product.getPvo(), promo, tipo_descuento);
					product.setPvo(discount);
					product.setPromo(false);
					return true;
				}
				
			}
		}
		
		return res;
	}
	
	private Double setDiscount(Double pvo, Float promo, int tipo_descuento) {
		if (tipo_descuento == 1) {
			return pvo - (pvo*(promo/100));
		} else {
			return pvo - promo;
		}
		
		
	}
	
	private Double setSurcharge(Double pvo, Float promo, int tipo_descuento) { //54,4   -5
		if (tipo_descuento == 1) {
			double dto= ((100-promo)/100);
			return pvo * dto;
		} else {
			return pvo - promo;
		}
		
		//return pvo + (pvo*(promo/100));
		
	}

	public Map <String, VariantDTO> getPromociones() {
		return promociones;
	}

	public void setPromociones(Map <String, VariantDTO> promociones) {
		this.promociones = promociones;
	}

	public List<String> getTamanios() {
		return tamanios;
	}

	public void setTamanios(List<String> tamanios) {
		this.tamanios = tamanios;
	}
	
	public InfoPackGenericoDTO getInfoPack() {
		return infoPack;
	}

	public void setInfoPack(InfoPackGenericoDTO infoPack) {
		this.infoPack = infoPack;
	}

	public List<String> getSizes() {
		return sizes;
	}

	public void setSizes(List<String> sizes) {
		this.sizes = sizes;
	}
	
}
