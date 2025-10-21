[#if cmsfn.children(content)?has_content]
	[#list cmsfn.children(content) as footer]
		[#assign hasPermissions = false]
		[#if footer.permisos?? && footer.permisos?has_content]
			[#assign field = footer.permisos.field!]
			[#switch field]
	
				[#case "roles"]
					[#if model.hasPermissionsRoles(footer.permisos.roles!)]
						[#assign content = footer]
						[#break]
					[/#if]
				
				[#case "campaing"]
					[#if model.hasPermissionsCampana(footer.permisos.campaing!)]
						[#assign content = footer]
						[#break]
					[/#if]
					
				[#default]
    				[#break]
    		[/#switch]
    	[#else]
    		[#assign content = footer]
		[/#if]
	[/#list]
[/#if]
<footer>
	<div class="b2b-footer">
	    <div class="footer-left">
	        [#if content.title?? && content.title?has_content]
		    	${content.title!""}
		    [/#if]
	    </div>
	    <div class="footer-right">
	        [#if content.phone?? && content.phone?has_content]
		    	${content.phone!""}
		    [/#if] · [#if content.mail?? && content.mail?has_content]
		    	${content.mail!""}
		    [/#if]
	    </div>
	</div>
</footer>