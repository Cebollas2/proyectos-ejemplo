[#if !cmsfn.editMode && model.flagCondiciones]
	[#assign campaignId = ""]
	[#assign hasPermissions = false]
	[#if cmsfn.children(content)?has_content]
		[#list cmsfn.children(content) as campaing]
			[#if campaing.permisos?? && campaing.permisos?has_content]
				[#assign field = campaing.permisos.field!]
				[#switch field]
					[#case "roles"]
						[#if model.hasPermissionsRoles(campaing.permisos.roles!)]
							[#assign hasPermissions = true]
						[/#if]
						[#break]
						
					[#case "campaing"]
						[#if model.hasPermissionsCampana(campaing.permisos.campaing!)]
							[#assign hasPermissions = true]
						[/#if]
						[#break]
						
					[#default]
						[#break]
				[/#switch]
			[#else]
				[#assign hasPermissions = true]
			[/#if]
			[#if hasPermissions]
				[#assign content = campaing]
				[#assign campaignId = content.campaignId]
				[#break]
			[/#if]
		[/#list]
	[/#if]

	[#if hasPermissions]
		[#assign fInicio = (content.fechaInicio?datetime)!""]
		[#assign fFin = (content.fechaFin?datetime)!""] 
		[#if fInicio?? && fFin?? && .now > fInicio && fFin > .now && hasPermissions]
		<div class="cmp-popup">
		  <!-- Modal -->
		  <div class="modal fade" id="campaignModal" tabindex="-1" role="dialog" aria-labelledby="campaignModalLabel" aria-hidden="true" style="max-height: 500px;">
		    <div class="modal-dialog" role="document">
		      <div class="modal-content" style="overflow-y: hidden;overflow-x: hidden;">
		      	[#if content.logo?? && content.logo?has_content]
			        <div class="modal-header">
			          <div class="logo">
			          [#if content.logo?? && content.logo?has_content]
			              <img src="${damfn.getAssetLink("jcr:" + content.logo)!''}" alt="">
			          [/#if]
			          </div>
			        </div>
			    [#else]
			    	<div class="modal-header">
			          <div class="logo">
			            [#assign titleTextColor = "#00609c"]
			            [#if content.title?? && content.title?has_content]
					    	[#if content.titleTextColor?? && content.titleTextColor?has_content]
					    		[#assign titleTextColor = '#' + content.titleTextColor]
					    	[/#if]
				    		<h3 class="titulo" style="font-size: 36px; color: ${titleTextColor}; font-weight: bold;">${content.title}</h3>
				    	[/#if]
				      </div>
			        </div>
			    [/#if]
			    
			    [#if content.enlacebanner?? && content.enlacebanner?has_content]
			    	<a href="#" onclick="enviarRespuesta('${campaignId}', '${content.enlacebanner!}')" >
			    		[#if content.backgroundImage?? && content.backgroundImage?has_content]
				          [#--  --assign backgroundImage = damfn.getAssetLink(content.backgroundImage)!'#'--]
				          [#assign backgroundImage = damfn.getAssetLink("jcr:" + content.backgroundImage)!""]
				        [/#if]
			    		<img src="${backgroundImage}" />
			    	</a>
			    [#else]
			        [#assign backgroundImage = "#"]
			        [#if content.backgroundImage?? && content.backgroundImage?has_content]
			          [#--  --assign backgroundImage = damfn.getAssetLink(content.backgroundImage)!'#'--]
			          [#assign backgroundImage = damfn.getAssetLink("jcr:" + content.backgroundImage)!""]
			        [/#if]
			        <div class="modal-body modal-body-bg-image" style="background-image:url('${backgroundImage}')">
			          	[#assign color = "#00609c"]
						[#if content.textColor?? && content.textColor?has_content]
							[#assign color = '#' + content.textColor]
						[/#if]
						
				          
			          	[#if content.text?? && content.text?has_content]
			          		[#if content.backgroundColor?? && content.backgroundColor]
			          			<div class="modal-box campaign-text" >
				          			<p>${cmsfn.decode(content).text!""}</p>
				            	</div>
			          		[#else]
			          			<div class="modal-box campaign-text"></div>
			          		[/#if]
				            	
			            [#else]
			            	<div class="modal-box campaign-text" style="visibility: hidden;">
			            	</div>
			            [/#if]
				          
				        
			          
			          <div class="modal-buttons">
			            <div class="buttons-secondary">
			              [#list cmsfn.children(content.secondaryButtons) as button]
			                <style>
			                  #${button.buttonId} {
			                    [#if button.backgroundColor?has_content]
			                      background-color: ${button.backgroundColor};
			                      border-color: ${button.backgroundColor};
			                    [/#if]
			                    [#if button.textColor?has_content]
			                      color: ${button.textColor};
			                    [/#if]
			                  }
			                  #${button.buttonId}:hover {
			                    [#if button.backgroundHoverColor?has_content]
			                      background-color: ${button.backgroundHoverColor}!important;
			                      border-color: ${button.backgroundHoverColor};
			                    [/#if]
			                    [#if button.textHoverColor?has_content]
			                      color: ${button.textHoverColor};
			                    [/#if]
			                  }
			                </style>
			                <button type="button" id="${button.buttonId}" class="btn btn-outline-primary   button-cione-secondary" onclick="enviarRespuesta('${button.buttonId}', '${button.enlace!}')" >${button.buttonTitle}</button>
			              [/#list]
			            </div>
			            <div class="buttons-primary">
			              [#list cmsfn.children(content.primaryButtons) as button]
			                <style>
			                  #${button.buttonId} {
			                    [#if button.backgroundColor?has_content]
			                      background-color: ${button.backgroundColor};
			                      border-color: ${button.backgroundColor};
			                    [/#if]
			                    [#if button.textColor?has_content]
			                      color: ${button.textColor};
			                    [/#if]
			                  }
			                  #${button.buttonId}:hover {
			                    [#if button.backgroundHoverColor?has_content]
			                      background-color: ${button.backgroundHoverColor}!important;
			                      border-color: ${button.backgroundHoverColor}!important;
			                    [/#if]
			                    [#if button.textHoverColor?has_content]
			                      color: ${button.textHoverColor};
			                    [/#if]
			                  }
			                </style>
			                <button type="button" id="${button.buttonId}" class="btn btn-primary button-cione-primary" onclick="enviarRespuesta('${button.buttonId}', '${button.enlace!""}')" >${button.buttonTitle}</button>
			              [/#list]
			            </div>
		          	</div>
		          	[/#if]
		        </div>
		      </div>
		    </div>
		  </div>
		</div>
		<div id="campaignModalBackdrop" class=""></div>
		<script>
		  $(document).ready(function() {
		  	var user = "${ctx.user.name}";
			var terminacion = user.substring((user.length - 2), user.length);
			//
			if (terminacion == "00") {
			  var flagSurvey = true;
			  if($("#modalQualitySurvey").length > 0) {
			      
			      $.ajax({
					url : PATH_API + "/private/quality-surveys/v1/do-question",
					type : "GET",
					async: false,
					contentType : 'application/json; charset=utf-8',
					cache : false,
					dataType : "json",
					success : function(response) {
						if(response == true){
							flagSurvey = false;
						}
					},
					error : function(response) {
						console.log("error");				
					},
					complete : function(response) {
						$("#loading").hide();
					}
				  });
			  }
		      if (flagSurvey) {
		        data = {};
		        data['campaignName'] = "${campaignId}";
		        $.ajax({
		          type: 'POST',
		          url: PATH_API + '/private/campaigns/v1/exists',
		            data: JSON.stringify(data),
		            async: false,
		            contentType: 'application/json; charset=utf-8',
		              success : function(response) {
		                if (!response) {
		                  $('#campaignModal').addClass("show");
		                  $('#campaignModalBackdrop').addClass("modal-backdrop fade show");
		                }
		              }, 
		              error : function(data) {
		            console.log(data);
		          }
		        });
		      }
			   
		    }
		  });
		  
		  function enviarRespuesta(opcion, enlace) {
		  	data = {};
		    data['opcion'] = opcion;
		    data['campaignName'] = "${campaignId}";
		    $.ajax({
		      type: 'POST',
		      url: PATH_API + '/private/campaigns/v1/registrar',
		        data: JSON.stringify(data),
		        async: false,
		        contentType: 'application/json; charset=utf-8',
		          success : function(response) {
		            $('#campaignModal').removeClass("show");
		            $('#campaignModalBackdrop').removeClass("modal-backdrop fade show");
		            if (enlace != ''){ 
		            	updateUniversityCampaing(enlace);
		            }
		          }, 
		          error : function(data) {
		        	console.log(data);
		      	  }
		    });
		  }
		  
		function updateUniversityCampaing(url) {
			getTokenUniversity("https://university.cione.es/");
			if (jwtLoginUniversity != '') {
				loginUniversityPopup("https://university.cione.es/", jwtLoginUniversity, url);
			}
		}
		
		function sleep(ms) {
		  return new Promise(resolve => setTimeout(resolve, ms));
		}
		
		async function loginUniversityPopup(urlUni, jwt, url) {
			try {
				var urlRest = urlUni + "/auth/autologin/login.php?token=" + jwt;
				var openedWindow = window.open(urlRest,'_blank');
				await sleep(500);
				openedWindow.close();
				var openedWindow = window.open(url,'_blank');
				
			} catch (error) {
		    	console.error("Error al realizar las llamadas:", error);
		  	}
			
			
				
		}
		
		
		  $("#closeModal").on("click", function() {
		      $('#campaignModal').removeClass("show");
		      $('#campaignModalBackdrop').removeClass("modal-backdrop fade show");
		  });
		</script>
		[/#if]
	[/#if]
[/#if]