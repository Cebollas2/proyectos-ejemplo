package com.magnolia.cione.service;

import javax.ws.rs.core.Response;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CustomLineItem;
import com.commercetools.api.models.cart.LineItem;
import com.commercetools.api.models.common.Money;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.product.ProductVariant;
import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.CartParamsDTO;
import com.magnolia.cione.dto.CartParamsDevolucionDTO;
import com.magnolia.cione.dto.DireccionParamsDTO;
import com.magnolia.cione.dto.LensParamsDTO;
import com.magnolia.cione.dto.VariantDTO;

import info.magnolia.ecommerce.common.Connection;

@ImplementedBy(CartServiceImpl.class)
public interface CartService {
	
	public Response addCartCustomLine(LensParamsDTO lensParamsDTO);
	
	public Response addCartCustomLineReturn(CartParamsDevolucionDTO cartQueryParamsDTO);
	
	public Response addCart(CartParamsDTO cartQueryParamsDTO);
	
	public Cart getCart(String customerId);
	
	/**
	 * Devuelve el carrito por el identificador del carrito
	 * @param String idCart identificador del carrito
	 * @return Cart en caso de encontrar el carrito y null en caso contrario
	 * */	
	public Cart getCartById(String id);
	
	public Cart createCart(Customer customer, Long expiration);
	
	public Response updatePlazoEntrega(CartParamsDTO cartQueryParamsDTO, Integer plazoEntrega);
	
	public Response updateQuantityCLI(String cartId, String lineItemId, Integer quantity, String quoteId, info.magnolia.ecommerce.common.Customer customer, Connection connection);

	//public Response removeCLI(String cartId, String lineItemId, info.magnolia.ecommerce.common.Customer customer, Connection connection);
	public Response removeCLI(String cartId, String customLineItemId, String customer);
	
	public VariantDTO getPromocionesByVariant(ProductVariant variante);

	public Response updatePvoConDescuento(CartParamsDTO cartQueryParamsDTO, Money nuevoPvo);
	
	public Response updateShippingAddress(DireccionParamsDTO addressParams);
	
	/**
	 * Elimina el carrito del usuario identificado por idCart
	 * @param String idCart identificador del carrito
	 * @return Response respuesta de la operacion
	 * */	
	public Response deleteCart(String idCart);
	
	/**
	 * Devuelve el carrito oculto (carrito tipo pack generico) del usuario a partir de su skuPackMaster
	 * @param String customerId y String skupackMaster
	 * @return Cart de tipo pack
	 * */
	public Cart getCartPackByCustomeridPurchase(String customerId, String skuPackMaster);
	
	/**
	 * Metodo añadir los productos al del pack al carrito oculto
	 * @param CartParamsDTO con la informacion de sku y skuPackMaster
	 * */
	public Response addCartPack(CartParamsDTO cartQueryParamsDTO) throws Exception;
	
	/**
	 * Metodo añadir los productos preconfigurados al del pack al carrito oculto
	 * @param CartParamsDTO con la informacion de sku y skuPackMaster
	 * */
	public Response addCartPackPreconfigurados(CartParamsDTO cartQueryParamsDTO);
	
	/**
	 * Metodo añadir los productos del carrito oculto al carrito del usuario
	 * @param String con skuPackMaster que identifica el carrito pack
	 * */
	public Response addtoUserCartPack(CartParamsDTO cartQueryParamsDTO);

	/**
	 * Metodo añadir los productos del carrito oculto al carrito del usuario
	 * @param String con skuPackMaster que identifica el carrito pack
	 * */
	public Cart addCartLineItem(Cart cart, LineItem item, String refPackPromos, String refTaller, String refCliente);
	
	/**
	 * Metodo añadir los productos del carrito oculto al carrito del usuario
	 * @param String con skuPackMaster que identifica el carrito pack
	 * */
	public Cart addCartCustomLine(Cart cart, CustomLineItem item, String descPackPromos, String refPackPromos);
	
	/**
	 * Metodo que actualiza los campos custom fields de una LineItem
	 * El campo updatedField puede venir con los valores de aTaller o all
	 * En el caso de all, actualiza todos los campos de cartQueryParamsDTO
	 * 
	 * @param cartQueryParamsDTO objeto con los campos a actualizar
	 * @return String campo de control para la actualizacion
	 * */
	public Response updateCustomField(CartParamsDTO cartQueryParamsDTO, String updatedField);
	
	/**
	 * Metodo que actualiza los campos custom fields de una LineItem
	 * El campo updatedField puede venir con los valores de aTaller o all
	 * En el caso de all, actualiza todos los campos de cartQueryParamsDTO
	 * 
	 * @param cartQueryParamsDTO objeto con los campos a actualizar
	 * @return String campo de control para la actualizacion
	 * */
	public Response updateCustomFieldCLI(CartParamsDTO cartQueryParamsDTO, String updatedField);
	
	
	/**
	 * Metodo previo a realizar el pedido, que revisa los precios de las promociones del carrito y las actualiza 
	 * en caso de haberse modificado
	 * */
	public Response prepareCart();
	
	/**
	 * Metodo para pasar de carrito al pedido
	 * setea los campos :
	 * customerNumber codigo socio (codigo largo)
	 * codSocio - numero Socio (codigo corto)
	 * pvoFinalAcumulado - precio final acumulado tras las promociones
	 * RegisteredBy - se actualiza en caso de que el pedido se realice tras la simulacion
	 * Actualiza el orderNumber a MYScodigoautogenerado
	 * */
	public Response cartToOrder();
	
	/**
	 * Metodo para añadir un producto al carrito por su sku
	 * campos obligatorios:
	 * 	sku
	 * 	cantidad
	 * campos opcionales:
	 * 	refcliente
	 * 	coloraudio
	 * */
	public Response addCartBySku(CartParamsDTO cartQueryParamsDTO);
	
	//public void updateOrderNumber();
	
	/**
	 * 
	 * Actualiza la nota para el SAS que se envia en la pantalla del carrito
	 * 
	 * @param cartQueryParamsDTO
	 * @return OK (200) o ERROR (500)
	 */
	public Response updateSASNoteField(CartParamsDTO cartQueryParamsDTO);

	/**
	 * 
	 * Anade todos los elementos del carrito a una nueva ShoppingList
	 * 
	 * @return nueva Shopping List
	 
	public Response addCartToShoppingList(Connection connection);*/

	public String getPvo(String sku);

	/**
	 * 
	 * Devuelve el precio de un carrito, incluidos descuentos y eliminando productos en deposito
	 * 
	 * @param Cart carrito sobre el que se aplicara el calculo
	 * @return Money precio del carrito
	 */
	public Money getPvoByCart(Cart cart);
	
	/**
	 * 
	 * Devuelve si los producto del carrito tiene compra minima
	 * @param idioma por el que ha accedido el usuario
	 * @param nodo para recuperar su contenido
	 * @return MinimunPurchase
	 */
	public Response isValidaCartMinimunPurchase(String language, String contentNodePath);
}
