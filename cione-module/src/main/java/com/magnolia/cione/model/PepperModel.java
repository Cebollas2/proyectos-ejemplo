package com.magnolia.cione.model;

import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.PepperAccessTokenDTO;
import com.magnolia.cione.dto.PepperUrlDTO;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.service.MiddlewareService;
import com.magnolia.cione.service.PepperService;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.context.MgnlContext;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class PepperModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	private static final Logger log = LoggerFactory.getLogger(PepperModel.class);

	@Inject
	private PepperService pepperService;

	@Inject
	private MiddlewareService middlewareService;

	private String url;

	@Inject
	public PepperModel(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
	}

	@Override
	public String execute() {

		String iframe = MgnlContext.getParameter("iframe");
		if (StringUtils.isNotEmpty(iframe)) {

			// Obtenemos el identificador de usuario en pepper para el socio actual
			String userPepper = getUserPepper();
			if (StringUtils.isNotEmpty(userPepper)) {

				// Obtenemos el token de autorizacion del usuario en pepper
				PepperAccessTokenDTO accessTokenDTO = pepperService.getAccessToken(userPepper);
				if (isValidToken(accessTokenDTO)) {

					// Utilizamos el token para obtener la url del iframe
					PepperUrlDTO urlDTO = pepperService.getUrl(accessTokenDTO.getData().getAccess_token());
					if (isValidUrl(urlDTO)) {
						url = urlDTO.getData().getUrl();
						
					} else {
						log.error("La respuesta de url obtenida no es valida. Usuario Cione: " + CioneUtils.getIdCurrentClientERP()
						+ ", Usuario pepper: " + userPepper + ", Token: " + accessTokenDTO.getData().getAccess_token());
					}
				} else {
					log.error("La respuesta de token obtenida no es valida. Usuario Cione: " + CioneUtils.getIdCurrentClientERP()
							+ ", Usuario pepper: " + userPepper);
				}
			} else {
				log.error("No se ha podido obtener el usuario pepper para el socio actual: "
						+ CioneUtils.getIdCurrentClientERP());
			}
		}

		return super.execute();
	}

	/**
	 * Hace una llamada al servicio /datossocio para obtener el campo usuarioPepper
	 * 
	 * @return El codigo de usuario pepper
	 */
	private String getUserPepper() {
		String userPepper = null;
		/*UserERPCioneDTO datosUsuario = middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
		if(datosUsuario != null)
			userPepper = datosUsuario.getSocio().getUsuarioPepper();*/
		
		return userPepper;
	}

	public String getUrl() {
		return url;
	}

	/**
	 * Comprueba que el objeto PepperAccessTokenDTO no es nulo y tiene un token en
	 * su interior
	 * 
	 * @param accessTokenDTO Objeto que se evaluara
	 * @return Verdadero si el objeto contiene el token, falso si no
	 */
	private boolean isValidToken(PepperAccessTokenDTO accessTokenDTO) {
		boolean result = true;

		// Si ha habiado errores
		if (accessTokenDTO == null || accessTokenDTO.getResult() == null
				|| accessTokenDTO.getResult().getErrors() != null) {
			result = false;
		}
		// Si no existe el token en la respuesta
		if (accessTokenDTO.getData() == null || StringUtils.isEmpty(accessTokenDTO.getData().getAccess_token())) {
			result = false;
		}

		return result;
	}

	/**
	 * Comprueba que el objeto PepperUrlDTO no es nulo y tiene una url en su
	 * interior
	 * 
	 * @param PepperUrlDTO Objeto que se evaluara
	 * @return Verdadero si el objeto contiene la url, falso si no
	 */
	private boolean isValidUrl(PepperUrlDTO urlDTO) {
		boolean result = true;

		// Si ha habiado errores
		if (urlDTO == null || urlDTO.getResult() == null || urlDTO.getResult().getErrors() != null) {
			result = false;
		}
		// Si no existe el token en la respuesta
		if (urlDTO.getData() == null || StringUtils.isEmpty(urlDTO.getData().getUrl())) {
			result = false;
		}

		return result;
	}

}
