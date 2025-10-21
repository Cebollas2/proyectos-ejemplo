[#assign catalogosRoot = cmsfn.contentByPath("/", "catalogos")]
[#assign catalogosRootNode = cmsfn.asJCRNode(catalogosRoot)]
[#assign catalogos = model.childrenRecursive(catalogosRootNode,"catalogo")]
[#assign hayCatalogos = false]

<section class="cmp-catalogos">
    <h2 class="title">${i18n['cione-module.templates.components.catalogos.catalogos']}</h2>
    <div class="wrapper-box">
		[#list catalogos as catalogo]			
			[#if model.hasPermissions(catalogo.roles)]
				[#assign hayCatalogos = true]	
				[#assign link = '#']
				[#assign pdf = false]
				[#assign documentAsset = ""]
				[#if catalogo.externalLink?has_content]
					[#assign link = catalogo.externalLink]
				[/#if]
				[#if catalogo.documento?? && catalogo.documento?has_content]
					[#attempt]
						[#assign documentAsset = cmsfn.contentById(catalogo.documento, "dam")]
						[#assign link = cmsfn.link(documentAsset)!""]
						[#assign pdf = true]
					[#recover]
						[#assign documentAsset = ""]
					[/#attempt]
				[/#if]
				
				<div class="box">
					[#if cmsfn.editMode]						
						<div style='text-align: right;'>[${catalogo.description!}]</div>
					[/#if]					
			    	<div class="content-img">                	
			        	[#if catalogo.imagen?has_content]
							[#assign asset = damfn.getAsset(catalogo.imagen)!]
							[#if asset?has_content && asset.link?has_content]        					
			 					<img src="${asset.link}" alt="catalogo">
							[/#if]	        							        		
						[/#if]		
			    	</div>
			    	<a href=${link} [#if documentAsset?has_content && documentAsset.audit?has_content && documentAsset.audit]onclick="auditDocument('${documentAsset.@id}')"[/#if]>
			    		<div class="content-text">	            		
			    			<span>${catalogo.title!}</span>
			    			[#if pdf]
			    			<span class="icon documento"></span>
			    			[/#if]
			    		</div>
			    	</a>
				</div>			
			[/#if]
		[/#list]    		
	</div>	
	
	[#if !hayCatalogos]
		<div>	
			${i18n['cione-module.templates.components.catalogos.empty']}
		</div>	
	[/#if]
	
</section>
	