package com.magnolia.cione.dto;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.commercetools.api.models.common.Image;
import com.magnolia.cione.beans.GiftProduct;

/**
 * @author maria
 *
 */
public class VariantDTO {
	
	private String name;
	private String codigoCentral;
	private String codigoColor;
	private String gamaColorMontura;
	private String colorMontura;
	private String colorIcono;
	private String coleccion;
	private String composicion;
	private boolean comprable;
	private boolean isMaster;
	private String nombreArticulo;
	private String marca;
	private String modelo;
	private String statusEkon;
	private String ean;
	private String sku;
	private String skuPadre;
	private String dimensiones_ancho_ojo;
	private String dimensiones_largo_varilla;
	private String dimensiones_ancho_puente;
	private String graduacion;
	private String material;
	private String tipoCristal;
	private String tipoMontaje;
	private String tamanios;
	private Integer plazoEntregaProveedor;
	private boolean progresiva;
	private String pvpRecomendado;
	private String target;
	private String gruposPrecio;
	private boolean tienePromociones;
	private boolean tieneRecargo;
	private String tipoProducto;
	private String tipoPromocion;
	private String familiaProducto;
	private String familiaEkon;
	private List<DetalleDTO> mensajesEspecificos;
	private String nivel1;
	private String nivel2;
	private Integer homeSN;
	private Integer orderField;
	private String aliasEkon;
	private boolean gestionStock;
	private Image imagencentral;
	private List<Image> listImages;
	private String pvo;	
	private String pvoDto;
	private String pvoIncremento;
	private String logoMarca;
	private String valorDescuento;
	private List<Image> imagesVarians;
	private List<ColorDTO> colors;
	private List<ColorDTO> tamaniosLiquidos;
	private float stock;
	private String descripcion;
	private Image masterImage;
	private List<PromocionesDTO> listPromos;
	private boolean exist;
	private String productId;
	private Integer variantId;
	private boolean repuesto;
	private List<String> variantsSku;

	private List<String> diseno;
	private String esferafrom;
	private String esferato;
	private String esferastep;
	private String esferafrom2;
	private String esferato2;
	private String esferastep2;
	private String cilindrofrom;
	private String cilindroto;
	private String cilindrostep;
	private String diametrofrom;
	private String diametroto;
	private String diametrostep;
	private String curvabasefrom;
	private String curvabaseto;
	private String curvabasestep;
	private String ejefrom;
	private String ejeto;
	private String ejestep;
	private String colorlentemask;
	private List<String> adicion;
	private List<String> colorlente;
	private List<String> coloraudifonos;
	private List<String> colorCodo;
	private Map<String,String> auriculares;
	private Map<String,String> acopladores;
	private Map<String,String> cargadores;
	private Map<String,String> accesorios;
	private Map<String,String> garantia;
	private Map<String,String> tubosFinos;
	private Map<String,String> sujecionesDeportivas;
	private Map<String,String> filtros;
	private boolean deposito;
	private String esfera1mask;
	private String esfera2mask;
	private String cilindromask;
	private String ejemask;
	private String diametromask;
	private String curvaBasemask;
	private String skumask;
	private String reemplazo;
	private String geometria;
	private List<String> formatos;
	private String formato;
	private String materialBase;
	private String materialDetalle;
	private boolean isContactLab;
	private String contenidoAgua;
	private String hidratacion;
	private String bproteccionSolar;
	private String dkt;
	private String equivProveedor;
	private String equivProducto;
	private Boolean config;
	private List<String> esferaListConfig;
	private List<String> cilindroListConfig;
	private List<String> ejeListConfig;
	private List<String> diametroListConfig;
	private List<String> curvaBaseListConfig;
	private List<String> adicionListConfig;
	private List<String> disenoListConfig;
	private boolean hasSubstitute;
	private Map<String, String> substitutiveReplacement;
	private String segmento;
	private Map<String,String> colorAudifonoAudioLab;
	private Map<String,String> colorCodoAudioLab;
	private Map<String,String> colorPlatoAudioLab;
	private List<String> powerList;
	private boolean direccionalidad;
	private boolean conectividad;
	private boolean mediaconcha;
	private String tamanoPila;
	private List<String> longitudCanal;
	private boolean telebobina;
	private List<String> filtroCerumen;
	private boolean pulsador;
	private boolean controlVolumen;
	private boolean hiloExtractor;
	private boolean tinnitus;
	private boolean venting;
	private List<String> tipoVenting;
	private List<String> modVenting;
	private boolean ofertaFlash;
	private boolean liquidacion;

	private boolean pertenecePack;
	private List<MasterProductFrontDTO> packsContienenProducto;
	private List<VariantDTO> contenidoPack;
	private Integer id_Pack;
	private String codigoPack;
	private String skuPack;
	private String pvoPackUD;
	private Integer unidadesPack;
	private String codigo_centralPack;
	private String tipoProductoPack;
	private boolean masterPack;	
	private List<AgrupadorDTO> contenidoPackListGenerico;
	private InfoPackGenericoDTO infoPack;
	private String tipoPrecioPack;
	private String pvoPack;
	private String step;

	//SKU del producto Pack (usado en packs contactologia)
	private String skupackPadre;
	//SKU del producto seleccionado tras la navegacion
	private String skuPackMasterProduct;
	//sku del producto pack para no perderlo en la navegacion
	private String skuPackMaster;

	private String tipoRegalo;
	//Necesario para en el front poder discriminar los productos de regalo al mostrar la informacion de los productos relacionados
	private Map<String,GiftProduct> listadoProductosRegalo; 
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, String> getTubosFinos() {
		return tubosFinos;
	}
	public void setTubosFinos(Map<String, String> tubosFinos) {
		this.tubosFinos = tubosFinos;
	}
	public Map<String, String> getSujecionesDeportivas() {
		return sujecionesDeportivas;
	}
	public void setSujecionesDeportivas(Map<String, String> sujecionesDeportivas) {
		this.sujecionesDeportivas = sujecionesDeportivas;
	}
	public Map<String, String> getFiltros() {
		return filtros;
	}
	public void setFiltros(Map<String, String> filtros) {
		this.filtros = filtros;
	}
	public List<String> getColoraudifonos() {
		return coloraudifonos;
	}
	public void setColoraudifonos(List<String> coloraudifonos) {
		this.coloraudifonos = coloraudifonos;
	}
	public List<String> getColorCodo() {
		return colorCodo;
	}
	public void setColorCodo(List<String> colorCodo) {
		this.colorCodo = colorCodo;
	}
	public Map<String, String> getAuriculares() {
		return auriculares;
	}
	public void setAuriculares(Map<String, String> auriculares) {
		this.auriculares = auriculares;
	}
	public Map<String, String> getAcopladores() {
		return acopladores;
	}
	public void setAcopladores(Map<String, String> acopladores) {
		this.acopladores = acopladores;
	}
	public Map<String, String> getCargadores() {
		return cargadores;
	}
	public void setCargadores(Map<String, String> cargadores) {
		this.cargadores = cargadores;
	}
	public Map<String, String> getAccesorios() {
		return accesorios;
	}
	public void setAccesorios(Map<String, String> accesorios) {
		this.accesorios = accesorios;
	}
	public boolean getDeposito() {
		return deposito;
	}
	public void setDeposito(boolean deposito) {
		this.deposito = deposito;
	}
	public Map<String, String> getGarantia() {
		return garantia;
	}
	public void setGarantia(Map<String, String> garantia) {
		this.garantia = garantia;
	}
	public List<String> getDisenoListConfig() {
		return disenoListConfig;
	}
	public void setDisenoListConfig(List<String> disenoListConfig) {
		this.disenoListConfig = disenoListConfig;
	}
	public boolean isHasSubstitute() {
		return hasSubstitute;
	}
	public void setHasSubstitute(boolean hasSubstitute) {
		this.hasSubstitute = hasSubstitute;
	}
	public String getColorlentemask() {
		return colorlentemask;
	}
	public void setColorlentemask(String colorlentemask) {
		this.colorlentemask = colorlentemask;
	}
	public List<String> getAdicionListConfig() {
		return adicionListConfig;
	}
	public void setAdicionListConfig(List<String> adicionListConfig) {
		this.adicionListConfig = adicionListConfig;
	}
	public List<String> getCurvaBaseListConfig() {
		return curvaBaseListConfig;
	}
	public void setCurvaBaseListConfig(List<String> curvaBaseListConfig) {
		this.curvaBaseListConfig = curvaBaseListConfig;
	}
	public List<String> getDiametroListConfig() {
		return diametroListConfig;
	}
	public void setDiametroListConfig(List<String> diametroListConfig) {
		this.diametroListConfig = diametroListConfig;
	}
	public List<String> getEjeListConfig() {
		return ejeListConfig;
	}
	public void setEjeListConfig(List<String> ejeListConfig) {
		this.ejeListConfig = ejeListConfig;
	}
	public List<String> getCilindroListConfig() {
		return cilindroListConfig;
	}
	public void setCilindroListConfig(List<String> cilindroListConfig) {
		this.cilindroListConfig = cilindroListConfig;
	}
	public Boolean getConfig() {
		return config;
	}
	public void setConfig(Boolean config) {
		this.config = config;
	}
	public List<String> getEsferaListConfig() {
		return esferaListConfig;
	}
	public void setEsferaListConfig(List<String> esferaListConfig) {
		this.esferaListConfig = esferaListConfig;
	}

	private String urlPdf;
	private String urlVideo;
	
	public String getReemplazo() {
		return reemplazo;
	}
	public void setReemplazo(String reemplazo) {
		this.reemplazo = reemplazo;
	}
	public String getGeometria() {
		return geometria;
	}
	public void setGeometria(String geometria) {
		this.geometria = geometria;
	}
	public List<String> getFormatos() {
		return formatos;
	}
	public void setFormatos(List<String> formatos) {
		this.formatos = formatos;
	}
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
	public String getMaterialBase() {
		return materialBase;
	}
	public void setMaterialBase(String materialBase) {
		this.materialBase = materialBase;
	}
	public boolean getIsContactLab() {
		return isContactLab;
	}
	public void setIsContactLab(boolean isContactLab) {
		this.isContactLab = isContactLab;
	}
	public String getHidratacion() {
		return hidratacion;
	}
	public void setHidratacion(String hidratacion) {
		this.hidratacion = hidratacion;
	}
	public String getBproteccionSolar() {
		return bproteccionSolar;
	}
	public void setBproteccionSolar(String bproteccionSolar) {
		this.bproteccionSolar = bproteccionSolar;
	}
	public String getDkt() {
		return dkt;
	}
	public void setDkt(String dkt) {
		this.dkt = dkt;
	}
	public String getEquivProveedor() {
		return equivProveedor;
	}
	public void setEquivProveedor(String equivProveedor) {
		this.equivProveedor = equivProveedor;
	}
	public String getEquivProducto() {
		return equivProducto;
	}
	public void setEquivProducto(String equivProducto) {
		this.equivProducto = equivProducto;
	}
	public List<String> getColorlente() {
		return colorlente;
	}
	public void setColorlente(List<String> colorlente) {
		this.colorlente = colorlente;
	}
	public String getEsfera1mask() {
		return esfera1mask;
	}
	public void setEsfera1mask(String esfera1mask) {
		this.esfera1mask = esfera1mask;
	}
	public String getEsfera2mask() {
		return esfera2mask;
	}
	public void setEsfera2mask(String esfera2mask) {
		this.esfera2mask = esfera2mask;
	}
	public String getCilindromask() {
		return cilindromask;
	}
	public void setCilindromask(String cilindromask) {
		this.cilindromask = cilindromask;
	}
	public String getEjemask() {
		return ejemask;
	}
	public void setEjemask(String ejemask) {
		this.ejemask = ejemask;
	}
	public String getDiametromask() {
		return diametromask;
	}
	public void setDiametromask(String diametromask) {
		this.diametromask = diametromask;
	}
	public String getCurvaBasemask() {
		return curvaBasemask;
	}
	public void setCurvaBasemask(String curvaBasemask) {
		this.curvaBasemask = curvaBasemask;
	}
	public String getSkumask() {
		return skumask;
	}
	public void setSkumask(String skumask) {
		this.skumask = skumask;
	}
	public String getEjefrom() {
		return ejefrom;
	}
	public void setEjefrom(String ejefrom) {
		this.ejefrom = ejefrom;
	}
	public String getEjeto() {
		return ejeto;
	}
	public void setEjeto(String ejeto) {
		this.ejeto = ejeto;
	}
	public String getEjestep() {
		return ejestep;
	}
	public void setEjestep(String ejestep) {
		this.ejestep = ejestep;
	}
	public List<String> getAdicion() {
		
		if (Boolean.TRUE.equals(this.config)) {
			return this.adicionListConfig;
		}else {
			return adicion;
		}
	}
	public void setAdicion(List<String> adicion) {
		this.adicion = adicion;
	}
	public String getCurvabasefrom() {
		return curvabasefrom;
	}
	public void setCurvabasefrom(String curvabasefrom) {
		this.curvabasefrom = curvabasefrom;
	}
	public String getCurvabaseto() {
		return curvabaseto;
	}
	public void setCurvabaseto(String curvabaseto) {
		this.curvabaseto = curvabaseto;
	}
	public String getCurvabasestep() {
		return curvabasestep;
	}
	public void setCurvabasestep(String curvabasestep) {
		this.curvabasestep = curvabasestep;
	}
	public String getEsferafrom2() {
		return esferafrom2;
	}
	public void setEsferafrom2(String esferafrom2) {
		this.esferafrom2 = esferafrom2;
	}
	public String getEsferato2() {
		return esferato2;
	}
	public void setEsferato2(String esferato2) {
		this.esferato2 = esferato2;
	}
	public String getEsferastep2() {
		return esferastep2;
	}
	public void setEsferastep2(String esferastep2) {
		this.esferastep2 = esferastep2;
	}
	public String getDiametrofrom() {
		return diametrofrom;
	}
	public void setDiametrofrom(String diametrofrom) {
		this.diametrofrom = diametrofrom;
	}
	public String getDiametroto() {
		return diametroto;
	}
	public void setDiametroto(String diametroto) {
		this.diametroto = diametroto;
	}
	public String getDiametrostep() {
		return diametrostep;
	}
	public void setDiametrostep(String diametrostep) {
		this.diametrostep = diametrostep;
	}
	public String getCilindrofrom() {
		return cilindrofrom;
	}
	public void setCilindrofrom(String cilindrofrom) {
		this.cilindrofrom = cilindrofrom;
	}
	public String getCilindroto() {
		return cilindroto;
	}
	public void setCilindroto(String cilindroto) {
		this.cilindroto = cilindroto;
	}
	public String getCilindrostep() {
		return cilindrostep;
	}
	public void setCilindrostep(String cilindrostep) {
		this.cilindrostep = cilindrostep;
	}
	public List<String> getDiseno() {
		if (Boolean.TRUE.equals(this.config)) {
			return this.disenoListConfig;
		}else {
			return diseno;
		}
	}
	public void setDiseno(List<String> diseno) {
		this.diseno = diseno;
	}
	public String getEsferafrom() {
		return esferafrom;
	}
	public void setEsferafrom(String esferafrom) {
		this.esferafrom = esferafrom;
	}
	public String getEsferato() {
		return esferato;
	}
	public void setEsferato(String esferato) {
		this.esferato = esferato;
	}
	public String getEsferastep() {
		return esferastep;
	}
	public void setEsferastep(String esferastep) {
		this.esferastep = esferastep;
	}
	public String getCodigoCentral() {
		return codigoCentral;
	}
	public void setCodigoCentral(String codigoCentral) {
		this.codigoCentral = codigoCentral;
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
	public String getColorIcono() {
		return colorIcono;
	}
	public void setColorIcono(String colorIcono) {
		this.colorIcono = colorIcono;
	}
	public String getColeccion() {
		return coleccion;
	}
	public void setColeccion(String coleccion) {
		this.coleccion = coleccion;
	}
	public String getComposicion() {
		return composicion;
	}
	public void setComposicion(String composicion) {
		this.composicion = composicion;
	}
	public boolean isComprable() {
		return comprable;
	}
	public void setComprable(boolean comprable) {
		this.comprable = comprable;
	}
	public boolean isMaster() {
		return isMaster;
	}
	public void setMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}
	public String getNombreArticulo() {
		return nombreArticulo;
	}
	public void setNombreArticulo(String nombreArticulo) {
		this.nombreArticulo = nombreArticulo;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getStatusEkon() {
		return statusEkon;
	}
	public void setStatusEkon(String statusEkon) {
		this.statusEkon = statusEkon;
	}
	public String getEan() {
		return ean;
	}
	public void setEan(String ean) {
		this.ean = ean;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getSkuPadre() {
		return skuPadre;
	}
	public void setSkuPadre(String skuPadre) {
		this.skuPadre = skuPadre;
	}
	public String getGamaColorMontura() {
		return gamaColorMontura;
	}
	public void setGamaColorMontura(String gamaColorMontura) {
		this.gamaColorMontura = gamaColorMontura;
	}
	public String getDimensiones_ancho_ojo() {
		return dimensiones_ancho_ojo;
	}
	public void setDimensiones_ancho_ojo(String dimensiones_ancho_ojo) {
		this.dimensiones_ancho_ojo = dimensiones_ancho_ojo;
	}
	public String getDimensiones_largo_varilla() {
		return dimensiones_largo_varilla;
	}
	public void setDimensiones_largo_varilla(String dimensiones_largo_varilla) {
		this.dimensiones_largo_varilla = dimensiones_largo_varilla;
	}
	public String getDimensiones_ancho_puente() {
		return dimensiones_ancho_puente;
	}
	public void setDimensiones_ancho_puente(String dimensiones_ancho_puente) {
		this.dimensiones_ancho_puente = dimensiones_ancho_puente;
	}
	public String getGraduacion() {
		return graduacion;
	}
	public void setGraduacion(String graduacion) {
		this.graduacion = graduacion;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getTipoCristal() {
		return tipoCristal;
	}
	public void setTipoCristal(String tipoCristal) {
		this.tipoCristal = tipoCristal;
	}
	public String getTipoMontaje() {
		return tipoMontaje;
	}
	public void setTipoMontaje(String tipoMontaje) {
		this.tipoMontaje = tipoMontaje;
	}
	public String getTamanios() {
		return tamanios;
	}
	public void setTamanios(String tamanios) {
		this.tamanios = tamanios;
	}
	public Integer getPlazoEntregaProveedor() {
		return plazoEntregaProveedor;
	}
	public void setPlazoEntregaProveedor(Integer plazoEntregaProveedor) {
		this.plazoEntregaProveedor = plazoEntregaProveedor;
	}
	public boolean isProgresiva() {
		return progresiva;
	}
	public void setProgresiva(boolean progresiva) {
		this.progresiva = progresiva;
	}
	public String getPvpRecomendado() {
		return pvpRecomendado;
	}
	public void setPvpRecomendado(String pvpRecomendado) {
		this.pvpRecomendado = pvpRecomendado;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getGruposPrecio() {
		return gruposPrecio;
	}
	public void setGruposPrecio(String gruposPrecio) {
		this.gruposPrecio = gruposPrecio;
	}
	public boolean isTienePromociones() {
		return tienePromociones;
	}
	public void setTienePromociones(boolean tienePromociones) {
		this.tienePromociones = tienePromociones;
	}
	public boolean isTieneRecargo() {
		return tieneRecargo;
	}
	public void setTieneRecargo(boolean tieneRecargo) {
		this.tieneRecargo = tieneRecargo;
	}
	public String getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	public String getTipoPromocion() {
		return tipoPromocion;
	}
	public void setTipoPromocion(String tipoPromocion) {
		this.tipoPromocion = tipoPromocion;
	}
	public List<DetalleDTO> getMensajesEspecificos() {
		return mensajesEspecificos;
	}
	public void setMensajesEspecificos(List<DetalleDTO> mensajesEspecificos) {
		this.mensajesEspecificos = mensajesEspecificos;
	}
	public String getFamiliaProducto() {
		return familiaProducto;
	}
	public void setFamiliaProducto(String familiaProducto) {
		this.familiaProducto = familiaProducto;
	}
	public String getFamiliaEkon() {
		return familiaEkon;
	}
	public void setFamiliaEkon(String familiaEkon) {
		this.familiaEkon = familiaEkon;
	}
	public String getNivel1() {
		return nivel1;
	}
	public void setNivel1(String nivel1) {
		this.nivel1 = nivel1;
	}
	public String getNivel2() {
		return nivel2;
	}
	public void setNivel2(String nivel2) {
		this.nivel2 = nivel2;
	}
	public Integer getHomeSN() {
		return homeSN;
	}
	public void setHomeSN(Integer homeSN) {
		this.homeSN = homeSN;
	}
	public Integer getOrderField() {
		return orderField;
	}
	public void setOrderField(Integer orderField) {
		this.orderField = orderField;
	}
	public String getAliasEkon() {
		return aliasEkon;
	}
	public void setAliasEkon(String aliasEkon) {
		this.aliasEkon = aliasEkon;
	}
	public boolean isGestionStock() {
		return gestionStock;
	}
	public void setGestionStock(boolean gestionStock) {
		this.gestionStock = gestionStock;
	}
	public Image getImagencentral() {
		return imagencentral;
	}
	public void setImagencentral(Image imagencentral) {
		this.imagencentral = imagencentral;
	}
	public List<Image> getListImages() {
		return listImages;
	}
	public void setListImages(List<Image> listImages) {
		this.listImages = listImages;
	}
	public String getPvo() {
		return pvo;
	}
	public void setPvo(String pvo) {
		this.pvo = pvo;
	}
	public String getPvoDto() {
		return pvoDto;
	}
	public void setPvoDto(String pvoDto) {
		this.pvoDto = pvoDto;
	}
	public String getPvoIncremento() {
		return pvoIncremento;
	}
	public void setPvoIncremento(String pvoIncremento) {
		this.pvoIncremento = pvoIncremento;
	}
	public String getLogoMarca() {
		return logoMarca;
	}
	public void setLogoMarca(String logoMarca) {
		this.logoMarca = logoMarca;
	}
	public String getValorDescuento() {
		return valorDescuento;
	}
	public void setValorDescuento(String valorDescuento) {
		this.valorDescuento = valorDescuento;
	}
	public List<Image> getImagesVarians() {
		return imagesVarians;
	}
	public void setImagesVarians(List<Image> imagesVarians) {
		this.imagesVarians = imagesVarians;
	}
	public List<ColorDTO> getColors() {
		return colors;
	}
	public void setColors(List<ColorDTO> colors) {
		this.colors = colors;
	}
	public List<ColorDTO> getTamaniosLiquidos() {
		return tamaniosLiquidos;
	}
	public void setTamaniosLiquidos(List<ColorDTO> tamaniosLiquidos) {
		this.tamaniosLiquidos = tamaniosLiquidos;
	}
	public float getStock() {
		return stock;
	}
	public void setStock(float stock) {
		this.stock = stock;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Image getMasterImage() {
		return masterImage;
	}
	public void setMasterImage(Image masterImage) {
		this.masterImage = masterImage;
	}
	public List<PromocionesDTO> getListPromos() {
		return listPromos;
	}
	public void setListPromos(List<PromocionesDTO> listPromos) {
		this.listPromos = listPromos;
	}
	public boolean isExist() {
		return exist;
	}
	public void setExist(boolean exist) {
		this.exist = exist;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getVariantId() {
		return variantId;
	}
	public void setVariantId(Integer variantId) {
		this.variantId = variantId;
	}
	public boolean isRepuesto() {
		return repuesto;
	}
	public void setRepuesto(boolean repuesto) {
		this.repuesto = repuesto;
	}	
	public List<String> getVariantsSku() {
		return variantsSku;
	}
	public void setVariantsSku(List<String> variantsSku) {
		this.variantsSku = variantsSku;
	}
	
	public String getUrlPdf() {
		return urlPdf;
	}
	public void setUrlPdf(String urlPdf) {
		this.urlPdf = urlPdf;
	}
	public String getUrlVideo() {
		return urlVideo;
	}
	public void setUrlVideo(String urlVideo) {
		this.urlVideo = urlVideo;
	}
	public Map<String, String> getSubstitutiveReplacement() {
		return substitutiveReplacement;
	}
	public void setSubstitutiveReplacement(Map<String, String> substitutiveReplacement) {
		this.substitutiveReplacement = substitutiveReplacement;
	}
	public String getSegmento() {
		return segmento;
	}
	public void setSegmento(String segmento) {
		this.segmento = segmento;
	}
	
	public Map<String, String> getColorAudifonoAudioLab() {
		return colorAudifonoAudioLab;
	}
	public void setColorAudifonoAudioLab(Map<String, String> colorAudifonoAudioLab) {
		this.colorAudifonoAudioLab = colorAudifonoAudioLab;
	}
	public Map<String, String> getColorCodoAudioLab() {
		return colorCodoAudioLab;
	}
	public void setColorCodoAudioLab(Map<String, String> colorCodoAudioLab) {
		this.colorCodoAudioLab = colorCodoAudioLab;
	}
	public Map<String, String> getColorPlatoAudioLab() {
		return colorPlatoAudioLab;
	}
	public void setColorPlatoAudioLab(Map<String, String> colorPlatoAudioLab) {
		this.colorPlatoAudioLab = colorPlatoAudioLab;
	}
	public List<String> getPowerList() {
		return powerList;
	}
	public void setPowerList(List<String> powerList) {
		this.powerList = powerList;
	}
	public boolean isDireccionalidad() {
		return direccionalidad;
	}
	public void setDireccionalidad(boolean direccionalidad) {
		this.direccionalidad = direccionalidad;
	}
	public boolean isConectividad() {
		return conectividad;
	}
	public void setConectividad(boolean conectividad) {
		this.conectividad = conectividad;
	}
	public boolean isMediaconcha() {
		return mediaconcha;
	}
	public void setMediaconcha(boolean mediaconcha) {
		this.mediaconcha = mediaconcha;
	}
	public String getTamanoPila() {
		return tamanoPila;
	}
	public void setTamanoPila(String tamanoPila) {
		this.tamanoPila = tamanoPila;
	}
	public List<String> getLongitudCanal() {
		return longitudCanal;
	}
	public void setLongitudCanal(List<String> longitudCanal) {
		this.longitudCanal = longitudCanal;
	}
	public boolean isTelebobina() {
		return telebobina;
	}
	public void setTelebobina(boolean telebobina) {
		this.telebobina = telebobina;
	}
	public List<String> getFiltroCerumen() {
		return filtroCerumen;
	}
	public void setFiltroCerumen(List<String> filtroCerumen) {
		this.filtroCerumen = filtroCerumen;
	}
	public boolean isPulsador() {
		return pulsador;
	}
	public void setPulsador(boolean pulsador) {
		this.pulsador = pulsador;
	}
	public boolean isControlVolumen() {
		return controlVolumen;
	}
	public void setControlVolumen(boolean controlVolumen) {
		this.controlVolumen = controlVolumen;
	}
	public boolean isHiloExtractor() {
		return hiloExtractor;
	}
	public void setHiloExtractor(boolean hiloExtractor) {
		this.hiloExtractor = hiloExtractor;
	}
	public boolean isTinnitus() {
		return tinnitus;
	}
	public void setTinnitus(boolean tinnitus) {
		this.tinnitus = tinnitus;
	}
	public boolean isVenting() {
		return venting;
	}
	public void setVenting(boolean venting) {
		this.venting = venting;
	}
	public List<String> getTipoVenting() {
		return tipoVenting;
	}
	public void setTipoVenting(List<String> tipoVenting) {
		this.tipoVenting = tipoVenting;
	}
	public List<String> getModVenting() {
		return modVenting;
	}
	public void setModVenting(List<String> modVenting) {
		this.modVenting = modVenting;
	}
	public boolean isPertenecePack() {
		return pertenecePack;
	}
	public void setPertenecePack(boolean pertenecePack) {
		this.pertenecePack = pertenecePack;
	}
	public List<MasterProductFrontDTO> getPacksContienenProducto() {
		return packsContienenProducto;
	}
	public void setPacksContienenProducto(List<MasterProductFrontDTO> packsContienenProducto) {
		this.packsContienenProducto = packsContienenProducto;
	}
	public String getCodigoPack() {
		return codigoPack;
	}
	public void setCodigoPack(String codigoPack) {
		this.codigoPack = codigoPack;
	}
	public List<VariantDTO> getContenidoPack() {
		return contenidoPack;
	}
	public void setContenidoPack(List<VariantDTO> contenidoPack) {
		this.contenidoPack = contenidoPack;
	}
	public Integer getId_Pack() {
		return id_Pack;
	}
	public void setId_Pack(Integer id_Pack) {
		this.id_Pack = id_Pack;
	}
	public String getSkuPack() {
		return skuPack;
	}
	public void setSkuPack(String skuPack) {
		this.skuPack = skuPack;
	}
	public String getPvoPackUD() {
		return pvoPackUD;
	}
	public void setPvoPackUD(String pvoPackUD) {
		this.pvoPackUD = pvoPackUD;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public Integer getUnidadesPack() {
		return unidadesPack;
	}
	public void setUnidadesPack(Integer unidadesPack) {
		this.unidadesPack = unidadesPack;
	}
	public String getCodigo_centralPack() {
		return codigo_centralPack;
	}
	public void setCodigo_centralPack(String codigo_centralPack) {
		this.codigo_centralPack = codigo_centralPack;
	}
	public String getTipoProductoPack() {
		return tipoProductoPack;
	}
	public void setTipoProductoPack(String tipoProductoPack) {
		this.tipoProductoPack = tipoProductoPack;
	}
	public String getMaterialDetalle() {
		return materialDetalle;
	}
	public void setMaterialDetalle(String materialDetalle) {
		this.materialDetalle = materialDetalle;
	}
	public String getContenidoAgua() {
		return contenidoAgua;
	}
	public void setContenidoAgua(String contenidoAgua) {
		this.contenidoAgua = contenidoAgua;
	}
	public boolean isMasterPack() {
		return masterPack;
	}
	public void setMasterPack(boolean masterPack) {
		this.masterPack = masterPack;
	}
	public List<AgrupadorDTO> getContenidoPackListGenerico() {
		return contenidoPackListGenerico;
	}
	public void setContenidoPackListGenerico(List<AgrupadorDTO> contenidoPackListGenerico) {
		this.contenidoPackListGenerico = contenidoPackListGenerico;
	}
	public InfoPackGenericoDTO getInfoPack() {
		return infoPack;
	}
	public void setInfoPack(InfoPackGenericoDTO infoPack) {
		this.infoPack = infoPack;
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
	public String getSkupackPadre() {
		return skupackPadre;
	}
	public void setSkupackPadre(String skupackPadre) {
		this.skupackPadre = skupackPadre;
	}
	public String getSkuPackMasterProduct() {
		return skuPackMasterProduct;
	}
	public void setSkuPackMasterProduct(String skuPackMasterProduct) {
		this.skuPackMasterProduct = skuPackMasterProduct;
	}
	public String getSkuPackMaster() {
		return skuPackMaster;
	}
	public void setSkuPackMaster(String skuPackMaster) {
		this.skuPackMaster = skuPackMaster;
	}
	public String getTipoRegalo() {
		return tipoRegalo;
	}
	public void setTipoRegalo(String tipoRegalo) {
		this.tipoRegalo = tipoRegalo;
	}
	public Map<String, GiftProduct> getListadoProductosRegalo() {
		return listadoProductosRegalo;
	}
	public void setListadoProductosRegalo(Map<String, GiftProduct> listadoProductosRegalo) {
		this.listadoProductosRegalo = listadoProductosRegalo;
	}
	
	public List<String> getEsferaList(){
		
		if (Boolean.TRUE.equals(this.config)) {
			return this.esferaListConfig;
		}else {
			if (esferafrom!=null && !esferafrom.isEmpty() && 
				esferato!=null && !esferato.isEmpty() && 
				esferastep!=null && !esferastep.isEmpty()){
				
					if(esferafrom2!=null && !esferafrom2.isEmpty() && 
						 esferato2!=null && !esferato2.isEmpty() && 
						 esferastep2!=null && !esferastep2.isEmpty()){
						
						List<String> esferalist = getListRangeAndStep(esferafrom, esferato, esferastep, esfera1mask,esferafrom2, esferato2, esferastep2, esfera2mask);
						
						// se recorren los elementos y si alguno es mayor que 0 o igual se le anade el simbolo +
						IntStream.range(0, esferalist.size())
						  .filter(index -> Float.valueOf(esferalist.get(index)) >= 0)
						  .forEach(index -> esferalist.set(index, "+" + esferalist.get(index)));
						
						return esferalist;

					}else {
						List<String> esferalist = getListRangeAndStep(esferafrom, esferato, esferastep, esfera1mask);
						
						// se recorren los elementos y si alguno es mayor que 0 o igual se le anade el simbolo +
						IntStream.range(0, esferalist.size())
						  .filter(index -> Float.valueOf(esferalist.get(index)) >= 0)
						  .forEach(index -> esferalist.set(index, "+" + esferalist.get(index)));
						
						return esferalist;

					}
				}
			
			return new ArrayList<>();
		}
	}
	
	public List<String> getCilindroList(){
		
		if (Boolean.TRUE.equals(this.config)) {
			return this.cilindroListConfig;
		}else {
			return getListRangeAndStep(cilindrofrom, cilindroto, cilindrostep, cilindromask);
		}
	}
	
	public List<String> getDiametroList(){
		
		if (Boolean.TRUE.equals(this.config)) {
			return this.diametroListConfig;
		}else {
			return getListRangeAndStep(diametrofrom, diametroto, diametrostep, diametromask);
		}
	}
	
	public List<String> getCurvaBaseList(){
		
		if (Boolean.TRUE.equals(this.config)) {
			return this.curvaBaseListConfig;
		}else {
			return getListRangeAndStep(curvabasefrom, curvabaseto, curvabasestep, curvaBasemask);
		}
		
	}
	
	public List<String> getEjeList(){
		
		if (Boolean.TRUE.equals(this.config)) {
			return this.ejeListConfig;
		}else {
			return getListRangeAndStep(ejefrom, ejeto, ejestep, ejemask);
		}
	}
	
	private String getStepString(float elem, String mask) {
		
		DecimalFormatSymbols decimalSymbols = DecimalFormatSymbols.getInstance();
	    decimalSymbols.setDecimalSeparator('.');
	    
	    // a partir de la mascara creamos el patron a mostrar
		String maskPattern = mask.replaceAll("S", "");
		maskPattern = maskPattern.replaceAll("D", "0");
		DecimalFormat df = new DecimalFormat(maskPattern, decimalSymbols);
		df.isDecimalSeparatorAlwaysShown();
		
		return df.format(elem);
		
	}
	
	private List<String> getListRangeAndStep(String from, String to, String step, String mask){
		
		List<String> res = new ArrayList<>();
		List<Float> resFloat = new ArrayList<>();
		
		try {
			if (from!=null && !from.isEmpty() && 
				to!=null && !to.isEmpty() && 
				step!=null && !step.isEmpty()){
				
				 float fromfloat = Float.parseFloat(from);
				 float tofloat = Float.parseFloat(to);
				 float stepfloat = Float.parseFloat(step);
				 
				 if (fromfloat != tofloat) {
					 
					for (float j=fromfloat; j<=tofloat; j=j+stepfloat) {
						 resFloat.add(j);
					}
					 
					List<Float> uniqueJoinList = transformList(resFloat);
					
					if(!uniqueJoinList.isEmpty()) {
						for (Float value : uniqueJoinList) {
							res.add(getStepString(value,mask));
						}
					}					 
					 
				 }else {
					 res.add(getStepString(fromfloat,mask));
				 }
			 }
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	private List<String> getListRangeAndStep(String from, String to, String step, String mask,
											 String fromSecond, String toSecond, String stepSecond, String maskSecond){
		
		List<String> res = new ArrayList<>();
		List<Float> resFloat = new ArrayList<>();
		
		try {
			if (from!=null && !from.isEmpty() && 
				to!=null && !to.isEmpty() && 
				step!=null && !step.isEmpty() &&
				fromSecond!=null && !fromSecond.isEmpty() && 
				toSecond!=null && !toSecond.isEmpty() && 
				stepSecond!=null && !stepSecond.isEmpty()){
				
				 float fromfloat = Float.parseFloat(from);
				 float tofloat = Float.parseFloat(to);
				 float stepfloat = Float.parseFloat(step);
				 
				 float fromfloatSecond = Float.parseFloat(fromSecond);
				 float tofloatSecond = Float.parseFloat(toSecond);
				 float stepfloatSecond = Float.parseFloat(stepSecond);
				 
				 if ((fromfloat != tofloat) && ((fromfloatSecond != tofloatSecond))) {
					 
					for (float j=fromfloat; j<=tofloat; j=j+stepfloat) {
						resFloat.add(j);
					}
					
					for (float j=fromfloatSecond ; j<=tofloatSecond ; j=j+stepfloatSecond ) {
						resFloat.add(j);
					}
					
					List<Float> uniqueJoinList = transformList(resFloat);
					
					if(!uniqueJoinList.isEmpty()) {
						for (Float value : uniqueJoinList) {
							res.add(getStepString(value,mask));
						}
					}					 
					 
				 }else {
					 res.add(getStepString(fromfloat,mask));
				 }
			 }
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	/*private List<Integer> getListIntegerRangeAndStep(String from, String to, String step, String mask){
		
		List<Integer> res = new ArrayList<>();
		
		if (from!=null && !from.isEmpty() && 
			to!=null && !to.isEmpty() && 
			step!=null && !step.isEmpty()){
			
			 int fromint = Integer.parseInt(from);
			 int toint = Integer.parseInt(to);
			 int stepint = Integer.parseInt(step);
			 
			 if (fromint != toint) {
				 for (int j=fromint; j<=toint; j=j+stepint) {
					 res.add(j);
				 }
			 }else {
				 res.add(fromint);
			 }
		 }
		
		return res;
	}*/
	
	private List<Float> transformList(List<Float> list){
		
		if (list != null && !list.isEmpty() && list.size() > 1) {
			
			// Dividimos en dos listas negativos y positivos para ordenar por
			// separado (en un orden absurdo los negativos (0, -0,25, - 0,5, -0,75...))
			List<Float> positives = list.stream().filter(i -> i >= 0).collect(Collectors.toList());
			List<Float> negatives = list.stream().filter(i -> i <= 0).collect(Collectors.toList());
			
			//eliminas los repetidos (Ologn)
			HashSet<Float> uniqueHasSetPositives = new HashSet<>(positives);
			HashSet<Float> uniqueHasSetNegatives = new HashSet<>(negatives);
			
			//Ordenas (Ologn)
			TreeSet<Float> uniqueTreeSetPositives = new TreeSet<>(uniqueHasSetPositives);
			TreeSet<Float> uniqueTreeSetNegatives = new TreeSet<>(uniqueHasSetNegatives);
			TreeSet<Float> uniqueTreeSetNegativesDes = new TreeSet<>(uniqueTreeSetNegatives.descendingSet());
			
			List<Float> uniqueJoinList = new ArrayList<>();
			
			if(!uniqueTreeSetNegativesDes.isEmpty()) {
			    for (Float value : uniqueTreeSetNegativesDes) {
			    	if(!uniqueJoinList.contains(value)) {
			    		uniqueJoinList.add(value);
			    	}
			    }
		    }
			
			if(!uniqueTreeSetPositives.isEmpty()) {
			    for (Float value : uniqueTreeSetPositives) {
			    	if(!uniqueJoinList.contains(value)) {
			    		uniqueJoinList.add(value);
			    	}
			    }
		    }
			
			return uniqueJoinList;
		}
		
		return list;
		
	}
	public boolean isOfertaFlash() {
		return ofertaFlash;
	}
	public void setOfertaFlash(boolean ofertaFlash) {
		this.ofertaFlash = ofertaFlash;
	}
	public boolean isLiquidacion() {
		return liquidacion;
	}
	public void setLiquidacion(boolean liquidacion) {
		this.liquidacion = liquidacion;
	}
	
}
