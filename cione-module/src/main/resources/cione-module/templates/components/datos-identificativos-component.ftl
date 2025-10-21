<section>
<div class="cmp-datosidentificativos mobile-wrapper">

	<ul class="accordion-mobile">
		<li><a class="toggle" href="javascript:void(0);">
			<div class="title">
				${i18n['cione-module.templates.components.datos-identificativos-component.change-pwd']}<i
					class="fa fa-chevron-right rotate"> </i>
			</div>
			</a>
			<ul class="inner" style="display: none;">
				<li></li>
			</ul>
		</li>
	</ul> 
    <div class="wrapper">
        <div class="title">${i18n['cione-module.templates.components.datos-identificativos-component.identifying-data']}</div>
        <form class="data-panel inbox">
        <div class="title-mobile">${i18n['cione-module.templates.components.datos-identificativos-component.identifying-data']}</div>
            <div class="item half">
                <label>${i18n['cione-module.templates.components.datos-identificativos-component.partner-id']}</label>
                <input class="form-control" type="text" value="${model.getData().getCliente()!}" disabled>
            </div>
            <div class="item half">
                <label>${i18n['cione-module.templates.components.datos-identificativos-component.partner']}</label>
                <input class="form-control" type="text" value="${model.getData().getNumSocio()!}" disabled>
            </div>
            <div class="item">
                <label>${i18n['cione-module.templates.components.datos-identificativos-component.partner-name']}</label>
                <input class="form-control" type="text" value="${model.getData().getNombreComercial()!}" disabled>
            </div>
            <div class="item half">
                <label>${i18n['cione-module.templates.components.datos-identificativos-component.razon-social']}</label>
                <input class="form-control" type="text" value="${model.getData().getRazonSocial()!}" disabled>
            </div>
            <div class="item half">
                <label>${i18n['cione-module.templates.components.datos-identificativos-component.nif-cif']}</label>
                <input class="form-control" type="text" value="${model.getData().getNif()!}" disabled>
            </div>
            <div class="item">
                <label>${i18n['cione-module.templates.components.datos-identificativos-component.address']}</label>
                <input class="form-control" type="text" value="${model.getData().getDireccion()!}" disabled>
            </div>
            <div class="item half">
                <label>${i18n['cione-module.templates.components.datos-identificativos-component.codigo-postal']}</label>
                <input class="form-control" type="text" value="${model.getData().getCodigoPostal()!}" disabled>
            </div>
            <div class="item half">
                <label>${i18n['cione-module.templates.components.datos-identificativos-component.poblacion']}</label>
                <input class="form-control" type="text" value="${model.getData().getPoblacion()!}" disabled>
            </div>
            <div class="item half">
                <label>${i18n['cione-module.templates.components.datos-identificativos-component.provincia']}</label>
                <input class="form-control" type="text" value="${model.getData().getProvincia()!}" disabled>
            </div>
            <div class="item half">
                <label>${i18n['cione-module.templates.components.datos-identificativos-component.pais']}</label>
                <input class="form-control" type="text" value="${model.getData().getPais()!}" disabled>
            </div>
            <div class="item half">
                <label>${i18n['cione-module.templates.components.datos-identificativos-component.phone']}</label>
                <input class="form-control" type="text" value="${model.getData().getTelefono()!}" disabled>
            </div>
            <div class="item half">
                <label>${i18n['cione-module.templates.components.datos-identificativos-component.mobile']}</label>
                <input class="form-control" type="text" value="${model.getData().getMovil()!}" disabled>
            </div>
            <div class="item">
                <label>${i18n['cione-module.templates.components.datos-identificativos-component.email']}</label>
                <input class="form-control" type="text" value="${model.getData().getEmail()!}" disabled>
            </div>
            <div class="item">
                <label>${i18n['cione-module.templates.components.datos-identificativos-component.persona-contacto']}</label>
                <input class="form-control" type="text" value="${model.getData().getPersonaContacto()!}" disabled>
            </div>

        </form>
    </div>  
    
    <#if model.isPartner()>
	    <div class="wrapper panel-filter">
	
	        <div class="title">${i18n['cione-module.templates.components.datos-identificativos-component.change-pwd']}</div>
	        <div class="texto-info">${i18n['cione-module.templates.components.datos-identificativos-component.step-1']}</div>
	        <form id="change-pwd-form">
	            <div class="item">
	                <label>${i18n['cione-module.templates.components.datos-identificativos-component.current-password']}</label>
	                <div class="password">
	                    <input id="change-pwd-current-password" type="password" class="form-control" name="password" value="" required>                    
	                    <span toggle="#change-pwd-current-password" class="fa fa-fw fa-eye field-icon toggle-password showpassword"></span>
	                    <span id="change-pwd-current-password-error" class="error">                    	      
	                    </span>
	                </div>                
	            </div>
	            <div class="texto-info">${i18n['cione-module.templates.components.datos-identificativos-component.step-2']}</div>
	            <div class="box">            	
	                <div class="item">
	                    <label>${i18n['cione-module.templates.components.datos-identificativos-component.new-password']}</label>
	                    <div class="password">
	                    	<input id="change-pwd-new-password" type="password" class="form-control" name="password" value="" required>                    
	                    	<span toggle="#change-pwd-new-password" class="fa fa-fw fa-eye field-icon toggle-password showpassword"></span>
	                    </div>	                                        
	                </div>
	                <div class="item">
	                    <label>${i18n['cione-module.templates.components.datos-identificativos-component.confirm-password']}</label>
	                    <div class="password">
	                    	<input id="change-pwd-confirm-password" type="password" class="form-control" name="password" value="" required>                    
	                    	<span toggle="#change-pwd-confirm-password" class="fa fa-fw fa-eye field-icon toggle-password showpassword"></span>
	                    </div>
	                    <span id="change-pwd-confirm-password-error" class="error"></span>
	                </div>                
	                <div class="panel-button">
	                   	<button id="change-pwd-btn" class="" type="submit" onclick="sendChangePwd(); return false">
	                   		<label>${i18n['cione-module.templates.components.datos-identificativos-component.modify']}</label>
	                   	</button>
	                </div>                
	            </div>
	        </form>
	        <div id="change-pwd-result" class="text-success">
	        	
	        </div>
	        
	    </div>
    </#if> 
      
</div>
</section>


<style>	
	input.error{
		border: 1px solid red;
	}
	span.error{
		color:red;
	}	
</style>


<script>

	function resetChangePwd(){		
		$("#change-pwd-current-password-error").html("");
		$("#change-pwd-current-password").removeClass("error");
		$("#change-pwd-confirm-password-error").html("");
		$("#change-pwd-confirm-password").removeClass("error");		
		$("#change-pwd-new-password").removeClass("error");
	}
	
	function sendChangePwd(){	
		resetChangePwd();
							
		var oldPwd = $("#change-pwd-current-password").val();
		var newPwd = $("#change-pwd-new-password").val();
		var confirmPwd = $("#change-pwd-confirm-password").val();
		
		
		if(oldPwd == "" || newPwd == "" || confirmPwd == ""){
			//alert("Por favor, rellene todos los campos");
			return;			
		}
		
		var data = {
			username: CURRENT_USER,
			oldPassword: oldPwd,
			newPassword: newPwd,
			confirmPassword: confirmPwd
		};
		
		$("#loading").show();
		$("#change-pwd-btn").attr("disabled","disabled");
		$.ajax({
			url: PATH_API + "/private/my-data/v1/change-password" + "?lang=" + LANG,
	        type: "POST",
	        data: JSON.stringify(data),
	        contentType: 'application/json; charset=utf-8',
	        cache: false,
	        dataType: "json",	        
	        success: function(response){	        	
	        	$("#loading").hide();	        	
	        	var successText = "${i18n['cione-module.templates.components.datos-identificativos-component.change-password-success']}";	        	
	        	$("#change-pwd-result").html(successText);	        	
	        },
		 	error: function(response){
		 		
		 		var error = response.responseText.split("##");	
		 		var codError = error[0];
		 		var msgError = error[1];
		 			 		
		 		if(codError==1){
		 			//alert("error contraseña actual");
		 			$("#change-pwd-current-password-error").html("<p>" +  msgError + "</p>");
		 			$("#change-pwd-current-password").addClass("error");
		 		}else{
		 			//alert("error nueva contraseña");
		 			$("#change-pwd-confirm-password-error").html("<p>" +  msgError + "</p>");
		 			$("#change-pwd-new-password").addClass("error");
		 			$("#change-pwd-confirm-password").addClass("error");
		 		} 				 			 		
		 		//$("#change-pwd-result").html("<p>" +  response.responseJSON.error.message + "</p>");
		 	},
		 	complete: function(response){
		 		$("#loading").hide();
		 		$("#change-pwd-form")[0].reset();
		 		$("#change-pwd-btn").removeAttr("disabled");		 		
		 	}
	    });
		
		return false;
	}
</script>