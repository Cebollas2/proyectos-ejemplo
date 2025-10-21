[#assign noticiasRoot = cmsfn.contentByPath("/", "noticias")]
[#assign noticias = ""]
[#assign carpetas = cmsfn.children(noticiasRoot,"mgnl:folder")!]
[#assign userId = ctx.getUser().getIdentifier()]

<style>
	a.flag:before {text-decoration:underline; display:inline-block;}
	a.flag:before,
	a:hover:before {text-decoration:none;}
	
	a.flag:before {
	  margin-right: 5px;
	  padding: 2px 3px 1px 3px;
	  color: white;
	  font-size: 13px;
	  border-radius: 3px;
	  font-weight: normal;
	  position:absolute;
	  margin: 10px;
	  z-index: 1;
	}
	
	a.flag.pdf:before {
	  content: "PDF";
	  background-color: #db090a;
	  color: white;
	  text-decoration: none !important;
	}
	
	a.flag.new:before {
	  content: "NEW";
	  background-color: red;
	  color: white;
	}
</style>

<section id="cmp-bloque-noticias" class="cmp-bloquenoticias">
    <h2 class="title">${i18n['cione-module.templates.components.bloque-noticias.bloque-noticias']}</h2>
    <p> ${i18n['cione-module.templates.components.bloque-noticias.descripcion']} </p>
	<div class="wrapper-box">
		[#list carpetas as carpeta]
			[#assign noticias = ""]
			[#assign years = cmsfn.children(carpeta,"mgnl:folder")!]
			[#list years as year]
				[#if !year?has_next]
					[#assign noticias = cmsfn.children(year,"noticia")!]
				[/#if]
			[/#list]
			[#if noticias?? && noticias?has_content]
				[#list noticias as noticia]
					[#if model.hasPermissions(noticia.roles)]
						[#if model.hasUserReadNewGeneral(userId,noticia.@uuid) > 0]
						<div class="box unread" onclick="detalleNoticias('${carpeta.@uuid}','${noticia.nombreBloque!}')">
						[#else]
						<div class="box" onclick="detalleNoticias('${carpeta.@uuid}','${noticia.nombreBloque!}')">
						[/#if]
							[#if cmsfn.editMode]						
								<div style='text-align: right;'>${noticia.descripcionRoles!}</div>
							[/#if]		
							<div class="content-img">						
		        				[#if noticia.imagenDesktop?has_content]
		        					[#if damfn.getAsset(noticia.imagenDesktop!)?? && damfn.getAsset(noticia.imagenDesktop!)?has_content]
										<img src="${damfn.getAsset(noticia.imagenDesktop!).link!}" class="noticias-desktop"/>
									[/#if]
								[/#if]
								[#if noticia.imagenMovil?has_content]
									[#if damfn.getAsset(noticia.imagenMovil!)?? && damfn.getAsset(noticia.imagenMovil!)?has_content]
										<img src="${damfn.getAsset(noticia.imagenMovil!).link!}" class="noticias-movil"/>
									[/#if]
								[#else]
									<img src="${damfn.getAsset(noticia.imagenDesktop!).link!}" class="noticias-movil"/>
								[/#if]		
								
								<span style="color:#${noticia.color!'000'}"> ${noticia.nombreBloque!} </span>										
		    				</div>	
		    				  					
						</div>				
					[/#if]
				[/#list]
			[/#if]
		[/#list]
			
	</div>
	[#if noticias?has_content && noticias?size==0]
		<div>	
			${i18n['cione-module.templates.components.bloque-noticias.empty']}
		</div>	
	[/#if]
</section>
 
 <script>
 	function detalleNoticias(id){
 		var url = "${cmsfn.link("website", content.detailPageLink!)!}?id=" + id;
 		document.location.href = url;
 	}
 </script>