package com.magnolia.cione.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.w3c.dom.Document;

import com.magnolia.cione.dto.LineaEnvioDTO;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.setup.CioneEcommerceConectionProvider;
import com.magnolia.cione.utils.CioneUtils;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import info.magnolia.context.MgnlContext;
import info.magnolia.init.MagnoliaConfigurationProperties;

public class PDFGeneratorServiceImpl implements PDFGeneratorService {
	
	@Inject
	private MiddlewareService middlewareService; 
	
	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider; 

	@Inject
	private MagnoliaConfigurationProperties mcp;
	
	@Override
	public File getFileSending(String tracking, String agencia, String tipoEnvio) {
		
		File file = new File(cioneEcommerceConectionProvider.getConfigService().getConfig().getPdfGeneratorPath() + tracking + ".pdf");
		if (!file.exists()) {
			createHtml(tracking, agencia, tipoEnvio);
			file = new File(cioneEcommerceConectionProvider.getConfigService().getConfig().getPdfGeneratorPath() + tracking + ".pdf");
		}
		return file;
	}
	
	@Override
	public void createHtml(String trackingPedido, String agencia, String tipoEnvio) {
		String originalBrowserURL = MgnlContext.getWebContext().getRequest().getRequestURL().toString();//MgnlContext.getAggregationState().getOriginalBrowserURL();
		URL url;
		String https = CioneUtils.getURLHttps();
		URL urlhttps;
		try {
			urlhttps = new URL(https);
			url = new URL(originalBrowserURL);
			String contextPath = MgnlContext.getContextPath();
			String logoPath = "/.resources/cione-theme/webresources/img/cione.png";
			String html = readFile("cione-module/templates/pdf/pdf-services-template.html", StandardCharsets.UTF_8);
			
			html = html.replace("$myCioneLogo", urlhttps.getProtocol() + "://" +  url.getAuthority() + contextPath + logoPath);
			html = html.replace("$host", CioneUtils.getURLHttps() + logoPath);
			//html = html.replace("$host", urlhttps.getProtocol() + "://" +  url.getAuthority() + contextPath);
			html = html.replace("$CIF", "B82396235");
			html = html.replace("$telefono", "91 640 29 87");
			html = html.replace("$direccion", "JOSE ECHEGARAY Nº7");
			html = html.replace("$fax", "91 640 29 87");
			
			html = html.replace("$trackingPedido", trackingPedido);
			html = html.replace("$agencia", agencia);
			html = html.replace("$tipoEnvio", tipoEnvio);
			String idSocio = CioneUtils.getIdCurrentClientERP();
			
			/*EnvioQueryParamsDTO filter = new EnvioQueryParamsDTO();
			filter.setTrackingPedido(trackingPedido);
			PaginaEnviosDTO paginaEnvios = middlewareService.getEnvios(idSocio, filter);
			String envio_html = "";
			if ((paginaEnvios != null) && !paginaEnvios.getEnvios().isEmpty()) {
				List<EnvioDTO> envios = paginaEnvios.getEnvios();
				
				for (EnvioDTO envio: envios) {
					envio_html += envio.getNumEnvio() + ",";
				}
			}
			
			html = html.replace("$listaenvios", envio_html);*/
			
			
			//datos socio
			
			UserERPCioneDTO data = middlewareService.getUserFromERP(idSocio);
			if (data != null) {
				html = html.replace("$nombreSocio", data.getNombreComercial());
				html = html.replace("$dirSocio", data.getDireccion());
				html = html.replace("$provincia", data.getProvincia());
				html = html.replace("$ciudad", data.getPoblacion());
				html = html.replace("$tlfSocio", data.getTelefono());
				html = html.replace("$email", data.getEmail());
			}
			
			//tabla envios
			List<LineaEnvioDTO> lineasEnvio = 
					middlewareService.getLineasEnvio(CioneUtils.getIdCurrentClientERP(), null, trackingPedido);
			String lineasEnvio_html = "";
			for (LineaEnvioDTO linea: lineasEnvio) {
				lineasEnvio_html += "<tr class=\"table-body\">";
				lineasEnvio_html += "<td width=\"225\">" + linea.getArticulo() + "</td>";
				lineasEnvio_html += "<td>" + linea.getUnidades() + "</td>";
				lineasEnvio_html += "<td>" + linea.getImporteBruto() + "</td>";
				lineasEnvio_html += "<td>" + linea.getDescuento() + "</td>";
				lineasEnvio_html += "<td>" + linea.getImporteVenta() + "</td>";
				lineasEnvio_html += "<td>" + linea.getReferenciaWeb() + "</td>";
				lineasEnvio_html += "<td>" + linea.getReferenciaTaller() + "</td>";
				lineasEnvio_html += "<td>" + linea.getAlbaran() + "</td>";
				lineasEnvio_html += "<td width=\"60\">" + linea.getFechaAlbaranView() + "</td>";
				lineasEnvio_html += "</tr>";
			}
			
			html = html.replace("$listaEnvios", lineasEnvio_html);
			
			
			// Source HTML file
		    //String inputHTML = "F:\\knpcode\\Java\\Java Programs\\PDF using Java\\PDFBox\\MyPage.html";
		    // Generated PDF file name
		    
		    htmlToPdf(html, trackingPedido);
			
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
	 */
	private String readFile(String path, Charset encoding) throws IOException {
		
		InputStream inputStreamFile = getClass().getClassLoader().getResourceAsStream(path);
		byte[] encoded = IOUtils.toByteArray(inputStreamFile);
		
		return new String(encoded, encoding);
	}
	
	private void htmlToPdf(String inputHTML, String tracking) throws IOException {
		Document doc = html5ParseDocument(inputHTML);
		String outputPdf = cioneEcommerceConectionProvider.getConfigService().getConfig().getPdfGeneratorPath() + tracking + ".pdf";
		
		try {
			
			/*String baseUri = FileSystems.getDefault()
					.getPath("F:/", "knpcode/Java/", "Java Programs/PDF using Java/PDFBox/")
					.toUri()
					.toString();*/
			OutputStream os = new FileOutputStream(outputPdf);
			PdfRendererBuilder builder = new PdfRendererBuilder();
			builder.withUri(outputPdf);
			builder.toStream(os);
			// using absolute path here
			String mgnlHome = mcp.getProperty("magnolia.home");
			String baseUri = mgnlHome + "/WEB-INF/fonts/ArialMT.ttf";
			/*String baseUrl = getClass()
		                .getProtectionDomain()
		                .getCodeSource()
		                .getLocation()
		                .toString();*/
			builder.useFastMode();
			builder.useFont(new File(baseUri), "ArialMT");
			builder.withW3cDocument(doc, baseUri);
			//builder.withHtmlContent(inputHTML, baseUrl);
			//builder.useUriResolver(new MyResolver());
			builder.run();
			System.out.println("PDF generation completed");
			    os.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Document html5ParseDocument(String inputHTML) throws IOException{
		org.jsoup.nodes.Document doc;
		//doc = Jsoup.parse(new File(inputHTML), "UTF-8");
		doc = Jsoup.parse(inputHTML, "UTF-8");
		return new W3CDom().fromJsoup(doc);
	}

}
