package com.magnolia.cione.model;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.LineItem;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.product.ProductVariant;
import com.commercetools.api.models.product_type.AttributePlainEnumValue;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.CartModelDTO;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.dto.VariantDTO;
import com.magnolia.cione.service.CartService;
import com.magnolia.cione.service.CommercetoolsService;
import com.magnolia.cione.service.CustomerService;
import com.magnolia.cione.service.LensService;
import com.magnolia.cione.service.MiddlewareService;
import com.magnolia.cione.service.PeriodicPurchaseService;
import com.magnolia.cione.utils.CioneUtils;
import com.magnolia.cione.utils.MyShopUtils;

import info.magnolia.context.MgnlContext;
import info.magnolia.ecommerce.EcommerceConnectionProvider;
import info.magnolia.ecommerce.common.Connection;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;


public class DetallePeriodicPurchaseModel <RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD>{
	
	private static final Logger log = LoggerFactory.getLogger(DetallePeriodicPurchaseModel.class);
	
	private final TemplatingFunctions templatingFunctions;
	
	private CartModelDTO periodicpurchase;
	
	private String id;

	@Inject
	private  EcommerceConnectionProvider ecommerceConnectionProvider;
	
	@Inject
	PeriodicPurchaseService periodicPurchaseService;
	
	@Inject
	private CommercetoolsService commercetoolsservice;
	
	@Inject
	private MiddlewareService middlewareService;
	
	@Inject
	private CartService cartService;
	
	@Inject
	private LensService lensservice;
	
	@Inject
	private CustomerService customerService;
	
	@Inject
	public DetallePeriodicPurchaseModel(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
		this.templatingFunctions = templatingFunctions;
	}
	
	@Override
	public String execute() {
		
		if (!templatingFunctions.isEditMode()) {
			
			id = MgnlContext.getParameter("id");
			
			if (id != null && !id.isEmpty()) {
				
				Cart car = periodicPurchaseService.getPeriodicPurchaseById(id);
				
				if (car != null) {
					CartModelDTO pre = new CartModelDTO();
					pre.setCarrito(car);
					pre.setPriceDisplayConfiguration(getPriceDisplayConfiguration());
					pre.setGrupoPrecioCommerceTools(commercetoolsservice.getGrupoPrecioCommerce());
					
					periodicpurchase = pre;
					
				}
			}
		}
		
		return super.execute();
	}
	
	private String getPriceDisplayConfiguration() {
		
		String configuration= getPriceDisplayRol();
		
		try {
			Node userNode = NodeUtil.getNodeByIdentifier("users", MyShopUtils.getUuid());
		
			if (userNode.hasProperty(MyshopConstants.PRICE_CONFIGURATION_PROPERTY)) {
				configuration = userNode.getProperty(MyshopConstants.PRICE_CONFIGURATION_PROPERTY).getString();
			}
		} catch (RepositoryException e) {
			log.error("Exception while obtaining user's node: ", e);
		}

		return configuration;
	}
	
	public String getValidity() {
		
		String res = "";
		
		try {
			LocalDate dateIniPurcharse = (LocalDate)periodicpurchase.getCarrito().getCustom().getFields().values().get(MyshopConstants.DATEINI_PURCHARSE);
			LocalDate dateFinPurcharse = (LocalDate)periodicpurchase.getCarrito().getCustom().getFields().values().get(MyshopConstants.DATEDFIN_PURCHARSE);
			
			if ((dateIniPurcharse != null) &&
					(dateFinPurcharse != null)) {
				String ini = getFormattedDate(dateIniPurcharse);
				String fin = getFormattedDate(dateFinPurcharse);
				res = "Desde " + ini + " hasta " + fin;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return res;
	}
	
	private String getFormattedDate(LocalDate datestr) {
		
        String res = "";
        
		try {
			//SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
			//Date date = parser.parse(datestr);
			//SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	        //res = formatter.format(datestr);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			res = datestr.format(formatter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	private String getPriceDisplayRol() {
		
		String configuration;
		
		Collection<String> userRoles = MgnlContext.getUser().getRoles();
		
		if (userRoles.contains(MyshopConstants.PVP_PVO_ROL)) {
			configuration = MyshopConstants.PVP_PVO;
		} else if (userRoles.contains(MyshopConstants.PVO_ROL)) {
			configuration = MyshopConstants.PVO;
		} else if (userRoles.contains(MyshopConstants.PVP_ROL)) {
			configuration = MyshopConstants.PVP;
		} else if (userRoles.contains(MyshopConstants.HIDDEN_PRICES_ROL)) {
			configuration = MyshopConstants.HIDDEN_PRICES;
		} else {
			configuration = MyshopConstants.PVP_PVO;
		}
		
		return configuration;
	}
	
	public String getCarritoTotal() {
		Cart carrito = periodicPurchaseService.getPeriodicPurchaseById(id);
		return periodicPurchaseService.getCarritoTotal(carrito);
	}
	
	public String getCartId() {
		return customerService.getUserCart().getId();
	}

	public CartModelDTO getPeriodicPurchase() {
		return periodicpurchase;
	}
    
    public String getUuid() {
    	return MyShopUtils.getUuid();
    }
    
    public String getUserName() {
		return MyShopUtils.getUserName();
    }
	
	public UserERPCioneDTO getData() {	
		UserERPCioneDTO data = middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
		if(data == null) {
			data = new UserERPCioneDTO();
		}
		return data; 
	}
	
	public VariantDTO getVariantInfoPromociones(ProductVariant variante) {
		return cartService.getPromocionesByVariant(variante);
	}
	
	public String getFamilia(LineItem linea) {
		String familia=null;
		if (MyShopUtils.hasAttribute(MyshopConstants.PLANTILLA, linea.getVariant().getAttributes()))
			familia = ((AttributePlainEnumValue) MyShopUtils.findAttribute(MyshopConstants.PLANTILLA, linea.getVariant().getAttributes()).getValue()).getKey();
		//este else sobra si todos los productos tiene contribuida la familia
		else if (MyShopUtils.hasAttribute("tipoProducto", linea.getVariant().getAttributes())) {
				LocalizedString tipoProducto = (LocalizedString) MyShopUtils.findAttribute("tipoProducto", linea.getVariant().getAttributes()).getValue();
				familia = MyShopUtils.getFamiliaProducto(tipoProducto.get(MyshopConstants.esLocale));
		}
		if (familia == null)
			familia = "monturas";
		return familia;
	}
	
	public String getId() {
		return id;
	}
    
    public Locale getLocale() {
    	Locale locale = MyshopConstants.esLocale;
    	return locale;
    }
	
    public String getPvp(String sku) {
    	String pvp ="0";
    	try {
			Map<String, String> price = lensservice.getPriceBySku(sku);
			pvp = price.get("pvp");
			if (pvp != null) {
				DecimalFormat decimalFormat = new DecimalFormat("#00.00");
				pvp = decimalFormat.format(Double.valueOf(pvp).doubleValue());
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
    	return pvp;
    }

    public float getStock(LineItem linea) {
    	float stock = 0;
		if (MyShopUtils.hasAttribute("LC_sku", linea.getVariant().getAttributes())) {
			String sku = (String) MyShopUtils.findAttribute("LC_sku", linea.getVariant().getAttributes()).getValue();
			stock = middlewareService.getStock(sku);
		}
		return stock;
	}
    
    public float getStockbyAliasEkon(String aliasEkon) {
		return middlewareService.getStock(aliasEkon);
	}
    
    private Optional<Connection> getConnection(String definitionName, String connectionName) {
        return ecommerceConnectionProvider.getConnection(definitionName, connectionName);
    }
    
}
