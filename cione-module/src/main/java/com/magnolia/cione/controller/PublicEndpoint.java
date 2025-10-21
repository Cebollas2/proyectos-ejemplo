package com.magnolia.cione.controller;

import java.io.File;
import java.security.Key;
import java.util.Base64;
import java.util.StringTokenizer;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.ParamsDTO;
import com.magnolia.cione.setup.CioneEcommerceConectionProvider;

import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;
//import io.swagger.annotations.Api;

//@Api(value = "/public")
@Path("/public")
public class PublicEndpoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider;
	private static final String PATH_SEPARATOR="/";
	private static final Logger log = LoggerFactory.getLogger(PublicEndpoint.class);
	
	@Inject
	protected PublicEndpoint(D endpointDefinition, SimpleTranslator i18n) {
		super(endpointDefinition);
	}

	@POST
	@Path("/audiolabPDF")
	@Produces("application/octet-stream")
	public Response getPDFConfigurador(@Valid ParamsDTO params){
		try {
			String clave = encript(params.getRefTaller());
			log.debug(clave);
			
			String desencriptar = decrypt(params.getKey());
			
			if ((params.getNameFile()!=null) && (!params.getNameFile().isEmpty())) {
				String tipo = "audiolab";
				if (params.getNameFile().indexOf("impresion_L") !=-1? true: false)
					tipo="impresion_L";
				if (params.getNameFile().indexOf("impresion_R") !=-1? true: false)
					tipo="impresion_R";
				
				StringTokenizer st = new StringTokenizer(params.getNameFile(), "-");
				String codSocio = "";
				if (st.hasMoreTokens())
					codSocio = st.nextToken();
				else {
					log.error("El nombre del fichero no es valido");
					return Response.status(Response.Status.BAD_REQUEST).entity("El nombre del fichero no es valido").build();
				}
				if (desencriptar.equals(params.getRefTaller())) {
					
					String path = cioneEcommerceConectionProvider.getConfigService().getConfig().getAudioLabConfiguradorPDFPath();
					if (tipo.equals("audiolab")) {
						File file = new File(path + PATH_SEPARATOR + codSocio + PATH_SEPARATOR 
								+ PATH_SEPARATOR + params.getRefTaller() + PATH_SEPARATOR + params.getNameFile());
						if ((file != null) && file.exists()) {
							ResponseBuilder response = Response.ok((Object) file);
							response.header("Content-Disposition", "attachment; filename=\""+ file.getName() + "\"");
							return response.build();
						} else {
							return Response.status(Response.Status.NOT_FOUND).entity("El documento no está disponible").build();
						}
					}
					else {
						//impresiones, buscamos por cualquier extension
						path = cioneEcommerceConectionProvider.getConfigService().getConfig().getAudioLabImpresionesPath();
					    File directoryPath = new File(path + PATH_SEPARATOR + codSocio + PATH_SEPARATOR 
								+ PATH_SEPARATOR + params.getRefTaller());
					    File filesList[] = directoryPath.listFiles();
					    File fileImp = null;
					    for(File file : filesList) {
					    	System.out.println("File name: "+file.getName());
					    	System.out.println("File path: "+file.getAbsolutePath());
					    	if (file.getName().indexOf(tipo) !=-1? true: false) {
					    		fileImp = new File(file.getPath());
					    	}
					    }
					    if (fileImp != null) {
					    	ResponseBuilder response = Response.ok((Object) fileImp);
							response.header("Content-Disposition", "attachment; filename=\""+ fileImp.getName() + "\"");
							return response.build();
					    } else {
					    	return Response.status(Response.Status.NOT_FOUND).entity("El documento no está disponible").build();
					    }
					}
					
				} else {
					log.error("Clave no permitida");
					return Response.status(Response.Status.FORBIDDEN).entity("Clave no permitida").build();
				}
			} else {
				log.error("El nombre del fichero es obligatorio");
				return Response.status(Response.Status.BAD_REQUEST).entity("El nombre del fichero es obligatorio").build();
			}
		} catch (Exception e) {
			log.error("ERROR al desencriptar la clave " + e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/audiolabPDFGET")
	@Produces("application/octet-stream")
	public Response getPDFConfiguradorGET(@QueryParam ("nameFile") String nameFile, 
			@QueryParam ("refTaller") String refTaller, @QueryParam ("key") String key){
		try {
//			String clave = encript(refTaller);
			log.debug(encript(refTaller));
//			
			String desencriptar = decrypt(key);
			
			if ((nameFile!=null) && (!nameFile.isEmpty())) {
				String tipo = "audiolab";
				if (nameFile.indexOf("impresion_L") !=-1? true: false)
					tipo="impresion_L";
				if (nameFile.indexOf("impresion_R") !=-1? true: false)
					tipo="impresion_R";
				
				StringTokenizer st = new StringTokenizer(nameFile, "-");
				String codSocio = "";
				if (st.hasMoreTokens())
					codSocio = st.nextToken();
				else {
					log.error("El nombre del fichero no es valido");
					return Response.status(Response.Status.BAD_REQUEST).entity("El nombre del fichero no es valido").build();
				}
				if (desencriptar.equals(refTaller)) {
					
					String path = cioneEcommerceConectionProvider.getConfigService().getConfig().getAudioLabConfiguradorPDFPath();
					if (tipo.equals("audiolab")) {
						File file = new File(path + PATH_SEPARATOR + codSocio + PATH_SEPARATOR 
								+ PATH_SEPARATOR + refTaller + PATH_SEPARATOR
								+ nameFile);
						if ((file != null) && file.exists()) {
							ResponseBuilder response = Response.ok((Object) file);
							response.header("Content-Disposition", "attachment; filename=\""+ file.getName() + "\"");
							return response.build();
						} else {
							return Response.status(Response.Status.NOT_FOUND).entity("El documento no está disponible").build();
						}
					}
					else {
						//impresiones, buscamos por cualquier extension
						path = cioneEcommerceConectionProvider.getConfigService().getConfig().getAudioLabImpresionesPath();
					    File directoryPath = new File(path + PATH_SEPARATOR + codSocio + PATH_SEPARATOR 
								+ PATH_SEPARATOR + refTaller);
					    File filesList[] = directoryPath.listFiles();
					    File fileImp = null;
					    for(File file : filesList) {
					    	System.out.println("File name: "+file.getName());
					    	System.out.println("File path: "+file.getAbsolutePath());
					    	if (file.getName().indexOf(tipo) !=-1? true: false) {
					    		fileImp = new File(file.getPath());
					    	}
					    }
					    if (fileImp != null) {
					    	ResponseBuilder response = Response.ok((Object) fileImp);
							response.header("Content-Disposition", "attachment; filename=\""+ fileImp.getName() + "\"");
							return response.build();
					    } else {
					    	return Response.status(Response.Status.NOT_FOUND).entity("El documento no está disponible").build();
					    }
					}
					
				} else {
					log.error("Clave no permitida");
					return Response.status(Response.Status.FORBIDDEN).entity("Clave no permitida").build();
				}
			} else {
				log.error("El nombre del fichero es obligatorio");
				return Response.status(Response.Status.BAD_REQUEST).entity("El nombre del fichero es obligatorio").build();
			}
		} catch (Exception e) {
			log.error("ERROR al desencriptar la clave " + e.getMessage());
			return Response.status(Response.Status.FORBIDDEN).entity("ERROR al desencriptar la clave").build();
		}
	}
	
	/*@GET
	@Path("/audiolabPDFGET2")
	@Produces("application/pdf")
	public Response getPDFConfiguradorGET2(@Context UriInfo uriInfo){

	}*/
	
	private String encript(String refTaller) throws Exception {	
		String ENCRYPT_KEY = "RUmFT2LhZMnTjHLo";
		
		Key aesKey = new SecretKeySpec(ENCRYPT_KEY.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, aesKey);

		byte[] encrypted = cipher.doFinal(refTaller.getBytes());
			
		return Base64.getEncoder().encodeToString(encrypted);
		
	}

	private String decrypt(String key) throws Exception {
		String ENCRYPT_KEY = "RUmFT2LhZMnTjHLo";
		
		byte[] encryptedBytes=Base64.getDecoder().decode( key.replace("\n", "") );
		Key aesKey = new SecretKeySpec(ENCRYPT_KEY.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, aesKey);

		String decrypted = new String(cipher.doFinal(encryptedBytes));
	        
		return decrypted;
	}

}
