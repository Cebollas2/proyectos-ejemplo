package com.magnolia.cione.service;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.ConfigCione;
import com.magnolia.cione.exceptions.CioneRuntimeException;

import info.magnolia.init.MagnoliaConfigurationProperties;
import info.magnolia.objectfactory.Components;

public class ConfigServiceImpl implements ConfigService {

	private static final Logger log = LoggerFactory.getLogger(ConfigServiceImpl.class);
	
	private ConfigCione configCione= new ConfigCione();
	
//	@Override
//	public ConfigAuthCione getConfig() {
//		
//		ConfigAuthCione configAuthCione = new ConfigAuthCione();
//		
//		try {
//			Session session = MgnlContext.getJCRSession("config");			
//		    Node configNode = session.getNode("/modules/cione-module/config");		    
//		    //configAuthCione.setTestMode(configNode.getProperty(TEST_MODE).getBoolean());
//		    //configAuthCione.setTestEmail(configNode.getProperty(TEST_EMAIL).getString());
//		    //configAuthCione.setLoginTemplateRegisterMail(configNode.getProperty(LOGIN_TEMPLATE_REGISTER_MAIL).getString());
//		    //configAuthCione.setLoginTemplateRecoveryPasswordMail(configNode.getProperty(LOGIN_TEMPLATE_RECOVERY_PASS_MAIL).getString());
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error(e.getLocalizedMessage());		
//			throw new CioneRuntimeException("Error getting configuration of cione-module");
//		}		
//		return configAuthCione;
//	}


	public ConfigServiceImpl() {
		Properties prop = new Properties();				
		try {			
			//log.debug("Load configuration from properties");
			prop.load(ConfigServiceImpl.class.getClassLoader().getResourceAsStream("config.properties"));			
			configCione.setAuthTestMode(Boolean.valueOf(prop.getProperty(ConfigCione.AUTH_TEST_MODE)));
			configCione.setAuthTestEmail(prop.getProperty(ConfigCione.AUTH_TEST_EMAIL));
			configCione.setAuthSenderEmail(prop.getProperty(ConfigCione.AUTH_SENDER_EMAIL));
			configCione.setAuthSenderEmailPedidos(prop.getProperty(ConfigCione.AUTH_SENDER_EMAIL_PEDIDOS));
			configCione.setAuthAuthorPath(prop.getProperty(ConfigCione.AUTH_AUTHOR_PATH));
			configCione.setApiMiddlewarePath(prop.getProperty(ConfigCione.API_MIDDLEWARE_PATH));
			configCione.setApiMiddlewareUser(prop.getProperty(ConfigCione.API_MIDDLEWARE_USER));
			configCione.setApiMiddlewarePwd(prop.getProperty(ConfigCione.API_MIDDLEWARE_PWD));
			configCione.setInvoiceDocsPath(prop.getProperty(ConfigCione.INVOICE_DOCS_PATH));
			configCione.setPathAlbaranes(prop.getProperty(ConfigCione.PATH_ALBARANES));
			configCione.setPathFacturas(prop.getProperty(ConfigCione.PATH_FACTURAS));
			configCione.setPepperApiHost(prop.getProperty(ConfigCione.PEPPER_API_HOST));
			configCione.setPepperToken(prop.getProperty(ConfigCione.PEPPER_TOKEN));
			configCione.setPepperPassword(prop.getProperty(ConfigCione.PEPPER_PASSWORD));
			
			configCione.setApiCommercetoolsPath(prop.getProperty(ConfigCione.API_COMMERCETOOLS_PATH));
			configCione.setApiCommercetoolsAuthPath(prop.getProperty(ConfigCione.API_COMMERCETOOLS_AUTH_PATH));
			configCione.setApiCommercetoolsClientId(prop.getProperty(ConfigCione.API_COMMERCETOOLS_CLIENT_ID));
			configCione.setApiCommercetoolsClientSecret(prop.getProperty(ConfigCione.API_COMMERCETOOLS_CLIENT_SECRET));
			configCione.setApiCommercetoolsProjectKey(prop.getProperty(ConfigCione.API_COMMERCETOOLS_PROJECT_KEY));
			configCione.setApiCommercetoolsCustomerPwd(prop.getProperty(ConfigCione.API_COMMERCETOOLS_CUSTOMER_PWD));
			configCione.setDoofinderPath(prop.getProperty(ConfigCione.DOOFINDER_PATH));
			configCione.setAudioPath(prop.getProperty(ConfigCione.AUDIO_PATH));
			configCione.setAudioPsw(prop.getProperty(ConfigCione.AUDIO_PSW));
			configCione.setTaxCategoryKey(prop.getProperty(ConfigCione.TAX_CATEGORY_KEY));
			
			configCione.setApiFittingBoxPath(prop.getProperty(ConfigCione.API_FITTINGBOX_PATH));
			configCione.setApiFittingBoxClientId(prop.getProperty(ConfigCione.API_FITTINGBOX_CLIENT_ID));
			configCione.setApiFittingBoxClientSecret(prop.getProperty(ConfigCione.API_FITTINGBOX_CLIENT_SECRET));
			configCione.setApiFittingBoxApiKey(prop.getProperty(ConfigCione.API_FITTINGBOX_API_KEY));
			
			configCione.setPdfGeneratorPath(prop.getProperty(ConfigCione.PDF_GENERATOR_PATH));
			configCione.setAudioLabImpresionesPath(prop.getProperty(ConfigCione.AUDIO_LAB_IMPRESIONES_PATH));
			configCione.setAudioLabConfiguradorPDFPath(prop.getProperty(ConfigCione.AUDIOLAB_CONFIGURADOR_PDF_PATH));
			configCione.setPdfGeneratorBudgetPath(prop.getProperty(ConfigCione.PDF_GENERATOR_BUDGET_PATH));
			
			configCione.setUniversityAuthKey(prop.getProperty(ConfigCione.UNIVERSTITY_AUTH_KEY));
			configCione.setUniversityWstoken(prop.getProperty(ConfigCione.UNIVERSITY_WSTOKEN));
			
			configCione.setApiUrlServices(prop.getProperty(ConfigCione.API_URL_SERVICES));
			configCione.setApiUserServices(prop.getProperty(ConfigCione.API_USER_SERVICES));
			configCione.setApiPasswordServices(prop.getProperty(ConfigCione.API_PASSWORD_SERVICES));
			configCione.setApiCioneAuthServices(prop.getProperty(ConfigCione.API_CIONE_AUTH_SERVICES));
			
			
			//esto de magnolia
			
			try {
				MagnoliaConfigurationProperties configurationProperties = Components.getComponent(MagnoliaConfigurationProperties.class);
				configCione.setIsAuthor(Boolean.parseBoolean(configurationProperties.getProperty("magnolia.bootstrap.authorInstance")));
			}catch(Exception e) {
				log.warn("No se ha obtenido el MagnoliaConfigurationProperties para ver si la instancia es author o public");
			}
			  
		} catch (Exception e) {		
			log.error(e.getMessage());
			throw new CioneRuntimeException("Error getting configuration of cione-module (properties)");			
		}
	}
	
	
	@Override
	public ConfigCione getConfig() {		
		return configCione;
	}

}
