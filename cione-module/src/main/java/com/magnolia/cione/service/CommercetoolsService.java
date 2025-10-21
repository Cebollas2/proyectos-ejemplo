package com.magnolia.cione.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.commercetools.api.models.common.Price;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.product.ProductProjection;
import com.commercetools.api.models.product.ProductVariant;
import com.commercetools.api.models.tax_category.TaxCategory;
import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.AgrupadorDTO;
import com.magnolia.cione.dto.CustomerCT;
import com.magnolia.cione.dto.EmployeeDTO;
import com.magnolia.cione.dto.MasterProductFrontDTO;
import com.magnolia.cione.dto.StockMgnlDTO;
import com.magnolia.cione.dto.CT.ProductSearchCT;
import com.magnolia.cione.dto.CT.VariantsFilterCT;

@ImplementedBy(CommercetoolsServiceImpl.class)
public interface CommercetoolsService {

	/**
	 * Obtiene un ProductProjection a partir de su SKU.
	 * Busca el producto publicado que contiene la variante con el SKU indicado.
	 *
	 * @param sku SKU de la variante (no puede ser nulo)
	 * @param grupoPrecio key del grupo de precio para filtrar precios/variantes (puede ser nulo o vacío)
	 * @return el ProductProjection que contiene la variante con el SKU o null si no existe
	 */
	public ProductProjection getProductBysku(String sku, String grupoPrecio);
	
	/**
	 * Obtiene la ProductVariant correspondiente a un SKU concreto.
	 * Internamente obtiene el producto y devuelve la variante que coincide con el SKU.
	 *
	 * @param sku SKU de la variante (no puede ser nulo)
	 * @param priceGroup key del grupo de precio usado para buscar (puede ser nulo)
	 * @return la ProductVariant que corresponde al SKU o null si no se encuentra
	 */
	public ProductVariant getProductVariantBysku(String sku, String priceGroup);
	
	/**
	 * Busca una variante dentro de un ProductProjection por su SKU.
	 *
	 * @param product ProductProjection sobre el que se busca la variante (no puede ser nulo)
	 * @param sku SKU de la variante buscada (no puede ser nulo)
	 * @return la ProductVariant encontrada
	 */
	public ProductVariant findVariantBySku(ProductProjection product, String sku);
	
	/**
	 * Crea un nuevo Customer en CommerceTools a partir de los datos de un empleado.
	 * La implementación mapea campos de EmployeeDTO a CustomerDraft y realiza la llamada a la API.
	 *
	 * @param employeeDTO DTO con la información del empleado (no puede ser nulo)
	 * @return el Customer creado en CommerceTools o null si no se crea
	 */
	public Customer createCustomer(EmployeeDTO employeeDTO);
	
	/**
	 * Actualiza información del Customer correspondiente a un empleado (p. ej. dirección o email).
	 *
	 * @param employeeDTO DTO con la información del empleado a actualizar (no puede ser nulo)
	 * @return el Customer actualizado en CommerceTools
	 */
	public Customer updateCustomerEmployee(EmployeeDTO employeeDTO);
	
	/**
	 * Obtiene el ID de un CustomerGroup en CommerceTools a partir de su key.
	 *
	 * @param key key del CustomerGroup
	 * @return el ID del CustomerGroup o null si no existe
	 */
	public String getCustomerGroupByKey(String key);
	
	/**
	 * Obtiene el ID de un Custom Field (Type) en CommerceTools a partir de su key.
	 *
	 * @param key key del Custom Field (type)
	 * @return el ID del Custom Field en CommerceTools o cadena vacía si no existe
	 */
	public String getCustomFieldIdByKey(String key);

	/**
	 * Recupera el grupo de precio (key) almacenado en sesión o a partir del usuario ERP.
	 *
	 * @return key del grupo de precio en CommerceTools (puede ser cadena vacía si no se determina)
	 */
	public String getGrupoPrecioCommerce();

	/**
	 * Obtiene la key del grupo de precio de EKON a partir de una colección de roles de usuario.
	 *
	 * @param userroles colección de roles del usuario
	 * @return key del customer group equivalente en EKON o null si no se encuentra
	 */
	public String getKeyOfCustomerGroupEKONByRoles(Collection<String> userroles);

	/**
	 * Obtiene la key de un CustomerGroup dado su ID en CommerceTools.
	 *
	 * @param id ID del CustomerGroup en CommerceTools
	 * @return la key asociada al CustomerGroup o null si no existe
	 */
	public String getKeyOfCustomerGroupById(String id);
	
	/**
	 * Determina la key del CustomerGroup a partir de los roles del usuario en Magnolia/CommerceTools.
	 *
	 * @param userroles colección de roles del usuario
	 * @return key del CustomerGroup aplicable o null si no se determina
	 */
	public String getKeyOfCustomerGroupByRoles(Collection<String> userroles);
	
	/**
	 * Recupera el ID del CustomerGroup asociado a un customer identificado por su customerNumber/magnolia id.
	 *
	 * @param id customerNumber o identificador del cliente en Magnolia
	 * @return ID del CustomerGroup en CommerceTools o null si no existe
	 */
	public String getIdOfCustomerGroupByCostumerId(String id);
	
	/**
	 * Devuelve los productos preparados para el front (MasterProductFrontDTO) de una categoría concreta
	 * aplicando el filtro del grupo de precio del cliente.
	 *
	 * @param category id o lista de ids de categoría (cadena) a consultar
	 * @param keycustomergroup key del grupo de precio del cliente
	 * @param customerId id del cliente (customerNumber o id de session)
	 * @return lista de MasterProductFrontDTO con los productos preparados para mostrar en front
	 */
	public List<MasterProductFrontDTO> getProductsFrontByCategory(String category, String keycustomergroup, String customerId);

	/**
	 * Obtiene el título (nombre) de una categoría por su id y locale.
	 *
	 * @param categoryid id de la categoría
	 * @param locale código de idioma (por ejemplo "es", "en", "pt")
	 * @return el nombre de la categoría en el locale solicitado o null/cadena vacía si no existe
	 */
	public String getCategoryTitleById(String categoryid, String locale);
	
	/**
	 * Obtiene los filtros (facetas) genéricos aplicables a una categoría.
	 *
	 * @param categoryid id de la categoría
	 * @param keycustomergroup key del grupo de precio del cliente
	 * @param filters mapa con los filtros provenientes de la URL (param -> valores[])
	 * @param facetas lista adicional de facetas a incluir
	 * @return VariantsFilterCT con la información de facetas y estadísticas
	 */
	public VariantsFilterCT getFacetsGenerico(String categoryid, String keycustomergroup, Map<String, String[]> filters, ArrayList<String> facetas);
	
	/**
	 * Obtiene los filtros (facetas) aplicables a una categoría.
	 *
	 * @param categoryid id de la categoría
	 * @param keycustomergroup key del grupo de precio del cliente
	 * @param filters mapa con los filtros provenientes de la URL (param -> valores[])
	 * @return VariantsFilterCT con la información de facetas y estadísticas
	 */
	public VariantsFilterCT getFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters);

	/**
	 * Obtiene las facetas específicas para soluciones dentro de una categoría.
	 *
	 * @param categoryid id de la categoría
	 * @param keycustomergroup key del grupo de precio del cliente
	 * @param filters mapa con los filtros provenientes de la URL (param -> valores[])
	 * @return VariantsFilterCT con la información de facetas para soluciones
	 */
	public VariantsFilterCT getSolutionsFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters);

	/**
	 * Obtiene las facetas para accesorios dentro de una categoría.
	 *
	 * @param categoryid id de la categoría
	 * @param keycustomergroup key del grupo de precio del cliente
	 * @param filters mapa con los filtros provenientes de la URL (param -> valores[])
	 * @return VariantsFilterCT con la información de facetas para accesorios
	 */
	public VariantsFilterCT getAccessoriesFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters);

	/**
	 * Obtiene las facetas para la categoría "Todo audiología".
	 *
	 * @param categoryid id de la categoría
	 * @param keycustomergroup key del grupo de precio del cliente
	 * @param filters mapa con los filtros provenientes de la URL (param -> valores[])
	 * @return VariantsFilterCT con la información de facetas para audiología completa
	 */
	public VariantsFilterCT getAudiologiaCompletaFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters);

	/**
	 * Obtiene las facetas para audífonos, aplicando además un filtro obligatorio.
	 *
	 * @param categoryid id de la categoría
	 * @param keycustomergroup key del grupo de precio del cliente
	 * @param filters mapa con los filtros provenientes de la URL (param -> valores[])
	 * @param mandatoryfilter filtro obligatorio que debe aplicarse
	 * @return VariantsFilterCT con la información de facetas para audiología
	 */
	public VariantsFilterCT getAudiologiaFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters, String mandatoryfilter);

	/**
	 * Obtiene las facetas para contactología dentro de una categoría.
	 *
	 * @param categoryid id de la categoría
	 * @param keycustomergroup key del grupo de precio del cliente
	 * @param filters mapa con los filtros provenientes de la URL (param -> valores[])
	 * @return VariantsFilterCT con la información de facetas para contactología
	 */
	public VariantsFilterCT getContactologiaFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters);

	/**
	 * Obtiene las facetas para marketing dentro de una categoría.
	 *
	 * @param categoryid id de la categoría
	 * @param keycustomergroup key del grupo de precio del cliente
	 * @param filters mapa con los filtros provenientes de la URL (param -> valores[])
	 * @return VariantsFilterCT con la información de facetas para marketing
	 */
	public VariantsFilterCT getMarketingFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters);

	/**
	 * Obtiene las facetas para tapones dentro de una categoría.
	 *
	 * @param categoryid id de la categoría
	 * @param keycustomergroup key del grupo de precio del cliente
	 * @param filters mapa con los filtros provenientes de la URL (param -> valores[])
	 * @return VariantsFilterCT con la información de facetas para tapones
	 */
	public VariantsFilterCT getTaponesFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters);

	/**
	 * Obtiene las facetas para baterías dentro de una categoría.
	 *
	 * @param categoryid id de la categoría
	 * @param keycustomergroup key del grupo de precio del cliente
	 * @param filters mapa con los filtros provenientes de la URL (param -> valores[])
	 * @return VariantsFilterCT con la información de facetas para baterías
	 */
	public VariantsFilterCT getBateriasFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters);

	/**
	 * Obtiene las facetas para complementos dentro de una categoría.
	 *
	 * @param categoryid id de la categoría
	 * @param keycustomergroup key del grupo de precio del cliente
	 * @param filters mapa con los filtros provenientes de la URL (param -> valores[])
	 * @return VariantsFilterCT con la información de facetas para complementos
	 */
	public VariantsFilterCT getComplementosFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters);

	/**
	 * Obtiene las facetas para accesorios inalámbricos dentro de una categoría.
	 *
	 * @param categoryid id de la categoría
	 * @param keycustomergroup key del grupo de precio del cliente
	 * @param filters mapa con los filtros provenientes de la URL (param -> valores[])
	 * @return VariantsFilterCT con la información de facetas para accesorios inalámbricos
	 */
	public VariantsFilterCT getAccesoriosInalambricosFacets(String categoryid, String keycustomergroup, Map<String, String[]> filters);

	/**
	 * Recupera una lista de productos (DTO) a partir de una lista de SKUs.
	 *
	 * @param skus lista de SKUs a recuperar
	 * @return lista de MasterProductFrontDTO con la información mínima necesaria para el front
	 */
	public List<MasterProductFrontDTO> getProductBySkuList(List<String> skus);
	
	/**
	 * Recupera una lista de ProductProjection a partir de una lista de SKUs.
	 *
	 * @param skus lista de SKUs a recuperar
	 * @return lista de ProductProjection
	 */
	public List<ProductProjection> getProductProjectionBySkuList(List<String> skus);
	
	/**
	 * Obtiene el Price aplicable a un customer group dentro de la lista de precios de una variante.
	 * Si no existe un precio específico para el grupo, devuelve el precio "any" por defecto si existe.
	 *
	 * @param keycustomergroup key del customer group en CommerceTools
	 * @param prices lista de Price de la variante
	 * @return el Price aplicable para ese customer group o el Price por defecto (any) o null
	 */
	public Price getPriceBycustomerGroup(String keycustomergroup, List<Price> prices);
	
	/**
	 * Obtiene los productos preparados para el front aplicando filtros, paginación y ordenación.
	 * La implementación transforma los resultados del SDK de CommerceTools a objetos DTO pensados para el front.
	 *
	 * @param categoryid id de la categoría a filtrar
	 * @param keycustomergroup key del grupo de precio del usuario
	 * @param filters mapa de filtros provenientes de la URL
	 * @param skus lista de SKUs a forzar en la búsqueda (opcional)
	 * @param productsearchct DTO usado para devolver información de paginación (total y count)
	 * @param page número de página solicitado
	 * @param pagination tamaño de página (limit)
	 * @param count offset o contador inicial para la consulta
	 * @param strsorting cadena que indica el modo de ordenación
	 * @return lista de MasterProductFrontDTO con los productos listos para el front
	 */
	public List<MasterProductFrontDTO> getProductWithFilters(String categoryid, String keycustomergroup,
			Map<String, String[]> filters, List<String> skus, ProductSearchCT productsearchct, int page, int pagination, int count, String strsorting);
	
	/**
	 * Obtiene el Customer desde CommerceTools usando el SDK y el username actual de Magnolia.
	 *
	 * @return Customer obtenido vía SDK o null si no existe
	 */
	public Customer getCustomerSDK();

	/**
	 * Obtiene un cliente (CustomerCT) a partir de su número de usuario en Magnolia.
	 *
	 * @param numUsuario número de usuario en Magnolia (ej: "005080000")
	 * @return CustomerCT con la información del cliente o null si no existe
	 */
	public CustomerCT getCustomer(String numUsuario);
	
	/**
	 * Obtiene el CustomerCT del usuario actualmente autenticado en el contexto de Magnolia.
	 * Requiere que la sesión del usuario esté abierta.
	 *
	 * @return CustomerCT del usuario en sesión o null si no está disponible
	 */
	public CustomerCT getCustomer();
	
	/**
	 * Devuelve el id del customer (CommerceTools) del usuario en sesión.
	 * Si no está en sesión, intenta recuperarlo y almacenarlo en sesión.
	 *
	 * @return id del customer en CommerceTools o null si no se puede obtener
	 */
	public String getCustomerId();

	/**
	 * Comprueba si un cliente existe en CommerceTools a partir de su número de usuario en Magnolia.
	 *
	 * @param numUsuario número de usuario en Magnolia
	 * @return true si existe en CommerceTools, false en caso contrario
	 */
	public boolean customerExists(String numUsuario);

	/**
	 * Recupera el stock (cantidad disponible) para un SKU mediante el servicio de middleware.
	 *
	 * @param sku SKU del producto
	 * @return cantidad disponible en almacén (entero)
	 */
	public int getStock(String sku);

	/**
	 * Recupera el stock como DTO desde el servicio de middleware.
	 *
	 * @param sku SKU del producto
	 * @return StockMgnlDTO con información detallada de stock
	 */
	public StockMgnlDTO getStockDTO(String sku);

	/**
	 * Recupera el stock disponible descontando las unidades que el cliente tiene en el carrito.
	 *
	 * @param sku SKU del producto
	 * @param aliasEkon aliasEkon del producto (opcional) usado por el middleware
	 * @return cantidad disponible tras descontar unidades en el carrito (entero)
	 */
	public int getStockWithCart(String sku, String aliasEkon);

	/**
	 * Obtiene el número de unidades de un SKU que el cliente tiene en su carrito.
	 *
	 * @param sku SKU del producto
	 * @return número de unidades en el carrito (entero)
	 */
	public int getUnitsCart(String sku);
	
	/**
	 * Recupera la TaxCategory configurada en CommerceTools.
	 *
	 * @return TaxCategory usada en el proyecto
	 */
	public TaxCategory getTaxCategory();
	
	/**
	 * Obtiene un mapa de productos sustitutivos a partir de una variante.
	 *
	 * @param variant variante para la que se buscan productos sustitutivos
	 * @param cant cantidad mínima requerida para el sustituto
	 * @return Map donde la key es el SKU sustituto y el value una descripción o nombre mostrado
	 */
	public Map<String, String> getSustituveReplacement(ProductVariant variant, Integer cant);
	
	/**
	 * Crea una lista de filtros válidos para las consultas a la API de CommerceTools a partir
	 * del mapa de filtros obtenido de la URL.
	 *
	 * @param filters mapa con los filtros de la URL (param -> valores[])
	 * @return lista de expresiones de filtro en formato compatible con la API
	 */
	public List<String> addFilters(Map<String, String[]> filters);
	
	/**
	 * Recupera desde la sesión el HashMap con la información de packs genéricos almacenada para el usuario.
	 *
	 * @return HashMap cuya clave es el skuMasterPack y cuyo valor es una lista de AgrupadorDTO con la información del pack
	 */
	public HashMap<String, List<AgrupadorDTO>> getInfoPackMapSession();
	
	/**
	 * Devuelve el tipoPrecioPack almacenado en la sesión para el skuPackMaster actual.
	 * Se basa en la información del primer elemento (master) del pack porque el campo es sameForAll.
	 *
	 * @return string con el tipo de precio del pack (por ejemplo "GLOBAL", "INDIVIDUAL") o null si no existe
	 */
	public String getTipoPrecioPackListadoFilter();
	
	/**
	 * Calcula el PVO (precio) aplicado a un producto dentro de un pack genérico, aplicando descuentos
	 * o precios cerrados según la configuración del pack.
	 *
	 * @param pvo precio de origen del producto (Double)
	 * @param tipoPrecioPack tipo de precio del pack (CERRADO | GLOBAL | INDIVIDUAL)
	 * @param tipoProducto tipo de producto (familia) usado para emparejar en el pack
	 * @param index índice dentro de la estructura de pack (debe estar informado)
	 * @return String con el precio resultante aplicando el descuento o precio de pack
	 * @throws Exception si no se puede calcular el precio del producto dentro del pack
	 */
	public String getTipoPvoPackListadoFilter(Double pvo, String tipoPrecioPack, String tipoProducto, int index) throws Exception;
	
	/**
	 * Evalúa si una variante cumple un filtro dado en formato "variants.attributes.xxx:value" o "variants.attributes.xxx=value".
	 *
	 * @param variant variante a evaluar
	 * @param filtro cadena con el filtro en el formato esperado
	 * @return true si la variante cumple el filtro, false en caso contrario
	 */
	public boolean variantMatchesFilter(ProductVariant variant, String filtro);
	
	/**
	 * Calcula el precio PVO de origen para Portugal (formateado como String) aplicando recargos o descuentos almacenados en BBDD.
	 *
	 * @param variant variante evaluada
	 * @param grupoPrecio key del grupo de precio (por ejemplo PORCLI o PORLENS)
	 * @param price Price correspondiente a la variante para el grupo de precio
	 * @return String con el PVO de origen tras aplicar recargos/ajustes
	 */
	public String getPvoOriginForPortugal(ProductVariant variant, String grupoPrecio, Price price);
	
	/**
	 * Igual que {@link #getPvoOriginForPortugal(ProductVariant, String, Price)} pero devolviendo un double.
	 *
	 * @param variant variante evaluada
	 * @param grupoPrecio key del grupo de precio
	 * @param price Price correspondiente a la variante para el grupo de precio
	 * @return double con el PVO de origen tras aplicar recargos/ajustes
	 */
	public double getPvoOriginForPortugalDouble(ProductVariant variant, String grupoPrecio, Price price);
	
}
