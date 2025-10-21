[#assign title = content.title!"Cione"]
[#assign site = sitefn.site()!]
[#assign theme = sitefn.theme(site)!]
[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]

<html lang="es">

	<head>
	    
	    <title>${content.windowTitle!content.title!"Cione"}</title>	
		<meta charset="utf-8" pageEncoding="UTF-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <meta name="application-name" content="MyShop" />
	  
	    <script>window.MSInputMethodContext && document.documentMode && document.write('<script src="${resourceURL}/js/myshop/ie11CustomProperties.js"><\x2fscript>');</script>
	   
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
	
		[@cms.area name="header"/]	
		[@cms.area name="main"/]	
		[@cms.area name="footer"/]
	
	
	</body>

</html>