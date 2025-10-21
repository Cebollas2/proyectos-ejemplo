[#if !model.flagCondiciones]
	
	[#if content.listcomponent?has_content]
	    [#list cmsfn.children(content.listcomponent) as listado]
	    	[#if listado.permisos?? && listado.permisos?has_content]
				[#assign field = listado.permisos.field!]
				[#switch field]
		
					[#case "roles"]
						[#if model.hasPermissionsRoles(listado.permisos.roles!)]
							[#assign content = listado]
						[/#if]
						[#break]
					
					[#case "campaing"]
						[#if model.hasPermissionsCampana(listado.permisos.campaing!)]
							[#assign content = listado]
						[/#if]
						[#break]
					[#default]
	    				[#break]
	    		[/#switch]
	    	[#else]
	    		[#assign content = listado]
			[/#if]
		[/#list]
	[/#if]

	[#assign count = 0]
	[#assign countDocuments = 0]
	[#assign mail = content.mail!]
	[#if content.termsList?? && content.termsList?has_content]
		<div id="modalCondiciones" class="modal modal-anuncio" tabindex="-1" role="dialog" style="overflow: auto; z-index:500; display:block">
		    <div class="modal-dialog"  role="document">
		      <div class="modal-content" style="max-height: 600px; !important">
		        <div class="modal-body">   
		        	 	
		        	<div style="display: flex;flex-direction: column;">
		        		<div class="logo">
		        			[#assign link = ""]
		        			[#if content.logo?? && content.logo?has_content]
		        				[#assign link = damfn.getAssetLink("jcr:" + content.logo)!""]
		        			[/#if]
		        			<img align="right" src="${link}" alt="logotipo">
						</div>
						
						<div class="wrapper-text">
							<p style="padding-bottom: 0px;">${content.texto!}</p>
						</div>
		       			
		       			[#if content.termsList?has_content]
		       				[#list cmsfn.children(content.termsList) as terms]
		       					[#if terms.document?? && terms.document?has_content]
			       					[#assign enlace = cmsfn.link(cmsfn.contentById(terms.document , "dam"))]
		       						[#assign count ++]
		       						[#if terms.mandatoryDocument?has_content && terms.mandatoryDocument]
		       							[#assign countDocuments ++]
		       						[/#if]
			       					<div class="panel-button">
								        <div class="wrapper">
							                <a id="btn-ver" href="#" onclick="ver('${enlace}', ${terms?index});">${terms.titleFile!}</a>
								        </div>
					   				</div>
					   			[/#if]
		       				[/#list] 
		       			[/#if]
		       			
						
		   				<div class="panel-button" id="termsBlock" style="display: block;">
		   					
		   					[#if content.termsList?has_content]
			       				[#list cmsfn.children(content.termsList) as terms]
			       					[#if terms.textConditions?? && terms.textConditions?has_content]
				       					<div>
						               		<input id="checkterms-${terms?index}" type="checkbox">
						                	<label for="checkterms-${terms?index}">${terms.textConditions!}</label>
						                </div>
					                [/#if]
			       				[/#list] 
			       			[/#if]
			       			
			       			[#if content.summarytext?? && content.summarytext?has_content]
				            	<div class="panel-button">
				            		<p style="padding-bottom: 0px;">${content.summarytext}</p>
				            	</div>
				            [/#if]
		   					
			                <div class="wrapper" style="padding-top:20px;">
				                <a id="btn-aceptar" href="#" onclick="aceptar();">${i18n['cione-module.templates.components.condiciones-comerciales.aceptar']}</a>
					        </div>
			            </div>
			            
			            <div class="b2b-msg-validation"></div>
			            <div class="b2b-msg-validation2"></div>
		        		
		       		    
		        	</div>
		        </div>
		        
		        
		      </div>
		    </div>
		</div>
	[/#if]
	
	<script>
		var count = ${count};
		var countDocuments = ${countDocuments};
		var mapLinks = new Map();
		var viewdocuments = false;
		function ver(enlace, it) {
			var linkView = mapLinks.get(it);
			window.open(enlace, '_blank');
			if (linkView === undefined) {
				mapLinks.set(it, true);
				if (mapLinks.size == countDocuments){
					viewdocuments = true;
				}
			}
		}
		
		function aceptar(){
			$("btn-aceptar").attr("disabled"); 
			if (validateForm()) {
				var indexed_array = {};
				
				indexed_array["mail"] = "${content.mail!}";
			
				$("#loading").show();
				$("#modalCondiciones").hide();
				$.ajax({
		            url : "${ctx.contextPath}/.rest/auth/v1/acceptTerms",
		            type : "POST",
		            data : JSON.stringify(indexed_array),
		            contentType : 'application/json; charset=utf-8',
		            cache : false,
		            dataType : "json",
		            success : function(response) {
		            
		            },
		            error : function(response) {
		                alert("error");
		                $("#modalCondiciones").show();
		            },
		            complete : function(response) {
		            	$("#loading").hide();
		            }
		        });
		    }
		    
		}
		
		function validateForm() {
			var result= true;
			var msg_obligatorio = "${i18n['cione-module.templates.myshop.direcciones-envio-component.form.msg-obligatorio']}";
			var msg_obligatorio_documentos = "${i18n['cione-module.templates.myshop.direcciones-envio-component.form.msg-obligatorio-doc']}";
			var flag = true
			for (var i=0; i < count; i++) {
				if ($("#checkterms-"+i).prop("checked") !== undefined && (!$("#checkterms-"+i).prop("checked"))) {
					flag = false;
				}
			}
			if (!flag) {
				$('.b2b-msg-validation').addClass("msg-error");
				$('.b2b-msg-validation').text(msg_obligatorio);
				result= false;
			} else {
				$('.b2b-msg-validation').removeClass("msg-error");
				$('.b2b-msg-validation').empty();
			}
			
			if (!viewdocuments && (countDocuments != 0)) {
				$('.b2b-msg-validation2').addClass("msg-error");
				$('.b2b-msg-validation2').text(msg_obligatorio_documentos);
				result= false;
			} else {
				$('.b2b-msg-validation2').removeClass("msg-error");
				$('.b2b-msg-validation2').empty();
			}
			
			return result;
		}
		

		
	</script>
[/#if]
