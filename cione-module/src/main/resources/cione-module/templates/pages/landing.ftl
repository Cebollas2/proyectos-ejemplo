<!DOCTYPE html>
<html>
    <head>
       [@cms.page /]
	   <title>${content.title!}</title>
	   [#include "../includes/macros/content-head.ftl"]
	   [#include "../includes/macros/content-js.ftl"]
    </head>
    <body>    	
    [#assign link = '#']
    [#if content.image?has_content]
        [#assign link = damfn.getAssetLink("jcr:" + content.image)!""]   
    [/#if] 
	<section class="cmp-landing" style="background-image:url('${link!"#"}')" alt="logotipo">
		[#if content.text?has_content]
		    <div class="wrapper">
		    	[#if content.logo?? && content.logo?has_content]
		    		[#assign link = damfn.getAssetLink("jcr:" + content.logo)!""]  
		    		<div class="logo" style="background-image:url('${link!}')"></div> 
			    [/#if]
		        <div class="box-text">
	        		[#list content.text as listado]
						<h2>${listado!}</h2>
					[/#list]
		        </div>
		        
		        ${cmsfn.decode(content).textoinfo!}
		    </div>
		[#elseif content.textoinfo?has_content]
			<div class="wrapper">
		    	[#if content.logo?? && content.logo?has_content]
		    		[#assign link = damfn.getAssetLink("jcr:" + content.logo)!""]  
		    		<div class="logo" style="background-image:url('${link!}')"></div> 
			    [/#if]
		        <div class="box-text">
	        		${cmsfn.decode(content).textoinfo!}
		        </div>
		        
		        
		    </div>
	    [/#if]
	</section>
	<script>                
		
	</script>
	</body>
</html>