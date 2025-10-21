
[#assign carpetaCarrusel = cmsfn.contentById(content.carpetaCarrusel, "promociones")]
[#assign carpetaCarruselNode = cmsfn.asJCRNode(carpetaCarrusel)]
[#assign promocionesCarrusel = model.childrenRecursive(carpetaCarruselNode,"promocione")]


<section class="cmp-promocion">
	[#assign color = "#00609c"]
	[#if content.colorTitle?? && content.colorTitle?has_content]
		[#assign color = content.colorTitle]
	[/#if]
	[#if content.title?? && content.title?has_content]
    	<h2 class="title" style="color:${color}">${content.title}</h2>
    [#else]
    	<h2 class="title" style="color:${color}">${i18n['cione-module.templates.components.promociones.promociones']}</h2>
    [/#if]
    <div class="wrapper-box">
    	[#assign hayPromos = false]
    	<!-- carousel -->
    	
	      
        <div class="box carousel">
            <div class="owl-carousel owl-promociones owl-theme">  
				[#list promocionesCarrusel as promoDestacada]		
					[#assign fInicio = (promoDestacada.fechaInicio?date?string["dd/MM/yyyy HH:mm:ss"])!"null"]
					[#assign fFin = (promoDestacada.fechaFin?date?string["dd/MM/yyyy HH:mm:ss"])!"null"]			
							
					[#if model.hasPermissions(promoDestacada.roles) && model.checkFechaInicio(fInicio) && model.checkFechaFin(fFin)]
						[#assign hayPromos = true]
						[#assign link = '#']
						[#if promoDestacada.externalLink?has_content]
							[#assign link = promoDestacada.externalLink]	
						[/#if]
						[#if promoDestacada.documento?? && promoDestacada.documento?has_content]
							[#assign link = cmsfn.link(cmsfn.contentById(promoDestacada.documento, "dam"))!""]
						[/#if]
						
						[#if promoDestacada.imagen?has_content]
							[#assign asset = damfn.getAsset(promoDestacada.imagen)!]
							[#if asset?has_content && asset.link?has_content]    								
								<div class="item">
                    				<div class="content-img">
                    					<a href=${link} target="_blank">
                    						[#if cmsfn.editMode]						
												<div style='text-align: right;padding:2px;'>${model.getRolesAsString(promoDestacada.roles)} [${promoDestacada.position!}]</div>
											[/#if]						
				     						<img src="${asset.link}" alt="promocion" class="promocion-desktop">
			     							[#if promoDestacada.imagenMovil?has_content]
												[#assign asset = damfn.getAsset(promoDestacada.imagenMovil)!]
												[#if asset?has_content && asset.link?has_content]    
													<img src="${asset.link}" alt="promocion" class="promocion-mobile">		     					
												[/#if]
											[#else]												
												<img src="${damfn.getAsset(promoDestacada.imagen).link!}" alt="promocion" class="promocion-mobile">															        							        	
											[/#if]		
				     					</a>	
				     				</div>
				     			</div>		
							[/#if]	        							        		
						[/#if]
					[/#if]		
				[/#list]
            </div>
        </div>
       	
        <!-- promociones -->

        [#if content.carpetaListado?? && content.carpetaListado?has_content]
			[#assign carpetaListado = cmsfn.contentById(content.carpetaListado, "promociones")]
			[#assign carpetaListadoNode = cmsfn.asJCRNode(carpetaListado)]
			[#assign promocionesListado = model.childrenRecursive(carpetaListadoNode,"promocione")]
			[#list promocionesListado as promo]
				[#assign fInicio = (promo.fechaInicio?date?string["dd/MM/yyyy HH:mm:ss"])!"null"]
				[#assign fFin = (promo.fechaFin?date?string["dd/MM/yyyy HH:mm:ss"])!"null"]			
			
				[#if model.hasPermissions(promo.roles) && model.checkFechaInicio(fInicio) && model.checkFechaFin(fFin)]
					[#assign hayPromos = true]
					[#assign link = '#']
					[#if promo.externalLink?has_content]
						[#assign link = promo.externalLink]	
					[/#if]
					[#if promo.documento?? && promo.documento?has_content]
						[#assign link = cmsfn.link(cmsfn.contentById(promo.documento, "dam"))]
					[/#if]
					
					<div class="box ${promo.tamano!}">
						[#if cmsfn.editMode]						
							<div style='text-align: right;'>${model.getRolesAsString(promo.roles)} [${promo.position!}]</div>
						[/#if]					
		            	<div class="content-img">                	
		                	[#if promo.imagen?has_content]
								[#assign asset = damfn.getAsset(promo.imagen)!]
								[#if asset?has_content && asset.link?has_content]        					
			     					<!-- <img src="${asset.link}" alt="promocion" class="promocion-desktop">-->
			     					<div style="background-image: url('${asset.link}');" class="promocion-desktop"></div>
								[/#if]	        							        		
							[/#if]
							[#if promo.imagenMovil?has_content]
								[#assign asset = damfn.getAsset(promo.imagenMovil)!]
								[#if asset?has_content && asset.link?has_content]        					
			     					<!-- <img src="${asset.link}" alt="promocion" class="promocion-mobile">-->
			     					<div style="background-image: url('${asset.link}');" class="promocion-mobile"></div>
								[/#if]
							[#else]							
								<!-- <img src="${damfn.getAsset(promo.imagen).link!}" alt="promocion" class="promocion-mobile">-->
								<div style="background-image: url('${asset.link}');" class="promocion-mobile"></div>									        							        		
							[/#if]		
		            	</div>
		            	<a href=${link}>
		            		<div class="content-text">	            		
		            			<span>${promo.title!}</span>
		            			[#if promo.documento?? && promo.documento?has_content]
		            				<span class="icon documento"></span>
		            			[/#if]		            		
		            		</div>
		            	</a>
		        	</div>			
		        	
		        	
				[/#if]		
			[/#list]
		[/#if]
    </div>
    
    [#if !hayPromos]
		<div>	
			${i18n['cione-module.templates.components.promociones.empty']}
		</div>		
	[/#if]

</section>


<script>
	function initPage(){
		$('.owl-carousel.owl-promociones').owlCarousel({
			loop:true,
			margin:10,
			nav:false,
			dots: true,
			items: 1,			
			autoplay:true, 
			autoplayTimeout:5000, 
			autoplayHoverPause:true,
			responsive:{
        		0:{
          			dots: true,
            		nav:true
        		},
        		992:{
           			dots: true,
            		nav:false
        		}
		    }
		});	
	} 	
	 
</script>
