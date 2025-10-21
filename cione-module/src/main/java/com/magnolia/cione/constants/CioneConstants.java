package com.magnolia.cione.constants;

import java.util.HashMap;
import java.util.Map;

public final class CioneConstants {

	private CioneConstants() {
		throw new IllegalStateException("Utility class");
	}

	public static final String USER_SYNC = "sync";
	public static final String ROLE_CIONE_SUPERUSER = "cione_superuser";
	public static final String ROLE_OPTOFIVE_SUPERUSER = "optofive_superuser";
	public static final String AUDIOLOGY_ACCESS = "audiology_access";
	public static final String IMPERSONATE_FIELD_ID_SOCIO = "impersonateIdSocio";
	public static final String IMPERSONATE_FIELD_NAME_SOCIO = "impersonateNameSocio";
	public static final String IMPERSONATE_FIELD_BACKUP_ROLES = "backupRoles";
	public static final String ROLE_FIDELIZACION="rol_fidelizacion";
	
	public static final String OPTMAD="OPTMAD";
	public static final String OPTCAN="OPTCAN";
	public static final String OPTICAPRO="OPTICAPRO";
	public static final String CLIENTE_MONTURAS="cliente_monturas";
	public static final String TALLERMAD="TALLERMAD";

	
	public static final String PATH_SEPARATOR = "/";
	public static final String STRING_SEMICOLON = ";";
	public static final String STRING_COMMA = ",";
	public static final String STRING_EMPTY = "";
	public static final String STRING_HYPHEN = "-";
	public static final String STRING_UNDERSCORE = "_";
	public static final String lAST_DAY_FEB_NOT_LEAP_YEAR = "28";
	public static final String LAST_DAY_FEB_LEAP_YEAR = "29";
	public static final String LAST_DAY_MONTH = "30";
	public static final String MAX_LAST_DAY_MONTH = "31";
	
	//patron que valida que la fecha sea a partir de 1600 teniendo en cuenta bisiestos y dias de cada mes, ademas debe cumplir que los ocho caracteres de la fecha sean numericos
	//hay que tener en cuenta que si en el String tiene algo que que pueda ser tomado como fecha cogera la primera ocurrencia
	//public static final String PATTERN_YYYYMMDD = "\\b(?:(?:(?:1[6-9]|[2-9]\\d)\\d{2})(?:(?:0[13578]|1[02])31|(?:0[1,3-9]|1[0-2])(29|30)|(?:02(?:29|28))|(?:0[1-9]|1[0-2])(?:0[1-9]|1\\d|2[0-8])))(?!\\d)\\b";
	//para informes de 
	public static final String PATTERN_YYYYMMDD = "^[A-Za-z_]+_\\d{7}[A-Z]_(\\d{8})\\.pdf$";
	//public static final String PATTERN_YYYYMMDD = "(?<!\\d)(?:(?:(?:1[6-9]|[2-9]\\d)?\\d{2})(?:(?:(?:0[13578]|1[02])31)|(?:(?:0[1,3-9]|1[0-2])(?:29|30)))|(?:(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))0229)|(?:(?:1[6-9]|[2-9]\\d)?\\d{2})(?:(?:0?[1-9])|(?:1[0-2]))(?:0?[1-9]|1\\d|2[0-8]))(?!\\d)";
	public static final String PATTERN_YYYYMM_RE1 = "((?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3})))(?![\\d])";	
	public static final String PATTERN_YYYYMM_RE2 = "(_)";	
	public static final String PATTERN_YYYYMM_RE3 = "((?:(?:[0-2]?\\d{1})|(?:[3][01]{1})))(?![\\d])";
	public static final String PATTERN_CODSOCIO = "_[0-9]{1,}[A-Za-z]?_";
	public static final String PATTERN_NIFSOCIO = "([a-z]|[A-Z]|[0-9])[0-9]{7}([a-z]|[A-Z]|[0-9])";
	public static final String PATTERN_CODSOCIO_ABONOS = "-[0-9]{1,}\\.";  
	
	public static final String OUPUT_DATE_FORMAT = "dd-MM-yyyy";
	public static final String DATE_FORMAT_MM_YYYY = "MM/yyyy";
	
	public static final String DEFAULT_ADMIN_MAIL = "info@cione.es";
	
	public static final String MIS_CONSUMOS_SESSION_NAME = "mis-consumos";
	
	public static final String AUDITORIA_USER_NODE_NAME = "auditoria";
	public static final String AUDITORIA_NOTICIAS_NAME = "noticias";
	public static final String AUDITORIA_ID_PROPERTY = "id";
	public static final String NOTICIA_NOTIFICAR_PROPERTY = "notificar";
	public static final String NOTICIA_ROLES_NODE = "roles";
	public static final String NOTICIAS_WORKSPACE = "noticias";
	public static final String PAGES_WORKSPACE = "website";
	public static final String CIONE_USERS_WORKSPACE = "cione-users";
	public static final String USERS_WORKSPACE = "users";
	public static final String ROLES_USER_NODE_NAME = "roles";
	public static final String PUBLIC_NODE = "public";
	public static final String CONTENT_NODE_TYPE = "mgnl:contentNode";
	public static final String CONTENT_FOLDER_TYPE = "mgnl:folder";
	
	
	/*A partir del rol de Magnolia nos da el correspondiente Grupo de Precio en CommerceTools*/
    public static final Map<String, String> equivalenciaRolMagnoliaCommerceTools = new HashMap<String, String>(){
    	private static final long serialVersionUID = 1L;
	{
		
        put("cione", "CIONE");
        put("socio_cione_vco_aco-centro", "1001");
        put("socio_cione_vco_aco-corner", "1002");
        put("socio_cione_club_marketing", "1020");
        put("socio_cione_aco_centro", "1103");
        put("socio_cione_aco_corner", "1104");
        put("cliente", "1200");
        put("personal_otros", "PERSONALOTROS");
        put("cliente_monturas", "CLIENTEMON");
        put("cliente_portugal", "PORCLI");
        put("PORLENS", "PORLENS");
        put("socio_cione_portugal", "PORSO");
        put("ciosa-primera", "PRI");
        put("socio_vision_co", "VCO");
        put("cliente_portugal_vco", "VCOCLIP");
        put("connecta", "CONNECTA");
        put("OPTMAD", "OPTMAD");
        put("OPTCAN", "OPTCAN");
        put("OPTICAPRO", "OPTICAPRO");
        put("TALLERMAD", "TALLERMAD");
        
    }};
    
	/*A partir del rol de Magnolia nos da el correspondiente Grupo de Precio en Ekon*/
    public static final Map<String, String> equivalenciaRolMagnoliaEkon = new HashMap<String, String>(){
    	private static final long serialVersionUID = 1L;
	{
		
        put("cione", "1");
        put("socio_cione_vco_aco-centro", "1001");
        put("socio_cione_vco_aco-corner", "1002");
        put("socio_cione_club_marketing", "1020");
        put("socio_cione_aco_centro", "1103");
        put("socio_cione_aco_corner", "1104");
        put("cliente", "1200");
        put("personal_otros", "2");
        put("cliente_monturas", "CLIENTEMON");
        put("cliente_portugal", "PORCLI");
        put("PORLENS", "PORLENS");
        put("socio_cione_portugal", "PORSO");
        put("ciosa-primera", "PRI");
        put("socio_vision_co", "VCO");
        put("cliente_portugal_vco", "VCOCLIP");
        put("connecta", "CONNECTA");
        put("OPTMAD", "OPTMAD");
        put("OPTCAN", "OPTCAN");
        put("OPTICAPRO", "OPTICAPRO");
        put("TALLERMAD", "TALLERMAD");
    }};
    
    /*A partir del Grupo de Precio en CommerceTools nos da el correspondiente Rol en Magnolia*/
    public static final Map<String, String> equivalenciaRolCommerceToolsMagnolia = new HashMap<String, String>(){
    	private static final long serialVersionUID = 1L;
	{
		
        put("CIONE", "cione");
        put("1001","socio_cione_vco_aco-centro");
        put("1002", "socio_cione_vco_aco-corner");
        put("1020", "socio_cione_club_marketing");
        put("1103", "cocio_cione_aco_centro");
        put("1104", "socio_cione_aco_corner");
        put("1200", "cliente");
        put("PERSONALOTROS", "personal_otros");
        put("CLIENTEMON", "cliente_monturas");
        put("PORCLI", "cliente_portugal");
        put("PORLENS", "PORLENS");
        put("PORSO", "socio_cione_portugal");
        put("PRI", "ciosa-primera");
        put("VCO", "socio_vision_co");
        put("VCOCLIP", "cliente_portugal_vco");
        put("CONNECTA", "connecta");
        put("OPTMAD", "OPTMAD");
        put("OPTCAN", "OPTCAN");
        put("OPTICAPRO", "OPTICAPRO");
        put("TALLERMAD", "TALLERMAD");
    }};
    
    
    /*A partir del Grupo de Precio en CommerceTools nos da el correspondiente Rol en Magnolia*/
    public static final Map<String, String> equivalenciaRolCommerceToolsEKON = new HashMap<String, String>(){
    	private static final long serialVersionUID = 1L;
	{
		
        put("CIONE", "1");
        put("1001","1001");
        put("1002", "1002");
        put("1020", "1020");
        put("1103", "1103");
        put("1104", "1104");
        put("1200", "1200");
        put("PERSONALOTROS", "2");
        put("CLIENTEMON", "CLIENTEMON");
        put("PORCLI", "PORCLI");
        put("PORLENS", "PORLENS");
        put("PORSO", "PORSO");
        put("PRI", "PRI");
        put("VCO", "VCO");
        put("VCOCLIP", "VCOCLIP");
        put("CONNECTA", "CONNECTA");
        put("OPTMAD", "OPTMAD");
        put("OPTCAN", "OPTCAN");
        put("OPTICAPRO", "OPTICAPRO");
        put("TALLERMAD", "TALLERMAD");
    }};
    
    /*A partir del Grupo de Precio en Ekon nos da el correspondiente Grupo de Precio en CommerceTools*/
	public static final Map<String, String> equivalenciaEKONCommerceTools = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		
        put("1", "CIONE");
        put("1001", "1001");
        put("1002", "1002");
        put("1020", "1020");
        put("1103", "1103");
        put("1104", "1104");
        put("1200", "1200");
        put("2", "PERSONALOTROS");
        put("CLIENTEMON", "CLIENTEMON");
        put("PORCLI", "PORCLI");
        put("PORLENS", "PORLENS");
        put("PORSO", "PORSO");
        put("PRI", "PRI");
        put("VCO", "VCO");
        put("VCOCLIP", "VCOCLIP");
        put("CONNECTA", "CONNECTA");
        put("OPTMAD", "OPTMAD");
        put("OPTCAN", "OPTCAN");
        put("OPTICAPRO", "OPTICAPRO");
        put("TALLERMAD", "TALLERMAD");
    }};
    
	// Aunque solo haya dos diferentes esto puede cambiar en el futuro
	public static final Map<String, String> equivalenciaEKONMagnolia = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

	{
		
        put("1", "cione");
        put("1001","socio_cione_vco_aco-centro");
        put("1002", "socio_cione_vco_aco-corner");
        put("1020", "socio_cione_club_marketing");
        put("1103", "cocio_cione_aco_centro");
        put("1104", "socio_cione_aco_corner");
        put("1200", "cliente");
        put("2", "personal_otros");
        put("CLIENTEMON", "cliente_monturas");
        put("PORCLI", "cliente_portugal");
        put("PORLENS", "PORLENS");
        put("PORSO", "socio_cione_portugal");
        put("PRI", "ciosa-primera");
        put("VCO", "socio_vision_co");
        put("VCOCLIP", "cliente_portugal_vco");
        put("CONNECTA", "connecta");
        put("OPTMAD", "OPTMAD");
        put("OPTCAN", "OPTCAN");
        put("OPTICAPRO", "OPTICAPRO");
        put("TALLERMAD", "TALLERMAD");
    }};
    
    public static final String OM90 = "OM90"; //ROl EKO
    public static final String OM180 = "OM180";
    public static final String OM180PLUS = "OM180+";
    public static final String OM360 = "OM360";
    public static final String OM_90 = "om_90";
    public static final String OM_180 = "om_180";
    public static final String OM_180_PLUS = "om_180_plus";
    public static final String OM_360 = "om_360";
    
    
}


