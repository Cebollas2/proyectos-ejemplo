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
			[#assign documentAux = "#"]
			[#list cmsfn.children(content) as image]
				[#if image.imagedocument?has_content]
	    			[#assign hasPermissions = false]
	    			[#if image.permisos?? && image.permisos?has_content]
	    				[#assign field = image.permisos.field!]
	    				[#switch field]
				
							[#case "roles"]
								[#if model.hasPermissionsRoles(image.permisos.roles!)]
									[#assign hasPermissions = true]
								[/#if]
								[#break]
								
							
							[#case "campaing"]
								[#if model.hasPermissionsCampana(image.permisos.campaing!)]
									[#assign hasPermissions = true]
								[/#if]
								[#break]
								
							[#default]
			    				[#break]
			    		[/#switch]
	    			[/#if]
	    			
	    			[#if hasPermissions]
	    				[#assign imageAux = cmsfn.link(cmsfn.contentById(image.imagedocument , "dam"))!]
	    				[#assign documentAux = cmsfn.link(cmsfn.contentById(image.document , "dam"))!]
    					<div class="b2b-container-download">
		        			<div class="d-flex b2b-listado-descarga ">
		        				<div class="b2b-listado-descarga-container">
									<a href="${documentAux}">
									    <img class="" src="${imageAux}" alt="" />
									</a>
								</div>
								
							    [#if image.document?? && image.document?has_content]
							    <div class="pl-5 b2b-listado-descarga-container">
							    	<a href="${documentAux}">
									    <div class="b2b-descarga-link-wrapper">
									        <img src="${resourcesURL}/img/myshop/icons/descarga.svg" alt="" />
									        <span class="b2b-descarga-link pl-2">Descargar tarifas de Taller</span>
									    </div>
									</a>
								</div>
							    [/#if]
								
							    
							</div>
								<hr class='dotted' />
		    			</div>
    			
    				[/#if]
    			[/#if]
			[/#list]
			
    		
    		
			<div class="b2b-container">
				<div class="b2b-container-main" style="width: 100%;">
					[@cms.area name="submain-right" /]
				</div>
			</div>
		</main>
		
		[@cms.area name="footer" /]
		
		<div id="fly-card" class="fly-card">

		    <p class="fly-card-text">
		        ${i18n['cione-module.templates.pages.myshop.myshop-page.popupcart']}
		    </p>
		
		</div>
		
		<section id="loading" class="cmp-loader" style="display:none">
		    <div class="lds-ripple">
		        <div></div>
		        <div></div>
		    </div>
		</section>
                 
	</body>
</html>