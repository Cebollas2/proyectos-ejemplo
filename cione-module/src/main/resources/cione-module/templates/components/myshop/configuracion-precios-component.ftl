<section class="b2b-form-precios">
    <form id="configuracion-precios-form">
    	[#assign configuration = model.getPriceDisplayConfiguration()]
    	[#assign rol = model.getPriceDisplayRol()]
    	[#assign user = model.getUser()!]
    	[#assign precioPantallaOculto = true]
    	[#if user?? && user?has_content]
	    	[#assign precioPantallaOculto = user.hasRole("empleado_cione_precio_pantalla")]
	    [/#if]
    	[#assign current = i18n.get('cione-module.templates.configuracion-precios-component.PVO-PVP')]
    	
    	[#if configuration == "pvo"]
    		[#assign current = i18n.get('cione-module.templates.configuracion-precios-component.currently.showing') + i18n.get('cione-module.templates.configuracion-precios-component.PVO')]
    	[#elseif configuration == "pvp"]
    		[#assign current = i18n.get('cione-module.templates.configuracion-precios-component.currently.showing') + i18n.get('cione-module.templates.configuracion-precios-component.PVP')]
    	[#elseif configuration == "hidden"]
    		[#assign current =  i18n.get('cione-module.templates.configuracion-precios-component.currently.hidden')]
    	[#else]
    		[#assign current = i18n.get('cione-module.templates.configuracion-precios-component.currently.showing') + i18n.get('cione-module.templates.configuracion-precios-component.PVO-PVP')]
    	[/#if]
    	
        <h2 class="b2b-h2">${i18n.get('cione-module.templates.configuracion-precios-component.title')}</h2>
        <span id="currently-showing" class="b2b-form-subtitle">${current}</span>
        <div class="b2b-form-radio-wrapper">
            <div class="b2b-form-col">
            
				[#if rol == "pvo" || !precioPantallaOculto]
	                <label class="b2b-form-label-container">
	                    <input type="radio" [#if configuration == "pvo"]checked="checked"[/#if] name="radio" id="pvo">
	
	                    <span>${i18n.get('cione-module.templates.configuracion-precios-component.PVO')}</span>
	                </label>
	
		        [/#if]
		        [#if rol == "pvp" || !precioPantallaOculto]
	                <label class="b2b-form-label-container">
	                    <input type="radio" [#if configuration == "pvp"]checked="checked"[/#if] name="radio" id="pvp">
	
	                    <span>${i18n.get('cione-module.templates.configuracion-precios-component.PVP')}</span>
	                </label>
				[/#if]
	            </div>
	            <div class="b2b-form-col">
	            [#if rol == "pvp-pvo"|| !precioPantallaOculto]
	                <label class="b2b-form-label-container">
	                    <input type="radio" [#if configuration == "pvp-pvo"]checked="checked"[/#if] name="radio" id="pvp-pvo">
	
	                    <span>${i18n.get('cione-module.templates.configuracion-precios-component.PVO-PVP')}</span>
	                </label>
				[/#if]
				[#if rol == "hidden" || !precioPantallaOculto]
	                <label class="b2b-form-label-container">
	                    <input type="radio" [#if configuration == "hidden"]checked="checked"[/#if] name="radio" id="hidden">
	
	                    <span>${i18n.get('cione-module.templates.configuracion-precios-component.hidden')}</span>
	                </label>
                [/#if]
            </div>

        </div>
        <div class="b2b-form-input-container">
            <label>${i18n.get('cione-module.templates.configuracion-precios-component.password')}</label>
            <input class="form-control" id="password" type="password">
            <div class="b2b-msg-passowrd">
            </div>
        </div>

        
		<div class="b2b-button-wrapper">
		    <button class="b2b-button " type="submit">
		        ${i18n.get('cione-module.templates.configuracion-precios-component.accept')}
		    </button>
		</div>
		
    </form>
    
     <script type="text/javascript">
        
        $(document).ready(function () {
	        $("#configuracion-precios-form").submit(function( event ) {
					if(event.isDefaultPrevented()){
					}
					else{
						 $("#loading").show();
						 $.ajax({
						  type: "POST",
						  url: "${ctx.contextPath}/.rest/price-configuration/submitPriceConfigurationForm",
						  data: formToJSON(),
						  headers: {
						    'Accept': 'application/json',
						    'Content-Type': 'application/json'
						  },
						  
						  	success: function(response) {
						  		$("#loading").hide();
						  		if (response.config == "pvo") {
						  			$('#currently-showing').text("${i18n.get('cione-module.templates.configuracion-precios-component.currently.showing')}${i18n.get('cione-module.templates.configuracion-precios-component.PVO')}")
						  			$('#b2b-modifyprices').text("${i18n.get('cione-module.templates.myshop.header-component.modify-prices')} (${i18n.get('cione-module.templates.configuracion-precios-component.PVO')})");
						  		} else if (response.config == "pvp") {
						  			$('#currently-showing').text("${i18n.get('cione-module.templates.configuracion-precios-component.currently.showing')}${i18n.get('cione-module.templates.configuracion-precios-component.PVP')}")
						  			$('#b2b-modifyprices').text("${i18n.get('cione-module.templates.myshop.header-component.modify-prices')} (${i18n.get('cione-module.templates.configuracion-precios-component.PVP')})");
						  		} else if (response.config == "pvp-pvo") {
						  			$('#currently-showing').text("${i18n.get('cione-module.templates.configuracion-precios-component.currently.showing')}${i18n.get('cione-module.templates.configuracion-precios-component.PVO-PVP')}")
						  			$('#b2b-modifyprices').text("${i18n.get('cione-module.templates.myshop.header-component.modify-prices')} (${i18n.get('cione-module.templates.configuracion-precios-component.PVO-PVP')})");
						  		} else if (response.config == "hidden") {
						  			$('#currently-showing').text("${i18n.get('cione-module.templates.configuracion-precios-component.currently.hidden')}")
						  			$('#b2b-modifyprices').text("${i18n.get('cione-module.templates.myshop.header-component.modify-prices')} (${i18n.get('cione-module.templates.configuracion-precios-component.hidden')})");
						  		} 
								$('.b2b-msg-passowrd').addClass("msg-ok");
								$('.b2b-msg-passowrd').removeClass("msg-error");
								$('.b2b-msg-passowrd').text(response.message);
						  	},
						  	error: function(response) { 
						  		$("#loading").hide();
						  		$('.b2b-msg-passowrd').addClass("msg-error");
						  		$('.b2b-msg-passowrd').removeClass("msg-ok");
						  		$('.b2b-msg-passowrd').text(response.responseJSON.message);
						  	}
						  
						});
					}
				return false;	
		         
			});
			function formToJSON() {
			
				configuration = '';
				if (document.getElementById('pvp') != null && document.getElementById('pvp').checked) {
				  configuration = 'pvp';
				} else if (document.getElementById('pvo') != null && document.getElementById('pvo').checked) {
				  configuration = 'pvo';
				} else if (document.getElementById('pvp-pvo') != null && document.getElementById('pvp-pvo').checked) {
				  configuration = 'pvp-pvo';
				} else if (document.getElementById('hidden') != null && document.getElementById('hidden').checked) {
				  configuration = 'hidden';
				}
				return JSON.stringify({
			        "newConfiguration": configuration,
			        [#if user?? && user?has_content]
			        "user": "${user.getIdentifier()}",
			        [/#if]
			        "password": $('#password').val(),
			    });
			}
		});
		</script>
</section>