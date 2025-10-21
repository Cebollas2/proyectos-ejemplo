[#include "../../includes/macros/cione-utils-impersonate.ftl"]

[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]
[#assign defaultImage = resourcesURL + "/img/myshop/common/oops.jpg"]
[#assign defaultImageCLI = resourcesURL + "/img/myshop/common/imagennodisponible_lente_graduada.jpg"]

[#assign carrito = model.currentUserCart()!]
[#assign uuid = model.getUuid()!]
[#assign username = model.getUserName()!]
[#assign locale = model.getLocale()!]

<style>
	.loading {
	  font-size: 12px;
	}
	
	.loading:after {
	  overflow: hidden;
	  display: inline-block;
	  vertical-align: bottom;
	  -webkit-animation: ellipsis steps(4,end) 900ms infinite;      
	  animation: ellipsis steps(4,end) 900ms infinite;
	  content: "\2026"; /* ascii code for the ellipsis character */
	  width: 0px;
	}
	
	@keyframes ellipsis {
	  to {
	    width: 1.25em;    
	  }
	}
	
	@-webkit-keyframes ellipsis {
	  to {
	    width: 1.25em;    
	  }
	}
</style>

<main class="b2b-main" role="main">
<form id="formCarritoList" name="formCarritoList" method="POST">
	<h2 class="b2b-h2">${i18n['cione-module.templates.myshop.listado-productos-carrito-component.CARRITO']}</h2>
<div class="b2b-cart-container">

 <div class="b2b-cart-items-wrapper">
 
 [#if carrito?has_content && (carrito.getLineItems()?size > 0 || carrito.getCustomLineItems()?size > 0)]
 
 ${ctx.response.setHeader("Cache-Control", "no-cache")}
 <input type="hidden" name="csrf" value="${ctx.getAttribute('csrf')!''}" />
 <input type="hidden" name="definitionName" value="commercetools" />
 <input type="hidden" name="connectionName" value="connectionName" />
 <input type="hidden" name="id" value="${carrito.getId()}" />
 <input type="hidden" name="userId" value="${carrito.getCustomerId()}" />
 <input type="hidden" name="userIdEncodingDisabled" value="true" />
 <input type="hidden" name="accessToken" value="" />
 
 [#assign mapPVOPackCerrado = {}]
 
 [#list carrito.getLineItems() as item]
 [#assign mapCustomFields = item.getCustom().getFields().values()]
 [#assign infoVariant = model.getVariantInfoPromociones(item.getVariant())!]
 [#assign familiaProducto = model.getFamilia(item)!]
 
 [#assign tipoPrecioPack = ""]
 [#assign isPackGenerico = false]
 [#if mapCustomFields["tipoPrecioPack"]?? && mapCustomFields["tipoPrecioPack"]?has_content]
 	[#assign tipoPrecioPack = mapCustomFields["tipoPrecioPack"]]
 	[#assign isPackGenerico = true]
 [/#if]
 
  <div class="b2b-cart-item" id="cart-item_${item.getId()}">
  	
    <div class="b2b-cart-item-delete" onclick="removeItem('${item.getId()}', '${item.getVariant().getSku()}', '${item.getQuantity()}'); return false">
        <img class="b2b-cart-item-icon-delete" src="${resourcesURL}/img/myshop/icons/close-thin.svg" alt="">
        <span class="b2b-cart-item-delete-text">${i18n['cione-module.templates.myshop.listado-productos-carrito-component.eliminar']}</span>
    </div>
    
    <div class="b2b-cart-item-row">
        <div class="b2b-cart-item-col b2b-cart-item-img">
            <img [#if item.getVariant().getImages()?has_content]
            		src="${item.getVariant().getImages()[0].getUrl()}"
            	[#else]
            		src="${defaultImage}"
            	[/#if] onerror="this.onerror=null; this.src='${defaultImage}'" alt="">
        </div>
        <div class="b2b-cart-item-col">
			
			[#if hasAttributte(item.getVariant().getAttributes(), "nombreArticulo")]
            <div class="product-block-title">
                <span class="just-title">${findAttributte(item.getVariant().getAttributes(), "nombreArticulo")!""}</span>
            </div>
            [#else]
            	<div class="product-block-title">
                	<span class="just-title">${item.getName().get(locale)!""}</span>
            	</div>
            [/#if]
            
            [#if mapCustomFields["descPackPromos"]?? && mapCustomFields["descPackPromos"]?has_content]
            	<div class="product-block-title">
                    <span>${i18n.get('cione-module.templates.myshop.header-component.packname')}</span>
                    <span style="font-weight: bold;">${mapCustomFields["descPackPromos"]}</span>
                </div>
            [/#if]
            
            [#if hasAttributte(item.getVariant().getAttributes(), "colorIcono")]
	            <div class="product-block-title">
	                <span>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.color_variante']}</span>
	                <span><div class="product-item-color" style="background-color:${findAttributte(item.getVariant().getAttributes(), "colorIcono")!""}"></div></span>
	            </div>
            [#else]
            	[#if familiaProducto == "monturas"]
	            	<div class="product-block-title">
		                <span>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.color_variante']}</span>
		                <span><div class="product-item-color" style="background-color:#ffffff; background: linear-gradient(-45deg, white 12px, black 15px, black 15px, white 17px );"></div></span>
		            </div>
	            [/#if]
            [/#if]
            [#if mapCustomFields["colorAudifono"]?? && mapCustomFields["colorAudifono"]?has_content]
            	<div class="product-block-title">
                    <span>${i18n.get('cione-module.templates.myshop.header-component.color-audifono')}</span>
                    <span>${mapCustomFields["colorAudifono"]}</span>
                </div>
            [/#if]
            [#if mapCustomFields["colorCodo"]?? && mapCustomFields["colorCodo"]?has_content]
            	<div class="product-block-title">
                    <span>${i18n.get('cione-module.templates.myshop.header-component.color-codo')}</span>
                    <span>${mapCustomFields["colorCodo"]}</span>
                </div>
            [/#if]
            
            [#if mapCustomFields["color_audifono_R"]?? && mapCustomFields["color_audifono_R"]?has_content]
            	<div class="product-block-title">
                    <span>${i18n.get('cione-module.templates.myshop.header-component.color-audifono-drch')}</span>
                    <span>${mapCustomFields["color_audifono_R"]}</span>
                </div>
            [/#if]
            [#if mapCustomFields["color_audifono_L"]?? && mapCustomFields["color_audifono_L"]?has_content]
            	<div class="product-block-title">
                    <span>${i18n.get('cione-module.templates.myshop.header-component.color-audifono-izq')}</span>
                    <span>${mapCustomFields["color_audifono_L"]}</span>
                </div>
            [/#if]
            [#if mapCustomFields["color_plato_R"]?? && mapCustomFields["color_plato_R"]?has_content]
            	<div class="product-block-title">
                    <span>${i18n.get('cione-module.templates.myshop.header-component.color-plato-drch')}</span>
                    <span>${mapCustomFields["color_plato_R"]}</span>
                </div>
            [/#if]
            [#if mapCustomFields["color_plato_L"]?? && mapCustomFields["color_plato_L"]?has_content]
            	<div class="product-block-title">
                    <span>${i18n.get('cione-module.templates.myshop.header-component.color-plato-izq')}</span>
                    <span>${mapCustomFields["color_plato_L"]}</span>
                </div>
            [/#if]
            [#if mapCustomFields["color_codo_R"]?? && mapCustomFields["color_codo_R"]?has_content]
            	<div class="product-block-title">
                    <span>${i18n.get('cione-module.templates.myshop.header-component.color-codo-drch')}</span>
                    <span>${mapCustomFields["color_codo_R"]}</span>
                </div>
            [/#if]
            [#if mapCustomFields["url_pdf"]?? && mapCustomFields["url_pdf"]?has_content]
            	[#assign refTallerPDF = ""]
	            [#if mapCustomFields["refTaller"]?? && mapCustomFields["refTaller"]?has_content]
	            	[#assign refTallerPDF = mapCustomFields["refTaller"]]
	            [/#if]
            	<div class="product-block-title">
                    <span>${i18n.get('cione-module.templates.myshop.header-component.enlacePDF')}</span>
                    <img style="cursor: pointer; padding-left: 15px;" src="${resourcesURL}/img/myshop/icons/file-earmark-pdf.svg" 
                    	alt="Generar PDF" onclick="loadFile('${mapCustomFields["url_pdf"]}', '${refTallerPDF}')" >
                </div>
            [/#if]
            [#if mapCustomFields["color_codo_L"]?? && mapCustomFields["color_codo_L"]?has_content]
            	<div class="product-block-title">
                    <span>${i18n.get('cione-module.templates.myshop.header-component.color-codo-izq')}</span>
                    <span>${mapCustomFields["color_codo_L"]}</span>
                </div>
            [/#if]
            [#if mapCustomFields["colorCodo"]?? && mapCustomFields["colorCodo"]?has_content]
            	<div class="product-block-title">
                    <span>${i18n.get('cione-module.templates.myshop.header-component.color-codo')}</span>
                    <span>${mapCustomFields["colorCodo"]}</span>
                </div>
            [/#if]
            [#if familiaProducto == "liquidos"]
            	[#if hasAttributte(item.getVariant().getAttributes(), "tamanios")]
	                <div class="product-block-title">
	                    <span>${i18n.get('cione-module.templates.components.detalle-producto-component.tamanio')}</span>
	                    <span>${findAttributte(item.getVariant().getAttributes(), "tamanios").get("es")}</span>
	                </div>
	            [/#if]
            [/#if]
            [#if hasAttributte(item.getVariant().getAttributes(), "dimensiones_ancho_ojo")]
            <div class="product-block-title">
                <span>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.calibre']}</span>
                <span>${findAttributte(item.getVariant().getAttributes(), "dimensiones_ancho_ojo")!""}</span>
            </div>
            [/#if]
            
            [#if hasAttributte(item.getVariant().getAttributes(), "graduacion")]
            <div class="product-block-title">
                <span>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.graduacion']}</span>
                <span>${findAttributte(item.getVariant().getAttributes(), "graduacion")!""}</span>
            </div>
            [/#if]
            
            [#if mapCustomFields["LC_ojo"]?? && mapCustomFields["LC_ojo"]?has_content]
	            <div class="product-block-title">
	                <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojo']}</span>
	                [#if mapCustomFields["LC_ojo"] == "D" || mapCustomFields["LC_ojo"] == "R"]
	                	<span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojod']}</span>
	                [#else]
	                	<span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojoi']}</span>
	                [/#if]
	            </div>
            [/#if]
            
            [#if mapCustomFields["LC_diseno"]?? && mapCustomFields["LC_diseno"]?has_content]
            <div class="product-block-title">
                <span>${i18n['cione-module.templates.components.detalle-producto-component.design']}</span>
                <span>${mapCustomFields["LC_diseno"]!""}</span>
            </div>
            [/#if]
            
            [#if mapCustomFields["LC_esfera"]?? && mapCustomFields["LC_esfera"]?has_content]
            <div class="product-block-title">
                <span>${i18n['cione-module.templates.components.detalle-producto-component.esfera']}</span>
                <span>${mapCustomFields["LC_esfera"]!""}</span>
            </div>
            [/#if]
            
            [#if mapCustomFields["LC_cilindro"]?? && mapCustomFields["LC_cilindro"]?has_content]
            <div class="product-block-title">
                <span>${i18n['cione-module.templates.components.detalle-producto-component.cilindro']}</span>
                <span>${mapCustomFields["LC_cilindro"]!""}</span>
            </div>
            [/#if]
            
            [#if mapCustomFields["LC_eje"]?? && mapCustomFields["LC_eje"]?has_content]
            <div class="product-block-title">
                <span>${i18n['cione-module.templates.components.detalle-producto-component.eje']}</span>
                <span>${mapCustomFields["LC_eje"]!""}</span>
            </div>
            [/#if]
            
            [#if mapCustomFields["LC_diametro"]?? && mapCustomFields["LC_diametro"]?has_content]
            <div class="product-block-title">
                <span>${i18n['cione-module.templates.components.detalle-producto-component.diametro']}</span>
                <span>${mapCustomFields["LC_diametro"]!""}</span>
            </div>
            [/#if]
            
            [#if mapCustomFields["LC_curvaBase"]?? && mapCustomFields["LC_curvaBase"]?has_content]
            <div class="product-block-title">
                <span>${i18n['cione-module.templates.components.detalle-producto-component.curvabase']}</span>
                <span>${mapCustomFields["LC_curvaBase"]!""}</span>
            </div>
            [/#if]
            
            [#if mapCustomFields["LC_adicion"]?? && mapCustomFields["LC_adicion"]?has_content]
            <div class="product-block-title">
                <span>${i18n['cione-module.templates.components.detalle-producto-component.adicion']}</span>
                <span>${mapCustomFields["LC_adicion"]!""}</span>
            </div>
            [/#if]
            
            [#if mapCustomFields["LC_descColor"]?? && mapCustomFields["LC_descColor"]?has_content]
            <div class="product-block-title">
                <span>${i18n['cione-module.templates.myshop.header-component.color']}</span>
                <span>${mapCustomFields["LC_descColor"]!""}</span>
            </div>
            [/#if]
            
            <div class="product-block-title">
                <span>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.plazo-entrega']}</span>
                [#assign plazoEntregaProveedor =2]
                [#if hasAttributte(item.getVariant().getAttributes(), "plazoEntregaProveedor")]
                	[#assign plazoEntregaProveedor = findAttributte(item.getVariant().getAttributes(), "plazoEntregaProveedor")]
                [/#if]
                
                [#if mapCustomFields["plazoEntrega"]?? && mapCustomFields["plazoEntrega"]?has_content]
                	[#assign diasEntrega = mapCustomFields["plazoEntrega"]]
                [#else]
                	[#assign diasEntrega = plazoEntregaProveedor + 2] 
                [/#if]
                [#if diasEntrega == 2]
                	<span id="plazo_${item.getId()}">${i18n['cione-module.templates.components.siguiente-envio']}</span>
                [#else]
                	<span id="plazo_${item.getId()}">${i18n['cione-module.templates.components.plazo-proveedor']}</span>
                [/#if]
                [#-- <span id="plazo_${item.getId()}">${diasEntrega} ${i18n['cione-module.templates.myshop.listado-productos-carrito-component.dias']}</span> --]
                <input type="hidden" id="plazo-proveedor_${item.getId()}" value="${plazoEntregaProveedor}">
            </div>
            
            [#assign isDeposit = false] 
            [#if mapCustomFields["enDeposito"]?? && mapCustomFields["enDeposito"]?has_content]
        		[#assign isDeposit = mapCustomFields["enDeposito"]!false] 
            [/#if]
            
            [#-- MOSTRAMOS EL PVO --]
        	[#if !isDeposit]
        		[#if showPVO(ctx.getUser(), uuid, username)]
					[#-- Comprobamos si es un incremento --]            
	            	[#if mapCustomFields["pvoConDescuento"]?has_content && !isPackGenerico]
	            		[#assign pvoDescuento = mapCustomFields["pvoConDescuento"].getCentAmount() / 100.0]
	            		[#assign pvoOrigen = item.getPrice().getValue().getCentAmount() / 100.0]
	            		[#if model.compare(pvoDescuento, pvoOrigen)]
	            			<div class="product-block-title">
				                <span>${i18n['cione-module.templates.components.detalle-producto-component.pvo']}</span>
				                <span>${pvoDescuento?string["0.00"]} €</span>
				            </div>
				        [#else]
				        	<div class="product-block-title">
				                <span>${i18n['cione-module.templates.components.detalle-producto-component.pvo']}</span>
				                <span>${pvoOrigen?string["0.00"]} €</span>
				            </div>
	            		[/#if]
	            	[#else]
	            		[#assign pvoOrigen = item.getPrice().getValue().getCentAmount() / 100.0]
	            		<div class="product-block-title">
			                <span>${i18n['cione-module.templates.components.detalle-producto-component.pvo']}</span>
			                <span>${pvoOrigen?string["0.00"]} €</span>
			            </div>
	            	[/#if]
            	[/#if]
            [#else]
	            <div class="product-block-title">
	                <span>${i18n['cione-module.templates.components.detalle-producto-component.deposit']}</span>
                	<span>${i18n['cione-module.templates.components.detalle-producto-component.yes']}</span>
	            </div>
        	[/#if]
            
            [#-- MOSTRAMOS EL PVO-DTO --]
            
        	[#if !isDeposit]
        		[#if showPVO(ctx.getUser(), uuid, username)]
					[#assign hasPromo = infoVariant.isTienePromociones()!]				
		            [#if hasPromo]
		            	[#if mapCustomFields["pvoConDescuento"]?has_content]
		            		[#assign pvoDescuento = mapCustomFields["pvoConDescuento"].getCentAmount() / 100.0]
		            		<div class="product-block-title">
		                		<span>${i18n['cione-module.templates.components.detalle-producto-component.pvo-dto']}</span>
		            			<span>${pvoDescuento?string["0.00"]} €</span>
		            		</div>
		            	[/#if]
		            [#else]
		            	[#if mapCustomFields["refPackPromos"]?has_content]
							[#if mapCustomFields["pvoConDescuento"]?has_content]
								[#assign pvoDescuento = mapCustomFields["pvoConDescuento"].getCentAmount() / 100.0]
								[#assign pvoDescuentostring = pvoDescuento?string["0.00"]]
								
								[#-- AÑADIMOS LA INFO DEL PACK --]
					            [#if mapCustomFields["refPackPromos"]?has_content]
									[#if mapCustomFields["tipoPrecioPack"]?? && mapCustomFields["tipoPrecioPack"]?has_content && mapCustomFields["tipoPrecioPack"] == "CERRADO"]
										[#if pvoDescuento?? && pvoDescuento?has_content && (pvoDescuentostring!="0,00")]
											[#assign mapPVOPackCerrado = mapPVOPackCerrado + {mapCustomFields["refPackPromos"], pvoDescuentostring}]
										[/#if]
									[/#if]
								[/#if]
								
								[#if mapCustomFields["tipoPrecioPack"]?? && mapCustomFields["tipoPrecioPack"]?has_content && mapCustomFields["tipoPrecioPack"] == "CERRADO"]
									<div class="product-block-title">
				                		<span>${i18n['cione-module.templates.components.detalle-producto-component.all-pack-for']}</span>
				                		[#if mapPVOPackCerrado?? && mapPVOPackCerrado?has_content]
				            				<span>${mapPVOPackCerrado[mapCustomFields["refPackPromos"]]} €</span>
				            			[/#if]
				            		</div>
	    						[#else]
				            		<div class="product-block-title">
				                		<span>${i18n['cione-module.templates.components.detalle-producto-component.pvo-dto']}</span>
				            			<span>${pvoDescuento?string["0.00"]} €</span>
				            		</div>
			            		[/#if]
					        [/#if]
			            [/#if]
		            [/#if]
	            [/#if]
            [/#if]
            
            
            [#if showPVP(ctx.getUser(), uuid, username)]
            	[#if !isDeposit]
            		[#if hasAttributte(item.getVariant().getAttributes(), "pvpRecomendado")]
            			[#assign pvpRecomendado = findAttributte(item.getVariant().getAttributes(), "pvpRecomendado").getCentAmount() / 100.0]
			            <div class="product-block-title">
			                <span>${i18n['cione-module.templates.components.detalle-producto-component.pvp']}</span>
			                	<span>${pvpRecomendado?string["0.00"]} €</span>
			                </span>
			            </div>
		            [/#if]
	            [/#if]
	        [/#if]
            
            [#if familiaProducto != "audifonos" && familiaProducto != "complementosaudioservicios"]
	            [#-- el stock se carga en el document ready para evitar la carga inicial de la web --]
	            <div class="product-block-title">
	                <span id="stockreal">${i18n['cione-module.templates.myshop.listado-productos-carrito-component.stockreal']}</span>
	                <span id="stockreal_${item.getId()}"><div class="loading"></div></span>
	                
	                <span id="stockcentral">${i18n['cione-module.templates.myshop.listado-productos-carrito-component.stockcentral']}</span>
	                <span id="stockcentral_${item.getId()}"><div class="loading"></div></span>
	            
	                <span id="stockcanar">${i18n['cione-module.templates.myshop.listado-productos-carrito-component.stockcanar']}</span>
	                <span id="stockcanar_${item.getId()}"><div class="loading"></div></span>
	            </div>
            [/#if]
            
            [#if mapCustomFields["numDocEspecial"]?has_content]
	            <div class="product-block-title">
	            	[#assign refDoc = mapCustomFields["numDocEspecial"]]
	            	
	            	[#if refDoc?starts_with("CP")]
		            	<span>${i18n['cione-module.components.myshop.compra-periodica.nueva-cp-component-properties.label']}</span>
		                <span>${mapCustomFields["numDocEspecial"]!""}</span>
	            	[#elseif refDoc?starts_with("PR")]
		            	<span>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.budget']}</span>
		                <span>${mapCustomFields["numDocEspecial"]!""}</span>
	            	[/#if]
	            </div>
            [/#if]
            
        </div>
        
        <div class="b2b-cart-item-col floating-col" data-reference="${item?counter}">
            <img class="b2b-cart-item-col-delete hide-in-desktop" src="${resourcesURL}/img/myshop/icons/close-thin.svg" alt="">
            <div class="floating-title hide-in-desktop">${i18n['cione-module.templates.myshop.listado-productos-carrito-component.referencias-packs']}</div>
            <div class="product-reference pt-2">
                <label>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.referencia-cliente']}</label>
                <input type="text" [#if mapCustomFields["refCliente"]?has_content]
                	value="${mapCustomFields["refCliente"]}"[/#if]
                	[#if familiaProducto == "audifonos"]
                		onchange="actualizarExtraInfoAudio(this, '${item.getId()}', 'refCliente'); return false" autocomplete="off"
                	[#else]
                		onchange="actualizarExtraInfo(this, '${item.getId()}', 'refCliente'); return false" autocomplete="off"
                	[/#if]
                	[#if familiaProducto == "complementosaudio"]
	                	[#if mapCustomFields["refTaller"]?has_content]
	                		data-audio="${item.getId()}|${mapCustomFields["refTaller"]}"
                		[#else]
                			data-audio="${item.getId()}"
			        	[/#if]
                	[/#if]
                	>
            </div>
            
            [#if mapCustomFields["refPackPromos"]?has_content]
	            <div class="product-reference pt-2">
	            	<label>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.referencia-packs-promo']}</label>
	            	<input type="text" name="packpromo" iditem="${item.getId()}" key="${item.getVariant().getSku()}" id="packpromo-${item.getId()}" value="${mapCustomFields["refPackPromos"]}" style="background-color: #d1d1d1;" disabled>
	            	
	            </div>
            [/#if]
        	[#-- refenrencia taller --]
            [#if familiaProducto == "contactologia"]
	            [#-- <div class="product-reference product-reference-wrapper d-flex pt-2" style="">
	                <div class="product-reference-col1">
	                    <label>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.referencia-taller']}</label>
	                    [#if item.getCustom().getFieldAsString("refTaller")?has_content]
		                	<input type="text" value="${item.getCustom().getFieldAsString("refTaller")}" id="${item.getId()}"
	                			onchange="actualizarExtraInfo(this, '${item.getId()}', 'refTaller'); return false" style="background-color: #d1d1d1;" disabled>
	                	[#else]
	                		<input type="text" id="${item.getId()}"
	                			onchange="actualizarExtraInfo(this, '${item.getId()}', 'refTaller'); return false" style="background-color: #d1d1d1;" disabled>
	                	[/#if]
	                </div>
	                <div class="product-reference-col2">
	               		<input class="styled-checkbox" id="ref-${item.getId()}" type="checkbox" onchange="actualizarRefTaller(this, '${item.getId()}');" disabled>
	                	<label class="b2b-label" for="ref-${item.getId()}"></label>
	                </div>
	             
	            </div>	 --]
            [#else]
            	<div class="product-reference proudct-reference-wrapper d-flex pt-2">
	            		<div class="product-reference-col1">
		            		<label>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.referencia-taller']}</label>
		        			<input type="text" 
		        				[#if mapCustomFields["refTaller"]?has_content]
		        					value="${mapCustomFields["refTaller"]}" style="background-color: rgb(209, 209, 209); font-weight: bold;" 
	        					[#else] style="background-color: rgb(209, 209, 209);" [/#if] disabled="" id="${item.getId()}"
		        			onchange="actualizarExtraInfo(this, '${item.getId()}', 'refTaller'); return false" autocomplete="off">
	        			</div>
	        			
        			[#if model.getFamilia(item) == "monturas"]
        				[#assign aTaller = mapCustomFields["aTaller"]!false]
        				[#assign tallercheck = "" ]
        				[#if aTaller]
        					[#assign tallercheck = 'checked="checked"' ]
        				[/#if]
	                	<div class="product-reference-col2">
	               			<input class="styled-checkbox" id="ref-${item.getId()}" type="checkbox" ${tallercheck!} 
	               			onchange="actualizarExtraInfoBoolean(this, '${item.getId()}', 'aTaller');" autocomplete="off">
	               			
	               			
	               			
	                		<label class="b2b-label" for="ref-${item.getId()}"></label>
	                	</div>
        			[/#if]
        			
	            </div>	
            [/#if]
            
 			[#-- Pack Promo --]
	 		[#if model.getFamilia(item) == "monturas"]
		 		[#if mapCustomFields["step"]?? && mapCustomFields["step"]?has_content]
	            	
	            [#else]
			 		[#-- este campo debe ir oculto --]
		 			<input type="hidden" id="input-refpack-promo-${item.getId()}" [#if mapCustomFields["refPack"]?has_content]
		                	value="${mapCustomFields["refPack"]}"[/#if] autocomplete="off">
		            <div class="product-reference proudct-reference-wrapper d-flex pt-2">
		            	<div class="product-reference-col1">
		                	<label>Pack montura+lentes</label>
		                	<input type="text" id="input-descpack-${item.getId()}" [#if mapCustomFields["descPack"]?has_content]
		                	value="${mapCustomFields["descPack"]}"[/#if]
		                	onchange="actualizarExtraInfoDescPackPromo(this, '${item.getId()}', 'descPack'); return false" autocomplete="off"
		                	[#if !mapCustomFields["refPack"]?has_content]style="background-color: rgb(209, 209, 209);" disabled=""[/#if]>
		            	</div>
					    <div class="product-reference-col2">
					        <input class="styled-checkbox ${model.getFamilia(item)!""}" id="promo-${item.getId()}" type="checkbox" [#if mapCustomFields["refPack"]?has_content]checked="checked"[/#if] onchange="actualizarPackPromo(this, '${item.getId()}', true);" autocomplete="off">
					        <label class="b2b-label" for="promo-${item.getId()}"></label>
					    </div>
		            </div>
	            [/#if]
	        [/#if]
            
        </div>
        
        [#-- CANTIDAD --]
        <div class="b2b-cart-item-col   overflow-hidden">
            <div class="product-amount pt-4">
                <div class="product-amount-button-wrapper">
                	
                    <button class="product-amount-button product-amount-button-minus" type="button" 
                    [#if mapCustomFields["refPackPromos"]?has_content]
                    	style="cursor: not-allowed;background-color: #d1d1d1;">
                    [#else]
                    	[#if familiaProducto != "audifonos" && familiaProducto != "complementosaudioservicios"]onclick="updateItemQuantity('${item.getId()}', -1, '${item.getVariant().getSku()}'); return false"[/#if] 
                    	[#if familiaProducto == "audifonos" || familiaProducto == "complementosaudioservicios"]style="cursor: not-allowed;background-color: #d1d1d1;"[/#if]>
                    [/#if]
                            -
                    </button>
                    
                    <input [#if familiaProducto == "audifonos" || familiaProducto == "complementosaudioservicios"]disabled[/#if] type="number" name="cant_${item.getId()}" class="product-amount-input" min="0" max="999999"
                    	value="${item.getQuantity()}" onchange="updateItemQuantity('${item.getId()}', 0, '${item.getVariant().getSku()}'); return false" autocomplete="off">
                    
                    <button class="product-amount-button product-amount-button-plus" type="button" 
                    [#if mapCustomFields["refPackPromos"]?has_content]
                    	style="cursor: not-allowed;background-color: #d1d1d1;">
                    [#else]
                    	[#if familiaProducto != "audifonos" && familiaProducto != "complementosaudioservicios"]onclick="updateItemQuantity('${item.getId()}', 1, '${item.getVariant().getSku()}'); return false"[/#if]
                    	[#if familiaProducto == "audifonos" || familiaProducto == "complementosaudioservicios"]style="cursor: not-allowed;background-color: #d1d1d1;"[/#if]>
                    [/#if]
                            +
                    </button>
                    
                    [#if familiaProducto != "contactologia" && familiaProducto != "audifonos"]
                    	<input type="hidden" name="stock_${item.getId()}" value="${model.getStockbyAliasEkon(findAttributte(item.getVariant().getAttributes(), "aliasEkon")!)!}">
                    [#elseif familiaProducto == "contactologia"]
                    	<input type="hidden" name="stock_${item.getId()}" value="${model.getStock(item)!}">
                    [#else]
                    	<input type="hidden" name="stock_${item.getId()}" value="0">
                    [/#if]
                    <input type="hidden" id="cant-original_${item.getId()}" value="${item.getQuantity()}">
                
                </div>
            </div>
        </div>
        
        
    </div>
    
    <div class="b2b-cart-item-row">
    	<div class="b2b-cart-item-col b2b-cart-item-img"></div>
    	[#if mapCustomFields["enviocorreo_R"]?? && mapCustomFields["enviocorreo_R"]]
        	<span style="width: 75%; font-weight: bold;">${i18n.get('cione-module.templates.myshop.header-component.enviocorreomsg')}</span>
        [#elseif mapCustomFields["enviocorreo_L"]?? && mapCustomFields["enviocorreo_R"]]
        	<span style="width: 75%; font-weight: bold;">${i18n.get('cione-module.templates.myshop.header-component.enviocorreomsg')}</span>
        [/#if]
    </div>
    
    [#if !isDeposit]
    <div class="b2b-cart-item-row">
        <div class="b2b-cart-item-subtotal-wrapper">

            <div class="open-reference" data-reference="${item?counter}">
                ${i18n['cione-module.templates.myshop.listado-productos-carrito-component.referencias-packs']}
            </div>
            
            [#if showPVO(ctx.getUser(), uuid, username)]
	            [#assign valorDescuento = infoVariant.getValorDescuento()!]
				[#assign hasPromo = infoVariant.isTienePromociones()!]
				[#assign tipoPromo = infoVariant.getTipoPromocion()!]
				
				<br><br>
	            [#if hasPromo && !isPackGenerico]
	            	[#if tipoPromo == "porcentaje"]
	            		<div class="box">
		                	${i18n['cione-module.templates.myshop.listado-productos-carrito-component.producto-con']}
		                	${valorDescuento}${i18n['cione-module.templates.myshop.listado-productos-carrito-component.porcentaje-descuento']}
		                </div>
	            	[#elseif tipoPromo == "escalado"]
	            		[#assign listPromos = infoVariant.getListPromos()!]
	            		[#list listPromos as promo]
	        				<div class="box">
	        					${i18n['cione-module.templates.myshop.listado-productos-carrito-component.compra']} ${promo.getCantidad_desde()} 
	        					${i18n['cione-module.templates.myshop.listado-productos-carrito-component.unidades-ahorra']} ${promo.getValor()}%
	        					[#-- pvo original : ${promo.getPvo()} , pvoDto : ${promo.getPvoDto()} --]
	        				</div>
	            		[/#list]
	            	[/#if]
	            [/#if]
            
            	<div class="separator"></div>
            
            [/#if]
            
            
            <div class="b2b-cart-item-subtotal" id="subtotal_${item.getId()}">
            [#if showPVO(ctx.getUser(), uuid, username)]
                <span>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.subtotal']}</span>
                [#if mapCustomFields["pvoConDescuento"]?has_content]
	                [#assign pvoConDescuento = mapCustomFields["pvoConDescuento"].getCentAmount() / 100.0]
                	[#assign precioActual = pvoConDescuento?string["0.00"]]
                	[#if mapCustomFields["tipoPrecioPack"]?? && mapCustomFields["tipoPrecioPack"]?has_content && mapCustomFields["tipoPrecioPack"] == "CERRADO"]
                		[#if mapPVOPackCerrado?? && mapPVOPackCerrado?has_content]
                			<span>${i18n['cione-module.templates.components.detalle-producto-component.all-pack-for']} ${mapPVOPackCerrado[mapCustomFields["refPackPromos"]]} €</span>
                		[/#if]
                	[#else]
                		<span>${(item.getQuantity() * pvoConDescuento)?string["0.00"]}€</span>
                	[/#if]
                [#else]
                	[#assign priceActualDouble = item.getPrice().getValue().getCentAmount() / 100.0]
                	[#assign precioActual = priceActualDouble?string["0.00"]]
                	[#assign priceTotalDouble = item.getTotalPrice().getCentAmount() / 100.0]
                	<span>${priceTotalDouble?string["0.00"]}€</span>
                [/#if]
            [#else]
            	[#if mapCustomFields["pvoConDescuento"]?has_content]
                	[#assign pvoConDescuento = mapCustomFields["pvoConDescuento"].getCentAmount() / 100.0]
                	[#assign precioActual = pvoConDescuento?string["0.00"]]
                [#else]
                	[#assign priceActualDouble = item.getPrice().getValue().getCentAmount() / 100.0]
                	[#assign precioActual = priceActualDouble?string["0.00"]]
                [/#if]
            [/#if]
            </div>
            <input type="hidden" id="precioActual_${item.getId()}" value="${precioActual}">
        </div>
    </div>
	[/#if]
  </div>
 [/#list]
 
 [#---------------------------------------------------------------------------------------------------------------------]
 [#------------------------------------------------- CUSTOM LINE ITEMS -------------------------------------------------]
 [#---------------------------------------------------------------------------------------------------------------------]
 [#list carrito.getCustomLineItems() as itemc]
 	[#assign mapCustomFieldsC = itemc.getCustom().getFields().values()]
	 <div class="b2b-cart-item" id="cart-item_${itemc.getId()}">
	 	
	 	[#-- Eliminar elemento --]
	 	[#if mapCustomFieldsC["SKU"]?? && mapCustomFieldsC["SKU"]?has_content]
		    <div class="b2b-cart-item-delete" onclick="removeCLI('${itemc.getId()}', '${mapCustomFieldsC["SKU"]}', '${itemc.getQuantity()}'); return false">
		        <img class="b2b-cart-item-icon-delete" src="${resourcesURL}/img/myshop/icons/close-thin.svg" alt="Borrar elemento">
		        <span class="b2b-cart-item-delete-text">${i18n['cione-module.templates.myshop.listado-productos-carrito-component.eliminar']}</span>
		    </div>
		[#elseif itemc.getSlug()?? && itemc.getSlug()?has_content]
			<div class="b2b-cart-item-delete" onclick="removeCLI('${itemc.getId()}', '${itemc.getSlug()}', '${itemc.getQuantity()}'); return false">
		        <img class="b2b-cart-item-icon-delete" src="${resourcesURL}/img/myshop/icons/close-thin.svg" alt="Borrar elemento">
		        <span class="b2b-cart-item-delete-text">${i18n['cione-module.templates.myshop.listado-productos-carrito-component.eliminar']}</span>
		    </div>
		[/#if]
	    
	    <div class="b2b-cart-item-row">
	    
	    	[#-- imagen --]
	        <div class="b2b-cart-item-col b2b-cart-item-img">
	            <img class="b2b-floating-cart-image" src="${defaultImageCLI}" onerror="this.onerror=null; this.src='${defaultImageCLI}'" alt="" />
	        </div>
	        
	    	<div class="b2b-cart-item-col">
	    		
	    		[#-- nombre --]
		    	[#if mapCustomFieldsC["LNAM_DESC_R"]?? && mapCustomFieldsC["LNAM_DESC_R"]?has_content]
			    	<div class="product-block-title">
	                	<span class="just-title">${mapCustomFieldsC["LNAM_DESC_R"]!""}</span>
	            	</div>
	            [#elseif mapCustomFieldsC["LNAM_DESC_L"]?? && mapCustomFieldsC["LNAM_DESC_L"]?has_content]
	            	<div class="product-block-title">
	                	<span class="just-title">${mapCustomFieldsC["LNAM_DESC_L"]!""}</span>
	            	</div>
	            	[#-- Es una lente de stock --]
	            [#elseif mapCustomFieldsC["DESCRIPTION"]?? && mapCustomFieldsC["DESCRIPTION"]?has_content]
	            	[#assign descripcion = mapCustomFieldsC["DESCRIPTION"]!]
	            	[#list descripcion?split(";") as sValue]
	            		[#if sValue?is_first]
	            			<div class="product-block-title">
			                	<span class="just-title">${sValue}</span>
			            	</div>
	            		[/#if]
					[/#list]
	            	
	            [#elseif itemc.getName().get("es")?? && itemc.getName().get("es")?has_content]
	            	<div class="product-block-title">
	                	<span class="just-title">${itemc.getName().get("es")!""}</span>
	            	</div>
	            [/#if]
	            
	            [#if mapCustomFieldsC["descPackPromos"]?? && mapCustomFieldsC["descPackPromos"]?has_content]
	            	<div class="product-block-title">
	                    <span>${i18n.get('cione-module.templates.myshop.header-component.packname')}</span>
	                    <span style="font-weight: bold;">${mapCustomFieldsC["descPackPromos"]}</span>
	                </div>
	            [/#if]
	            
	            [#-- ojo CioneLab --]
	            [#if mapCustomFieldsC["LC_Ojo"]?? && mapCustomFieldsC["LC_Ojo"]?has_content]
	            	[#if mapCustomFieldsC["LC_Ojo"] == "R"]
			            <div class="product-block-title">
		                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojo']}</span>
		                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojod']}</span>
			            </div>
			        [#elseif mapCustomFieldsC["LC_Ojo"] == "L"]
			        	<div class="product-block-title">
		                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojo']}</span>
		                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojoi']}</span>
			            </div>
			        [/#if]
		        [/#if]
		        
	            [#-- ojo --]
	          	[#if mapCustomFieldsC["EYE"]?? && mapCustomFieldsC["EYE"]?has_content]
		            <div class="product-block-title">
	                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojo']}</span>
	                    [#if mapCustomFieldsC["EYE"] == "D" || mapCustomFieldsC["EYE"] == "R"]
	                    	<span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojod']}</span>
	                    [#else]
	                    	<span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojoi']}</span>
	                    [/#if]
		            </div>
		        
	            [/#if]
	            
	            [#-- esfera --]
	            [#if mapCustomFieldsC["SPH"]?? && mapCustomFieldsC["SPH"]?has_content]
		            <div class="product-block-title">
	                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.esfera']}</span>
	                    <span>${mapCustomFieldsC["SPH"]!""}</span>
		            </div>
	            [/#if]
	            
	            [#if mapCustomFieldsC["SPH_L"]?? && mapCustomFieldsC["SPH_L"]?has_content]
		            <div class="product-block-title">
	                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.esfera']}</span>
	                    <span>${mapCustomFieldsC["SPH_L"]!""}</span>
		            </div>
	            [/#if]
	            
	            [#if mapCustomFieldsC["SPH_R"]?? && mapCustomFieldsC["SPH_R"]?has_content]
		            <div class="product-block-title">
	                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.esfera']}</span>
	                    <span>${mapCustomFieldsC["SPH_R"]!""}</span>
		            </div>
	            [/#if]
	            
	            [#-- cilindro --]
	            [#if mapCustomFieldsC["CYL"]?? && mapCustomFieldsC["CYL"]?has_content]
		            <div class="product-block-title">
		            	 <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.cilindro']}</span>
		            	 <span>${mapCustomFieldsC["CYL"]!""}</span>
		            </div>
		        [/#if]
		        
		        [#if mapCustomFieldsC["CYL_L"]?? && mapCustomFieldsC["CYL_L"]?has_content]
		            <div class="product-block-title">
		            	 <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.cilindro']}</span>
		            	 <span>${mapCustomFieldsC["CYL_L"]!""}</span>
		            </div>
		        [/#if]
		        
		        [#if mapCustomFieldsC["CYL_R"]?? && mapCustomFieldsC["CYL_R"]?has_content]
		            <div class="product-block-title">
		            	 <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.cilindro']}</span>
		            	 <span>${mapCustomFieldsC["CYL_R"]!""}</span>
		            </div>
		        [/#if]
	            
	            [#-- eje --]
	            [#if mapCustomFieldsC["AX_L"]?? && mapCustomFieldsC["AX_L"]?has_content]
		            <div class="product-block-title">
	                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.eje']}</span>
	                    <span>${mapCustomFieldsC["AX_L"]!""}</span>
		            </div>
	            [/#if]
	            
	            [#if mapCustomFieldsC["AX_R"]?? && mapCustomFieldsC["AX_R"]?has_content]
		            <div class="product-block-title">
	                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.eje']}</span>
	                    <span>${mapCustomFieldsC["AX_R"]!""}</span>
		            </div>
	            [/#if]
	            
	            [#-- adicion --]
	            [#if mapCustomFieldsC["ADD_L"]?? && mapCustomFieldsC["ADD_L"]?has_content]
		            <div class="product-block-title">
	                    <span>${i18n['cione-module.templates.components.detalle-producto-component.adicion']}</span>
	                    <span>${mapCustomFieldsC["ADD_L"]!""}</span>
		            </div>
	            [/#if]
	            
	            [#if mapCustomFieldsC["ADD_R"]?? && mapCustomFieldsC["ADD_R"]?has_content]
		            <div class="product-block-title">
	                    <span>${i18n['cione-module.templates.components.detalle-producto-component.adicion']}</span>
	                    <span>${mapCustomFieldsC["ADD_R"]!""}</span>
		            </div>
	            [/#if]
	            
	            [#-- diametro --]
	          	[#if mapCustomFieldsC["CRIB"]?? && mapCustomFieldsC["CRIB"]?has_content]
		            <div class="product-block-title">
	                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.diametro']}</span>
	                    <span>${mapCustomFieldsC["CRIB"]!""}</span>
		            </div>
	            [/#if]
	            
	            [#if mapCustomFieldsC["CRIB_L"]?? && mapCustomFieldsC["CRIB_L"]?has_content]
		            <div class="product-block-title">
	                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.diametro']}</span>
	                    <span>${mapCustomFieldsC["CRIB_L"]!""}</span>
		            </div>
	            [/#if]
	            
	            [#if mapCustomFieldsC["CRIB_R"]?? && mapCustomFieldsC["CRIB_R"]?has_content]
		            <div class="product-block-title">
	                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.diametro']}</span>
	                    <span>${mapCustomFieldsC["CRIB_R"]!""}</span>
		            </div>
	            [/#if]
	            
	            [#-- color --]
	            [#if mapCustomFieldsC["COLR_L"]?? && mapCustomFieldsC["COLR_L"]?has_content]
		            <div class="product-block-title">
	                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.color']}</span>
	                    <span id="color-${itemc.getId()}">${mapCustomFieldsC["COLR_L"]!""}</span>
	                    <script>
	                    	$(document).ready(function(){
	                    		var colorCustom = '${mapCustomFieldsC["COLR_L"]!""}';
	                    		colorCustom = colorCustom.replace(/\+/g, '%20');
	                    		var decode = decodeURIComponent(colorCustom);
	                    		$('#color-${itemc.getId()}').text(decode);
							});
	                    	
	                    </script>
	                    
	                    
		            </div>
	            [/#if]
	            
	            [#if mapCustomFieldsC["COLR_R"]?? && mapCustomFieldsC["COLR_R"]?has_content]
		            <div class="product-block-title">
	                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.color']}</span>
	                    <span id="color-${itemc.getId()}">${mapCustomFieldsC["COLR_R"]!""}</span>
	                    <script>
	                    	$(document).ready(function(){
	                    		var colorCustom = '${mapCustomFieldsC["COLR_R"]!""}';
	                    		colorCustom = colorCustom.replace(/\+/g, '%20');
	                    		var decode = decodeURIComponent(colorCustom);
	                    		$('#color-${itemc.getId()}').text(decode);
							});
	                    	
	                    </script>
	                    
	                    
		            </div>
	            [/#if]
		        
		        [#-- ojo CioneLab --]
	            [#if mapCustomFieldsC["INF_R"]?? && mapCustomFieldsC["INF_R"]?has_content]
	            	[#if mapCustomFieldsC["INF_R"] == "1"]
			            <div class="product-block-title">
			            	 <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojo-informativo']}</span>
			            	 <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.yes']}</span>
			            </div>
			        [/#if]
		        [/#if]
	            
	        [#if showPVO(ctx.getUser(), uuid, username)]  
	        	[#if mapCustomFieldsC["PVO_R"]?? && mapCustomFieldsC["PVO_R"]?has_content]
		        	<div class="product-block-title">
		                <span>${i18n['cione-module.templates.components.detalle-producto-component.pvo']}</span>
		                <span>${mapCustomFieldsC["PVO_R"]!} €</span>
		            </div>
		        [#elseif mapCustomFieldsC["PVO_L"]?? && mapCustomFieldsC["PVO_L"]?has_content]
		        	<div class="product-block-title">
		                <span>${i18n['cione-module.templates.components.detalle-producto-component.pvo']}</span>
		                <span>${mapCustomFieldsC["PVO_L"]!} € </span>
		            </div>
		        [#else]
		        	[#assign moneyDouble = itemc.getMoney().getCentAmount() / 100.0]
		        	<div class="product-block-title">
	                	<span>${i18n['cione-module.templates.components.detalle-producto-component.pvo']}</span>
	                	<span>${moneyDouble!""} €</span>
	            	</div>
	            [/#if]
            	
            [/#if]
            
            
            [#if showPVO(ctx.getUser(), uuid, username)]  
	        	[#if mapCustomFieldsC["pvoConDescuento_R"]?? && mapCustomFieldsC["pvoConDescuento_R"]?has_content]
	        		[#assign priceTotalDouble = mapCustomFieldsC["pvoConDescuento_R"].getCentAmount() / 100.0]
	        		<div class="product-block-title">
		                <span>${i18n['cione-module.templates.components.detalle-producto-component.pvo-dto']}</span>
		                <span>${priceTotalDouble?string["0.00"]} €</span>
		            </div>
		        [#elseif mapCustomFieldsC["pvoConDescuento_L"]?? && mapCustomFieldsC["pvoConDescuento_L"]?has_content]
		        	[#assign priceTotalDouble = mapCustomFieldsC["pvoConDescuento_L"].getCentAmount() / 100.0]
		        	<div class="product-block-title">
		                <span>${i18n['cione-module.templates.components.detalle-producto-component.pvo-dto']}</span>
		                <span>${priceTotalDouble?string["0.00"]} €</span>
		            </div>
            	[/#if]
            [/#if]
            
            [#if showPVP(ctx.getUser(), uuid, username)]
            	[#if mapCustomFieldsC["LMATTYPE"]?? && mapCustomFieldsC["LMATTYPE"]?has_content]
            		[#-- SON LENTES DE STOCK --]
	            	<div class="product-block-title">
		                <span>${i18n['cione-module.templates.components.detalle-producto-component.pvp']}</span>
		                <span>
		                [#assign skuCustom = mapCustomFieldsC["SKU"]!]
		                ${model.getPvp(skuCustom)!} €
		                </span>
		            </div>
		        [#elseif mapCustomFieldsC["PVP_R"]?? && mapCustomFieldsC["PVP_R"]?has_content]
		        	[#-- SON LENTES DE CIONELAB --]
		        	<div class="product-block-title">
		                <span>${i18n['cione-module.templates.components.detalle-producto-component.pvp']}</span>
		                <span>${mapCustomFieldsC["PVP_R"]!} €</span>
		            </div>
		        [#elseif mapCustomFieldsC["PVP_L"]?? && mapCustomFieldsC["PVP_L"]?has_content]
		        	[#-- SON LENTES DE CIONELAB --]
		        	<div class="product-block-title">
		                <span>${i18n['cione-module.templates.components.detalle-producto-component.pvp']}</span>
		                <span>${mapCustomFieldsC["PVP_L"]!} € </span>
		            </div>
	            [#elseif mapCustomFieldsC["PVP"]?? && mapCustomFieldsC["PVP"]?has_content]
	            	[#-- SON TRABAJOS DE CIONELAB --]
		        	<div class="product-block-title">
		        		[#assign pvp = mapCustomFieldsC["PVP"].getCentAmount() / 100.0]
		                <span>${i18n['cione-module.templates.components.detalle-producto-component.pvp']}</span>
		                <span>${pvp?string["0.00"]} € </span>
		            </div>
	            [/#if]
            [/#if]
            
	            [#-- el stock se carga en el document ready para evitar la carga inicial de la web --]
	            [#if !(mapCustomFieldsC["LNAM_R"]?? && mapCustomFieldsC["LNAM_R"]?has_content) &&
        			 !(mapCustomFieldsC["LNAM_L"]?? && mapCustomFieldsC["LNAM_L"]?has_content) &&
        			 !(mapCustomFieldsC["TYPJOB"]?? && mapCustomFieldsC["TYPJOB"]?has_content)]
	            <div class="product-block-title">
	                <span id="stockreal">${i18n['cione-module.templates.myshop.listado-productos-carrito-component.stockreal']}</span>
	                <span id="stockreal_${itemc.getId()}"><div class="loading"></div></span>
	                
	                <span id="stockcentral">${i18n['cione-module.templates.myshop.listado-productos-carrito-component.stockcentral']}</span>
	                <span id="stockcentral_${itemc.getId()}"><div class="loading"></div></span>
	            
	                <span id="stockcanar">${i18n['cione-module.templates.myshop.listado-productos-carrito-component.stockcanar']}</span>
	                <span id="stockcanar_${itemc.getId()}"><div class="loading"></div></span>
	            </div>
            	[/#if]
            	
	    	</div>
	    	
	    	[#-- refcliente y reftaller --]
	        <div class="b2b-cart-item-col floating-col" data-reference="${itemc?counter}">
	            <img class="b2b-cart-item-col-delete hide-in-desktop" src="${resourcesURL}/img/myshop/icons/close-thin.svg" alt="">
	            <div class="floating-title hide-in-desktop">${i18n['cione-module.templates.myshop.listado-productos-carrito-component.referencias-packs']}</div>
	            
	            [#-- referencia cliente --]
	            [#if mapCustomFieldsC["refCliente"]??]
		            <div class="product-reference pt-2">
		                <label>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.referencia-cliente']}</label>
		                <input type="text" value="${mapCustomFieldsC["refCliente"]}"
		                	onchange="actualizarExtraInfoCLI(this, '${itemc.getId()}', 'refCliente'); return false" autocomplete="off">
		            </div>
	            [#elseif mapCustomFieldsC["_REFSOCIO"]??]
	            	<div class="product-reference pt-2">
		                <label>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.referencia-cliente']}</label>
		                <input type="text" value="${mapCustomFieldsC["_REFSOCIO"]}"
		                	onchange="actualizarExtraInfoCLI(this, '${itemc.getId()}', '_REFSOCIO'); return false" autocomplete="off">
		            </div>
		        [#else]
		        	<div class="product-reference pt-2">
		                <label>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.referencia-cliente']}</label>
		                <input type="text" onchange="actualizarExtraInfoCLI(this, '${itemc.getId()}', 'refCliente'); return false" autocomplete="off">
		            </div>
	            [/#if]
	            
				[#if mapCustomFieldsC["refPackPromos"]?has_content]
		            <div class="product-reference pt-2">
		            	<label>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.referencia-packs-promo']}</label>
		            	<input type="text" name="packpromo" iditem="${itemc.getId()}" key="${itemc.getSlug()}" id="packpromo-${itemc.getId()}" value="${mapCustomFieldsC["refPackPromos"]}" style="background-color: #d1d1d1;" disabled>
		            </div>
	            [/#if]
	            
	            [#-- referencia taller --]
	            <div class="product-reference proudct-reference-wrapper d-flex pt-2">
	                <div class="product-reference-col1">
	                    <label>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.referencia-taller']}</label>
	                    [#if mapCustomFieldsC["refTaller"]??]
	                    	[#if mapCustomFieldsC["refTaller"]?has_content]
	                    		[#assign aTaller = mapCustomFieldsC["aTaller"]!false]
	                			[#if aTaller]
			                		<input type="text" value="${mapCustomFieldsC["refTaller"]}" id="${itemc.getId()}"
		                				onchange="actualizarExtraInfoCLI(this, '${itemc.getId()}', 'refTaller'); return false" style="background-color: #d1d1d1;" disabled>
		                				</div>
						                <div class="product-reference-col2">
						               		<input class="styled-checkbox" id="ref-${itemc.getId()}" type="checkbox" checked="checked" onchange="actualizarRefTaller(this, '${itemc.getId()}');" disabled>
						                	<label class="b2b-label" for="ref-${itemc.getId()}"></label>
						                </div>
						        [#else]
						        	<input type="text" value="${mapCustomFieldsC["refTaller"]}" id="${itemc.getId()}"
		                				onchange="actualizarExtraInfoCLI(this, '${itemc.getId()}', 'refTaller'); return false" style="background-color: #d1d1d1;" disabled>
		                				</div>
						                <div class="product-reference-col2">
						               		<input class="styled-checkbox" id="ref-${itemc.getId()}" type="checkbox" onchange="actualizarRefTaller(this, '${itemc.getId()}');" disabled>
						                	<label class="b2b-label" for="ref-${itemc.getId()}"></label>
						                </div>
						        [/#if]
						        
                			[#else]
                				[#assign aTaller = mapCustomFieldsC["aTaller"]!false]
	                			[#if aTaller]
	                				<input type="text" value="${mapCustomFieldsC["refTaller"]}" id="${itemc.getId()}"
		                				onchange="actualizarExtraInfoCLI(this, '${itemc.getId()}', 'refTaller'); return false" style="background-color: #d1d1d1;" disabled>
		                				</div>
						                <div class="product-reference-col2">
						               		<input class="styled-checkbox" id="ref-${itemc.getId()}" type="checkbox" checked="checked" onchange="actualizarRefTaller(this, '${itemc.getId()}');" disabled>
						                	<label class="b2b-label" for="ref-${itemc.getId()}"></label>
						                </div>
					            [#else]
					            	<input type="text" value="${mapCustomFieldsC["refTaller"]}" id="${itemc.getId()}"
		                				onchange="actualizarExtraInfoCLI(this, '${itemc.getId()}', 'refTaller'); return false" style="background-color: #d1d1d1;" disabled>
		                				</div>
						                <div class="product-reference-col2">
						               		<input class="styled-checkbox" id="ref-${itemc.getId()}" type="checkbox" onchange="actualizarRefTaller(this, '${itemc.getId()}');" disabled>
						                	<label class="b2b-label" for="ref-${itemc.getId()}"></label>
						                </div>
						        [/#if]
                			[/#if]
	                	[#elseif mapCustomFieldsC["_REFTALLER"]??]
	                		[#if mapCustomFieldsC["_REFTALLER"]?has_content]
	                			[#if mapCustomFieldsC["_STCIONE"]?? 
	                				&& mapCustomFieldsC["_STCIONE"]?has_content 
	                				&& mapCustomFieldsC["_STCIONE"] == "1"]
	                				<input type="text" value="${mapCustomFieldsC["_REFTALLER"]}" id="${itemc.getId()}"
		                				onchange="actualizarExtraInfoCLI(this, '${itemc.getId()}', '_REFTALLER'); return false " style="background-color: #d1d1d1;" disabled>
		                			</div>
					                <div class="product-reference-col2">
					               		<input class="styled-checkbox" id="ref-${itemc.getId()}" type="checkbox" checked onchange="actualizarRefTaller(this, '${itemc.getId()}');" disabled>
					                	<label class="b2b-label" for="ref-${itemc.getId()}"></label>
					                </div>
					            [#else]
					            	<input type="text" value="${mapCustomFieldsC["_REFTALLER"]}" id="${itemc.getId()}"
		                				onchange="actualizarExtraInfoCLI(this, '${itemc.getId()}', '_REFTALLER'); return false " style="background-color: #d1d1d1;" disabled>
		                			</div>
					                <div class="product-reference-col2">
					               		<input class="styled-checkbox" id="ref-${itemc.getId()}" type="checkbox" onchange="actualizarRefTaller(this, '${itemc.getId()}');" disabled>
					                	<label class="b2b-label" for="ref-${itemc.getId()}"></label>
					                </div>
					            [/#if]
		                		[#-- <input type="text" value="${itemc.getCustom().getFieldAsString("_REFTALLER")}" id="${itemc.getId()}"
	                				onchange="actualizarExtraInfoCLI(this, '${itemc.getId()}', '_REFTALLER'); return false" >
	                			</div>
				                <div class="product-reference-col2">
				               		<input class="styled-checkbox" id="ref-${itemc.getId()}" type="checkbox" checked="checked" onchange="actualizarRefTaller(this, '${itemc.getId()}');">
				                	<label class="b2b-label" for="ref-${itemc.getId()}"></label>
				                </div> --]
				                
				            [#else]
				            	[#if mapCustomFieldsC["_STCIONE"]?? 
	                				&& mapCustomFieldsC["_STCIONE"]?has_content 
	                				&& mapCustomFieldsC["_STCIONE"] == "1"]
	                				<input type="text" value="${mapCustomFieldsC["_REFTALLER"]}" id="${itemc.getId()}"
		                				onchange="actualizarExtraInfoCLI(this, '${itemc.getId()}', '_REFTALLER'); return false" style="background-color: #d1d1d1;" disabled>
		                			</div>
					                <div class="product-reference-col2">
					               		<input class="styled-checkbox" id="ref-${itemc.getId()}" type="checkbox" checked disabled onchange="actualizarRefTaller(this, '${itemc.getId()}');" disabled>
					                	<label class="b2b-label" for="ref-${itemc.getId()}"></label>
					                </div>
	                			[#else]
	                				<input type="text" value="${mapCustomFieldsC["_REFTALLER"]}" id="${itemc.getId()}"
		                				onchange="actualizarExtraInfoCLI(this, '${itemc.getId()}', '_REFTALLER'); return false" style="background-color: #d1d1d1;" disabled>
		                			</div>
					                <div class="product-reference-col2">
					               		<input class="styled-checkbox" id="ref-${itemc.getId()}" type="checkbox" onchange="actualizarRefTaller(this, '${itemc.getId()}');" disabled>
					                	<label class="b2b-label" for="ref-${itemc.getId()}"></label>
					                </div>
	                			[/#if]
				            	[#-- <input type="text" value="${itemc.getCustom().getFieldAsString("_REFTALLER")}" id="${itemc.getId()}"
	                				onchange="actualizarExtraInfoCLI(this, '${itemc.getId()}', '_REFTALLER'); return false" style="background-color: #d1d1d1;" disabled>
	                			</div>
				                <div class="product-reference-col2">
				               		<input class="styled-checkbox" id="ref-${itemc.getId()}" type="checkbox" onchange="actualizarRefTaller(this, '${itemc.getId()}');">
				                	<label class="b2b-label" for="ref-${itemc.getId()}"></label>
				                </div> --]
				            [/#if]
	                	[/#if]
	                
	             
	            </div>
            
	 			[#-- Pack Promo --]
	 			[#if mapCustomFieldsC["step"]?? && mapCustomFieldsC["step"]?has_content]
	 			[#else]
		 			<input type="hidden" id="input-refpack-promo-${itemc.getId()}" [#if mapCustomFieldsC["refPack"]?has_content]
		                	value="${mapCustomFieldsC["refPack"]}"[/#if] autocomplete="off">
		                	
		            <div class="product-reference proudct-reference-wrapper d-flex pt-2">
		            	<div class="product-reference-col1">
		                	<label>Pack montura+lentes</label>
		                	<input type="text" id="input-descpack-${itemc.getId()}" [#if mapCustomFieldsC["descPack"]?has_content]
		                	value="${mapCustomFieldsC["descPack"]}"[/#if]
		                	onchange="actualizarExtraInfoDescPackPromoCLI(this, '${itemc.getId()}', 'descPack'); return false" autocomplete="off"
		                	[#if !mapCustomFieldsC["refPack"]?has_content]style="background-color: rgb(209, 209, 209);" disabled=""[/#if]>
		            	</div>
					    <div class="product-reference-col2">
					        <input class="styled-checkbox ppcli" id="promo-${itemc.getId()}" type="checkbox" [#if mapCustomFieldsC["refPack"]?has_content]checked="checked"[/#if] onchange="actualizarPackPromo(this, '${itemc.getId()}', false);" autocomplete="off">
					        <label class="b2b-label" for="promo-${itemc.getId()}"></label>
					    </div>
		            </div>
	            [/#if]
	        </div>
	        
	        [#-- cantidad --]
	        <div class="b2b-cart-item-col   overflow-hidden">
	            <div class="product-amount pt-4">
	                <div class="product-amount-button-wrapper">
	                	[#if mapCustomFieldsC["SKU"]?? && mapCustomFieldsC["SKU"]?has_content]
	                	
	                		[#if !(mapCustomFieldsC["LNAM_R"]?? && mapCustomFieldsC["LNAM_R"]?has_content) &&
	                			 !(mapCustomFieldsC["LNAM_L"]?? && mapCustomFieldsC["LNAM_L"]?has_content) &&
	                			 !(mapCustomFieldsC["TYPJOB"]?? && mapCustomFieldsC["TYPJOB"]?has_content)] 
		                    <button class="product-amount-button product-amount-button-minus" type="button" onclick="updateCLIQuantity('${itemc.getId()}', -1, '${mapCustomFieldsC["SKU"]}'); return false">
		                        -
		                    </button>
		                    
		                    <input type="number" name="cant_${itemc.getId()}" class="product-amount-input" min="0" max="999999"
		                    	value="${itemc.getQuantity()}" onchange="updateCLIQuantity('${itemc.getId()}', 0, '${mapCustomFieldsC["SKU"]}); return false" autocomplete="off">
		                    
		                    <button class="product-amount-button product-amount-button-plus" type="button" onclick="updateCLIQuantity('${itemc.getId()}', 1, '${mapCustomFieldsC["SKU"]}'); return false">
		                        +
		                    </button>
	                		[/#if]
	                		
		                [#elseif itemc.getSlug()?? && itemc.getSlug()?has_content]
		                
		                	[#if !(mapCustomFieldsC["LNAM_R"]?? && mapCustomFieldsC["LNAM_R"]?has_content) &&
	                			 !(mapCustomFieldsC["LNAM_L"]?? && mapCustomFieldsC["LNAM_L"]?has_content) &&
	                			 !(mapCustomFieldsC["TYPJOB"]?? && mapCustomFieldsC["TYPJOB"]?has_content)] 
		                	<button class="product-amount-button product-amount-button-minus" type="button" onclick="updateCLIQuantity('${itemc.getId()}', -1, '${itemc.getSlug()}'); return false">
		                        -
		                    </button>
		                    
		                    <input type="number" name="cant_${itemc.getId()}" class="product-amount-input" min="0" max="999999"
		                    	value="${itemc.getQuantity()}" onchange="updateCLIQuantity('${itemc.getId()}', 0, '${itemc.getSlug()}); return false" autocomplete="off">
		                    	
		                    <button class="product-amount-button product-amount-button-plus" type="button" onclick="updateCLIQuantity('${itemc.getId()}', 1, '${itemc.getSlug()}'); return false">
		                        +
		                    </button>
		                    [/#if]
		                    
		                [/#if]
	                    <input type="hidden" id="cant-original_${itemc.getId()}" value="${itemc.getQuantity()}">
	                </div>
	            </div>
	        </div>
	        
	    </div>
	    
	    [#-- precio --]
	    <div class="b2b-cart-item-row">
	        <div class="b2b-cart-item-subtotal-wrapper">
	
	            <div class="open-reference" data-reference="${itemc?counter}">
	                ${i18n['cione-module.templates.myshop.listado-productos-carrito-component.referencias-packs']}
	            </div>
	            
	            <div class="b2b-cart-item-subtotal" id="subtotal_${itemc.getId()}">
	            	[#assign precioActualDouble = itemc.getTotalPrice().getCentAmount() / 100.0]
		            [#assign precioActual = precioActualDouble?string["0.00"]]
		            
		            [#if showPVO(ctx.getUser(), uuid, username)]
		            
		            	[#if mapCustomFieldsC["pvoConDescuento_R"]?? && mapCustomFieldsC["pvoConDescuento_R"]?has_content]
			        		[#assign precioActual = mapCustomFieldsC["pvoConDescuento_R"].getCentAmount() / 100.0]
			        		
				        [#elseif mapCustomFieldsC["pvoConDescuento_L"]?? && mapCustomFieldsC["pvoConDescuento_L"]?has_content]
				        	[#assign precioActual = mapCustomFieldsC["pvoConDescuento_L"].getCentAmount() / 100.0]
				        	
		            	[/#if]
		            
		            
		                <span>Subtotal</span>
	                	<span>${precioActual}€</span>
		            [/#if]
	            </div>
	            <input type="hidden" id="precioActual_${itemc.getId()}" value="${precioActual}">
	        </div>
	    </div>
	    
	 </div>
 [/#list]
 
    [#-- Nota para el SAS: se movio al componente direcciones de envio
    <div class="b2b-cart-notes">
        <div class="b2b-cart-notes-title">
        	<strong>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.sastitle']}</strong> ${i18n['cione-module.templates.myshop.listado-productos-carrito-component.sascomment']}
        </div>
        <div>
            <textarea class="b2b-cart-notes-comment" maxlength='500' rows="5" onchange="actualizarNotaSAS(this, '${carrito.id}'); return false">[#if carrito.getCustom()??][#if carrito.getCustom().getFieldAsString("notaSAS")??][#if carrito.getCustom().getFieldAsString("notaSAS")?has_content]${carrito.getCustom().getFieldAsString("notaSAS")?trim!""}[/#if][/#if][/#if]</textarea>
        </div>
        <div class="b2b-cart-notes-footer">${i18n['cione-module.templates.myshop.listado-productos-carrito-component.maxchar']}</div>
    </div>
 	--]
 	
 [#else]
	<div>${i18n['cione-module.templates.myshop.header-component.carritoVacio']}</div>
 [/#if]
 
 </div>


	<div class="b2b-cart-total-wrapper">
		<div class="b2b-card-total-wrapper sticky">
	    	<div class="b2b-cart-total">
		    	[#if showPVO(ctx.getUser(), uuid, username)]
					<div class="b2b-cart-total-price" id="cart-total-price">
						<span>Total</span>
					    [#if carrito?has_content]
							<span>${model.getCarritoTotal()}€</span>
						[#else]
						   	<span>0,00€</span>
						[/#if]
					</div>
				    <div class="b2b-cart-total-text">
				        
				    </div>
				[/#if]
			    <div class="product-button-wrapper">
					[#assign link = cmsfn.link("website", content.formularioDireccionesLink!)!]
			    	<button class="product-button" type="button" onclick="validarCompraMinima()" 
			    		[#if !carrito?has_content || (carrito.getLineItems()?size <= 0 && carrito.getCustomLineItems()?size <= 0)] disabled [/#if]>
			            ${i18n['cione-module.templates.myshop.listado-productos-carrito-component.enviar-pedido']?upper_case}
			        </button>
			    </div>
			    
			</div>
			<span class="error ml-5" id="cart-error-message"></span>
			<div class="b2b-cart-back-button">
				<div class="b2b-button-wrapper">
				    <button  class="b2b-button b2b-button-filter" type="button" onclick="location.href='${ctx.contextPath}/cione/private/myshop'">
				       ${i18n['cione-module.templates.myshop.direcciones-envio-component.seguir-comprando']?upper_case}
				    </button>
				</div>
	        </div>
	        
	        [#if !ctx.getUser().hasRole("OPTCAN") && !ctx.getUser().hasRole("OPTMAD") && !ctx.getUser().hasRole("cliente_monturas")]
				<div class="b2b-cart-back-button pt-3">
					<div class="b2b-button-wrapper">
					    <button  class="b2b-button b2b-button-filter" type="button" onclick="showModalBudget(); return false">
					       ${i18n['cione-module.templates.myshop.direcciones-envio-component.presupuesto']?upper_case}
					    </button>
					</div>
		        </div>
		        
				<div class="b2b-cart-back-button pt-3">
					<div class="b2b-button-wrapper">
					    <button  class="b2b-button b2b-button-filter" type="button" onclick="showModalPeriodicPurchase(); return false">
					       ${i18n['cione-module.templates.myshop.direcciones-envio-component.suscripcion']?upper_case}
					    </button>
					</div>
		        </div>
	        [/#if]
        </div>
    </div>
    

    
    [#-- BEGIN: MODAL PRESUPUESTO --]
    [#assign closeimg = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/icons/close-thin.svg"]
    <div id="modal-budget" class="modal-purchase">
	    <div class="modal-purchase-box">
	    
	        <div class="modal-purchase-header">
	            <p>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.modal.title']?upper_case}</p>
	            <div class="modal-purchase-close">
	                <img class="modal-purchase-close-img" src="${closeimg!""}" alt="cerrar" onclick='closeModal("modal-budget")'>
	            </div>
	        </div>
	        
	        <div class="modal-purchase-info">
	            <div>
	                <p>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.modal.body']}</p>
	            </div>
	        </div>
	        
	        <div class="modal-purchase-footer">
	            <button class="modal-purchase-button modal-purchase-button--transparent" type="button" onclick='closeModal("modal-budget")'>
	                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}
	            </button>
	            <button class="modal-purchase-button" type="button" onclick="addToShoppingList('${carrito.id!""}'); return false">
	                ${i18n['cione-module.templates.myshop.listado-productos-carrito-component.modal.ok']?upper_case}
	            </button>
	        </div>
	    </div>
	</div>
    [#-- END: MODAL PRESUPUESTO --]
    
    
    [#-- BEGIN: MODAL COMPRA PERIODICA --]
    <div id="modal-periodic-purchase" class="b2b-modal-periodica">

        <div class="b2b-modal-container">

            <h3 class="b2b-modal-title">${i18n['cione-module.templates.components.nueva-cp-component.title']}</h3>
            <img class="b2b-modal-close" src="${closeimg!""}" alt="Cerrar" onclick='closeModal("modal-periodic-purchase")'/>
            <div class="b2b-modal-body">

                <div class="row">
                
                    <div class="col-6">

                        <div class="b2b-button-wrapper">
                            <button class="b2b-button b2b-button-filter" type="button" onclick="addToPeriodicPurchase('${carrito.id!""}'); return false">
                                ${i18n['cione-module.templates.components.nueva-cp-component.new']}
                            </button>
                        </div>
                    </div>
                    
                    <div class="col-6">

                        <div class="b2b-button-wrapper">
                            <button class="b2b-button b2b-button-filter" type="button" onclick='getAllPeriodicPurchase()'>
                                ${i18n['cione-module.templates.components.nueva-cp-component.exist']}
                            </button>
                        </div>
						<span id="periodicpurchaselistmsg" style="max-width: 300px;margin: 30px auto 0;display: none;">${i18n['cione-module.templates.components.nueva-cp-component.noexist']}</span>
                        <select id="periodicpurchaselist" style="display:none">
                        	<option></option>
                        </select>
                        
                    </div>
                    
                </div>

            </div>
        </div>
    </div>
    [#-- END: MODAL COMPRA PERIODICA --]
    
	[#-- BEGIN: MODAL CONFIRMACION ELIMINAR PACK --]
    [#assign closeimg = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/icons/close-thin.svg"]
    <div id="modal-pack" class="modal-purchase">
	    <div class="modal-purchase-box">
	        <div class="modal-purchase-header">
	            <p></p>
	            <div class="modal-purchase-close">											
	                <img class="modal-purchase-close-img" src="${closeimg!""}" alt="cerrar" onclick='closeModalGeneric("#modal-pack");'>
	            </div>
	        </div>
	        
	        <div class="modal-purchase-info">
	            <div>
	                <p style="font-size: 16px;">${i18n['cione-module.templates.myshop.listado-productos-carrito-component.modal.delete-items-pack-confirmation']}</p>
	            </div>
	        </div>
	        
	        <div class="modal-purchase-footer">
	            <button class="modal-purchase-button modal-purchase-button--transparent" type="button" onclick='closeModalGeneric("#modal-pack");'>
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
</form>
</main>


<script type="text/javascript">

	$( document ).ready(function() {
	
		[#if carrito?has_content && (carrito.getLineItems()?size > 0 || carrito.getCustomLineItems()?size > 0)]
		
			var urlstock = "";
			var stock = 0;
			var stockCTRAL = 0;
			[#list carrito.getLineItems() as item]
				[#if item.getCustom().getFields().values()["LC_sku"]?has_content]
					urlstock = "${ctx.contextPath}/.rest/private/stock?sku=" + encodeURIComponent("${item.getCustom().getFields().values()["LC_sku"]}");
					
					
				[#else]
					[#if hasAttributte(item.getVariant().getAttributes(), "aliasEkon")]
						urlstock = "${ctx.contextPath}/.rest/private/stock?sku=" + encodeURIComponent("${findAttributte(item.getVariant().getAttributes(), "aliasEkon")!""}");
						
					[/#if]
				[/#if]
				if (urlstock != "") {
					stock = 0;
					stockCTRAL = 0;
					$.ajax({
						url : urlstock,
						type : "GET",
						cache : false, 
						async: false, 
						success : function(response) {
							stock = response.stock;
							stockCTRAL = response.stockCTRAL;
							almacen = response.almacen;
							
							if (almacen == 'stockCANAR'){
								$("span#stockcanar_${item.getId()}").text(stock);
								$("span#stockcentral_${item.getId()}").text(stockCTRAL);
								
								$("span#stockreal").hide();
								$("span#stockreal_${item.getId()}").hide();
							} else {
								$("span#stockreal_${item.getId()}").text(stock);
								
								$("span#stockcentral").hide();
								$("span#stockcentral_${item.getId()}").hide();
								$("span#stockcanar").hide();
								$("span#stockcanar_${item.getId()}").hide();
							}
						},
						error : function(response) {
							console.log(response); 
						}
					});
					
				}
			[/#list]
		[/#if]
		
		[#if carrito?has_content && (carrito.getLineItems()?size > 0 || carrito.getCustomLineItems()?size > 0)]
			[#list carrito.getCustomLineItems() as itemc]
				[#assign mapCustomFieldsC = itemc.getCustom().getFields().values()]
				[#if !(mapCustomFieldsC["LNAM_R"]?? && mapCustomFieldsC["LNAM_R"]?has_content) &&
	            	 !(mapCustomFieldsC["LNAM_L"]?? && mapCustomFieldsC["LNAM_L"]?has_content) &&
	                 !(mapCustomFieldsC["TYPJOB"]?? && mapCustomFieldsC["TYPJOB"]?has_content)] 
	                 
	                var urlstock = "${ctx.contextPath}/.rest/private/stock?sku=" + encodeURIComponent("${mapCustomFieldsC["SKU"]!""}");
					var stock = 0;
					var stockCTRAL = 0;
					$.ajax({
						url : urlstock,
						type : "GET",
						cache : false, 
						async: false, 
						success : function(response) {
							stock = response.stock;
							stockCTRAL = response.stockCTRAL;
							almacen = response.almacen;
							
							if (almacen == 'stockCANAR'){
								$("span#stockcanar_${itemc.getId()}").text(stock);
								$("span#stockcentral_${itemc.getId()}").text(stockCTRAL);
								
								$("span#stockreal").hide();
								$("span#stockreal_${itemc.getId()}").hide();
							} else {
								$("span#stockreal_${itemc.getId()}").text(stock);
								
								$("span#stockcentral").hide();
								$("span#stockcentral_${itemc.getId()}").hide();
								$("span#stockcanar").hide();
								$("span#stockcanar_${itemc.getId()}").hide();
							}
						},
						error : function(response) {
							console.log(response); 
						}
					});
					
					
					
				[/#if]
			[/#list]
		[/#if]
	});
	
	function showModalBudget(){
		$('#modal-budget').css('display','flex');
	}

	function showModalPeriodicPurchase(){
		$('#modal-periodic-purchase').css('display','flex');
	}
	
	function closeModal(title) {
		$("#"+title).css("display","none");
	}	
	
	function getAllPeriodicPurchase(){
		
		$("#loading").css("z-index","99999999");
		$("#loading").show();
		
		var url = "${ctx.contextPath}/.rest/private/periodicpurchase/allmodal";
		
		$.ajax({
			url : url,
			type : "GET",
			cache : false, 
			async: false, 
			success : function(response) {
				
				if(response.total>=1){
					response.results.forEach(function(periodic, key, results){
						var options = $("#periodicpurchaselist");
						options.append($("<option />").val(periodic.id).text(periodic.custom.fields.numCliente));
		        	});
		        	$('#periodicpurchaselist').css('display','flex');
				}else{
					$('#periodicpurchaselistmsg').css('display','flex');
				}
			
			},
			error : function(response) {
				console.log("Error al recuperar los presupuestos");
				console.log(response);
				$("#loading").hide();
			}
		});
		
		$("#loading").hide();
	}
	
	function getStock(sku){
		var urlstock = "${ctx.contextPath}/.rest/private/stock?sku=" + encodeURIComponent(sku);
		var stock = 0;
		
		$.ajax({
			url : urlstock,
			type : "GET",
			cache : false, 
			async: false, 
			success : function(response) {
				stock = response.stock;
			},
			error : function(response) {
				console.log(response); 
			}
		});
		
		return stock;
	}
	
	function addToShoppingList(itemid) {
		
		$("#loading").css("z-index","99999999");
		$("#loading").show();
		
		var url = "${ctx.contextPath}/.rest/private/shoppinglist/addCartToShoppingList";
				
		$.ajax({
			url : url,
			type : "POST",
	        contentType: 'application/json; charset=utf-8',
			cache : false, 
			async: false, 
			success : function(response) {
				[#assign linkShoppingList = cmsfn.link("website", content.detalleShoppingListLink!)!]
				location.href = '${linkShoppingList!'#'}' + '?id=' + response.id;
				$("#loading").hide();
				
			},
			error : function(response) {
				console.log("Error al crear el presupuesto: "); 	
				console.log(response); 
				$("#loading").hide();
			}
		});
		
		$("#loading").hide();
	}
	function addToPeriodicPurchase(itemid) {
		
		$("#loading").css("z-index","99999999");
		$("#loading").show();
		
		[#assign linkPeriodicPurchase = cmsfn.link("website", content.newPeriodicPurchaseLink!)!]
		window.location.replace("${linkPeriodicPurchase!"#"}");
		
		[#--  
		var url = "${ctx.contextPath}/.rest/private/periodicpurchase/addCartToPeriodicPurchase";
				
		$.ajax({
			url : url,
			type : "POST",
	        contentType: 'application/json; charset=utf-8',
			cache : false, 
			async: false, 
			success : function(response) {
				[#assign linkPeriodicPurchase = cmsfn.link("website", content.newPeriodicPurchaseLink!)!]
				location.href = '${linkPeriodicPurchase!'#'}' + '?id=' + response.id;
				$("#loading").hide();
				
			},
			error : function(response) {
				console.log("Error al crear el presupuesto: "); 	
				console.log(response); 
				$("#loading").hide();
			}
		});--]
		
		$("#loading").hide();
	}

function formToJSON(itemId) {
	
	var csrf = $("#formCarritoList input[name=csrf]").val();
	var definitionName = $("#formCarritoList input[name=definitionName]").val();
	var connectionName = $("#formCarritoList input[name=connectionName]").val();
	var id = $("#formCarritoList input[name=id]").val();
	var lineItemId = itemId;
	var userId = $("#formCarritoList input[name=userId]").val();
	var accessToken = $("#formCarritoList input[name=accessToken]").val();
	var userIdEncodingDisabled = $("#formCarritoList input[name=userIdEncodingDisabled]").val();
	
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

function updateItemQuantity(itemId, botonPulsado, sku) {
	$("#loading").show();
	
	var filter = formToJSON(itemId);
	
	var cantidad = parseInt($("#formCarritoList input[name=cant_" + itemId + "]").val().replace(",", ".").replace(".", ""));
	cantidad += parseInt(botonPulsado);
	
	var cantidadOriginal = parseInt($("#cant-original_" + itemId).val().replace(",", ".").replace(".", ""));
	
	$("#formCarritoList input[name=cant_" + itemId + "]").val(cantidad);
	
	if(cantidad <= 0) {
		removeItem(itemId, sku, cantidadOriginal);
		return;
	}
	
	$.ajax({
		url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/update/" + cantidad,
		type: "POST",
		data: filter,
        contentType: 'application/json; charset=utf-8',
        cache: false,
        dataType: "json",
        success: function(response) {
        	
        	var cant = parseInt(cantidad);
			if (typeof mapCarrito.get(sku) !== 'undefined') {
				cant += parseInt(mapCarrito.get(sku));
				cant -= cantidadOriginal;
			}
			mapCarrito.set(sku, parseInt(cant));
			
			console.log(mapCarrito);
			
			let respuesta = actualizarLineItems(response, sku);
			
			//actualiza popup carrito
			refrescarPopupCarrito(respuesta);
			
    		//actualiza listado del carrito
        	refrescarListadoCarrito(respuesta);
        },
        error: function(response) { console.log("Error update cantidad"); },
        complete : function(response) { $("#loading").hide(); }
	});
}

function updateCLIQuantity(itemId, botonPulsado, sku) {
	$("#loading").show();
	
	var filter = formToJSON(itemId);
	
	var cantidad = parseInt($("#formCarritoList input[name=cant_" + itemId + "]").val().replace(",", ".").replace(".", ""));
	cantidad += parseInt(botonPulsado);
	
	var cantidadOriginal = parseInt($("#cant-original_" + itemId).val().replace(",", ".").replace(".", ""));
	
	$("#formCarritoList input[name=cant_" + itemId + "]").val(cantidad);
	
	if(cantidad <= 0) {
		removeCLI(itemId, sku, cantidadOriginal);
		return;
	}
	
	$.ajax({
		url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updatecli/" + cantidad,
		type: "POST",
		data: filter,
        contentType: 'application/json; charset=utf-8',
        cache: false,
        dataType: "json",
        success: function(response) {
        	
        	var cant = parseInt(cantidad);
			if (typeof mapCarrito.get(sku) !== 'undefined') {
				cant += parseInt(mapCarrito.get(sku));
				cant -= cantidadOriginal;
			}
			mapCarrito.set(sku, parseInt(cant));
			
			console.log(mapCarrito);
			
			let respuesta = actualizarLineItems(response, sku);
			
			//actualiza popup carrito
			refrescarPopupCarrito(respuesta);
			
    		//actualiza listado del carrito
        	refrescarListadoCarrito(respuesta);
        },
        error: function(response) { console.log("Error update cantidad"); },
        complete : function(response) { $("#loading").hide(); }
	});
}

function refrescarListadoCarrito(response) {
	if(response.lineItems.length > 0 || response.customLineItems.length > 0) {
		response.lineItems.forEach(item => {
			[#if showPVO(ctx.getUser(), uuid, username)]
				let precio;
	            if(typeof item.custom.fields.pvoConDescuento !== 'undefined')
	            	precio = parseFloat((item.custom.fields.pvoConDescuento.centAmount / 100) * item.quantity);
	            else
	            	precio = parseFloat(item.totalPrice.centAmount / 100);
            	
            	$("#subtotal_" + item.id).html('<span>Subtotal</span><span>' + formatPrice(precio) + '€</span>');
            [/#if]
            
            $("#cant-original_" + item.id).val(item.quantity);
		});
		
		response.customLineItems.forEach(item => {
			[#if showPVO(ctx.getUser(), uuid, username)]
				let precio;
				if(typeof item.custom.fields.pvoConDescuento_L !== 'undefined')
					precio = parseFloat((item.custom.fields.pvoConDescuento_L.centAmount / 100) * item.quantity);
				else if (typeof item.custom.fields.pvoConDescuento_R !== 'undefined')
					precio = parseFloat((item.custom.fields.pvoConDescuento_R.centAmount / 100) * item.quantity);
				else
					precio = parseFloat(item.totalPrice.centAmount / 100);
					
            	$("#subtotal_" + item.id).html('<span>Subtotal</span><span>' + formatPrice(precio) + '€</span>');
            [/#if]
            
            $("#cant-original_" + item.id).val(item.quantity);
		});
		
		
		$("#cart-total-price").html('<span>Total</span><span>' + getPrecio(response.lineItems,response.customLineItems) + '€</span>');
		
	} else {
		$(".b2b-cart-items-wrapper").html('<div></div>');
		$(".b2b-cart-items-wrapper").first().html('<div>${i18n['cione-module.templates.myshop.header-component.carritoVacio']}</div>');
		$("#cart-total-price").html('<span>Total</span><span>0,00€</span>');
		$(".product-button").prop("disabled", true);
	}
}

	function actualizarExtraInfo(thisElem, itemId, updatedField) {
		
		$("#loading").show();
		
		[#-- loggin 
		console.log(thisElem.value);
		console.log(itemId);
		console.log(updatedField);
		--]
		
		var filter = formToJSON(itemId);
		var jFilter = JSON.parse(filter);
		
		if(updatedField == "refCliente")
			jFilter['refCliente'] = thisElem.value;
		else if(updatedField == "refTaller")
			jFilter['refTaller'] = thisElem.value;
			
		filter = JSON.stringify(jFilter);
		
		$.ajax({
			url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updateCustomField/" + updatedField,
			type: "POST",
			data: filter,
	        contentType: 'application/json; charset=utf-8',
	        cache: false,
	    	async: false,
	        dataType: "json",
	        error: function(response) { console.log("Error update " + updatedField); },
	        complete : function(response) { $("#loading").hide(); }
		});
	}
	
	function actualizarExtraInfoAudio(thisElem, itemId, updatedField) {
	
		$("input[data-audio]").val(thisElem.value);
		
		$("#loading").show();
		
		[#-- loggin 
		console.log(thisElem.value);
		console.log(itemId);
		console.log(updatedField);
		--]
		
		
		var filter = formToJSON(itemId);
		var jFilter = JSON.parse(filter);
		
		if(updatedField == "refCliente")
			jFilter['refCliente'] = thisElem.value;
		else if(updatedField == "refTaller")
			jFilter['refTaller'] = thisElem.value;
			
		filter = JSON.stringify(jFilter);
		
		$("input[data-audio]").each(function( index ) {
			var arrsplit = $(this).attr("data-audio").split("|");
			if (arrsplit.length == 2 && arrsplit[1] == $("#"+itemId).val()){
				actualizarExtraInfoString(thisElem.value, arrsplit[0], 'refCliente')
			}
		});
		
		$.ajax({
			url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updateCustomField/" + updatedField,
			type: "POST",
			data: filter,
	        contentType: 'application/json; charset=utf-8',
	        cache: false,
	    	async: false,
	        dataType: "json",
	        error: function(response) { console.log("Error update " + updatedField); },
	        complete : function(response) { $("#loading").hide(); }
		});
		
	}

	function actualizarExtraInfoString(str, itemId, updatedField) {
		
		$("#loading").show();
		
		var filter = formToJSON(itemId);
		var jFilter = JSON.parse(filter);
		
		if(updatedField == "refCliente")
			jFilter['refCliente'] = str;
		else if(updatedField == "refTaller")
			jFilter['refTaller'] = str;
			
		filter = JSON.stringify(jFilter);
		
		$.ajax({
			url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updateCustomField/" + updatedField,
			type: "POST",
			data: filter,
	        contentType: 'application/json; charset=utf-8',
	        cache: false,
	    	async: false,
	        dataType: "json",
	        error: function(response) { console.log("Error update " + updatedField); },
	        complete : function(response) { $("#loading").hide(); }
		});
	}

	function actualizarExtraInfoBoolean(that, itemId, updatedField) {
		
		$("#loading").show();
		
		var filter = formToJSON(itemId);
		var jFilter = JSON.parse(filter);
		
		jFilter[updatedField] = $("#" + that.id).is(":checked");
		
		filter = JSON.stringify(jFilter);
		
		$.ajax({
			url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updateCustomField/" + updatedField,
			type: "POST",
			data: filter,
	        contentType: 'application/json; charset=utf-8',
	        cache: false,
	    	async: false,
	        dataType: "json",
	        error: function(response) { console.log("Error update " + updatedField); },
	        complete : function(response) { $("#loading").hide(); }
		});
	}

	function actualizarExtraInfoRefDesPack(info, itemId, updatedField) {
		
		$("#loading").show();
		
		var filter = formToJSON(itemId);
		var jFilter = JSON.parse(filter);
		
		jFilter[updatedField] = info;
			
		filter = JSON.stringify(jFilter);
		
		$.ajax({
			url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updateCustomField/" + updatedField,
			type: "POST",
			data: filter,
	        contentType: 'application/json; charset=utf-8',
	        cache: false,
	    	async: false,
	        dataType: "json",
	        error: function(response) { console.log("Error update " + updatedField); },
	        complete : function(response) { $("#loading").hide(); }
		});
	}

	function actualizarExtraInfoRefDesPackCLI(info, itemId, updatedField) {
		
		$("#loading").show();
		
		var filter = formToJSON(itemId);
		var jFilter = JSON.parse(filter);
		
		jFilter[updatedField] = info;
			
		filter = JSON.stringify(jFilter);
		
		$.ajax({
			url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updateCustomFieldCLI/" + updatedField,
			type: "POST",
			data: filter,
	        contentType: 'application/json; charset=utf-8',
	        cache: false,
	    	async: false,
	        dataType: "json",
	        error: function(response) { console.log("Error update refPack en CLI"); },
	        complete : function(response) { $("#loading").hide(); }
		});
	}

	function actualizarExtraInfoDescPackPromoCLI(info, itemId, updatedField) {
	
		$("#loading").show();
		
		var refTaller = $("#" + itemId).val();
		
		var filter = formToJSON(itemId);
		var jFilter = JSON.parse(filter);
		jFilter[updatedField] = info.value;
		filter = JSON.stringify(jFilter);
		
		$.ajax({
			url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updateCustomFieldCLI/" + updatedField,
			type: "POST",
			data: filter,
	        contentType: 'application/json; charset=utf-8',
	        cache: false,
	    	async: false,
	        dataType: "json",
	        error: function(response) { console.log("Error update refCliente/refTaller/descPack"); },
	        complete : function(response) { $("#loading").hide(); }
		});
		
		if (refTaller !== undefined){
			$('[id^=promo-]').each(function( index ) {
        		if($("#" + $(this).attr("id").substring(6)).val() === refTaller){
        		
        			$("#input-descpack-" + $(this).attr("id").substring(6)).val($("#input-descpack-" + itemId).val());
        			
        			if ($(this).hasClass("ppcli")){
        				actualizarExtraInfoRefDesPackCLI($("#input-descpack-" + itemId).val(), $(this).attr("id").substring(6), 'descPack');
        			}else{
        				actualizarExtraInfoRefDesPack($("#input-descpack-" + itemId).val(), $(this).attr("id").substring(6), 'descPack');
        			}
        		}
    		});
		}
	}

	function actualizarExtraInfoDescPackPromo(info, itemId, updatedField) {
	
		$("#loading").show();
		
		var refTaller = $("#" + itemId).val();
		
		if (refTaller !== undefined){
			$('[id^=promo-]').each(function( index ) {
        		if($("#" + $(this).attr("id").substring(6)).val() === refTaller){
        		
        			$("#input-descpack-" + $(this).attr("id").substring(6)).val($("#input-descpack-" + itemId).val());
        			
        			if ($(this).hasClass("ppcli")){
        				actualizarExtraInfoRefDesPackCLI($("#input-descpack-" + itemId).val(), $(this).attr("id").substring(6), 'descPack');
        			}else{
        				actualizarExtraInfoRefDesPack($("#input-descpack-" + itemId).val(), $(this).attr("id").substring(6), 'descPack');
        			}
        		}
    		});
		}
	}

	function actualizarExtraInfoCLI(thisElem, itemId, updatedField) {
		$("#loading").show();
		
		var filter = formToJSON(itemId);
		var jFilter = JSON.parse(filter);
		
		if((updatedField == "refCliente") || (updatedField == "_REFSOCIO"))
			jFilter['refCliente'] = thisElem.value;
		else if((updatedField == "refTaller")|| (updatedField == "_REFTALLER"))
			jFilter['refTaller'] = thisElem.value;
			
		filter = JSON.stringify(jFilter);
		
		$.ajax({
			url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updateCustomFieldCLI/" + updatedField,
			type: "POST",
			data: filter,
	        contentType: 'application/json; charset=utf-8',
	        cache: false,
	        dataType: "json",
	        error: function(response) { console.log("Error update refCliente/refTaller/packPromo"); },
	        complete : function(response) { $("#loading").hide(); }
		});
	}
	
	function notaSASToJSON(nota) {
	
		var csrf = $("#formCarritoList input[name=csrf]").val();
		var definitionName = $("#formCarritoList input[name=definitionName]").val();
		var connectionName = $("#formCarritoList input[name=connectionName]").val();
		var id = $("#formCarritoList input[name=id]").val();
		var userId = $("#formCarritoList input[name=userId]").val();
		var accessToken = $("#formCarritoList input[name=accessToken]").val();
		var userIdEncodingDisabled = $("#formCarritoList input[name=userIdEncodingDisabled]").val();
		var notaSAS = nota;
		
		return JSON.stringify({
	        "csrf": csrf,
	        "definitionName": definitionName,
	        "connectionName": connectionName,
	        "id": id,
	        "notaSAS": notaSAS,
	        "userId": userId,
	        "accessToken": accessToken,
	        "userIdEncodingDisabled": userIdEncodingDisabled,
	    });
	    
	}
	
	function connectionpass() {
	
		var csrf = $("#formCarritoList input[name=csrf]").val();
		var definitionName = $("#formCarritoList input[name=definitionName]").val();
		var connectionName = $("#formCarritoList input[name=connectionName]").val();
		var id = $("#formCarritoList input[name=id]").val();
		var userId = $("#formCarritoList input[name=userId]").val();
		var accessToken = $("#formCarritoList input[name=accessToken]").val();
		var userIdEncodingDisabled = $("#formCarritoList input[name=userIdEncodingDisabled]").val();
		
		return JSON.stringify({
	        "csrf": csrf,
	        "definitionName": definitionName,
	        "connectionName": connectionName,
	        "id": id,
	        "userId": userId,
	        "accessToken": accessToken,
	        "userIdEncodingDisabled": userIdEncodingDisabled,
	    });
	    
	}
	
	function actualizarNotaSAS(thisElem, id){
		
		$("#loading").show();
		
		var filter = notaSASToJSON(thisElem.value);
		var jFilter = JSON.parse(filter);
		filter = JSON.stringify(jFilter);
		console.log(filter);
		$.ajax({
			url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updateSASNote",
			type: "POST",
			data: filter,
	        contentType: 'application/json; charset=utf-8',
	        cache: false,
	        dataType: "json",
	        error: function(response) { console.log("Error update nota para el SAS"); },
	        complete : function(response) { $("#loading").hide(); }
		});
		
	}
	
	function actualizarRefTaller(thisElem, id) {
		if(thisElem.checked) {
			$("#"+id).prop('disabled', false);
            $("#"+id).css('background-color', '#fff');
			//$(thisElem).prev().prop('disabled', false);
            //$(thisElem).prev().css('background-color', '#fff');
            //$(thisElem).parent().prev().prop('disabled', false);
            //$(thisElem).parent().prev().css('background-color', '#fff');
        }else{
        	$("#"+id).prop('disabled', true);
            $("#"+id).css('background-color', '#d1d1d1');
        	//$(thisElem).prev().prop('disabled', true);
        	//$(thisElem).prev().css('background-color', '#d1d1d1');
        	//$(thisElem).parent().prev().prop('disabled', true);
        	//$(thisElem).parent().prev().css('background-color', '#d1d1d1');
        }
		
	}
	
	function actualizarPackPromo(thisElem, id, isFrame) {
		
		[#-- loading y estilos --]
		$("#loading").show();
		$("#input-descpack-"+id).prop('disabled', false);
        $("#input-descpack-"+id).css('background-color', '#fff');
        
        [#-- variables de funcion --]
		var refTaller = $("#" + id).val();
		var packpromocode = '${model.code}';
		
		
		[#-- hacemos check o "descheck" --]
		if(thisElem.checked) {
			
			[#-- hacemos check sobre una montura --]
			if (isFrame){
			
				
				[#-- hacemos check sobre cualquier otro producto --]
				var refTallerCLI;
				var packpromocodeCLI;
				
				$('[id^=promo-]').each(function( index ) {
					if ($(this).hasClass("ppcli") && refTallerCLI === undefined){
						refTallerCLI = $("#" + $(this).attr("id").substring(6)).val();
						packpromocodeCLI = $("#input-refpack-" + $(this).attr("id")).val();
					}
				});
				
				if(refTallerCLI !== undefined && refTallerCLI !== ""){
					
					if (packpromocodeCLI !== undefined && packpromocodeCLI !== ""){
						packpromocode = packpromocodeCLI;
					}
					
					$("#input-refpack-promo-"+id).val(packpromocode);
					$("#"+id).val(refTallerCLI);
					$("#"+id).prop('disabled', true);
					$("#"+id).css('background-color', '#d1d1d1');
					$("#ref-"+id).prop('checked', true);
					
										
					$('[id^=promo-]').each(function( index ) {
						
						if($("#" + $(this).attr("id").substring(6)).val() === refTallerCLI){
						
	            			$("#input-refpack-" + $(this).attr("id")).val(packpromocode);
	            			$("#input-descpack-" + $(this).attr("id").substring(6)).val("");
	            			$("#" + $(this).attr("id")).prop('checked', true);
	            			$("#input-descpack-" + $(this).attr("id").substring(6)).prop('disabled', false);
	            			$("#input-descpack-" + $(this).attr("id").substring(6)).css('background-color', '#fff');
	            			
		            		if ($(this).hasClass("ppcli")){
		            			var jsonInfo = {refPack: packpromocode, descPack: "", esPack: true};
		            			actualizarExtraInfoAllCLI(jsonInfo, $(this).attr("id").substring(6));
			  					[#-- actualizarExtraInfoRefDesPackCLI(packpromocode, $(this).attr("id").substring(6), 'refPack');
			  					actualizarExtraInfoRefDesPackCLI("", $(this).attr("id").substring(6), 'descPack'); --]
			  				}else{
			  					
			  					var jsonInfo = {refTaller: refTallerCLI, refPack: packpromocode, descPack: "", aTaller: true, esPack: true};
			  					actualizarExtraInfoAll(jsonInfo, id);
			  				
			  					[#-- actualizarExtraInfoString(refTallerCLI, id, 'refTaller');
			  					//actualizarExtraInfoRefDesPack(packpromocode, id, 'refPack');
			  					//actualizarExtraInfoRefDesPack("", id, 'descPack'); --]
			  				}
	            		}
					});
				}
			
			[#-- hacemos check sobre una lente --]
			}else{
				
				[#-- se hace check sobre una lente
				     y por tanto tenemos refTaller --]
				
				[#-- se recorren todos los elementos del carrito --]
				$('[id^=promo-]').each(function( index ) {
            		
            		if($("#" + $(this).attr("id").substring(6)).val() === refTaller){
            		
            			$("#input-refpack-" + $(this).attr("id")).val(packpromocode);
            			$("#input-descpack-" + $(this).attr("id").substring(6)).val("");
            			$("#" + $(this).attr("id")).prop('checked', true);
            			$("#input-descpack-" + $(this).attr("id").substring(6)).prop('disabled', false);
            			$("#input-descpack-" + $(this).attr("id").substring(6)).css('background-color', '#fff');
            			
            			if ($(this).hasClass("ppcli") ){
            				var jsonInfo = {refPack: packpromocode, descPack: "", esPack: true};
		            		actualizarExtraInfoAllCLI(jsonInfo, $(this).attr("id").substring(6));
		            			
	  						[#-- actualizarExtraInfoRefDesPackCLI(packpromocode, $(this).attr("id").substring(6), 'refPack');
	  						actualizarExtraInfoRefDesPackCLI("", $(this).attr("id").substring(6), 'descPack'); --]
		  				}else{
		  					var jsonInfo = {refPack: packpromocode, descPack: "", aTaller: true, esPack: true};
			  				actualizarExtraInfoAll(jsonInfo, $(this).attr("id").substring(6));
			  				
			  				[#-- actualizarExtraInfoRefDesPack(packpromocode, $(this).attr("id").substring(6), 'refPack');
		  					actualizarExtraInfoRefDesPack("", $(this).attr("id").substring(6), 'descPack'); --]
		  				}
            		}
				});
			}
         
        [#-- descheck --] 
        }else{
        
			[#-- se recorren todos los elementos del carrito --]
			$('[id^=promo-]').each(function( index ) {
        		
        		if($("#" + $(this).attr("id").substring(6)).val() === refTaller){
        			$("#input-refpack-" + $(this).attr("id")).val("");
        			$("#input-descpack-" + $(this).attr("id").substring(6)).val("");
        			$("#" + $(this).attr("id")).prop('checked', false);
        			$("#input-descpack-" + $(this).attr("id").substring(6)).prop('disabled', true);
        			$("#input-descpack-" + $(this).attr("id").substring(6)).css('background-color', '#d1d1d1');
        		}
        		
  				if ($(this).hasClass("ppcli") ){
  					var jsonInfo = {refPack: "", descPack: "", esPack: false};
            		actualizarExtraInfoAllCLI(jsonInfo, $(this).attr("id").substring(6));
            			
					[#-- actualizarExtraInfoRefDesPackCLI("", $(this).attr("id").substring(6), 'refPack');
  					actualizarExtraInfoRefDesPackCLI("", $(this).attr("id").substring(6), 'descPack'); --]
  				}else if($(this).hasClass("monturas")){
  					actualizarExtraInfoString("", $(this).attr("id").substring(6), 'refTaller');
  					$("#" + $(this).attr("id").substring(6)).val("");
  					$("#" + $(this).attr("id").substring(6)).prop('disabled', true);
  					$("#" + $(this).attr("id").substring(6)).css('background-color', '#d1d1d1');
  					$("#ref-" + $(this).attr("id").substring(6)).prop('checked', false);
  					
  					var jsonInfo = {refPack: "", descPack: "", aTaller: false, esPack: false};
			  		actualizarExtraInfoAll(jsonInfo, $(this).attr("id").substring(6));
  					
  					[#-- actualizarExtraInfoRefDesPack("", $(this).attr("id").substring(6), 'refPack');
  					actualizarExtraInfoRefDesPack("", $(this).attr("id").substring(6), 'descPack'); --]
  				}
  				
			});
        }
        
        $("#loading").hide();
        
	}
	
	function getPvp(sku){
		
		var sPageURL = window.location.search.substring(1);
		
		var url = "${ctx.contextPath}/.rest/private/lens/price?sku=" + sku;
		
		if(sPageURL != null){
			url = url + "&" + sPageURL;
		}
	
		$("#loading").show();
		
		$.ajax({
            url : url,
            type : "GET",
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            success : function(response) {
            	return parseFloat(response.pvp).toFixed(2);
            },
            error : function(response) {
                console.log("Error al recuperar los datos de compra del ojo izquierdo"); 
                $("#loading").hide();  
            },
            complete : function(response) {
            	return parseFloat('0');
            	$("#loading").hide(); 
            }
        });
	}
	
	function actualizarExtraInfoAll(info, itemId) {
		
		$("#loading").show();
		console.log(info);
		
		var filter = formToJSON(itemId);
		var jFilter = JSON.parse(filter);
		$.extend( jFilter, info );
		filter = JSON.stringify(jFilter);
		
		$.ajax({
			url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updateCustomField/all",
			type: "POST",
			data: filter,
	        contentType: 'application/json; charset=utf-8',
	        cache: false,
	    	async: false,
	        dataType: "json",
	        error: function(response) { console.log("Error update all"); },
	        complete : function(response) { $("#loading").hide(); }
		});
	}
	
	function actualizarExtraInfoAllCLI(info, itemId) {
		
		$("#loading").show();
		
		var filter = formToJSON(itemId);
		var jFilter = JSON.parse(filter);
		$.extend( jFilter, info );
		filter = JSON.stringify(jFilter);
		
		$.ajax({
			url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updateCustomFieldCLI/all",
			type: "POST",
			data: filter,
	        contentType: 'application/json; charset=utf-8',
	        cache: false,
	    	async: false,
	        dataType: "json",
	        error: function(response) { console.log("Error update all"); },
	        complete : function(response) { $("#loading").hide(); }
		});
	}
	
	function loadFile(fileName, refTaller) {
		window.open('${ctx.contextPath}/.rest/private/carrito/v1/audiolabPDF?namefile='+fileName + '&refTaller='+refTaller, '_blank');
	}
	
	
	periodicpurchaselist.onchange = function(){
		
		var id = $("#periodicpurchaselist").val();
		var url = "${ctx.contextPath}/.rest/private/periodicpurchase/add?id=" + id ;
		
		$.ajax({
			url: url,
			type: "POST",
	        contentType: 'application/json; charset=utf-8',
	        cache: false,
	    	async: false,
	        dataType: "json",
            success : function(response) {
            	console.log("Compra periodica actualizada");
            	[#assign linkDetailPeriodicPurchase = cmsfn.link("website", content.detailPeriodicPurchaseLink!)!]
            	var urldetail = "${linkDetailPeriodicPurchase!"#"}";
            	urldetail += "?id=" + id;
				window.location.replace(urldetail);
            },
	        error: function(response) { console.log("Error al mergear con la compra rapida."); },
	        complete : function(response) { $("#loading").hide(); }
		});
	};
	
</script>