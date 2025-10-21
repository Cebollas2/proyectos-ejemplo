[#assign site = sitefn.site()!]
[#assign theme = sitefn.theme(site)!]

<!DOCTYPE html>
<html lang="en">
    <head>
       [@cms.page /]
	   <title>${content.title!}</title>
		[#include "../includes/macros/content-head.ftl"]
		
    </head>
    <body>
    	[#-- Marketing Tags headerScripts --]
		[@cms.area name="bodyBeginScripts" /]
		
		
    	<div id="mobile-title" style="display:none">
    		[#if content.iconMenu?has_content]
				[#assign asset = damfn.getAsset(content.iconMenu)!]
				[#if asset?has_content && asset.link?has_content]        					
					<img src="${asset.link}" class="icon-menu-movile" alt="">
				[/#if]	        							        		
      		[/#if]				        	
        	${content.title!childNode.@name}    		
    	</div>
    	<div>
    		[@cms.area name="header" /]
    	</div>
    	<main role="main" class="layout-aside-content">
        	<aside>
        		[@cms.area name="left" /]
        	</aside>
        	<section>
        		[@cms.area name="main" /]
        	</section>
    	</main>    	
		<div>
			[@cms.area name="footer" /]
		</div>				
		[#include "../includes/macros/content-js.ftl"]                  
	</body>
</html>