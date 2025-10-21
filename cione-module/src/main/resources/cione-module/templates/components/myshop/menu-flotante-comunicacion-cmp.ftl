<section class="cmp-menuflotante">
  <ul>
      [#-- <li><a href="javascript:void(0)" >${content.titulo!""}</a></li>  --]
      <li class="cmp-menuflotante-bubble"><a href="javascript:void(0)" >
        <span>${i18n['cione-module.templates.myshop.menu-flotante-comunicacion-cmp.teEscuchamos']}</span>
        <img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/iconos/globo.svg" alt="icono">
      </a></li>
      [#assign flagSinOpcion = true]
      [#list cmsfn.children(content.enlaces) as enlace]	
      	[#assign hasPermissions = false]
		[#if enlace.permisos?? && enlace.permisos?has_content]
			[#assign field = enlace.permisos.field!]
			[#switch field]
	
				[#case "roles"]
					[#if model.hasPermissionsRoles(enlace.permisos.roles!)]
						[#assign hasPermissions = true]
						[#assign flagSinOpcion = false]
					[/#if]
					[#break]
					
				
				[#case "campaing"]
					[#if model.hasPermissionsCampana(enlace.permisos.campaing!)]
						[#assign hasPermissions = true]
						[#assign flagSinOpcion = false]
					[/#if]
					[#break]
					
				[#default]
    				[#break]
    		[/#switch]
    	[#elseif model.hasPermissionsCampana(enlace.campanas!)]
    		[#assign hasPermissions = true]
		[/#if]
		
		
      	[#if hasPermissions]
	      	<li>  
	      		[#if enlace.enlace?has_content]
	      			<div>
		      			<a href="${enlace.enlace}" target="_blank" class="d-flex">
			      			<div class="wrapper-img">
			      				[#assign imgItemKey = enlace.icono]
			      				[#if imgItemKey?? && imgItemKey?has_content]
									<img src="${damfn.getAssetLink("jcr:" +imgItemKey)!''}" alt="icono"/>
								[/#if]
				      		</div>
				      		<div>
				      			${enlace.texto!}
				      		</div>
				      	</a>
			      	</div>
	      		[#elseif enlace.javascript?has_content]
	      			<div>
		      			<a href="${enlace.javascript}" class="d-flex">
			      			<div class="wrapper-img">
				      			[#assign imgItemKey = enlace.icono]
			      				[#if imgItemKey?? && imgItemKey?has_content]
									<img src="${damfn.getAssetLink("jcr:" +imgItemKey)!''}" alt="icono"/>
								[/#if]
				      		</div>
				      		<div>
				      			${enlace.texto!}
				      		</div>
				      	</a>
			      	</div>
			    [#elseif enlace.especialLink?? && enlace.especialLink?has_content]
			    
			    	[#assign field = enlace.especialLink.field!]
			    	[#assign userERP = model.getUserFromERP()!]
		        	[#switch field]
						[#case "enlaceUniversity"]
							[#if !ctx.getUser().hasRole("empleado_cione_cione_university")
								&& !ctx.getUser().hasRole("TALLERMAD")
								&& !ctx.getUser().hasRole("OPTCAN")
								&& !ctx.getUser().hasRole("cliente_monturas")
								&& !ctx.getUser().hasRole("OPTMAD")
								&& !ctx.getUser().hasRole("OPTICAPRO")]
								
								[#if userERP?? && userERP?has_content]
									<a href="#" onClick="updateUniversityUser('${href}')" target="_blank" class="d-flex">
						      			<div class="wrapper-img">
						      				[#assign imgItemKey = enlace.icono]
						      				[#if imgItemKey?? && imgItemKey?has_content]
												<img src="${damfn.getAssetLink("jcr:" +imgItemKey)!''}" alt="icono"/>
											[/#if]
							      		</div>
							      		<div>
							      			${enlace.texto!}
							      		</div>
							      	</a>
								[/#if]
								
							[/#if]
							[#break]
						
						[#case "enlaceAudio"]
							[#if !ctx.getUser().hasRole("empleado_cione_myom")
				        		&& !ctx.getUser().hasRole("TALLERMAD")
				        		&& !ctx.getUser().hasRole("OPTCAN")
				        		&& !ctx.getUser().hasRole("cliente_monturas")
								&& !ctx.getUser().hasRole("OPTMAD")]  
					        	
				            	[#assign key = link.especialLink.key]
				            	[#assign href = link.especialLink.linkName]
					            [#assign target = "_blank"]
					            [#if userERP?? && userERP?has_content]
					            	<a href="#" onClick="updateUser('${href}', '${key}', '${userERP.numSocio!}')" target="_blank" class="d-flex">
						      			<div class="wrapper-img">
						      				[#assign imgItemKey = enlace.icono]
						      				[#if imgItemKey?? && imgItemKey?has_content]
												<img src="${damfn.getAssetLink("jcr:" +imgItemKey)!''}" alt="icono"/>
											[/#if]
							      		</div>
							      		<div>
							      			${enlace.texto!}
							      		</div>
							      	</a>
						        [/#if]
							[/#if]
							[#break]
						[#case "enlaceForo"]
							[#if !ctx.getUser().hasRole("empleado_cione_comunidad")
				        		&& !ctx.getUser().hasRole("TALLERMAD")
				        		&& !ctx.getUser().hasRole("OPTCAN")
				        		&& !ctx.getUser().hasRole("cliente_monturas")
								&& !ctx.getUser().hasRole("OPTMAD")]  
				            	[#assign href = enlace.especialLink.linkName]
					            [#assign target = "_blank"]
					            [#if userERP?? && userERP?has_content]
					      			<a href="${href}/auth.php?userid=${model.getNumSocioEncrypt()!}" target="_blank" class="d-flex">
						      			<div class="wrapper-img">
						      				[#assign imgItemKey = enlace.icono]
						      				[#if imgItemKey?? && imgItemKey?has_content]
												<img src="${damfn.getAssetLink("jcr:" +imgItemKey)!''}" alt="icono"/>
											[/#if]
							      		</div>
							      		<div>
							      			${enlace.texto!}
							      		</div>
							      	</a>
						        [/#if]
						    [/#if]
							[#break]
						[#default]
		    				[#break]
		    		[/#switch]
	      		[/#if]
	      	</li>
      	[/#if]
      [/#list]  
      
      [#-- Si no puede ver ninguna opcion, ocultamos el submenu  --]
      [#if flagSinOpcion]
      		<script>
	    		$(".cmp-menuflotante").css("display", "none");
	    	</script>
      [/#if]
  </ul>
</section>
