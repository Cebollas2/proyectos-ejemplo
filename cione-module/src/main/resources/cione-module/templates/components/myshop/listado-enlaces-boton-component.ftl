[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]
[#if cmsfn.children(content)?has_content]
<div class="b2b-main" role="main">
<section class="b2b-subhome-grid bg-gray" style="margin-top: 30px;">
    <ul class="b2b-subhome-grid__list">
    	[#list cmsfn.children(content.linksList) as enlaces]
    	
    		[#assign hasPermissions = false]
			[#if enlaces.permisos?? && enlaces.permisos?has_content]
				[#assign field = enlaces.permisos.field!]
				[#switch field]
		
					[#case "roles"]
						[#if model.hasPermissionsRoles(enlaces.permisos.roles!)]
							[#assign hasPermissions = true]
						[/#if]
						[#break]
						
					
					[#case "campaing"]
						[#if model.hasPermissionsCampana(enlaces.permisos.campaing!)]
							[#assign hasPermissions = true]
						[/#if]
						[#break]
						
					[#default]
						[#assign hasPermissions = true]
	    				[#break]
	    		[/#switch]
			[/#if]
    	
    		[#if hasPermissions]
				[#assign link = "#"]
				[#assign href = "#"]
				[#if enlaces.image?? && enlaces.image?has_content]
		    		[#assign link = damfn.getAssetLink("jcr:" + enlaces.image)!""]
		    	[/#if]
		    	[#if enlaces.internalLink?? && enlaces.internalLink?has_content]
		    		[#assign href = cmsfn.link(cmsfn.contentById(enlaces.internalLink))]
		    	[/#if]
		    	
		    	<li class="b2b-subhome-grid__item">
		            <a href="${href!'#'}">
		                <h2 class="b2b-subhome-grid__item-title">
		                   ${enlaces.linkText!}
		                </h2>
		                <div class="aspect-ratio aspect-ratio--3x2">
		                    <img src="${link!}" alt="${enlaces.linkText!}">
		                </div>
		            </a>
		        </li>
			[/#if]
	    [/#list]
	</ul>
</section>
</div>
[/#if]
