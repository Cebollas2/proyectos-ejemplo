package com.magnolia.cione.dto;

import java.io.Serializable;
import java.util.List;

public class AgrupadorDTO implements Cloneable, Serializable{
	private boolean master;
	private String tipoProductoPack; //deprecated
	private String tipoProductoPackKey; //deprecated
	private List<String> agrupadores;
	private Integer unidadesProductoPack =1;
	private boolean configurado; //se setea en el carrito
	private String skuProductoConfigurado; //se setea en el carrito
	private String nombreProductoConfigurado; //se setea en el carrito
	
	private String urlImagen;
	
	private static final long serialVersionUID = 1L;
	
	private String tipoPrecioPack;
	private String pvoOrigin;
	private String pvoPack;//precio del producto al comprarlo en el pack dependiendo del tipoPrecioPack
	private String pvoAllLines;
	
	private boolean filtrosObligatorios; //se setea en el carrito
	
	//tipoPrecioPack=GLOBAL
	private String descuentoGlobal; 
	private Double valorDescuentoGlobal = 0.0;
	private boolean porcentaje;
	private boolean valor;
	
	//tipoPrecioPack=INDIVIDUAL-DTO y tipoPrecioVariante=DESCUENTO
	private String tipoPrecioVariante;  //PVO-CERRADO , DESCUENTO , CIONELAB
	private String descuentoVariante; //Descuento a aplicar al componente del pack (variante), puede ser % o por valor (misma aplicacion que descuentoGlobal)
	private Double valorDescuentoVariante = 0.0; //almacena descuentoVariante eliminando % en caso de tenerlo
	private boolean porcentajeIndividual;
	private boolean valorIndividual;
	
	//sku en caso de que sea unn producto especifico
	private String skuProductoPackPreconfigurado; //sku del producto contenido en el pack (relacion 1-1)
	private List<String> listadoSkuProductosPackPreconfigurados;
	
	private String idCarritoOculto;
	private String lineItemIdOculto;
	private String step;
	private String packLentes; //pack por el que filtramos en la pantalla de CioneLab
	private boolean habilitar=true; //controla para permitir seleccionar el producto en la pantalla detalle pack (en pack montura+lente hay que seleccionar primero la montura)
	private String refTaller;

	private List<AgrupadorDTO> infoCustomLineItemsCioneLab;


	@Override
	public String toString() {
		return "AgrupadorDTO [master=" + master + ", tipoProductoPack=" + tipoProductoPack + ", agrupadores="
				+ agrupadores + ", unidadesProductoPack=" + unidadesProductoPack + ", configurado=" + configurado
				+ ", skuProductoConfigurado=" + skuProductoConfigurado + ", nombreProductoConfigurado="
				+ nombreProductoConfigurado + ", urlImagen=" + urlImagen +", tipoPrecioPack=" + tipoPrecioPack + ", pvoOrigin=" + pvoOrigin
				+ ", pvoPack=" + pvoPack + ", pvoAllLines=" + pvoAllLines + ", filtrosObligatorios="
				+ filtrosObligatorios + ", descuentoGlobal=" + descuentoGlobal + ", valorDescuentoGlobal="
				+ valorDescuentoGlobal + ", porcentaje=" + porcentaje + ", valor=" + valor + ", tipoPrecioVariante="
				+ tipoPrecioVariante + ", descuentoVariante=" + descuentoVariante + ", valorDescuentoVariante="
				+ valorDescuentoVariante + ", porcentajeIndividual=" + porcentajeIndividual + ", valorIndividual="
				+ valorIndividual + ", skuProductoPackPreconfigurado=" + skuProductoPackPreconfigurado
				+ ", listadoSkuProductosPackPreconfigurados=" + listadoSkuProductosPackPreconfigurados
				+ ", idCarritoOculto=" + idCarritoOculto + ", lineItemIdOculto=" + lineItemIdOculto + ", step=" + step
				+ "]";
	}

	@Override
    public AgrupadorDTO clone() {
        try {
            return (AgrupadorDTO) super.clone(); 
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); 
        }
    }

	public boolean isMaster() {
		return master;
	}
	public void setMaster(boolean master) {
		this.master = master;
	}
	public String getTipoProductoPack() {
		return tipoProductoPack;
	}
	public void setTipoProductoPack(String tipoProductoPack) {
		this.tipoProductoPack = tipoProductoPack;
	}
	public String getTipoProductoPackKey() {
		return tipoProductoPackKey;
	}
	public void setTipoProductoPackKey(String tipoProductoPackKey) {
		this.tipoProductoPackKey = tipoProductoPackKey;
	}
	public List<String> getAgrupadores() {
		return agrupadores;
	}
	public void setAgrupadores(List<String> agrupadores) {
		this.agrupadores = agrupadores;
	}
	public String getSkuProductoPackPreconfigurado() {
		return skuProductoPackPreconfigurado;
	}
	public void setSkuProductoPackPreconfigurado(String skuProductoPackPreconfigurado) {
		this.skuProductoPackPreconfigurado = skuProductoPackPreconfigurado;
	}
	public List<String> getListadoSkuProductosPackPreconfigurados() {
		return listadoSkuProductosPackPreconfigurados;
	}
	public void setListadoSkuProductosPackPreconfigurados(List<String> listadoSkuProductosPackPreconfigurados) {
		this.listadoSkuProductosPackPreconfigurados = listadoSkuProductosPackPreconfigurados;
	}
	public int getUnidadesProductoPack() {
		return unidadesProductoPack;
	}
	public void setUnidadesProductoPack(Integer unidadesProductoPack) {
		this.unidadesProductoPack = unidadesProductoPack;
	}
	public boolean isConfigurado() {
		return configurado;
	}
	public void setConfigurado(boolean configurado) {
		this.configurado = configurado;
	}
	public String getSkuProductoConfigurado() {
		return skuProductoConfigurado;
	}
	public void setSkuProductoConfigurado(String skuProductoConfigurado) {
		this.skuProductoConfigurado = skuProductoConfigurado;
	}
	public String getNombreProductoConfigurado() {
		return nombreProductoConfigurado;
	}
	public String getUrlImagen() {
		return urlImagen;
	}
	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}
	public void setNombreProductoConfigurado(String nombreProductoConfigurado) {
		this.nombreProductoConfigurado = nombreProductoConfigurado;
	}
	public String getTipoPrecioPack() {
		return tipoPrecioPack;
	}
	public void setTipoPrecioPack(String tipoPrecioPack) {
		this.tipoPrecioPack = tipoPrecioPack;
	}
    public String getPvoOrigin() {
		return pvoOrigin;
	}
	public void setPvoOrigin(String pvoOrigin) {
		this.pvoOrigin = pvoOrigin;
	}
	public String getPvoPack() {
		return pvoPack;
	}
	public void setPvoPack(String pvoPack) {
		this.pvoPack = pvoPack;
	}
	public String getPvoAllLines() {
		return pvoAllLines;
	}
	public void setPvoAllLines(String pvoAllLines) {
		this.pvoAllLines = pvoAllLines;
	}
	public boolean isFiltrosObligatorios() {
		return filtrosObligatorios;
	}
	public void setFiltrosObligatorios(boolean filtrosObligatorios) {
		this.filtrosObligatorios = filtrosObligatorios;
	}
	public String getDescuentoGlobal() {
		return descuentoGlobal;
	}
	public void setDescuentoGlobal(String descuentoGlobal) {
		this.descuentoGlobal = descuentoGlobal;
	}
	public Double getValorDescuentoGlobal() {
		return valorDescuentoGlobal;
	}
	public void setValorDescuentoGlobal(Double valorDescuentoGlobal) {
		this.valorDescuentoGlobal = valorDescuentoGlobal;
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
	public String getIdCarritoOculto() {
		return idCarritoOculto;
	}
	public void setIdCarritoOculto(String idCarritoOculto) {
		this.idCarritoOculto = idCarritoOculto;
	}
	public String getLineItemIdOculto() {
		return lineItemIdOculto;
	}
	public void setLineItemIdOculto(String lineItemIdOculto) {
		this.lineItemIdOculto = lineItemIdOculto;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getPackLentes() {
		return packLentes;
	}
	public void setPackLentes(String packLentes) {
		this.packLentes = packLentes;
	}
	public boolean isHabilitar() {
		return habilitar;
	}
	public void setHabilitar(boolean habilitar) {
		this.habilitar = habilitar;
	}
	public String getRefTaller() {
		return refTaller;
	}
	public void setRefTaller(String refTaller) {
		this.refTaller = refTaller;
	}
	public List<AgrupadorDTO> getInfoCustomLineItemsCioneLab() {
		return infoCustomLineItemsCioneLab;
	}
	public void setInfoCustomLineItemsCioneLab(List<AgrupadorDTO> infoCustomLineItemsCioneLab) {
		this.infoCustomLineItemsCioneLab = infoCustomLineItemsCioneLab;
	}
}
