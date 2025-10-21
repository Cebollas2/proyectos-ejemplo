[#include "../../includes/macros/cione-utils.ftl"]

<style>
   .ui-datepicker-calendar {
       display: none;
   }
</style>

<section class="cmp-InformeBlister mobile-wrapper">
    <form id="formInformeBlister" name="formInformeBlister" method="post" class="formSearch">
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
        	${i18n['cione-module.templates.components.informe-blister-component.informe-blister']}            
		</h2>
        <div class="panel-filter">     
        	 <div class="filter" style="display:none">
                <label>${i18n['cione-module.templates.components.informe-blister-component.tipo-documento']}</label>
                [#-- <select id="tipoDocumento" name="tipoDocumento" onchange="searchOtrosDocumentos(); return false"> --]
               <select id="tipoDocumento" name="tipoDocumento">
                    <option value="InformeBlister"></option>                                       
                </select>
            </div>
                   
            <div class="filter fecha order-2">
				<label>${i18n['cione-module.templates.components.informe-blister-component.fecha-desde']}</label> 
				<input id="fechaIni" class="inputfechaMY" data-date-format="MM-YYYY" type="text" name="fechaDesde" autocomplete="off">
			</div>
			
			<div class="filter fecha order-2">
				<label>${i18n['cione-module.templates.components.informe-blister-component.fecha-hasta']}</label> 
				<input id="fechaFin" class="inputfechaMY" data-date-format="MM-YYYY" type="text" name="fechaHasta" autocomplete="off">
			</div>		
            
            
            <div class="filter" style="display: none">
                <input class="" id="pagina" name="pagina" type="hidden" value="0">
            </div>
            <div class="filter" style="display: none">
                <input class="" id="tiposDisponibles" name="tiposDisponibles" type="hidden" value="">
            </div>
        </div>
        <div class="panelbuttons">
            <button id="informe-blister-component-search-btn"
                    class="btn-blue icon-search" type="btn"
                    onclick="search(); return false">${i18n['cione-module.templates.components.informe-blister-component.search.btn-buscar']}
            </button>
        </div>
    </form>
    <div class="panel-table">
        <table class="table">
            <thead>
                <tr>
                    <th>${i18n['cione-module.templates.components.informe-blister-component.nombre']}</th>
                    <th>${i18n['cione-module.templates.components.informe-blister-component.fecha']}</th>
                    [#if showPrices(ctx.getUser())]
                    <th>${i18n['cione-module.templates.components.informe-blister-component.documentos']}</th>
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
		//var enlace = PATH_API + "/private/facturas/v1/pdf?url=" + encodeURI(documento.enlaceDoc);
    	var urlFactura = encodeURIComponent(documento.enlaceDoc);
    	
        var html = "";
        html += "<tr data-id='" + index + "'>";
        html += "<td>" + documento.nombreDoc + "</td>";
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

    function search(){
        page = 1;
        nreg = 0;

		clearErrorMessages();

        var options = $('#tipoDocumento option');

        var values = $.map(options ,function(option) {
            return option.value;
        });
        
		if(!validateForm(document.forms["formInformeBlister"])){
			return;
		}	

        $("#otros-documentos-table-data").html("");
        $("#formInformeBlister input[name=pagina]").val(page);
        $("#formInformeBlister input[name=tiposDisponibles]").val(values.toString());
        filter = getFormData($("#formInformeBlister"));
        otrosDocsLoaded = {};

        getOtrosDocumentos();        
    }

    function showMoreDocumentos(){
        page = page+1;    
        $("#formInformeBlister input[name=pagina]").val(page);
        filter = getFormData($("#formInformeBlister"));  
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
        html += "<span>${i18n['cione-module.templates.components.informe-blister-component.search.showing-page']} " + nreg;
        html += " ${i18n['cione-module.templates.components.informe-blister-component.search.showing-page-of']} ";
        html += "<span class='cantrow'>" + regTotales + "</span>";
        html += " ";
        html += "${i18n['cione-module.templates.components.informe-blister-component.search.results']}";
        html += "</span>";
        html += " ";
        
        if (cPage == nPages){
            html += "<span>";
            html += " ";
        } else {
            html += "<span class='vermas' onclick='showMoreDocumentos()'>";
            html += "${i18n['cione-module.templates.components.informe-blister-component.search.show-more']}";
        }

        html += "</span>";
        html += " ";
        html += "<span></span>";

        return html;
    }

    function getOtrosDocumentos() {    
        $("#loading").show();
        $("#informe-blister-component-search-btn").attr("disabled", "disabled");
        $.ajax({
            url : PATH_API + "/private/facturas/v1/otros-documentos",
            type : "POST",
            data : JSON.stringify(filter),
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            success : function(response) { 
            	console.log(response);             
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
                }else {
                	$("#otros-documentos-table-data").append(templateNoRecordsFoundForTable(3));	
                }

                retailerZebra();              
            },
            error : function(response) {
                alert("error");
            },
            complete : function(response) {
                $("#loading").hide();
                $("#informe-blister-component-search-btn").removeAttr("disabled");
            }
        });

        return false;
    }

    function initPage(){
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