<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="application-name" content="Cione" />
<meta name="msapplication-notification" content="frequency=30;polling-uri=http://notifications.buildmypinnedsite.com/?feed=http://feeds.feedburner.com/applus/ndwe&amp;id=1;polling-uri2=http://notifications.buildmypinnedsite.com/?feed=http://feeds.feedburner.com/applus/ndwe&amp;id=2;polling-uri3=http://notifications.buildmypinnedsite.com/?feed=http://feeds.feedburner.com/applus/ndwe&amp;id=3;polling-uri4=http://notifications.buildmypinnedsite.com/?feed=http://feeds.feedburner.com/applus/ndwe&amp;id=4;polling-uri5=http://notifications.buildmypinnedsite.com/?feed=http://feeds.feedburner.com/applus/ndwe&amp;id=5; cycle=1" />	
<link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,900" rel="stylesheet">	      
    
<link rel="stylesheet" href="${ctx.contextPath}/.resources/cione-theme/webresources/css/font-awesome.min.css" media="all">
<link rel="stylesheet" href="${ctx.contextPath}/.resources/cione-theme/webresources/css/myshop/fontawesome-5.9.0/fontawesome.all.min.css" media="all">
<link rel="stylesheet" href="${ctx.contextPath}/.resources/cione-theme/webresources/css/bootstrap.css" media="all">
<link rel="stylesheet" href="${ctx.contextPath}/.resources/cione-theme/webresources/css/jquery-ui.min.css" media="all">
<!-- <link rel="stylesheet" href="${ctx.contextPath}/.resources/cione-theme/webresources/css/jquery.modal.min.css" />   -->
<link rel="stylesheet" href="${ctx.contextPath}/.resources/cione-theme/webresources/css/owl.carousel.min.css" media="all">
<link rel="stylesheet" href="${ctx.contextPath}/.resources/cione-theme/webresources/css/owl.theme.default.min.css" media="all">
<link rel="stylesheet" href="${ctx.contextPath}/.resources/cione-theme/webresources/css/style.css" media="all">
<link rel="stylesheet" href="${ctx.contextPath}/.resources/cione-theme/webresources/css/main.css" media="all">

<link rel="icon" href="${ctx.contextPath}/.resources/cione-theme/webresources/img/Favicon-100x100.ico" type="image/x-icon" />
<link rel="icon" href="${ctx.contextPath}/.resources/cione-theme/webresources/img/Favicon-300x300.ico" type="image/x-icon" />
<link rel="apple-touch-icon" href="${ctx.contextPath}/.resources/cione-theme/webresources/img/Favicon-300x300.ico" />

<!-- Marketing Tags headerScripts  -->
[@cms.area name="headerScripts" /]

<style>
	.hidePage{
		display:none !important;
	}
</style>


[#assign userId = '']
[#if ctx.getUser().getName()?? && ctx.getUser().getName()?has_content]

[#assign userId = ctx.getUser().getName()]
[/#if]

