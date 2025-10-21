package com.magnolia.cione.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.LineItem;
import com.commercetools.api.models.category.Category;
import com.commercetools.api.models.category.CategoryReference;
import com.commercetools.api.models.common.Price;
import com.commercetools.api.models.product.ProductProjection;
import com.commercetools.api.models.product.ProductVariant;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dao.PromocionesDao;
import com.magnolia.cione.dto.CartParamsDTO;
import com.magnolia.cione.dto.CustomerCT;
import com.magnolia.cione.dto.PromocionesDTO;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.dto.VariantDTO;
import com.magnolia.cione.setup.CategoryUtils;
import com.magnolia.cione.setup.CioneEcommerceConectionProvider;
import com.magnolia.cione.utils.CioneUtils;
import com.magnolia.cione.utils.MyShopUtils;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import freemarker.template.TemplateException;
import info.magnolia.cms.util.SimpleFreemarkerHelper;
import info.magnolia.context.MgnlContext;
import info.magnolia.init.MagnoliaConfigurationProperties;

public class PDFGeneratorConfiguradorImpl implements PDFGeneratorConfigurador {
	
	@Inject
	private MiddlewareService middlewareService; 
	
	@Inject
	private CommercetoolsService commerceToolsService;
	
	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider; 
	
	@Inject
	private MagnoliaConfigurationProperties mcp;
	
	@Inject
	private PromocionesDao promocionesDao;
	
	@Inject
	private CommercetoolsService commercetoolsService;
	
	@Inject
	private CommercetoolsServiceAux commercetoolsServiceAux;
	
	@Inject
	private CartService cartService;
	
	@Inject
	private CategoryUtils categoryUtils;
	
	@Inject
	private CategoryService categoryService;
	
	private static final Logger log = LoggerFactory.getLogger(PDFGeneratorConfiguradorImpl.class);

	private static final String PATH_SEPARATOR="/";
	
	private static final String NO_APLICA="No aplica";
	
	@Override
	public File getFileSending(CartParamsDTO cartQueryParamsDTO) {
		String idClient = CioneUtils.getIdCurrentClientERP(); /* 0050800 */
		File file = null;
		try {
			if (cartQueryParamsDTO.getNamePdfAudio() == null) {
				cartQueryParamsDTO.setNamePdfAudio("audioPDF.pdf");
			}
			createDir(cartQueryParamsDTO.getRefTaller());
			createHtml(cartQueryParamsDTO, idClient);
			file = new File(cioneEcommerceConectionProvider.getConfigService().getConfig().getAudioLabConfiguradorPDFPath() 
					+ PATH_SEPARATOR + idClient + PATH_SEPARATOR
					+ cartQueryParamsDTO.getRefTaller() + PATH_SEPARATOR + cartQueryParamsDTO.getNamePdfAudio());
			file.setReadable(true, false);
	    	file.setWritable(true, false);
	    	file.setExecutable(true, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
	
	@Override
	public void createHtml(CartParamsDTO cartQueryParamsDTO, String idClient) {
		try {	
			Map<String, Object> templateValues = new HashMap<>();
			
			CustomerCT customer = commercetoolsService.getCustomer();
			cartQueryParamsDTO.setCustomerGroup(customer.getCustomerGroup().getId());
		    Cart cart = cartService.getCart(customer.getId());
		    for (LineItem lineItem : 
		    	cart.getLineItems()) {
		    	if (lineItem.getProductId().equals(cartQueryParamsDTO.getProductId())) {
		    		
		    		if (MyShopUtils.hasAttribute("telebobina", lineItem.getVariant().getAttributes())) {
		    			templateValues.put("mostrarTelebobina", (Boolean) MyShopUtils.findAttribute("telebobina", lineItem.getVariant().getAttributes()).getValue());
		    			//templateValues.put("mostrarTelebobina", lineItem.getVariant().getAttribute("telebobina").getValueAsBoolean());
		    		}
		    		if (MyShopUtils.hasAttribute("pulsador", lineItem.getVariant().getAttributes())) {
		    			templateValues.put("mostrarPulsador", (Boolean) MyShopUtils.findAttribute("pulsador", lineItem.getVariant().getAttributes()).getValue());
		    			//templateValues.put("mostrarPulsador", lineItem.getVariant().getAttribute("pulsador").getValueAsBoolean());
		    		}
		    		if (MyShopUtils.hasAttribute("hiloExtractor", lineItem.getVariant().getAttributes())) {
		    			templateValues.put("mostrarHiloExtractor", (Boolean) MyShopUtils.findAttribute("hiloExtractor", lineItem.getVariant().getAttributes()).getValue());
		    			//templateValues.put("mostrarHiloExtractor", lineItem.getVariant().getAttribute("hiloExtractor").getValueAsBoolean());
		    		}
		    		if (MyShopUtils.hasAttribute("controlVolumen", lineItem.getVariant().getAttributes())) {
		    			templateValues.put("mostrarControlVolumen", (Boolean) MyShopUtils.findAttribute("controlVolumen", lineItem.getVariant().getAttributes()).getValue());
		    			//templateValues.put("mostrarControlVolumen", lineItem.getVariant().getAttribute("controlVolumen").getValueAsBoolean());
		    		}
		    		if (MyShopUtils.hasAttribute("venting", lineItem.getVariant().getAttributes())) {
		    			templateValues.put("mostrarVenting", (Boolean) MyShopUtils.findAttribute("venting", lineItem.getVariant().getAttributes()).getValue());
		    			//templateValues.put("mostrarVenting", lineItem.getVariant().getAttribute("venting").getValueAsBoolean());
		    		}
		    		if (MyShopUtils.hasAttribute("filtroCerumen", lineItem.getVariant().getAttributes())) {
		    			boolean mostrar = ((ArrayList<String>) MyShopUtils.findAttribute("filtroCerumen", lineItem.getVariant().getAttributes()).getValue()).isEmpty();
		    			templateValues.put("mostrarFiltroCerumen", !mostrar);
		    			//boolean mostrar = lineItem.getVariant().getAttribute("filtroCerumen").getValueAsStringSet().isEmpty();
		    			//templateValues.put("mostrarFiltroCerumen", !mostrar);
		    		}
		    		if (MyShopUtils.hasAttribute("longitudCanal", lineItem.getVariant().getAttributes())) {
		    			boolean mostrar = ((ArrayList<String>) MyShopUtils.findAttribute("longitudCanal", lineItem.getVariant().getAttributes()).getValue()).isEmpty();
		    			templateValues.put("mostrarLongitudCanal", !mostrar);
		    			//boolean mostrar = lineItem.getVariant().getAttribute("longitudCanal").getValueAsStringSet().isEmpty();
		    			//templateValues.put("mostrarLongitudCanal", !mostrar);
		    		}
		    		if (MyShopUtils.hasAttribute("colorPlato", lineItem.getVariant().getAttributes())) {
		    			boolean mostrar = ((ArrayList<String>) MyShopUtils.findAttribute("colorPlato", lineItem.getVariant().getAttributes()).getValue()).isEmpty();
		    			templateValues.put("mostrarColorPlato", !mostrar);
		    			//boolean mostrar = lineItem.getVariant().getAttribute("colorPlato").getValueAsStringSet().isEmpty();
		    			//templateValues.put("mostrarColorPlato", !mostrar);
		    		}
		    		break;
		    	}
		    }
		    
		    boolean aplicaDerecho = (cartQueryParamsDTO.getSide().equals("binaural") || cartQueryParamsDTO.getSide().equals("derecho"));
		    boolean aplicaIzquierdo = (cartQueryParamsDTO.getSide().equals("binaural") || cartQueryParamsDTO.getSide().equals("izquierdo"));
		    
			String idSocio = CioneUtils.getIdCurrentClientERP(); /* 0050800 */
			templateValues.put("numSocio", idClient );
			templateValues.put("host", CioneUtils.getURLHttps());
			/* Siempre que se llame al middleware se llama por el idSocio (sin 00 al final) */
			UserERPCioneDTO data = middlewareService.getUserFromERP(idSocio); 
			templateValues.put("nombreSocio", data.getRazonSocial());
			
			String sku = cartQueryParamsDTO.getSku();
			VariantDTO producto = getInfoVariant(sku);
			
			String logocione = CioneUtils.getURLHttps() + "/.resources/cione-theme/webresources/img/myshop/audio/logo_cione_audiolab_pdf.png";
			templateValues.put("logocione", logocione);
			if (producto.getNombreArticulo() != null){
				templateValues.put("nombreAudifono", producto.getNombreArticulo());
			}else {
				templateValues.put("nombreAudifono", "");
			}						
			if ( (producto.getDescripcion() != null) && (!producto.getDescripcion().equals(".")) ) {
				templateValues.put("descripcionAudifono", producto.getDescripcion());
				//template.put("descripcionAudifono", "lorem ipsum ddddddddd ffffffffffff dddddddd eeeeeeeeed ddddddde  eeeeeeeeee ddddd lorem ipsum ddddddddd ffffffffffff dddddddd eeeeeeeeed ddddddde  eeeeeeeeee ddddd");
			}else {
				templateValues.put("descripcionAudifono", "&nbsp;");
				//template.put("descripcionAudifono", "lorem ipsum ddddddddd ffffffffffff dddddddd eeeeeeeeed ddddddde  eeeeeeeeee ddddd lorem ipsum ddddddddd ffffffffffff dddddddd eeeeeeeeed ddddddde  eeeeeeeeee ddddd");
			}
			if (producto.getLogoMarca() != null) { 
				templateValues.put("logo", producto.getLogoMarca()); 
			}else {
				templateValues.put("logo", "&nbsp;");
			}
			if (producto.getPvo() != null) {
				templateValues.put("precioPVP", producto.getPvo());
			}else {
				templateValues.put("precioPVP", "&nbsp;");
			}
			
			
			/* Seleccion lado */
			if ((cartQueryParamsDTO.getSide() != null) && (!cartQueryParamsDTO.getSide().isEmpty())) {
				if (cartQueryParamsDTO.getSide().equals("binaural")) 
					templateValues.put("binaural",  cartQueryParamsDTO.getSide() + " (2 unidades)");
				else 
					templateValues.put("binaural",  cartQueryParamsDTO.getSide());
			}
				
			if(cartQueryParamsDTO.getReferencia() != null)
				templateValues.put("referencia", cartQueryParamsDTO.getReferencia());
			if(cartQueryParamsDTO.getGabinete() != null)
				templateValues.put("contactoGabinete", cartQueryParamsDTO.getGabinete());
			if(cartQueryParamsDTO.getFormato() != null)
				templateValues.put("formato", cartQueryParamsDTO.getFormato());
			else
				templateValues.put("formato", "No aplica");
			
			/* Audiograma */
			Map<String, String> raudiogram = cartQueryParamsDTO.getRaudiogram();
			if(raudiogram != null){
				for(Map.Entry<String, String> entry : raudiogram.entrySet()) { 
					templateValues.put("r" + entry.getKey(), entry.getValue());
				}				 
			}
			
			Map<String, String> laudiogram = cartQueryParamsDTO.getLaudiogram();
			if(laudiogram != null) {
				for(Map.Entry<String, String> entry : laudiogram.entrySet()) {
					templateValues.put("l" + entry.getKey(), entry.getValue());
				}
			}
			
						
			/* Potencia */
			if(cartQueryParamsDTO.getRpotencia() != null) {
				templateValues.put("potenciaDcha", aplicaDerecho ? cartQueryParamsDTO.getRpotencia() : NO_APLICA); 
			}else {
				templateValues.put("potenciaDcha", aplicaDerecho ? "" : NO_APLICA);
			}
			if(cartQueryParamsDTO.getLpotencia() != null) {
				templateValues.put("potenciaIzda", aplicaIzquierdo ? cartQueryParamsDTO.getLpotencia() : NO_APLICA);
			}else {
				templateValues.put("potenciaIzda", aplicaIzquierdo ? "" : NO_APLICA);
			}
			
			
			/* Caracteristicas personalizadas */
			if(cartQueryParamsDTO.isDireccionalidad()) {
				templateValues.put("direccionalidadDcha", aplicaDerecho ? "Si" : NO_APLICA);//cambiar por icono a poder ser
				templateValues.put("direccionalidadIzda", aplicaIzquierdo ? "Si" : NO_APLICA);
			}						
			if(cartQueryParamsDTO.isConectividad()) {
				templateValues.put("conectividadDcha", aplicaDerecho ? "Si" : NO_APLICA);
				templateValues.put("conectividadIzda",  aplicaIzquierdo ? "Si" : NO_APLICA);
			}							
			if(cartQueryParamsDTO.isMediaconcha()) {
				templateValues.put("conchaDcha", aplicaDerecho ? "Si" : NO_APLICA);
				templateValues.put("conchaIzda",  aplicaIzquierdo ? "Si" : NO_APLICA);
			}
							
			
			/* Caracteristicas opcionales */
			if(cartQueryParamsDTO.getRpila() != null && cartQueryParamsDTO.getRpila() != "") {
				templateValues.put("tamPilaDcha", aplicaDerecho ? cartQueryParamsDTO.getRpila() : NO_APLICA); 				
			}else {
				templateValues.put("tamPilaDcha", aplicaDerecho ? "" : NO_APLICA); 
			}
			if(cartQueryParamsDTO.getLpila() != null && cartQueryParamsDTO.getLpila() != "") {
				templateValues.put("tamPilaIzda",  aplicaIzquierdo ? cartQueryParamsDTO.getLpila() : NO_APLICA);
			}else {
				templateValues.put("tamPilaIzda",  aplicaIzquierdo ? "" : NO_APLICA); 
			}				
			if(cartQueryParamsDTO.getRlon() != null) {
			  templateValues.put("longCanalDcha",  aplicaDerecho ? cartQueryParamsDTO.getRlon() : NO_APLICA);
			}else {
			  templateValues.put("longCanalDcha",  aplicaDerecho ? "" : NO_APLICA);
			}
			if(cartQueryParamsDTO.getLlon() != null) {
			  templateValues.put("longCanalIzda",  aplicaIzquierdo ? cartQueryParamsDTO.getLlon() : NO_APLICA); 
			}else {
			  templateValues.put("longCanalIzda",  aplicaIzquierdo ? "" : NO_APLICA);
			}
			if(cartQueryParamsDTO.isRtelebobina())
			  templateValues.put("telebobinaDcha",  aplicaDerecho ? "Si" : NO_APLICA);
			else
			  templateValues.put("telebobinaDcha",  aplicaDerecho ? "No" : NO_APLICA);
			if(cartQueryParamsDTO.isLtelebobina())
			  templateValues.put("telebobinaIzda",  aplicaIzquierdo ? "Si" : NO_APLICA);
			else
			  templateValues.put("telebobinaIzda",  aplicaIzquierdo ? "No" : NO_APLICA);
			if(cartQueryParamsDTO.getRfiltro() != null) {
			  templateValues.put("anticerumenDcha",  aplicaDerecho ? cartQueryParamsDTO.getRfiltro()  : NO_APLICA);
			}else {
			  templateValues.put("anticerumenDcha",  aplicaDerecho ? "" : NO_APLICA);
			}
			if(cartQueryParamsDTO.getLfiltro() != null) {
			  templateValues.put("anticerumenIzda",  aplicaIzquierdo ? cartQueryParamsDTO.getLfiltro()  : NO_APLICA);
			}else {
			  templateValues.put("anticerumenIzda",  aplicaIzquierdo ? "" : NO_APLICA);
			}
			if(cartQueryParamsDTO.isRpulsador()) {
			  templateValues.put("pulsadorDcha",  aplicaDerecho ? "Si" : NO_APLICA);
			}else {
			  templateValues.put("pulsadorDcha",  aplicaDerecho ? "No" : NO_APLICA);
			}
			if(cartQueryParamsDTO.isLpulsador()) {
			  templateValues.put("pulsadorIzda",  aplicaIzquierdo ? "Si" : NO_APLICA);
			}else {
			  templateValues.put("pulsadorIzda",  aplicaIzquierdo ? "No" : NO_APLICA);
			}			
			if(cartQueryParamsDTO.isRvolumen()) {				
			  templateValues.put("volumenDcha",  aplicaDerecho ? "Si" : NO_APLICA);
			}else {
			  templateValues.put("volumenDcha",  aplicaDerecho ? "No" : NO_APLICA);
			}
			if(cartQueryParamsDTO.isLvolumen()) {				
			  templateValues.put("volumenIzda",  aplicaIzquierdo ? "Si" : NO_APLICA);
			}else {
			  templateValues.put("volumenIzda",  aplicaIzquierdo ? "No" : NO_APLICA);
			}
			if(cartQueryParamsDTO.isRhilo()) {				
			  templateValues.put("hiloExtractorDcha",  aplicaDerecho ? "Si" : NO_APLICA);
			}else {
			  templateValues.put("hiloExtractorDcha",  aplicaDerecho ? "No" : NO_APLICA);
			}
			if(cartQueryParamsDTO.isLhilo()) {				
			  templateValues.put("hiloExtractorIzda",  aplicaIzquierdo ? "Si" : NO_APLICA);
			}else {
			  templateValues.put("hiloExtractorIzda",  aplicaIzquierdo ? "No" : NO_APLICA);
			}	
			if(cartQueryParamsDTO.isTinnitus()) {				
			  templateValues.put("tinnitusDcha",  aplicaDerecho ? "Si" : NO_APLICA);
			  templateValues.put("tinnitusIzda",  aplicaIzquierdo ? "Si" : NO_APLICA);
			}
			if(cartQueryParamsDTO.isVenting()) {				
			  templateValues.put("ventingDcha",  aplicaDerecho ? "Si" : NO_APLICA);
			}else {
			  templateValues.put("ventingDcha",  aplicaDerecho ? "No" : NO_APLICA);
			}
			if(cartQueryParamsDTO.isVenting()) {				
			  templateValues.put("ventingIzda",  aplicaIzquierdo ? "Si" : NO_APLICA);
			}else {
			  templateValues.put("ventingIzda",  aplicaIzquierdo ? "No" : NO_APLICA);
			}  				
			if(cartQueryParamsDTO.getRtipoventing() != null) {
			  templateValues.put("tipoVentingDcha",  aplicaDerecho ? cartQueryParamsDTO.getRtipoventing() : NO_APLICA);	
			}else {
			  templateValues.put("tipoVentingDcha",  aplicaDerecho ? "" : NO_APLICA);	
			}		
			if(cartQueryParamsDTO.getRmodventing() != null) {
			  templateValues.put("modifVentingDcha",  aplicaDerecho ? cartQueryParamsDTO.getRmodventing() : NO_APLICA); 
			}else {
			  templateValues.put("modifVentingDcha",  aplicaDerecho ? "" : NO_APLICA); 
			}	            
			if(cartQueryParamsDTO.getLtipoventing() != null) {
			  templateValues.put("tipoVentingIzda",  aplicaIzquierdo ? cartQueryParamsDTO.getLtipoventing() : NO_APLICA); 
			}else {
			  templateValues.put("tipoVentingIzda",  aplicaIzquierdo ? "" : NO_APLICA);
			}
			if(cartQueryParamsDTO.getLmodventing() != null) {
			  templateValues.put("modifVentingIzda",  aplicaIzquierdo ? cartQueryParamsDTO.getLmodventing() : NO_APLICA); 
			}else {	
			  templateValues.put("modifVentingIzda",  aplicaIzquierdo ? "" : NO_APLICA);				
			}
			      

			/* Color */ 
			if(cartQueryParamsDTO.getRcarcasa() != null) { 
			  templateValues.put("colorCarcasaDcha",  aplicaDerecho ? cartQueryParamsDTO.getRcarcasa() : NO_APLICA); 
			}else {
			  templateValues.put("colorCarcasaDcha",  aplicaDerecho ? "" : NO_APLICA);
			}
			if(cartQueryParamsDTO.getLcarcasa() != null) { 
			  templateValues.put("colorCarcasaIzda",  aplicaIzquierdo ? cartQueryParamsDTO.getLcarcasa() : NO_APLICA); 
			}else {
			  templateValues.put("colorCarcasaIzda",  aplicaIzquierdo ? "" : NO_APLICA);
			}
			if(cartQueryParamsDTO.getRplato() != null) { 
			  templateValues.put("colorPlatoDcha",  aplicaDerecho ? cartQueryParamsDTO.getRplato() : NO_APLICA);
			}else {
			  templateValues.put("colorPlatoDcha",  aplicaDerecho ? "" : NO_APLICA);
			}
			if(cartQueryParamsDTO.getLplato() != null) { 
			  templateValues.put("colorPlatoIzda",  aplicaIzquierdo ? cartQueryParamsDTO.getLplato() : NO_APLICA); 
			}else {
			  templateValues.put("colorPlatoIzda",  aplicaIzquierdo ? "" : NO_APLICA);
			}

			/* Impresiones */			
			if(cartQueryParamsDTO.getRnumSerie() != null && !cartQueryParamsDTO.getRnumSerie().isEmpty()) {
			  templateValues.put("numSerieDcha", cartQueryParamsDTO.getRnumSerie()); 
			}else if (cartQueryParamsDTO.getLnumSerie() != null && !cartQueryParamsDTO.getLnumSerie().isEmpty() && !aplicaDerecho) {
			  templateValues.put("numSerieDcha", NO_APLICA);
			}				
			if(cartQueryParamsDTO.getLnumSerie() != null && !cartQueryParamsDTO.getLnumSerie().isEmpty()) {
			  templateValues.put("numSerieIzda", cartQueryParamsDTO.getLnumSerie()); 
			}else if (cartQueryParamsDTO.getRnumSerie() != null && !cartQueryParamsDTO.getRnumSerie().isEmpty() && !aplicaIzquierdo) {
			  templateValues.put("numSerieIzda", NO_APLICA);
			}						
			if(cartQueryParamsDTO.getRpathscan() != null && !cartQueryParamsDTO.getRpathscan().equals("")) {
				String rutaCompleta = cartQueryParamsDTO.getRpathscan();
				
				if (CioneUtils.getURLHttps().indexOf("localhost") !=-1? true: false) {
					String[] trozosRuta = rutaCompleta.split("\\\\");
					String nombrePDF = trozosRuta[trozosRuta.length-1];				
					templateValues.put("imprEscaneadaDcha", nombrePDF ); 
				} else {
					String[] trozosRuta = rutaCompleta.split("/");
					String nombrePDF = trozosRuta[trozosRuta.length-1];				
					templateValues.put("imprEscaneadaDcha", nombrePDF ); 
				}
				
				
			}else if(cartQueryParamsDTO.getLpathscan() != null && !cartQueryParamsDTO.getLpathscan().equals("") && !aplicaDerecho) {
				templateValues.put("imprEscaneadaDcha", NO_APLICA );
			}
			if(cartQueryParamsDTO.getLpathscan() != null && !cartQueryParamsDTO.getLpathscan().equals("")) {
				String rutaCompleta = cartQueryParamsDTO.getLpathscan();				
				if (CioneUtils.getURLHttps().indexOf("localhost") !=-1? true: false) {
					String[] trozosRuta = rutaCompleta.split("\\\\");
					String nombrePDF = trozosRuta[trozosRuta.length-1];				
					templateValues.put("imprEscaneadaIzda", nombrePDF ); 
				} else {
					String[] trozosRuta = rutaCompleta.split("/");
					String nombrePDF = trozosRuta[trozosRuta.length-1];				
					templateValues.put("imprEscaneadaIzda", nombrePDF ); 
				}
			}else if(cartQueryParamsDTO.getRpathscan() != null && !cartQueryParamsDTO.getRpathscan().equals("") && !aplicaIzquierdo) {
				templateValues.put("imprEscaneadaIzda", NO_APLICA );
			}
			if(cartQueryParamsDTO.isEnvioCorreoOrdinario()) {
				templateValues.put("envioPorCorreoOrdinario", "Envío por correo ordinario");
			}		
			if(cartQueryParamsDTO.getOtoscan()!= null) {
				templateValues.put("otocloudID", cartQueryParamsDTO.getOtoscan() );
			}
			
			
			/* Informacion adicional */
			if(cartQueryParamsDTO.getRinstrucciones() != null)
				templateValues.put("instrucEspecialesDcha", aplicaDerecho ? cartQueryParamsDTO.getRinstrucciones() : NO_APLICA); 
			else
				templateValues.put("instrucEspecialesDcha", aplicaDerecho ? "" : NO_APLICA); 
			if(cartQueryParamsDTO.getLinstrucciones() != null)
				templateValues.put("instrucEspecialesIzda",  aplicaIzquierdo ? cartQueryParamsDTO.getLinstrucciones() : NO_APLICA);
			else
				templateValues.put("instrucEspecialesIzda", aplicaIzquierdo ? "" : NO_APLICA);
			if(cartQueryParamsDTO.getRefTaller() != null)
				templateValues.put("refTaller", cartQueryParamsDTO.getRefTaller());
			
			SimpleFreemarkerHelper simpleFreemarkerHelper = new SimpleFreemarkerHelper(getClass(), false);
			Writer writer = new StringWriter();
			try {
				simpleFreemarkerHelper.render("cione-module/templates/pdf/pdf-configurador.ftl", templateValues, writer);
			} catch (TemplateException e) {
				e.printStackTrace();
			}	    
			String html = writer.toString();		 
			htmlToPdf(html, idClient, cartQueryParamsDTO.getNamePdfAudio(), cartQueryParamsDTO.getRefTaller());
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads given path's file and return its content
	 * @param Path to the file
	 * @param File encoding
	 * @return File content
	 * @throws IOException
	 
	private String readFile(String path, Charset encoding) throws IOException {
		
		InputStream inputStreamFile = getClass().getClassLoader().getResourceAsStream(path);
		byte[] encoded = IOUtils.toByteArray(inputStreamFile);
		
		return new String(encoded, encoding);
	}*/
	
	private void htmlToPdf(String inputHTML, String idClient, String pdfName, String refTaller) throws IOException {
		Document doc = html5ParseDocument(inputHTML);
		String outputPdf = cioneEcommerceConectionProvider.getConfigService().getConfig().getAudioLabConfiguradorPDFPath() 
					+ PATH_SEPARATOR + idClient + PATH_SEPARATOR + refTaller + PATH_SEPARATOR + pdfName;
		OutputStream os = null;
		try {
			os = new FileOutputStream(outputPdf);
			PdfRendererBuilder builder = new PdfRendererBuilder();
			builder.withUri(outputPdf);
			builder.toStream(os);
			String mgnlHome = mcp.getProperty("magnolia.home");
			String baseUri = mgnlHome + "/WEB-INF/fonts/Lato-Regular.ttf";
			log.debug(baseUri);
			builder.useFastMode();
			builder.useFont(new File(baseUri), "Lato");
			builder.withW3cDocument(doc, baseUri);
			builder.run();
			System.out.println("PDF generation completed");
			    os.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			if (os!=null)
				os.close();
			e.printStackTrace();
		} finally {
			if (os!=null) {
				os.close();
			}
			
		}
	}
	
	private Document html5ParseDocument(String inputHTML) throws IOException{
		org.jsoup.nodes.Document doc;
		//doc = Jsoup.parse(new File(inputHTML), "UTF-8");
		doc = Jsoup.parse(inputHTML, "UTF-8");
		return new W3CDom().fromJsoup(doc);
	}
	
	private String createDir(String refTaller) {
		String idClient = CioneUtils.getIdCurrentClientERP();
		String dir_primer_nivel = cioneEcommerceConectionProvider.getConfigService().getConfig().getAudioLabConfiguradorPDFPath();
		String dir_segundo_nivel = dir_primer_nivel+PATH_SEPARATOR+idClient;
		String dir_tercer_nivel = dir_segundo_nivel+PATH_SEPARATOR+refTaller;
		
		try {
			File files = new File(dir_primer_nivel);
			if (!files.exists()) {
				files.mkdir();
				getGroupPermission(files);
				log.debug("Creado directorio " + dir_primer_nivel);
				File file_second = new File(dir_segundo_nivel);
				file_second.mkdir();
				getGroupPermission(file_second);
				log.debug("Creado directorio " + dir_segundo_nivel);
				File file_third = new File(dir_tercer_nivel);
				file_third.mkdir();
				getGroupPermission(file_third);
				log.debug("Creado directorio " + dir_tercer_nivel);
			} else {
				File file_second = new File(dir_segundo_nivel);
				if (!file_second.exists()) {
					file_second.mkdir();
					getGroupPermission(file_second);
					log.debug("Creado directorio " + dir_segundo_nivel);
			    	File file_third = new File(dir_tercer_nivel);
			    	file_third.mkdir();
			    	getGroupPermission(file_third);
			    	log.debug("Creado directorio " + dir_tercer_nivel);
			    } else {
			    	File file_third = new File(dir_tercer_nivel);
			    	file_third.mkdir();
			    	getGroupPermission(file_third);
			    	log.debug("Creado directorio " + dir_tercer_nivel);
			    }
			}
		} catch (Exception e) {
			log.error("ERROR al crear el directorio " + e.getMessage());
		}
        return dir_tercer_nivel;
	}
	
	private void getGroupPermission(File file) {
		file.setReadable(true, false);
    	file.setWritable(true, false);
    	file.setExecutable(true, false);
	}
	
	@Override
	public VariantDTO getInfoVariant(String sku) {
		
		VariantDTO variantResult = new VariantDTO();
		variantResult.setSku(sku);
		String grupoPrecio = commerceToolsService.getGrupoPrecioCommerce();
		
		ProductProjection product = commercetoolsServiceAux.getProductBySkuFilter(sku, grupoPrecio);
		if (product != null) {
			variantResult.setExist(true);
		} else {
			return variantResult;
		}
		
		try {
			
			List<String> listMarcas = categoryService.getIdCategoriesByParent("MARCAS");
			
			//El listado solo debería devolver un unico registro
			//for (ProductProjection product : productsSearch) {
				if (product.getDescription() != null)
					variantResult.setDescripcion(product.getDescription().get(MyshopConstants.esLocale));
				
				List<CategoryReference> refCategories = product.getCategories();
				//variant.setProductProjection(producto);
				for(CategoryReference ref: refCategories) {
				//for( Iterator<Reference<Category>> it = refCategories.iterator(); it.hasNext();) { 
					//Reference<Category> ref = (Reference<Category>)it.next();	
					//buscamos en el listado de marcas
					if (listMarcas.contains(ref.getId())) {
						Category categoria = categoryUtils.getCategoryById(ref.getId());
						if (categoria.getCustom() != null) {
							variantResult.setLogoMarca(CioneUtils.getURLHttps() + categoria.getCustom().getFields().values().get("logoProducto"));
						}
						else {
							variantResult.setLogoMarca(CioneUtils.getURLHttps() + "/" + MyshopConstants.defaultCategoryLogo(MgnlContext.getLocale()));
						}
					} else {
						//variantResult.setLogoMarca(MyshopConstants.defaultLogo);
					}
				}
				
				ProductVariant variant = commercetoolsServiceAux.findVariantBySku(product, sku);
				//ProductVariant variant = product.findVariantBySku(sku).get();	
				
				if (MyShopUtils.hasAttribute("nombreArticulo", variant.getAttributes())) {
					variantResult.setNombreArticulo((String) MyShopUtils.findAttribute("nombreArticulo", variant.getAttributes()).getValue());
				} else {
	    			//variantResult.setNombreArticulo(product.getName().get(MgnlContext.getLocale()));
	    			variantResult.setNombreArticulo(product.getName().get(MyshopConstants.esLocale));
	    		}
				
				if (MyShopUtils.hasAttribute("aliasEkon", variant.getAttributes()))
					variantResult.setAliasEkon((String) MyShopUtils.findAttribute("aliasEkon", variant.getAttributes()).getValue());
				
				if (MyShopUtils.hasAttribute("codigoCentral", variant.getAttributes()))
					variantResult.setCodigoCentral((String) MyShopUtils.findAttribute("codigoCentral", variant.getAttributes()).getValue());
				
				variantResult.setGruposPrecio(commerceToolsService.getGrupoPrecioCommerce());
				
				boolean tienePromociones = false;
				if (MyShopUtils.hasAttribute("tienePromociones", variant.getAttributes()))
					tienePromociones = (Boolean) MyShopUtils.findAttribute("tienePromociones", variant.getAttributes()).getValue();
				if (!tienePromociones) { 
					
					Price price = commercetoolsServiceAux.getPriceBycustomerGroup(variantResult.getGruposPrecio(), variant.getPrices());
					variantResult.setPvo(MyShopUtils.formatTypedMoney(price.getValue()));
//					List<Price> prices = variant.getPrices();
//					for (Price price : prices) {
//						variantResult.setPvo(MyShopUtils.formatTypedMoney(price.getValue()));
//					}
				}else {
					/* Cuando el precio tenga promociones hay que calcularlo */
					Price price = commercetoolsServiceAux.getPriceBycustomerGroup(variantResult.getGruposPrecio(), variant.getPrices());
					
					variantResult.setTipoPromocion("sin-promo");
		    		//for (Price price : prices) {
	    			//if (price.getCustomerGroup() == null) {
	    				//del listado de precios nos quedamos con el señalado con Any
	    				//price to Money
    				variantResult.setPvo(MyShopUtils.formatTypedMoney(price.getValue()));
    				if (variantResult.isTienePromociones() || variantResult.isTieneRecargo()) {
    					String pvoOrigin = MyShopUtils.formatTypedMoney(price.getValue());
    					List<PromocionesDTO> listPromos = promocionesDao.getPromociones(variantResult);
    					List<PromocionesDTO> listPromosAux = new ArrayList<PromocionesDTO>();
    					if (listPromos.size() > 1) {
    						//aplicamos escalado
    						variantResult.setPvoDto(variantResult.getPvo());
    						variantResult.setTipoPromocion("escalado");
    						int i =0;
    						for (PromocionesDTO promo: listPromos) {
    							promo.setId(i);
    							i++;
    							double pvodto =0;
    							if (promo.getTipo_descuento() == MyshopConstants.dtoPorcentaje) {
    								double dto= ((100-promo.getValor())/100);
	    							pvodto = Double.valueOf(variantResult.getPvo().replace(',', '.')) * dto;
	    							variantResult.setValorDescuento(String.valueOf(promo.getValor()).replace('.', ','));
	    						} else {
	    							pvodto = Double.valueOf(variantResult.getPvo().replace(',', '.')) - promo.getValor();
	    							variantResult.setValorDescuento(String.valueOf(promo.getValor()).replace('.', ','));
	    						}
    							promo.setPvo(pvoOrigin);
    							pvodto = MyShopUtils.round(pvodto, 2);
    							promo.setPvoDto(String.valueOf(pvodto).replace('.', ','));
    							listPromosAux.add(promo);
    							
    							if (promo.getCantidad_desde()==1) {
    								variantResult.setPvoDto(promo.getPvoDto());
    							}
    						}
    						variantResult.setListPromos(listPromosAux);
    					} else if (listPromos.size() == 1) {
    						//si devuelve un unico resultado solo hay una promo
	    						PromocionesDTO promo = listPromos.get(0);
	    						double pvodto =0;
	    						if (promo.getTipo_descuento() == MyshopConstants.dtoPorcentaje) { // si es por porcentaje
	    							variantResult.setTipoPromocion("porcentaje");
	    							double dto= ((100-promo.getValor())/100);
	    							pvodto = Double.valueOf(variantResult.getPvo().replace(',', '.')) * dto;
	    							variantResult.setValorDescuento(String.valueOf(promo.getValor()).replace('.', ','));
	    						} else {
	    							variantResult.setTipoPromocion("valor");
	    							pvodto = Double.valueOf(variantResult.getPvo().replace(',', '.')) - promo.getValor();
	    							variantResult.setValorDescuento(String.valueOf(promo.getValor()).replace('.', ','));
	    						}
	    						pvodto = MyShopUtils.round(pvodto, 2);
	    						variantResult.setPvoDto(String.valueOf(pvodto).replace('.', ','));
    							if (promo.getValor() < 0) { //es un incremento, no lo marcamos como promocion pero actualizamos su pvo con el incremento
    								variantResult.setPvo(String.valueOf(pvodto).replace('.', ','));
    								variantResult.setTienePromociones(false);
    								variantResult.setPvoIncremento(variantResult.getPvo());
    							} 
    					} else { //si no hay promociones para ese grupo de precio 
    						variantResult.setTienePromociones(false);
    					}
    					
    				}
//	    			} else {
//	    				/*Comentada la logica en caso de que metan precios por grupo de usuarios 
//	    				String customerid = commercetoolsService.getIdOfCustomerGroupByCostumerId(CioneUtils.getIdCurrentClient());
//	    				if (price.getCustomerGroup().getId().equals(customerid)) {
//	    					variantResult.setPvo(MyShopUtils.formatTypedMoney(price.getValue()));
//	    				}*/
//	    				variantResult.setPvo(MyShopUtils.formatTypedMoney(price.getValue()));
//	    			}
		    		//}
					
					
				}
			//}
				
		
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		
		
		return variantResult;
	}
		
}
