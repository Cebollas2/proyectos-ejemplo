[#include "../../includes/macros/cione-utils.ftl"]
[#assign fideliza=model.ocultarSecciones()]

<section class="cmp-facturasdocumentos">
    <form id="formOtrosDocumentos" name="formOtrosDocumentos" method="post">
    	<ul class="accordion-mobile">
            <li><a class="toggle" href="javascript:void(0);">
                    <div class="title">${i18n['cione-module.templates.components.facturas-otros-documentos-component.buscar-documentos']}<i class="fa fa-chevron-right"> </i></div>
                </a>
                <ul class="inner show" style="display: block;">
                    <li>
                     </li>
                </ul>
            </li>
        </ul>
        <div class="panel-filter">
            <div class="filter order-1">
                <label>${i18n['cione-module.templates.components.facturas-otros-documentos-component.tipo-documento']}</label>
                [#-- <select id="tipoDocumento" name="tipoDocumento" onchange="searchOtrosDocumentos(); return false"> --]
                <select id="tipoDocumento" name="tipoDocumento">
                    [#if !fideliza]<option value="consumos">${i18n['cione-module.templates.components.facturas-otros-documentos-component.consumos']}</option>[/#if]               
                    <option value="recibos">${i18n['cione-module.templates.components.facturas-otros-documentos-component.recibos']}</option>
                    <option value="inadaptaciones">${i18n['cione-module.templates.components.facturas-otros-documentos-component.abonos']}</option>
                    <option value="modelo347">${i18n['cione-module.templates.components.facturas-otros-documentos-component.mod347']}</option>
                </select>
            </div>
            
            <div class="filter fecha order-2">
				<label>${i18n['cione-module.templates.components.facturas-otros-documentos-component.fecha-desde']}</label> 
				<input id="fechaIniP" class="inputfecha" type="text" name="fechaDesde" autocomplete="off">
			</div>
			
			<div class="filter fecha order-3">
				<label>${i18n['cione-module.templates.components.facturas-otros-documentos-component.fecha-hasta']}</label> 
				<input id="fechaFinP" class="inputfecha" type="text" name="fechaHasta" autocomplete="off">
			</div>		
            
            
            <div class="filter" style="display: none">
                <input class="" id="pagina" name="pagina" type="hidden" value="0">
            </div>
            <div class="filter" style="display: none">
                <input class="" id="tiposDisponibles" name="tiposDisponibles" type="hidden" value="">
            </div>
        </div>
        <div class="panelbuttons">
            <button id="facturas-otros-documentos-component-search-btn"
                    class="btn-blue icon-search" type="btn"
                    onclick="searchOtrosDocumentos(); return false">${i18n['cione-module.templates.components.facturas-otros-documentos-component.search.btn-buscar']}
            </button>
        </div>
    </form>
    <div class="panel-table">
        <table class="table">
            <thead>
                <tr>
                    <th>${i18n['cione-module.templates.components.facturas-otros-documentos-component.nombre']}</th>
                    <th>${i18n['cione-module.templates.components.facturas-otros-documentos-component.fecha']}</th>
                    [#if showPrices(ctx.getUser())]
                    <th>${i18n['cione-module.templates.components.facturas-otros-documentos-component.documentos']}</th>
                    [/#if]
                </tr>
            </thead>
            <tbody id="otros-documentos-table-data"></tbody>
        </table>
        <div class="foot" id="footDo"></div>
    </div>
</section>

<div class="modal" id="tablaModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
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
    var otrosDocsLoaded = {};

    function templateOtrosDocumentos(documento,index) {

        //var enlace = PATH_API + "/private/facturas/v1/pdf/" + documento.nombreDoc;
        var urlFactura = encodeURIComponent(documento.enlaceDoc);

        var html = "";
        html += "<tr data-id='" + index + "'>";
        if(($('.accordion-mobile').css('display') == 'block') && (documento.nombreDoc.length > 20)) {
        	html += "<td>" + documento.nombreDoc.substring(0, 20) + "...</td>";
        } else {
        	html += "<td>" + documento.nombreDoc + "</td>";
        }
        html += "<td>" + documento.fechaDocView + "</td>";
        [#if showPrices(ctx.getUser())]
        //html += "<td class='icon'><a href='" + enlace + "'><div class='icon documento'></div></a></td>";
        html += '<td class="icon"><a href="#" onclick="obtenerPDF(\'' + urlFactura +'\')"><div class="icon documento"></div></a></td>';
        [/#if]
        html += "</tr>";

        return html;
    }

	function obtenerPDF(uri){
		$("#loading").show();
		var url = "${ctx.contextPath}/.rest/private/facturas/v1/checkPdfDocumentos?url="+uri;
		$.ajax({
			url : url,
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			success : function(response) {	
				window.open("${ctx.contextPath}/.rest/private/facturas/v1/pdfDocumentos?url=" + uri, '_blank');
			},
			error : function(response) {
				$("#modal-anuncio-error").html("<p>" + response.responseText + "</p>");
				$("#modalError").show();
				
			},
			complete : function(response) {
				$("#loading").hide();
			}
		});	
	    return false;
    }

    function searchOtrosDocumentos(){
        page = 1;
        nreg = 0;

        var options = $('#tipoDocumento option');

        var values = $.map(options ,function(option) {
        	if(option.value != ""){
        		return option.value;
        	}            
        });

        $("#otros-documentos-table-data").html("");
        $("#formOtrosDocumentos input[name=pagina]").val(page);
        $("#formOtrosDocumentos input[name=tiposDisponibles]").val(values.toString());
        filter = getFormData($("#formOtrosDocumentos"));
        otrosDocsLoaded = {};

        getOtrosDocumentos();        
    }

    function showMoreDocumentos(){
        page = page+1;    
        $("#formOtrosDocumentos input[name=pagina]").val(page);
        filter = getFormData($("#formOtrosDocumentos"));  
        getOtrosDocumentos();        
    }

    function templateShowMore(cPage,nPages,regTotales){

        var accReg = 0;

        if (cPage == nPages) {
            accReg = regTotales - (cPage - 1)*10;
        } else {
            accReg = 10;
        }

        nreg = nreg + accReg;

        var html="";
        html += "<span>${i18n['cione-module.templates.components.facturas-otros-documentos-component.search.showing-page']} " + nreg;
        html += " ${i18n['cione-module.templates.components.facturas-otros-documentos-component.search.showing-page-of']} ";
        html += "<span class='cantrow'>" + regTotales + "</span>";
        html += " ";
        html += "${i18n['cione-module.templates.components.facturas-otros-documentos-component.search.results']}";
        html += "</span>";
        html += " ";
        
        if (cPage == nPages){
            html += "<span>";
            html += " ";
        } else {
            html += "<span class='vermas' onclick='showMoreDocumentos()'>";
            html += "${i18n['cione-module.templates.components.facturas-otros-documentos-component.search.show-more']}";
        }

        html += "</span>";
        html += " ";
        html += "<span></span>";

        return html;
    }

    function getOtrosDocumentos() {    
        $("#loading").show();
        $("#facturas-otros-documentos-component-search-btn").attr("disabled", "disabled");
        $.ajax({
            url : PATH_API + "/private/facturas/v1/otros-documentos",
            type : "POST",
            data : JSON.stringify(filter),
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            success : function(response) {              
                var listResult = [];    
                var cPage = response.pagina;
                var regTotales = response.numRegistros;  
                nPages = response.ultimaPagina; 
                var count = 0;           
                response.documentos.forEach(function(documento){
                    count++;
                    listResult.push(templateOtrosDocumentos(documento,count));
                })              
                $("#otros-documentos-table-data").append(listResult.join(" ")); 
                $("#footDo").empty();
                if (regTotales > 0) {
                    $("#footDo").append(templateShowMore(cPage,nPages,regTotales));    
                }

                retailerZebra();              
            },
            error : function(response) {
                alert("error");
            },
            complete : function(response) {
                $("#loading").hide();
                $("#facturas-otros-documentos-component-search-btn").removeAttr("disabled");
            }
        });

        return false;
    }

    function initPage(){
    	//$("#fechaIniP").val("01-" + new Date().getMonth() + "-" + new Date().getFullYear());	
        //searchOtrosDocumentos();
    }

</script>