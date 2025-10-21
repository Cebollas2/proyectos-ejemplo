<#include "../../includes/macros/cione-utils.ftl">

<section>
<div class="cmp-tab">

	<ul class="tabs">
		<li id="tabPedidosPn" class="tab-link" data-tab="tab-pendiente">${i18n['cione-module.templates.components.pedidos-component.tab.pendientes']}</li>
		<li id="tabPedidosHs" class="tab-link" data-tab="tab-historial">${i18n['cione-module.templates.components.pedidos-component.tab.historial']}</li>
	</ul>

	<div id="tab-pendiente" class="tab-content">


		<section class="cmp-pedidospendientes mobile-wrapper">
			<form id="formPedidos" name="formPedidos" method="post">
				<h2 class="title">${i18n['cione-module.templates.components.pedidos-component.pedidos-pendientes']}</h2></br>
			    <ul class="accordion-mobile">
                    <li><a class="toggle" href="javascript:void(0);">
                            <div class="title">${i18n['cione-module.templates.components.pedidos-component.buscar-pedidos']}<i class="fa fa-chevron-right"> </i></div>
                        </a>
                        <ul class="inner show" style="display: block;">
                            <li>
                             </li>
                        </ul>
                    </li>
                </ul>
				<div class="panel-filter">
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.num-pedido']}</label> <input class="" id="" name="numPedido"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter fecha order-2">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.fecha-desde']}</label> <input id="fechaIniP" class="inputfecha" type="text" name="fechaIni" autocomplete="off">						
					</div>
					<div class="filter fecha order-2">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.fecha-hasta']}</label> <input id="fechaFinP" class="inputfecha" id="" name="fechaFin"
							type="text" value="" autocomplete="off">						
					</div>
					<div class="filter order-1">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.id-web']}</label> <input class="" id="" name="idWeb"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.ref-socio']}</label> <input class="" id="" name="refSocio"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.ref-web']}</label> <input class="" id="" name="refWeb"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter order-3">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.description']}</label> <input class="" id="" name="descripcion"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter hide-mobile" style="display: none;">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.marca']}</label> <input class="" id="" name="marca" type="text"
							value="" autocomplete="off">
					</div>
					<div class="filter hide-mobile" style="display: none">
						<input class="" id="pagina" name="pagina" type="hidden" value="0">
					</div>
					<div class="filter" style="display: none">
						<input class="" id="" name="historico" type="hidden" value="0">
					</div>
				</div>
				<div class="panelbuttons">
					<button id="pedidos-component-search-btn"
							class="btn-blue icon-search" type="submit"
							onclick="searchPedidos(0); return false">${i18n['cione-module.templates.components.pedidos-component.search.btn-buscar']}</button>
				</div>
			</form>
			<div id="panel-table" class="panel-table">
				<table id="tablePendientes" class="table">
					<thead>
						<tr>
							<th>${i18n['cione-module.templates.components.pedidos-component.num-pedido']}</th>
							<th>${i18n['cione-module.templates.components.pedidos-component.fecha-pedido']}</th>
							<th class="hide-mobile">${i18n['cione-module.templates.components.pedidos-component.tipo-pedido']}</th>
							<th class="hide-mobile">${i18n['cione-module.templates.components.pedidos-component.estado-pedido']}</th>
							<th>${i18n['cione-module.templates.components.pedidos-component.fecha-entrega-pedido']}</th>
							<th class="hide-mobile"></th>
						</tr>
					</thead>
					<tbody id="pedidos-table-data"></tbody>
				</table>
				<div class="foot" id="footPen"></div>
			</div>
		</section>
	</div>
	
	<div id="tab-historial" class="tab-content">
		<section class="cmp-pedidoshistorial mobile-wrapper">
			<form id="formPedidosHis" name="formPedidosHis" method="post">
				<h2 class="title">${i18n['cione-module.templates.components.pedidos-component.pedidos-historial']}</h2></br>
			    <ul class="accordion-mobile">
                    <li><a class="toggle" href="javascript:void(0);">
                            <div class="title">${i18n['cione-module.templates.components.pedidos-component.buscar-pedidos']}<i class="fa fa-chevron-right"> </i></div>
                        </a>
                        <ul class="inner show" style="display: block;">
                            <li>
                             </li>
                        </ul>
                    </li>
                </ul>
				<div class="panel-filter">
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.num-pedido']}</label> <input class="" id="" name="numPedido"
							type="text" value="${model.getNumPedido()!}" autocomplete="off">
					</div>
					<div class="filter fecha order-2">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.fecha-desde']}</label> <input id="fechaIniH" class="inputfecha" name="fechaIni" type="text" autocomplete="off">
					</div>
					<div class="filter fecha order-2">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.fecha-hasta']}</label> <input id="fechaFinH" class="inputfecha" id="" name="fechaFin"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.id-web']}</label> <input class="" id="" name="idWeb"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter order-1">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.ref-socio']}</label> <input class="" id="" name="refSocio"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter hide-mobile">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.ref-web']}</label> <input class="" id="" name="refWeb"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter order-3">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.description']}</label> <input class="" id="" name="descripcion"
							type="text" value="" autocomplete="off">
					</div>
					<div class="filter hide-mobile" style="display: none;">
						<label>${i18n['cione-module.templates.components.pedidos-component.search.marca']}</label> <input class="" id="" name="marca" type="text"
							value="" autocomplete="off">
					</div>
					<div class="filter " style="display: none">
						<input class="" id="pagina" name="pagina" type="hidden" value="0">
					</div>
					<div class="filter" style="display: none">
						<input class="" id="" name="historico" type="hidden" value="1">
					</div>
				</div>
				<div class="panelbuttons">
					<button id="pedidoshis-component-search-btn"
							class="btn-blue icon-search" type="submit"
							onclick="searchPedidos(1); return false">${i18n['cione-module.templates.components.pedidos-component.search.btn-buscar']}</button>
				</div>
			</form>
			<div class="panel-table">
				<table id="tableHistorial" class="table">
					<thead>
						<tr>
							<th>${i18n['cione-module.templates.components.pedidos-component.num-pedido']}</th>
							<th>${i18n['cione-module.templates.components.pedidos-component.fecha-pedido']}</th>
							<th class="hide-mobile">${i18n['cione-module.templates.components.pedidos-component.tipo-pedido']}</th>
							<th class="hide-mobile">${i18n['cione-module.templates.components.pedidos-component.estado-pedido']}</th>
							<th>${i18n['cione-module.templates.components.pedidos-component.fecha-entrega-pedido']}</th>
							<th class="hide-mobile"></th>
						</tr>
					</thead>
					<tbody id="pedidoshis-table-data"></tbody>
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
	var pedidosLoaded = {};

	function templatePedido(pedido,tab,index) {
		
		var html = "";
		html += "<tr data-id='" + index + "'>";
		html += "<td>" + pedido.numPedido + "</td>";
		html += "<td>" + pedido.fechaView + "</td>";
		html += "<td class='hide-mobile'>" + pedido.tipoPedido + "</td>";
		html += "<td class='hide-mobile'>" + pedido.estado + "</td>";		
		html += "<td>" + pedido.fechaEstimadaEntregaView + "</td>";		
		html += "<td class='masinfo hide-mobile'><a class='masinforesponsive' href='javascript:void(0)' onclick='getLineasPedido(\"" + pedido.numPedido + "\",\"" + pedido.historico + 
		"\",\"" + tab + "\",this)'>${i18n['cione-module.templates.components.pedidos-component.more-info']}</a></td>";
		html += "</tr>";

		//subtable		 
	 	html += "<tr class='subtabla'>";
		html += "<td colspan='9'>";
		html += "<table class='table hide-mobile'>";
		html += "<thead>";
		html += "<tr class='encabezado'>";
		html += "<th>${i18n['cione-module.templates.components.pedidos-component.search.description']}</th>";
		html += "<th>${i18n['cione-module.templates.components.pedidos-component.unidades-pedidas']}</th>";
		html += "<th>${i18n['cione-module.templates.components.pedidos-component.unidades-entregadas']}</th>";
		
		<#if showPrices(ctx.getUser())>
		 	html += "<th>${i18n['cione-module.templates.components.pedidos-component.precio-unitario']}</th>";
			html += "<th>${i18n['cione-module.templates.components.pedidos-component.precio-total']}</th>";
		</#if>
		
		html += "<th>${i18n['cione-module.templates.components.pedidos-component.estado-producto']}</th>";
		html += "<th>${i18n['cione-module.templates.components.pedidos-component.ref-web']}</th>";
		html += "<th>${i18n['cione-module.templates.components.pedidos-component.ref-socio']}</th>";
		html += "<th>${i18n['cione-module.templates.components.pedidos-component.id-web']}</th>";
		html += "<th>${i18n['cione-module.templates.components.pedidos-component.fecha-entrega-pedido']}</th>";
		html += "</tr>";
		html += "</thead>";
		html += "<tbody id='" + pedido.numPedido + tab + "'>";
		html += "</tbody>";
		html += "</table>";
		html += "</td>"; //td-colspan-9
		html += "</tr>"; 
		//subtable		
		return html;
	}

	function templateLineaPedido(lineaPedido) {
		var html = "";
		html += "<tr>";
		if (lineaPedido.descripcionArticulo.length > 50 && !$(".panel-table").hasClass("mobile")) {
			html += "<td title='" + lineaPedido.descripcionArticulo + "'>" + lineaPedido.descripcionArticulo.substring(0, 50) + "...</td>";
		} else {
			html += "<td>" + lineaPedido.descripcionArticulo + "</td>";
		}		
		//html += "<td>" + lineaPedido.descripcionArticulo + "</td>";
		html += "<td align='right'>" + lineaPedido.unidadesPedidas + "</td>";
		html += "<td align='right'>" + lineaPedido.unidadesEntregadas + "</td>";
		
		<#if showPrices(ctx.getUser())>
			html += "<td align='right'>" + lineaPedido.precioUnitario + "</td>";
			html += "<td align='right'>" + lineaPedido.precioTotal + "</td>";
		</#if>
		
		html += "<td align='right'>" + lineaPedido.estadoPedido + "</td>";
		html += "<td>" + lineaPedido.refWeb + "</td>";
		html += "<td>" + lineaPedido.refSocio + "</td>";
		html += "<td>" + lineaPedido.idWeb + "</td>";
		html += "<td>" + lineaPedido.fechaEntregaPrevistaView + "</td>";
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
			accReg = regTotales - (cPage - 1)*10;
		} else {
			accReg = 10;
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
			html += "<span class='vermas' onclick='showMorePedidos(\"" + tab + "\")'>";
			html += "${i18n['cione-module.templates.components.pedidos-component.search.show-more']}";
		}

		html += "</span>";
		html += " ";
		html += "<span></span>";

		return html;
	}

	function searchPedidos(tab){

		page = 1;
		nreg = 0;
		pedidosLoaded = {};

		clearErrorMessages();

		if (tab == 0) {
			var oForm = document.forms["formPedidos"];
			var vForm = validateForm(oForm);
			if(vForm){
				$("#pedidos-table-data").html("");		
				$("#formPedidos input[name=pagina]").val(page);
				filter = getFormData($("#formPedidos"));
				getPedidos(tab);
			}

		} else {
			var oForm = document.forms["formPedidosHis"];
			var vForm = validateForm(oForm);			
			if (vForm) {
				$("#pedidoshis-table-data").html("");
				$("#formPedidosHis input[name=pagina]").val(page);
				filter = getFormData($("#formPedidosHis"));
				getPedidos(tab);
			}
		}
		
	}

	function showMorePedidos(tab){
		page = page+1;

		if (tab == 0) {
			$("#formPedidos input[name=pagina]").val(page);
			filter = getFormData($("#formPedidos"));
		} else {
			$("#formPedidosHis input[name=pagina]").val(page);
			filter = getFormData($("#formPedidosHis"));
		}

		getPedidos(tab);		
	}

	function getPedidos(tab) {

		$("#loading").show();
		if (tab == 0) {
			$("#pedidos-component-search-btn").attr("disabled", "disabled");	
		} else {
			$("#pedidoshis-component-search-btn").attr("disabled", "disabled");
		}		
		$.ajax({
			url : PATH_API + "/private/pedidos/v1/pedidos",
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
		        	response.pedidos.forEach(function(pedido){
		        		count++;
		        		listResult.push(templatePedido(pedido,pedido.historico,count));
		        	});					
				} else {
					listResult.push(templateNoRecordsFound(tab));
				}

	        	if (tab == 0) {
	        		$("#pedidos-table-data").append(listResult.join(" "));
	        		$("#footPen").empty();
	        		if (regTotales > 0) {
	        			$("#footPen").append(templateShowMore(tab,cPage,nPages,regTotales));	
	        		}						    	
	        	} else{
	        		$("#pedidoshis-table-data").append(listResult.join(" "));
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

				//$("#formPedidos")[0].reset();
				if (tab == 0) {
					$("#tabPedidosPn").addClass("current");
					$("#tab-pendiente").addClass("current");
					$("#pedidos-component-search-btn").removeAttr("disabled");
				} else {
					$("#tabPedidosHs").addClass("current");
					$("#tab-historial").addClass("current");
					$("#pedidoshis-component-search-btn").removeAttr("disabled");
				}

				if (page >= nPages) {
					$(".vermas").prop("onclick", null).off("click");
				} else{
					$(".vermas").attr("onclick","showMorePedidos(\"" + tab + "\")");
				}

				retailerZebra();

			}

		});

		return false;
	}

	function togglePedido(element){  		
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

  	function getLineasPedido(numPedido, historico, tab, element) {
		if(pedidosLoaded[numPedido]){
			togglePedido(element);
			if (!($(".panel-table").hasClass("mobile"))) {
				verifyTest(element);
				return;
			}
		}else{
			pedidosLoaded[numPedido] = true;
		}		
		
		$("#loading").show();				
		$.ajax({
			url : PATH_API + "/private/pedidos/v1/info-pedido?numPedido=" + numPedido + "&historico=" + historico,
			type : "GET",					
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			success : function(response) {			
				var listResult = [];	        	
	        	response.forEach(function(lineaPedido){
	        		listResult.push(templateLineaPedido(lineaPedido));
	        	})	
				$("#" + numPedido + tab).empty().append(listResult.join(" "));

				if ($(".panel-table").hasClass("mobile")) {
					loadsubtable(element); 
				}

	        	togglePedido(element);
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
			$("#formPedidosHis input[name=numPedido]").val(numPedido);
			filter = getFormData($("#formPedidosHis"));
			searchPedidos(1);
		} else {
			searchPedidos(0);
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
		var temp = document.getElementById("pedidos-table-data").parentNode;
		var child = temp.children;
		if (child.length > 0){
			searchPedidos(0);
		}  	
  	} else {
  		var temp = document.getElementById("pedidoshis-table-data").parentNode;
  		var child = temp.children;
		if (child.length > 0){
			searchPedidos(1);
		}
  	}
  }

  bindAll();
}

var connectTabs = new Tabs();

</script>
