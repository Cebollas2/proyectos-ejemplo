<section class="cmp-login">

    [#include "../includes/macros/public-logo.ftl"]
   
    [#assign isOpticaPRO = false]
    [#assign site = sitefn.site(content)]
    [#if site.name == "opticapro"]
    	[#assign isOpticaPRO = true]
    [/#if]
    
    <form class="form-login">
        <h2 class="title [#if isOpticaPRO]opticapro[/#if]">${i18n['cione-module.templates.components.recovery-pwd-component.title']}</h2>
        <div class="subtitle [#if isOpticaPRO]opticapro[/#if]"> 
            <p>${i18n['cione-module.templates.components.recovery-pwd-component.subtitle']}</p>
          </div>
        <div id="register-fields" class="item">
            <label class="d-flex" for="register-id">${i18n['cione-module.templates.components.recovery-pwd-component.username']}</label>
            <input id="register-id" type="text" required="required">
        </div>
        <span id="register-error" class="error">        	
        </span>
     
        <button id="register-btn-send" class="[#if isOpticaPRO]btn-green[#else]btn-blue[/#if]" onclick="sendRecovery(); return false">${i18n['cione-module.templates.components.recovery-pwd-component.btn-ok']}</button>
        <button id="register-btn-back" class="[#if isOpticaPRO]btn-green[#else]btn-blue[/#if]" onclick="goToLogin(); return false" style="display:none">${i18n['cione-module.templates.components.recovery-pwd-component.btn-back']}</button>
    </form>
    

</section>


<script>
	
	function goToLogin(){
		window.location.replace("${cmsfn.link("website", content.returnPageLink!)!}");
	}

	function sendRecovery(){			
		var registerId = $("#register-id").val();
		
		if((registerId == "") || (registerId.length < 2)){
			return;
			//alert("Es necesario rellenar el campo id usuario");
		}
		
		//comprobación para desabilitar codigos de socio de empleados
		var terminacion = registerId.substring((registerId.length - 2), registerId.length);
		if (terminacion != "00") {
			$("#register-error").html("<p> ${i18n['cione-module.templates.components.login-component-pur-cione.error-login-empleado']} </p>");
			return;
		}
		
		$("#loading").show();
		$("#register-btn-send").attr("disabled","disabled");
		$.ajax({
			url: PATH_API + "/auth/v1/recovery-password?id=" + registerId + "&lang=" + LANG,
	        type: "GET",
	        cache: false,
	        dataType: "json",	        
	        success: function(response){
	        	//alert("Verifique su email");
	        	$("#loading").hide();
	        	$("#register-fields").hide();
	        	$("#register-btn-send").hide();
	        	$("#register-error").hide();
	        	$("#register-btn-back").show();
	        	var successText = "${i18n['cione-module.templates.components.recovery-pwd-component.text-success']}";
	        	$(".subtitle").html(successText);	        	
	        },
		 	error: function(response){			 		
		 		//alert(response.responseJSON.error.message);		 		
		 		$("#loading").hide();
		 		$("#register-error").html("<p>" +  response.responseText + "</p>");
		 	},
		 	complete: function(response){
		 		$("#loading").hide();
		 		$("#register-btn-send").removeAttr("disabled");
		 	}
	    });
		
		return false;
	}
</script>
