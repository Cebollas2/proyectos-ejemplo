<section>
<div class="cmp-tab">

	<ul class="tabs">
		<li id="tabAbonosPn" class="tab-link" data-tab="tab-pendiente">${i18n['cione-module.templates.components.interacciones.abiertas']}</li>
		<li id="tabAbonosHs" class="tab-link" data-tab="tab-historial">${i18n['cione-module.templates.components.interacciones.historial']}</li>
	</ul>

	<div id="tab-pendiente" class="tab-content">
		<section class="cmp-interacciones mobile-wrapper">
			<form id="formAbonos" name="formAbonos" method="post">
				<h2 class="title">${i18n['cione-module.templates.components.interacciones.interacciones-abiertas']}</h2></br>
				<!-- 
				<ul class="accordion-mobile">
                    <li><a class="toggle" href="javascript:void(0);">
                            <div class="title">${i18n['cione-module.templates.components.abonos-component.buscar-abonos']}<i class="fa fa-chevron-right"> </i></div>
                        </a>
                        <ul class="inner show" style="display: block;">
                            <li>
                             </li>
                        </ul>
                    </li>
                </ul>
                 -->
				<div style="display:none">
					<div class="filter" style="display: none">
						<input class="" id="" name="estado" type="hidden" value="1">
					</div>
					<div class="filter" style="display: none">
						<input class="" id="" name="pagina" type="hidden" value="0">
					</div>
				</div>
			</form>
			<div class="panel-table">
				<table id="tablePendientes" class="table abono-pendiente">
					<thead>
						<tr>
							<th>${i18n['cione-module.templates.components.interacciones.num_interaccion']}</th>
							<th>${i18n['cione-module.templates.components.interacciones.fecha']}</th>
							<th>${i18n['cione-module.templates.components.interacciones.tipo']}</th>
							<th class="hide-mobile">${i18n['cione-module.templates.components.interacciones.tema']}</th>
							<th class="hide-mobile">${i18n['cione-module.templates.components.interacciones.estado']}</th>
							<!-- 
							<th class="hide-mobile">${i18n['cione-module.templates.components.interacciones.fecha-cierre']}</th>
							<th class="hide-mobile">${i18n['cione-module.templates.components.interacciones.dias-resolucion']}</th>
							 -->
						</tr>
					</thead>
					<tbody id="abonos-table-data"></tbody>
				</table>	
				<div class="foot" id="footPen"></div>
			</div>	
			
			<div>		
				<a id="btnExport" class="btnExport" href="javascript:exportToExcel('tablePendientes')">${i18n['cione-module.global.btn-download']}</a>
			</div>
		</section>
	</div>

	<div id="tab-historial" class="tab-content">
		<section class="cmp-interaccionesHistorico mobile-wrapper">
			<form id="formAbonosHistorico" name="formAbonosHistorico" method="post">
				<h2 class="title">${i18n['cione-module.templates.components.interacciones.interacciones-historial']}</h2></br>
				<!-- 
				<ul class="accordion-mobile">
                    <li><a class="toggle" href="javascript:void(0);">
                            <div class="title">${i18n['cione-module.templates.components.abonos-component.buscar-abonos']}<i class="fa fa-chevron-right"> </i></div>
                        </a>
                        <ul class="inner show" style="display: block;">
                            <li>
                             </li>
                        </ul>
                    </li>
                </ul>
                 -->
				<div style="display:none">	
					<div class="filter" style="display: none">
						<input class="" id="" name="estado" type="hidden" value="0">
					</div>
					<div class="filter" style="display: none">
						<input class="" id="" name="pagina" type="hidden" value="0">
					</div>
				</div>
				</form>
				<div class="panel-table">
					<table id="tableHistorial" class="table abono-historico">
						<thead>
							<tr>
								<th>${i18n['cione-module.templates.components.interacciones.num_interaccion']}</th>
								<th>${i18n['cione-module.templates.components.interacciones.fecha']}</th>
								<th>${i18n['cione-module.templates.components.interacciones.tipo']}</th>
								<th class="hide-mobile">${i18n['cione-module.templates.components.interacciones.tema']}</th>
								<th class="hide-mobile">${i18n['cione-module.templates.components.interacciones.estado']}</th>
								<th class="hide-mobile">${i18n['cione-module.templates.components.interacciones.fecha-cierre']}</th>
								<th class="hide-mobile">${i18n['cione-module.templates.components.interacciones.dias-resolucion']}</th>
							</tr>
						</thead>
						<tbody id="abonos-historico-table-data"></tbody>
					</table>
					<div class="foot" id="footHis"></div>
				</div>
				
				<div>		
					<a id="btnExport" class="btnExport" href="javascript:exportToExcel('tableHistorial')">${i18n['cione-module.global.btn-download']}</a>
				</div>
				
			</section>
		</div>
	</div>
</section>

<!-- MODAL -->
<div class="modal" id="tablaModal" tabindex="-1" role="dialog">
    <div class="modal-dialog"  role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Modal title</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <p>Modal body text goes here.</p>
        </div>
      </div>
    </div>
</div>

<form id="form-export-data" method="post" action="">
	<input id="export-data" name="export-data"  value="" type="hidden"> 	 	
</form>


<script>
	var pagePn = 0;
	var pageHs = 0;
	var nPages = 0;
	var nreg = 0;
	var filter = {};
	var abonosLoaded = {};
	
	

	function templateInteraccion(interaccion, tab, index) {
		var html = "";
		html += "<tr data-id='" + index + "'>";
		html += "<td>" + interaccion.id + "</td>";
		html += "<td>" + interaccion.fecha + "</td>";
		html += "<td>" + interaccion.tipo+ "</td>";		
		html += "<td class='hide-mobile'>" + interaccion.tema + "</td>";
		html += "<td class='hide-mobile'>" + interaccion.estado + "</td>";
		if(tab == 1){
			html += "<td class='hide-mobile'>" + interaccion.fechaCierre + "</td>";
			html += "<td class='hide-mobile' align='right'>" + interaccion.dias + "</td>";
		}
	
		html += "</tr>";
			
		return html;
	}


	function templateNoRecordsFound(tab){
		var columns;
		if ( tab == 0) {
			columns = $("#tablePendientes").find('tr')[0].cells.length;
		} else {
			columns = $("#tableHistorial").find('tr')[0].cells.length;
		}
		var html="";
		html += "<tr id='trNoRecords'>";
		html += "<td class='text-center' colspan='" + columns + "'>";
		html += "<span class='text-muted'>${i18n['cione-module.templates.components.abonos-component.search.no-records-found']}</span>";
		html += "</td>";
		html += "</tr>";
		return html;
	}
	
	function searchAbonos(tab){
		pagePn = 1;
		pageHs = 1;
		nreg = 0;
		abonosLoaded = {};

		//clearErrorMessages();

		if (tab==0) {
			var oForm = document.forms["formAbonos"];
			//var vForm = validateForm(oForm);
			vForm = true;
			if(vForm) {
				$("#abonos-table-data").html("");		
				$("#formAbonos input[name=pagina]").val(pagePn);
				filter = getFormData($("#formAbonos"));
				getAbonos();
			}

		} else {
			var oForm = document.forms["formAbonosHistorico"];
			//var vForm = validateForm(oForm);
			if(true) {
				$("#abonos-historico-table-data").html("");
				$("#formAbonosHistorico input[name=pagina]").val(pageHs);
				filter = getFormData($("#formAbonosHistorico"));
				getAbonosHistorico();
			}
		}
	}
	
	function getAbonos() {
		$("#loading").show();
		$.ajax({
			url : PATH_API + "/private/interacciones/v1/interacciones",
			type : "POST",
			data : JSON.stringify(filter),
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			success : function(response) {
				var count = 0;			
				var listResult = [];
				var cPage = response.pagina;  
				var regTotales = response.numRegistros;
				nPages = response.ultimaPagina;

				if (regTotales > 0){
		        	response.interacciones.forEach(function(interaccion){
		        		count++;
		        		listResult.push(templateInteraccion(interaccion, 0, count));
		        	});					
				} else {
					listResult.push(templateNoRecordsFound(0));
				}
	        	
	        	$("#abonos-table-data").append(listResult.join(" "));
	        	$("#footPen").empty();
				if (regTotales > 0) {
					$("#footPen").append(templateShowMore(0,cPage,nPages,regTotales));
				}      	
			},
			error : function(response) {
				alert("error");				
				//$("#change-pwd-result").html("<p>" +  response.responseJSON.error.message + "</p>");
			},
			complete : function(response) {
				$("#loading").hide();
				//$("#formAbonos")[0].reset();
				$("#tabAbonosPn").addClass("current");
				$("#tab-pendiente").addClass("current");
				$("#abonos-component-search-btn").removeAttr("disabled");

				if (pagePn >= nPages) {
					$(".vermas").prop("onclick", null).off("click");
				} else{
					$(".vermas").attr("onclick","showMoreAbonos(0)");
				}

				retailerZebra();
			}
		});

		return false;
	}
	
	function getAbonosHistorico() {		
		$("#loading").show();
		$.ajax({
			url : PATH_API + "/private/interacciones/v1/interacciones",
			type : "POST",
			data : JSON.stringify(filter),
			contentType : 'application/json; charset=utf-8',
			dataType : "json",
			success : function(response) {				
				var count = 0;
				var listResult = [];
				var cPage = response.pagina;  
				var regTotales = response.numRegistros;
				nPages = response.ultimaPagina;

	        	if (regTotales > 0){
		        	response.interacciones.forEach(function(interaccion){
		        		count++;
		        		listResult.push(templateInteraccion(interaccion, 1, count));
		        	});					
				} else {
					listResult.push(templateNoRecordsFound(1));
				}

	        	$("#abonos-historico-table-data").append(listResult.join(" "));
	        	$("#footHis").empty();
				if (regTotales > 0) {
					$("#footHis").append(templateShowMore(1,cPage,nPages,regTotales));
				}      	
			},
			error : function(response) {
				alert("error");				
				//$("#change-pwd-result").html("<p>" +  response.responseJSON.error.message + "</p>");
			},
			complete : function(response) {
				$("#loading").hide();
				//$("#formAbonos")[0].reset();
				$("#tabAbonosHs").addClass("current");
				$("#tab-historial").addClass("current");
				$("#abonoshistorico-component-search-btn").removeAttr("disabled");

				if (pageHs >= nPages) {
					$(".vermas").prop("onclick", null).off("click");
				} else{
					$(".vermas").attr("onclick","showMoreAbonos(1)");
				}

				retailerZebra();
			}
		});

		return false;
	}

	
	function initPage(){
		searchAbonos(0);
		//setTimeout ("searchAbonos(1);", 200);
	}
	
	function showMoreAbonos(tab){
		//page = page+1;

		if (tab == 0) {
			pagePn = pagePn+1;
			$("#formAbonos input[name=pagina]").val(pagePn);
			filter = getFormData($("#formAbonos"));
			getAbonos();
		} else {
			pageHs = pageHs+1;
			$("#formAbonosHistorico input[name=pagina]").val(pageHs);
			filter = getFormData($("#formAbonosHistorico"));
			getAbonosHistorico();
		}

				
	}
	
	function templateShowMore(tab,cPage,nPages,regTotales){

		var accReg = 0;

		if (cPage == nPages) {
			accReg = regTotales - (cPage - 1)*10;
		} else {
			accReg = 10;
		}

		nreg = nreg + accReg;

		var html="";
		html += "<span>${i18n['cione-module.templates.components.abonos-component.search.showing-page']} " + nreg;
		html += " ${i18n['cione-module.templates.components.abonos-component.search.showing-page-of']} ";
		html += "<span class='cantrow'>" + regTotales + "</span>";
		html += " ";
		html += "${i18n['cione-module.templates.components.abonos-component.search.results']}";
		html += "</span>";
		html += " ";
		
		if (cPage == nPages){
			html += "<span>";
			html += " ";
		} else {
			html += "<span class='vermas' onclick='showMoreAbonos(\"" + tab + "\")'>";
			html += "${i18n['cione-module.templates.components.abonos-component.search.show-more']}";
		}

		html += "</span>";
		html += " ";
		html += "<span></span>";

		return html;
	}

</script>
<script>

'use strict';

function Tabs() {
  var bindAll = function() {
    var menuElements = document.querySelectorAll('[data-tab]');
    for(var i = 0; i < menuElements.length ; i++) {
      menuElements[i].addEventListener('click', change, false);
    }
  }

  var clear = function() {
    var menuElements = document.querySelectorAll('[data-tab]');
    for(var i = 0; i < menuElements.length ; i++) {
      menuElements[i].classList.remove('current');
      var id = menuElements[i].getAttribute('data-tab');
      document.getElementById(id).classList.remove('current');
    }
  }

  var change = function(e) {
    clear();
    var id = e.currentTarget.getAttribute('data-tab');
	if (id == "tab-pendiente") {
		var temp = document.getElementById("abonos-table-data").parentNode;
		var child = temp.children;
		if (child.length > 0){
			searchAbonos(0);
		}  	
  	} else {
  		var temp = document.getElementById("abonos-historico-table-data").parentNode;
  		var child = temp.children;
		if (child.length > 0){
			searchAbonos(1);
		}
  	}
  }

  bindAll();
}

var connectTabs = new Tabs();


</script>
