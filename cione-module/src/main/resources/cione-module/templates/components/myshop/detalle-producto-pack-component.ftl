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
[#assign skuPackMaster = infoVariant.getSkuPackMaster()!]


[#assign descripcion = infoVariant.getDescripcion()!]

[#assign existe = infoVariant.isExist()!]


[#assign listPromos = infoVariant.getListPromos()!]

[#assign uuid = model.getUuid()!]
[#assign username = model.getUserName()!]

[#assign refPack = model.getRefTaller()]
[#assign stock = 0.0]
[#assign refTaller = ""]

[#assign userId = model.getCustomerId()!]

[#assign idCarritoOcultoMaster =""]

[#assign enabledRemovePackgenerico = false]
[#assign completePack = true]
[#assign isPackWithFilters = true]
[#assign hasProductPackCioneLab = false]
[#assign allProductPreConfigured = true]

[#if infoVariant.contenidoPackListGenerico?? && infoVariant.contenidoPackListGenerico?has_content && (infoVariant.contenidoPackListGenerico?size > 0)]
	[#list infoVariant.contenidoPackListGenerico as productPack]
		[#if productPack.configurado && !productPack.skuProductoPackPreconfigurado??]
			[#assign enabledRemovePackgenerico = true]
		[#else]
			[#assign completePack = false]
		[/#if]
		[#if productPack.skuProductoPackPreconfigurado?? && productPack.skuProductoPackPreconfigurado?has_content]
			[#assign isPackWithFilters = false]
		[#else]
			[#assign allProductPreConfigured = false]
		[/#if]
		[#if productPack.infoCustomLineItemsCioneLab?? && productPack.infoCustomLineItemsCioneLab?has_content]
			[#assign hasProductPackCioneLab = true]
		[/#if]
	[/#list]
[/#if]

[#assign listadoProductosPreconfigurados = [] ]


[#-- 
	 aqui se debe determinar que productos estamos renderizando para poder 
	 determinar que clases debemos insertar
	 VER LA PANTALLA DETALLE DE CONTACTOLOGIA
  --]

[#assign container = "container-fluid"]
[#assign imageswrapper = "b2b-55"]
[#assign cardwrapper = "b2b-45"]


<style>
.validation-error {
	box-shadow: 0 0 0px 1px #EE0000;
}
.disabled-cart {
	cursor: not-allowed !important;
	background-color: #837f7f !important;
}

.disabled-cursor {
	cursor: not-allowed !important;
}

</style>
<script>
	//control para la pulsacion del boton volver en caso de que se haya seleccionado "cambiar"
	window.addEventListener('pageshow', (event) => {
		console.log("pageshow");
		console.log(performance.getEntriesByType("navigation")[0].type);
		console.log(event.persisted);
        if (performance.getEntriesByType("navigation")[0].type === "back_forward") {
            window.location.reload();
        }
    });
    
</script>

<div class="${container!"container"}">
<form id="formDetalleProducto" name="formDetalleProducto" method="POST" action="${ctx.contextPath}/cione/private/myshop/monturas/listado.html" style="display: contents;">
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
	
	
	<input type="hidden" id="skuPackMaster" name="skuPackMaster" value="${skuPackMaster}" />
	<input type="hidden" id="agrupadores" name="agrupadores" value="" />
	
	<div class="b2b-detail-container">
	    <div class="b2b-detail-images-wrapper ${imageswrapper!""}">
	        <div class="b2b-detalle-imagen">
			    <div class="b2b-detalle-imagen-principal">
			    	
			    	[#if !existe]
						<p>${i18n['cione-module.templates.components.detalle-producto-component.producto-no-disponible']}</p>
					[/#if]
					
					[#assign urlVideo = infoVariant.getUrlVideo()!]
			    	<iframe src="${urlVideo}" class="b2b-detalle-video" style="display:none" width="560" height="315" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
				    
				    [#assign assetDescuentoPack = damfn.getAsset("jcr", "cione/imagenes/promociones/ico-packs.png")!]
				    <div style="display: flex; align-items: flex-start;">
				    	[#if assetDescuentoPack?? && assetDescuentoPack?has_content]
					    	<img class="b2b-detalle-imagen-dto" src="${assetDescuentoPack.link}" alt="" />
					    [/#if]
				        <img  class="b2b-detalle-imagen-marca" src="${infoVariant.getLogoMarca()!}" alt="" />
			        </div>
			        
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
			</div>
			
		</div>
	    <div class="b2b-detail-card-wrapper ${cardwrapper!""}">
	        <section class="b2b-detail-compra">
	
			    <div class="b2b-detail-compra-product ${skuPackMaster!}">
			        <div class="product-info-wrapper">
			            		<div class="product-name">${infoVariant.getName()!}</div>
			            		[#if infoVariant.descripcion?? && infoVariant.descripcion?has_content]
			            			<div class="product-name">${infoVariant.getDescripcion()!}</div>
			            		[/#if]
			            
			            [#assign tipoPrecioPack = ""]
			            [#if infoVariant.infoPack?? && infoVariant.infoPack?has_content]
			            	[#assign tipoPrecioPack = infoVariant.infoPack.tipoPrecioPack!""]
			            [/#if]
			            [#if familiaProducto == "pack-generico"]
			            	
				            	<div class="product-price-wrapper" id="product-price">
						            [#if showPVO(ctx.getUser(), uuid, username)]
						                <div class="product-price-item">
						                    <div class="product-price-text-dto">${i18n['cione-module.templates.components.detalle-producto-component.pvo-en-pack']}</div>
						                    <div id="pvo-allpack-dto" class="product-price-number product-price-pvo-dto">[#if pvo?? && pvo?has_content]${pvo} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']} [#else] - ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']} [/#if]</div>
									    </div>
									    <div class="product-price-item">
						                    <div class="product-price-text product-price-strike">${i18n['cione-module.templates.components.detalle-producto-component.pvo-sin-pack']}</div>
						                    <div id="pvo-allpack" class="product-price-number product-price-strike">- ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}</div>
						                </div>
						                
						        	[/#if]
						        </div>
						        <div class="product-separator-solid"></div>
						        <div class="product-pack-description">
						        	[#if showPVO(ctx.getUser(), uuid, username)]
								        [#switch tipoPrecioPack]
											[#case "CERRADO"] [#-- deprecated --]
												<div class="box-pack">
													[#-- ${pvo} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']} --]
													${i18n['cione-module.templates.myshop.listado-productos-home-component.allpack']}: ${pvo} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']} 
												</div>
												[#break]
											[#case "GLOBAL"]
												<div class="box-pack">
													[#-- ${infoVariant.infoPack.descuentoGlobal} --]
													${i18n['cione-module.templates.myshop.listado-productos-home-component.pack-con']}: ${infoVariant.infoPack.descuentoGlobal} ${i18n['cione-module.templates.myshop.listado-productos-home-component.entodoslosproductos']}
												</div>
												[#break]
											[#case "INDIVIDUAL-CERRADO"] [#-- deprecated --]
												<div class="box-pack">
													[#-- ${pvo} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']} --]
													${i18n['cione-module.templates.myshop.listado-productos-home-component.allpack']}: ${pvo} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}
												</div>
												[#break]
											[#case "INDIVIDUAL"]
												<div class="box-pack">
													[#-- PONEMOS EL NOMBRE --]
													${infoVariant.getName()!}
												</div>
												[#break]
										[/#switch]
									[/#if]
									[#if !allProductPreConfigured]
										<button id="remove-pack-generico" class="b2b-button b2b-button-filter blue [#if !enabledRemovePackgenerico]bloqueado" disabled="disabled"[#else]habilitado"[/#if] type="button" onclick="removePackGenerico(); return false">
											${i18n['cione-module.templates.components.detalle-producto-component.delete-pack']}
										</button>
									[/#if]
								</div>
								
								<div class="product-separator-short"></div>
								<div id="infoPackNoConfigurado" class="product-price-text-infoPack" [#if completePack] style="display:none" [/#if] >
			                        <i class="fas fa-info-circle" style="padding-right: 2px;">
			                            <span style='font-family: "Lato", sans-serif;font-weight: 400;padding-left: 5px;'>${i18n['cione-module.templates.components.detalle-producto-component.pack-info-1']}</span>
			                        </i>
			                            <span style="display: inline-block; padding: 5px 22px 2px 22px; width: 95%;">${i18n['cione-module.templates.components.detalle-producto-component.pack-info-2']}</span>
			                            <span style="display: inline-block; padding: 5px 22px 2px 22px; width: 95%;" >${i18n['cione-module.templates.components.detalle-producto-component.pack-info-3']}</span>
			                    </div>
			                    <div id="infoPackConfigurado" class="product-price-text-infoPack" [#if !completePack] style="display:none" [/#if]>
			                    	<i class="fas fa-info-circle" style="padding-right: 2px;">
			                            <span style='font-family: "Lato", sans-serif;font-weight: 400;padding-left: 5px;'>${i18n['cione-module.templates.components.detalle-producto-component.pack-info-complete-1']}</span>
			                        </i>
			                            <span style="display: inline-block; padding: 5px 22px 2px 22px; width: 95%;">${i18n['cione-module.templates.components.detalle-producto-component.pack-info-complete-2']}</span>
			                    </div>
								<div class="product-separator-short"></div>
						        
					    [/#if]
				        
				        		
				        
		        		[@macropacksgenericos infoVariant/]
	            
	            		[@macrocomprar infoVariant/]
	            		
	            		
						    
			            
			        </div>
			    </div>
			</section>
		
		</div>
		
		
		[#assign closeimg = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/icons/close-thin.svg"]

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
										            	[#assign stockctral = model.getStock(contenido.sku).getStockCTRAL()!""]
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
		            
		            <button id="product-button-pack-generico" class="modal-purchase-button" type="button" 
						onclick="addToUserCartPackGenerico()">
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

[#macro macropacksgenericos infoVariant]
	[#if infoVariant.getContenidoPackListGenerico()?? && infoVariant.getContenidoPackListGenerico()?has_content]
		[#assign unidadesProductoPack = infoVariant.getContenidoPackListGenerico()?size]
		[#list infoVariant.contenidoPackListGenerico as productPack]
			
			[#if productPack.skuProductoPackPreconfigurado?? && productPack.skuProductoPackPreconfigurado?has_content]
			[#-- Producto pack preconfigurados con relacion 1-1 --]
			<div class="box-pack-product" >
				<div class="box-pack-shadow-img">
					<div class="tooltip-img"> <img src="${productPack.urlImagen?replace(".jpg", "-medium.jpg")}"> </div>
					<img class="img-base" src="${productPack.urlImagen!}" >
				</div>
				<div class="box-pack-shadow-separador"></div>
				<div class="box-pack-shadow-product">
					<div class="product-pack-title">
						<div class="tipoProducto-title">
			                <span id="circle-tipoProducto-title-change" class="circle-tipoProducto-title-change">${productPack?index + 1}</span>
			                <span class="title-color-pvoPack-fix">${productPack.nombreProductoConfigurado!}</span>
			            </div>
		                <span class="title-color-pvoPack-fix"> ${productPack.unidadesProductoPack!} [#if productPack.unidadesProductoPack == 1] ${i18n['cione-module.templates.components.detalle-producto-component.unidad']} [#else] ${i18n['cione-module.templates.components.detalle-producto-component.unidades']} [/#if]</span>
					</div>
					<div class="product-price-wrapper">
						[#if showPVO(ctx.getUser(), uuid, username)]
				            <div class="product-price-item">
				                <div class="product-price-text">${i18n['cione-module.templates.components.detalle-producto-component.pvo-sin-pack']}</div>
				                <div id="pvoOrigin" class="product-price-number price-color-pvo">[#if productPack.pvoOrigin?? && productPack.pvoOrigin?has_content]${productPack.pvoOrigin} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']} [/#if] </div>
				            </div>
				            <div class="product-price-item">
			                	[#if tipoPrecioPack != "CERRADO"]
			                    	<div class="product-price-text-dto">${i18n['cione-module.templates.components.detalle-producto-component.pvo-en-pack']}</div>
			                    	<div class="product-price-number product-price-pvo-dto">[#if productPack.pvoPack?? && productPack.pvoPack?has_content]${productPack.pvoPack} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']} [/#if]</div>
			                    [/#if]
						    </div>
						    [#if productPack.pvoAllLines?? && productPack.pvoAllLines?has_content]
							    <div class="product-price-item">
					                <div class="product-price-text-dto">${i18n['cione-module.templates.components.detalle-producto-component.total-pack']}</div>
					                <div id="pvoAllLines" class="product-price-number product-price-pvo-dto">${productPack.pvoAllLines} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']} </div>
					            </div>
				            [/#if]
				            
					    [/#if]
			            [#assign listadoProductosPreconfigurados = listadoProductosPreconfigurados + [productPack.skuProductoPackPreconfigurado!] ]
			            
			        </div>
			        <div class="product-separator"></div>
		        </div>
		    </div>
			[#elseif productPack.infoCustomLineItemsCioneLab?? && productPack.infoCustomLineItemsCioneLab?has_content]
				[#-- Producto pack monturas + lentes (mostramos aqui la parte de lentes) --]
				<div class="box-shadow-product">
				[#list productPack.infoCustomLineItemsCioneLab as productPackCioneLab]
					[#if productPackCioneLab?is_first]
						
					[#else]
						<div id="customLineItem">
					[/#if]
						<div class="product-pack-title">
							<div class="tipoProducto-title">
				                [#if productPackCioneLab?is_first] <span id="circle-tipoProducto-title-change-${productPack?index}" class="circle-tipoProducto-title-change">${productPack?index + 1}</span>[/#if]
				                <span id="nombreProducto-${productPack?index}" class="title-color-pvoPack">${productPackCioneLab.nombreProductoConfigurado!}</span>
				                <span id="tipoProducto-${productPack?index}" style="display:none">${productPackCioneLab.tipoProductoPack!}</span>
				            </div>
				            <span id="unidadesProducto-${productPack?index}" class="title-color-pvoPack">1 ${i18n['cione-module.templates.components.detalle-producto-component.unidad']}</span>
						</div>
						<div class="product-price-wrapper">
							[#if showPVO(ctx.getUser(), uuid, username)]
					            <div class="product-price-item">
					                <div class="product-price-text">${i18n['cione-module.templates.components.detalle-producto-component.pvo-sin-pack']}</div>
					                <div id="pvoOrigin-${productPack?index}" class="product-price-number price-color-pvo">[#if productPackCioneLab.pvoOrigin?? && productPackCioneLab.pvoOrigin?has_content]${productPackCioneLab.pvoOrigin} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']} [/#if] </div>
					            </div>
					            <div class="product-price-item">
			                    	<div id="texto-pvo-pack-${productPack?index}" class="product-price-text-dto">${i18n['cione-module.templates.components.detalle-producto-component.pvo-en-pack']}</div>
			                    	<div id="value-pvo-pack-${productPack?index}" class="product-price-number product-price-pvo-dto">[#if productPackCioneLab.pvoPack?? && productPackCioneLab.pvoPack?has_content]${productPackCioneLab.pvoPack} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']} [/#if]</div>
							    </div>
					            
						    [/#if]
						    
				        </div>
				        [#if productPackCioneLab?is_first]
				        	<div id="infocustomLineItemSeparador" class="product-separator" style="display:none"></div>
					        <div id="infocustomLineItem" class="product-price-wrapper" style="display:none" >
				        		<i class="fas fa-info-circle" style="padding-right: 2px;">
		                            <span style='font-family: "Lato", sans-serif;font-weight: 400;padding-left: 5px;'>${i18n['cione-module.templates.components.detalle-producto-component.validate-frame']}</span>
		                        </i>
				        	</div>
			        	[/#if]
			        [#if productPackCioneLab?is_first]
					[#else]
						</div>
					[/#if]
					
					<div id="customLineItem" class="product-separator"></div>
					
					[#if productPackCioneLab.refTaller?? && productPackCioneLab.refTaller??] 
						[#assign refTaller = productPackCioneLab.refTaller]
					[/#if]
				[/#list]
				</div>
			[#else]
				[#-- Producto pack con relacion n-n (por agrupadores) --]
				[#if productPack.configurado]
				<div class="box-pack-product" >
					<div id="box-pack-shadow-img-${productPack?index}" class="box-pack-shadow-img">
						<div class="tooltip-img"> <img src="${productPack.urlImagen?replace(".jpg", "-medium.jpg")}"> </div>
						<img class="img-base" src="${productPack.urlImagen!}" >
					</div>
					<div id="box-pack-shadow-separador-${productPack?index}" class="box-pack-shadow-separador"></div>
					<div class="box-pack-shadow-product">
				[#else]
					<div class="box-shadow-product">
				[/#if]
					[#if !productPack.configurado]
						<div class="product-pack-title">
				            <div class="tipoProducto-title">
				                <span id="circle-tipoProducto-title-${productPack?index}" class="circle-tipoProducto-title">${productPack?index + 1}</span>
				                <span id="tipoProducto-${productPack?index + 1}">${productPack.tipoProductoPack!}</span>
				            </div>
				                <span> ${productPack.unidadesProductoPack!} [#if productPack.unidadesProductoPack == 1] ${i18n['cione-module.templates.components.detalle-producto-component.unidad']} [#else] ${i18n['cione-module.templates.components.detalle-producto-component.unidades']} [/#if]</span>
				        </div>
					[#else]
						<div class="product-pack-title">
							<div class="tipoProducto-title">
				                <span id="circle-tipoProducto-title-change-${productPack?index}" class="circle-tipoProducto-title-change">${productPack?index + 1}</span>
				                <span id="nombreProducto-${productPack?index}" class="title-color-pvoPack">${productPack.nombreProductoConfigurado!}</span>
				                <span id="tipoProducto-${productPack?index}" style="display:none">${productPack.tipoProductoPack!}</span>
				            </div>
				            <span id="unidadesProducto-${productPack?index}" class="title-color-pvoPack"> ${productPack.unidadesProductoPack!} [#if productPack.unidadesProductoPack == 1] ${i18n['cione-module.templates.components.detalle-producto-component.unidad']} [#else] ${i18n['cione-module.templates.components.detalle-producto-component.unidades']} [/#if]</span>
						</div>
					[/#if]
					
					
				    <div class="product-price-wrapper">
				    	[#if showPVO(ctx.getUser(), uuid, username)]
							[#-- PVO sin Pack --]      
				            <div class="product-price-item">
			                    <div class="product-price-text">${i18n['cione-module.templates.components.detalle-producto-component.pvo-sin-pack']}</div>
			                    <div id="pvoOrigin-${productPack?index}" class="product-price-number price-color-pvo">[#if productPack.pvoOrigin?? && productPack.pvoOrigin?has_content]${productPack.pvoOrigin} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']} [#else] - ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']} [/#if]</div>
			                </div>
			                [#-- PVO en PACK --]  
			                [#if !productPack.configurado]
			                	<div class="product-price-item">
				                	[#if tipoPrecioPack != "CERRADO"]
				                    	<div class="product-price-text">${i18n['cione-module.templates.components.detalle-producto-component.pvo-en-pack']}</div>
				                    	<div class="product-price-number price-color-pvo">[#if productPack.pvoPack?? && productPack.pvoPack?has_content]${productPack.pvoPack} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']} [#else] - ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']} [/#if]</div>
				                    [/#if]
						    	</div>	
			                [#else]
			                	<div class="product-price-item">
				                	[#if tipoPrecioPack != "CERRADO"]
				                    	<div id="texto-pvo-pack-${productPack?index}" class="product-price-text-dto">${i18n['cione-module.templates.components.detalle-producto-component.pvo-en-pack']}</div>
				                    	<div id="value-pvo-pack-${productPack?index}" class="product-price-number product-price-pvo-dto">[#if productPack.pvoPack?? && productPack.pvoPack?has_content]${productPack.pvoPack} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']} [/#if]</div>
				                    [/#if]
							    </div>	
			                [/#if]
		                [/#if]
					    [#if productPack.configurado]
							<a href="#" class="" id="configurar-${productPack?index}" style="display:none;" [#if productPack.habilitar] onclick="configurarProducto('${productPack.agrupadores?join("&")}', ${productPack?index})" [/#if] >
			               		<span class="button-product-wrapper" [#if !productPack.habilitar] style="cursor: not-allowed ;background-color: #837f7f;" [/#if]>${i18n['cione-module.templates.components.detalle-producto-component.seleccionar-producto']}</span>  
			                </a>
			                <a href="#" class="" id="borrar-${productPack?index}" onclick="removeItemPack('${productPack.idCarritoOculto!}', '${productPack.lineItemIdOculto!}', '${productPack.skuProductoConfigurado!}', '${productPack.tipoProductoPack!}', '${productPack.agrupadores?join("&")}', '${productPack?index}', '${productPack.pvoPack!}', ${hasProductPackCioneLab?c})" >
			                    <span class="button-product-wrapper-change">${i18n['cione-module.templates.components.detalle-producto-component.cambiar-producto']}</span>  
			                </a>
							
							[#assign idCarritoOcultoMaster = productPack.idCarritoOculto!]
						[#else]
							[#if productPack.habilitar]
								[#if productPack.tipoProductoPackKey?? && productPack.tipoProductoPackKey?has_content && productPack.tipoProductoPackKey =="LENOF"]
									<a href="#" class="" id="configurar-customLineItem" [#if productPack.habilitar] onclick="configurarProducto('${productPack.agrupadores?join("&")}', ${productPack?index})" [/#if] >
					               		<span class="button-product-wrapper">${i18n['cione-module.templates.components.detalle-producto-component.seleccionar-producto']}</span>  
					                </a>
								[#else]
								<a href="#" class="" id="configurar-${productPack?index}" [#if productPack.habilitar] onclick="configurarProducto('${productPack.agrupadores?join("&")}', ${productPack?index})" [/#if] >
				               		<span class="button-product-wrapper">${i18n['cione-module.templates.components.detalle-producto-component.seleccionar-producto']}</span>  
				                </a>
				                [#-- <a href="#" class="" id="borrar-${productPack?index}" style="display:none;" onclick="removeItemPack('${productPack.idCarritoOculto!}', '${productPack.lineItemIdOculto!}', '${productPack.skuProductoConfigurado!}','${productPack.tipoProductoPack!}', '${productPack.agrupadores?join("&")}', '${productPack?index}', '${productPack.pvoPack!}')" >
				                    <span class="button-product-wrapper-change">${i18n['cione-module.templates.components.detalle-producto-component.cambiar-producto']}</span>  
				                </a> --]
				                [/#if]
				            [/#if]
						[/#if]
			        </div>
			        
			        [#if productPack.tipoProductoPackKey?? && productPack.tipoProductoPackKey?has_content && productPack.tipoProductoPackKey =="LENOF"]
			        	
				        <div id="infocustomLineItemSeparador" class="product-separator" [#if productPack.habilitar] style="display:none;" [/#if]></div>
				        	<div id="infocustomLineItem" class="product-price-wrapper" [#if productPack.habilitar] style="display:none;" [/#if] >
				        		<i class="fas fa-info-circle" style="padding-right: 2px;">
		                            <span style='font-family: "Lato", sans-serif;font-weight: 400;padding-left: 5px;'>${i18n['cione-module.templates.components.detalle-producto-component.validate-frame']}</span>
		                        </i>
				        	</div>
				        
			        [/#if]
			        
					[#-- <button id="button-envio-form-${productPack?index}" type="submit" onclick="submitFormulario(this, event, ${productPack?index}, '${productPack.agrupadores?join("&")}')">Submit</button> --] 
		        
		        	</div>
		        [#if productPack.configurado]
		        	</div>
		        [/#if]
	    	[/#if]
	    [/#list]
	[/#if]
	
	[#-- BEGIN: MODAL CONFIRMACION ELIMINAR PACK --]
    [#assign closeimg = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/icons/close-thin.svg"]
    <div id="modal-pack-removeItem" class="modal-purchase">
	    <div class="modal-purchase-box">
	        <div class="modal-purchase-header">
	            <p></p>
	            <div class="modal-purchase-close">
	                <img class="modal-purchase-close-img" src="${closeimg!""}" alt="cerrar" onclick='closeModalGenericForm("#modal-pack-removeItem");'>
	            </div>
	        </div>
	        
	        <div class="modal-purchase-info">
	            <div>
	                <p style="font-size: 16px;">${i18n['cione-module.templates.myshop.listado-productos-carrito-component.modal.delete-items-pack-removeItem-confirmation']}</p>
	            </div>
	        </div>
	        
	        <div class="modal-purchase-footer">
	            <button class="modal-purchase-button modal-purchase-button--transparent" type="button" onclick='closeModalGenericForm("#modal-pack-removeItem")'>
	                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}
	            </button>
	            <button id="modal-pack-removeItem-button-confirm" class="modal-purchase-button" type="button">
	                ${i18n['cione-module.templates.myshop.listado-productos-carrito-component.modal.confirmar']?upper_case}
	            </button>
	        </div>
	    </div>
	</div>
    [#-- END: MODAL CONFIRMACION ELIMINAR PACK --]
	
[/#macro]

[#macro macrocomprar infoVariant]	

	<div id="error-pack" class="col-6" style="color: red; display:none">
	    ${i18n['cione-module.templates.components.detalle-producto-component.missing-units']}
	</div>
    <div class="product-button-wrapper">
    	[#if !existe]
    		<button class="product-button" style="cursor: not-allowed;" disabled="disabled" type="button" onclick="addtoCart(); return false">
               ${i18n['cione-module.templates.components.detalle-producto-component.add-cart']}
            </button>
        [#else]
        	[#assign permitecompra = true]
        	[#assign pvoFinal = 0.0]
        	[#assign pvoPackFinal = 0.0]
        	[#if infoVariant.getContenidoPackListGenerico()?? && infoVariant.getContenidoPackListGenerico()?has_content]
				[#list infoVariant.contenidoPackListGenerico as productPack]
					[#assign unidadesProductoPack = 1]
					[#if productPack.pvoOrigin?? && productPack.pvoOrigin?has_content]
						[#assign unidadesProductoPack = productPack.unidadesProductoPack]
					[/#if]	
					
					[#if productPack.pvoOrigin?? && productPack.pvoOrigin?has_content]
						[#assign precioOrigin = productPack.pvoOrigin?number * unidadesProductoPack]
						[#assign pvoFinal = pvoFinal + precioOrigin]
					[/#if]	
					
					[#if productPack.pvoPack?? && productPack.pvoPack?has_content && productPack.pvoPack?? && productPack.pvoPack?has_content]
						[#assign precioPack = productPack.pvoPack?number * unidadesProductoPack]
						[#assign pvoPackFinal = pvoPackFinal + precioPack]
					[/#if]
					
					
					[#if !productPack.configurado]
						[#assign permitecompra = false]
					[/#if]
				[/#list]
			[/#if]
			[#if permitecompra]
				<script> 
					$("#pvo-allpack").text("${pvoFinal?string("0.00")} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}");
					$("#pvo-allpack-dto").text("${pvoPackFinal?string("0.00")} ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}");  
				</script>
			[/#if]
        	<button id="product-button-pack-generico" class="product-button [#if !permitecompra]disabled-cart" disabled="disabled"[#else]"[/#if] type="button" onclick="addToUserCartPackGenerico(); return false">
				${i18n['cione-module.templates.components.detalle-producto-component.add-cart']}
			</button>
    	[/#if]
    	
    	
            
    </div>
    [#-- Referencia cliente --]
    <div class="product-reference">
        <label>${i18n['cione-module.templates.components.detalle-producto-component.ref-cliente']}</label>
        <input type="text" name="refCliente">
    </div>
	    
				        
[/#macro]
<script>

$(document).ready(function(){

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

});


	function submitFormulario(boton, event, pos, agrupadores) {
		[#assign link = cmsfn.link("website", content.internalLinkPacks!)!]
		event.preventDefault();
		
		const form = document.getElementById('formDetalleProducto');
		
		const params = agrupadores.split('&');
		params.forEach(param => {
	        // Separar clave y valor
	        const [key, value] = param.split('=');
	
	        // Crear un nuevo campo input
	        const input = document.createElement('input');
	        input.type = 'hidden'; // Campo oculto
	        input.name = key;      // Asignar el nombre (clave)
	        input.value = value;   // Asignar el valor
	
	        // Agregar el campo al formulario
	        form.appendChild(input);
	    });
		
		$("#formDetalleProducto input[name=agrupadores]").val(agrupadores); 
		
		form.action = "${link}";
		
		form.submit();
	
	}


    
	function configurarProducto(agrupadores, step) {
		[#assign link = cmsfn.link("website", content.internalLinkPacks!)!]
		[#assign linkNoFilters = cmsfn.link("website", content.internalLinkPacksNofilters!)!]
		[#assign linkCioneLab = cmsfn.link("website", content.internalLinkCioneLab!)!]
		
		var link ="${link}" + "?skuPackMaster=${skuPackMaster}";
		if (agrupadores.includes('variants.sku')) {
			//vamos a la pagina de listado sin filtros
			[#if linkNoFilters?? && linkNoFilters?has_content]
				link ="${linkNoFilters}" + "?skuPackMaster=${skuPackMaster}";
				link += "&" + agrupadores + "&step=" + step;
			[#else]
				link ="#";
			[/#if]
		} else if(agrupadores.includes('variants.attributes')) {
			link += "&" + agrupadores + "&step=" + step;
		} else if(agrupadores.includes('pack=')) {
			//vamos a la pantalla de CioneLab
			[#if linkCioneLab?? && linkCioneLab?has_content]
				link ="${linkCioneLab}" + "?" + agrupadores + "&skuPackMaster=${skuPackMaster}";
			[#else]
				link ="#";
			[/#if]
		}
		//le pasamos el listado de agrupadores
		//link += "&" + agrupadores + "&mandatory=true";
		
		
		window.location.href = link;
	}
	
	function removeItemPackModal() {
		
		var idCarritoOculto = '${idCarritoOcultoMaster}';
		var lineItemIdOculto = $('#lineItemIdOcultoHidden').val();
		var skuProductoConfigurado = $('#skuProductoConfigurado').val();
		var tipoProducto = $('#tipoProducto').val();
		var agrupadores = $('#agrupadores').val();
		var index = $('#index').val();
		var pvoPack = $('#pvoPack').val();
		
		removeItemPack(idCarritoOculto, lineItemIdOculto, skuProductoConfigurado, tipoProducto, agrupadores, index, pvoPack, true);
	}
	
	function removeItemPack(idCarritoOculto, lineItemIdOculto, skuProductoConfigurado, tipoProducto, agrupadores, index, pvoPack, checkLENOF) {
		if (!checkLENOF) {
			$("#borrar-" + index).addClass("disabled-link");
			$("#borrar-" + index).css("pointer-events", "none");
		
			[#if (tipoPrecioPack != "CERRADO") && isPackWithFilters]
	    		$("#pvo-allpack-dto").text("- ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}");
	    	[/#if]
	    	
	    	[#if isPackWithFilters]
	    		$("#pvo-allpack").text("- ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}");
	    	[/#if]
	    	
	    	$("#circle-tipoProducto-title-change-" + index).toggleClass("circle-tipoProducto-title-change circle-tipoProducto-title");
	    	$("#nombreProducto-" + index).removeClass("title-color-pvoPack");
	    	$("#unidadesProducto-" + index).removeClass("title-color-pvoPack");
	    	$("#infoPackConfigurado").hide();
	    	$("#infoPackNoConfigurado").show();
	    	
	    	
			$("#nombreProducto-" + index).hide();
			$("#tipoProducto-" + index).show();
			$("#pvoOrigin-" + index).text("- ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}");
			
			$("#texto-pvo-pack-" + index).toggleClass("product-price-text-dto product-price-text");
			$("#value-pvo-pack-" + index).toggleClass("product-price-pvo-dto product-price-pvo");
			$("#value-pvo-pack-" + index).toggleClass("price-color-pvo product-price-pvo");
			[#if (tipoPrecioPack != "INDIVIDUAL-CERRADO")] [#-- deprecated --]
				[#if (tipoPrecioPack == "INDIVIDUAL")]
					$("#value-pvo-pack-" + index).text("- ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}");
				[#else]
					$("#value-pvo-pack-" + index).text("- ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}");
				[/#if]
			[/#if]
			$("#configurar-" + index).show();
			$("#borrar-" + index).hide();
		
		
			var definitionName = $("#formDetalleProducto input[name=definitionName]").val();
			var connectionName = $("#formDetalleProducto input[name=connectionName]").val();
		
	    	var filter = JSON.stringify({
	        	"skuPackMaster": '${skuPackMaster!}',
	        	"idCarritoOculto": idCarritoOculto,
	        	"lineItemIdOculto": lineItemIdOculto,
	        	"pvoPack": pvoPack,
	        	"tipoProducto": tipoProducto,
	        	"userId": '${userId}',
	        	"skuProductoConfigurado": skuProductoConfigurado,
	        	"step": index,
	        	"definitionName": definitionName,
	        	"connectionName": connectionName
	    	});
			
			$.ajax({
	            url : "${ctx.contextPath}/.rest/private/carrito/v1/carts-deleteProductPack",
	            type : "POST",
	            data : filter,
	            contentType : 'application/json; charset=utf-8',
	            cache : false,
	            dataType : "json",
	            success : function(response) {
	            	console.log("producto añadido al pack");
					configurarProducto(agrupadores, index);
					
	            },
	            error : function(response) {
	                alert("error");             
	                //$("#change-pwd-result").html("<p>" +  response.responseJSON.error.message + "</p>");
	            },
	            complete : function(response) {
	            	$("#borrar-" + index).removeClass("disabled-link");
	            	$("#borrar-" + index).css("pointer-events", "auto");
	            }
			});		
		} else {
			$('#modal-pack-removeItem').css('display','flex');
    		$('#modal-pack-removeItem').css('visibility','visible');
    		
    		$("#modal-pack-removeItem-button-confirm").data("params", {
	            idCarritoOculto,
	            lineItemIdOculto,
	            skuProductoConfigurado,
	            tipoProducto,
	            agrupadores,
	            index,
	            pvoPack
	        });
		}
	}
	
	$("#modal-pack-removeItem-button-confirm").click(function() {
	    let params = $(this).data("params"); // Recuperar los parámetros
	    if (params) {
	        removeItemPack(params.idCarritoOculto, params.lineItemIdOculto, params.skuProductoConfigurado, params.tipoProducto, params.agrupadores, params.index, params.pvoPack, false);
	    }
	    $("#confirmModal").hide(); // Ocultar la modal después de la acción
	});
	
	function removePackGenerico() {
		$("#remove-pack-generico").attr("disabled", "disabled");
		$("#remove-pack-generico").addClass("bloqueado");
		$("#remove-pack-generico").removeClass("habilitado");
		
		var definitionName = $("#formDetalleProducto input[name=definitionName]").val();
		var connectionName = $("#formDetalleProducto input[name=connectionName]").val();
	
    	var filter = JSON.stringify({
        	"skuPackMaster": '${skuPackMaster!}',
        	"idCarritoOculto": '${idCarritoOcultoMaster}',
        	"userId": '${userId}',
        	"definitionName": definitionName,
        	"connectionName": connectionName
    	});
		
		$.ajax({
            url : "${ctx.contextPath}/.rest/private/carrito/v1/carts-deleteAllPack",
            type : "POST",
            data : filter,
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            success : function(response) {
            	[#if infoVariant.getContenidoPackListGenerico()?? && infoVariant.getContenidoPackListGenerico()?has_content]
	            	var listProductsPackSize = ${infoVariant.getContenidoPackListGenerico()?size};
	            	
            		[#if (tipoPrecioPack != "CERRADO") && isPackWithFilters]
	            		$("#pvo-allpack-dto").text("- ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}");
	            	[/#if]
	            	
	            	[#if isPackWithFilters]
	            		$("#pvo-allpack").text("- ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}");
	            	[/#if]
	            	
	            	$("#infoPackConfigurado").hide();
	            	$("#infoPackNoConfigurado").show();
	            	
	            	$('div#customLineItem').remove();
	            	$("#infocustomLineItemSeparador").show();
	            	$("#infocustomLineItem").show();
	            	
	            	$("#configurar-customLineItem").hide();
	            	
	            	[#list infoVariant.getContenidoPackListGenerico() as producto]
	            		[#if !producto.skuProductoPackPreconfigurado??]
		            		$("#circle-tipoProducto-title-change-" + ${producto?index}).toggleClass("circle-tipoProducto-title-change circle-tipoProducto-title");
		            		
		            		$("#nombreProducto-" + ${producto?index}).removeClass("title-color-pvoPack");
	    					$("#unidadesProducto-" + ${producto?index}).removeClass("title-color-pvoPack");
		            	
		            		[#assign isPVOCERRADO = (producto.getTipoPrecioVariante()?? && producto.getTipoPrecioVariante()?string == "PVO-CERRADO")]
		            		$("#nombreProducto-" + ${producto?index}).hide();
		            		$("#tipoProducto-" + ${producto?index}).show();
		            		$("#pvoOrigin-" + ${producto?index}).text("- ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}");
		            		
		            		$("#texto-pvo-pack-" + ${producto?index}).toggleClass("product-price-text-dto product-price-text");
		            		$("#value-pvo-pack-" + ${producto?index}).toggleClass("product-price-pvo-dto product-price-pvo");
		            		$("#value-pvo-pack-" + ${producto?index}).toggleClass("price-color-pvo product-price-pvo");
		            		[#if (tipoPrecioPack != "INDIVIDUAL-CERRADO")] [#-- deprecated --]
		            			[#if (tipoPrecioPack == "INDIVIDUAL")]
		            				[#if !isPVOCERRADO]
									   $("#value-pvo-pack-" + ${producto?index}).text("- ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}");
									[/#if]
		            			[#else]
		            				$("#value-pvo-pack-" + ${producto?index}).text("- ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}");
		            			[/#if]
		            		[/#if]
		            		$("#configurar-" + ${producto?index}).show();
		            		$("#borrar-" + ${producto?index}).hide();
		            		$("#box-pack-shadow-img-" + ${producto?index}).hide();
		            		$("#box-pack-shadow-separador-" + ${producto?index}).hide();
	            		[/#if]
	            	
	            	[/#list]
	            [/#if]
            	
            },
            error : function(response) {
                alert("Se ha producido un error al vaciar el pack");             
                //$("#change-pwd-result").html("<p>" +  response.responseJSON.error.message + "</p>");
            },
            complete : function(response) {
            	
            }
		});
		
	}
        
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
	
	
	function addToUserCartPackGenerico() {
		$("#product-button-pack-generico").attr("disabled", "disabled");
		
		var listadoProductosPreconfigurados = '${listadoProductosPreconfigurados?join("&")}';
		
		var refCliente = $("input[name='refCliente']").val();
		var refPackValue = $("input[name='refPack']").val();
	    var filter = JSON.stringify({
        	"skuPackMaster": '${skuPackMaster!}',
        	"refPackPromos": refPackValue,
        	"refCliente" : refCliente,
        	"listadoProductosPreconfigurados" : listadoProductosPreconfigurados,
        	"refTaller" : '${refTaller!}'
    	});
    	
    	$("#loading").show();
	
		$.ajax({
            url : "${ctx.contextPath}/.rest/private/carrito/v1/carts-addtoUserCartPack",
            type : "POST",
            data : filter,
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            success : function(response) {
            	console.log("producto añadido al pack");
            	refrescarPopupCarrito(response);
            	//pushTemplateModalPack();
            	$("#fly-card").css("display","block");
				setTimeout(function(){ 
					$("#fly-card").css("display","none");
				}, 5000);
				
				$('div#customLineItem').remove();
            	$("#infocustomLineItemSeparador").show();
            	$("#infocustomLineItem").show();
				
            	[#if infoVariant.getContenidoPackListGenerico()?? && infoVariant.getContenidoPackListGenerico()?has_content]
	            	var listProductsPackSize = ${infoVariant.getContenidoPackListGenerico()?size};
	            	var isPackWithFilters=true;
	            	
	            	[#--  --if isPackWithFilters]
	            		$("#pvo-allpack-dto").text("");
	            		$("#pvo-allpack").text("");
	            	[/#if--]
	            	
	            	
	            	
	            	
	            	[#list infoVariant.getContenidoPackListGenerico() as producto]
	            	
	            		[#if producto.skuProductoPackPreconfigurado?? && producto.skuProductoPackPreconfigurado?has_content]
	            			console.log('${producto.skuProductoPackPreconfigurado}');
	            		[#else]
	            			isPackWithFilters=false;
	            			$("#circle-tipoProducto-title-change-" + ${producto?index}).toggleClass("circle-tipoProducto-title-change circle-tipoProducto-title");
	            			
	            			$("#nombreProducto-" + ${producto?index}).removeClass("title-color-pvoPack");
    						$("#unidadesProducto-" + ${producto?index}).removeClass("title-color-pvoPack");
	            			
	            			
		            		[#assign isPVOCERRADO = (producto.getTipoPrecioVariante()?? && producto.getTipoPrecioVariante()?string == "PVO-CERRADO")]
		            		$("#nombreProducto-" + ${producto?index}).hide();
		            		$("#tipoProducto-" + ${producto?index}).show();
		            		$("#pvoOrigin-" + ${producto?index}).text("- ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}");
		            		
		            		$("#texto-pvo-pack-" + ${producto?index}).toggleClass("product-price-text-dto product-price-text");
		            		$("#value-pvo-pack-" + ${producto?index}).toggleClass("product-price-pvo-dto product-price-pvo");
		            		$("#value-pvo-pack-" + ${producto?index}).toggleClass("price-color-pvo product-price-pvo");
		            		[#if (tipoPrecioPack != "INDIVIDUAL-CERRADO")] [#-- deprecated --]
		            			[#if (tipoPrecioPack == "INDIVIDUAL")]
		            				[#if !isPVOCERRADO]
									   $("#value-pvo-pack-" + ${producto?index}).text("- ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}");
									[/#if]
		            			[#else]
		            				$("#value-pvo-pack-" + ${producto?index}).text("- ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}");
		            			[/#if]
		            		[/#if]
		            		$("#configurar-" + ${producto?index}).show();
		            		$("#borrar-" + ${producto?index}).hide();
	            		[/#if]
	            	[/#list]
	            	if (!isPackWithFilters){
		            	$("#pvo-allpack-dto").text("- ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}");
		            	$("#pvo-allpack").text("- ${i18n['cione-module.templates.myshop.listado-productos-home-component.euro']}");
		            	
		            	$("#infoPackConfigurado").hide();
		            	$("#infoPackNoConfigurado").show();
		            	
		            	//$(".product-price-strike").text("");
		            	$("#product-button-pack-generico").addClass("disabled-cart");
		            	
		            	$("#remove-pack-generico").attr("disabled", "disabled");
		            	$("#remove-pack-generico").removeClass("habilitado");
						$("#remove-pack-generico").addClass("bloqueado");
	            	}
	            	
	            [/#if]
	            
	            [#if !isPackWithFilters]
	            	[#assign refPack = model.getRefTaller()]
	            	$("input[name='refPack']").val("${refPack}");
					$("#product-button-pack-generico").removeAttr("disabled");
				[/#if]
	            
            },
            error : function(response) {
                alert("error");             
                //$("#change-pwd-result").html("<p>" +  response.responseJSON.error.message + "</p>");
                $("#product-button-pack-generico").removeAttr("disabled");
            },
            complete : function(response) {
                $("#loading").hide();
                
            }
		});
		
	}
    
	function validateForm(oForm) {	
	
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

	
	function closeModalPacks(that) {
		$("#modal-packs-detail").css("display","none");
	}

</script>


</form>
</div>
