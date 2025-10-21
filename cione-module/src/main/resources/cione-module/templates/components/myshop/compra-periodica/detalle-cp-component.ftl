[#include "../../../includes/macros/cione-utils-impersonate.ftl"]

[#if !cmsfn.editMode]

	[#assign periodicpurchase = model.getPeriodicPurchase()!]
	[#assign employee = model.getData()!]
	[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]
	[#assign defaultImage = resourcesURL + "/img/myshop/common/oops.jpg"]
	[#assign defaultImageCLI = resourcesURL + "/img/myshop/common/imagennodisponible_lente_graduada.jpg"]
	[#assign uuid = model.getUuid()!]
	[#assign username = model.getUserName()!]
	
	<main class="b2b-main" role="main">
	
	    <h2 class="b2b-h2">${i18n['cione-module.templates.components.detalle-cp-component.title']}
	    	[#if periodicpurchase.carrito.getCustom()??]
	    		[#if periodicpurchase.carrito.getCustom().getFields().values()["idPurchase"]?? && periodicpurchase.carrito.getCustom().getFields().values()["idPurchase"]?has_content]
	    			${periodicpurchase.carrito.getCustom().getFields().values()["idPurchase"]?trim!""}
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
	                            <span style="width: 50px;">Frecuencia</span>
	                            [#assign fre = ""]
	                            [#if periodicpurchase.carrito.getCustom().getFields().values()["periodicityPurcharse"]??]
		                            [#if periodicpurchase.carrito.getCustom().getFields().values()["periodicityPurcharse"]?has_content]
		                            	[#assign fre = periodicpurchase.carrito.getCustom().getFields().values()["periodicityPurcharse"]!]
		                            [/#if]
	                            [/#if]
	                            <span>${fre!}</span>
	                        </div>
	                        <div class="product-block-title">
	                            <span style="width: 50px;">Vigencia</span>
	                            <span>${model.getValidity()!}</span>
	                        </div>
	                        <div class="product-block-title">
	                            <span style="font-weight: normal !important;">${i18n['cione-module.templates.myshop.detalle-cp-component.adv']}</span>
	                            <span></span>
	                        </div>
	                    </div>
	                </div>
	            </div>
	            [#-- END: HEADER DEL PRESUPUESTO + FECHA --]
	
	            [#-- BEGIN: LISTADO DE ELEMENTOS DEL COMPRA PERIODICA --]
	            [#list periodicpurchase.carrito.lineItems as item]
	            

	            <div class="b2b-cart-item b2b-cart-item-presupuesto" id="item_${item.getId()}">
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
	                        
	                        [#if item.getQuantity()?has_content]
				            	<div class="product-block-title">
				                    <span>${i18n.get('cione-module.templates.components.detalle-producto-component.cantidad')}</span>
				                    <span>${item.getQuantity()}</span>
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
	                    <div class="b2b-cart-item-col align-self-end d-flex">
	                    
	                    	[#assign closeimg = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/icons/close-thin.svg"]
	                     	<div class="b2b-cart-item" style="border: 0;position:static">
				                <div class="b2b-cart-item-delete mr-4" onclick="removeItemPeriodicPurchase('${item.getId()}'); return false">
				                    <img class="b2b-cart-item-icon-delete" src="${closeimg!""}" alt="Cerrar">
				                    <span class="b2b-cart-item-delete-text">Eliminar</span>
				                </div>
				            </div>
				            
			                [#-- BEGIN: PVO --]  
	                         <div class="product-reference product-reference-periodica  mb-0 pt-2">
					            [#if showPVO(ctx.getUser(), uuid, username)]
					                <label>PVO</label>
					                [#if mapCustomFields["pvoConDescuento"]?has_content]
		            					[#assign precioActual = mapCustomFields["pvoConDescuento"].getCentAmount() / 100.0]
		            					<div>${(item.getQuantity() * precioActual)?string["0.00"]}€</div>
					                [#--  --if item.getCustom().getFieldAsMoney("pvoConDescuento")?has_content]
					                	[#assign precioActual = item.getCustom().getFieldAsMoney("pvoConDescuento").getNumber()?string["0.00"]]
					                	<div>${(item.getQuantity() * item.getCustom().getFieldAsMoney("pvoConDescuento").getNumber())?string["0.00"]}€</div> --]
					                [#else]
					                	[#assign pvoOrigen = item.getPrice().getValue().getCentAmount() / 100.0]
					                	[#assign precioActual = pvoOrigen?string["0.00"]]
					                	
					                	[#assign totalPrice = item.getTotalPrice().getCentAmount() / 100.0]
					                	<div>${(totalPrice)?string["0.00"]}€</div>
					                	[#--  --assign precioActual = item.getPrice().getValue().getNumber()?string["0.00"]]
					                	<div>${item.getTotalPrice().getNumber()?string["0.00"]}€</div>--]
					                [/#if]
					            [/#if]
				            </div>   
	                        [#-- END: PVO --]
	                        
	                    </div>
	                    [/#if]
	                    
	                </div>
	            </div>
	            [/#list]
	            
	            [#-- ELEMENTO INDIVIDUAL PARA LOS CUSTOM LINE ITEMS --]
	            [#list periodicpurchase.carrito.getCustomLineItems() as itemc]
	            	[#assign mapCustomFieldsC = itemc.getCustom().getFields().values()]
	            <div class="b2b-cart-item b2b-cart-item-presupuesto" id="item_${itemc.getId()}">
	
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
				            
				            [#if itemc.getQuantity()?has_content]
				            	<div class="product-block-title">
				                    <span>${i18n.get('cione-module.templates.components.detalle-producto-component.cantidad')}</span>
				                    <span>${itemc.getQuantity()}</span>
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
			            
			            <div class="b2b-cart-item-col align-self-end d-flex">
	                    
	                    	[#assign closeimg = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/icons/close-thin.svg"]
	                     	<div class="b2b-cart-item" style="border: 0;position:static">
				                <div class="b2b-cart-item-delete mr-4" onclick="removeItemCLIPeriodicPurchase('${itemc.getId()}'); return false">
				                    <img class="b2b-cart-item-icon-delete" src="${closeimg!""}" alt="Cerrar">
				                    <span class="b2b-cart-item-delete-text">Eliminar</span>
				                </div>
				            </div>
			            	
			            	[#-- BEGIN: PVO --]
	                        <div class="product-reference product-reference-periodica  mb-0 pt-2">
	                            [#if showPVO(ctx.getUser(), uuid, username)]
			                		<label>PVO</label>
			                		[#assign totalPrice = itemc.getTotalPrice().getCentAmount() / 100.0]
		                			<div>${totalPrice?string["0.00"]}€</div>
		            			[/#if]
	                        </div>
	                        [#-- END: PVO --]
	                    </div>
	                    
	                </div>
	            </div>
	            [/#list]
	          	[#-- ELEMENTO INDIVIDUAL PARA LOS CUSTOM LINE ITEMS --]
	            
	            [#-- END: LISTADO DE ELEMENTOS DEL PRESUPUESTO --]
	             <div class="b2b-cart-item b2b-cart-item-presupuesto no-border-bt">
				    <div class="b2b-cart-item-row b2b-cart-item-footer">
				        <div class="product-reference  mb-0 pt-4"></div>
				        <div class="product-reference  mb-0 pt-4"></div>
				        <div class="product-reference product-reference-periodica   mb-0 pt-4 pb-4">
				            <label>TOTAL</label>
				            
				            [#if showPVO(ctx.getUser(), uuid, username)]
							    [#if periodicpurchase?has_content]
									<div id="total-pvo">${model.getCarritoTotal()!}€</div>
								[#else]
								   	<div>0,00€</div>
								[/#if]
							[/#if]
				            
				        </div>
			    	</div>
				</div>
	
	        </div>
	
	        [#-- BEGIN: BOTON VOLVER: preparado, descomentar dialogo y boton volver configurable --]
            <div class="b2b-cart-total-wrapper">
                <div class="b2b-card-total-wrapper  b2b-card-total-wrapper-detalle sticky">
                    
					[#if content.internalLink?has_content && content.internalLink??]
						[#assign link = cmsfn.link("website", content.internalLink!)!]
	                    <div class="b2b-cart-back-button pt-3 pb-3">
	                        <div class="b2b-button-wrapper">
	                            <button class="b2b-button b2b-button-filter" type="button" onclick="location.href='${link!""}';">
	                            	${i18n['cione-module.templates.components.listado-shoppinglist-component.back']}
	                            </button>
	                        </div>
	                    </div>
					[/#if]

                </div>
            </div>
	        [#-- END: BOTON VOLVER --]
	
	    </div>
	
	</main>

<script type="text/javascript">
	
	function removeItemPeriodicPurchase(itemId) {
	
    	$("#loading").show();
    	
		var filter = formToJSONPeriodicPurchase(itemId);
		var periodicpurchaseId = '${periodicpurchase.carrito.id}';
		
    	$.ajax({
    		url: "${ctx.contextPath}/.rest/private/carrito/v1/cartDeleteItem",
    		type: "POST",
    		data: filter,
            contentType: 'application/json; charset=utf-8',
            cache: false,
            dataType: "json",
            success: function(response) {
				$("#item_"+itemId).remove();
				$(this).parent().remove();
				actualizarPVOTotal(periodicpurchaseId);
            },
            error: function(response) { console.log("Error borrando elemento de la compra rapida"); },
            complete: function(response) { $("#loading").hide(); }
    	});
    	
    	$("#loading").hide();
    }
    
    function actualizarPVOTotal(itemId) {
		
    	$.ajax({
    		url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/pvoFullCartById?idCart="+itemId,
    		type : "GET",
    		contentType : 'application/json; charset=utf-8',
            cache : false,
            async: false,
            dataType : "json",
            success: function(response) {
            	var pvo = response.pvocart; 
				$("#total-pvo").html(pvo);
            },
            error: function(response) { console.log("Error borrando elemento de la compra rapida"); },
            complete: function(response) { }
    	});
    	
    }

    function removeItemCLIPeriodicPurchase(itemId) {
    
    	$("#loading").show();
    	
		var filter = formToJSONPeriodicPurchase(itemId);
		var periodicpurchaseId = '${periodicpurchase.carrito.id}';
    	$.ajax({
    		url: "${ctx.contextPath}/.rest/private/carrito/v1/cartDeleteCLI",
    		type: "POST",
    		data: filter,
            contentType: 'application/json; charset=utf-8',
            cache: false,
            dataType: "json",
            success: function(response) {
				$("#item_"+itemId).remove();
				$(this).parent().remove();
				actualizarPVOTotal(periodicpurchaseId);
            },
            error: function(response) { console.log("Error borrando elemento (CLI) de la compra rapida"); },
            complete: function(response) { $("#loading").hide(); }
    	});
    	
    	$("#loading").hide();
    }
	
	function formToJSONPeriodicPurchase(itemId) {
		
		var csrf = $("#formCarritoPop input[name=csrf]").val();
		var definitionName = $("#formCarritoPop input[name=definitionName]").val();
		var connectionName = $("#formCarritoPop input[name=connectionName]").val();
		var id = '${periodicpurchase.carrito.id}';
		var lineItemId = itemId;
		var userId = '${periodicpurchase.carrito.getCustomerId()}';
		var accessToken = $("#formCarritoPop input[name=accessToken]").val();
		//var userIdEncodingDisabled = $("#formCarritoPop input[name=userIdEncodingDisabled]").val();
		var userIdEncodingDisabled = true;
		
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

</script>
[/#if]
