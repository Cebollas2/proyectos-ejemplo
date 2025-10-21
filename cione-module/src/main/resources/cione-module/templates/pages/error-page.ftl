<!DOCTYPE html>
<html>
    <head>
       [@cms.page /]
	   <title>${content.title!}</title>
	   [#include "../includes/macros/content-head.ftl"]
	   [#include "../includes/macros/content-js.ftl"]
    </head>
    <body>    			    
	<section class="cmp-error404" style="background-image:url('${ctx.contextPath}/.resources/cione-theme/webresources/img/error.jpg')" alt="logotipo">
	    <div class="wrapper">
	        <div class="logo">
	        </div>
	        <div class="box-text">
	            <h1>${content.code!}</h1>
	            <h2>${content.text!}</h2>
	        </div>
	        <button onclick="goToLogin()">
	            Volver
	        </button>
	    </div>
	</section>
	<script>                
		function goToLogin(){
			window.location.replace("${cmsfn.link("website", content.returnPageLink!)!}");
		}
	</script>
	</body>
</html>