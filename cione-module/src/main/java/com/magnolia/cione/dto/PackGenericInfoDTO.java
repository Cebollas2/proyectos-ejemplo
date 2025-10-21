package com.magnolia.cione.dto;

import java.util.List;

import com.commercetools.api.models.common.Image;

public class PackGenericInfoDTO {
	private String gruposPrecio;
	private String sku;
	private String skuPack;
	private String skuPackMaster;
	private String refPack;
	private boolean exist;
	private boolean repuesto;
	private String logoMarca;
	private List<Image> listImages;
	private String productId;
	private Integer variantId;
	private Image masterImage;
	private String name;
	private String nombreArticulo;
	private String tipoProducto;
	private String familiaProducto;
	private String aliasEkon;
	private float stock;
	private Boolean config;
	private boolean tienePromociones;
	private boolean tieneRecargo;
	private String pvo;	
	private String pvoDto;
	private String pvpRecomendado;
	private String tipoPromocion;
	private boolean pertenecePack;
	private List<MasterProductFrontDTO> packsContienenProducto;
	private List<VariantDTO> contenidoPack;
	private Integer id_Pack;
	private String codigoPack;
	private String urlPdf;
	private String urlVideo;
	private String pvoPackUD;
	private Integer unidadesPack;
	private String codigo_centralPack;
	private String tipoProductoPack;
	private boolean masterPack;	
	private List<AgrupadorDTO> contenidoPackListGenerico;
	private InfoPackGenericoDTO infoPack;
	
	public PackGenericInfoDTO(String skuPackMaster, String refPack) {
		super();
		this.skuPackMaster = skuPackMaster;
		this.refPack = refPack;
	}
	
	public String getGruposPrecio() {
		return gruposPrecio;
	}
	public void setGruposPrecio(String gruposPrecio) {
		this.gruposPrecio = gruposPrecio;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getSkuPack() {
		return skuPack;
	}
	public void setSkuPack(String skuPack) {
		this.skuPack = skuPack;
	}
	public String getSkuPackMaster() {
		return skuPackMaster;
	}
	public void setSkuPackMaster(String skuPackMaster) {
		this.skuPackMaster = skuPackMaster;
	}
	public String getRefPack() {
		return refPack;
	}
	public void setRefPack(String refPack) {
		this.refPack = refPack;
	}
	public boolean isExist() {
		return exist;
	}
	public void setExist(boolean exist) {
		this.exist = exist;
	}
	public boolean isRepuesto() {
		return repuesto;
	}
	public void setRepuesto(boolean repuesto) {
		this.repuesto = repuesto;
	}
	public String getLogoMarca() {
		return logoMarca;
	}
	public void setLogoMarca(String logoMarca) {
		this.logoMarca = logoMarca;
	}
	public List<Image> getListImages() {
		return listImages;
	}
	public void setListImages(List<Image> listImages) {
		this.listImages = listImages;
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
	public Image getMasterImage() {
		return masterImage;
	}
	public void setMasterImage(Image masterImage) {
		this.masterImage = masterImage;
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
	public String getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	public String getFamiliaProducto() {
		return familiaProducto;
	}
	public void setFamiliaProducto(String familiaProducto) {
		this.familiaProducto = familiaProducto;
	}
	public String getAliasEkon() {
		return aliasEkon;
	}
	public void setAliasEkon(String aliasEkon) {
		this.aliasEkon = aliasEkon;
	}
	public float getStock() {
		return stock;
	}
	public void setStock(float stock) {
		this.stock = stock;
	}
	public Boolean getConfig() {
		return config;
	}
	public void setConfig(Boolean config) {
		this.config = config;
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
	public String getPvpRecomendado() {
		return pvpRecomendado;
	}
	public void setPvpRecomendado(String pvpRecomendado) {
		this.pvpRecomendado = pvpRecomendado;
	}
	public String getTipoPromocion() {
		return tipoPromocion;
	}
	public void setTipoPromocion(String tipoPromocion) {
		this.tipoPromocion = tipoPromocion;
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
	public String getCodigoPack() {
		return codigoPack;
	}
	public void setCodigoPack(String codigoPack) {
		this.codigoPack = codigoPack;
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
	public String getPvoPackUD() {
		return pvoPackUD;
	}
	public void setPvoPackUD(String pvoPackUD) {
		this.pvoPackUD = pvoPackUD;
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
}