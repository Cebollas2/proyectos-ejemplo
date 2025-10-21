package com.magnolia.cione.dto;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.product.Attribute;
import com.commercetools.api.models.product_type.AttributePlainEnumValue;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.utils.MyShopUtils;

public class InfoPackGenericoDTO {
	
	private String tipoPrecioPack; //cerrado, global, individual
	private String tipoPackGenerico; //Familia a la que pertenece el pack
	private String descuentoGlobal;  //Descuento a aplicar a cada componente del pack, puede ser % o por valor
	private String tipoProducto;
	private boolean porcentaje;
	private boolean valor;
	private Double valorDescuentoGlobal = 0.0;
	private String descripcionPack;
	
	//(VA A NIVEL DE VARIANTE)
	//tipoPrecioPack=INDIVIDUAL-DTO y tipoPrecioVariante=DESCUENTO
	private String tipoPrecioVariante;  //PVO-CERRADO , DESCUENTO , CIONELAB
	private String descuentoVariante; //Descuento a aplicar al componente del pack (variante), puede ser % o por valor (misma aplicacion que descuentoGlobal)
	private Double valorDescuentoVariante = 0.0; //almacena descuentoVariante eliminando % en caso de tenerlo
	private boolean porcentajeIndividual;
	private boolean valorIndividual;
	
	private static final Logger log = LoggerFactory.getLogger(InfoPackGenericoDTO.class);

	public InfoPackGenericoDTO(List<Attribute> attributes) {
		super();
		try {
			if (MyShopUtils.hasAttribute("tipoPackGenerico", attributes)) //solo se usa para identificar monturas mas lentes "ML"
				this.tipoPackGenerico = ((AttributePlainEnumValue) MyShopUtils.findAttribute("tipoPackGenerico", attributes).getValue()).getLabel();
			if (MyShopUtils.hasAttribute("tipoPrecioPack", attributes))
				this.tipoPrecioPack = ((AttributePlainEnumValue) MyShopUtils.findAttribute("tipoPrecioPack", attributes).getValue()).getKey();
			if (MyShopUtils.hasAttribute("descuentoGlobal", attributes))
				this.descuentoGlobal = (String) MyShopUtils.findAttribute("descuentoGlobal", attributes).getValue();
			if (MyShopUtils.hasAttribute("tipoProducto", attributes))
				this.tipoProducto = ((LocalizedString) MyShopUtils.findAttribute("tipoProducto", attributes).getValue()).get(MyshopConstants.esLocale);
			
			if ((tipoPrecioPack!= null) && (tipoPrecioPack.equals("GLOBAL"))){
				if ((descuentoGlobal!= null) && (!descuentoGlobal.isEmpty())){
					if (descuentoGlobal.contains("%")) {
						this.porcentaje=true;
						this.valorDescuentoGlobal = Double.valueOf(descuentoGlobal.substring(0, descuentoGlobal.indexOf("%")));
					}else {
						this.valor=true;
						this.valorDescuentoGlobal = Double.valueOf(descuentoGlobal);
					}
				}
			}
			
			if (MyShopUtils.hasAttribute("tipoPrecioVariante", attributes)) {
				this.tipoPrecioVariante = ((AttributePlainEnumValue) MyShopUtils.findAttribute("tipoPrecioVariante", attributes).getValue()).getKey();
			}
			if (MyShopUtils.hasAttribute("descuentoVariante", attributes)) {
				this.descuentoVariante = (String) MyShopUtils.findAttribute("descuentoVariante", attributes).getValue();
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public String getTipoPrecioPack() {
		return tipoPrecioPack;
	}
	public void setTipoPrecioPack(String tipoPrecioPack) {
		this.tipoPrecioPack = tipoPrecioPack;
	}
	public String getTipoPackGenerico() {
		return tipoPackGenerico;
	}
	public void setTipoPackGenerico(String tipoPackGenerico) {
		this.tipoPackGenerico = tipoPackGenerico;
	}
	public String getDescuentoGlobal() {
		return descuentoGlobal;
	}
	public void setDescuentoGlobal(String descuentoGlobal) {
		this.descuentoGlobal = descuentoGlobal;
	}
	public String getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	public boolean isPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(boolean porcentaje) {
		this.porcentaje = porcentaje;
	}
	public boolean isValor() {
		return valor;
	}
	public void setValor(boolean valor) {
		this.valor = valor;
	}
	public Double getValorDescuentoGlobal() {
		return valorDescuentoGlobal;
	}
	public void setValorDescuentoGlobal(Double valorDescuentoGlobal) {
		this.valorDescuentoGlobal = valorDescuentoGlobal;
	}
	public String getDescripcionPack() {
		return descripcionPack;
	}
	public void setDescripcionPack(String descripcionPack) {
		this.descripcionPack = descripcionPack;
	}
	public String getTipoPrecioVariante() {
		return tipoPrecioVariante;
	}
	public void setTipoPrecioVariante(String tipoPrecioVariante) {
		this.tipoPrecioVariante = tipoPrecioVariante;
	}
	public String getDescuentoVariante() {
		return descuentoVariante;
	}
	public void setDescuentoVariante(String descuentoVariante) {
		this.descuentoVariante = descuentoVariante;
	}
	public Double getValorDescuentoVariante() {
		return valorDescuentoVariante;
	}
	public void setValorDescuentoVariante(Double valorDescuentoVariante) {
		this.valorDescuentoVariante = valorDescuentoVariante;
	}
	public boolean isPorcentajeIndividual() {
		return porcentajeIndividual;
	}
	public void setPorcentajeIndividual(boolean porcentajeIndividual) {
		this.porcentajeIndividual = porcentajeIndividual;
	}
	public boolean isValorIndividual() {
		return valorIndividual;
	}
	public void setValorIndividual(boolean valorIndividual) {
		this.valorIndividual = valorIndividual;
	}
}
