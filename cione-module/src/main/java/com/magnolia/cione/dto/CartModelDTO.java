package com.magnolia.cione.dto;

import com.commercetools.api.models.cart.Cart;

public class CartModelDTO {
	private Cart carrito;
	private String grupoPrecioCommerceTools;
	private String priceDisplayConfiguration;
	private String carritoTotal;
	
	public Cart getCarrito() {
		return carrito;
	}
	public void setCarrito(Cart carrito) {
		this.carrito = carrito;
	}
	public String getGrupoPrecioCommerceTools() {
		return grupoPrecioCommerceTools;
	}
	public void setGrupoPrecioCommerceTools(String grupoPrecioCommerceTools) {
		this.grupoPrecioCommerceTools = grupoPrecioCommerceTools;
	}
	public String getPriceDisplayConfiguration() {
		return priceDisplayConfiguration;
	}
	public void setPriceDisplayConfiguration(String priceDisplayConfiguration) {
		this.priceDisplayConfiguration = priceDisplayConfiguration;
	}
	public String getCarritoTotal() {
		return carritoTotal;
	}
	public void setCarritoTotal(String carritoTotal) {
		this.carritoTotal = carritoTotal;
	}
}
