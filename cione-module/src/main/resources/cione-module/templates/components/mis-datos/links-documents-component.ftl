
[#list cmsfn.children(content.formularios) as formulario]
	[#if formulario.text?? && formulario.text?has_content]

		<ul>
			<li>
				<span>
	    			<label>${formulario.text}
	    				[#if formulario.mailto?? && formulario.mailto?has_content]
	    					[#if formulario.subject?? && formulario.subject?has_content]
		    					<a href="mailto:${formulario.mailto}?Subject=${formulario.subject}">${formulario.mailto}</a>
		    				[#else]
		    					<a href="mailto:${formulario.mailto}">${formulario.mailto}</a>
		    				[/#if]
	    				[/#if]
	    			</label>
	    		</span>
	    	</li>
		</ul>
	
	[/#if]
[/#list]

[#--             

<a href="mailto:${formulario.mailto}?Subject="${formulario.subject}>${formulario.mailto}</a>
<div class="item">
                <p>Para canjear tus €Cione envía un email a  <a href="mailto:">pedidos@cione.es</a></p>
            </div> --]

