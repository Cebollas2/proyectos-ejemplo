 
[#list cmsfn.children(content.formularios) as formulario]
	<div class="item">
		[#if formulario.title?? && formulario.title?has_content]
			<label class="title">
				${formulario.title}
			</label>
		[/#if]
		<ul>
	    	[#list cmsfn.children(formulario.documentos) as documento]
	    		[#if ctx.getUser().hasRole("superuser") || !documento.campana?? || model.getUserCatalogosCampana() == documento.campana]
		    		<li>		    			
		    			<span>
		    				<a href="${cmsfn.link(cmsfn.contentById(documento.targetNode , "dam"))}">
		    					${documento.title}
		    					<span class="icon documento" style="margin-left:2px"></span>
		    				</a>
		    			</span>
		    			[#if cmsfn.editMode]						
							<span style='color:red;margin-left:10px'>${documento.campana!}</span>
						[/#if]		    						
		    		</li>						
	    		[/#if]
			[/#list]
		</ul>
	</div>
[/#list]
