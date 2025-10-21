package com.magnolia.cione.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.common.Price;
import com.commercetools.api.models.customer_group.CustomerGroup;
import com.commercetools.api.models.product.ProductProjection;
import com.commercetools.api.models.product.ProductVariant;
import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.dao.PackDao;
import com.magnolia.cione.dao.PromocionesDao;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.setup.CioneEcommerceConectionProvider;
import com.magnolia.cione.utils.CioneUtils;


public class CommercetoolsServiceAuxImpl implements CommercetoolsServiceAux {
	
	private static final Logger log = LoggerFactory.getLogger(CommercetoolsServiceAuxImpl.class);
	
	@Inject
	private MiddlewareService middlewareService;

	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider;
	
	@Inject
	private PromocionesDao promocionesDao;
	
	@Inject
	private CategoryService categoryservice;
	
	@Inject
	private PackDao packDao;
	
	public CommercetoolsServiceAuxImpl() {
		super();
	}
	
	public ProductProjection getProductBySku(String sku, String grupoPrecio) {
		ProductProjection product = null;
		List<String> querys = new ArrayList<>();
		querys.add("variants(sku=\"" + sku + "\") or masterVariant(sku=\"" + sku + "\")");
		if ((grupoPrecio != null) && !grupoPrecio.isEmpty())
			querys.add("masterVariant(attributes(name=\"gruposPrecio\" and value=\""+grupoPrecio+"\")) ");
		try {
			List<ProductProjection> products =  cioneEcommerceConectionProvider.getApiRoot().productProjections()
				.get()
				.withWhere(querys)
			    .withStaged(false)
			    .executeBlocking()
			    .getBody()
			    .getResults();
			
			if ((products!=null) && !products.isEmpty()) {
				product = products.get(0);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return product;
	}
	
	public ProductProjection getProductBySkuFilter(String sku, String grupoPrecio) {
		ProductProjection product = null;
		List<String> filters = new ArrayList<>();
		filters.add("variants.sku:\"" + sku + "\"");
		if ((grupoPrecio != null) && !grupoPrecio.isEmpty())
			filters.add("variants.attributes.gruposPrecio:\""+ grupoPrecio +"\"");
		List<ProductProjection> products = cioneEcommerceConectionProvider.getApiRoot().productProjections()
				.search().get()
                .withMarkMatchingVariants(true)
                .withStaged(false) // Para obtener solo los productos publicados
                //.withFilterQuery(f -> f.variants().sku().is(sku)) // Filtra por SKU
                .withFilter(filters)
                .executeBlocking()
                .getBody()
                .getResults();
		if ((products!=null) && !products.isEmpty()) {
			product = products.get(0);
		}
		return product;
	}
	
	public String getGrupoPrecioCommerce() {
		String grupoCommerceTools = "";
    	UserERPCioneDTO user =middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP());
    	if (user != null) {
	    	String grupoEkon = user.getGrupoPrecio();
	    	grupoCommerceTools = CioneConstants.equivalenciaEKONCommerceTools.get(grupoEkon);
    	}
    	return grupoCommerceTools;
	}
	
	public Price getPriceBycustomerGroup(String keycustomergroup, List<Price> prices) {
		
		CustomerGroup customerGroup = cioneEcommerceConectionProvider.getCustomerByKey(keycustomergroup);
		
		Price priceGroup = null;
		
		//prices.stream().anyMatch(price -> Optional.ofNullable(price.getCustomerGroup()).orElse(customerGroupReference -> customerGroupReference.getCustomerGroup().getId().equals(customerGroup.getId())));
		if (customerGroup != null) {
			for (Price price : prices) {
				if (price.getCustomerGroup() == null) //es any
					priceGroup = price;
				else {
					if (price.getCustomerGroup().getId().equals(customerGroup.getId())) {
						priceGroup = price;
						break;
					}
				}
					
			}
		}	
		
		return priceGroup;
	}
	
	public ProductVariant findVariantBySku(ProductProjection product, String sku) {
		Optional<ProductVariant> productVariant= null;
		
		if (product.getMasterVariant().getSku().equals(sku)) {
			productVariant = Optional.of(product.getMasterVariant());
		} else {
			productVariant = product.getVariants().stream().filter(v -> sku.equals(v.getSku())).findFirst();
		}
		
		return productVariant.get();
	}
	
	public List<ProductVariant> getAllVariants(ProductProjection product) {
		List<ProductVariant> allVariants = new ArrayList<>();
		
		allVariants.add(product.getMasterVariant());
		allVariants.addAll(product.getVariants());
		
		return allVariants;
		
	}

}
