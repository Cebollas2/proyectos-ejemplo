[#assign folderNode = cmsfn.contentById(content.carpeta, "rsc-contactos")]
[#if folderNode??]	
	[#assign contactos = cmsfn.children(folderNode,"rsc-contacto")]
		<section class="cmp-contacto">
	    <h2 class="title">${content.titulo!}</h2>
	    <div class="wrapper-box">
			[#list contactos as contacto]
				[#assign permitido = true]
				[#if contacto.roles?? && contacto.roles?has_content]
					[#assign permitido = model.hasPermissions(contacto.roles)]
				[/#if] 
				[#if permitido]
					<div class="box ${contacto.tamano!}">	            	
		            	<div class="content-img">
	                    	[#if contacto.foto?has_content]
								<img src="${damfn.getAsset(contacto.foto!).link!}" alt="contacto"/>							    		
							[/#if]
	                	</div>
		                <div class="content-text">
		                    <div class="name">${contacto.nombre!}</div>
		                    <div class="cargo">${cmsfn.wrapForI18n(contacto).cargo!""}</div>
		                    <address><a href="mailto:${contacto.email}">${contacto.email}</a></address>
		                    [#if contacto.documento?has_content]
								<a href="${cmsfn.link(cmsfn.contentById(contacto.documento, 'dam'))}" class="pdf" target="_blank">	
									Ver PDF Área
								</a>		    		
							[/#if]
		                </div>	            	
		         	</div>
		         [/#if]
			[/#list]
			[#if contactos?size==0]
				<div>	
					${i18n['cione-module.global.no-data']}
				</div>	
			[/#if]
		</div>
	</section>    
[/#if]


