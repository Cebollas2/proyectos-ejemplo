[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]

[#if !cmsfn.editMode]

[#assign link = cmsfn.link("website", content.internalLink!)!]
[#assign link += "?id="]

<section class="b2b-form-presupuestos">

	[#-- BEGIN: FILTROS --]
    <form>
        <h2 class="b2b-form-title">${i18n['cione-module.templates.components.listado-shoppinglist-component.title']}</h2>

        <div class="b2b-form-radio-wrapper">

            <div class="b2b-form-col">
                <div class="b2b-form-input-container">
                    <label for="preClien">${i18n['cione-module.templates.components.listado-shoppinglist-component.num-cliente']}</label>
                    <input class="form-control" type="text" id="numcliente" autocomplete="off">
                </div>

                <div class="b2b-form-input-container">
                    <label>${i18n['cione-module.templates.components.listado-shoppinglist-component.fecha']}</label>
                    <input class="form-control" type="date" id="startdate" autocomplete="off">
                </div>

                <div class="b2b-form-input-container">
                    <label for="preEmail">${i18n['cione-module.templates.components.listado-shoppinglist-component.mail']}</label>
                    <input class="form-control" type="text" id="customermail" autocomplete="off">
                </div>
                <div class="b2b-form-input-container">
                    <label for="preCod">${i18n['cione-module.templates.components.listado-shoppinglist-component.budget']}</label>
                    <input class="form-control" type="text" id="numbudget" autocomplete="off">
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
    <div class="panel-table mt-5" style="padding-bottom: 30px;">
    	
        <table class="table" id="shoppinglisttable" >
            <thead>
                <tr>
                    <th>${i18n['cione-module.templates.components.listado-shoppinglist-component.budget']}</th>
                    <th>${i18n['cione-module.templates.components.listado-shoppinglist-component.fecha']}</th>
                    <th>${i18n['cione-module.templates.components.listado-shoppinglist-component.num-cliente']}</th>
                    <th class="hide-mobile">${i18n['cione-module.templates.components.listado-shoppinglist-component.mail']}</th>
                    <th class="hide-mobile">${i18n['cione-module.templates.components.listado-shoppinglist-component.vgn']}</th>
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
            <span id="buttonshowmore" class="vermas" style="cursor: pointer;" onclick="getAllShoppingLists();">${i18n['cione-module.templates.components.listado-shoppinglist-component.mas']}</span>
            <span></span>
        </div>
        [#-- END: VER MAS --]
        
    </div>
    <div><span id="msgerror" style="padding-left:10px; color: red;display: none;">ERROR: Complete los campos obligatorios</span></div>
    [#-- END: LISTADO --]
   	
   	[#-- BEGIN: MODAL BORRAR --]
    [#assign closeimg = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/icons/close-thin.svg"]
    <div id="modal-remove" class="modal-purchase">
	    <div class="modal-purchase-box">
	    
	        <div class="modal-purchase-header">
	            <p>${i18n['cione-module.templates.myshop.shoppinglist.modal.remove.title']}</p>
	            <div class="modal-purchase-close">
	                <img class="modal-purchase-close-img" src="${closeimg!""}" alt="${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}" onclick='closeModal("modal-remove")'>
	            </div>
	        </div>
	        
	        <div class="modal-purchase-info">
	            <div>
	                <p>${i18n['cione-module.templates.myshop.shoppinglist.modal.remove.body']}</p>
	            </div>
	        </div>
	        
	        <div class="modal-purchase-footer">
	            <button class="modal-purchase-button modal-purchase-button--transparent" type="button" onclick='closeModal("modal-remove")'>
	                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}
	            </button>
	            <button class="modal-purchase-button" type="button" onclick="removeShoppingList();">
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
	            <p>${i18n['cione-module.templates.myshop.shoppinglist.modal.retrive.title']}</p>
	            <div class="modal-purchase-close">
	                <img class="modal-purchase-close-img" src="${closeimg!""}" alt="${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}" onclick='closeModal("modal-retrieve")'>
	            </div>
	        </div>
	        
	        <div class="modal-purchase-info">
	            <div>
	                <p>${i18n['cione-module.templates.myshop.shoppinglist.modal.retrive.body']}</p>
	            </div>
	        </div>
	        
	        <div class="modal-purchase-footer">
	            <button class="modal-purchase-button modal-purchase-button--transparent" type="button" onclick='closeModal("modal-retrieve")'>
	                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}
	            </button>
	            <button class="modal-purchase-button" type="button" onclick="getShoppingList();">
	                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.ok']}
	            </button>
	        </div>
	    </div>
	</div>
    [#-- END: MODAL RECUPERAR --]
    
</section>

[/#if]

<script type="text/javascript">
	
	var page = 0;
	var count = 0;
	var total = 0;
	
	$( document ).ready(function() {
		
		getAllShoppingLists();
		
	});
	
	function getAllShoppingLists(){
		
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
	        		
	        		if((typeof shoppinglist.custom.fields.mailCliente !== 'undefined')) {
	        			mailCliente = shoppinglist.custom.fields.mailCliente;
	        		}
	        		
	        		if((typeof shoppinglist.custom.fields.numCliente !== 'undefined')) {
	        			numCliente = shoppinglist.custom.fields.numCliente;
	        		}
	        		
	        		if((typeof shoppinglist.custom.fields.vigencia !== 'undefined')) {
	        			vigencia = shoppinglist.custom.fields.vigencia;
	        			vyear = vigencia.split("-", 3)[0];
	        			vmonth = vigencia.split("-", 3)[1];
	        			vday = vigencia.split("-", 3)[2];
	        		}else{
	        			vyear = "";
	        			vmonth = "";
	        			vday = "";
	        		}
	        		
	        		var fechaObj = new Date(shoppinglist.createdAt);
	        		var fechaObjMont = fechaObj.getMonth() + 1;
	        		
	        		link += shoppinglist.id;
	        		var newColContentNum        = "<td><a href='" + link + "'> " + shoppinglist.custom.fields.idPresupuesto  + " </a></td>";
	        		var newColContentDate       = "<td>" +  fechaObj.getDate()  + "/" + fechaObjMont + "/" + fechaObj.getFullYear() + "</td>";
	        		var newColContentNumClient  = "<td>" + numCliente + "</td>";
	        		var newColContentMailClient = "<td class='hide-mobile'>" + mailCliente + "</td>";
	        		var newColContentVigencia   = "<td class='hide-mobile'>" + vday + "/" + vmonth + "/" + vyear + "</td>";
	        		var btnEditar   = "<img class='b2b-icon-delete' src='${resourcesURL}/img/myshop/icons/pencil-square.svg'    alt='${i18n['cione-module.templates.components.listado-shoppinglist-component.edit']}'          title='${i18n['cione-module.templates.components.listado-shoppinglist-component.edit']}'          onclick='editBudget(" + id + "); return false;'   style='padding-left: 10px;padding-right: 10px;'>";
	        		var btnCarrito  = "<img class='b2b-icon-delete' src='${resourcesURL}/img/myshop/icons/cart-check.svg'       alt='${i18n['cione-module.templates.components.listado-shoppinglist-component.recuperar-pre']}' title='${i18n['cione-module.templates.components.listado-shoppinglist-component.recuperar-pre']}' onclick='showModalRetrieve(" + id + "); return false;' style='padding-left: 10px;padding-right: 10px;'>";
	        		var btnPDF      = "<img class='b2b-icon-delete' src='${resourcesURL}/img/myshop/icons/file-earmark-pdf.svg' alt='${i18n['cione-module.templates.components.listado-shoppinglist-component.pdf']}'           title='${i18n['cione-module.templates.components.listado-shoppinglist-component.pdf']}'           onclick='saveBudgetPDF(" + id + "); return false;'   style='padding-left: 10px;padding-right: 10px;'>";
	        		var btnEliminar = "<img class='b2b-icon-delete' src='${resourcesURL}/img/myshop/icons/bi-trash.svg'         alt='${i18n['cione-module.templates.components.listado-shoppinglist-component.remove']}'        title='${i18n['cione-module.templates.components.listado-shoppinglist-component.remove']}'        onclick='showModalRemove(" + id + "); return false;'   style='padding-left: 10px;padding-right: 10px;'>";
	        		
	        		var newColContentAcciones   = "<td class='masinfo hide-mobile' style='text-align: center;'>" + btnEditar + btnCarrito + btnPDF + btnEliminar + "</td>";

	        		newRowContent += newColContentNum + newColContentDate + newColContentNumClient + newColContentMailClient + newColContentVigencia + newColContentAcciones;
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
	        	
				$("#loading").hide();
			},
			error : function(response) {
				console.log("Error al recuperar los presupuestos");
				console.log(response);
				$("#loading").hide();
			}
		});
		
	}
	
	function generateURL(){
	
		var url = "${ctx.contextPath}/.rest/private/shoppinglist/all?page=" + page;
		
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
	
	function removeShoppingList(){
	
		var id = $('#modal-remove').data('idbudget');
		$('#modal-remove').css('display','none');
		$('#modal-remove').data('idbudget', '');
		
		$("#loading").show();
		
		var url = "${ctx.contextPath}/.rest/private/shoppinglist/delete?id=" + id;
		
		$.ajax({
			url : url,
			type : "DELETE",
			cache : false, 
			async: false, 
			success : function(response) {
				$("#fila-" + id).remove();
			},
			error : function(response) {
				console.log("Error al eliminar el presupuesto");
				console.log(response);
				$("#loading").hide();
			}
		});
		
		$("#loading").hide();
	
	}
	
	function getShoppingList(){
		
		var id = $('#modal-retrieve').data('idbudget');
		$('#modal-retrieve').css('display','none');
		$('#modal-retrieve').data('idbudget', '');
		
		$("#loading").show();
		
		var url = "${ctx.contextPath}/.rest/private/shoppinglist/retrieve?id=" + id;
		
		$.ajax({
			url : url,
			type : "GET",
			cache : false, 
			async: false, 
			success : function(response) {
				refrescarPopupCarrito(response);
				console.log("Presupuesto recuperado");
				setMsgValid('',true);
			},
			error : function(response) {
				console.log("Error al recuperar el presupuesto");
				console.log(response);
				setMsgValid("* Ha ocurrido un error al recuperar el carrito",false);
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
	
		getAllShoppingLists();
	}
	
	function closeModal(name) {
		$("#"+name).css("display","none");
	}
	
	function showModalRetrieve(id){
		$('#modal-retrieve').data('idbudget', id);
		$('#modal-retrieve').css('display','flex');
	}
	
	function showModalRemove(id){
		$('#modal-remove').data('idbudget', id);
		$('#modal-remove').css('display','flex');
	}

	function saveBudgetPDF(id){
		window.open('${ctx.contextPath}/.rest/private/shoppinglist/pdfbudget?id='+id, '_blank');
	}

	function editBudget(id){
		var link = "${link!"#"}";
		window.open(link+id, '_self');
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
	
</script>