[#assign title = content.title!"Dummy page (created by maven archetype)"]
<!DOCTYPE html>
<html>
<head>
	[@cms.page /]
    <title>${title}</title>
    <link rel="stylesheet" href="${ctx.contextPath}/.resources/cione-theme/webresources/css/style.css">
    [#include "../includes/macros/content-head.ftl"]	

</head>
<body>
<div class="container">              
    <div class="main">    	
	    [@cms.area name="main"/]
    </div>
</div>
</body>
</html>
