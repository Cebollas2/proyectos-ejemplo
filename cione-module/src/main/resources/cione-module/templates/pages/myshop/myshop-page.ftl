[#assign title = content.title!"Cione"]
[#assign site = sitefn.site()!]
[#assign theme = sitefn.theme(site)!]
[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]

<!DOCTYPE html>
<html lang="en">
    <head>
	   <title>${content.title!"Cione"}</title>
	   <meta charset="utf-8" pageEncoding="UTF-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <meta name="application-name" content="MyShop" />
	  
	    <script>window.MSInputMethodContext && document.documentMode && document.write('<script src="${resourcesURL}/js/myshop/ie11CustomProperties.js"><\x2fscript>');</script>
	   
		[#list theme.cssFiles as cssFile]
		    <link rel="stylesheet" href="${cssFile.link}" [#if cssFile.media?? && cssFile.media?has_content] media="${cssFile.media!''}"[/#if] />
	  	[/#list]
	 
	 	[#list theme.jsFiles as jsFile]
		    <script src="${jsFile.link}"></script>
		[/#list]
		
		<link rel="icon" href="${resourcesURL!""}/img/Favicon-100x100.ico" type="image/x-icon" />
		<link rel="icon" href="${resourcesURL!""}/img/Favicon-300x300.ico" type="image/x-icon" />
		<link rel="apple-touch-icon" href="${resourcesURL!""}/img/Favicon-300x300.ico" />
		
		[@cms.page /]
		
		<!-- Marketing Tags headerScripts  -->
		[@cms.area name="headerScripts" /]
		
    </head>
    <body>
   		[#-- Marketing Tags bodyScripts --]
		[@cms.area name="bodyBeginScripts" /]
   	
		[@cms.area name="header" /]
		
		[@cms.area name="main" /]
		
		[@cms.area name="footer" /]
		
		<div id="fly-card" class="fly-card">

		    <p class="fly-card-text">
		        ${i18n['cione-module.templates.pages.myshop.myshop-page.popupcart']}
		    </p>
		
		</div>
		
		[#-- BEGIN: modal prueba virtual
		<div class="detalle-virtual-modal">
	        <div class="detalle-virtual-modal-wrapper">
	            <div class="detalle-virtual-modal-close-wrapper">
	                <img class="detalle-virtual-modal-close-img" 
	                	 src="/.resources/cione-theme/webresources/img/myshop/icons/close-thin-white.svg"
	                     alt="Cerrar" />
	            </div>
	           <div id="fitmixContainer"></div>
	        </div>
	    </div>
		[#-- END: modal prueba virtual --]
		
		<section id="loading" class="cmp-loader" style="display:none">
		    <div class="lds-ripple">
		        <div></div>
		        <div></div>
		    </div>
		</section>
                 
	</body>
</html>
