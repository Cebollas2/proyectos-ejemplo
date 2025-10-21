[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]
[#include "../../includes/macros/ct-utils.ftl"]
[#include "../../includes/macros/cione-utils-impersonate.ftl"]
[#assign products = model.productsFront!]
[#if !cmsfn.editMode]
	[#assign fittingboxproducts = model.getFittingBoxProducts()!]
	
[/#if]
[#assign uuid = model.getUuid()!]
[#assign username = model.getUserName()!]

[#-- Recuperamos los assets de promociones --]
[#assign imgFlash = damfn.getAsset("jcr", "cione/imagenes/promociones/flash.png")!]
[#assign imgPromo = damfn.getAsset("jcr", "cione/imagenes/promociones/promo.png")!]
[#assign imgLiquidacion = damfn.getAsset("jcr", "cione/imagenes/promociones/liquidacion.png")!]

[#-- si no hay producto se evita cargar incluso los scripts --]
[#if products?has_content]
	
	[#assign titleclass = ""]
	[#if content.smalltitle?? && content.smalltitle?has_content]
		[#if content.smalltitle]
			[#assign titleclass  = "b2b-carrusel-sm" ]
		[/#if]
	[/#if]
	
	[#if content.gray?? && !content.gray]
		<section class="b2b-carrusel-products ${titleclass!""}">
	[#else]
		<section class="b2b-carrusel-products bg-gray ${titleclass!""} b2b-carrusel-notitle">
		<div class="owl-carrusel-layer"></div>
	[/#if]
	
	[#if content.first?? && !content.first]
		
	[#else]
		<script>
			$('.b2b-carrusel-products').addClass('b2b-carrusel-single');
		</script>
	[/#if]
	<style>
	.owl-stage-outer {
		min-height: 305px !important;
	}
	.b2b-carrusel-home .owl-stage-outer {
		min-height: auto !important;
	}
	.b2b-collection-link {
		font-family: Lato, sans-serif;
	  	font-size: 16px;
	  	font-weight: normal;
	  	font-stretch: normal;
	  	font-style: normal;
	  	line-height: normal;
	  	letter-spacing: normal;
	  	color: #3a3a3a;
	  	text-align: right;
	  	text-decoration: underline;
	  	background-color: #e0dbdb;
  		padding: 5px 10px 5px 10px;
  		border-radius: 12px;
	}
	</style>
	
	[#if content.title??]
		<div style="justify-content: space-between; display: flex; align-items: center; width: calc(100% - 50px); margin-left: 15px;">
    		<h2 class="section-title">${content.title!""}</h2>
    		[#if content.vertodoscomposite?? && content.vertodoscomposite.vertodos]
    			[#if content.vertodoscomposite.todosLink?has_content]
					[#assign linkTodos = cmsfn.link("website", content.vertodoscomposite.todosLink!)!]
					[#assign categoria = ""]
					[#list content.category?split("/") as sValue]
						[#if sValue?is_last]
					  		[#assign categoria = sValue]
					  	[/#if]
					[/#list] 
					<a class="b2b-collection-link" href="${linkTodos}?category=${categoria}">${i18n['cione-module.templates.myshop.listado-productos-home-component.vertodos']}</a>
				[/#if]
    			
    		[/#if]
    	</div>
    [/#if]
    
    <div class="owl-carousel owl-theme owl-carousel-products">
	    
	    [#list products as product]
	    	
	    	[#assign familiaProducto = ""]
			[#if product.master.familiaproducto?? && product.master.familiaproducto?has_content]
				[#assign familiaProducto = product.master.familiaproducto]
			[/#if]
	    	
	    	[#assign classtipo  = "" ]
		    [#if product.master.tipoproducto?has_content]
				[#if product.master.tipoproducto == "Accesorios" || product.master.tipoproducto == "Lentes de contacto"  
					|| product.master.tipoproducto?lower_case == "maquinaria" || product.master.tipoproducto == "Packs" 
					|| product.master.tipoproducto == "pack-generico" || product.master.tipoproducto == "Packs universales"]
					[#assign classtipo  = "item-accesorios" ]
					[#assign classtipoitem  = "item-accesorios" ]
				[/#if]
			[/#if]
			
			<div class="item ${classtipo!""}">
			
				[#assign promo = ""]
				[#assign mamount = product.master.amoutdiscount!0]
				[#if product.master.promo?has_content && mamount gte 1]
					[#assign promo = product.master.promo!"false"]
				[/#if]
				
				
				[#assign ofertaFlash = ""]
				[#if product.master.ofertaFlash?has_content]
					[#assign ofertaFlash = product.master.ofertaFlash!false]
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
					
						<img class="product-ribbon" src="[#if imgFlash??]${imgFlash.link!}[/#if]" alt="oferta flash" />
					[#else]
						[#if product.master.statusEkon?has_content  && product.master.statusEkon == "Liquidacion"]
		                	<img class="product-ribbon" src="[#if imgLiquidacion??]${imgLiquidacion.link!}[/#if]" alt="liquidacion" />
							[#if product.master.descripcion?has_content]
								[#if product.master.descripcion == "oferta"]
									<img class="product-ribbon" src="[#if imgFlash??]${imgFlash.link!}[/#if]" alt="oferta flash" />
								[/#if]
								[#if product.master.descripcion == "promo"]
									<img class="product-ribbon" src="[#if imgPromo??]${imgPromo.link!}[/#if]" alt="promocion" />
								[/#if]
							[/#if]
						[#elseif promo?has_content]
							[#if promo]
								<img class="product-ribbon" src="[#if imgPromo??]${imgPromo.link!}[/#if]" alt="promocion" />
							[/#if]
						[/#if]
					[/#if]

	                <div class="product-image-wrapper">
	                    <img class="product-image" src="${mainImage!""}" alt="${product.master.name!""}" />
	                </div>
	
	                <div class="product-info-wrapper">
	                    <div class="product-name" title="${product.master.name!""}">${getTruncate(product.master.name,64)!""}</div>
	                    
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
		                    		<div class="product-price"><span class="product-price-type">${i18n.get('cione-module.templates.configuracion-precios-component.PVO')}</span> #{product.master.pvo!;M2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
		                    	[/#if]
		                    [/#if]
	                    [/#if]
	                    
	                    [#assign strike = ""]
	                    [#if product.master.discount?has_content]			    
	                    	[#assign strike = "product-price-strike"]
	                    [/#if]
	                    
	                    <div class="product-price-wrapper-mod">
	                    	[#if familiaProducto != "packs"]
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
			                [#else]
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
	                        [/#if]
	                    </div>
		                    
	                </div>
	
	            </div>
	            
	            [#------------------------- PRODUCTO MASTER -------------------------]
	            
	            <div class="item-back ${product.master.sku?replace(".", "")?replace("-", "")!"ERROR"}" data-value="${product.master.sku!"ERROR"}">
	                
	                [#if ofertaFlash?has_content && ofertaFlash]
						<img class="product-ribbon" src="[#if imgFlash??]${imgFlash.link!}[/#if]" alt="oferta flash" />
					[#else]
						[#if product.master.statusEkon?has_content && product.master.statusEkon == "Liquidacion"]
		                		<img class="product-ribbon" src="[#if imgLiquidacion??]${imgLiquidacion.link!}[/#if]" alt="liquidacion" />
							[#if product.master.descripcion?has_content]
								[#if product.master.descripcion == "oferta"]
									<img class="product-ribbon" src="[#if imgFlash??]${imgFlash.link!}[/#if]" alt="oferta flash" />
								[/#if]
								[#if product.master.descripcion == "promo"]
									<img class="product-ribbon" src="[#if imgPromo??]${imgPromo.link!}[/#if]" alt="promocion" />
								[/#if]
							[/#if]
						[#elseif promo?has_content]
							[#if promo]
								<img class="product-ribbon" src="[#if imgPromo??]${imgPromo.link!}[/#if]" alt="promocion" />
							[/#if]
						[/#if]
					[/#if]

	                [#-- BEGIN: prueba virtual --]
	                [#if !cmsfn.editMode]
	                	[#if fittingboxproducts?? && fittingboxproducts?has_content]
			                [#if fittingboxproducts[product.master.sku]]
			                	[#--  fittinboxinstance('${product.master.sku}') --]
							    <img class="product-virtual b2b-detalle-virtual-button" 
							         onclick="fitmixInstance.setFrame('${product.master.sku}'); openVto()" 
							         src="${resourcesURL + "/img/myshop/icons/icon-virtual.svg"!}" 
							         alt="VTO" 
						         />
						    [/#if]
						[/#if]
					[/#if]
	
	                <div class="product-image-wrapper">
						
						[#assign sku = '']
						[#assign link = '#']
						[#if product.master.sku?has_content && product.master.sku?? && content.internalLink?has_content && content.internalLink??]
							[#assign link = cmsfn.link("website", content.internalLink!)!]
							[#assign sku = product.master.sku!'ERROR']
							
							[#if product.master.packNavegacionDetalle]
								[#assign link = cmsfn.link("website", content.internalLinkPacksDetail!)!]
								[#assign link = link + "?skuPackMaster=" + model.encodeURIComponent(sku)!"#"]
							[#else]
								[#assign link = link + "?sku=" + model.encodeURIComponent(sku)!"#"]
							[/#if]
							
						[/#if]
	                	
	                	<a href="${link!"#"}" data-value="${sku}">
	                    	<img class="product-image" src="${mainImage!""}" alt="${product.master.name!""}" />
	                	</a>
	                </div>
	                
	                <div class="product-info-wrapper">
	                
	                    <div class="product-name" title="${product.master.name!""}">${getTruncate(product.master.name,64)!""}</div>
	                    [#if product.master.nombreArticulo?? && product.master.nombreArticulo?has_content 
	                    	&& (familiaProducto == "monturas")
	                    	&& (product.master.name != product.master.nombreArticulo)]
	                    		<div class="product-name" title="${product.master.nombreArticulo!""}">${getTruncate(product.master.nombreArticulo!"",64)!""}</div>
	                    [/#if]
	                    [#if product.master.coleccion?? && product.master.coleccion?has_content]
	                    	<div class="product-name" title="${product.master.coleccion!""}">${getTruncate(product.master.coleccion!"",64)!""}</div>
	                    [/#if]
	                    [#if product.master.descripcionPack?? && product.master.descripcionPack?has_content]
	                    	<div class="product-name" title="${product.master.descripcionPack!""}">${getTruncate(product.master.descripcionPack!"",64)!""}</div>
	                    [/#if]
	                    <div class="product-price-wrapper">
	                    	[#if product.master.tipoproducto != "Packs"]
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
	                        [#else]
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
	                        [/#if]
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
					
					[#assign flagFamilia = ""]
					[#if product.master.familiaproducto?? && product.master.familiaproducto?has_content]
						[#assign flagFamilia = product.master.familiaproducto]
						[#if product.master.familiaproducto == "audifonos"]
							[@macrocoloraudio product/]
						[/#if]
					[/#if]
					
					[#if product.master.tipoproducto?has_content]
						[#if product.master.tipoproducto != "Lentes de contacto" 
							&& product.master.tipoproducto?lower_case != "maquinaria"
							&& flagFamilia != "audiolab" && product.master.tipoproducto != "Packs"
							&& product.master.familiaproducto?? && product.master.familiaproducto?has_content && product.master.familiaproducto != "pack-generico" ]
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
						<input type="hidden" name="listPromos_${product.master.sku?replace(".", "")?replace("-", "")}" value="${promo.getCantidad_hasta()}|${promo.getPvoDto()}">
		    		[/#list]
		    		<input type="hidden" id="tipoPromo_${product.master.sku?replace(".", "")?replace("-", "")}" value="escalado">
		    	[#else]
		    		<input type="hidden" id="tipoPromo_${product.master.sku?replace(".", "")?replace("-", "")}" value="otro">
				[/#if] --]
	            
	            
	            [#------------------------- PRODUCTO VARIANTS -------------------------]
	        
		        [#if product.variants?has_content]
		        	[#list product.variants as variant]
			
					[#assign vpromo = ""]
					[#assign vamount = variant.amoutdiscount!0]
					[#if variant.promo?has_content && vamount gte 1]
						[#assign vpromo = variant.promo!"false"]
					[/#if]
					
					[#assign strike = ""]
	                [#if variant.discount?has_content]
	                	[#assign strike = "product-price-strike"]
	                [/#if]
		        	
		        	<div class="item-back ${getId(product,variant)?replace(".", "")!"ERROR"}" data-value="${getId(product,variant)!"ERROR"}" style="display:none;">
	                	                
		                [#if variant.ofertaFlash?has_content && variant.ofertaFlash]
							<img class="product-ribbon" src="[#if imgFlash??]${imgFlash.link!}[/#if]" alt="oferta flash" />
						[#else]
							[#if variant.statusEkon?has_content  && variant.statusEkon == "Liquidacion"]
			                	<img class="product-ribbon" src="[#if imgLiquidacion??]${imgLiquidacion.link!}[/#if]" alt="liquidacion" />
								[#if variant.descripcion?has_content]
									[#if variant.descripcion == "oferta"]
										<img class="product-ribbon" src="[#if imgFlash??]${imgFlash.link!}[/#if]" alt="oferta flash" />
									[/#if]
									[#if variant.descripcion == "promo"]
										<img class="product-ribbon" src="[#if imgPromo??]${imgPromo.link!}[/#if]" alt="promocion" />
									[/#if]
								[/#if]
							[#elseif vpromo?has_content]
								[#if vpromo]
									<img class="product-ribbon" src="[#if imgPromo??]${imgPromo.link!}[/#if]" alt="promocion" />
								[/#if]
							[/#if]
						[/#if]           

		                [#-- BEGIN: prueba virtual --]
		                [#if !cmsfn.editMode]
		                	[#if fittingboxproducts?? && fittingboxproducts?has_content]
				                [#if fittingboxproducts[getId(product,variant)]!false]
				                	[#--  fittinboxinstance('${getId(product,variant)}') --]
								    <img class="product-virtual b2b-detalle-virtual-button" 
								         onclick="fitmixInstance.setFrame('${getId(product,variant)}'); openVto()" 
								         src="${resourcesURL + "/img/myshop/icons/icon-virtual.svg"!}" 
								         alt="Prueba Virtual" 
							         />	
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
							[/#if]
		                
		                	<a href="${link!"#"}" data-value="${sku}">
		                    	<img class="product-image" src="${mainImage!""}" alt="${variant.name!""}" />
	                    	</a>
		                </div>
		                
		                <div class="product-info-wrapper">
		                
		                    <div class="product-name" title="${variant.name!""}">${getTruncate(variant.name,64)!""}</div>
		                    [#if variant.nombreArticulo?? && variant.nombreArticulo?has_content 
		                    	&& (variant.name != variant.nombreArticulo)]
		                    		<div class="product-name" title="${variant.nombreArticulo!""}">${getTruncate(variant.nombreArticulo!"",64)!""}</div>
		                    [/#if]
		                    [#if variant.coleccion?? && variant.coleccion?has_content]
		                    	<div class="product-name" title="${variant.coleccion!""}">${getTruncate(variant.coleccion!"",64)!""}</div>
		                    [/#if]
		                    <div class="product-price-wrapper">
		                    	[#if variant.tipoproducto != "Packs"]
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
						
						[#if variant.tipoproducto?has_content]
							[#if variant.tipoproducto != "Accesorios"  && product.master.tipoproducto?lower_case != "maquinaria"]
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
							<input type="hidden" name="listPromos_${variant.sku?replace(".", "")?replace("-", "")}" value="${promo.getCantidad_hasta()}|${promo.getPvoDto()}">
			    		[/#list]
			    		<input type="hidden" id="tipoPromo_${variant.sku?replace(".", "")?replace("-", "")}" value="escalado">
			    	[#else]
			    		<input type="hidden" id="tipoPromo_${variant.sku?replace(".", "")?replace("-", "")}" value="otro">
					[/#if] --]
	            	
		        	[/#list]
		        [/#if]
	            
	        </div>
		[/#list]
		
    
    </div>
    
    <div class="custom-dots-wrapper">
        <span class="custom-dots"></span>
        <span class="custom-dots"></span>
        <span class="custom-dots"></span>
    </div>

</section>

[#------------------------------------------------------------------]
[#---------------------------- MODALES -----------------------------]
[#------------------------------------------------------------------]

[#if products?has_content]
	[#list products as product]
	
		[@macromodal product.master.sku product.master.delivery/]
		
		[#if product.variants?has_content]
	    	[#list product.variants as variant]
	    	
				[@macromodal variant.sku variant.delivery/]
				
	    	[/#list]
    	[/#if]
	
	[/#list]
[/#if]

[#------------------------------------------------------------------]
[#----------------------------- SCRIPT -----------------------------]
[#------------------------------------------------------------------]

<script>

	
	function addCart(sku,that,ref,num) {
		
		var refclient = '';
		if (ref == '' || ref == null){
			refclient = sessionStorage.getItem('refclient');
		}else{
			refclient = ref;
		}
		
		if(that != null){
			closeModalParent(that);
		}
		
		[#-- TODO: llama al servicio de compra --]
		if(refclient != '' && refclient != null){
			console.log("Add cart: " + sku + " Units: " + num + " and ref: " + refclient);
			addtoCartList(sku, num, refclient)
			
			[#-- llama al servicio con las variablaes sku y refclient --]
		}else{
			console.log("Add cart: " + sku + " Units: " + num);
			addtoCartList(sku, num, "")
			[#-- llama al servicio solo con la variable sku --]
		}
		
		sessionStorage.setItem('refclient', '');
	}
	
	function addtoCartList(sku, num, refCliente) {
		var coloraudio = $( "#coloraudio-" + sku).val();
		var filter = JSON.stringify({
        	"sku": sku,
        	"cantidad": num,
        	"refCliente": refCliente,
        	"colorAudio": coloraudio,
    	});

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
				
				//actualiza popup carrito
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
	
	function closeModal(that) {
		$(that).css("display","none");
		sessionStorage.setItem('refclient', '');
	}
	
	function closeModalParent(that) {
		$(that).parent().parent().parent().css("display","none");
		sessionStorage.setItem('refclient', '');
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
				beforeSend: function( xhr ) {
					$(".spinner").css("display","block");
				},
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
				   
				},complete : function(response) {
					$(".spinner").css("display","none");
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
		$('.owl-carousel-products').owlCarousel({
			[#if content.loop?? && !content.loop]
		    	loop:false,
		    [#else]
		    	loop:true,
		    [/#if]
		    margin:10,
		    dots: false,
		    nav:true,
		    autoWidth:true,
		    mouseDrag:false,
		    responsive:{
		        0:{
		            items:1,
		            autoWidth: false,
		            nav: false,
		            margin: 10,
		            center: true
		        },
		        410:{
		            items:8,
		           
		        },
		        1000:{
		            items:10,
		          
		        }
		    }
		});
		
		
		$('.modal-purchase-close-img').on("click", function () {
			$(this).parent().parent().parent().parent().css("display","none");
			sessionStorage.setItem('refclient', '');
		});
		
		[#if products?has_content]
			[#list products as product]
			
				$(".${product.master.sku?replace(".", "")?replace("-", "")?replace("+", "")!""} .product-color-circle").on("click",function(){
				    
				    var myid = $(this).parent().parent().data('value');
				    var goid = $(this).data('value');
				    
				    var rmyid = myid.replace(/\./g,"");
				    var rgoid = goid.replace(/\./g,"");
				    
				    if (myid != goid){
				    	$('.' + goid + ' .select-calibration').prop('selectedIndex',0);
				    	$("div.item-back." + rmyid).css("display", "none");
						$("div.item-back." + rgoid).css("display", "flex");
				    }
				
				});
				  
				$(".${product.master.sku?replace(".", "")?replace("-", "")?replace("+", "")!""} .product-calibre").on("click",function(e){
				    
				    var myid = $(this).parent().parent().data('value');
				    var goid = $(this).data('value');
					
					var rmyid = myid.replace(/\./g,"");
				    var rgoid = goid.replace(/\./g,"");
					
				    if (myid != goid){
				    	$("div.item-back." + rmyid).css("display", "none");
						$("div.item-back." + rgoid).css("display", "flex");
				    }
					
				});
				
				var lastSel${product.master.sku?replace(".", "")?replace("-", "")?replace("+", "")!""} = $('.${product.master.sku?replace(".", "")?replace("-", "")?replace("+", "")!""} .select-calibration').val();
				  
				$('.${product.master.sku?replace(".", "")?replace("-", "")?replace("+", "")!""} .select-calibration').change(function(){ 
					
					var myid = $(this).parent().parent().data('value');
				    var goid = $(this).val();
					
					$('.${product.master.sku?replace(".", "")?replace("-", "")?replace("+", "")!""} .select-calibration').val(lastSel${product.master.sku?replace(".", "")?replace("-", "")?replace("+", "")!""});
					
					var rmyid = myid.replace(/\./g,"");
				    var rgoid = goid.replace(/\./g,"");
					
				    if (myid != goid){
				    	$("div.item-back." + rmyid).css("display", "none");
						$("div.item-back." + rgoid).css("display", "flex");
				    }
					
				});
				
				var lastTamSel${product.master.sku?replace(".", "")?replace("-", "")?replace("+", "")!""} = $('.${product.master.sku?replace(".", "")?replace("-", "")?replace("+", "")!""} .select-tamanio').val();
				  
				$('.${product.master.sku?replace(".", "")?replace("-", "")?replace("+", "")!""} .select-tamanio').change(function(){ 
					
					var myid = $(this).parent().parent().data('value');
				    var goid = $(this).val();
					
					$('.${product.master.sku?replace(".", "")?replace("-", "")?replace("+", "")!""} .select-tamanio').val(lastTamSel${product.master.sku?replace(".", "")?replace("-", "")?replace("+", "")!""});
					
					var rmyid = myid.replace(/\./g,"");
				    var rgoid = goid.replace(/\./g,"");
					
				    if (myid != goid){
				    	$("div.item-back." + rmyid).css("display", "none");
						$("div.item-back." + rgoid).css("display", "flex");
				    }
					
				});
				
				$('.${product.master.sku?replace(".", "")?replace("-", "")?replace("+", "")!""} .UUID${content["jcr:uuid"]!""}.product-button').click(function(){
					[#if product.master.gestionStock]
						var stock = 0;
						var stockwithcart = 0;
						var almacen = '';
						var stockCTRAL = 0;
						var unitscart = 0;
						var urlstockwithcart = "${ctx.contextPath}/.rest/private/stock/withcart?sku=" + "${product.master.sku?trim}" + "&aliasEkon=" + encodeURIComponent("${product.master.aliasEKON!}");
						var urlstock = "${ctx.contextPath}/.rest/private/stock?sku=" + encodeURIComponent("${product.master.aliasEKON!}");
						var unitscarturl = "${ctx.contextPath}/.rest/private/stock/unitscart?sku=" + "${product.master.sku?trim}";
						var tipoProducto = "${product.master.tipoproducto}";
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
								console.log(response);
							},
							error : function(response) {
								console.log(response); 
							}
						});
									
						var stockreallabel = "${i18n['cione-module.templates.myshop.listado-productos-home-component.only']} ";
						var stockcanariaslabel = "${i18n['cione-module.templates.myshop.listado-productos-home-component.canar']}";
						var stockcentrallabel = "${i18n['cione-module.templates.myshop.listado-productos-home-component.ctral']}";
						var stockunidadeslabel = " ${i18n['cione-module.templates.myshop.listado-productos-home-component.units']}";
						
						$(this).parent().parent().find('input#stock_${product.master.sku?replace(".", "")?replace("-", "")?replace("+", "")}').attr("value",stock);
						
						if (almacen == 'stockCANAR'){
							$('div.modal-purchase.${product.master.sku?replace(".", "")?replace("-", "")?replace("+", "")!""}.UUID${content["jcr:uuid"]!""}').children().find('.stock_modal').html(stockcanariaslabel + stock + stockunidadeslabel + "<br\>" + stockcentrallabel + stockCTRAL + stockunidadeslabel);
						}else{
							$('div.modal-purchase.${product.master.sku?replace(".", "")?replace("-", "")?replace("+", "")!""}.UUID${content["jcr:uuid"]!""}').children().find('.stock_modal').text("${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.stock']} " + stock + " ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}");
						}
						
						
						$('span#unitscart').text("");
						
						if ((stockwithcart < $(this).parent().parent().find('.product-amount-input').val()) && (tipoProducto != "Audífonos")){
						
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
							
				    		$('div.modal-purchase.${product.master.sku?replace(".", "")?replace("-", "")?replace("+", "")!""}.UUID${content["jcr:uuid"]!""}').css("display", "flex");
				    		
						}else{
						
							addCart('${product.master.sku!""}','',$(this).parent().parent().find('.product-ref-input').val(),$(this).parent().parent().find('.product-amount-input').val());
						}
					[#else]	
						addCart('${product.master.sku!""}','',$(this).parent().parent().find('.product-ref-input').val(),$(this).parent().parent().find('.product-amount-input').val());
					[/#if]
				});
				
				[#if product.variants?has_content]
			    	[#list product.variants as variant]
			    	
			    		$(".${variant.sku?replace(".", "")?replace("-", "")?replace("+", "")!""} .product-color-circle").on("click",function(){
						    
						    var myid = $(this).parent().parent().data('value');
						    var goid = $(this).data('value');
						    
						    var rmyid = myid.replace(/\./g,"");
				    		var rgoid = goid.replace(/\./g,"");
						    
						    if (myid != goid){
						    	$('.' + goid + ' .select-calibration').prop('selectedIndex',0);
						    	$("div.item-back." + rmyid).css("display", "none");
								$("div.item-back." + rgoid).css("display", "flex");
						    }
						
						});
				  
						$(".${variant.sku?replace(".", "")?replace("-", "")?replace("+", "")!""} .product-calibre").on("click",function(e){
						    
						    var myid = $(this).parent().parent().data('value');
						    var goid = $(this).data('value');
						    
						    var rmyid = myid.replace(/\./g,"");
				    		var rgoid = goid.replace(/\./g,"");
						    
						    if (myid != goid){
						    	$("div.item-back." + rmyid).css("display", "none");
								$("div.item-back." + rgoid).css("display", "flex");
						    }
							
						});
						
						var lastSel${variant.sku?replace(".", "")?replace("-", "")?replace("+", "")!""} = $('.${variant.sku?replace(".", "")?replace("-", "")?replace("+", "")!""}  .select-calibration').val();
				  
						$('.${variant.sku?replace(".", "")?replace("-", "")?replace("+", "")!""}  .select-calibration').change(function(){ 
						
							var myid = $(this).parent().parent().data('value');
						    var goid = $(this).val();
						    
						    $('.${variant.sku?replace(".", "")?replace("-", "")?replace("+", "")!""}  .select-calibration').val(lastSel${variant.sku?replace(".", "")?replace("-", "")?replace("+", "")!""});
						    
						    var rmyid = myid.replace(/\./g,"");
				    		var rgoid = goid.replace(/\./g,"");
								    
						    if (myid != goid){
						    	$("div.item-back." + rmyid).css("display", "none");
								$("div.item-back." + rgoid).css("display", "flex");
						    }
							
						});
				
						var lastTamSel${variant.sku?replace(".", "")?replace("-", "")?replace("+", "")!""} = $('.${variant.sku?replace(".", "")?replace("-", "")?replace("+", "")!""} .select-tamanio').val();
						  
						$('.${variant.sku?replace(".", "")?replace("-", "")?replace("+", "")!""} .select-tamanio').change(function(){ 
							
							var myid = $(this).parent().parent().data('value');
						    var goid = $(this).val();
							
							$('.${variant.sku?replace(".", "")?replace("-", "")?replace("+", "")!""} .select-tamanio').val(lastTamSel${variant.sku?replace(".", "")?replace("-", "")?replace("+", "")!""});
							
							var rmyid = myid.replace(/\./g,"");
						    var rgoid = goid.replace(/\./g,"");
							
						    if (myid != goid){
						    	$("div.item-back." + rmyid).css("display", "none");
								$("div.item-back." + rgoid).css("display", "flex");
						    }
							
						});
				
						$('.${variant.sku?replace(".", "")?replace("-", "")?replace("+", "")!""} .UUID${content["jcr:uuid"]!""}.product-button').click(function(){
						[#if variant.gestionStock]
							var stock = 0;
							var stockwithcart = 0;
							var unitscart = 0;
							var urlstockwithcart = "${ctx.contextPath}/.rest/private/stock/withcart?sku=" + "${variant.sku?trim}" + "&aliasEkon=" + encodeURIComponent("${variant.aliasEKON!}");
							var urlstock = "${ctx.contextPath}/.rest/private/stock?sku=" + encodeURIComponent("${variant.aliasEKON!}")
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
							
							$(this).parent().parent().find('input#stock_${variant.sku?replace(".", "")?replace("-", "")?replace("+", "")}').attr("value",stock);
							$('div.modal-purchase.${variant.sku?replace(".", "")?replace("-", "")?replace("+", "")!""}.UUID${content["jcr:uuid"]!""}').children().find('.stock_modal').text("${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.stock']} " + stock + " ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}");
							$('span#unitscart').text("");
							
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
								
					    		$('div.modal-purchase.${variant.sku?replace(".", "")?replace("-", "")?replace("+", "")!""}.UUID${content["jcr:uuid"]!""}').css("display", "flex");
					    		
							}else{
								addCart('${variant.sku!""}','',$(this).parent().parent().find('.product-ref-input').val(),$(this).parent().parent().find('.product-amount-input').val());
							}
						[#else]	
							addCart('${variant.sku!""}','',$(this).parent().parent().find('.product-ref-input').val(),$(this).parent().parent().find('.product-amount-input').val());
						[/#if]
					    	
						});
						
			    	[/#list]
		    	[/#if]
			
			[/#list]
		[/#if]
		
		if ($("#relacionado").length){
		  $("#relacionado").css("display","block");
		}
		
	});
	
	[#-- modificamos todos los listados que repite el loop --]
	function changeloop(sku, obj) {
		
		$("#coloraudio-" + sku +" option[value= '" + obj.val() + "']").attr("selected", "selected");
	}

</script>

[#else]
	[#if content.takeSpace?? && content.takeSpace]
		<section class="b2b-carrusel-products b2b-carrusel-empty">
		</section>
	[/#if]
	
	<style>
	.owl-stage-outer {
		min-height: 305px !important;
	}
	</style>
	<script>
		$(document).ready(function(){
			if ($("#relacionado").length){
			  $("#relacionado").css("display","none");
			}
		});
	</script>

[/#if] 
[#-- si no hay producto se evita cargar incluso los scripts --]



[#------------------------------------------------------------------]
[#----------------------------- MACROS -----------------------------]
[#------------------------------------------------------------------]

[#macro macrocolor product]

	[#if product.colorsdto?has_content]
		<div class="product-color-select">
			[#list product.colorsdto as color]
				<div data-value="${color.sku}"
					 class="product-color-circle [#if color.selected] selected [/#if] hover-text"
					 style="background-color:${color.colorIcono!"#ffffff"};">
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
					 class="product-color-circle selected"
					 style="${getRealColor(color)!"background-color:#ffffff;"}">
				</div>
			[#else]
				<div data-value="${product.getSkuByColor(color)!""}"
					 class="product-color-circle"
					 style="${getRealColor(color)!"background-color:#ffffff;"}">
				</div>
			[/#if]
		[/#list]
		</div>
	[/#if--]
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
	[#if product.master.tipoproducto?has_content]
		[#if product.master.tipoproducto == "Soluciones Mantenimiento"]
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
	[#if product.master.tipoproducto?has_content]
		[#if product.master.tipoproducto == "Audífonos"]
			[#if product.master.colorsAudio?has_content]
				<div class="product-combo">
			    <div class="product-combo-title">Color</div>
			    	<select name="" id="coloraudio-${product.master.sku}" class="select-colorAudio" onchange="changeloop('${product.master.sku}', $(this))">
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
	[#if product.master.tipoproducto?has_content]
		
	    <div class="product-amount">
	    
	        <div class="product-amount-button-wrapper">
	            <button class="product-amount-button product-amount-button-minus" type="button" onclick="amountProductMinus(this)">
	                -
	            </button>
	            <input type="text" class="product-amount-input" min="1" max="999999" value="1">
	            <button class="product-amount-button product-amount-button-plus" type="button" onclick="amountProductPlus(this)">
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
		                <input type="hidden" id="stock_${product.master.sku?replace(".", "")?replace("-", "")}" value=""> 
		            </span>
		        </button>
			[/#if]
	    </div>
		
	[/#if]
    <div class="product-button-wrapper">
        <button class="UUID${content["jcr:uuid"]!""} product-button">
            ${i18n['cione-module.templates.myshop.listado-productos-home-component.buy']}
        </button>
    </div>


    <div class="product-reference">
        <label>${i18n['cione-module.templates.myshop.listado-productos-home-component.ref']}</label>
        <input type="text" class="product-ref-input">
    </div>

[/#macro]

[#-- marcro variantes --]

[#macro macrovariantcolor product variant]
	[#if product.colorsdto?has_content]
		<div class="product-color-select">
			[#list product.colorsdto as color]
				<div data-value="${color.sku}"
					 class="product-color-circle [#if color.sku == variant.sku] selected [/#if] hover-text"
					 style="background-color:${color.colorIcono!"#ffffff"};">
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
					 class="product-color-circle selected"
					 style="${getRealColor(color)!"background-color:#ffffff;"}">
				</div>
			[#else]
				<div data-value="${product.getSkuByColor(color)!""}"
					 class="product-color-circle"
					 style="${getRealColor(color)!"background-color:#ffffff;"}">
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
	[#if variant.tipoproducto?has_content]
		[#if variant.tipoproducto == "Soluciones Mantenimiento"]
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
        <div class="product-amount-button-wrapper">
            <button class="product-amount-button product-amount-button-minus" type="button" onclick="amountProductMinus(this)">
                -
            </button>
            <input type="text" class="product-amount-input" min="1" max="999999" value="1">
            <button class="product-amount-button product-amount-button-plus" type="button" onclick="amountProductPlus(this)">
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
	                <input type="hidden" id="stock_${variant.sku?replace(".", "")?replace("-", "")}" value="">
	            </span>
	        </button>
		[/#if]
    </div>

    <div class="product-button-wrapper">
        <button class="UUID${content["jcr:uuid"]!""} product-button">
            ${i18n['cione-module.templates.myshop.listado-productos-home-component.buy']}
        </button>
    </div>

    <div class="product-reference">
        <label>${i18n['cione-module.templates.myshop.listado-productos-home-component.ref']}</label>
        <input type="text" class="product-ref-input">
    </div>
				                
[/#macro]

[#macro macromodal sku delivery]

	[#assign closeimg = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/icons/close-thin.svg"]
	<div class="modal-purchase ${sku?replace(".", "")!""} UUID${content["jcr:uuid"]!""}" style="display: none;">
	
	    <div class="modal-purchase-box">
	    
	        <div class="modal-purchase-header">
	            <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.nostock']}</p>
	            <div class="modal-purchase-close">
	                <img class="modal-purchase-close-img" src="${closeimg!""}" alt="cerrar" onclick="closeModal('modal-purchase ${sku?replace(".", "")!""} UUID${content["jcr:uuid"]!""}')">
	            </div>
	        </div>
	        
	        <div class="modal-purchase-info">
	            <div>
	                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.noproduct']}</p>
	            </div>
	            <div>
	                [#-- <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.delivery']} ${delivery!""} ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.days']}</p> --]
	                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.delivery']} ${i18n['cione-module.templates.components.plazo-proveedor']}</p>
	                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.for']} <span id="unitsselected"></span> ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}<span id="unitscart"></span></p>
	                <p class="stock_modal"></p>
	            </div>
	        </div>
	        
	        <div class="modal-purchase-footer">
	            <button class="modal-purchase-button modal-purchase-button--transparent" type="button" onclick="closeModalParent(this)">
	                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}
	            </button>
	            <button class="modal-purchase-button" type="button" onclick="addCart('${sku!""}',this,'',$('span#unitsselected').html())">
	                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.add']}
	            </button>
	        </div>
	
	    </div>
	
	</div>  

[/#macro]
