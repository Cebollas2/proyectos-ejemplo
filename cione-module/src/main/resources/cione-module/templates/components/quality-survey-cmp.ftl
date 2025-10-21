[#if model.flagCondiciones]
<div id="modalQualitySurvey" class="modal modal-anuncio" tabindex="-1" role="dialog" style="overflow: auto; z-index:500; display: none;">
    <div class="modal-dialog"  role="document">
      <div class="modal-content">
        <div class="modal-body">        	
        	<div id="modalQualitySurveyContent">
        	
        		<div class="logo">
					<img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/LOGO_CIONE_OK.png" alt="logo">
				</div> 
       	
        		<div class="wrapper-text">
        			<div class="title">${i18n['cione-module.templates.components.quality-surveys.question.q1']}</div>
        			<p>${i18n['cione-module.templates.components.quality-surveys.question.q2']}</p>
        			<p>${i18n['cione-module.templates.components.quality-surveys.question.q3']}</p>
 					 
				</div>
        		
        		
        		<div class="smiley-rating">
        			<label>
					  <input type="radio" name="puntuacion" value="1">
					  <img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/smiley-rating/muyinsatisfecho.svg" style="width:80px;">
					  <span>${i18n['cione-module.templates.components.quality-surveys.respuestas.muy-insatisfecho']}</span>
					</label>
					<label>
					  <input type="radio" name="puntuacion" value="2">
					  <img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/smiley-rating/insatisfecho.svg" style="width:80px;">
					  <span>${i18n['cione-module.templates.components.quality-surveys.respuestas.insatisfecho']}</span>
					</label>
					<label>
					  <input type="radio" name="puntuacion" value="3">
					  <img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/smiley-rating/normal.svg" style="width:80px;">
					  <span>${i18n['cione-module.templates.components.quality-surveys.respuestas.normal']}</span>
					</label>
					<label>
					  <input type="radio" name="puntuacion" value="4">
					  <img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/smiley-rating/satisfecho.svg" style="width:80px;">
					  <span>${i18n['cione-module.templates.components.quality-surveys.respuestas.satisfecho']}</span>
					</label>
					<label>
					  <input type="radio" name="puntuacion" value="5">
					  <img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/smiley-rating/muysatisfecho.svg" style="width:80px;">
					  <span>${i18n['cione-module.templates.components.quality-surveys.respuestas.muy-satisfecho']}</span>
					</label>					        	
        		</div>
        		
        		<div id="puntuacion-error" class="error"></div>
        		<div>
        			<label>${i18n['cione-module.templates.components.quality-surveys.comentarios']}</label>
        			<textarea id="comentario"></textarea>
        		</div>
        		
       		    <div class="panel-button">
			        <div class="wrapper">
			            <a href="javascript:masTarde()">${i18n['cione-module.templates.components.quality-surveys.mas-tarde']}</a>
			            <a href="javascript:cancelar()">${i18n['cione-module.templates.components.quality-surveys.cancelar']}</a>
			        </div>
			        <div class="wrapper">
		                <a id="btn-enviar" href="#" class="disable">${i18n['cione-module.templates.components.quality-surveys.enviar']}</a>
			        </div>
   				</div>
        	</div>
        </div>
      </div>
    </div>
</div>




<script>
	setTimeout(doQuestion, 1000);
	selected = false;
	
	function activateBtnEnviar(){
		$("#btn-enviar").removeClass("disable");
		$("#btn-enviar").on("click",aceptar);
	}
	
	function doQuestion(){		
		$('input[type="radio"]').on('change', function(e) {
			if(!selected){
				activateBtnEnviar();
			}		    
		});
		
		if(sessionStorage.hideCioneSurvey){
			return;
		}
		
		var user = "${ctx.user.name}";
		var terminacion = user.substring((user.length - 2), user.length);
		
		if (terminacion == "00") {
			$("#loading").show();
			$.ajax({
				url : PATH_API + "/private/quality-surveys/v1/do-question",
				type : "GET",
				contentType : 'application/json; charset=utf-8',
				cache : false,
				dataType : "json",
				success : function(response) {
					if(response == true){
						$("#modalQualitySurvey").show();
					} else {
						$("#modalQualitySurvey").hide();
					}
				},
				error : function(response) {
					alert("error");				
				},
				complete : function(response) {
					$("#loading").hide();
				}
			});
		}
		
	}
	
	
	function aceptar(){
		$("#btn-enviar").addClass("disable");
		var puntuacion = $('input[name=puntuacion]:checked').val();
		var comentario = $("#comentario").val();
		if(!puntuacion){
			$("#puntuacion-error").html("Campo obligatorio");
			return;
		}
		sendQuestion(puntuacion,comentario);
	}
	
	
	function cancelar(){
		var puntuacion = 0;
		var comentario = $("#comentario").val();
		sendQuestion(puntuacion,comentario); 		
	}
	
	function sendQuestion(puntuacion,comentario){
		
		var survey = {
			"codSocio" : "${ctx.user.name}",
			"socio" : "${ctx.user.getProperty('title')}",
			"pregunta" : "Nivel satisfacción",
			"puntuacion" : 	puntuacion,
			"comentario" : comentario				
		};
			
		$("#loading").show();		
		$.ajax({
			url: PATH_API + "/private/quality-surveys/v1/save",
	        type: "POST",
	        //cache: false,
	        data : JSON.stringify(survey),
			contentType : 'application/json; charset=utf-8',
	        dataType: "json",	        
	        success: function(response){	        	
	        	resetSurvey();
	        	var text = "<div class='success'>";
	        	if(puntuacion == 0){
	        		text += "<p>${i18n['cione-module.templates.components.quality-surveys.resultado-ok2.title']}</p>";
		        	text += "<p>${i18n['cione-module.templates.components.quality-surveys.resultado-ok2.text']}</p>";
	        	}else{
	        		text += "<p>${i18n['cione-module.templates.components.quality-surveys.resultado-ok.title']}</p>";
		        	text += "<p>${i18n['cione-module.templates.components.quality-surveys.resultado-ok.text']}</p>";
	        	}
	        	text += "<div class='panel-button'><a href='javascript:closeModalQualitySurvey()'>Cerrar</a></div>";
	        	text += "</div>";
	        	$("#modalQualitySurveyContent").html(text);	        	
	        },
		 	error: function(response){			 		 		
		 		var text = "<div class='error'>";
	        	text += "<p>${i18n['cione-module.templates.components.radio-busqueda.resultado-ko.title']}</p>";
	        	text += "<p>${i18n['cione-module.templates.components.radio-busqueda.resultado-ko.text']}</p>";
	        	text += "<div class='panel-button'><a href='javascript:closeModalQualitySurvey()'>Cerrar</a></div>";
	        	text += "</div>";
	        	$("#modalQualitySurveyContent").html(text);		        	
	        	
		 	},
		 	complete: function(response){
		 		$("#loading").hide();	
		 		//clearAnuncioForm();
		 	}
	    });			
			
			
	}		
	
	function masTarde(){
		sessionStorage.hideCioneSurvey = 'true';
		$("#modalQualitySurvey").hide();
	}
	
	function resetSurvey(){
		$("input.error").each(function() {			
			$(this).removeClass("error");
		});		
		$("span.error").each(function() {
			$(this).empty();			
		});		
	}
	
	function closeModalQualitySurvey(){
		$("#modalQualitySurvey").hide();
	}
	
</script>
[/#if]
