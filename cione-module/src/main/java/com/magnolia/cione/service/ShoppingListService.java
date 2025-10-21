package com.magnolia.cione.service;

import java.io.File;

import javax.ws.rs.core.Response;

import com.commercetools.api.models.cart.Cart;
import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.ShoppingListDTO;

import info.magnolia.ecommerce.common.Connection;

@ImplementedBy(ShoppingListServiceImpl.class)
public interface ShoppingListService {
	
	/**
	 * 
	 * Metodo que devuelve todos los presupuestos con paginacion. No todos 
	 * los elementos son obligatorios. 
	 * 
	 * @param connection conexion para commercetools
	 * @param date filtro de fecha (opcional)
	 * @param customermail filtro por mail (opcional)
	 * @param numbudget filtro por id de presupuesto (opcional)
	 * @param numcliente filtro por numero de cliente (opcional)
	 * @param page paginacion
	 * @return listado de de carritos (que son tratados como presupuesto)
	 * */
	 
	public Response getShoppingLists(String date, String customermail, String numbudget, String numcliente, int page);

	/*public PagedQueryResult<Cart> getShoppingLists(String date, String customermail, String numbudget, 
			String numcliente, int page);*/
	
	/**
	 * 
	 * Obtener presupuesto por id del mismo. Es usado para la pagina
	 * de detalle del presupuesto.
	 * 
	 * @param connection conexion para commercetools
	 * @param id del presupuesto
	 * @return Cart tratado como presupuesto
	 * */
	 
	public Cart getShoppingListById(Connection connection, String id);

	/**
	 * Metodo para convertir el carrito actual en presupuesto. Se limita
	 * a obtener el carrito actual del usuario y convertirlo en presupuesto.
	 * 
	 * @param connection conexion para commercetools
	 * @return Presupuesto (Cart) que se acaba de incorporar al presupuesto
	 * */
	 
	public Cart addCartToShoppingList();
	
	
	/**
	 * Elimina un presupuesto dado su id
	 * 
	 * @param connection conexion para commercetools
	 * @param id del presupuesto
	 * @return Presupuesto (Cart) que ha sido eliminado
	 * */
	 
	public Cart removeShoppingList(String id);

	/**
	 * 
	 * Realiza un merge con el carrito actual.
	 * 
	 * @param connection conexion para commercetools
	 * @param id del presupuesto que se pretende recuperar
	 * @return Presupuesto (Cart) que se acaba de recuperar
	 * */
	 
	public Cart getCartByShoppingListAndRetrieve(Connection connection, 
			String id);

	/**
	 * 
	 * Actualiza los campo del formulario
	 * 
	 * @param connection conexion para commercetools
	 * @param shoppingListDTO datos del presupuesto
	 * @return Presupuesto (Cart) que se ha actualizado
	 * */
	 
	public Cart updateShoppingList(Connection connection, ShoppingListDTO shoppingListDTO);

	/**
	 * 
	 * Actualiza un campo custom de un item del presupuesto
	 * 
	 * @param connection conexion para commercetools
	 * @param shoppingListDTO datos del campo a actualizar
	 * @param updatedField nombre del campo a actualizar
	 * @return Presupuesto (Cart) que se ha actualizado
	 * */
	 
	public Cart updateCustomField(Connection connection, ShoppingListDTO shoppingListDTO, 
			String updatedField);

	/**
	 * 
	 * Actualiza un campo custom de un item custom del presupuesto
	 * 
	 * @param connection conexion para commercetools
	 * @param shoppingListDTO datos del campo a actualizar
	 * @param updatedField nombre del campo a actualizar
	 * @return Presupuesto (Cart) que se ha actualizado
	 * */
	 
	public Cart updateCustomFieldCLI(Connection connection, ShoppingListDTO shoppingListDTO, 
			String updatedField);
	
	/**
	 * 
	 * Metodo para verificar que el id del usuario
	 * es el id del usuario conectado
	 * 
	 * @param id del presupuesto
	 * @return true o false si es el mismo usuario logado
	 * */
	 
	public boolean isUserCart(String id);
	
	/**
	 * 
	 * Obtenemos el PDF segun el id de un presupuesto
	 * @param connection conexion para commercetools
	 * 
	 * @param id del presupuessto
	 * @return PDF del presupuesto
	 * */
	 
	public File getBudgetPDF(Connection connection, String id);
	
}
