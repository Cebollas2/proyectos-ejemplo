[#include "../../includes/macros/cione-utils-impersonate.ftl"] 

[#-- Component definition --]
[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]
[#assign infoVariant = model.getVariant()!]
[#assign familiaProducto = infoVariant.getFamiliaProducto()!]
[#--  --assign esComprable = infoVariant.isComprable()!] --]

[#assign listImages = infoVariant.getListImages()!]
[#assign masterImage = infoVariant.getMasterImage()!]
[#assign pvo = infoVariant.getPvo()!]
[#assign pvoDto = infoVariant.getPvoDto()!]
[#assign pvp = infoVariant.getPvpRecomendado()!]
[#assign tipoPromo = infoVariant.getTipoPromocion()!]
[#assign sku = infoVariant.getSku()!]



[#assign stock = infoVariant.getStock()!]
[#assign descripcion = infoVariant.getDescripcion()!]

[#assign target = infoVariant.getTarget()!]
[#assign tipoMontaje = infoVariant.getTipoMontaje()!]
[#assign material = infoVariant.getMaterial()!]
[#assign plazoProveedor = infoVariant.getPlazoEntregaProveedor()!]

[#assign existe = infoVariant.isExist()!]


[#assign listPromos = infoVariant.getListPromos()!]

[#assign uuid = model.getUuid()!]
[#assign username = model.getUserName()!]

[#assign skumask = infoVariant.getSkumask()!]

[#assign refTaller = ""]
[#assign refPack = ""]
[#assign almacen = model.getAlmacen()!]
[#assign flagPack = false]

[#-- control para funcionalidad de packs genericos --]
[#assign pack_generico = false]
[#if infoVariant.skuPackMaster?? && infoVariant.skuPackMaster?has_content]
	[#assign pack_generico = true]
[/#if]

[#assign step = infoVariant.step!]

[#-- 
	 aqui se debe determinar que productos estamos renderizando para poder 
	 determinar que clases debemos insertar
	 VER LA PANTALLA DETALLE DE CONTACTOLOGIA
  --]
[#assign container = "container"]
[#assign imageswrapper = ""]
[#assign cardwrapper = ""]

[#switch familiaProducto]
	[#case "monturas"]
		[#assign magniflier = "magniflier"]
		[#break]
	[#case "accesorios"]
		[#break]
	[#case "marketing"]
		[#break]
	[#case "liquidos"]
		[#break]
	[#case "contactologia"]
		[#assign container = "container-fluid"]
		[#assign imageswrapper = "b2b-60"]
		[#assign cardwrapper = "b2b-40"]
		[#assign refTaller = model.getRefTaller()]
		[#break]
	[#case "audifonos"]
		[#assign refTaller = model.getRefTaller()]
		[#break]
	[#case "audiolab"]
		[#assign refTaller = model.getRefTaller()]
		[#break]
	[#case "packs"]
		[#assign container = "container-fluid"]
		[#assign imageswrapper = "b2b-60"]
		[#assign cardwrapper = "b2b-40"]
		[#assign refTaller = model.getRefTaller()]
		[#assign flagPack = true]
		[#assign refPack = model.getRefTaller()]
		[#break]
[/#switch]

<style>
.validation-error {
	box-shadow: 0 0 0px 1px #EE0000;
}
</style>

<div class="${container!"container"}">
<form id="formDetalleProducto" name="formDetalleProducto" method="POST" style="display: contents;">
	<input id="" name="key" type="hidden" value=""> 
	<input id="" name="sku" type="hidden" value=""> 
	${ctx.response.setHeader("Cache-Control", "no-cache")}
	<input type="hidden" name="csrf" value="${ctx.getAttribute('csrf')!''}" />
	
	<input type="hidden" name="definitionName" value="commercetools" />
	<input type="hidden" name="connectionName" value="connectionName" />
	<input type="hidden" name="userIdEncodingDisabled" value="true" />
	<input type="hidden" name="accessToken" value="" />
	<input type="hidden" name="productId" value="${infoVariant.getProductId()!}" />
	<input type="hidden" name="variantId" value="${infoVariant.getVariantId()!}" />
	<input type="hidden" name="tipoPromocion" value="${tipoPromo}" />
	<input type="hidden" name="stock" value="${stock}" />
	<input type="hidden" id="refTaller" name="refTaller" value="${refTaller}" autocomplete="off"/>
	<input type="hidden" id="refPack" name="refPack" value="${refPack}" autocomplete="off"/>
	<input type="hidden" id="skuPackMaster" name="skuPackMaster" value="${infoVariant.skuPackMaster!}" autocomplete="off"/>
	
	<div class="b2b-detail-container">
	    <div class="b2b-detail-images-wrapper ${imageswrapper!""}">
	        <div class="b2b-detalle-imagen">
			    <div class="b2b-detalle-imagen-principal">
			    	
			    	[#if !existe]
						<p>${i18n['cione-module.templates.components.detalle-producto-component.producto-no-disponible']}</p>
					[/#if]
					
					[#assign urlVideo = infoVariant.getUrlVideo()!]
			    	<iframe src="${urlVideo}" class="b2b-detalle-video" style="display:none" width="560" height="315" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
				    
				    [#assign assetDescuento = damfn.getAsset("jcr", "cione/imagenes/promociones/promo.png")!]
				    [#assign assetFlash = damfn.getAsset("jcr", "cione/imagenes/promociones/flash.png")!]
				    [#assign assetLiquidacion = damfn.getAsset("jcr", "cione/imagenes/promociones/liquidacion.png")!]
				    <div style="display: flex; align-items: flex-start;">
					    [#if infoVariant.isLiquidacion()]
					    	<img class="b2b-detalle-imagen-dto" src="${assetLiquidacion.link}" alt="" />
					    [#elseif infoVariant.isOfertaFlash()]
					    	<img class="b2b-detalle-imagen-dto" src="${assetFlash.link}" alt="" />
					    [#elseif infoVariant.isTienePromociones()]
					    	<img class="b2b-detalle-imagen-dto" src="${assetDescuento.link}" alt="" />
					    [/#if]
					    
					    
				        <img  class="b2b-detalle-imagen-marca" src="${infoVariant.getLogoMarca()!}" alt="" />
			        </div>
			        [#if familiaProducto?? && familiaProducto?has_content]
				        [#if familiaProducto == "monturas"]
				        	<p class="b2b-detalle-full-screen">
				        		<img class="b2b-detalle-imagen-ampliar" src="${resourcesURL + "/img/myshop/icons/fullscreen.svg"!}" alt="" />
				        	</p>
				        [/#if]
			        [/#if]
			        
			        [#if masterImage?? && masterImage?has_content]
			        	<img class="b2b-detalle-imagen-full ${magniflier!""}" src="${masterImage.getUrl()!}" alt="" />
			        [/#if]
			        [#if listImages?has_content]
					    <ul class="b2b-detalle-miniaturas">
					    [#list listImages as imagen]
					    	<li class="b2b-detalle-miniaturas-item [#if imagen == masterImage]selected[/#if]">
					            <img class="b2b-detalle-miniaturas-imagen"  src="${imagen.getUrl()!}" alt="" />
					        </li>
					    [/#list]
					    	[#if urlVideo?? && urlVideo?has_content]
						    	<li class="b2b-detalle-miniaturas-item  b2b-detalle-miniaturas-video">
						        	[#assign playIcon = resourcesURL + "/img/myshop/common/play.svg"!]
						            <img class="b2b-detalle-miniaturas-imagen" src="${playIcon}" alt="">
						        </li>
					        [/#if]
				    	</ul>
				    [#else]
				    	[#if urlVideo?? && urlVideo?has_content]
					    	<ul class="b2b-detalle-miniaturas">
						        <li class="b2b-detalle-miniaturas-item  b2b-detalle-miniaturas-video">
						        	[#assign playIcon = resourcesURL + "/img/myshop/common/play.svg"!]
						            <img class="b2b-detalle-miniaturas-imagen" src="${playIcon}" alt="">
						        </li>
						    </ul>
						[/#if]
					[/#if]
			    </div>
			    
			    [@macrocarruselPacks infoVariant/]
			    
			    [#-- BEGIN: prueba virtual --]
			    [#assign fittingboxproducts = model.getFittingBoxProducts()!]
			    [#assign skufit = ""]
			    [#if fittingboxproducts?? && fittingboxproducts?has_content]
			    	[#if fittingboxproducts[sku]]
			    		[#assign skufit = sku]
				    [#else]
				    	[#assign listSku= infoVariant.variantsSku!]
				    	[#list listSku as fittingboxvariant]
				    		[#if fittingboxproducts[fittingboxvariant]]
				    			[#assign skufit = fittingboxvariant]
				    		[/#if]
				    	[/#list]
				    [/#if]
			    [/#if]
			    [#if skufit?? && skufit?has_content]
					[#if familiaProducto?? && familiaProducto?has_content]
				        [#if familiaProducto == "monturas"]
							<div class="b2b-detalle-virtual">
								 [#--  fittinboxinstance('${sku}') --]
								<button class="b2b-detalle-virtual-button" onclick="fitmixInstance.setFrame('${skufit}'); openVto()" type="button">
									<img  src="${resourcesURL + "/img/myshop/icons/icon-virtual.svg"!}" alt="${i18n['cione-module.templates.components.detalle-producto-component.vtobtn']}"/> 
									${i18n['cione-module.templates.components.detalle-producto-component.vtobtn']}
								</button> 
								
								<p class="b2b-detalle-virtual-texto">
									${i18n['cione-module.templates.components.detalle-producto-component.vto']}
								</p>
							</div>
							
							[#-- BEGIN: modal prueba virtual
							<div class="detalle-virtual-modal">
								<div class="detalle-virtual-modal-wrapper">
									<div class="detalle-virtual-modal-close-wrapper">
										<img class="detalle-virtual-modal-close-img" src="${resourcesURL + "/img/myshop/icons/close-thin-white.svg"!}" alt="" />
									</div>
									<div id="fitmixContainer"></div>
								</div>
							</div>
							[#-- END: modal prueba virtual --]
				        [/#if]
					[/#if]
				[/#if]
				
			    
			</div>
			
			
			[#-- BEGIN: modal del imagenes --]
			[#if familiaProducto?? && familiaProducto?has_content]
		        [#if familiaProducto == "monturas"]
					<div class="detalle-modal-zoom">

					    <div class="detalle-modal-zoom-close-wrapper">
					        <img class="detalle-modal-zoom-close-img" src="${resourcesURL + "/img/myshop/icons/close-thin-blue.svg"!}" alt="Close" />
					    </div>
					    
					    <div class="owl-carousel owl-theme owl-carousel-zoom">
							[#if listImages?has_content]
							    [#list listImages as imagen]
							        <div class="item">
							            <img class="b2b-detalle-imagen-full" src="${imagen.getUrl()!}" alt="" />
							        </div>
							    [/#list]
							[/#if]
					    </div>
					</div>
				[/#if]
			[/#if]
			[#-- END: modal del imagenes --]
			
		</div>
	    <div class="b2b-detail-card-wrapper ${cardwrapper!""}">
	        <section class="b2b-detail-compra">
	
			    <div class="b2b-detail-compra-product ${sku}">
			        <div class="product-info-wrapper">
						<div class="product-name">${infoVariant.getName()!}</div>
			        	[#if infoVariant.nombreArticulo?? && infoVariant.nombreArticulo?has_content 
			        		&& (infoVariant.nombreArticulo != infoVariant.name)]
			            	<div class="product-name">${infoVariant.nombreArticulo}</div>
			            [/#if]
			            [#if infoVariant.isTienePromociones()]
				            <div class="product-price-wrapper" id="product-price-dtos">
				            	[#if showPVO(ctx.getUser(), uuid, username)]
					            	[#if pvoDto?? && pvoDto?has_content]
					            		<div class="product-price-item">
						                    <div class="product-price-text-dto">${i18n['cione-module.templates.components.detalle-producto-component.pvo-dto']}</div>
						                    <input type="hidden" id="pvoConDescuento" name="pvoConDescuento" value="${pvoDto}" />
						                    <div id="product-price-number-dto" class="product-price-number product-price-pvo-dto">${pvoDto} €</div>
						                </div>
						                <div class="product-price-item">
						                    <div class="product-price-text product-price-strike">${i18n['cione-module.templates.components.detalle-producto-component.pvo']}</div>
						                    <div class="product-price-number product-price-strike">${pvo} €</div>
						                </div>
						            [#else]
						            	<div class="product-price-item">
						                    <div class="product-price-text">${i18n['cione-module.templates.components.detalle-producto-component.pvo']}</div>
						                    <div class="product-price-number">${pvo} €</div>
						                    <input type="hidden" id="pvoConDescuento" name="pvoConDescuento" value="${pvo}" />
						                </div>
						            [/#if]						            
						        [/#if]
			
						        [#if showPVP(ctx.getUser(), uuid, username)]
					                <div class="product-price-item">
					                    <div class="product-price-text-pvp">${i18n['cione-module.templates.components.detalle-producto-component.pvp']}</div>
					                    <div class="product-price-number product-price-pvp">${pvp} €</div>
					                </div>
				                [/#if]
				            </div>
				            <div class="product-separator"></div>
				            [#if familiaProducto?? && familiaProducto?has_content]
	    						[#if familiaProducto == "contactologia"]
	    							[#if infoVariant.getIsContactLab()?has_content && infoVariant.getIsContactLab()]
		                    			<div class="product-name">
		                    				<div class="pb-3 destacado">${i18n['cione-module.templates.components.detalle-producto-component.lentefabricacion']}</div>
		                    			</div>
		                    		[/#if]
	                    		[/#if]
	                    	[/#if]
			            	[#--[#if showPVO(ctx.getUser(), uuid, username)] --]
				            	[#if tipoPromo == "escalado"]
				            		
				            		[#list listPromos as promo]
			            				<div class="box"> 
			            					Compra ${promo.getCantidad_desde()} unidades y ahorra un ${promo.getValor()} %
			            				</div>
				            		[/#list]
				                	
				                [#elseif tipoPromo == "porcentaje"]
				                	<div class="box">
					                	${i18n['cione-module.templates.components.detalle-producto-component.producto-con']} ${infoVariant.getValorDescuento()!} 
					                	${i18n['cione-module.templates.components.detalle-producto-component.porcentaje-descuento']}
					                </div>
				                [#elseif tipoPromo == "valor"]
				                	<div class="box">
				                		${i18n['cione-module.templates.components.detalle-producto-component.producto-con']} ${infoVariant.getValorDescuento()!} €
				                		${i18n['cione-module.templates.components.detalle-producto-component.valor-descuento']}
				                	</div>
				                [#elseif tipoPromo == "pack"]
				                [/#if]
			            	[#--[/#if]--]
				        [#else]
				        	<div class="product-price-wrapper">
				        		[#if showPVO(ctx.getUser(), uuid, username)]
				                <div class="product-price-item">
				                    <div class="product-price-text">${i18n['cione-module.templates.components.detalle-producto-component.pvo']}</div>
				                    <div class="product-price-number">${pvo} €</div>				         
				                    [#if infoVariant.getPvoIncremento()?? && infoVariant.getPvoIncremento()?has_content]
				                    	<input type="hidden" id="pvoConDescuento" name="pvoConDescuento" value="${infoVariant.getPvoIncremento()}" />
				                    [/#if]
				                </div>
				                [/#if]
				                [#if showPVP(ctx.getUser(), uuid, username)]
				                <div class="product-price-item">
				                    <div class="product-price-text-pvp">${i18n['cione-module.templates.components.detalle-producto-component.pvp']}</div>
				                    <div class="product-price-number product-price-pvp">${pvp} €</div>
				                </div>
				                [/#if]
				            </div>
				            
				            <div class="product-separator"></div>

		                    [#if familiaProducto?? && familiaProducto?has_content]
	    						[#if familiaProducto == "contactologia"]
	    							[#if infoVariant.getIsContactLab()?has_content && infoVariant.getIsContactLab()]
		                    			<div class="product-name">
		                    				<div class="pb-3 destacado">${i18n['cione-module.templates.components.detalle-producto-component.lentefabricacion']}</div>
		                    			</div>
		                    		[/#if]
	                    		[/#if]
	                    	[/#if]
				        [/#if]
				        
				        [@macropacks infoVariant/]
			            
			            [#if familiaProducto == "packs"]
			            	[#if infoVariant.contenidoPack?size > 0]
				            	[#assign infoVariantPack = infoVariant.contenidoPack[0]]
				            	[#list infoVariant.contenidoPack as contenido]
				            		[#if contenido.isMasterPack()]
				            			[#assign infoVariantPack = contenido]
				            		[/#if]
				            	[/#list]
			            		[@macrovariantes infoVariantPack /]
			            	[/#if]
			            [#else]
			            	[@macrovariantes infoVariant /]
			            [/#if]
			            [@macropdfs /]
			            
			            [@macrocomprar /]
			            
			        </div>
			    </div>
			</section>
		
		</div>
		
		
		[#assign closeimg = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/icons/close-thin.svg"]
		<div id="modal-purchase-detail" class="modal-purchase" style="display: none;">
		
		    <div class="modal-purchase-box">
		    
		        <div class="modal-purchase-header">
		            <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.nostock']}</p>
		            <div class="modal-purchase-close">
		                <img class="modal-purchase-close-img" src="${closeimg!""}" alt="cerrar" onclick="closeModalDetail(this)">
		            </div>
		        </div>
		        
		        <div class="modal-purchase-info">
		            <div>
		                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.noproduct']}</p>
		            </div>
		            <div>
		                [#-- <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.delivery']} ${plazoProveedor + 2} ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.days']}</p> --]
		                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.delivery']} ${i18n['cione-module.templates.components.plazo-proveedor']}</p>
		                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.for']} <span id="unitsselected"> </span> ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}<span id="unitscart"></span></p>
		                [#assign stocklabel = i18n['cione-module.templates.myshop.listado-productos-carrito-component.real']] 
			            [#if almacen?has_content]
				            [#if almacen??]
					            [#if almacen == "stockCANAR"]
					            	[#assign stockctral = model.getStock(infoVariant.aliasEkon!).getStockCTRAL()!""]
					            	[#if stockctral?has_content && stockctral??]
					            	    <p class="stock_modal">${i18n['cione-module.templates.myshop.listado-productos-home-component.canar']}${stock} ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}</p>
				            			<p class="stock_modal">${i18n['cione-module.templates.myshop.listado-productos-home-component.ctral']}${stockctral!""} ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}</p>
					            	[#else]
				            			<p class="stock_modal">${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.stock']} ${stock} ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}</p>
					            	[/#if]
				            	[#else]
				            		<p class="stock_modal">${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.stock']} ${stock} ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}</p>
					            [/#if]
				            [/#if]
			            [/#if]
		            </div>
		        </div>
		        
		        <div class="modal-purchase-footer">
		            <button class="modal-purchase-button modal-purchase-button--transparent" type="button" onclick="closeModalDetail(this)">
		                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}
		            </button>
		            <button class="modal-purchase-button" type="button" onclick="addtoCartEndPoint();">
		                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.add']}
		            </button>
		        </div>
		
		    </div>
		
		</div>
		[#-- modal para productos sustitutivos --]
			<div id="modal-replacement-detail" class="modal-purchase" style="display: none;">
			    <div class="modal-purchase-box">
			    
			        <div class="modal-purchase-header">
			            <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.nostock']}</p>
			            <div class="modal-purchase-close">
			                <img class="modal-purchase-close-img" src="${closeimg!""}" alt="cerrar" onclick="closeModalReplacements(this)">
			            </div>
			        </div>
			        
			        <div class="modal-purchase-info">
			        	<div style="font-weight: bold;">
			        		<p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.noproduct']}</p>
			        	</div>
			            <div>
			                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.replacement']} ${infoVariant.getNombreArticulo()!}</p>
			                <p style="display: flex; padding: 10px 0 10px 0;"><span>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.available']}</span>
			                <span id="listReplacement" style="padding-left: 10px;">
				                
			                </span>
			                </p>
			                
			                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.continue']}</p>
			                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.question']}</p>
			            
			            </div>
			            
			        </div>
			        
			        <div class="modal-purchase-footer">
			            <button class="modal-purchase-button modal-purchase-button--transparent" type="button" onclick="closeModalReplacements(this)">
			                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}
			            </button>
			            <button class="modal-purchase-button" type="button" onclick="addProductOrigin();">
			                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.addOriginal']}
			            </button>
			            <button class="modal-purchase-button" type="button" onclick="addtoCartEndPoint();">
			                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.addSustitutivo']}
			            </button>
			        </div>
			
			    </div>
			
			</div>
		
		
		 [#-- modal para contactologia --]
		 <div id="modal-contactologia-detail" class="modal-purchase" style="display: none;">
		
		    <div class="modal-purchase-box">
		    
		        <div class="modal-purchase-header">
		            <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.resume']}</p>
		            <div class="modal-purchase-close">
		                <img class="modal-purchase-close-img" src="${closeimg!""}" alt="cerrar" onclick="closeModalContactologia(this)">
		            </div>
		        </div>
		        
		        <div class="modal-purchase-info">
		            <div>
		                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.stock-availability']}</p>
		            </div>
		            <div id="modalojoizq" style="font-weight: bold;">
		                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.left-eye']}</p>
		                <p id="plazomodalcontactologiaojoizq">${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.delivery']} ${i18n['cione-module.templates.components.plazo-proveedor']}</p>
		                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.for']} <span id="unitsselectedojoizq"> </span> ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}<span id="unitscartizq"></span></p>
		                <p class="stock_modal" id="stockmodalcontactologiaojoizq">${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.stock']} ${stock} ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}</p>
		            	<input type="hidden" id="plazoEntregaIzq" name="plazoEntregaIzq" value="" />
		            	<br>
		            </div>
		            
		            <div id="modalojodrc" style="font-weight: bold;">
		                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.right-eye']}</p>
		                <p id="plazomodalcontactologiaojodrch">${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.delivery']} ${i18n['cione-module.templates.components.plazo-proveedor']}</p>
		                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.for']} <span id="unitsselectedojodrch"> </span> ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}<span id="unitscartdrch"></span></p>
		                <p class="stock_modal" id="stockmodalcontactologiaojodrch">${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.stock']} ${stock} ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}</p>
		            	<input type="hidden" id="plazoEntregaDrch" name="plazoEntregaDrch" value="" />
		            </div>
		            
		        </div>
		        
		        <div class="modal-purchase-footer">
		            <button class="modal-purchase-button modal-purchase-button--transparent" type="button" onclick="closeModalContactologia(this)">
		                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}
		            </button>
		            
		            <button class="modal-purchase-button" type="button" 
						onclick="addtoCartContactologia('${infoVariant.getCodigoCentral()!""}', '${skumask}', 
							[#if infoVariant.getConfig()?has_content && infoVariant.getConfig()]${infoVariant.getConfig()?c}[#else]false[/#if]);">
	                	${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.add']}
	            	</button>
		            
					
		        </div>
		
		    </div>
		    
		    <input type="hidden" id="familiaProducto" name="familiaProducto" value="${familiaProducto}" />
		
		</div> 
		
		 [#-- modal para packs --]
		 <div id="modal-packs-detail" class="modal-purchase" style="display: none;">
		
		    <div class="modal-purchase-box">
		    
		        <div class="modal-purchase-header">
		            <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.resume']}</p>
		            <div class="modal-purchase-close">
		                <img class="modal-purchase-close-img" src="${closeimg!""}" alt="cerrar" onclick="closeModalPacks(this)">
		            </div>
		        </div>
		        
		        <div class="modal-purchase-info">
		            <div>
		                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.stock-availability']}</p>
		            </div>
		            
		            [#if infoVariant.contenidoPack?? && infoVariant.contenidoPack?has_content]
						[#if infoVariant.contenidoPack?size > 0]
				        	[#assign infoVariantPack = infoVariant.contenidoPack[0]]
				        	[#list infoVariant.contenidoPack as contenido]
				        		[#if contenido.familiaProducto?? & contenido.familiaProducto?has_content]
						        	[#if contenido.familiaProducto == "contactologia"]
						        		[#assign unidadesLente = 1]
										[#if contenido.unidadesPack?? && contenido.unidadesPack?has_content]
											[#assign unidadesLente = contenido.unidadesPack /2]
										[/#if]
						        		<div id="modalojoizqpack" style="padding-top: 15px; font-weight: bold;">
							                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.left-eye']}</p>
							                <p id="plazomodalcontactologiaojoizqpack">${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.delivery']} ${i18n['cione-module.templates.components.plazo-proveedor']}</p>
							                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.for']} <span id="unitsselectedojoizqpack"> </span> ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}<span id="unitscartizqpack"></span></p>
							                <p class="stock_modal" id="stockmodalcontactologiaojoizqpack">${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.stock']} ${contenido.stock} ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}</p>
							            	<input type="hidden" id="plazoEntregaIzq" name="plazoEntregaIzq" value="${contenido.plazoEntregaProveedor!}" />
							            	<br>
							            </div>
							            <div id="modalojodrcpack" style="padding-top: 15px; font-weight: bold;">
							                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.right-eye']}</p>
							                <p id="plazomodalcontactologiaojodrchpack">${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.delivery']} ${i18n['cione-module.templates.components.plazo-proveedor']}</p>
							                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.for']} <span id="unitsselectedojodrchpack"> </span> ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}<span id="unitscartdrchpack"></span></p>
							                <p class="stock_modal" id="stockmodalcontactologiaojodrchpack">${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.stock']} ${contenido.stock} ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}</p>
							            	<input type="hidden" id="plazoEntregaDrch" name="plazoEntregaDrch" value="${contenido.plazoEntregaProveedor!}" />
							            </div>
							        [#elseif contenido.tipoProductoPack?? && contenido.tipoProductoPack?has_content && contenido.tipoProductoPack == "complemento-pack" || contenido.familiaProducto == "complementosaudio"]
							        	<div id="modalpack-${contenido.sku}" style="padding-top: 15px; font-weight: bold;">
							        		
							        		<p>${contenido.nombreArticulo!}</p>
							        		<p id="plazomodal-${contenido.sku}">${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.delivery']} ${i18n['cione-module.templates.components.plazo-proveedor']}</p>
							                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.for']} <span id="unitsselected-${contenido.sku}"> </span> ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}<span id="unitscart-${contenido.sku}"> </span></p>
							                [#assign stocklabel = i18n['cione-module.templates.myshop.listado-productos-carrito-component.real']] 
								            [#if almacen?has_content]
									            [#if almacen??]
										            [#if almacen == "stockCANAR"]
										            	[#assign stockctral = model.getStock(contenido.aliasEkon!).getStockCTRAL()!""]
										            	[#if stockctral?has_content && stockctral??]
										            	    <p class="stock_modal">${i18n['cione-module.templates.myshop.listado-productos-home-component.canar']}${contenido.stock} ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}</p>
									            			<p class="stock_modal">${i18n['cione-module.templates.myshop.listado-productos-home-component.ctral']}${stockctral!""} ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}</p>
										            	[#else]
									            			<p class="stock_modal">${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.stock']} ${contenido.stock} ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}</p>
										            	[/#if]
									            	[#else]
									            		<p class="stock_modal">${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.stock']} ${contenido.stock} ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}</p>
										            [/#if]
									            [/#if]
								            [/#if]
							            </div>
						        	[/#if]	
				        		[/#if]
				        	[/#list]
				    	[/#if]
						
					[/#if]
		            
		        </div>
		        
		        <div class="modal-purchase-footer">
		            <button class="modal-purchase-button modal-purchase-button--transparent" type="button" onclick="closeModalPacks(this)">
		                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}
		            </button>
		            
		            <button class="modal-purchase-button" type="button" 
						onclick="addToCartPack()">
	                	${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.add']}
	            	</button>
		            
					
		        </div>
		
		    </div>
		    
		    <input type="hidden" id="familiaProducto" name="familiaProducto" value="${familiaProducto}" />
		
		</div>
				
				[#if infoVariant.contenidoPack?? && infoVariant.contenidoPack?has_content]
					[#if infoVariant.contenidoPack?size > 0]
			        	[#assign infoVariantPack = infoVariant.contenidoPack[0]]
			        	[#list infoVariant.contenidoPack as contenido]
			        		[@macrodescription contenido/]
			        	[/#list]
			        [/#if]
			    [#else]
			    	[@macrodescription infoVariant/]
			    [/#if]
			    <div id="relacionado" class="b2b-detail-tab-header mt-5" style="display: none">
			        <div class="b2b-detail-tab-title selected" data-id="tab03">
			            Artículos Relacionados</div>
			    </div>

			</section>
	    </div>
	   
	</div>
	
	[#if cmsfn.editMode]
		[@cms.area name="configurador" /]
	[/#if]

[#macro macrodescription infoVariant]
[#if infoVariant.familiaProducto?? && infoVariant.familiaProducto?has_content]
	
	[#switch infoVariant.familiaProducto]
	
		
	
		[#case "audiolab"] [#-- audiologia aun no se ha definido el tipo de producto --]
			[@cms.area name="configurador" /]
			[#break]
		[#case "audifonos"] [#-- audiologia aun no se ha definido el tipo de producto --]
			<div class="b2b-detail-tabs-container">
	            <div class="b2b-audifono-form" style="max-width: 1140px;">
	
				    <form action="">
						
						[#-- TITULO --]
				        <p class="b2b-audifono-form-title">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.title']}</p>
				        
				        <div class="row">
				        	[#-- COLOR --]
				        	[@audifonos "coloraudifono" infoVariant /]
				        	[#--cx c  COLOR --]
				        	[@audifonos "colorcodo" infoVariant/]
							
				        </div>
				        
				        <div class="row">
				        	[#-- AURICULAR --]
							[@audifonos "auriculares" infoVariant/]
				        	[#-- ACOPLADOR --]
							[@audifonos "acopladores" infoVariant/]
							
				        </div>
				        
				        <div class="row">
				        	[#-- CARGADOR --]
							[@audifonos "cargadores" infoVariant/]
				        	[#-- ACCESORIOS INALAMBRICOS --]
				        	[@audifonos "accesorios" infoVariant/]
				        </div>
				        
				        <div class="row">
				        	[#-- TUBOS FINOS --]
				            [@audifonos "tubosFinos" infoVariant/]
					        [#-- TUBOS FINOS --]
				            [@audifonos "sujecionesDeportivas" infoVariant/]
				     	</div> 
				     	
				     	<div class="row">
				        	[#-- FILTROS --]
				            [@audifonos "filtros" infoVariant/]
				     	</div>   
				        
				        [#if infoVariant.getGarantia()?has_content]
					        <div class="separator"></div>
					        
					        [#-- SUPERGARANTIA --]
					        <p class="b2b-audifono-form-subtitle">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.supergarantia']} <i class="fas fa-info-circle"></i></p>
					        <div class="row">
					        	<div class="col-12 col-md-6">
					                <select name="" id="garantia" autocomplete="off">
					                    [#assign garantias = infoVariant.getGarantia()]
			                    		<option value="" selected></option>
			                    		[#list garantias?keys as key]
			                            	<option value="${key!""}">${garantias[key]!""}</option>
				                        [/#list]
					                </select>
				             	</div>
				            </div>
						[/#if]
						
						[#if infoVariant.getDeposito()?has_content]
							[#if infoVariant.getDeposito()]
						        <div class="separator"></div>
								
								[#-- DEPOSITO --]
								
						        <p class="b2b-audifono-form-subtitle">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.deposito']}</p>
						        
						        <div class="row">
						            <div class="col-md-3 col-12">
						            	<input type="radio" id="radio1deposito" name="deposito" value="true" autocomplete="off">
						                <label for="radio1">${i18n['cione-module.templates.components.detalle-producto-component.si']}</label>
						            </div>
						            <div class="col-md-9 col-12">
						            	<input type="radio" id="radio2deposito" name="deposito" value="false" checked autocomplete="off">
						                <label for="radio2">${i18n['cione-module.templates.components.detalle-producto-component.no']}</label>
						            </div>
						        </div>
						        
						        <i class="fas fa-info-circle"></i> <label > ${i18n['cione-module.templates.components.detalle-producto-component.infodeposito']} </label>
					        [/#if]
        				[/#if]
				        
				    </form>
				</div>
			</div>
			
		    <div class="b2b-detail-tabs-container">
				<section class="b2b-detail-tabs">
					[#assign specificMessages = infoVariant.getMensajesEspecificos()!]
					[#if specificMessages?has_content]
					[#-- pestañas --]
				    <div class="b2b-detail-tab-header">
				        <div class="b2b-detail-tab-title selected" data-id="tab01">${i18n['cione-module.templates.components.detalle-producto-component.condiciones-de-envio']}</div>
				    </div>
				    
				    [#-- descripcion del prodcuto --]
				    <div class="b2b-detail-tab-body">
				        
				        [#-- condiciones de envio --]
				        <div id="tab01" class="b2b-detail-tab-body-content active">
				        	[#assign flag = true]
				        	[#list specificMessages as message]
				        		[#if flag]
						        	<div class="b2b-detail-tab-row">
				                		<div class="b2b-detail-tab-col">
				                    		<p class="b2b-detail-tab-col-title">${message.getTitle()}</p>
						                    <p class="b2b-detail-tab-col-text">${message.getDescripcion()}</p>
						                </div>
						            	[#assign flag = false]
						        [#else]
						        		<div class="b2b-detail-tab-col">
				                    		<p class="b2b-detail-tab-col-title">${message.getTitle()}</p>
						                    <p class="b2b-detail-tab-col-text">${message.getDescripcion()}</p>
						                </div>
						            </div>
						            [#assign flag = true]
						        [/#if]
				        	[/#list]
				        </div>
				    </div>
				    [/#if]
			
			[#break]
		
		[#case "contactologia"]
			
		    <div class="b2b-detail-tabs-container">
				<section class="b2b-detail-tabs">
					
					[#-- pestañas --]
				    <div class="b2b-detail-tab-header">
				        <div class="b2b-detail-tab-title selected" data-id="tab01">${i18n['cione-module.templates.components.detalle-producto-component.detalles']}</div>
				        <div class="b2b-detail-tab-title " data-id="tab02">${i18n['cione-module.templates.components.detalle-producto-component.condiciones-comerciales']}</div>
				    </div>
				    
				    [#-- descripcion del prodcuto --]
				    <div class="b2b-detail-tab-body">
				        
				        <div id="tab01" class="b2b-detail-tab-body-content active">
				            <div>
				            
				
				                <div class="b2b-detail-tab-row d-flex flex-wrap">
				
				                    <div class="b2b-detail-tab-col b2b-detail-tab-table">
				                        <p class="b2b-detail-tab-col-title">${i18n['cione-module.templates.components.albaranes-component.descripcion']}</p>
				                        <div class="b2b-detail-tab-text-wrapper">${descripcion}</div>
				                    </div>
				                    <div class="b2b-detail-tab-col b2b-detail-tab-table">
				                        <p class="b2b-detail-tab-col-title">${i18n['cione-module.templates.components.detalle-producto-component.propiedades']}</p>
										
				                        <table>
				                        	
				                        	[#if infoVariant.getReemplazo()?has_content]
					                        	<tr>
					                                <td>${i18n['cione-module.templates.components.detalle-producto-component.reemplazo']}</td>
					                                <td>${infoVariant.getReemplazo()!}</td>
					                            </tr>
							                [/#if]
							                
							                [#if infoVariant.getGeometria()?has_content]
					                            <tr>
					                                <td>${i18n['cione-module.templates.components.detalle-producto-component.geometria']}</td>
					                                <td>${infoVariant.getGeometria()!}</td>
					                            </tr>
							                [/#if]
				                        	
				                        	[#if infoVariant.getFormato()?has_content]
					                            <tr>
					                                <td>${i18n['cione-module.templates.components.detalle-producto-component.formato']}</td>
					                                <td>${infoVariant.getFormato()!}</td>
					                            </tr>
							                [/#if]
				                        	
				                        	[#if infoVariant.getMaterialBase()?has_content]
					                            <tr>
					                                <td>${i18n['cione-module.templates.components.detalle-producto-component.material']}</td>
					                                <td>${infoVariant.getMaterialBase()!}</td>
					                            </tr>
							                [/#if]
							                
							                [#if infoVariant.getMaterialDetalle()?has_content]
					                            <tr>
					                                <td>${i18n['cione-module.templates.components.detalle-producto-component.material-detalle']}</td>
					                                <td>${infoVariant.getMaterialDetalle()!}</td>
					                            </tr>
							                [/#if]
							                
							                [#if infoVariant.getContenidoAgua()?has_content]
					                            <tr>
					                                <td>${i18n['cione-module.templates.components.detalle-producto-component.contenido-agua']}</td>
					                                <td>${infoVariant.getContenidoAgua()!}</td>
					                            </tr>
							                [/#if]
				                        	
				                        	[#if infoVariant.getHidratacion()?has_content]
					                            <tr>
					                                <td>${i18n['cione-module.templates.components.detalle-producto-component.hidratacion']}</td>
					                                <td>${infoVariant.getHidratacion()!}</td>
					                            </tr>
							                [/#if]
				                        	
				                        	[#if infoVariant.getBproteccionSolar()?has_content]
					                            <tr>
					                                <td>${i18n['cione-module.templates.components.detalle-producto-component.proteccionsolar']}</td>
					                                [#if infoVariant.getBproteccionSolar() == "true"]
					                                	<td>${i18n['cione-module.templates.components.detalle-producto-component.si']}</td>
					                                [#else]
					                                	<td>${i18n['cione-module.templates.components.detalle-producto-component.no']}</td>
					                                [/#if]
					                            </tr>
					                        [#else]
					                        	<tr>
					                                <td>${i18n['cione-module.templates.components.detalle-producto-component.proteccionsolar']}</td>
					                                <td>${i18n['cione-module.templates.components.detalle-producto-component.no']}</td>
					                            </tr>
							                [/#if]
				                        	
				                        	[#if infoVariant.getDkt()?has_content]
					                            <tr>
					                                <td>${i18n['cione-module.templates.components.detalle-producto-component.dk']}</td>
					                                <td>${infoVariant.getDkt()!}</td>
					                            </tr>
							                [/#if]
				                        	
				                        	[#if infoVariant.getEquivProveedor()?has_content]
					                            <tr>
					                                <td>${i18n['cione-module.templates.components.detalle-producto-component.proveedor']}</td>
					                                <td>${infoVariant.getEquivProveedor()!}</td>
					                            </tr>
							                [/#if]
				                        	
				                        	[#if infoVariant.getEquivProducto()?has_content]
					                            <tr>
					                                <td>${i18n['cione-module.templates.components.detalle-producto-component.equivalencia']}</td>
					                                <td>${infoVariant.getEquivProducto()!}</td>
					                            </tr>
							                [/#if]
				                            
				                        </table>
				                        
				                    </div>
				                </div>
				            </div>
				        </div>
				        
				        [#-- condiciones de envio --]
				        <div id="tab02" class="b2b-detail-tab-body-content ">
				        	[#assign flag = true]
				        	[#assign specificMessages = infoVariant.getMensajesEspecificos()!]
				        	[#list specificMessages as message]
				        		[#if flag]
						        	<div class="b2b-detail-tab-row">
				                		<div class="b2b-detail-tab-col">
				                    		<p class="b2b-detail-tab-col-title">${message.getTitle()}</p>
						                    <p class="b2b-detail-tab-col-text">${message.getDescripcion()}</p>
						                </div>
						            	[#assign flag = false]
						        [#else]
						        		<div class="b2b-detail-tab-col">
				                    		<p class="b2b-detail-tab-col-title">${message.getTitle()}</p>
						                    <p class="b2b-detail-tab-col-text">${message.getDescripcion()}</p>
						                </div>
						            </div>
						            [#assign flag = true]
						        [/#if]
				        	[/#list]
				        </div>
				    </div>
	    
		[#break]
		
		[#case "liquidos"]
			
		    <div class="b2b-detail-tabs-container">
				<section class="b2b-detail-tabs">
					[#assign specificMessages = infoVariant.getMensajesEspecificos()!]
					[#if specificMessages?has_content]
					[#-- pestañas --]
				    <div class="b2b-detail-tab-header">
				        <div class="b2b-detail-tab-title selected" data-id="tab01">${i18n['cione-module.templates.components.detalle-producto-component.condiciones-de-envio']}</div>
				    </div>
				    
				    [#-- descripcion del prodcuto --]
				    <div class="b2b-detail-tab-body">
				        
				        [#-- condiciones de envio --]
				        <div id="tab01" class="b2b-detail-tab-body-content active">
				        	[#assign flag = true]
				        	[#list specificMessages as message]
				        		[#if flag]
						        	<div class="b2b-detail-tab-row">
				                		<div class="b2b-detail-tab-col">
				                    		<p class="b2b-detail-tab-col-title">${message.getTitle()}</p>
						                    <p class="b2b-detail-tab-col-text">${message.getDescripcion()}</p>
						                </div>
						            	[#assign flag = false]
						        [#else]
						        		<div class="b2b-detail-tab-col">
				                    		<p class="b2b-detail-tab-col-title">${message.getTitle()}</p>
						                    <p class="b2b-detail-tab-col-text">${message.getDescripcion()}</p>
						                </div>
						            </div>
						            [#assign flag = true]
						        [/#if]
				        	[/#list]
				        </div>
				    </div>
				    [/#if]
		[#break]
		[#case "complementosaudio"]
			
		    <div class="b2b-detail-tabs-container">
				<section class="b2b-detail-tabs">
					[#assign specificMessages = infoVariant.getMensajesEspecificos()!]
					[#if specificMessages?has_content]
					[#-- pestañas --]
				    <div class="b2b-detail-tab-header">
				        <div class="b2b-detail-tab-title selected" data-id="tab01">${i18n['cione-module.templates.components.detalle-producto-component.condiciones-de-envio']}</div>
				    </div>
				    
				    [#-- descripcion del prodcuto --]
				    <div class="b2b-detail-tab-body">
				        
				        [#-- condiciones de envio --]
				        <div id="tab01" class="b2b-detail-tab-body-content active">
				        	[#assign flag = true]
				        	[#list specificMessages as message]
				        		[#if flag]
						        	<div class="b2b-detail-tab-row">
				                		<div class="b2b-detail-tab-col">
				                    		<p class="b2b-detail-tab-col-title">${message.getTitle()}</p>
						                    <p class="b2b-detail-tab-col-text">${message.getDescripcion()}</p>
						                </div>
						            	[#assign flag = false]
						        [#else]
						        		<div class="b2b-detail-tab-col">
				                    		<p class="b2b-detail-tab-col-title">${message.getTitle()}</p>
						                    <p class="b2b-detail-tab-col-text">${message.getDescripcion()}</p>
						                </div>
						            </div>
						            [#assign flag = true]
						        [/#if]
				        	[/#list]
				        </div>
				    </div>
				    [/#if]
		[#break]
		[#case "marketing"]
			
		    <div class="b2b-detail-tabs-container">
				<section class="b2b-detail-tabs">
					[#assign specificMessages = infoVariant.getMensajesEspecificos()!]
					[#if specificMessages?has_content]
					[#-- pestañas --]
				    <div class="b2b-detail-tab-header">
				        <div class="b2b-detail-tab-title selected" data-id="tab01">${i18n['cione-module.templates.components.detalle-producto-component.condiciones-de-envio']}</div>
				    </div>
				    
				    [#-- descripcion del prodcuto --]
				    <div class="b2b-detail-tab-body">
				        
				        [#-- condiciones de envio --]
				        <div id="tab01" class="b2b-detail-tab-body-content active">
				        	[#assign flag = true]
				        	[#list specificMessages as message]
				        		[#if flag]
						        	<div class="b2b-detail-tab-row">
				                		<div class="b2b-detail-tab-col">
				                    		<p class="b2b-detail-tab-col-title">${message.getTitle()}</p>
						                    <p class="b2b-detail-tab-col-text">${message.getDescripcion()}</p>
						                </div>
						            	[#assign flag = false]
						        [#else]
						        		<div class="b2b-detail-tab-col">
				                    		<p class="b2b-detail-tab-col-title">${message.getTitle()}</p>
						                    <p class="b2b-detail-tab-col-text">${message.getDescripcion()}</p>
						                </div>
						            </div>
						            [#assign flag = true]
						        [/#if]
				        	[/#list]
				        </div>
				    </div>
				    [/#if]
		[#break]
		[#case "accesorios"]
			
		    <div class="b2b-detail-tabs-container">
				<section class="b2b-detail-tabs">
					[#assign specificMessages = infoVariant.getMensajesEspecificos()!]
					[#if specificMessages?has_content]
					[#-- pestañas --]
				    <div class="b2b-detail-tab-header">
				        <div class="b2b-detail-tab-title selected" data-id="tab01">${i18n['cione-module.templates.components.detalle-producto-component.condiciones-de-envio']}</div>
				    </div>
				    
				    [#-- descripcion del prodcuto --]
				    <div class="b2b-detail-tab-body">
				        
				        [#-- condiciones de envio --]
				        <div id="tab01" class="b2b-detail-tab-body-content active">
				        	[#assign flag = true]
				        	[#list specificMessages as message]
				        		[#if flag]
						        	<div class="b2b-detail-tab-row">
				                		<div class="b2b-detail-tab-col">
				                    		<p class="b2b-detail-tab-col-title">${message.getTitle()}</p>
						                    <p class="b2b-detail-tab-col-text">${message.getDescripcion()}</p>
						                </div>
						            	[#assign flag = false]
						        [#else]
						        		<div class="b2b-detail-tab-col">
				                    		<p class="b2b-detail-tab-col-title">${message.getTitle()}</p>
						                    <p class="b2b-detail-tab-col-text">${message.getDescripcion()}</p>
						                </div>
						            </div>
						            [#assign flag = true]
						        [/#if]
				        	[/#list]
				        </div>
				    </div>
				    [/#if]
		[#break]
		[#case "maquinaria"]
			
		    <div class="b2b-detail-tabs-container">
				<section class="b2b-detail-tabs">
					
					[#-- pestañas --]
				    <div class="b2b-detail-tab-header">
				    	<div class="b2b-detail-tab-title selected" data-id="tab01">${i18n['cione-module.templates.components.detalle-producto-component.detalles']}</div>
				    	[#assign specificMessages = infoVariant.getMensajesEspecificos()!]
				        [#if specificMessages?has_content]
				        <div class="b2b-detail-tab-title" data-id="tab02">${i18n['cione-module.templates.components.detalle-producto-component.condiciones-de-envio']}</div>
				        [/#if]
				    </div>
				    
				    [#-- descripcion del prodcuto --]
				    <div class="b2b-detail-tab-body">
				        
				        <div id="tab01" class="b2b-detail-tab-body-content active">
				            <div>
				            
				
				                <div class="b2b-detail-tab-row d-flex flex-wrap">
				
				                    <div class="b2b-detail-tab-col b2b-detail-tab-table">
				                        <p class="b2b-detail-tab-col-title">${i18n['cione-module.templates.components.albaranes-component.descripcion']}</p>
				                        <div class="b2b-detail-tab-text-wrapper">${descripcion}</div>
				                    </div>
				                    <div class="b2b-detail-tab-col b2b-detail-tab-table">
				                        <p class="b2b-detail-tab-col-title">${i18n['cione-module.templates.components.detalle-producto-component.propiedades']}</p>
										
				                        <table>
				                        	
				                        	[#-- ejemplo de obtencion de datos
				                        	[#if lenteequivProveedor?has_content]
					                            <tr>
					                                <td>${i18n['cione-module.templates.components.detalle-producto-component.proveedor']}</td>
					                                <td>${lenteequivProveedor!}</td>
					                            </tr>
							                [/#if]
							                --]
				                        	
				                            <tr>
				                                <td>Propiedad 1</td>
				                                <td>Por definir</td>
				                            </tr>
				                            
				                        </table>
				                        
				                    </div>
				                </div>
				            </div>
				        </div>
				        
				        [#-- condiciones de envio --]
				        [#if specificMessages?has_content]
				        <div id="tab02" class="b2b-detail-tab-body-content">
				        	[#assign flag = true]
				        	[#list specificMessages as message]
				        		[#if flag]
						        	<div class="b2b-detail-tab-row">
				                		<div class="b2b-detail-tab-col">
				                    		<p class="b2b-detail-tab-col-title">${message.getTitle()}</p>
						                    <p class="b2b-detail-tab-col-text">${message.getDescripcion()}</p>
						                </div>
						            	[#assign flag = false]
						        [#else]
						        		<div class="b2b-detail-tab-col">
				                    		<p class="b2b-detail-tab-col-title">${message.getTitle()}</p>
						                    <p class="b2b-detail-tab-col-text">${message.getDescripcion()}</p>
						                </div>
						            </div>
						            [#assign flag = true]
						        [/#if]
				        	[/#list]
				        </div>
				        [/#if]
				        
				    </div>
				    
		[#break]
		[#case "packs"]
			
		    <div class="b2b-detail-tabs-container">
				<section class="b2b-detail-tabs">
					
					[#if specificMessages?has_content]
					[#-- pestañas --]
				    <div class="b2b-detail-tab-header">
				        <div class="b2b-detail-tab-title selected" data-id="tab01">${i18n['cione-module.templates.components.detalle-producto-component.condiciones-de-envio']}</div>
				    </div>
				    
				    [#-- descripcion del prodcuto --]
				    <div class="b2b-detail-tab-body">
				        
				        [#-- condiciones de envio --]
				        <div id="tab01" class="b2b-detail-tab-body-content active">
				        	[#assign flag = true]
				        	[#list specificMessages as message]
				        		[#if flag]
						        	<div class="b2b-detail-tab-row">
				                		<div class="b2b-detail-tab-col">
				                    		<p class="b2b-detail-tab-col-title">${message.getTitle()}</p>
						                    <p class="b2b-detail-tab-col-text">${message.getDescripcion()}</p>
						                </div>
						            	[#assign flag = false]
						        [#else]
						        		<div class="b2b-detail-tab-col">
				                    		<p class="b2b-detail-tab-col-title">${message.getTitle()}</p>
						                    <p class="b2b-detail-tab-col-text">${message.getDescripcion()}</p>
						                </div>
						            </div>
						            [#assign flag = true]
						        [/#if]
				        	[/#list]
				        </div>
				    </div>
				    [/#if]
			[#break]
		[#default]
		<div class="b2b-detail-tabs-container">
			<section class="b2b-detail-tabs mt-5">
			    <div class="b2b-detail-tab-header">
			        <div class="b2b-detail-tab-title selected" data-id="tab01">
			            ${i18n['cione-module.templates.components.detalle-producto-component.detalles']}</div>
			        <div class="b2b-detail-tab-title" data-id="tab02">${i18n['cione-module.templates.components.detalle-producto-component.condiciones-comerciales']}</div>
			    </div>
			    <div class="b2b-detail-tab-body">
			        <div id="tab01" class="b2b-detail-tab-body-content active">
			            <div>
			                <p class="b2b-detail-tab-text">${descripcion}</p>
			
			                <div class="b2b-detail-tab-row d-flex flex-wrap">
			                    <div class="b2b-detail-tab-col b2b-detail-tab-table">
			                        <p class="b2b-detail-tab-col-title">${i18n['cione-module.templates.components.detalle-producto-component.propiedades']}</p>
			            
			                        <table>
			                        	[#if infoVariant.coleccion?? && infoVariant.coleccion?has_content]
				                        	<tr>
				                                <td>${i18n['cione-module.templates.components.detalle-producto-component.coleccion']}</td>
				                                <td>${infoVariant.getColeccion()!}</td>
				                            </tr>
			                            [/#if]
			                            [#if target?? && target?has_content]
				                            <tr>
				                                <td>${i18n['cione-module.templates.components.detalle-producto-component.target']}</td>
				                                <td>${target}</td>
				                            </tr>
			                            [/#if]
			                            [#if tipoMontaje?? && tipoMontaje?has_content]
				                            <tr>
				                                <td>${i18n['cione-module.templates.components.detalle-producto-component.tipo-montaje']}</td>
				                                <td>${tipoMontaje}</td>
				                            </tr>
			                            [/#if]
			                            [#if material?? && material?has_content]
				                            <tr>
				                                <td>${i18n['cione-module.templates.components.detalle-producto-component.material']}</td>
				                                <td>${material}</td>
				                            </tr>
			                            [/#if]
			                        </table>
			                    </div>
			                    <div class="b2b-detail-tab-col b2b-detail-tab-image">
			                        <p class="b2b-detail-tab-col-title">${i18n['cione-module.templates.components.detalle-producto-component.dimensiones']}</p>
			                     <div class="b2b-detail-tab-images-wrapper">
			                     	<div><img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/common/1.png" alt="">
			                     		<p class="b2b-detail-tab-images-text">${infoVariant.getDimensiones_largo_varilla()!}mm</p></div>
			                        <div><img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/common/3.png" alt="">
			                     		<p class="b2b-detail-tab-images-text">${infoVariant.getDimensiones_ancho_ojo()!}mm</p></div>
			                     	<div><img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/common/4.png" alt="">
			                     		<p class="b2b-detail-tab-images-text">${infoVariant.getDimensiones_ancho_puente()!}mm</p></div>
			                     </div>
			                    </div>
			                </div>
			            </div>
			        </div>
			        <div id="tab02" class="b2b-detail-tab-body-content">
			        	[#assign flag = true]
			        	[#assign specificMessages = infoVariant.getMensajesEspecificos()!]
			        	[#list specificMessages as message]
			        		[#if flag]
					        	<div class="b2b-detail-tab-row">
			                		<div class="b2b-detail-tab-col">
			                    		<p class="b2b-detail-tab-col-title">${message.getTitle()}</p>
					                    <p class="b2b-detail-tab-col-text">${message.getDescripcion()}</p>
					                </div>
					            	[#assign flag = false]
					        [#else]
					        		<div class="b2b-detail-tab-col">
			                    		<p class="b2b-detail-tab-col-title">${message.getTitle()}</p>
					                    <p class="b2b-detail-tab-col-text">${message.getDescripcion()}</p>
					                </div>
					            </div>
					            [#assign flag = true]
					        [/#if]
			        	[/#list]
			        </div>
			    </div>	
		[#break]
	[/#switch]

[/#if]
[/#macro]

[#macro macropacks infovariant]
	[#if infovariant.getContenidoPack()?has_content]
		[#list infovariant.contenidoPack as productPack]
			
		        <div class="product-pack-title"><span>${productPack.nombreArticulo!}</span><span style="font-size: 20px;"> (${productPack.unidadesPack!} unidades)</span></div>

		        [#if showPVO(ctx.getUser(), uuid, username)]
		        	<div class="product-price-wrapper">
			        	<div class="product-pack-title">
			        		<div class="product-pack-pvo">PVO Individual</div>
			        		<div class="product-pack-amount" id="pvo-individual-${productPack?index}" style="text-decoration: line-through;">${productPack.pvo!}</div>
			        	</div>
			        	<div class="product-pack-title">
			        		<div class="product-pack-pvo">PVO Pack</div>
			        		<div class="product-pack-amount">${productPack.pvoPackUD!} €</div>
			        	</div>
			        	<div class="product-pack-title" style="width:130px; visibility:hidden">
			        		<div class="product-pack-pvo"></div>
			        		<div class="product-pack-amount"></div>
			        	</div>
		        	</div>
		        [/#if]
		    
		   
		
		    <div class="product-separator"></div>
	    
	    [/#list]
	[/#if]
    [#if infovariant.pertenecePack]
	    <div class="product-available">
		    Este producto está disponible en Pack
		</div>
	[/#if]
[/#macro]

[#macro macrocarruselPacks infovariant]
	[#if infovariant.getContenidoPack()?has_content]
		<div class="b2b-pack-carousel">
	        <p class="b2b-pack-carousel--title">${i18n['cione-module.templates.components.detalle-producto-component.contenido-pack']}</p>
	
	        <div class="b2b-owl-carousel-pack--container">
	            <div class="owl-carousel owl-theme b2b-owl-carousel-pack">
					[#list infovariant.contenidoPack as productPack]
						[#assign imgppal = ""]
						[#if productPack.masterImage?? && productPack.masterImage?has_content]
							[#assign imgppal = productPack.masterImage.getUrl()!]
						[/#if]
						[#--  --if productPack.listImages?? && productPack.listImages?has_content]
						    [#list productPack.listImages as imagen]
						    	[#assign imgppal = imagen.getUrl()!]
						    [/#list]
						[/#if--]
						<div class="item" style="max-width:210px"> 
							[#assign link = "#"]
							[#if content.internalLinkPacks?has_content && content.internalLinkPacks??]
								[#if productPack.sku?? && productPack.sku?has_content]
									[#assign link = cmsfn.link("website", content.internalLinkPacks!)!]
									[#assign link = link + "?sku=" + productPack.sku!]
									<a href="${link!"#"}" target="_blank" style="text-decoration: none;">
										<img class="b2b-detalle-miniaturas-imagen"
					                        src="${imgppal}" alt="" />
					                    <div class="b2b-pack-carosuel--product">${productPack.nombreArticulo!}</div>
					                    [#if showPVO(ctx.getUser(), uuid, username)]
					                    	<div class="b2b-pack-carosuel--price">${productPack.pvo!} €</div>
					                    [/#if]
					                </a>
					            [#else]
					            	<div>
										<img class="b2b-detalle-miniaturas-imagen"
					                        src="${imgppal}" alt="" />
					                    <div class="b2b-pack-carosuel--product">${productPack.nombreArticulo!}</div>
					                    [#if showPVO(ctx.getUser(), uuid, username)]
					                    	<div class="b2b-pack-carosuel--price">${productPack.pvo!} €</div>
					                    [/#if]
					                </div>
					        	[/#if]
							[/#if]
							
		                </div>
				    
				    [/#list]
				</div>
	
	        </div>
		
    	</div>
	[/#if]
	[#if infovariant.pertenecePack]
		<div class="b2b-pack-carousel">
	        <p class="b2b-pack-carousel--title">${i18n['cione-module.templates.components.detalle-producto-component.pack-disponibles']}</p>
	
	        <div class="b2b-owl-carousel-pack--container">
	            <div class="owl-carousel owl-theme b2b-owl-carousel-pack">
	                [#list infovariant.packsContienenProducto as productPack]
	                	[#assign imgppal = ""]
						[#if productPack.master.images?has_content]
						    [#list productPack.master.images as imagen]
						    	[#assign imgppal = imagen.getUrl()!]
						    [/#list]
						[/#if]
		                <div class="item" style="max-width:210px"> 
		                	[#assign link = "#"]
							[#if content.internalLinkPacks?has_content && content.internalLinkPacks??]
								[#assign link = cmsfn.link("website", content.internalLinkPacks!)!]
								[#assign skuPack = productPack.master.sku!'ERROR']
								[#assign link = link + "?sku=" + skuPack]
							[/#if]
							<a href="${link!"#"}" target="_blank" style="text-decoration: none;">
			                	<img class="b2b-detalle-miniaturas-imagen"
			                        src="${imgppal}" alt="" />
			                    <div class="b2b-pack-carosuel--product">
			                        ${productPack.master.name!}
			                    </div>
			                    [#if showPVO(ctx.getUser(), uuid, username)]
				                    <div class="b2b-pack-carosuel--price">
				                        #{productPack.master.pvo!;M2} €
				                    </div>
				                [/#if]
			                </a>
		                </div>
	                [/#list]
	            </div>
	
	        </div>
		
    	</div>
    [/#if]
[/#macro]

[#macro macrostock]
	[#-- stock del producto --]
	[#assign colorStock = "high-stock"]
    [#if stock == 0]
    	[#assign colorStock = "low-stock"]
    [#elseif stock <= 2]
    	[#assign colorStock = "medium-stock"]
    [/#if]
    
    [#assign stockctral = model.getStock(infoVariant.aliasEkon!).getStockCTRAL()!""]
    [#assign colorStockCentral = "high-stock"]
    [#if almacen?? && almacen?has_content && (almacen == "stockCANAR")]
    	
    	[#if stockctral == 0]
	    	[#assign colorStockCentral = "low-stock"]
	    [#elseif stockctral <= 2]
	    	[#assign colorStockCentral = "medium-stock"]
	    [/#if]
    [/#if]
    [#if infoVariant.gestionStock]
		<div class="product-stock">
	        [#assign stocklabel = i18n['cione-module.templates.myshop.listado-productos-carrito-component.real']] 
	        [#if almacen?? && almacen?has_content]
	            [#if almacen == "stockCANAR"]
	            	[#if stockctral?? && stockctral?has_content]
	            		<div class="product-stock-canar ${colorStock}">
	            			<span class="circle-product-stock"></span>
		            		<p class="product-stock-canar">${i18n['cione-module.templates.myshop.listado-productos-home-component.canar']}${stock!""}</p>
		            	</div>
		            	<div class="product-stock-ctral ${colorStockCentral}">
	            			<span class="circle-product-stock"></span>
		            		<p class="product-stock-ctral">${i18n['cione-module.templates.myshop.listado-productos-home-component.ctral']}${stockctral!""}</p>
		            	</div>
	            	[#else]
	            		<div class="product-stock-ctral ${colorStock}">
	            			<span class="circle-product-stock"></span>
	            			<p>${stock} ${i18n['cione-module.templates.components.detalle-producto-component.in-stock']}</p>
	            		</div>
	            	[/#if]
	        	[#else]
	        		<div class="product-stock-ctral ${colorStock}">
	            		<span class="circle-product-stock"></span>
	        			<p>${stock} ${i18n['cione-module.templates.components.detalle-producto-component.in-stock']}</p>
	        		</div>
	            [/#if]
	        [/#if]
		</div>
	[/#if]
[/#macro]

[#macro macrovariantes infoVariantMacro]
[#assign familiaProducto=infoVariantMacro.getFamiliaProducto()!]
[#switch familiaProducto]
	[#case "monturas"]
		<div class="product-amount" style="margin-bottom: 5px;">  
			<div class="product-block-title">
		        ${i18n['cione-module.templates.components.detalle-producto-component.color-variantes']}
		    </div>
		    [@macrostock /]
		</div>
		<div class="product-color-select">
			[#assign calibres = ""]
			[#assign graduaciones = ""]
			[#assign listColor = infoVariantMacro.getColors()!]
			[#list listColor as color]
				[#assign colorIcono = color.getColorIcono()]
				[#assign codColor_colorMontura = color.getCodigoColor()! + "-" + color.getColorMontura()!]
				[#if color.getColorIcono() == "#ZZZZZZ"] [#-- fix para variantes sin iconoColor --]
					[#if color.isSelected()]
		    			<div id="${color.getSku()}" class="product-color-circle selected icoNone hover-text" style="background-color:#ffffff; background: linear-gradient(-45deg, white 12px, black 15px, black 15px, white 17px );">
		    				<span class="tooltip-text" id="top">${color.getCodigoColor()!} - ${color.getColorMontura()!}</span>
		    			</div>
		    			[#assign calibres = color.getCalibres()!]
		    			[#assign graduaciones = color.getGraduaciones()!]
		    		[#else]
		    			<div id="${color.getSku()}" class="product-color-circle icoNone hover-text"" style="background-color:#ffffff; background: linear-gradient(-45deg, white 12px, black 15px, black 15px, white 17px );">
		    				<span class="tooltip-text" id="top">${color.getCodigoColor()!} - ${color.getColorMontura()!}</span>
		    			</div>
		    		[/#if]
				[#else]
					[#if color.isSelected()]
		    			<div id="${color.getSku()}" class="product-color-circle selected hover-text" style="background-color:${color.getColorIcono()}">
		    				<span class="tooltip-text" id="top">${color.getCodigoColor()!} - ${color.getColorMontura()!}</span>
		    			</div>
		    			[#assign calibres = color.getCalibres()!]
		    			[#assign graduaciones = color.getGraduaciones()!]
		    		[#else]
		    			<div id="${color.getSku()}" class="product-color-circle hover-text" style="background-color:${color.getColorIcono()}">
		    				<span class="tooltip-text" id="top">${color.getCodigoColor()!} - ${color.getColorMontura()!}</span>
		    			</div>
		    		[/#if]
		    	[/#if]
			[/#list]
		    
		</div>
		
		[#if calibres?has_content]
		    <div class="product-block-title">
		        ${i18n['cione-module.templates.components.detalle-producto-component.calibre']}
		    </div>
		    <div class="product-calibre-wrapper">
		    	[#list calibres as calibre]
		    		[#if calibre.isSelected()]
		    			<div id="calibre-${calibre.getSku()}" class="product-calibre selected">${calibre.getCalibre()}</div>
		    		[#else]
		    			<div id="calibre-${calibre.getSku()}" class="product-calibre">${calibre.getCalibre()}</div>
		    		[/#if]
			    	
			    [/#list]
		
		    </div>
		[/#if]
		[#if graduaciones?has_content]
			<div class="product-combo">
		        <div class="product-combo-title">${i18n['cione-module.templates.components.detalle-producto-component.graduacion']}</div>
		        <select name="" id="select-graduacion">
		    	[#list graduaciones as graduacion]
		    		[#if graduacion.isSelected()]
		    			<option selected="selected" class="product-graduacion" id="graduacion-${graduacion.getSku()}" value="graduacion-${graduacion.getSku()}">${graduacion.getGraduacion()}</option>
		    		[#else]
		    			<option class="product-graduacion" id="graduacion-${graduacion.getSku()}" value="graduacion-${graduacion.getSku()}">${graduacion.getGraduacion()}</option>
		    		[/#if]
			    [/#list]
		        </select>
		    </div>
		[/#if]
	[#break]
	[#case "accesorios"]
	
	[#break]
	[#case "marketing"]
	
	[#break]
	[#case "liquidos"]
		[#assign listTamanios = infoVariantMacro.getTamaniosLiquidos()!]
		[#if listTamanios?has_content]
			<div class="product-amount" style="margin-bottom: 5px;">  
				<div class="product-block-title">
		            ${i18n['cione-module.templates.components.detalle-producto-component.tamanio']}
		        </div>
			    [@macrostock /]
			</div>
	        <div class="product-calibre-wrapper">
	        	[#list listTamanios as tamanio]
		    		[#if tamanio.isSelected()]
		    			<div id="calibre-${tamanio.getSku()}" class="product-calibre selected">${tamanio.getTamanio()}</div>
		    		[#else]
		    			<div id="calibre-${tamanio.getSku()}" class="product-calibre">${tamanio.getTamanio()}</div>
		    		[/#if]
			    	
			    [/#list]
	        </div>
	    [/#if]
	[#break]
	[#case "contactologia"]
			[#assign unidades = 0]
			[#if infoVariantMacro.unidadesPack?? && infoVariantMacro.unidadesPack?has_content]
				[#assign unidades = infoVariantMacro.unidadesPack /2]
			[/#if]
			[#-- configurador --]
            <div class="product-configurador">
            	[#if infoVariantMacro.getConfig()?has_content && infoVariantMacro.getConfig()]
                	<div class="product-configurador-referencia row">
	                	<div class="col-12 pt-4">
	                		<p style="font-weight:bold">
	                		<i class="fas fa-info-circle"></i>
							${content.infoconfig!""}
							</p>
						</div>
	                </div>
	                <div class="product-separator"></div>
				[/#if]
      			[#-- labels --]
                <div class="producto-configurador-subtitle row">
                    <div class="col-3"></div>
                    <div class="col-4">${i18n['cione-module.templates.components.detalle-producto-component.ojodrch']}</div>
                    <div class="col-1"></div>
                    <div class="col-4 ">${i18n['cione-module.templates.components.detalle-producto-component.ojoizq']}</div>
                </div>
                
                
                [#if infoVariantMacro.getConfig()?has_content]
	                [#if infoVariantMacro.getConfig()]
	                	[#-- Diseno (xtipodepieza): no cambia --]
						[#if infoVariantMacro.getDiseno()?has_content]
		                <div class="product-configurador-selects row">
		                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.design']}</div>
		                    <div class="col-4"> 
		                    	<select id="disenolentedrch" autocomplete="off">
		                    		[#assign disenos = infoVariantMacro.getDiseno()]
		                    		[#if disenos?size > 1]
		                    			<option value="" selected></option>
		                    		[/#if]
		                    		[#list disenos as val]
		                            	<option value="${val!""}">${val!""}</option>
			                        [/#list]
		                        </select>
		                    </div>
		                    <div class="col-1"></div>
		                    <div class="col-4"> 
		                    	<select id="disenolenteizq" autocomplete="off">
		                    		[#if disenos?size > 1]
		                    			<option value="" selected></option>
		                    		[/#if]
		                    		[#list disenos as val]
		                            	<option value="${val!""}">${val!""}</option>
			                        [/#list]
		                        </select>
		                    </div>
		                </div>
						[/#if]
						
	                	[#-- Esfera (xesfera): cambia segun el diseno seleccionado --]
		                [#if infoVariantMacro.getEsferaList()?has_content && infoVariantMacro.getEsferaList()?size > 0]
			                <div class="product-configurador-selects row">
			                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.esfera']}</div>
			                    <div class="col-4"> 
			                    	<select id="esferadrch" autocomplete="off">
			                    		[#assign esferas = infoVariantMacro.getEsferaList()]
			                    		[#if esferas?size > 1]
			                    			<option value="" selected></option>
			                    		[/#if]
			                    		[#list esferas as val]
				                        	<option value="${val!""}">${val!""}</option>
				                        [/#list]
			                    	</select>
			                	</div>
			                    <div class="col-1"></div>
			                    <div class="col-4"> 
			                    	<select id="esferaizq" autocomplete="off">
			                    		[#if esferas?size > 1]
			                    			<option value="" selected></option>
			                    		[/#if]
			                    		[#list esferas as val]
				                        	<option value="${val!""}">${val!""}</option>
				                        [/#list]
			                    	</select>
			                    </div>
			                </div>
		                [#else]
			                <div class="product-configurador-selects row" style="display: none;">
			                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.esfera']}</div>
			                    <div class="col-4"> 
			                    	<select id="esferadrch" style="display: none;" autocomplete="off">
		                    			<option value="" selected></option>
			                    	</select>
			                	</div>
			                    <div class="col-1"></div>
			                    <div class="col-4"> 
			                    	<select id="esferaizq" style="display: none;" autocomplete="off">
		                    			<option value="" selected></option>
			                    	</select>
			                    </div>
			                </div>
						[/#if]
	                	
	                	[#-- Cilindro (xcilindro): no cambia --]
	                	[#-- cilindro (xcilindro): cambia segun el diseno seleccionado --]
	                	[#assign cilindros = infoVariantMacro.getCilindroList()!]
		                [#if cilindros?has_content && cilindros?size > 0]
			                <div class="product-configurador-selects row">
			                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.cilindro']}</div>
			                    <div class="col-4"> 
				                    <select id="cilindrodrch" autocomplete="off">
				                    	
			                    		[#if cilindros?size > 1]
			                    			<option value="" selected></option>
			                    		[/#if]
			                    		[#list cilindros as val]
				                        	<option value="${val!""}">${val!""}</option>
				                        [/#list]
				                    </select>
			                    </div>
			                    <div class="col-1"></div>
			                    <div class="col-4"> 
			                    	<select id="cilindroizq" autocomplete="off">
		                    			[#if cilindros?size > 1]
			                    			<option value="" selected></option>
			                    		[/#if]
			                    		[#list cilindros as val]
				                        	<option value="${val!""}">${val!""}</option>
				                        [/#list]
			                    	</select>
			                	</div>
			                </div>
			            [#else]
			            	<div class="product-configurador-selects row" style="display: none;">
			                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.cilindro']}</div>
			                    <div class="col-4"> 
				                    <select id="cilindrodrch" style="display: none;" autocomplete="off">
				                    	
				                    </select>
			                    </div>
			                    <div class="col-1"></div>
			                    <div class="col-4"> 
			                    	<select id="cilindroizq" style="display: none;" autocomplete="off">
		                    			
			                    	</select>
			                	</div>
			                </div>
			            [/#if]
	                	
	                	[#-- Eje (xeje): no cambia --]
	                	[#assign ejes = infoVariantMacro.getEjeList()!]
	                	[#if ejes?has_content && ejes?size > 0]
			                <div class="product-configurador-selects row">
			                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.eje']}</div>
			                    <div class="col-4"> 
			                    	<select id="ejedrch" autocomplete="off">
			                    		[#if ejes?size > 1]
			                    			<option value="" selected></option>
			                    		[/#if]
			                    		[#list ejes as val]
				                        	<option value="${val!""}">${val!""}</option>
				                        [/#list]
			                        </select>
			                    </div>
			                    <div class="col-1"></div>
			                    <div class="col-4"> 
			                		<select id="ejeizq" autocomplete="off">
		                    			[#if ejes?size > 1]
			                    			<option value="" selected></option>
			                    		[/#if]
			                    		[#list ejes as val]
				                        	<option value="${val!""}">${val!""}</option>
				                        [/#list]
			                        </select>
			                    </div>
			                </div>
						[#else]
							<div class="product-configurador-selects row" style="display: none;">
			                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.eje']}</div>
			                    <div class="col-4"> 
			                    	<select id="ejedrch" style="display: none;" autocomplete="off">
		                    			
			                        </select>
			                    </div>
			                    <div class="col-1"></div>
			                    <div class="col-4"> 
			                		<select id="ejeizq" style="display: none;" autocomplete="off">
		                    			
			                        </select>
			                    </div>
			                </div>
						[/#if]
	                	[#-- Diametro (xdiametro): no cambia --]
	                	[#assign diametros = infoVariantMacro.getDiametroList()!]
	                	[#if diametros?has_content && diametros?size > 0]
			                <div class="product-configurador-selects row">
			                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.diametro']}</div>
			                    <div class="col-4"> 
			                		<select id="diametrodrch" autocomplete="off">
			                    		
			                    		[#if diametros?size > 1]
			                    			<option value="" selected></option>
			                    		[/#if]
			                    		[#list diametros as val]
				                        	<option value="${val!""}">${val!""}</option>
				                        [/#list]
			                        </select>
			                    </div>
			                    <div class="col-1"></div>
			                    <div class="col-4"> 
			                		<select id="diametroizq" autocomplete="off">
		                    			[#if diametros?size > 1]
			                    			<option value="" selected></option>
			                    		[/#if]
			                    		[#list diametros as val]
				                        	<option value="${val!""}">${val!""}</option>
				                        [/#list]
			                        </select>
			                    </div>
			                </div>
			            [#else]
			            	<div class="product-configurador-selects row" style="display: none;">
			                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.diametro']}</div>
			                    <div class="col-4"> 
			                		<select id="diametrodrch" style="display: none;" autocomplete="off">
			                    		
			                        </select>
			                    </div>
			                    <div class="col-1"></div>
			                    <div class="col-4"> 
			                		<select id="diametroizq" style="display: none;" autocomplete="off">
		                    			
			                        </select>
			                    </div>
			                </div>
						[/#if]
	                	[#-- Radio (xradio): cambia segun la esfera seleccionada--]
	                	[#-- Radio (xcurvaBase): no cambia --]
	                	[#assign radios = infoVariantMacro.getCurvaBaseList()!]
	                	[#if radios?has_content && radios?size > 0]
			                <div class="product-configurador-selects row">
			                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.curvabase']}</div>
			                    <div class="col-4"> 
			                    	<select id="curvabasedrch" autocomplete="off">
		                    			
			                    		[#if radios?size > 1]
			                    			<option value="" selected></option>
			                    		[/#if]
			                    		[#list radios as val]
				                        	<option value="${val!""}">${val!""}</option>
				                        [/#list]
			                        </select>
			                    </div>
			                    <div class="col-1"></div>
			                    <div class="col-4"> 
			                		<select id="curvabaseizq" autocomplete="off">
			                    		[#if radios?size > 1]
			                    			<option value="" selected></option>
			                    		[/#if]
			                    		[#list radios as val]
				                        	<option value="${val!""}">${val!""}</option>
				                        [/#list]
			                        </select>
			                    </div>
			                </div>
	                	[#else]
	                		<div class="product-configurador-selects row" style="display: none;">
			                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.curvabase']}</div>
			                    <div class="col-4"> 
			                    	<select id="curvabasedrch" style="display: none;" autocomplete="off">
		                    			
			                        </select>
			                    </div>
			                    <div class="col-1"></div>
			                    <div class="col-4"> 
			                		<select id="curvabaseizq" style="display: none;" autocomplete="off">
			                    		
			                        </select>
			                    </div>
			                </div>
	                	[/#if]
	                	[#-- Adicion (xadicion): no cambia --]
	                	[#assign adicion = infoVariantMacro.getAdicionListConfig()!]
	                	[#if adicion?has_content && adicion?size > 0]
		                	<div class="product-configurador-selects row">
			                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.adicion']}</div>
			                    <div class="col-4"> 
			                    	<select id="adiciondrch" autocomplete="off">
			                    		[#if adicion?size > 1]
			                    			<option value="" selected></option>
			                    		[/#if]
			                    		[#list adicion as val]
				                        	<option value="${val!""}">${val!""}</option>
				                        [/#list]
			                        </select>
			                    </div>
			                    <div class="col-1"></div>
			                    <div class="col-4"> 
			                    	<select id="adicionizq" autocomplete="off">
		                    			[#if adicion?size > 1]
			                    			<option value="" selected></option>
			                    		[/#if]
			                    		[#list adicion as val]
				                        	<option value="${val!""}">${val!""}</option>
				                        [/#list]
			                        </select>
			                    </div>
			                </div>
		                [#else]
		                	<div class="product-configurador-selects row" style="display: none;">
			                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.adicion']}</div>
			                    <div class="col-4"> 
			                    	<select id="adiciondrch" style="display: none;" autocomplete="off">
		                    			
			                        </select>
			                    </div>
			                    <div class="col-1"></div>
			                    <div class="col-4"> 
			                    	<select id="adicionizq" style="display: none;" autocomplete="off">
		                    			
			                        </select>
			                    </div>
			                </div>
		                [/#if]
	                	[#-- Color (xcolor): no cambia --]
	                	[#assign colores = infoVariantMacro.getColorlente()!]
	                	[#if colores?has_content && colores?size > 0]
			                <div class="product-configurador-selects row">
			                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.color']}</div>
			                    <div class="col-4"> 
			                    	<select id="colorlentedrch">
			                    		[#if colores?size > 1]
			                    			<option value="" selected></option>
			                    		[/#if]
                                
                                		[#list colores as val]
			                            	<option value="${val}">${val}</option>
			                            	[#-- <option value="${val?split("|")[0]!""}">${val?split("|")[1]!""}</option> --]
				                        [/#list]
			                        </select>
			                    </div>
			                    <div class="col-1"></div>
			                    <div class="col-4"> 
			                    	<select id="colorlenteizq">
			                    		[#if colores?size > 1]
			                    			<option value="" selected></option>
			                    		[/#if]
			                    		[#list colores as val]
			                            	<option value="${val}">${val}</option>
			                            	[#-- <option value="${val?split("|")[0]!""}">${val?split("|")[1]!""}</option> --]
				                        [/#list]
			                        </select>
			                    </div>
			                </div>
						[#else]
			                <div class="product-configurador-selects row" style="display: none;">
			                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.color']}</div>
			                    <div class="col-4"> 
			                    	<select id="colorlentedrch" style="display: none;" autocomplete="off"></select>
			                    </div>
			                    <div class="col-1"></div>
			                    <div class="col-4"> 
			                    	<select id="colorlenteizq" style="display: none;" autocomplete="off"></select>
			                    </div>
			                </div>
	                	[/#if]
	                [#else]
	                
	                	[#-- Esfera --]
		                [#if infoVariantMacro.getEsferaList()?has_content]
			                <div class="product-configurador-selects row">
			                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.esfera']}</div>
			                    <div class="col-4"> 
			                    	<select id="esferadrch">
			                    		[#assign esferas = infoVariantMacro.getEsferaList()]
			                    		[#if esferas?size > 1]
			                    			<option value="" selected></option>
			                    		[/#if]
			                    		[#list esferas as val]
				                        	<option value="${val!""}">${val!""}</option>
				                        [/#list]
			                    	</select>
			                	</div>
			                    <div class="col-1"></div>
			                    <div class="col-4"> 
			                    	<select id="esferaizq">
			                    		[#if esferas?size > 1]
			                    			<option value="" selected></option>
			                    		[/#if]
			                    		[#list esferas as val]
				                        	<option value="${val!""}">${val!""}</option>
				                        [/#list]
			                    	</select>
			                    </div>
			                </div>
						[/#if]
						
						[#-- Cilindro --]
						[#if infoVariantMacro.getCilindroList()?has_content]
		                <div class="product-configurador-selects row">
		                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.cilindro']}</div>
		                    <div class="col-4"> 
			                    <select id="cilindrodrch">
			                    	[#assign cilindros = infoVariantMacro.getCilindroList()]
		                    		[#if cilindros?size > 1]
		                    			<option value="" selected></option>
		                    		[/#if]
		                    		[#list cilindros as val]
			                        	<option value="${val!""}">${val!""}</option>
			                        [/#list]
			                    </select>
		                    </div>
		                    <div class="col-1"></div>
		                    <div class="col-4"> 
		                    	<select id="cilindroizq">
		                    		[#if cilindros?size > 1]
		                    			<option value="" selected></option>
		                    		[/#if]
		                    		[#list cilindros as val]
			                        	<option value="${val!""}">${val!""}</option>
			                        [/#list]
		                    	</select>
		                	</div>
		                </div>
						[/#if]
						
						[#-- Eje --]
						[#if infoVariantMacro.getEjeList()?has_content]
		                <div class="product-configurador-selects row">
		                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.eje']}</div>
		                    <div class="col-4"> 
		                    	<select id="ejedrch">
		                    		[#assign ejes = infoVariantMacro.getEjeList()]
		                    		[#if ejes?size > 1]
		                    			<option value="" selected></option>
		                    		[/#if]
		                    		[#list ejes as val]
			                        	<option value="${val!""}">${val!""}</option>
			                        [/#list]
		                        </select>
		                    </div>
		                    <div class="col-1"></div>
		                    <div class="col-4"> 
		                		<select id="ejeizq">
		                			[#if ejes?size > 1]
		                    			<option value="" selected></option>
		                    		[/#if]
		                    		[#list ejes as val]
			                        	<option value="${val!""}">${val!""}</option>
			                        [/#list]
		                        </select>
		                    </div>
		                </div>
						[/#if]
						
						[#-- Diametro --]
						[#if infoVariantMacro.getDiametroList()?has_content]
		                <div class="product-configurador-selects row">
		                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.diametro']}</div>
		                    <div class="col-4"> 
		                		<select id="diametrodrch">
		                			[#assign diametros = infoVariantMacro.getDiametroList()]
		                    		[#if diametros?size > 1]
		                    			<option value="" selected></option>
		                    		[/#if]
		                    		[#list diametros as val]
			                        	<option value="${val!""}" [#if val=="0"]selected[/#if]>${val!""}</option>
			                        [/#list]
		                        </select>
		                    </div>
		                    <div class="col-1"></div>
		                    <div class="col-4"> 
		                		<select id="diametroizq">
		                			[#if diametros?size > 1]
		                    			<option value="" selected></option>
		                    		[/#if]
		                    		[#list diametros as val]
			                        	<option value="${val!""}" [#if val=="0"]selected[/#if]>${val!""}</option>
			                        [/#list]
		                        </select>
		                    </div>
		                </div>
						[/#if]
						
						[#-- Curva base o radio --]
						[#if infoVariantMacro.getCurvaBaseList()?has_content]
		                <div class="product-configurador-selects row">
		                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.curvabase']}</div>
		                    <div class="col-4"> 
		                    	<select id="curvabasedrch">
		                    		[#assign curvas = infoVariantMacro.getCurvaBaseList()]
		                    		[#if curvas?size > 1]
		                    			<option value="" selected></option>
		                    		[/#if]
		                    		[#list curvas as val]
			                        	<option value="${val!""}">${val!""}</option>
			                        [/#list]
		                        </select>
		                    </div>
		                    <div class="col-1"></div>
		                    <div class="col-4"> 
		                		<select id="curvabaseizq">
		                			[#if curvas?size > 1]
		                    			<option value="" selected></option>
		                    		[/#if]
		                    		[#list curvas as val]
			                        	<option value="${val!""}">${val!""}</option>
			                        [/#list]
		                        </select>
		                    </div>
		                </div>
						[/#if]
						
						[#-- Adicion --]
						[#if infoVariantMacro.getAdicion()?has_content]
		                <div class="product-configurador-selects row">
		                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.adicion']}</div>
		                    <div class="col-4"> 
		                    	<select id="adiciondrch">
		                    		[#assign adiciones = infoVariantMacro.getAdicion()]
		                    		[#if adiciones?size > 1]
		                    			<option value="" selected></option>
		                    		[/#if]
		                    		[#list adiciones as val]
			                        	<option value="${val!""}">${val!""}</option>
			                        [/#list]
		                        </select>
		                    </div>
		                    <div class="col-1"></div>
		                    <div class="col-4"> 
		                    	<select id="adicionizq">
		                    		[#if adiciones?size > 1]
		                    			<option value="" selected></option>
		                    		[/#if]
		                    		[#list adiciones as val]
			                        	<option value="${val!""}">${val!""}</option>
			                        [/#list]
		                        </select>
		                    </div>
		                </div>
						[/#if]
						
						[#-- Diseno --]
						[#if infoVariantMacro.getDiseno()?has_content]
		                <div class="product-configurador-selects row">
		                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.design']}</div>
		                    <div class="col-4"> 
		                    	<select id="disenolentedrch">
		                    		[#assign disenos = infoVariantMacro.getDiseno()]
		                    		[#if disenos?size > 1]
		                    			<option value="" selected></option>
		                    		[/#if]
		                    		[#list disenos as val]
		                            	<option value="${val!""}">${val!""}</option>
			                        [/#list]
		                        </select>
		                    </div>
		                    <div class="col-1"></div>
		                    <div class="col-4"> 
		                    	<select id="disenolenteizq">
		                    		[#if disenos?size > 1]
		                    			<option value="" selected></option>
		                    		[/#if]
		                    		[#list disenos as val]
		                            	<option value="${val!""}">${val!""}</option>
			                        [/#list]
		                        </select>
		                    </div>
		                </div>
						[/#if]
						
						[#-- Color --]
						[#if infoVariantMacro.getColorlente()?has_content]
		                <div class="product-configurador-selects row">
		                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.color']}</div>
		                    <div class="col-4"> 
		                    	<select id="colorlentedrch">
		                    		[#assign colores = infoVariantMacro.getColorlente()]
		                    		[#if colores?size > 1]
		                    			<option value="" selected></option>
		                    		[/#if]
		                    		[#list colores as val]
		                            	<option value="${val?split("|")[0]!""}">${val?split("|")[1]!""}</option>
			                        [/#list]
		                        </select>
		                    </div>
		                    <div class="col-1"></div>
		                    <div class="col-4"> 
		                    	<select id="colorlenteizq">
		                    		[#if colores?size > 1]
		                    			<option value="" selected></option>
		                    		[/#if]
		                    		[#list colores as val]
		                            	<option value="${val?split("|")[0]!""}">${val?split("|")[1]!""}</option>
			                        [/#list]
		                        </select>
		                    </div>
		                </div>
						[/#if]
	                
	                [/#if]
                [/#if]
                
                
                

				[#-- Cantidad --]
                <div class="product-configurador-selects row">
                    <div class="col-3">${i18n['cione-module.templates.components.detalle-producto-component.cantidad']}</div>
                    <div class="col-4 product-amount product-amount-configurador">
                        <div class="product-amount-button-wrapper">
                            <button class="product-amount-button product-amount-button-minus" type="button" [#if flagPack] disabled="disabled" style="cursor: not-allowed;background-color: #d1d1d1;" [/#if]>
                                -
                            </button>
                            <input type="number" id="cantidaddrch" class="product-amount-input" min="0" max="999999" value="${unidades}">
                            <button class="product-amount-button product-amount-button-plus" type="button" [#if flagPack] disabled="disabled" style="cursor: not-allowed;background-color: #d1d1d1;" [/#if]>
                                +
                            </button>
                        </div>
                    </div>
                    <div class="col-1"></div>
                    <div class="col-4 product-amount product-amount-configurador">
                        <div class="product-amount-button-wrapper">
                            <button class="product-amount-button product-amount-button-minus" type="button" [#if flagPack] disabled="disabled" style="cursor: not-allowed;background-color: #d1d1d1;"[/#if]>
                                -
                            </button>
                            <input type="number" id="cantidadizq" class="product-amount-input" min="0" max="999999" value="${unidades}">
                            <button class="product-amount-button product-amount-button-plus" type="button" [#if flagPack] disabled="disabled" style="cursor: not-allowed;background-color: #d1d1d1;"[/#if]>
                                +
                            </button>
                        </div>
                    </div>
                </div>
				
				[#-- Referencia --]
                <div class="product-configurador-selects row">

                    <div class="col-3"></div>
                    <div class="col-4">
                        <div class="product-reference">
                            <label>${i18n['cione-module.templates.components.detalle-producto-component.referenciaod']}</label>
                            <input id="dreferencia" name="dreferencia" type="text" value="">
                        </div>

                    </div>
                    <div class="col-1">
                     </div>
                    <div class="col-4">
                        <div class="product-reference">
                            <label>${i18n['cione-module.templates.components.detalle-producto-component.referenciaoi']}</label>
                            <input id="ireferencia" name="ireferencia" type="text" value="">
                        </div>
                    </div>
                </div>
				
				[#if infoVariantMacro.getConfig()?has_content && infoVariantMacro.getConfig()]
					
				[#else]
	                <div class="product-configurador-referencia row">
	                    <div class="col-3"></div>
	                    <div class="col-4 copy-to-right"><span>${i18n['cione-module.templates.components.detalle-producto-component.copyretole']}</span> </div>
	                    <div class="col-1 product-configurador-icon-copy">
	                        <img class="copy-to-left" src="${resourcesURL + "/img/myshop/icons/flecha-derecha.svg"!}" width="20"
	                            height="20" alt="">
	
	                        <img class="copy-to-right" src="${resourcesURL + "/img/myshop/icons/flecha-izquierda.svg"!}" width="20"
	                            height="20" alt="">
	                    </div>
	                    <div class="col-4 copy-to-left"><span>${i18n['cione-module.templates.components.detalle-producto-component.copyletore']}</span> </div>
	                </div>
				[/#if]
                
                <div class="product-configurador-referencia row" style="display: none;">
                	<div class="col-6 b2b-msg-cilindro" style="color: red;">*Se deben completar todos los campos</div>
                </div>
                <div class="product-configurador-referencia row">
                	<div class="b2b-msg-errorlcsku" style="color: red; display: none;">${i18n['cione-module.templates.components.detalle-producto-component.errorlcsku']}</div>
                </div>
            </div>
	[#break]
[/#switch]
[#assign familiaProducto=infoVariant.getFamiliaProducto()!]
[/#macro]

[#macro macropdfs]
	[#assign urlPdf = infoVariant.getUrlPdf()!]
	[#if urlPdf?? && urlPdf?has_content]
			<div class="product-block-title">
				<span>Información adicional</span>
				<span>
					<a href="${urlPdf}" download="lorem_ipsum_definicion.pdf">
    					<span class="icon documento black" style="margin-left:2px"></span>
    				</a>
				</span>
			</div>
	[/#if]
[/#macro]

[#macro macrocomprar]
[#-- [#if esComprable] --]
	
	[#if familiaProducto != "contactologia" && familiaProducto != "packs" && familiaProducto != "audifonos" && familiaProducto != "audiolab"]
		<div class="product-block-title" [#if pack_generico] style="display:none" [/#if]>
		    ${i18n['cione-module.templates.components.detalle-producto-component.cantidad']}
		</div>
		<div class="b2b-msg-cantidad"></div>
			<div class="product-amount [#if familiaProducto == "contactologia"]justify-content-end[/#if]">
			[#if familiaProducto != "audiolab"]
			    <div class="product-amount-button-wrapper" [#if pack_generico] style="visibility:hidden" [/#if]>
			        <button class="product-amount-button product-amount-button-minus-detail" type="button">
			            -
			        </button>
			
			        <input type="text" id="cantidad" name="cantidad" class="product-amount-input"  min="1" max="999999" value="1" readonly>
			
			        <button class="product-amount-button product-amount-button-plus-detail" type="button">
			            +
			        </button>
			    </div>
			[/#if]
			[#if (familiaProducto == "marketing") || (familiaProducto == "accesorios")]
			
				[#-- stock del producto --]
				[#assign colorStock = "high-stock"]
			    [#if stock == 0]
			    	[#assign colorStock = "low-stock"]
			    [#elseif stock <= 2]
			    	[#assign colorStock = "medium-stock"]
			    [/#if]
			    [#assign stockctral = model.getStock(infoVariant.aliasEkon!).getStockCTRAL()!""]
			    [#assign colorStockCentral = "high-stock"]
			    [#if almacen?? && almacen?has_content && (almacen == "stockCANAR")]
			    	[#assign stockctral = model.getStock(infoVariant.aliasEkon!).getStockCTRAL()!""]
			    	[#if stockctral == 0]
				    	[#assign colorStockCentral = "low-stock"]
				    [#elseif stockctral <= 2]
				    	[#assign colorStockCentral = "medium-stock"]
				    [/#if]
			    [/#if]
			    [#if infoVariant.gestionStock]
					<div class="product-stock">
				        [#assign stocklabel = i18n['cione-module.templates.myshop.listado-productos-carrito-component.real']] 
				        [#if almacen?? && almacen?has_content]
				            [#if almacen == "stockCANAR"]
				            	[#if stockctral?? && stockctral?has_content]
					            	<div class="product-stock-canar ${colorStock}">
				            			<span class="circle-product-stock"></span>
					            		<p class="product-stock-canar">${i18n['cione-module.templates.myshop.listado-productos-home-component.canar']}${stock!""}</p>
					            	</div>
					            	<div class="product-stock-ctral ${colorStockCentral}">
				            			<span class="circle-product-stock"></span>
					            		<p class="product-stock-ctral">${i18n['cione-module.templates.myshop.listado-productos-home-component.ctral']}${stockctral!""}</p>
					            	</div>
				            	[#else]
				            		<div class="product-stock-ctral ${colorStock}">
				            			<span class="circle-product-stock"></span>
				            			<p>${stock} ${i18n['cione-module.templates.components.detalle-producto-component.in-stock']}</p>
				            		</div>
				            	[/#if]
			            	[#else]
			            		<div class="product-stock-ctral ${colorStock}">
				            		<span class="circle-product-stock"></span>
				        			<p>${stock} ${i18n['cione-module.templates.components.detalle-producto-component.in-stock']}</p>
				        		</div>
				            [/#if]
				        [/#if]
					</div>
				[/#if]
			 [/#if]
		</div>
	[/#if]

	<div id="error-pack" class="col-6" style="color: red; display:none">
	    ${i18n['cione-module.templates.components.detalle-producto-component.missing-units']}
	</div>
    <div class="product-button-wrapper">
    	[#if !existe]
    		<button class="product-button" style="cursor: not-allowed;" disabled="disabled" type="button" onclick="addtoCart(); return false">
               ${i18n['cione-module.templates.components.detalle-producto-component.add-cart']}
            </button>
        [#else]
        	[#if pack_generico]
        		<button class="product-button" type="button" onclick="addtoCartPackGenerico('${sku!}', '${infoVariant.tipoPrecioPack!}', '${infoVariant.pvoPack!}' ); return false">
					${i18n['cione-module.templates.components.detalle-producto-component.select']}
				</button>
        	[#else]
        		<button class="product-button" type="button" onclick="addtoCart(); return false" [#if familiaProducto == "contactologia"]style="cursor: not-allowed;" disabled="disabled" alt="Debe completar el formulario superior."[/#if]>
					${i18n['cione-module.templates.components.detalle-producto-component.add-cart']}
				</button>
        	[/#if]
    	[/#if]
    	
    	<div id="change-pwd-result" class="text-error"></div>
            
    </div>
    
    [#if familiaProducto != "contactologia" && familiaProducto != "audiolab" && familiaProducto != "packs"]
		<div id="plazo-entrega" class="product-block-title">
		[#if stock>0]
	        <div id="standar" class="product-block-title">
	            <span>${i18n['cione-module.templates.components.detalle-producto-component.plazo-entrega']}</span>
	            [#-- descomentar si es necesario volver a incluir el detalle del plazo de entrega
	            <span>2 ${i18n['cione-module.templates.components.detalle-producto-component.dias']}</span>  --]
	            <span>${i18n['cione-module.templates.components.siguiente-envio']}</span>
	        </div>
	        <div id="aplazado" class="product-block-title" style="display:none">
	            <span>${i18n['cione-module.templates.components.detalle-producto-component.plazo-entrega']}</span>
	            <span>${i18n['cione-module.templates.components.plazo-proveedor']}</span>
	            [#-- descomentar si es necesario volver a incluir el detalle del plazo de entrega
	            <span>${plazoProveedor + 2} ${i18n['cione-module.templates.components.detalle-producto-component.dias']}</span> --]
	        </div>
	        <input type="hidden" id="plazoEntrega" name="plazoEntrega" value="2" />
	    [#else]
	    	<div id="standar" class="product-block-title" style="display:none">
	            <span>${i18n['cione-module.templates.components.detalle-producto-component.plazo-entrega']}</span>
	            [#-- descomentar si es necesario volver a incluir el detalle del plazo de entrega
	            <span>2 ${i18n['cione-module.templates.components.detalle-producto-component.dias']}</span> --]
	            <span>${i18n['cione-module.templates.components.siguiente-envio']}</span>
	        </div>
	        <div id="aplazado" class="product-block-title">
	            <span>${i18n['cione-module.templates.components.detalle-producto-component.plazo-entrega']}</span>
	            [#-- descomentar si es necesario volver a incluir el detalle del plazo de entrega
	            <span>${plazoProveedor + 2} ${i18n['cione-module.templates.components.detalle-producto-component.dias']}</span> --]
	            <span>${i18n['cione-module.templates.components.plazo-proveedor']}</span>
	        </div>
	        <input type="hidden" id="plazoEntrega" name="plazoEntrega" value="${plazoProveedor + 2}" />
	    [/#if] 
	    </div>
	    
	    [#-- Referencia cliente --]
	    <div class="product-reference">
	        <label>${i18n['cione-module.templates.components.detalle-producto-component.ref-cliente']}</label>
	        <input type="text" name="refCliente">
	    </div>
	    
	    [#if familiaProducto != "tapones" && familiaProducto != "baterias" && familiaProducto != "audifonos"]
		    [#if !infoVariant.isRepuesto()! && content.internalLink??]
		        <div class="product-block-title">
		        	[#if content.internalLink?has_content]
						[#assign link = cmsfn.link("website", content.internalLink!)!]
						[#assign categoria = '#']
						[#if content.categoriaRepuestos?has_content]
							[#list content.categoriaRepuestos?split("/") as sValue]
								[#if sValue?is_last]
							  		[#assign categoria = sValue]
							  	[/#if]
							[/#list]
						[/#if]
						[#assign link = link + "?category=" + categoria]
						[#assign link = link + "&variants.attributes.skuPadre=" + sku]
						
						<a href="${link}">${i18n['cione-module.templates.components.detalle-producto-component.solicitud-repuestos']}</a>
					[#else]
						<a href="#">${i18n['cione-module.templates.components.detalle-producto-component.solicitud-repuestos']}</a>
					[/#if]
		            
		        </div>
		    [/#if]
	    [/#if]
    [/#if]
				        
[/#macro]

<script type="text/javascript">

$(document).ready(function(){

	[#if fittingboxproducts?? && fittingboxproducts?has_content && fittingboxproducts[sku]]
		console.log("createWidget detalle-producto.");
		window.fitmixInstance = FitMix.createWidget('fitmixContainer', params, function() {
        	console.log('VTO module is ready detalle-producto.');
		});
	[/#if]

	[#if familiaProducto?? && familiaProducto?has_content]
    	[#if familiaProducto == "monturas"]
	
			var native_width = 0;
			var native_height = 0;
			var mouse = { x: 0, y: 0 };
			var magnify;
			var cur_img;
			
			var ui = {
			    magniflier: $('.magniflier')
			};
			
			// Add the magnifying glass
			if (ui.magniflier.length) {
			    var div = document.createElement('div');
			    div.setAttribute('class', 'glass');
			    ui.glass = $(div);
			
			    $('body').append(div);
			}
			
			
			// All the magnifying will happen on "mousemove"
			
			var mouseMove = function (e) {
			    var $el = $(this);
			
			    // Container offset relative to document
			    var magnify_offset = cur_img.offset();
			
			    // Mouse position relative to container
			    // pageX/pageY - container's offsetLeft/offetTop
			    mouse.x = e.pageX - magnify_offset.left;
			    mouse.y = e.pageY - magnify_offset.top;
			
			    // The Magnifying glass should only show up when the mouse is inside
			    // It is important to note that attaching mouseout and then hiding
			    // the glass wont work cuz mouse will never be out due to the glass
			    // being inside the parent and having a higher z-index (positioned above)
			    if (
			        mouse.x < cur_img.width() &&
			        mouse.y < cur_img.height() &&
			        mouse.x > 0 &&
			        mouse.y > 0
			    ) {
			
			        magnify(e);
			    }
			    else {
			        ui.glass.fadeOut(100);
			    }
			
			    return;
			};
			
			var magnify = function (e) {
			
			    // The background position of div.glass will be
			    // changed according to the position
			    // of the mouse over the img.magniflier
			    //
			    // So we will get the ratio of the pixel
			    // under the mouse with respect
			    // to the image and use that to position the
			    // large image inside the magnifying glass
			
			    var rx = Math.round(mouse.x / cur_img.width() * native_width - ui.glass.width() / 2) * -1;
			    var ry = Math.round(mouse.y / cur_img.height() * native_height - ui.glass.height() / 2) * -1;
			    var bg_pos = rx + "px " + ry + "px";
			
			    // Calculate pos for magnifying glass
			    //
			    // Easy Logic: Deduct half of width/height
			    // from mouse pos.
			
			    // var glass_left = mouse.x - ui.glass.width() / 2;
			    // var glass_top  = mouse.y - ui.glass.height() / 2;
			    var glass_left = e.pageX - ui.glass.width() / 2;
			    var glass_top = e.pageY - ui.glass.height() / 2;
			    //console.log(glass_left, glass_top, bg_pos)
			    // Now, if you hover on the image, you should
			    // see the magnifying glass in action
			    ui.glass.css({
			        left: glass_left,
			        top: glass_top,
			        backgroundPosition: bg_pos
			    });
			
			    return;
			};
			
			$('.magniflier').on('mousemove', function () {
			    ui.glass.fadeIn(200);
			
			    cur_img = $(this);
			
			    var large_img_loaded = cur_img.data('large-img-loaded');
			    var src = cur_img.data('large') || cur_img.attr('src');
			
			    // Set large-img-loaded to true
			    // cur_img.data('large-img-loaded', true)
			
			    if (src) {
			        ui.glass.css({
			            'background-image': 'url(' + src + ')',
			            'background-repeat': 'no-repeat'
			        });
			    }
			
			    // When the user hovers on the image, the script will first calculate
			    // the native dimensions if they don't exist. Only after the native dimensions
			    // are available, the script will show the zoomed version.
			    //if(!native_width && !native_height) {
			
			    if (!cur_img.data('native_width')) {
			        // This will create a new image object with the same image as that in .small
			        // We cannot directly get the dimensions from .small because of the 
			        // width specified to 200px in the html. To get the actual dimensions we have
			        // created this image object.
			        var image_object = new Image();
			
			        image_object.onload = function () {
			            // This code is wrapped in the .load function which is important.
			            // width and height of the object would return 0 if accessed before 
			            // the image gets loaded.
			            native_width = image_object.width;
			            native_height = image_object.height;
			
			            cur_img.data('native_width', native_width);
			            cur_img.data('native_height', native_height);
			
			            //console.log(native_width, native_height);
			
			            mouseMove.apply(this, arguments);
			
			            ui.glass.on('mousemove', mouseMove);
			        };
			
			
			        image_object.src = src;
			
			        return;
			    } else {
			
			        native_width = cur_img.data('native_width');
			        native_height = cur_img.data('native_height');
			    }
			    //}
			    //console.log(native_width, native_height);
			
			    mouseMove.apply(this, arguments);
			
			    ui.glass.on('mousemove', mouseMove);
			});
			
			ui.glass.on('mouseout', function () {
			    ui.glass.off('mousemove', mouseMove);
			});
	    [/#if]
    [/#if]
		
	var aux = '${tipoPromo}';
	if(aux == 'escalado'){
		//var map = new Map() //Declarado en header-component.ftl
		[#list listPromos as promo]
			map.set("${promo.getCantidad_hasta()}", "${promo.getPvoDto()}");
		[/#list]
		
		
	}
	var unidadesCarrito = 0;

	//comprobacion del carrito para actualizar el precio de promociones y plazo de entrega
	if (typeof mapCarrito !== 'undefined') {
		unidadesCarrito = mapCarrito.get('${sku}');
		if ((typeof unidadesCarrito !== 'undefined') && unidadesCarrito != 0){
			//alert("ya hay " + unidadesCarrito + " unidades de " + '${sku}');
			var auxInitStock = ${stock} - (unidadesCarrito + 1); //añade una unidad porque por defecto vamos a añadir 1
			[#if familiaProducto != "contactologia" && familiaProducto != "audiolab"]
			if (auxInitStock>=0 ) {
				$("#standar").show();
				$("#aplazado").hide();
				document.getElementById("plazoEntrega").value = 2;
			} else {
				$("#standar").hide();
				$("#aplazado").show();
				document.getElementById("plazoEntrega").value = ${plazoProveedor} + 2;
			}
			[/#if]
			if(aux == 'escalado'){
				var encontrado = false;
				map.forEach(function(value, key) {
					if ((unidadesCarrito+1) <= key.replace(".", "")) { //key cantidad hasta, value pvoDto
						if (!encontrado) {
							//actualizo el pvoDto
							document.getElementById("pvoConDescuento").value = value;
							$('#product-price-number-dto').text(value.concat(' €'));
						}
						encontrado = true;
					}
				})
			}
		}
	}

	// // CHANGE GRADUACION
	
	$("#select-graduacion").on("change",function(e){
	   
	    //var pos = $(this).attr('id').length;
	    var texto = $("#select-graduacion option:selected" ).attr('value');
	    var pos = texto.length;
	    var skuseleccionado = texto.substring(11,pos); //graduacion-sku
	    $("#formDetalleProducto input[name=sku]").val(skuseleccionado);
	    
	    var encoding_sku = encodeURIComponent(skuseleccionado);
	    [#if pack_generico]
	    	var skuPackMaster = $('#skuPackMaster').val();
	    	var skuPackMasterEncode = encodeURIComponent(skuPackMaster);
	    	location.href = location.origin + location.pathname + '?sku=' + encoding_sku + "&skuPackMaster=" + skuPackMasterEncode + "&step=" + ${step!};
	    [#else]
	    	location.href = location.origin + location.pathname + '?sku=' + encoding_sku;
	    [/#if]
	});

	// // CHANGE CALIBRE
	
	$(".product-calibre").on("click",function(e){
	
	    $(this).parent().find(".product-calibre").removeClass("selected");
	    $(this).addClass("selected");
	    e.stopPropagation();
	    
	    var pos = $(this).attr('id').length;
	    var skuseleccionado = $(this).attr('id').substring(8,pos); //calibre-sku
	    $("#formDetalleProducto input[name=sku]").val(skuseleccionado);
	    var encoding_sku = encodeURIComponent(skuseleccionado);
	    [#if pack_generico]
	    	var skuPackMaster = $('#skuPackMaster').val();
	    	var skuPackMasterEncode = encodeURIComponent(skuPackMaster);
	    	location.href = location.origin + location.pathname + '?sku=' + encoding_sku + "&skuPackMaster=" + skuPackMasterEncode + "&step=" + ${step!};
	    [#else]
	    	location.href = location.origin + location.pathname + '?sku=' + encoding_sku;
	    [/#if]
	});
	
	// // SELECT COLOR
	
	$(".product-info-wrapper .product-color-select .product-color-circle").on("click",function(){
	
	    $(".product-color-circle").removeClass("selected");
	    $(this).addClass("selected");
	    var skuseleccionado = $(this).attr('id');
	    $("#formDetalleProducto input[name=sku]").val(skuseleccionado);
	    
	    var encoding_sku = encodeURIComponent(skuseleccionado);
	    
	    [#if pack_generico]
	    	var skuPackMaster = $('#skuPackMaster').val();
	    	var skuPackMasterEncode = encodeURIComponent(skuPackMaster);
	    	location.href = location.origin + location.pathname + '?sku=' + encoding_sku + "&skuPackMaster=" + skuPackMasterEncode + "&step=" + ${step!};
	    [#else]
	    	location.href = location.origin + location.pathname + '?sku=' + encoding_sku;
	    [/#if]
	    //alert(location.href);
	    //location.reload();
	
	});
	
	// ADD OR QUIT AMOUNT
	$('.product-amount-button-minus-detail').on("click", function () {
		
	    var inputAmount = $(this).parent().find('.product-amount-input');
	    count = parseInt(inputAmount.val());
	    count = parseInt(count) - 1;
	
	    if (count >= 0) {
	        inputAmount.val(count);
	    }
	    
	    if ((typeof mapCarrito !== 'undefined') && (typeof mapCarrito.get('${sku}') !== 'undefined')) {
			unidadesCarrito = mapCarrito.get('${sku}');
		} else {
			unidadesCarrito=0;
		}
		var unidades = ${stock} - count - unidadesCarrito;
		//comprobar cambiar el plazo de entrega
		if (unidades>=0 ) {
			$("#standar").show();
			$("#aplazado").hide();
			document.getElementById("plazoEntrega").value = 2;
		} else {
			$("#standar").hide();
			$("#aplazado").show();
			document.getElementById("plazoEntrega").value = ${plazoProveedor} + 2;
		}
		//comprobar cambiar el precio (escalado)
		if(aux == 'escalado'){
			var encontrado = false;
			map.forEach(function(value, key) {
				if ((count + unidadesCarrito) <= key.replace(".", "")) { //key cantidad hasta, value pvoDto
					if (!encontrado) {
						//actualizo el pvoDto
						document.getElementById("pvoConDescuento").value = value;
						$('#product-price-number-dto').text(value.concat(' €'));
					}
					encontrado = true;
				}
			})
		}
	
	});
	
	$('.product-amount-button-plus-detail').on("click", function () {
	
	    var inputAmount = $(this).parent().find('.product-amount-input');
	    count = parseInt(inputAmount.val());
	    count = parseInt(count) + 1;
	    inputAmount.val(count);
	    
	    if ((typeof mapCarrito !== 'undefined') && (typeof mapCarrito.get('${sku}') !== 'undefined')) {
			unidadesCarrito = mapCarrito.get('${sku}');
		} else {
			unidadesCarrito=0;
		}
		var unidades = ${stock} - count - unidadesCarrito;
		//comprobar cambiar el plazo de entrega
		if (unidades>=0 ) {
			$("#standar").css("display", "block");
			$("#aplazado").css("display", "none");
			document.getElementById("plazoEntrega").value = 2;
		} else {
			$("#standar").css("display", "none");
			$("#aplazado").css("display", "block");
			document.getElementById("plazoEntrega").value = ${plazoProveedor} + 2;
		}
		if(aux == 'escalado'){
			var encontrado = false;
			map.forEach(function(value, key) {
				if ((count + unidadesCarrito) <= key.replace(".", "")) { //key cantidad hasta, value pvoDto
					if (!encontrado) {
						//actualizo el pvoDto
						document.getElementById("pvoConDescuento").value = value;
						$('#product-price-number-dto').text(value.concat(' €'));
					}
					encontrado = true;
				}
			})
		}
	});
	
	// ADD OR QUIT AMOUNT
	$('.product-amount-button-minus').on("click", function () {
		
	    var inputAmount = $(this).parent().find('.product-amount-input');
	    count = parseInt(inputAmount.val());
	    count = parseInt(count) - 1;
	
	    if (count >= 0) {
	        inputAmount.val(count);
	    }
	    
	    [#if familiaProducto == "contactologia"]
	    if($("#cantidadizq").val() > 0 || $("#cantidaddrch").val() > 0){
    		$('.product-info-wrapper > .product-button-wrapper > .product-button').css("cursor", "pointer");
			$('.product-info-wrapper > .product-button-wrapper > .product-button').prop('disabled', false);
    	}else{
    		$('.product-info-wrapper > .product-button-wrapper > .product-button').css("cursor", "not-allowed");
			$('.product-info-wrapper > .product-button-wrapper > .product-button').prop('disabled', true);
    	}
	    [/#if]
	    
	    [#if familiaProducto != "contactologia"]
	    if ((typeof mapCarrito !== 'undefined') && (typeof mapCarrito.get('${sku}') !== 'undefined')) {
			unidadesCarrito = mapCarrito.get('${sku}');
		} else {
			unidadesCarrito=0;
		}
		var unidades = ${stock} - count - unidadesCarrito;
		//comprobar cambiar el plazo de entrega
		if (unidades>=0 ) {
			$("#standar").show();
			$("#aplazado").hide();
			document.getElementById("plazoEntrega").value = 2;
		} else {
			$("#standar").hide();
			$("#aplazado").show();
			document.getElementById("plazoEntrega").value = ${plazoProveedor} + 2;
		}
		//comprobar cambiar el precio (escalado)
		if(aux == 'escalado'){
			var encontrado = false;
			map.forEach(function(value, key) {
				if ((count + unidadesCarrito) <= key.replace(".", "")) { //key cantidad hasta, value pvoDto
					if (!encontrado) {
						//actualizo el pvoDto
						document.getElementById("pvoConDescuento").value = value;
						$('#product-price-number-dto').text(value.concat(' €'));
					}
					encontrado = true;
				}
			})
		}
		[/#if] 
	});
	
	$('.product-amount-button-plus').on("click", function () {
	
	    var inputAmount = $(this).parent().find('.product-amount-input');
	    count = parseInt(inputAmount.val());
	    count = parseInt(count) + 1;
	    inputAmount.val(count);
	    
	    [#if familiaProducto == "contactologia"]
	    	if($("#cantidadizq").val() > 0 || $("#cantidaddrch").val() > 0){
	    		$('.product-info-wrapper > .product-button-wrapper > .product-button').css("cursor", "pointer");
				$('.product-info-wrapper > .product-button-wrapper > .product-button').prop('disabled', false);
	    	}else{
	    		$('.product-info-wrapper > .product-button-wrapper > .product-button').css("cursor", "not-allowed");
				$('.product-info-wrapper > .product-button-wrapper > .product-button').prop('disabled', true);
	    	}
	    [/#if]
	    
	    [#if familiaProducto != "contactologia"]
	    if ((typeof mapCarrito !== 'undefined') && (typeof mapCarrito.get('${sku}') !== 'undefined')) {
			unidadesCarrito = mapCarrito.get('${sku}');
		} else {
			unidadesCarrito=0;
		}
		var unidades = ${stock} - count - unidadesCarrito;
		//comprobar cambiar el plazo de entrega
		if (unidades>=0 ) {
			$("#standar").css("display", "block");
			$("#aplazado").css("display", "none");
			document.getElementById("plazoEntrega").value = 2;
		} else {
			$("#standar").css("display", "none");
			$("#aplazado").css("display", "block");
			document.getElementById("plazoEntrega").value = ${plazoProveedor} + 2;
		}
		if(aux == 'escalado'){
			var encontrado = false;
			map.forEach(function(value, key) {
				if ((count + unidadesCarrito) <= key.replace(".", "")) { //key cantidad hasta, value pvoDto
					if (!encontrado) {
						//actualizo el pvoDto
						document.getElementById("pvoConDescuento").value = value;
						$('#product-price-number-dto').text(value.concat(' €'));
					}
					encontrado = true;
				}
			})
		}
		[/#if]
	});
	
	[#-- BEGIN TRIGGERS LENTES CONFIGURABLES: para las lentes configurales es necesario 
		 hacer llamadas con cada cambio que se realice sobre algun desplegables. Todos 
		 estos trigger no deben estar para lentes no configurables --]
	[#assign infoVariantOrigen = infoVariant]
	[#if infoVariant.contenidoPack?? && infoVariant.contenidoPack?has_content]
		[#if infoVariant.contenidoPack?size > 0]
        	[#assign infoVariantPack = infoVariant.contenidoPack[0]]
        	[#list infoVariant.contenidoPack as contenido]
        		[#if contenido.isMasterPack()]
        			[#assign infoVariantPack = contenido]
        		[/#if]
        	[/#list]
    		[#assign infoVariant = infoVariantPack]
    	[/#if]
		
	[/#if]
	
	[#if (infoVariant.getConfig()?has_content && infoVariant.getConfig()) ]
		window.disenolentedrchtemp;
		window.disenolenteizqtemp;
		window.esferadrchtemp;
		window.esferaizqtemp;
		window.cilindrodrchtemp;
		window.cilindroizqtemp;
		window.ejedrchtemp;
		window.ejeizqtemp;
		window.diametrodrchtemp;
		window.diametroizqtemp;
		window.curvabasedrchtemp;
		window.curvabaseizqtemp;
		window.adiciondrchtemp;
		window.adicionizqtemp;
		window.colorlentedrchtemp;
		window.colorlenteizqtemp;
	[/#if]
		
	[#if familiaProducto == "contactologia"]
		if ($("#cantidadizq").val() != null){
			$("#cantidadizq").val(0);
		}
		
		if ($("#cantidaddrch").val() != null){
			$("#cantidaddrch").val(0);
		}
    [/#if]
	
	[#if infoVariant.getConfig()?has_content && infoVariant.getConfig()]
		
		$("#disenolentedrch").change(function() {
		
			if ($("#disenolentedrch").val() != "" && $("#disenolentedrch").val() != null && 
				window.disenolentedrchtemp != $("#disenolentedrch").val()){
					
				var url = "${ctx.contextPath}/.rest/private/contactlens/spheres?codigocentral=${infoVariant.getCodigoCentral()!""}&diseno=" + $("#disenolentedrch").val();
			
				$("#loading").show();
				$("#esferadrch").css( "display", "none" );
				resetDisenoDrch();
				
				$.ajax({
		            url : url,
		            type : "GET",
		            contentType : 'application/json; charset=utf-8',
		            cache : false,
		            dataType : "json",
		            success : function(response) {
		            	$('#esferadrch').empty();
		            	window.esferadrchtemp = 'undefined';
		            	$("#esferadrch").append(new Option("", ""));
						$.map(response.spheres, function( val, i ) {
							$("#esferadrch").append(new Option(val, val));
						});
		            },
		            error : function(response) {
		                console.log("Front: Error al recuperar las esferas del ojo derecho"); 
		                $("#loading").hide();   
		            	$("#esferadrch").css( "display", "none" );
		            	$("#esferadrch").parent().parent().css( "display", "none" );
		            },
		            complete : function(response) {
		            	$("#loading").hide(); 
		            	$("#esferadrch").css( "display", "flex" );
		            	$("#esferadrch").parent().parent().css( "display", "flex" );
		            }}); 
				
			}
	        
	        window.disenolentedrchtemp = $("#disenolentedrch").val();
		});
		
		$("#disenolenteizq").change(function() {
		
			if ($("#disenolenteizq").val() != "" && $("#disenolenteizq").val() != null && 
				window.disenolenteizqtemp != $("#disenolenteizq").val()){
				
				var url = "${ctx.contextPath}/.rest/private/contactlens/spheres?codigocentral=${infoVariant.getCodigoCentral()!""}&diseno=" + $("#disenolenteizq").val();
			
				$("#loading").show(); 
				$("#esferaizq").css( "display", "none" );
				resetDisenoIzq();
					
				$.ajax({
		            url : url,
		            type : "GET",
		            contentType : 'application/json; charset=utf-8',
		            cache : false,
		            dataType : "json",
		            success : function(response) {
		            	$('#esferaizq').empty();
		            	window.esferaizqtemp = 'undefined';
		            	$("#esferaizq").append(new Option("", ""));
						$.map(response.spheres, function( val, i ) {
							$("#esferaizq").append(new Option(val, val));
						});
		            },
		            error : function(response) {
		                console.log("Front: Error al recuperar las esferas del ojo derecho"); 
		                $("#loading").hide();  
		            	$("#esferaizq").css( "display", "none" );
		            	$("#esferaizq").parent().parent().css( "display", "none" ); 
		                $("#esferadrch").prop( "disabled", false );
		            },
		            complete : function(response) {
		            	$("#loading").hide(); 
		            	$("#esferaizq").css( "display", "flex" );
		            	$("#esferaizq").parent().parent().css( "display", "flex" );
		            }}); 
				
			}
	        
	        window.disenolenteizqtemp = $("#disenolenteizq").val();
	        
		});
		
		$("#esferadrch").change(function() {
			
			if ($("#esferadrch").val() != "" && $("#esferadrch").val() != null && 
				window.esferadrchtemp != $("#esferadrch").val()){
					
				var url = "${ctx.contextPath}/.rest/private/contactlens/cylinders?codigocentral=${infoVariant.getCodigoCentral()!""}";
				
				if ($( "#disenolentedrch option:checked" ).val() != null && $( "#disenolentedrch option:checked" ).val() != ""){
					url = url + "&diseno=" + encodeURIComponent($( "#disenolentedrch option:checked" ).val());
				}
				
				if ($( "#esferadrch option:checked" ).val() != null && $( "#esferadrch option:checked" ).val() != ""){
					url = url + "&esfera=" + encodeURIComponent($( "#esferadrch option:checked" ).val());
				}
			
				$("#loading").show(); 
				$("#cilindrodrch").css( "display", "none" );
				resetEsferaDrch();
				
				$.ajax({
		            url : url,
		            type : "GET",
		            contentType : 'application/json; charset=utf-8',
		            cache : false,
		            dataType : "json",
		            success : function(response) {
		            
		            	$('#cilindrodrch').empty();
		            	window.cilindrodrchtemp = 'undefined';
		            	
		            	if (response.cylinders.length > 1){
			            	$("#cilindrodrch").append(new Option("", ""));
							$.map(response.cylinders, function( val, i ) {
								$("#cilindrodrch").append(new Option(val, val));
							});
		            	}else{
							$.map(response.cylinders, function( val, i ) {
								$("#cilindrodrch").append(new Option(val, val));
							});
		            	}
		            	
						
		            },
		            error : function(response) {
		            
		                console.log("Front: Error al recuperar las esferas del ojo derecho"); 
		                $("#loading").hide();  
		            	$("#cilindrodrch").css( "display", "none" );
		            	$("#cilindrodrch").parent().parent().css( "display", "none" );
		            	
		            },
		            complete : function(response) {
		            	
		            	$("#loading").hide();
		            	
			            if ($("#cilindrodrch").children('option').length > 0){
			            	$("#cilindrodrch").css( "display", "flex" );
			            	$("#cilindrodrch").parent().parent().css( "display", "flex" );
			            }
			            
			            if ($("#cilindrodrch").children('option').length == 1){
							window.cilindrodrchtemp = $("#cilindrodrch").val();
							getEjesDrch();
						}
			            
			            if ($("#cilindrodrch").children('option').length == 0){
			            	getEjesDrch();
			            }
		            	
		            }});
				
				}
				
			window.esferadrchtemp = $("#esferadrch").val();
		
		});
		
		$("#esferaizq").change(function() {
		
			if ($("#esferaizq").val() != "" && $("#esferaizq").val() != null && 
				window.esferaizqtemp != $("#esferaizq").val()){
					
				var url = "${ctx.contextPath}/.rest/private/contactlens/cylinders?codigocentral=${infoVariant.getCodigoCentral()!""}";
				
				if ($( "#disenolenteizq option:checked" ).val() != null && $( "#disenolenteizq option:checked" ).val() != ""){
					url = url + "&diseno=" + encodeURIComponent($( "#disenolenteizq option:checked" ).val());
				}
		
		    	if ($( "#esferaizq option:checked" ).val() != null && $( "#esferaizq option:checked" ).val() != ""){
					url = url + "&esfera=" + encodeURIComponent($( "#esferaizq option:checked" ).val());
				}
			
				$("#loading").show(); 
				$("#cilindroizq").css( "display", "none" );
				resetEsferaIzq();
				
				$.ajax({
		            url : url,
		            type : "GET",
		            contentType : 'application/json; charset=utf-8',
		            cache : false,
		            dataType : "json",
		            success : function(response) {
		            
		            	$('#cilindroizq').empty();
		            	window.cilindroizqtemp = 'undefined';
		            	
		            	if (response.cylinders.length > 1){
			            	$("#cilindroizq").append(new Option("", ""));
							$.map(response.cylinders, function( val, i ) {
								$("#cilindroizq").append(new Option(val, val));
							});
		            	}else{
							$.map(response.cylinders, function( val, i ) {
								$("#cilindroizq").append(new Option(val, val));
							});
		            	}
		            	
						
		            },
		            error : function(response) {
		            
		                console.log("Front: Error al recuperar las esferas del ojo derecho"); 
		                $("#loading").hide();  
		            	$("#cilindroizq").css( "display", "none" );
		            	$("#cilindroizq").parent().parent().css( "display", "none" );
		            	
		            },
		            complete : function(response) {
		            	
		            	$("#loading").hide();
		            	
			            if ($("#cilindroizq").children('option').length > 0){
			            	$("#cilindroizq").css( "display", "flex" );
			            	$("#cilindroizq").parent().parent().css( "display", "flex" );
			            }
			            
			            if ($("#cilindroizq").children('option').length == 1){
			            
							window.cilindroizqtemp = $("#cilindroizq").val();
							getEjesIzq();
						}
			            
			            if ($("#cilindroizq").children('option').length == 0){
			            	getEjesIzq();
			            }
		            	
		            }});
				
				}
				
			window.esferaizqtemp = $("#esferaizq").val();
		
		});
		
		$("#cilindrodrch").change(function() {
		
			if ($("#cilindrodrch").val() != "" && $("#cilindrodrch").val() != null && 
				window.cilindrodrchtemp != $("#cilindrodrch").val()){
				getEjesDrch();
			}
			
			window.cilindrodrchtemp = $("#cilindrodrch").val();
		});
			
		$("#cilindroizq").change(function() {
		
			if ($("#cilindroizq").val() != "" && $("#cilindroizq").val() != null && 
				window.cilindroizqtemp != $("#cilindroizq").val()){
				getEjesIzq();
			}
			
			window.cilindroizqtemp = $("#cilindroizq").val();
		});
		
		$("#ejedrch").change(function() {
		
			if ($("#ejedrch").val() != "" && $("#ejedrch").val() != null && 
				window.ejedrchtemp != $("#ejedrch").val()){
				getDiametrosDrch();
			}
			
			window.ejedrchtemp = $("#ejedrch").val();
		});
		
		$("#ejeizq").change(function() {
		
			if ($("#ejeizq").val() != "" && $("#ejeizq").val() != null && 
				window.ejeizqtemp != $("#ejeizq").val()){
				getDiametrosIzq();
			}
			
			window.ejeizqtemp = $("#ejeizq").val();
		});
		
		$("#diametrodrch").change(function() {
		
			if ($("#diametrodrch").val() != "" && $("#diametrodrch").val() != null && 
				window.diametrodrchtemp != $("#diametrodrch").val()){
				getRadiosDrch();
			}
			
			window.diametrodrchtemp = $("#diametrodrch").val();
		});
		
		$("#diametroizq").change(function() {
		
			if ($("#diametroizq").val() != "" && $("#diametroizq").val() != null && 
				window.diametroizqtemp != $("#diametroizq").val()){
				getRadiosIzq();
			}
			
			window.diametroizqtemp = $("#diametroizq").val();
		});
		
		$("#curvabasedrch").change(function() {
		
			if ($("#curvabasedrch").val() != "" && $("#curvabasedrch").val() != null && 
				window.curvabasedrchtemp != $("#curvabasedrch").val()){
				getAdicionesDrch();
			}
			
			window.curvabasedrchtemp = $("#curvabasedrch").val();
		});
		
		$("#curvabaseizq").change(function() {
		
			if ($("#curvabaseizq").val() != "" && $("#curvabaseizq").val() != null && 
				window.curvabaseizqtemp != $("#curvabaseizq").val()){
				getAdicionesIzq();
			}
			
			window.curvabaseizqtemp = $("#curvabaseizq").val();
		});
		
		$("#adiciondrch").change(function() {
		
			if ($("#adiciondrch").val() != "" && $("#adiciondrch").val() != null && 
				window.adiciondrchtemp != $("#adiciondrch").val()){
				getColorsDrch();
			}
			
			window.adiciondrchtemp = $("#adiciondrch").val();
		});
		
		$("#adicionizq").change(function() {
		
			if ($("#adicionizq").val() != "" && $("#adicionizq").val() != null && 
				window.adicionizqtemp != $("#adicionizq").val()){
				getColorsIzq();
			}
			
			window.adicionizqtemp = $("#adicionizq").val();
		});
	[/#if]
	
	[#-- END TRIGGERS LENTES CONFIGURABLES --]

});
    
    [#-- BEGIN LENTES DE CONTACTOS BD: funciones con las llamadas ajax necesarias para obtener 
    	 los valores de lentes de contactos de BD. Estas funciones se llamaran desde otras 
    	 llamadas ajax para encadenar resultados. Estas funciones no son necesarias para 
    	 lentes no configurables por tanto no se cargaran  --]
    
   	[#if infoVariant.getConfig()?has_content && infoVariant.getConfig()]
   	
   		function getCilindroDrch(){
	    
	    	var url = "${ctx.contextPath}/.rest/private/contactlens/cylinders?codigocentral=${infoVariant.getCodigoCentral()!""}";
	
	    	if ($( "#disenolentedrch option:checked" ).val() != null && $( "#disenolentedrch option:checked" ).val() != ""){
				url = url + "&diseno=" + encodeURIComponent($( "#disenolentedrch option:checked" ).val());
			}
	
	    	if ($( "#esferadrch option:checked" ).val() != null && $( "#esferadrch option:checked" ).val() != ""){
				url = url + "&esfera=" + encodeURIComponent($( "#esferadrch option:checked" ).val());
			}
	
	    	
		    $.ajax({
		        url : url,
		        type : "GET",
		        contentType : 'application/json; charset=utf-8',
		        cache : false,
		        dataType : "json",
		        success : function(response) {
		        
		        	$('#cilindrodrch').empty();
		        	window.cilindrodrch = 'undefined';
		        	
		        	if (response.cylinders.length > 1){
		            	$("#cilindrodrch").append(new Option("", ""));
						$.map(response.cylinders, function( val, i ) {
							$("#cilindrodrch").append(new Option(val, val));
						});
		        	}else{
						$.map(response.cylinders, function( val, i ) {
							$("#cilindrodrch").append(new Option(val, val));
						});
		        		
		        	}
		        },
		        error : function(response) {
		        
		            console.log("Front getCinlindroDrch(): Error al recuperar los cilindros del ojo derecho"); 
		            $("#loading").hide();  
		        	$("#cilindrodrch").css( "display", "none" );
		        	$("#cilindrodrch").parent().parent().css( "display", "none" );
		        	
		        },
		        complete : function(response) {
		        	
		        	$("#loading").hide();
		        	
		            if ($("#cilindrodrch").children('option').length > 0){
		            	$("#cilindrodrch").css( "display", "flex" );
		            	$("#cilindrodrch").parent().parent().css( "display", "flex" );
		            }
		            
					if ($("#cilindrodrch").children('option').length == 1){
						window.cilindrodrchtemp = $("#cilindrodrch").val();
						getEjesDrch();
					}
		            
		            if ($("#cilindrodrch").children('option').length == 0){
		            	getEjesDrch();
		            }
		        	
	        }});
	        
	        
	    }
	    
   		function getCilindroIzq(){
	    
	    	var url = "${ctx.contextPath}/.rest/private/contactlens/cylinders?codigocentral=${infoVariant.getCodigoCentral()!""}";
	
	    	if ($( "#disenolenteizq option:checked" ).val() != null && $( "#disenolenteizq option:checked" ).val() != ""){
				url = url + "&diseno=" + encodeURIComponent($( "#disenolenteizq option:checked" ).val());
			}
	
	    	if ($( "#esferaizq option:checked" ).val() != null && $( "#esferaizq option:checked" ).val() != ""){
				url = url + "&esfera=" + encodeURIComponent($( "#esferaizq option:checked" ).val());
			}
	
	    	
		    $.ajax({
		        url : url,
		        type : "GET",
		        contentType : 'application/json; charset=utf-8',
		        cache : false,
		        dataType : "json",
		        success : function(response) {
		        
		        	$('#cilindroizq').empty();
		        	window.cilindroizq = 'undefined';
		        	
		        	if (response.cylinders.length > 1){
		            	$("#cilindroizq").append(new Option("", ""));
						$.map(response.cylinders, function( val, i ) {
							$("#cilindroizq").append(new Option(val, val));
						});
		        	}else{
						$.map(response.cylinders, function( val, i ) {
							$("#cilindroizq").append(new Option(val, val));
						});
		        		
		        	}
		        },
		        error : function(response) {
		        
		            console.log("Front getCinlindroIzq(): Error al recuperar los cilindros del ojo izquierdo"); 
		            $("#loading").hide();  
		        	$("#cilindroizq").css( "display", "none" );
		        	$("#cilindroizq").parent().parent().css( "display", "none" );
		        	
		        },
		        complete : function(response) {
		        	
		        	$("#loading").hide();
		        	
		            if ($("#cilindroizq").children('option').length > 0){
		            	$("#cilindroizq").css( "display", "flex" );
		            	$("#cilindroizq").parent().parent().css( "display", "flex" );
		            }
		            
					if ($("#cilindroizq").children('option').length == 1){
						window.cilindroizqtemp = $("#cilindroizq").val();
						getEjesIzq();
					}
		            
		            if ($("#cilindroizq").children('option').length == 0){
		            	getEjesIzq();
		            }
		        	
	        }});
	        
	        
	    }
	    

   	 
	    function getEjesDrch(){
	    
	    	var url = "${ctx.contextPath}/.rest/private/contactlens/axis?codigocentral=${infoVariant.getCodigoCentral()!""}";
	
	    	if ($( "#disenolentedrch option:checked" ).val() != null && $( "#disenolentedrch option:checked" ).val() != ""){
				url = url + "&diseno=" + encodeURIComponent($( "#disenolentedrch option:checked" ).val());
			}
	
	    	if ($( "#esferadrch option:checked" ).val() != null && $( "#esferadrch option:checked" ).val() != ""){
				url = url + "&esfera=" + encodeURIComponent($( "#esferadrch option:checked" ).val());
			}
	
	    	if ($( "#cilindrodrch option:checked" ).val() != null && $( "#cilindrodrch option:checked" ).val() != ""){
				url = url + "&cilindro=" + encodeURIComponent($( "#cilindrodrch option:checked" ).val());
			}
	    	
	    	resetCilindroDrch();
	    	
		    $.ajax({
		        url : url,
		        type : "GET",
		        contentType : 'application/json; charset=utf-8',
		        cache : false,
		        dataType : "json",
		        success : function(response) {
		        
		        	$('#ejedrch').empty();
		        	window.ejedrchtemp = 'undefined';
		        	
		        	if (response.axis.length > 1){
		            	$("#ejedrch").append(new Option("", ""));
						$.map(response.axis, function( val, i ) {
							$("#ejedrch").append(new Option(val, val));
						});
		        	}else{
						$.map(response.axis, function( val, i ) {
							$("#ejedrch").append(new Option(val, val));
						});
		        		
		        	}
		        },
		        error : function(response) {
		        
		            console.log("Front getEjesDrch(): Error al recuperar los ejes del ojo derecho"); 
		            $("#loading").hide();  
		        	$("#ejedrch").css( "display", "none" );
		        	$("#ejedrch").parent().parent().css( "display", "none" );
		        	
		        },
		        complete : function(response) {
		        	
		        	$("#loading").hide();
		        	
		            if ($("#ejedrch").children('option').length > 0){
		            	$("#ejedrch").css( "display", "flex" );
		            	$("#ejedrch").parent().parent().css( "display", "flex" );
		            }
		            
					if ($("#ejedrch").children('option').length == 1){
						window.ejedrchtemp = $("#ejedrch").val();
						getDiametrosDrch();
					}
		            
		            if ($("#ejedrch").children('option').length == 0){
		            	getDiametrosDrch();
		            }
		        	
	        }});
	        
	        
	    }
	    	 
	    function getEjesIzq(){
	    
	    	var url = "${ctx.contextPath}/.rest/private/contactlens/axis?codigocentral=${infoVariant.getCodigoCentral()!""}";
	
	    	if ($( "#disenolenteizq option:checked" ).val() != null && $( "#disenolenteizq option:checked" ).val() != ""){
				url = url + "&diseno=" + encodeURIComponent($( "#disenolenteizq option:checked" ).val());
			}
	
	    	if ($( "#esferaizq option:checked" ).val() != null && $( "#esferaizq option:checked" ).val() != ""){
				url = url + "&esfera=" + encodeURIComponent($( "#esferaizq option:checked" ).val());
			}
	
	    	if ($( "#cilindroizq option:checked" ).val() != null && $( "#cilindroizq option:checked" ).val() != ""){
				url = url + "&cilindro=" + encodeURIComponent($( "#cilindroizq option:checked" ).val());
			}
	    	
	    	resetCilindroIzq();
	    	
		    $.ajax({
		        url : url,
		        type : "GET",
		        contentType : 'application/json; charset=utf-8',
		        cache : false,
		        dataType : "json",
		        success : function(response) {
		        
		        	$('#ejeizq').empty();
		        	window.ejeizqtemp = 'undefined';
		        	
		        	if (response.axis.length > 1){
		            	$("#ejeizq").append(new Option("", ""));
						$.map(response.axis, function( val, i ) {
							$("#ejeizq").append(new Option(val, val));
						});
		        	}else{
						$.map(response.axis, function( val, i ) {
							$("#ejeizq").append(new Option(val, val));
						});
		        		
		        	}
		        },
		        error : function(response) {
		        
		            console.log("Front getEjeIzq(): Error al recuperar los ejes del ojo derecho"); 
		            $("#loading").hide();  
		        	$("#ejeizq").css( "display", "none" );
		        	$("#ejeizq").parent().parent().css( "display", "none" ); 
		        	
		        },
		        complete : function(response) {
		        	
		        	$("#loading").hide();
		        	
		            if ($("#ejeizq").children('option').length > 0){
		            	$("#ejeizq").css( "display", "flex" );
		            	$("#ejeizq").parent().parent().css( "display", "flex" );
		            }
		            
					if ($("#ejeizq").children('option').length == 1){
						window.ejeizqtemp = $("#ejeizq").val();
						getDiametrosIzq();
					}
		            
		            if ($("#ejeizq").children('option').length == 0){
		            	getDiametrosIzq();
		            }
		        	
	        }});
	        
	        
	    }
	    
	    function getDiametrosDrch(){
	    
	    	var url = "${ctx.contextPath}/.rest/private/contactlens/diameters?codigocentral=${infoVariant.getCodigoCentral()!""}";
	
	    	if ($( "#disenolentedrch option:checked" ).val() != null && $( "#disenolentedrch option:checked" ).val() != ""){
				url = url + "&diseno=" + encodeURIComponent($( "#disenolentedrch option:checked" ).val());
			}
	
	    	if ($( "#esferadrch option:checked" ).val() != null && $( "#esferadrch option:checked" ).val() != ""){
				url = url + "&esfera=" + encodeURIComponent($( "#esferadrch option:checked" ).val());
			}
	
	    	if ($( "#cilindrodrch option:checked" ).val() != null && $( "#cilindrodrch option:checked" ).val() != ""){
				url = url + "&cilindro=" + encodeURIComponent($( "#cilindrodrch option:checked" ).val());
			}
	
	    	if ($( "#ejedrch option:checked" ).val() != null && $( "#ejedrch option:checked" ).val() != ""){
				url = url + "&eje=" + encodeURIComponent($( "#ejedrch option:checked" ).val());
			}
			
			resetEjeDrch();
	    	
		    $.ajax({
		        url : url,
		        type : "GET",
		        contentType : 'application/json; charset=utf-8',
		        cache : false,
		        dataType : "json",
		        success : function(response) {
		        
		        	$('#diametrodrch').empty();
		        	window.diametrodrchtemp = 'undefined';
		        	
		        	if (response.diameters.length > 1){
		            	$("#diametrodrch").append(new Option("", ""));
						$.map(response.axis, function( val, i ) {
							$("#diametrodrch").append(new Option(val, val));
						});
		        	}else{
						$.map(response.diameters, function( val, i ) {
							$("#diametrodrch").append(new Option(val, val));
						});
		        		
		        	}
		        },
		        error : function(response) {
		        
		            console.log("Front getDiametrosDrch(): Error al recuperar los diametros del ojo derecho"); 
		            $("#loading").hide();  
		        	$("#diametrodrch").css( "display", "none" );
		        	$("#diametrodrch").parent().parent().css( "display", "none" );
		        	 
		        },
		        complete : function(response) {
		        	
		        	$("#loading").hide();
		        	
		            if ($("#diametrodrch").children('option').length > 0){
		            	$("#diametrodrch").css( "display", "flex" );
		            	$("#diametrodrch").parent().parent().css( "display", "flex" );
		            }
		            
					if ($("#diametrodrch").children('option').length == 1){
						window.diametrodrchtemp = $("#diametrodrch").val();
						getRadiosDrch();
					}
		            
		            if ($("#diametrodrch").children('option').length == 0){
		            	getRadiosDrch();
		            }
		        	
	        }});
	    }
	    
	    function getDiametrosIzq(){
	    
		    var url = "${ctx.contextPath}/.rest/private/contactlens/diameters?codigocentral=${infoVariant.getCodigoCentral()!""}";
		
		    if ($( "#disenolenteizq option:checked" ).val() != null && $( "#disenolenteizq option:checked" ).val() != ""){
		        url = url + "&diseno=" + encodeURIComponent($( "#disenolenteizq option:checked" ).val());
		    }
		
		    if ($( "#esferaizq option:checked" ).val() != null && $( "#esferaizq option:checked" ).val() != ""){
		        url = url + "&esfera=" + encodeURIComponent($( "#esferaizq option:checked" ).val());
		    }
		
		    if ($( "#cilindroizq option:checked" ).val() != null && $( "#cilindroizq option:checked" ).val() != ""){
		        url = url + "&cilindro=" + encodeURIComponent($( "#cilindroizq option:checked" ).val());
		    }
		
		    if ($( "#ejeizq option:checked" ).val() != null && $( "#ejeizq option:checked" ).val() != ""){
		        url = url + "&eje=" + encodeURIComponent($( "#ejeizq option:checked" ).val());
		    }
		    
		    resetEjeIzq();
		    
		    $.ajax({
		        url : url,
		        type : "GET",
		        contentType : 'application/json; charset=utf-8',
		        cache : false,
		        dataType : "json",
		        success : function(response) {
		        
		            $('#diametroizq').empty();
		            window.diametroizqtemp = 'undefined';
		            
		            if (response.diameters.length > 1){
		                $("#diametroizq").append(new Option("", ""));
		                $.map(response.axis, function( val, i ) {
		                    $("#diametroizq").append(new Option(val, val));
		                });
		            }else{
		                $.map(response.diameters, function( val, i ) {
		                    $("#diametroizq").append(new Option(val, val));
		                });
		            }
		        },
		        error : function(response) {
		        
		            console.log("Front getDiametrosIzq(): Error al recuperar los diametros del ojo derecho"); 
		            $("#loading").hide();  
		            $("#diametroizq").css( "display", "none" );
		            $("#diametroizq").parent().parent().css( "display", "none" );
		             
		        },
		        complete : function(response) {
		            
		            $("#loading").hide();
		            
		            if ($("#diametroizq").children('option').length > 0){
		                $("#diametroizq").css( "display", "flex" );
		                $("#diametroizq").parent().parent().css( "display", "flex" );
		            }
		            
		            if ($("#diametroizq").children('option').length == 1){
		                window.diametroizqtemp = $("#diametroizq").val();
		                getRadiosIzq();
		            }
		            
		            if ($("#diametroizq").children('option').length == 0){
		                getRadiosIzq();
		            }
		            
		            [#-- 
		            diaojoizqtemp = 'undefined';
		            en el complete habría que asegurarse de que
		            tiene que estar oculto y que propiedades
		            deben estar undefined
		            $("#diaojoizq").prop( "disabled", true );
		            --]
		    }});
		}
	    
	    function getRadiosDrch(){
	    
	    	var url = "${ctx.contextPath}/.rest/private/contactlens/radios?codigocentral=${infoVariant.getCodigoCentral()!""}";
	
	    	if ($( "#disenolentedrch option:checked" ).val() != null && $( "#disenolentedrch option:checked" ).val() != ""){
				url = url + "&diseno=" + encodeURIComponent($( "#disenolentedrch option:checked" ).val());
			}
	
	    	if ($( "#esferadrch option:checked" ).val() != null && $( "#esferadrch option:checked" ).val() != ""){
				url = url + "&esfera=" + encodeURIComponent($( "#esferadrch option:checked" ).val());
			}
	
	    	if ($( "#cilindrodrch option:checked" ).val() != null && $( "#cilindrodrch option:checked" ).val() != ""){
				url = url + "&cilindro=" + encodeURIComponent($( "#cilindrodrch option:checked" ).val());
			}
	
	    	if ($( "#ejedrch option:checked" ).val() != null && $( "#ejedrch option:checked" ).val() != ""){
				url = url + "&eje=" + encodeURIComponent($( "#ejedrch option:checked" ).val());
			}
			
	    	if ($( "#diametrodrch option:checked" ).val() != null && $( "#diametrodrch option:checked" ).val() != ""){
				url = url + "&diametro=" + encodeURIComponent($( "#diametrodrch option:checked" ).val());
			}
	    	
	    	resetDiametroDrch();
	    	
		    $.ajax({
		        url : url,
		        type : "GET",
		        contentType : 'application/json; charset=utf-8',
		        cache : false,
		        dataType : "json",
		        success : function(response) {
		        
		        	$('#curvabasedrch').empty();
		        	window.curvabasedrchtemp = 'undefined';
		        	
		        	if (response.radios.length > 1){
		            	$("#curvabasedrch").append(new Option("", ""));
						$.map(response.radios, function( val, i ) {
							$("#curvabasedrch").append(new Option(val, val));
						});
		        	}else{
						$.map(response.radios, function( val, i ) {
							$("#curvabasedrch").append(new Option(val, val));
						});
		        		
		        	}
		        },
		        error : function(response) {
		        
		            console.log("Front getRadiosDrch(): Error al recuperar los radios del ojo derecho"); 
		            $("#loading").hide();  
		        	$("#curvabasedrch").css( "display", "none" );
		        	$("#curvabasedrch").parent().parent().css( "display", "none" ); 
		             
		        },
		        complete : function(response) {
		        	
		        	$("#loading").hide();
		        	
		            if ($("#curvabasedrch").children('option').length > 0){
		            	$("#curvabasedrch").css( "display", "flex" );
		            	$("#curvabasedrch").parent().parent().css( "display", "flex" );
		            }
		            
					if ($("#curvabasedrch").children('option').length == 1){
						window.curvabasedrchtemp = $("#curvabasedrch").val();
						getAdicionesDrch();
					}
					
		            if ($("#curvabasedrch").children('option').length == 0){
		            	getAdicionesDrch();
		            }
		        	
        	}});
	    }
	    
	    function getRadiosIzq(){
	    
	    	var url = "${ctx.contextPath}/.rest/private/contactlens/radios?codigocentral=${infoVariant.getCodigoCentral()!""}";
	
	    	if ($( "#disenolenteizq option:checked" ).val() != null && $( "#disenolenteizq option:checked" ).val() != ""){
				url = url + "&diseno=" + encodeURIComponent($( "#disenolenteizq option:checked" ).val());
			}
	
	    	if ($( "#esferaizq option:checked" ).val() != null && $( "#esferaizq option:checked" ).val() != ""){
				url = url + "&esfera=" + encodeURIComponent($( "#esferaizq option:checked" ).val());
			}
	
	    	if ($( "#cilindroizq option:checked" ).val() != null && $( "#cilindroizq option:checked" ).val() != ""){
				url = url + "&cilindro=" + encodeURIComponent($( "#cilindroizq option:checked" ).val());
			}
	
	    	if ($( "#ejeizq option:checked" ).val() != null && $( "#ejeizq option:checked" ).val() != ""){
				url = url + "&eje=" + encodeURIComponent($( "#ejeizq option:checked" ).val());
			}
			
	    	if ($( "#diametroizq option:checked" ).val() != null && $( "#diametroizq option:checked" ).val() != ""){
				url = url + "&diametro=" + encodeURIComponent($( "#diametroizq option:checked" ).val());
			}
	    	
	    	resetDiametroIzq();
	    	
		    $.ajax({
		        url : url,
		        type : "GET",
		        contentType : 'application/json; charset=utf-8',
		        cache : false,
		        dataType : "json",
		        success : function(response) {
		        
		        	$('#curvabaseizq').empty();
		        	window.curvabaseizqtemp = 'undefined';
		        	
		        	if (response.radios.length > 1){
		            	$("#curvabaseizq").append(new Option("", ""));
						$.map(response.radios, function( val, i ) {
							$("#curvabaseizq").append(new Option(val, val));
						});
		        	}else{
						$.map(response.radios, function( val, i ) {
							$("#curvabaseizq").append(new Option(val, val));
						});
		        		
		        	}
		        },
		        error : function(response) {
		        
		            console.log("Front getRadiosIzq(): Error al recuperar los radios del ojo derecho"); 
		            $("#loading").hide();  
		        	$("#curvabaseizq").css( "display", "none" );
		        	$("#curvabaseizq").parent().parent().css( "display", "none" );
		        	 
		        },
		        complete : function(response) {
		        	
		        	$("#loading").hide();
		        	
		            if ($("#curvabaseizq").children('option').length > 0){
		            	$("#curvabaseizq").css( "display", "flex" );
		            	$("#curvabaseizq").parent().parent().css( "display", "flex" );
		            }
		            
					if ($("#curvabaseizq").children('option').length == 1){
						window.curvabaseizqtemp = $("#curvabaseizq").val();
						getAdicionesIzq();
					}
					
		            if ($("#curvabaseizq").children('option').length == 0){
		            	getAdicionesIzq();
		            }
		        	
	        }});
	    }
	    
	    function getAdicionesDrch(){
	    	
	    	var url = "${ctx.contextPath}/.rest/private/contactlens/additions?codigocentral=${infoVariant.getCodigoCentral()!""}";
	
	    	if ($( "#disenolentedrch option:checked" ).val() != null && $( "#disenolentedrch option:checked" ).val() != ""){
				url = url + "&diseno=" + encodeURIComponent($( "#disenolentedrch option:checked" ).val());
			}
	
	    	if ($( "#esferadrch option:checked" ).val() != null && $( "#esferadrch option:checked" ).val() != ""){
				url = url + "&esfera=" + encodeURIComponent($( "#esferadrch option:checked" ).val());
			}
	
	    	if ($( "#cilindrodrch option:checked" ).val() != null && $( "#cilindrodrch option:checked" ).val() != ""){
				url = url + "&cilindro=" + encodeURIComponent($( "#cilindrodrch option:checked" ).val());
			}
	
	    	if ($( "#ejedrch option:checked" ).val() != null && $( "#ejedrch option:checked" ).val() != ""){
				url = url + "&eje=" + encodeURIComponent($( "#ejedrch option:checked" ).val());
			}
			
	    	if ($( "#diametrodrch option:checked" ).val() != null && $( "#diametrodrch option:checked" ).val() != ""){
				url = url + "&diametro=" + encodeURIComponent($( "#diametrodrch option:checked" ).val());
			}
			
	    	if ($( "#curvabasedrch option:checked" ).val() != null && $( "#curvabasedrch option:checked" ).val() != ""){
				url = url + "&radio=" + encodeURIComponent($( "#curvabasedrch option:checked" ).val());
			}
	    	
	    	resetRadioDrch();
	    	
		    $.ajax({
		        url : url,
		        type : "GET",
		        contentType : 'application/json; charset=utf-8',
		        cache : false,
		        dataType : "json",
		        success : function(response) {
		        
		        	$('#adiciondrch').empty();
		        	window.adiciondrchtemp = 'undefined';
		        	
		        	if (response.additions.length > 1){
		            	$("#adiciondrch").append(new Option("", ""));
						$.map(response.additions, function( val, i ) {
							$("#adiciondrch").append(new Option(val, val));
						});
		        	}else{
						$.map(response.additions, function( val, i ) {
							$("#adiciondrch").append(new Option(val, val));
						});
		        		
		        	}
		        },
		        error : function(response) {
		        
		            console.log("Front getAdicionesDrch(): Error al recuperar los adiciones del ojo derecho"); 
		            $("#loading").hide();  
		        	$("#adiciondrch").css( "display", "none" );
		        	$("#adiciondrch").parent().parent().css( "display", "none" ); 
		             
		        },
		        complete : function(response) {
		        	
		        	$("#loading").hide();
		        	
		            if ($("#adiciondrch").children('option').length > 0){
		            	$("#adiciondrch").css( "display", "flex" );
		            	$("#adiciondrch").parent().parent().css( "display", "flex" );
		            }
		            
					if ($("#adiciondrch").children('option').length == 1){
						window.adiciondrchtemp = $("#adiciondrch").val();
						getColorsDrch();
					}
					
					if ($("#adiciondrch").children('option').length == 0){
						getColorsDrch();
					}
		        	
	        }});
	    }
	    
	    function getAdicionesIzq(){
	    	
		    var url = "${ctx.contextPath}/.rest/private/contactlens/additions?codigocentral=${infoVariant.getCodigoCentral()!""}";
		
		    if ($( "#disenolenteizq option:checked" ).val() != null && $( "#disenolenteizq option:checked" ).val() != ""){
		        url = url + "&diseno=" + encodeURIComponent($( "#disenolenteizq option:checked" ).val());
		    }
		
		    if ($( "#esferaizq option:checked" ).val() != null && $( "#esferaizq option:checked" ).val() != ""){
		        url = url + "&esfera=" + encodeURIComponent($( "#esferaizq option:checked" ).val());
		    }
		
		    if ($( "#cilindroizq option:checked" ).val() != null && $( "#cilindroizq option:checked" ).val() != ""){
		        url = url + "&cilindro=" + encodeURIComponent($( "#cilindroizq option:checked" ).val());
		    }
		
		    if ($( "#ejeizq option:checked" ).val() != null && $( "#ejeizq option:checked" ).val() != ""){
		        url = url + "&eje=" + encodeURIComponent($( "#ejeizq option:checked" ).val());
		    }
		    
		    if ($( "#diametroizq option:checked" ).val() != null && $( "#diametroizq option:checked" ).val() != ""){
		        url = url + "&diametro=" + encodeURIComponent($( "#diametroizq option:checked" ).val());
		    }
		    
		    if ($( "#curvabaseizq option:checked" ).val() != null && $( "#curvabaseizq option:checked" ).val() != ""){
		        url = url + "&radio=" + encodeURIComponent($( "#curvabaseizq option:checked" ).val());
		    }
		    
		    resetRadioIzq();
		    
		    $.ajax({
		        url : url,
		        type : "GET",
		        contentType : 'application/json; charset=utf-8',
		        cache : false,
		        dataType : "json",
		        success : function(response) {
		        
		            $('#adicionizq').empty();
		            window.adicionizqtemp = 'undefined';
		            
		            if (response.additions.length > 1){
		                $("#adicionizq").append(new Option("", ""));
		                $.map(response.additions, function( val, i ) {
		                    $("#adicionizq").append(new Option(val, val));
		                });
		            }else{
		                $.map(response.additions, function( val, i ) {
		                    $("#adicionizq").append(new Option(val, val));
		                });
		                
		            }
		        },
		        error : function(response) {
		        
		            console.log("Front getAdicionesIzq(): Error al recuperar los adiciones del ojo derecho"); 
		            $("#loading").hide();  
		            $("#adicionizq").css( "display", "none" );
		            $("#adicionizq").parent().parent().css( "display", "none" ); 
		              
		        },
		        complete : function(response) {
		            
		            $("#loading").hide();
		            
		            if ($("#adicionizq").children('option').length > 0){
		                $("#adicionizq").css( "display", "flex" );
		                $("#adicionizq").parent().parent().css( "display", "flex" );
		            }
		            
		            if ($("#adicionizq").children('option').length == 1){
		                window.adicionizqtemp = $("#adicionizq").val();
		                getColorsIzq();
		            }
		            
		            if ($("#adicionizq").children('option').length == 0){
		            	getColorsIzq();
		            }
		            
		    }});
		}
	    
	    function getColorsDrch() {	
	    	var url = "${ctx.contextPath}/.rest/private/contactlens/colors?codigocentral=${infoVariant.getCodigoCentral()!""}";
	
	    	if ($( "#disenolentedrch option:checked" ).val() != null && $( "#disenolentedrch option:checked" ).val() != ""){
				url = url + "&diseno=" + encodeURIComponent($( "#disenolentedrch option:checked" ).val());
			}
	
	    	if ($( "#esferadrch option:checked" ).val() != null && $( "#esferadrch option:checked" ).val() != ""){
				url = url + "&esfera=" + encodeURIComponent($( "#esferadrch option:checked" ).val());
			}
	
	    	if ($( "#cilindrodrch option:checked" ).val() != null && $( "#cilindrodrch option:checked" ).val() != ""){
				url = url + "&cilindro=" + encodeURIComponent($( "#cilindrodrch option:checked" ).val());
			}
	
	    	if ($( "#ejedrch option:checked" ).val() != null && $( "#ejedrch option:checked" ).val() != ""){
				url = url + "&eje=" + encodeURIComponent($( "#ejedrch option:checked" ).val());
			}
			
	    	if ($( "#diametrodrch option:checked" ).val() != null && $( "#diametrodrch option:checked" ).val() != ""){
				url = url + "&diametro=" + encodeURIComponent($( "#diametrodrch option:checked" ).val());
			}
			
	    	if ($( "#curvabasedrch option:checked" ).val() != null && $( "#curvabasedrch option:checked" ).val() != ""){
				url = url + "&radio=" + encodeURIComponent($( "#curvabasedrch option:checked" ).val());
			}
			
	    	if ($( "#adiciondrch option:checked" ).val() != null && $( "#adiciondrch option:checked" ).val() != ""){
				url = url + "&adicion=" + encodeURIComponent($( "#adiciondrch option:checked" ).val());
			}
			
			resetAdicionDrch();
	    	
		    $.ajax({
		        url : url,
		        type : "GET",
		        contentType : 'application/json; charset=utf-8',
		        cache : false,
		        dataType : "json",
		        success : function(response) {
		        
		        	$('#colorlentedrch').empty();
		        	window.colorlentedrchtemp = 'undefined';
		        	
		        	if (response.colors.length > 1){
		            	$("#colorlentedrch").append(new Option("", ""));
						$.map(response.colors, function( val, i ) {
							//var strT = val.split("|");	 
							//$("#colorlentedrch").append(new Option(strT[1], strT[0]));
							$("#colorlentedrch").append(new Option(val, val));
						});
		        	}else{
						$.map(response.colors, function( val, i ) {
							//var strT = val.split("|");	 
							//$("#colorlentedrch").append(new Option(strT[1], strT[0]));
							$("#colorlentedrch").append(new Option(val, val));
						});
		        		
		        	}
		        },
		        error : function(response) {
		        
		            console.log("Front getColorsDrch(): Error al recuperar los colores del ojo derecho"); 
		            $("#loading").hide();  
		        	$("#colorlentedrch").css( "display", "none" );
		        	$("#colorlentedrch").parent().parent().css( "display", "none" ); 
		            [#-- habria que ocultar o deshabilitar?? --]  
		        },
		        complete : function(response) {
		        	
		        	$("#loading").hide();
		        	
		            if ($("#colorlentedrch").children('option').length > 0){
		            	$("#colorlentedrch").css( "display", "flex" );
		            	$("#colorlentedrch").parent().parent().css( "display", "flex" );
		            }
		            
					if ($("#colorlentedrch").children('option').length == 1){
						[#-- aqui es donde se debe llamar a la funcion
						para que concatene en caso de que solo hay un valor
						para cinlindros o en caso de que no haya ningun valor --]
						window.colorlentedrchtemp = $("#colorlentedrch").val();
					}
					
	        }});
	    }
	    
	    function getColorsIzq(){
	    	
	    	var url = "${ctx.contextPath}/.rest/private/contactlens/colors?codigocentral=${infoVariant.getCodigoCentral()!""}";
	
	    	if ($( "#disenolenteizq option:checked" ).val() != null && $( "#disenolenteizq option:checked" ).val() != ""){
				url = url + "&diseno=" + encodeURIComponent($( "#disenolenteizq option:checked" ).val());
			}
	
	    	if ($( "#esferaizq option:checked" ).val() != null && $( "#esferaizq option:checked" ).val() != ""){
				url = url + "&esfera=" + encodeURIComponent($( "#esferaizq option:checked" ).val());
			}
	
	    	if ($( "#cilindroizq option:checked" ).val() != null && $( "#cilindroizq option:checked" ).val() != ""){
				url = url + "&cilindro=" + encodeURIComponent($( "#cilindroizq option:checked" ).val());
			}
	
	    	if ($( "#ejeizq option:checked" ).val() != null && $( "#ejeizq option:checked" ).val() != ""){
				url = url + "&eje=" + encodeURIComponent($( "#ejeizq option:checked" ).val());
			}
			
	    	if ($( "#diametroizq option:checked" ).val() != null && $( "#diametroizq option:checked" ).val() != ""){
				url = url + "&diametro=" + encodeURIComponent($( "#diametroizq option:checked" ).val());
			}
			
	    	if ($( "#curvabaseizq option:checked" ).val() != null && $( "#curvabaseizq option:checked" ).val() != ""){
				url = url + "&radio=" + encodeURIComponent($( "#curvabaseizq option:checked" ).val());
			}
			
	    	if ($( "#adicionizq option:checked" ).val() != null && $( "#adicionizq option:checked" ).val() != ""){
				url = url + "&adicion=" + encodeURIComponent($( "#adicionizq option:checked" ).val());
			}
			
			resetAdicionIzq();
	    	
		    $.ajax({
		        url : url,
		        type : "GET",
		        contentType : 'application/json; charset=utf-8',
		        cache : false,
		        dataType : "json",
		        success : function(response) {
		        
		        	$('#colorlenteizq').empty();
		        	window.colorlenteizqtemp = 'undefined';
		        	
		        	if (response.colors.length > 1){
		            	$("#colorlenteizq").append(new Option("", ""));
						$.map(response.colors, function( val, i ) {
							//var strT = val.split("|");	 
							//$("#colorlenteizq").append(new Option(strT[1], strT[0]));
							$("#colorlenteizq").append(new Option(val, val));
						});
		        	}else{
						$.map(response.colors, function( val, i ) {
							//var strT = val.split("|");	 
							//$("#colorlenteizq").append(new Option(strT[1], strT[0]));
							$("#colorlenteizq").append(new Option(val, val));
						});
		        	}
		        },
		        error : function(response) {
		        
		            console.log("Front getColorsIzq(): Error al recuperar los colores del ojo derecho"); 
		            $("#loading").hide();  
		        	$("#colorlenteizq").css( "display", "none" );
		        	$("#colorlenteizq").parent().parent().css( "display", "none" ); 
		              
		        },
		        complete : function(response) {
		        	
		        	$("#loading").hide();
		        	
		            if ($("#colorlenteizq").children('option').length > 0){
		            	$("#colorlenteizq").css( "display", "flex" );
		            	$("#colorlenteizq").parent().parent().css( "display", "flex" );
		            }
		            
					if ($("#colorlenteizq").children('option').length == 1){
						window.colorlenteizqtemp = $("#colorlenteizq").val();
					}
					
	        }});
	    }
	    
	    function resetDisenoDrch(){
	    	
	    	$("#esferadrch").empty();
	    	window.esferadrchtemp = 'undefined';
	    	$("#cilindrodrch").empty();
	    	window.cilindrodrchtemp = 'undefined';
	    	$("#ejedrch").empty();
	    	window.ejedrchtemp = 'undefined';
	    	$("#diametrodrch").empty();
	    	window.diametrodrchtemp = 'undefined';
	    	$("#curvabasedrch").empty();
	    	window.curvabasedrchtemp = 'undefined';
	    	$("#adiciondrch").empty();
	    	window.adiciondrchtemp = 'undefined';
	    	$("#colorlentedrch").empty();
	    	window.colorlentedrchtemp = 'undefined';
	    
	    }
	    
	    function resetDisenoIzq(){
	    	
	    	$("#esferaizq").empty();
	    	window.esferaizqtemp = 'undefined';
	    	$("#cilindroizq").empty();
	    	window.cilindroizqtemp = 'undefined';
	    	$("#ejeizq").empty();
	    	window.ejeizqtemp = 'undefined';
	    	$("#diametroizq").empty();
	    	window.diametroizqtemp = 'undefined';
	    	$("#curvabaseizq").empty();
	    	window.curvabaseizqtemp = 'undefined';
	    	$("#adicionizq").empty();
	    	window.adicionizqtemp = 'undefined';
	    	$("#colorlenteizq").empty();
	    	window.colorlenteizqtemp = 'undefined';
	    
	    }
	    
	    function resetEsferaDrch(){
	    	
	    	$("#cilindrodrch").empty();
	    	window.cilindrodrchtemp = 'undefined';
	    	$("#ejedrch").empty();
	    	window.ejedrchtemp = 'undefined';
	    	$("#diametrodrch").empty();
	    	window.diametrodrchtemp = 'undefined';
	    	$("#curvabasedrch").empty();
	    	window.curvabasedrchtemp = 'undefined';
	    	$("#adiciondrch").empty();
	    	window.adiciondrchtemp = 'undefined';
	    	$("#colorlentedrch").empty();
	    	window.colorlentedrchtemp = 'undefined';
	    
	    }
	    
	    function resetEsferaIzq(){
	    	
	    	$("#cilindroizq").empty();
	    	window.cilindroizqtemp = 'undefined';
	    	$("#ejeizq").empty();
	    	window.ejeizqtemp = 'undefined';
	    	$("#diametroizq").empty();
	    	window.diametroizqtemp = 'undefined';
	    	$("#curvabaseizq").empty();
	    	window.curvabaseizqtemp = 'undefined';
	    	$("#adicionizq").empty();
	    	window.adicionizqtemp = 'undefined';
	    	$("#colorlenteizq").empty();
	    	window.colorlenteizqtemp = 'undefined';
	    
	    }
	    
	    function resetCilindroDrch(){
	    	
	    	$("#ejedrch").empty();
	    	window.ejedrchtemp = 'undefined';
	    	$("#diametrodrch").empty();
	    	window.diametrodrchtemp = 'undefined';
	    	$("#curvabasedrch").empty();
	    	window.curvabasedrchtemp = 'undefined';
	    	$("#adiciondrch").empty();
	    	window.adiciondrchtemp = 'undefined';
	    	$("#colorlentedrch").empty();
	    	window.colorlentedrchtemp = 'undefined';
	    
	    }
	    
	    function resetCilindroIzq(){
	    	
	    	$("#ejeizq").empty();
	    	window.ejeizqtemp = 'undefined';
	    	$("#diametroizq").empty();
	    	window.diametroizqtemp = 'undefined';
	    	$("#curvabaseizq").empty();
	    	window.curvabaseizqtemp = 'undefined';
	    	$("#adicionizq").empty();
	    	window.adicionizqtemp = 'undefined';
	    	$("#colorlenteizq").empty();
	    	window.colorlenteizqtemp = 'undefined';
	    
	    }
	    
	    function resetEjeDrch(){
	    	
	    	$("#diametrodrch").empty();
	    	window.diametrodrchtemp = 'undefined';
	    	$("#curvabasedrch").empty();
	    	window.curvabasedrchtemp = 'undefined';
	    	$("#adiciondrch").empty();
	    	window.adiciondrchtemp = 'undefined';
	    	$("#colorlentedrch").empty();
	    	window.colorlentedrchtemp = 'undefined';
	    
	    }
	    
	    function resetEjeIzq(){
	    	
	    	$("#diametroizq").empty();
	    	window.diametroizqtemp = 'undefined';
	    	$("#curvabaseizq").empty();
	    	window.curvabaseizqtemp = 'undefined';
	    	$("#adicionizq").empty();
	    	window.adicionizqtemp = 'undefined';
	    	$("#colorlenteizq").empty();
	    	window.colorlenteizqtemp = 'undefined';
	    
	    }
	    
	    function resetDiametroDrch(){
	    	
	    	$("#curvabasedrch").empty();
	    	window.curvabasedrchtemp = 'undefined';
	    	$("#adiciondrch").empty();
	    	window.adiciondrchtemp = 'undefined';
	    	$("#colorlentedrch").empty();
	    	window.colorlentedrchtemp = 'undefined';
	    
	    }
	    
	    function resetDiametroIzq(){
	    	
	    	$("#curvabaseizq").empty();
	    	window.curvabaseizqtemp = 'undefined';
	    	$("#adicionizq").empty();
	    	window.adicionizqtemp = 'undefined';
	    	$("#colorlenteizq").empty();
	    	window.colorlenteizqtemp = 'undefined';
	    
	    }
	    
	    function resetRadioDrch(){
	    	
	    	$("#adiciondrch").empty();
	    	window.adiciondrchtemp = 'undefined';
	    	$("#colorlentedrch").empty();
	    	window.colorlentedrchtemp = 'undefined';
	    
	    }
	    
	    function resetRadioIzq(){
	    	
	    	$("#adicionizq").empty();
	    	window.adicionizqtemp = 'undefined';
	    	$("#colorlenteizq").empty();
	    	window.colorlenteizqtemp = 'undefined';
	    
	    }
	    
	    function resetAdicionDrch(){
	    	
	    	$("#colorlentedrch").empty();
	    	window.colorlentedrchtemp = 'undefined';
	    
	    }
	    
	    function resetAdicionIzq(){
	    	
	    	$("#colorlenteizq").empty();
	    	window.colorlenteizqtemp = 'undefined';
	    
	    }
    [/#if]
    	 
    [#-- END LENTES DE CONTACTOS BD --]
    
    function initPage(){
    	var param = "key";
		var key = getParameterByName(param, window.location.href);
		if( key ) {
			$("#formDetalleProducto input[name=key]").val(key);
		}
		var param = "sku";
		var sku = getParameterByName(param, window.location.href);
		if( sku ) {
			$("#formDetalleProducto input[name=sku]").val(sku);
		}
    }
    
	function getParameterByName(name, url) {
	    if (!url) url = window.location.href;
	    name = name.replace(/[\[\]]/g, '\\$&');
	    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
	        results = regex.exec(url);
	    if (!results) return null;
	    if (!results[2]) return '';
	    
	    return decodeURIComponent(results[2].replace(/\+/g, ' '));
	} 
	
	function addtoCart() {
		
		[#switch familiaProducto]
			[#case "contactologia"]
				var config = false;
				[#if infoVariant.getConfig()?has_content && infoVariant.getConfig()]
					config = ${infoVariant.getConfig()?c};
				[/#if]
				checktoCartContactologia(${plazoProveedor}, "${infoVariant.getCodigoCentral()!""}", "${skumask}", config, false);
				
			[#break]
			[#case "audifonos"]
		    	unidadesCarrito = 0;
		    	if(typeof mapCarrito.get('${sku}') !== 'undefined') {
					unidadesCarrito = mapCarrito.get('${sku}');
				}
				var filterDetail = getFormDataAudio($("#formDetalleProducto"));
				addtoCartEndPointAudio(filterDetail);
			[#break]
			[#case "audiolab"]
		    	unidadesCarrito = 0;
		    	if(typeof mapCarrito.get('${sku}') !== 'undefined') {
					unidadesCarrito = mapCarrito.get('${sku}');
				}
				addtoCartEndPointAudioLab();
			[#break]
			[#case "packs"]
				[#if infoVariantOrigen.contenidoPack?? && infoVariantOrigen.contenidoPack?has_content]
					var flagaddtocart = true;
					var flagvalidate = true;
					var flagvalidateAudio = true;
					$("#loading").show();
					$(".product-button").attr("disabled", "disabled");
		        	[#list infoVariantOrigen.contenidoPack as contenido]
		        		[#if contenido.familiaProducto?? && contenido.familiaProducto?has_content]
		        			var pppp${contenido?index} = "${contenido.familiaProducto!}";
			        		[#if contenido.familiaProducto == "contactologia"]
			        			var plazoProveedor = ${contenido.plazoEntregaProveedor!""};
			        			var codigoCentral = "${contenido.codigoCentral!""}";
			        			var skumask = "${contenido.skumask!}";
			        			var pvoPackUD = "${contenido.pvoPackUD!}";
			        			var config = false;
			        			
								[#if contenido.getConfig()?has_content && contenido.getConfig()]
									config = ${contenido.getConfig()?c};
								[/#if]
			        			var showmodalelement = checktoCartContactologia(plazoProveedor, codigoCentral, skumask, config, true);
			        			if (flagaddtocart) {
			        				if (showmodalelement == 2) {
			        					flagaddtocart = false;
			        				}
			        			}
			        			if (flagvalidate) {
			        				if (showmodalelement == 1) {
			        					flagvalidate = false;
			        				}
			        			}
			        		[#elseif contenido.tipoProductoPack?? && contenido.tipoProductoPack?has_content 
			        			&& contenido.tipoProductoPack == "complemento-pack" || contenido.familiaProducto == "complementosaudio"]
			        			var sku = "${contenido.sku!}";
			        			var pvoPackUD = "${contenido.pvoPackUD!}";
			        			var showmodalelement = checktoCartDefault(sku, ${contenido.stock?c}, ${contenido.unidadesPack});
			        			if (flagaddtocart) {
			        				if (showmodalelement == 2) {
			        					flagaddtocart = false;
			        				}
			        			}
			        			if (flagvalidate) {
			        				if (showmodalelement == 1) {
			        					flagvalidate = false;
			        				}
			        			}
			        		[#elseif contenido.familiaProducto == "auriculares"]
			        			if ($( "#auricularesDestino" ).val() != null){
			        				var auriculares = document.getElementById("auricularesDestino");
			        				if (auriculares.length < ${contenido.unidadesPack}) {
			        					flagvalidateAudio = false;
			        				}
								}
							[#elseif contenido.familiaProducto == "acopladores"]
			        			if ($( "#acopladoresDestino" ).val() != null){
			        				var acopladores = document.getElementById("acopladoresDestino");
			        				if (acopladores.length < ${contenido.unidadesPack}) {
			        					flagvalidateAudio = false;
			        				}
								}
							[#elseif contenido.familiaProducto == "cargadores"]
			        			if ($( "#cargadoresDestino" ).val() != null){
			        				var cargadores = document.getElementById("cargadoresDestino");
			        				if (cargadores.length < ${contenido.unidadesPack}) {
			        					flagvalidateAudio = false;
			        				}
								}
							[#elseif contenido.familiaProducto == "accesorios"]
			        			if ($( "#accesoriosDestino" ).val() != null){
			        				var accesorios = document.getElementById("accesoriosDestino");
			        				if (accesorios.length < ${contenido.unidadesPack}) {
			        					flagvalidateAudio = false;
			        				}
								}
							[#elseif contenido.familiaProducto == "tubosFinos"]
			        			if ($( "#tubosFinosDestino" ).val() != null){
			        				var tubosFinos = document.getElementById("tubosFinosDestino");
			        				if (tubosFinos.length < ${contenido.unidadesPack}) {
			        					flagvalidateAudio = false;
			        				}
								}
							[#elseif contenido.familiaProducto == "sujecionesDeportivas"]
			        			if ($( "#sujecionesDeportivasDestino" ).val() != null){
			        				var sujecionesDeportivas = document.getElementById("sujecionesDeportivasDestino");
			        				if (sujecionesDeportivas.length < ${contenido.unidadesPack}) {
			        					flagvalidateAudio = false;
			        				}
								}
							[#elseif contenido.familiaProducto == "filtros"]
			        			if ($( "#filtrosDestino" ).val() != null){
			        				var filtros = document.getElementById("filtrosDestino");
			        				if (filtros.length < ${contenido.unidadesPack}) {
			        					flagvalidateAudio = false;
			        				}
								}
							[/#if]
						[/#if]
		        	[/#list]
		        	if (flagvalidate) { 
		        		if (flagaddtocart) {
		        			if (flagvalidateAudio) {
		        				$("#error-pack").hide();
			        			addToCartPack();
			        		} else {
			        			$("#error-pack").show();
			        		}
			        	} else {
			        		$("#modal-packs-detail").css("display","flex");
			        	}
		        	}
		        	$("#loading").hide();
		        	$(".product-button").removeAttr("disabled");
        		[#else]
        			$(".product-button").attr("disabled", "disabled");
				[/#if]
			[#break]
			[#default]
				var oForm = document.forms["formDetalleProducto"];
			    var vForm = validateForm(oForm);
			    if(vForm){
			    
			    	unidadesCarrito = 0;
			    	if(typeof mapCarrito.get('${sku}') !== 'undefined') {
						unidadesCarrito = mapCarrito.get('${sku}');
					}
			    	var cant = parseInt(document.getElementById("cantidad").value);
			    	if ((${stock?c}<cant+unidadesCarrito) && (${infoVariant.gestionStock?string('true', 'false')})) {
			    		
			    		$('span#unitsselected').text(cant);
			    		
			    		if(unidadesCarrito > 0){
							$('span#unitscart').text(" + " + unidadesCarrito + "${i18n['cione-module.templates.myshop.configurador-lentes-component.incart']}");
						}
						[#if infoVariant.hasSubstitute]
							generateReplacementModal('${sku}', cant);	
						[#else]
							$("#modal-purchase-detail").css("display","flex");
						[/#if]					
				    } else {
					    filterDetail = getFormData($("#formDetalleProducto"));
					    addtoCartEndPoint();
					}
			    }
			[#break]
		[/#switch]
		
	}
	
	function addtoCartPackGenerico(sku, tipoPrecioPack, pvoproductopack) {    
		$(".product-button").attr("disabled", "disabled");
		var filter = JSON.stringify({
        	"sku": '${sku}',
        	"skuPackMaster": '${infoVariant.skuPackMaster!}',
        	"familiaProducto": '${familiaProducto}',
        	"tipoProducto": '${infoVariant.tipoProducto!}',
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
				var link ="${link}" + "?skuPackMaster=${infoVariant.skuPackMaster!}";
				window.location.href = link;
            },
            error : function(response) {
                //alert("error");             
                $("#change-pwd-result").html("<p>" +  response.responseText + "</p>");
            },
            complete : function(response) {
                $("#loading").hide();
                $(".product-button").removeAttr("disabled");
            }
		});
	}
	
	function addToCartPack() {
		[#if infoVariantOrigen.contenidoPack?? && infoVariantOrigen.contenidoPack?has_content]
			$("#loading").show();
				[#list infoVariantOrigen.contenidoPack as contenido]
					var plazoProveedor = "${contenido.plazoEntregaProveedor!""}";
					var codigoCentral = "${contenido.codigoCentral!""}";
					var skumask = "${contenido.skumask!}";
					var pvoPackUD = "${contenido.pvoPackUD!}";
					var config = false;
					var productId = "${contenido.productId!}";
					var variantId = "${contenido.variantId!}";
					var familia = "${contenido.familiaProducto!}";
					var packName = "${infoVariantOrigen.getName()!}";
					[#if contenido.familiaProducto?? && contenido.familiaProducto?has_content]
			    		[#if contenido.familiaProducto == "contactologia"]
							[#if contenido.getConfig()?has_content && contenido.getConfig()]
								config = ${contenido.getConfig()?c};
							[/#if]
			    			addtoCartContactologiaPack(codigoCentral, "${infoVariantOrigen.sku!}", skumask, config, pvoPackUD, productId, variantId, familia, packName);
			    		[#elseif contenido.tipoProductoPack?? && contenido.tipoProductoPack?has_content && contenido.tipoProductoPack == "complemento-pack" 
			    			|| contenido.familiaProducto == "complementosaudio" ]
			    			addtoCartDefault("${contenido.sku!}", "${infoVariantOrigen.sku!}", pvoPackUD, ${contenido.unidadesPack}, productId, variantId, familia, packName);
						[#elseif contenido.familiaProducto == "audifonos"][#-- añade el producto padre y los hijos, se hace asi porque originalmente en la misma llamada se añade producto y complementos --]
							var filterDetail = getFormDataAudioPack($("#formDetalleProducto"), "${contenido.sku!}", "${contenido.skupackPadre!}", pvoPackUD, ${contenido.unidadesPack}, productId, variantId, familia, packName);
							addtoCartEndPointAudio(filterDetail);
						[#-- en el else excluir complemtentos de audio, ya que se añaden en el producto padre--]
						[/#if]
					[/#if]
		    	[/#list]
		    
		    var refPack = "";
		
			$.ajax({
	            url : "${ctx.contextPath}/.rest/private/carrito/v1/refPack",
	            type : "GET",
	            contentType : 'application/json; charset=utf-8',
	            cache : false,
	            async: false,
	            dataType : "json",
	            success : function(response) {
	            	refPack = response["refPack"];
	            },
	            error : function(response) {
	            	console.log("Error consultando el codigo autogenerado de refTaller.");
	        	}
	        });
		    
		    $('#refPack').val(refPack);
	    	$("#fly-card").css("display","block");
			setTimeout(function(){ 
			$("#fly-card").css("display","none");
			}, 5000);
	    	$("#modal-packs-detail").css( "display", "none" );
	    	$(".product-button").removeAttr("disabled");
	    	$("#loading").hide();
	    [/#if]
	}
	
	function checktoCartDefault(sku, stock, unidades){
		[#-- los tipos de errores pueden ser 
		0: ok
		1: formulario no valido
		2: mostrar modal --]
		var checkerror = 0;
		var oForm = document.forms["formDetalleProducto"];
    	unidadesCarrito = 0;
    	if(typeof mapCarrito.get(sku) !== 'undefined') {
			unidadesCarrito = mapCarrito.get(sku);
		}
    	
    	if (stock < unidades+unidadesCarrito) {
    		
    		$('span#unitsselected-' + sku).text(unidades);
    		
    		if(unidadesCarrito > 0){
				$('span#unitscart-' + sku).text(" + " + unidadesCarrito + "${i18n['cione-module.templates.myshop.configurador-lentes-component.incart']}");
			}
			
			checkerror = 2;				
	    } else {
	    	$('span#unitsselected-' + sku).text(unidades);
	    	var labelentrega = "${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.delivery']}";
			var plazocionie = "${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.two-days']}";
	    	
	    	$('#plazomodal-' + sku).text(labelentrega + " " + plazocionie);
	    	
	    	if (unidadesCarrito > 0) {
		    	
				$('span#unitscart-' + sku).text(" + " + unidadesCarrito + "${i18n['cione-module.templates.myshop.configurador-lentes-component.incart']}");
			}
	    }
	    return checkerror;
	}
	
	function checktoCartContactologia(plazoProveedor, codigocentral, skumask, config, ispack) {
		[#-- los tipos de errores pueden ser 
		0: ok
		1: formulario no valido
		2: mostrar modal --]
		var checkerror = 0;
		var showmodal = false;
		[#-- primero validamos --]
		var oForm = document.forms["formDetalleProducto"];
		var vForm = validateFormContactologia(oForm);
		
		if(vForm){
		
			var ojoizqstock = 0;
			var ojodrchstock = 0;
			var labelstock = "${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.stock']}";
			var labelunits = "${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}";
			var labelentrega = "${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.delivery']}";
			var plazoproveedor_text = "${i18n['cione-module.templates.components.plazo-proveedor']}";
			var plazocionie = "${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.two-days']}";
			var selectedunitsojoizq = $("#cantidadizq").val();
			var selectedunitsojodrch = $("#cantidaddrch").val();
			
			var skuojoderecho;
			var skuojoizquierdo;
			var unitscartdrch = 0;
			var unitscartizq = 0;
			
			[#-- si la validacion es correcta podemos obtener el sku --]
			if ($("#cantidadizq").val() != null && $("#cantidadizq").val() > 0){
				var skuojoizquierdo = generateSkuContactologyIzq(config, codigocentral, skumask);
				var skumaskizq = skumask;
				
				if (skuojoizquierdo != null){
					
					$.ajax({
			            url : "${ctx.contextPath}/.rest/private/stock?sku=" +  encodeURIComponent(skuojoizquierdo),
			            type : "GET",
			            contentType : 'application/json; charset=utf-8',
			            cache : false,
			            async: false,
			            dataType : "json",
			            success : function(response) {
			
							var stockreallabel = "${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.stock']}";
							var stockcanariaslabel = "${i18n['cione-module.templates.myshop.listado-productos-home-component.canar']}";
							var stockcentrallabel = "${i18n['cione-module.templates.myshop.listado-productos-home-component.ctral']}";
							var stockunidadeslabel = " ${i18n['cione-module.templates.myshop.listado-productos-home-component.units']}";
							ojoizqstock = response["stock"];
							
							if (response.almacen == 'stockCANAR'){
								$('#stockmodalcontactologiaojoizq').html(stockcanariaslabel + response.stock + stockunidadeslabel + "<br\>" + stockcentrallabel + response.stockCTRAL + stockunidadeslabel);
								$('#stockmodalcontactologiaojoizqpack').html(stockcanariaslabel + response.stock + stockunidadeslabel + "<br\>" + stockcentrallabel + response.stockCTRAL + stockunidadeslabel);
							}else{
								$('#stockmodalcontactologiaojoizq').text(stockreallabel + ojoizqstock + stockunidadeslabel);
								$('#stockmodalcontactologiaojoizqpack').text(stockreallabel + ojoizqstock + stockunidadeslabel);
							}
			            
			            },
			            error : function(response) {
			            	console.log("Error consultando el stock del ojo izquierdo");
		            	}
		            });
		            
		            var unitscarturl = "${ctx.contextPath}/.rest/private/stock/unitscart?sku=" + encodeURIComponent(skuojoizquierdo);
		            
					$.ajax({
						url : unitscarturl,
						type : "GET",
						cache : false, 
						async: false, 
						success : function(response) {
							unitscartizq = response.units;
							if(unitscartizq > 0){
								$('span#unitscartizq').text(" + " + unitscartizq + "${i18n['cione-module.templates.myshop.configurador-lentes-component.incart']}");
								$('span#unitscartizqpack').text(" + " + unitscartizq + "${i18n['cione-module.templates.myshop.configurador-lentes-component.incart']}");
							}
						},
						error : function(response) {
							console.log(response); 
						}
					}); 
				}
				
				$('#unitsselectedojoizq').text($("#cantidadizq").val());
				$('#unitsselectedojoizqpack').text($("#cantidadizq").val());
				
				if (parseInt($("#cantidadizq").val())+unitscartizq > ojoizqstock){
					showmodal = true;
					$('#plazomodalcontactologiaojoizq').text(labelentrega + " " + plazoproveedor_text);
					$('#plazomodalcontactologiaojoizqpack').text(labelentrega + " " + plazoproveedor_text);
					document.getElementById("plazoEntregaIzq").value = plazoProveedor + 2;
				}else{
					$('#plazomodalcontactologiaojoizq').text(labelentrega + " " + plazocionie);
					$('#plazomodalcontactologiaojoizqpack').text(labelentrega + " " + plazocionie);
					document.getElementById("plazoEntregaIzq").value = 2;
				}
				
				$("#modalojoizq").css("display", "block");
			}else{
				$("#modalojoizq").css("display", "none");
			}
			
			if ($("#cantidaddrch").val() != null && $("#cantidaddrch").val() > 0){
				
				var skuojoderecho = generateSkuContactologyDrch(config, codigocentral, skumask);
				var skumaskdrch = skumask;
				
				if (skuojoderecho != null){
					
					$.ajax({
			            url : "${ctx.contextPath}/.rest/private/stock?sku=" +  encodeURIComponent(skuojoderecho),
			            type : "GET",
			            contentType : 'application/json; charset=utf-8',
			            cache : false,
			            async: false,
			            dataType : "json",
			            success : function(response) {
			
							var stockreallabel = "${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.stock']}";
							var stockcanariaslabel = "${i18n['cione-module.templates.myshop.listado-productos-home-component.canar']}";
							var stockcentrallabel = "${i18n['cione-module.templates.myshop.listado-productos-home-component.ctral']}";
							var stockunidadeslabel = " ${i18n['cione-module.templates.myshop.listado-productos-home-component.units']}";
							ojodrchstock = response["stock"];
							$("#stockdrch").val(ojodrchstock);
							
							if (response.almacen == 'stockCANAR'){
								$('#stockmodalcontactologiaojodrch').html(stockcanariaslabel + response.stock + stockunidadeslabel + "<br\>" + stockcentrallabel + response.stockCTRAL + stockunidadeslabel);
								$('#stockmodalcontactologiaojodrchpack').html(stockcanariaslabel + response.stock + stockunidadeslabel + "<br\>" + stockcentrallabel + response.stockCTRAL + stockunidadeslabel);
							}else{
								$('#stockmodalcontactologiaojodrch').text(stockreallabel + ojoizqstock + stockunidadeslabel);
								$('#stockmodalcontactologiaojodrchpack').text(stockreallabel + ojoizqstock + stockunidadeslabel);
							}
			            	
			            },
			            error : function(response) {
			            	console.log("Error consultando el stock del ojo derecho");
		            	}
		            });
		            
		            var unitscarturl = "${ctx.contextPath}/.rest/private/stock/unitscart?sku=" + encodeURIComponent(skuojoderecho);
		            
					$.ajax({
						url : unitscarturl,
						type : "GET",
						cache : false, 
						async: false, 
						success : function(response) {
							unitscartdrch = response.units;
							if(unitscartdrch > 0){
								$('span#unitscartdrch').text(" + " + unitscartdrch + "${i18n['cione-module.templates.myshop.configurador-lentes-component.incart']}");
								$('span#unitscartdrchpack').text(" + " + unitscartdrch + "${i18n['cione-module.templates.myshop.configurador-lentes-component.incart']}");
							}
						},
						error : function(response) {
							console.log(response); 
						}
					}); 
				}
				
				$('#unitsselectedojodrch').text($("#cantidaddrch").val());
				$('#unitsselectedojodrchpack').text($("#cantidaddrch").val());
				
				if (parseInt($("#cantidaddrch").val())+unitscartdrch > ojodrchstock){
					showmodal = true;
					$('#plazomodalcontactologiaojodrch').text(labelentrega + " " + plazoproveedor_text);
					$('#plazomodalcontactologiaojodrchpack').text(labelentrega + " " + plazoproveedor_text);
					document.getElementById("plazoEntregaDrch").value = plazoProveedor + 2;
				}else{
					$('#plazomodalcontactologiaojodrch').text(labelentrega + " " + plazocionie);
					$('#plazomodalcontactologiaojodrchpack').text(labelentrega + " " + plazocionie);
					document.getElementById("plazoEntregaDrch").value = 2;
				}
				
				$("#modalojodrc").css("display", "block");
			}else{
				$("#modalojodrc").css("display", "none");
			}
			
			
			if(skuojoizquierdo == skuojoderecho){
				if(ojoizqstock < (parseInt($("#cantidaddrch").val()) + parseInt($("#cantidadizq").val()) + unitscartizq)){
					showmodal = true;
					$('#plazomodalcontactologiaojoizq').text(labelentrega + " " + plazoproveedor_text);
					$('#plazomodalcontactologiaojodrch').text(labelentrega + " " + plazoproveedor_text);
					$('#plazomodalcontactologiaojoizqpack').text(labelentrega + " " + plazoproveedor_text);
					$('#plazomodalcontactologiaojodrchpack').text(labelentrega + " " + plazoproveedor_text);
					$("#modalojoizq").css("display", "block");
					$("#modalojodrc").css("display", "block");
					
				}
			}
			
			if (!ispack) {
				if (showmodal){
					$("#modal-contactologia-detail").css("display","flex");
				}else{
					addtoCartContactologia(codigocentral, skumask, config);
				}
			}
			if (showmodal) {
				checkerror = 2;
			}
		} else {
			checkerror = 1;
		}
		
		return checkerror;
	}
	
	function generateSkuContactologyIzq(config, codigocentral, skumask){
		
		var result = "";
		
		var disenolenteizq = $( "#disenolenteizq option:checked" ).val();
		var esferaizq = $( "#esferaizq option:checked" ).val();
		var cilindroizq = $( "#cilindroizq option:checked" ).val();
		var ejeizq = $( "#ejeizq option:checked" ).val();
		var diametroizq = $( "#diametroizq option:checked" ).val();
		var curvabaseizq = $( "#curvabaseizq option:checked" ).val();
		var adicionizq = $( "#adicionizq option:checked" ).val();
		var colorlenteizq = $( "#colorlenteizq option:checked" ).val();
		
		if (config) {
	    	
	    	var url = "${ctx.contextPath}/.rest/private/contactlens/sku?codigocentral=" + codigocentral;
	
	    	if ($( "#disenolenteizq option:checked" ).val() != null && $( "#disenolenteizq option:checked" ).val() != ""){
				url = url + "&diseno=" + encodeURIComponent($( "#disenolenteizq option:checked" ).val());
			}
	
	    	if ($( "#esferaizq option:checked" ).val() != null && $( "#esferaizq option:checked" ).val() != ""){
				url = url + "&esfera=" + encodeURIComponent($( "#esferaizq option:checked" ).val());
			}
	
	    	if ($( "#cilindroizq option:checked" ).val() != null && $( "#cilindroizq option:checked" ).val() != ""){
				url = url + "&cilindro=" + encodeURIComponent($( "#cilindroizq option:checked" ).val());
			}
	
	    	if ($( "#ejeizq option:checked" ).val() != null && $( "#ejeizq option:checked" ).val() != ""){
				url = url + "&eje=" + encodeURIComponent($( "#ejeizq option:checked" ).val());
			}
			
	    	if ($( "#diametroizq option:checked" ).val() != null && $( "#diametroizq option:checked" ).val() != ""){
				url = url + "&diametro=" + encodeURIComponent($( "#diametroizq option:checked" ).val());
			}
			
	    	if ($( "#curvabaseizq option:checked" ).val() != null && $( "#curvabaseizq option:checked" ).val() != ""){
				url = url + "&radio=" + encodeURIComponent($( "#curvabaseizq option:checked" ).val());
			}
			
	    	if ($( "#adicionizq option:checked" ).val() != null && $( "#adicionizq option:checked" ).val() != ""){
				url = url + "&adicion=" + encodeURIComponent($( "#adicionizq option:checked" ).val());
			}
			
	    	if ($( "#colorlenteizq option:checked" ).val() != null && $( "#colorlenteizq option:checked" ).val() != ""){
				//var colorAux = $( "#colorlenteizq option:checked" ).val()+"|"+$( "#colorlenteizq option:selected" ).text()
				//url = url + "&color=" + encodeURIComponent(colorAux);
				url = url + "&color=" + encodeURIComponent($( "#colorlenteizq option:checked" ).val());
			}
	    	
		    $.ajax({
		        url : url,
		        type : "GET",
		        async: false,
		        contentType : 'application/json; charset=utf-8',
		        cache : false,
		        dataType : "json",
		        success : function(response) {
		        	result = response.sku;
		        },
		        error : function(response) {
		            console.log("Front generateSkuContactologyIzq(): Error al recuperar el sku del ojo izquierdo");
		        	return result;
	        }});
	        
	        return result;
		
		} else {
		
			var arrskumask = skumask.split("+").map(Function.prototype.call, String.prototype.trim);
			
			arrskumask.forEach(function(elem,index) {
			  
			  if (index == 0){
			  
			  	result = result + elem; 
			  	
			  }else{
			  
			  	if(elem.toLowerCase().startsWith('esf')){
			  		result = result + esferaizq;
			  	}
			  	
			  	if(elem.toLowerCase().startsWith('eje')){
			  		result = result + ejeizq;
			  	}
			  	
			  	if(elem.toLowerCase().startsWith('cil')){
			  		result = result + cilindroizq;
			  	}
			  	
			  	if(elem.toLowerCase().startsWith('rad')){
			  		result = result + curvabaseizq;
			  	}
			  	
			  	if(elem.toLowerCase().startsWith('adi')){
			  		result = result + adicionizq;
			  	}
			  	
			  	if(elem.toLowerCase().startsWith('col')){
			  		result = result + colorlenteizq;
			  	}
			  	
			  	if(elem.toLowerCase().startsWith('dia')){
			  		result = result + diametroizq;
			  	}
			  	
			  	if(elem.toLowerCase().startsWith('dis')){
			  		result = result + disenolenteizq;
			  	}
			  }
			  
			});
			
			return result;
		
		}
		
	}
	
	function generateSkuContactologyDrch(config, codigocentral, skumask){
	
		var result = "";
		
		var disenolentedrch = $( "#disenolentedrch option:checked" ).val();
		var esferadrch = $( "#esferadrch option:checked" ).val();
		var cilindrodrch = $( "#cilindrodrch option:checked" ).val();
		var ejedrch = $( "#ejedrch option:checked" ).val();
		var diametrodrch = $( "#diametrodrch option:checked" ).val();
		var curvabasedrch = $( "#curvabasedrch option:checked" ).val();
		var adiciondrch = $( "#adiciondrch option:checked" ).val();
		var colorlentedrch = $( "#colorlentedrch option:checked" ).val();
		
		if (config) {
			
			var url = "${ctx.contextPath}/.rest/private/contactlens/sku?codigocentral=" + codigocentral;
	
	    	if ($( "#disenolentedrch option:checked" ).val() != null && $( "#disenolentedrch option:checked" ).val() != ""){
				url = url + "&diseno=" + encodeURIComponent($( "#disenolentedrch option:checked" ).val());
			}
	
	    	if ($( "#esferadrch option:checked" ).val() != null && $( "#esferadrch option:checked" ).val() != ""){
				url = url + "&esfera=" + encodeURIComponent($( "#esferadrch option:checked" ).val());
			}
	
	    	if ($( "#cilindrodrch option:checked" ).val() != null && $( "#cilindrodrch option:checked" ).val() != ""){
				url = url + "&cilindro=" + encodeURIComponent($( "#cilindrodrch option:checked" ).val());
			}
	
	    	if ($( "#ejedrch option:checked" ).val() != null && $( "#ejedrch option:checked" ).val() != ""){
				url = url + "&eje=" + encodeURIComponent($( "#ejedrch option:checked" ).val());
			}
			
	    	if ($( "#diametrodrch option:checked" ).val() != null && $( "#diametrodrch option:checked" ).val() != ""){
				url = url + "&diametro=" + encodeURIComponent($( "#diametrodrch option:checked" ).val());
			}
			
	    	if ($( "#curvabasedrch option:checked" ).val() != null && $( "#curvabasedrch option:checked" ).val() != ""){
				url = url + "&radio=" + encodeURIComponent($( "#curvabasedrch option:checked" ).val());
			}
			
	    	if ($( "#adiciondrch option:checked" ).val() != null && $( "#adiciondrch option:checked" ).val() != ""){
				url = url + "&adicion=" + encodeURIComponent($( "#adiciondrch option:checked" ).val());
			}
			
	    	if ($( "#colorlentedrch option:checked" ).val() != null && $( "#colorlentedrch option:checked" ).val() != ""){
	    		//var colorAux = $( "#colorlentedrch option:checked" ).val()+"|"+$( "#colorlentedrch option:selected" ).text()
				//url = url + "&color=" + encodeURIComponent(colorAux);
				url = url + "&color=" + encodeURIComponent($( "#colorlentedrch option:checked" ).val());
			}
	    	
		    $.ajax({
		        url : url,
		        type : "GET",
		        async: false,
		        contentType : 'application/json; charset=utf-8',
		        cache : false,
		        dataType : "json",
		        success : function(response) {
		        	result = response.sku;
		        },
		        error : function(response) {
		            console.log("Front generateSkuContactologyDrch(): Error al recuperar el sku del ojo derecho");
		        	return result;
	        }});
	        
	        return result;
		
		} else {
		
			var arrskumask = skumask.split("+").map(Function.prototype.call, String.prototype.trim);
			
			arrskumask.forEach(function(elem,index) {
			  
			  if (index == 0){
			  
			  	result = result + elem; 
			  	
			  }else{
			  
			  	if(elem.toLowerCase().startsWith('esf')){
			  		result = result + esferadrch;
			  	}
			  	
			  	if(elem.toLowerCase().startsWith('eje')){
			  		result = result + ejedrch;
			  	}
			  	
			  	if(elem.toLowerCase().startsWith('cil')){
			  		result = result + cilindrodrch;
			  	}
			  	
			  	if(elem.toLowerCase().startsWith('rad')){
			  		result = result + curvabasedrch;
			  	}
			  	
			  	if(elem.toLowerCase().startsWith('adi')){
			  		result = result + adiciondrch;
			  	}
			  	
			  	if(elem.toLowerCase().startsWith('col')){
			  		result = result + colorlentedrch;
			  	}
			  	
			  	if(elem.toLowerCase().startsWith('dia')){
			  		result = result + diametrodrch;
			  	}
			  	
			  	if(elem.toLowerCase().startsWith('dis')){
			  		result = result + disenolentedrch;
			  	}
			  }
			  
			});
			
			return result;
		
		}
		
	}
	
    function addtoCartContactologiaPack(codigocentral, skuPackPadre, skumask, config, pvoPackUD, productId, variantId, familia, packName){
    	var checkAdd = false;
    	var detailizq = getFormDataContactologia($("#formDetalleProducto"),"izq", codigocentral, skumask, config);
    	detailizq["pvoPackUD"] = pvoPackUD;
    	detailizq["productId"] = productId;
    	detailizq["variantId"] = variantId;
    	detailizq["familia"] = familia;
    	detailizq["refPackPromos"] = $('#refPack').val();
    	detailizq["packName"] = packName;
    	detailizq["skuPackPadre"] = skuPackPadre;
    	
    	var detaildrch = getFormDataContactologia($("#formDetalleProducto"),"drch", codigocentral, skumask, config);
    	detaildrch["pvoPackUD"] = pvoPackUD;
    	detaildrch["productId"] = productId;
    	detaildrch["variantId"] = variantId;
    	detaildrch["familia"] = familia;
    	detaildrch["refPackPromos"] = $('#refPack').val();
    	detaildrch["packName"] = packName;
    	detaildrch["skuPackPadre"] = skuPackPadre;
    	
        if(detailizq != null && detaildrch != null){
        	
	    	$("#loading").show();
	    	
	    	$.ajax({
	            url : "${ctx.contextPath}/.rest/private/carrito/v1/carts-addItem",
	            type : "POST",
	            data : JSON.stringify(detaildrch),
	            contentType : 'application/json; charset=utf-8',
	            cache : false,
	            async: false,
	            dataType : "json",
	            success : function(response) {
	            	checkAdd = true;
	            	refrescarPopupCarrito(response);
	            },
	            error : function(response) {
	                alert("error"); 
	                checkAdd = false;
	                $("#loading").hide();           
	            },
	            complete : function(response) {
	                
			    	$.ajax({
			            url : "${ctx.contextPath}/.rest/private/carrito/v1/carts-addItem",
			            type : "POST",
			            data : JSON.stringify(detailizq),
			            contentType : 'application/json; charset=utf-8',
			            cache : false,
			            async: false,
			            dataType : "json",
			            success : function(response) {
			            	checkAdd = true;
			            	refrescarPopupCarrito(response);
			            },
			            error : function(response) {
			            	checkAdd = false;
			                alert("error"); 
			                $("#loading").hide();           
			            },
			            complete : function(response) {
			                
			                resetAllFields('both');
			            }
			        });
	            }
	        });
    	}
    	
    	return checkAdd;
    }

    function addtoCartContactologia(codigocentral, skumask, config) {
    	
    	var detailojoizq = null;
    	if ($("#cantidadizq").val() != null && $("#cantidadizq").val() > 0){
    		detailojoizq = getFormDataContactologia($("#formDetalleProducto"),"izq", codigocentral, skumask, config);
    	}
    	
    	var detailojodrch = null;
    	if ($("#cantidaddrch").val() != null && $("#cantidaddrch").val() > 0){
    		detailojodrch = getFormDataContactologia($("#formDetalleProducto"),"drch", codigocentral, skumask, config);
    	}
    	
        if(detailojoizq != null && detailojodrch != null){
        	if(detailojoizq["lcsku"] == "") {
        		$("#modal-contactologia-detail").css("display","none");
        		$(".b2b-msg-errorlcsku").css("display", "block");
        		return;
        	}
        	if(detailojodrch["lcsku"] == "") {
        		$("#modal-contactologia-detail").css("display","none");
        		$(".b2b-msg-errorlcsku").css("display", "block");
        		return;
        	}
        	addtoCartEndPointContactologiaBoth(detailojoizq,detailojodrch);
    	}else{
    		
	        if(detailojoizq != null){
	        	if(detailojoizq["lcsku"] == "") {
	    			$("#modal-contactologia-detail").css("display","none");
	        		$(".b2b-msg-errorlcsku").css("display", "block");
	        		return;
	        	}
	        	addtoCartEndPointContactologia(detailojoizq, "izq");
	    	}
	        if(detailojodrch != null){
	        	if(detailojodrch["lcsku"] == "") {
	        		$(".b2b-msg-errorlcsku").css("display", "block");
	        		return;
	        	}
	        	addtoCartEndPointContactologia(detailojodrch, "drch");
	        }
    	}
    	
    }
    
    function addtoCartEndPointContactologiaBoth(detailizq, detaildrch){
    	
    	$("#modal-contactologia-detail").css("display","none");
    	$("#loading").show();
    	$(".product-button").attr("disabled", "disabled");
    	
    	$.ajax({
            url : "${ctx.contextPath}/.rest/private/carrito/v1/carts-addItem",
            type : "POST",
            data : JSON.stringify(detaildrch),
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            success : function(response) {
            
            	$("#fly-card").css("display","block");
				
				setTimeout(function(){ 
				$("#fly-card").css("display","none");
				}, 5000);
				
                var KK = response;
                //alert("producto añadido");
                
                console.log(KK);
                
                //actualiza popup carrito
                refrescarPopupCarrito(response);
                
            },
            error : function(response) {
                alert("error"); 
                $("#loading").hide();           
            },
            complete : function(response) {
                
		    	$.ajax({
		            url : "${ctx.contextPath}/.rest/private/carrito/v1/carts-addItem",
		            type : "POST",
		            data : JSON.stringify(detailizq),
		            contentType : 'application/json; charset=utf-8',
		            cache : false,
		            dataType : "json",
		            success : function(response) {
		            
		            	$("#fly-card").css("display","block");
						
						setTimeout(function(){ 
						$("#fly-card").css("display","none");
						}, 5000);
						
		                var KK = response;
		                //alert("producto añadido");
		                
		                console.log(KK);
		                
		                //actualiza popup carrito
		                refrescarPopupCarrito(response);
		                
		            },
		            error : function(response) {
		                alert("error"); 
		                $("#loading").hide();           
		            },
		            complete : function(response) {
		                $("#loading").hide();
		                $(".product-button").removeAttr("disabled"); 
		                $('.product-info-wrapper > .product-button-wrapper > .product-button').css("cursor", "not-allowed");
		                $('.product-info-wrapper > .product-button-wrapper > .product-button').prop('disabled', true);
		                resetAllFields('both');
		            }
		        });
            }
        });
    
    
    }
    
	function resetAllFields(ojo){
		var isPack = false;
		[#if familiaProducto?? && familiaProducto?has_content]
    		[#if familiaProducto == "packs"]
    			isPack = true;
    		[/#if]
    	[/#if]
		
		var refTaller = "";
		
		$.ajax({
            url : "${ctx.contextPath}/.rest/private/carrito/v1/refPack",
            type : "GET",
            contentType : 'application/json; charset=utf-8',
            cache : false,
            async: false,
            dataType : "json",
            success : function(response) {
            	refTaller = response["refPack"];
            },
            error : function(response) {
            	console.log("Error consultando el codigo autogenerado de refTaller.");
        	}
        });
		
		
		$('#refTaller').val(refTaller);
		if (ojo == "both" || ojo == "drch") {
		
			if (typeof $('#esferadrch') !== 'undefined') {
				if ($('#esferadrch option').length > 1){
					$('#esferadrch').val('');
		        }
			}
			if (typeof $('#ejedrch') !== 'undefined') {
				if ($('#ejedrch option').length > 1){
					$('#ejedrch').val('');
		        }
			}
			if (typeof $('#diametrodrch') !== 'undefined') {
				if ($('#diametrodrch option').length > 1){
		        	$('#diametrodrch').val('');
		        }
			}
			if (typeof $('#cilindrodrch') !== 'undefined') {
				if ($('#cilindrodrch option').length > 1){
					$('#cilindrodrch').val('');
		        }
			}
			if (typeof $('#curvabasedrch') !== 'undefined') {
				if ($('#curvabasedrch option').length > 1){
		        	$('#curvabasedrch').val('');
		        }
			}
			if (typeof $('#adiciondrch') !== 'undefined') {
				if ($('#adiciondrch option').length > 1){
		        	$('#adiciondrch').val('');
		        }
			}
			if (typeof $('#colorlentedrch') !== 'undefined') {
				if ($('#colorlentedrch option').length > 1){
		        	$('#colorlentedrch').val('');
		        }
			}
			$("#dreferencia").val('');
			if (!isPack) {
				$("#cantidaddrch").val(0);
			}
		}
		
		if (ojo == "both" || ojo == "izq") {
			if (typeof $('#esferaizq') !== 'undefined') {
				if ($('#esferaizq option').length > 1){
					$('#esferaizq').val('');
		        }
			}
			if (typeof $('#ejeizq') !== 'undefined') {
				if ($('#ejeizq option').length > 1){
					$('#ejeizq').val('');
		        }
			}
			if (typeof $('#diametroizq') !== 'undefined') {
				if ($('#diametroizq option').length > 1){
		        	$('#diametroizq').val('');
		        }
			}
			if (typeof $('#cilindroizq') !== 'undefined') {
				if ($('#cilindroizq option').length > 1){
					$('#cilindroizq').val('');
		        }
			}
			if (typeof $('#curvabaseizq') !== 'undefined') {
				if ($('#curvabaseizq option').length > 1){
		        	$('#curvabaseizq').val('');
		        }
			}
			if (typeof $('#adicionizq') !== 'undefined') {
				if ($('#adicionizq option').length > 1){
		        	$('#adicionizq').val('');
		        }
			}
			if (typeof $('#colorlenteizq') !== 'undefined') {
				if ($('#colorlenteizq option').length > 1){
		        	$('#colorlenteizq').val('');
		        }
			}		
			$("#ireferencia").val('');
			if (!isPack) {
		        $("#cantidadizq").val(0);
			}
			$("#modalojoizq").css("display", "none");
			$("#modalojodrc").css("display", "none");
			
		}
		
		[#if infoVariant.getConfig()?has_content  && infoVariant.getConfig()]
			
			$('#disenolentedrch').val('');
			$('#disenolenteizq').val('');
			$('#esferadrch').val('');
			$('#esferaizq').val('');
			
			[#if infoVariant.getDiseno()?has_content]
				$('#esferadrch').css( "display", "none" );
				$("#esferadrch").parent().parent().css( "display", "none" );
				
				$('#esferaizq').css( "display", "none" );
				$("#esferaizq").parent().parent().css( "display", "none" );
			[/#if]
				
			$('#cilindrodrch').css( "display", "none" );
			$("#cilindrodrch").parent().parent().css( "display", "none" );
			$('#cilindrodrch').val('');
			
			$('#ejedrch').css( "display", "none" );
			$("#ejedrch").parent().parent().css( "display", "none" );
			$('#ejedrch').val('');
			
			$('#diametrodrch').css( "display", "none" );
			$("#diametrodrch").parent().parent().css( "display", "none" );
			$('#diametrodrch').val('');
			
			$('#curvabasedrch').css( "display", "none" );
			$("#curvabasedrch").parent().parent().css( "display", "none" );
			$('#curvabasedrch').val('');
			
			$('#adiciondrch').css( "display", "none" );
			$("#adiciondrch").parent().parent().css( "display", "none" );
			$('#adiciondrch').val('');
			
			$('#colorlentedrch').css( "display", "none" );
			$("#colorlentedrch").parent().parent().css( "display", "none" );
			$('#colorlentedrch').val('');
			
			$('#cilindroizq').css( "display", "none" );
			$("#cilindroizq").parent().parent().css( "display", "none" );
			$('#cilindroizq').val('');
			
			$('#ejeizq').css( "display", "none" );
			$("#ejeizq").parent().parent().css( "display", "none" );
			$('#ejeizq').val('');
			
			$('#diametroizq').css( "display", "none" );
			$("#diametroizq").parent().parent().css( "display", "none" );
			$('#diametroizq').val('');
			
			$('#curvabaseizq').css( "display", "none" );
			$("#curvabaseizq").parent().parent().css( "display", "none" );
			$('#curvabaseizq').val('');
			
			$('#adicionizq').css( "display", "none" );
			$("#adicionizq").parent().parent().css( "display", "none" );
			$('#adicionizq').val('');
			
			$('#colorlenteizq').css( "display", "none" );
			$("#colorlenteizq").parent().parent().css( "display", "none" );
			$('#colorlenteizq').val('');
			
			window.disenolentedrchtemp = 'undefined';
			window.disenolenteizqtemp = 'undefined';
			window.esferadrchtemp = 'undefined';
			window.esferaizqtemp = 'undefined';
			window.cilindrodrchtemp = 'undefined';
			window.cilindroizqtemp = 'undefined';
			window.ejedrchtemp = 'undefined';
			window.ejeizqtemp = 'undefined';
			window.diametrodrchtemp = 'undefined';
			window.diametroizqtemp = 'undefined';
			window.curvabasedrchtemp = 'undefined';
			window.curvabaseizqtemp = 'undefined';
			window.adiciondrchtemp = 'undefined';
			window.adicionizqtemp = 'undefined';
			window.colorlentedrchtemp = 'undefined';
			window.colorlenteizqtemp = 'undefined';
		
		[/#if]
		
	}
    
    function addtoCartEndPointContactologia(detail, ojo){
    	
    	$("#modal-contactologia-detail").css("display","none");
    	$("#loading").show();
    	$(".product-button").attr("disabled", "disabled");
    	
    	$.ajax({
            url : "${ctx.contextPath}/.rest/private/carrito/v1/carts-addItem",
            type : "POST",
            data : JSON.stringify(detail),
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            success : function(response) {
            
            	$("#fly-card").css("display","block");
				
				setTimeout(function(){ 
				$("#fly-card").css("display","none");
				}, 5000);
				
                var KK = response;
                //alert("producto añadido");
                
                console.log(KK);
                
                //actualiza popup carrito
                refrescarPopupCarrito(response);
                
                [#--  
				var cant = parseInt(document.getElementById("cantidad").value);
				if (typeof mapCarrito.get('${sku}') !== 'undefined') {
					cant += mapCarrito.get('${sku}');
				}
				mapCarrito.set('${sku}', parseInt(cant));
					
				console.log(mapCarrito);
				
				var flagPlazo = parseInt(cant) + 1;
				if (${stock} < flagPlazo) {
					$("#standar").css("display", "none");
					$("#aplazado").css("display", "block");
					document.getElementById("plazoEntrega").value = ${plazoProveedor} + 2;
				}
				if('${tipoPromo}' == 'escalado'){
					var map = new Map()
					[#list listPromos as promo]
						map.set("${promo.getCantidad_hasta()}", "${promo.getPvoDto()}");
					[/#list]
					var encontrado = false;
					
					
					map.forEach(function(value, key) {
						console.log('key =' + key + ' value = ' + value);
						console.log(flagPlazo);
						if (flagPlazo <= key.replace(".", "")) { //key cantidad hasta, value pvoDto
							if (!encontrado) {
								//actualizo el pvoDto
								document.getElementById("pvoConDescuento").value = value;
								$('#product-price-number-dto').text(value.concat(' €'));
							}
							encontrado = true;
						}
					})
				}
				--]
				
            },
            error : function(response) {
                alert("error"); 
                $("#loading").hide();           
            },
            complete : function(response) {
                $("#loading").hide();
                $(".product-button").removeAttr("disabled");
                
                $('.product-info-wrapper > .product-button-wrapper > .product-button').css("cursor", "not-allowed");
                $('.product-info-wrapper > .product-button-wrapper > .product-button').prop('disabled', true);
                resetAllFields(ojo);
            }
        });
    
    }
    
    function addtoCartDefault(sku, skuPackPadre, pvoPackUD, unidades, productId, variantId, familia, packName) {
    	var checkAdd = false;
		filterDetail = {};
		filterDetail["sku"] = sku;
		filterDetail["pvoPackUD"] = pvoPackUD;
		filterDetail["cantidad"] = unidades;
		filterDetail["productId"] = productId;
    	filterDetail["variantId"] = variantId;
    	filterDetail["familia"] = familia;
    	filterDetail["familiaProducto"] = familia;
    	filterDetail["skuPackPadre"] = skuPackPadre;
    	filterDetail["refPackPromos"] = $('#refPack').val();
    	filterDetail["packName"] = packName;
		
		$.ajax({
            url : "${ctx.contextPath}/.rest/private/carrito/v1/carts-addItem",
            type : "POST",
            data : JSON.stringify(filterDetail),
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            async: false,
            success : function(response) {
            	checkAdd = true;
            	refrescarPopupCarrito(response);
            },
            error : function(response) {
                alert("error");             
                //$("#change-pwd-result").html("<p>" +  response.responseJSON.error.message + "</p>");
            },
            complete : function(response) {
                
            }
        });
        
        return checkAdd;
	}

    function addtoCartEndPoint() {
    
    	filterDetail = getFormData($("#formDetalleProducto"));
    	var skuAdd = filterDetail.sku;
    	if ((filterDetail.sustitutiveProduct !== undefined) && (filterDetail.sustitutiveProduct != '')) {
    		skuAdd = filterDetail.sustitutiveProduct;
    	}
    	$("#modal-purchase-detail").css("display","none");
    	$("#modal-replacement-detail").css("display","none");
        $("#loading").show();
        $(".product-button").attr("disabled", "disabled");
        
        $("#sustitutiveProduct").val('');
        $('#sustitutiveProduct').prop('checked',false);
        
        $.ajax({
            url : "${ctx.contextPath}/.rest/private/carrito/v1/carts-addItem",
            type : "POST",
            data : JSON.stringify(filterDetail),
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            success : function(response) {
            
            	$("#fly-card").css("display","block");
				
				setTimeout(function(){ 
				$("#fly-card").css("display","none");
				}, 5000);
				
                var KK = response;
                //alert("producto añadido");
                
                console.log(KK);
                
                //actualiza popup carrito
                refrescarPopupCarrito(response);
                
				var cant = parseInt(document.getElementById("cantidad").value);
				if (typeof mapCarrito.get(skuAdd) !== 'undefined') {
					cant += mapCarrito.get(skuAdd);
				}
				mapCarrito.set(skuAdd, parseInt(cant));
					
				console.log(mapCarrito);
				
				var flagPlazo = parseInt(cant) + 1;
				if (${stock} < flagPlazo) {
					$("#standar").css("display", "none");
					$("#aplazado").css("display", "block");
					document.getElementById("plazoEntrega").value = ${plazoProveedor} + 2;
				}
				if('${tipoPromo}' == 'escalado'){
					var map = new Map()
					[#list listPromos as promo]
						map.set("${promo.getCantidad_hasta()}", "${promo.getPvoDto()}");
					[/#list]
					var encontrado = false;
					
					
					map.forEach(function(value, key) {
						console.log('key =' + key + ' value = ' + value);
						console.log(flagPlazo);
						if (flagPlazo <= key.replace(".", "")) { //key cantidad hasta, value pvoDto
							if (!encontrado) {
								//actualizo el pvoDto
								document.getElementById("pvoConDescuento").value = value;
								$('#product-price-number-dto').text(value.concat(' €'));
							}
							encontrado = true;
						}
					})
				}
				
            },
            error : function(response) {
                alert("error");             
                //$("#change-pwd-result").html("<p>" +  response.responseJSON.error.message + "</p>");
            },
            complete : function(response) {
                $("#loading").hide();
                $(".product-button").removeAttr("disabled");                
                document.getElementById("cantidad").value = 1;
                
                $("#sustitutiveProduct").val('');
                $('#sustitutiveProduct').prop('checked',false);
                
            }
        });
        return false;
    }
    
    function addtoCartEndPointAudio(filterDetail) {
    	
    	console.log(filterDetail);
        $("#loading").show();
        $(".product-button").attr("disabled", "disabled");
        
        var side = $(".selector-img.selected").attr('id');
        filterDetail["side"] = side;
        
        $.ajax({
            url : "${ctx.contextPath}/.rest/private/carrito/v1/carts-addItem",
            type : "POST",
            data : JSON.stringify(filterDetail),
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            success : function(response) {
            
            	$("#fly-card").css("display","block");
				
				setTimeout(function(){ 
				$("#fly-card").css("display","none");
				}, 5000);
				
                var KK = response;
                //alert("producto añadido");
                
                console.log(KK);
                
                //actualiza popup carrito
                refrescarPopupCarrito(response);
                
				var cant = 1;
				if (typeof mapCarrito.get('${sku}') !== 'undefined') {
					cant += mapCarrito.get('${sku}');
				}
				mapCarrito.set('${sku}', parseInt(cant));
					
				console.log(mapCarrito);
				
				var flagPlazo = parseInt(cant) + 1;
				
				if('${tipoPromo}' == 'escalado'){
					var map = new Map()
					[#list listPromos as promo]
						map.set("${promo.getCantidad_hasta()}", "${promo.getPvoDto()}");
					[/#list]
					var encontrado = false;
					
					
					map.forEach(function(value, key) {
						console.log('key =' + key + ' value = ' + value);
						console.log(flagPlazo);
						if (flagPlazo <= key.replace(".", "")) { //key cantidad hasta, value pvoDto
							if (!encontrado) {
								//actualizo el pvoDto
								document.getElementById("pvoConDescuento").value = value;
								$('#product-price-number-dto').text(value.concat(' €'));
							}
							encontrado = true;
						}
					})
				}
				
            },
            error : function(response) {
                alert("error");             
                //$("#change-pwd-result").html("<p>" +  response.responseJSON.error.message + "</p>");
            },
            complete : function(response) {
                $("#loading").hide();
                $(".product-button").removeAttr("disabled");
                $('#auriculares').prop('selectedIndex',0);
                $('#acopladores').prop('selectedIndex',0);
                $('#cargadores').prop('selectedIndex',0);
                $('#accesorios').prop('selectedIndex',0);
                $('#tubosFinos').prop('selectedIndex',0);
                $('#sujecionesDeportivas').prop('selectedIndex',0);
                $('#garantia').prop('selectedIndex',0);
                $('#radio2deposito').prop('checked', true);
                $("input[name='refCliente']").val('');
            }
        });
        return false;
    }
    
	function validateForm(oForm) {	
	
		//var cantidad = $(formDetalleProducto).data("cantidad") || null;
		var cantidad = document.getElementsByName("cantidad")[0].value
		if ((cantidad == null) || (cantidad <= 0)){
			$('.b2b-msg-cantidad').addClass("msg-error");
			$('.b2b-msg-cantidad').removeClass("msg-ok");
			$('.b2b-msg-cantidad').text("Seleccionar al menos una unidad");
			return false;
		} else {
			$('.b2b-msg-cantidad').addClass("msg-ok");
			$('.b2b-msg-cantidad').removeClass("msg-error");
			$('.b2b-msg-cantidad').text("");
			return true;
		}
	}
    
	function validateFormContactologia(oForm){
		var resVal = true;
		
		if ($("#cantidadizq").val() != null && $("#cantidadizq").val() > 0){
			
			if ($( "#esferaizq option:checked" ).val() != null && $( "#esferaizq option:checked" ).val() == ""){
				$('#esferaizq').addClass("validation-error");
				resVal = false;
			}else{
				$('#esferaizq').removeClass("validation-error");
			}
		
			if ($( "#cilindroizq option:checked" ).val() != null && $( "#cilindroizq option:checked" ).val() == ""){
				$('#cilindroizq').addClass("validation-error");
				resVal = false;
			}else{
				$('#cilindroizq').removeClass("validation-error");
			}
		
			if ($( "#ejeizq option:checked" ).val() != null && $( "#ejeizq option:checked" ).val() == ""){
				$('#ejeizq').addClass("validation-error");
				resVal = false;
			}else{
				$('#ejeizq').removeClass("validation-error");
			}
		
			if ($( "#diametroizq option:checked" ).val() != null && $( "#diametroizq option:checked" ).val() == ""){
				$('#diametroizq').addClass("validation-error");
				resVal = false;
			}else{
				$('#diametroizq').removeClass("validation-error");
			}
		
			if ($( "#curvabaseizq option:checked" ).val() != null && $( "#curvabaseizq option:checked" ).val() == ""){
				$('#curvabaseizq').addClass("validation-error");
				resVal = false;
			}else{
				$('#curvabaseizq').removeClass("validation-error");
			}
		
			if ($( "#adicionizq option:checked" ).val() != null && $( "#adicionizq option:checked" ).val() == ""){
				$('#adicionizq').addClass("validation-error");
				resVal = false;
			}else{
				$('#adicionizq').removeClass("validation-error");
			}
		
			if ($( "#disenolenteizq option:checked" ).val() != null && $( "#disenolenteizq option:checked" ).val() == ""){
				$('#disenolenteizq').addClass("validation-error");
				resVal = false;
			}else{
				$('#disenolenteizq').removeClass("validation-error");
			}
		
			if ($( "#colorlenteizq option:checked" ).val() != null && $( "#colorlenteizq option:checked" ).val() == ""){
				$('#colorlenteizq').addClass("validation-error");
				resVal = false;
			}else{
				$('#colorlenteizq').removeClass("validation-error");
			}
			
			
			
		}
		
		if ($("#cantidaddrch").val() != null && $("#cantidaddrch").val() > 0){
			
			if ($( "#esferadrch option:checked" ).val() != null && $( "#esferadrch option:checked" ).val() == ""){
				$('#esferadrch').addClass("validation-error");
				resVal = false;
			}else{
				$('#esferadrch').removeClass("validation-error");
			}
		
			if ($( "#cilindrodrch option:checked" ).val() != null && $( "#cilindrodrch option:checked" ).val() == ""){
				$('#cilindrodrch').addClass("validation-error");
				resVal = false;
			}else{
				$('#cilindrodrch').removeClass("validation-error");
			}
		
			if ($( "#ejedrch option:checked" ).val() != null && $( "#ejedrch option:checked" ).val() == ""){
				$('#ejedrch').addClass("validation-error");
				resVal = false;
			}else{
				$('#ejedrch').removeClass("validation-error");
			}
		
			if ($( "#diametrodrch option:checked" ).val() != null && $( "#diametrodrch option:checked" ).val() == ""){
				$('#diametrodrch').addClass("validation-error");
				resVal = false;
			}else{
				$('#diametrodrch').removeClass("validation-error");
			}
		
			if ($( "#curvabasedrch option:checked" ).val() != null && $( "#curvabasedrch option:checked" ).val() == ""){
				$('#curvabasedrch').addClass("validation-error");
				resVal = false;
			}else{
				$('#curvabasedrch').removeClass("validation-error");
			}
		
			if ($( "#adiciondrch option:checked" ).val() != null && $( "#adiciondrch option:checked" ).val() == ""){
				$('#adiciondrch').addClass("validation-error");				resVal = false;
			}else{
				$('#adiciondrch').removeClass("validation-error");
			}
		
			if ($( "#disenolentedrch option:checked" ).val() != null && $( "#disenolentedrch option:checked" ).val() == ""){
				$('#disenolentedrch').addClass("validation-error");
				resVal = false;
			}else{
				$('#disenolentedrch').removeClass("validation-error");
			}
		
			if ($( "#colorlentedrch option:checked" ).val() != null && $( "#colorlentedrch option:checked" ).val() == ""){
				$('#colorlentedrch').addClass("validation-error");
				resVal = false;
			}else{
				$('#colorlentedrch').removeClass("validation-error");
			}
			
		}
		
		if(!resVal){
			$(".b2b-msg-cilindro").parent().css("display", "block");
		}else{
			$(".b2b-msg-cilindro").parent().css("display", "none");
		}
		
		return resVal;
	}
	
	function getFormData($form) {
		var unindexed_array = $form.serializeArray();
		var indexed_array = {};
		$.map(unindexed_array, function(n, i) {
			indexed_array[n['name']] = n['value'];
		});
		if ($( "#sustitutiveProduct option:checked" ).val() != null && $( "#sustitutiveProduct option:checked" ).val() != "") {
			indexed_array["sustitutiveProduct"] = $( "#sustitutiveProduct option:checked" ).val();
		}
		return indexed_array;
	}
	
	function getFormDataAudioPack($form, sku, skuPackPadre, pvoPackUD, unidades, productId, variantId, familia, packName) {
		var filterDetail = getFormDataAudio($form);
		filterDetail["sku"] = sku;
		filterDetail["pvoConDescuento"] = pvoPackUD;
		filterDetail["cantidad"] = unidades;
		filterDetail["productId"] = productId;
    	filterDetail["variantId"] = variantId;
    	filterDetail["familia"] = familia;
    	filterDetail["familiaProducto"] = familia;
    	filterDetail["refPackPromos"] = $('#refPack').val();
    	filterDetail["packName"] = packName;
    	filterDetail["skuPackPadre"] = skuPackPadre;
    	
    	
    	return filterDetail;
	}
	
	function getFormDataAudio($form) {
	
		var unindexed_array = $form.serializeArray();
		var indexed_array = {};
		
		var refTaller = "";
		
		$.ajax({
            url : "${ctx.contextPath}/.rest/private/carrito/v1/refPack",
            type : "GET",
            contentType : 'application/json; charset=utf-8',
            cache : false,
            async: false,
            dataType : "json",
            success : function(response) {
            	refTaller = response["refPack"];
            },
            error : function(response) {
            	console.log("Error consultando el codigo autogenerado de refTaller.");
        	}
        });
		
		var addRefTaller = false;
		$.map(unindexed_array, function(n, i) {
			if ((n['name'] != 'plazoEntrega') && ((n['name'] != 'refTaller'))) {
				indexed_array[n['name']] = n['value'];
			}
		});
		
		if ($( "#coloraudifono option:checked" ).val() != null && $( "#coloraudifono option:checked" ).val() != ""){
			indexed_array["colorAudio"] = $( "#coloraudifono option:checked" ).val();
		}
		if ($( "#colorcodo option:checked" ).val() != null && $( "#colorcodo option:checked" ).val() != ""){
			indexed_array["colorCodo"] = $( "#colorcodo option:checked" ).val();
		}

		if ($( "#auriculares option:checked" ).val() != null && $( "#auriculares option:checked" ).val() != ""){
			indexed_array["auricular"] = $( "#auriculares option:checked" ).val();
			addRefTaller = true;
		}
		if ($( "#auricularesDestino" ).val() != null){
			indexed_array["auricular"] = getMultiValues("auricularesDestino");
			addRefTaller = true;
		}

		if ($( "#acopladores option:checked" ).val() != null && $( "#acopladores option:checked" ).val() != ""){
			indexed_array["acoplador"] = $( "#acopladores option:checked" ).val();
			addRefTaller = true;
		}
		if ($( "#acopladoresDestino" ).val() != null){
			indexed_array["acoplador"] = getMultiValues("acopladoresDestino");
			addRefTaller = true;
		}

		if ($( "#cargadores option:checked" ).val() != null && $( "#cargadores option:checked" ).val() != ""){
			indexed_array["cargador"] = $( "#cargadores option:checked" ).val();
			addRefTaller = true;
		}
		if ($( "#cargadoresDestino" ).val() != null){
			indexed_array["cargador"] = getMultiValues("cargadoresDestino");
			addRefTaller = true;
		}

		if ($( "#accesorios option:checked" ).val() != null && $( "#accesorios option:checked" ).val() != ""){
			indexed_array["accesorioinalambrico"] = $( "#accesorios option:checked" ).val();
			addRefTaller = true;
		}
		if ($( "#accesoriosDestino" ).val() != null){
			indexed_array["accesorioinalambrico"] = getMultiValues("accesoriosDestino");
			addRefTaller = true;
		}
		
		if ($( "#tubosFinos option:checked" ).val() != null && $( "#tubosFinos option:checked" ).val() != ""){
			indexed_array["tubosFinos"] = $( "#tubosFinos option:checked" ).val();
			addRefTaller = true;
		}
		if ($( "#tubosFinosDestino" ).val() != null){
			indexed_array["tubosFinos"] = getMultiValues("tubosFinosDestino");
			addRefTaller = true;
		}
		
		if ($( "#sujecionesDeportivas option:checked" ).val() != null && $( "#sujecionesDeportivas option:checked" ).val() != ""){
			indexed_array["sujecionesDeportivas"] = $( "#sujecionesDeportivas option:checked" ).val();
			addRefTaller = true;
		}
		if ($( "#sujecionesDeportivasDestino" ).val() != null){
			indexed_array["sujecionesDeportivas"] = getMultiValues("sujecionesDeportivasDestino");
			addRefTaller = true;
		}
		
		if ($( "#filtros option:checked" ).val() != null && $( "#filtros option:checked" ).val() != ""){
			indexed_array["filtros"] = $( "#filtros option:checked" ).val();
			addRefTaller = true;
		}
		if ($( "#filtrosDestino" ).val() != null){
			indexed_array["filtros"] = getMultiValues("filtrosDestino");
			addRefTaller = true;
		}
		
		if ($( "#garantia option:checked" ).val() != null && $( "#garantia option:checked" ).val() != "") {
			indexed_array["garantia"] = $( "#garantia option:checked" ).val();
			addRefTaller = true;
		}
		
		if ($("#radio1deposito").prop("checked")) {
			indexed_array["deposito"] = true;
			addRefTaller = true;
		}
		
		if (addRefTaller) {
			indexed_array["refTaller"] = refTaller;
		}
		
		
		if ($("input[name='refCliente']").val() != null && $("input[name='refCliente']").val() != ""){
			indexed_array["refCliente"] = $("input[name='refCliente']").val();
		}
		
		return indexed_array;
	}
	
	function getFormDataContactologia($form,ojoaenviar, codigocentral, skumask, config) {
	
		var familiaProducto = "${familiaProducto}";
		var unindexed_array = $form.serializeArray();
		var indexed_array = {};
		$.map(unindexed_array, function(n, i) {
			indexed_array[n['name']] = n['value'];
		});
		indexed_array["familiaProducto"] = familiaProducto;
		
		var refTaller = $('#refTaller').val();
		
		
		
		indexed_array["refTaller"] = refTaller;
		
		if (ojoaenviar == "izq"){
		
			indexed_array["lcsku"] = generateSkuContactologyIzq(config, codigocentral, skumask);
			
			if ($( "#esferaizq option:checked" ).val() != null && $( "#esferaizq option:checked" ).val() != ""){
				indexed_array["lcesfera"] = $( "#esferaizq option:checked" ).val();
			}
	
			indexed_array["lcojo"] = "I";
			
			[#-- ojo dominante  
			if ($( "#colorlenteizq option:checked" ).val() != null){
				indexed_array["LC_ojoDominante"] = "true";
			}
			--]
			
			if ($( "#disenolenteizq option:checked" ).val() != null && $( "#disenolenteizq option:checked" ).val() != ""){
				indexed_array["lcdiseno"] = $( "#disenolenteizq option:checked" ).val();
			}
			
			if ($( "#cilindroizq option:checked" ).val() != null && $( "#cilindroizq option:checked" ).val() != ""){
				indexed_array["lccilindro"] = $( "#cilindroizq option:checked" ).val();
			}
			
			if ($( "#ejeizq option:checked" ).val() != null && $( "#ejeizq option:checked" ).val() != ""){
				indexed_array["lceje"] = $( "#ejeizq option:checked" ).val();
			}
			
			if ($( "#diametroizq option:checked" ).val() != null && $( "#diametroizq option:checked" ).val() != ""){
				indexed_array["lcdiametro"] = $( "#diametroizq option:checked" ).val();
			}
			
			if ($( "#curvabaseizq option:checked" ).val() != null && $( "#curvabaseizq option:checked" ).val() != ""){
				indexed_array["lccurvaBase"] = $( "#curvabaseizq option:checked" ).val();
			}
			
			if ($( "#colorlenteizq option:checked" ).val() != null && $( "#colorlenteizq option:checked" ).val() != ""){
				indexed_array["lccolor"] = $( "#colorlenteizq option:checked" ).val();
				indexed_array["lcdesccolor"] = $( "#colorlenteizq option:selected" ).text();
			}
			
			if ($( "#adicionizq option:checked" ).val() != null && $( "#adicionizq option:checked" ).val() != ""){
				indexed_array["lcadicion"] = $( "#adicionizq option:checked" ).val();
			}
			
			if ($("#cantidadizq").val() != null && $("#cantidadizq").val() != ""){
				indexed_array["cantidad"] = $("#cantidadizq").val();
			}
			
			if ($("#ireferencia").val() != null && $("#ireferencia").val() != ""){
				indexed_array["refCliente"] = $("#ireferencia").val();
			}
			
			if ($("#plazoEntregaIzq").val() != null && $("#plazoEntregaIzq").val() != ""){
				indexed_array["plazoEntrega"] = $("#plazoEntregaIzq").val();
			}
			
			
		}
		
		if (ojoaenviar == "drch"){
	
			indexed_array["lcsku"] = generateSkuContactologyDrch(config, codigocentral, skumask);
			
			if ($( "#esferadrch option:checked" ).val() != null && $( "#esferadrch option:checked" ).val() != ""){
				indexed_array["lcesfera"] = $( "#esferadrch option:checked" ).val();
			}
	
			indexed_array["lcojo"] = "D";
			
			if ($( "#disenolentedrch option:checked" ).val() != null && $( "#disenolentedrch option:checked" ).val() != ""){
				indexed_array["lcdiseno"] = $( "#disenolentedrch option:checked" ).val();
			}
			
			if ($( "#cilindrodrch option:checked" ).val() != null && $( "#cilindrodrch option:checked" ).val() != ""){
				indexed_array["lccilindro"] = $( "#cilindrodrch option:checked" ).val();
			}
			
			if ($( "#ejedrch option:checked" ).val() != null && $( "#ejedrch option:checked" ).val() != ""){
				indexed_array["lceje"] = $( "#ejedrch option:checked" ).val();
			}
			
			if ($( "#diametrodrch option:checked" ).val() != null && $( "#diametrodrch option:checked" ).val() != ""){
				indexed_array["lcdiametro"] = $( "#diametrodrch option:checked" ).val();
			}
			
			if ($( "#curvabasedrch option:checked" ).val() != null && $( "#curvabasedrch option:checked" ).val() != ""){
				indexed_array["lccurvaBase"] = $( "#curvabasedrch option:checked" ).val();
			}
			
			if ($( "#colorlentedrch option:checked" ).val() != null && $( "#colorlentedrch option:checked" ).val() != ""){
				indexed_array["lccolor"] = $( "#colorlentedrch option:checked" ).val();
				indexed_array["lcdesccolor"] = $( "#colorlentedrch option:selected" ).text();
			}
			
			if ($( "#adiciondrch option:checked" ).val() != null && $( "#adiciondrch option:checked" ).val() != ""){
				indexed_array["lcadicion"] = $( "#adiciondrch option:checked" ).val();
			}
			
			if ($("#cantidaddrch").val() != null && $("#cantidaddrch").val() != ""){
				indexed_array["cantidad"] = $("#cantidaddrch").val();
			}
			
			if ($("#dreferencia").val() != null && $("#dreferencia").val() != ""){
				indexed_array["refCliente"] = $("#dreferencia").val();
			}
			
			if ($("#plazoEntregaDrch").val() != null && $("#plazoEntregaDrch").val() != ""){
				indexed_array["plazoEntrega"] = $("#plazoEntregaDrch").val();
			}
		}
		
		return indexed_array;
	}

function refrescarDetalle() {
	var unidadesCarrito = 0;
	var tipoPromo = '${tipoPromo}';
	
	//comprobacion del carrito para actualizar el precio de promociones y plazo de entrega
	if (typeof mapCarrito !== 'undefined') {
		if(typeof mapCarrito.get('${sku}') !== 'undefined') {
			unidadesCarrito = mapCarrito.get('${sku}');
		}
		var auxInitStock = ${stock} - (unidadesCarrito + 1); //añade una unidad porque por defecto vamos a añadir 1
		if (auxInitStock >= 0 ) {
			$("#standar").show();
			$("#aplazado").hide();
			if(typeof $("#plazoEntrega").val() !== 'undefined'){
				document.getElementById("plazoEntrega").value = 2;
			}
		} else {
			$("#standar").hide();
			$("#aplazado").show();
			if(typeof $("#plazoEntrega").val() !== 'undefined'){
				document.getElementById("plazoEntrega").value = ${plazoProveedor} + 2;
			}
		}
		
		if(tipoPromo == 'escalado') {
			var encontrado = false;
			map.forEach(function(value, key) {
				if ((unidadesCarrito + 1) <= key.replace(".", "")) { //key cantidad hasta, value pvoDto
					if (!encontrado) {
						document.getElementById("pvoConDescuento").value = value;
						$('#product-price-number-dto').text(value.concat(' €'));
					}
					encontrado = true;
				}
			})
		}
	}
}

	function closeModalDetail(that) {
		$("#modal-purchase-detail").css("display","none");
	}
	
	function closeModalPacks(that) {
		$("#modal-packs-detail").css("display","none");
	}

	function closeModalContactologia(that) {
		$("#modal-contactologia-detail").css("display","none");
		$("#modalojoizq").css("display", "none");
		$("#modalojodrc").css("display", "none");
	}
	
	function closeModalReplacements(that) {
		$("#modal-replacement-detail").css("display","none");
		$("#sustitutiveProduct").val('');
	}
	
	function addProductOrigin(that) {
		$("#sustitutiveProduct").val('');
		addtoCartEndPoint();
	}
	
	function generateReplacementModal(sku, cant) {
		console.log(sku);
		console.log(cant);
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
					    	html += '<input type="hidden" id="sustitutiveProduct" name="sustitutiveProduct" value="' + key +'" />';
                    		html += resMap[key];
					    }
					}
					$("#listReplacement").html(html);
                    $("#modal-replacement-detail").css("display","flex");
	        	} else if (mapReplacements.size > 1){
	        		html += '<div style="max-width: 300px;">';
	        		html += '<select name="" id="sustitutiveProduct" autocomplete="off">';
		        	for (var key in resMap) {
					    if (resMap.hasOwnProperty(key)) {
					    	console.log(key);
					    	console.log(resMap[key]);
					    	html += '<option value="' + key + '">' + resMap[key] + '</option>';
					    }
					}
					html += '</select>';
					html += '</div>';
					$("#listReplacement").html(html);
                    $("#modal-replacement-detail").css("display","flex");
	        	} else {
	        		$("#modal-purchase-detail").css("display","flex");
	        	}
	        },
	        error: function(response) { 
	        	console.log("Error al recuperar los productos sustitutivos"); 
	        	$("#modal-purchase-detail").css("display","flex");
	        }
		});
	}

	function updateSinglePvo(component) {
		values = getMultiValues(component);
		$.ajax({
			url : "${ctx.contextPath}/.rest/private/carrito/v1/pvo?sku=" +  encodeURIComponent(values),
            type : "GET",
            contentType : 'application/json; charset=utf-8',
            cache : false,
            async: false,
            dataType : "json",
	        success: function(response) {
	        	$('#pvo-individual-1').text(response.pvo);
	        },
	        error: function(response) { 
	        	console.log("Error al recuperar el pvo de los productos"); 
	        }
		});
	}
	
	//multiselect
	var add = function(origen,destino,ordenar,input, limit){

        var index_selected = new Array();
        var index_dados = 0;
    
        var select_origen = document.getElementById(origen);
        var select_destino = document.getElementById(destino);
    
        var length = select_origen.length;

		if (limit > 0 && select_destino.length >= limit) {
			return;
		}
    
        for(var i=0; i<length; i++){
            if(select_origen.options[i].selected){
    
                index_selected.push(i);
            }
        }
    
        index_selected.reverse();
    
    
        for(var i=0; i< index_selected.length; i++){
    
            var option_aux = select_origen.options[index_selected[i]].cloneNode(true);

            if (origen.includes('Destino')){
                select_origen.remove(index_selected[i]);
            }
            else{
                select_destino.add(option_aux);
            }
    
            
    
        }
    
        /*verificando se há necessidade de ordenar os options*/
        if(ordenar){
    
            var dados_option = new Array();
            var options =  document.getElementById(destino).options;
    
            for(var i=0; i<options.length; i++){
                dados_option[i] = new Array();
                dados_option[i][0] = options[i].text;
                dados_option[i][1] = options[i].value;
            }
    
            /*limpando o select*/
            document.getElementById(destino).innerHTML = "";
    
            dados_option.sort();
    
            /*adicionando os options ordenado*/
            for(var i=0; i<dados_option.length; i++){
                var new_option = new Option(dados_option[i][0],dados_option[i][1],"","");
    
                document.getElementById(destino).add(new_option);
            }
    
        }else{
    
            //revertendo a ordem
            var dados_option = new Array();
            var options =  document.getElementById(destino).options;
    
            for(var i=0; i<options.length; i++){
                dados_option[i] = new Array();
                dados_option[i][0] = options[i].text;
                dados_option[i][1] = options[i].value;
            }
    
            /*limpando o select*/
            document.getElementById(destino).innerHTML = "";
    
            dados_option.reverse();
    
            /*adicionando os options ordenado*/
            for(var i=0; i<dados_option.length; i++){
                var new_option = new Option(dados_option[i][0],dados_option[i][1],"","");
    
                document.getElementById(destino).add(new_option);
            }
    
        }
    
        /*removendo a selecao dos selects*/
        select_origen.selectedIndex = -1;
        select_destino.selectedIndex = -1;
    
        /*colocando os valores no lugar desejado*/
        if(!ordenar)
            get_join_values(destino,input);
        else
            get_join_values(origen,input);
    
    }
    

    /*função para varrer e juntar os valores relacionados no select desejado*/
    var get_join_values = function(id,input){
        var select = document.getElementById(id);
        var values = "";
    
        for(var i=0; i<select.length;i++){
            if(i==0){
                values = select.options[i].value;
            }else{
                values += ","+select.options[i].value;
            }
        }
    
        document.getElementById(id).value = values;
    }

	function getMultiValues(id) {
		var select = document.getElementById(id);
        var values = "";
    
        for(var i=0; i<select.length;i++){
            if(i==0){
                values = select.options[i].value;
            }else{
                values += ","+select.options[i].value;
            }
        }
		return values;
	}
</script>


</form>
</div>


[#-- MACROS @audifonos--]
[#macro audifonos title infoVariant]
	[#assign free = false]
	[#assign freeUnits = 0]
	[#if infoVariant.listadoProductosRegalo?? && infoVariant.listadoProductosRegalo?has_content]
		[#if infoVariant.listadoProductosRegalo[title]?? && infoVariant.listadoProductosRegalo[title]?has_content]
			[#assign free = true]
			[#assign freeUnits = infoVariant.listadoProductosRegalo[title].units]
		[/#if]
		[#--  --assign listado = infoVariant.listadoProductosRegalo!]
		[#list listado?keys as groupKey]
			[#assign valor = listado[groupKey]!]
			[#if valor == title]
				[#assign free = true]
			[/#if]
        [/#list --]
	[/#if]
	[#switch title]
		[#case "coloraudifono"]
			[#if infoVariant.getColoraudifonos()?has_content]
	        <div class="col-12 col-md-6">
	            <label for="coloraudifono">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.color-audifono']} </label>
	            <select name="" id="coloraudifono" autocomplete="off">
	                [#assign colores = infoVariant.getColoraudifonos()]
	        		[#list colores as val]
	                	<option value="${val!""}">${val!""}</option>
	                [/#list]
	            </select>
	        </div>
			[/#if]
			[#break]
		[#case "colorcodo"]
			[#if infoVariant.getColorCodo()?has_content]
	        <div class="col-12 col-md-6">
	            <label for="colorcodo">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.color-codo']} </label>
	            <select name="" id="colorcodo" autocomplete="off">
	                [#assign colorescodo = infoVariant.getColorCodo()]
	        		[#list colorescodo as val]
	                	<option value="${val!""}">${val!""}</option>
	                [/#list]
	            </select>
	        </div>
			[/#if]
			[#break]
		[#case "auriculares"]
			[#if infoVariant.getAuriculares()?has_content]
				[#assign auriculares = infoVariant.getAuriculares()]
				[#if free]
					</div>
					<div class="row">
						<div class="col-12 col-md-5">
			                <label for="auricularesOrigen">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.auricular']} [#if free] ${i18n['cione-module.templates.myshop.listado-productos-home-component.free']} [/#if]</label>
			                <div class="select_1">
			                    
			                    <!-- ID del select utilizado en el JS -->
			                    <select id="auricularesOrigen" name="auricularesOrigen[]" multiple="multiple">
			                    	[#list auriculares?keys as key]
				                    	<option value="${key!""}">${auriculares[key]!""}</option>
				                    [/#list]
			                    </select>
			                </div>
			            </div>
			            <div class="col-12 col-md-2">
			                <div class="operadores b2b-button-wrapper">
			                                  
								<div class=" b2b-button b2b-button-filter" onclick="add('auricularesOrigen','auricularesDestino',false,'values_selecteds_listbox',${freeUnits}); updateSinglePvo('auricularesDestino');">
									<span class="copy-arrow-right"></span>
								</div>
								<div class=" b2b-button b2b-button-filter" onclick="add('auricularesDestino','auricularesOrigen',true,'values_selecteds_listbox,0'); updateSinglePvo('auricularesDestino');">
									<span class="copy-arrow-left"></span>
								</div>
							</div>
			
						</div>
			            <div class="col-12 col-md-5">
			                <label for="auricularesDestino">${i18n['cione-module.templates.myshop.listado-productos-home-component.producto-seleccionado']}</label>
			                <div class="select_2">
			                    <!-- ID del select utilizado en el JS -->
			                    <select id="auricularesDestino" name="auricularesDestino[]" multiple="multiple"  limit="${freeUnits}"></select>
			                </div>
			            </div>
			        </div>
		            <div class="row">
				[#else]
		            <div class="col-12 col-md-6">
		                <label for="auriculares">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.auricular']} [#if free] ${i18n['cione-module.templates.myshop.listado-productos-home-component.free']} [/#if]</label>
		                
		                <select name="" id="auriculares" autocomplete="off">
		            		<option value="" selected></option>
		            		[#list auriculares?keys as key]
		                    	<option value="${key!""}">${auriculares[key]!""}</option>
		                    [/#list]
		                </select>
		                
		            </div>
	            [/#if]
            [/#if]
			[#break]
		[#case "acopladores"]
        	[#if infoVariant.getAcopladores()?has_content]
				[#assign acopladores = infoVariant.getAcopladores()]
				[#if free]
					</div>
					<div class="row">
						<div class="col-12 col-md-5">
			                <label for="acopladoresOrigen">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.acoplador']} [#if free] ${i18n['cione-module.templates.myshop.listado-productos-home-component.free']} [/#if]</label>
			                <div class="select_1">
			                    
			                    <!-- ID del select utilizado en el JS -->
			                    <select id="acopladoresOrigen" name="acopladoresOrigen[]" multiple="multiple">
			                    	[#list acopladores?keys as key]
				                    	<option value="${key!""}">${acopladores[key]!""}</option>
				                    [/#list]
			                    </select>
			                </div>
			            </div>
			            <div class="col-12 col-md-2">
			                <div class="operadores b2b-button-wrapper">
			                                  
								<div class=" b2b-button b2b-button-filter" onclick="add('acopladoresOrigen','acopladoresDestino',false,'values_selecteds_listbox',${freeUnits}); updateSinglePvo('acopladoresDestino');">
									<span class="copy-arrow-right"></span>
								</div>
								<div class=" b2b-button b2b-button-filter" onclick="add('acopladoresDestino','acopladoresOrigen',true,'values_selecteds_listbox,0'); updateSinglePvo('acopladoresDestino');">
									<span class="copy-arrow-left"></span>
								</div>
							</div>
			
						</div>
			            <div class="col-12 col-md-5">
			                <label for="acopladoresDestino">${i18n['cione-module.templates.myshop.listado-productos-home-component.producto-seleccionado']}</label>
			                <div class="select_2">
			                    <!-- ID del select utilizado en el JS -->
			                    <select id="acopladoresDestino" name="acopladoresDestino[]" multiple="multiple"  limit="${freeUnits}"></select>
			                </div>
			            </div>
			        </div>
		            <div class="row">
				[#else]
					<div class="col-12 col-md-6">
						<label for="acopladores">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.acoplador']} [#if free] ${i18n['cione-module.templates.myshop.listado-productos-home-component.free']} [/#if]</label>
						<select name="" id="acopladores" autocomplete="off">
							<option value="" selected></option>
							[#list acopladores?keys as key]
								<option value="${key!""}">${acopladores[key]!""}</option>
							[/#list]
						</select>
					</div>
				[/#if]
            [/#if]
			[#break]
		[#case "cargadores"]
			[#if infoVariant.getCargadores()?has_content]
				[#assign cargadores = infoVariant.getCargadores()]
				[#if free]
					</div>
					<div class="row">
						<div class="col-12 col-md-5">
			                <label for="cargadoresOrigen">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.cargador']} [#if free] ${i18n['cione-module.templates.myshop.listado-productos-home-component.free']} [/#if]</label>
			                <div class="select_1">
			                    
			                    <!-- ID del select utilizado en el JS -->
			                    <select id="cargadoresOrigen" name="cargadoresOrigen[]" multiple="multiple">
			                    	[#list cargadores?keys as key]
				                    	<option value="${key!""}">${cargadores[key]!""}</option>
				                    [/#list]
			                    </select>
			                </div>
			            </div>
			            <div class="col-12 col-md-2">
			                <div class="operadores b2b-button-wrapper">
			                                  
								<div class=" b2b-button b2b-button-filter" onclick="add('cargadoresOrigen','cargadoresDestino',false,'values_selecteds_listbox',${freeUnits}); updateSinglePvo('cargadoresDestino');">
									<span class="copy-arrow-right"></span>
								</div>
								<div class=" b2b-button b2b-button-filter" onclick="add('cargadoresDestino','cargadoresOrigen',true,'values_selecteds_listbox,0'); updateSinglePvo('cargadoresDestino');">
									<span class="copy-arrow-left"></span>
								</div>
							</div>
			
						</div>
			            <div class="col-12 col-md-5">
			                <label for="cargadoresDestino">${i18n['cione-module.templates.myshop.listado-productos-home-component.producto-seleccionado']}</label>
			                <div class="select_2">
			                    <!-- ID del select utilizado en el JS -->
			                    <select id="cargadoresDestino" name="cargadoresDestino[]" multiple="multiple"  limit="${freeUnits}"></select>
			                </div>
			            </div>
			        </div>
		            <div class="row">
				[#else]
					<div class="col-12 col-md-6">
						<label for="cargadores">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.cargador']} [#if free] ${i18n['cione-module.templates.myshop.listado-productos-home-component.free']} [/#if]</label>
						<select name="" id="cargadores" autocomplete="off">
							<option value="" selected></option>
							[#list cargadores?keys as key]
								<option value="${key!""}">${cargadores[key]!""}</option>
							[/#list]
						</select>
					</div>
				[/#if]
            [/#if]
			[#break]
		[#case "accesorios"]
			[#if infoVariant.getAccesorios()?has_content]
				[#assign accesorios = infoVariant.getAccesorios()]
				[#if free]
					</div>
		            <div class="row">
						<div class="col-12 col-md-5">
			                <label for="accesoriosOrigen">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.inalambricos']} [#if free] ${i18n['cione-module.templates.myshop.listado-productos-home-component.free']} [/#if]</label>
			                <div class="select_1">
			                    
			                    <!-- ID del select utilizado en el JS -->
			                    <select id="accesoriosOrigen" name="accesoriosOrigen[]" multiple="multiple">
			                    	[#list accesorios?keys as key]
				                    	<option value="${key!""}">${accesorios[key]!""}</option>
				                    [/#list]
			                    </select>
			                </div>
			            </div>
			            <div class="col-12 col-md-2">
			                <div class="operadores b2b-button-wrapper">
			                                  
								<div class=" b2b-button b2b-button-filter" onclick="add('accesoriosOrigen','accesoriosDestino',false,'values_selecteds_listbox',${freeUnits}); updateSinglePvo('accesoriosDestino');">
									<span class="copy-arrow-right"></span>
								</div>
								<div class=" b2b-button b2b-button-filter" onclick="add('accesoriosDestino','accesoriosOrigen',true,'values_selecteds_listbox,0'); updateSinglePvo('accesoriosDestino');">
									<span class="copy-arrow-left"></span>
								</div>
							</div>
			
						</div>
			            <div class="col-12 col-md-5">
			                <label for="accesoriosDestino">${i18n['cione-module.templates.myshop.listado-productos-home-component.producto-seleccionado']}</label>
			                <div class="select_2">
			                    <!-- ID del select utilizado en el JS -->
			                    <select id="accesoriosDestino" name="accesoriosDestino[]" multiple="multiple"  limit="${freeUnits}"></select>
			                </div>
			            </div>
			        </div>
		            <div class="row">
				[#else]
					<div class="col-12 col-md-6">
						<label for="accesorios">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.inalambricos']} [#if free] ${i18n['cione-module.templates.myshop.listado-productos-home-component.free']} [/#if]</label>
						<select name="" id="accesorios" autocomplete="off">
							<option value="" selected></option>
							[#list accesorios?keys as key]
								<option value="${key!""}">${accesorios[key]!""}</option>
							[/#list]
						</select>
					</div>
				[/#if]
	        [/#if]
			[#break]
		[#case "tubosFinos"]
	        [#if infoVariant.getTubosFinos()?has_content]
				[#assign tubosFinos = infoVariant.getTubosFinos()]
				[#if free]
					</div>
		            <div class="row">
						<div class="col-12 col-md-5">
			                <label for="tubosFinosOrigen">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.tfinos']} [#if free] ${i18n['cione-module.templates.myshop.listado-productos-home-component.free']} [/#if]</label>
			                <div class="select_1">
			                    
			                    <!-- ID del select utilizado en el JS -->
			                    <select id="tubosFinosOrigen" name="tubosFinosOrigen[]" multiple="multiple">
			                    	[#list tubosFinos?keys as key]
				                    	<option value="${key!""}">${tubosFinos[key]!""}</option>
				                    [/#list]
			                    </select>
			                </div>
			            </div>
			            <div class="col-12 col-md-2">
			                <div class="operadores b2b-button-wrapper">
			                                  
								<div class=" b2b-button b2b-button-filter" onclick="add('tubosFinosOrigen','tubosFinosDestino',false,'values_selecteds_listbox',${freeUnits}); updateSinglePvo('tubosFinosDestino');">
									<span class="copy-arrow-right"></span>
								</div>
								<div class=" b2b-button b2b-button-filter" onclick="add('tubosFinosDestino','tubosFinosOrigen',true,'values_selecteds_listbox,0'); updateSinglePvo('tubosFinosDestino');">
									<span class="copy-arrow-left"></span>
								</div>
							</div>
			
						</div>
			            <div class="col-12 col-md-5">
			                <label for="tubosFinosDestino">${i18n['cione-module.templates.myshop.listado-productos-home-component.producto-seleccionado']}</label>
			                <div class="select_2">
			                    <!-- ID del select utilizado en el JS -->
			                    <select id="tubosFinosDestino" name="tubosFinosDestino[]" multiple="multiple"  limit="${freeUnits}"></select>
			                </div>
			            </div>
			        </div>
		            <div class="row">
				[#else]
					<div class="col-12 col-md-6">
						<label for="accesorios">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.tfinos']} [#if free] ${i18n['cione-module.templates.myshop.listado-productos-home-component.free']} [/#if]</label>
						<select name="" id="tubosFinos" autocomplete="off">
							<option value="" selected></option>
							[#list tubosFinos?keys as key]
								<option value="${key!""}">${tubosFinos[key]!""}</option>
							[/#list]
						</select>
					</div>
				[/#if]
	        [/#if]
			[#break]
		[#case "sujecionesDeportivas"]
			[#if infoVariant.getSujecionesDeportivas()?has_content]
					[#assign sujecionesDeportivas = infoVariant.getSujecionesDeportivas()]
					[#if free]
					</div>
		            <div class="row">
						<div class="col-12 col-md-5">
			                <label for="sujecionesDeportivasOrigen">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.sports']} [#if free] ${i18n['cione-module.templates.myshop.listado-productos-home-component.free']} [/#if]</label>
			                <div class="select_1">
			                    
			                    <!-- ID del select utilizado en el JS -->
			                    <select id="sujecionesDeportivasOrigen" name="sujecionesDeportivasOrigen[]" multiple="multiple">
			                    	[#list sujecionesDeportivas?keys as key]
				                    	<option value="${key!""}">${sujecionesDeportivas[key]!""}</option>
				                    [/#list]
			                    </select>
			                </div>
			            </div>
			            <div class="col-12 col-md-2">
			                <div class="operadores b2b-button-wrapper">
			                                  
								<div class=" b2b-button b2b-button-filter" onclick="add('tubosFinosOrigen','sujecionesDeportivasDestino',false,'values_selecteds_listbox',${freeUnits}); updateSinglePvo('sujecionesDeportivasDestino');">
									<span class="copy-arrow-right"></span>
								</div>
								<div class=" b2b-button b2b-button-filter" onclick="add('sujecionesDeportivasDestino','sujecionesDeportivasOrigen',true,'values_selecteds_listbox,0'); updateSinglePvo('sujecionesDeportivasDestino');">
									<span class="copy-arrow-left"></span>
								</div>
							</div>
			
						</div>
			            <div class="col-12 col-md-5">
			                <label for="sujecionesDeportivasDestino">${i18n['cione-module.templates.myshop.listado-productos-home-component.producto-seleccionado']}</label>
			                <div class="select_2">
			                    <!-- ID del select utilizado en el JS -->
			                    <select id="sujecionesDeportivasDestino" name="sujecionesDeportivasDestino[]" multiple="multiple"  limit="${freeUnits}"></select>
			                </div>
			            </div>
			        </div>
		            <div class="row">
				[#else]
					<div class="col-12 col-md-6">
						[#-- SUJECIONES DEPORTIVAS --]
						<label for="accesorios">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.sports']} [#if free] ${i18n['cione-module.templates.myshop.listado-productos-home-component.free']} [/#if]</label>
						<select name="" id="sujecionesDeportivas" autocomplete="off">
							<option value="" selected></option>
							[#list sujecionesDeportivas?keys as key]
								<option value="${key!""}">${sujecionesDeportivas[key]!""}</option>
							[/#list]
						</select>
					</div>
				[/#if]
			[/#if]
			[#break]
		[#case "filtros"]
			[#if infoVariant.getFiltros()?has_content]
				[#assign filtros = infoVariant.getFiltros()]
				[#if free]
					</div>
		            <div class="row">
						<div class="col-12 col-md-5">
			                <label for="filtrosOrigen">${i18n['cione-module.templates.myshop.listado-productos-home-component.filtros']} [#if free] ${i18n['cione-module.templates.myshop.listado-productos-home-component.free']} [/#if]</label>
			                <div class="select_1">
			                    
			                    <!-- ID del select utilizado en el JS -->
			                    <select id="filtrosOrigen" name="filtrosOrigen[]" multiple="multiple">
			                    	[#list filtros?keys as key]
				                    	<option value="${key!""}">${filtros[key]!""}</option>
				                    [/#list]
			                    </select>
			                </div>
			            </div>
			            <div class="col-12 col-md-2">
			                <div class="operadores b2b-button-wrapper">
			                                  
								<div class=" b2b-button b2b-button-filter" onclick="add('tubosFinosOrigen','filtrosDestino',false,'values_selecteds_listbox',${freeUnits}); updateSinglePvo('filtrosDestino');">
									<span class="copy-arrow-right"></span>
								</div>
								<div class=" b2b-button b2b-button-filter" onclick="add('filtrosDestino','filtrosOrigen',true,'values_selecteds_listbox,0'); updateSinglePvo('filtrosDestino');">
									<span class="copy-arrow-left"></span>
								</div>
							</div>
			
						</div>
			            <div class="col-12 col-md-5">
			                <label for="filtrosDestino">${i18n['cione-module.templates.myshop.listado-productos-home-component.producto-seleccionado']}</label>
			                <div class="select_2">
			                    <!-- ID del select utilizado en el JS -->
			                    <select id="filtrosDestino" name="filtrosDestino[]" multiple="multiple"  limit="${freeUnits}"></select>
			                </div>
			            </div>
			        </div>
		            <div class="row">
				[#else]
					<div class="col-12 col-md-6">
						[#-- FILTROS --]
						<label for="accesorios">${i18n['cione-module.templates.myshop.listado-productos-home-component.filtros']} [#if free] ${i18n['cione-module.templates.myshop.listado-productos-home-component.free']} [/#if]</label>
						<select name="" id="filtros" autocomplete="off">
							<option value="" selected></option>
							[#list filtros?keys as key]
								<option value="${key!""}">${filtros[key]!""}</option>
							[/#list]
						</select>
					</div>
				[/#if]
			[/#if]
			[#break]
	[/#switch]

[/#macro]


[#-- FUNCTIONS --]
[#function numberOfRows]
  [#assign totalNum = 0]
  [#if infoVariant.getColoraudifonos()?has_content]
  	[#assign totalNum = totalNum + 1]
  [/#if]
  [#if infoVariant.getColorCodo()?has_content]
  	[#assign totalNum = totalNum + 1]
  [/#if]
  [#if infoVariant.getAuriculares()?has_content]
  	[#assign totalNum = totalNum + 1]
  [/#if]
  [#if infoVariant.getAcopladores()?has_content]
  	[#assign totalNum = totalNum + 1]
  [/#if]
  [#if infoVariant.getCargadores()?has_content]
  	[#assign totalNum = totalNum + 1]
  [/#if]
  [#if infoVariant.getAccesorios()?has_content]
  	[#assign totalNum = totalNum + 1]
  [/#if]
  [#if infoVariant.getTubosFinos()?has_content]
  	[#assign totalNum = totalNum + 1]
  [/#if]
  [#if infoVariant.getSujecionesDeportivas()?has_content]
  	[#assign totalNum = totalNum + 1]
  [/#if]
  [#if infoVariant.getFiltros()?has_content]
  	[#assign totalNum = totalNum + 1]
  [/#if]
  [#return (totalNum / 2)?round]
[/#function]