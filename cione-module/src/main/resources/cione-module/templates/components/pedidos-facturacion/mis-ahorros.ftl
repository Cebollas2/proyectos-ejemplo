[#include "../../includes/macros/cione-utils.ftl"]
<style>
   .ui-datepicker-calendar {
       display: none;
   }
</style>
<section class="cmp-mis-ahorros mobile-wrapper">

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
			${i18n['cione-module.templates.components.mis-ahorros.mis-ahorros']}
		</h2>

		<div class="panel-filter">
			<div class="filter fecha order-1">
				<label>${i18n['cione-module.templates.components.mis-ahorros.search.fecha-desde']}</label>
				<input class="inputfechaMY" id="fechaIni" name="fechaDesde" data-date-format="MM-YYYY"
					type="text" autocomplete="off">
			</div>
			<div class="filter fecha order-2">
				<label>${i18n['cione-module.templates.components.mis-ahorros.search.fecha-hasta']}</label>
				<input class="inputfechaMY" id="fechaFin" name="fechaHasta" data-date-format="MM-YYYY"
					type="text" value="" autocomplete="off">
			</div>
		</div>
		<div class="panelbuttons">
			<button class="btn-blue icon-search" type="submit"
				onclick="search(); return false">${i18n['cione-module.global.btn-search']}</button>
		</div>

		<h2 id="info-filter" class="title">
			${i18n['cione-module.templates.components.mis-ahorros.mis-ahorros']}
		</h2>

	</form>
	<div class="panel-table">
		<table id="tableExport" class="table">
			<thead>
				<tr>
					<th></th>					
					<th>${i18n['cione-module.templates.components.mis-ahorros.importe']}</th>					
				</tr>
			</thead>
			<tbody id="table-data">
			</tbody>
		</table>
	</div>

	<div class="note-table">
		*€Ciones
	</div>
	

	<div>		
		<a id="btnExport" class="btnExport" href="javascript:exportToExcel()">${i18n['cione-module.global.btn-download']}</a>
	</div>
 	<form id="form-export-data" method="post" action="">
 		<input id="export-data" name="export-data"  value="" type="hidden"> 	 	
 	</form>
</section>

<script>
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
			url : PATH_API + "/private/mis-ahorros/v1/ahorros",
			type : "POST",
			data : JSON.stringify(filter),
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			success : function(response) {
				//KK = response;
				results = [];
				if (response.ahorros.length == 0) {
					results.push(templateNoRecordsFound());
					$("#info-filter").html("");	
				} else {
					response.ahorros.forEach(function(ahorro) {
						results.push(template(ahorro));
					});
					results.push(templateTotal(response));					
				}
				var title = "${i18n['cione-module.templates.components.mis-ahorros.mis-ahorros']} [" + response.minDate + " - " + response.maxDate + "]";
					$("#info-filter").html(title);	
				$("#table-data").append(results.join(" "));				
			},
			error : function(response) {
				alert("error");
			},
			complete : function(response) {
				$("#loading").hide();
				retailerZebra();
			}
		});
		return false;
	}

	function template(ahorro) {
		var html = "";
		html += "<tr>";
		html += "<td>" + ahorro.concepto + "</td>";		
		html += "<td align='right'>" + ahorro.importeView + "</td>";		
		html += "</tr>";

		return html;
	}

	function templateTotal(misAhorros) {
		var html = "";
		[#if showPrices(ctx.getUser())]
		html += "<tr class='total'>";
		html += "<td>${i18n['cione-module.templates.components.mis-ahorros.total']}</td>";		
		html += "<td align='right'>" + misAhorros.importeTotalView + "</td>";		
		html += "</tr>";
		[/#if]
		return html;
	}

	function templateNoRecordsFound() {
		var html = "";
		html += "<tr id='trNoRecords'>";
		html += "<td class='text-center' colspan='2'>";
		html += "<span class='text-muted'>${i18n['cione-module.templates.components.mis-ahorros.search.no-records-found']}</span>";
		html += "</td>";
		html += "</tr>";
		return html;
	}

	
	function initPage() {
		$("#fechaIni").val("01-" + moment().year());
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