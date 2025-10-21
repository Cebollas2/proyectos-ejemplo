[#assign existe = false]
[#assign hasPermissions = false]
[#list cmsfn.children(content.categoriesList) as listado]
	[#assign hasPermissions = false]
	[#if listado.permisos?? && listado.permisos?has_content]
		[#assign field = listado.permisos.field!]
		[#switch field]

			[#case "roles"]
				[#if model.hasPermissionsRoles(listado.permisos.roles!)]
					[#assign hasPermissions = true]
				[/#if]
				[#break]
				
			
			[#case "campaing"]
				[#if model.hasPermissionsCampana(listado.permisos.campaing!)]
					[#assign hasPermissions = true]
				[/#if]
				[#break]
				
			[#default]
				[#break]
		[/#switch]
	[#else]
		[#assign hasPermissions = true]
	[/#if]
	[#if hasPermissions]
		[#assign existe = true]
	[/#if]
[/#list]

[#if hasPermissions]
	<!-- NAV SCROLL -->
	<div class="b2b-nav-scroll">
		[#if content.categoriesList?has_content]
	    <ul class="b2b-nav-scroll-items">
			[#list cmsfn.children(content.categoriesList) as listado]
				[#if listado.linkText?? && listado.linkText?has_content]
					[#assign link = '#']
					[#assign target ='_self']
					[#if listado.externalLink?has_content]
						[#assign link = listado.externalLink]
						[#assign target ='_blank']	
					[/#if]
					[#if listado.internalLink?has_content]
						[#assign link = cmsfn.link("website", listado.internalLink!)!]
						[#assign categoria = '#']
						[#list listado.category?split("/") as sValue]
							[#if sValue?is_last]
						  		[#assign categoria = sValue]
						  	[/#if]
						[/#list] 
						[#assign link = link + "?category=" + categoria]
					[/#if]
					
					[#assign hasPermissions = false]
	    			[#if listado.permisos?? && listado.permisos?has_content]
	    				[#assign field = listado.permisos.field!]
	    				[#switch field]
				
							[#case "roles"]
								[#if model.hasPermissionsRoles(listado.permisos.roles!)]
									[#assign hasPermissions = true]
								[/#if]
								[#break]
								
							
							[#case "campaing"]
								[#if model.hasPermissionsCampana(listado.permisos.campaing!)]
									[#assign hasPermissions = true]
								[/#if]
								[#break]
								
							[#default]
			    				[#break]
			    		[/#switch]
			    	[#else]
			    		[#assign hasPermissions = true]
	    			[/#if]
	    			[#if hasPermissions]
						<li class="b2b-nav-scroll-item"><a target=${target} href=${link}>${listado.linkText}</a> </li>
			    	[/#if]
				[/#if]
			[/#list] 
	
	    </ul>
	    
	    [/#if]
	    
	</div>
[/#if]
[#-- 
<!-- NAV SCROLL -->
<div class="b2b-nav-scroll">
    <ul class="b2b-nav-scroll-items">
		
			[#if content.text?? && content.text?has_content]
				[#assign link = '#']
				[#assign target ='_self']
				[#if content.externalLink?has_content]
					[#assign link = content.externalLink]
					[#assign target ='_blank']
					[#assign link = link + "?category=" + content.category!]
				[/#if]
				[#if content.internalLink?has_content]
					[#assign link = cmsfn.link("website", content.internalLink!)!]
					[#assign link = link + "?category=" + content.category!]
				[/#if]
			
				<li class="b2b-nav-scroll-item"><a target=${target} href=${link}>${content.text}</a> </li>
				<li>${content.category!}</li>
		    
			[/#if]
    </ul>
</div>
--]