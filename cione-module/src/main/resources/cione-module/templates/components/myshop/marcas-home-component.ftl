<!-- MARCAS -->
<section class="b2b-collection">
    <div class="b2b-collection-header">
        <h2 class="b2b-collection-title">${i18n['cione-module.templates.myshop.marcas-component.marcas']}</h2>
		[#if content.todasMarcasLink?has_content]
			[#assign linkMarcas = cmsfn.link("website", content.todasMarcasLink!)!]
		[#else]
			[#assign linkMarcas = "#"]
		[/#if]
        <a class="b2b-collection-link" href="${linkMarcas}">${i18n['cione-module.templates.myshop.marcas-home-component.verTodas']}</a>
    </div>

    <div class="b2b-collection-wrapper">
    	[#assign relacionada = content.categoryRelated!]
    	[#assign limit = content.maxListCategories!]
    	[#assign marcas = model.getListMarcasRelMonturas(relacionada, limit)]
    	[#if cmsfn.language() == 'pt']
        	[#assign defaultImage = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/common/oops-PT.jpg"]
        [#else]
        	[#assign defaultImage = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/common/oops.jpg"]
        [/#if]
    	
    	[#if marcas?has_content && marcas??]
    		[#if content.listadoLink?has_content]
    			[#assign link = cmsfn.link("website", content.listadoLink!)!]
    		[/#if]
    		[#assign atributo = '']
	    		[#list content.attribute! as sValue]
	    			[#assign sValue = model.encodeURIComponent(sValue)]
					[#assign atributo = atributo +'&'+sValue]
				[/#list] 
    	
    		[#if marcas?size <= 8]    		
	    		[#list marcas?chunk(4) as fila]
	    			<div class="b2b-collection-row">
	    				[#list fila as marca]
	    					[#if link?has_content]
				    			[#assign linkAux = link + "?category=" + marca.getId() + atributo]
				    		[#else]
				    			[#assign linkAux = "#"]
				    		[/#if]
	    					<div class="b2b-collection-item">
					            <a href="${linkAux}"><img src="${marca.getLogoListado()}" onerror="this.onerror=null; this.src='${defaultImage}'" alt=""></a>
							</div>
	    				[/#list]
	    			</div>
	    		[/#list]
	    	[#else]
	    		<div class="b2b-collection-row">
		    		[#list marcas[0..3] as marca]
						[#if link?has_content]
			    			[#assign linkAux = link + "?category=" + marca.getId() + atributo]
			    		[#else]
			    			[#assign linkAux = "#"]
			    		[/#if]
						<div class="b2b-collection-item">
				            <a href="${linkAux}"><img src="${marca.getLogoListado()}" onerror="this.onerror=null; this.src='${defaultImage}'" alt="imagen-marca"></a>
						</div>
					[/#list]
				</div>
				
				<div class="b2b-collection-row">
					[#list marcas[4..7] as marca]
						[#if link?has_content]
			    			[#assign linkAux = link + "?category=" + marca.getId() + atributo]
			    		[#else]
			    			[#assign linkAux = "#"]
			    		[/#if]
						<div class="b2b-collection-item">
				            <a href="${linkAux}"><img src="${marca.getLogoListado()}" onerror="this.onerror=null; this.src='${defaultImage}'" alt="imagen-marca"></a>
						</div>
					[/#list]
				</div>
	    	[/#if]
    	[#else]
    		<div class="b2b-collection-row">
				<div class="b2b-collection-item">
		            No se encuentran marcas
				</div>
			</div>
    	[/#if]

    </div>
</section>