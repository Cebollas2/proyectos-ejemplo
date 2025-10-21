
[#assign hasPermissions = false]
[#if content.permisos?? && content.permisos?has_content]
	[#assign field = content.permisos.field!]
	[#switch field]

		[#case "roles"]
			[#if model.hasPermissionsRoles(content.permisos.roles!)]
				[#assign hasPermissions = true]
			[/#if]
			[#break]
			
		
		[#case "campaing"]
			[#if model.hasPermissionsCampana(content.permisos.campaing!)]
				[#assign hasPermissions = true]
			[/#if]
			[#break]
			
		[#default]
			[#assign hasPermissions = true]
			[#break]
	[/#switch]
[#else]
	[#assign hasPermissions = true]
[/#if]

[#if hasPermissions]
	<section class="b2b-collection bg-gray">
	
	    <div class="b2b-collection-header">
	        <h2 class="b2b-collection-title">${content.title!i18n['cione-module.templates.myshop.marcas-component.title']}</h2>
	    </div>
	    
	    <div class="b2b-collection-wrapper">
	        <div class="b2b-collection-row">
	        	[#assign categoryRoot = content.filterSelect!]
	        	[#assign relacionadas = content.categoriesRelated!]
	        	[#assign relacionada = ""]
	        	[#list relacionadas as related]
	        		[#assign relacionada = related]
	        	[/#list]
	        	
	        	[#assign limit = content.limit!4]
	            [#assign colecciones = model.getListColeccionesMonturas(categoryRoot, relacionada, limit)]
	            [#if cmsfn.language() == 'pt']
	            	[#assign defaultImage = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/common/oops-PT.jpg"]
	            [#else]
	            	[#assign defaultImage = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/common/oops.jpg"]
	            [/#if]
	            
	            [#if colecciones?has_content && colecciones??]
		            [#list colecciones as colec]
		                
		            	[#assign link = "#"]
		            	[#assign linkandfilter = "#"]
		            	[#if content.listadoLink?has_content]
		            	
		            		
		            		[#assign link = cmsfn.link("website", content.listadoLink!)]
		            		
		            		[#-- si una de las categorias hijas esta como excepcion entonces
		            		     no ira al listado comun sino ira al indicado en la excepcion. En
		            		     la excepcion seleccionamos categoria y link. --]
		            		[#assign atributo = '']
		            		[#assign excepcion = false]
		            		[#assign ocultar_categoria = false]
		            		[#if content.categoriesException?? && content.categoriesException?has_content]
		            			[#list cmsfn.children(content.categoriesException, "mgnl:contentNode") as categoriaexcep]
		            				[#if categoriaexcep.categoriaException?split("/")[2] == colec.getId()]
		            					[#assign link = cmsfn.link("website", categoriaexcep.linkException!)]
			            			
				            			[#if categoriaexcep.attributeException?has_content && categoriaexcep.attributeException??]
				            				[#list categoriaexcep.attributeException! as sValue]
								    			[#assign encodingValue = model.encodeURIComponent(sValue)]
												[#assign atributo = atributo +'&'+encodingValue]
												[#assign excepcion = true]
											[/#list]
				            			[/#if]
				            			[#if categoriaexcep.isMainPage?? && categoriaexcep.isMainPage?has_content]
				            				[#assign ocultar_categoria = categoriaexcep.isMainPage]
				            			[/#if]
			            			[/#if]
			            		[/#list]
			            	[/#if]
			            	[#if !excepcion]
			            		[#list content.attribute! as sValue]
					    			[#assign encodingValue = model.encodeURIComponent(sValue)]
									[#assign atributo = atributo +'&'+encodingValue]
								[/#list]
							[/#if]
							[#if !ocultar_categoria]
								[#assign link = link + "?category=" + colec.getId() + atributo]
							[/#if]
		            	[/#if]
						
		            	<div class="b2b-collection-item">
		            	
		            	
		            		
			                <a href="${link}">
			                	[#assign logoListado = colec.getLogoListado()]
			                	[#if logoListado?starts_with("/")]
			                		[#assign logoListado = ctx.contextPath + logoListado]
			                	[/#if]
			                    <img src="${logoListado}" onerror="this.onerror=null; this.src='${defaultImage}'" alt="">
			                    <span class="b2b-collection-text">
			                        [#if cmsfn.language() == 'pt' && colec.getName().getPt()?has_content]
			                        	${colec.getName().getPt()}
			                        [#elseif cmsfn.language() == 'en' && colec.getName().getEn()?has_content]
			                        	${colec.getName().getEn()}
			                        [#else]
			                        	${colec.getName().getEs()}
			                        [/#if]
			                    </span>
			                </a>
			            </div>
			            
		            [/#list]
	            
	            [#else]
	            	<div class="b2b-collection-item">
			            ${content.textnofound!i18n['cione-module.templates.myshop.marcas-component.notfoundtext']}
					</div>
	            [/#if]
	            
	        </div> 
	           
	    </div>
	    
	</section>

[/#if]