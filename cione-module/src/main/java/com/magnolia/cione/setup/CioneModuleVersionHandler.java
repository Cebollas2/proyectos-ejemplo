package com.magnolia.cione.setup;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.ImportUUIDBehavior;

import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.BootstrapSingleModuleResource;
import info.magnolia.module.delta.DeltaBuilder;
import info.magnolia.module.delta.FilterOrderingTask;
import info.magnolia.module.delta.Task;

/**
 * This class is optional and lets you manage the versions of your module, by
 * registering "deltas" to maintain the module's configuration, or other type of
 * content. If you don't need this, simply remove the reference to this class in
 * the module descriptor xml.
 *
 * @see info.magnolia.module.DefaultModuleVersionHandler
 * @see info.magnolia.module.ModuleVersionHandler
 * @see info.magnolia.module.delta.Task
 */
public class CioneModuleVersionHandler extends DefaultModuleVersionHandler {

	public CioneModuleVersionHandler() {
		
		// Version 1.57
		register(DeltaBuilder.update("1.58-SNAPSHOT", "")
			.addTask(new BootstrapSingleModuleResource("Configuracion Endpoints", "",
					"config.modules.cione-module.rest-endpoints.xml",
					ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
			.addTask(new BootstrapSingleModuleResource("Mime Type de Woff2", "",
					"config.server.MIMEMapping.woff2.xml",
					ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
			.addTask(new BootstrapSingleModuleResource("Configuracion Endpoints Noticias", "",
					"config.modules.cione-module.rest-endpoints.userNewsEndpoint.xml",
					ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
		);
		
		// Version 1.60
		register(DeltaBuilder.update("1.60", "")
				.addTask(new BootstrapSingleModuleResource("Nuevo Rol: Empleado CIONE - Catalogo de Promociones", "",
						"userroles.cione.empleado_cione_catalogo_promociones.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo Rol: Empleado CIONE - CIONE University", "",
						"userroles.cione.empleado_cione_cione_university.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo Rol: Empleado CIONE - Comunicacion", "",
						"userroles.cione.empleado_cione_comunicacion.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo Rol: Empleado CIONE - Facturacion y pedidos: Envios", "",
						"userroles.cione.empleado_cione_fp_envios.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo Rol: Empleado CIONE - Facturacion y pedidos: Pedidos", "",
						"userroles.cione.empleado_cione_fp_pedidos.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo Rol: Empleado CIONE - Facturacion y pedidos", "",
						"userroles.cione.empleado_cione_fp.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo Rol: Empleado CIONE - Mis Datos", "",
						"userroles.cione.empleado_cione_mis_datos.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo Rol: Empleado CIONE - My Shop", "",
						"userroles.cione.empleado_cione_my_shop.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo Rol: Empleado CIONE - Perfil Administrador", "",
						"userroles.cione.empleado_cione_perfil_admin.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo Rol: Empleado CIONE - Perfil Empleado", "",
						"userroles.cione.empleado_cione_perfil_employee.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo Rol: Empleado CIONE - Precio Oculto", "",
						"userroles.cione.empleado_cione_precio_oculto.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo Rol: Empleado CIONE - Pantalla de Precios", "",
						"userroles.cione.empleado_cione_precio_pantalla.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo Rol: Empleado CIONE - Precio PVO", "",
						"userroles.cione.empleado_cione_precio_pvo.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo Rol: Empleado CIONE - Precio PVP", "",
						"userroles.cione.empleado_cione_precio_pvp.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo Rol: Empleado CIONE - Precio PVP + PVO", "",
						"userroles.cione.empleado_cione_precio_pvppvo.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo Rol: Empleado CIONE - RSC", "",
						"userroles.cione.empleado_cione_rsc.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo Rol: Empleado CIONE - Servicios", "",
						"userroles.cione.empleado_cione_servicios.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nueva Pagina: Alta de empleados", "",
						"website.cione.private.mis-datos.mis-datos.alta-empleado.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nueva Pagina: Gestion de empleados", "",
						"website.cione.private.mis-datos.mis-datos.gestion-usuarios.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo EndPoint: Repuestos", "",
						"config.modules.cione-module.rest-endpoints.repuestosEndpoint.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo EndPoint: Configuracion de Precios", "",
						"config.modules.cione-module.rest-endpoints.priceConfigurationEndpoint.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
			);
		register(DeltaBuilder.update("1.61-SNAPSHOT", "")
				.addTask(new BootstrapSingleModuleResource("Nuevo EndPoint: Carrito", "",
						"config.modules.cione-module.rest-endpoints.carritoEndpoint.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Configuracion roles cione_pur", "",
						"userroles.cione.cione_pur.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
			);
		register(DeltaBuilder.update("1.62-SNAPSHOT", "")
				.addTask(new BootstrapSingleModuleResource("Nuevo EndPoint: Doofinder", "",
						"config.modules.cione-module.rest-endpoints.dooFinderEndPoint.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Web access new EndPoint: Doofinder", "",
						"userroles.rest-anonymous.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo EndPoint: Stock", "",
						"config.modules.cione-module.rest-endpoints.stockEndpoint.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
			);
		register(DeltaBuilder.update("1.63-SNAPSHOT", "")
				.addTask(new BootstrapSingleModuleResource("Deny access to mycione", "",
						"userroles.cione.empleado_cione_perfil_employee.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
			);
		register(DeltaBuilder.update("1.64", "")
				.addTask(new BootstrapSingleModuleResource("Deny access to Merchandishing", "",
						"userroles.cione.cione_pur.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Deny access to Merchandishing", "",
						"userroles.cione.ciosa-primera.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo EndPoint: Lends", "",
						"config.modules.cione-module.rest-endpoints.lensEndpoint.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo EndPoint: ContactLends", "",
						"config.modules.cione-module.rest-endpoints.contactLensEndpoint.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Audiology Access Rol", "",
						"userroles.cione.audiology_access.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nivel OM 90 Rol", "",
						"userroles.cione.om_90.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nivel OM 180 Rol", "",
						"userroles.cione.om_180.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nivel OM 360 Rol", "",
						"userroles.cione.om_360.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
			);
		register(DeltaBuilder.update("1.65", "")
				.addTask(new BootstrapSingleModuleResource("Deny access to accesorios", "",
						"userroles.cione.empleado_cione_accesorios.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Deny access to audiologia", "",
						"userroles.cione.empleado_cione_audiologia.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Deny access to banner", "",
						"userroles.cione.empleado_cione_banner.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Deny access to abonos", "",
						"userroles.cione.empleado_cione_fp_abonos.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Deny access to ahorro", "",
						"userroles.cione.empleado_cione_fp_ahorro.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Deny access to albaranes", "",
						"userroles.cione.empleado_cione_fp_albaranes.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Deny access to informe blister", "",
						"userroles.cione.empleado_cione_fp_blister.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Deny access to informe consumos", "",
						"userroles.cione.empleado_cione_fp_consumos.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Deny access to facturas", "",
						"userroles.cione.empleado_cione_fp_facturas.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Deny access to abonos", "",
						"userroles.cione.empleado_cione_fp_informe_abonos.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Deny access to factura", "",
						"userroles.cione.empleado_cione_fp_otros_factura.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Deny access to pedidos taller", "",
						"userroles.cione.empleado_cione_fp_pedidos_taller.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Deny access to lentes", "",
						"userroles.cione.empleado_cione_lentes.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Deny access to lentes de contacto", "",
						"userroles.cione.empleado_cione_lentes_contacto.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Deny access to marketing", "",
						"userroles.cione.empleado_cione_marketing.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Deny access to monturas", "",
						"userroles.cione.empleado_cione_monturas.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Deny access to myom", "",
						"userroles.cione.empleado_cione_myom.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Deny access to soluciones", "",
						"userroles.cione.empleado_cione_soluciones.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
			);
		/*register(DeltaBuilder.update("1.66", "")
				.addTask(new BootstrapSingleModuleResource("Deny access to devoluciones", "",
						"userroles.cione.empleado_cione_fp_devoluciones.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Deny access to cione lovers", "",
						"userroles.cione.empleado_cione_lovers.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo EndPoint: Lends", "",
						"config.modules.cione-module.rest-endpoints.lensEndpoint.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo EndPoint: ContactLends", "",
						"config.modules.cione-module.rest-endpoints.contactLensEndpoint.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo EndPoint: ShoppingList", "",
						"config.modules.cione-module.rest-endpoints.shoppingListEndpoint.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo EndPoint: PeriodicPurchase", "",
						"config.modules.cione-module.rest-endpoints.periodicPurchaseEndpoint.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo EndPoint: Public", "",
						"config.modules.cione-module.rest-endpoints.publicEndpoint.xml",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo EndPoint: Auditar Documentos", "",
						"config.modules.cione-module.rest-endpoints.AuditarDocumentosEndpoint",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				.addTask(new BootstrapSingleModuleResource("Nuevo EndPoint: Campaigns", "",
						"config.modules.cione-module.rest-endpoints.campaignsEndpoint",
						ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING))
				);*/
		
	}
	
	@Override
	protected List<Task> getExtraInstallTasks(InstallContext installContext) {
		List<Task> extraInstallTasks = new ArrayList<Task>(super.getExtraInstallTasks(installContext));
		extraInstallTasks.add(new FilterOrderingTask("i18nCioneRest", new String[] { "i18n" }));
		return extraInstallTasks;
	}

}
