<#include "../includes/macros/cione-utils.ftl">

<section>
<div class="cmp-tab">

	<div id="tab-campaign" class="tab-content current">


		<section class="cmp-pedidospendientes mobile-wrapper">
			<form id="formCampaign" name="formCampaign" onsubmit="return false">
				<h2 class="title">${i18n['cione-module.templates.components.campaigns-component.campaigns']}</h2></br>
			    <ul class="accordion-mobile">
                    <li><a class="toggle" href="javascript:void(0);">
                            <div class="title">${i18n['cione-module.templates.components.campaigns-component.buscar-campaign']}<i class="fa fa-chevron-right"> </i></div>
                        </a>
                        <ul class="inner show" style="display: block;">
                            <li>
                             </li>
                        </ul>
                    </li>
                </ul>
				<div class="panel-filter">
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.campaigns-component.search.nombre-campana']}</label> <input class="" id="" name="campaignName"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.campaigns-component.search.cod-socio']}</label> <input class="" id="" name="codSocio"
							type="text" value="" autocomplete="off">
					</div>
				</div>
				<div class="panelbuttons">
					<button id="campaign-component-search-btn"
							class="btn-blue icon-search"
							onclick="searchCampaigns()">${i18n['cione-module.templates.components.campaigns-component.search.btn-buscar']}</button>
				</div>
			</form>
			<div id="campaigns-table" class="panel-table">
				<table id="tableCampaign" class="table">
					<thead>
						<tr>
							<th>${i18n['cione-module.templates.components.campaigns-component.search.nombre-campana']}</th>
							<th>${i18n['cione-module.templates.components.campaigns-component.search.cod-socio']}</th>
							<th>${i18n['cione-module.templates.components.campaigns-component.search.nombre-socio']}</th>
							<th>${i18n['cione-module.templates.components.campaigns-component.search.direccion-socio']}</th>
							<th>${i18n['cione-module.templates.components.campaigns-component.search.poblacion']}</th>
							<th>${i18n['cione-module.templates.components.campaigns-component.search.provincia']}</th>
							<th>${i18n['cione-module.templates.components.campaigns-component.search.codigo-postal']}</th>
							<th>${i18n['cione-module.templates.components.campaigns-component.search.opcion']}</th>
							<th>${i18n['cione-module.templates.components.campaigns-component.search.fecha']}</th>
						</tr>
					</thead>
					<tbody id="campaign-table-data"></tbody>
				</table>
				<div class="foot" id="footPen"></div>
			</div>
		</section>
	</div>
	<div style="margin-top:15px">		
		<a id="btnExport" class="btnExport" href="javascript:exportCampaignsToExcel()">${i18n['cione-module.global.btn-download']}</a>
	</div>
 	<form id="form-export-data" method="post" action="">
 		<input id="export-data" name="export-data"  value="" type="hidden"> 	 	
 	</form>

</div>

</section>

<script>


	var page = 0;
	var nPages = 0;
	var nreg = 0;
	var filter = {};
	var listResult = [];
	var campaignsJson = {};
	var pageLimit = 10;
	var shownResults = 0;
	var regTotales = 0;

	function templateCampaign(campaign,index) {
		
		var html = "";
		html += "<tr data-id='" + index + "'>";
		html += "<td>" + campaign.nombre + "</td>";
		html += "<td>" + campaign.idSocio + "</td>";
		html += "<td>" + campaign.nombreSocio + "</td>";
		html += "<td>" + campaign.direccionSocio + "</td>";
		html += "<td>" + campaign.poblacion + "</td>";
		html += "<td>" + campaign.provincia + "</td>";
		html += "<td>" + campaign.codigoPostal + "</td>";
		html += "<td>" + campaign.opcion + "</td>";
		html += "<td class='hide-mobile'>" + new Date (campaign.fecha).toLocaleString().replaceAll('/', '-') + "</td>";		
		html += "</tr>";	
		return html;
	}

	function templateNoRecordsFound(){

		var columns = $("#tableCampaign").find('tr')[0].cells.length;
		
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
			html += "<span class='vermas' onclick='showMoreCampaigns()'>";
			html += "${i18n['cione-module.templates.components.pedidos-component.search.show-more']}";
		}

		html += "</span>";
		html += " ";
		html += "<span></span>";

		return html;
	}

	function searchCampaigns(){

		page = 1;
		nreg = 0;
		campaignsLoaded = {};

		clearErrorMessages();

		var oForm = document.forms["formCampaign"];

		$("#campaign-table-data").html("");		
		$("#formCampaign input[name=pagina]").val(page);
		filter = getFormData($("#formCampaign"));
		getCampaigns();
		
		
	}

	function showMoreCampaigns(){
		page = page+1;

		current = shownResults;
		shownResults += pageLimit;
		
		if (shownResults > regTotales) {
			shownResults = regTotales;
		}
		
		for (var i = current; i < shownResults; i++) { 
			$("#campaign-table-data").append(listResult[i]);
		}
		
		$("#footPen").empty();
		if (regTotales > 0) {
			$("#footPen").append(templateShowMore(page,nPages,regTotales));	
		}
		retailerZebra();
	}

	function getCampaigns() {

		$("#loading").show();
	
		$.ajax({
			url : PATH_API + "/private/campaigns/v1/campaigns",
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
				campaignsJson = response.campaigns;
				listResult = [];

				if (regTotales > 0){
		        	response.campaigns.forEach(function(campaign){
		        		count++;
		        		listResult.push(templateCampaign(campaign,campaign.historico,count));
		        	});					
				} else {
					listResult.push(templateNoRecordsFound());
				}
				
				shownResults = pageLimit;
				if (pageLimit > regTotales) {
					shownResults = regTotales;
				}
				
				for (var i = 0; i < shownResults; i++) { 
					$("#campaign-table-data").append(listResult[i]);
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
					$(".vermas").attr("onclick","showMoreCampaigns()");
				}

				retailerZebra();

			}

		});

		return false;
	}


	function initPage(){

		searchCampaigns();
	
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

	var temp = document.getElementById("campaign-table-data").parentNode;
	var child = temp.children;
	if (child.length > 0){
		searchCampaigns();
	}  	

  }

  bindAll();
}

var connectTabs = new Tabs();

function exportCampaignsToExcel(){
	
	var url = PATH_API + "/private/export/v1/xls";
	$("#form-export-data")[0].action=url;						
	$("#export-data").val(JSON.stringify(getCampaignsToExport()));
	$("#form-export-data")[0].submit();		
}

function getCampaignsToExport(){
	var result = [];	
	var filas = $("#campaigns-table tr");
	
	//Cabecera
	var columnas = filas[0].children;			
	var resultFilas = [];
	for(j=0; j<columnas.length; j++){
		var columna = columnas[j];				
		if(columna.nodeName === "TH" || columna.nodeName === "TD"){
			resultFilas.push(columna.innerText);
		}
	}
	result.push(resultFilas);
	
	//Campaigns
	campaignsJson.forEach(function(campaign){
		resultFilas = [];
		resultFilas.push(campaign.nombre);
		resultFilas.push(campaign.idSocio);
		resultFilas.push(campaign.nombreSocio);
		resultFilas.push(campaign.direccionSocio);
		resultFilas.push(campaign.poblacion);
		resultFilas.push(campaign.provincia);
		resultFilas.push(campaign.codigoPostal);
		resultFilas.push(campaign.opcion);
		resultFilas.push(new Date (campaign.fecha).toLocaleString().replaceAll('/', '-'));
		result.push(resultFilas);
	});	

	return result;
}

</script>