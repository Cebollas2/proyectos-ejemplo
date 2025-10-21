package com.magnolia.cione.service;

import javax.ws.rs.core.Response;

import com.commercetools.api.models.cart.Cart;
import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.PeriodicPurchaseDTO;

import info.magnolia.ecommerce.common.Connection;

@ImplementedBy(PeriodicPurchaseServiceImpl.class)
public interface PeriodicPurchaseService {
	
	/**
	 * 
	 * Crea una nueva compra rapida
	 * 
	 * @param connection conexion para commercetools
	 * @param periodicPurchaseDTO datos de la compra rapida
	 * @return Compra rapida (Cart) que se ha creado 
	 */
	 
	public Cart addCartToPeriodicPurchase(PeriodicPurchaseDTO periodicPurchaseDTO);
	
	/**
	 * 
	 * Metodo que devuelve todos las compras periodicas 
     * con paginacion. No todos los elementos son obligatorios.  
	 * 
	 * @param date filtro de fecha (opcional)
	 * @param customermail filtro por mail (opcional)
	 * @param numbudget filtro por id de presupuesto (opcional)
	 * @param numcliente filtro por numero de cliente (opcional)
	 * @param page paginacion
	 * @return listado de de carritos (que son tratados como compra periódica)
	 */
	 
	public Response getPeriodicPurchases(String date, String customermail, String numbudget, String numcliente, int page);
	
	/**
	 * 
	 * Actualiza los campo del formulario
	 * 
	 * @param periodicPurchaseDTO datos de la compra rapida
	 * @return Compra rapida (Cart) que se ha actualizado
	 */
	 
	public Cart updatePeriodicPurchase(PeriodicPurchaseDTO periodicPurchaseDTO);
	
	/**
	 * 
	 * Metodo que devuelve todas las compras rapidas del usuario. 
	 * Este metodo es usado en la modal para anadir los productos 
	 * del carrito a una compra rapida ya existente.
	 * 
	 * @return listado de de carritos (que son tratados como compra periodica)
	 */
	 
	public Response getAllPeriodicPurchases();
	
	/**
	 * 
	 * Devuelve una compra rapida dado su id
	 * 
	 * @param id de la compra periodica
	 * @return Compra periodica (Cart)
	 */
	
	public Cart getPeriodicPurchaseById(String id);
	
	
	/**
	 * 
	 * Realiza un merge con el carrito actual.
	 * 
	 * @param id de la compra periodica que se pretende recuperar
	 * @return Compra periodica (Cart) que se acaba de recuperar
	 */
	
	public Cart getCartByPeriodicPurchaseAndRetrieve(String id);
	
	/**
	 * 
	 * Devuelve el estado segun todos los estado de las compras 
	 * periodicas del usuario en sesion. Si existe alguna 
	 * compra periodica con un estado en rojo entonces se 
	 * devolvera red, si hay algun estado amarillo y niguno rojo 
	 * entonces devolvera; yellow y si todos son verde entonces 
	 * devolvera green
	 * 
	 * @param connection conexion para commercetools
	 * @return "red", "yellow" or "green"
	 */
	
	public String getStatus(Connection connection);
	
	/**
	 * Elimina una compra rapida dado su id
	 * 
	 * @param connection conexion para commercetools
	 * @param id de la compra rapida
	 * @return Compra rapida (Cart) que ha sido eliminada
	 */
	
	public Cart removePeriodicPurchase(Connection connection, String id);
	
	/**
	 * 
	 * Metodo para verificar que el id del usuario
	 * es el id del usuario conectado
	 * 
	 * @param id del presupuesto
	 * @return true o false si es el mismo usuario logado
	 */
	
	public boolean isUserCart(String id);
	
	/**
	 * Anade una compra periodica al carrito. Realiza un merge con
	 * el carrito actual
	 * 
	 * @param connection conexion para commercetools
	 * @param id de la compra periodica
	 * @return Carrito actualizado
	 */
	
	public Cart addCartToPeriodicPurchase(String id);
	
	/**
	 * Devuelve el importe del carrito
	 * 
	 * @param Cart carrito
	 * @return importe del carrito
	 */
	public String getCarritoTotal(Cart carrito);
	
	public void setActivePeriodicPurchase();
	
	/**
	 * Metodo de pruebas de envio de mail
	 * 
	 * @param connection conexion para commercetools
	 * @param id de la compra periodica
	 * @return Carrito actualizado
	 */
	public void checkMail(Cart periodicpurchase, String mail);
	
}
