[#include "../../includes/macros/cione-utils.ftl"]

<style>
   .ui-datepicker-calendar {
       display: none;
   }
</style>

<section class="cmp-deuda mobile-wrapper">

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
			${i18n['cione-module.templates.components.deuda.deuda']}
		</h2>

		<div class="panel-filter">
			<div class="filter fecha order-1">
				<label>${i18n['cione-module.templates.components.deuda.search.fecha-desde']}</label>
				<input class="inputfechaMY" id="fechaIni" name="fechaInicio" data-date-format="MM-YYYY"
					type="text" autocomplete="off">
				<!--<span id="search-envios-fecha-desde-error"> </span>-->
			</div>
			<div class="filter fecha order-2">
				<label>${i18n['cione-module.templates.components.deuda.search.fecha-hasta']}</label>
				<input class="inputfechaMY" id="fechaFin" name="fechaFin" data-date-format="MM-YYYY"
					type="text" value="" autocomplete="off">
				<!--<span id="search-envios-fecha-hasta-error"> </span>-->
			</div>
		</div>
		<div class="panelbuttons">
		<h2 id="info-filter" class="title">
			${i18n['cione-module.templates.components.deuda.deuda']} 
		</h2>
			<button class="btn-blue icon-search" type="submit"
				onclick="search(); return false">${i18n['cione-module.global.btn-search']}</button>
		</div>

		

	</form>
	<div id="download-table" class="panel-table">
		<table id="tableExport" class="table">
			<thead>
				<tr>
					<th>${i18n['cione-module.templates.components.deuda.fecha']}</th>
					[#if showPrices(ctx.getUser())]
					<th>${i18n['cione-module.templates.components.deuda.deuda-acumulada']}</th>
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
			url : PATH_API + "/private/deudas/v1/deudas",
			type : "POST",
			data : JSON.stringify(filter),
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			success : function(response) {
				//KK = response;
				results = [];
				if (response.deudas.length == 0) {
					results.push(templateNoRecordsFoundForTable(2));
				} else {
					response.deudas.forEach(function(deuda) {
						results.push(template(deuda));
					});					
				}
				results.push("<tr style='display:none'><td>${i18n['cione-module.templates.components.deuda.deuda-total']}</td><td>[#if showPrices(ctx.getUser())]" + response.totalView + "[/#if]</td></tr>")
				//var total = Math.round(response.total * 100) / 100;
				var title = "${i18n['cione-module.templates.components.deuda.deuda-total']} " [#if showPrices(ctx.getUser())] + response.totalView[/#if];
				$("#info-filter").html(title);	
				$("#table-data").append(results.join(" "));
			},
			error : function(response) {
				//KK = response;
				if(response.responseText.indexOf("RESTEASY004655") != -1 && numRequest<maxNumRequest){
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

	function template(deuda) {
		var html = "";
		html += "<tr>";
		html += "<td>" + deuda.fecha + "</td>";
		[#if showPrices(ctx.getUser())]
		html += "<td align='right'>" + deuda.deudaView + "</td>";
		[/#if]	
		html += "</tr>";

		return html;
	}

	
	function initPage() {		
		search();
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
		$('.inputfechaMY').mask('00-0000');
	}
	
</script>