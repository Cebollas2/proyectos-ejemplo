package com.magnolia.cione.dto;

import java.util.Map;

import com.commercetools.api.models.common.Money;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CartParamsDTO {
	
	private String sku;
	private Integer cantidad;
	private String definitionName;
	private String userId;
	private String connectionName;
	private String userIdEncodingDisabled;
	private String currencyCode;
	private String productId;
	private Integer variantId;
	private String refTaller;
	private String refCliente;
	private String packPromo;
	private String refPackPromos;
	private boolean aTaller;
	private String descPack;
	private boolean esPack;
	private String refPack;
	private String promoAplicada;
	private String tipoPromocion;
	private String plazoEntrega;
	private String plazoEntregaProveedor;
	private String pvoConDescuento;
	private Money pvoConDescuentoMonetary;
	private String customerGroup;
	private String id;
	private String lineItemId;
	private String notaSAS;
	private float stock;
	
	private String lcojo;
	private String lcdiseno;
	private String lcsku;
	private String lcesfera;
	private String lccilindro;
	private String lceje;
	private String lcdiametro;
	private String lccurvaBase;
	private String lccolor;
	private String lcdesccolor;
	private String lcadicion;
	
	private String colorAudio;
	private String colorCodo;
	private String familiaProducto;
	private String auricular;
	private String auricularName;
	private String acoplador;
	private String acopladorName;
	private String cargador;
	private String cargadorName;
	private String accesorioinalambrico;
	private String accesorioinalambricoName;
	private String garantia;
	private String garantiaName;
	private String tubosFinos;
	private String tubosFinosName;
	private String sujecionesDeportivas;
	private String sujecionesDeportivasName;
	private String filtros;
	private String filtrosName;
	private boolean deposito;
	private String sustitutiveProduct;
	private boolean direccionalidad;
	private boolean conectividad;
	private boolean mediaconcha;
	private String formato;
	private String gabinete;
	private String lcarcasa;
	private String lcodo;
	private String lfiltro;
	private boolean lhilo;
	private String llon;
	private String lmodventing;
	private String lcolorAudifono;
	private String lcolorCodo;
	private String lcolorPlato;
	private String lnumSerie;
	private String otoscan;
	private String lotoscan;
	private String lpathscan;
	private String linstrucciones;
	private String lpila;
	private String lplato;
	private String lpotencia;
	private boolean lpulsador;
	private boolean ltelebobina;
	private boolean lvolumen;
	private String ltipoventing;
	private String rcarcasa;
	private String rcodo;
	private String referencia;
	private String rfiltro;
	private boolean rhilo;
	private String rinstrucciones;
	private String rlon;
	private String rmodventing;
	private String rcolorAudifono;
	private String rcolorCodo;
	private String rcolorPlato;
	private String rnumSerie;
	private String rotoscan;
	private boolean renviocorreo;
	private boolean lenviocorreo;
	private String rpathscan;
	private String rpila;
	private String rplato;
	private String rpotencia;
	private boolean rpulsador;
	private boolean rtelebobina;
	private boolean rvolumen;
	private String rtipoventing;
    private boolean venting;
	private String side;
	private boolean telebobina;
	private boolean tinnitus;
	private Map<String, String> raudiogram;
	private Map<String, String> laudiogram;
	private String namePdfAudio;
	private String pathPdfAudio;
	private boolean envioCorreoOrdinario;
	private String pvoPackUD;
	private String packName;
	private String skuPackPadre;
	private String skuPackMaster;
	private String tipoPrecioPack;
	private String pvoproductopack;
	private String tipoProducto;
	private String idCarritoOculto;
	private String lineItemIdOculto;
	private String pvoPack;
	private String listadoProductosPreconfigurados;
	private String skuProductoConfigurado;
	private int step = -1;

	public String getTubosFinos() {
		return tubosFinos;
	}
	public void setTubosFinos(String tubosFinos) {
		this.tubosFinos = tubosFinos;
	}
	public String getTubosFinosName() {
		return tubosFinosName;
	}
	public void setTubosFinosName(String tubosFinosName) {
		this.tubosFinosName = tubosFinosName;
	}
	public String getSujecionesDeportivas() {
		return sujecionesDeportivas;
	}
	public void setSujecionesDeportivas(String sujecionesDeportivas) {
		this.sujecionesDeportivas = sujecionesDeportivas;
	}
	public String getSujecionesDeportivasName() {
		return sujecionesDeportivasName;
	}
	public void setSujecionesDeportivasName(String sujecionesDeportivasName) {
		this.sujecionesDeportivasName = sujecionesDeportivasName;
	}
	public String getFiltros() {
		return filtros;
	}
	public void setFiltros(String filtros) {
		this.filtros = filtros;
	}
	public String getFiltrosName() {
		return filtrosName;
	}
	public void setFiltrosName(String filtrosName) {
		this.filtrosName = filtrosName;
	}
	public String getDescPack() {
		return descPack;
	}
	public void setDescPack(String descPack) {
		this.descPack = descPack;
	}
	public boolean isEsPack() {
		return esPack;
	}
	public void setEsPack(boolean esPack) {
		this.esPack = esPack;
	}
	public String getRefPack() {
		return refPack;
	}
	public void setRefPack(String refPack) {
		this.refPack = refPack;
	}
	public String getLcojo() {
		return lcojo;
	}
	public void setLcojo(String lcojo) {
		this.lcojo = lcojo;
	}
	public String getLcdiseno() {
		return lcdiseno;
	}
	public void setLcdiseno(String lcdiseno) {
		this.lcdiseno = lcdiseno;
	}
	public String getLcsku() {
		return lcsku;
	}
	public void setLcsku(String lcsku) {
		this.lcsku = lcsku;
	}
	public String getLcesfera() {
		return lcesfera;
	}
	public void setLcesfera(String lcesfera) {
		this.lcesfera = lcesfera;
	}
	public String getLccilindro() {
		return lccilindro;
	}
	public void setLccilindro(String lccilindro) {
		this.lccilindro = lccilindro;
	}
	public String getLceje() {
		return lceje;
	}
	public void setLceje(String lceje) {
		this.lceje = lceje;
	}
	public String getLcdiametro() {
		return lcdiametro;
	}
	public void setLcdiametro(String lcdiametro) {
		this.lcdiametro = lcdiametro;
	}
	public String getLccurvaBase() {
		return lccurvaBase;
	}
	public void setLccurvaBase(String lccurvaBase) {
		this.lccurvaBase = lccurvaBase;
	}
	public String getLccolor() {
		return lccolor;
	}
	public void setLccolor(String lccolor) {
		this.lccolor = lccolor;
	}
	public String getLcdesccolor() {
		return lcdesccolor;
	}
	public void setLcdesccolor(String lcdesccolor) {
		this.lcdesccolor = lcdesccolor;
	}
	public String getLcadicion() {
		return lcadicion;
	}
	public void setLcadicion(String lcadicion) {
		this.lcadicion = lcadicion;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDefinitionName() {
		return definitionName;
	}
	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}
	public String getConnectionName() {
		return connectionName;
	}
	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}
	public String getUserIdEncodingDisabled() {
		return userIdEncodingDisabled;
	}
	public void setUserIdEncodingDisabled(String userIdEncodingDisabled) {
		this.userIdEncodingDisabled = userIdEncodingDisabled;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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
	public String getRefTaller() {
		return refTaller;
	}
	public void setRefTaller(String refTaller) {
		this.refTaller = refTaller;
	}
	public String getRefCliente() {
		return refCliente;
	}
	public void setRefCliente(String refCliente) {
		this.refCliente = refCliente;
	}
	public String getPackPromo() {
		return packPromo;
	}
	public void setPackPromo(String packPromo) {
		this.packPromo = packPromo;
	}
	public String getRefPackPromos() {
		return refPackPromos;
	}
	public void setRefPackPromos(String refPackPromos) {
		this.refPackPromos = refPackPromos;
	}
	public boolean isaTaller() {
		return aTaller;
	}
	public void setaTaller(boolean aTaller) {
		this.aTaller = aTaller;
	}
	public String getPromoAplicada() {
		return promoAplicada;
	}
	public void setPromoAplicada(String promoAplicada) {
		this.promoAplicada = promoAplicada;
	}
	public String getTipoPromocion() {
		return tipoPromocion;
	}
	public void setTipoPromocion(String tipoPromocion) {
		this.tipoPromocion = tipoPromocion;
	}
	public String getPlazoEntrega() {
		return plazoEntrega;
	}
	public void setPlazoEntrega(String plazoEntrega) {
		this.plazoEntrega = plazoEntrega;
	}
	public String getPlazoEntregaProveedor() {
		return plazoEntregaProveedor;
	}
	public void setPlazoEntregaProveedor(String plazoEntregaProveedor) {
		this.plazoEntregaProveedor = plazoEntregaProveedor;
	}
	public String getPvoConDescuento() {
		return pvoConDescuento;
	}
	public void setPvoConDescuento(String pvoConDescuento) {
		this.pvoConDescuento = pvoConDescuento;
	}
	public Money getPvoConDescuentoMonetary() {
		return pvoConDescuentoMonetary;
	}
	public void setPvoConDescuentoMonetary(Money pvoConDescuentoMonetary) {
		this.pvoConDescuentoMonetary = pvoConDescuentoMonetary;
	}
	public String getCustomerGroup() {
		return customerGroup;
	}
	public void setCustomerGroup(String customerGroup) {
		this.customerGroup = customerGroup;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLineItemId() {
		return lineItemId;
	}
	public void setLineItemId(String lineItemId) {
		this.lineItemId = lineItemId;
	}
	public float getStock() {
		return stock;
	}
	public void setStock(float stock) {
		this.stock = stock;
	}
	public String getNotaSAS() {
		return notaSAS;
	}
	public void setNotaSAS(String notaSAS) {
		this.notaSAS = notaSAS;
	}
	public String getColorAudio() {
		return colorAudio;
	}
	public void setColorAudio(String colorAudio) {
		this.colorAudio = colorAudio;
	}
	public String getColorCodo() {
		return colorCodo;
	}
	public void setColorCodo(String colorCodo) {
		this.colorCodo = colorCodo;
	}
	public String getFamiliaProducto() {
		return familiaProducto;
	}
	public void setFamiliaProducto(String familiaProducto) {
		this.familiaProducto = familiaProducto;
	}
	public String getAuricular() {
		return auricular;
	}
	public void setAuricular(String auricular) {
		this.auricular = auricular;
	}
	public String getAuricularName() {
		return auricularName;
	}
	public void setAuricularName(String auricularName) {
		this.auricularName = auricularName;
	}
	public String getAccesorioinalambricoName() {
		return accesorioinalambricoName;
	}
	public void setAccesorioinalambricoName(String accesorioinalambricoName) {
		this.accesorioinalambricoName = accesorioinalambricoName;
	}
	public String getCargadorName() {
		return cargadorName;
	}
	public void setCargadorName(String cargadorName) {
		this.cargadorName = cargadorName;
	}
	public String getAcoplador() {
		return acoplador;
	}
	public void setAcoplador(String acoplador) {
		this.acoplador = acoplador;
	}
	public String getAcopladorName() {
		return acopladorName;
	}
	public void setAcopladorName(String acopladorName) {
		this.acopladorName = acopladorName;
	}
	public String getCargador() {
		return cargador;
	}
	public void setCargador(String cargador) {
		this.cargador = cargador;
	}
	public String getAccesorioinalambrico() {
		return accesorioinalambrico;
	}
	public void setAccesorioinalambrico(String accesorioinalambrico) {
		this.accesorioinalambrico = accesorioinalambrico;
	}
	public String getGarantia() {
		return garantia;
	}
	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}
	public String getGarantiaName() {
		return garantiaName;
	}
	public void setGarantiaName(String garantiaName) {
		this.garantiaName = garantiaName;
	}
	public boolean isDeposito() {
		return deposito;
	}
	public void setDeposito(boolean deposito) {
		this.deposito = deposito;
	}
	public String getSustitutiveProduct() {
		return sustitutiveProduct;
	}
	public void setSustitutiveProduct(String sustitutiveProduct) {
		this.sustitutiveProduct = sustitutiveProduct;
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
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
	public String getGabinete() {
		return gabinete;
	}
	public void setGabinete(String gabinete) {
		this.gabinete = gabinete;
	}
	public String getLcarcasa() {
		return lcarcasa;
	}
	public void setLcarcasa(String lcarcasa) {
		this.lcarcasa = lcarcasa;
	}
	public String getLcodo() {
		return lcodo;
	}
	public void setLcodo(String lcodo) {
		this.lcodo = lcodo;
	}
	public String getLfiltro() {
		return lfiltro;
	}
	public void setLfiltro(String lfiltro) {
		this.lfiltro = lfiltro;
	}
	public boolean isLhilo() {
		return lhilo;
	}
	public void setLhilo(boolean lhilo) {
		this.lhilo = lhilo;
	}
	public String getLinstrucciones() {
		return linstrucciones;
	}
	public void setLinstrucciones(String linstrucciones) {
		this.linstrucciones = linstrucciones;
	}
	public String getLlon() {
		return llon;
	}
	public void setLlon(String llon) {
		this.llon = llon;
	}
	public String getLmodventing() {
		return lmodventing;
	}
	public void setLmodventing(String lmodventing) {
		this.lmodventing = lmodventing;
	}
	public String getLcolorAudifono() {
		return lcolorAudifono;
	}
	public void setLcolorAudifono(String lcolorAudifono) {
		this.lcolorAudifono = lcolorAudifono;
	}
	public String getLcolorCodo() {
		return lcolorCodo;
	}
	public void setLcolorCodo(String lcolorCodo) {
		this.lcolorCodo = lcolorCodo;
	}
	public String getLcolorPlato() {
		return lcolorPlato;
	}
	public void setLcolorPlato(String lcolorPlato) {
		this.lcolorPlato = lcolorPlato;
	}
	public String getLnumSerie() {
		return lnumSerie;
	}
	public void setLnumSerie(String lnumSerie) {
		this.lnumSerie = lnumSerie;
	}
	public String getOtoscan() {
		return otoscan;
	}
	public void setOtoscan(String otoscan) {
		this.otoscan = otoscan;
	}
	public String getLotoscan() {
		return lotoscan;
	}
	public void setLotoscan(String lotoscan) {
		this.lotoscan = lotoscan;
	}
	public String getLpathscan() {
		return lpathscan;
	}
	public void setLpathscan(String lpathscan) {
		this.lpathscan = lpathscan;
	}
	public String getLpila() {
		return lpila;
	}
	public void setLpila(String lpila) {
		this.lpila = lpila;
	}
	public String getLplato() {
		return lplato;
	}
	public void setLplato(String lplato) {
		this.lplato = lplato;
	}
	public boolean isLpulsador() {
		return lpulsador;
	}
	public void setLpulsador(boolean lpulsador) {
		this.lpulsador = lpulsador;
	}
	public boolean isLtelebobina() {
		return ltelebobina;
	}
	public void setLtelebobina(boolean ltelebobina) {
		this.ltelebobina = ltelebobina;
	}
	public boolean isTinnitus() {
		return tinnitus;
	}
	public void setTinnitus(boolean tinnitus) {
		this.tinnitus = tinnitus;
	}
	public boolean isLvolumen() {
		return lvolumen;
	}
	public void setLvolumen(boolean lvolumen) {
		this.lvolumen = lvolumen;
	}
	public String getLpotencia() {
		return lpotencia;
	}
	public void setLpotencia(String lpotencia) {
		this.lpotencia = lpotencia;
	}
	public String getLtipoventing() {
		return ltipoventing;
	}
	public void setLtipoventing(String ltipoventing) {
		this.ltipoventing = ltipoventing;
	}
	public String getRcarcasa() {
		return rcarcasa;
	}
	public void setRcarcasa(String rcarcasa) {
		this.rcarcasa = rcarcasa;
	}
	public String getRcodo() {
		return rcodo;
	}
	public void setRcodo(String rcodo) {
		this.rcodo = rcodo;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getRfiltro() {
		return rfiltro;
	}
	public void setRfiltro(String rfiltro) {
		this.rfiltro = rfiltro;
	}
	public boolean isRhilo() {
		return rhilo;
	}
	public void setRhilo(boolean rhilo) {
		this.rhilo = rhilo;
	}
	public String getRinstrucciones() {
		return rinstrucciones;
	}
	public void setRinstrucciones(String rinstrucciones) {
		this.rinstrucciones = rinstrucciones;
	}
	public String getRlon() {
		return rlon;
	}
	public void setRlon(String rlon) {
		this.rlon = rlon;
	}
	public String getRmodventing() {
		return rmodventing;
	}
	public void setRmodventing(String rmodventing) {
		this.rmodventing = rmodventing;
	}
	public String getRcolorAudifono() {
		return rcolorAudifono;
	}
	public void setRcolorAudifono(String rcolorAudifono) {
		this.rcolorAudifono = rcolorAudifono;
	}
	public String getRcolorCodo() {
		return rcolorCodo;
	}
	public void setRcolorCodo(String rcolorCodo) {
		this.rcolorCodo = rcolorCodo;
	}
	public String getRcolorPlato() {
		return rcolorPlato;
	}
	public void setRcolorPlato(String rcolorPlato) {
		this.rcolorPlato = rcolorPlato;
	}
	public String getRnumSerie() {
		return rnumSerie;
	}
	public void setRnumSerie(String rnumSerie) {
		this.rnumSerie = rnumSerie;
	}
	public String getRotoscan() {
		return rotoscan;
	}
	public void setRotoscan(String rotoscan) {
		this.rotoscan = rotoscan;
	}
	public boolean getRenviocorreo() {
		return renviocorreo;
	}
	public void setRenviocorreo(boolean renviocorreo) {
		this.renviocorreo = renviocorreo;
	}
	public boolean getLenviocorreo() {
		return lenviocorreo;
	}
	public void setLenviocorreo(boolean lenviocorreo) {
		this.lenviocorreo = lenviocorreo;
	}
	public String getRpathscan() {
		return rpathscan;
	}
	public void setRpathscan(String rpathscan) {
		this.rpathscan = rpathscan;
	}
	public String getRpila() {
		return rpila;
	}
	public void setRpila(String rpila) {
		this.rpila = rpila;
	}
	public String getRplato() {
		return rplato;
	}
	public void setRplato(String rplato) {
		this.rplato = rplato;
	}
	public String getRpotencia() {
		return rpotencia;
	}
	public void setRpotencia(String rpotencia) {
		this.rpotencia = rpotencia;
	}
	public boolean isRpulsador() {
		return rpulsador;
	}
	public void setRpulsador(boolean rpulsador) {
		this.rpulsador = rpulsador;
	}
	public boolean isRtelebobina() {
		return rtelebobina;
	}
	public void setRtelebobina(boolean rtelebobina) {
		this.rtelebobina = rtelebobina;
	}
	public boolean isRvolumen() {
		return rvolumen;
	}
	public void setRvolumen(boolean rvolumen) {
		this.rvolumen = rvolumen;
	}
	public String getRtipoventing() {
		return rtipoventing;
	}
	public void setRtipoventing(String rtipoventing) {
		this.rtipoventing = rtipoventing;
	}	
	public boolean isVenting() {
		return venting;
	}
	public void setVenting(boolean venting) {
		this.venting = venting;
	}
	public String getSide() {
		return side;
	}
	public void setSide(String side) {
		this.side = side;
	}
	public boolean isTelebobina() {
		return telebobina;
	}
	public void setTelebobina(boolean telebobina) {
		this.telebobina = telebobina;
	}
	public Map<String, String> getRaudiogram() {
		return raudiogram;
	}
	public void setRaudiogram(Map<String, String> raudiogram) {
		this.raudiogram = raudiogram;
	}
	public Map<String, String> getLaudiogram() {
		return laudiogram;
	}
	public void setLaudiogram(Map<String, String> laudiogram) {
		this.laudiogram = laudiogram;
	}
	public String getNamePdfAudio() {
		return namePdfAudio;
	}
	public void setNamePdfAudio(String namePdfAudio) {
		this.namePdfAudio = namePdfAudio;
	}
	public String getPathPdfAudio() {
		return pathPdfAudio;
	}
	public void setPathPdfAudio(String pathPdfAudio) {
		this.pathPdfAudio = pathPdfAudio;
	}
	public boolean isEnvioCorreoOrdinario() {
		return envioCorreoOrdinario;
	}
	public void setEnvioCorreoOrdinario(boolean envioCorreoOrdinario) {
		this.envioCorreoOrdinario = envioCorreoOrdinario;
	}
	public String getPvoPackUD() {
		return pvoPackUD;
	}
	public void setPvoPackUD(String pvoPackUD) {
		this.pvoPackUD = pvoPackUD;
	}
	public String getPackName() {
		return packName;
	}
	public void setPackName(String packName) {
		this.packName = packName;
	}
	public String getSkuPackPadre() {
		return skuPackPadre;
	}
	public void setSkuPackPadre(String skuPackPadre) {
		this.skuPackPadre = skuPackPadre;
	}
	public String getSkuPackMaster() {
		return skuPackMaster;
	}
	public void setSkuPackMaster(String skuPackMaster) {
		this.skuPackMaster = skuPackMaster;
	}
	public String getTipoPrecioPack() {
		return tipoPrecioPack;
	}
	public void setTipoPrecioPack(String tipoPrecioPack) {
		this.tipoPrecioPack = tipoPrecioPack;
	}
	public String getPvoproductopack() {
		return pvoproductopack;
	}
	public void setPvoproductopack(String pvoproductopack) {
		this.pvoproductopack = pvoproductopack;
	}
	public String getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
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
	public String getPvoPack() {
		return pvoPack;
	}
	public void setPvoPack(String pvoPack) {
		this.pvoPack = pvoPack;
	}
	public String getListadoProductosPreconfigurados() {
		return listadoProductosPreconfigurados;
	}
	public void setListadoProductosPreconfigurados(String listadoProductosPreconfigurados) {
		this.listadoProductosPreconfigurados = listadoProductosPreconfigurados;
	}
	public String getSkuProductoConfigurado() {
		return skuProductoConfigurado;
	}
	public void setSkuProductoConfigurado(String skuProductoConfigurado) {
		this.skuProductoConfigurado = skuProductoConfigurado;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
}
