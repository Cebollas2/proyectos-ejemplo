[#assign title = content.title!"Cione"]
[#assign site = sitefn.site()!]
[#assign theme = sitefn.theme(site)!]
[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]

<!DOCTYPE html>
<html lang="es ">
    <head>
	   <title>${content.title!}</title>
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
		
		<main class="b2b-main" role="main">
			
			<h2 class="b2b-h2">${model.categoryName!content.title!""}</h2>
			[#if content.imagedocument?? && content.imagedocument?has_content]
				[#assign image = ""]
    			[#if content.imagedocument?? && content.imagedocument?has_content]
    				[#assign image = damfn.getAssetLink("jcr:" + content.imagedocument)!""]
    			[/#if]
    			
    			[#assign documentAux = "#"]
    			[#if content.document?? && content.document?has_content]
    				[#assign documentAux = cmsfn.link(cmsfn.contentById(content.document , "dam"))!]
    			[/#if]
    			<div class="b2b-container-download">
        			<div class="b2b-listado-descarga ">
						<a class="b2b-listado-descarga-container"  href="${documentAux}">
						    <img class="" src="${image}" alt="" />
						    [#if content.document?? && content.document?has_content]
							    <div class="b2b-descarga-link-wrapper">
							        <img class="pl-5" src="${resourcesURL}/img/myshop/icons/descarga.svg" alt="" />
							        <span class="b2b-descarga-link pl-2">Descargar tarifas de Taller</span>
							    </div>
						    [/#if]
						</a>
					    <hr class='dotted' />
					</div>
    			</div>
	    		
			[/#if]
			
			<div class="b2b-container">
				<div class="b2b-container-filter">
					[@cms.area name="submain-left" /]
				</div>
				<div class="b2b-container-main">
					[@cms.area name="submain-right" /]
				</div>
			</div>
		</main>
		[@cms.area name="main2" /]
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
	                	 src="${resourcesURL + "/img/myshop/icons/close-thin-white.svg"!}"
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