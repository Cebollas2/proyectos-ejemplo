[#include "../../../includes/macros/cione-utils-impersonate.ftl"]
[#if !cmsfn.editMode]

	[#assign presupuesto = model.getPresupuesto()!]
	[#assign employee = model.getData()!]
	[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]
	[#assign defaultImage = resourcesURL + "/img/myshop/common/oops.jpg"]
	[#assign defaultImageCLI = resourcesURL + "/img/myshop/common/imagennodisponible_lente_graduada.jpg"]
	[#assign internalLinkFeedback = cmsfn.link("website", content.internalLinkFeedback!)!]
	[#assign mapCustomFieldsPresupuesto = presupuesto.carrito.getCustom().getFields().values()]
	
	<main class="b2b-main" role="main">
	
	    <h2 class="b2b-h2">${i18n['cione-module.templates.components.listado-shoppinglist-component.title']} 
	    	[#if presupuesto.carrito.getCustom()??]
	    		[#if mapCustomFieldsPresupuesto["idPresupuesto"]?? && mapCustomFieldsPresupuesto["idPresupuesto"]?has_content]
	    			${mapCustomFieldsPresupuesto["idPresupuesto"]?trim!""}
	    		[/#if]
	    	[/#if]
	    </h2>
	
	    <div class="b2b-cart-container">
	        <div class="b2b-cart-items-wrapper">
	           
	            [#-- BEGIN: HEADER DEL PRESUPUESTO + FECHA --]
	            <div class="b2b-cart-item b2b-cart-item-presupuesto">
	                <div class="b2b-cart-item-row b2b-cart-item-header">
	                    <div class="b2b-cart-item-col">
	                        <div class="product-block-title">
	                            <span>${i18n['cione-module.templates.components.listado-shoppinglist-component.name']}</span>
	                            <span>${model.getData().getNombreComercial()!}</span>
	                        </div>
	                        <div class="product-block-title">
	                            <span>${i18n['cione-module.templates.components.listado-shoppinglist-component.address']}</span>
	                            <span>${model.getData().getDireccion()!}</span>
	                        </div>
	                    </div>
	
	                    <div class="b2b-cart-item-col">
	                        <div class="product-reference  ">
	                            <label>${i18n['cione-module.templates.components.listado-shoppinglist-component.current']} <span style="color: red;">*</span></label>
	                            <input type="date" required="required" id="effectivedate" autocomplete="off" value="[#if presupuesto.carrito.getCustom()??][#if mapCustomFieldsPresupuesto["vigencia"]?? && mapCustomFieldsPresupuesto["vigencia"]?has_content]${mapCustomFieldsPresupuesto["vigencia"]?trim!""}[/#if][/#if]">
	                        </div>
	                    </div>
	                </div>
	            </div>
	            [#-- END: HEADER DEL PRESUPUESTO + FECHA --]
	
	            [#-- BEGIN: LISTADO DE ELEMENTOS DEL PRESUPUESTO --]
	            [#list presupuesto.carrito.lineItems as item]
	            

	            <div class="b2b-cart-item b2b-cart-item-presupuesto">
	                <div class="b2b-cart-item-row">
	                	
	                	[#-- IMAGEN --]
	                    <div class="b2b-cart-item-col b2b-cart-item-img">
	                        <img [#if item.getVariant().getImages()?has_content]
				            		src="${item.getVariant().getImages()[0].getUrl()}"
				            	 [#else]
				            		src="${defaultImage}"
				            	 [/#if] onerror="this.onerror=null; this.src='${defaultImage!""}'" alt="">
	                    </div>
	                    
	                    [#-- DATOS --]
	                    <div class="b2b-cart-item-col">
							
							[#assign infoVariant = model.getVariantInfoPromociones(item.getVariant())!]
							[#assign familiaProducto = model.getFamilia(item)!]
							[#assign locale = model.getLocale()!]
							
							[#if hasAttributte(item.getVariant().getAttributes(), "nombreArticulo")]
		                        <div class="product-block-title">
		                            <span class="just-title">${findAttributte(item.getVariant().getAttributes(), "nombreArticulo")!""}</span>
		                        </div>
	                        [#else]
		                        <div class="product-block-title">
		                            <span class="just-title">${item.getName().get(locale)!""}</span>
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
				            
				            [#assign mapCustomFields = item.getCustom().getFields().values()]
				            
				            [#if mapCustomFields["colorAudifono"]?? && mapCustomFields["colorAudifono"]?has_content]
				            	<div class="product-block-title">
				                    <span>${i18n.get('cione-module.templates.myshop.header-component.color')}</span>
				                    <span>${mapCustomFields["colorAudifono"]}</span>
				                </div>
				            [/#if]
	                        
	                        [#--  
	                        [#if item.getQuantity()?has_content]
				            	<div class="product-block-title">
				                    <span>${i18n.get('cione-module.templates.components.detalle-producto-component.cantidad')}</span>
				                    <span>${item.getQuantity()}</span>
				                </div>
				            [/#if]
				            --]
				            
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
	
	                    </div>
						
						[#assign pvp = "0,00"]
						[#if mapCustomFields["importeVenta"]?? && mapCustomFields["importeVenta"]?has_content]
							[#assign pvp = mapCustomFields["importeVenta"]!"0,00"]
						[#else]
							[#if hasAttributte(item.getVariant().getAttributes(), "pvpRecomendado")]
								[#assign pvpRecomendado = findAttributte(item.getVariant().getAttributes(), "pvpRecomendado").getCentAmount() / 100.0]
								[#assign pvp = (item.getQuantity() * pvpRecomendado)?string["0.00"]!"0,00"]
							[/#if]
						[/#if]
						
						[#if hasAttributte(item.getVariant().getAttributes(), "pvpRecomendado")]
	                    <div class="b2b-cart-item-col align-self-end">
	                    
	                    	[#-- BEGIN: CANTIDAD --]
	                    	<div class="product-amount"  style="justify-content: flex-end;">
				                <div class="product-amount-button-wrapper">
				                	
				                    <button class="product-amount-button product-amount-button-minus" type="button" 
				                    [#if familiaProducto != "audifonos" && familiaProducto != "complementosaudioservicios"]onclick="updateItemQuantity('${item.getId()}', -1, '${item.getVariant().getSku()}'); return false"[/#if] 
				                    [#if familiaProducto == "audifonos" || familiaProducto == "complementosaudioservicios"]style="cursor: not-allowed;background-color: #d1d1d1;"[/#if]>
				                        -
				                    </button>
				                    
				                    <input [#if familiaProducto == "audifonos" || familiaProducto == "complementosaudioservicios"]disabled[/#if] type="number" name="cant_${item.getId()}" class="product-amount-input" min="1" max="999999"
				                    	value="${item.getQuantity()}" onchange="updateItemQuantity('${item.getId()}', 0, '${item.getVariant().getSku()}'); return false" autocomplete="off">
				                    
				                    <button class="product-amount-button product-amount-button-plus" type="button" 
				                    [#if familiaProducto != "audifonos" && familiaProducto != "complementosaudioservicios"]onclick="updateItemQuantity('${item.getId()}', 1, '${item.getVariant().getSku()}'); return false"[/#if]
				                    [#if familiaProducto == "audifonos" || familiaProducto == "complementosaudioservicios"]style="cursor: not-allowed;background-color: #d1d1d1;"[/#if]>
				                        +
				                    </button>
				                    
				                    [#if familiaProducto != "contactologia" && familiaProducto != "audifonos"]
				                    	[#if hasAttributte(item.getVariant().getAttributes(), "aliasEkon")]
				                    		<input type="hidden" name="stock_${item.getId()}" value="${model.getStockbyAliasEkon(findAttributte(item.getVariant().getAttributes(), "aliasEkon")!)!}">
				                    	[/#if]
				                    [#elseif familiaProducto == "contactologia"]
				                    	<input type="hidden" name="stock_${item.getId()}" value="${model.getStock(item)!}">
				                    [#else]
				                    	<input type="hidden" name="stock_${item.getId()}" value="0">
				                    [/#if]
				                    <input type="hidden" id="cant-original_${item.getId()}" value="${item.getQuantity()}">
				                
				                </div>
				            </div>
	                    	[#-- END: CANTIDAD --]
				            
			                [#-- BEGIN: PVP --]     
	                        <div class="product-reference  mb-0 pt-2">
	                            <label>${i18n['cione-module.templates.components.listado-shoppinglist-component.pvplb']} <span style="color: red;">*</span></label>
	                            <input id="total_${item.getId()}" class="pvpitem" required="required" value="${pvp?replace(".", ",")!"0,00"}"
	                            	   onchange="actualizarExtraInfo(this, '${item.getId()}', 'importeVenta'); return false" autocomplete="off">
	                        </div>
	                        [#-- END: PVP --]
	                        
	                    </div>
	                    [/#if]
	                    
	                </div>
	            </div>
	            [/#list]
	            
	            [#-- ELEMENTO INDIVIDUAL PARA LOS CUSTOM LINE ITEMS --]
	            [#list presupuesto.carrito.getCustomLineItems() as itemc]
	            [#assign mapCustomFieldsC = itemc.getCustom().getFields().values()]
	            <div class="b2b-cart-item b2b-cart-item-presupuesto">
	
	                <div class="b2b-cart-item-row">
	                
	                	[#-- IMAGEN --]
	                    <div class="b2b-cart-item-col b2b-cart-item-img">
	            			<img class="b2b-floating-cart-image" src="${defaultImageCLI}" onerror="this.onerror=null; this.src='${defaultImageCLI}'" alt="" />
	                    </div>
	                    
	                    [#-- DATOS --]
	                    <div class="b2b-cart-item-col">
	    		
				    		[#-- nombre --]
					    	[#if itemc.getName()?has_content]
						    	<div class="product-block-title">
				                	<span class="just-title">${itemc.getName().get("es")!""}</span>
				            	</div>
				            [/#if]
				            
				            [#-- cilindro --]
				            [#if mapCustomFieldsC["CYL"]?? && mapCustomFieldsC["CYL"]?has_content]
					            <div class="product-block-title">
					            	 <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.cilindro']}</span>
					            	 <span>${mapCustomFieldsC["CYL"]}</span>
					            </div>
					        [/#if]
					        
					        [#if mapCustomFieldsC["CYL_L"]?? && mapCustomFieldsC["CYL_L"]?has_content]
					            <div class="product-block-title">
					            	 <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.cilindro']}</span>
					            	 <span>${mapCustomFieldsC["CYL_L"]}</span>
					            </div>
					        [/#if]
					        
					        [#if mapCustomFieldsC["CYL_R"]?? && mapCustomFieldsC["CYL_R"]?has_content]
					            <div class="product-block-title">
					            	 <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.cilindro']}</span>
					            	 <span>${mapCustomFieldsC["CYL_R"]}</span>
					            </div>
					        [/#if]
				            
				            [#-- esfera --]
				            [#if mapCustomFieldsC["SPH"]?? && mapCustomFieldsC["SPH"]?has_content]
					            <div class="product-block-title">
				                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.esfera']}</span>
				                    <span>${mapCustomFieldsC["SPH"]}</span>
					            </div>
				            [/#if]
				            
				            [#if mapCustomFieldsC["SPH_L"]?? && mapCustomFieldsC["SPH_L"]?has_content]
					            <div class="product-block-title">
				                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.esfera']}</span>
				                    <span>${mapCustomFieldsC["SPH_L"]}</span>
					            </div>
				            [/#if]
				            
				           [#if mapCustomFieldsC["SPH_R"]?? && mapCustomFieldsC["SPH_R"]?has_content]
					            <div class="product-block-title">
				                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.esfera']}</span>
				                    <span>${mapCustomFieldsC["SPH_R"]}</span>
					            </div>
				            [/#if]
				            
				            [#-- diametro --]
				          	[#if mapCustomFieldsC["CRIB"]?? && mapCustomFieldsC["CRIB"]?has_content]
					            <div class="product-block-title">
				                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.diametro']}</span>
				                    <span>${mapCustomFieldsC["CRIB"]}</span>
					            </div>
				            [/#if]
				            
				            [#if mapCustomFieldsC["CRIB_L"]?? && mapCustomFieldsC["CRIB_L"]?has_content]
					            <div class="product-block-title">
				                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.diametro']}</span>
				                    <span>${mapCustomFieldsC["CRIB_L"]}</span>
					            </div>
				            [/#if]
				            
				            [#if mapCustomFieldsC["CRIB_R"]?? && mapCustomFieldsC["CRIB_R"]?has_content]
					            <div class="product-block-title">
				                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.diametro']}</span>
				                    <span>${mapCustomFieldsC["CRIB_R"]}</span>
					            </div>
				            [/#if]
				            
				            [#-- eje --]
				            [#if mapCustomFieldsC["AX_L"]?? && mapCustomFieldsC["AX_L"]?has_content]
					            <div class="product-block-title">
				                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.eje']}</span>
				                    <span>${mapCustomFieldsC["AX_L"]}</span>
					            </div>
				            [/#if]
				            
				            [#if mapCustomFieldsC["AX_R"]?? && mapCustomFieldsC["AX_R"]?has_content]
					            <div class="product-block-title">
				                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.eje']}</span>
				                    <span>${mapCustomFieldsC["AX_R"]}</span>
					            </div>
				            [/#if]
				            
				            [#-- adicion --]
				            [#if mapCustomFieldsC["ADD_L"]?? && mapCustomFieldsC["ADD_L"]?has_content]
					            <div class="product-block-title">
				                    <span>${i18n['cione-module.templates.components.detalle-producto-component.adicion']}</span>
				                    <span>${mapCustomFieldsC["ADD_L"]}</span>
					            </div>
				            [/#if]
				            
				            [#if mapCustomFieldsC["ADD_R"]?? && mapCustomFieldsC["ADD_R"]?has_content]
					            <div class="product-block-title">
				                    <span>${i18n['cione-module.templates.components.detalle-producto-component.adicion']}</span>
				                    <span>${mapCustomFieldsC["ADD_R"]}</span>
					            </div>
				            [/#if]
				            
				            [#-- color --]
				            [#if mapCustomFieldsC["COLR_L"]?? && mapCustomFieldsC["COLR_L"]?has_content]
					            <div class="product-block-title">
				                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.color']}</span>
				                    <span id="color-${itemc.getId()}">${mapCustomFieldsC["COLR_L"]}</span>
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
				                    <span id="color-${itemc.getId()}">${mapCustomFieldsC["COLR_R"]}</span>
				                    <script>
				                    	$(document).ready(function(){
				                    		var colorCustom = '${mapCustomFieldsC["COLR_R"]}';
				                    		colorCustom = colorCustom.replace(/\+/g, '%20');
				                    		var decode = decodeURIComponent(colorCustom);
				                    		$('#color-${itemc.getId()}').text(decode);
										});
				                    	
				                    </script>
					            </div>
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
					        [#elseif mapCustomFieldsC["LNAM_L"]?? && mapCustomFieldsC["LNAM_L"]?has_content]
					        	<div class="product-block-title">
				                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojo']}</span>
				                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojoi']}</span>
					            </div>
					        [#elseif mapCustomFieldsC["LNAM_R"]?? && mapCustomFieldsC["LNAM_R"]?has_content]
					        	<div class="product-block-title">
				                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojo']}</span>
				                    <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.ojod']}</span>
					            </div>
				            [/#if]
	
	                    </div>
						
						[#assign pvp = "0,00"]
						[#if mapCustomFieldsC["importeVenta"]?has_content]
							[#assign pvp = mapCustomFieldsC["importeVenta"]!"0,00"]
						[#else]
							[#if mapCustomFieldsC["LMATTYPE"]?? && mapCustomFieldsC["LMATTYPE"]?has_content]
			            		[#-- SON LENTES DE STOCK --]
						        [#assign skuCustom = mapCustomFieldsC["SKU"]!]
						        [#assign pvp = model.getPvp(skuCustom)!"0,00"]  
					        [#elseif mapCustomFieldsC["PVP_R"]?? && mapCustomFieldsC["PVP_R"]?has_content]
					        	[#-- SON LENTES DE CIONELAB --]
					        	[#assign pvp = mapCustomFieldsC["PVP_R"]!"0,00"] 
					        [#elseif mapCustomFieldsC["PVP_L"]?? && mapCustomFieldsC["PVP_L"]?has_content]
					        	[#-- SON LENTES DE CIONELAB --]
					        	[#assign pvp = mapCustomFieldsC["PVP_L"]!"0,00"]
				            [#elseif mapCustomFieldsC["PVP"]?? && mapCustomFieldsC["PVP"]?has_content]
				            	[#-- SON TRABAJOS DE CIONELAB --]
				            	[#assign pvpcustom = mapCustomFieldsC["PVP"].getCentAmount() / 100.0]
			                    [#assign pvp = pvpcustom?string["0.00"]!"0,00"]
				            [/#if]
						[/#if]
			            
			            <div class="b2b-cart-item-col align-self-end">
			            
			            	[#-- BEGIN: CANTIDAD --]
			            	<div class="product-amount" style="justify-content: flex-end;">
				                <div class="product-amount-button-wrapper">
				                	[#if mapCustomFieldsC["SKU"]?? && mapCustomFieldsC["SKU"]?has_content]
				                	
				                		[#if !(mapCustomFieldsC["LNAM_R"]?? && mapCustomFieldsC["LNAM_R"]?has_content) &&
				                			 !(mapCustomFieldsC["LNAM_L"]?? && mapCustomFieldsC["LNAM_L"]?has_content) &&
				                			 !(mapCustomFieldsC["TYPJOB"]?? && mapCustomFieldsC["TYPJOB"]?has_content)] 
					                    <button class="product-amount-button product-amount-button-minus" type="button" 
					                    	onclick="updateCLIQuantity('${itemc.getId()}', -1, '${mapCustomFieldsC["SKU"]}'); return false">
					                        -
					                    </button>
					                    
					                    <input type="number" name="cant_${itemc.getId()}" class="product-amount-input" min="1" max="999999"
					                    	value="${itemc.getQuantity()}" onchange="updateCLIQuantity('${itemc.getId()}', 0, '${mapCustomFieldsC["SKU"]}'); return false" autocomplete="off">
					                    
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
			            	[#-- END: CANTIDAD --]
			            	
			            	[#-- BEGIN: TOTAL --]
	                        <div class="product-reference  mb-0 pt-2">
	                            <label>${i18n['cione-module.templates.components.listado-shoppinglist-component.pvp']} <span style="color: red;">*</span></label>
	                            <input id="total_${itemc.getId()}" class="pvpitem" required="required" autocomplete="off" value="${pvp?replace(".", ",")!"0,00"}" 
	                            	   onchange="actualizarExtraInfoCLI(this, '${itemc.getId()}', 'importeVenta'); return false">
	                        </div>
	                        [#-- END: TOTAL --]
	                    </div>
	                    
	                </div>
	            </div>
	            [/#list]
	          	[#-- ELEMENTO INDIVIDUAL PARA LOS CUSTOM LINE ITEMS --]
	            
	            [#-- END: LISTADO DE ELEMENTOS DEL PRESUPUESTO --]
	            
	            [#-- BEGIN: FOOTER DEL PRESUPUESTO: MAIL + CLIENTE + TOTAL  --]
	            <div class="b2b-cart-item b2b-cart-item-presupuesto no-border-bt">
				    <div class="b2b-cart-item-row b2b-cart-item-footer">
				    
				        <div class="product-reference  mb-0 pt-4">
				            <label>${i18n['cione-module.templates.components.listado-shoppinglist-component.customermail']}</label>
				            
				            <input type="text" id="customermail" autocomplete="off" 
				            	value="[#if presupuesto.carrito.getCustom()??][#if mapCustomFieldsPresupuesto["mailCliente"]?? && mapCustomFieldsPresupuesto["mailCliente"]?has_content]${mapCustomFieldsPresupuesto["mailCliente"]?trim!""}[/#if][/#if]">
				        </div>
				   
				        <div class="product-reference mb-0 pt-4">
				            <label>${i18n['cione-module.templates.components.listado-shoppinglist-component.customer']} <span style="color: red;">*</span></label>
				            <input type="text" required="required" id="customernumber" autocomplete="off" 
				            	value="[#if presupuesto.carrito.getCustom()??][#if mapCustomFieldsPresupuesto["numCliente"]?? && mapCustomFieldsPresupuesto["numCliente"]?has_content]${mapCustomFieldsPresupuesto["numCliente"]?trim!""}[/#if][/#if]">
				        </div>
				   
				        <div class="product-reference mb-0 pt-4 pb-4">
				            <label>${i18n['cione-module.templates.components.listado-shoppinglist-component.total']} <span style="color: red;">*</span></label>
				            <input required="required" id="totalbudget" autocomplete="off" 
				            	value="[#if presupuesto.carrito.getCustom()??][#if mapCustomFieldsPresupuesto["importeTotal"]??][#if mapCustomFieldsPresupuesto["importeTotal"]?has_content]${mapCustomFieldsPresupuesto["importeTotal"]?replace(".", ",")?trim!""}[/#if][/#if][/#if]">
				        </div>
				        
				    </div>
				</div>
				[#-- END: FOOTER DEL PRESUPUESTO: MAIL + CLIENTE + TOTAL  --]
	
	        </div>
	
	        [#-- BEGIN: BOTONES GUARDAR + GENERAR PDF + VOLVER --]
            <div class="b2b-cart-total-wrapper">
                <div class="b2b-card-total-wrapper  b2b-card-total-wrapper-detalle sticky">
                    <div class="b2b-cart-back-button">

                        <div class="b2b-button-wrapper">
                            <button class="b2b-button b2b-button-filter" type="button" onclick="saveBudget('back'); return false">
                            	${i18n['cione-module.templates.components.listado-shoppinglist-component.save']}
	                        </button>
                        </div>

                        <div class="b2b-button-wrapper">
                            <button class="b2b-button b2b-button-filter mt-3" type="button" onclick="saveBudgetPDF();">
                            	${i18n['cione-module.templates.components.listado-shoppinglist-component.savepdf']}
                            </button>
                            <span id="msgerror" style="color: red;display: none;">ERROR: Complete los campos obligatorios</span>
                        </div>

                    </div>
                    
                    [#-- BEGIN: MODAL BORRAR
					[#if content.internalLink?has_content && content.internalLink??]
						[#assign link = cmsfn.link("website", content.internalLink!)!]
	                    <div class="b2b-cart-back-button pt-3 pb-3">
	                        <div class="b2b-button-wrapper">
	                            <button class="b2b-button b2b-button-filter" type="button" onclick="location.href='${link!""}';">
	                            	${i18n['cione-module.templates.components.listado-shoppinglist-component.back']}
	                            </button>
	                        </div>
	                    </div>
					[/#if] --]
					
					<div class="b2b-cart-back-button pt-3 pb-3">
                        <div class="b2b-button-wrapper">
                            <button class="b2b-button b2b-button-filter" type="button" onclick='returnBudget()'>
                            	${i18n['cione-module.templates.components.listado-shoppinglist-component.back']}
                            </button>
                        </div>
                    </div>

                </div>
            </div>
	        [#-- END: BOTONES GUARDAR + GENERAR PDF + VOLVER --]
	
	    </div>
	    
	    [#-- BEGIN: MODAL BORRAR --]
	    [#assign closeimg = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/icons/close-thin.svg"]
	    <div id="modal-remove" class="modal-purchase">
		    <div class="modal-purchase-box">
		    
		        <div class="modal-purchase-header">
		            <p>${i18n['cione-module.templates.myshop.shoppinglist.modal.save.title']}</p>
		            <div class="modal-purchase-close">
		                <img class="modal-purchase-close-img" src="${closeimg!""}" onclick='closeModal("modal-remove")' 
		                	alt="${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}" >
		            </div>
		        </div>
		        
		        <div class="modal-purchase-info">
		            <div>
		                <p style="padding-bottom: 10px;">${i18n['cione-module.templates.myshop.shoppinglist.modal.save.body']}</p>
		                <p style="padding-bottom: 10px;">${i18n['cione-module.templates.myshop.shoppinglist.modal.save.body2']}</p>
		                <p>${i18n['cione-module.templates.myshop.shoppinglist.modal.save.body3']}</p>
		            </div>
		        </div>
		        
		        <div class="modal-purchase-footer">
		            <button class="modal-purchase-button modal-purchase-button--transparent" type="button" onclick='deleteBudget()'>
		                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.delete']}
		            </button>
		            <button class="modal-purchase-button" type="button" onclick='closeModal("modal-remove")'>
		                ${i18n['cione-module.templates.myshop.shoppinglist.modal.continue.ok']}
		            </button>
		        </div>
		    </div>
		</div>
	    [#-- END: MODAL BORRAR --]
	
	</main>
	


<script type="text/javascript">

	$('#generatepdf').on('click', function () {
	    $.ajax({
	        url: '${ctx.contextPath}/.rest/private/shoppinglist/pdfbudget?id=${model.id!}',
	        method: 'GET',
	        xhrFields: {
	            responseType: 'blob'
	        },
	        success: function (data) {
	            var a = document.createElement('a');
	            var url = window.URL.createObjectURL(data);
	            a.href = url;
	            a.download = '${model.id!}.pdf';
	            document.body.append(a);
	            a.click();
	            a.remove();
	            window.URL.revokeObjectURL(url);
	        }
	    });
	});

	$( document ).ready(function() {
	
		[#-- set 30 dias por defecto --]
		[#if presupuesto.carrito.getCustom()??]
			[#if !mapCustomFieldsPresupuesto["vigencia"]?? && !mapCustomFieldsPresupuesto["vigencia"]?has_content]
        		setInitDate();
        	[/#if]
		[/#if]
		
	});

	function setInitDate(){
	
		var now = new Date();
		now.setDate(now.getDate() + 30);
		var day = ("0" + now.getDate()).slice(-2);
		var month = ("0" + (now.getMonth() + 1)).slice(-2);
		var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
		$('#effectivedate').val(today);	
	
	}

	function saveBudgetPDF(){
		
		if (validateForm()){
			saveBudget();
			window.open('${ctx.contextPath}/.rest/private/shoppinglist/pdfbudget?id=${model.id!}', '_blank');
		}
	
	}
	
	function saveBudget(control){
	
		if (validateForm()){
		
			$("#loading").show();
			
			var url = "${ctx.contextPath}/.rest/private/shoppinglist/update";
			
			var indexed_array = {};
			indexed_array["id"] = '${presupuesto.carrito.id}';
			indexed_array["total"] =  $.trim($("#totalbudget").val());
			indexed_array["mailconsumer"] = $.trim($("#customermail").val());
			indexed_array["effectivedate"] = $.trim($("#effectivedate").val());
			indexed_array["customer"] = $.trim($("#customernumber").val());
			
			$.ajax({
				url : url,
				type : "POST",
				cache : false,
	            dataType : "json",
				async: false,
				data : JSON.stringify(indexed_array),
	            contentType : 'application/json; charset=utf-8',
				success : function(response) {
					console.log("Presupuesto actualizado");
					if ((control !== undefined) && (control == 'back')) {
						window.location.replace("${internalLinkFeedback!"#"}");
					}
				},
				error : function(response) {
					console.log("Error al actualizar presupuesto");
					console.log(response);
					$("#loading").hide();
					if (response.status == 403) {
						var msg = "No tiene permisos para actualizar el presupuesto";
					} else {
						var msg = "Ha ocurrido un error al generar el presupuesto, contacte con el administrador";
					}
					
					setMsgValid(msg,false);
				}
			});
			
			$("#loading").hide();
		
		}
	}
	
	function setFieldValid(field,isValid){
		
		if(!isValid){
			field.css("color", "red");
	        field.css("border", "1px solid red"); 
		}else{
	    	field.css("color", "black");
	        field.css("border", "solid 1px #c3c3c3"); 
		}
	}
	
	function setMsgValid(msg,isValid){
		
		if(!isValid){
	        $('#msgerror').css("display","flex");
        	$('#msgerror').html('');
        	$('#msgerror').append(msg);
		}else{
			$('#msgerror').css("display","none");
		}
	}
	
	function validateForm(){
		
		var res = true;
		var msg = "";
		
		[#-- start: validacion fecha --]
		var fecha = $("#effectivedate").val();
		
		if(fecha.length == 0){
	        res = false;
	        setFieldValid($("#effectivedate"),false);
	        msg = "ERROR: Complete los campos obligatorios";
	    }else{
	    	if (!validator.isDate(fecha)){
	    		res = false;
	    		setFieldValid($("#effectivedate"),false); 
	    		msg = "ERROR: fecha introducida es incorrecta";
	    	}else{
	    		setFieldValid($("#effectivedate"),true); 
	    	}
	    }
	    [#-- end: validacion fecha --]
	    
		[#-- start: validacion cliente --]
		var cliente = $("#customernumber").val();
		
		if(cliente.length == 0){
			res = false;
        	setFieldValid($("#customernumber"),false);
        	msg = "ERROR: Complete los campos obligatorios";
	    }else{
	    	setFieldValid($("#customernumber"),true); 
	    }
	    [#-- end: validacion cliente --]
		
		[#-- start: validacion total --]
		var total = $("#totalbudget").val();
		
		if(total.length == 0 && res){
	        res = false;
	        setFieldValid($("#totalbudget"),false);
	        msg = "ERROR: Complete los campos obligatorios";
	    }else{
	    	setFieldValid($("#totalbudget"),true);
	    }
	    [#-- end: validacion total --]
		
	    
	    [#-- star: validacion de precios de items --]
	    $(".pvpitem").each(function(index) {
	    	var item = $(this).val();
	    	if(item.length == 0){
	        	setFieldValid($(this),false);
	        	res = false;
	        	msg = "ERROR: Complete los campos obligatorios";
	    	}else{
	    		setFieldValid($(this),true);
	    	}
	    });
	    [#-- end: validacion de precios de items --]
	    
	    
	    [#-- start: validacion mail --]
		var mail = $("#customermail").val();
		
		if(mail.length > 0){
			if(!validator.isEmail(mail)){
		        res = false;
		        setFieldValid($("#customermail"),false);
		        if (msg.length == 0){
		    		msg = "ERROR: mail introducido es incorrecto";
		    	}else{
		    		msg += "\r\nERROR: mail introducido es incorrecto";
		    	}
			}else{
		    	setFieldValid($("#customermail"),true);
		    }
	    }else{
	    	setFieldValid($("#customermail"),true);
	    }
	    [#-- end: validacion mail --]
	    
	    setMsgValid(msg,res);
	    return res;
	
	}
	
	function validateFormVolver(){
		//validar sin haber guardado antes
		//comprueba que los campos ya almacenados sean correctos
		
		var fecha = "";
		[#if presupuesto.carrito.getCustom()??]
			[#if mapCustomFieldsPresupuesto["vigencia"]?? && mapCustomFieldsPresupuesto["vigencia"]?has_content]
				var fecha = '${mapCustomFieldsPresupuesto["vigencia"]?trim!""}';
			[/#if]
		[/#if]
		
		var res = true;
		var msg = "";
		
		[#-- start: validacion fecha --]
		
		if(fecha.length == 0){
	        res = false;
	    }else{
	    	if (!validator.isDate(fecha)){
	    		res = false;
	    		setFieldValid($("#effectivedate"),false); 
	    		msg = "ERROR: fecha introducida es incorrecta";
	    	}else{
	    		setFieldValid($("#effectivedate"),true); 
	    	}
	    }
	    [#-- end: validacion fecha --]
	    
		[#-- start: validacion cliente --]
		var cliente = "";
		[#if presupuesto.carrito.getCustom()??]
			[#if mapCustomFieldsPresupuesto["numCliente"]?? && mapCustomFieldsPresupuesto["numCliente"]?has_content]
				var cliente = '${mapCustomFieldsPresupuesto["numCliente"]?trim!""}';
			[/#if]
		[/#if]
		
		if(cliente.length == 0){
			res = false;
	    }else{
	    	setFieldValid($("#customernumber"),true); 
	    }
	    [#-- end: validacion cliente --]
	    
	    setMsgValid(msg,res);
	    return res;
	
	}
	
	function actualizarTotal(thisElem) {
	
		var total = 0.0;
		var control=true;
		
		$(".pvpitem").each(function(index) {
		
			if (control==true){
				total += parseFloat($(this).val().replace(/,/g, '.'));
			}else{
				control=false;
				total = '';
			}
		});
		
		if ($.isNumeric(total)){
			$("#totalbudget").val(total.toFixed(2).toString().replaceAll('.', ','));
		}else{
			$("#totalbudget").val('');
		}
		
	}
	
	function actualizarExtraInfo(thisElem, itemId, updatedField) {
		
		$("#loading").show();
		
		var filter = formToJSON(itemId);
		var jFilter = JSON.parse(filter);
		
		jFilter["id"] = '${presupuesto.carrito.id}';
		var impv = thisElem.value;
		impv = impv.replace(",", ".");
		jFilter['importeVenta'] = impv;
			
		filter = JSON.stringify(jFilter);
		
		$.ajax({
			url: "${ctx.contextPath}/.rest/private/shoppinglist/updateCustomField/" + updatedField,
			type: "POST",
			data: filter,
	        contentType: 'application/json; charset=utf-8',
	        cache: false,
	    	async: false,
	        dataType: "json",
	        error: function(response) { console.log("Error update PVP en LineItems: " + updatedField); },
	        complete : function(response) { actualizarTotal(this); saveBudget(); $("#loading").hide(); }
		});
		
		$("#loading").hide();
	}	
	
	function actualizarExtraInfoByVal(val, itemId, updatedField) {
		
		$("#loading").show();
		
		var filter = formToJSON(itemId);
		var jFilter = JSON.parse(filter);
		
		jFilter["id"] = '${presupuesto.carrito.id}';
		var impv = val;
		impv = impv.replace(",", ".");
		jFilter['importeVenta'] = impv;
			
		filter = JSON.stringify(jFilter);
		
		$.ajax({
			url: "${ctx.contextPath}/.rest/private/shoppinglist/updateCustomField/" + updatedField,
			type: "POST",
			data: filter,
	        contentType: 'application/json; charset=utf-8',
	        cache: false,
	    	async: false,
	        dataType: "json",
	        error: function(response) { console.log("Error update PVP en LineItems: " + updatedField); },
	        complete : function(response) { actualizarTotal(this); saveBudget(); $("#loading").hide(); }
		});
		
		$("#loading").hide();
	}
	
	function actualizarExtraInfoCLI(thisElem, itemId, updatedField) {
		
		$("#loading").show();
		
		var filter = formToJSON(itemId);
		var jFilter = JSON.parse(filter);
		
		jFilter["id"] = '${presupuesto.carrito.id}';
		var impv = thisElem.value;
		impv = impv.replace(",", ".");
		jFilter['importeVenta'] = impv;
			
		filter = JSON.stringify(jFilter);
		
		$.ajax({
			url: "${ctx.contextPath}/.rest/private/shoppinglist/updateCustomFieldCLI/" + updatedField,
			type: "POST",
			data: filter,
	        contentType: 'application/json; charset=utf-8',
	        cache: false,
	    	async: false,
	        dataType: "json",
	        error: function(response) { console.log("Error update PVP en CustomLineItems: " + updatedField); },
	        complete : function(response) { actualizarTotal(this); saveBudget(); $("#loading").hide(); }
		});
		
		$("#loading").hide();
	}
	
	function actualizarExtraInfoCLIByVal(val, itemId, updatedField) {
		
		$("#loading").show();
		
		var filter = formToJSON(itemId);
		var jFilter = JSON.parse(filter);
		
		jFilter["id"] = '${presupuesto.carrito.id}';
		var impv = val;
		impv = impv.replace(",", ".");
		jFilter['importeVenta'] = impv;
			
		filter = JSON.stringify(jFilter);
		
		$.ajax({
			url: "${ctx.contextPath}/.rest/private/shoppinglist/updateCustomFieldCLI/" + updatedField,
			type: "POST",
			data: filter,
	        contentType: 'application/json; charset=utf-8',
	        cache: false,
	    	async: false,
	        dataType: "json",
	        error: function(response) { console.log("Error update PVP en CustomLineItems: " + updatedField); },
	        complete : function(response) { actualizarTotal(this); saveBudget(); $("#loading").hide(); }
		});
		
		$("#loading").hide();
	}
	
	function updateItemQuantity(itemId, botonPulsado, sku) {
	
		$("#loading").show();
		
		var filter = formToJSONShoppingList(itemId);
		var cantidad = parseInt($(".b2b-cart-items-wrapper input[name=cant_" + itemId + "]").val().replace(",", ".").replace(".", ""));
		cantidad += parseInt(botonPulsado);
		var cantidadOriginal = parseInt($("#cant-original_" + itemId).val().replace(",", ".").replace(".", ""));
		
		if (cantidad > 0){
		
			$.ajax({
				url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/update/" + cantidad,
				type: "POST",
				data: filter,
		        contentType: 'application/json; charset=utf-8',
		        cache: false,
		        dataType: "json",
		        success: function(response) {
		        	
		        	var cant = parseInt(cantidad);
		        	var pvpitem = parseFloat($("#total_" + itemId).val().replace(",",".")) / parseInt(cantidadOriginal);
		        	var totalitem = cant * pvpitem;
		        	
		        	$("#total_" + itemId).val(totalitem.toFixed(2).toString().replace(".",","));
		        	$(".b2b-cart-items-wrapper input[name=cant_" + itemId + "]").val(cant);
		        	$("#cant-original_" + itemId).val(cant);
		        	
		        	actualizarTotal();
		        	actualizarExtraInfoByVal($("#total_" + itemId).val(), itemId, 'importeVenta')
		        	
		        },
		        error: function(response) {$("#loading").hide();console.log("Error update cantidad"); },
		        complete : function(response) { $("#loading").hide(); }
			});
		
		}
		
		$("#loading").hide();
		
	}
	
	function updateCLIQuantity(itemId, botonPulsado, sku) {
	
		$("#loading").show();
		
		var filter = formToJSONShoppingList(itemId);
		var cantidad = parseInt($(".b2b-cart-items-wrapper input[name=cant_" + itemId + "]").val().replace(",", ".").replace(".", ""));
		cantidad += parseInt(botonPulsado);
		var cantidadOriginal = parseInt($("#cant-original_" + itemId).val().replace(",", ".").replace(".", ""));
		
		if (cantidad > 0){
		
			$.ajax({
				url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updatecli/" + cantidad,
				type: "POST",
				data: filter,
		        contentType: 'application/json; charset=utf-8',
		        cache: false,
		        dataType: "json",
		        success: function(response) {
		        	
		        	var cant = parseInt(cantidad);
		        	var pvpitem = parseFloat($("#total_" + itemId).val().replace(",",".")) / parseInt(cantidadOriginal);
		        	var totalitem = cant * pvpitem;
		        	
		        	$("#total_" + itemId).val(totalitem.toFixed(2).toString().replace(".",","));
		        	$(".b2b-cart-items-wrapper input[name=cant_" + itemId + "]").val(cant);
		        	$("#cant-original_" + itemId).val(cant);
		        	
		        	actualizarTotal();
		        	actualizarExtraInfoCLIByVal($("#total_" + itemId).val(), itemId, 'importeVenta')
		        	
		        },
		        error: function(response) {$("#loading").hide();console.log("Error update cantidad"); },
		        complete : function(response) { $("#loading").hide(); }
			});
		
		}
		
		$("#loading").hide();
		
	}
	
	function formToJSONShoppingList(itemId) {
		
		var csrf = '${ctx.getAttribute('csrf')!''}';
		var definitionName = 'commercetools';
		var connectionName = 'connectionName';
		var id = '${presupuesto.carrito.id}';
		var lineItemId = itemId;
		var userId = '${presupuesto.carrito.getCustomerId()}';
		var accessToken = '';
		var userIdEncodingDisabled = 'true';
		
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
	
	function returnBudget() {
		if (!validateFormVolver()) {
			$('#msgerror').css("display","none");
			$('#modal-remove').css('display','flex');
		} else {
			window.location.replace("${internalLinkFeedback!"#"}");
		}
	}
	
	function closeModal(name) {
		$("#"+name).css("display","none");
	}
	
	function deleteBudget(){
	
		var id = '${presupuesto.carrito.id}';
		
		$("#loading").show();
		
		var url = "${ctx.contextPath}/.rest/private/shoppinglist/delete?id=" + id;
		
		$.ajax({
			url : url,
			type : "DELETE",
			cache : false, 
			async: false, 
			success : function(response) {
				window.location.replace("${internalLinkFeedback!"#"}");
			},
			error : function(response) {
				console.log("Error al eliminar el presupuesto");
				console.log(response);
				$("#loading").hide();
			}
		});
		
		$("#loading").hide();
	
	}

</script>
[/#if]
