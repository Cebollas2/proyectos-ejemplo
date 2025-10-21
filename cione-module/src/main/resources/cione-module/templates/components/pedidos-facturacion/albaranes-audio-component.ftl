[#include "../../includes/macros/cione-utils.ftl"]

[#assign audiosubfamilias = model.audiosubfamilias!]
[#assign audioproveedores = model.audioproveedores!]
[#assign listipoalbaran = model.listipoalbaran!]
[#assign listipoventa = model.listipoventa!]

<section class="cmp-albaranes mobile-wrapper">
    <form id="formAlbaranes" name="formAlbaranes" method="post">
    	<ul class="accordion-mobile">
            <li><a class="toggle" href="javascript:void(0);">
                    <div class="title">${i18n['cione-module.templates.components.albaranes-component.buscar-albaranes']}<i class="fa fa-chevron-right"> </i></div>
                </a>
                <ul class="inner show" style="display: block;">
                    <li>
                     </li>
                </ul>
            </li>
        </ul>
        <div class="panel-filter">
            <div class="filter hide-mobile">
                <label>${i18n['cione-module.templates.components.albaranes-component.numpedido']}</label>
                <input class="" id="" name="numPedido" type="text" value="" autocomplete="off">
            </div>
            <div class="filter fecha order-2">
                <label>${i18n['cione-module.templates.components.albaranes-component.fecha-desde']}</label>
                <input class="inputfecha" id="fechaIni" name="fechaIni" type="text" autocomplete="off">                
            </div>
            <div class="filter fecha order-2">
                <label>${i18n['cione-module.templates.components.albaranes-component.fecha-hasta']}</label>
                <input class="inputfecha" id="fechaFin" name="fechaFin" type="text" value="" autocomplete="off">                
            </div>
            <div class="filter order-1">
                <label>${i18n['cione-module.templates.components.albaranes-component.numalbarancione']}</label>
                <input class="" id="" name="numAlbaran" type="text" value="" autocomplete="off">
            </div>
            <div class="filter hide-mobile">
                <label>${i18n['cione-module.templates.components.albaranes-component.idweb']}</label>
                <input class="" id="" name="idWeb" type="text" value="" autocomplete="off">
            </div>
            <div class="filter order-3">
                <label>${i18n['cione-module.templates.components.albaranes-component.descripcion']}</label>
                <input class="" id="" name="descripcion" type="text" value="" autocomplete="off">
            </div>
            <div class="filter hide-mobile">
                <label>${i18n['cione-module.templates.components.albaranes-component.tipoalbaran']}</label>
                <select name="tipoAlbaran" id="tipoAlbaran" class="select-tamanio">
                	<option value=""></option>
					[#list listipoalbaran.tiposAlbaran as tipoAlbaran]
						<option value="${tipoAlbaran.clave!""}">${tipoAlbaran.etiqueta!""}</option>
					[/#list]
                </select>
                
            </div>
            <div class="filter hide-mobile">
                <label>${i18n['cione-module.templates.components.albaranes-component.tipoVenta']}</label>
                <select name="tipoVenta" id="tipoVenta" class="select-tamanio">
                	<option value=""></option>
					[#list listipoventa as tipoVenta]
						<option value="${tipoVenta!""}">${tipoVenta!""}</option>
					[/#list]
                </select>
            </div>
            <div class="filter hide-mobile">
                <label>${i18n['cione-module.templates.components.albaranes-component.albProveedor']}</label>
                <input class="" id="" name="albProveedor" type="text" value="" autocomplete="off">
            </div>
            <div class="filter hide-mobile">
                <label>${i18n['cione-module.templates.components.albaranes-component.refweb']}</label>
                <input class="" id="" name="refWeb" type="text" value="" autocomplete="off">
            </div>
            <div class="filter hide-mobile">
                <label>${i18n['cione-module.templates.components.albaranes-component.refsocio']}</label>
                <input class="" id="" name="refSocio" type="text" value="" autocomplete="off">
            </div>
            <div class="filter hide-mobile">
                <label>${i18n['cione-module.templates.components.albaranes-component.tipoProducto']}</label>
                <select name="subFamilia" id="subFamilia" class="select-tamanio">
                	<option value=""></option>
					[#list audiosubfamilias.subfamilias as subFamilia]
						<option value="${subFamilia.clave!""}">${subFamilia.etiqueta!""}</option>
					[/#list]
                </select>
            </div>
            <div class="filter hide-mobile">
                <label>${i18n['cione-module.templates.components.albaranes-component.proveedor']}</label>
                <select name="proveedor" id="proveedor" class="select-tamanio">
                	<option value=""></option>
					[#list audioproveedores.proveedores as proveedor]
						<option value="${proveedor.clave!""}">${proveedor.etiqueta!""}</option>
					[/#list]
                </select>
                
            </div>
            <div class="filter hide-mobile">
                <label>${i18n['cione-module.templates.components.albaranes-component.tracking']}</label>
                <input class="" id="" name="tracking" type="text" value="" autocomplete="off">
            </div>
            <div class="filter hide-mobile">
                <label>${i18n['cione-module.templates.components.albaranes-component.numSerie']}</label>
                <input class="" id="" name="numSerie" type="text" value="" autocomplete="off">
            </div>

            <div class="filter" style="display: none">
                <input class="" id="pagina" name="pagina" type="hidden" value="0">
            </div>
        </div>
        <div class="mt-2">
  			<p class="font-weight-bold">${i18n['cione-module.templates.components.albaranes-component.txt-resultados']}</p>
		</div>
        <div class="panelbuttons">
            <button id="albaranes-component-search-btn"
                    class="btn-blue icon-search" type="submit"
                    onclick="searchAlbaranes(); return false">${i18n['cione-module.templates.components.albaranes-component.btn-buscar']}</button>
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
                	<th>${i18n['cione-module.templates.components.albaranes-component.tipoventa']}</th>
                	<th class="hide-mobile">${i18n['cione-module.templates.components.albaranes-component.tipo']}</th>
                	<th>${i18n['cione-module.templates.components.albaranes-component.num-albaran-cione']}</th>
                	<th>${i18n['cione-module.templates.components.albaranes-component.fecha']}</th>
                    <th>${i18n['cione-module.templates.components.albaranes-component.num-albaran-proveedor']}</th>
                    [#if showPrices(ctx.getUser())]
                    	<th class="">${i18n['cione-module.templates.components.albaranes-component.importe']}</th>
                   	[/#if]
                    <th class="hide-mobile">${i18n['cione-module.templates.components.albaranes-component.documento']}</th>
                    [#if showShipping(ctx.getUser())]
                    	<th class="hide-mobile">${i18n['cione-module.templates.components.albaranes-component.envio']}</th>
                    [/#if]
                    <th class="hide-mobile"></th>
                </tr>
            </thead>
            <tbody id="albaranes-table-data"></tbody>
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
    var albaranesLoaded = {};

	//function cabecera
    function templateAlbaran(albaran,index) {
    	
    	var enlaceEnvio = CTX_PATH + "/private/pedidos-facturacion/pedidos/consulta-envios.html?numAlbaran=" + albaran.numAlbaran;
        var html = "";
        html += "<tr data-id='" + index + "'>";
        html += "<td class='hide-mobile'>" + albaran.tipoVenta + "</td>";
        html += "<td class='hide-mobile'>" + albaran.tipoAlbaran + "</td>";
        html += "<td class=''>" + albaran.numAlbaran + "</td>";
        html += "<td class=''>" + albaran.fecha + "</td>";
        html += "<td class=''>" + albaran.albProveedor + "</td>";
        [#if showPrices(ctx.getUser())]
        	html += "<td class=''>" + albaran.importeNeto + "</td>";
        [/#if]
        
        html += '<td class="hide-mobile"><a href="#" onclick="obtenerPDFAlbaran(\'' + albaran.numAlbaran +'\')"><div class="icon documento"></div></a></td>';
        html += "<td class='hide-mobile'><a href='" + enlaceEnvio + "'>${i18n['cione-module.templates.components.albaranes-component.show-envio']}</a></td>";
        html += "<td class='masinfo hide-mobile'><a class='masinforesponsive' href='javascript:void(0)' onclick='getLineasAlbaran(\"" + albaran.numAlbaran + "\",this)'>${i18n['cione-module.templates.components.facturas-component.more-info']}</a></td>";
        html += "</tr>";

        //subtable       
        html += "<tr class='subtabla'>";
        html += "<td colspan='9'>";
        html += "<table class='table hide-mobile'>";
        html += "<thead>";
        html += "<tr class='encabezado'>";
        html += "<th>${i18n['cione-module.templates.components.facturas-component.numpedido']}</th>";
        html += "<th colspan='2'>${i18n['cione-module.templates.components.facturas-component.descripcion']}</th>";
        html += "<th>${i18n['cione-module.templates.components.facturas-component.unidades']}</th>";
		[#if showPrices(ctx.getUser())]
        html += "<th>${i18n['cione-module.templates.components.facturas-component.implinea']}</th>";
        [/#if]
        html += "<th>${i18n['cione-module.templates.components.facturas-component.numserie-cheque']}</th>";
        html += "</tr>";
        html += "</thead>";
        html += "<tbody id='" + albaran.numAlbaran + "'>";
        html += "</tbody>";
        html += "</table>";
        html += "</td>"; //td-colspan-9
        html += "</tr>"; 
        //subtable      
        return html;
    }
    
    function getLineasAlbaran(numAlbaran, element) {
        if(albaranesLoaded[numAlbaran]){
            toggleAlbaran(element);
            if (!($(".panel-table").hasClass("mobile"))) {
                verifyTest(element);
                return;
            }
        }else{
            albaranesLoaded[numAlbaran] = true;
        }       
        
        $("#loading").show();               
        $.ajax({
            url : PATH_API + "/private/albaranes/v1/albaranaudioinfo?numAlbaran=" + numAlbaran,
            type : "GET",                   
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            success : function(response) {            
                var listResult = [];                
                response.lineasAlbaranaudio.forEach(function(lineaAlbaran){
                    listResult.push(templateLineaAlbaranAudio(lineaAlbaran, numAlbaran));
                })  
                $("#" + numAlbaran).empty().append(listResult.join(" "));

                if ($(".panel-table").hasClass("mobile")) {
                    loadsubtable(element); 
                }
                
                toggleAlbaran(element);
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
    
    function obtenerPDFAlbaran(numAlbaran) {
    	var json_data = {};
		json_data["numAlbaran"] = numAlbaran;
		var uri = "";
		
		$.ajax({
        	url : PATH_API + "/private/albaranes/v1/albaranes",
            type : "POST",
            data : JSON.stringify(json_data),
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            success : function(response) {
                response.albaranes.forEach(function(albaran){
                    uri = albaran.albaran.url;
                });   
                obtenerPDF(uri);
            },
            error : function(response) {
                alert("error"); 
            }
        });
        
        

        return false;
    }

    
    function obtenerPDF(uri){
		$("#loading").show();
		var url = "${ctx.contextPath}/.rest/private/albaranes/v1/checkPdf?url="+uri;
		$.ajax({
			url : url,
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			success : function(response) {	
				window.open("${ctx.contextPath}/.rest/private/albaranes/v1/pdf?url=" + uri, '_blank');
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
    
    function templateLineaAlbaranAudio(lineaAlbaran, numAlbaran) {
    	var html = "";
        var enlacePedido = CTX_PATH + "/private/pedidos-facturacion/pedidos/consulta-pedidos.html?numPedido=" + lineaAlbaran.numPedido;
        
        html += "<tr>";
        html += "<td><a href='" + enlacePedido + "'>" + lineaAlbaran.numPedido + "</a></td>";
        html += "<td colspan='2'>" + lineaAlbaran.descripcionArticulo + "</td>";
        html += "<td>" + lineaAlbaran.unidades + "</td>";
        [#if showPrices(ctx.getUser())]
        html += "<td align='right'>" + lineaAlbaran.importeLinea + "</td>";
        [/#if]
        html += "<td align='right'>" + lineaAlbaran.loteCheque + "</td>";
        html += "</tr>";
        html += "</tr>";

        return html;
    }


    function templateShowMore(cPage,nPages,regTotales){

        var accReg = 0;

        if (cPage == nPages) {
            accReg = regTotales - (cPage - 1)*20;
        } else {
            accReg = 20;
        }

        nreg = nreg + accReg;

        var html="";
        html += "<span>${i18n['cione-module.templates.components.albaranes-component.search.showing-page']} " + nreg;
        html += " ${i18n['cione-module.templates.components.albaranes-component.search.showing-page-of']} ";
        html += "<span class='cantrow'>" + regTotales + "</span>";
        html += " ";
        html += "${i18n['cione-module.templates.components.albaranes-component.search.results']}";
        html += "</span>";
        html += " ";
        
        if (cPage == nPages){
            html += "<span>";
            html += " ";
        } else {
            html += "<span class='vermas' onclick='showMoreAlbaranes()'>";
            html += "${i18n['cione-module.templates.components.albaranes-component.search.show-more']}";
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
        html += "<span class='text-muted'>${i18n['cione-module.templates.components.albaranes-component.search.no-records-found']}</span>";
        html += "</td>";
        html += "</tr>";
        return html;
    }

    function searchAlbaranes(){

        page = 1;
        nreg = 0;
        albaranesLoaded = {};

        clearErrorMessages();
   
        var oForm = document.forms["formAlbaranes"];
        var vForm = validateForm(oForm);
        if(vForm) {
            $("#albaranes-table-data").html("");      
            $("#formAlbaranes input[name=pagina]").val(page);
            filter = getFormData($("#formAlbaranes"));
            getAlbaranes();
        }     
    }

    function showMoreAlbaranes(){
        page = page+1;

        $("#formAlbaranes input[name=pagina]").val(page);
        filter = getFormData($("#formAlbaranes"));

        getAlbaranes();        
    }

    function getAlbaranes() {

        $("#loading").show();
        $("#albaranes-component-search-btn").attr("disabled", "disabled");    
        
        $.ajax({
            url : PATH_API + "/private/albaranes/v1/albaranesaudio",
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
                    response.albaranesAudio.forEach(function(albaran){
                        count++;
                        listResult.push(templateAlbaran(albaran,count));
                    });                        
                } else {
                    listResult.push(templateNoRecordsFound());
                }
                    
                $("#albaranes-table-data").append(listResult.join(" "));
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
                $("#albaranes-component-search-btn").removeAttr("disabled");
                if (page >= nPages) {
                    $(".vermas").prop("onclick", null).off("click");
                } else{
                    $(".vermas").attr("onclick","showMoreAlbaranes()");
                }

                retailerZebra();
            }
        });

        return false;
    }

    function toggleAlbaran(element){         	
    	toggleRow(element);
        if ($(".panel-table").hasClass("mobile")) {
            loadsubtable(element); 
        }
    }

    function initPage(){
    
		var param = "numAlbaran";
		var numAlbaran = getParameterByName(param, window.location.href);
		if( numAlbaran ) {
			$("#formAlbaranes input[name=numAlbaran]").val(numAlbaran);
			filter = getFormData($("#formAlbaranes"));
		}
		searchAlbaranes();
    }
    
    function closeModalError() {
    	$("#modalError").hide();
    }

</script>