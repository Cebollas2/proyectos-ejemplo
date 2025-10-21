[#assign folderDescargas = cmsfn.nodeByPath("/descargas", "rsc-documentos")]
[#if folderDescargas??]
	<section class="cmp-rsc-documentos-minimal">
		<h2 class="title">${folderDescargas.name!} </h2>
		[#assign listFolder = model.getFolders()]
		[#list listFolder as folder]
			[#assign permitido = true]
			[#if folder.roles?? && folder.roles?has_content]
				[#assign permitido = model.hasPermissions(folder.roles)]
			[/#if] 
			[#if permitido]
				[#assign folderContent = cmsfn.contentById(folder, "rsc-documentos")]
				[#assign folderNode = cmsfn.nodeById(folder, "rsc-documentos")]
				[#if folderContent??]	
					<h2 class="title">${folderNode.name!}</h2>
					<div class="wrapper-box">
						[#assign documentos = cmsfn.children(folderContent,"rsc-documento")]
						[#list documentos as documento]
							[#if cmsfn.wrapForI18n(documento).documento?? && cmsfn.wrapForI18n(documento).documento?has_content]
								[#assign asset = cmsfn.contentById(cmsfn.wrapForI18n(documento).documento!"", "dam")]
								<div class="box" style="max-width: 180px; max-height: 180px;">
					            	<h3>${cmsfn.wrapForI18n(documento).titulo!""}</h3>
					            	<a href="${cmsfn.link(cmsfn.contentById(cmsfn.wrapForI18n(documento).documento!"", "dam"))}" target="_blank" [#if asset?has_content && asset.audit?has_content && asset.audit]onclick="auditDocument('${asset.@id}')"[/#if]>
						                <div class="content-img">
							                [#if documento.imagen?has_content]
												<img src="${damfn.getAsset(documento.imagen!).link!}" alt="documento"/>							    		
											[/#if]                
						                </div>
					                </a>
					         	</div>
					        [#elseif cmsfn.wrapForI18n(documento).externalLink?? && cmsfn.wrapForI18n(documento).externalLink?has_content]
				            	<div class="box" style="max-width: 180px; max-height: 180px;">
					            	<h3>${cmsfn.wrapForI18n(documento).titulo!""}</h3>
					            	<a href="${cmsfn.wrapForI18n(documento).externalLink}" target="_blank">
						                <div class="content-img">
							                [#if documento.imagen?has_content]
												<img src="${damfn.getAsset(documento.imagen!).link!}" alt="documento"/>							    		
											[/#if]                
						                </div>
					                </a>
					         	</div> 
				            [#else]
				             	<div class="box" style="max-width: 180px; max-height: 180px;">
					            	<h3>${cmsfn.wrapForI18n(documento).titulo!""}</h3>
					            	<a href="#" target="_blank">
						                <div class="content-img">
							                [#if documento.imagen?has_content]
												<img src="${damfn.getAsset(documento.imagen!).link!}" alt="documento"/>							    		
											[/#if]                
						                </div>
					                </a>
					         	</div> 
					        [/#if]
						[/#list]
					</div>
				[/#if]
			[/#if]
		[/#list]
		
	</section>
[/#if]

