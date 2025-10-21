package com.magnolia.cione.constants;

import java.util.Locale;

public final class MyshopConstants {
	
	private MyshopConstants() {
		throw new IllegalStateException("Constant class");
	}
		
	//Price display roles
	public static final String PVP_ROL = "empleado_cione_precio_pvp";
	public static final String PVO_ROL = "empleado_cione_precio_pvo";
	public static final String PVP_PVO_ROL = "empleado_cione_precio_pvppvo";
	public static final String HIDDEN_PRICES_ROL = "empleado_cione_precio_oculto";
	
	//Price display constants
	public static final String PVP = "pvp";
	public static final String PVO = "pvo";
	public static final String PVP_PVO = "pvp-pvo";
	public static final String HIDDEN_PRICES = "hidden";
	public static final String VIEWPRICES_USER_NODE_NAME = "viewprices";
	public static final String PRICE_CONFIGURATION_PROPERTY = "priceConfiguration";

	//CT constants
	public static final String BEARER = "Bearer ";
	public static final String BASIC = "Basic ";
	public static final String TIENEPROMOCIONES = "tienePromociones";
	public static final String TIENERECARGO = "tieneRecargo";
	public static final String COLECCION = "coleccion";
	public static final String REPUESTOALTERNATIVO = "repuestoAlternativo";
	public static final String ALIASEKON = "aliasEkon";
	public static final String CODIGOCENTRAL = "codigoCentral";
	public static final String CODIGO_CENTRAL = "codigo_central";
	public static final String COLORICONO = "colorIcono";
	public static final String CODIGOCOLOR = "codigoColor";
	public static final String COLORMONTURA = "colorMontura";
	public static final String DIMENSIONES_ANCHO_OJO = "dimensiones_ancho_ojo";
	public static final String GRADUACION = "graduacion";
	public static final String PVPRECOMENDADO = "pvpRecomendado";
	public static final String NOMBREARTICULO = "nombreArticulo";
	public static final String SKU = "sku";
	public static final String DTO_PORCENTAJE = "dto_porcentaje";
	public static final String CANT_DESDE = "cantidad_desde";
	public static final String TIPO_DESCUENTO = "tipo_descuento";
	public static final String CONN = "java:/comp/env/jdbc/Cione";
	public static final String VALOR = "valor";
	public static final String TYPE = "type";
	public static final String CURRENCYCODE = "currencyCode";
	public static final String CENTAMOUNT = "centAmount";
	public static final String FRACTIONDIGITS = "fractionDigits";
	public static final String PLAZOENTREGAPROVEEDOR = "plazoEntregaProveedor";
	public static final String TAMANIO = "tamanios";
	public static final String SIZE = "size";
	public static final String COLOR = "color";
	public static final String SUBTIPOPRODUCTO = "subTipoProducto";
	public static final String TIPOPRODUCTO = "tipoProducto";
	public static final String PLANTILLA = "plantilla";
	public static final String AMEDIDA = "aMedida";
	public static final String PVOSINPACK = "pvoSinPack";
	public static final String STATUSEKON = "statusEkon";
	public static final String OFERTAFLASH = "ofertaFlash";
	public static final String DESCRIPCION = "descripcion";
	public static final String GESTIONSTOCK = "gestionStock";

	public static final Locale esLocale = new Locale("es");
	public static final Locale ptLocale = new Locale("pt");
	public static final String defaultCategoryLogo = ".resources/cione-theme/webresources/img/myshop/common/230×162-OOPS-ES.jpg";
	public static final String defaultCategoryLogo_es = ".resources/cione-theme/webresources/img/myshop/common/230×162-OOPS-ES.jpg";
	public static final String defaultCategoryLogo_pt = ".resources/cione-theme/webresources/img/myshop/common/230×162-OOPS-PT.jpg";
	
	public static final String defaultProductLogo = ".resources/cione-theme/webresources/img/myshop/common/320×168-OOPS-ES.jpg";
	public static final String defaultProductLogo_es = ".resources/cione-theme/webresources/img/myshop/common/320×168-OOPS-ES.jpg";
	public static final String defaultProductLogo_pt = ".resources/cione-theme/webresources/img/myshop/common/320×168-OOPS-PT.jpg";
	
	public static final String defaultLenteGraduadaLogo = ".resources/cione-theme/webresources/img/myshop/common/imagennodisponible_lente_graduada.jpg";

	public static final int dtoPorcentaje = 1;
	public static final int dtoValor = 0;
	
	public static final String MARCAS = "MARCAS";
	public static final String REPUESTOS = "REPUESTOS";
	
	public static final String RESTRICCION_COMPRAMINIMA_PROVEEDOR = "PROVEEDOR";
	public static final String RESTRICCION_COMPRAMINIMA_MARCA = "MARCA";
	
	public static Long deleteDaysAfterLastModification = Long.valueOf(30);
	public static Long deleteDaysAfterLastModificationPack = Long.valueOf(1);
	
	public static final String cabeceraWebAudio = "{\"typ\":\"JWT\",\"alg\":\"HS256\"}";
	
	public static final String defaultCategoryLogo(Locale locale) {
		if (locale == esLocale)
			return defaultCategoryLogo_es;
		else if (locale == ptLocale)
			return defaultCategoryLogo_pt;
		else 
			return defaultCategoryLogo;
	}
	
	public static final String defaultProductLogo(Locale locale) {
		if (locale == esLocale)
			return defaultProductLogo_es;
		else if (locale == ptLocale)
			return defaultProductLogo_pt;
		else 
			return defaultProductLogo;
	}
	
	public static final String RECOMENDADOS = "recomendados";
	
	//Facets Filter
	public static final String FF_GAMA_COLOR_MONTURA = "variants.attributes.gamaColorMontura.es";
	public static final String FF_COLOR_MONTURA = "variants.attributes.colorMontura.es";
	public static final String FF_COLOR = "variants.attributes.color";
	public static final String FF_MARCA = "variants.attributes.marca";
	public static final String FF_TIPO_PRODUCTO = "variants.attributes.tipoProducto.es";
	public static final String FF_FAMILIA_EKON = "variants.attributes.familiaEkon";
	public static final String FF_GRADUACION = "variants.attributes.graduacion";
	public static final String FF_DIMENSIONES_ANCHO_OJO = "variants.attributes.dimensiones_ancho_ojo";
	public static final String FF_MATERIAL = "variants.attributes.material";
	public static final String FF_TARGET = "variants.attributes.target.es";
	public static final String FF_COLECCION = "variants.attributes.coleccion.es";
	public static final String FF_TAMANIOS = "variants.attributes.tamanios.es";
	public static final String FF_DIMENSIONES_LARGO_VARILLA = "variants.attributes.dimensiones_largo_varilla";
	public static final String FF_PRICE = "variants.price.centAmount";
	public static final String FF_ALIASEKON = "variants.attributes.aliasEkon";
	public static final String FF_STATUS = "variants.attributes.statusEkon";
	public static final String FF_PRUEBA_VIRTUAL = "variants.attributes.pruebaVirtual";
	public static final String FF_TIPO_CRISTAL = "variants.attributes.tipoCristal.es";
	public static final String FF_SKUPADRE = "variants.attributes.skuPadre";
	public static final String FF_AZULGRADUADO = "variants.attributes.azulGraduado";
	public static final String FF_COLECCIONMODA = "variants.attributes.coleccionModa";
	public static final String FF_CLIPSOLAR = "variants.attributes.clipSolar";
	public static final String FF_LINEANEGOCIO = "variants.attributes.lineaNegocio";
	public static final String FF_TIPOPACK = "variants.attributes.tipoPack";
	public static final String FF_TIENEPROMOCIONES = "variants.attributes.tienePromociones";
	public static final String FF_FAMILIAAUDIO = "variants.attributes.familiaAudio";
	public static final String FF_SEGMENTO = "variants.attributes.segmento";
	public static final String FF_SIZE = "variants.attributes.size";
	public static final String FF_PRESTACIONES = "variants.attributes.prestaciones";
	public static final String FF_PILA = "variants.attributes.pila";
	
	public static final String FF_SUBTIPOPRODUCTO = "variants.attributes.subTipoProducto";

	public static final String FF_BLISTEROCAJA = "variants.attributes.blisterocaja.key";
	public static final String FF_REEMPLAZO = "variants.attributes.reemplazo";
	public static final String FF_MATERIALBASE = "variants.attributes.materialBase";
	public static final String FF_GEOMETRIA = "variants.attributes.geometria";
	public static final String FF_GAMA = "variants.attributes.gama";
	public static final String FF_PROVEEDOR = "variants.attributes.proveedor";
	public static final String FF_BPROTECCIONSOLAR = "variants.attributes.bproteccionSolar";
	public static final String FF_DISPONIBILIDAD = "variants.attributes.disponibilidad";
	public static final String FF_MODELO = "variants.attributes.modelo";
	public static final String FF_ISCONTACTLAB = "variants.attributes.isContactLab";
	public static final String FF_COMPOSICION = "variants.attributes.composicion";
	public static final String FF_AMEDIDA = "variants.attributes.aMedida";
	public static final String FF_FORMATOAUDIO = "variants.attributes.formatoAudio";
	public static final String FF_CODPACK = "variants.attributes.codPack";
	
	public static final String FF_ALL_RANGES = ":range(* to 0),(0 to *)";
	

	
	//Componente Filtro
	public static final String CATEGORY = "category";
	public static final String FILTERSELECT = "filterSelect";
	
	//Componente Listado Filter
	public static final String ORDERCONF = "orderconf";
	public static final String ORDERNEW = "new";
	public static final String ORDERNEWALTAEKON = "newaltaekon";
	public static final String ORDERALFABETICO = "alfabetico";
	public static final String ORDERLOWTOHIGH = "lowtohigh";
	public static final String ORDERHIGHTOLOW = "hightolow";
	public static final String PRICEGROUP = "gruposPrecio";
	public static final String FECHAALTAEKONATTR = "fechaAltaEkon";
	public static final String HOMESN = "homeSN";
	public static final String ORDERFIELD = "orderField";
	
	public static final String HIDEFILTER = "hideFilter";
	public static final String NUMPRODUCT = "numProduct";
	
	public static final String AGRUPADORES = "agrupadores";
	public static final String MANDATORYFILTERS = "mandatoryFilters";
	
	//Componente colecciones destacadas
	public static final String CATEGORYROOT = "filterSelect";
	public static final String CATEGORYDEFAULT = "COLECCIONES";
	//public static final String AUDIFONOS = "Audífonos";
	
	//Shopping Lists MyshopConstants
	public static final String CARTTYPE = "cartType";
	public static final String BUDGET = "budget";
	public static final String IDPRESUPUESTO = "idPresupuesto";
	public static final String IMPORTEVENTA = "importeVenta";
	public static final String ERRORUPDATECUSTOMFIELD = "Error updateCustomField ";
	public static final String ERRORUPDATECUSTOMFIELDCLI = "Error updateCustomFieldCLI ";
	public static final String REFCLIENTE = "refCliente";
	public static final String EUR = "EUR";
	public static final String ENDEPOSITO = "enDeposito";
	public static final String SKUUPPERCASE = "SKU";
	public static final String MAILCLIENTE = "mailCliente";
	public static final String IMPORTETOTAL = "importeTotal";
	public static final String VIGENCIA = "vigencia";
	public static final String NUMCLIENTE = "numCliente";
	public static final String DATEFORMATJS = "yyyy-MM-dd";
	public static final String COMMASPACE = ", ";
	public static final String I18N_CILINDRO = "cione-module.templates.myshop.configurador-lentes-component.cilindro";
	public static final String I18N_ESFERA = "cione-module.templates.myshop.configurador-lentes-component.esfera";
	public static final String I18N_DIAMETRO = "cione-module.templates.myshop.configurador-lentes-component.diametro";
	public static final String I18N_EJE = "cione-module.templates.myshop.configurador-lentes-component.eje";
	public static final String I18N_ADICION = "cione-module.templates.components.detalle-producto-component.adicion";
	public static final String I18N_OJO = "cione-module.templates.myshop.configurador-lentes-component.ojo";
	public static final String I18N_OJOI = "cione-module.templates.myshop.configurador-lentes-component.ojoi";
	public static final String I18N_OJOD = "cione-module.templates.myshop.configurador-lentes-component.ojod";
	public static final String I18N_CURVABASE = "cione-module.templates.components.detalle-producto-component.curvabase";
	public static final String I18N_TAMANIO = "cione-module.templates.components.detalle-producto-component.tamanio";
	public static final String I18N_CALIBRE = "cione-module.templates.myshop.listado-productos-carrito-component.calibre";
	public static final String I18N_GRADUACION = "cione-module.templates.myshop.listado-productos-carrito-component.graduacion";
	public static final String I18N_DESIGN = "cione-module.templates.components.detalle-producto-component.design";
	public static final String I18N_ERROR_PACK_MONTURAS = "cione-module.templates.myshop.listado-productos-carrito-component.error-pack-monturas";
	public static final String LMATTYPE = "LMATTYPE";
	public static final String PVP_R = "PVP_R";
	public static final String PVP_L = "PVP_L";
	public static final String PVP_UPPERCASE = "PVP";
	public static final String CYL = "CYL";
	public static final String CYL_L = "CYL_L";
	public static final String CYL_R = "CYL_R";
	public static final String SPH = "SPH";
	public static final String SPH_L = "SPH_L";
	public static final String SPH_R = "SPH_R";
	public static final String CRIB = "CRIB";
	public static final String CRIB_L = "CRIB_L";
	public static final String CRIB_R = "CRIB_R";
	public static final String AX_L = "AX_L";
	public static final String AX_R = "AX_R";
	public static final String ADD_L = "ADD_L";
	public static final String ADD_R = "ADD_R";
	public static final String EYE = "EYE";
	public static final String LNAM_L = "LNAM_L";
	public static final String LNAM_R = "LNAM_R";
	public static final String LC_OJO = "LC_ojo";
	public static final String LC_DISENO = "LC_diseno";
	public static final String LC_ESFERA = "LC_esfera";
	public static final String LC_CILINDRO = "LC_cilindro";
	public static final String LC_EJE = "LC_eje";
	public static final String LC_DIAMETRO = "LC_diametro";
	public static final String LC_CURVABASE = "LC_curvaBase";
	public static final String LC_ADICION = "LC_adicion";
	public static final String TAMANIOS = "tamanios";
	public static final String CT = "commercetools";
	public static final String CON = "connectionName";
	public static final String DESC = "DESCRIPTION";
	public static final String LVL1 = "nivel1";
	public static final String LVL2 = "nivel2";
	public static final String REFTALLER = "refTaller";
	public static final String ATALLER = "aTaller";
	
	
	public static final String AURICULARES = "auriculares";
	public static final String ACCESORIOSAUDIO = "accesoriosAudio";
	
	public static final String ACOPLADORES = "acopladores";
	public static final String CARGADORES = "cargadores";
	public static final String ACCESORIOS = "accesorios";
	public static final String TUBOSFINOS = "tubosFinos";
	public static final String SUJECCIONESDEPORTIVAS = "sujecionesDeportivas";
	public static final String FILTROS = "filtros";
	
	
	public static final String GARANTIA = "garantia";
	
	//cart types
	
	public static final String PVOCONDESCUENTO = "pvoConDescuento";
	public static final String REFPACKPROMOS = "refPackPromos";
	
	//tipo de stock
	public static final String ATTR_STOCK_SESSION = "tipoStockSession";
	public static final String STOCKCTRAL = "stockCTRAL";
	public static final String STOCKCANAR = "stockCANAR";
	public static final String ALMACENDEFAULTCTRAL = "CTRAL";
	public static final String ALMACENDEFAULTCANAR = "CANAR";
	
	public static final String ATTR_PRICE_GROUP_SESSION = "grupoPrecioSession";
	public static final String ATTR_CUSTOMER_SESSION = "infoSocioSession";
	public static final String ATTR_CUSTOMERID_SESSION = "customerIdSession";
	public static final String ATTR_PACK_SESSION = "packsSession";
	
	//Compra Periodica MyshopConstants
	public static final String PERIODIC_PURCHASE = "periodicpursache";
	public static final String PURCHASE = "idPurchase";
	public static final String PERIODICITY_PURCHARSE = "periodicityPurcharse";
	public static final String PAYMENT = "payment";
	public static final String DATEINI_PURCHARSE = "dateIniPurcharse";
	public static final String DATEDFIN_PURCHARSE = "datedFinPurcharse";
	public static final String STATUS_PURCHASE = "statusPurchase";
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String YELLOW_STATUS = "yellow";
	public static final String GREEN_STATUS = "green";
	public static final String RED_STATUS = "red";
	public static final String CLIENTE = "cliente";
	public static final String MENSUAL = "Mensual";
	public static final String BIMENSUAL = "Bimensual";
	public static final String TRIMESTRAL = "Trimestral";
	public static final String SEMESTRAL = "Semestral";
	public static final String ANUAL = "Anual";
	
	public static final String I18N_DIMENSIONESANCHOOJO = "cione-module.templates.myshop.listado-productos-carrito-component.calibre";
	public static final String I18N_PVO = "cione-module.templates.components.detalle-producto-component.pvo";
	public static final String COLR_L = "COLR_L";
	public static final String COLR_R = "COLR_R";
	public static final String I18N_COLOR = "cione-module.templates.myshop.configurador-lentes-component.color";
	public static final String INF_R = "INF_R";
	public static final String I18N_INF_R = "cione-module.templates.myshop.configurador-lentes-component.ojo-informativo";
	public static final String I18N_YES = "cione-module.templates.myshop.configurador-lentes-component.yes";
	public static final String COLORAUDIFONO = "colorAudifono";
	public static final String SUBJECT_COMPRA_PERIODICA = "cione-module.templates.components.listado-cp-component.mailsub";
	
	public static final String GENERIC_PRODUCT = "generic_product";
	public static final String AUDIOLOGY_BASIC = "audiology_basic";
	public static final String CONTACT_LENSES = "contact_lenses";
	public static final String PACKS_BUNDLES = "packs_bundles";
	
	public static final String CONTACTOLOGIA = "contactologia";
	public static final String AUDIFONOS = "audifonos";
	public static final String AUDIOLAB = "audiolab";
	public static final String PACKS = "packs";
	
	public static final String GENERIC_PACK = "pack";
	
	public static final String CL_DATESTART = "datestart";
	public static final String CL_DATEEND = "dateend";
	public static final String CL_OPEN_BRACKET = "[";
	public static final String CL_CLOSE_BRACKET = "]";
	public static final String CL_SLASH = "/";
	public static final String CL_SPACE_HYPHEN = " - ";

	//categorias
	public static final String C_GAFAS_GRADUADAS = "monturas-gafasgraduadas";
	public static final String C_GAFAS_SOL = "monturas-gafasdesol";
	
	public static final String TIPOPACKGENERICO_MONTURAS_LENTES = "ML";
}
