<script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/jquery-3.1.1.min.js"></script>
<script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/.resources/cione-theme/webresources/js/jquery.jscrollpane.min.js" defer="defer"></script>
<script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/popper.js"></script>
<script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/bootstrap.min.js"></script>
<script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/validator.min.js"></script>
<script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/owl.carousel.min.js"></script>
<!-- <script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/jquery.modal.min.js"></script>  -->
<script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/main.js" async="async"></script>
<script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/cione.js" async="async"></script>
<script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/moment.min.js" async="async"></script>
<script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/jquery.mask.min.js" async="async"></script>
<script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/cookies.js"></script>
<script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/myshop/crypto-js.min.js"></script>
<script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/auditoria.js"></script>
<!-- 
<script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/jquery.waypoints.min.js"></script>
<script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/jquery.counterup.min.js"></script>
 -->

<section id="loading" class="cmp-loader" style="display:none">
    <div class="lds-ripple">
        <div></div>
        <div></div>
    </div>
</section>


<script>
	CTX_PATH = "${ctx.contextPath}" + "/cione";
	PATH_API = "${ctx.contextPath}" +  "/.rest";
	console.log(PATH_API);
	LANG = "${cmsfn.language()}";
	I18N_LINK = {};
	I18N_LINK["es"] = "${cmsfn.localizedLinks()['es']}"; 
	I18N_LINK["pt"] = "${cmsfn.localizedLinks()['pt']}";
	
	i18n_noRecordsFound = "${i18n['cione-module.global.no-records-found']}";
	i18n_errorFechaIncorrecta = "${i18n['cione-module.global.errors.fecha-incorrecta']}";
	i18n_errorFechaDesdeHasta = "${i18n['cione-module.global.errors.desde-hasta']}";
	
	
	function changeLang(lang){
		window.location.replace(I18N_LINK[lang]);
	}	
</script>
