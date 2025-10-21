[#include "../../includes/macros/cione-utils.ftl"]

[#assign enlacePdf = ""]
[#if content.btnPdf?has_content]
	[#if content.btnPdf.field?has_content && content.btnPdf.field == "linkPdf"]
		[#if content.btnPdf.linkPdf?has_content]
			[#assign documentAsset = cmsfn.contentById(content.btnPdf.linkPdf, "dam")]
			[#assign link = cmsfn.link(documentAsset)!""]
			[#assign enlacePdf = link!]
		[/#if]
	[/#if]
	[#if content.btnPdf.field?has_content && content.btnPdf.field == "linkUrl"]
		[#assign enlacePdf = content.btnPdf.linkUrl!]
	[/#if]
[/#if]

<style>

</style>
[#if !cmsfn.editMode]

<section class="cmp-agrupacion">
	
    <div class="panel-table">
    
	    <div class="panelbuttons">
			<h2 id="info-filter" class="title">
			
				[#assign titledate = ""]
				
				[#if model.getDateinterval()?has_content && model.getDateinterval()??]
					[#assign titledate = " - " + model.getDateinterval()!]
				[/#if]
				
				${content.title!i18n['cione-module.templates.components.mis-consumos-cionelovers.title']}${titledate!""}
				
			</h2>
		</div>
		[#-- tabla oculta para exportar datos --]
        <table class="table" id="table-devolucion-gestion" style="display: none;">
            <thead>
                <tr>
                    <th>${i18n['cione-module.templates.components.mis-consumos-cionelovers.agr1']}</th>
                    <th>${i18n['cione-module.templates.components.mis-consumos-cionelovers.agr2']}</th>
                    <th>${i18n['cione-module.templates.components.mis-consumos-cionelovers.agr3']}</th>
                    <th class="hide-mobile">${i18n['cione-module.templates.components.mis-consumos-cionelovers.codcent']}</th>
                    <th class="hide-mobile">${i18n['cione-module.templates.components.mis-consumos-cionelovers.cant']}</th>
                    <th class="hide-mobile">${i18n['cione-module.templates.components.mis-consumos-cionelovers.imp']}</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
    
    <div class="panel-table">
    	<table class="table" id="table-cl">
	        <thead>
	            <tr>
	                <th rowspan="2" style="vertical-align: middle;">${i18n['cione-module.templates.components.mis-consumos-cionelovers.agr1']}</th>
	                <th rowspan="2" style="vertical-align: middle;">${i18n['cione-module.templates.components.mis-consumos-cionelovers.agr2']}</th>
	                
	                <th id="canttotal" rowspan="2" style="vertical-align: middle;">Cantidad: </th>
	                <th id="preciototal" class="hide-mobile" rowspan="2" style="vertical-align: middle;">Precio:</th>
	                [#-- <th colspan="2" rowspan="2" class="hide-mobile" style="vertical-align: middle; padding: 0;">
		                <table class="table" style="margin-bottom: 0;border: 0;">
					        <thead>
					            <tr>
					            	<th colspan="2" style="text-align: center;border-right: 0;border-left: 0;border-top: 0;">Total</th>
					            </tr>
					        	<tr>
					                <th id="canttotal" style="text-align: right;border-bottom: 0; width: 50%;">Cantidad: </th>
					                <th id="preciototal" style="text-align: right;border: 0; width: 50%;">Precio:</th>
					                
					            </tr>
					        </thead>
				        </table>
	                </th> --]
	                <th rowspan="2" class="hide-mobile" style="vertical-align: middle;"></th>
	            </tr>
	        </thead>
            <tbody></tbody>
        </table>
    </div>

	<div id="divbtnexport" style="margin-top:15px;display: none;">		
		<a id="btnExport" class="btnExport" href="javascript:exportToExcel()">${i18n['cione-module.global.btn-download']}</a>
		[#if enlacePdf?has_content]
			<a class="btnExport" target="_blank" href="${enlacePdf!}">${i18n['cione-module.global.btn-condiciones-viaje']}</a>
		[/#if]
	</div>
    
 	<form id="form-export-data" method="post" action="">
 		<input id="export-data" name="export-data"  value="" type="hidden"> 	 	
 	</form>

</section>
[#else]
	[@previsualizacion /]
[/#if]

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

[#-- SCRIPTS --]
<script>
	
	[#if !cmsfn.editMode]
		function initPage() {
			getData("${model.getIdsocio()!}");
			
			exportToExcel= function(){
				var url = PATH_API + "/private/export/v1/xls";
				$("#form-export-data")[0].action=url;						
				$("#export-data").val(JSON.stringify(getTableToExport("table-devolucion-gestion")));
				$("#form-export-data")[0].submit();		
			}
		}
	[/#if]
	
	function getData(idsocio){
		
		$("#loading").show();
		
		var url = PATH_API + "/private/mis-consumos/v1/consumoscl?idClient="+idsocio
		var urlgrupo = PATH_API + "/private/mis-consumos/v1/consumosclbygrouptwo?idClient="+idsocio+"&grouptwo="
		
		 $.ajax({
            url : url,
            type : "GET",
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            success : function(response) {
            
            	 var sum = 0.0;
            	 var sumcant = 0.0;
            	 
                 $.each(response.consumosCL, function( index, value ) {
                 	
                 	if (value.importe != null && value.cantidad != null){
                 		sum += value.importe;
                 		sumcant += value.cantidad;
                 	}
                 	
                 	var imp =  parseFloat(value.importe).toFixed(2).toString().replace(".", ",");
                 	
                 	var row = "<tr><td>" + value.level1_desc + "</td><td>" + value.level2_desc + "</td><td>" + value.level3_desc + "</td><td class='hide-mobile'>" + value.cod_central_desc + "</td><td>" + value.cantidad + "</td><td>" + imp + "</td></tr>";
					
					$('#table-devolucion-gestion > tbody:last-child').append(row);
				});
				
				var rowfoot = "<tr class='tfoot'><td class='td-foot'>&nbsp;</td><td class='td-foot'>&nbsp;</td><td class='td-foot hide-mobile'>&nbsp;</td><td class='td-foot'>" + "${i18n['cione-module.templates.components.mis-consumos-cionelovers.total']}" + "</td><td class='td-foot'>" + sumcant + "</td><td class='td-foot'>" + parseFloat(sum).toFixed(2).toString().replace(".", ",") + "</td></tr>";
				$('#table-devolucion-gestion > tbody:last-child').append(rowfoot);
				
				$("#canttotal").text("Cantidad Total: " + sumcant );
				$("#preciototal").text("Importe Total: " + parseFloat(sum).toFixed(2).toString().replace(".", ",") + " €" );
				$("#divbtnexport").css("display","block");
				
				
				[#-- Aqui empezamos la segunda tabla --]
				
				var rowcontrolbefore = '';
				var rowcontrolafter = '';
				var subtabla = false;
				var evenodd = 'even';
				
				var sumimpparcial = 0.0;
            	var sumcantparcial = 0.0;
				
				$.each(response.consumosCL, function( index, value ) {
					
					rowcontrolbefore = value.level2_desc;
					
					if (rowcontrolbefore !== rowcontrolafter){
						sumcantparcial = getCantParcial(response.consumosCL, rowcontrolbefore);
						sumimpparcial = getImpParcial(response.consumosCL, rowcontrolbefore);
						[#-- fila principal --]
						var row = "<tr class='" + evenodd + "'><td>" + value.level1_desc + "</td><td>" + value.level2_desc + "</td><td style='text-align: right;'>" + sumcantparcial+ "</td><td class='hide-mobile' style='text-align: right;'>" + sumimpparcial + "</td><td class='masinfo hide-mobile' style='text-align: right;'><a class='masinforesponsive' href='javascript:void(0)' onclick='toggleLovers(this)'>info</a></td></tr>";
						$('#table-cl > tbody:last-child').append(row);
						
						var subtable = getSubtable(response.consumosCL, rowcontrolbefore, index);
						$('#table-cl > tbody:last-child').append(subtable);
						
						subtabla = true;
						if (evenodd === 'odd'){evenodd = 'even';}else{evenodd = 'odd';}
					}else{
						[#-- subtabla --]
						
						if (subtabla){
							var subtable = getSubtable(response.consumosCL, rowcontrolbefore, index);
							$('#table-cl > tbody:last-child').append(subtable);
							subtabla = false;
						}
					}
					
					rowcontrolafter = value.level2_desc;
				});
				
				$("#loading").hide();
				
            },
            error : function(response) {
                console.log("Error al llamar al servicio cione lovers");
                $("#loading").hide();
            },
            complete : function(response) {
                $("#loading").hide();
            }
        });
        
        $("#loading").hide();
	}
	
	function getCantParcial(consumos, grupo) {
		var cant = 0;
		
		$.each(consumos, function( index, value ) {
			if (value.level2_desc == grupo){
				if (value.cantidad != null){
             		cant += value.cantidad;
             	}
			}
		});
		
		return cant;
	}
	
	function getImpParcial(consumos, grupo) {
		var cant = 0;
		var result = "0";
		
		$.each(consumos, function( index, value ) {
			if (value.level2_desc == grupo){
				if (value.importe != null){
             		cant += value.importe;
             	}
			}
		});
		
		result =  parseFloat(cant).toFixed(2).toString().replace(".", ",") + " €";
		
		return result;
	}
	
	function getSubtable(consumos, grupo, index){
		
		var table = "";
		
		var headtable = "<tr class='subtabla'>";
        headtable += "<td colspan='5'>";
        headtable += "<table class='table hide-mobile'>";
        headtable += "<thead>";
        headtable += "<tr class='encabezado'>";
        headtable += "<th>${i18n['cione-module.templates.components.mis-consumos-cionelovers.agr1']}</th>";
        headtable += "<th>${i18n['cione-module.templates.components.mis-consumos-cionelovers.agr2']}</th>";
        headtable += "<th>${i18n['cione-module.templates.components.mis-consumos-cionelovers.agr3']}</th>";
        headtable += "<th>${i18n['cione-module.templates.components.mis-consumos-cionelovers.codcent']}</th>";
        headtable += "<th>${i18n['cione-module.templates.components.mis-consumos-cionelovers.cant']}</th>";
        headtable += "<th>${i18n['cione-module.templates.components.mis-consumos-cionelovers.imp']}</th>";
        headtable += "</tr>";
        headtable += "</thead>";
		headtable += "<tbody id='" + index + "'>";
		
		//var headtable = "<tr class='subtabla'>";
		//headtable += "<td colspan='3'><table class='table'><thead><tr><th>${i18n['cione-module.templates.components.mis-consumos-cionelovers.agr1']}</th><th>${i18n['cione-module.templates.components.mis-consumos-cionelovers.agr2']}</th><th>${i18n['cione-module.templates.components.mis-consumos-cionelovers.agr3']}</th><th class='hide-mobile'>${i18n['cione-module.templates.components.mis-consumos-cionelovers.codcent']}</th><th class='hide-mobile'>${i18n['cione-module.templates.components.mis-consumos-cionelovers.cant']}</th><th class='hide-mobile'>${i18n['cione-module.templates.components.mis-consumos-cionelovers.imp']}</th></tr></thead><tbody>";
		table += headtable;
		
		var sum = 0.0;
        var sumcant = 0.0;
		
		$.each(consumos, function( index, value ) {
			
			if (value.level2_desc == grupo){
			
				if (value.importe != null && value.cantidad != null){
             		sum += value.importe;
             		sumcant += value.cantidad;
             	}
			
				var imp =  parseFloat(value.importe).toFixed(2).toString().replace(".", ",");
				var row = "<tr><td>" + value.level1_desc + "</td><td>" + value.level2_desc + "</td><td>" + value.level3_desc + "</td><td class='hide-mobile'>" + value.cod_central_desc + "</td><td style='text-align: right;'>" + value.cantidad + "</td><td style='text-align: right;'>" + imp + " €</td></tr>";
				table += row;
			}
					
		});
		
		[#-- sumatorio final en una fila con la cantidad y el precio total --]
		var rowfoot = "<tr class='tfoot'><td class='td-foot'>&nbsp;</td><td class='td-foot'>&nbsp;</td><td class='td-foot hide-mobile'>&nbsp;</td><td class='td-foot'>" + "${i18n['cione-module.templates.components.mis-consumos-cionelovers.total']}" + "</td><td class='td-foot' style='text-align: right;'>" + sumcant + "</td><td class='td-foot' style='text-align: right;'>" + parseFloat(sum).toFixed(2).toString().replace(".", ",") + " €</td></tr>";
		table += rowfoot;
		
		var closetable = "</tbody></table></td></tr>"
		table += closetable;
		return table;
	}
	
    function toggleLovers(element){         	
    	toggleRow(element);
        if ($(".panel-table").hasClass("mobile")) {
            loadsubtable(element); 
        }
    }
	
</script>



[#-- BEGIN: MACRO PREVISUALIZACION --]
[#macro previsualizacion]
<section class="cmp-agrupacion"> <div class="panel-table"> <div class="panelbuttons"><h2 id="info-filter" class="title">[#assign titledate=""][#if model.getDateinterval()?has_content && model.getDateinterval()??][#assign titledate=" - " + model.getDateinterval()!][/#if]${content.title!i18n['cione-module.templates.components.mis-consumos-cionelovers.title']}${titledate!""}</h2></div><table class="table"> <thead> <tr> <th>AGRUPACIÓN 1</th> <th>AGRUPACIÓN 2</th> <th>AGRUPACIÓN 3</th> <th class="hide-mobile">CÓDIGO CENTRAL</th> <th>CANTIDAD</th> <th>IMPORTE</th> </tr></thead> <tbody> <tr class="even"> <td>PRODUCTO PROPIO</td><td>MONTURAS Y GAFAS DE SOL</td><td>GRADUADO BASE</td><td class="hide-mobile">ESSENTIAL</td><td>175</td><td>300€</td></tr><tr class="odd"> <td>PRODUCTO PROPIO</td><td>MONTURAS Y GAFAS DE SOL</td><td>GRADUADO BASE</td><td class="hide-mobile">ESSENTIAL</td><td>175</td><td>300€</td></tr><tr class="even"> <td>PRODUCTO PROPIO</td><td>MONTURAS Y GAFAS DE SOL</td><td>GRADUADO BASE</td><td class="hide-mobile">ESSENTIAL</td><td>175</td><td>300€</td></tr><tr class="tfoot"> <td class="td-foot">&nbsp;</td><td class="td-foot">&nbsp;</td><td class="td-foot hide-mobile">&nbsp;</td><td class="td-foot">TOTAL</td><td class="td-foot">500</td><td class="td-foot">300</td></tr></tbody> </table> </div></section>
[/#macro]
[#-- END: MACRO PREVISUALIZACION --]
 