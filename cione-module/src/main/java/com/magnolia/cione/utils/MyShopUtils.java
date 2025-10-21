package com.magnolia.cione.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.commercetools.api.models.cart.RoundingMode;
import com.commercetools.api.models.common.CentPrecisionMoneyBuilder;
import com.commercetools.api.models.common.Money;
import com.commercetools.api.models.common.Reference;
import com.commercetools.api.models.common.TypedMoney;
import com.commercetools.api.models.common.TypedMoneyImpl;
import com.commercetools.api.models.product.Attribute;
import com.fasterxml.jackson.core.type.TypeReference;
import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.constants.CioneRoles;

import info.magnolia.cms.security.SecuritySupport;
import info.magnolia.context.MgnlContext;
import info.magnolia.objectfactory.Components;

public class MyShopUtils {
	private static final Logger log = LoggerFactory.getLogger(MyShopUtils.class);
	
	/*A partir de un tipo TypedMoney devolvemos el String*/
	public static String formatTypedMoney(TypedMoney money) {
		//BigDecimal valorConDosDecimales = new BigDecimal(money.getCentAmount()).setScale(2, RoundingMode.HALF_UP);
		
		//BigDecimal amountInCents = BigDecimal.valueOf(money.getCentAmount());
		//BigDecimal amountInEuros = amountInCents.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
		
		BigDecimal amountInCents = new BigDecimal(money.getCentAmount());
		BigDecimal divisor = new BigDecimal(100);
		BigDecimal amountInEuros = amountInCents.divide(divisor, 2, RoundingMode.HALF_UP);
		
		return amountInEuros.toString();
		
	}
	
	public static String formatMoney(Money money) {
		//BigDecimal valorConDosDecimales = new BigDecimal(money.getCentAmount()).setScale(2, RoundingMode.HALF_UP);
		
		BigDecimal amountInCents = new BigDecimal(money.getCentAmount());
		BigDecimal divisor = new BigDecimal(100);
		BigDecimal amountInEuros = amountInCents.divide(divisor, 2, RoundingMode.HALF_UP);
		
//		BigDecimal amountInCents = BigDecimal.valueOf(money.getCentAmount());
//		BigDecimal amountInEuros = amountInCents.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
		
		return amountInEuros.toString();
		
	}
	
	public static double formatMoneyDouble(TypedMoney money) {
		//BigDecimal valorConDosDecimales = new BigDecimal(money.getCentAmount()).setScale(2, RoundingMode.HALF_UP);
		//BigDecimal amountInCents = BigDecimal.valueOf(money.getCentAmount());
		//BigDecimal amountInEuros = amountInCents.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
		
		BigDecimal amountInCents = new BigDecimal(money.getCentAmount());
		BigDecimal divisor = new BigDecimal(100);
		BigDecimal amountInEuros = amountInCents.divide(divisor, 2, RoundingMode.HALF_UP);
		
		return amountInEuros.doubleValue();
		
	}
	
	public static double formatStringDouble(String money) {
		return Double.valueOf(money);
	}
	
	//A partir de un tipo Double devuelve un String ajustado a 2 unidades
	public static String formatDoubleString(double money) {
		BigDecimal amountInCents = new BigDecimal(money).setScale(2, RoundingMode.HALF_UP);
		return amountInCents.toString();
	}
	
    public static Double getCentAmountDouble(TypedMoney money) {
        return money.getCentAmount() / Math.pow(10, money.getFractionDigits());
    }
    
	public static Money getMoney(String value) {
		value = value.replace(',', '.');//reemplazo , por . para poder parsear
		double doubleValue = Double.valueOf(value);
		return Money
	            .builder()
	            .centAmount(getCentAmount(doubleValue,2))
	            .currencyCode("EUR")
	            .build();
	}
	
	public static TypedMoney getTypeMoney(String value) {
		value = value.replace(',', '.');//reemplazo , por . para poder parsear 
		double doubleValue = Double.valueOf(value);
		
		TypedMoney typedMoney = new TypedMoneyImpl();
		typedMoney.setCentAmount(getCentAmount(doubleValue,2));
		typedMoney.setCurrencyCode("EUR");
		typedMoney.setFractionDigits(2);
		
		return typedMoney;
		

	}
	
	public static Money getMoney(double doubleValue) {
		return Money
	            .builder()
	            .centAmount(getCentAmount(doubleValue, 2))
	            .currencyCode("EUR")
	            .build();
	}
	
    public static Long getCentAmount(double doubleValue, int fraction) {
        return BigDecimal
            .valueOf(doubleValue)
            .setScale(fraction, RoundingMode.HALF_EVEN)
            .multiply(BigDecimal.valueOf(100))
            .longValue();
    }
    
    public static Double getCentAmountDouble(Long value, int fraction) {
        return value / Math.pow(10, fraction);
    }
	
	/*A partir de un String devuelve un tipo TypedMoney*/
	public static TypedMoney getMonetaryAmount(String money) {
		TypedMoney amount = null;
		Long dpvodto;
		try {
			String currencyCode = "EUR";
			String daux = money.replace(',', '.');
			if (money.contains(".")) {
				//viene en formato Double
				//double doubleValue = Double.parseDouble(money);
				BigDecimal bd = new BigDecimal(money);
		        bd = bd.setScale(2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
		        //doubleValue = bd.doubleValue();
				dpvodto = bd.longValue();
			} else 
				dpvodto = Long.valueOf(daux);
			
			
			amount = CentPrecisionMoneyBuilder.of()
			        .currencyCode(currencyCode)
			        .fractionDigits(2)
			        .centAmount(dpvodto)
			        .build();
		} catch (Exception e) {
			log.error("Error al convertir el String " + money + " a tipo money ", e);
		}
		return amount;
	}
	
/*	public static TypedMoney getMonetaryAmount(String money) {
		TypedMoney amount = null;
		Long dpvodto;
		try {
			String currencyCode = "EUR";
			String daux = money.replace(',', '.');
			if (money.contains(".")) {
				//viene en formato Double
				
				BigDecimal bd = new BigDecimal(money).setScale(4, RoundingMode.HALF_UP);
				dpvodto = bd.movePointRight(4).longValueExact();
			} else 
				dpvodto = Long.valueOf(daux);
			
			amount = HighPrecisionMoneyBuilder.of()
					.currencyCode(currencyCode)
					.centAmount(Long.valueOf(1))
			        .fractionDigits(4)
			        .preciseAmount(dpvodto)
			        .build();
		} catch (Exception e) {
			log.error("Error al convertir el String " + money + " a tipo money ", e);
		}
		return amount;
	}*/

	/*A partir de un double devuelve un TypedMoney*/
	public static TypedMoney getMonetaryAmount(double dpvodto) {
		TypedMoney amount = null;
		String currencyCode = "EUR";
		try {
			Long ldpvodto = Double.valueOf(dpvodto).longValue();
			amount = CentPrecisionMoneyBuilder.of()
			        .currencyCode(currencyCode)
			        .fractionDigits(2)
			        .centAmount(ldpvodto)
			        .build();
		} catch (Exception e) {
			log.error("Error al convertir el double " + dpvodto + " a tipo money ", e);
		}
		return amount;
	}
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;

    }  
    
    /*
     * Metodo que añade un nuevo type necesario a los definidos en TypeReference.
     * Este type nos permite obtener customFields de tipo Reference como los definidos en categorias
     */
    public static TypeReference<Set<Reference>> referenceSetTypeReference() {
        return new TypeReference<Set<Reference>>() {
            @Override
            public String toString() {
                return "TypeReference<Set<Reference>>";
            }
        };
    }
    
    //metodo que encuentra un atributo de producto con el nombre attributeName del listado de atributos de producto
	public static Attribute findAttribute(String attributeName, List<Attribute> attributes) {
		return attributes.stream().filter(attr -> attributeName.equals(attr.getName())).findFirst().orElse(null);
	}
	
	public static boolean hasAttribute(String attributeName, List<Attribute> attributes) {
		return attributes.stream().anyMatch(attr -> attr.getName().equals(attributeName));
	}
	
	public static String getFilter(String filterExpression, String value) {
		return filterExpression + ":" + value;
		
	}
	
	public static String getFilter(String filterExpression, String[] values) {
		return filterExpression + ":\"" + String.join("\",\"", values) + "\"";
		
	}
	
	public static String getFilter(String filterExpression, List<String> values) {
		return filterExpression + ":\"" + String.join("\",\"", values) + "\"";
		
	}
    
    public static String getUuid() {
    	String uuid ="";
    	String id = MgnlContext.getUser().getName();
		// impersonate
		if (MgnlContext.getUser().hasRole(CioneConstants.ROLE_CIONE_SUPERUSER) || MgnlContext.getUser().hasRole(CioneConstants.ROLE_OPTOFIVE_SUPERUSER)) {
			String idToSimulate = MgnlContext.getUser().getProperty(CioneConstants.IMPERSONATE_FIELD_ID_SOCIO);
			if (idToSimulate != null && !idToSimulate.isEmpty()) {
				log.debug("El socio " + id + " simula a " + idToSimulate);
				id = idToSimulate;
			}
		}
    	uuid = Components.getComponent(SecuritySupport.class).getUserManager().getUser(id).getIdentifier();
    	return uuid;
    }
    
    public static String getUserName() {
    	String userName = MgnlContext.getUser().getName();
		// impersonate
		if (MgnlContext.getUser().hasRole(CioneConstants.ROLE_CIONE_SUPERUSER) || MgnlContext.getUser().hasRole(CioneConstants.ROLE_OPTOFIVE_SUPERUSER)) {
			String idToSimulate = MgnlContext.getUser().getProperty(CioneConstants.IMPERSONATE_FIELD_ID_SOCIO);
			if (idToSimulate != null && !idToSimulate.isEmpty()) {
				log.debug("El socio " + userName + " simula a " + idToSimulate);
				userName = idToSimulate;
			}
		}
		return userName;
    }
    
	private static final Map<String, String> familia_tipoProducto = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		
        put("Gafas graduadas", "monturas");
        put("Gafas de sol", "monturas");
        put("Gafas premontadas", "monturas");
        put("Repuestos graduado", "monturas");
        put("Repuestos sol", "monturas");
        put("Clips para venta", "monturas");
        put("Smart Glasses", "monturas");
        put("Lentes de contacto", "contactologia");
        put("Liquidos", "liquidos");
        put("Soluciones Mantenimiento", "liquidos");
        put("Accesorios", "accesorios");
        put("Suministros", "accesorios");
        put("Epis", "accesorios");
        put("EPIS", "accesorios");
        put("EPI", "accesorios");
        put("Maquinaria", "maquinaria");
        put("COMUNIK", "marketing");
        put("Publicidad Marcas", "marketing");
        put("Merchandishing PO", "marketing");
        put("Merchandising PO", "marketing");
        put("Merchandising OM", "marketing");
        put("Baterias", "complementosaudio");
        put("Baterías", "complementosaudio");
        put("Uso Profesional", "complementosaudio");
        put("Tapones", "complementosaudio");
        put("Complementos", "complementosaudio");
        put("Accesorios Inalambricos", "complementosaudio");
        put("Acc. Inalámbricos", "complementosaudio");
        put("Audífonos", "audifonos");
        put("Audiolab", "audiolab");
        put("Garantías", "complementosaudioservicios");
        put("Servicios Audiología", "complementosaudioservicios");
        put("packs", "packs");
        put("Packs", "packs");
        put("packs-audio", "packs-audio");
        put("pack-generico", "pack-generico");
        put("Packs universales", "pack-generico");
        
    }};
    
	private static final Map<String, Boolean> permiteCompra = new HashMap<String, Boolean>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		
        put("Gafas graduadas", true);
        put("Gafas de sol", true);
        put("Gafas premontadas", true);
        put("Repuestos graduado", true);
        put("Repuestos sol", true);
        put("Lentes de contacto", false);
        put("Liquidos", true);
        put("Soluciones Mantenimiento", true);
        put("Accesorios", true);
        put("Suministros", true);
        put("Epis", true);
        put("EPIS", true);
        put("Maquinaria", true);
        put("COMUNIK", true);
        put("Publicidad Marcas", true);
        put("Merchandishing PO", true);
        put("Merchandising PO", true);
        put("Baterias", true);
        put("Baterías", true);
        put("Tapones", true);
        put("Complementos", true);
        put("Accesorios Inalambricos", true);
        put("Acc. Inalámbricos", true);
        put("Audífonos", true);
        put("Garantías", true);
        put("Servicios Audiología", true);
        put("packs", true);
        put("Packs", true);
        put("packs-audio", false);
        put("pack-generico", false);
    }};
    
    public static String getFamiliaProducto(String tipoProducto) {
    	return familia_tipoProducto.get(tipoProducto);
    }
    
    public static boolean isComprable(String tipoProducto) {
    	if (permiteCompra.get(tipoProducto) != null)
    		return permiteCompra.get(tipoProducto);
    	else return false;
    }
    
    public static HashMap<String, String> sortByValue(HashMap<String, String> hm){
        // Create a list from elements of HashMap
        List<Map.Entry<String, String> > list
            = new LinkedList<Map.Entry<String, String> >(
                hm.entrySet());
 
        // Sort the list using lambda expression
        Collections.sort(
            list,
            (i1,
             i2) -> i1.getValue().compareTo(i2.getValue()));
 
        // put data from sorted list to hashmap
        HashMap<String, String> temp
            = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
    
    //funcion que busca value dentro del List array
    public static boolean contains(String[] array, String value) {
        return array != null && value != null && Arrays.stream(array).anyMatch(value::equals);
    }
    
    public static boolean isSocioPortugal() {
    	if (MgnlContext.getUser().hasRole(CioneRoles.CLIENTES_PORTUGAL)
				|| MgnlContext.getUser().hasRole(CioneRoles.PORLENS)) {
			return true;
		} else
			return false;
    }
    
    
    /*Tabla que devuelve si las promociones tenemos que buscarlas por codigo central o AliasEkon
    //recibimos la familia
    public static boolean searchByCodigoCentral_familiaInput(String familiaProducto) {
    	if (familiaProducto != null) {
			switch (familiaProducto){
				case "monturas" :
					return false;
				case "contactologia" :
					return true;
				case "marketing" :
					return true;
				case "liquidos" :
					return true;
				default:
					return false;
			}
		} else {
			return false;
		}
    }

    //Tabla que devuelve si las promociones tenemos que buscarlas por codigo central o AliasEkon
    //recibimos el tipoProducto
    public static boolean searchByCodigoCentral_tipoProductoInput(String tipoProducto) {
    	if (tipoProducto != null) {
    		String familiaProducto = familia_tipoProducto.get(tipoProducto);
    		if (familiaProducto != null) { 
				switch (familiaProducto){
					case "monturas" :
						return false;
					case "contactologia" :
						return true;
					case "marketing" :
						return true;
					case "liquidos" :
						return true;
					default:
						return false;
				}
    		} else 
    			return false;
		} else {
			return false;
		}
    }*/

}

