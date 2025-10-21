[#if content.applymain?? && content.applymain]
<div class="b2b-main" role="main">
[/#if]

<section class="b2b-carrusel-home">
	
	[#if cmsfn.children(content)?has_content]
    <div class="owl-carousel owl-theme owl-carousel-banner">
    	
		[#list cmsfn.children(content) as image]
    		[#if image.image?has_content || image.video?has_content]
    			[#assign hasPermissions = false]
    			[#if image.permisos?? && image.permisos?has_content]
    				[#assign field = image.permisos.field!]
    				[#switch field]
			
						[#case "roles"]
							[#if model.hasPermissionsRoles(image.permisos.roles!)]
								[#assign hasPermissions = true]
							[/#if]
							[#break]
							
						
						[#case "campaing"]
							[#if model.hasPermissionsCampana(image.permisos.campaing!)]
								[#assign hasPermissions = true]
							[/#if]
							[#break]
							
						[#default]
		    				[#break]
		    		[/#switch]
		    	[#elseif model.hasPermissionsCampana(image.campanas!)]
		    		[#assign hasPermissions = true]
    			[/#if]
    			
    			
    			[#if hasPermissions]
		    		[#assign fInicio = (image.fechaInicio?date?string["dd/MM/yyyy HH:mm:ss"])!"null"]
					[#assign fFin = (image.fechaFin?date?string["dd/MM/yyyy HH:mm:ss"])!"null"]			
								
					[#if model.checkFechaInicio(fInicio) && model.checkFechaFin(fFin)]
						[#if image.image?has_content]
							[#assign link = damfn.getAssetLink("jcr:" + image.image)!""]
							[#assign document = ""]
							
							
					    	[#if image.document?? && image.document?has_content]
					    		[#assign iamgenfield = image.document.field!]
					    		[#assign iamgendam = image.document.magnolia!]
					    		[#if iamgenfield?? && iamgenfield?has_content]
						    		[#if iamgenfield == 'magnolia' && iamgendam?has_content]
						    			[#assign documentAux = cmsfn.contentById(iamgendam , "dam")!]
						    			[#if documentAux?? && documentAux?has_content]
						    				[#assign document = cmsfn.link(cmsfn.contentById(iamgendam , "dam"))]
						    			[/#if]
						    		[#elseif iamgenfield == 'external' && image.document.external?has_content]
						    			[#assign document = image.document.external]
						    		[/#if]
						    	[/#if]
					    	[/#if]
				    	[/#if]
				        <div class="item">
				        	[#if image.video?has_content]
			        			<img class="img-video-carousel" src="${link!"#"}" alt="${image.alternativeText!""}"/>
			        			<iframe width="560" height="315" src="${image.video!}"
				                title="YouTube video player" frameborder="0"
				                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
				                allowfullscreen></iframe>
							 	<img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/iconos/playButton.svg" class="playButton" />
				        	[#else]
					            [#if document?has_content]
					            	<a href="${document!""}" target="_blank">
					            		<img src="${link!"#"}" alt="${image.alternativeText!""}" />
					            	</a>
					            [#else]
					            	<img src="${link!"#"}" alt="${image.alternativeText!""}" />
					            [/#if]
				            [/#if]
				        </div>		    
				    [/#if]
				[/#if]
			[/#if]
        [/#list]
    </div>
    [/#if]
    
</section>
[#if content.applymain?? && content.applymain]
</div>
[/#if]
<script>

    $('.owl-carousel').owlCarousel({
        loop: true,
        items: 1,
        margin: 0,
        autoHeight: true,
        autoplay: true,
        autoplayHoverPause: true,
        onTranslate: function () {
            const allIframes = document.querySelectorAll('.owl-carousel iframe');
            const allPlayButtons = document.querySelectorAll('.owl-carousel .playButton');

            if (allIframes.length > 0) {
                allIframes.forEach((iframe) => {
                    iframe.classList.remove('show');
                    let src = iframe.src;
					if (src.includes("youtube")) {
                    	iframe.src = iframe.src.replace("&autoplay=1", "");
                    } else {
                    	iframe.src = iframe.src.replace("?autoplay=1", "");
                    }
                });
            }

            if (allPlayButtons.length > 0) {
                allPlayButtons.forEach((button) => {
                    button.classList.remove('hide');
                });
            }
        }
    });

    const playButtons = document.querySelectorAll('.playButton');

    if (playButtons.length > 0) {
        playButtons.forEach((button) => {
            button.addEventListener('click', () => {
                const iframe = button.parentElement.querySelector('iframe');
                iframe.classList.add('show');
                button.classList.add('hide');
				let src = iframe.src;
				if (src.includes("youtube")) {
                	iframe.src += "&autoplay=1";
                } else {
                	iframe.src += "?autoplay=1";
                }
            });
        });
    }
</script>
