[#assign title = content.title!"Cione"]
[#assign site = sitefn.site()!]
[#assign theme = sitefn.theme(site)!]
[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]

<!DOCTYPE html>
<html xml:lang="${cmsfn.language()}" lang="${cmsfn.language()}">
	<head>
	    <title>${content.windowTitle!content.title!"Cione"}</title>	
		<meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <meta name="application-name" content="Cione"/>
	    <meta name="msapplication-notification" content="frequency=30;polling-uri=http://notifications.buildmypinnedsite.com/?feed=http://feeds.feedburner.com/applus/ndwe&amp;id=1;polling-uri2=http://notifications.buildmypinnedsite.com/?feed=http://feeds.feedburner.com/applus/ndwe&amp;id=2;polling-uri3=http://notifications.buildmypinnedsite.com/?feed=http://feeds.feedburner.com/applus/ndwe&amp;id=3;polling-uri4=http://notifications.buildmypinnedsite.com/?feed=http://feeds.feedburner.com/applus/ndwe&amp;id=4;polling-uri5=http://notifications.buildmypinnedsite.com/?feed=http://feeds.feedburner.com/applus/ndwe&amp;id=5; cycle=1"/>
	
	    [#list theme.cssFiles as cssFile]
		    <link rel="stylesheet" href="${cssFile.link}" [#if cssFile.media?? && cssFile.media?has_content] media="${cssFile.media!''}"[/#if] />
	  	[/#list]
	  	
	  	[#-- Favicon
	  	[#assign imgFavicon = resourcesURL + "/img/favicon.ico"]
	  	<link rel="icon" href="${imgFavicon!""}" type="image/x-icon" /> --]
	  	
	  	<link rel="icon" href="${resourcesURL!""}/img/Favicon-100x100.ico" type="image/x-icon" />
		<link rel="icon" href="${resourcesURL!""}/img/Favicon-300x300.ico" type="image/x-icon" />
		<link rel="apple-touch-icon" href="${resourcesURL!""}/img/Favicon-300x300.ico" />
	
		[@cms.page /]	
	</head>
	<body>
		[@cms.area name="header"/]	
		[@cms.area name="main"/]	
		[@cms.area name="footer"/]
		
		[#list theme.jsFiles as jsFile]
		    <script src="${jsFile.link}"></script>
		[/#list]	
		
		<section id="loading" class="cmp-loader" style="display:none">
		    <div class="lds-ripple">
		        <div></div>
		        <div></div>
		    </div>
		</section>
	
		<script>
			PATH_API = "${ctx.contextPath}" +  "/.rest";
			LANG = "${cmsfn.language()}";
			I18N_LINK = {};
			I18N_LINK["es"] = "${cmsfn.localizedLinks()['es']}"; 
			I18N_LINK["pt"] = "${cmsfn.localizedLinks()['pt']}";
	
			function changeLang(lang){
				window.location.replace(I18N_LINK[lang]);
			}
		</script>	
	</body>
</html>
