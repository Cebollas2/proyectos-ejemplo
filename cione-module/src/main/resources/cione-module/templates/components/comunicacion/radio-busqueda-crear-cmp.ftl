<section class="cmp-radiobusquedaform">
	<div class="wrapper">
	    <h2 class="title">${i18n['cione-module.templates.components.radio-busqueda.radio-busqueda']}</h2>
	    <p> ${i18n['cione-module.templates.components.radio-busqueda.descripcion2']} </p>
	    
	    <div id="result" class="result" style="display:none"></div>
	    
	    <div>
	    	<form class="data-panel inbox">
	    		<fieldset>
	    			<input type="hidden" id="to" value="${content.destinatarios!}"/>                    
                    <input type="hidden" id="checksum" value="${content.destinatarios!?length}"/>
	    		
			    	<div class="item">
			    		<label for="titulo">
			    			${i18n['cione-module.templates.components.radio-busqueda.titulo-anuncio']}
			    		 	<!-- <dfn title="required">*</dfn> -->
			    		</label>
			    		<input id="titulo" type="text" class="required form-control" >
			    		<span id="titulo-error" class="error"></span>
			    	</div>
			    	
			    	<div class="item">
			    		<label for="texto">
			    			${i18n['cione-module.templates.components.radio-busqueda.anuncio']}
			    			<!-- <dfn title="required">*</dfn> -->
			    		</label>
			    		<textarea id="texto" type="text" class="required form-control" rows="10" maxlength="500"></textarea>
			    		<span id="texto-error" class="error"></span>
			    	</div>
			    	
			    	<div class="panelbuttons">
		                  <div class="item">
								<label for="email">
	                                ${i18n['cione-module.templates.components.radio-busqueda.email']}
	                                <!-- <dfn title="required">*</dfn> -->
		                        </label>
		                        <input id="email" type="email" class="required email form-control">
		                        <span id="email-error" class="error"></span>
		                   </div>	
		                   <a href="#" onclick="crearAnuncio(); return false;"> ${i18n['cione-module.templates.components.radio-busqueda.enviar']}</a>
		             </div>
	    		<fieldset>  	    	
	    	</form>	  
	    </div>
    </div>
</section>

<script>

	function clearAnuncioForm(){
		$("#titulo").val("");
		$("#texto").val("");
		$("#email").val("");
	}

	function resetAnuncioForm(){
		$("input.error").each(function() {			
			$(this).removeClass("error");
		});
		$("textarea.error").each(function() {			
			$(this).removeClass("error");
		});
		$("span.error").each(function() {
			$(this).empty();			
		});		
	}
	
	function validateAnuncio(){
		var result = true;		
		$(".required").each(function(index,inputRequired){			
			if($(inputRequired).val().trim() == ""){
				$(inputRequired).addClass("error");				
				$("#"+ inputRequired.id + "-error").html("Campo obligatorio");
				result = false;
			}			
		})
		
		$(".email").each(function(index,input){
			var email = $(input).val().trim();
			if( email != "" && !validateEmail(email)){
				$(input).addClass("error");				
				$("#"+ input.id + "-error").html("Formato de email incorrecto");
				result = false;
			}			
		})
		
		return result;
	}
	
	function crearAnuncio(){	
		resetAnuncioForm();
		if(!validateAnuncio()){
			return;
		}
		
		var anuncio = {
			"titulo" : 	$("#titulo").val(),
			"texto" : 	$("#texto").val(),
			"fecha" : 	$("#fecha").val(),
			"email" : $("#email").val(),
			"to" : $("#to").val(),
			"checksum" : $("#checksum").val()
		};
		
		
		$("#loading").show();		
		$.ajax({
			url: PATH_API + "/private/radio-busqueda/v1/radiobusquedas?lang=" + LANG,
	        type: "POST",
	        //cache: false,
	        data : JSON.stringify(anuncio),
			contentType : 'application/json; charset=utf-8',
	        dataType: "json",	        
	        success: function(response){
	        	resetAnuncioForm();
	        	var text = "<div class='success'>";
	        	text += "<p>${i18n['cione-module.templates.components.radio-busqueda.resultado-ok.title']}</p>";
	        	text += "<p>${i18n['cione-module.templates.components.radio-busqueda.resultado-ok.text']}</p>";
	        	text += "</div>";
	        	$("#result").html(text);
	        	$("#result").show();
	        	
	        },
		 	error: function(response){			 		
		 		var text = "<div class='error'>";
	        	text += "<p>${i18n['cione-module.templates.components.radio-busqueda.resultado-ko.title']}</p>";
	        	text += "<p>${i18n['cione-module.templates.components.radio-busqueda.resultado-ko.text']}</p>";
	        	text += "</div>";
	        	$("#result").html(text);	
	        	$("#result").show();
		 	},
		 	complete: function(response){
		 		$("#loading").hide();	
		 		clearAnuncioForm();
		 	}
	    });			
	}
	
	
</script>