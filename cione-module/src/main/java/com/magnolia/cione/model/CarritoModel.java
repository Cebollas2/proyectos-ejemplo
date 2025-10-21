package com.magnolia.cione.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.LineItem;
import com.commercetools.api.models.common.Image;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.common.Money;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.product.ProductProjection;
import com.commercetools.api.models.product.ProductVariant;
import com.commercetools.api.models.product_type.AttributePlainEnumValue;
import com.magnolia.cione.constants.CioneRoles;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.CartModelDTO;
import com.magnolia.cione.dto.DireccionesDTO;
import com.magnolia.cione.dto.ProductFrontDTO;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.dto.VariantDTO;
import com.magnolia.cione.service.CartService;
import com.magnolia.cione.service.CommercetoolsService;
import com.magnolia.cione.service.CustomerService;
import com.magnolia.cione.service.LensService;
import com.magnolia.cione.service.MiddlewareService;
import com.magnolia.cione.service.UserService;
import com.magnolia.cione.utils.CioneUtils;
import com.magnolia.cione.utils.MyShopUtils;

import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.ContentMap;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.jcr.util.SessionUtil;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class CarritoModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {
	
	@Inject
	private MiddlewareService middlewareService;
	
	@Inject
	private CommercetoolsService commercetoolsservice;
	
	@Inject
	private UserService userservice;
	
	@Inject
	private CustomerService customerService;
	
	@Inject
	private CartService cartService;
	
	@Inject
	private LensService lensservice;
	
	private List<ProductFrontDTO> listPacksFilter = new ArrayList<>();
	
	//private String almacen;
	
	private static final Logger log = LoggerFactory.getLogger(CarritoModel.class);
	
	public CarritoModel(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
	}
	
	@Override
	public String execute() {
		
		if (MgnlContext.getWebContext().getRequest().getSession().getAttribute(MyshopConstants.ATTR_STOCK_SESSION) == null) {
			
			UserERPCioneDTO user = middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
			HttpSession session = MgnlContext.getWebContext().getRequest().getSession();
			
			if (user != null &&  user.getAlmacenDefault() != null && !user.getAlmacenDefault().isEmpty() && user.getAlmacenDefault().equals(MyshopConstants.ALMACENDEFAULTCANAR)) {
				session.setAttribute(MyshopConstants.ATTR_STOCK_SESSION, MyshopConstants.STOCKCANAR);
				//almacen = MyshopConstants.STOCKCANAR;
			}else {
				session.setAttribute(MyshopConstants.ATTR_STOCK_SESSION, MyshopConstants.STOCKCTRAL);
				//almacen = MyshopConstants.STOCKCTRAL;
			}
		}
		listPacksFilter = getPacksCart();
		
		return super.execute();
	}
	
	public CartModelDTO getDataModel() {
		CartModelDTO cartModel = new CartModelDTO();
		//cartModel.setCarrito(customerService.getUserCart(MgnlContext.getUser().getName()));
		cartModel.setCarrito(customerService.getUserCart());
		cartModel.setPriceDisplayConfiguration(getPriceDisplayConfiguration());
		cartModel.setCarritoTotal(getCarritoTotal());
		cartModel.setGrupoPrecioCommerceTools(commercetoolsservice.getGrupoPrecioCommerce());
		
		/* Para pruebas - no vale
		List<LineItem> lines = cartModel.getCarrito().getLineItems();
		for (LineItem item: lines) {
			
//			item.getPrice().getValue().getCentAmount();
//			item.getTotalPrice().getCentAmount();
			
//			LocalizedString loc =((LocalizedString) item.getVariant().getAttributes().get(0).getValue()).get(getLocale());
			
			Map<String, Object> map = item.getCustom().getFields().values();
			String valor = (String) item.getCustom().getFields().values().get("refPackPromos");
			log.debug(valor);
		}*/
		
		return cartModel;
	}
	
	public boolean compare(Number n, Number p) {
		if (n.floatValue() > p.floatValue()) {
			return true;
		} else  {
			return false;
		}
	}
	
	public DireccionesDTO getUserDirecciones() {
		String idClient = MyShopUtils.getUserName();
		
		if (idClient.length() > 2)
			idClient = idClient.substring(0, idClient.length() - 2);
		
		return middlewareService.getDirecciones(idClient);
	}
	
	public Boolean isDefaultAddress(String idDireccion) {
		String defaultAddress = customerService.getDefaultAddressId(MyShopUtils.getUserName());
		if (defaultAddress.isEmpty()) //no tiene seteada direccion de envio
			return false;
		else
			return idDireccion.equals(defaultAddress);
	}
	
	public Cart currentUserCart() {
		Cart carr = customerService.getUserCart();

		return carr;
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
	
	public float getStock(LineItem linea) {
		float stock = 0;
		if (MyShopUtils.hasAttribute("LC_sku", linea.getVariant().getAttributes())) {
			String sku = (String) MyShopUtils.findAttribute("LC_sku", linea.getVariant().getAttributes()).getValue();
			stock = middlewareService.getStock(sku);
		}
		return stock;
	}
	
	//PROBAR BIEN!!
	public String getCarritoTotal() {
		Cart carrito = currentUserCart();
		if (carrito == null) {
			return "0";
		} else {
			
			Money total = cartService.getPvoByCart(carrito);
			
			/*TypedMoney total = MyShopUtils.getTypeMoney("0");
			
			for (LineItem item: carrito.getLineItems()) {
				
				boolean enDeposito = false;
				
				if ((item.getCustom() != null) && (item.getCustom().getFields().values().get("enDeposito") != null)) {
					enDeposito = (boolean) item.getCustom().getFields().values().get("enDeposito");
				}
				
				if(!enDeposito) {
					if ((item.getCustom() != null) && (item.getCustom().getFields().values().get("pvoConDescuento") != null)) {
						CentPrecisionMoney pvoConDescuento = (CentPrecisionMoney) item.getCustom().getFields().values().get("pvoConDescuento");
						Long suma = total.getCentAmount() + (pvoConDescuento.getCentAmount()*item.getQuantity());
						total.setCentAmount(suma);
					} else {
						Long suma = total.getCentAmount() + item.getTotalPrice().getCentAmount();
						total.setCentAmount(suma);
					}
				}
			}
			
			for (CustomLineItem item: carrito.getCustomLineItems()) {
				Long suma = total.getCentAmount() + item.getTotalPrice().getCentAmount();
				total.setCentAmount(suma);
			}*/
			
			return MyShopUtils.formatMoney(total);
		}
	}
	
	
	public String getPriceDisplayConfiguration() {
		String configuration= getPriceDisplayRol();
		
		try {
			Node userNode = NodeUtil.getNodeByIdentifier("users", MyShopUtils.getUuid());
			
			if (userNode.hasNode(MyshopConstants.VIEWPRICES_USER_NODE_NAME) 
					&& userNode.getNode(MyshopConstants.VIEWPRICES_USER_NODE_NAME).hasProperty(MyshopConstants.PRICE_CONFIGURATION_PROPERTY)) {
				Node priceNode = userNode.getNode(MyshopConstants.VIEWPRICES_USER_NODE_NAME);
					configuration = priceNode.getProperty(MyshopConstants.PRICE_CONFIGURATION_PROPERTY).getString();
			} else {
				//consulto por roles
				Collection<String> roles = userservice.getRolesCurrentClient();
				for (String rolMagnolia: roles) {
	        		if (rolMagnolia != null)
	        			switch (rolMagnolia) {
	    				case "empleado_cione_precio_oculto":
	    					return "hidden";
	    				case "empleado_cione_precio_pvo":
	    					return "pvo";
	    				case "empleado_cione_precio_pvp":
	    					return "pvp";
	    				case "empleado_cione_precio_pvppvo":
	    					return "pvp-pvo";
	        			}
	        	}
			}
		} catch (RepositoryException e) {
			log.error("Exception while obtaining user's node: ", e);
			
		}

		return configuration;
	}
	
	public String getPriceDisplayRol() {
		String configuration;
		
		//Collection<String> userRoles = MgnlContext.getUser().getRoles();
		Collection<String> userRoles = userservice.getRolesCurrentClient();
		
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
	
	public float getStockbyAliasEkon(String aliasEkon) {
		return middlewareService.getStock(aliasEkon);
	}
	
	public VariantDTO getVariantInfoPromociones(ProductVariant variante) {
		return cartService.getPromocionesByVariant(variante);
	}
    
    public String getUuid() {
    	return MyShopUtils.getUuid();
    }
    
    public String getUserName() {
		return MyShopUtils.getUserName();
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
    
    public String getCode() {
    	return CioneUtils.generateRef();
    }
    
    public String getAlmacen() {
    	return MgnlContext.getWebContext().getRequest().getSession().getAttribute(MyshopConstants.ATTR_STOCK_SESSION).toString();
    }
    
	public boolean hasPermissionsRoles(ContentMap contentRolesAllowed) {
		boolean result = false;
		
		try {
			if (MgnlContext.getUser().hasRole(CioneRoles.EMPLEADO_CIONE_BANNER))
				return false;
			if (MgnlContext.getUser().hasRole("superuser")) {
				return true;
			}
			
			List<String> listado = new ArrayList<>();
			Node nodeRolesAllowed = contentRolesAllowed.getJCRNode();
			List<Node> childrenList = NodeUtil.asList(NodeUtil.getNodes(nodeRolesAllowed));
			for (Node node : childrenList) {
				Property roleAllowedProperty = PropertyUtil.getPropertyOrNull(node, "rol");
				if (roleAllowedProperty != null) {
					Node role = SessionUtil.getNodeByIdentifier("userroles", roleAllowedProperty.getString());
					// existe el rol (no se ha borrado ni nada deso)
					if (role != null) {
						listado.add(role.getName());
						log.debug("ROL = " + role.getName());
						if (MgnlContext.getUser().hasRole(role.getName())) {
							result = true;
						}
					}
				}
			}
			
		} catch (Exception e) {
			log.debug(e.getMessage());
			//log.debug("rolFilter field is empty");
			result = false;
		}
		
		return result;
	}
	
	public boolean hasPermissionsCampana(String campana) {
		boolean result = false;
		String flag = "es";
		//comprobamos si no tiene acceso por rol de empleado
		if (MgnlContext.getUser().hasRole(CioneRoles.EMPLEADO_CIONE_BANNER))
			return false;
		if (MgnlContext.getUser().hasRole("superuser")) {
			return true;
		}
		
		
		//campañas
		if (campana.equals("all")) {
			if (MgnlContext.getUser().hasRole(CioneRoles.OPTCAN) 
				|| MgnlContext.getUser().hasRole(CioneRoles.OPTMAD)
				|| MgnlContext.getUser().hasRole(CioneRoles.CLIENTE_MONTURAS)
				|| MgnlContext.getUser().hasRole(CioneRoles.OPTICAPRO)) {
				return false;
			} else {
				return true;
			}
		}
		
		if (MgnlContext.getUser().hasRole(CioneRoles.SICIO_CIONE_PORTUGAL)
				|| MgnlContext.getUser().hasRole(CioneRoles.CLIENTES_PORTUGAL)
				|| MgnlContext.getUser().hasRole(CioneRoles.CLIENTE_PORTUGAL_VCO)
				|| MgnlContext.getUser().hasRole(CioneRoles.PORLENS)) {
			flag = "pt";
		} else if (MgnlContext.getUser().hasRole(CioneRoles.OPTCAN)
				|| MgnlContext.getUser().hasRole(CioneRoles.OPTMAD)
				|| MgnlContext.getUser().hasRole(CioneRoles.CLIENTE_MONTURAS)) {
			flag = "optofive";
		} else if (MgnlContext.getUser().hasRole(CioneRoles.OPTICAPRO)) {
			flag = "opticapro";
		}
		if (campana.equals(flag)) {
			return true;
		}

		return result;
	}
	
	public String getPagePath() {
        try {
        	String path = MgnlContext.getAggregationState().getMainContentNode().getPath();
            return path;
        } catch (Exception e) {
            return "";
        }
    }
	
	public boolean hasPacks() {
		if (listPacksFilter.size() > 0)
			return true;
		else
			return false;
	}
	
	public List<ProductFrontDTO> getPacksCart() {
		Customer customer = commercetoolsservice.getCustomerSDK();
		List<ProductFrontDTO> listPacksFilter = new ArrayList<>();
		if (customer != null) {
			String skuPackMaster = MgnlContext.getWebContext().getRequest().getParameter("skuPackMaster");
			List<Cart> listPacks = customerService.getCartPackByCustomerId(customer.getId());
			
			for(Cart pack: listPacks) {
				if ((pack.getCustom().getFields().values().get("idPurchase")!= null) && (!pack.getCustom().getFields().values().get("idPurchase").equals(skuPackMaster)) ) {
					ProductProjection product = commercetoolsservice.getProductBysku(pack.getCustom().getFields().values().get("idPurchase").toString(), commercetoolsservice.getGrupoPrecioCommerce());
					if (product != null) {
						ProductFrontDTO pfront = new ProductFrontDTO();
						pfront.setImages(getImages(product.getMasterVariant()));
						pfront.setSku(product.getMasterVariant().getSku());
						pfront.setName(getName(product.getName()));
						pfront.setDescripcionPack(getName(product.getDescription()));
						pfront.setIdCarritoOculto(pack.getId());
						
						listPacksFilter.add(pfront);
					}
				}
			}
		} 
		
		return listPacksFilter;
	}
	
	private List<com.magnolia.cione.beans.Image> getImages(ProductVariant masterVariant) {
		
		List<com.magnolia.cione.beans.Image> images = new ArrayList<>();
		
		if (masterVariant != null) {
			for(Image i: masterVariant.getImages()) {
				com.magnolia.cione.beans.Image image = new com.magnolia.cione.beans.Image();
				image.setUrl(i.getUrl());
				images.add(image);
			}
		}
		
		return images.isEmpty() ? Collections.emptyList() : images;
	}
	
	private String getName(LocalizedString name) {
		
		if (name != null) {
			Optional<String> s = name.find(new Locale("es"));
			return s.isPresent() ? s.get() : null;
		}
		
		return null;
	}
	
	public List<ProductFrontDTO> getListPacksFilter() {
		return listPacksFilter;
	}

}
