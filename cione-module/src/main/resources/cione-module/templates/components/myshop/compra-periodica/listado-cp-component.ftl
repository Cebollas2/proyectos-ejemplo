[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]

[#if !cmsfn.editMode]

[#assign link = cmsfn.link("website", content.internalLink!)!]
[#assign link += "?id="]

<section class="b2b-form-presupuestos">

	[#-- BEGIN: FILTROS --]
    <form>
        <h2 class="b2b-form-title">${i18n['cione-module.templates.components.listado-cp-component.title']}</h2>

        <div class="b2b-form-radio-wrapper">

            <div class="b2b-form-col">
                
                <div class="b2b-form-input-container">
                    <label for="preCod">${i18n['cione-module.templates.components.listado-cp-component.num-compra']}</label>
                    <input class="form-control" type="text" id="numbudget" autocomplete="off">
                </div>

                <div class="b2b-form-input-container">
                    <label>${i18n['cione-module.templates.components.listado-shoppinglist-component.fecha']}</label>
                    <input class="form-control" type="date" id="startdate" autocomplete="off">
                </div>
                
                <div class="b2b-form-input-container">
                    <label for="preClien">${i18n['cione-module.templates.components.listado-cp-component.num-cliente']}</label>
                    <input class="form-control" type="text" id="numcliente" autocomplete="off">
                </div>

                <div class="b2b-form-input-container">
                    <label for="preEmail">${i18n['cione-module.templates.components.listado-shoppinglist-component.mail']}</label>
                    <input class="form-control" type="text" id="customermail" autocomplete="off">
                </div>
            </div>

            <div class="b2b-button-wrapper">
                <button type="button" class="b2b-button b2b-button-filter" onclick="search(); return false">
                    ${i18n['cione-module.templates.components.albaranes-component.btn-buscar']}
                </button>
            </div>

        </div>
    </form>
	[#-- END: FILTROS --]
	
	[#-- BEGIN: LISTADO --]
    <div class="panel-table mt-5">
    	
        <table class="table" id="shoppinglisttable">
            <thead>
                <tr>
                	<th></th>
                    <th>${i18n['cione-module.templates.components.listado-cp-component.num-compra']}</th>
                    <th>Fecha Alta</th>
                    <th>Cliente</th>
                    <th class="hide-mobile">${i18n['cione-module.templates.components.listado-shoppinglist-component.mail']}</th>
                    <th class="hide-mobile">${i18n['cione-module.templates.components.listado-shoppinglist-component.acc']}</th>
                </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
        
        [#-- BEGIN: VER MAS --]
        <div class="foot" id="showmore" style="display: none;">
            <span> 
            	<span></span> 
            	<span class="cantrow"><span></span></span>
            </span>
            <span id="buttonshowmore" class="vermas" style="cursor: pointer;" onclick="getAllPeriodicPurchase();">${i18n['cione-module.templates.components.listado-shoppinglist-component.mas']}</span>
            <span></span>
        </div>
        [#-- END: VER MAS --]
        
    </div>
    [#-- END: LISTADO --]
   	
   	[#-- BEGIN: MODAL BORRAR --]
    [#assign closeimg = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/icons/close-thin.svg"]
    <div id="modal-remove" class="modal-purchase">
	    <div class="modal-purchase-box">
	    
	        <div class="modal-purchase-header">
	            <p>${i18n['cione-module.templates.myshop.cp.modal.remove.title']}</p>
	            <div class="modal-purchase-close">
	                <img class="modal-purchase-close-img" src="${closeimg!""}" alt="${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}" onclick='closeModal("modal-remove")'>
	            </div>
	        </div>
	        
	        <div class="modal-purchase-info">
	            <div>
	                <p>${i18n['cione-module.templates.myshop.cp.modal.remove.body']}</p>
	            </div>
	        </div>
	        
	        <div class="modal-purchase-footer">
	            <button class="modal-purchase-button modal-purchase-button--transparent" type="button" onclick='closeModal("modal-remove")'>
	                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}
	            </button>
	            <button class="modal-purchase-button" type="button" onclick="removePeriodicPurchase();">
	                ${i18n['cione-module.templates.myshop.shoppinglist.modal.remove.ok']}
	            </button>
	        </div>
	    </div>
	</div>
    [#-- END: MODAL BORRAR --]
    
    [#-- BEGIN: MODAL RECUPERAR --]
    <div id="modal-retrieve" class="modal-purchase">
	    <div class="modal-purchase-box">
	    
	        <div class="modal-purchase-header">
	            <p>${i18n['cione-module.templates.myshop.cp.modal.retrive.title']}</p>
	            <div class="modal-purchase-close">
	                <img class="modal-purchase-close-img" src="${closeimg!""}" alt="${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}" onclick='closeModal("modal-retrieve")'>
	            </div>
	        </div>
	        
	        <div class="modal-purchase-info">
	            <div>
	                <p>${i18n['cione-module.templates.myshop.cp.modal.retrive.body']}</p>
	            </div>
	        </div>
	        
	        <div class="modal-purchase-footer">
	            <button class="modal-purchase-button modal-purchase-button--transparent" type="button" onclick='closeModal("modal-retrieve")'>
	                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}
	            </button>
	            <button class="modal-purchase-button" type="button" onclick="getPeriodicPurchase();">
	                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.ok']}
	            </button>
	        </div>
	    </div>
	</div>
    [#-- END: MODAL RECUPERAR --]
    
</section>

[#-- BEGIN: MODAL POSPONER: fuera del section por estilos --]
<div id="modal-posponer" class="b2b-modal-posponer">

    <div class="b2b-modal-container">

        <h3 class="b2b-modal-title">POSPONER</h3>
        <img class="b2b-modal-close" src="${closeimg!""}" alt="${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}" onclick='closeModal("modal-posponer")'/>
        <div class="b2b-modal-body">

            <label class="b2b-modal-label">Introducir frecuencia</label> 
            
            <div class="row" style="height: 50px;">
                
                <div class="col-12 col-lg-3">
                    <select id="periodicity_select">
                        <option value="Mensual">${i18n['cione-module.templates.components.nueva-cp-component.mensual']}</option>
                        <option value="Bimensual">${i18n['cione-module.templates.components.nueva-cp-component.bimensual']}</option>
                        <option value="Trimestral">${i18n['cione-module.templates.components.nueva-cp-component.trimestral']}</option>
                        <option value="Semestral">${i18n['cione-module.templates.components.nueva-cp-component.semestral']}</option>
                        <option value="Anual">${i18n['cione-module.templates.components.nueva-cp-component.anual']}</option>
                    </select>
                </div>
                
                <input id="periodicity" type='hidden'>
            </div>
            
            <label class="b2b-modal-label" for="Fechas">${i18n['cione-module.templates.myshop.cp.modal.retrive.fechas']}</label>

            <div class="row">
            
                <div class="col-12 col-lg-3">
                    <input id="startdatemodal" type="date">
                    <span id="msgerror" style="display: flex;white-space: nowrap;margin-top: 10px;color: red;font-weight: bold;"></span>
                </div>
                
                <div class="col-12 col-lg-3">
                    <input id="enddate" type="date">
                </div>
               
            </div>
 			
            <div class="b2b-modal-button">
                <div class="b2b-button-wrapper">
                    <button class="b2b-button b2b-button-filter" type="button"  onclick="savePeriodicPurchase();">
                        ${i18n['cione-module.templates.myshop.cp.modal.retrive.save']}
                    </button>
                </div>
            </div>

        </div>

    </div>

</div>
[#-- END: MODAL POSPONER --]

[/#if]

<script type="text/javascript">
	
	var page = 0;
	var count = 0;
	var total = 0;
	
	$( document ).ready(function() {
		
		getAllPeriodicPurchase();
		
	});
	
	function savePeriodicPurchase(){
	
		var id = $('#modal-posponer').data('idbudget');
		console.log(id);
		 
		if (validateForm()){
			
			$("#loading").show();
			
			var url = "${ctx.contextPath}/.rest/private/periodicpurchase/update";
			
			var indexed_array = {};
			indexed_array["id"] = id;
			indexed_array["enddate"] = $.trim($("#enddate").val());
			indexed_array["startdate"] = $.trim($("#startdatemodal").val());
			indexed_array["periodicity"] =  $.trim($("#periodicity_select").val());
			
			$.ajax({
				url : url,
				type : "POST",
				cache : false,
	            dataType : "json",
				async: false,
				data : JSON.stringify(indexed_array),
	            contentType : 'application/json; charset=utf-8',
				success : function(response) {
					console.log("Compra periodica actualizada");
					setFieldValid($("#enddate"),true);
					setFieldValid($("#startdate"),true);
					$('#modal-posponer').data('idbudget', '');
					$('#modal-posponer').css('display','none');
					
				},
				error : function(response) {
					console.log("Error al actualizar compra periodica");
					console.log(response);
					$("#loading").hide();
				},complete: function(){getAllPeriodicPurchaseReset();}
			});
			
			$("#loading").hide();
		
		}
		
	}
	
	function validateForm(){
		
		var res = true;
		var msg = "";
		
		[#-- start: validacion fecha --]
		var fechaini = $("#startdatemodal").val();
		var fechafin = $("#enddate").val();
		
		if(fechaini.length == 0){
	        res = false;
	        setFieldValid($("#startdatemodal"),false);
	        msg = "ERROR: Complete los campos obligatorios";
	    }else{
	    	if (!validator.isDate(fechaini)){
	    		res = false;
	    		setFieldValid($("#startdatemodal"),false); 
	    		msg = "ERROR: fecha introducida es incorrecta";
	    	}else{
	    		setFieldValid($("#startdatemodal"),true); 
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
				setFieldValid($("#startdatemodal"),false);
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
				setFieldValid($("#startdatemodal"),false);
				setFieldValid($("#enddate"),false); 
	    		msg = "ERROR: dada la frecuencia y fechas introducidas nunca se producirá la compra";
			}
	    
	    }
	    [#-- end: validacion fecha --]
	    
	    setMsgValid(msg,res);
	    console.log("Formulario: " + res);
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
	
	function getAllPeriodicPurchaseReset(){
		page = 0;
		count = 0;
		total = 0;
		$("#shoppinglisttable tbody").empty();
		getAllPeriodicPurchase();
	}
	
	function getAllPeriodicPurchase(){
		
		$("#loading").show();
		var url = generateURL();
		
		$.ajax({
			url : url,
			type : "GET",
			cache : false, 
			async: false, 
			success : function(response) {
			
				count += response.count;
				total = response.total;
				
				page += 1;
				var totalpages = Math.ceil(total/12)-1;
				
				if (page <= totalpages){
					$("#showmore").css("display","flex");
				}else{
					$("#showmore").css("display","none");
				}
				
				response.results.forEach(function(shoppinglist, key, results){
	        		
	        		var id = '"' +  shoppinglist.id + '"';
	        		var idrow = '"' + 'fila-' + shoppinglist.id + '"';
	        		var mailCliente = '-';
	        		var numCliente = '-';
	        		var newRowContent = "<tr id=" + idrow + ">";
	        		var link = "${link!"#"}";
	        		var startdate = "";
	        		var enddate = "";
	        		var frecuencia = "";
	        		var color = "";
	        		var idColor = '"' + 'color-' + shoppinglist.id + '"';
	        		
	        		if((typeof shoppinglist.custom.fields.mailCliente !== 'undefined')) {
	        			mailCliente = shoppinglist.custom.fields.mailCliente;
	        		}
	        		
	        		if((typeof shoppinglist.custom.fields.numCliente !== 'undefined')) {
	        			numCliente = shoppinglist.custom.fields.numCliente;
	        		}
	        		
	        		if((typeof shoppinglist.custom.fields.dateIniPurcharse !== 'undefined')) {
	        			startdate = shoppinglist.custom.fields.dateIniPurcharse;
	        		}
	        		
	        		if((typeof shoppinglist.custom.fields.datedFinPurcharse !== 'undefined')) {
	        			enddate = shoppinglist.custom.fields.datedFinPurcharse;
	        		}
	        		
	        		if((typeof shoppinglist.custom.fields.periodicityPurcharse !== 'undefined')) {
	        			frecuencia = shoppinglist.custom.fields.periodicityPurcharse;
	        		}
	        		
	        		if((typeof shoppinglist.custom.fields.statusPurchase !== 'undefined')) {
	        			color = shoppinglist.custom.fields.statusPurchase;
	        		}
	        		
	        		var inputstart = "<input id='dateIniPurcharse_" + shoppinglist.id + "' type='hidden' value=" + startdate +" />";
	        		var inputend = "<input id='datedFinPurcharse_" + shoppinglist.id + "' type='hidden' value=" + enddate + " />";
	        		var inputperiodicity = "<input id='periodicityPurcharse_" + shoppinglist.id + "' type='hidden' value=" + frecuencia + " />";
	        		var newContentDates         = inputstart + inputend + inputperiodicity;
	        		
	        		var fechaObj = new Date(shoppinglist.createdAt);
	        		var fechaObjMont = fechaObj.getMonth() + 1;
	        		
	        		link += shoppinglist.id;
	        		var newColColor             = "<td width='24'><div id=" + idColor + " class='b2b-dot-color " + color + "'></div></td>";
	        		var newColContentNum        = "<td><a href='" + link + "'> " + shoppinglist.custom.fields.idPurchase  + " </a></td>";
	        		var newColContentDate       = "<td>" +  fechaObj.getDate()  + "/" + fechaObjMont + "/" + fechaObj.getFullYear() + "</td>";
	        		var newColContentNumClient  = "<td>" + numCliente + newContentDates + "</td>";
	        		var newColContentMailClient = "<td class='hide-mobile'>" + mailCliente + "</td>";
	        		
	        		
	        		var btnEditar   = "<img class='b2b-icon-delete' src='${resourcesURL}/img/myshop/icons/pencil-square.svg' alt='${i18n['cione-module.templates.components.listado-cp-component.edit']}'          title='${i18n['cione-module.templates.components.listado-cp-component.edit']}'          onclick='editBudget(" + id + "); return false;'   style='padding-left: 10px;padding-right: 10px;'>";
	        		var btnCarrito  = "<img class='b2b-icon-delete' src='${resourcesURL}/img/myshop/icons/cart-check.svg'    alt='${i18n['cione-module.templates.components.listado-cp-component.recuperar-pre']}' title='${i18n['cione-module.templates.components.listado-cp-component.recuperar-pre']}' onclick='showModalRetrieve(" + id + "); return false;' style='padding-left: 10px;padding-right: 10px;'>";
	        		var btnPDF      = "<img class='b2b-icon-delete' src='${resourcesURL}/img/myshop/icons/clock-history.svg' alt='${i18n['cione-module.templates.components.listado-cp-component.repro']}'         title='${i18n['cione-module.templates.components.listado-cp-component.repro']}'         onclick='showModalPostpone(" + id + "); return false;'   style='padding-left: 10px;padding-right: 10px;'>";
	        		var btnEliminar = "<img class='b2b-icon-delete' src='${resourcesURL}/img/myshop/icons/bi-trash.svg'      alt='${i18n['cione-module.templates.components.listado-cp-component.remove']}'        title='${i18n['cione-module.templates.components.listado-cp-component.remove']}'        onclick='showModalRemove(" + id + "); return false;'   style='padding-left: 10px;padding-right: 10px;'>";
	        		
	        		var newColContentAcciones   = "<td class='masinfo hide-mobile' style='text-align: center;'>" + btnEditar + btnCarrito + btnPDF + btnEliminar + "</td>";

	        		newRowContent += newColColor + newColContentNum + newColContentDate + newColContentNumClient + newColContentMailClient + newColContentAcciones;
	        		newRowContent += "</tr>";
	        		
	        		$("#shoppinglisttable tbody").append(newRowContent);
	        		
	        		newRowContent = "";
	        		newColContentNum = "";
	        		newColContentDate = "";
	        		newColContentNumClient = "";
	        		newColContentMailClient = "";
	        		newColContentRecuperar = "";
	        		newColContentEliminar = "";
	        		
	        	});
			
			},
			error : function(response) {
				console.log("Error al recuperar los presupuestos");
				console.log(response);
				$("#loading").hide();
			}
		});
		
		$("#loading").hide();
	}
	
	function generateURL(){
	
		var url = "${ctx.contextPath}/.rest/private/periodicpurchase/all?page=" + page;
		
		if($("#numcliente").val() !== "" && $("#numcliente").val() !== 'undefined'){
			url += '&numcliente='+ $("#numcliente").val();
		}
		
		if($("#startdate").val() !== "" && $("#startdate").val() !== 'undefined'){
			url += '&date='+ $("#startdate").val();
		}
		
		if($("#customermail").val() !== "" && $("#customermail").val() !== 'undefined'){
			url += '&customermail='+ $("#customermail").val();
		}
		
		if($("#numbudget").val() !== "" && $("#numbudget").val() !== 'undefined'){
			url += '&numbudget='+ $("#numbudget").val();
		}
		
		return url;
		
	}
	
	function removePeriodicPurchase(){
	
		var id = $('#modal-remove').data('idbudget');
		$('#modal-remove').css('display','none');
		$('#modal-remove').data('idbudget', '');
		
		$("#loading").show();
		
		var url = "${ctx.contextPath}/.rest/private/periodicpurchase/delete?id=" + id;
		
		$.ajax({
			url : url,
			type : "DELETE",
			cache : false, 
			async: false, 
			success : function(response) {
				$("#fila-" + id).remove();
				getAndSetStatusPeriodicPurchase();
			},
			error : function(response) {
				console.log("Error al eliminar la compra periodica");
				console.log(response);
				$("#loading").hide();
			}
		});
		
		$("#loading").hide();
	
	}
	
	function getPeriodicPurchase(){
		
		var id = $('#modal-retrieve').data('idbudget');
		$('#modal-retrieve').css('display','none');
		$('#modal-retrieve').data('idbudget', '');
		
		$("#loading").show();
		
		var url = "${ctx.contextPath}/.rest/private/periodicpurchase/retrieve?id=" + id;
		
		$.ajax({
			url : url,
			type : "GET",
			cache : false, 
			async: false, 
			success : function(response) {
				refrescarPopupCarrito(response);
				$("#color-"+id).removeClass("red");
				$("#color-"+id).removeClass("yellow");
				$("#color-"+id).addClass("green");
				console.log("Comprar periodica recuperada");
			},
			error : function(response) {
				console.log("Error al recuperar la compra periodica");
				console.log(response);
				$("#loading").hide();
			}
		});
		
		$("#loading").hide();
	
	}
	
	function search(){
	
		page = 0;
		count = 0;
		$("#shoppinglisttable tbody").html("");
		$("#buttonshowmore").css("display","flex");
	
		getAllPeriodicPurchase();
	}
	
	function closeModal(name) {
		$("#"+name).css("display","none");
	}
	
	function showModalRetrieve(id){
		$('#modal-retrieve').data('idbudget', id);
		$('#modal-retrieve').css('display','flex');
	}
	
	function showModalPostpone(id){
		$('#startdatemodal').val($('#dateIniPurcharse_'+id).val());
		$('#enddate').val($('#datedFinPurcharse_'+id).val());
		$('#periodicity').val($('#periodicityPurcharse_'+id).val());
		
		var periodicity_sel = $('#periodicityPurcharse_'+id).val();
		
		$("#periodicity_select option").each(function() {
		  if ($(this).text() == periodicity_sel) {
		    $(this).prop("selected", true);
		  }
		});
		
		$('#modal-posponer').data('idbudget', id);
		$('#modal-posponer').css('display','flex');
	}
	
	function showModalRemove(id){
		$('#modal-remove').data('idbudget', id);
		$('#modal-remove').css('display','flex');
	}

	function editBudget(id){
		var link = "${link!"#"}";
		window.open(link+id, '_self');
	}
	
</script>