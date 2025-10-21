package com.magnolia.cione.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.customer.Customer;
import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.setup.CioneEcommerceConectionProvider;
import com.magnolia.cione.utils.MyShopUtils;

import io.vrap.rmf.base.client.ApiHttpResponse;

public class CustomerServiceImpl implements CustomerService {
	
	private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Inject
	private CioneEcommerceConectionProvider cioneEcommerceConectionProvider;
	
	public String getDefaultAddressId(String customerNumber) {
		
		String defaultAddressId ="";
		
		Customer customer = null;
		List<Customer> listCustomers = 
			cioneEcommerceConectionProvider.getApiRoot().customers().get()
				.withWhere("customerNumber=\"" + customerNumber + "\"")
				.executeBlocking()
				.getBody()
				.getResults();
		if ((listCustomers != null) && (!listCustomers.isEmpty())) {
			customer = listCustomers.get(0);
		
	        if (customer.getCustom() != null)
	        	defaultAddressId = (String) customer.getCustom().getFields().values().get("codDirDefault");
	        	//defaultAddressId = customer.getCustom().getFieldAsString("codDirDefault");
		}
        return defaultAddressId;
	}
	
	public Cart getUserCart() {
		Customer customer = getCustomer();
		Cart cart = null;
		if (customer != null) {
			try {
				/*CartByCustomerIdGet predicate = CartByCustomerIdGet.of(customer.getId());
				CompletionStage<Cart> request = cioneEcommerceConectionProvider.getClient().execute(predicate);
				cart = request.toCompletableFuture().join();*/
				
				
				List<String> querys = new ArrayList<>();
				querys.add("customerId=\"" + customer.getId() + "\"");
				querys.add("cartState=\"Active\"");
				querys.add("custom(fields(cartType!=\"" + MyshopConstants.PERIODIC_PURCHASE + "\"))");
				querys.add("custom(fields(cartType!=\"" + MyshopConstants.BUDGET + "\"))");
				querys.add("custom(fields(cartType!=\"" + MyshopConstants.GENERIC_PACK + "\"))");
				List<Cart> carts = 
					cioneEcommerceConectionProvider
					.getApiRoot()
					.carts()
					//.withCustomerId(customer.getId())
					.get()
					.withWhere(querys)
					.executeBlocking()
					.getBody()
					.getResults();
				
				
				if (carts.isEmpty()) {
					return null;
				} else if (carts.size() == 1) {
					return carts.get(0);
				} else if (carts.size() > 1) {
					//borrar carrito duplicado.Control para BUG carritos duplicados, si descubrimos porque se da podemos quitarlo
					log.error("CARRITO DUPLICADO!!!!!");
					for (Cart carrito: carts) {
						if (carrito.getLineItems().isEmpty()) {
							//cioneEcommerceConectionProvider.getApiRoot().carts().withId(carrito.getId()).delete().withVersion(cart.getVersion()).executeBlocking().getBody()
							ApiHttpResponse<Cart> resultDelete = cioneEcommerceConectionProvider.getApiRoot().carts()
								.withId(carrito.getId())
				                .delete()
				                .withVersion(carrito.getVersion())
				                .executeBlocking();
							if (resultDelete.getStatusCode() != 200) {
								log.error(resultDelete.getMessage());
							}
							
						} else
							cart = carrito;
					}
				}
				
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return cart;
	}
	
	public List<Cart> getCartPackByCustomerId(String customerId) {
		try {
			List<String> querys = new ArrayList<>();
			querys.add("customerId=\"" + customerId + "\"");
			querys.add("cartState=\"Active\"");
			querys.add("custom(fields(cartType=\"" + MyshopConstants.GENERIC_PACK + "\"))");
			
			return cioneEcommerceConectionProvider
					.getApiRoot()
					.carts()
					.get()
					.withWhere(querys)
					.executeBlocking().getBody().getResults();
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
		return new ArrayList<Cart>();
	}
	
	public Customer getCustomer() {
		
		String customerNumber = MyShopUtils.getUserName();
		Customer customer = null;
		List<Customer> listCustomers = 
			cioneEcommerceConectionProvider.getApiRoot().customers().get()
				.withWhere("customerNumber=\"" + customerNumber + "\"")
				.executeBlocking()
				.getBody()
				.getResults();
		if ((listCustomers != null) && (!listCustomers.isEmpty())) {
			customer = listCustomers.get(0);
		}
		
		return customer;
	}
	
	public Customer getCustomerByCustomerNumber(String customerNumber) {
		
		Customer customer = null;
		List<Customer> listCustomers = 
			cioneEcommerceConectionProvider.getApiRoot().customers().get()
				.withWhere("customerNumber=\"" + customerNumber + "\"")
				.executeBlocking()
				.getBody()
				.getResults();
		if ((listCustomers != null) && (!listCustomers.isEmpty())) {
			customer = listCustomers.get(0);
		}
		
		return customer;
	}
	
}