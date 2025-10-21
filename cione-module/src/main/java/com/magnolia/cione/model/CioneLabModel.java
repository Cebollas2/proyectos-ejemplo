package com.magnolia.cione.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.customer.Customer;
import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.constants.CioneRoles;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.AgrupadorDTO;
import com.magnolia.cione.dto.CioneLabDTO;
import com.magnolia.cione.dto.CustomerCT;
import com.magnolia.cione.service.CartService;
import com.magnolia.cione.service.CommercetoolsService;
import com.magnolia.cione.service.UserService;
import com.magnolia.cione.utils.MyShopUtils;

import info.magnolia.cms.security.User;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;

public class CioneLabModel <RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {
	
	private static final Logger log = LoggerFactory.getLogger(CioneLabModel.class);
	
	@Inject
	private CommercetoolsService commercetoolsService;
	
	@Inject
	private UserService userService;
	
	@Inject
	private CartService cartService;

	public CioneLabModel(Node content, RD definition, RenderingModel<?> parent) {
		super(content, definition, parent);
	}
	
	
	public CioneLabDTO getInfo() {

		String idUsuario = MyShopUtils.getUserName();
		String grupoPrecio = getGrupoPrecioEkon();
		String profile = getVisibilityPrice();
		String idCarrito = getCartId();
		
		CioneLabDTO cioneLab = new CioneLabDTO();
		cioneLab.setIdSocio(idUsuario);
		cioneLab.setGrupoPrecio(grupoPrecio);
		cioneLab.setVisibilidad(profile);
		cioneLab.setIdCarrito(idCarrito);
		
		
		//es un pack generico
		if ((MgnlContext.getParameter("skuPackMaster")!= null) && (!MgnlContext.getParameter("skuPackMaster").isEmpty())) {
			String skuPackMaster = MgnlContext.getParameter("skuPackMaster");
			cioneLab.setSkuPackMaster(skuPackMaster);
			HashMap<String, List<AgrupadorDTO>> infoPackMapSession = commercetoolsService.getInfoPackMapSession();
			if ((infoPackMapSession != null) && (infoPackMapSession.get(skuPackMaster) != null)) {
				List<AgrupadorDTO> productosPackGenerico = infoPackMapSession.get(skuPackMaster);
				for(AgrupadorDTO elemento: productosPackGenerico) {
					if (elemento.isConfigurado() && (elemento.getTipoProductoPackKey() !=null) && elemento.getTipoProductoPackKey().equals("GG")) {
						cioneLab.setSkuMontura(elemento.getSkuProductoConfigurado());
						cioneLab.setIdCarritoOculto(elemento.getIdCarritoOculto());
					}
				}
				if ((MgnlContext.getParameter("pack")!= null) && (!MgnlContext.getParameter("pack").isEmpty())) 
					cioneLab.setPack(MgnlContext.getParameter("pack"));
			}
		}
		
		return cioneLab;
	}
	
	
	/*
	 * Recorre los roles del usuario y nos devuelve el grupo de Precio con la nomenclatura de Ekon
	 */
    public String getGrupoPrecioEkon() {
    	
    	//Collection<String> roles = MgnlContext.getUser().getAllRoles();
    	Collection<String> roles = userService.getRolesCurrentClient();
    	String grupoPrecio="";
    	
    	for (String rol: roles) {
    		String rolMagnolia = CioneConstants.equivalenciaRolMagnoliaEkon.get(rol);
    		if (rolMagnolia != null) {
    			if (!grupoPrecio.isEmpty()) {
    				if (!rol.equals(CioneRoles.CONNECTA)) {
    					grupoPrecio=rolMagnolia;
    				}
    			}else {
    				grupoPrecio=rolMagnolia;
    			}
    		}
    	}
    	
    	return grupoPrecio;
    }
    /*
     * 1 - ve pvo
     * 2 - ve pvp
     * 3 - ve pvo y pvp
     * */
    public String getVisibilityPrice() {
    	
    	Collection<String> roles = userService.getRolesCurrentClient();
    	String result = "3";
    	/*si tenemos que hacerlo por la propiedad */
    	Node userNode;
		try {
			User user = userService.getUserCurrentClient();
			
			userNode = NodeUtil.getNodeByIdentifier("users", user.getIdentifier());
			if (userNode.hasNode(MyshopConstants.VIEWPRICES_USER_NODE_NAME)
					&& userNode.getNode(MyshopConstants.VIEWPRICES_USER_NODE_NAME).hasProperty(MyshopConstants.PRICE_CONFIGURATION_PROPERTY)) {
	    		String rolMgn = userNode.getNode(MyshopConstants.VIEWPRICES_USER_NODE_NAME).getProperty(MyshopConstants.PRICE_CONFIGURATION_PROPERTY).getString();
	    		if ((rolMgn != null) && !rolMgn.isEmpty()) {
	    			switch (rolMgn) {
					case "pvo":
						return "1";
					case "pvp":
						return "2";
					case "hidden":
						return "0";
					case "pvp-pvo":
						return "3";
	    			}
	    			
	    		} else {
	    			return "3";
	    		}
	    	} else {
	    		//miro por roles
	    		for (String rolMagnolia: roles) {
	        		if (rolMagnolia != null)
	        			switch (rolMagnolia) {
	    				case "empleado_cione_precio_oculto":
	    					return "0";
	    				case "empleado_cione_precio_pvo":
	    					return "1";
	    				case "empleado_cione_precio_pvp":
	    					return "2";
	    				case "empleado_cione_precio_pvppvo":
	    					return "3";
	        			}
	        	}
	    	}
		} catch (RepositoryException e) {
			log.error(e.getMessage());
		}
    	return result;
    }
    
    public String getCartId() {
    	Customer customer = commercetoolsService.getCustomerSDK();
    	if (customer != null) {
			
			Cart cart = cartService.getCart(customer.getId());
			
			if (cart == null) {
				cart = cartService.createCart(customer, MyshopConstants.deleteDaysAfterLastModification);
			}
			return cart.getId();
    	} else {
    		return null;
    	}
    }

}
