<#include "../includes/macros/cione-utils.ftl">

<section>
<div class="cmp-tab">

	<div id="tab-auditar" class="tab-content current">


		<section class="cmp-pedidospendientes mobile-wrapper">
			<form id="formAuditar" name="formAuditar" onsubmit="return false">
				<h2 class="title">${i18n['cione-module.templates.components.auditar-documentos-component.auditar-documentos']}</h2></br>
			    <ul class="accordion-mobile">
                    <li><a class="toggle" href="javascript:void(0);">
                            <div class="title">${i18n['cione-module.templates.components.auditar-documentos-component.buscar-auditoria']}<i class="fa fa-chevron-right"> </i></div>
                        </a>
                        <ul class="inner show" style="display: block;">
                            <li>
                             </li>
                        </ul>
                    </li>
                </ul>
				<div class="panel-filter">
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.auditar-documentos-component.search.cod-socio']}</label> <input class="" id="" name="codSocio"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.auditar-documentos-component.search.nombre-documento']}</label> <input class="" id="" name="nombreDocumento"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter fecha order-2">
						<label>${i18n['cione-module.templates.components.auditar-documentos-component.search.fecha-desde']}</label> <input id="fechaIniH" class="inputfecha" name="fechaIni" type="text" autocomplete="off">
					</div>
					<div class="filter fecha order-2">
						<label>${i18n['cione-module.templates.components.auditar-documentos-component.search.fecha-hasta']}</label> <input id="fechaFinH" class="inputfecha" id="" name="fechaFin"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter order-1">
						<label>${i18n['cione-module.templates.components.auditar-documentos-component.search.descargas']}</label> <input class="" id="descargas" name="descargas"
							type="text" value="" autocomplete="off">
					</div>
				</div>
				<div class="panelbuttons">
					<button id="auditar-component-search-btn"
							class="btn-blue icon-search"
							onclick="searchAuditoriasInit()">${i18n['cione-module.templates.components.auditar-documentos-component.search.btn-buscar']}</button>
				</div>
			</form>
			<div id="panel-table" class="panel-table">
				<table id="tableAuditar" class="table">
					<thead>
						<tr>
							<!--<th>${i18n['cione-module.templates.components.auditar-documentos-component.search.cod-socio']}</th>-->
							<th>${i18n['cione-module.templates.components.auditar-documentos-component.search.nombre-documento']}</th>
							<th>${i18n['cione-module.templates.components.auditar-documentos-component.search.ruta']}</th>
							<th>${i18n['cione-module.templates.components.auditar-documentos-component.search.descargas']}</th>
							<th>${i18n['cione-module.templates.components.auditar-documentos-component.search.fecha-descarga']}</th>
							<th></th>
						</tr>
					</thead>
					<tbody id="auditar-table-data"></tbody>
				</table>
				<div class="foot" id="footPen"></div>
			</div>
		</section>
	</div>

</div>
<div>		
	<a id="btnExport" class="btnExport" href="javascript:exportToExcel('tableAuditar')">${i18n['cione-module.global.btn-download']}</a>
</div>
<form id="form-export-data" method="post" action="">
	<input id="export-data" name="export-data"  value="" type="hidden"> 	
</form>
</section>

<script>


	var page = 0;
	var nPages = 0;
	var nreg = 0;
	var filter = {};
	var listResult = [];
	var pageLimit = 10;
	var shownResults = 0;
	var regTotales = 0;
	var nombreDocumento = "";
	var listDocAditoria = [["${i18n['cione-module.templates.components.auditar-documentos-component.search.nombre-documento']}", "${i18n['cione-module.templates.components.auditar-documentos-component.search.ruta']}","${i18n['cione-module.templates.components.auditar-documentos-component.search.descargas']}","${i18n['cione-module.templates.components.auditar-documentos-component.search.fecha-descarga']}"]];

	function templateAuditoria(auditoria,index,auditorias) {
		
		var html = "";
		var descargas = 0;
		var listAux = [];
		if (nombreDocumento == "" || nombreDocumento != auditoria.nombreDocumento) {
			html += "<tr data-id='" + index + "'>";
			//html += "<td>" + auditoria.idSocio + "</td>";
			html += "<td>" + auditoria.nombreDocumento + "</td>";
			html += "<td>" + auditoria.rutaDocumento + "</td>";
			auditorias.forEach(function(audit){
    			if(auditoria.nombreDocumento == audit.nombreDocumento) {
	        		descargas += audit.descargas;
				}
    		});	
			html += "<td class='hide-mobile'>" + descargas + "</td>";
			html += "<td class='hide-mobile'>" + new Date (auditoria.fechaDescarga).toLocaleString().replaceAll('/', '-') + "</td>";		
			html += "<td class='masinfo hide-mobile'><a class='masinforesponsive' href='javascript:void(0)' onclick='toggleConsumo(this)'>${i18n['cione-module.templates.components.mis-consumos.more-info']}</a></td>";
			html += "</tr>";
			
			listAux.push(auditoria.nombreDocumento);
			listAux.push(auditoria.rutaDocumento);
			listAux.push(descargas.toString());
			listAux.push(new Date (auditoria.fechaDescarga).toLocaleString().replaceAll('/', '-'));
			listAux.push("${i18n['cione-module.templates.components.mis-consumos.more-info']}");
			listDocAditoria.push(listAux);
			listAux = [];
			
			html += "<tr class='subtabla subtabla-auditoria'>";
				html += "<td colspan='5'>";
					html += "<table class='table'>";
					html += "<thead>";
						html += "<tr>";
							html += "<th>${i18n['cione-module.templates.components.auditar-documentos-component.search.cod-socio']}</th>";
							html += "<th>${i18n['cione-module.templates.components.auditar-documentos-component.search.nombre-documento']}</th>";
							html += "<th>${i18n['cione-module.templates.components.auditar-documentos-component.search.ruta']}</th>";
							html += "<th>${i18n['cione-module.templates.components.auditar-documentos-component.search.descargas']}</th>";
							html += "<th>${i18n['cione-module.templates.components.auditar-documentos-component.search.fecha-descarga']}</th>";
						html += "</tr>";
					html += "</thead>";
					html += "<tbody id='row-"+ auditoria.nombreDocumento + index +"'>";
						var i = 0
						listAux.push("${i18n['cione-module.templates.components.auditar-documentos-component.search.cod-socio']}");
						listAux.push("${i18n['cione-module.templates.components.auditar-documentos-component.search.nombre-documento']}");
						listAux.push("${i18n['cione-module.templates.components.auditar-documentos-component.search.ruta']}");
						listAux.push("${i18n['cione-module.templates.components.auditar-documentos-component.search.descargas']}");
						listAux.push("${i18n['cione-module.templates.components.auditar-documentos-component.search.fecha-descarga']}");
						listDocAditoria.push(listAux);
						listAux = [];
						auditorias.forEach(function(audit){
		        			i++;
		        			if(auditoria.nombreDocumento == audit.nombreDocumento) {
		        				listAux.push(audit.idSocio);
								listAux.push(audit.nombreDocumento);
								listAux.push(audit.rutaDocumento);
								listAux.push(audit.descargas.toString());
								listAux.push(new Date (audit.fechaDescarga).toLocaleString().replaceAll('/', '-'));
								listDocAditoria.push(listAux);
								listAux = [];

				        		html += "<tr data-id='" + audit.nombreDocumento + i + "'>";
								html += "<td>" + audit.idSocio + "</td>";
								html += "<td>" + audit.nombreDocumento + "</td>";
								html += "<td>" + audit.rutaDocumento + "</td>";
								html += "<td class='hide-mobile'>" + audit.descargas + "</td>";
								html += "<td class='hide-mobile'>" + new Date (audit.fechaDescarga).toLocaleString().replaceAll('/', '-') + "</td>";
								html += "</tr>";
							}
		        		});	
					html += "</tbody>";
					html += "</table>";
				html += "</td>";
			html += "</tr>";
		}
	
		nombreDocumento = auditoria.nombreDocumento;
		return html;
	}
	
	function toggleConsumo(element) {
		toggleRow(element);
	}

	function templateNoRecordsFound(){

		var columns = $("#tableAuditar").find('tr')[0].cells.length;
		
		var html = templateNoRecordsFoundForTable(columns);
		
		return html;
	}

	function templateShowMore(cPage,nPages,regTotales){

		var accReg = 0;

		if (cPage == nPages) {
			accReg = regTotales - (cPage - 1)*pageLimit;
		} else {
			accReg = pageLimit;
		}

		nreg = nreg + accReg;

		var html="";
		html += "<span>${i18n['cione-module.templates.components.pedidos-component.search.showing-page']} " + nreg;
		html += " ${i18n['cione-module.templates.components.pedidos-component.search.showing-page-of']} ";
		html += "<span class='cantrow'>" + regTotales + "</span>";
		html += " ";
		html += "${i18n['cione-module.templates.components.pedidos-component.search.results']}";
		html += "</span>";
		html += " ";
		
		if (cPage == nPages){
			html += "<span>";
			html += " ";
		} else {
			html += "<span class='vermas' onclick='showMoreAuditorias()'>";
			html += "${i18n['cione-module.templates.components.pedidos-component.search.show-more']}";
		}

		html += "</span>";
		html += " ";
		html += "<span></span>";

		return html;
	}
	
	function searchAuditoriasInit() {
		listResult = [];
		searchAuditorias();
	}

	function searchAuditorias(){
		page = 1;
		nreg = 0;
		auditoriasLoaded = {};
		

		clearErrorMessages();

		var oForm = document.forms["formAuditar"];
		var vForm = validateForm(oForm);
		if(vForm){
			$("#auditar-table-data").html("");		
			$("#formAuditar input[name=pagina]").val(page);
			filter = getFormData($("#formAuditar"));
			getAuditorias();
		}
		
	}

	function showMoreAuditorias(){
		page = page+1;

		current = shownResults;
		shownResults += pageLimit;
		
		if (shownResults > regTotales) {
			shownResults = regTotales;
		}
		
		for (var i = current; i < shownResults; i++) { 
			$("#auditar-table-data").append(listResult[i]);
		}
		
		$("#footPen").empty();
		if (regTotales > 0) {
			$("#footPen").append(templateShowMore(page,nPages,regTotales));	
		}
		retailerZebra();
	}

	function getAuditorias() {

		$("#loading").show();
	
		$.ajax({
			url : PATH_API + "/private/auditar-documentos/v1/auditorias",
			type : "POST",
			data : JSON.stringify(filter),
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			success : function(response) {
				var count = 0;
				
				var cPage = 1;
				regTotales = response.numRegistros;
				//nPages = response.ultimaPagina;
				nPages = Math.ceil(regTotales / pageLimit);
				var auditorias = response.auditorias;
				if (regTotales > 0){
		        	response.auditorias.forEach(function(auditoria){
		        		count++;
		        		listResult.push(templateAuditoria(auditoria,count,auditorias));
		        	});					
				} else {
					listResult.push(templateNoRecordsFound());
				}
				
				shownResults = pageLimit;
				if (pageLimit > regTotales) {
					shownResults = regTotales;
				}
				for (var i = 0; i < shownResults; i++) { 
					$("#auditar-table-data").append(listResult[i]);
				}
				$("#footPen").empty();
				if (regTotales > 0) {
					$("#footPen").append(templateShowMore(cPage,nPages,regTotales));	
				}						    	

			},
			error : function(response) {
				alert("error");				
				//$("#change-pwd-result").html("<p>" +  response.responseJSON.error.message + "</p>");
			},
			complete : function(response) {
				$("#loading").hide();

				$("#tabPedidosPn").addClass("current");
				$("#tab-pendiente").addClass("current");
				$("#pedidos-component-search-btn").removeAttr("disabled");


				if (page >= nPages) {
					$(".vermas").prop("onclick", null).off("click");
				} else{
					$(".vermas").attr("onclick","showMoreAuditorias()");
				}

				retailerZebra();

			}

		});

		return false;
	}


	function initPage(){
		searchAuditorias();
		exportToExcel= function(){
			var url = PATH_API + "/private/export/v1/xls";
			$("#form-export-data")[0].action=url;			
			$("#export-data").val(JSON.stringify(listDocAditoria));
			$("#form-export-data")[0].submit();		
		}
	}


/***** */
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

	var temp = document.getElementById("auditar-table-data").parentNode;
	var child = temp.children;
	if (child.length > 0){
		searchAuditorias();
	}  	

  }

  bindAll();
}

var connectTabs = new Tabs();


</script>