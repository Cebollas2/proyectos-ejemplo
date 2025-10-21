package com.magnolia.cione.service;

import java.util.List;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.customer.Customer;
import com.google.inject.ImplementedBy;

@ImplementedBy(CustomerServiceImpl.class)
public interface CustomerService {
	
	public String getDefaultAddressId(String idClient);
	
	public Cart getUserCart();
	
	public Customer getCustomerByCustomerNumber(String customerNumber);
	
	public List<Cart> getCartPackByCustomerId(String customerId);
	
	public Customer getCustomer();

}
