<!DOCTYPE html>
<html lang="en">
    <head>
       [@cms.page /]
	   <title>${content.title!}</title>
		[#include "../includes/macros/content-head.ftl"]
		
		
    </head>
    <body>
    	[#-- Marketing Tags bodyScripts --]
		[@cms.area name="bodyBeginScripts" /]
		
		[#-- <div >
			<hr style="border: 3px solid #07355a; margin-top: 0.5rem;">
			<hr style="border: 3px solid #84bd00; margin-top: 0.5rem;">  
		</div> --]
		
	    [#assign isOpticaPRO = false]
	    [#if cmsfn.page(content) == "opticapro"]
	    	[#assign isOpticaPRO = true]
	    [/#if]
		
    	[#if content.background?? && content.background?has_content]
    		[#assign asset = damfn.getAssetLink("jcr:" + content.background)!""]
    		[#if asset?? && asset?has_content]
    			<section class="cmp-bundles-loginback" style="background-image: url('${asset}');">
    		[/#if]
    	[#else]
    		<section class="cmp-bundles-loginback" style="background-image: url(${ctx.contextPath}/.resources/cione-theme/webresources/img/fondologin.jpg);">
    	[/#if]			
    	[#assign style ="padding: 5rem 0 9.375rem 0;"]
		[#if content.style?? && content.style?has_content]
			[#assign style = content.style]
		[/#if]
			[#if isOpticaPRO]
        		<div class="container container-login" style="max-width: 1450px;">
        	[#else ]
        		<div class="container container-login">
        	[/#if]
        		<div class="title-login" style="${style}">
	        		[#if content.highLightText?? && content.highLightText?has_content]
	                
	                	[#--  --list cmsfn.children(content.highLightText) as row]
	                        <p>${row.text}</p>
	                    [/#list--] 
	                    [#list cmsfn.children(content.highLightText) as row]
	                        <p>${row.texto}</p>
	                    [/#list]                      
	                
	                [/#if]
                </div>
                
                [@cms.area name="main" /]
        	</div>
		</section>
		[#--  [#if ctx.getUser().hasRole("OPTICAPRO")] 
			<hr style="border: 3px solid #07355a; margin-top: 0.5rem;">
			<hr style="border: 3px solid #84bd00; margin-top: 0.5rem;">  
		[/#if]  --]
     	[#include "../includes/macros/content-js.ftl"]                  
	</body>
</html>