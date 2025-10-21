[#include "../../includes/macros/cione-utils.ftl"]

<style>
   .ui-datepicker-calendar {
       display: none;
   }
</style>


<section class="cmp-pagos-pendientes mobile-wrapper">

	<form id="form" name="form" method="post" class="formSearch">
		<ul class="accordion-mobile">
			<li><a class="toggle" href="javascript:void(0);">
					<div class="title">
						${i18n['cione-module.global.btn-search']}<i
							class="fa fa-chevron-right"> </i>
					</div>
			</a>
				<ul class="inner show" style="display: block;">
					<li></li>
				</ul></li>
		</ul>
		
		<h2 class="title">
			${i18n['cione-module.templates.components.pagos-pendientes.pagos-pendientes']}
		</h2>

		<div class="panel-filter">
			<div class="filter fecha order-1">
				<label>${i18n['cione-module.templates.components.pagos-pendientes.search.fecha-desde']}</label>
				<input class="inputfechaMY" id="fechaIni" name="fechaInicio" data-date-format="MM-YYYY"
					type="text" autocomplete="off">
				<!--<span id="search-envios-fecha-desde-error"> </span>-->
			</div>
			<div class="filter fecha order-2">
				<label>${i18n['cione-module.templates.components.pagos-pendientes.search.fecha-hasta']}</label>
				<input class="inputfechaMY" id="fechaFin" name="fechaFin" data-date-format="MM-YYYY"
					type="text" value="" autocomplete="off">
				<!--<span id="search-envios-fecha-hasta-error"> </span>-->
			</div>
		</div>
		<div class="panelbuttons">
			<button class="btn-blue icon-search" type="submit"
				onclick="search(); return false">${i18n['cione-module.global.btn-search']}</button>
		</div>

		<h2 id="info-filter" class="title">
			${i18n['cione-module.templates.components.pagos-pendientes.pagos-pendientes']}
		</h2>

	</form>
	<div class="panel-table">
		<table id="tableExport2" class="table">
			<thead>
				<tr>
					<th id="title-table" colspan="7" style="display:none"></th>
				</tr>
				<tr>
					<th>${i18n['cione-module.templates.components.pagos-pendientes.fecha']}</th>					
					<th>${i18n['cione-module.templates.components.pagos-pendientes.num-factura']}</th>
					<th class="hide-mobile">${i18n['cione-module.templates.components.pagos-pendientes.descripcion']}</th>
					<th class="hide-mobile">${i18n['cione-module.templates.components.pagos-pendientes.vencimiento']}</th>
					[#if showPrices(ctx.getUser())]
					<th class="hide-mobile">${i18n['cione-module.templates.components.pagos-pendientes.importe-inicial']}</th>
					<th class="hide-mobile">${i18n['cione-module.templates.components.pagos-pendientes.importe-pendiente']}</th>
					<th class="hide-mobile">${i18n['cione-module.templates.components.pagos-pendientes.saldo']}</th>
					[/#if]				
				</tr>
			</thead>
			<tbody id="table-data">
			</tbody>
		</table>
	</div>

	<div style="margin-top:15px">		
		<a id="btnExport" class="btnExport" href="javascript:exportToExcel()">${i18n['cione-module.global.btn-download']}</a>
	</div>
 	<form id="form-export-data" method="post" action="">
 		<input id="export-data" name="export-data"  value="" type="hidden"> 	 	
 	</form>

</section>

<script>
	numRequest = 0;
	maxNumRequest = 3;
	function search() {
		clearErrorMessages();
		var oForm = document.forms["form"];		
		var vForm = validateForm(oForm);
		if(!vForm){
			return;
		} 

		$("#table-data").html("");
		filter = getFormData($("#form"));
		$("#loading").show();
		$.ajax({
			url : PATH_API + "/private/pagos-pendientes/v1/pagos-pendientes",
			type : "POST",
			data : JSON.stringify(filter),
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			success : function(response) {
				KK = response;
				results = [];
				if (response.pagosFacturas.length == 0) {
					results.push(templateNoRecordsFoundForTable(7));
					$("#info-filter").html("");
					$("#title-table").html("");
				} else {
					response.pagosFacturas.forEach(function(pago) {
						results.push(template(pago));
					});										
				}
				var title = "${i18n['cione-module.templates.components.pagos-pendientes.pagos-pendientes']} [" + response.minDate + " - " + response.maxDate + "]";
				$("#info-filter").html(title);
				$("#title-table").html(title);
				$("#table-data").append(results.join(" "));
			},
			error : function(response) {
				//KK = response;
				if(response.responseText.indexOf("RESTEASY004655") != -1 && numRequest< maxNumRequest){
					console.log("error en el servidor");
					numRequest++;
					search();
				}else{
					alert("error");	
				}
			},
			complete : function(response) {
				$("#loading").hide();
				retailerZebra();
			}
		});
		return false;
	}

	function template(pago) {
		var html = "";
		html += "<tr>";
		html += "<td>" + pago.fechaView + "</td>";		
		html += "<td>" + pago.numFactura + "</td>";
		html += "<td class='hide-mobile'>" + pago.descripcion + "</td>";
		html += "<td class='hide-mobile'>" + pago.vencimiento + "</td>";
		[#if showPrices(ctx.getUser())]
		html += "<td class='hide-mobile' align='right'>" + pago.importeInicial + "</td>";
		html += "<td class='hide-mobile' align='right'>" + pago.importePendiente + "</td>";
		html += "<td class='hide-mobile' align='right'>" + pago.saldo + "</td>";
		[/#if]
		html += "</tr>";

		return html;
	}

	function initPage() {		
		search();	        
		$(".inputfechaMY").datepicker({ 
			dateFormat: 'mm-yy',
			changeMonth: true,
		    changeYear: true,
		    showButtonPanel: true,
		    onChangeMonthYear : function(year,month,inst){
		    	$(this).datepicker('setDate', new Date(inst.selectedYear, inst.selectedMonth, 1));
		    }
		});		
		
		exportToExcel= function(){
			var url = PATH_API + "/private/export/v1/xls";
			$("#form-export-data")[0].action=url;						
			$("#export-data").val(JSON.stringify(getTableToExport("tableExport2")));
			$("#form-export-data")[0].submit();		
		}
		
		$('.inputfechaMY').mask('00-0000');
	}
	
</script>
