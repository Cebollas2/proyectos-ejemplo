[#assign temasRoot = cmsfn.contentByPath("/", "encuestas")]
[#assign temas = cmsfn.children(temasRoot,"encuesta")!]
[#assign hayTemas = false]

<section class="cmp-bloque-encuestas">
    <h2 class="title">${i18n['cione-module.templates.components.encuestas.encuestas']}</h2>
    <p> ${i18n['cione-module.templates.components.encuestas.descripcion']} </p>    
    
    <div>
    [#list temas as tema]    	    	
		[#if model.hasPermissions(tema.roles)]
			[#assign hayTemas = true]
			<div class="wrapper-encuesta">
				<div class="title-encuesta">${tema.tema!}</div>
				[#assign encuestas =  cmsfn.children(tema.encuestas)]
				[#if encuestas?size==0]
					<span>	
						${i18n['cione-module.templates.components.encuestas.empty-encuestas']}
					</span>
				[/#if]
				[#list encuestas?sort_by("fecha")?reverse as encuesta]
					<div>
						<span>${encuesta.titulo!}</span>						
						<a href="${encuesta.enlace!}" target="_blank">
							<img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/iconos/icon-link.svg" alt="ver">
						</a>
						<time>
							${i18n['cione-module.templates.components.encuestas.fecha-publicacion']}
							${encuesta.fecha?string["dd/MM/yyyy"]}
						</time>
					</div>				
				[/#list]
			</div>
		[/#if]
    [/#list]
    </div>
    
    [#if !hayTemas]
		<div>	
			${i18n['cione-module.templates.components.encuestas.empty']}
		</div>	
	[/#if]
    
</section>    