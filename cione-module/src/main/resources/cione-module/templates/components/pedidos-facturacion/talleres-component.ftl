[#include "../../includes/macros/cione-utils.ftl"]

<section>
<div class="cmp-tab">

	<ul class="tabs">
		<li id="tabTalleresPn" class="tab-link" data-tab="tab-pendiente">${i18n['cione-module.templates.components.pedidos-component.tab.pendientes']}</li>
		<li id="tabTalleresHs" class="tab-link" data-tab="tab-historial">${i18n['cione-module.templates.components.pedidos-component.tab.historial']}</li>
	</ul>

	<div id="tab-pendiente" class="tab-content">

		<section class="cmp-pedidospendientes mobile-wrapper">
			<form id="formTalleres" name="formTalleres" method="post">
				<h2 class="title">${i18n['cione-module.templates.components.talleres-component.talleres-pendientes']}</h2></br>
			    <ul class="accordion-mobile">
                    <li><a class="toggle" href="javascript:void(0);">
                            <div class="title">${i18n['cione-module.templates.components.talleres-component.buscar-talleres']}<i class="fa fa-chevron-right"> </i></div>
                        </a>
                        <ul class="inner show" style="display: block;">
                            <li>
                             </li>
                        </ul>
                    </li>
                </ul>
				<div class="panel-filter">
					<div class="filter">
						<label>${i18n['cione-module.templates.components.talleres-component.ref-trabajo']}</label> <input class="" id="" name="refTrabajo"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.num-pedido']}</label> <input class="" id="" name="numPedido"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.talleres-component.tipoTrabajo']}</label>
						<select id="" name="tipoTrabajo">
						  <option value=""></option>
						  <option value="Biselado">${i18n['cione-module.templates.components.talleres-component.tipoTrabajo.option.biselado']}</option>
						  <option value="Precalibrado y Biselado">${i18n['cione-module.templates.components.talleres-component.tipoTrabajo.option.precalibrado-biselado']}</option>
						  <option value="Otros trabajos">${i18n['cione-module.templates.components.talleres-component.tipoTrabajo.option.otros-trabajos']}</option>
						</select>
					</div>
					<div class="filter order-3">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.description']}</label> <input class="" id="" name="descripcion"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.talleres-component.ref-socio']}</label> <input class="" id="" name="idWeb"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter fecha order-2">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.fecha-desde']}</label> <input id="fechaIniP" class="inputfecha" type="text" name="fechaIni" autocomplete="off">						
					</div>
					<div class="filter fecha order-2">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.fecha-hasta']}</label> <input id="fechaFinP" class="inputfecha" id="" name="fechaFin"
							type="text" value="" autocomplete="off">						
					</div> 
					<input id="fechaFinP" class="inputfecha" id="" name="fechaFin" type="hidden" value="" autocomplete="off">
					<div class="filter hide-mobile" style="display: none">
						<input class="" id="pagina" name="pagina" type="hidden" value="0">
					</div>
					<div class="filter" style="display: none">
						<input class="" id="" name="historico" type="hidden" value="0">
					</div>
				</div>
				<div class="panelbuttons">
					<button id="talleres-component-search-btn"
							class="btn-blue icon-search" type="submit"
							onclick="searchTalleres(0); return false">${i18n['cione-module.templates.components.pedidos-component.search.btn-buscar']}</button>
				</div>
			</form>
			<div id="panel-table" class="panel-table">
				<table id="tablePendientes" class="table">
					<thead>
						<tr>
							<th>${i18n['cione-module.templates.components.talleres-component.ref-trabajo']}</th>
							<th>${i18n['cione-module.templates.components.pedidos-component.num-pedido']}</th>
							<th class="hide-mobile">${i18n['cione-module.templates.components.talleres-component.ref-socio-reduced']}</th>
							<th class="hide-mobile">${i18n['cione-module.templates.components.talleres-component.fecha']}</th>
							<th class="hide-mobile">${i18n['cione-module.templates.components.talleres-component.estado-optilab']}</th>
							<th class="hide-mobile">${i18n['cione-module.templates.components.talleres-component.estado-pedido']}</th>
							<th>${i18n['cione-module.templates.components.talleres-component.fecha-estimada-entrega']}</th>
							<th class="hide-mobile"></th>
						</tr>
					</thead>
					<tbody id="talleres-table-data"></tbody>
				</table>
				<div class="foot" id="footPen"></div>
			</div>
		</section>
	</div>
	
	<div id="tab-historial" class="tab-content">
		<section class="cmp-pedidoshistorial mobile-wrapper">
			<form id="formTalleresHis" name="formTalleresHis" method="post">
				<h2 class="title">${i18n['cione-module.templates.components.talleres-component.talleres-historial']}</h2></br>
			    <ul class="accordion-mobile">
                    <li><a class="toggle" href="javascript:void(0);">
                            <div class="title">${i18n['cione-module.templates.components.talleres-component.buscar-talleres']}<i class="fa fa-chevron-right"> </i></div>
                        </a>
                        <ul class="inner show" style="display: block;">
                            <li>
                             </li>
                        </ul>
                    </li>
                </ul>
				<div class="panel-filter">
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.talleres-component.ref-trabajo']}</label> <input class="" id="" name="refTrabajo"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.num-pedido']}</label> <input class="" id="" name="numPedido"
							type="text" value="${model.getNumPedido()!}" autocomplete="off">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.talleres-component.tipoTrabajo']}</label>
						<select id="" name="tipoTrabajo">
						  <option value=""></option>
						  <option value="Biselado">${i18n['cione-module.templates.components.talleres-component.tipoTrabajo.option.biselado']}</option>
						  <option value="Precalibrado y Biselado">${i18n['cione-module.templates.components.talleres-component.tipoTrabajo.option.precalibrado-biselado']}</option>
						  <option value="Otros trabajos">${i18n['cione-module.templates.components.talleres-component.tipoTrabajo.option.otros-trabajos']}</option>
						</select>
					</div>
					<div class="filter order-3">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.description']}</label> <input class="" id="" name="descripcion"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter order-1">
						<label>${i18n['cione-module.templates.components.talleres-component.ref-socio']}</label> <input class="" id="" name="idWeb"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter fecha order-2">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.fecha-desde']}</label> <input id="fechaIniH" class="inputfecha" name="fechaIni" type="text" autocomplete="off">
					</div>
					<div class="filter fecha order-2">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.fecha-hasta']}</label> <input id="fechaFinH" class="inputfecha" id="" name="fechaFin"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter " style="display: none">
						<input class="" id="pagina" name="pagina" type="hidden" value="0">
					</div>
					<div class="filter" style="display: none">
						<input class="" id="" name="historico" type="hidden" value="1">
					</div>
				</div>
				<div class="panelbuttons">
					<button id="tallereshis-component-search-btn"
							class="btn-blue icon-search" type="submit"
							onclick="searchTalleres(1); return false">${i18n['cione-module.templates.components.pedidos-component.search.btn-buscar']}</button>
				</div>
			</form>
			<div class="panel-table">
				<table id="tableHistorial" class="table">
					<thead>
						<tr>
							<th>${i18n['cione-module.templates.components.talleres-component.ref-trabajo']}</th>
							<th>${i18n['cione-module.templates.components.pedidos-component.num-pedido']}</th>
							<th class="hide-mobile">${i18n['cione-module.templates.components.talleres-component.ref-socio-reduced']}</th>
							<th class="hide-mobile">${i18n['cione-module.templates.components.talleres-component.fecha']}</th>
							<th class="hide-mobile">${i18n['cione-module.templates.components.talleres-component.estado-optilab']}</th>
							<th class="hide-mobile">${i18n['cione-module.templates.components.talleres-component.estado-pedido']}</th>
							<th>${i18n['cione-module.templates.components.talleres-component.fecha-estimada-entrega']}</th>
							<th class="hide-mobile"></th>
						</tr>
					</thead>
					<tbody id="tallereshis-table-data"></tbody>
				</table>
				<div class="foot" id="footHis"></div>
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


<script>
	var page = 0;
	var nPages = 0;
	var nreg = 0;
	var filter = {};
	var talleresLoaded = {};

	function templateTaller(taller,tab,index) {
		
		var html = "";
		html += "<tr data-id='" + index + "'>";
		html += "<td>" + taller.refTrabajo + "</td>";
		html += "<td>" + taller.numPedido + "</td>";
		html += "<td class='hide-mobile'>" + taller.idWeb + "</td>";
		html += "<td class='hide-mobile'>" + taller.fechaView + "</td>";
		html += "<td class='hide-mobile'>" + taller.estadoOptilab + "</td>";	
		html += "<td class='hide-mobile'>" + taller.estadoPedido + "</td>";
		html += "<td>" + taller.fechaEstimadaEntregaView + "</td>";		
		html += "<td class='masinfo hide-mobile'><a class='masinforesponsive' href='javascript:void(0)' onclick='getLineasTaller(\"" + taller.idServicio + "\", \"" + taller.numPedido + "\", \"" + tab + 
		"\",\"" + tab + "\",this)'>${i18n['cione-module.templates.components.pedidos-component.more-info']}</a></td>";
		html += "</tr>";

		//subtable		 
	 	html += "<tr class='subtabla'>";
		html += "<td colspan='9'>";
		html += "<table class='table hide-mobile'>";
		html += "<thead>";
		html += "<tr class='encabezado'>";
		html += "<th>${i18n['cione-module.templates.components.talleres-component.search.description']}</th>";
		html += "<th>${i18n['cione-module.templates.components.talleres-component.tipoTrabajo']}</th>";
		html += "<th>${i18n['cione-module.templates.components.talleres-component.cantidad']}</th>";
		[#if showPrices(ctx.getUser())]
		html += "<th>${i18n['cione-module.templates.components.talleres-component.precio']}</th>";
		[/#if]
		html += "<th>${i18n['cione-module.templates.components.talleres-component.refSocio']}</th>";
		html += "<th>${i18n['cione-module.templates.components.talleres-component.refTrabajo']}</th>";
		html += "<th>${i18n['cione-module.templates.components.talleres-component.estadoPedido']}</th>";
		html += "</tr>";
		html += "</thead>";
		html += "<tbody id='" + (taller.idServicio + '-' + taller.numPedido).replace('.', '-') + tab + "'>";
		html += "</tbody>";
		html += "</table>";
		html += "</td>"; //td-colspan-9
		html += "</tr>"; 
		//subtable		
		return html;
	}

	function templateLineaTaller(lineataller) {
		var html = "";
		html += "<tr>";
		if (lineataller.descripcion.length > 50 && !$(".panel-table").hasClass("mobile")) {
			html += "<td title='" + lineataller.descripcion + "'>" + lineataller.descripcion.substring(0, 50) + "...</td>";
		} else {
			html += "<td>" + lineataller.descripcion + "</td>";
		}		
		//html += "<td>" + lineataller.descripcion + "</td>";
		html += "<td>" + lineataller.tipoTrabajo + "</td>";
		html += "<td align='right'>" + lineataller.cantidad + "</td>";
		[#if showPrices(ctx.getUser())]
		html += "<td align='right'>" + lineataller.precio + "</td>";
		[/#if]
		html += "<td>" + lineataller.refSocio + "</td>";
		html += "<td>" + lineataller.refTrabajo + "</td>";
		html += "<td>" + lineataller.estadoPedido + "</td>";
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
		
		var html = templateNoRecordsFoundForTable(columns);
		
		return html;
	}

	function templateShowMore(tab,cPage,nPages,regTotales){

		var accReg = 0;

		if (cPage == nPages) {
			accReg = regTotales - (cPage - 1)*15;
		} else {
			accReg = 15;
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
			html += "<span class='vermas' onclick='showMoreTalleres(\"" + tab + "\")'>";
			html += "${i18n['cione-module.templates.components.pedidos-component.search.show-more']}";
		}

		html += "</span>";
		html += " ";
		html += "<span></span>";

		return html;
	}

	function searchTalleres(tab){

		page = 1;
		nreg = 0;
		talleresLoaded = {};

		clearErrorMessages();

		if (tab == 0) {
			var oForm = document.forms["formTalleres"];
			var vForm = validateForm(oForm);
			if(vForm){
				$("#talleres-table-data").html("");		
				$("#formTalleres input[name=pagina]").val(page);
				filter = getFormData($("#formTalleres"));
				getTalleres(tab);
			}

		} else {
			var oForm = document.forms["formTalleresHis"];
			var vForm = validateForm(oForm);			
			if (vForm) {
				$("#tallereshis-table-data").html("");
				$("#formTalleresHis input[name=pagina]").val(page);
				filter = getFormData($("#formTalleresHis"));
				getTalleres(tab);
			}
		}
		
	}

	function showMoreTalleres(tab){
		page = page+1;

		if (tab == 0) {
			$("#formTalleres input[name=pagina]").val(page);
			filter = getFormData($("#formTalleres"));
		} else {
			$("#formTalleresHis input[name=pagina]").val(page);
			filter = getFormData($("#formTalleresHis"));
		}

		getTalleres(tab);		
	}

	function getTalleres(tab) {

		$("#loading").show();
		if (tab == 0) {
			$("#talleres-component-search-btn").attr("disabled", "disabled");	
		} else {
			$("#tallereshis-component-search-btn").attr("disabled", "disabled");
		}		
		$.ajax({
			url : PATH_API + "/private/talleres/v1/talleres",
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
		        	response.serviciosTaller.forEach(function(taller){
		        		count++;
		        		listResult.push(templateTaller(taller,tab,count));
		        	});					
				} else {
					listResult.push(templateNoRecordsFound(tab));
				}

	        	if (tab == 0) {
	        		$("#talleres-table-data").append(listResult.join(" "));
	        		$("#footPen").empty();
	        		if (regTotales > 0) {
	        			$("#footPen").append(templateShowMore(tab,cPage,nPages,regTotales));	
	        		}						    	
	        	} else{
	        		$("#tallereshis-table-data").append(listResult.join(" "));
					$("#footHis").empty();
					if (regTotales > 0) {
						$("#footHis").append(templateShowMore(tab,cPage,nPages,regTotales));
					}
	        	}
			},
			error : function(response) {
				alert("error");				
				//$("#change-pwd-result").html("<p>" +  response.responseJSON.error.message + "</p>");
			},
			complete : function(response) {
				$("#loading").hide();

				//$("#formTalleres")[0].reset();
				if (tab == 0) {
					$("#tabTalleresPn").addClass("current");
					$("#tab-pendiente").addClass("current");
					$("#talleres-component-search-btn").removeAttr("disabled");
				} else {
					$("#tabTalleresHs").addClass("current");
					$("#tab-historial").addClass("current");
					$("#tallereshis-component-search-btn").removeAttr("disabled");
				}

				if (page >= nPages) {
					$(".vermas").prop("onclick", null).off("click");
				} else{
					$(".vermas").attr("onclick","showMoreTalleres(\"" + tab + "\")");
				}

				retailerZebra();

			}

		});

		return false;
	}

	function toggleTaller(element){  		
  		if ($(element).parent().parent().hasClass("active")) {
            $(element).parent().parent().removeClass("active");
        } else {
            //$('.masinfo > a').parents('tr').removeClass("active");            
            $(element).parent().parent().addClass("active");
        }

        var box = $(element).parents('tr').next();
        if ($(box).hasClass("subtabla")) {
            box.toggle();
        }
  	}

  	function getLineasTaller(idServicio, numPedido, historico, tab, element) {
  		[#-- El id de la tabla tiene que formar con idServicio + numPedido porque hay servicios repetidos --]
  	
		if(talleresLoaded[idServicio+numPedido]){
			toggleTaller(element);
			if (!($(".panel-table").hasClass("mobile"))) {
				verifyTest(element);
				return;
			}
		}else{
			talleresLoaded[idServicio+numPedido] = true;
		}		
		
		$("#loading").show();				
		$.ajax({
			url : PATH_API + "/private/talleres/v1/info-taller?idServicio=" + idServicio + "&numPedido=" + numPedido + "&historico=" + historico,
			type : "GET",					
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			success : function(response) {			
				var listResult = [];	        	
	        	response.forEach(function(lineaTaller){
	        		listResult.push(templateLineaTaller(lineaTaller));
	        	})	
				$("#" + (idServicio + '-' + numPedido).replace('.', '-') + tab).empty().append(listResult.join(" "));

				if ($(".panel-table").hasClass("mobile")) {
					loadsubtable(element); 
				}

	        	toggleTaller(element);
			},
			error : function(response) {
				alert("error");
			},
			complete : function(response) {
				$("#loading").hide();
                if (!($(".panel-table").hasClass("mobile"))) {
                    verifyTest(element);
                }  						
			}
		});
	
		return false;
	}

	function initPage(){

		var param = "numPedido";
		var numPedido = getParameterByName(param);
		if( numPedido ) {
			$("#formTalleresHis input[name=numPedido]").val(numPedido);
			filter = getFormData($("#formTalleresHis"));
			searchTalleres(1);
		} else {
			searchTalleres(0);
		}
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
		var temp = document.getElementById("talleres-table-data").parentNode;
		var child = temp.children;
		if (child.length > 0){
			searchTalleres(0);
		}  	
  	} else {
  		var temp = document.getElementById("tallereshis-table-data").parentNode;
  		var child = temp.children;
		if (child.length > 0){
			searchTalleres(1);
		}
  	}
  }

  bindAll();
}

var connectTabs = new Tabs();

</script>
