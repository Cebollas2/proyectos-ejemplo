package com.magnolia.cione.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.constants.CioneConstants;

import info.magnolia.context.MgnlContext;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.objectfactory.Components;

public class CioneUtils {
	

	private static final Logger log = LoggerFactory.getLogger(CioneUtils.class);
	

	// Cadena de 6 a 20 caracteres con al menos un dígito, una letra mayúscula, una
	// letra minúscula y un símbolo especial ("@ # $%")
	// private static final String PWD_PATTERN =
	// "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

	// Cadena de 8 a 20 caracteres con al menos un dígito, una letra mayúscula, una
	// letra minúscula
	private static final String PWD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})";

	private CioneUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Genera una password automática para enviar al usuario
	 * 
	 * @return
	 */
	public static String generatePassword() {
		// Creates a 32 chars length of string from the defined array of
		// characters including numeric and alphabetic characters.
		return RandomStringUtils.random(32, 0, 20, true, true, "qw32rfHIJk9iQ8Ud7h0X".toCharArray());
	}

	/**
	 * Traductor especializado
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
	public static String translate(String key, Object... args) {
		final SimpleTranslator translator = Components.getComponent(SimpleTranslator.class);
		return translator.translate(key, args);
	}

	/**
	 * Genera una autenticación http básica
	 * 
	 * @param user
	 * @param pwd
	 * @return
	 */
	public static String getHttpBasicAuth(String user, String pwd) {
		String userPwd = String.format("%s:%s", user, pwd);
		return "Basic " + Base64.getEncoder().encodeToString(userPwd.getBytes());
	}

	public static boolean validatePassword(String password) {
		return Pattern.compile(PWD_PATTERN).matcher(password).matches();
	}

	public static String getURLBase() {
		String result = null;
		try {
			HttpServletRequest request = MgnlContext.getWebContext().getRequest();
			URL requestURL = new URL(request.getRequestURL().toString());
			String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
			return requestURL.getProtocol() + "://" + requestURL.getHost() + port;
		} catch (MalformedURLException e) {
			log.error("se ha producido un erro al obtner la url base", e);
		}
		return result;
	}

	public static String getURLHttps() {
		String result = null;
		try {
			HttpServletRequest request = MgnlContext.getWebContext().getRequest();
			URL requestURL = new URL(request.getRequestURL().toString());
			// en desarrollo no tenemos frontal, generamos la ip con http:dns:puerto
			if (requestURL.getHost().equals("localhost")) {
				String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
				return requestURL.getProtocol() + "://" + requestURL.getHost() + port ;
			} else if (requestURL.getHost().equals("devmycione.cione.es")) {
				if (requestURL.getPort() == 8081) {
					String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
					log.debug("Entramos por desarrollo " + requestURL.getProtocol() + "://" + requestURL.getHost() + port
							+ MgnlContext.getContextPath());
					return requestURL.getProtocol() + "://" + requestURL.getHost() + port + MgnlContext.getContextPath();
				} else {
					//REVISAR BUG YA QUE EN DEVMYCIONE DEVUELVE HTTP AUN YENDO POR HTTPS
					return "https://" + requestURL.getHost();
				}
			} else if (requestURL.getHost().equals("premycione.cione.es")) {
				// para el resto de entornos vamos por el frontal para que funcione la descarga
				// de imagenes
				log.debug("Entramos por pre-produccion https://" + requestURL.getHost());
				return "https://" + requestURL.getHost();
			} else {
				//bug, en el entorno de pro devolvia la ip interna https://10.20.30.11/magnoliaAuthor/dam/jcr:2a12cfd2-f7b1-4dc7-aaea-cb65f896e37d/logo-header.png
				//return "https://mycione-clon.cione.es";
				return "https://mycione.cione.es";
			}
			
		} catch (MalformedURLException e) {
			log.error("se ha producido un erro al obtner la url base", e);
			
		}
		return result;
	}
	

	public static String getIdCurrentClientERP() {
		String id = MgnlContext.getUser().getName();
		// impersonate
		if (MgnlContext.getUser().hasRole(CioneConstants.ROLE_CIONE_SUPERUSER) || MgnlContext.getUser().hasRole(CioneConstants.ROLE_OPTOFIVE_SUPERUSER)) {
			String idToSimulate = MgnlContext.getUser().getProperty(CioneConstants.IMPERSONATE_FIELD_ID_SOCIO);
			if (idToSimulate != null && !idToSimulate.isEmpty()) {
				log.debug("El socio " + id + " simula a " + idToSimulate);
				id = idToSimulate;
			}
		}

		return getidClienteFromNumSocio(id);
	}

	public static String getIdCurrentClient() {
		String id = MgnlContext.getUser().getName();
		// impersonate
		if (MgnlContext.getUser().hasRole(CioneConstants.ROLE_CIONE_SUPERUSER) || MgnlContext.getUser().hasRole(CioneConstants.ROLE_OPTOFIVE_SUPERUSER)) {
			String idToSimulate = MgnlContext.getUser().getProperty(CioneConstants.IMPERSONATE_FIELD_ID_SOCIO);
			if (idToSimulate != null && !idToSimulate.isEmpty()) {
				log.debug("El socio " + id + " simula a " + idToSimulate);
				id = idToSimulate;
			}
		}

		return id;
	}
	
	public static boolean isEmpleado(String userid) {
		boolean result = false;
		
		if(StringUtils.isEmpty(userid) || !userid.endsWith("00")) {
			result = true;
		}
		
		return result;
	}

	public static Map<String, String> buildQueryParams(Object obj) {
		Map<String, String> params = new HashMap<>();
		try {
			Field[] attributes = obj.getClass().getDeclaredFields();
			for (Field field : attributes) {
				String key = field.getName();
				Object value = PropertyUtils.getSimpleProperty(obj, field.getName());
				if (value != null && !value.toString().isEmpty()) {
					params.put(key, value.toString());
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
		return params;
	}

	public static String getNumSocioFromCliente(String idCliente) {
		return idCliente + "00";
	}

	public static String getidClienteFromNumSocio(String numSocio) {
		return numSocio.substring(0, numSocio.length() - 2);
	}

	/**
	 * 
	 * @param date in format dd-MM-yyyy
	 * @return date in format yyyy-MM-dd
	 */
	public static String changeDateFormat(String date) {

		String newDate = CioneConstants.STRING_EMPTY;

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {

			if (date != null && !date.isEmpty()) {
				Date dateParse = formatter.parse(date);
				newDate = new SimpleDateFormat("yyyy-MM-dd").format(dateParse);
			}

		} catch (ParseException e) {
			log.error(e.getMessage());
			return null;
		}

		return newDate;

	}

	public static String changeDateFormat(String stringDate, String inputFormat, String outputFormat) {
		String result = null;
		try {
			SimpleDateFormat inputSimpleDateFormat = new SimpleDateFormat(inputFormat);
			SimpleDateFormat outputSimpleDateFormat = new SimpleDateFormat(outputFormat);
			Date date = inputSimpleDateFormat.parse(stringDate);
			result = outputSimpleDateFormat.format(date);
		} catch (Exception e) {
			result = null;
			log.error(e.getMessage());
		}
		return result;
	}

	public static String changeNumberFormat(String number, String format) {
		String result = null;
		try {
			// number = number.replace(",", ".");
			result = String.format(Locale.GERMAN,format, Double.valueOf(number));
		} catch (Exception e) {
			result = null;
			log.error(e.getMessage());
		}
		return result;
	}

	public static boolean isEmptyOrNull(String value) {
		return value == null || value.isEmpty();
	}

	public static Date parseStringToDate(String stringDate, String format) {
		Date date = null;		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(stringDate);
		} catch (Exception e) {
			date = null;
		}
		return date;
	}
	
	public static String decimalToView(String field) {
		if(!CioneUtils.isEmptyOrNull(field)) {
			field = CioneUtils.changeNumberFormat(field, "%,.2f");
		}
		return field;
	}
	
	public static String doubletoString(double field) {
		return String.valueOf(field).replace(".", ",");
	}
	
	public static String integerToView(String field) {
		if(!CioneUtils.isEmptyOrNull(field)) {
			field = CioneUtils.changeNumberFormat(field, "%,.0f");
		}
		return field;
	}
	
	public static boolean validUserToSycn(String username) {
		boolean result = false;
		//si soy yo o es el usuario sync
		if(username.equals(MgnlContext.getUser().getName()) ||
				MgnlContext.getUser().getName().equals(CioneConstants.USER_SYNC)) {
			result = true;
		}
		return result;
	}
	
	public static String getAuthToSync() {
		String userPwd = "sync:Jv7(nw'UE]2%hJ8+";
		return "Basic " + Base64.getEncoder().encodeToString(userPwd.getBytes());		
	}
	
	public static Date addAndSubstractMonthToDate(Date date, int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH,months);
		return calendar.getTime();
	}
	
	/**
	 * Determina si el usuario actual logado es un socio a partir de los dos ultimos digitos de se identificador
	 * @param userid Identificador del usuario
	 * @return Si es un socio o no
	 */
	public static boolean isSocio(String userid) {
		boolean result = true;
		
		if(StringUtils.isEmpty(userid) || !userid.endsWith("00")) {
			result = false;
		}
		
		return result;
	}
	
	public static double roundDouble(double value) {
		try {
			BigDecimal nuevoValor = BigDecimal.valueOf(value);
			return nuevoValor.setScale(2, RoundingMode.HALF_UP).doubleValue();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return value;
		}
		
	}
	
	/**
     * Obtiene el identificador de socio al que pertenece un empleado. Por ejemplo, para el empleado 00123456
     * @param idEmpleado
     * @return
     */
    public static String getIdSocioFromEmpleado(String idEmpleado) {
        return CioneUtils.getNumSocioFromCliente(CioneUtils.getidClienteFromNumSocio(idEmpleado));
    }
    
    public static String generateRef() {
    	int length = 5;
	    boolean useLetters = true;
	    boolean useNumbers = true;
		String generatedString = RandomStringUtils.random(length, useLetters, useNumbers) + getIdCurrentClientERP();
    	return generatedString.toUpperCase();
    }
    
	public static String encodeURIComponent(String title) {
		
		String res = "";

	    try{
	    	
	    	res = URLEncoder.encode(title, "UTF-8")
	    			.replaceAll("%3D", "=")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
	      
	    }catch (UnsupportedEncodingException e){
	      res = title;
	    }
        
        return res;
	}
	
	public static boolean containsList(List<String> lista, String name) {
		return lista.stream().filter(filtro -> filtro.equals(name)).findFirst().isPresent();
	}

}
