package com.magnolia.cione.dto;

public class ConfigCione {

	public static final String AUTH_TEST_MODE = "auth.test";
	public static final String AUTH_TEST_EMAIL = "auth.test-email";
	public static final String AUTH_SENDER_EMAIL = "auth.sender-email";
	public static final String AUTH_SENDER_EMAIL_PEDIDOS = "auth.sender-email-pedidos";
	public static final String AUTH_AUTHOR_PATH = "auth.author-path";

	public static final String API_MIDDLEWARE_PATH = "api.middleware-path";
	public static final String API_MIDDLEWARE_USER = "api.middleware-user";
	public static final String API_MIDDLEWARE_PWD = "api.middleware-pwd";
	public static final String INVOICE_DOCS_PATH = "invoice.docs-path";
	public static final String API_ELASTIC_PATH = "api.elastic-path";

	public static final String PATH_ALBARANES = "path.albaranes.dest";
	public static final String PATH_FACTURAS = "path.facturas.dest";

	public static final String PEPPER_API_HOST = "pepper.api.host";
	public static final String PEPPER_TOKEN = "pepper.token";
	public static final String PEPPER_PASSWORD = "pepper.password";
	
	public static final String API_COMMERCETOOLS_PATH = "api.commercetools-path";
	public static final String API_COMMERCETOOLS_AUTH_PATH = "api.commercetools-auth-path";
	public static final String API_COMMERCETOOLS_CLIENT_ID = "api.commercetools-client-id";
	public static final String API_COMMERCETOOLS_CLIENT_SECRET = "api.commercetools-client-secret";
	public static final String API_COMMERCETOOLS_PROJECT_KEY = "api.commercetools-project-key";
	public static final String API_COMMERCETOOLS_CUSTOMER_PWD = "api.commercetools-customer-pwd";

	public static final String API_FITTINGBOX_PATH = "api.fittingbox-path";
	public static final String API_FITTINGBOX_CLIENT_ID = "api.fittingbox-client-id";
	public static final String API_FITTINGBOX_CLIENT_SECRET = "api.fittingbox-client-secret";
	public static final String API_FITTINGBOX_API_KEY = "api.fittingbox-api-key";
	
	public static final String DOOFINDER_PATH = "doofinder.path";
	
	public static final String AUDIO_PATH = "audio.path";
	public static final String AUDIO_PSW = "audio.psw";
	
	public static final String PDF_GENERATOR_PATH = "pdf.generator.path";
	public static final String PDF_GENERATOR_BUDGET_PATH = "pdf.generator-budget.path";
	
	public static final String TAX_CATEGORY_KEY = "taxCategory.key";
	
	public static final String AUDIO_LAB_IMPRESIONES_PATH = "audiolab.impresiones.path";
	public static final String AUDIOLAB_CONFIGURADOR_PDF_PATH = "audiolab.configurador.pdf.path";
	
	public static final String UNIVERSTITY_AUTH_KEY = "university.auth-key";
	public static final String UNIVERSITY_WSTOKEN = "university.wstoken";
	
	public static final String API_URL_SERVICES = "api.url.services";
	public static final String API_USER_SERVICES = "api.user.services";
	public static final String API_PASSWORD_SERVICES = "api.password.services";
	public static final String API_CIONE_AUTH_SERVICES = "api.cione-auth.services";

	private Boolean authTestMode;
	private String authTestEmail;
	private String authSenderEmail;
	private String authSenderEmailPedidos;
	private String authAuthorPath;
	private String apiMiddlewarePath;
	private String apiMiddlewareUser;
	private String apiMiddlewarePwd;
	private String invoiceDocsPath;
	private String pathAlbaranes;
	private String pathFacturas;
	private String apiElasticPath;
	private String pepperApiHost;
	private String pepperToken;
	private String pepperPassword;
	private String apiCommercetoolsPath;
	private String apiCommercetoolsAuthPath;
	private String apiCommercetoolsClientId;
	private String apiCommercetoolsClientSecret;
	private String apiCommercetoolsProjectKey;
	private String apiCommercetoolsCustomerPwd;
	private String apiFittingBoxPath;
	private String apiFittingBoxClientId;
	private String apiFittingBoxClientSecret;
	private String apiFittingBoxApiKey;
	private String doofinderPath;
	private String audioPath;
	private String audioPsw;
	private String taxCategoryKey;
	private String pdfGeneratorPath;
	private String audioLabImpresionesPath;
	private String audioLabConfiguradorPDFPath;
	private String universityAuthKey;
	private String universityWstoken;
	private String apiUrlServices;
	private String apiUserServices;
	private String apiPasswordServices;
	private String apiCioneAuthServices;

	private String pdfGeneratorBudgetPath;
	private Boolean isAuthor;
	
	public String getApiFittingBoxPath() {
		return apiFittingBoxPath;
	}

	public void setApiFittingBoxPath(String apiFittingBoxPath) {
		this.apiFittingBoxPath = apiFittingBoxPath;
	}

	public String getApiFittingBoxClientId() {
		return apiFittingBoxClientId;
	}

	public void setApiFittingBoxClientId(String apiFittingBoxClientId) {
		this.apiFittingBoxClientId = apiFittingBoxClientId;
	}

	public String getApiFittingBoxClientSecret() {
		return apiFittingBoxClientSecret;
	}

	public void setApiFittingBoxClientSecret(String apiFittingBoxClientSecret) {
		this.apiFittingBoxClientSecret = apiFittingBoxClientSecret;
	}

	public String getApiFittingBoxApiKey() {
		return apiFittingBoxApiKey;
	}

	public void setApiFittingBoxApiKey(String apiFittingBoxApiKey) {
		this.apiFittingBoxApiKey = apiFittingBoxApiKey;
	}

	public Boolean getAuthTestMode() {
		return authTestMode;
	}

	public void setAuthTestMode(Boolean authTestMode) {
		this.authTestMode = authTestMode;
	}

	public String getAuthTestEmail() {
		return authTestEmail;
	}

	public void setAuthTestEmail(String authTestEmail) {
		this.authTestEmail = authTestEmail;
	}

	public String getApiMiddlewarePath() {
		return apiMiddlewarePath;
	}

	public void setApiMiddlewarePath(String apiMiddlewarePath) {
		this.apiMiddlewarePath = apiMiddlewarePath;
	}

	public String getApiMiddlewareUser() {
		return apiMiddlewareUser;
	}

	public void setApiMiddlewareUser(String apiMiddlewareUser) {
		this.apiMiddlewareUser = apiMiddlewareUser;
	}

	public String getApiMiddlewarePwd() {
		return apiMiddlewarePwd;
	}

	public void setApiMiddlewarePwd(String apiMiddlewarePwd) {
		this.apiMiddlewarePwd = apiMiddlewarePwd;
	}

	public String getInvoiceDocsPath() {
		return invoiceDocsPath;
	}

	public void setInvoiceDocsPath(String invoiceDocsPath) {
		this.invoiceDocsPath = invoiceDocsPath;
	}

	public String getAuthSenderEmail() {
		return authSenderEmail;
	}

	public void setAuthSenderEmail(String authSenderEmail) {
		this.authSenderEmail = authSenderEmail;
	}

	public String getPathAlbaranes() {
		return pathAlbaranes;
	}

	public void setPathAlbaranes(String pathAlbaranes) {
		this.pathAlbaranes = pathAlbaranes;
	}

	public String getPathFacturas() {
		return pathFacturas;
	}

	public void setPathFacturas(String pathFacturas) {
		this.pathFacturas = pathFacturas;
	}

	public String getApiElasticPath() {
		return apiElasticPath;
	}

	public void setApiElasticPath(String apiElasticPath) {
		this.apiElasticPath = apiElasticPath;
	}

	public String getAuthAuthorPath() {
		return authAuthorPath;
	}

	public void setAuthAuthorPath(String authAuthorPath) {
		this.authAuthorPath = authAuthorPath;
	}

	public Boolean getIsAuthor() {
		return isAuthor;
	}

	public void setIsAuthor(Boolean isAuthor) {
		this.isAuthor = isAuthor;
	}

	public String getPepperApiHost() {
		return pepperApiHost;
	}

	public void setPepperApiHost(String pepperApiHost) {
		this.pepperApiHost = pepperApiHost;
	}

	public String getPepperToken() {
		return pepperToken;
	}

	public void setPepperToken(String pepperToken) {
		this.pepperToken = pepperToken;
	}

	public String getPepperPassword() {
		return pepperPassword;
	}

	public void setPepperPassword(String pepperPassword) {
		this.pepperPassword = pepperPassword;
	}

	public String getApiCommercetoolsPath() {
		return apiCommercetoolsPath;
	}

	public void setApiCommercetoolsPath(String apiCommercetoolsPath) {
		this.apiCommercetoolsPath = apiCommercetoolsPath;
	}

	public String getApiCommercetoolsAuthPath() {
		return apiCommercetoolsAuthPath;
	}

	public void setApiCommercetoolsAuthPath(String apiCommercetoolsAuthPath) {
		this.apiCommercetoolsAuthPath = apiCommercetoolsAuthPath;
	}

	public String getApiCommercetoolsClientId() {
		return apiCommercetoolsClientId;
	}

	public void setApiCommercetoolsClientId(String apiCommercetoolsClientId) {
		this.apiCommercetoolsClientId = apiCommercetoolsClientId;
	}

	public String getApiCommercetoolsClientSecret() {
		return apiCommercetoolsClientSecret;
	}

	public void setApiCommercetoolsClientSecret(String apiCommercetoolsClientSecret) {
		this.apiCommercetoolsClientSecret = apiCommercetoolsClientSecret;
	}

	public String getApiCommercetoolsProjectKey() {
		return apiCommercetoolsProjectKey;
	}

	public void setApiCommercetoolsProjectKey(String apiCommercetoolsProjectKey) {
		this.apiCommercetoolsProjectKey = apiCommercetoolsProjectKey;
	}

	public String getApiCommercetoolsCustomerPwd() {
		return apiCommercetoolsCustomerPwd;
	}

	public void setApiCommercetoolsCustomerPwd(String apiCommercetoolsCustomerPwd) {
		this.apiCommercetoolsCustomerPwd = apiCommercetoolsCustomerPwd;
	}

	public String getAuthSenderEmailPedidos() {
		return authSenderEmailPedidos;
	}

	public void setAuthSenderEmailPedidos(String authSenderEmailPedidos) {
		this.authSenderEmailPedidos = authSenderEmailPedidos;
	}
	public String getDoofinderPath() {
		return doofinderPath;
	}

	public void setDoofinderPath(String doofinderPath) {
		this.doofinderPath = doofinderPath;
	}
	public String getAudioPath() {
		return audioPath;
	}

	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}
	
	public String getAudioPsw() {
		return audioPsw;
	}

	public void setAudioPsw(String audioPsw) {
		this.audioPsw = audioPsw;
	}
	
	public String getTaxCategoryKey() {
		return taxCategoryKey;
	}

	public void setTaxCategoryKey(String taxCategoryKey) {
		this.taxCategoryKey = taxCategoryKey;
	}
	
	public String getPdfGeneratorPath() {
		return pdfGeneratorPath;
	}

	public void setPdfGeneratorPath(String pdfGeneratorPath) {
		this.pdfGeneratorPath = pdfGeneratorPath;
	}

	public String getAudioLabImpresionesPath() {
		return audioLabImpresionesPath;
	}

	public void setAudioLabImpresionesPath(String audioLabImpresionesPath) {
		this.audioLabImpresionesPath = audioLabImpresionesPath;
	}

	public String getAudioLabConfiguradorPDFPath() {
		return audioLabConfiguradorPDFPath;
	}

	public void setAudioLabConfiguradorPDFPath(String audioLabConfiguradorPDFPath) {
		this.audioLabConfiguradorPDFPath = audioLabConfiguradorPDFPath;
	}
	
	public String getPdfGeneratorBudgetPath() {
		return pdfGeneratorBudgetPath;
	}

	public void setPdfGeneratorBudgetPath(String pdfGeneratorBudgetPath) {
		this.pdfGeneratorBudgetPath = pdfGeneratorBudgetPath;
	}

	public String getUniversityAuthKey() {
		return universityAuthKey;
	}

	public void setUniversityAuthKey(String universityAuthKey) {
		this.universityAuthKey = universityAuthKey;
	}

	public String getUniversityWstoken() {
		return universityWstoken;
	}

	public void setUniversityWstoken(String universityWstoken) {
		this.universityWstoken = universityWstoken;
	}

	public String getApiUrlServices() {
		return apiUrlServices;
	}

	public void setApiUrlServices(String apiUrlServices) {
		this.apiUrlServices = apiUrlServices;
	}

	public String getApiUserServices() {
		return apiUserServices;
	}

	public void setApiUserServices(String apiUserServices) {
		this.apiUserServices = apiUserServices;
	}

	public String getApiPasswordServices() {
		return apiPasswordServices;
	}

	public void setApiPasswordServices(String apiPasswordServices) {
		this.apiPasswordServices = apiPasswordServices;
	}

	public String getApiCioneAuthServices() {
		return apiCioneAuthServices;
	}

	public void setApiCioneAuthServices(String apiCioneAuthServices) {
		this.apiCioneAuthServices = apiCioneAuthServices;
	}

}
