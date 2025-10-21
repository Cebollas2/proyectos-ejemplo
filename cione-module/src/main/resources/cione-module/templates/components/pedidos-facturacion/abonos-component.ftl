[#include "../../includes/macros/cione-utils.ftl"]

<section>
<div class="cmp-tab">

	<ul class="tabs">
		<li id="tabAbonosPn" class="tab-link" data-tab="tab-pendiente">${i18n['cione-module.templates.components.abonos-component.tab.pendientes']}</li>
		<li id="tabAbonosHs" class="tab-link" data-tab="tab-historial">${i18n['cione-module.templates.components.abonos-component.tab.historial']}</li>
	</ul>

	<div id="tab-pendiente" class="tab-content">
		<section class="cmp-abonospendientes mobile-wrapper">
			<form id="formAbonos" name="formAbonos" method="post">
				<h2 class="title">${i18n['cione-module.templates.components.abonos-component.abonos-pendientes']}</h2></br>
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
				<div class="panel-filter">
					<div class="filter order-1">
						<label>${i18n['cione-module.templates.components.abonos-component.search.num-abono']}</label> <input class="" id="" name="numAbono" type="text"
								value="">
					</div>
					<div class="filter fecha order-2">
						<label>${i18n['cione-module.templates.components.abonos-component.search.fecha-desde']}</label> <input class="inputfecha" id="fechaIniP" name="fechaIni" type="text">
					</div>
					<div class="filter fecha order-2">
						<label>${i18n['cione-module.templates.components.abonos-component.search.fecha-hasta']}</label> <input class="inputfecha" id="fechaFinP" name="fechaFin" type="text" value="">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.abonos-component.search.id-web']}</label> <input class="" id="" name="idWeb" type="text" value="">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.abonos-component.search.ref-socio']}</label> <input class="" id="" name="refSocio" type="text" value="">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.abonos-component.search.ref-web']}</label> <input class="" id="" name="refWeb" type="text" value="">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.abonos-component.search.num-albaran-cargo']}</label> <input class="" id="" name="albaranCargo" type="text" value="">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.abonos-component.search.num-pedido-cargo']}</label> <input class="" id="" name="numPedido" type="text" value="">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.abonos-component.search.tipo-abono']}</label> <input class="" id="" name="tipoAbono" type="text" value="">
					</div>
					<div class="filter order-3">
						<label>${i18n['cione-module.templates.components.abonos-component.search.description']}</label> <input class="" id="" name="descripcion"
							type="text" value="">
					</div>
					<div class="filter hide-mobile" style="display: none;">
						<label>${i18n['cione-module.templates.components.abonos-component.search.marca']}</label> <input class="" id="" name="marca" type="text" value="">
					</div>
					<div class="filter" style="display: none">
						<input class="" id="" name="historico" type="hidden" value="0">
					</div>
					<div class="filter" style="display: none">
						<input class="" id="" name="pagina" type="hidden" value="0">
					</div>
				</div>
				<div class="panelbuttons">
					<button id="abonos-component-search-btn"
							class="btn-blue icon-search" type="submit"
							onclick="searchAbonos(0); return false">${i18n['cione-module.global.btn-search']}</button>
				</div>
			</form>
			<div class="panel-table">
				<table id="tablePendientes" class="table abono-pendiente">
					<thead>
						<tr>
							<th>${i18n['cione-module.templates.components.abonos-component.num-abono']}</th>
							<th>${i18n['cione-module.templates.components.abonos-component.fecha-abono']}</th>
							<th>${i18n['cione-module.templates.components.abonos-component.tipo-abono']}</th>
							<th class="hide-mobile">${i18n['cione-module.templates.components.abonos-component.estado-abono']}</th>
							<th class="hide-mobile"></th>
						</tr>
					</thead>
					<tbody id="abonos-table-data"></tbody>
				</table>	
				<div class="foot" id="footPen"></div>
			</div>	
		</section>
	</div>

	<div id="tab-historial" class="tab-content">
		<section class="cmp-abonoshistorial mobile-wrapper">
			<form id="formAbonosHistorico" name="formAbonosHistorico" method="post">
				<h2 class="title">${i18n['cione-module.templates.components.abonos-component.abonos-historial']}</h2></br>
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
				<div class="panel-filter">	
					<div class="filter order-1">
						<label>${i18n['cione-module.templates.components.abonos-component.search.num-abono']}</label> <input class="" id="" name="numAbono" type="text" value="">
					</div>
					<div class="filter fecha order-2">
						<label>${i18n['cione-module.templates.components.abonos-component.search.fecha-desde']}</label> <input class="inputfecha" id="fechaIniH" name="fechaIni" type="text">				
					</div>
					<div class="filter fecha order-2">
						<label>${i18n['cione-module.templates.components.abonos-component.search.fecha-hasta']}</label> <input class="inputfecha" id="fechaFinH" name="fechaFin" type="text" value="">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.abonos-component.search.estado-abono']}</label> <input class="" id="" name="idWeb"
								type="text" value="">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.abonos-component.search.id-web']}</label> <input class="" id="" name="idWeb"
								type="text" value="">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.abonos-component.search.ref-socio']}</label> <input class="" id="" name="refSocio"
								type="text" value="">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.abonos-component.search.ref-web']}</label> <input class="" id="" name="refWeb"
								type="text" value="">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.abonos-component.search.num-albaran-cargo']}</label> <input class="" id="" name="albaranCargo"
								type="text" value="">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.abonos-component.search.num-pedido-cargo']}</label> <input class="" id="" name="numPedido"
								type="text" value="">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.abonos-component.search.tipo-abono']}</label> <input class="" id="" name="tipoAbono"
								type="text" value="">
					</div>
					<div class="filter fecha order-3">
						<label>${i18n['cione-module.templates.components.abonos-component.search.description']}</label> <input class="" id="" name="descripcion"
								type="text" value="">
					</div>
					<div class="filter hide-mobile" style="display: none;">
						<label>${i18n['cione-module.templates.components.abonos-component.search.marca']}</label> <input class="" id="" name="marca" type="text"
								value="">
					</div>
					<div class="filter" style="display: none">
						<input class="" id="" name="historico" type="hidden" value="1">
					</div>
					<div class="filter" style="display: none">
						<input class="" id="" name="pagina" type="hidden" value="0">
					</div>
				</div>
				<div class="panelbuttons">
					<button id="abonoshistorico-component-search-btn"
						class="btn-blue icon-search" type="submit"
						onclick="searchAbonos(1); return false">${i18n['cione-module.global.btn-search']}</button>
				</div>
				</form>
				<div class="panel-table">
					<table id="tableHistorial" class="table abono-historico">
						<thead>
							<tr>
								<th>${i18n['cione-module.templates.components.abonos-component.num-abono']}</th>
								<th>${i18n['cione-module.templates.components.abonos-component.fecha-abono']}</th>
								<th>${i18n['cione-module.templates.components.abonos-component.tipo-abono']}</th>
								<th class="hide-mobile">${i18n['cione-module.templates.components.abonos-component.estado-abono']}</th>
								<th class="hide-mobile"></th>
							</tr>
						</thead>
						<tbody id="abonos-historico-table-data"></tbody>
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
	var pagePn = 0;
	var pageHs = 0;
	var nPages = 0;
	var nreg = 0;
	var filter = {};
	var abonosLoaded = {};

	function templateAbono(abono, tab, index) {
		var html = "";
		html += "<tr data-id='" + index + "'>";
		html += "<td>" + abono.numAbono + "</td>";
		html += "<td>" + abono.fechaView + "</td>";
		html += "<td>" + abono.tipo+ "</td>";		
		html += "<td class='hide-mobile'>" + abono.estado + "</td>";
		html += "<td class='masinfo hide-mobile'><a class='masinforesponsive' href='javascript:void(0)' onclick='getLineasAbono(\"" + abono.numAbono + "\",\"" + tab + "\",this)'>${i18n['cione-module.templates.components.abonos-component.more-info']}</a></td>";
		html += "</tr>";

		//subtable		 
	 	html += "<tr class='subtabla'>";
		html += "<td colspan='10'>";
		html += "<table class='table hide-mobile'>";
		html += "<thead>";
		html += "<tr class='encabezado'>";
		html += "<th>${i18n['cione-module.templates.components.abonos-component.num-pedido-cargo']}</th>";
		html += "<th>${i18n['cione-module.templates.components.abonos-component.descripcion']}</th>";
		html += "<th>${i18n['cione-module.templates.components.abonos-component.uni-pedidas']}</th>";
		html += "<th>${i18n['cione-module.templates.components.abonos-component.uni-entregadas']}</th>";
		[#if showPrices(ctx.getUser())]
		html += "<th>${i18n['cione-module.templates.components.abonos-component.precio-unitario']}</th>";
		html += "<th>${i18n['cione-module.templates.components.abonos-component.precio-total']}</th>";
		[/#if]
		html += "<th>${i18n['cione-module.templates.components.abonos-component.estado-producto']}</th>";
		html += "<th>${i18n['cione-module.templates.components.abonos-component.ref-socio']}</th>";
		html += "<th>${i18n['cione-module.templates.components.abonos-component.ref-web']}</th>";
		html += "<th>${i18n['cione-module.templates.components.abonos-component.num-albaran-cargo']}</th>";
		html += "</tr>";
		html += "</thead>";
		html += "<tbody id='" + abono.numAbono + tab + "'>";
		html += "</tbody>";
		html += "</table>";
		html += "</td>"; //td-colspan-9
		html += "</tr>"; 
		//subtable		
		return html;
	}

	function templateLineaAbono(lineaAbono) {
		var descripcion = lineaAbono.descripcionArticulo;
		var enlacePedido = CTX_PATH + "/private/pedidos-facturacion/pedidos/consulta-pedidos.html?numPedido=" + lineaAbono.numPedido;
		var enlaceAlbaran = CTX_PATH + "/private/pedidos-facturacion/pedidos/consulta-albaranes.html?numAlbaran=" + lineaAbono.numAlbaranCargo;

		var html = "";
		html += "<tr>";
		html += "<td title='${i18n['cione-module.templates.components.abonos-component.tooltip-ver-pedido']}'>";
		html += "<a href='" + enlacePedido + "'>" + lineaAbono.numPedido + "</a>";
		html += "</td>";
		if (descripcion.length > 50 && !$(".panel-table").hasClass("mobile")) {
			html += "<td title='" + descripcion + "'>" + descripcion.substring(0, 50) + "...</td>";
		} else {
			html += "<td>" + lineaAbono.descripcionArticulo + "</td>";
		}		
		html += "<td align='right'>" + lineaAbono.unidadesPedidas + "</td>";
		html += "<td align='right'>" + lineaAbono.unidadesAbonadas + "</td>";
		[#if showPrices(ctx.getUser())]
		html += "<td align='right'>" + lineaAbono.precioUnitario + "</td>";
		html += "<td align='right'>" + lineaAbono.precioTotal + "</td>";
		[/#if]
		html += "<td>" + lineaAbono.estadoEnvioProducto + "</td>";
		html += "<td>" + lineaAbono.refSocio + "</td>";
		html += "<td>" + lineaAbono.refWeb + "</td>";
		html += "<td><a href='" + enlaceAlbaran + "'>" + lineaAbono.numAlbaranCargo + "</a> </td>"; 
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

		clearErrorMessages();

		if (tab==0) {
			var oForm = document.forms["formAbonos"];
			var vForm = validateForm(oForm);
			if(vForm) {
				$("#abonos-table-data").html("");		
				$("#formAbonos input[name=pagina]").val(pagePn);
				filter = getFormData($("#formAbonos"));
				getAbonos();
			}

		} else {
			var oForm = document.forms["formAbonosHistorico"];
			var vForm = validateForm(oForm);
			if(vForm) {
				$("#abonos-historico-table-data").html("");
				$("#formAbonosHistorico input[name=pagina]").val(pageHs);
				filter = getFormData($("#formAbonosHistorico"));
				getAbonosHistorico();
			}
		}
	}
	
	function getAbonos() {

		$("#loading").show();
		$("#abonos-component-search-btn").attr("disabled", "disabled");
		$.ajax({
			url : PATH_API + "/private/abonos/v1/abonos",
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
		        	response.abonos.forEach(function(abono){
		        		count++;
		        		listResult.push(templateAbono(abono, 0, count));
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
		$("#abonoshistorico-component-search-btn").attr("disabled", "disabled");
		$.ajax({
			url : PATH_API + "/private/abonos/v1/abonos",
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
		        	response.abonos.forEach(function(abono){
		        		count++;
		        		listResult.push(templateAbono(abono, 1, count));
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

	function getLineasAbono(numAbono, tab,element) {
		if(abonosLoaded[numAbono]){
			toggleRow(element);
			if (!($(".panel-table").hasClass("mobile"))) {
				verifyTest(element);
				return;
			}
		}else{
			abonosLoaded[numAbono] = true;
		}		
		
		$("#loading").show();				
		$.ajax({
			url : PATH_API + "/private/abonos/v1/info-abono?numAbono=" + numAbono + "&historico=" + tab,
			type : "GET",					
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			success : function(response) {
				var listResult = [];	        	
	        	response.forEach(function(lineaAbono){
	        		listResult.push(templateLineaAbono(lineaAbono));
	        	})	
				$("#" + numAbono + tab).empty().append(listResult.join(" "));

				if ($(".panel-table").hasClass("mobile")) {
					loadsubtable(element); 
				}

	        	toggleRow(element);
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
