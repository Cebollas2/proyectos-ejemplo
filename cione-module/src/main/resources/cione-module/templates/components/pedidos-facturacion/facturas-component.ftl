[#include "../../includes/macros/cione-utils.ftl"]

<section class="cmp-facturas mobile-wrapper">
    <form id="formFacturas" name="formFacturas" method="post">
    	<ul class="accordion-mobile">
            <li><a class="toggle" href="javascript:void(0);">
                    <div class="title">${i18n['cione-module.templates.components.facturas-component.buscar-facturas']}<i class="fa fa-chevron-right"> </i></div>
                </a>
                <ul class="inner show" style="display: block;">
                    <li>
                     </li>
                </ul>
            </li>
        </ul>
        <div class="panel-filter">
            <div class="filter order-1">
                <label>${i18n['cione-module.templates.components.facturas-component.numfactura']}</label>
                <input class="" id="" name="numFactura" type="text" value="" autocomplete="off">
            </div>
            <div class="filter fecha order-2">
                <label>${i18n['cione-module.templates.components.facturas-component.fecha-desde']}</label>
                <input class="inputfecha" id="fechaIni" name="fechaIni" type="text" autocomplete="off">
            </div>
            <div class="filter fecha order-2">
                <label>${i18n['cione-module.templates.components.facturas-component.fecha-hasta']}</label>
                <input class="inputfecha" id="fechaFin" name="fechaFin" type="text" value="" autocomplete="off">
            </div>
            <div class="filter hide-mobile">
                <label>${i18n['cione-module.templates.components.facturas-component.numalbaran']}</label>
                <input class="" id="" name="numAlbaran" type="text" value="" autocomplete="off">
            </div>
            <div class="filter order-3">
                <label>${i18n['cione-module.templates.components.facturas-component.tipofactura']}</label>
                <input class="" id="" name="tipo" type="text" value="" autocomplete="off">
            </div>
            <div class="filter hide-mobile">
                <label>${i18n['cione-module.templates.components.facturas-component.notaentrega']}</label>
                <input class="" id="" name="notaEntrega" type="text" value="" autocomplete="off">
            </div>
            <div class="filter" style="display: none">
                <input class="" id="pagina" name="pagina" type="hidden" value="0">
            </div>
        </div>
        <div class="panelbuttons">
            <button id="facturas-component-search-btn"
                    class="btn-blue icon-search" type="submit"
                    onclick="searchFacturas(); return false">${i18n['cione-module.templates.components.facturas-component.btn-buscar']}</button>
        </div>
        
        <div id="modalError" class="modal modal-anuncio" tabindex="-1" role="dialog">
	    	<div class="modal-dialog" role="document">
	      		<div class="modal-content">
	        		<div class="modal-body">
	        			<div id="modal-anuncio">
	        				<div id="modal-anuncio-error" style="padding-top: 25px;padding-left: 40px;font-size: large;">dljajjdaj</div>
        					<div class="panelbutton" style="padding-top: 50px;">
        						<a href="javascript:closeModalError()" class="closemodal">${i18n['cione-module.templates.components.menu-home-component.close']}</a>
        					</div>
            			</div>
	        		</div>
	      		</div>
	    	</div>
		</div>
		
    </form>
    <div class="panel-table">
        <table class="table">
            <thead>
                <tr>
                    <th>${i18n['cione-module.templates.components.facturas-component.num-factura']}</th>
                    <th>${i18n['cione-module.templates.components.facturas-component.tipo']}</th>
                    <th class="hide-mobile">${i18n['cione-module.templates.components.facturas-component.fecha']}</th>
                    
                    [#if showPrices(ctx.getUser())]
                    <th class="hide-mobile">${i18n['cione-module.templates.components.facturas-component.importe-neto']}</th>
                    <th>${i18n['cione-module.templates.components.facturas-component.importe']}</th>
                    <th class="hide-mobile">${i18n['cione-module.templates.components.facturas-component.documento']}</th>
                    [/#if]
                    
                    <th class="hide-mobile"></th>
                </tr>
            </thead>
            <tbody id="facturas-table-data"></tbody>
        </table>
        <div class="foot" id="foot"></div>
    </div>
</section>

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
    var facturasLoaded = {};

	//function cabecera
    function templateFactura(factura,index) {
    	var enlace = PATH_API + "/private/facturas/v1/pdf?url=" + encodeURI(factura.url);
    	var urlFactura = encodeURIComponent(factura.url);
        var html = "";
        html += "<tr data-id='" + index + "'>";
        html += "<td>" + factura.numFactura + "</td>";
        html += "<td>" + factura.tipo + "</td>";
        html += "<td class='hide-mobile'>" + factura.fecha + "</td>";
        
        [#if showPrices(ctx.getUser())]
        html += "<td class='hide-mobile' align='right'>" + factura.importeNeto + "</td>";
        html += "<td class='' align='right'>" + factura.importeTotal + "</td>";
        //html += "<td class='hide-mobile'><a href='" + enlace + "'><div class='icon documento'></div></a></td>";
        html += '<td class="hide-mobile"><a href="#" onclick="obtenerPDF(\'' + urlFactura +'\')"><div class="icon documento"></div></a></td>';
        [/#if]
        
        html += "<td class='masinfo hide-mobile'><a class='masinforesponsive' href='javascript:void(0)' onclick='getLineasFactura(\"" + factura.numFactura + "\",this)'>${i18n['cione-module.templates.components.facturas-component.more-info']}</a></td>";
        html += "</tr>";

        //subtable       
        html += "<tr class='subtabla'>";
        html += "<td colspan='9'>";
        html += "<table class='table hide-mobile'>";
        html += "<thead>";
        html += "<tr class='encabezado'>";
        html += "<th>${i18n['cione-module.templates.components.facturas-component.num-albaran']}</th>";
        html += "<th>${i18n['cione-module.templates.components.facturas-component.nota-entrega']}</th>";
        html += "<th>${i18n['cione-module.templates.components.facturas-component.descripcion2']}</th>";
		[#if showPrices(ctx.getUser())]
        html += "<th>${i18n['cione-module.templates.components.facturas-component.imp-bruto']}</th>";
        html += "<th>${i18n['cione-module.templates.components.facturas-component.tipo-impositivo']}</th>";
        html += "<th>${i18n['cione-module.templates.components.facturas-component.imp-dto']}</th>";
        html += "<th>${i18n['cione-module.templates.components.facturas-component.imp-neto']}</th>";
        [/#if]
        html += "<th>${i18n['cione-module.templates.components.facturas-component.unidades']}</th>";
        html += "</tr>";
        html += "</thead>";
        html += "<tbody id='" + factura.numFactura + "'>";
        html += "</tbody>";
        html += "</table>";
        html += "</td>"; //td-colspan-9
        html += "</tr>"; 
        //subtable      
        return html;
    }

    function templateLineaFactura(lineaFactura, numFactura) {
        var html = "";
        var enlaceAlbaran = CTX_PATH + "/private/pedidos-facturacion/pedidos/consulta-albaranes.html?numAlbaran=" + lineaFactura.numAlbaran;
        if (numFactura.indexOf("D") == 0) { 
        	//es directa
        	enlace = "javascript:void(0)";
        }
        html += "<tr>";
        html += "<td><a href='" + enlaceAlbaran + "'>" + lineaFactura.numAlbaran + "</a></td>";
        html += "<td>" + lineaFactura.notaEntrega + "</td>";
        html += "<td>" + lineaFactura.descripcion + "</td>";
        [#if showPrices(ctx.getUser())]
        html += "<td align='right'>" + lineaFactura.importeBruto + "</td>";
        html += "<td align='right'>" + lineaFactura.tipoImpositivo + "</td>";
        html += "<td align='right'>" + lineaFactura.importeDescuento + "</td>";
        html += "<td align='right'>" + lineaFactura.importeNeto + "</td>";
        [/#if]
        html += "<td align='right'>" + lineaFactura.unidades + "</td>";
        html += "</tr>";
        html += "</tr>";

        return html;
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
        html += "<span>${i18n['cione-module.templates.components.facturas-component.search.showing-page']} " + nreg;
        html += " ${i18n['cione-module.templates.components.facturas-component.search.showing-page-of']} ";
        html += "<span class='cantrow'>" + regTotales + "</span>";
        html += " ";
        html += "${i18n['cione-module.templates.components.facturas-component.search.results']}";
        html += "</span>";
        html += " ";
        
        if (cPage == nPages){
            html += "<span>";
            html += " ";
        } else {
            html += "<span class='vermas' onclick='showMoreFacturas()'>";
            html += "${i18n['cione-module.templates.components.facturas-component.search.show-more']}";
        }

        html += "</span>";
        html += " ";
        html += "<span></span>";

        return html;
    }

    function templateNoRecordsFound(){
        
        var columns = $("table").find('tr')[0].cells.length;

        var html="";
        html += "<tr id='trNoRecords'>";
        html += "<td class='text-center' colspan='" + columns + "'>";
        html += "<span class='text-muted'>${i18n['cione-module.templates.components.facturas-component.search.no-records-found']}</span>";
        html += "</td>";
        html += "</tr>";
        return html;
    }

    function searchFacturas(){

        page = 1;
        nreg = 0;
        facturasLoaded = {};

        clearErrorMessages();
   
        var oForm = document.forms["formFacturas"];
        var vForm = validateForm(oForm);

        if(vForm) {
            $("#facturas-table-data").html("");      
            $("#formFacturas input[name=pagina]").val(page);
            filter = getFormData($("#formFacturas"));
            getFacturas();
        }     
    }

    function showMoreFacturas(){
        page = page+1;

        $("#formFacturas input[name=pagina]").val(page);
        filter = getFormData($("#formFacturas"));

        getFacturas();        
    }

    function getFacturas() {

        $("#loading").show();
        $("#facturas-component-search-btn").attr("disabled", "disabled");    
        
        $.ajax({
            url : PATH_API + "/private/facturas/v1/facturas",
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
                    response.facturas.forEach(function(factura){
                        count++;
                        listResult.push(templateFactura(factura,count));
                    });                        
                } else {
                    listResult.push(templateNoRecordsFound());
                }
                    
                $("#facturas-table-data").append(listResult.join(" "));
                $("#foot").empty();
                if (regTotales > 0) {
                    $("#foot").append(templateShowMore(cPage,nPages,regTotales));    
                }                               
 
                 
            },
            error : function(response) {
                alert("error");             
                //$("#change-pwd-result").html("<p>" +  response.responseJSON.error.message + "</p>");
            },
            complete : function(response) {
                $("#loading").hide();
                $("#facturas-component-search-btn").removeAttr("disabled");
                if (page >= nPages) {
                    $(".vermas").prop("onclick", null).off("click");
                } else{
                    $(".vermas").attr("onclick","showMoreFacturas()");
                }

                retailerZebra();
            }
        });

        return false;
    }

    function toggleFactura(element){         
    	toggleRow(element);
    }

    function getLineasFactura(numFactura, element) {
        if(facturasLoaded[numFactura]){
            toggleFactura(element);
            if (!($(".panel-table").hasClass("mobile"))) {
                verifyTest(element);
                return;
            }
        }else{
            facturasLoaded[numFactura] = true;
        }       
        
        $("#loading").show();               
        $.ajax({
            url : PATH_API + "/private/facturas/v1/info-factura?numFactura=" + numFactura,
            type : "POST",                   
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            success : function(response) {            
                var listResult = [];                
                response.forEach(function(lineaFactura){
                    listResult.push(templateLineaFactura(lineaFactura, numFactura));
                })  
                $("#" + numFactura).empty().append(listResult.join(" "));

                if ($(".panel-table").hasClass("mobile")) {
                    loadsubtable(element); 
                }
                
                toggleFactura(element);
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
        searchFacturas();
    }
    
    function obtenerPDF(uri){
		$("#loading").show();
		var url = "${ctx.contextPath}/.rest/private/facturas/v1/checkPdf?url="+uri;
		$.ajax({
			url : url,
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			success : function(response) {	
				window.open("${ctx.contextPath}/.rest/private/facturas/v1/pdf?url=" + uri, '_blank');
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
    
    function closeModalError() {
    	$("#modalError").hide();
    }

</script>