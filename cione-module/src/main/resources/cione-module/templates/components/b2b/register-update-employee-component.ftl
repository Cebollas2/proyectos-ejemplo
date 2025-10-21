[#if cmsfn.editMode]

	<section class="cmp-nuevo-usuario">
	    <div class="form-wrapper">
	
	        <form class="data-panel inbox">
	            <div class="title item">Nuevo Usuario</div>
	            <div class="item-wrapper">
	                <div class="item third">
	                    <label>${i18n['cione-module.templates.components.register-update-employee-component.name']}</label>
	                    <input class="form-control" type="text">
	                </div>
	                <div class="item third">
	                    <label>${i18n['cione-module.templates.components.register-update-employee-component.code']}</label>
	                    <input class="form-control transparent" type="text" readonly value="QWERTY">
	                </div>
	                <div class="item third">
	                    <label>${i18n['cione-module.templates.components.register-update-employee-component.profile']}</label>
	                    <input class="form-control" type="text">
	                </div>
	                <div class="item third">
	                    <label>${i18n['cione-module.templates.components.register-update-employee-component.psw']}</label>
	                    <input class="form-control" type="password">
	                </div>
	                <div class="item third">
	                    <label>${i18n['cione-module.templates.components.register-update-employee-component.repsw']}</label>
	                    <input class="form-control" type="password">
	                </div>
	            </div>
	            
	            <div class="item half">
	                <div class="title item">${i18n['cione-module.templates.components.register-update-employee-component.address']}</div>
	                <div class="item-wrapper minheight">
	                    <div class="content-radio">
	                    </div>
	                </div>
	            </div>
	            
	            [#-- Permitir cambiar de precios  --]
	            <div class="item half">
	                <div class="title item">
	                    <div class="content-radio">
	                        <label class="container" style="padding-bottom:0;">
	                            <input type="radio" checked="checked" name="radio-precios">
	                            <span class="checkmark"></span>
	                            <span class="radio-title">${i18n['cione-module.templates.components.register-update-employee-component.pricesscreen']}</span> 
	                        </label>
	                    </div>
	                </div>
	                
	                [#-- Precios a mostrar  --]
	                <div class="item-wrapper minheight">
	                    <div class="content-radio">
	                        <label class="container">
	                            <input type="radio" checked="checked" name="radio-precios">
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.pvo']}</span>
	                        </label>
	                        <label class="container">
	                            <input type="radio" name="radio-precios">
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.pvp']}</span>
	                        </label>
	                        <label class="container">
	                            <input type="radio" name="radio-precios">
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.pvppvo']}</span>
	                        </label>
	                        <label class="container">
	                            <input type="radio" name="radio-precios">
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.hide']}</span>
	                        </label>
	                    </div>
	                </div>
	            </div>
				
				[#-- Accesos a myCione  --]
	            <div class="item half">
	                <div class="title item">${i18n['cione-module.templates.components.register-update-employee-component.accesstomyciones']}</div>
	                <div class="item-wrapper">
	                    <div class="content-radio">
	                        <label class="container">
	                            <input type="checkbox" checked="checked" name="checks">
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.mydata']}</span>
	                        </label>
	                        <label class="container">
	                            <input type="checkbox" name="checks">
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.billingandorders']}</span>
	                        </label>
	                        <div class="subitems">
	                            <label class="container">
	                                <input type="checkbox" name="checks">
	                                <span class="checkmark"></span>
	                                <span>${i18n['cione-module.templates.components.register-update-employee-component.orders']}</span>
	                            </label>
	                            <label class="container">
	                                <input type="checkbox" name="checks">
	                                <span class="checkmark"></span>
	                                <span>${i18n['cione-module.templates.components.register-update-employee-component.shipping']}</span>
	                            </label>
	                        </div>
	                        <label class="container">
	                            <input type="checkbox" name="checks">
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.promotionscatalog']}</span>
	                        </label>
	                        <label class="container">
	                            <input type="checkbox" name="checks">
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.communication']}</span>
	                        </label>
	                        <label class="container">
	                            <input type="checkbox" name="checks">
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.services']}</span>
	                        </label>
	                        <label class="container">
	                            <input type="checkbox" name="checks">
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.cioneuniversity']}</span>
	                        </label>
	                        <label class="container">
	                            <input type="checkbox" name="checks">
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.myshop']}</span>
	                        </label>
	                        <label class="container">
	                            <input type="checkbox" name="checks">
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.farmacione']}</span>
	                        </label>
	                        
	                        <label class="container">
	                            <input type="checkbox" name="checks">
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.rsc']}</span>
	                        </label>
	
	                    </div>
	
	                </div>
	            </div>
	        </form>
	
	    </div>
	</section>

[#else]

	[#-- Este componente es compartido para la edicion y creacion de empleados.
		En el caso de que sea una edicion, se leera el identificador del empleado de la url y se
		inicializaran todos los datos para mostrarlos en el formulario --]
	[#assign isOptofive = model.isOptofive()]
	[#assign isOpticapro = model.isOpticaPro()]
	[#if model.employee?has_content && model.validEmployee]
	
		[#assign employee = model.employee]
		[#assign name = employee.title!]
		[#assign mail = model.mail!]
		[#assign id = employee.name!model.nextid!"ERROR"]
		[#assign address = employee.address!]
		[#assign empleado_cione_perfil_employee = employee.hasRole("empleado_cione_perfil_employee")]
		[#assign empleado_cione_perfil_admin = employee.hasRole("empleado_cione_perfil_admin")]
		
		[#assign empleado_cione_mis_datos = employee.hasRole("empleado_cione_mis_datos")]
		[#assign empleado_cione_mis_datos_transporte_recsending = employee.hasRole("empleado_cione_mis_datos_transporte_recsending")]
		[#assign empleado_cione_fp = employee.hasRole("empleado_cione_fp")]
		[#assign empleado_cione_fp_pedidos = employee.hasRole("empleado_cione_fp_pedidos")]
		[#assign empleado_cione_fp_pedidos_taller = employee.hasRole("empleado_cione_fp_pedidos_taller")]
		[#assign empleado_cione_fp_abonos = employee.hasRole("empleado_cione_fp_abonos")]
		[#assign empleado_cione_fp_devoluciones = employee.hasRole("empleado_cione_fp_devoluciones")]
		[#assign empleado_cione_fp_albaranes = employee.hasRole("empleado_cione_fp_albaranes")]
		[#assign empleado_cione_fp_albaranes_audio = employee.hasRole("empleado_cione_fp_albaranes_audio")]
		[#assign empleado_cione_fp_facturas = employee.hasRole("empleado_cione_fp_facturas")]
		
		[#assign empleado_cione_fp_envios = employee.hasRole("empleado_cione_fp_envios")]
		[#assign empleado_cione_fp_consumos = employee.hasRole("empleado_cione_fp_consumos")]
		[#assign empleado_cione_fp_cionelovers = employee.hasRole("empleado_cione_fp_cionelovers")]
		[#assign empleado_cione_fp_blister = employee.hasRole("empleado_cione_fp_blister")]
		[#assign empleado_cione_fp_informe_abonos = employee.hasRole("empleado_cione_fp_informe_abonos")]
		[#assign empleado_cione_fp_ahorro = employee.hasRole("empleado_cione_fp_ahorro")]
		[#assign empleado_cione_fp_otros_factura = employee.hasRole("empleado_cione_fp_otros_factura")]
		
		
		[#assign empleado_cione_catalogo_promociones = employee.hasRole("empleado_cione_catalogo_promociones")]
		[#assign empleado_cione_comunicacion = employee.hasRole("empleado_cione_comunicacion")]
		[#assign empleado_cione_servicios = employee.hasRole("empleado_cione_servicios")]
		[#assign empleado_cione_cione_university = employee.hasRole("empleado_cione_cione_university")]
		[#assign empleado_cione_my_shop = employee.hasRole("empleado_cione_my_shop")]
		
		[#assign empleado_cione_monturas = employee.hasRole("empleado_cione_monturas")]
		[#assign empleado_cione_lentes = employee.hasRole("empleado_cione_lentes")]
		[#assign empleado_cione_lentes_contacto = employee.hasRole("empleado_cione_lentes_contacto")]
		[#assign empleado_cione_soluciones = employee.hasRole("empleado_cione_soluciones")]
		[#assign empleado_cione_audiologia = employee.hasRole("empleado_cione_audiologia")]
		[#assign empleado_cione_accesorios = employee.hasRole("empleado_cione_accesorios")]
		[#assign empleado_cione_marketing = employee.hasRole("empleado_cione_marketing")]
		
		[#assign empleado_cione_comunidad = employee.hasRole("empleado_cione_comunidad")]
		[#assign empleado_cione_myom = employee.hasRole("empleado_cione_myom")]
		[#--  --assign empleado_cione_farmacione = employee.hasRole("empleado_cione_farmacione")--]
		[#assign empleado_cione_rsc = employee.hasRole("empleado_cione_rsc")]
		
		[#assign empleado_cione_documentacion = employee.hasRole("empleado_cione_documentacion")]
		[#assign empleado_cione_boutiquePRO = employee.hasRole("empleado_cione_boutiquePRO")]
		
		[#assign empleado_cione_banner = employee.hasRole("empleado_cione_banner")]
		
		
		[#assign empleado_cione_precio_pantalla = employee.hasRole("empleado_cione_precio_pantalla")]
		[#assign empleado_cione_precio_pvo = employee.hasRole("empleado_cione_precio_pvo")]
		[#assign empleado_cione_precio_pvp = employee.hasRole("empleado_cione_precio_pvp")]
		[#assign empleado_cione_precio_pvppvo = employee.hasRole("empleado_cione_precio_pvppvo")]
		[#assign empleado_cione_precio_oculto = employee.hasRole("empleado_cione_precio_oculto")]
		
	[#else]
		
		[#assign empleado_cione_perfil_employee = true]
		[#assign empleado_cione_perfil_admin = false]
		
		[#assign empleado_cione_mis_datos = true]
		[#assign empleado_cione_mis_datos_transporte_recsending = false]
		[#assign empleado_cione_fp = true]
		[#assign empleado_cione_fp_pedidos = true]
		[#assign empleado_cione_fp_pedidos_taller = true]
		[#assign empleado_cione_fp_abonos = true]
		[#assign empleado_cione_fp_devoluciones = true]
		[#assign empleado_cione_fp_albaranes = true]
		[#assign empleado_cione_fp_albaranes_audio = true]
		[#assign empleado_cione_fp_facturas = true]
		[#assign empleado_cione_fp_consumos = true]
		[#assign empleado_cione_fp_cionelovers = true]
		[#assign empleado_cione_fp_blister = true]
		[#assign empleado_cione_fp_informe_abonos = true]
		[#assign empleado_cione_fp_ahorro = true]
		[#assign empleado_cione_fp_otros_factura = true]
		[#assign empleado_cione_fp_envios = true]
		[#assign empleado_cione_catalogo_promociones = true]
		[#assign empleado_cione_comunicacion = true]
		[#assign empleado_cione_servicios = true]
		[#assign empleado_cione_cione_university = true]
		[#assign empleado_cione_my_shop = true]
		
		[#assign empleado_cione_monturas = true]
		[#assign empleado_cione_lentes = true]
		[#assign empleado_cione_lentes_contacto = true]
		[#assign empleado_cione_soluciones = true]
		[#assign empleado_cione_audiologia = true]
		[#assign empleado_cione_accesorios = true]
		[#assign empleado_cione_marketing = true]
		
		[#assign empleado_cione_myom = true]
		[#assign empleado_cione_comunidad = true]
		[#--  --assign empleado_cione_farmacione = true --]
		[#assign empleado_cione_rsc = true]
		
		[#assign empleado_cione_documentacion = true]
		[#assign empleado_cione_boutiquePRO = true]
		
		[#assign empleado_cione_banner = false]
		
		[#assign empleado_cione_precio_pantalla = true]
		[#assign empleado_cione_precio_pvo = false]
		[#assign empleado_cione_precio_pvp = false]
		[#assign empleado_cione_precio_pvppvo = false]
		[#assign empleado_cione_precio_oculto = false]
		
	[/#if]
	
	<section class="cmp-nuevo-usuario">
	    <div class="form-wrapper">
	
	        <form class="data-panel inbox" id="register-employee-form">
	        
	            <div class="title item">Nuevo Usuario</div>
	            
	            [#-- BEGIN: datos del empleado --]
	            <div class="item-wrapper">
	            	
	            	[#-- nombre --]
	                <div class="item third">
	                    <label>${i18n['cione-module.templates.components.register-update-employee-component.name']}</label>
	                    <input name="name" class="form-control" type="text" value="${name!}" required>
	                </div>
	                
	                [#-- codigo --]
	                <div class="item third">
	                    <label>${i18n['cione-module.templates.components.register-update-employee-component.code']}</label>
	                    <input name="id" class="form-control transparent" type="text" readonly value="${id!model.nextid!"ERROR"}">
	                </div>
	                
	                [#-- perfil --]
	                <div class="item third">
	                    <label>${i18n['cione-module.templates.components.register-update-employee-component.profile']}</label>
	                    <select id="profile" class="form-control">
	                        <option value="empleado_cione_perfil_admin" [#if empleado_cione_perfil_admin]selected[/#if]>Administrador</option>
	                        <option value="empleado_cione_perfil_employee" [#if empleado_cione_perfil_employee]selected[/#if]>Empleado</option>
	                    </select>
	                </div>
	                
	                [#-- password --]
	                <div class="item third">
	                    <label id="labelpsw">${i18n['cione-module.templates.components.register-update-employee-component.psw']}</label>
	                    <input class="form-control" id="psw" name="psw" type="password" data-error="Este campo es requerido." required="">
	                </div>
	                
	                <div class="item third">
	                    <label id="labelrepsw">${i18n['cione-module.templates.components.register-update-employee-component.repsw']}</label>
	                    <input class="form-control" id="repsw" name="repsw" type="password" required="" data-match="#psw" data-match-error="Whoops, these don't match">
	                </div>
	                
	                [#-- mail --]
	                <div class="item third">
	                    <label id="labelmail">${i18n['cione-module.templates.components.register-update-employee-component.mail']}</label>
	                    <input name="mail" class="form-control" type="text" value="${mail!}" required>
	                </div>
	                
	                <div class="help-block with-errors" id="errors" style="color: red;"></div>
	                
	            </div>
	            [#-- END: datos del empleado --]
	            
	            
	            [#-- BEGIN: direcciones --]
	            <div class="item half">
	                <div class="title item">${i18n['cione-module.templates.components.register-update-employee-component.address']}</div>
	
	                <div class="item-wrapper minheight">
	                    <div class="content-radio">
	                    [#if model.getDirecciones().getTransportes()??]
		                    [#if model.getDirecciones().getTransportes()?has_content]
			                    [#list model.getDirecciones().getTransportes() as dir]
			                    
			                    	[#assign dir_predeterminada = false]
			                    	[#if address?has_content]
				                    	[#if address == dir.id_localizacion]
				                    		[#assign dir_predeterminada = true]
				                    	[/#if]
			                    	[/#if]
			                    	
			                    	[#if dir_predeterminada]
				                    	<label class="container">
				                            <div class="radio-default-wrapper">
				                                <div class="d-flex">
				                                    <input name="address" type="radio" checked="checked" name="radio" value="${dir.id_localizacion!""}" required>
				                                    <span class="checkmark"></span>
				                                    <span>${dir.direccion!""}</span>
				                                </div>
				                                <div>
				                                    <span class="radio-default"><img class="tick-icon" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/iconos/tick.png" alt="Predeterminada">
				                                        Predeterminada
			                                        </span>
				                                </div>
				                            </div>
				                        </label>
			                    	[#else]
			                    		<label class="container">
				                            <input name="address" type="radio" name="radio" value="${dir.id_localizacion!""}" required>
				                            <span class="checkmark"></span>
				                            <span>${dir.direccion!""}</span>
			                        	</label>
			                    	[/#if]
			                        
								[/#list]
							[/#if]
						[/#if]
	                        
	                    </div>
	                </div>
	            </div>
	            [#-- BEGIN: direcciones --]
	            
	            
	            [#-- BEGIN: precios --]
	            <div class="item half">
	            
	                <div class="title item">
	                    <div class="content-radio">
	                    
	                    	[#-- Precios a mostrar  --]
	                        <label class="container" style="padding-bottom:0;">
	                            <span class="radio-title">${i18n['cione-module.templates.components.register-update-employee-component.prices']}</span>
	                        </label>
	                        
	                    </div>
	                </div>
	                
	                <div class="item-wrapper minheight">
	                    <div class="content-radio content-radio-50">
	                    
	                    	[#-- precio pantalla --]
	                        <label class="container">
	                            <input type="radio" name="radio-precios" value="empleado_cione_precio_pvo" [#if empleado_cione_precio_pvo]checked[/#if] required>
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.pvo']}</span>
	                        </label>
	                        
	                        [#-- precio pantalla --]
	                        <label class="container">
	                            <input type="radio" name="radio-precios" value="empleado_cione_precio_pvp" [#if empleado_cione_precio_pvp]checked[/#if] required>
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.pvp']}</span>
	                        </label>
	                        
	                        [#-- precio pantalla --]
	                        <label class="container">
	                            <input type="radio" name="radio-precios" value="empleado_cione_precio_pvppvo" [#if empleado_cione_precio_pvppvo]checked[/#if] required>
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.pvppvo']}</span>
	                        </label>
	                        
	                        [#-- precio pantalla --]
	                        <label class="container">
	                            <input type="radio" name="radio-precios" value="empleado_cione_precio_oculto" [#if empleado_cione_precio_oculto]checked[/#if] required>
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.hide']}</span>
	                        </label>
	
	                    </div>
	                </div>
	
	            </div>
				[#-- END: precios --]
				
				[#-- BEGIN: accesos --]
	            <div class="item half">
	            
	                <div class="title item">[#if isOpticapro]${i18n['cione-module.templates.components.register-update-employee-component.accesstoopticapro']}[#else]${i18n['cione-module.templates.components.register-update-employee-component.accesstomyciones']}[/#if]</div>
	                
	                <div class="item-wrapper">
	                    <div class="content-radio">
							
							[#-- mis datos --]
	                        <label class="container">
	                            <input id="misdatos" class="pedidosenvios" type="checkbox" name="checks" value="empleado_cione_mis_datos" [#if !empleado_cione_mis_datos]checked[/#if]>
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.mydata']}</span>
	                        </label>
	                        
	                        <div class="subitems">
	                        	[#-- mis datos transporte --]
	                            <label class="container">
	                                <input id="transportsending" class="transportsending" type="checkbox" name="checks" value="empleado_cione_mis_datos_transporte_recsending" [#if empleado_cione_mis_datos_transporte_recsending || !empleado_cione_mis_datos]checked[/#if]>
	                                <span class="checkmark"></span>
	                                <span>${i18n['cione-module.templates.components.register-update-employee-component.transport']} - ${i18n['cione-module.templates.components.register-update-employee-component.transport-sending']}</span>
	                                
	                                [#-- <a class="text-decoration-none" href="#" onclick="alert('hola')"> <i class="fas fa-info-circle" style="padding-left: 20px; padding-top: 1px;">
			                            <span style='font-family: "Lato", sans-serif;font-weight: 400;padding-left: 5px;'></span>
			                        </i></a>  --]
	                            </label>
	                        </div>
	                        
	                        [#-- facturacion y pedidos --]
	                        <label class="container">
	                            <input id="pedido_facturacion" class="pedidosenvios" type="checkbox" name="checks" value="empleado_cione_fp" [#if !empleado_cione_fp]checked[/#if]>
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.billingandorders']}</span>
	                        </label>
	                        
	                        [#-- pedidos y envios --]
	                        <div class="subitems">
	                        	[#-- pedidos --]
	                            <label class="container">
	                                <input class="pedidosenvios facturacion" type="checkbox" name="checks" value="empleado_cione_fp_pedidos" [#if !empleado_cione_fp_pedidos]checked[/#if]>
	                                <span class="checkmark"></span>
	                                <span>${i18n['cione-module.templates.components.register-update-employee-component.orders']}</span>
	                            </label>
	                            [#-- pedidos taller--]
	                            <label class="container">
	                                <input class="pedidosenvios facturacion" type="checkbox" name="checks" value="empleado_cione_fp_pedidos_taller" [#if !empleado_cione_fp_pedidos_taller]checked[/#if]>
	                                <span class="checkmark"></span>
	                                <span>${i18n['cione-module.templates.components.register-update-employee-component.orders.workshop']}</span>
	                            </label>
	                            [#-- abonos--]
	                            <label class="container">
	                                <input class="pedidosenvios facturacion" type="checkbox" name="checks" value="empleado_cione_fp_abonos" [#if !empleado_cione_fp_abonos]checked[/#if]>
	                                <span class="checkmark"></span>
	                                <span>${i18n['cione-module.templates.components.register-update-employee-component.orders.payment']}</span>
	                            </label>
	                            [#-- abonos--]
	                            <label class="container">
	                                <input class="pedidosenvios facturacion" type="checkbox" name="checks" value="empleado_cione_fp_devoluciones" [#if !empleado_cione_fp_devoluciones]checked[/#if]>
	                                <span class="checkmark"></span>
	                                <span>${i18n['cione-module.templates.components.register-update-employee-component.orders.devoluciones']}</span>
	                            </label>
	                            [#-- pedidos albaranes--]
	                            <label class="container">
	                                <input class="pedidosenvios facturacion" type="checkbox" name="checks" value="empleado_cione_fp_albaranes" [#if !empleado_cione_fp_albaranes]checked[/#if]>
	                                <span class="checkmark"></span>
	                                <span>${i18n['cione-module.templates.components.register-update-employee-component.orders.delivery_note']}</span>
	                            </label>
	                            [#if !isOpticapro]
		                            [#-- pedidos albaranes audio--]
		                            <label class="container">
		                                <input class="pedidosenvios facturacion" type="checkbox" name="checks" value="empleado_cione_fp_albaranes_audio" [#if !empleado_cione_fp_albaranes_audio]checked[/#if]>
		                                <span class="checkmark"></span>
		                                <span>${i18n['cione-module.templates.components.register-update-employee-component.orders.delivery_note_audio']}</span>
		                            </label>
		                        [/#if]
	                            [#-- pedidos facturas--]
	                            <label class="container">
	                                <input class="pedidosenvios facturacion" type="checkbox" name="checks" value="empleado_cione_fp_facturas" [#if !empleado_cione_fp_facturas]checked[/#if]>
	                                <span class="checkmark"></span>
	                                <span>${i18n['cione-module.templates.components.register-update-employee-component.orders.invoices']}</span>
	                            </label>
	                            [#-- envios --]
	                            <label class="container">
	                                <input class="pedidosenvios facturacion" type="checkbox" name="checks" value="empleado_cione_fp_envios" [#if !empleado_cione_fp_envios]checked[/#if]>
	                                <span class="checkmark"></span>
	                                <span>${i18n['cione-module.templates.components.register-update-employee-component.shipping']}</span>
	                            </label>
	                            
	                            [#-- consumos --]
	                            [#if !isOptofive && !isOpticapro]
		                            <label class="container">
		                                <input class="pedidosenvios facturacion" type="checkbox" name="checks" value="empleado_cione_fp_consumos" [#if !empleado_cione_fp_consumos]checked[/#if]>
		                                <span class="checkmark"></span>
		                                <span>${i18n['cione-module.templates.components.register-update-employee-component.consumos']}</span>
		                            </label>
		                            [#-- cionelovers --]
		                            <label class="container">
		                                <input class="pedidosenvios facturacion" type="checkbox" name="checks" value="empleado_cione_fp_cionelovers" [#if !empleado_cione_fp_cionelovers]checked[/#if]>
		                                <span class="checkmark"></span>
		                                <span>${i18n['cione-module.templates.components.register-update-employee-component.cione-lovers']}</span>
		                            </label>
		                            [#-- blister --]
		                            <label class="container">
		                                <input class="pedidosenvios facturacion" type="checkbox" name="checks" value="empleado_cione_fp_blister" [#if !empleado_cione_fp_blister]checked[/#if]>
		                                <span class="checkmark"></span>
		                                <span>${i18n['cione-module.templates.components.register-update-employee-component.blister']}</span>
		                            </label>
		                            [#-- abonos --]
		                            <label class="container">
		                                <input class="pedidosenvios facturacion" type="checkbox" name="checks" value="empleado_cione_fp_informe_abonos" [#if !empleado_cione_fp_informe_abonos]checked[/#if]>
		                                <span class="checkmark"></span>
		                                <span>${i18n['cione-module.templates.components.register-update-employee-component.abonos']}</span>
		                            </label>
		                            [#-- ahorro --]
		                            <label class="container">
		                                <input class="pedidosenvios facturacion" type="checkbox" name="checks" value="empleado_cione_fp_ahorro" [#if !empleado_cione_fp_ahorro]checked[/#if]>
		                                <span class="checkmark"></span>
		                                <span>${i18n['cione-module.templates.components.register-update-employee-component.ahorro']}</span>
		                            </label>
		                            [#-- otros facturacion --]
		                            <label class="container">
		                                <input class="pedidosenvios facturacion" type="checkbox" name="checks" value="empleado_cione_fp_otros_factura" [#if !empleado_cione_fp_otros_factura]checked[/#if]>
		                                <span class="checkmark"></span>
		                                <span>${i18n['cione-module.templates.components.register-update-employee-component.otros-facturacion']}</span>
		                            </label>
	                            [/#if]
	                        </div>
	                        
	                        
	                        [#-- catalogo promociones --]
	                        [#if !isOptofive]
	                        <label class="container">
	                            <input class="pedidosenvios" type="checkbox" name="checks" value="empleado_cione_catalogo_promociones" [#if !empleado_cione_catalogo_promociones]checked[/#if]>
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.promotionscatalog']}</span>
	                        </label>
	                        
	                        [#-- comunicacion --]
	                        <label class="container">
	                            <input class="pedidosenvios" type="checkbox" name="checks" value="empleado_cione_comunicacion" [#if !empleado_cione_comunicacion]checked[/#if]>
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.communication']}</span>
	                        </label>
	                        [/#if]
	                        [#if !isOptofive && !isOpticapro]
	                        [#-- servicios --]
	                        <label class="container">
	                            <input class="pedidosenvios" type="checkbox" name="checks" value="empleado_cione_servicios" [#if !empleado_cione_servicios]checked[/#if]>
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.services']}</span>
	                        </label>
	                        
	                        [#-- cione university --]
	                        <label class="container">
	                            <input class="pedidosenvios" type="checkbox" name="checks" value="empleado_cione_cione_university" [#if !empleado_cione_cione_university]checked[/#if]>
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.cioneuniversity']}</span>
	                        </label>

	                        [/#if]
	                        [#-- my shop --]
	                        <label class="container">
	                            <input id="myshop_seccion" class="pedidosenvios" type="checkbox" name="checks" value="empleado_cione_my_shop" [#if !empleado_cione_my_shop]checked[/#if]>
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.myshop']}</span>
	                        </label>
	                        
	                        [#-- secciones myshop --]
	                        <div class="subitems">
	                        	[#-- monturas --]
	                            <label class="container">
	                                <input class="pedidosenvios myshop" type="checkbox" name="checks" value="empleado_cione_monturas" [#if !empleado_cione_monturas]checked[/#if]>
	                                <span class="checkmark"></span>
	                                <span>${i18n['cione-module.templates.components.register-update-employee-component.frame']}</span>
	                            </label>
	                            [#-- lentes--]
	                            <label class="container">
	                                <input class="pedidosenvios myshop" type="checkbox" name="checks" value="empleado_cione_lentes" [#if !empleado_cione_lentes]checked[/#if]>
	                                <span class="checkmark"></span>
	                                <span>${i18n['cione-module.templates.components.register-update-employee-component.lens']}</span>
	                            </label>
	                            [#-- lentes de contacto--]
	                            <label class="container">
	                                <input class="pedidosenvios myshop" type="checkbox" name="checks" value="empleado_cione_lentes_contacto" [#if !empleado_cione_lentes_contacto]checked[/#if]>
	                                <span class="checkmark"></span>
	                                <span>${i18n['cione-module.templates.components.register-update-employee-component.contact_lens']}</span>
	                            </label>
	                            [#-- Soluciones--]
	                            <label class="container">
	                                <input class="pedidosenvios myshop" type="checkbox" name="checks" value="empleado_cione_soluciones" [#if !empleado_cione_soluciones]checked[/#if]>
	                                <span class="checkmark"></span>
	                                <span>${i18n['cione-module.templates.components.register-update-employee-component.liquid']}</span>
	                            </label>
	                            [#if !isOpticapro]
		                            [#-- Audiologia--]
		                            <label class="container">
		                                <input class="pedidosenvios myshop" type="checkbox" name="checks" value="empleado_cione_audiologia" [#if !empleado_cione_audiologia]checked[/#if]>
		                                <span class="checkmark"></span>
		                                <span>${i18n['cione-module.templates.components.register-update-employee-component.audiology']}</span>
		                            </label>
	                            [/#if]
	                            [#-- Accesorios --]
	                            <label class="container">
	                                <input class="pedidosenvios myshop" type="checkbox" name="checks" value="empleado_cione_accesorios" [#if !empleado_cione_accesorios]checked[/#if]>
	                                <span class="checkmark"></span>
	                                <span>${i18n['cione-module.templates.components.register-update-employee-component.suministros']}</span>
	                            </label>
	                            [#if !isOptofive && !isOpticapro]
		                            [#-- Marketing --]
		                            <label class="container">
		                                <input class="pedidosenvios myshop" type="checkbox" name="checks" value="empleado_cione_marketing" [#if !empleado_cione_marketing]checked[/#if]>
		                                <span class="checkmark"></span>
		                                <span>${i18n['cione-module.templates.components.register-update-employee-component.marketing']}</span>
		                            </label>
		                        [#elseif isOpticapro]
				                    [#-- BoutiquePRO --]
		                            <label class="container">
		                                <input class="pedidosenvios myshop" type="checkbox" name="checks" value="empleado_cione_boutiquePRO" [#if !empleado_cione_boutiquePRO]checked[/#if]>
		                                <span class="checkmark"></span>
		                                <span>${i18n['cione-module.templates.components.register-update-employee-component.boutiquePRO']}</span>
		                            </label>
	                            [/#if]
	                        </div>
	                        
	                        [#-- MyOM --]
	                        [#if !isOptofive && !isOpticapro]
		                        <label class="container">
		                            <input class="pedidosenvios" type="checkbox" name="checks" value="empleado_cione_myom" [#if !empleado_cione_myom]checked[/#if]>
		                            <span class="checkmark"></span>
		                            <span>${i18n['cione-module.templates.components.register-update-employee-component.myom']}</span>
		                        </label>
	                        
		                        [#-- COMUNIDAD CIONE --]
		                        <label class="container">
		                            <input class="pedidosenvios" type="checkbox" name="checks" value="empleado_cione_comunidad" [#if !empleado_cione_comunidad]checked[/#if]>
		                            <span class="checkmark"></span>
		                            <span>${i18n['cione-module.templates.components.register-update-employee-component.comunidad-cione']}</span>
		                        </label>
	                        [/#if]
	                        [#if isOpticapro]
	                        	[#-- DOCUMENTOS --]
		                        <label class="container">
		                            <input class="pedidosenvios" type="checkbox" name="checks" value="empleado_cione_documentacion" [#if !empleado_cione_documentacion]checked[/#if]>
		                            <span class="checkmark"></span>
		                            <span>${i18n['cione-module.templates.components.register-update-employee-component.documentos']}</span>
		                        </label>
	                        [#else]
		                        [#-- RSC --]
		                        <label class="container">
		                            <input class="pedidosenvios" type="checkbox" name="checks" value="empleado_cione_rsc" [#if !empleado_cione_rsc]checked[/#if]>
		                            <span class="checkmark"></span>
		                            <span>${i18n['cione-module.templates.components.register-update-employee-component.rsc']}</span>
		                        </label>
	                        [/#if]
	                        [#-- FARMACIONE 
	                        [#if !isOptofive && !isOpticapro]
	                        <label class="container">
	                            <input class="pedidosenvios" type="checkbox" name="checks" value="empleado_cione_farmacione" [#if !empleado_cione_farmacione]checked[/#if]>
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.farmacione']}</span>
	                        </label>
	                        [/#if]--]
	                        
	                        [#-- Banner --]
	                        <label class="container">
	                            <input class="pedidosenvios" type="checkbox" name="checks" value="empleado_cione_banner" [#if !empleado_cione_banner]checked[/#if]>
	                            <span class="checkmark"></span>
	                            <span>${i18n['cione-module.templates.components.register-update-employee-component.banner']}</span>
	                        </label>
	
	                    </div>
	
	                </div>
	            </div>
	            [#-- BEGIN: accesos --]
	            
				<div class="item half">
					<div class="content-radio">
						<label class="container changeprice">
	                        <input id="preciopantalla" type="checkbox" name="checks" value="empleado_cione_precio_pantalla" [#if !empleado_cione_precio_pantalla]checked[/#if]>
							<span class="checkmark"></span>
							<span class="title item"> ${i18n['cione-module.templates.components.register-update-employee-component.pricesscreen']}</span>
						</label>
					</div>
				</div>
	            
	            <input name="token" type="hidden" value="${model.token!}" />
	            
	            <div class="panelbuttons">
	                <button class="btn-blue" type="submit" style="cursor: pointer;">${i18n['cione-module.templates.components.register-update-employee-component.save']}</button>
	            </div>
	            
	        </form>
	    </div>
	</section>
	
	<script>
	
		function initPage() {
		
			var editMode = false;
			
			var getUrlParameter = function getUrlParameter(sParam) {
			    var sPageURL = window.location.search.substring(1),
			        sURLVariables = sPageURL.split('&'),
			        sParameterName,
			        i;
			
			    for (i = 0; i < sURLVariables.length; i++) {
			        sParameterName = sURLVariables[i].split('=');
			
			        if (sParameterName[0] === sParam) {
			            return typeof sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
			        }
			    }
			    return false;
			};
		 
		 	var id = getUrlParameter('id');
		 	
		 	if(id){
		 		editMode = true;
		 		$('#psw').removeAttr( "required" );
		 		$('#repsw').removeAttr( "required" );
		 	}
		 	
			$('#register-employee-form').submit(function(e){
				e.preventDefault();
				//$('#register-employee-form').validator();
				createOrUpdateUser(this,editMode);
			});
			
	    	$('#profile').change(function() {
			  if ($(this).val() == "empleado_cione_perfil_admin"){
			  	$("input:checkbox:not(:checked).pedidosenvios").prop('checked', true);
			  }else{
			  	$("input:checkbox:checked.pedidosenvios").prop('checked', false);
			  }
			});
			
		}
		
		function createOrUpdateUser(form, editMode){
			
			[#-- Se genera el objeto que se envia a partir del formulario --]
			var isOK = false;
			
			if(editMode){
				isOK = true;
			}else{
				isOK = pswIsValid(form);
			}
			
			if(isOK){
			
				var roles = [];
			
				$("input:checkbox:not(:checked).pedidosenvios").each(function(){
				    roles.push($(this).val());
				});
				
				if ($('#transportsending').is(":checked") && (!$('#misdatos').is(":checked")))
					roles.push($('#transportsending').val());
				
				var rolprecio = $('input[name=radio-precios]:checked', '#register-employee-form').val();
				var profile = $("#profile :selected").val();
				var address = $('input[name=address]:checked', '#register-employee-form').val();
				var rolpantallaprecio;
				if (!$('#preciopantalla').is(":checked")){
					rolpantallaprecio = $('#preciopantalla').val();
				}
			
				var data = {}
				data['id'] = form['id'].value;
				data['name'] = form['name'].value;
				data['profile'] = profile;
				data['psw'] = form['psw'].value;
				data['mail'] = form['mail'].value;
				data['address'] = address;
				data['token'] = form['token'].value;
				data['roles'] = roles;
				data['rolprecio'] = rolprecio;
				data['rolpreciopantalla'] = rolpantallaprecio;
				
				sendForm(data);
			}
			
		}
		
		function emailIsValid (email) {
		  return /\S+@\S+\.\S+/.test(email)
		}
		
		function pswIsValid(form){
		
			var pass = form['psw'].value;
		    var repass = form['repsw'].value;
		    var email = form['mail'].value;
		    
		    if (!emailIsValid(email)) {
		    	$('#labelmail').css("color", "red");
		        $('#mail').css("border", "1px solid red"); 
		        $("html, body").animate({ scrollTop: $('#labelmail').offset().top }, 1000);
	        	$('#errors').html('');
	        	$('#errors').append( "<strong>${i18n['cione-module.templates.components.register-update-employee-component.mailerrorrequired']}</strong>" );
		        return false;
		    }
		    
		    if((pass.length == 0) || (repass.length == 0)){
		    
		        $('#labelpsw').css("color", "red");
		        $('#psw').css("border", "1px solid red"); 
		        $('#labelrepsw').css("color", "red");
		        $('#repsw').css("border", "1px solid red");
		        $("html, body").animate({ scrollTop: $('#labelpsw').offset().top }, 1000);
	        	$('#errors').html('');
	        	$('#errors').append( "<strong>${i18n['cione-module.templates.components.register-update-employee-component.pswerrorrequired']}</strong>" );
		        return false;
		        
		    }else if (pass != repass) {
		    
		        $('#labelpsw').css("color", "red");
		        $('#psw').css("border", "1px solid red"); 
		        $('#labelrepsw').css("color", "red");
		        $('#repsw').css("border", "1px solid red");
		        $("html, body").animate({ scrollTop: $('#labelpsw').offset().top }, 1000);
		        $('#errors').html('');
		        $('#errors').append( "<strong>${i18n['cione-module.templates.components.register-update-employee-component.pswerrorequals']}</strong>" );
		        return false;
		        
		    }else {
		        return true;
		    }
		}
		
		function sendForm(data){
			$("#loading").show();
			$.ajax({
				type: 'POST',
				url: '${ctx.contextPath}/.rest/auth/v1/employee/register',
			  	data: JSON.stringify(data),
			  	error: function( response ) {
			  		$("#loading").hide();
			  		if (response.status == 412) {
			  			alert(response.responseText);
			  		} else {
	        			alert("${i18n['cione-module.templates.components.register-update-employee-component.error']}");
	        		}
	    		},
			  	success: function(response){
			  		window.location = "./gestion-usuarios.html"; 
			  	},
			  	contentType: 'application/json; charset=utf-8'
			});
		}
	
	</script>

[/#if]
