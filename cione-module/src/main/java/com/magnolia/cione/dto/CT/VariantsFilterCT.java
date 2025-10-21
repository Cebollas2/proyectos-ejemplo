package com.magnolia.cione.dto.CT;

import java.util.Map;

import com.commercetools.api.models.product.FacetResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.magnolia.cione.dto.CT.variants.VariantsAttributes;

public class VariantsFilterCT{
	
	
	private Map<String, FacetResult> variantsAttributes;

	@JsonProperty("variants.attributes.graduacion") 
    private VariantsAttributes variantsAttributesGraduacion;
    
    @JsonProperty("variants.attributes.tamanios.es") 
    private VariantsAttributes variantsAttributesTamaniosEs;
    
    @JsonProperty("variants.attributes.size") 
    private VariantsAttributes variantsAttributesSize;
    
	@JsonProperty("variants.attributes.color") 
    private VariantsAttributes variantsAttributesColorAudio;

	@JsonProperty("variants.attributes.modelo") 
    private VariantsAttributes variantsAttributesModelo;

	@JsonProperty("variants.attributes.colorMontura.es") 
    private VariantsAttributes variantsAttributesColorMonturaEs;
    
    @JsonProperty("variants.attributes.gamaColorMontura.es") 
    private VariantsAttributes variantsAttributesGamaColorMonturaEs;
    
    @JsonProperty("variants.attributes.target.es") 
    private VariantsAttributes variantsAttributesTargetEs;
    
    @JsonProperty("variants.attributes.material") 
    private VariantsAttributes variantsAttributesMaterial;
    
    @JsonProperty("variants.attributes.familiaEkon") 
    private VariantsAttributes variantsAttributesFamiliaEkon;
    
    @JsonProperty("variants.attributes.tipoProducto.es") 
    private VariantsAttributes variantsAttributesTipoProductoEs;
    
    @JsonProperty("variants.attributes.coleccion.es") 
    private VariantsAttributes variantsAttributesColeccionEs;
    
    @JsonProperty("variants.attributes.dimensiones_largo_varilla") 
    private VariantsAttributes variantsAttributesDimensionesLargoVarilla;
    
    @JsonProperty("variants.attributes.marca") 
    private VariantsAttributes variantsAttributesMarca;
    
    @JsonProperty("variants.attributes.dimensiones_ancho_ojo") 
    private VariantsAttributes variantsAttributesDimensionesAnchoOjo;
    
    @JsonProperty("variants.attributes.statusEkon") 
    private VariantsAttributes variantsAttributesStatusEkon;
    
    @JsonProperty("variants.attributes.pruebaVirtual") 
    private VariantsAttributes variantsAttributesPruebaVirtual;
    
    @JsonProperty("variants.attributes.lineaNegocio") 
    private VariantsAttributes variantsAttributesLineaNegocio;
    
    @JsonProperty("variants.attributes.tipoPack") 
    private VariantsAttributes variantsAttributesTipoPack;
    
    @JsonProperty("variants.attributes.tienePromociones") 
    private VariantsAttributes variantsAttributesTienePromociones;
    
    @JsonProperty("variants.attributes.subTipoProducto") 
    private VariantsAttributes variantsAttributesSubTipoProducto;
    
    @JsonProperty("variants.attributes.blisterocaja") 
    private VariantsAttributes variantsAttributesBlisterocaja;
    
    @JsonProperty("variants.attributes.reemplazo") 
    private VariantsAttributes variantsAttributesReemplazo;
    
    @JsonProperty("variants.attributes.isContactLab") 
    private VariantsAttributes variantsAttributesisContactLab;

	@JsonProperty("variants.attributes.materialbase") 
    private VariantsAttributes variantsAttributesMaterialbase;
    
    @JsonProperty("variants.attributes.geometria") 
    private VariantsAttributes variantsAttributesGeometria;
    
    @JsonProperty("variants.attributes.gama") 
    private VariantsAttributes variantsAttributesGama;
    
    @JsonProperty("variants.attributes.proveedor") 
    private VariantsAttributes variantsAttributesProveedor;
    
    @JsonProperty("variants.attributes.bproteccionSolar") 
    private VariantsAttributes variantsAttributesBproteccionSolar;
    
    @JsonProperty("variants.attributes.disponibilidad") 
    private VariantsAttributes variantsAttributesDisponibilidad;
    
    @JsonProperty("variants.attributes.familiaAudio") 
    private VariantsAttributes variantsAttributesFamiliaAudio;
    
    @JsonProperty("variants.attributes.segmento") 
    private VariantsAttributes variantsAttributesSegmento;
    
    @JsonProperty("variants.attributes.prestaciones") 
    private VariantsAttributes variantsAttributesPrestaciones;
    
    @JsonProperty("variants.attributes.pila") 
    private VariantsAttributes variantsAttributesPila;
    
    @JsonProperty("variants.attributes.composicion") 
    private VariantsAttributes variantsAttributescComposicion;
    
    @JsonProperty("variants.attributes.formatoAudio") 
    private VariantsAttributes variantsAttributesFormatosAudio;

	private int min;
    private int max;
    private boolean activeprice;
    
    public Map<String, FacetResult> getVariantsAttributes() {
		return variantsAttributes;
	}

	public void setVariantsAttributes(Map<String, FacetResult> variantsAttributes) {
		this.variantsAttributes = variantsAttributes;
	}
    
	public VariantsAttributes getVariantsAttributesPila() {
		return variantsAttributesPila;
	}

	public void setVariantsAttributesPila(VariantsAttributes variantsAttributesPila) {
		this.variantsAttributesPila = variantsAttributesPila;
	}

	public VariantsAttributes getVariantsAttributesPrestaciones() {
		return variantsAttributesPrestaciones;
	}

	public void setVariantsAttributesPrestaciones(VariantsAttributes variantsAttributesPrestaciones) {
		this.variantsAttributesPrestaciones = variantsAttributesPrestaciones;
	}

	public VariantsAttributes getVariantsAttributesSegmento() {
		return variantsAttributesSegmento;
	}

	public void setVariantsAttributesSegmento(VariantsAttributes variantsAttributesSegmento) {
		this.variantsAttributesSegmento = variantsAttributesSegmento;
	}

	public VariantsAttributes getVariantsAttributesFamiliaAudio() {
		return variantsAttributesFamiliaAudio;
	}

	public void setVariantsAttributesFamiliaAudio(VariantsAttributes variantsAttributesFamiliaAudio) {
		this.variantsAttributesFamiliaAudio = variantsAttributesFamiliaAudio;
	}
	
	public VariantsAttributes getVariantsAttributescComposicion() {
		return variantsAttributescComposicion;
	}

	public void setVariantsAttributescComposicion(VariantsAttributes variantsAttributescComposicion) {
		this.variantsAttributescComposicion = variantsAttributescComposicion;
	}

	public VariantsAttributes getVariantsAttributesDisponibilidad() {
		return variantsAttributesDisponibilidad;
	}

	public void setVariantsAttributesDisponibilidad(VariantsAttributes variantsAttributesDisponibilidad) {
		this.variantsAttributesDisponibilidad = variantsAttributesDisponibilidad;
	}

	public VariantsAttributes getVariantsAttributesGeometria() {
		return variantsAttributesGeometria;
	}

	public void setVariantsAttributesGeometria(VariantsAttributes variantsAttributesGeometria) {
		this.variantsAttributesGeometria = variantsAttributesGeometria;
	}

	public VariantsAttributes getVariantsAttributesGama() {
		return variantsAttributesGama;
	}

	public void setVariantsAttributesGama(VariantsAttributes variantsAttributesGama) {
		this.variantsAttributesGama = variantsAttributesGama;
	}

	public VariantsAttributes getVariantsAttributesProveedor() {
		return variantsAttributesProveedor;
	}

	public void setVariantsAttributesProveedor(VariantsAttributes variantsAttributesProveedor) {
		this.variantsAttributesProveedor = variantsAttributesProveedor;
	}

	public VariantsAttributes getVariantsAttributesBproteccionSolar() {
		return variantsAttributesBproteccionSolar;
	}

	public void setVariantsAttributesBproteccionSolar(VariantsAttributes variantsAttributesBproteccionSolar) {
		this.variantsAttributesBproteccionSolar = variantsAttributesBproteccionSolar;
	}

	public VariantsAttributes getVariantsAttributesMaterialbase() {
		return variantsAttributesMaterialbase;
	}

	public void setVariantsAttributesMaterialbase(VariantsAttributes variantsAttributesMaterialbase) {
		this.variantsAttributesMaterialbase = variantsAttributesMaterialbase;
	}

	public VariantsAttributes getVariantsAttributesReemplazo() {
		return variantsAttributesReemplazo;
	}

	public void setVariantsAttributesReemplazo(VariantsAttributes variantsAttributesReemplazo) {
		this.variantsAttributesReemplazo = variantsAttributesReemplazo;
	}
	
    public VariantsAttributes getVariantsAttributesisContactLab() {
		return variantsAttributesisContactLab;
	}

	public void setVariantsAttributesisContactLab(VariantsAttributes variantsAttributesisContactLab) {
		this.variantsAttributesisContactLab = variantsAttributesisContactLab;
	}

	public VariantsAttributes getVariantsAttributesBlisterocaja() {
		return variantsAttributesBlisterocaja;
	}

	public void setVariantsAttributesBlisterocaja(VariantsAttributes variantsAttributesBlisterocaja) {
		this.variantsAttributesBlisterocaja = variantsAttributesBlisterocaja;
	}

	public boolean isActiveprice() {
		return activeprice;
	}
	
	public void setActiveprice(boolean activeprice) {
		this.activeprice = activeprice;
	}
	
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
    
	public VariantsAttributes getVariantsAttributesGraduacion() {
		return variantsAttributesGraduacion;
	}

	public void setVariantsAttributesGraduacion(VariantsAttributes variantsAttributesGraduacion) {
		this.variantsAttributesGraduacion = variantsAttributesGraduacion;
	}

	public VariantsAttributes getVariantsAttributesTamaniosEs() {
		return variantsAttributesTamaniosEs;
	}

	public void setVariantsAttributesTamaniosEs(VariantsAttributes variantsAttributesTamaniosEs) {
		this.variantsAttributesTamaniosEs = variantsAttributesTamaniosEs;
	}
	
    public VariantsAttributes getVariantsAttributesSize() {
		return variantsAttributesSize;
	}

	public void setVariantsAttributesSize(VariantsAttributes variantsAttributesSize) {
		this.variantsAttributesSize = variantsAttributesSize;
	}

	public VariantsAttributes getVariantsAttributesColorAudio() {
		return variantsAttributesColorAudio;
	}

	public void setVariantsAttributesColorAudio(VariantsAttributes variantsAttributesColorAudio) {
		this.variantsAttributesColorAudio = variantsAttributesColorAudio;
	}
	
    public VariantsAttributes getVariantsAttributesModelo() {
		return variantsAttributesModelo;
	}

	public void setVariantsAttributesModelo(VariantsAttributes variantsAttributesModelo) {
		this.variantsAttributesModelo = variantsAttributesModelo;
	}

	public VariantsAttributes getVariantsAttributesColorMonturaEs() {
		return variantsAttributesColorMonturaEs;
	}

	public void setVariantsAttributesColorMonturaEs(VariantsAttributes variantsAttributesColorMonturaEs) {
		this.variantsAttributesColorMonturaEs = variantsAttributesColorMonturaEs;
	}
	
	public VariantsAttributes getVariantsAttributesGamaColorMonturaEs() {
		return variantsAttributesGamaColorMonturaEs;
	}

	public void setVariantsAttributesGamaColorMonturaEs(VariantsAttributes variantsAttributesGamaColorMonturaEs) {
		this.variantsAttributesGamaColorMonturaEs = variantsAttributesGamaColorMonturaEs;
	}

	public VariantsAttributes getVariantsAttributesTargetEs() {
		return variantsAttributesTargetEs;
	}

	public void setVariantsAttributesTargetEs(VariantsAttributes variantsAttributesTargetEs) {
		this.variantsAttributesTargetEs = variantsAttributesTargetEs;
	}

	public VariantsAttributes getVariantsAttributesMaterial() {
		return variantsAttributesMaterial;
	}

	public void setVariantsAttributesMaterial(VariantsAttributes variantsAttributesMaterial) {
		this.variantsAttributesMaterial = variantsAttributesMaterial;
	}

	public VariantsAttributes getVariantsAttributesFamiliaEkon() {
		return variantsAttributesFamiliaEkon;
	}

	public void setVariantsAttributesFamiliaEkon(VariantsAttributes variantsAttributesFamiliaEkon) {
		this.variantsAttributesFamiliaEkon = variantsAttributesFamiliaEkon;
	}

	public VariantsAttributes getVariantsAttributesTipoProductoEs() {
		return variantsAttributesTipoProductoEs;
	}

	public void setVariantsAttributesTipoProductoEs(VariantsAttributes variantsAttributesTipoProductoEs) {
		this.variantsAttributesTipoProductoEs = variantsAttributesTipoProductoEs;
	}

	public VariantsAttributes getVariantsAttributesColeccionEs() {
		return variantsAttributesColeccionEs;
	}

	public void setVariantsAttributesColeccionEs(VariantsAttributes variantsAttributesColeccionEs) {
		this.variantsAttributesColeccionEs = variantsAttributesColeccionEs;
	}

	public VariantsAttributes getVariantsAttributesDimensionesLargoVarilla() {
		return variantsAttributesDimensionesLargoVarilla;
	}

	public void setVariantsAttributesDimensionesLargoVarilla(VariantsAttributes variantsAttributesDimensionesLargoVarilla) {
		this.variantsAttributesDimensionesLargoVarilla = variantsAttributesDimensionesLargoVarilla;
	}

	public VariantsAttributes getVariantsAttributesMarca() {
		return variantsAttributesMarca;
	}

	public void setVariantsAttributesMarca(VariantsAttributes variantsAttributesMarca) {
		this.variantsAttributesMarca = variantsAttributesMarca;
	}

	public VariantsAttributes getVariantsAttributesDimensionesAnchoOjo() {
		return variantsAttributesDimensionesAnchoOjo;
	}

	public void setVariantsAttributesDimensionesAnchoOjo(VariantsAttributes variantsAttributesDimensionesAnchoOjo) {
		this.variantsAttributesDimensionesAnchoOjo = variantsAttributesDimensionesAnchoOjo;
	}

	public VariantsAttributes getVariantsAttributesStatusEkon() {
		return variantsAttributesStatusEkon;
	}

	public void setVariantsAttributesStatusEkon(VariantsAttributes variantsAttributesStatusEkon) {
		this.variantsAttributesStatusEkon = variantsAttributesStatusEkon;
	}
	
	public VariantsAttributes getVariantsAttributesPruebaVirtual() {
		return variantsAttributesPruebaVirtual;
	}

	public void setVariantsAttributesPruebaVirtual(VariantsAttributes variantsAttributesPruebaVirtual) {
		this.variantsAttributesPruebaVirtual = variantsAttributesPruebaVirtual;
	}

	public VariantsAttributes getVariantsAttributesLineaNegocio() {
		return variantsAttributesLineaNegocio;
	}

	public void setVariantsAttributesLineaNegocio(VariantsAttributes variantsAttributesLineaNegocio) {
		this.variantsAttributesLineaNegocio = variantsAttributesLineaNegocio;
	}

	public VariantsAttributes getVariantsAttributesTipoPack() {
		return variantsAttributesTipoPack;
	}

	public void setVariantsAttributesTipoPack(VariantsAttributes variantsAttributesTipoPack) {
		this.variantsAttributesTipoPack = variantsAttributesTipoPack;
	}

	public VariantsAttributes getVariantsAttributesTienePromociones() {
		return variantsAttributesTienePromociones;
	}

	public void setVariantsAttributesTienePromociones(VariantsAttributes variantsAttributesTienePromociones) {
		this.variantsAttributesTienePromociones = variantsAttributesTienePromociones;
	}

	public VariantsAttributes getVariantsAttributesSubTipoProducto() {
		return variantsAttributesSubTipoProducto;
	}

	public void setVariantsAttributesSubTipoProducto(VariantsAttributes variantsAttributesSubTipoProducto) {
		this.variantsAttributesSubTipoProducto = variantsAttributesSubTipoProducto;
	}
	
	public VariantsAttributes getVariantsAttributesFormatosAudio() {
		return variantsAttributesFormatosAudio;
	}

	public void setVariantsAttributesFormatosAudio(VariantsAttributes variantsAttributesFormatosAudio) {
		this.variantsAttributesFormatosAudio = variantsAttributesFormatosAudio;
	}
	
}
