[#include "../../includes/macros/cione-utils.ftl"]
<section class="cmp-envios mobile-wrapper">
    <form id="formEnvios" name="formEnvios" method="post">
    	<ul class="accordion-mobile">
            <li><a class="toggle" href="javascript:void(0);">
                    <div class="title">${i18n['cione-module.templates.components.envios-component.buscar-envios']}<i class="fa fa-chevron-right"> </i></div>
                </a>
                <ul class="inner show" style="display: block;">
                    <li>
                     </li>
                </ul>
            </li>
        </ul>
        <div class="panel-filter">
            <div class="filter order-1">
                <label>${i18n['cione-module.templates.components.envios-component.search.num-envio']}</label>
                <input class="" id="" name="numEnvio" type="text" value="" autocomplete="off">
            </div>
            <div class="filter fecha order-2">
                <label>${i18n['cione-module.templates.components.envios-component.search.fecha-desde']}</label>
                <input class="inputfecha" id="fechaIni" name="fechaIni" type="text" autocomplete="off">
            </div>
            <div class="filter fecha order-2">
                <label>${i18n['cione-module.templates.components.envios-component.search.fecha-hasta']}</label>
                <input class="inputfecha" id="fechaFin" name="fechaFin" type="text" value="" autocomplete="off">
            </div>
            <div class="filter hide-mobile">
                <label>${i18n['cione-module.templates.components.envios-component.search.num-albaran']}</label>
                <input class="numAlbaran" id="numAlbaran" name="numAlbaran" type="text" value="" autocomplete="off">
            </div>
            <div class="filter order-3">
                <label>${i18n['cione-module.templates.components.envios-component.search.tracking-pedido']}</label>
                <input class="trackingPedido" id="trackingPedido" name="trackingPedido" type="text" value="" autocomplete="off">
            </div>
            <div class="filter" style="display: none">
                <input class="" id="pagina" name="pagina" type="hidden" value="0">
            </div>
        </div>
        <div class="panelbuttons">
            <button id="envios-component-search-btn"
                    class="btn-blue icon-search" type="submit"
                    onclick="searchEnvios(); return false">${i18n['cione-module.templates.components.envios-component.search.btn-buscar']}</button>
        </div>
    </form>
    <div class="panel-table">
        <table class="table">
            <thead>
                <tr>
                    <th>${i18n['cione-module.templates.components.envios-component.num-envio']}</th>
                    <th>${i18n['cione-module.templates.components.envios-component.num-fecha']}</th>
                    <th class="hide-mobile">${i18n['cione-module.templates.components.envios-component.tipo-pedido']}</th>
                    <th class="hide-mobile">${i18n['cione-module.templates.components.envios-component.num-bultos']}</th>
                    <th class="hide-mobile">${i18n['cione-module.templates.components.envios-component.agencia']}</th>
                    <th class="hide-mobile">${i18n['cione-module.templates.components.envios-component.tracking-pedido']}</th>
                    <th class="hide-mobile">${i18n['cione-module.templates.components.envios-component.num-tracking']}</th>
                    <th>${i18n['cione-module.templates.components.envios-component.estado']}</th>
                    <th class="hide-mobile"></th>
                </tr>
            </thead>
            <tbody id="envios-table-data"></tbody>
        </table>
        <div class="foot" id="foot"></div>
    </div>
    
    
 	<form id="form-export-data" method="post" action="">
 		<input id="export-data" name="export-data"  value="" type="hidden"> 	 	
 	</form>
 	
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
    var enviosLoaded = {};
    var tracking = "";

    function templateEnvio(envio,index) {
        var html = "";
        html += "<tr data-id='" + index + "'>";
        html += "<td>" + envio.numEnvio + "</td>";
        html += "<td>" + envio.fechaView + "</td>";
        html += "<td class='hide-mobile'>" + envio.tipoEnvio + "</td>";
        html += "<td class='hide-mobile' align='right'>" + envio.numBultos + "</td>";
        html += "<td class='hide-mobile'>" + envio.agencia + "</td>";
        if (envio.urlAgencia == '') {
        	html += "<td class='icon hide-mobile'><a href='javascript:void(0)' target='_blank'><div class='icon envio'></div></a></td>"; // TRAKING PEDIDO
        } else {
        	html += "<td class='icon hide-mobile'><a href='" + envio.urlAgencia + "' target='_blank'><div class='icon envio'></div></a></td>"; // TRAKING PEDIDO
        }
        html += "<td class='hide-mobile'>" + envio.trackingPedido + "</td>";  //Nº TRAKING
        
        
        html += "<td>" + envio.estado + "</td>";
        if ($(".panel-table").hasClass("mobile")) {
            var enlace = PATH_API + "/private/facturas/v1/pdfEnvios?trackingPedido=" + envio.trackingPedido + "&agencia=" + envio.agencia + "&tipoEnvio=" + envio.tipoEnvio;
            html += "<td class='masinfo hide-mobile'><a class='masinforesponsive' href='"+ enlace + "'>${i18n['cione-module.templates.components.envios-component.detalle-albaran']}</a></td>";
        } else {
        	html += "<td class='masinfo hide-mobile'><a class='masinforesponsive' href='javascript:void(0)' onclick='getLineasEnvio(\"" + envio.numEnvio + "\",\"" + envio.trackingPedido + 
			"\",this)'>${i18n['cione-module.templates.components.envios-component.more-info']}</a></td>";     
		}
        html += "</tr>";

        //subtable       
        html += "<tr class='subtabla'>";
        html += "<td colspan='9'>";
        html += "<table class='table hide-mobile'>";
        html += "<thead>";
        html += "<tr class='encabezado'>";
        
        html += "<th>${i18n['cione-module.templates.components.envios-component.articulo']}</th>";
        html += "<th>${i18n['cione-module.templates.components.envios-component.unidades']}</th>";
        [#if showPrices(ctx.getUser())]
        html += "<th>${i18n['cione-module.templates.components.envios-component.importeBruto']}</th>";
        html += "<th>${i18n['cione-module.templates.components.envios-component.descuento']}</th>";
        html += "<th>${i18n['cione-module.templates.components.envios-component.importeVenta']}</th>";
        [/#if]
        html += "<th>${i18n['cione-module.templates.components.envios-component.referenciaWeb']}</th>";
        html += "<th>${i18n['cione-module.templates.components.envios-component.referenciaTaller']}</th>";
        html += "<th>${i18n['cione-module.templates.components.envios-component.albaran']}</th>";
        html += "<th>${i18n['cione-module.templates.components.envios-component.fecha']}</th>";

        html += "</tr>";
        html += "</thead>";
        html += "<tbody id='" + envio.numEnvio + "'>";
        html += "</tbody>";
        html += "</table>";
        html += "</td>"; //td-colspan-9
        html += "</tr>"; 
 	
        //subtable      
        return html;
    }

    function templateLineaEnvio(lineaEnvio) {
        var html = "";
        var enlaceAlbaran = CTX_PATH + "/private/pedidos-facturacion/pedidos/consulta-albaranes.html?numAlbaran=" + lineaEnvio.albaran;
        html += "<tr>";
        
        html += "<td align='left'>" + lineaEnvio.articulo + "</td>";
        html += "<td align='right'>" + lineaEnvio.unidades + "</td>";
        [#if showPrices(ctx.getUser())]
        html += "<td align='right'>" + lineaEnvio.importeBruto + "</td>";
        html += "<td align='right'>" + lineaEnvio.descuento + "</td>";
        html += "<td align='right'>" + lineaEnvio.importeVenta + "</td>";
        [/#if]
        html += "<td align='right'>" + lineaEnvio.referenciaWeb + "</td>";
        html += "<td align='right'>" + lineaEnvio.referenciaTaller + "</td>";
        html += "<td><a href='" + enlaceAlbaran + "'>" + lineaEnvio.albaran + "</a></td>";
        html += "<td align='right'>" + lineaEnvio.fechaAlbaranView + "</td>";

        html += "</tr>";

        return html;
    }

    function templateLineaEnvioDownload(numEnvio) {
        var html = "";
        
        html += "<tr class='descargarMobile'>";
        html += "<td colspan='10' style='width: 100%; text-align: right;' ><i class='fa fa-download'></i><a href='javascript:exportToExcelShipping(" + numEnvio + ")'> ${i18n['cione-module.global.btn-download']}</a></td>";

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
        html += "<span>${i18n['cione-module.templates.components.envios-component.search.showing-page']} " + nreg;
        html += " ${i18n['cione-module.templates.components.envios-component.search.showing-page-of']} ";
        html += "<span class='cantrow'>" + regTotales + "</span>";
        html += " ";
        html += "${i18n['cione-module.templates.components.envios-component.search.results']}";
        html += "</span>";
        html += " ";
        
        if (cPage == nPages){
            html += "<span>";
            html += " ";
        } else {
            html += "<span class='vermas' onclick='showMoreEnvios()'>";
            html += "${i18n['cione-module.templates.components.envios-component.search.show-more']}";
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
        html += "<span class='text-muted'>${i18n['cione-module.templates.components.envios-component.search.no-records-found-extra']}</span>";
        html += "</td>";
        html += "</tr>";
        return html;
    }

    function searchEnvios(){

        page = 1;
        nreg = 0;
        enviosLoaded = {};

        clearErrorMessages();
   
        var oForm = document.forms["formEnvios"];
        var vForm = validateForm(oForm);
        if(vForm){
            $("#envios-table-data").html("");      
            $("#formEnvios input[name=pagina]").val(page);
            filter = getFormData($("#formEnvios"));
            getEnvios();
        }     
    }

    function showMoreEnvios(){
        page = page+1;

        $("#formEnvios input[name=pagina]").val(page);
        filter = getFormData($("#formEnvios"));

        getEnvios();        
    }

    function getEnvios() {

        $("#loading").show();
        $("#envios-component-search-btn").attr("disabled", "disabled");    
        
        $.ajax({
            url : PATH_API + "/private/envios/v1/envios",
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
                    response.envios.forEach(function(envio){
                        count++;
                        listResult.push(templateEnvio(envio,count));
                    });                        
                } else {
                    listResult.push(templateNoRecordsFound());
                }
                    
                $("#envios-table-data").append(listResult.join(" "));
                $("#foot").empty();
                if (regTotales > 0) {
                    $("#foot").append(templateShowMore(cPage,nPages,regTotales));    
                }
                
                var indice =0;
                response.envios.forEach(function(envio){
                    indice++;
                    if ($(".panel-table").hasClass("mobile") && (tracking == envio.trackingPedido)) {
	                    var element = $("tr[data-id='" + indice + "']");     
	                	element.trigger("click");
	                }
                });
                                        
 
            },
            error : function(response) {
                alert("error");             
                //$("#change-pwd-result").html("<p>" +  response.responseJSON.error.message + "</p>");
            },
            complete : function(response) {
                $("#loading").hide();
                $("#envios-component-search-btn").removeAttr("disabled");
                if (page >= nPages) {
                    $(".vermas").prop("onclick", null).off("click");
                } else{
                    $(".vermas").attr("onclick","showMoreEnvios()");
                }

                retailerZebra();
            }
        });

        return false;
    }

    function getLineasEnvio(numEnvio, trackingPedido, element) {
        if(enviosLoaded[numEnvio]){
            toggleRow(element);
            if (!($(".panel-table").hasClass("mobile"))) {
                verifyTest(element);
                return;
            }
        }else{
            enviosLoaded[numEnvio] = true;
        }   
        
        var numAlbaran = $('#numAlbaran').val();
        
        $("#loading").show();               
        $.ajax({
            url : PATH_API + "/private/envios/v1/info-envio?trackingPedido=" + trackingPedido + "&numAlbaran=" +numAlbaran,
            type : "GET",                   
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            success : function(response) {            
                var listResult = [];                
                response.forEach(function(lineaEnvio){
                    listResult.push(templateLineaEnvio(lineaEnvio));
                }) 
                listResult.push(templateLineaEnvioDownload(numEnvio)); 
                $("#" + numEnvio).empty().append(listResult.join(" "));

                
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
    	var param = "numAlbaran";
		var numAlbaran = getParameterByName(param, window.location.href);
		if( numAlbaran ) {
			$("#formEnvios input[name=numAlbaran]").val(numAlbaran);
			filter = getFormData($("#formEnvios"));
		}
    	var paramTracking = "trackingPedido";
		var trackingPedido = getParameterByName(paramTracking, window.location.href);
		if( trackingPedido ) {
			$("#formEnvios input[name=trackingPedido]").val(trackingPedido);
			filter = getFormData($("#formEnvios"));
			tracking = trackingPedido;
		}
        searchEnvios();
    }
    
	function openrow (element) {

		if ($(element).next().hasClass("tr-extend")) {
			$(element).next().remove();
			$(element).removeClass("open");
		} else {
			$(element).addClass("open");
			var contenido_tr = [];
			var contenido_titulo = [];
			wrapper_responsive = "";
			var td = "";

			// contar numero de columnas visibles
			var columns_visible = $(element).closest("tbody")
					.siblings("thead").find("th").not(
							".hide-mobile").length;

			// titulo de columnas ocultas
			$(element)
					.closest("tbody")
					.siblings("thead")
					.find("th.hide-mobile")
					.each(
							function() {
								contenido_titulo
										.push("<div class='titulo-extend'>"
												+ $(element).html()
												+ "</div>");
							});

			// contenido de columnas oculta
			$(element).find("td.hide-mobile").each(
					function() {
						contenido_tr.push("<div class=''>"
								+ $(element).html() + "</div>");
					});

			$.each(contenido_tr, function(i) {
				wrapper_responsive += "<div >"
						+ contenido_titulo[i] + "  "
						+ contenido_tr[i] + "</div>";

			});
			// imprime si existe columnas ocultas
			if ((0 < $(element).closest("tbody").siblings("thead")
					.find(".hide-mobile").length)
					&& ($(element).closest("tr").hasClass("tr-extend")) == false) {
				$(
						"<tr data-id='"
								+ $(element).data("id")
								+ "' class='tr-extend'><td class='td-extend' colspan="
								+ columns_visible + ">"
								+ wrapper_responsive + "</td></tr>")
						.insertAfter($(element));

			}
		}
	}

</script>