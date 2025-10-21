[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]
[#include "../../includes/macros/ct-utils.ftl"]
[#include "../../includes/macros/cione-utils-impersonate.ftl"]

[#assign imgFlash = damfn.getAsset("jcr", "cione/imagenes/promociones/flash.png")!]
[#assign imgPromo = damfn.getAsset("jcr", "cione/imagenes/promociones/promo.png")!]
[#assign imgLiquidacion = damfn.getAsset("jcr", "cione/imagenes/promociones/liquidacion.png")!]
[#assign pack_generico = false]
[#assign skuPackMaster = model.skuPackMaster!]
[#if skuPackMaster?? && skuPackMaster?has_content]
	[#assign pack_generico = model.isValidForPack()]
[/#if]
[#assign step = model.step!]
[#if !cmsfn.editMode]

	[#-- BEGIN: modal prueba virtual 
		<div class="detalle-virtual-modal">
	        <div class="detalle-virtual-modal-wrapper">
	            <div class="detalle-virtual-modal-close-wrapper">
	                <img class="detalle-virtual-modal-close-img" 
	                	 src="${resourcesURL + "/img/myshop/icons/close-thin-white.svg"!}"
	                     alt="Cerrar" />
	            </div>
	           <div id="fitmixContainer"></div>
	        </div>
        </div>
		[#-- END: modal prueba virtual --]

	[#assign products = model.productsFront!]
	[#assign fittingboxproducts = model.getFittingBoxProducts()!]
	[#assign uuid = model.getUuid()!]
	[#assign username = model.getUserName()!]
 	[#assign count = model.count!]
 	[#assign numProduct = 12]
 	
 	[#if products?has_content]
 	
	 	[#assign total = model.productSearch.total!]
		[#assign page = model.page!]
		[#assign count = model.count!]
		
		[#if content.numProduct?has_content]
			[#assign numProduct = content.numProduct!]
		[/#if]
 	
		[#-- Orden y numero de productos --]
		<div class="b2b-listado-orden ">
		
			<div id="productslabel" class="b2b-listado-orden-text">Mostrando ${count} de ${total} ${i18n['cione-module.templates.myshop.listado-productos-filter-component.productsLabel']}</div>
			<div id="pagelist" style="display:none" data-value="${page}"></div>
			<div id="countproducts" style="display:none" data-value="${count}"></div>
			<div id="totalproducts" style="display:none" data-value="${total}"></div>
			
			<div class="hide-in-desktop b2b-mobile-button-filter-top">
				<div class="b2b-button-wrapper">
				    <button  id="b2b-button-filter-open-modal"  class="b2b-button b2b-button-filter">
				        ${i18n['cione-module.templates.myshop.listado-productos-filter-component.sortandfilter']}
				    </button>
				</div>
			</div>
			[#assign orderlabel = "(Novedad)"]
			[#if content.orderconf?? && content.orderconf?has_content]
				[#assign orderconf = content.orderconf]
				[#switch orderconf]
					[#case "altaEkon"]
						[#assign orderlabel = "(Novedad)"]
						[#break]
					[#case "fechacreacion"]
						[#assign orderlabel = "(Nuevos)"]
						[#break]
					[#case "alfabetico"]
						[#assign orderlabel = "(" + i18n['cione-module.templates.myshop.listado-productos-filter-component.sortalfabetico'] + ")"]
						[#break]
				[/#switch]
			[/#if]
		    <div class="b2b-listado-orden-text hide-in-mobile">
		        ${i18n['cione-module.templates.myshop.listado-productos-filter-component.sortby']}
		        <select id="selectorder" class="selectorder">
		            <option value="cleanorder">${i18n['cione-module.templates.myshop.listado-productos-filter-component.sortdefault']} ${orderlabel}</option>
		            <option value="new">${i18n['cione-module.templates.myshop.listado-productos-filter-component.sortnovedad']}</option>
		            <option value="alfabetico">${i18n['cione-module.templates.myshop.listado-productos-filter-component.sortalfabetico']}</option>
		            <option value="lowtohigh">${i18n['cione-module.templates.myshop.listado-productos-filter-component.lowtohigh']}</option>
		            <option value="hightolow">${i18n['cione-module.templates.myshop.listado-productos-filter-component.hightolow']}</option>
		        </select>
		    </div>
	            
	    </div>
	    [#-- END: Orden y numero de productos --]
    
        
		[#-- listado de productos --]        
		<div class="b2b-listado-monturas d-flex justify-content-start flex-wrap">
			
	    
		    [#list products as product]
		    
		    [#assign familiaProducto = ""]
			[#if product.master.familiaproducto?? && product.master.familiaproducto?has_content]
				[#assign familiaProducto = product.master.familiaproducto]
			[/#if]
		    [#assign classtipo  = "" ]
		    [#assign classtipocard  = "" ]
		    [#if product.master.tipoproducto?has_content]
				[#if product.master.tipoproducto == "Accesorios" 
					|| product.master.tipoproducto == "Lentes de contacto" 
					|| product.master.tipoproducto == "Packs" 
					|| product.master.tipoproducto?lower_case == "maquinaria"
					|| familiaProducto == "audiolab"
					|| model.skupack?? && model.skupack?has_content
					|| familiaProducto == "pack-generico"]
					[#assign classtipocard  = "card-accesorios" ]
					[#assign classtipoitem  = "item-accesorios" ]
				[/#if]
			[/#if]
			
			
		    <div class="b2b-product-card b2b-product-card--double ${classtipocard!""}">
				<div class="item ${classtipoitem!""}">
					
					[@macromodal product.master.sku product.master.delivery/]
					
					[@macromodalreplacements product.master /]
					
					<script>
						$(document).ready(function(){
							$(".${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")!""} .product-color-circle").on("click",function(){
						    
							    var myid = $(this).parent().parent().data('value');
							    var goid = $(this).data('value');
							    
							    var rmyid = myid.replace(/\./g,"").replace(new RegExp('-', 'g'), '');
							    var rgoid = goid.replace(/\./g,"").replace(new RegExp('-', 'g'), '');
							    
							    if (myid != goid){
							    	$('.' + goid + ' .select-calibration').prop('selectedIndex',0);
							    	$("div.item-back." + rmyid).css("display", "none");
									$("div.item-back." + rgoid).css("display", "flex");
							    }
						
							});
							  
							$(".${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")!""} .product-calibre").on("click",function(e){
							    
							    var myid = $(this).parent().parent().data('value');
							    var goid = $(this).data('value');
								
								var rmyid = myid.replace(/\./g,"").replace(new RegExp('-', 'g'), '');
							    var rgoid = goid.replace(/\./g,"").replace(new RegExp('-', 'g'), '');
								
							    if (myid != goid){
							    	$("div.item-back." + rmyid).css("display", "none");
									$("div.item-back." + rgoid).css("display", "flex");
							    }
								
							});
							
							var lastSel${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")!""} = $('.${product.master.sku?replace("-", "")?replace(".", "")?replace("+", "")!""} .select-calibration').val();
							  
							$('.${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")!""} .select-calibration').change(function(){ 
								
								var myid = $(this).parent().parent().data('value');
							    var goid = $(this).val();
								
								$('.${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")!""} .select-calibration').val(lastSel${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")!""});
								
								var rmyid = myid.replace(/\./g,"").replace(new RegExp('-', 'g'), '');
							    var rgoid = goid.replace(/\./g,"").replace(new RegExp('-', 'g'), '');
								
							    if (myid != goid){
							    	$("div.item-back." + rmyid).css("display", "none");
									$("div.item-back." + rgoid).css("display", "flex");
							    }
								
							});
				
							var lastTamSel${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")!""} = $('.${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")!""} .select-tamanio').val();
							  
							$('.${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")!""} .select-tamanio').change(function(){ 
								
								var myid = $(this).parent().parent().data('value');
							    var goid = $(this).val();
								
								$('.${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")!""} .select-tamanio').val(lastTamSel${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")!""});
								
								var rmyid = myid.replace(/\./g,"").replace(new RegExp('-', 'g'), '');
							    var rgoid = goid.replace(/\./g,"").replace(new RegExp('-', 'g'), '');
								
							    if (myid != goid){
							    	$("div.item-back." + rmyid).css("display", "none");
									$("div.item-back." + rgoid).css("display", "flex");
							    }
								
							});
							
							$('.${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")!""} .UUID${content["jcr:uuid"]!""}.product-button').click(function(){
								[#if pack_generico && product.master.isIncluidoEnPack()]
									addPack('${product.master.sku!""}', '${product.master.tipoPrecioPack!}', '${product.master.pvoPack!}', '${product.master.tipoproducto!}');
								[#elseif product.master.gestionStock]
									var stock = 0;
									var stockwithcart = 0;
									var almacen = '';
									var stockCTRAL = 0;
									var unitscart = 0;
									var urlstockwithcart = "${ctx.contextPath}/.rest/private/stock/withcart?sku=" + "${product.master.sku?trim}" + "&aliasEkon=" + encodeURIComponent("${product.master.aliasEKON!}");
									var urlstock = "${ctx.contextPath}/.rest/private/stock?sku=" + encodeURIComponent("${product.master.aliasEKON!}");
									var unitscarturl = "${ctx.contextPath}/.rest/private/stock/unitscart?sku=" + "${product.master.sku?trim}";
									
									$.ajax({
										url : urlstockwithcart,
										type : "GET",
										cache : false, 
										async: false, 
										success : function(response) {
											stockwithcart = response.stock;
										},
										error : function(response) {
											console.log(response); 
										}
									});
									$.ajax({
										url : urlstock,
										type : "GET",
										cache : false, 
										async: false, 
										success : function(response) {
											stock = response.stock;
											stockCTRAL = response.stockCTRAL;
											almacen = response.almacen;
										},
										error : function(response) {
											console.log(response); 
										}
									});
									$.ajax({
										url : unitscarturl,
										type : "GET",
										cache : false, 
										async: false, 
										success : function(response) {
											unitscart = response.units;
										},
										error : function(response) {
											console.log(response); 
										}
									});
									
									var stockreallabel = "${i18n['cione-module.templates.myshop.listado-productos-home-component.only']} ";
									var stockcanariaslabel = "${i18n['cione-module.templates.myshop.listado-productos-home-component.canar']}";
									var stockcentrallabel = "${i18n['cione-module.templates.myshop.listado-productos-home-component.ctral']}";
									var stockunidadeslabel = " ${i18n['cione-module.templates.myshop.listado-productos-home-component.units']}";
									
									$(this).parent().parent().find('input#stock_${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")}').attr("value",stock);
									
									if (almacen == 'stockCANAR'){
										$('div.modal-purchase.${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")!""}.UUID${content["jcr:uuid"]!""}').children().find('.stock_modal').html(stockcanariaslabel + stock + stockunidadeslabel + "<br\>" + stockcentrallabel + stockCTRAL + stockunidadeslabel);
									}else{
										$('div.modal-purchase.${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")!""}.UUID${content["jcr:uuid"]!""}').children().find('.stock_modal').text("${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.stock']} " + stock + " ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}");
									} 
									
									if ((stockwithcart < $(this).parent().parent().find('.product-amount-input').val()) && (${product.master.gestionStock?string('true', 'false')}) ){
									
										$('span#unitsselected').text($(this).parent().parent().find('.product-amount-input').val());
										
										if(unitscart > 0){
											$('span#unitscart').text(" + " + unitscart + "${i18n['cione-module.templates.myshop.configurador-lentes-component.incart']}");
										}
										
										var refcliente = $(this).parent().parent().find('.product-ref-input').val();
										
										if (refcliente == null || refcliente == ''){
											sessionStorage.setItem('refclient', '');
										}else{
											sessionStorage.setItem('refclient', refcliente);
										}
										if (${product.master.hasSubstitute?c}) {
											var res = generateReplacementModal('${product.master.sku}', $(this).parent().parent().find('.product-amount-input').val());
											if (!res) {
												$('div.modal-purchase.${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")!""}.UUID${content["jcr:uuid"]!""}').css("display", "flex");	
											}
										} else {
							    			$('div.modal-purchase.${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")!""}.UUID${content["jcr:uuid"]!""}').css("display", "flex");
							    		}
									}else{
									
										addCart('${product.master.sku!""}','',$(this).parent().parent().find('.product-ref-input').val(),$(this).parent().parent().find('.product-amount-input').val());
									}
								[#else]
									addCart('${product.master.sku!""}','',$(this).parent().parent().find('.product-ref-input').val(),$(this).parent().parent().find('.product-amount-input').val());
								[/#if]
							});
						});
					</script>
					
					[#assign promo = ""]
					[#assign mamount = product.master.amoutdiscount!0]
					[#if product.master.promo?has_content && mamount gte 1]
						[#assign promo = product.master.promo!"false"]
					[/#if]
					
					[#assign ofertaFlash = ""]
					[#if product.master.ofertaFlash?has_content]
						[#assign ofertaFlash = product.master.ofertaFlash!"false"]
					[/#if]
					
					[#assign mainImage = ""]
					[#if product.master.images?has_content]
						[#list product.master.images as image]
							[#if image?is_first]
								[#assign mainImage = image.url!]
							[/#if]
						[/#list]
					[/#if]
					
					[#if !mainImage?has_content]
						[#assign mainImage = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/common/noproduct.png"]
					[/#if]

		            <div class="item-front ${classtipoitem!""}">
		            			            
						[#if ofertaFlash?has_content && ofertaFlash]
							<img class="product-ribbon" class="product-image" src="[#if imgFlash??]${imgFlash.link!}[/#if]" alt="oferta flash" />
						[#else]						
							[#if product.master.statusEkon?has_content && product.master.statusEkon == "Liquidacion"]
			                	<img class="product-ribbon" class="product-image" src="[#if imgLiquidacion??]${imgLiquidacion.link!}[/#if]" alt="liquidacion" />
								[#if product.master.descripcion?has_content]
									[#if product.master.descripcion == "oferta"]
										<img class="product-ribbon" class="product-image" src="[#if imgFlash??]${imgFlash.link!}[/#if]" alt="oferta flash" />
									[/#if]
									[#if product.master.descripcion == "promo"]
										<img class="product-ribbon" class="product-image" src="[#if imgPromo??]${imgPromo.link!}[/#if]" alt="promocion" />
									[/#if]
								[/#if]
							[#elseif promo?has_content]
								[#if promo]
									<img class="product-ribbon" class="product-image" src="[#if imgPromo??]${imgPromo.link!}[/#if]" alt="promocion" />
								[/#if]
							[#elseif familiaProducto=="pack-generico"]
		            			[#assign assetDescuentoPack = damfn.getAsset("jcr", "cione/imagenes/promociones/ico-packs.png")!]
								<img class="product-ribbon" class="product-image" src="[#if assetDescuentoPack??]${assetDescuentoPack.link!}[/#if]" alt="oferta pack" />
							[/#if]
						[/#if]

		                <div class="product-image-wrapper">
		                    <img class="product-image" src="${mainImage!""}" alt="${product.master.name!""}" />
		                </div>

		                <div class="product-info-wrapper">
		                    <div class="product-name" title="${product.master.name!""}">${getTruncate(product.master.name!"",64)!""}</div>
		                    
	                 	 	[#-- TODO OCULTAMOS LA FORMA DE MOSTRAR EL PRECIO ANTERIOR  --]
							[#if false]
								[#assign strike = ""]
								[#if product.master.discount?has_content]
								    [#if showPVO(ctx.getUser(), uuid, username)]  
								    	<div class="product-price"><span class="product-price-type">${i18n.get('cione-module.templates.configuracion-precios-component.PVO')}</span> #{product.master.discount!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
								    [/#if]
									[#assign strike = "product-price-strike"]
								[#else]
									[#if showPVO(ctx.getUser(), uuid, username)]
										<div class="product-price">
											<span class="product-price-type">${i18n.get('cione-module.templates.configuracion-precios-component.PVO')}</span> 
											#{product.master.pvo!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}
										</div>
									[/#if]
								[/#if]
							[/#if]
							
						 	[#assign strike = ""]
		                    [#if product.master.discount?has_content]			    
		                    	[#assign strike = "product-price-strike"]
		                    [/#if]
							
	                     	<div class="product-price-wrapper-mod">
	                     		[#switch familiaProducto]
	                     			[#case "packs"]
	                     				[#if product.master.pvoSinPack?has_content && showPVO(ctx.getUser(), uuid, username)]
					                        <div class="product-price-item">
					                            <div class="product-price-text">${i18n['cione-module.templates.myshop.listado-productos-home-component.pvo']}</div>
					                            <div class="product-price-number product-price-strike">#{product.master.pvoSinPack!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
					                        </div>
					                    [/#if]
	                     				[#if product.master.pvo?has_content && showPVO(ctx.getUser(), uuid, username)]
					                        <div class="product-price-item">
					                            <div class="product-price-text">${i18n['cione-module.templates.myshop.listado-productos-home-component.pvodto']}</div>
					                            <div class="product-price-number">#{product.master.pvo!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
					                        </div>
				                        [/#if]
			                        	
	                     				[#break]
	                     			[#case "pack-generico"]
	                     				[#switch product.infoPack.tipoPrecioPack]
											[#--  --case "CERRADO"]
												<div class="product-price-item">
						                            <div class="product-price-text">${i18n['cione-module.templates.myshop.listado-productos-home-component.allpack']}: #{product.master.pvo!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
						                        </div>
												[#break--]
											[#case "GLOBAL"]
												<div class="product-price-item">
						                            <div class="product-price-text">${i18n['cione-module.templates.myshop.listado-productos-home-component.pack-con']}</div>
						                            <div class="product-price-number">${product.infoPack.descuentoGlobal!} ${i18n['cione-module.templates.myshop.listado-productos-home-component.descuento']}</div>
						                        </div>
												[#break]
											[#case "INDIVIDUAL-CERRADO"] [#-- deprecated --]
												<div class="product-price-item">
						                            <div class="product-price-text">${i18n['cione-module.templates.myshop.listado-productos-home-component.allpack']}: #{product.master.pvo!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
						                        </div>
												[#break]
											[#case "INDIVIDUAL"]
												<div class="product-price-item">
						                            <div class="product-price-text">${product.name!}</div>
						                        </div>
												[#break]
											[#default]
												[#break]
										[/#switch]
	                     				[#break]

	                     				[#if product.master.pvo?has_content && showPVO(ctx.getUser(), uuid, username)]
					                        [#if product.master.discount?has_content && showPVO(ctx.getUser(), uuid, username)]
					                    		<div class="product-price-item">
						                            <div class="product-price-text">${i18n['cione-module.templates.myshop.listado-productos-home-component.pvo']}</div>
						                            <div class="product-price-number ${strike!""}">#{product.master.pvo!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
						                        </div>
						                        <div class="product-price-item">
						                            <div class="product-price-text">${i18n['cione-module.templates.myshop.listado-productos-home-component.pvodto']}</div>
						                            <div class="product-price-number">#{product.master.discount!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
						                        </div>
						                    [#else]
						                    	<div class="product-price-item">
						                            <div class="product-price-text">${i18n['cione-module.templates.myshop.listado-productos-home-component.pvo']}</div>
						                            <div class="product-price-number ${strike!""}">#{product.master.pvo!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
						                        </div>
						                    [/#if]
				                        [/#if]
	                     				[#break]
	                     		[/#switch]
	                    	</div>
		                    
		                </div>

		            </div>
		            
		            [#-- [#if product.master.packNavegacionDetalle]
						[#-- PACKS GENERICOS ]
						[#assign linkPack = cmsfn.link("website", content.internalLinkPacksDetail!)!]
						[#assign linkPack = linkPack + "?skuPackMaster=" + model.encodeURIComponent(product.master.sku)!"#"]
						[#-- <button  class="b2b-button b2b-button-filter" type="button" onclick="openModalPack('${linkPack}','${product.master.sku}'); return false">
							ABRIR EN MODAL
						</button> ]
						[@macromodalPack linkPack product.master.sku/]
		            [/#if] --]
		            
		            
		            
		            [#------------------------- PRODUCTO MASTER -------------------------]
		            
		            [#if product.master.excludeMasterProductFront]
		            	<div class="item-back ${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")!"ERROR"}" data-value="${product.master.sku!"ERROR"}" style="display:none">
		            [#else]
		            	<div class="item-back ${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")!"ERROR"}" data-value="${product.master.sku!"ERROR"}">
		            [/#if]   
		            						
		                [#if ofertaFlash?has_content && ofertaFlash]
							<img class="product-ribbon" class="product-image" src="[#if imgFlash??]${imgFlash.link!}[/#if]" alt="oferta flash" />
						[#else]
							[#if product.master.statusEkon?has_content && product.master.statusEkon == "Liquidacion"]
			                	<img class="product-ribbon" class="product-image" src="[#if imgLiquidacion??]${imgLiquidacion.link!}[/#if]" alt="liquidacion" />
								[#if product.master.descripcion?has_content]
									[#if product.master.descripcion == "oferta"]
										<img class="product-ribbon" class="product-image" src="[#if imgFlash??]${imgFlash.link!}[/#if]" alt="oferta flash" />
									[/#if]
									[#if product.master.descripcion == "promo"]
										<img class="product-ribbon" class="product-image" src="[#if imgPromo??]${imgPromo.link!}[/#if]" alt="promocion" />
									[/#if]
								[/#if]
							[#elseif promo?has_content]
								[#if promo]
									<img class="product-ribbon" class="product-image" src="[#if imgPromo??]${imgPromo.link!}[/#if]" alt="promocion" />
								[/#if]
							[#elseif familiaProducto=="pack-generico"]
		            			[#assign assetDescuentoPack = damfn.getAsset("jcr", "cione/imagenes/promociones/ico-packs.png")!]
								<img class="product-ribbon" class="product-image" src="[#if assetDescuentoPack??]${assetDescuentoPack.link!}[/#if]" alt="oferta pack" />
							[/#if]
						[/#if]

	                
		                [#-- BEGIN: prueba virtual --]
		                [#if fittingboxproducts?? && fittingboxproducts?has_content]
			                [#if fittingboxproducts[product.master.sku]]
			                	[#--  fittinboxinstance('${product.master.sku}') --]
							    <img class="product-virtual b2b-detalle-virtual-button" 
							         onclick="fitmixInstance.setFrame('${product.master.sku}'); openVto()" 
							         src="${resourcesURL!}/img/myshop/icons/icon-virtual.svg" 
							         alt="Prueba Virtual" 
						         />	
						    [#else]
						    	[#--  comprobamnos sus variantes si alguna lo tiene marcamos la prueba virtual en todas --]
						    	[#if product.variants?has_content]
						    		[#assign flag = true]
			        				[#list product.variants as variantfit]
			        					[#if fittingboxproducts[variantfit.sku] && flag]
			        						[#assign flag = false]
			        						<img class="product-virtual b2b-detalle-virtual-button" 
										         onclick="fitmixInstance.setFrame('${variantfit.sku}'); openVto()" 
										         src="${resourcesURL!}/img/myshop/icons/icon-virtual.svg" 
										         alt="Prueba Virtual" 
									         />	
			        					[/#if]
			        				[/#list]
			        			[/#if]
							[/#if]
						[/#if]
		                [#-- END: prueba virtual --]

		                <div class="product-image-wrapper">
		                
			                [#assign sku = '']
							[#assign link = '#']
							[#if product.master.packNavegacion]
								[#-- PACKS AUDIOLOGIA deprecated --]
								[#assign link = cmsfn.link("website", content.internalLinkResultPacks!)!]
								
								[#list product.master.filtros as filtro]
									[#if filtro?is_first]
										[#assign link = link + "?" + filtro!"#"]
									[#else]
										[#assign link = link + "&" +filtro!"#"]
									[/#if]
								[/#list]
								[#assign link = link + "&skupack=" + model.encodeURIComponent(product.master.sku)!"#"]
							[#elseif product.master.packNavegacionDetalle]
								[#-- PACKS GENERICOS --]
								[#assign link = cmsfn.link("website", content.internalLinkPacksDetail!)!] 
								[#assign link = link + "?skuPackMaster=" + model.encodeURIComponent(product.master.sku)!"#"]
								
							[#else]
								[#if product.master.sku?has_content && product.master.sku?? && content.internalLink?has_content && content.internalLink??]
									[#assign link = cmsfn.link("website", content.internalLink!)!]
									[#assign sku = product.master.sku!'ERROR']
									[#assign link = link + "?sku=" + model.encodeURIComponent(sku)!"#"]
									
									[#if model.skupack?has_content && model.skupack??]
										[#assign link = link + "&skupack=" + model.encodeURIComponent(model.skupack)!"#"]
									[/#if]
									[#if model.skuPackMaster?has_content && model.skuPackMaster??]
										[#assign link = link + "&skuPackMaster=" + model.encodeURIComponent(model.skuPackMaster)!"#"]
										[#assign link = link + "&step=" + step!]
									[/#if]
								[/#if]
							[/#if]
							
		                	<a href="${link!"#"}" data-value="${sku}">
		                    	<img class="product-image" src="${mainImage!""}" alt="${product.master.name!""}" />
	                    	</a>
		                </div>
		                
		                <div class="product-info-wrapper">
		                
		                    <div class="product-name" title="${product.master.name!""}">${getTruncate(product.master.name!"",64)!""}</div>
		                    [#if product.master.nombreArticulo?? && product.master.nombreArticulo?has_content 
		                    	&& (familiaProducto == "monturas")
		                    	&& (product.master.name != product.master.nombreArticulo)]
		                    		<div class="product-name" title="${product.master.nombreArticulo!""}">${getTruncate(product.master.nombreArticulo!"",64)!""}</div>
		                    [/#if]
		                    [#if product.master.coleccion?? && product.master.coleccion?has_content]
		                    	<div class="product-name" title="${product.master.coleccion!""}">${getTruncate(product.master.coleccion!"",64)!""} coleccion</div>
		                    [/#if]
		                    [#if product.master.descripcionPack?? && product.master.descripcionPack?has_content]
		                    	<div class="product-name" title="${product.master.descripcionPack!""}">${getTruncate(product.master.descripcionPack!"",64)!""}</div>
		                    [/#if]
		                    <div class="product-price-wrapper">
		                    	[#switch familiaProducto]
									[#case "packs"]
										[#if product.master.pvoSinPack?has_content && showPVO(ctx.getUser(), uuid, username)]
					                        <div class="product-price-item">
					                            <div class="product-price-text">${i18n['cione-module.templates.myshop.listado-productos-home-component.pvo']}</div>
					                            <div class="product-price-number product-price-strike">#{product.master.pvoSinPack!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
					                        </div>
					                    [/#if]
					                    [#if product.master.pvo?has_content && showPVO(ctx.getUser(), uuid, username)]
				                        <div class="product-price-item">
				                            <div class="product-price-text">${i18n['cione-module.templates.myshop.listado-productos-home-component.pvodto']}</div>
				                            <div class="product-price-number">#{product.master.pvo!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
				                        </div>
				                        [/#if]
										[#break]
									[#case "pack-generico"]
										[#switch product.infoPack.tipoPrecioPack]
											[#--  --case "CERRADO"]
												<div class="product-price-item">
						                            <div class="product-price-text">${i18n['cione-module.templates.myshop.listado-productos-home-component.allpack']}: #{product.master.pvo!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
						                        </div>
												[#break --]
											[#case "GLOBAL"]
												<div class="product-price-item">
						                            <div class="product-price-text">${i18n['cione-module.templates.myshop.listado-productos-home-component.pack-con']}</div>
						                            <div class="product-price-number">${product.infoPack.descuentoGlobal} ${i18n['cione-module.templates.myshop.listado-productos-home-component.descuento']}</div>
						                        </div>
												[#break]
											[#case "INDIVIDUAL-CERRADO"] [#-- deprecated --]
												<div class="product-price-item">
						                            <div class="product-price-text">${i18n['cione-module.templates.myshop.listado-productos-home-component.pack-completo']}</div>
						                            <div class="product-price-number">#{product.master.pvo!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
						                        </div>
												[#break]
											[#case "INDIVIDUAL"]
												[#-- no ponemos precio, va en el nombre --]
												[#break]
											[#default]
												[#break]
										[/#switch]
										
										[#break]
									[#default]
										[#if product.master.pvo?has_content && showPVO(ctx.getUser(), uuid, username)]
					                        <div class="product-price-item">
					                            <div class="product-price-text">${i18n['cione-module.templates.myshop.listado-productos-home-component.pvo']}</div>
					                            <div class="product-price-number ${strike!""}">#{product.master.pvo!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
					                        </div>
					                    [/#if]
				                        	[#if product.master.discount?has_content && showPVO(ctx.getUser(), uuid, username)]
					                        <div class="product-price-item">
					                            <div class="product-price-text">${i18n['cione-module.templates.myshop.listado-productos-home-component.pvodto']}</div>
					                            <div class="product-price-number">#{product.master.discount!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
					                        </div>
				                        [/#if]
				                        [#break]
								[/#switch]
		                    
		                        [#if product.master.pvp?has_content && showPVP(ctx.getUser(), uuid, username)]
		                        <div class="product-price-item">
		                            <div class="product-price-text-pvp">${i18n['cione-module.templates.myshop.listado-productos-home-component.pvp']}</div>
		                            <div class="product-price-pvp">#{product.master.pvp!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
		                        </div>
		                        [/#if]
		                    </div>
		                </div>
		                
		                [#if product.master.color?has_content]
		                	 [#if product.master.calibration?has_content]
		                	 	[#-- color + graduacion --]
		                	 	[@macrocolorandcalibration product/]
		                	 [#else]
			                	 [#if product.master.calibre?has_content]
			                	 	[#-- color + calibre --]
			                	 	[@macrocolorandcaliber product/]
			                	 [#else]
			                	 	[#-- color --]
			                	 	[@macrocolor product/]
			                	 [/#if]
		                	 [/#if]
		                [#else]
		                	[#if product.master.calibre?has_content]
		                		[#-- calibre --]
		                		[@macrocaliber product/]
							[#else]
								[#if product.master.calibration?has_content]
									[#-- calibration  --]
									[@macrocalibration product/]
								[/#if]
							[/#if]
		                [/#if]
						
						[@macrotamanio product/]
						
						[#if familiaProducto == "audifonos"]
							[@macrocoloraudio product/]
						[/#if]
						
						[#if product.master.familiaproducto?has_content]
							
							[#if product.master.familiaproducto != "contactologia" 
								&& familiaProducto != "audiolab" && familiaProducto != "packs" 
								&& !model.skupack?? && !model.skupack?has_content && familiaProducto != "pack-generico" ]
								[@macrocompra product/]
							[/#if]
						[#else]
							[@macrocompra product/]
		                [/#if]
		                
		            </div>
		            [#-- Obtener info de promociones
		            [#assign infoPromo = model.getVariantInfoPromociones(product.master.sku)]
		            [#if infoPromo.getTipoPromocion() == "escalado"]
			        	[#assign listPromos = infoPromo.getListPromos()!]
			    		[#list listPromos as promo]
							<input type="hidden" name="listPromos_${product.master.sku?replace(".", "")?replace("+", "")}" value="${promo.getCantidad_hasta()}|${promo.getPvoDto()}">
			    		[/#list]
			    		<input type="hidden" id="tipoPromo_${product.master.sku?replace(".", "")?replace("+", "")}" value="escalado">
			    	[#else]
			    		<input type="hidden" id="tipoPromo_${product.master.sku?replace(".", "")?replace("+", "")}" value="otro">
					[/#if] --]
		            
		            
		            [#------------------------- PRODUCTO VARIANTS -------------------------]
		        
			        [#if product.variants?has_content]
			        	[#list product.variants as variant]
				        	[#if !variant.isVartiantPack()]
					        	[@macromodal variant.sku variant.delivery/]
					        	
					        	<script>
					        	$(document).ready(function(){
					        		
						        	$(".${variant.sku?replace(".", "")?replace("+", "")?replace("-", "")!""} .product-color-circle").on("click",function(){
									    
									    var myid = $(this).parent().parent().data('value');
									    var goid = $(this).data('value');
									    
									    var rmyid = myid.replace(/\./g,"").replace(new RegExp('-', 'g'), '');
							    		var rgoid = goid.replace(/\./g,"").replace(new RegExp('-', 'g'), '');
									    
									    if (myid != goid){
									    	$('.' + goid + ' .select-calibration').prop('selectedIndex',0);
									    	$("div.item-back." + rmyid).css("display", "none");
											$("div.item-back." + rgoid).css("display", "flex");
									    }
								
								});
						  
								$(".${variant.sku?replace(".", "")?replace("+", "")?replace("-", "")!""} .product-calibre").on("click",function(e){
								    
								    var myid = $(this).parent().parent().data('value');
								    var goid = $(this).data('value');
								    
								    var rmyid = myid.replace(/\./g,"").replace(new RegExp('-', 'g'), '');
						    		var rgoid = goid.replace(/\./g,"").replace(new RegExp('-', 'g'), '');
								    
								    if (myid != goid){
								    	$("div.item-back." + rmyid).css("display", "none");
										$("div.item-back." + rgoid).css("display", "flex");
								    }
									
								});
								
								var lastSel${variant.sku?replace(".", "")?replace("+", "")?replace("-", "")!""} = $('.${variant.sku?replace(".", "")?replace("+", "")?replace("-", "")!""}  .select-calibration').val();
						  
								$('.${variant.sku?replace(".", "")?replace("+", "")?replace("-", "")!""}  .select-calibration').change(function(){ 
								
									var myid = $(this).parent().parent().data('value');
								    var goid = $(this).val();
								    
								    $('.${variant.sku?replace(".", "")?replace("+", "")?replace("-", "")!""}  .select-calibration').val(lastSel${variant.sku?replace(".", "")?replace("+", "")?replace("-", "")!""});
								    
								    var rmyid = myid.replace(/\./g,"").replace(new RegExp('-', 'g'), '');
						    		var rgoid = goid.replace(/\./g,"").replace(new RegExp('-', 'g'), '');
										    
								    if (myid != goid){
								    	$("div.item-back." + rmyid).css("display", "none");
										$("div.item-back." + rgoid).css("display", "flex");
								    }
									
								});
					
								var lastTamSel${variant.sku?replace(".", "")?replace("+", "")?replace("-", "")!""} = $('.${variant.sku?replace(".", "")?replace("+", "")?replace("-", "")!""} .select-tamanio').val();
								  
								$('.${variant.sku?replace(".", "")?replace("+", "")?replace("-", "")!""} .select-tamanio').change(function(){ 
									
									var myid = $(this).parent().parent().data('value');
								    var goid = $(this).val();
									
									$('.${variant.sku?replace(".", "")?replace("+", "")?replace("-", "")!""} .select-tamanio').val(lastTamSel${variant.sku?replace(".", "")?replace("+", "")?replace("-", "")!""});
									
									var rmyid = myid.replace(/\./g,"").replace(new RegExp('-', 'g'), '');
								    var rgoid = goid.replace(/\./g,"").replace(new RegExp('-', 'g'), '');
									
								    if (myid != goid){
								    	$("div.item-back." + rmyid).css("display", "none");
										$("div.item-back." + rgoid).css("display", "flex");
								    }
									
								});
						
								$('.${variant.sku?replace(".", "")?replace("+", "")?replace("-", "")!""} .UUID${content["jcr:uuid"]!""}.product-button').click(function(){
									[#if pack_generico && variant.isIncluidoEnPack()]
										addPack('${variant.sku!""}', '${variant.tipoPrecioPack!}', '${variant.pvoPack!}', '${variant.tipoproducto!}');
									[#elseif variant.gestionStock]
										var stock = 0;
										var stockwithcart = 0;
										var unitscart = 0;
										var urlstockwithcart = "${ctx.contextPath}/.rest/private/stock/withcart?sku=" + "${variant.sku?trim}" + "&aliasEkon=" + encodeURIComponent("${variant.aliasEKON!}");
										var urlstock = "${ctx.contextPath}/.rest/private/stock?sku=" + encodeURIComponent("${variant.aliasEKON!}");
										var unitscarturl = "${ctx.contextPath}/.rest/private/stock/unitscart?sku=" + "${variant.sku?trim}";
										$.ajax({
											url : urlstockwithcart,
											type : "GET",
											cache : false, 
											async: false, 
											success : function(response) {
												stockwithcart = response.stock;
											},
											error : function(response) {
												console.log(response); 
											}
										});
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
										$.ajax({
											url : unitscarturl,
											type : "GET",
											cache : false, 
											async: false, 
											success : function(response) {
												unitscart = response.units;
												console.log(response);
											},
											error : function(response) {
												console.log(response); 
											}
										});
										
										$(this).parent().parent().find('input#stock_${variant.sku?replace(".", "")?replace("+", "")?replace("-", "")}').attr("value",stock);
										$('div.modal-purchase.${variant.sku?replace(".", "")?replace("+", "")?replace("-", "")!""}.UUID${content["jcr:uuid"]!""}').children().find('.stock_modal').text("${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.stock']} " + stock + " ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}");
		
										if (stockwithcart < $(this).parent().parent().find('.product-amount-input').val()){
										
											$('span#unitsselected').text($(this).parent().parent().find('.product-amount-input').val());
									
											if(unitscart > 0){
												$('span#unitscart').text(" + " + unitscart + "${i18n['cione-module.templates.myshop.configurador-lentes-component.incart']}");
											}
											
											var refcliente = $(this).parent().parent().find('.product-ref-input').val();
											
											if (refcliente == null || refcliente == ''){
												sessionStorage.setItem('refclient', '');
											}else{
												sessionStorage.setItem('refclient', refcliente);
											}
											
								    		$('div.modal-purchase.${variant.sku?replace(".", "")?replace("+", "")?replace("-", "")!""}.UUID${content["jcr:uuid"]!""}').css("display", "flex");
								    		
										}else{
										
											addCart('${variant.sku!""}','',$(this).parent().parent().find('.product-ref-input').val(),$(this).parent().parent().find('.product-amount-input').val());
										}
									[#else]
										addCart('${variant.sku!""}','',$(this).parent().parent().find('.product-ref-input').val(),$(this).parent().parent().find('.product-amount-input').val());
							    	[/#if]	
								});
							});
							</script>
							
							[#assign vpromo = ""]
							[#assign vamount = variant.amoutdiscount!0]
							[#if variant.promo?has_content && vamount gte 1]
								[#assign vpromo = variant.promo!"false"]
							[/#if]
							
							[#assign strike = ""]
		                    [#if variant.discount?has_content]
		                    	[#assign strike = "product-price-strike"]
		                    [/#if]
				        	
				        	
				        	[#if product.master.excludeMasterProductFront]
				            	<div class="item-back ${getId(product,variant)?replace(".", "")?replace("+", "")?replace("-", "")!"ERROR"}" data-value="${getId(product,variant)!"ERROR"}">
				            [#else]
				        		<div class="item-back ${getId(product,variant)?replace(".", "")?replace("+", "")?replace("-", "")!"ERROR"}" data-value="${getId(product,variant)!"ERROR"}" style="display:none;">
			                [/#if]
			                  	[#if variant.ofertaFlash?has_content && variant.ofertaFlash]
									<img class="product-ribbon" class="product-image" src="[#if imgFlash??]${imgFlash.link!}[/#if]" alt="oferta flash" />
								[#else]
									[#if variant.statusEkon?has_content  && variant.statusEkon == "Liquidacion"]
					                	<img class="product-ribbon" class="product-image" src="[#if imgLiquidacion??]${imgLiquidacion.link!}[/#if]" alt="liquidacion" />
										[#if variant.descripcion?has_content]
											[#if variant.descripcion == "oferta"]
												<img class="product-ribbon" class="product-image" src="[#if imgFlash??]${imgFlash.link!}[/#if]" alt="oferta flash" />
											[/#if]
											[#if variant.descripcion == "promo"]
												<img class="product-ribbon" class="product-image" src="[#if imgPromo??]${imgPromo.link!}[/#if]" alt="promocion" />
											[/#if]
										[/#if]
									[#elseif vpromo?has_content]
										[#if vpromo]
											<img class="product-ribbon" class="product-image" src="[#if imgPromo??]${imgPromo.link!}[/#if]" alt="promocion" />
										[/#if]
									[/#if]
								[/#if]     
		                
				                [#-- BEGIN: prueba virtual --]
				                [#if fittingboxproducts?? && fittingboxproducts?has_content]
					                [#if fittingboxproducts[variant.sku]]
					                	[#--  fittinboxinstance('${getId(product,variant)}') --]
									    <img class="product-virtual b2b-detalle-virtual-button" 
									         onclick="fitmixInstance.setFrame('${getId(product,variant)}'); openVto()" 
									         src="${resourcesURL!}/img/myshop/icons/icon-virtual.svg" 
									         alt="Prueba Virtual" 
								         />
								     [#else]
									    [#--  comprobamnos la master y el resto de variantes, si alguna lo tiene marcamos la prueba virtual en todas --]
								    	[#if fittingboxproducts[product.master.sku]]
						                	[#--  fittinboxinstance('${product.master.sku}') --]
										    <img class="product-virtual b2b-detalle-virtual-button" 
										         onclick="fitmixInstance.setFrame('${product.master.sku}'); openVto()" 
										         src="${resourcesURL!}/img/myshop/icons/icon-virtual.svg" 
										         alt="Prueba Virtual" 
									         />	
									    [#else]
									    	[#--  comprobamnos sus variantes si alguna lo tiene marcamos la prueba virtual en todas --]
									    	[#if product.variants?has_content]
									    		[#assign flag = true]
						        				[#list product.variants as variantfit]
						        					[#if fittingboxproducts[variantfit.sku] && flag]
						        						[#assign flag = false]
						        						<img class="product-virtual b2b-detalle-virtual-button" 
													         onclick="fitmixInstance.setFrame('${variantfit.sku}'); openVto()" 
													         src="${resourcesURL!}/img/myshop/icons/icon-virtual.svg" 
													         alt="Prueba Virtual" 
												         />	
						        					[/#if]
						        				[/#list]
						        			[/#if]
										[/#if]
									[/#if]
								[/#if]
				                [#-- END: prueba virtual --]
				                
				                [#assign mainImage = ""]
				                [#if variant.images?has_content]
									[#list variant.images as image]
										[#if image?is_first]
											[#assign mainImage = image.url!]
										[/#if]
									[/#list]
								[/#if]
				
								[#if !mainImage?has_content]
									[#assign mainImage = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/common/noproduct.png"]
								[/#if]
				                
				                <div class="product-image-wrapper">
									[#assign sku = '']
									[#assign link = '#']
									[#if content.internalLink?has_content && content.internalLink??]
										[#assign link = cmsfn.link("website", content.internalLink!)!]
										[#assign sku = getId(product,variant)!'ERROR']
										[#assign link = link + "?sku=" + model.encodeURIComponent(sku)!"#"]
										[#if pack_generico]
											
											[#assign link = link + "&skuPackMaster=" + model.encodeURIComponent(skuPackMaster)!"#"]
											[#assign link = link + "&step=" + step!]
										[/#if]
									[/#if]
									
				                	<a href="${link!"#"}" data-value="${sku}">
				                    	<img class="product-image" src="${mainImage!""}" alt="${variant.name!""}" />
			                    	</a>
				                </div>
				                
				                <div class="product-info-wrapper">
				                
				                    <div class="product-name" title="${variant.name!""}">${getTruncate(variant.name!"",64)!""}</div>
				                    [#if variant.nombreArticulo?? && variant.nombreArticulo?has_content 
				                    	&& (variant.name != variant.nombreArticulo)]
				                    		<div class="product-name" title="${variant.nombreArticulo!""}">${getTruncate(variant.nombreArticulo!"",64)!""}</div>
				                    [/#if]
				                    [#if variant.coleccion?? && variant.coleccion?has_content]
				                    	<div class="product-name" title="${variant.coleccion!""}">${getTruncate(variant.coleccion!"",64)!""}</div>
				                    [/#if]
				                    
				                    <div class="product-price-wrapper">
				                    	[#if familiaProducto != "packs"]
					                    	[#if variant.pvo?has_content && showPVO(ctx.getUser(), uuid, username)]
					                        <div class="product-price-item">
					                            <div class="product-price-text">${i18n['cione-module.templates.myshop.listado-productos-home-component.pvo']}</div>
					                            <div class="product-price-number ${strike!""}">#{variant.pvo!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
					                        </div>
					                        [/#if]
					                        
					                        [#if variant.discount?has_content && showPVO(ctx.getUser(), uuid, username)]
					                        <div class="product-price-item">
					                            <div class="product-price-text">${i18n['cione-module.templates.myshop.listado-productos-home-component.pvodto']}</div>
					                            <div class="product-price-number">#{variant.discount!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
					                        </div>
					                        [/#if]
				                        [#else]
						                	[#if variant.pvoSinPack?has_content && showPVO(ctx.getUser(), uuid, username)]
						                        <div class="product-price-item">
						                            <div class="product-price-text">${i18n['cione-module.templates.myshop.listado-productos-home-component.pvo']}</div>
						                            <div class="product-price-number product-price-strike">#{variant.pvoSinPack!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
						                        </div>
						                    [/#if]
						                    [#if variant.pvo?has_content && showPVO(ctx.getUser(), uuid, username)]
					                        <div class="product-price-item">
					                            <div class="product-price-text">${i18n['cione-module.templates.myshop.listado-productos-home-component.pvodto']}</div>
					                            <div class="product-price-number">#{variant.pvo!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
					                        </div>
					                        [/#if]
				                        [/#if]
				                        [#if variant.pvp?has_content && showPVP(ctx.getUser(), uuid, username)]
					                        <div class="product-price-item">
					                            <div class="product-price-text-pvp">${i18n['cione-module.templates.myshop.listado-productos-home-component.pvp']}</div>
					                            <div class="product-price-pvp">#{variant.pvp!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
					                        </div>
				                        [/#if]
				                        
				                    </div>
				                    
				                </div>
				                
								[#if product.master.color?has_content]
									[#if product.master.calibration?has_content]
										[#-- color + graduacion--]
										[@macrovariantcolorandcalibration product variant/]
									[#else]
										[#if product.master.calibre?has_content]
											[#-- color + calibre --]
											[@macrovariantcolorandcaliber product variant/]
										[#else]
											[#-- color --]
											[@macrovariantcolor product variant/]
										[/#if]
									[/#if]
								[#else]
									[#if product.master.calibre?has_content]
										[#-- calibre --]
										[@macrovariantcaliber product variant/]
									[#else]
										[#if product.master.calibration?has_content]
											[#-- calibration   --]
											[@macrovariantcalibration product variant/]
										[/#if]
									[/#if]
								[/#if]
								
								[@macrovarianttamanio product variant/]
								
								[#if variant.familiaproducto?has_content]
									[#if variant.familiaproducto != "accesorios" && product.master.familiaproducto?lower_case != "maquinaria"]
						                [@macrocompravariant product variant/]
									[/#if]
								[#else]
									[@macrocompravariant product variant/]
								[/#if]
				                
			            	</div>
			            	[#-- Obtener info de promociones 
			            	[#assign infoPromo = model.getVariantInfoPromociones(variant.sku)]
				            [#if infoPromo.getTipoPromocion() == "escalado"]
					        	[#assign listPromos = infoPromo.getListPromos()!]
					    		[#list listPromos as promo]
									<input type="hidden" name="listPromos_${variant.sku?replace(".", "")?replace("+", "")}" value="${promo.getCantidad_hasta()}|${promo.getPvoDto()}">
					    		[/#list]
					    		<input type="hidden" id="tipoPromo_${variant.sku?replace(".", "")?replace("+", "")}" value="escalado">
					    	[#else]
					    		<input type="hidden" id="tipoPromo_${variant.sku?replace(".", "")?replace("+", "")}" value="otro">
							[/#if]--]
			            [/#if]
				        [/#list]
				        
			        [/#if]
		            
		        </div>
	        </div>
		[/#list]
	                
		</div>
	                    
	    <div class="mt-4 mr-auto ml-auto d-flex justify-content-center">
			<div class="b2b-button-wrapper">
			    <button id="showmorebutton" class="b2b-button b2b-button-filter" onclick="showMore(this)">
			        ${i18n['cione-module.templates.myshop.listado-productos-filter-component.showmore']} <div class="spinner" style="display:none"></div>
			    </button>
			</div>
			<div class="b2b-listado-mostrar">
                <select name="" id="select-paginacion">
	                <option value="12">12</option>
	                <option value="50">50</option>
	                <option value="100">100</option>
                </select>
            </div>
	    </div>
    	
    	[#assign spare = false]
    	[#if content.spare?has_content && content.spare??]
    		[#assign spare = content.spare]
    	[/#if]
    
		[#if spare]
		
			[#assign link = '#']
			[#if content.internalLinkResult?has_content && content.internalLinkResult??]
				[#assign link = cmsfn.link("website", content.internalLinkResult!)!]
				[#if model.skuPadre?has_content && model.skuPadre??]
					[#assign skuPadre = model.skuPadre!'ERROR']
					[#assign link = link + "?sku=" + skuPadre]
				[/#if]
			[/#if]	
			
		
			<section class="b2b-no-repuesto">
			    <div class="b2b-no-repuesto-block">
			        <p class="b2b-no-repuesto-text">${i18n['cione-module.templates.myshop.listado-productos-filter-component.spare.nofoundquestion']}</p>
			        <a href="${link!"#"}" class="b2b-no-repuesto-link">${i18n['cione-module.templates.myshop.listado-productos-filter-component.spare.here']}</a>
			    </div>
			</section>
			
		[/#if]
    	
    [#else]
    	
    	[#assign spare = false]
    	[#if content.spare?has_content && content.spare??]
    		[#assign spare = content.spare]
    	[/#if]
    
		[#if spare]
		
			[#assign link = '#']
			[#if content.internalLinkResult?has_content && content.internalLinkResult??]
				[#assign link = cmsfn.link("website", content.internalLinkResult!)!]
				[#if model.skuPadre?has_content && model.skuPadre??]
					[#assign skuPadre = model.skuPadre!'ERROR']
					[#assign link = link + "?sku=" + skuPadre]
				[/#if]
			[/#if]
		
			<section class="b2b-no-repuesto">
			    <div class="b2b-no-repuesto-block">
			        <p class="b2b-no-repuesto-text">${i18n['cione-module.templates.myshop.listado-productos-filter-component.spare.nofound']}</p>
			        <a href="${link!"#"}" class="b2b-no-repuesto-link">${i18n['cione-module.templates.myshop.listado-productos-filter-component.spare.request']}</a>
			    </div>
			</section>
			
		[#else]
			<section class="b2b-no-repuesto">
			    <div class="b2b-no-repuesto-block">
			        <p class="b2b-no-repuesto-text">${i18n['cione-module.templates.myshop.listado-productos-filter-component.nofound']}</p>
			    </div>
			</section>
		[/#if]
    [/#if]
    

[#------------------------------------------------------------------]
[#----------------------------- SCRIPT -----------------------------]
[#------------------------------------------------------------------]


[#if products?has_content]
<script>

	[#if fittingboxproducts?? && fittingboxproducts?has_content]
		
			$(document).ready(function(){
				console.log("createWidget listado-productos");
				window.fitmixInstance = FitMix.createWidget('fitmixContainer', params, function() {
		        	console.log('VTO module is ready listado-productos.');
				});
			});
		
	[/#if]
	
	var total = ${total?c};
	var page = ${page?c};
	var count = ${count?c};
	[#-- pages go 0 to n, hence -1 --]
	var totalpages = Math.ceil(${total?c}/12)-1;

	function addCart(sku,that,ref,num) {
	
		var refclient = '';
		if (ref == '' || ref == null){
			refclient = sessionStorage.getItem('refclient');
		}else{
			refclient = ref;
		}
		
		if(that != null){
			closeModal(that);
		}
		
		if(refclient != '' && refclient != null){
			console.log("Add cart: " + sku + " Units: " + num + " and ref: " + refclient);
			[#-- llama al servicio con las variables sku y refclient --]
			addtoCartList(sku, num, refclient);
		}else{
			console.log("Add cart: " + sku + " Units: " + num);
			[#-- llama al servicio solo con la variable sku --]
			addtoCartList(sku, num, "")
		}
		
		sessionStorage.setItem('refclient', '');
	}
	
	function addtoCartList(sku, num, refCliente) {
		var coloraudio = $( "#coloraudio-" + sku).val();
    	
    	var sustitutiveProduct = '';
    	if ($( "#sustitutiveProduct-" + sku + " option:checked" ).val() != null && $( "#sustitutiveProduct-" + sku + " option:checked" ).val() != "") {
			sustitutiveProduct = $( "#sustitutiveProduct-" + sku + "  option:checked" ).val();
		}
		if (($("#sustitutiveProduct-" + sku + " ").val() != null) && ($("#sustitutiveProduct-" + sku + " ").val() != '')) {
			sustitutiveProduct = $("#sustitutiveProduct-" + sku + " ").val();
		}
		
		var filter = JSON.stringify({
        	"sku": sku,
        	"cantidad": num,
        	"refCliente": refCliente,
        	"colorAudio": coloraudio,
        	"sustitutiveProduct": sustitutiveProduct,
    	});
    	
    	$("#sustitutiveProduct-" + sku).val('');
		$("#sustitutiveProduct-" + sku).prop('checked',false);
    
        $("#loading").show();
        $(".product-button").attr("disabled", "disabled");
        $.ajax({
            url : "${ctx.contextPath}/.rest/private/carrito/v1/carts-addItemList",
            type : "POST",
            data : filter,
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            success : function(response) {
            
            	$("#fly-card").css("display","block");
				
				setTimeout(function(){ 
				$("#fly-card").css("display","none");
				}, 5000);
				
                var KK = response;
                
                var numAux = parseInt(num);
				if (typeof mapCarrito.get(sku) !== 'undefined') {
					numAux += parseInt(mapCarrito.get(sku));
				}
				mapCarrito.set(sku, numAux);
				console.log(mapCarrito);
				
				refrescarPopupCarrito(response);
				let respuesta = actualizarLineItems(response, sku);
				
				refrescarPopupCarrito(respuesta);
				
            },
            error : function(response) {
                alert("error");             
                //$("#change-pwd-result").html("<p>" +  response.responseJSON.error.message + "</p>");
            },
            complete : function(response) {
                $("#loading").hide();
                $(".product-button").removeAttr("disabled");
            }
        }); 
        return false;
    }
    
    function addProductOrigin(sku,that,ref,num) {
		$("#sustitutiveProduct-" + sku).val('');
		addCart(sku,that,ref,num);
	}
	
	function closeModal(that) {
		$(that).parent().parent().parent().css("display","none");
		$('span#unitsselected').text("");
		$('span#unitscart').text("");
		sessionStorage.setItem('refclient', '');
	}
	
	function closeModalCross(that) {
		$('span#unitsselected').text("");
		$('span#unitscart').text("");
		sessionStorage.setItem('refclient', '');
	}
	
	function closeModalReplacements(sku) {
		var idModal = sku.replace(".", "");
		idModal = idModal.replace("+", "");
		idModal = idModal.replace("-", "");
		$("#modal-replacement-detail-" + idModal).css("display","none");
		$("#sustitutiveProduct-" + idModal).val('');
		$("#sustitutiveProduct-" + idModal).prop('checked',false);
	}
	
	function generateReplacementModal(sku, cant) {
		console.log(sku);
		console.log(cant);
		var res = false;
		
		var idModal = sku.replace(".", "");
		idModal = idModal.replace("+", "");
		idModal = idModal.replace("-", "");
		$.ajax({
			url : "${ctx.contextPath}/.rest/private/stock/replacements?sku=" +  encodeURIComponent(sku) + "&cant=" + cant,
            type : "GET",
            contentType : 'application/json; charset=utf-8',
            cache : false,
            async: false,
            dataType : "json",
	        success: function(response) {
	        	var html = "";
	        	var mapReplacements = new Map();
	        	var resMap = response.replacements;
	        	for (var key in resMap) {
	        		mapReplacements.set(key, resMap[key]);
	        	}
	        	if (mapReplacements.size == 1) {
	        		var html = '';
	        		
		        	for (var key in resMap) {
					    if (resMap.hasOwnProperty(key)) {
					    	console.log(key);
					    	console.log(resMap[key]);
					    	html += resMap[key];
					    	html += '<input type="hidden" id="sustitutiveProduct-' + idModal + '" name="sustitutiveProduct" value="' + key +'" />';
					    }
					}
					$("#listReplacement-" + idModal).html(html);	
                    $("#modal-replacement-detail-" + idModal).css("display","flex");
                    res = true;
	        	} else if (mapReplacements.size > 1){
	        		
	        		html += '<select name="" id="sustitutiveProduct-' + idModal + '" autocomplete="off">';
		        	for (var key in resMap) {
					    if (resMap.hasOwnProperty(key)) {
					    	console.log(key);
					    	console.log(resMap[key]);
					    	html += '<option value="' + key + '">' + resMap[key] + '</option>';
					    }
					}
					html += '</select>';
					$("#listReplacement-" + idModal).html(html);
                    $("#modal-replacement-detail-" + idModal).css("display","flex");
                    res = true;
	        	} 
	        },
	        error: function(response) { 
	        	console.log("Error al recuperar los productos sustitutivos"); 
	        }
		});
		
		return res;
	}
	
	function getUrlAndParametersNoFilter(){
		const queryString = window.location.search;
		//console.log("location = " + queryString); 
		//console.log(queryString);
		var url = (window.location.href).split('?')[0] + '?';
		
		const urlParams = new URLSearchParams(queryString);
		var keys = urlParams.keys();
  		var values = urlParams.values();
  		var entries = urlParams.entries();
  		
  		//for (const key of keys) console.log(key);
		
		//for (const value of values) console.log(value);
		
		var first= true;
  		for(const entry of entries) {
		  console.log(entry[0], entry[1]);
		  if (entry[0] != 'sorting') {
		  	if (first) {
		  		url += entry[0] + "=" + entry[1];
		  		first = false;
		  	} else {
		  		url += '&' + entry[0] + "=" + entry[1];
		  	}
		  }
		}
		
		return url;
	}
	
	
	function getUrlAndParameters(){
			
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
		
		var first= true;
		var url = (window.location.href).split('?')[0] ;
		if (getUrlParameter('category')){
			first = false;
			url += '?category=' + getUrlParameter('category');
		}
		//variants.price.centAmount
		if($('#PRICEACTIVE').data('value')){
			var pricefilter = '&variants.price.centAmount=range( '+ ($('#PVPMIN').data('value')*100) + ' to ' + ($('#PVPMAX').data('value')*100) +' )';
			url += pricefilter;
		}
		
		
		[#-- filtros visibles: todos los que esten seleccionados se aniadiran al buscar mas --]
		
	    $('.b2b-tab-check:checkbox:checked').each(function () {
	    	if (first) {
	    		first = false;
	    		url += ('?' + $(this).data('value') + '=' + encodeURIComponent($(this).data('label')));
	    	} else {
	    		url += ('&' + $(this).data('value') + '=' + encodeURIComponent($(this).data('label')));
	    	}
		});
		
		
		[#-- filtros ocultos: los otros filtros que esten en la url y sean tenidos en cuenta deben
			 estar aqui para que vayan en el mostrar mas --]
		
		if (getUrlParameter('variants.attributes.tamanios.es')){
			url += '&variants.attributes.tamanios.es' + '=' + getUrlParameter('variants.attributes.tamanios.es');
		}
		
		if (getUrlParameter('variants.attributes.aliasEkon')){
			url += '&variants.attributes.aliasEkon' + '=' + getUrlParameter('variants.attributes.aliasEkon');
		}
		
		if (getUrlParameter('variants.attributes.statusEkon')){
			url += '&variants.attributes.statusEkon' + '=' + getUrlParameter('variants.attributes.statusEkon');
		}
		
		if (getUrlParameter('variants.attributes.tipoCristal')){
			url += '&variants.attributes.tipoCristal' + '=' + getUrlParameter('variants.attributes.tipoCristal');
		}
		
		if (getUrlParameter('variants.attributes.skuPadre')){
			url += '&variants.attributes.skuPadre' + '=' + getUrlParameter('variants.attributes.skuPadre');
		}
		
		if (getUrlParameter('skupack')){
			url += '&skupack' + '=' + getUrlParameter('skupack');
		}
		
		if (getUrlParameter('variants.attributes.azulGraduado')){
			url += '&variants.attributes.azulGraduado' + '=' + getUrlParameter('variants.attributes.azulGraduado');
		}
		
		if (getUrlParameter('variants.attributes.coleccionModa')){
			url += '&variants.attributes.coleccionModa' + '=' + getUrlParameter('variants.attributes.coleccionModa');
		}
		
		if (getUrlParameter('variants.attributes.marca')){
			url += '&variants.attributes.marca' + '=' + getUrlParameter('variants.attributes.marca');
		}
		
		if (getUrlParameter('variants.attributes.pruebaVirtual')){
			url += '&variants.attributes.pruebaVirtual' + '=' + getUrlParameter('variants.attributes.pruebaVirtual');
		}
		
		if (getUrlParameter('variants.attributes.tienePromociones')){
			url += '&variants.attributes.tienePromociones' + '=' + getUrlParameter('variants.attributes.tienePromociones');
		}
		
		if (getUrlParameter('variants.attributes.tipoPack')){
			url += '&variants.attributes.tipoPack' + '=' + getUrlParameter('variants.attributes.tipoPack');
		}
		
		if (getUrlParameter('variants.attributes.clipSolar')){
			url += '&variants.attributes.clipSolar' + '=' + getUrlParameter('variants.attributes.clipSolar');
		}
		if (getUrlParameter('variants.attributes.clipSolar')){
			url += '&variants.attributes.clipSolar' + '=' + getUrlParameter('variants.attributes.clipSolar');
		}
		if (getUrlParameter('variants.attributes.codigoCentral')){
			url += '&variants.attributes.codigoCentral' + '=' + getUrlParameter('variants.attributes.codigoCentral');
		}
		if (getUrlParameter('skuPackMaster')){
			url += '&skuPackMaster' + '=' + getUrlParameter('skuPackMaster');
		}
		if (getUrlParameter('step')){
			url += '&step' + '=' + getUrlParameter('step');
		}
		
		
		return url;
	}
	
	[#------------------------------------------------------------------]
	[#------------------- MOSTRAR MAS FUNCIONALIDAD --------------------]
	[#------------------------------------------------------------------]
	
	function showMore(that) {
	
		//if ($("#filtros-pills-wrapper").length == 0) {
			//var url = getUrlAndParametersNoFilter();
		//} else {
			var url = getUrlAndParameters();
		//}
		
		
		if($("#selectorder").val() == 'new'){
			url += "&sorting=newaltaekon"
		} else if($("#selectorder").val() == 'lowtohigh'){
			url += "&sorting=lowtohigh"
		} else if($("#selectorder").val() == 'hightolow'){
			url += "&sorting=hightolow"
		} else if($("#selectorder").val() == 'alfabetico'){
			url += "&sorting=alfabetico"
		}
		
		if (page == 0){
			page = 1;
			count = ${numProduct!};
		}
		pagination = $('#select-paginacion option:checked').val();
		
		url += "&page=" + page;
		url += "&pagination=" + pagination;
		url += "&count=" + count;
		
		
		$.ajax({
			url : url,
			type : "GET",
			cache : false,  
			beforeSend: function( xhr ) {
				$(".spinner").css("display","block");
			},
			success : function(response) {

				var responsecount = $("#countproducts", response).data('value');
				page += 1;
				count += responsecount;
				
				$(".b2b-listado-monturas").append($(".b2b-listado-monturas", response).html()); 
				$("#productslabel").empty();
				$("#productslabel").append("Mostrando " + count + " de ${total} productos");
				
				
				var parser      = new DOMParser ();
			    var responseDoc = parser.parseFromString (response.responseText, "text/html");
			    var arr = responseDoc.documentElement.getElementsByTagName('script')
				let totalListados = document.querySelectorAll(".b2b-product-card");
				if (totalListados.length == total){
					$(that).css("display","none");
					$(".b2b-listado-mostrar").css("display","none");
				} 
				
			},
			error : function(response) {
				console.log("ERROR");            
			   
			},
			complete : function(response) {
				$(".spinner").css("display","none");
			}
		});
        

	}
	
	function amountProductPlus(that){
		
		var count = parseInt($(that).parent().find('.product-amount-input').val());
		count = parseInt(count) + 1;
		$('span#unitsselected').text(count);
		
		if (count > 0) {
	        $(that).parent().find('.product-amount-input').val(count);
	    }
	}
	
	function toggleStock(that,aliasEkon){
		
		if($(that).hasClass("selected")){
		
			$(that).toggleClass('selected');
			
		}else{
			var aliasEkonEncode = encodeURIComponent(aliasEkon);
			var urlstock = "${ctx.contextPath}/.rest/private/stock?sku=" + aliasEkonEncode;
		
			$.ajax({
				url : urlstock,
				type : "GET",
				cache : false,  
				success : function(response) {
					
					var stockreallabel = "${i18n['cione-module.templates.myshop.listado-productos-home-component.only']} ";
					var stockcanariaslabel = "${i18n['cione-module.templates.myshop.listado-productos-home-component.canar']}";
					var stockcentrallabel = "${i18n['cione-module.templates.myshop.listado-productos-home-component.ctral']}";
					var stockunidadeslabel = " ${i18n['cione-module.templates.myshop.listado-productos-home-component.units']}";
					
					if (response.almacen == 'stockCANAR'){
						$(that).children().find('.stock-text').html(stockcanariaslabel + response.stock + stockunidadeslabel + "<br\>" + stockcentrallabel + response.stockCTRAL + stockunidadeslabel);
						
					}else{
						$(that).children().find('.stock-text').text(stockreallabel + response.stock + stockunidadeslabel);
					} 
					
					$(that).toggleClass('selected');
					$(that).children().find('input').attr("value",response.stock);
				},
				error : function(response) {
				
					$(that).children().find('.stock-text').text("Error al solicitar el stock");
					$(that).toggleClass('selected');           
				   
				}
			});
		}
	}
	
	function amountProductMinus(that){
		
		var count = parseInt($(that).parent().find('.product-amount-input').val());
		count = parseInt(count) - 1;
		$('span#unitsselected').text(count);
		
		if (count > 0) {
	        $(that).parent().find('.product-amount-input').val(count);
	    }
	}

	$(document).ready(function(){
		
		setSelectValue();
		
		function setSelectValue(){
		
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
			
			var strsorting = getUrlParameter('sorting');
			
			if (strsorting != null){
				
				switch (strsorting) {
				  case "newaltaekon":
				  	$("#selectorder option[value=new]").prop("selected", true);
				    break;
				  case "alfabetico":
				  	$("#selectorder option[value=alfabetico]").prop("selected", true);
				    break;
				  case "hightolow":
				  	$("#selectorder option[value=hightolow]").prop("selected", true);
				    break;
				  case "lowtohigh":
				  	$("#selectorder option[value=lowtohigh]").prop("selected", true);
				    break;
				  default:
				  	$("#selectorder option[value=cleanorder]").prop("selected", true);
				    break;
				}
			}
		}
	
		$('.selectorder').change(function(){
		
			if ($("#filtros-pills-wrapper").length == 0) {
				var url = getUrlAndParametersNoFilter();
			} else {
				var url = getUrlAndParameters();
			}
			
			
			if($(this).val() == 'new'){
				url += "&sorting=newaltaekon"
			} else if($(this).val() == 'alfabetico'){
				url += "&sorting=alfabetico"
			} else if($(this).val() == 'lowtohigh'){
				url += "&sorting=lowtohigh"
			} else if($(this).val() == 'hightolow'){
				url += "&sorting=hightolow"
			}
			
			window.location.href = url;
		  
		});
	
		[#-- control inicial del boton de mosotrar mas --]
		checkButtonShowMore();
		
		function checkButtonShowMore(){
			
			if (count == total){
				$("#showmorebutton").css("display","none");
				$("#select-paginacion").css("display","none");
				$(".b2b-listado-mostrar").css("display","none");
			}
		
		}
		
		$('.modal-purchase-close-img').on("click", function () {
			$(this).parent().parent().parent().parent().css("display","none");
			sessionStorage.setItem('refclient', '');
		});

	});
	
</script>
[/#if]

[#------------------------------------------------------------------]
[#----------------------------- MACROS -----------------------------]
[#------------------------------------------------------------------]

[#macro macrocolor product]
	[#if product.colorsdto?has_content]
		<div class="product-color-select">
			[#list product.colorsdto as color]
				<div data-value="${color.sku}"
					 class="product-color-circle [#if color.selected] selected [/#if] hover-text"
					 style="background-color:${color.colorIcono!"#ffffff ; background: linear-gradient(-45deg, white 12px, black 15px, black 15px, white 17px )"};">
					 <span class="tooltip-text" id="tooltip-${color.sku}">${color.codigoColor!}-${color.colorMontura!}</span>
				</div>
			[/#list]
		</div>
	[/#if]
	
	
	[#--  --if product.colors?has_content]
		<div class="product-color-select">
		[#list product.colors as color]
			[#if product.master.color?has_content && product.master.color = color]
				<div data-value="${product.getSkuByColor(color)!""}"
					 class="product-color-circle selected hover-text"
					 style="${getRealColor(color!"001#000000")!"background-color:#ffffff;"}">
					 <span class="tooltip-text" id="tooltip-${product.master.sku}">${product.master.codigocolor!}-${product.master.colorMontura!}</span>
				</div>
			[#else]
				<div data-value="${product.getSkuByColor(color)!""}"
					 class="product-color-circle hover-text"
					 style="${getRealColor(color!"001#000000")!"background-color:#ffffff;"}">
					 <span class="tooltip-text" id="tooltip-${product.master.sku}">${product.master.codigocolor!}-${product.master.colorMontura!}</span>
				</div>
			[/#if]
		[/#list]
		</div>
	[/#if --]
[/#macro]

[#macro macrocaliber product]
	[#if product.calibers?has_content]
		<div class="product-calibre-wrapper">
		[#list product.calibers as caliber]
			[#if product.master.calibre?has_content && product.master.calibre = caliber]
				<div data-value="${product.getSkuByCaliber(caliber)!""}" class="product-calibre selected">${caliber!""}MM</div>
			[#else]
				<div data-value="${product.getSkuByCaliber(caliber)!""}" class="product-calibre">${caliber!""}MM</div>
			[/#if]
		[/#list]
		</div>
	[/#if]
[/#macro]

[#macro macrocalibration product]
	[#if product.calibrations?has_content]
		<div class="product-combo">
        <div class="product-combo-title">Graduación</div>
        <select name="" id="" class="select-calibration">
		[#list product.calibrations as calibration]
			[#if product.master.calibration?has_content && product.master.calibration = calibration]
				<option value="${product.getSkuByCalibration(calibration)!""}" selected>${calibration!""}</option>
			[#else]
				<option value="${product.getSkuByCalibration(calibration)!""}">${calibration!""}</option>
			[/#if]
		[/#list]
		 </select>
		</div>
	[/#if]
[/#macro]

[#macro macrotamanio product]
	[#if product.master.familiaproducto?has_content]
		[#if product.master.familiaproducto == "liquidos"]
			[#if product.tamanios?has_content]
				<div class="product-combo product-listado">
	                <select name="" id="" class="select-tamanio">
						[#list product.tamanios as tamanio]
							[#if product.master.tamanio?has_content && product.master.tamanio = tamanio]
								<option value="${product.getSkuByTamanio(tamanio)!""}" selected>${tamanio!""}</option>
							[#else]
								<option value="${product.getSkuByTamanio(tamanio)!""}">${tamanio!""}</option>
							[/#if]
						[/#list]
	                </select>
	            </div>
			[/#if]
        [/#if]
	[/#if]
[/#macro]

[#macro macrocoloraudio product]
	[#if product.master.familiaproducto?has_content]
		[#if product.master.familiaproducto == "audifonos"]
			[#if product.master.colorsAudio?has_content]
				<div class="product-combo product-listado">
	                <select name="" id="coloraudio-${product.master.sku}" class="select-colorAudio">
						[#list product.master.colorsAudio as colorAudio]
							<option value="${colorAudio!""}">${colorAudio!""}</option>
						[/#list]
	                </select>
	            </div>
			[/#if]
        [/#if]
	[/#if]
[/#macro]

[#macro macrocolorandcaliber product]

	[#if product.master.color?has_content]
	
		[@macrocolor product/]
		
		[#if product.getCalibersByColor(product.master.color)?has_content]
			<div class="product-calibre-wrapper">
			[#list product.getCalibersByColor(product.master.color) as caliber]
				[#if product.master.calibre?has_content && product.master.calibre = caliber]
					<div data-value="${product.getSkuByColorAndCaliber(product.master.color, caliber)!""}" class="product-calibre selected">${caliber!""}MM</div>
				[#else]
					<div data-value="${product.getSkuByColorAndCaliber(product.master.color, caliber)!""}" class="product-calibre">${caliber!""}MM</div>
				[/#if]
			[/#list]
			</div>
		[/#if]
		
	[/#if]
	
[/#macro]

[#macro macrocolorandcalibration product]

	[#if product.master.color?has_content]
	
		[@macrocolor product/]
		
		[#if product.getCalibrationsByColor(product.master.color)?has_content]
			<div class="product-combo">
		    <div class="product-combo-title">Graduación</div>
		    <select name="" id="" class="select-calibration">
				[#list product.getCalibrationsByColor(product.master.color) as calibration]
					[#if product.master.calibration?has_content && product.master.calibration = calibration]
						<option value="${product.getSkuByColorAndCalibration(product.master.color, calibration)!""}" selected>${calibration!""}</option>
					[#else]
						<option value="${product.getSkuByColorAndCalibration(product.master.color, calibration)!""}">${calibration!""}</option>
					[/#if]
				[/#list]
		    </select>
			</div>
		[/#if]
	[/#if]

[/#macro]

[#macro macrocompra product]
	
    <div class="product-amount">

    	<div class="product-amount-button-wrapper" [#if pack_generico] style="visibility: hidden;" [/#if]>
            <button class="product-amount-button product-amount-button-minus" type="button" [#if pack_generico] disabled [/#if] onclick="amountProductMinus(this)">
                -
            </button>
            <input type="text" class="product-amount-input" min="1" max="999999" value="1" [#if pack_generico] disabled [/#if]>
            <button class="product-amount-button product-amount-button-plus" type="button" [#if pack_generico] disabled [/#if] onclick="amountProductPlus(this)">
                +
            </button>
        </div>
        [#if product.master.gestionStock && product.master.aliasEKON?? && product.master.aliasEKON?has_content]
        <button class="product-stock" style="cursor: pointer;" type="button" onclick="toggleStock(this,'${product.master.aliasEKON?trim}')">
            ${i18n['cione-module.templates.myshop.listado-productos-home-component.stock']}
            <span class="product-stock-tooltip">
                <div class="arrow-up"></div>
                <p style="float: right">X</p>
                <p>${product.master.name!""}</p>
                <p class="stock-text"></p>
                <input type="hidden" id="stock_${product.master.sku?replace(".", "")?replace("+", "")?replace("-", "")}" value=""> 
                
            </span>
        </button>
		[/#if]
    </div>
	
    <div class="product-button-wrapper">
    	[#if pack_generico && product.master.isIncluidoEnPack()]
    		<div class="hover-text" data-value="${product.master.sku}">
	    		<span class="tooltip-text" id="tooltip-${product.master.sku}" >${i18n['cione-module.templates.myshop.listado-productos-home-component.info-select-button']}</span>
	    	
		        <button class="UUID${content["jcr:uuid"]!""} product-button">
		        	${i18n['cione-module.templates.myshop.listado-productos-home-component.select']}
		        </button> 
	        </div>
	        
        [#else]
        	<button class="UUID${content["jcr:uuid"]!""} product-button">
	        	${i18n['cione-module.templates.myshop.listado-productos-home-component.buy']}
	        </button> 
        [/#if]
        
        
        
    </div>

	[#if !pack_generico]
	    <div class="product-reference">
	        <label>${i18n['cione-module.templates.myshop.listado-productos-home-component.ref']}</label>
	        <input type="text" class="product-ref-input">
	    </div>
    [/#if]

[/#macro]

[#-- marcro variantes --]

[#macro macrovariantcolor product variant]
	[#if product.colorsdto?has_content]
		<div class="product-color-select">
			[#list product.colorsdto as color]
				<div data-value="${color.sku}"
					 class="product-color-circle [#if color.sku == variant.sku] selected [/#if] hover-text"
					 style="background-color:${color.colorIcono!"#ffffff ; background: linear-gradient(-45deg, white 12px, black 15px, black 15px, white 17px )"};">
					 <span class="tooltip-text" id="tooltip-${color.sku}">${color.codigoColor!}-${color.colorMontura!}</span>
				</div>
			[/#list]
		</div>
	[/#if]



	[#--  --if product.colors?has_content]
		<div class="product-color-select">
		[#list product.colors as color]
			[#if variant.color?has_content && variant.color = color]
				<div data-value="${product.getSkuByColor(color)!""}"
					 class="product-color-circle selected hover-text"
					 style="${getRealColor(color)!"background-color:#ffffff;"}">
					 <span class="tooltip-text" id="tooltip-${getId(product,variant)!"ERROR"}">${variant.codigocolor!}-${product.master.colorMontura!}</span>
				</div>
			[#else]
				<div data-value="${product.getSkuByColor(color)!""}"
					 class="product-color-circle hover-text"
					 style="${getRealColor(color)!"background-color:#ffffff;"}">
					 <span class="tooltip-text" id="tooltip-${getId(product,variant)!"ERROR"}">${variant.codigocolor!}-${product.master.colorMontura!}</span>
				</div>
			[/#if]
		[/#list]
		</div>
	[/#if--]
[/#macro]

[#macro macrovariantcaliber product variant]
	[#if product.calibers?has_content]
		<div class="product-calibre-wrapper">
		[#list product.calibers as caliber]
			[#if variant.calibre?has_content && variant.calibre = caliber]
			<div data-value="${product.getSkuByCaliber(caliber)!""}" 
				 class="product-calibre selected">${caliber!""}MM</div>
			[#else]
				<div data-value="${product.getSkuByCaliber(caliber)!""}" 
					 class="product-calibre">${caliber!""}MM</div>
			[/#if]
		[/#list]
		</div>
	[/#if]
[/#macro]

[#macro macrovariantcalibration product variant]
	[#if product.calibrations?has_content]
		<div class="product-combo">
        <div class="product-combo-title">Graduación</div>
        <select name="" id="" class="select-calibration">
		[#list product.calibrations as calibration]
			[#if variant.calibration?has_content && variant.calibration = calibration]
				<option value="${product.getSkuByCalibration(calibration)!""}" selected>${calibration!""}</option>
			[#else]
				<option value="${product.getSkuByCalibration(calibration)!""}">${calibration!""}</option>
			[/#if]
		[/#list]
		 </select>
		</div>
	[/#if]
[/#macro]

[#macro macrovarianttamanio product variant]
	[#if variant.familiaproducto?has_content]
		[#if variant.familiaproducto == "liquidos"]
			[#if product.tamanios?has_content]
				<div class="product-combo product-listado">
	                <select name="" id="" class="select-tamanio">
						[#list product.tamanios as tamanio]
							[#if variant.tamanio?has_content && variant.tamanio = tamanio]
								<option value="${product.getSkuByTamanio(tamanio)!""}" selected>${tamanio!""}</option>
							[#else]
								<option value="${product.getSkuByTamanio(tamanio)!""}">${tamanio!""}</option>
							[/#if]
						[/#list]
	                </select>
	            </div>
			[/#if]
        [/#if]
	[/#if]
[/#macro]

[#macro macrovariantcolorandcaliber product variant]

	[#if product.master.color?has_content]
	
		[@macrovariantcolor product variant/]
		
		[#if product.getCalibersByColor(variant.color)?has_content]
			<div class="product-calibre-wrapper">
			[#list product.getCalibersByColor(variant.color) as caliber]
				[#if variant.calibre?has_content && variant.calibre = caliber]
					<div data-value="${product.getSkuByColorAndCaliber(variant.color, caliber)!""}" class="product-calibre selected">${caliber!""}MM</div>
				[#else]
					<div data-value="${product.getSkuByColorAndCaliber(variant.color, caliber)!""}" class="product-calibre">${caliber!""}MM</div>
				[/#if]
			[/#list]
			</div>
		[/#if]
		
	[/#if]
	
[/#macro]

[#macro macrovariantcolorandcalibration product variant]

	[#if product.master.color?has_content]
	
		[@macrovariantcolor product variant/]
		
		[#if product.getCalibrationsByColor(variant.color)?has_content]
			<div class="product-combo">
		    <div class="product-combo-title">Graduación</div>
		    <select name="" id="" class="select-calibration">
				[#list product.getCalibrationsByColor(variant.color) as calibration]
					[#if variant.calibration?has_content && variant.calibration = calibration]
						<option value="${product.getSkuByColorAndCalibration(variant.color, calibration)!""}" selected>${calibration!""}</option>
					[#else]
						<option value="${product.getSkuByColorAndCalibration(variant.color, calibration)!""}">${calibration!""}</option>
					[/#if]
				[/#list]
		    </select>
			</div>
		[/#if]
	[/#if]

[/#macro]

[#macro macrocompravariant product variant]
	<div class="product-amount">
					                
        <div class="product-amount-button-wrapper" [#if pack_generico] style="visibility: hidden;" [/#if]>
            <button class="product-amount-button product-amount-button-minus" type="button" [#if pack_generico] disabled [/#if] onclick="amountProductMinus(this)">
                -
            </button>
            <input type="text" class="product-amount-input" min="1" max="999999" value="1" [#if pack_generico] disabled [/#if]>
            <button class="product-amount-button product-amount-button-plus" type="button" [#if pack_generico] disabled [/#if] onclick="amountProductPlus(this)">
                +
            </button>
        </div>
        
        [#if variant.gestionStock && variant.aliasEKON?? && variant.aliasEKON?has_content]
	        <button class="product-stock" style="cursor: pointer;" type="button" onclick="toggleStock(this,'${variant.aliasEKON?trim}')">
	            ${i18n['cione-module.templates.myshop.listado-productos-home-component.stock']}
	            <span class="product-stock-tooltip">
	                <div class="arrow-up"></div>
	                <p style="float: right">X</p>
	                <p>${variant.name!""}</p>
	                <p class="stock-text"></p>
	                <input type="hidden" id="stock_${variant.sku?replace(".", "")?replace("+", "")?replace("-", "")}" value="">
	            </span>
	        </button>
		[/#if]
    </div>
    
     <div class="product-button-wrapper">
    	[#if pack_generico && variant.isIncluidoEnPack()]
    		<div class="hover-text" data-value="${variant.sku}">
	    		<span class="tooltip-text" id="tooltip-${variant.sku}" >${i18n['cione-module.templates.myshop.listado-productos-home-component.info-select-button']}</span>
	    	
		        <button class="UUID${content["jcr:uuid"]!""} product-button">
		        	${i18n['cione-module.templates.myshop.listado-productos-home-component.select']}
		        </button> 
	        </div>
        [#else]
        	<button class="UUID${content["jcr:uuid"]!""} product-button">
	        	${i18n['cione-module.templates.myshop.listado-productos-home-component.buy']}
	        </button> 
        [/#if]
    </div>
	[#if !pack_generico] 
	    <div class="product-reference">
	        <label>${i18n['cione-module.templates.myshop.listado-productos-home-component.ref']}</label>
	        <input type="text" class="product-ref-input">
	    </div>
	[/#if]
[/#macro]

[#macro macromodalPack linkPack skuPack]

<div id="myModal-${skuPack}" class="modal-purchase" style="display: none;">
	<div class="modal-purchase-box">
		<div class="modal-purchase-header">
            <p>CONFIGURACION PACK</p>
            <div class="modal-purchase-close">
                <img class="modal-purchase-close-img" src="${closeimg!""}" alt="cerrar" onclick="closeModalPack('${skuPack}')">
            </div>
        </div>
        <div class="modal-purchase-info">
        	<div id="modal-body-${skuPack}">
        	</div>
        </div>
	</div>
</div>

[/#macro] 

[#macro macromodalreplacements master]
	[#-- modal para repuestos --]
	
		[#assign skurepl = master.sku]
		<div id="modal-replacement-detail-${skurepl?replace(".", "")?replace("+", "")?replace("-", "")!""}" class="modal-purchase" style="display: none;">
		    <div class="modal-purchase-box">
		    
		        <div class="modal-purchase-header">
		            <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.nostock']}</p>
		            <div class="modal-purchase-close">
		                <img class="modal-purchase-close-img" src="${closeimg!""}" alt="cerrar" onclick="closeModalReplacements('${master.sku!""}')">
		            </div>
		        </div>
		        <div class="modal-purchase-info">
		        	<div style="font-weight: bold;">
		        		<p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.noproduct']}</p>
		        	</div>
		            <div>
		                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.replacement']} ${master.name}</p>
		                <p style="display: flex; padding: 10px 0 10px 0;"><span>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.available']}</span>
			                <span id="listReplacement-${skurepl?replace(".", "")?replace("+", "")?replace("-", "")!""}" style="padding-left: 10px;">
				                
			                </span>
		                </p>
		                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.continue']}</p>
		                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.question']}</p>
		            </div>
		        </div>
		        <div class="modal-purchase-footer">
		            <button class="modal-purchase-button modal-purchase-button--transparent" type="button" onclick="closeModalReplacements('${master.sku!""}')">
		                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}
		            </button>
		            <button class="modal-purchase-button" type="button" onclick="addProductOrigin('${master.sku!""}',this,'',$('span#unitsselected').html())">
		                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.addOriginal']} 
		            </button>
		            
		            <button class="modal-purchase-button" type="button" onclick="addCart('${master.sku!""}',this,'',$('span#unitsselected').html())">
		                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.addSustitutivo']}
		            </button>
		        </div>
		
		    </div>
		
		</div>
[/#macro]

[#macro macromodal sku delivery]

	[#assign closeimg = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/icons/close-thin.svg"]
	<div class="modal-purchase ${sku?replace(".", "")?replace("+", "")?replace("-", "")!""} UUID${content["jcr:uuid"]!""}" style="display: none;">
	
	    <div class="modal-purchase-box">
	    
	        <div class="modal-purchase-header">
	            <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.nostock']}</p>
	            <div class="modal-purchase-close">
	                <img class="modal-purchase-close-img" src="${closeimg!""}" alt="cerrar" onclick="closeModalCross(this)">
	            </div>
	        </div>
	        
	        <div class="modal-purchase-info">
	            <div>
	                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.noproduct']}</p>
	            </div>
	            <div>
	                [#-- <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.delivery']} ${delivery!""} ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.days']}</p> --]
	                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.delivery']} ${i18n['cione-module.templates.components.plazo-proveedor']}</p>
	                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.for']}  <span id="unitsselected"></span> ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}<span id="unitscart"></span></p>
	                <p class="stock_modal"></p>
	            </div>
	        </div>
	        
	        <div class="modal-purchase-footer">
	            <button class="modal-purchase-button modal-purchase-button--transparent" type="button" onclick="closeModal(this)">
	                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}
	            </button>
	            <button class="modal-purchase-button" type="button" onclick="addCart('${sku!""}',this,'',$('span#unitsselected').html())">
	                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.add']}
	            </button>
	        </div>
	
	    </div>
	
	</div>  

[/#macro]
<script>

	document.querySelectorAll('.tooltip-btn').forEach(button => {
	  button.addEventListener('mouseenter', () => {
	    const tooltipId = button.dataset.tooltip;
	    const tooltip = document.getElementById(tooltipId);
	    if (tooltip) {
	      tooltip.style.display = 'block';
	    }
	  });
	
	  button.addEventListener('mouseleave', () => {
	    const tooltipId = button.dataset.tooltip;
	    const tooltip = document.getElementById(tooltipId);
	    if (tooltip) {
	      tooltip.style.display = 'none';
	    }
	  });
	});


	function openVto() {
		fitmixInstance.startVto('live');
		show();
	}
	
	function stopVto() {
		fitmixInstance.stopVto()
	}
	
	
	function addPack(sku, tipoPrecioPack, pvoproductopack, tipoProducto) { 
		$(".product-button").attr("disabled", "disabled");
		
		var filter = JSON.stringify({
        	"sku": sku,
        	"skuPackMaster": '${skuPackMaster}',
        	"familiaProducto": '${familiaProducto!}',
        	"tipoProducto": tipoProducto,
        	"tipoPrecioPack": tipoPrecioPack,
        	"pvoproductopack": pvoproductopack,
        	"step": '${step!}'
    	});
	
		$.ajax({
            url : "${ctx.contextPath}/.rest/private/carrito/v1/carts-addtoPack",
            type : "POST",
            data : filter,
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            success : function(response) {
            	console.log("producto añadido al pack");
            	[#assign link = cmsfn.link("website", content.internalLinkPacksDetail!)!]
				var link ="${link}" + "?skuPackMaster=${skuPackMaster}";
				window.location.href = link;
            },
            error : function(response) {
                alert("error");             
                //$("#change-pwd-result").html("<p>" +  response.responseJSON.error.message + "</p>");
            },
            complete : function(response) {
                $("#loading").hide();
                $(".product-button").removeAttr("disabled");
            }
		});
	}
	
	function openModalPack(contentUrl, skuPack) {
	  var modal = document.getElementById("myModal-" + skuPack);
	  var modalBody = document.getElementById("modal-body-" + skuPack);
	  
	  // Realiza una petición AJAX para cargar el contenido
	  fetch(contentUrl)
	    .then(response => response.text())
	    .then(html => {
	    	var tempContainer = document.createElement('div');
	    	tempContainer.innerHTML = html;
	    	var headerToRemove = tempContainer.querySelector(".b2b-header-wrapper");
	    	if (headerToRemove) {
		    	headerToRemove.remove();
		    }
		    var menuflotanteToRemove = tempContainer.querySelector(".cmp-menuflotante");
		    if (menuflotanteToRemove) {
		    	menuflotanteToRemove.remove();
		    }
		    
		    tempContainer.querySelector(".b2b-detalle-imagen-full").className = 'b2b-detalle-imagen-full modal';
		    
		    var breadcrumbToRemove = tempContainer.querySelector(".b2b-breadcrumb");
		    if (breadcrumbToRemove) {
		    	breadcrumbToRemove.remove();
		    }
		    
		    var scripts = tempContainer.querySelectorAll('script');
	    
	    	modalBody.innerHTML = tempContainer.innerHTML; // Inserta el contenido en el modal
	    	
	    	scripts.forEach(script => {
			    var newScript = document.createElement('script');
			    if (script.src) {
			      // Si el script tiene un src, cargarlo dinámicamente
			      newScript.src = script.src;
			      document.head.appendChild(newScript);
			    } else {
			      // Si el script es inline, ejecutarlo
			      newScript.innerHTML = script.innerHTML;
			      document.head.appendChild(newScript);
			    }
			});
	    	
	    	modal.style.display = "block"; // Muestra el modal
	    });
	}
	
	function executeScripts(html) {
		// Crear un contenedor temporal
		var tempContainer = document.createElement('div');
		tempContainer.innerHTML = html;
	
		// Buscar todos los scripts en el contenido cargado
		var scripts = tempContainer.querySelectorAll('script');
	  
		// Insertar el HTML sin los scripts
		document.getElementById("modal-body").innerHTML = tempContainer.innerHTML;
	
		// Ejecutar cada script manualmente
		scripts.forEach(script => {
		    var newScript = document.createElement('script');
		    if (script.src) {
		      // Si el script tiene un src, cargarlo dinámicamente
		      newScript.src = script.src;
		      document.head.appendChild(newScript);
		    } else {
		      // Si el script es inline, ejecutarlo
		      newScript.innerHTML = script.innerHTML;
		      document.head.appendChild(newScript);
		    }
		});
	}
	
	function closeModalPack(skuPack) {
		var modal = document.getElementById();
		modal.style.display = "none";
	}

</script>
[/#if] [#-- edit mode --]
