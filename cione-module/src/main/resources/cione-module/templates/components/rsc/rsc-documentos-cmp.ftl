[#assign folderNode = cmsfn.contentById(content.carpeta, "rsc-documentos")]
[#if folderNode??]	
	[#assign documentos = cmsfn.children(folderNode,"rsc-documento")]
		<section class="cmp-politicas">
	    <h2 class="title">${content.titulo!}</h2>
	    <div class="wrapper-box">
			[#list documentos as documento]
				[#assign permitido = true]
				[#if documento.roles?? && documento.roles?has_content]
					[#assign permitido = model.hasPermissionsRoles(documento.roles)]
				[/#if] 
				[#if permitido]
					<div class="box">
		                <div class="content-img">
			                [#if documento.imagen?has_content]
								<img src="${damfn.getAsset(documento.imagen!).link!}" alt="documento"/>							    		
							[/#if]                
		                </div>
		                <p class="despription">
		      				${cmsfn.wrapForI18n(documento).texto!""}
						</p>
						[#if cmsfn.wrapForI18n(documento).documento?? && cmsfn.wrapForI18n(documento).documento?has_content]
							[#assign asset = cmsfn.contentById(cmsfn.wrapForI18n(documento).documento!"", "dam")]
							<div class="content-text">
			                	<a href="${cmsfn.link(cmsfn.contentById(cmsfn.wrapForI18n(documento).documento!"", "dam"))}" target="_blank" [#if asset?has_content && asset.audit?has_content && asset.audit]onclick="auditDocument('${asset.@id}')"[/#if]>
			                    	${i18n['cione-module.global.btn-download']}
			                    </a>
			                    <span class="icon documento"></span>
			                </div>
			            [/#if]
			            [#if documento.externalLink?? && documento.externalLink?has_content]
		                	<div class="content-text">
			                	<a href="${cmsfn.wrapForI18n(documento).externalLink!}" target="_blank">
			                    	${i18n['cione-module.global.btn-ver']}
			                    </a>
			                </div>
		                [/#if]
		                [#if documento.internalLink?? && documento.internalLink?has_content]
		                	<div class="content-text">
			                	<a href="${cmsfn.link("website", documento.internalLink!)}" target="_blank">
			                    	${i18n['cione-module.global.btn-ver']}
			                    </a>
			                </div>
		                [/#if]
			            [#if documento.especialLink?? && documento.especialLink?has_content]
			            	[#assign field = documento.especialLink]
			            	[#switch field]
			            		[#case "enlaceUniversity"]
			            			[#if !ctx.getUser().hasRole("empleado_cione_cione_university")]
										[#assign href = documento.especialLinkenlaceUniversity!]
				            			<div class="content-text">
						                	<a href="#" onClick="updateUniversityUser('${href}')" target="_blank">
						                    	${i18n['cione-module.global.btn-ver']}
						                    </a>
						                </div>
					                [/#if]
			            		[#break]
			            	[/#switch]
		                [/#if]
		                
		         	</div>
		         [/#if]
			[/#list]
			[#if documentos?size==0]
				<div>	
					${i18n['cione-module.global.no-data']}
				</div>	
			[/#if]
		</div>
	</section>    
[/#if]


