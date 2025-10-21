[#include "../../includes/macros/cione-utils-impersonate.ftl"]

[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]
[#assign pageNode = cmsfn.page(content)]
[#assign locale = model.getLocale()!]
[#assign dataModel = model.getDataModel()!]
[#assign carrito = dataModel.getCarrito()!]
[#assign grupoPrecio = dataModel.getGrupoPrecioCommerceTools()!]
[#assign totalItems = 0]
[#assign uuid = model.getUuid()!]
[#assign username = model.getUserName()!]

[#assign defaultImage = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/common/oops.jpg"]
[#assign defaultImageCLI = resourcesURL + "/img/myshop/common/imagennodisponible_lente_graduada.jpg"]
[#--  --assign mapPVOPackCerrado = {} --]

[#if carrito?has_content]
	[#list carrito.getLineItems() as item]
		[#assign totalItems = totalItems + item.getQuantity()]
	[/#list]
	[#list carrito.getCustomLineItems() as itemc]
		[#assign totalItems = totalItems + itemc.getQuantity()]
	[/#list]
[/#if]

[#assign hashId = content.hashidDoofinder!]
[#assign userId = '']
[#if ctx.getUser().getName()?? && ctx.getUser().getName()?has_content] 
	[#assign userId = ctx.getUser().getName()]
[/#if]



<header class="b2b-header-wrapper">
	<script>

	    var doofinder_script = '//cdn.doofinder.com/media/js/doofinder-classic.7.latest.min.js';
	    (function (d, t) {
	        var f = d.createElement(t), s = d.getElementsByTagName(t)[0]; f.async = 1;
	        f.src = ('https:' == location.protocol ? 'https:' : 'http:') + doofinder_script;
	        f.setAttribute('charset', 'utf-8');
	        s.parentNode.insertBefore(f, s)
	    }(document, 'script'));
	    var dfClassicLayers = [{
	        "hashid": "${hashId}",
	        "zone": "eu1",
	        "display": {
	            "lang": "es"
	        },
	        "queryInput": "#search-input",
	        searchParams: {
	            filter: {
	                grupos_precio: ["${grupoPrecio}"]
	            }
	
	        },
	        showInMobile: true,
	        display: {
	               facets: {
	              startCollapsed: true
	            }
	        }
	    }];
	</script>

    <!-- HEADER SUPERIOR -->
    <div class="b2b-header">
        <!-- MENÚ -->
        <div id="nav-icon3">
            <span></span>
            <span></span>
            <span></span>
            <span></span>
        </div>
        
        <!-- LOGO -->
        [#assign contentLogo = ""]
        [#if content.imagesList?? && content.imagesList?has_content]
			[#list cmsfn.children(content.imagesList) as image]
				[#assign hasPermissions = false]
				[#if image.permisos?? && image.permisos?has_content]
					[#assign field = image.permisos.field!]
					[#switch field]
			
						[#case "roles"]
							[#if model.hasPermissionsRoles(image.permisos.roles!)]
								[#assign contentLogo = image]
								[#break]
							[/#if]
						
						[#case "campaing"]
							[#if model.hasPermissionsCampana(image.permisos.campaing!)]
								[#assign contentLogo = image]
								[#break]
							[/#if]
							
						[#default]
		    				[#break]
		    		[/#switch]
		    	[#else]
		    		[#assign contentLogo = image]
				[/#if]
			[/#list]
		[/#if]
        
        
        [#if contentLogo?? && contentLogo?has_content]
        	[#if contentLogo.logo?? && contentLogo.logo?has_content]
        		[#assign link = damfn.getAssetLink("jcr:" + contentLogo.logo)!""]
				[#assign logoLink = "#"]
				[#if contentLogo.logoLink?has_content]
					[#assign logoLink = cmsfn.link("website", contentLogo.logoLink!)!]
				[/#if]
    			<a href="${logoLink}">
			        <img class="b2b-logo" src="${link}" alt="${contentLogo.logoAlt!''}" />
				</a>
			[/#if]
		[/#if]
        
        [#-- BUSCADOR --]
        <div class="b2b-header-form">
            <input type="text" placeholder="${i18n.get('cione-module.templates.myshop.header-component.search-placeholder')}" name="search-input" id="search-input" class="b2b-header-input">
            <button class="button"><i class="fa fa-search"></i></button>
        </div>
		
		[#-- SUPLANTACION --]
		[#if (ctx.getUser().hasRole("cione_superuser") || ctx.getUser().hasRole("optofive_superuser")) && ctx.user.getProperty("impersonateIdSocio")?has_content]
        <div class="b2b-header-box hidden-lg-down">
            <span class="b2b-header-box-info">Suplantando a ${ctx.user.getProperty("impersonateNameSocio")} (${ctx.user.getProperty("impersonateIdSocio")})</span>
            <span style="cursor: pointer;" class="b2b-header-box-exit" onclick="logoutSimulateUser()">[${i18n.get('cione-module.global.salir')}]</span>
        </div>
       
        [/#if]
        
        [#-- MENU DESPLEGABLE --]
        <div class="b2b-dropdown-wrapper" id="b2b-dropdown-wrapper">
            <button id="b2b-dropdown-button" class="b2b-dropdown-button">${i18n.get('cione-module.templates.myshop.header-component.my-account')}</button>
            <div class="b2b-dropdown-items">

				[#if content.editProfileLink?has_content && ctx.getUser().hasRole("empleado_cione_perfil_admin")]
					[#assign editProfileLink = cmsfn.link("website", content.editProfileLink!)!]	
	                <a href="${editProfileLink}" class="b2b-dropdown-item">${i18n.get('cione-module.templates.myshop.header-component.edit-profile')}</a>
				[/#if]
				
				[#assign configuration = dataModel.getPriceDisplayConfiguration()]
				[#if configuration == "pvo"]
		    		[#assign current = i18n.get('cione-module.templates.configuracion-precios-component.PVO')]
		    	[#elseif configuration == "pvp"]
		    		[#assign current = i18n.get('cione-module.templates.configuracion-precios-component.PVP')]
		    	[#elseif configuration == "hidden"]
		    		[#assign current =  i18n.get('cione-module.templates.configuracion-precios-component.hidden')]
		    	[#else]
		    		[#assign current = i18n.get('cione-module.templates.configuracion-precios-component.PVO-PVP')]
		    	[/#if]
		    	
				[#if content.modifyPricesLink?has_content]
					[#assign modifyPricesLink = cmsfn.link("website", content.modifyPricesLink!)!]
	                <a href="${modifyPricesLink}" id="b2b-modifyprices" class="b2b-dropdown-item">${i18n.get('cione-module.templates.myshop.header-component.modify-prices')} (${current})</a>
				[/#if]
				
				[#if content.linksList?? && content.linksList?has_content]
					[#list cmsfn.children(content.linksList) as enlace]
						[#if !ctx.getUser().hasRole("empleado_cione_fp_devoluciones")]
							[#assign href = "#"]
							
					    	[#if enlace.internalLink?? && enlace.internalLink?has_content]
					    		[#if cmsfn.contentById(enlace.internalLink)?? && cmsfn.contentById(enlace.internalLink)?has_content]
					    			[#assign href = cmsfn.link(cmsfn.contentById(enlace.internalLink))]
					    		[/#if]
					    	[/#if]
					        <a href="${href!'#'}" class="b2b-dropdown-item">
					        	[#-- 
						        [#if enlace.checkboxcp?? && enlace.checkboxcp?has_content]
									[#if enlace.checkboxcp]
									<div id="statusperiodicpurchase" class="b2b-dot-color green mt-0 mr-1"></div>
									[/#if]
								[/#if]
								--]
						        ${enlace.linkText!}
					        </a>	
						[/#if]
				    [/#list]
				[/#if]
                <a href="${cmsfn.link(pageNode)}?mgnlLogout=true" class="b2b-dropdown-item">${i18n.get('cione-module.templates.myshop.header-component.close-session')}</a>


            </div>
        </div>
        
        [#-- ALERTA PACKS --]
        [#if model.hasPacks() && content.alertsLink?? && content.alertsLink?has_content] 
        	<div id="alertsLink">
	        	<a [#if content.alertsLink?? && content.alertsLink?has_content] href="${cmsfn.link("website", content.alertsLink!)!}" [#else] href="#" [/#if] style="text-decoration: none;"> 
			        <div class="b2b-pack-ico" id="b2b-pack-ico" >
			        	[#-- Numero de items en el carrito --]
			        	<span class="tooltip-text" > Tienes Packs Pendientes de Configurar</span>
			        	<div class="b2b-pack-ico-alert"></div>
			            <div class="b2b-pack-ico-img">
			            	<img src="${resourcesURL}/img/myshop/common/bell.svg" alt="" />
			            </div>
			        </div>
			    </a>
			</div>
	    [/#if]
        
        [#-- CESTA --]
        <div class="b2b-shooping-bag-wrapper" id="b2b-shooping-bag-wrapper">
        	[#-- Numero de items en el carrito --]
        	[#if totalItems?? && totalItems > 0]
            	<div class="b2b-shooping-bag-amount" id="number-items">${totalItems}</div>
            [#else]
            	<div id="number-items"></div>
            [/#if]
            <img class="b2b-shopping-bag-img" src="${resourcesURL}/img/myshop/common/shopping-bag.svg" alt="" />
        </div>
    </div>

    [#-- MENU LATERAL --]
    <div class="layer-opacity-menu">
        <i class="fas fa-times icon-close-modal-menu-mobile hide-in-desktop"></i>
    </div>
    <div class="b2b-menu-wrapper">
        <div class="b2b-menu1">
       	    [#if content.editProfileLink?has_content]
				[#assign editProfileLink = cmsfn.link("website", content.editProfileLink!)!]	
	            <ul class="b2b-menu-mi-cuenta-wrapper">
	                <li class="b2b-menu-mi-cuenta hide-in-desktop"><a href="${editProfileLink}">${i18n.get('cione-module.templates.myshop.header-component.my-account-caps')}</a></li>
	            </ul>
			[/#if]

            <ul class="b2b-menu1-items">
				[#list cmsfn.children(pageNode, "mgnl:page") as page]
			
				[#if !(page.hideInNav?has_content && page.hideInNav == true)]
					[#assign href = '']
					[#if page.externalLink?has_content]
						[#assign href = page.externalLink!]
						[#assign target = "_blank"]
					[#elseif page.subPageLink?has_content]
						[#assign href = cmsfn.link(cmsfn.contentById(page.subPageLink))!""]
					[/#if]
					
					[#assign categoria_menu = '']
					[#assign atributo_menu = '']
					[#if page.category?? && page.category?has_content]
						[#list page.category?split("/") as sValue]
							[#if sValue?is_last]
						  		[#assign categoria_menu = '?category=' +sValue]
						  	[/#if]
						[/#list] 
					[/#if]
					
					[#if page.attribute?? && page.attribute?has_content]
						[#list page.attribute as sValue]
							[#assign atributo_menu = atributo_menu +'&'+sValue]
						[/#list] 
					[/#if]
                <li class="b2b-menu1-item">
				       							       
                    <span class="b2b-menu1-text">
						
						[#if page.iconMenu?has_content]
			                [#assign asset = damfn.getAsset("jcr:" + page.iconMenu)!""]
			                [#if asset?has_content && asset.link?has_content]
			                	<img class="b2b-menu1-icon" src="${asset.link}" alt="" />
			                [/#if]
			            [/#if]
			              
			            [#if href != '']
			                <a href="${href}">${page.title!page.@name!""}</a>
			            [#else]
			            	<a href="${cmsfn.link(page)}${categoria_menu!}${atributo_menu!}">${page.title!page.@name!""}</a>
			            [/#if]
			            
			            [#if page.checkboxcp?? && page.checkboxcp?has_content]
							[#if page.checkboxcp]
							<div id="statusperiodicpurchase" class="b2b-dot-color green"></div>
							[/#if]
						[/#if]
                        
                    </span>
                    [#if cmsfn.children(page, "mgnl:page")?has_content]
                    <i class="fas fa-angle-right b2b-menu-item-more"></i>

                    <ul class="b2b-menu2-items">
						[#list cmsfn.children(page, "mgnl:page") as level1page]
							[#assign href = '']
							[#if !(level1page.hideInNav?has_content && level1page.hideInNav == true)]
								[#if level1page.externalLink?has_content]
									[#assign href = level1page.externalLink!]
									[#assign target = "_blank"]
								[#elseif level1page.subPageLink?has_content]
									[#assign href = cmsfn.link(cmsfn.contentById(level1page.subPageLink))!""]
								[/#if]
								[#assign categoria_menu_1 = '']
								[#if level1page.category?? && level1page.category?has_content]
									[#list level1page.category?split("/") as sValue]
										[#if sValue?is_last]
									  		[#assign categoria_menu_1 = '?category=' +sValue]
									  	[/#if]
									[/#list] 
								[/#if]
								[#assign atributo_menu_1 = '']
								[#if level1page.attribute?? && level1page.attribute?has_content]
									[#list level1page.attribute as sValue]
										[#assign atributo_menu_1 = atributo_menu_1+'&'+sValue]
									[/#list] 
								[/#if]
								[#if level1page?is_first]
									<li class="b2b-menu-back"><i class="fas fa-long-arrow-alt-left"></i> ${i18n.get('cione-module.templates.myshop.header-component.main-menu')}</li>
								[/#if]
		                        <li class="b2b-menu2-item"> 
			                        <span class="b2b-menu2-text">
			                        	[#if href != '']
							                <a href="${href}">${level1page.title!level1page.@name!""}</a>
							            [#else]
			                        		<a href="${cmsfn.link(level1page)}${categoria_menu_1!}${atributo_menu_1!}">${level1page.title!level1page.@name!""}</a>
			                        	[/#if]
			                        </span>
			                        [#if cmsfn.children(level1page, "mgnl:page")?has_content]
			                        	<i class="fas fa-angle-right b2b-menu-item-more"></i>
			                            <ul class="b2b-menu3-items">
			                                <li class="b2b-menu-back"><i class="fas fa-long-arrow-alt-left"></i>${i18n.get('cione-module.templates.myshop.header-component.back')}</li>
											[#list cmsfn.children(level1page, "mgnl:page") as level2page]
											[#assign href = '']
											[#if !(level2page.hideInNav?has_content && level2page.hideInNav == true)]
											
												[#if level2page.externalLink?has_content]
													[#assign href = level2page.externalLink!]
													[#assign target = "_blank"]
												[#elseif level2page.subPageLink?has_content]
													[#assign href = cmsfn.link(cmsfn.contentById(level2page.subPageLink))!""]
												[/#if]
								
												[#assign categoria_menu_2 = '']
												[#assign atributo_menu_2 = '']
												[#if level2page.category?? && level2page.category?has_content]
													
													[#list level2page.category?split("/") as sValue]
														[#if sValue?is_last]
													  		[#assign categoria_menu_2 = '?category=' +sValue]
													  	[/#if]
													[/#list]
													
													[#if level2page.attribute?? && level2page.attribute?has_content]
														[#list level2page.attribute as sValue]
															[#assign atributo_menu_2 = atributo_menu_2+'&'+sValue]
														[/#list] 
													[/#if]
												[/#if]
				                                <li class="b2b-menu3-item"> 
					                                <span class="b2b-menu3-text">
					                                	[#if href != '']
											                <a href="${href}">${level2page.title!level2page.@name!""}</a>
											            [#else]
											            	<a href="${cmsfn.link(level2page)}${categoria_menu_2!}${atributo_menu_2!}">${level2page.title!level2page.@name!""}</a>
											            [/#if] 
					                                </span>
				                                </li>
											[/#if]
			                                [/#list]
			                            </ul>
									[/#if]
		                        </li>
							[/#if]
						[/#list]
                    </ul>
	                [/#if]
                </li>
                [/#if]
                [/#list]
           
            </ul>

        </div>
    </div>

    <div class="layer-opacity-cart" id="control-full-cart">
    </div>
    
    <!-- LISTADO productos del carrito -->
    <div class="b2b-floating-cart" id="full-cart">
    	[#if carrito?has_content && (carrito.getLineItems()?size > 0 || carrito.getCustomLineItems()?size > 0)]
        <div class="b2b-floating-cart-body">
            <img class="b2b-floating-cart-icon-close-all" src="${resourcesURL}/img/myshop/icons/close-thin.svg" alt=""/>
            <div class="b2b-floating-cart-title">${i18n.get('cione-module.templates.myshop.header-component.my-purchase')}</div>

           [#-- Producto del carrito, recoger de commerce tools  --]
           <form id="formCarritoPop" name="formCarritoPop" method="POST">
	          	${ctx.response.setHeader("Cache-Control", "no-cache")}
	       		<input type="hidden" name="csrf" value="${ctx.getAttribute('csrf')!''}" />
	        	<input type="hidden" name="definitionName" value="commercetools" />
	        	<input type="hidden" name="connectionName" value="connectionName" />
	        	<input type="hidden" name="id" value="${carrito.getId()}" />
	        	<input type="hidden" name="userId" value="${carrito.getCustomerId()}" />
	        	<input type="hidden" name="userIdEncodingDisabled" value="true" />
	        	<input type="hidden" name="accessToken" value="" />
	        	
	        	<input type="hidden" id="itemIdHidden" name="itemIdHidden" value="" />
		    	<input type="hidden" id="skuHidden" name="skuHidden" value="" />
		    	<input type="hidden" id="quantityHidden" name="quantityHidden" value="" />
		    	
		    	
		    	
           </form>
           
           [#list carrito.getLineItems() as item]	
            <div class="b2b-floating-cart-item">
                <div class="b2b-floating-cart-close">
                	<img class="b2b-floating-cart-icon-close" src="${resourcesURL}/img/myshop/icons/close-thin.svg" alt=""
                		onclick="removeItem('${item.getId()}','${item.getVariant().getSku()}', ${item.getQuantity()}); return false" />
                </div>
                <div class="b2b-floating-cart-image-wrapper">
                    <img class="b2b-floating-cart-image" [#if item.getVariant().getImages()?has_content]
                    	src="${item.getVariant().getImages()[0].getUrl()}"
                    	[#else]
                    	src="${defaultImage}"
                    	[/#if] onerror="this.onerror=null; this.src='${defaultImage}'" alt="" />
                </div>
                [#assign mapCustomFields = item.getCustom().getFields().values()]
                
                [#if mapCustomFields["refPackPromos"]?? && mapCustomFields["refPackPromos"]?has_content]
	        	<input type="hidden" name="packpromo" iditem="${item.getId()}" key="${item.getVariant().getSku()}" 
	        		id="packpromo-${item.getId()}" value="${mapCustomFields["refPackPromos"]}">
	            [/#if]
                
                [#if hasAttributte(item.getVariant().getAttributes(), "nombreArticulo")]
                	<div class="b2b-floating-cart-name">
                    	${findAttributte(item.getVariant().getAttributes(), "nombreArticulo")}
                	</div>
                [#else]
                	<div class="b2b-floating-cart-name">
	                    ${item.getName().get(locale)}
	                </div>
                
                [/#if]
                
                [#assign tipoProducto = ""]
                [#if hasAttributte(item.getVariant().getAttributes(), "tipoProducto")]
                	[#assign valueTipoProducto = findAttributte(item.getVariant().getAttributes(), "tipoProducto")]
                	[#if valueTipoProducto?? && valueTipoProducto?has_content &&
		         		 valueTipoProducto.get("es")?? && 
		         		 valueTipoProducto.get("es")?has_content]
		         		[#assign tipoProducto = valueTipoProducto.get("es")]
		         		[#--  --assign familiaProducto = MyShopUtils.getFamiliaProducto(tipoProducto)] --]
	                [/#if]
                [/#if]
                
                [#if hasAttributte(item.getVariant().getAttributes(), "colorIcono")]
	                <div class="b2b-floating-cart-option">
	                    <span>${i18n.get('cione-module.templates.myshop.header-component.color')}</span>
	                    <span>
	                        <div class="b2b-floating-cart-color" style="background-color:${findAttributte(item.getVariant().getAttributes(), "colorIcono")}"></div>
	                    </span>
	
	                </div>
                [#else]
                	[#if tipoProducto == "monturas" || tipoProducto == "Gafas graduadas" || tipoProducto == "Gafas de sol" || tipoProducto == "Gafas premontadas"]
	                	<div class="b2b-floating-cart-option">
		                	<span>${i18n.get('cione-module.templates.myshop.header-component.color')}</span>
		                    <span>
		                        <div class="b2b-floating-cart-color" style="background-color:#ffffff; background: linear-gradient(-45deg, white 12px, black 15px, black 15px, white 17px );"></div>
		                    </span>
		                </div>
		            [/#if]
                [/#if]
                [#if mapCustomFields["colorAudifono"]?has_content]
                	<div class="b2b-floating-cart-option">
	                    <span>${i18n.get('cione-module.templates.myshop.header-component.color-audifono')}</span>
	                    <span>${mapCustomFields["colorAudifono"]}</span>
	                </div>
                [/#if]
                [#if mapCustomFields["colorCodo"]?has_content]
                	<div class="b2b-floating-cart-option">
	                    <span>${i18n.get('cione-module.templates.myshop.header-component.color-codo')}</span>
	                    <span>${mapCustomFields["colorCodo"]}</span>
	                </div>
                [/#if]
                [#if mapCustomFields["color_audifono_R"]?has_content]
                	<div class="b2b-floating-cart-option">
	                    <span>${i18n.get('cione-module.templates.myshop.header-component.color-audifono-drch')}</span>
	                    <span>${mapCustomFields["color_audifono_R"]}</span>
	                </div>
                [/#if]
                [#if mapCustomFields["color_audifono_L"]?has_content]
                	<div class="b2b-floating-cart-option">
	                    <span>${i18n.get('cione-module.templates.myshop.header-component.color-audifono-izq')}</span>
	                    <span>${mapCustomFields["color_audifono_L"]}</span>
	                </div>
                [/#if]
                [#if mapCustomFields["color_plato_R"]?has_content]
                	<div class="b2b-floating-cart-option">
	                    <span>${i18n.get('cione-module.templates.myshop.header-component.color-plato-drch')}</span>
	                    <span>${mapCustomFields["color_plato_R"]}</span>
	                </div>
                [/#if]
                [#if mapCustomFields["color_plato_L"]?has_content]
                	<div class="b2b-floating-cart-option">
	                    <span>${i18n.get('cione-module.templates.myshop.header-component.color-plato-izq')}</span>
	                    <span>${mapCustomFields["color_plato_L"]}</span>
	                </div>
                [/#if]
                [#if mapCustomFields["color_codo_R"]?has_content]
                	<div class="b2b-floating-cart-option">
	                    <span>${i18n.get('cione-module.templates.myshop.header-component.color-codo-drch')}</span>
	                    <span>${mapCustomFields["color_codo_R"]}</span>
	                </div>
                [/#if]
                [#if mapCustomFields["color_codo_L"]?has_content]
                	<div class="b2b-floating-cart-option">
	                    <span>${i18n.get('cione-module.templates.myshop.header-component.color-codo-izq')}</span>
	                    <span>${mapCustomFields["color_codo_L"]}</span>
	                </div>
                [/#if]
                <div class="b2b-floating-cart-option">
                    <span>${i18n.get('cione-module.templates.myshop.header-component.quantity')}</span>
                    <span>${item.getQuantity()}</span>
                </div>
                
                [#if tipoProducto == "Soluciones Mantenimiento"]
	                [#if hasAttributte(item.getVariant().getAttributes(), "tamanios")]
	                <div class="b2b-floating-cart-option">
	                    <span>${i18n.get('cione-module.templates.components.detalle-producto-component.tamanio')}</span>
	                    <span>${findAttributte(item.getVariant().getAttributes(), "tamanios").get("es")}</span>
	                </div>
	                [/#if]
                [/#if]
                
                [#if hasAttributte(item.getVariant().getAttributes(), "dimensiones_ancho_ojo")]
                <div class="b2b-floating-cart-option">
                    <span>${i18n.get('cione-module.templates.myshop.header-component.calibre')}</span>
                    <span>${findAttributte(item.getVariant().getAttributes(), "dimensiones_ancho_ojo")}</span>
                </div>
                [/#if]
                
                [#if hasAttributte(item.getVariant().getAttributes(), "graduacion")]
                <div class="b2b-floating-cart-option">
                    <span>${i18n.get('cione-module.templates.myshop.header-component.graduacion')}</span>
                    <span>${findAttributte(item.getVariant().getAttributes(), "graduacion")}</span>
                </div>
                [/#if]
            
	            [#if mapCustomFields["LC_ojo"]?has_content]
	                <div class="b2b-floating-cart-option">
			                <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojo']}</span>
			                [#if mapCustomFields["LC_ojo"] == "D" || mapCustomFields["LC_ojo"] == "R"]
			                	<span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojod']}</span>
			                [#else]
			                	<span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojoi']}</span>
			                [/#if]
	                </div>
	            [/#if]
            
            [#if mapCustomFields["LC_diseno"]?has_content]
            <div class="b2b-floating-cart-option">
                <span>${i18n['cione-module.templates.components.detalle-producto-component.design']}</span>
                <span>${mapCustomFields["LC_diseno"]!""}</span>
            </div>
            [/#if]
            
            [#if mapCustomFields["LC_esfera"]?has_content]
            <div class="b2b-floating-cart-option">
                <span>${i18n['cione-module.templates.components.detalle-producto-component.esfera']}</span>
                <span>${mapCustomFields["LC_esfera"]!""}</span>
            </div>
            [/#if]
            
            [#if mapCustomFields["LC_cilindro"]?has_content]
            <div class="b2b-floating-cart-option">
                <span>${i18n['cione-module.templates.components.detalle-producto-component.cilindro']}</span>
                <span>${mapCustomFields["LC_cilindro"]!""}</span>
            </div>
            [/#if]
            
            [#if mapCustomFields["LC_eje"]?has_content]
            <div class="b2b-floating-cart-option">
                <span>${i18n['cione-module.templates.components.detalle-producto-component.eje']}</span>
                <span>${mapCustomFields["LC_eje"]!""}</span>
            </div>
            [/#if]
            
            [#if mapCustomFields["LC_diametro"]?has_content]
            <div class="b2b-floating-cart-option">
                <span>${i18n['cione-module.templates.components.detalle-producto-component.diametro']}</span>
                <span>${mapCustomFields["LC_diametro"]!""}</span>
            </div>
            [/#if]
            
            [#if mapCustomFields["LC_curvaBase"]?has_content]
            <div class="b2b-floating-cart-option">
                <span>${i18n['cione-module.templates.components.detalle-producto-component.curvabase']}</span>
                <span>${mapCustomFields["LC_curvaBase"]!""}</span>
            </div>
            [/#if]
            
            [#if mapCustomFields["LC_adicion"]?has_content]
            <div class="b2b-floating-cart-option">
                <span>${i18n['cione-module.templates.components.detalle-producto-component.adicion']}</span>
                <span>${mapCustomFields["LC_adicion"]!""}</span>
            </div>
            [/#if]
            
            [#if mapCustomFields["LC_descColor"]?has_content]
            <div class="b2b-floating-cart-option">
                <span>${i18n['cione-module.templates.components.detalle-producto-component.color']}</span>
                <span>${mapCustomFields["LC_descColor"]!""}</span>
            </div>
            [/#if]
            
            [#assign isDeposit = false] 
            [#if mapCustomFields["enDeposito"]?has_content]
        		[#assign isDeposit = mapCustomFields["enDeposito"]!false] 
            [/#if]
                
            	[#if !isDeposit]
            		[#if showPVO(ctx.getUser(), uuid, username)]
	                <div class="b2b-floating-cart-option">
	                    <span>${i18n.get('cione-module.templates.myshop.header-component.total')}</span>
	                    [#if mapCustomFields["pvoConDescuento"]?has_content]
	                    	[#assign pvoConDescuento = mapCustomFields["pvoConDescuento"].getCentAmount() / 100.0]
	                    	[#--  --if mapCustomFields["tipoPrecioPack"]?? && mapCustomFields["tipoPrecioPack"]?has_content && mapCustomFields["tipoPrecioPack"] == "CERRADO"]
	                    		[#-- AÑADIMOS LA INFO DEL PACK CERRADO]
								[#if mapCustomFields["tipoPrecioPack"]?? && mapCustomFields["tipoPrecioPack"]?has_content && mapCustomFields["tipoPrecioPack"] == "CERRADO"]
									[#assign pvoDescuentostring = pvoConDescuento?string["0.00"]]
									[#if pvoConDescuento?? && pvoConDescuento?has_content && (pvoDescuentostring!="0,00")]
										[#assign mapPVOPackCerrado = mapPVOPackCerrado + {mapCustomFields["refPackPromos"], pvoDescuentostring}]
									[/#if]
								[/#if]
								[#if mapPVOPackCerrado?? && mapPVOPackCerrado?has_content]
	                    			<span>(${i18n['cione-module.templates.components.detalle-producto-component.all-pack-for']}:${mapPVOPackCerrado[mapCustomFields["refPackPromos"]]} €)</span>
	                    		[/#if]
	                    	[#else]
		                    	<span>${(item.getQuantity() * pvoConDescuento)?string["0.00"]}€</span>
		                    [/#if] --]
		                    
		                    <span>${(item.getQuantity() * pvoConDescuento)?string["0.00"]}€</span>
	                    [#else]
	                    	[#assign priceDouble = item.getTotalPrice().getCentAmount() / 100.0]
	                    	<span>${priceDouble?string["0.00"]}€</span>
	                    [/#if]
	                </div>
	                [/#if]
                [#else]
                <div class="b2b-floating-cart-option">
                    <span>${i18n['cione-module.templates.components.detalle-producto-component.deposit']}</span>
                	<span>${i18n['cione-module.templates.components.detalle-producto-component.yes']}</span>
                </div>
                [/#if]
                
                
                [#if hasAttributte(item.getVariant().getAttributes(), "tipoProducto")]
                	[#assign valueTipoProducto = findAttributte(item.getVariant().getAttributes(), "tipoProducto")]
                	[#if valueTipoProducto?? && valueTipoProducto?has_content &&
		         		 valueTipoProducto.get("es")?? && 
		         		 valueTipoProducto.get("es")?has_content]
		         		[#assign tipoProducto = valueTipoProducto.get("es")]
		         		[#--  --assign familiaProducto = MyShopUtils.getFamiliaProducto(tipoProducto)] --]
	                [/#if]
                [/#if]
	         	
	         	[#if hasAttributte(item.getVariant().getAttributes(), "tipoProducto")]
	         		[#if tipoProducto=="Lentes de contacto"]
	         			<input type="hidden" id="stock_${item.getId()}" 
         				value="${model.getStockbyAliasEkon(mapCustomFields["LC_sku"]!)!}" />
	         		[#elseif hasAttributte(item.getVariant().getAttributes(), "aliasEkon")]
	         			<input type="hidden" id="stock_${item.getId()}" 
	         			value="${model.getStockbyAliasEkon(findAttributte(item.getVariant().getAttributes(), "aliasEkon")!)!}" />
	         		[/#if]
	         	[#elseif hasAttributte(item.getVariant().getAttributes(), "aliasEkon")]
	         		<input type="hidden" id="stock_${item.getId()}" 
	         		value="${model.getStockbyAliasEkon(findAttributte(item.getVariant().getAttributes(), "aliasEkon")!)!}" />
	         	[/#if]
	         	
	         [#assign infoVariant = model.getVariantInfoPromociones(item.getVariant())!]
	         [#if infoVariant.getTipoPromocion() == "escalado"]
	             [#assign listPromos = infoVariant.getListPromos()!]
	    		 [#list listPromos as promo]
					<input type="hidden" name="listPromos_${item.getId()}" value="${promo.getCantidad_hasta()}|${promo.getPvoDto()}">
	    		 [/#list]
	    		 <input type="hidden" id="tipoPromo_${item.getId()}" value="escalado">
	    	 [#else]
	    	 	 <input type="hidden" id="tipoPromo_${item.getId()}" value="otro">
			 [/#if]
             
             </div>
             
             [/#list]
             
             [#list carrito.getCustomLineItems() as itemc]
             	[#assign mapCustomFieldsC = itemc.getCustom().getFields().values()]
             	<div class="b2b-floating-cart-item">
             	
             		[#if mapCustomFieldsC["SKU"]?? && mapCustomFieldsC["SKU"]?has_content]
					    <div class="b2b-floating-cart-close">
					        <img class="b2b-floating-cart-icon-close" src="${resourcesURL}/img/myshop/icons/close-thin.svg" alt=""
					              onclick="removeCLI('${itemc.getId()}','${mapCustomFieldsC["SKU"]}', ${itemc.getQuantity()}); return false" />
					    </div>
					[#elseif itemc.getSlug()?? && itemc.getSlug()?has_content]
						<div class="b2b-floating-cart-close">
					        <img class="b2b-floating-cart-icon-close" src="${resourcesURL}/img/myshop/icons/close-thin.svg" alt=""
					              onclick="removeCLI('${itemc.getId()}','${itemc.getSlug()}', ${itemc.getQuantity()}); return false" />
					    </div>
					[/#if]
					    <div class="b2b-floating-cart-image-wrapper">
					        <img class="b2b-floating-cart-image" src="${defaultImageCLI}" onerror="this.onerror=null; this.src='${defaultImageCLI}'" alt="" />
					    </div>
					    
					[#if mapCustomFieldsC["refPackPromos"]?? && mapCustomFieldsC["refPackPromos"]?has_content]
		        		<input type="hidden" name="packpromo" iditem="${itemc.getId()}" key="${itemc.getSlug()}" 
		        			id="packpromo-${itemc.getId()}" value="${mapCustomFieldsC["refPackPromos"]}">
		            [/#if]
	                
		            [#if itemc.getName()?has_content]
		                <div class="b2b-floating-cart-name">
		                    ${itemc.getName().get("es")!""}
		                </div>
		            [/#if]
	                [#if mapCustomFieldsC["CYL"]?? && mapCustomFieldsC["CYL"]?has_content]
		                <div class="b2b-floating-cart-option">
		                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.cilindro']}</span>
		                    <span>${mapCustomFieldsC["CYL"]!""}</span>
		                </div>
		            [/#if]
	                [#if mapCustomFieldsC["SPH"]?? && mapCustomFieldsC["SPH"]?has_content]
		                <div class="b2b-floating-cart-option">
		                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.esfera']}</span>
		                    <span>${mapCustomFieldsC["SPH"]!""}</span>
		                </div>
		            [/#if]
	                [#if mapCustomFieldsC["CRIB"]?? && mapCustomFieldsC["CRIB"]?has_content]
		                <div class="b2b-floating-cart-option">
		                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.diametro']}</span>
		                    <span>${mapCustomFieldsC["CRIB"]!""}</span>
		                </div>
	                [/#if]
	            
		          	[#if mapCustomFieldsC["EYE"]?? && mapCustomFieldsC["EYE"]?has_content]
		                <div class="b2b-floating-cart-option">
		                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojo']}</span>
		                    [#if mapCustomFieldsC["EYE"] == "D" || mapCustomFieldsC["EYE"] == "R"] 
		                    	<span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojod']}</span>
		                    [#else]
		                    	<span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojoi']}</span>
		                    [/#if]
		                </div>
		            [/#if]
		            
		                <div class="b2b-floating-cart-option">
		                    <span>${i18n.get('cione-module.templates.myshop.header-component.quantity')}</span>
		                    <span>${itemc.getQuantity()}</span>
		                </div>
	                
	                
	                [#if showPVO(ctx.getUser(), uuid, username)]  
	                	[#assign priceTotalDouble = itemc.getTotalPrice().getCentAmount() / 100.0]
			        	[#if mapCustomFieldsC["pvoConDescuento_R"]?? && mapCustomFieldsC["pvoConDescuento_R"]?has_content]
			        		[#assign priceTotalDouble = mapCustomFieldsC["pvoConDescuento_R"].getCentAmount() / 100.0]
			        		<div class="b2b-floating-cart-option">
				                <span>${i18n['cione-module.templates.components.detalle-producto-component.pvo-dto']}</span>
				                <span>${priceTotalDouble?string["0.00"]} €</span>
				            </div>
				        [#elseif mapCustomFieldsC["pvoConDescuento_L"]?? && mapCustomFieldsC["pvoConDescuento_L"]?has_content]
				        	[#assign priceTotalDouble = mapCustomFieldsC["pvoConDescuento_L"].getCentAmount() / 100.0]
			        		<div class="b2b-floating-cart-option">
				                <span>${i18n['cione-module.templates.components.detalle-producto-component.pvo-dto']}</span>
				                <span>${priceTotalDouble?string["0.00"]} €</span>
				            </div>
				        [#else]
				        	<div class="b2b-floating-cart-option">
			                    <span>${i18n.get('cione-module.templates.myshop.header-component.total')}</span>
			                    <span>${priceTotalDouble?string["0.00"]}€</span>
			                </div>
		            	[/#if]
		            [/#if]
				</div>
				
             [/#list]
             
             
            [#-- ----------------------------------------------- --]
		</div>
		
        <div class="b2b-floating-cart-footer">
            <div class="b2b-floating-cart-footer-price">
            [#if showPVO(ctx.getUser(), uuid, username)]
                <span>${i18n.get('cione-module.templates.myshop.header-component.total')}</span>
                [#if carrito?has_content]
                	[#-- <span>${carrito.getTotalPrice().getNumber()?string["0.00"]}€</span> --]
                	<span>${dataModel.getCarritoTotal()}€</span>
                [#else]
                	<span>0,00€</span>
                [/#if]
            [/#if]
            </div>
            
            <div class="product-button-wrapper">
            	[#assign link = '#']
            	[#if content.carritoLink?has_content]
					[#assign link = cmsfn.link("website", content.carritoLink)!]
				[/#if]
                <button class="product-button" onclick="location.href='${link}'" >
                    ${i18n.get('cione-module.templates.myshop.header-component.go-to-cart')}
                </button>
            </div>

        </div>
        [#else]
		    <div class="b2b-floating-cart-body">
				<div class="b2b-floating-cart-empty">
	                <span>${i18n['cione-module.templates.myshop.header-component.carritoVacio']}</span>
	            </div>
			</div>
			[#-- Producto del carrito, recoger de commerce tools  --]
           <form id="formCarritoPop" name="formCarritoPop" method="POST">
	          	${ctx.response.setHeader("Cache-Control", "no-cache")}
	       		<input type="hidden" name="csrf" value="${ctx.getAttribute('csrf')!''}" />
	        	<input type="hidden" name="definitionName" value="commercetools" />
	        	<input type="hidden" name="connectionName" value="connectionName" />
	        	<input type="hidden" name="userIdEncodingDisabled" value="true" />
	        	<input type="hidden" name="accessToken" value="" />
	        	
	        	<input type="hidden" id="itemIdHidden" name="itemIdHidden" value="" />
		    	<input type="hidden" id="skuHidden" name="skuHidden" value="" />
		    	<input type="hidden" id="quantityHidden" name="quantityHidden" value="" />
		    	
		    	
		    	
           </form>
		[/#if]
		
		
	[#-- BEGIN: MODAL CONFIRMACION ELIMINAR PACK --]
    [#assign closeimg = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/icons/close-thin.svg"]
    <div id="modal-pack-header" class="modal-purchase">
	    <div class="modal-purchase-box">
	        <div class="modal-purchase-header">
	            <p></p>
	            <div class="modal-purchase-close">
	                <img class="modal-purchase-close-img" src="${closeimg!""}" alt="cerrar" onclick='closeModalGenericForm("#modal-pack-header");'>
	            </div>
	        </div>
	        
	        <div class="modal-purchase-info">
	            <div>
	                <p style="font-size: 16px;">${i18n['cione-module.templates.myshop.listado-productos-carrito-component.modal.delete-items-pack-confirmation']}</p>
	            </div>
	        </div>
	        
	        <div class="modal-purchase-footer">
	            <button class="modal-purchase-button modal-purchase-button--transparent" type="button" onclick='closeModalGenericForm("#modal-pack-header")'>
	                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}
	            </button>
	            <button class="modal-purchase-button" type="button" onclick="deleteItemModalPack(); return false">
	                ${i18n['cione-module.templates.myshop.listado-productos-carrito-component.modal.confirmar']?upper_case}
	            </button>
	        </div>
	    </div>
	</div>
    [#-- END: MODAL CONFIRMACION ELIMINAR PACK --]
		
    </div>
    
    <script type="text/javascript">    
    
		$( document ).ready(function() {
		
			if ($( "#statusperiodicpurchase").length ) {
				getAndSetStatusPeriodicPurchase();
			}
			
		});
				

     function getAndSetStatusPeriodicPurchase() {
     	
     	var url = "${ctx.contextPath}/.rest/private/periodicpurchase/status";
		
		$.ajax({
			url : url,
			type : "GET",
			cache : false, 
			async: false, 
			success : function(response) {
				$("#statusperiodicpurchase").removeClass('yellow');
				$("#statusperiodicpurchase").removeClass('red');
				$("#statusperiodicpurchase").removeClass('green');
				if(response.status === ""){
					$("#statusperiodicpurchase").addClass('green');
				}else{
					$("#statusperiodicpurchase").addClass(response.status);
				}
			},
			error : function(response) {
				console.log("Error al recuperar el estado de la compra periodica");
			}
		});
     	
     }
    
    CURRENT_USER = "${ctx.user.name!}";
    PATH_API = "${ctx.contextPath}" +  "/.rest";
    LANG = "${cmsfn.language()}";
    
    function logoutSimulateUser() {		
		//alert("simular socio" + numSocio);
		var url = PATH_API + "/private/impersonate/v1/delete-impersonate" + "?lang=" + LANG + "&usernameImpersonator=" + CURRENT_USER;
		
		$("#loading").show();		
		$.ajax({
			url : url,
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			success : function(response) {	
				//KK = response
				//alert("OK");
				window.location = "${cmsfn.link(cmsfn.page(content))!}?mgnlLogout=true";
			},
			error : function(response) {
				alert("Se ha producido un error");
			},
			complete : function(response) {
				$("#loading").hide();
			}
		});				
		return false;
	}
    
    var map = new Map() //Para las promociones
    var mapCarrito = new Map()
    [#if carrito?has_content && (carrito.getLineItems()?size > 0 || carrito.getCustomLineItems()?size > 0)]
		[#list carrito.getLineItems() as item]
			if (typeof mapCarrito.get('${item.getVariant().getSku()}') !== 'undefined') {
				var total = mapCarrito.get('${item.getVariant().getSku()}') + ${item.getQuantity()};
				mapCarrito.set("${item.getVariant().getSku()}", total);
			} else {
				mapCarrito.set("${item.getVariant().getSku()}", ${item.getQuantity()});
			}
		[/#list]
		
		[#list carrito.getCustomLineItems() as itemc]
			[#if itemc.getCustom().getFields().values()["SKU"]?? && itemc.getCustom().getFields().values()["SKU"]?has_content]
				if (typeof mapCarrito.get('${itemc.getCustom().getFields().values()["SKU"]}') !== 'undefined') {
					var total = mapCarrito.get('${itemc.getCustom().getFields().values()["SKU"]}') + ${itemc.getQuantity()};
					mapCarrito.set("${itemc.getCustom().getFields().values()["SKU"]}", total);
				} else {
					mapCarrito.set("${itemc.getCustom().getFields().values()["SKU"]}", ${itemc.getQuantity()});
				}
			[#else]
				if (typeof mapCarrito.get('${itemc.getSlug()}') !== 'undefined') {
					var total = mapCarrito.get('${itemc.getSlug()}') + ${itemc.getQuantity()};
					mapCarrito.set("${itemc.getSlug()}", total);
				} else {
					mapCarrito.set("${itemc.getSlug()}", ${itemc.getQuantity()});
				}
			[/#if]
		[/#list]
		console.log(mapCarrito);
	[/#if]
	
	function formToJSON(itemId) {
		
		var csrf = $("#formCarritoPop input[name=csrf]").val();
		var definitionName = $("#formCarritoPop input[name=definitionName]").val();
		var connectionName = $("#formCarritoPop input[name=connectionName]").val();
		var id = $("#formCarritoPop input[name=id]").val();
		var lineItemId = itemId;
		var userId = $("#formCarritoPop input[name=userId]").val();
		var accessToken = $("#formCarritoPop input[name=accessToken]").val();
		var userIdEncodingDisabled = $("#formCarritoPop input[name=userIdEncodingDisabled]").val();
		
		console.log(JSON.stringify({
	        "csrf": csrf,
	        "definitionName": definitionName,
	        "connectionName": connectionName,
	        "id": id,
	        "lineItemId": lineItemId,
	        "userId": userId,
	        "accessToken": accessToken,
	        "userIdEncodingDisabled": userIdEncodingDisabled,
	    }));
		
		return JSON.stringify({
	        "csrf": csrf,
	        "definitionName": definitionName,
	        "connectionName": connectionName,
	        "id": id,
	        "lineItemId": lineItemId,
	        "userId": userId,
	        "accessToken": accessToken,
	        "userIdEncodingDisabled": userIdEncodingDisabled,
	    });
	}
	
	function removeItemComplete(itemId, sku, quantity) {
		$("#loading").show();
		
    	$(".b2b-floating-cart-icon-close").removeAttr("onclick");
    	
		var filter = formToJSON(itemId);
		var mapitemid = [itemId];
		
		var refPackPromos = $("#packpromo-" + itemId).val();
    	if (refPackPromos !== undefined) { //borramos todos los que tengan la misma refPack
    		filter = JSON.parse(filter);
	    	filter["refPackPromos"] = refPackPromos;
	    	filter = JSON.stringify(filter);
	    	
	    	//almacenamos todas las lineas de items
	    	var inputpackPromo = document.getElementsByName("packpromo");
	    	if (inputpackPromo != '') {
	    		for(var i = 0; i < inputpackPromo.length; i++){
	    			if (inputpackPromo[i].value == refPackPromos) {
	    				mapitemid.push(inputpackPromo[i].getAttribute('iditem'));
	    			}
	    		}
	    	}
    	} 
    	
    	console.log(mapitemid);
    	
    	$.ajax({
    		url: "${ctx.contextPath}/.rest/private/carrito/v1/cartDeleteItem",
    		type: "POST",
    		data: filter,
            contentType: 'application/json; charset=utf-8',
            cache: false,
            async: false,
            dataType: "json",
            success: function(response) {
				$(this).parent().remove();
				var unidades = mapCarrito.get(sku) - quantity; //no quitamos todas ya que pueden haber mas unidades en otras lineas de carrito
				if (unidades <= 0) {
					mapCarrito.delete(sku);
				} else {
					mapCarrito.set(sku, unidades);
				}
				
				console.log(mapCarrito);
				
				let respuesta = actualizarLineItems(response, sku);
				
				//actualiza popup carrito
				refrescarPopupCarrito(respuesta);
				
				//actualiza pantalla detalle
				if (typeof refrescarDetalle === "function" && $("#formDetalleProducto input[name=sku]").val() == sku) {
				    refrescarDetalle();
				}
				
				//actualiza listado del carrito
				if (typeof refrescarListadoCarrito === "function") { 
					mapitemid.forEach(itemmap => {
				        $("#cart-item_" + itemmap).remove();
				    });
				    
				    refrescarListadoCarrito(respuesta);
				}
            },
            error: function(response) { console.log("Error removing item"); },
            complete: function(response) { $("#loading").hide(); }
    	});
	}
	
	function deleteItemModalPack(){
		var itemId = $('#itemIdHidden').val();
		var sku = $('#skuHidden').val();
		var quantity = $('#quantityHidden').val();
		$('#modal-pack-header').css('display','none');
		$('#modal-pack-header').css('visibility','hidden');
		
		$('#modal-pack').css('visibility','hidden');
		
		$("#loading").show();
		
		console.log("cerrada");
		setTimeout(function () {
			removeItemComplete(itemId, sku, quantity);
		}, 0);
		
		
	}
	
	function closeModalGeneric(that) {
		$(that).css("display","none");
	}
	
	//modal con formulario, no la podemos ocultar para poder inyectar los valores del formulario
	function closeModalGenericForm(that) {
		$(that).css("visibility","hidden");
	}
	
	function removeItem(itemId, sku, quantity) {
		var refPackPromos = $("#packpromo-" + itemId).val();
    	if (refPackPromos !== undefined) {
    		//seteamos los valores ocultos de la modal
    		$('#itemIdHidden').val(itemId);
    		$('#skuHidden').val(sku);
    		$('#quantityHidden').val(quantity);
    		//abrimos modal confirmacion
    		if ($('#modal-pack').length > 0) {
    			//estamos en el carrito
    			$('#modal-pack').css('display','flex');
    			$('#modal-pack').css('visibility','visible');
    		} else {
    			$('#modal-pack-header').css('display','flex');
    			$('#modal-pack-header').css('visibility','visible');
    		}
    	} else {
			removeItemComplete(itemId, sku, quantity);
		}
	}

    function removeCLI(itemId, sku_slug, quantity) {
    	var refPackPromos = $("#packpromo-" + itemId).val();
    	if (refPackPromos !== undefined) {
    	    $('#itemIdHidden').val(itemId);
    		$('#skuHidden').val(sku_slug);
    		$('#quantityHidden').val(quantity);
    		//abrimos modal confirmacion
    		if ($('#modal-pack').length > 0) {
    			//estamos en el carrito
    			$('#modal-pack').css('display','flex');
    			$('#modal-pack').css('visibility','visible');
    		} else {
    			$('#modal-pack-header').css('display','flex');
    			$('#modal-pack-header').css('visibility','visible');
    		}
    	} else {
	    	$("#loading").show();
	    	$(".b2b-floating-cart-icon-close").removeAttr("onclick");
	    	
			var filter = formToJSON(itemId);
	    	$.ajax({
	    		url: "${ctx.contextPath}/.rest/private/carrito/v1/cartDeleteCLI",
	    		type: "POST",
	    		data: filter,
	            contentType: 'application/json; charset=utf-8',
	            cache: false,
	            dataType: "json",
	            success: function(response) {
					$(this).parent().remove();
					var unidades = mapCarrito.get(sku_slug) - quantity; //no quitamos todas ya que pueden haber mas unidades en otras lineas de carrito
					if (unidades <= 0) {
						mapCarrito.delete(sku_slug);
					} else {
						mapCarrito.set(sku_slug, unidades);
					}
					
					console.log(mapCarrito);
					
					let respuesta = actualizarLineItems(response, sku_slug);
					
					//actualiza popup carrito
					refrescarPopupCarrito(respuesta);
					
					//actualiza listado del carrito
					if (typeof refrescarListadoCarrito === "function") { 
					    $("#cart-item_" + itemId).remove();
					    refrescarListadoCarrito(respuesta);
					}
	            },
	            error: function(response) { console.log("Error removing item"); },
	            complete: function(response) { $("#loading").hide(); }
	    	});
	    }
    }
    
    function actualizarLineItemsPacks(response, skus) {
    	var respuesta = response;
    	skus.forEach(element => {
    		var sku = element;
	    	if((typeof mapCarrito.get(sku) !== 'undefined') && mapCarrito.get(sku) > 0) {
				var cantidadIntroducida = 0;
				
				response.lineItems.forEach(item => {			
					if(item.variant.sku == sku) {
						var itemCantidad = parseInt(item.quantity);
						cantidadIntroducida += itemCantidad;
						if ($("#stock_" + item.id).length) {
							var stock = parseInt($("#stock_" + item.id).val().replace(".", ""));
							
							var plazoProveedor = 0;
							if (item.variant.attributes.find(x => x.name == "plazoEntregaProveedor") !== undefined) {
								plazoProveedor = parseInt(item.variant.attributes.find(x => x.name == "plazoEntregaProveedor").value);
							}
							var plazoEntrega;
							if(item.custom.fields.plazoEntrega !== 'undefined')
								plazoEntrega = parseInt(item.custom.fields.plazoEntrega);
							else
								plazoEntrega = plazoProveedor + 2;
							
							if(cantidadIntroducida > stock && plazoEntrega <= 2) {
								plazoEntrega = plazoProveedor + 2;
								
								//Actualizamos el plazo de entrega en el carrito de Commercetools
								filter = formToJSON(item.id);
								$.ajax({
									url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updatePlazo/" + plazoEntrega,
									async: false,
									type: "POST",
									data: filter,
							        contentType: 'application/json; charset=utf-8',
							        cache: false,
							        dataType: "json",
							        success: function(response) {
							        	respuesta = response;
							        	//$("#plazo_" + item.id).text(plazoEntrega + ' ${i18n['cione-module.templates.myshop.listado-productos-carrito-component.dias']}');
							        	$("#plazo_" + item.id).text(' ${i18n['cione-module.templates.components.plazo-proveedor']}');
							        },
							        error: function(response) { console.log("Error cambio días de entrega"); }
								});
								
							} else if(cantidadIntroducida <= stock && plazoEntrega > 2) {
								plazoEntrega = 2;
								
								//Actualizamos el plazo de entrega en el carrito de Commercetools
								filter = formToJSON(item.id);
								$.ajax({
									url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updatePlazo/" + plazoEntrega,
									async: false,
									type: "POST",
									data: filter,
							        contentType: 'application/json; charset=utf-8',
							        cache: false,
							        dataType: "json",
							        success: function(response) {
							        	respuesta = response;
							        	//$("#plazo_" + item.id).text(plazoEntrega + ' ${i18n['cione-module.templates.myshop.listado-productos-carrito-component.dias']}');
							        	$("#plazo_" + item.id).text(' ${i18n['cione-module.templates.components.siguiente-envio']}');
							        },
							        error: function(response) { console.log("Error cambio días de entrega"); }
								});
							}
						}
						
						var tipoPromo = $("#tipoPromo_" + item.id).val();
						if(tipoPromo == "escalado") {
							$("input[name=listPromos_" + item.id + "]").each(function(i) {		
								var key = parseFloat($(this).val().split("|")[0].replace(".", ""));		//key: cantidad_hasta
								var value = parseFloat($(this).val().split("|")[1].replace(",", "."));	//value: pvoDto
								
								if(mapCarrito.get(sku) <= key) {
									var precioActual;
									
									if(item.custom.fields.pvoConDescuento !== 'undefined')
										precioActual = parseFloat(item.custom.fields.pvoConDescuento.centAmount / Math.pow(10, item.custom.fields.pvoConDescuento.fractionDigits));
									else
										precioActual = parseFloat(item.price.value.number);
									
									if(value != precioActual) {
										//Actualizamos el pvoConDescuento en el carrito de Commercetools
										filter = formToJSON(item.id);
										$.ajax({
											url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updatePvoConDescuento/" + value,
											async: false,
											type: "POST",
											data: filter,
									        contentType: 'application/json; charset=utf-8',
									        cache: false,
									        dataType: "json",
									        success: function(response) { respuesta = response; },
									        error: function(response) { console.log("Error update precio escalado"); }
										});
									}
									return false;
								}
							});
						}
					} //end_if
				}); //end_forEach
			}
		});
			
		return respuesta;
    } //end_function
    
    function actualizarLineItems(response, sku) {
    	var respuesta = response;
    	if((typeof mapCarrito.get(sku) !== 'undefined') && mapCarrito.get(sku) > 0) {
			var cantidadIntroducida = 0;
			
			response.lineItems.forEach(item => {			
				if(item.variant.sku == sku) {
					var itemCantidad = parseInt(item.quantity);
					cantidadIntroducida += itemCantidad;
					if ($("#stock_" + item.id).length) {
						var stock = parseInt($("#stock_" + item.id).val().replace(".", ""));
						
						var plazoProveedor = 0;
						if (item.variant.attributes.find(x => x.name == "plazoEntregaProveedor") !== undefined) {
							plazoProveedor = parseInt(item.variant.attributes.find(x => x.name == "plazoEntregaProveedor").value);
						}
						var plazoEntrega;
						if(item.custom.fields.plazoEntrega !== 'undefined')
							plazoEntrega = parseInt(item.custom.fields.plazoEntrega);
						else
							plazoEntrega = plazoProveedor + 2;
						
						if(cantidadIntroducida > stock && plazoEntrega <= 2) {
							plazoEntrega = plazoProveedor + 2;
							
							//Actualizamos el plazo de entrega en el carrito de Commercetools
							filter = formToJSON(item.id);
							$.ajax({
								url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updatePlazo/" + plazoEntrega,
								async: false,
								type: "POST",
								data: filter,
						        contentType: 'application/json; charset=utf-8',
						        cache: false,
						        dataType: "json",
						        success: function(response) {
						        	respuesta = response;
						        	//$("#plazo_" + item.id).text(plazoEntrega + ' ${i18n['cione-module.templates.myshop.listado-productos-carrito-component.dias']}');
						        	$("#plazo_" + item.id).text(' ${i18n['cione-module.templates.components.plazo-proveedor']}');
						        },
						        error: function(response) { console.log("Error cambio días de entrega"); }
							});
							
						} else if(cantidadIntroducida <= stock && plazoEntrega > 2) {
							plazoEntrega = 2;
							
							//Actualizamos el plazo de entrega en el carrito de Commercetools
							filter = formToJSON(item.id);
							$.ajax({
								url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updatePlazo/" + plazoEntrega,
								async: false,
								type: "POST",
								data: filter,
						        contentType: 'application/json; charset=utf-8',
						        cache: false,
						        dataType: "json",
						        success: function(response) {
						        	respuesta = response;
						        	//$("#plazo_" + item.id).text(plazoEntrega + ' ${i18n['cione-module.templates.myshop.listado-productos-carrito-component.dias']}');
						        	$("#plazo_" + item.id).text(' ${i18n['cione-module.templates.components.siguiente-envio']}');
						        },
						        error: function(response) { console.log("Error cambio días de entrega"); }
							});
						}
					}
					
					var tipoPromo = $("#tipoPromo_" + item.id).val();
					if(tipoPromo == "escalado") {
						$("input[name=listPromos_" + item.id + "]").each(function(i) {		
							var key = parseFloat($(this).val().split("|")[0].replace(".", ""));		//key: cantidad_hasta
							var value = parseFloat($(this).val().split("|")[1].replace(",", "."));	//value: pvoDto
							
							if(mapCarrito.get(sku) <= key) {
								var precioActual;
								
								if(item.custom.fields.pvoConDescuento !== 'undefined')
									precioActual = parseFloat(item.custom.fields.pvoConDescuento.centAmount / Math.pow(10, item.custom.fields.pvoConDescuento.fractionDigits));
								else
									precioActual = parseFloat(item.price.value.number);
								
								if(value != precioActual) {
									//Actualizamos el pvoConDescuento en el carrito de Commercetools
									filter = formToJSON(item.id);
									$.ajax({
										url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updatePvoConDescuento/" + value,
										async: false,
										type: "POST",
										data: filter,
								        contentType: 'application/json; charset=utf-8',
								        cache: false,
								        dataType: "json",
								        success: function(response) { respuesta = response; },
								        error: function(response) { console.log("Error update precio escalado"); }
									});
								}
								return false;
							}
						});
					}
				} //end_if
			}); //end_forEach
		}
		return respuesta;
    } //end_function

    function refrescarPopupCarrito(response) {
    
    	var regTotales = response.lineItems.length;
        var resCLITotales = response.customLineItems.length;
        
        var count = 0;
        var precioTotal = getPrecio(response.lineItems,response.customLineItems);
        var listResult = [];
        
        if (regTotales > 0 || resCLITotales > 0){
        
        	listResult.push(templateCabecera(response));
        	
        	if (regTotales > 0){
	        	response.lineItems.forEach(function(lineaCarrito){
	        		count += lineaCarrito.quantity;
	        		listResult.push(templateCarrito(lineaCarrito));
	        	});
        	}
        	
        	if (resCLITotales > 0){
	        	response.customLineItems.forEach(function(lineaCarrito){
	        		count += lineaCarrito.quantity;
	        		listResult.push(templateCarritoCLI(lineaCarrito));
	        	});
        	}
        	
        	listResult.push(templateFin(precioTotal));
        	
		} else {
			listResult.push(templateNoRecords());
		}
		
		updateCesta(count);
		
		listResult.push(templateModalPackHeader());
		
        $("#full-cart").html(listResult.join(" "));
        
        $(".b2b-floating-cart-icon-close-all").on("click", function () {
			var pathname = window.location.pathname;
		    var origin = window.location.origin; 
		    var path = origin + '/.resources/cione-theme/webresources/img/myshop/common/shopping-bag.svg';
		    if (pathname.includes('magnoliaAuthor')){
		        path = origin + '/magnoliaAuthor/.resources/cione-theme/webresources/img/myshop/common/shopping-bag.svg';
		    }
		    
		    $('.b2b-shopping-bag-img').attr('src', path);
		    $('.b2b-floating-cart').removeClass('dblock')
		    $('.layer-opacity-cart').removeClass('dblock');
		    $('body').removeClass('overflow-hidden');
		});
        
    }
    
    function pushTemplateModalPack(){
    	$("#full-cart").html(templateModal());
    }
    
    function pushTemplateModalPackHeader(){
    	$("#full-cart").html(templateModal());
    }
    
    function templateModalPack() {
		var html = "";
		html += "<div id='modal-pack' class='modal-purchase'>";
		html += "<div class='modal-purchase-box'>";
	    html += "<div class='modal-purchase-header'>";
	    html += "<p></p>";
		html += "<div class='modal-purchase-close'>	";
		html += "<img class='modal-purchase-close-img' src='${closeimg!''}' alt='cerrar' onclick=\"closeModalGeneric('#modal-pack');\">";
	    html += "</div>";
		html += "</div>";
		html += "<div class='modal-purchase-info'>";
	    html += "<div>";
		html += "<p style='font-size: 16px;'>${i18n["cione-module.templates.myshop.listado-productos-carrito-component.modal.delete-items-pack-confirmation"]}</p>";
		html += "</div>";
	    html += "</div>";
		html += "<div class='modal-purchase-footer'>";
		html += "<button class='modal-purchase-button modal-purchase-button--transparent' type='button' onclick='closeModalGeneric('#modal-pack');'>";
	    html += "${i18n["cione-module.templates.myshop.listado-productos-home-component.modal.close"]}";
		html += "</button>";
		html += "<button class='modal-purchase-button' type='button' onclick='deleteItemModalPack(); return false' >";
	    html += "${i18n["cione-module.templates.myshop.listado-productos-carrito-component.modal.confirmar"]?upper_case}";
		html += "</button>";
		html += "</div>";
		html += "</div>";
		return html;
    }
    
     function templateModalPackHeader() {
		var html = "";
		html += "<div id='modal-pack-header' class='modal-purchase'>";
		html += "<div class='modal-purchase-box'>";
	    html += "<div class='modal-purchase-header'>";
	    html += "<p></p>";
		html += "<div class='modal-purchase-close'>	";
		html += "<img class='modal-purchase-close-img' src='${closeimg!''}' alt='cerrar' onclick=\"closeModalGeneric('#modal-pack-header');\">";
	    html += "</div>";
		html += "</div>";
		html += "<div class='modal-purchase-info'>";
	    html += "<div>";
		html += "<p style='font-size: 16px;'>${i18n["cione-module.templates.myshop.listado-productos-carrito-component.modal.delete-items-pack-confirmation"]}</p>";
		html += "</div>";
	    html += "</div>";
		html += "<div class='modal-purchase-footer'>";
		html += "<button class='modal-purchase-button modal-purchase-button--transparent' type='button' onclick=\"closeModalGeneric('#modal-pack-header');\">";
	    html += "${i18n["cione-module.templates.myshop.listado-productos-home-component.modal.close"]}";
		html += "</button>";
		html += "<button class='modal-purchase-button' type='button' onclick='deleteItemModalPack(); return false' >";
	    html += "${i18n["cione-module.templates.myshop.listado-productos-carrito-component.modal.confirmar"]?upper_case}";
		html += "</button>";
		html += "</div>";
		html += "</div>";
		return html;
    }
    
    function formatPrice(price) {
    	return new Intl.NumberFormat('es-ES', { minimumFractionDigits: 2 }).format(price);
    }
    
    function getPrecio(linesItems,customLineItems){
    
    	var total = 0;
    	
    	linesItems.forEach(function(lineaCarrito){
    	
    		var enDeposito = false;
    		if (lineaCarrito.custom.fields.enDeposito != 'undefined'){
    			enDeposito = lineaCarrito.custom.fields.enDeposito;
    		}
    		
    		if (!enDeposito){
	    		var pvoDto = lineaCarrito.custom.fields.pvoConDescuento;
	    		if (typeof pvoDto !== 'undefined')
	    			total += parseFloat((pvoDto.centAmount / 100) * parseInt(lineaCarrito.quantity));
	    		else {
	    			var totalPriceCent = (lineaCarrito.totalPrice.centAmount / 100);
	    			total += parseFloat(totalPriceCent);
	    		}
	    			
			}
    	});
    	
    	if(customLineItems != null){
	    	customLineItems.forEach(function(lineaCarrito){
	    	
	    		if (lineaCarrito.custom.fields.pvoConDescuento_L !== undefined) {
	    			var totalPriceCent = (lineaCarrito.custom.fields.pvoConDescuento_L.centAmount / 100);
	    		} else if (lineaCarrito.custom.fields.pvoConDescuento_R !== undefined) {
	    			var totalPriceCent = (lineaCarrito.custom.fields.pvoConDescuento_R.centAmount / 100);
	    		} else {
	    			var totalPriceCent = (lineaCarrito.totalPrice.centAmount / 100);
	    		}
	    		
				total += parseFloat(totalPriceCent) * parseInt(lineaCarrito.quantity);
	    	});
    	}
    	
    	return formatPrice(total);
    }
    
    function templateCabecera(response) {
    	var html = "";
    	if (response.lineItems.length > 0 || response.customLineItems.length > 0){
    		html += "<div class='b2b-floating-cart-body'>";
    		html += "<img class='b2b-floating-cart-icon-close-all' src='${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/icons/close-thin.svg' alt=''/>";
    		html += "<div class='b2b-floating-cart-title'>${i18n.get('cione-module.templates.myshop.header-component.my-purchase')}</div>";
    		
    		var myvar = '<form id="formCarritoPop" name="formCarritoPop" method="POST">';
			myvar += '${ctx.response.setHeader("Cache-Control", "no-cache")}';
			myvar += '<input type="hidden" name="csrf" value="${ctx.getAttribute('csrf')!''}" />';
			myvar += '<input type="hidden" name="definitionName" value="commercetools" />';
			myvar += '<input type="hidden" name="connectionName" value="connectionName" />';
			myvar += '<input type="hidden" name="id" value="' + response.id + '" />';
			myvar += '<input type="hidden" name="userId" value="' + response.customerId + '" />';
			myvar += '<input type="hidden" name="userIdEncodingDisabled" value="true" />';
			myvar += '<input type="hidden" name="accessToken" value="" />';
			
			myvar += '<input type="hidden" id="itemIdHidden" name="itemIdHidden" value="" />';
			myvar += '<input type="hidden" id="skuHidden" name="skuHidden" value="" />';
			myvar += '<input type="hidden" id="quantityHidden" name="quantityHidden" value="" />';
			myvar += '</form>';
    		
    		html += myvar;
    	}
    	
    	return html;
    }
    
    function templateFin(precioTotal) { 
    	var html = '</div>';  //cierre b2b-floating-cart-body
    	html += '<div class="b2b-floating-cart-footer">';
    	
    	html += '<div class="b2b-floating-cart-footer-price">';
    	[#if showPVO(ctx.getUser(), uuid, username)]
			html += '<span>${i18n.get("cione-module.templates.myshop.header-component.total")}</span>';
			html += '<span>' + precioTotal +'€</span>';
		[/#if]
		html += '</div>';

		html += '<div class="product-button-wrapper">';
		html += '<button class="product-button" onclick="location.href=\'${ctx.contextPath}/cione/private/myshop/carrito.html\'">';
		html += '${i18n.get("cione-module.templates.myshop.header-component.go-to-cart")}';
		html += '</button>';
		html += '</div>';
		
		html += '</div>';
		
    	return html;
    }
    
	function templateCarrito(lineaCarrito) {
	
		var atributos = lineaCarrito.variant.attributes;
		var atrcustom = lineaCarrito.custom.fields;
		var html = "";
		
		var atotalPrice = (lineaCarrito.totalPrice.centAmount / 100);
		
		html += '<div class="b2b-floating-cart-item">';
        html += '<div class="b2b-floating-cart-close">';
        html += '<img class="b2b-floating-cart-icon-close" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/icons/close-thin.svg" alt=""';
        html += 'onclick="removeItem(\''+ lineaCarrito.id +'\', \''+ lineaCarrito.variant.sku +'\', '+ lineaCarrito.quantity + '); return false" />';
        html += '</div>';
        html += '<div class="b2b-floating-cart-image-wrapper">';
        var urlImagen = "${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/common/oops.jpg";
        if (lineaCarrito.variant.images.length > 0) {
        	urlImagen = lineaCarrito.variant.images[0].url;
        }
        html += '<img class="b2b-floating-cart-image" src="' + urlImagen + ' " ';
        html += 'onerror="this.onerror=null; this.src=\'${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/common/oops.jpg\'" alt="">';
        html += '</div>';
		
		var nombreArticulo ="";
		var colorIcono = "";
		var dimensiones_ancho_ojo = "";
		var graduacion = "";
		var tipoProductoLinea ="";
		var tamanio ="";
		var ojoid = "";
		var colorAudio = "";
		var refPackPromos = "";
		
		if (typeof lineaCarrito.custom.fields.pvoConDescuento !== 'undefined') {
			var pvoConDescuento = lineaCarrito.custom.fields.pvoConDescuento.centAmount;
		}
		
		atributos.forEach(function(atributo){
			if (atributo.name == "nombreArticulo") {
				nombreArticulo = atributo.value;
			}
			if (atributo.name == "colorIcono") {
				colorIcono = atributo.value;
			}
			if (atributo.name == "dimensiones_ancho_ojo") {
				dimensiones_ancho_ojo = atributo.value;
			}
			if (atributo.name == "graduacion") {
				graduacion = atributo.value;
			}
			if (atributo.name == "tipoProducto") {
				tipoProductoLinea = atributo.value.es;
			}
			if (atributo.name == "tamanios") {
				tamanio = atributo.value.es;
			}
    	});
		
		if (atrcustom.hasOwnProperty("LC_ojo")) {
			if (atrcustom['LC_ojo'] === 'D'){
				ojoid = '${i18n['cione-module.templates.myshop.configurador-lentes-component.ojod']}';
			}else{
				ojoid =  '${i18n['cione-module.templates.myshop.configurador-lentes-component.ojoi']}';
			}
		}
		
		if (atrcustom.hasOwnProperty("colorAudifono")) {
			colorAudio = atrcustom['colorAudifono'];
		}
		
		if (atrcustom.hasOwnProperty("refPackPromos")) {
			refPackPromos = atrcustom['refPackPromos'];
		}
		
		if (nombreArticulo != "") {
			html += '<div class="b2b-floating-cart-name">' + nombreArticulo + '</div>';
		}
		if (refPackPromos != "") {
			html += '<input type="hidden" name="packpromo" iditem="'+ lineaCarrito.id +'" key="'+ lineaCarrito.variant.sku +'"';
		    html += 'id="packpromo-'+ lineaCarrito.id +'" value="'+ refPackPromos + '">';
		}
		if (colorIcono != "") {
			html += '<div class="b2b-floating-cart-option">';
            html += '<span>${i18n.get("cione-module.templates.myshop.header-component.color")}</span>';
            html += '<span><div class="b2b-floating-cart-color" style="background-color:' + colorIcono +'"></div></span></div>';
		} else {
			if ((tipoProductoLinea == "monturas") || (tipoProductoLinea == "Gafas graduadas") || (tipoProductoLinea == "Gafas de sol") || (tipoProductoLinea == "Gafas premontadas")){

				html += '<div class="b2b-floating-cart-option">';
		        html += '<span>${i18n.get("cione-module.templates.myshop.header-component.color")}</span>';
		        html += '<span><div class="b2b-floating-cart-color" style="background-color:#ffffff; background: linear-gradient(-45deg, white 12px, black 15px, black 15px, white 17px );"></div></span></div>';
		        
		    }
		}
		
		if (colorAudio != "") {
			html += '<div class="b2b-floating-cart-option"><span>${i18n.get("cione-module.templates.myshop.header-component.color-audifono")}</span>';
            html += '<span>' + colorAudio + '</span> </div>';
		}
		
		if (atrcustom.hasOwnProperty("color_audifono_R")) {
			html += '<div class="b2b-floating-cart-option"><span>${i18n.get("cione-module.templates.myshop.header-component.color-audifono-drch")}</span>';
            html += '<span>' + atrcustom['color_audifono_R'] + '</span> </div>';
		}
		
		if (atrcustom.hasOwnProperty("color_audifono_L")) {
			html += '<div class="b2b-floating-cart-option"><span>${i18n.get("cione-module.templates.myshop.header-component.color-audifono-izq")}</span>';
            html += '<span>' + atrcustom['color_audifono_L'] + '</span> </div>';
		}
		
		if (atrcustom.hasOwnProperty("color_plato_R")) {
			html += '<div class="b2b-floating-cart-option"><span>${i18n.get("cione-module.templates.myshop.header-component.color-plato-drch")}</span>';
            html += '<span>' + atrcustom['color_plato_R'] + '</span> </div>';
		}
		
		if (atrcustom.hasOwnProperty("color_plato_R")) {
			html += '<div class="b2b-floating-cart-option"><span>${i18n.get("cione-module.templates.myshop.header-component.color-plato-izq")}</span>';
            html += '<span>' + atrcustom['color_plato_R'] + '</span> </div>';
		}
		
		if (atrcustom.hasOwnProperty("color_codo_R")) {
			html += '<div class="b2b-floating-cart-option"><span>${i18n.get("cione-module.templates.myshop.header-component.color-codo-drch")}</span>';
            html += '<span>' + atrcustom['color_codo_R'] + '</span> </div>';
		}
		
		if (atrcustom.hasOwnProperty("color_codo_L")) {
			html += '<div class="b2b-floating-cart-option"><span>${i18n.get("cione-module.templates.myshop.header-component.color-codo-izq")}</span>';
            html += '<span>' + atrcustom['color_codo_L'] + '</span> </div>';
		}
		
		if (atrcustom.hasOwnProperty("colorCodo")) {
			html += '<div class="b2b-floating-cart-option"><span>${i18n.get("cione-module.templates.myshop.header-component.color-codo")}</span>';
            html += '<span>' + atrcustom['colorCodo'] + '</span> </div>';
		}
		
		html += '<div class="b2b-floating-cart-option"><span>${i18n.get("cione-module.templates.myshop.header-component.quantity")}</span>';
        html += '<span>' + lineaCarrito.quantity + '</span> </div>';
        
        if ((tipoProductoLinea == "Soluciones Mantenimiento")){
        	if (tamanio != "") {
				html += '<div class="b2b-floating-cart-option"><span>${i18n.get("cione-module.templates.components.detalle-producto-component.tamanio")}</span>';
	            html += '<span>' + tamanio + '</span> </div>';
			}
		}
            
		if (dimensiones_ancho_ojo != "") {
			html += '<div class="b2b-floating-cart-option"><span>${i18n.get("cione-module.templates.myshop.header-component.calibre")}</span>';
            html += '<span>' + dimensiones_ancho_ojo + '</span> </div>';
		}
		if (graduacion != "") {
			html += '<div class="b2b-floating-cart-option"> <span>${i18n.get("cione-module.templates.myshop.header-component.graduacion")}</span>';
			html += '<span>' + graduacion + '</span> </div>';
		}
		if (ojoid != "") {
			html += '<div class="b2b-floating-cart-option"> <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojo']}</span>';
			html += '<span>' + ojoid + '</span> </div>';
		}
		
		if (atrcustom.hasOwnProperty("LC_diseno")) {
			html += '<div class="b2b-floating-cart-option"> <span>${i18n['cione-module.templates.components.detalle-producto-component.design']}</span>';
			html += '<span>' + atrcustom['LC_diseno'] + '</span> </div>';
		}
		
		if (atrcustom.hasOwnProperty("LC_esfera")) {
			html += '<div class="b2b-floating-cart-option"> <span>${i18n['cione-module.templates.components.detalle-producto-component.esfera']}</span>';
			html += '<span>' + atrcustom['LC_esfera'] + '</span> </div>';
		}
		
		if (atrcustom.hasOwnProperty("LC_cilindro")) {
			html += '<div class="b2b-floating-cart-option"> <span>${i18n['cione-module.templates.components.detalle-producto-component.cilindro']}</span>';
			html += '<span>' + atrcustom['LC_cilindro'] + '</span> </div>';
		}
		
		if (atrcustom.hasOwnProperty("LC_eje")) {
			html += '<div class="b2b-floating-cart-option"> <span>${i18n['cione-module.templates.components.detalle-producto-component.eje']}</span>';
			html += '<span>' + atrcustom['LC_eje'] + '</span> </div>';
		}
		
		if (atrcustom.hasOwnProperty("LC_diametro")) {
			html += '<div class="b2b-floating-cart-option"> <span>${i18n['cione-module.templates.components.detalle-producto-component.diametro']}</span>';
			html += '<span>' + atrcustom['LC_diametro'] + '</span> </div>';
		}
		
		if (atrcustom.hasOwnProperty("LC_curvaBase")) {
			html += '<div class="b2b-floating-cart-option"> <span>${i18n['cione-module.templates.components.detalle-producto-component.curvabase']}</span>';
			html += '<span>' + atrcustom['LC_curvaBase'] + '</span> </div>';
		}
		
		if (atrcustom.hasOwnProperty("LC_adicion")) {
			html += '<div class="b2b-floating-cart-option"> <span>${i18n['cione-module.templates.components.detalle-producto-component.adicion']}</span>';
			html += '<span>' + atrcustom['LC_adicion'] + '</span> </div>';
		}
		
		if (atrcustom.hasOwnProperty("LC_descColor")) {
			html += '<div class="b2b-floating-cart-option"> <span>${i18n['cione-module.templates.components.detalle-producto-component.color']}</span>';
			html += '<span>' + atrcustom['LC_descColor'] + '</span> </div>';
		}
		
		var isDeposit = false;
		if (atrcustom.hasOwnProperty("enDeposito")) {
			isDeposit = atrcustom["enDeposito"];
		}
		
		[#if showPVO(ctx.getUser(), uuid, username)]
			if(!isDeposit){
				if (typeof pvoConDescuento !== 'undefined') {
		  			var pvoLinea = (pvoConDescuento / 100) * lineaCarrito.quantity;
					html += '<div class="b2b-floating-cart-option"><span>${i18n.get("cione-module.templates.myshop.header-component.total")}</span>';
					html += '<span>' + formatPrice(pvoLinea) + '€</span> </div>';
				} else {
					html += '<div class="b2b-floating-cart-option"><span>${i18n.get("cione-module.templates.myshop.header-component.total")}</span>';
					html += '<span>' + formatPrice(lineaCarrito.totalPrice.centAmount / 100) + '€</span> </div>';
				}
			}else{
				html += '<div class="b2b-floating-cart-option"><span>${i18n['cione-module.templates.components.detalle-producto-component.deposit']}</span>';
				html += '<span>${i18n['cione-module.templates.components.detalle-producto-component.yes']}</span> </div>';
			}
		[/#if]
		
		[#-- Recuperar stock --]
		if($("#stock_" + lineaCarrito.id).length)
			var stock = $("#stock_" + lineaCarrito.id).val().replace(".", "");
		else if($("input[name=stock]").length)
			var stock = $("input[name=stock]").val().replace(".", "");
		else if($("#stock_" + lineaCarrito.variant.sku.replace(".", "")).length) 
			var stock = $("#stock_" + lineaCarrito.variant.sku.replace(".", "")).val().replace(".", "");
		else
			var stock = 0;
		html += '<input type="hidden" id="stock_' + lineaCarrito.id + '" value="' + stock + '" />';
		
		[#-- Recuperar tipoPromo --]
		if($("#tipoPromo_" + lineaCarrito.id).length) {
			var tipoPromo = $("#tipoPromo_" + lineaCarrito.id).val();
		} else if($("input[name=tipoPromocion]").length){
			var tipoPromo = $("input[name=tipoPromocion]").val();
		} else
			var tipoPromo = $("#tipoPromo_" + lineaCarrito.variant.sku.replace(".", "")).val();
		
		if(tipoPromo == "escalado") {
			if($("input[name=listPromos_" + lineaCarrito.id + "]").length) {

				$("input[name=listPromos_" + lineaCarrito.id + "]").each(function(i) {
					let key = parseFloat($(this).val().split("|")[0].replace(".", ""));		//key: cantidad_hasta
					let value = parseFloat($(this).val().split("|")[1].replace(",", "."));	//value: pvoDto
					html += '<input type="hidden" name="listPromos_' + lineaCarrito.id + '" value="' + key + '|' + value + '">';
				});
				
			} else if($("input[name=listPromos_" + lineaCarrito.variant.sku.replace(".", "") + "]").length) {

				$("input[name=listPromos_" + lineaCarrito.variant.sku.replace(".", "") + "]").each(function(i) {
					let key = parseFloat($(this).val().split("|")[0].replace(".", ""));		//key: cantidad_hasta
					let value = parseFloat($(this).val().split("|")[1].replace(",", "."));	//value: pvoDto
					html += '<input type="hidden" name="listPromos_' + lineaCarrito.id + '" value="' + key + '|' + value + '">';
				});
				
			} else {
				map.forEach(function(value, key) {
					html += '<input type="hidden" name="listPromos_' + lineaCarrito.id + '" value="' + key + '|' + value + '">';
				});
			}
			
			html += '<input type="hidden" id="tipoPromo_' + lineaCarrito.id + '" value="escalado">';
			
		} else
			html += '<input type="hidden" id="tipoPromo_' + lineaCarrito.id + '" value="otro">';
		
		html += '</div>'; //cierra b2b-floating-cart-item
		return html;
	}
    
	function templateCarritoCLI(lineaCarrito) {
	
		[#-- no existen las variantes
		var atributos = lineaCarrito.variant.attributes;
		--]
		
		var html = "";
		var atotalPrice = (lineaCarrito.totalPrice.centAmount / 100);
		
		html += '<div class="b2b-floating-cart-item">';
        html += '<div class="b2b-floating-cart-close">';
        html += '<img class="b2b-floating-cart-icon-close" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/icons/close-thin.svg" alt=""';
        if ((lineaCarrito.custom.fields.SKU !== undefined) && (lineaCarrito.custom.fields.SKU != "")) {
        	html += 'onclick="removeCLI(\''+ lineaCarrito.id +'\', \''+ lineaCarrito.custom.fields.SKU +'\', '+ lineaCarrito.quantity + '); return false" />';
        } else {
        	if (lineaCarrito.slug != "") {
        	html += 'onclick="removeCLI(\''+ lineaCarrito.id +'\', \''+ lineaCarrito.slug +'\', '+ lineaCarrito.quantity + '); return false" />';
        }
        }
        html += '</div>';
        html += '<div class="b2b-floating-cart-image-wrapper">';
        var urlImagen = "${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/common/imagennodisponible_lente_graduada.jpg";
        html += '<img class="b2b-floating-cart-image" src="' + urlImagen + ' " ';
        html += 'onerror="this.onerror=null; this.src=\'${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/common/imagennodisponible_lente_graduada.jpg\'" alt="">';
        html += '</div>';
    	
		if ((lineaCarrito.name.es !== undefined) && (lineaCarrito.name.es != "")) {
			html += '<div class="b2b-floating-cart-name">' + lineaCarrito.name.es + '</div>';
		}
    	
		if ((lineaCarrito.custom.fields.CYL !== undefined) && (lineaCarrito.custom.fields.CYL != "")) {
			html += '<div class="b2b-floating-cart-option"><span>${i18n['cione-module.templates.myshop.configurador-lentes-component.cilindro']}</span>';
        	html += '<span>' + lineaCarrito.custom.fields.CYL + '</span> </div>';
		}
    	
    	if ((lineaCarrito.custom.fields.SPH !== undefined) && (lineaCarrito.custom.fields.SPH != "")) {
			html += '<div class="b2b-floating-cart-option"><span>${i18n['cione-module.templates.myshop.configurador-lentes-component.esfera']}</span>';
        	html += '<span>' + lineaCarrito.custom.fields.SPH + '</span> </div>';
		}
    	
    	if ((lineaCarrito.custom.fields.CRIB !== undefined) && (lineaCarrito.custom.fields.CRIB != "")) {
			html += '<div class="b2b-floating-cart-option"><span>${i18n['cione-module.templates.myshop.configurador-lentes-component.diametro']}</span>';
        	html += '<span>' + lineaCarrito.custom.fields.CRIB + '</span> </div>';
		}
    	
    	if ((lineaCarrito.custom.fields.EYE !== undefined) && (lineaCarrito.custom.fields.EYE != "")) {
			html += '<div class="b2b-floating-cart-option"><span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojo']}</span>';
			if (lineaCarrito.custom.fields.EYE === 'D') {
				html += '<span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojod']}</span> </div>';
			}else{
				html += '<span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojoi']}</span> </div>';
			}
		}
		
		html += '<div class="b2b-floating-cart-option"><span>${i18n.get("cione-module.templates.myshop.header-component.quantity")}</span>';
        html += '<span>' + lineaCarrito.quantity + '</span> </div>';
		
		[#if showPVO(ctx.getUser(), uuid, username)]
			html += '<div class="b2b-floating-cart-option"><span>${i18n.get("cione-module.templates.myshop.header-component.total")}</span>';
			
			if ((lineaCarrito.custom.fields.pvoConDescuento_L !== undefined) && (lineaCarrito.custom.fields.pvoConDescuento_L != "")) {
				html += '<span>' + formatPrice(lineaCarrito.custom.fields.pvoConDescuento_L.centAmount / 100) + '€</span> </div>';
			} else if ((lineaCarrito.custom.fields.pvoConDescuento_R !== undefined) && (lineaCarrito.custom.fields.pvoConDescuento_R != "")) {
				html += '<span>' + formatPrice(lineaCarrito.custom.fields.pvoConDescuento_R.centAmount / 100) + '€</span> </div>';
			} else {
				html += '<span>' + formatPrice(lineaCarrito.totalPrice.centAmount / 100) + '€</span> </div>';
			}
			
			
		[/#if]
		
		html += '</div>'; //cierra b2b-floating-cart-item
		return html;
	}
	
	function templateNoRecords() {
		
		var html = "";
		
			html += "<div class='b2b-floating-cart-body'>";
			html += "<div class='b2b-floating-cart-empty'>";
		    html += "<span>${i18n['cione-module.templates.myshop.header-component.carritoVacio']}</span>";
		    html += "</div>";
			html += "</div>";
		
		return html;
	}
	
	function updateCesta(numItems) {
		if(numItems > 0) {
			$("#number-items").addClass("b2b-shooping-bag-amount");
			$("#number-items").text(numItems);
		} else {
			$("#number-items").removeClass("b2b-shooping-bag-amount");
			$("#number-items").text("");
		}
	}
	
</script>
	
	[#-- BEGIN: modal prueba virtual --]
	<div class="detalle-virtual-modal">
        <div class="detalle-virtual-modal-wrapper">
            <div class="detalle-virtual-modal-close-wrapper">
                <img class="detalle-virtual-modal-close-img" 
                	 src="${resourcesURL + "/img/myshop/icons/close-thin-white.svg"!}"
                     alt="Cerrar" onclick="stopVto();"/>
            </div>
           <div id="fitmixContainer"></div>
        </div>
    </div>
    [#-- END: modal prueba virtual --]
    
<script>

	[#-- function hide() {
		$(".detalle-virtual-modal").css("display", "none");
		document.getElementById("fitmixContainer").style.display = "none";
	}
	  
	function show() {
		$(".detalle-virtual-modal").css("display", "flex");
		document.getElementById("fitmixContainer").style.display = "block";
	}
	  
	function openVto() {
	    fitmixInstance.startVto("live");
	    show();
	}
	  
	function stopVto() {
	    fitmixInstance.stopVto();
	}
	
    var params = {
        apiKey: '9BjDfZvHu0VOJ6FHASzVSRmObGfFDquQ2NMjiED8',
		onStopVto: hide,
		onIssue: (data) => {
			console.log(data);
		},
	};
	
	window.onload = function () {
		console.log("createWidget header");
	    window.fitmixInstance = FitMix.createWidget('fitmixContainer', params, function() {
	        console.log('VTO module is ready header.');
		});
	};  --]
	
</script>

</header>