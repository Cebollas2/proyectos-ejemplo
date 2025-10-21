[#list cmsfn.children(content) as formulario]
	<div class="item">
		[#if formulario.title?? && formulario.title?has_content]
			<label class="title">
				${formulario.title}
			</label>
		[/#if]
		<ul>
	    	[#list cmsfn.children(formulario) as documento]
	    		[#assign hasPermissions = false]
    			[#if documento.permisos?? && documento.permisos?has_content]
    				[#assign field = documento.permisos.field!]
    				[#switch field]
			
						[#case "roles"]
							[#if model.hasPermissionsRoles(documento.permisos.roles!)]
								[#assign hasPermissions = true]
							[/#if]
							[#break]
							
						
						[#case "campaing"]
							[#if model.hasPermissionsCampana(documento.permisos.campaing!)]
								[#assign hasPermissions = true]
							[/#if]
							[#break]
							
						[#default]
		    				[#break]
		    		[/#switch]
		    	[#elseif model.hasPermissionsCampana(documento.campanas!)]
		    		[#assign hasPermissions = true]
    			[/#if]
    			[#if ctx.getUser().hasRole("superuser") || hasPermissions]
    				<li>		    			
		    			<span>
		    				<a href="${damfn.getAssetLink("jcr:" + documento.documentos)}">
		    					${documento.titleDocument}
		    					<span class="icon documento" style="margin-left:2px"></span>
		    				</a>
		    			</span>
		    			[#--  --if cmsfn.editMode]						
							<span style='color:red;margin-left:10px'>${documento.titleDocument!}</span>
						[/#if--]		    						
		    		</li>
    			[/#if]
			[/#list]
		</ul>
	</div>
[/#list]
