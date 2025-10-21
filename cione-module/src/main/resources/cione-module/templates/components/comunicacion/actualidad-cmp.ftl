<section class="cmp-noticiasactualidad">
    <h2 class="title">${i18n['cione-module.templates.components.actualidad.actualidad']}</h2>
    <div class="rrss">
    	[#assign redessociales =cmsfn.children(content.rrss)!]
    	[#list redessociales as red]
    		[#if red.logo?? && red.logo?has_content]
	    		[#assign imgItemKey = red.logo]
	    		<a href="${red.externalLink!''}" target="_blank">
		           <img src="${damfn.getAssetLink(imgItemKey)!''}" alt="icon">
		        </a>
		    [/#if]
    	[/#list]
        <span></span>
    </div>

    <div class="wrapper-content">
        <div class="content-news">
            <ul>
            	[#if (content.carpetadestacada)?? && (content.carpetadestacada)?has_content]
	            	[#assign folderFeatured = cmsfn.contentById(content.carpetadestacada, "current-news")]
					[#if folderFeatured??]	
						[#assign noticias = cmsfn.children(folderFeatured,"current-new")]
						
						[#list noticias as noticia]
							[#assign fInicio = (noticia.fechaInicio?date?string["dd/MM/yyyy HH:mm:ss"])!"null"]
							[#assign fFin = (noticia.fechaFin?date?string["dd/MM/yyyy HH:mm:ss"])!"null"]			
									
							[#if model.checkFechaInicio(fInicio) && model.checkFechaFin(fFin)]
								<li class="content-item">
				                    <article>
				                        <figure>
				                        	<img src="${damfn.getAsset(noticia.imagen!).link!}" alt=""/>
				                        </figure>
				                        [#if noticia.enlace?? && noticia.enlace?has_content]
				                        	<a href="${noticia.enlace!}" target="_blank">
					                        	<h2>${noticia.texto!}</h2>
					                        </a>
					                    [#elseif noticia.documento?? && noticia.documento?has_content]
					                    	[#assign link = cmsfn.link(cmsfn.contentById(noticia.documento, "dam"))]
					                    	<a href='${cmsfn.link(cmsfn.contentById(noticia.documento, "dam"))}' target="_blank">
					                        	<h2>${noticia.texto!}</h2>
					                        </a>
					                    [#else]
					                    	<h2>${noticia.texto!}</h2>
				                        [/#if]
				                    </article>
				                </li>
							[/#if]
						[/#list]
	        		[/#if]
        		[/#if]
        		
        		[#if (content.carpetasecundaria)?? && (content.carpetasecundaria)?has_content]
					[#assign folderSecondary = cmsfn.contentById(content.carpetasecundaria, "current-news")]
					[#if folderSecondary??]	
						[#assign noticias = cmsfn.children(folderSecondary,"current-new")]
						
						[#list noticias as noticia]
							[#assign fInicio = (noticia.fechaInicio?date?string["dd/MM/yyyy HH:mm:ss"])!"null"]
							[#assign fFin = (noticia.fechaFin?date?string["dd/MM/yyyy HH:mm:ss"])!"null"]			
									
							[#if model.checkFechaInicio(fInicio) && model.checkFechaFin(fFin)]
								<li class="content-item">
				                    <article>
				                        <figure>
				                        	<img src="${damfn.getAsset(noticia.imagen!).link!}" alt=""/>
				                        </figure>
				                        [#if noticia.enlace?? && noticia.enlace?has_content]
				                        	<a href="${noticia.enlace!}" target="_blank">
					                        	<h2>${noticia.texto!}</h2>
					                        </a>
					                    [#elseif noticia.documento?? && noticia.documento?has_content]
					                    	[#assign link = cmsfn.link(cmsfn.contentById(noticia.documento, "dam"))]
					                    	<a href='${cmsfn.link(cmsfn.contentById(noticia.documento, "dam"))}' target="_blank">
					                        	<h2>${noticia.texto!}</h2>
					                        </a>
					                    [#else]
					                    	<h2>${noticia.texto!}</h2>
				                        [/#if]
				                    </article>
				                </li>
							[/#if]
						[/#list]
	        		[/#if]
	        	[/#if]
            </ul>
        </div>

        <div class="right-aside">
	        <div class="indicadores">
	            <header>
	                <div>${i18n['cione-module.templates.components.actualidad.descripcion']}</div>
	            </header>
	            
	            <div class="wrapper-actualidad">
                    <ul>
                        
                        <li>
                            <div class="wrapper-img">
                                <img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/actividad/pedidos-servidos.svg" alt="icono">
                            </div>
                            <span class="counter"  data-count="${model.getKpis().getPorcPedidosPlazo()!}">0 %</span>
        					<p>${i18n['cione-module.templates.components.actualidad.pedidos-en-plazo']}</p>
                        </li>
                        [#--    
                        <li>
                            <div class="wrapper-img">
                                <img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/actividad/pedidos-plazo.svg" alt="icono">
                            </div>
                            <span class="counter" data-count="${model.getKpis().getPorcPedidosPendientes()!}">0</span>
        					<p>${i18n['cione-module.templates.components.actualidad.pedidos-servidos']}</p>
                        </li>
                        --]
                        <li>
                            <div class="wrapper-img">
                                <img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/actividad/pedidos-incidencia.svg" alt="icono">
                            </div>
                            <span class="counter" data-count="${model.getKpis().getPorcInteracciones()!}">0 %</span>
        					<p>${i18n['cione-module.templates.components.actualidad.pedidos-sin-incidencias']}</p>
                        </li>
                        [#--    
                        <li>
                            <div class="wrapper-img">
                                <img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/actividad/repuestos-servidos.svg" alt="icono">
                            </div>
                            <span class="counter"data-count="${model.getKpis().getPorcRepuestosServidos()!}">0 %</span>
        					<p>${i18n['cione-module.templates.components.actualidad.repuestos-servidos']}</p>
                        </li>
                        
                        <li>
                            <div class="wrapper-img">
                                <img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/actividad/alternativas-repos-servidos.svg" alt="icono">
                            </div>
                            <span class="counter" data-count="${model.getKpis().getPorcRepuestosAlter()!}">0 %</span>
        					<p>${i18n['cione-module.templates.components.actualidad.alternativas-repuestos-servidos']}</p>
                        </li>
                        --]
                    </ul>
                </div>
                
            </div>
            
            [#if (content.carpetaotras)?? && (content.carpetaotras)?has_content]
				[#assign folderOthers = cmsfn.contentById(content.carpetaotras, "current-news")]
				[#if folderOthers??]	
					[#assign noticias = cmsfn.children(folderOthers,"current-new")]
				<div class="othernews">
	            	<ul>
					[#list noticias as noticia]
						[#assign fInicio = (noticia.fechaInicio?date?string["dd/MM/yyyy HH:mm:ss"])!"null"]
						[#assign fFin = (noticia.fechaFin?date?string["dd/MM/yyyy HH:mm:ss"])!"null"]			
								
						[#if model.checkFechaInicio(fInicio) && model.checkFechaFin(fFin)]
							[#if noticia.enlace?? && noticia.enlace?has_content]
								<li>${noticia.texto!}<a href="${noticia.enlace!}"  target="_blank">${i18n['cione-module.global.btn-more']}</a></li>
							[#elseif noticia.documento?? && noticia.documento?has_content]
		                    	<li>${noticia.texto!}<a href='${cmsfn.link(cmsfn.contentById(noticia.documento, "dam"))}'  target="_blank">${i18n['cione-module.global.btn-more']}</a></li>
		                    [#else]
		                    	<li>${noticia.texto!}</li>
	                        [/#if]

						[/#if]
					[/#list]
					</ul>       
	            </div>
	            [/#if]
    		[/#if]

        </div>
 
    </div>

</section>

 <script>
	function initPage(){ 	
		initCounters();
	}
	
	function initCounters(){
		$('.counter').each(function () {
		    var $this = $(this),
		        countTo = $this.attr('data-count');

		    $({ countNum: $this.text() }).animate({
		        countNum: countTo
		    },
	        {

	            duration: 3000,
	            easing: 'linear',
	            step: function () {
	                $this.text(Math.floor(this.countNum) + "%");
	            },
	            complete: function () {
	            	if (this.countNum.toLocaleString().includes(',')) {
	                	$this.text(this.countNum.toLocaleString() + "%");
	                } else{
	                	//$this.text(this.countNum.toLocaleString());
	                	$this.text(this.countNum.toLocaleString() + "%");
	                }
	            }
	        });
		});
	}
	
 </script>
 