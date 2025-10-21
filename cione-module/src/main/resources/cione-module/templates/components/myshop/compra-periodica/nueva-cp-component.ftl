[#if !cmsfn.editMode]

[#assign link = cmsfn.link("website", content.internalLink!)!]
[#assign periodicpurchase = model.getPeriodicPurchase()!]

<section class="b2b-form-suscripcion">
    <form>
    
        <h2 class="b2b-form-title">${i18n['cione-module.templates.components.nueva-cp-component.title']}</h2>

        <div class="b2b-form-radio-wrapper">
            <div class="b2b-form-row">
				
				[#-- FRECUENCIA --]
				<div class="b2b-form-input-container">
                    <label for="susAviso">Frecuencia</label>
                    <select id="periodicity">
                        <option value="Mensual">${i18n['cione-module.templates.components.nueva-cp-component.mensual']}</option>
                        <option value="Bimensual">${i18n['cione-module.templates.components.nueva-cp-component.bimensual']}</option>
                        <option value="Trimestral">${i18n['cione-module.templates.components.nueva-cp-component.trimestral']}</option>
                        <option value="Semestral">${i18n['cione-module.templates.components.nueva-cp-component.semestral']}</option>
                        <option value="Anual">${i18n['cione-module.templates.components.nueva-cp-component.anual']}</option>
                    </select>
                </div>
                
                [#-- FECHA INICIO --]
                <div class="b2b-form-input-container">
                    <div>
                        <label>${i18n['cione-module.templates.components.nueva-cp-component.fechaini']}</label>
                        <input id="startdate" class="form-control" type="date">
                    </div>
                </div>
				
				[#-- FECHA FIN --]
                <div class="b2b-form-input-container">
                    <div>
                        <label>${i18n['cione-module.templates.components.nueva-cp-component.fechaend']}</label>
                        <input id="enddate" class="form-control" type="date">
                    </div>
                </div>
				
				[#-- CLIENTE --]
                <div class="b2b-form-input-container">
                    <label for="preClien">${i18n['cione-module.templates.components.nueva-cp-component.cliente']}</label>
                    <input class="form-control" type="text" id="customernumber">
                </div>

				[#-- MAIL --]
                <div class="b2b-form-input-container">
                    <label for="preEmailCliente">${i18n['cione-module.templates.components.nueva-cp-component.mailc']}</label>
                    <input class="form-control" type="text" id="customermail">
                </div>
                
                <div class="b2b-form-input-container"></div>
                
            </div>

            <div class="b2b-button-wrapper">
                <button type="button" class="b2b-button b2b-button-filter" onclick="savePeriodicPurchase(); return false">
                    ${i18n['cione-module.templates.components.listado-shoppinglist-component.save']}
                </button>
            </div>
            <div id="msgerror" style="display: none;padding-bottom: 30px;color: red;-webkit-box-pack: end;justify-content: flex-end;"></div>
        </div>

    </form>
</section>

<script>
	
	[#-- BEGIN: FUNCTIONS --]
	function savePeriodicPurchase(){
	
		if (validateForm()){
			
			$("#loading").show();
			
			var url = "${ctx.contextPath}/.rest/private/periodicpurchase/newaddCartToPeriodicPurchase";
			
			var indexed_array = {};
			indexed_array["id"] = '${model.getCartId()!""}';
			indexed_array["periodicity"] =  $.trim($("#periodicity").val());
			indexed_array["enddate"] = $.trim($("#enddate").val());
			indexed_array["startdate"] = $.trim($("#startdate").val());
			indexed_array["customer"] = $.trim($("#customernumber").val());
			indexed_array["mailconsumer"] = $.trim($("#customermail").val());
			
			$.ajax({
				url : url,
				type : "POST",
				cache : false,
	            dataType : "json",
				async: false,
				data : JSON.stringify(indexed_array),
	            contentType : 'application/json; charset=utf-8',
				success : function(response) {
					console.log("Compra periodica creada");
					window.location.replace("${link!"#"}");
				},
				error : function(response) {
					console.log("Error al crear una nueva compra periodica");
					console.log(response);
					$("#loading").hide();
				}
			});
			
			$("#loading").hide();
		
		}
	}
	
	function validateForm(){
		
		var res = true;
		var msg = "";
	    
	    [#-- start: validacion cliente --]
		var periodicity = $("#periodicity").val();
		
		if(periodicity.length == 0){
			res = false;
        	setFieldValid($("#periodicity"),false);
        	msg = "ERROR: Complete los campos obligatorios";
	    }else{
	    	setFieldValid($("#periodicity"),true); 
	    }
	    [#-- end: validacion cliente --]
		
		[#-- start: validacion fecha --]
		var fechaini = $("#startdate").val();
		var fechafin = $("#enddate").val();
		
		if(fechaini.length == 0){
	        res = false;
	        setFieldValid($("#startdate"),false);
	        msg = "ERROR: Complete los campos obligatorios";
	    }else{
	    	if (!validator.isDate(fechaini)){
	    		res = false;
	    		setFieldValid($("#startdate"),false); 
	    		msg = "ERROR: fecha introducida es incorrecta";
	    	}else{
	    		setFieldValid($("#startdate"),true); 
	    	}
	    }
		
		if(fechafin.length == 0){
	        res = false;
	        setFieldValid($("#enddate"),false);
	        msg = "ERROR: Complete los campos obligatorios";
	    }else{
	    	if (!validator.isDate(fechafin)){
	    		res = false;
	    		setFieldValid($("#enddate"),false); 
	    		msg = "ERROR: fecha introducida es incorrecta";
	    	}else{
	    		setFieldValid($("#enddate"),true); 
	    	}
	    }
	    
	    if (res){
	    
	    	var dfechaini = new Date(fechaini.split("-")[0], fechaini.split("-")[1], fechaini.split("-")[2]);
			var dfechafin = new Date(fechafin.split("-")[0], fechafin.split("-")[1], fechafin.split("-")[2]);
			
			if (dfechaini > dfechafin){
				res = false;
				setFieldValid($("#startdate"),false);
				setFieldValid($("#enddate"),false); 
	    		msg = "ERROR: la fecha fin debe ser mayor que la fecha de inicio";
			}
	    
	    }
	    
	    if (res){
	    	
	    	var dfechaini = new Date(fechaini.split("-")[0], fechaini.split("-")[1], fechaini.split("-")[2]);
			var dfechafin = new Date(fechafin.split("-")[0], fechafin.split("-")[1], fechafin.split("-")[2]);
			
			var months = getMonthsByFrequency($("#periodicity").val());
			
	    	dfechaini.setMonth(dfechaini.getMonth() + months);
	    	
	    	if (dfechaini > dfechafin){
				res = false;
				setFieldValid($("#periodicity"),false);
				setFieldValid($("#startdate"),false);
				setFieldValid($("#enddate"),false); 
	    		msg = "ERROR: dada la frecuencia y fechas introducidas nunca se producirá la compra";
			}
	    
	    }
	    [#-- end: validacion fecha --]
	    
	    [#-- start: validacion cliente --]
		var cliente = $("#customernumber").val();
		
		if(cliente.length == 0){
			res = false;
        	setFieldValid($("#customernumber"),false);
        	msg = "ERROR: Complete los campos obligatorios";
	    }else{
	    	setFieldValid($("#customernumber"),true); 
	    }
	    [#-- end: validacion cliente --]
	    
	    [#-- start: validacion mail --]
		var mail = $("#customermail").val();
		
		if(mail.length > 0){
			if(!validator.isEmail(mail)){
		        res = false;
		        setFieldValid($("#customermail"),false);
		        if (msg.length == 0){
		    		msg = "ERROR: mail introducido es incorrecto";
		    	}else{
		    		msg += "\r\nERROR: mail introducido es incorrecto";
		    	}
			}else{
		    	setFieldValid($("#customermail"),true);
		    }
	    }else{
	    	setFieldValid($("#customermail"),false);
	    }
	    [#-- end: validacion mail --]
	    
	    setMsgValid(msg,res);
	    return res;
	}
	
	function getMonthsByFrequency(frecuencia) {
		
		var months = 1;
		
		switch(frecuencia) {
			case "Mensual":
				months = 1;
				break;
			case "Bimensual":
				months = 2;
				break;
			case "Trimestral":
				months = 3;
				break;
			case "Semestral":
				months = 6;
				break;
			case "Anual":
				months = 12;
				break;
			default:
				months = 1;
		}
		
		return months;
	}
	
	function setFieldValid(field,isValid){
		
		if(!isValid){
			field.css("color", "red");
	        field.css("border", "1px solid red"); 
		}else{
	    	field.css("color", "black");
	        field.css("border", "solid 1px #c3c3c3"); 
		}
	}
	
	function setMsgValid(msg,isValid){
		
		if(!isValid){
	        $('#msgerror').css("display","flex");
        	$('#msgerror').html('');
        	$('#msgerror').append(msg);
		}else{
			$('#msgerror').css("display","none");
		}
	}
	[#-- END: FUNCTIONS --]
	
</script>

[/#if]
