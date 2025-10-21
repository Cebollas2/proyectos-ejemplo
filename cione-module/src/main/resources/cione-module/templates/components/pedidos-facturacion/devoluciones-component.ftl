[#include "../../includes/macros/cione-utils.ftl"]
[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]
<style>
.validation-error {
  box-shadow: 0 0 0px 1px #EE0000;
}
</style>

<section>	
[#if content.showMessage?has_content && content.showMessage]
	<div class="b2b-header-box" style="width: fit-content; padding: 15px 20px; margin-left: auto; margin-right: 0;">
	    <span class="b2b-header-box-info"><p>${content.messageText!""}</p></span>
	</div>
[/#if]
<div class="cmp-tab">

	<ul class="tabs">
		<li id="tab-devolucion" class="tab-link current" data-tab="tab-pendiente">${i18n['cione-module.templates.components.devoluciones-component.devolucion']}</li>
		<li id="tab-gestion" class="tab-link" data-tab="tab-historial">${i18n['cione-module.templates.components.devoluciones-component.gestion']}</li>
	</ul>

	<div id="tab-pendiente" class="tab-content current">
		
		<section class="cmp-pedidosdevoluciones">

		    <form id="formDevoluciones" name="formDevoluciones" method="post">
		        <ul class="accordion-mobile">
		            <li><a class="toggle" href="javascript:void(0);">
		                    <div class="title">${i18n['cione-module.templates.components.devoluciones-component.devolucion']}<i class="fa fa-chevron-right"> </i></div>
		                </a>
		                <ul class="inner show" style="display: block;">
		                    <li>
		                    </li>
		                </ul>
		            </li>
		        </ul>
		        <div class="panel-filter">
		            <div class="filter">
		                <label>${i18n['cione-module.templates.components.devoluciones-component.num-albaran']}</label>
		                <input class="" id="numAlbaran" name="numAlbaran" type="text" value="">
		            </div>
		            <div class="filter fecha">
		                <label>${i18n['cione-module.templates.components.devoluciones-component.fecha-desde']}</label>
		                <input id="fechaIni" name="fechaIni" class="inputfecha" type="text">
		            </div>
		            <div class="filter fecha">
		                <label>${i18n['cione-module.templates.components.devoluciones-component.fecha-hasta']}</label>
		                <input id="fechaFin" name="fechaFin" class="inputfecha" type="text" value="">
		            </div>
		            <div class="filter">
		                <label>${i18n['cione-module.templates.components.devoluciones-component.referencia-socio']}</label>
		                <input class="" id="refSocio" name="refSocio" type="text" value="">
		            </div>
		            <div class="filter">
		                <label>${i18n['cione-module.templates.components.devoluciones-component.referencia-web']}</label>
		                <input class="" id=refWeb" name="refWeb" type="text" value="">
		            </div>
		            <div class="filter">
		                <label>${i18n['cione-module.templates.components.devoluciones-component.descripcion']}</label>
		                <input class="" id="descripcion" name="descripcion" type="text" value="">
		            </div>
		            <div class="filter hide-mobile" style="display: none">
						<input class="" id="pagina" name="pagina" type="hidden" value="0">
					</div>
		        </div>
		        <div class="panelbuttons">
					<button id="devoluciones-component-search-btn"
							class="btn-blue icon-search" type="submit"
							onclick="searchDevoluciones(0); return false">${i18n['cione-module.templates.components.pedidos-component.search.btn-buscar']}</button>
				</div>
		    </form>

		    <div class="cione-busqueda-avanzada">
		        <div>Búsqueda avanzada</div>
		        <div>
		            <button id="devoluciones-ultimos-component-search-btn"
							class="btn-blue icon-search" type="submit"
							onclick="searchDevoluciones(2); return false">${i18n['cione-module.templates.components.devoluciones-component.ultimos-productos']}</button>
		            <button id="devoluciones-multifocales-component-search-btn"
							class="btn-blue icon-search" type="submit"
							onclick="searchDevoluciones(3); return false">${i18n['cione-module.templates.components.devoluciones-component.multifocales']}</button>
		            <button id="devoluciones-audifonos-component-search-btn"
							class="btn-blue icon-search" type="submit"
							onclick="searchDevoluciones(4); return false">${i18n['cione-module.templates.components.devoluciones-component.audifonos']}</button>
		        </div>
		    </div>
		    <div class="panel-table">
		        <table class="table" id="table-devolucion-pedido">
		            <thead>
		                <tr>
		                    <th>${i18n['cione-module.templates.components.devoluciones-component.articulo']}</th>
		                    <th>${i18n['cione-module.templates.components.devoluciones-component.und']}</th>
		                    <th>${i18n['cione-module.templates.components.devoluciones-component.importe']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.fecha']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.num-albaran-desc']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.ref-web']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.ref-socio']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.devolver']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.und-devolver']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.motivo']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.observaciones']}</th>
		                    
		                </tr>
		            </thead>
		            <tbody id="table-devolucion-pedido-data"></tbody>
		        </table>
		        <div id="footDevolucion" class="foot"></div>
		        <div>
                	<div id="validate-error-msg" style="color: red; display: none;">${i18n['cione-module.templates.components.devoluciones-component.field-error']}</div>
                </div>
		    </div>
		    
		    <form action="">
		        <div class="panelbuttons" id="buttons-above-table">
		            <button class="btn-blue" id="add-devolucion-btn" type="button" onclick="addDevolucion()">${i18n['cione-module.templates.components.devoluciones-component.add']}</button>
					<button class="btn-blue" id="generar-devolucion-btn" type="button" onclick="generarDevolucion()" style="margin-left: 15px;">${i18n['cione-module.templates.components.devoluciones-component.entregar']}</button>
		        </div>
		    </form>
		    
		    <div id="table-added" class="panel-table d-none">
		        <table class="table" id="table-devolucion-pedido-added">
		            <thead>
		                <tr>
		                    <th>${i18n['cione-module.templates.components.devoluciones-component.articulo']}</th>
		                    <th>${i18n['cione-module.templates.components.devoluciones-component.und']}</th>
		                    <th>${i18n['cione-module.templates.components.devoluciones-component.importe']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.fecha']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.num-albaran-desc']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.ref-web']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.ref-socio']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.devolver']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.und-devolver']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.motivo']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.observaciones']}</th>
							<th>${i18n['cione-module.templates.components.devoluciones-component.delete']}</th>
		                    
		                </tr>
		            </thead>
		            <tbody id="table-devolucion-pedido-data-added"></tbody>
		        </table>
		        <div>
                	<div id="validate-error-msg-added" style="color: red; display: none;">${i18n['cione-module.templates.components.devoluciones-component.field-error']}</div>
                </div>
		    </div>

		    <form action="">
		        <div class="panelbuttons" id="buttons-under-table">
		            
		        </div>
		    </form>
		</section>

	</div>
	<div id="tab-historial" class="tab-content">
		<section class="cmp-pedidosdevolucionesgestion">
		    <form id="formDevolucionesGestion" name="formDevolucionesGestion" method="post">
		        <ul class="accordion-mobile">
		            <li><a class="toggle" href="javascript:void(0);">
		                    <div class="title">${i18n['cione-module.templates.components.devoluciones-component.gestion']}<i class="fa fa-chevron-right"> </i></div>
		                </a>
		                <ul class="inner show" style="display: block;">
		                    <li>
		                    </li>
		                </ul>
		            </li>
		        </ul>
		        <div class="panel-filter">
		        	<div class="filter">
		                <label>${i18n['cione-module.templates.components.devoluciones-component.num-albaran']}</label>
		                <input class="" id="numAlbaran" name="numAlbaran" type="text" value="">
		            </div>
		            <div class="filter">
		                <label>${i18n['cione-module.templates.components.devoluciones-component.numRMA']}</label>
		                <input class="" id="numRMA" name="numRMA" type="text" value="">
		            </div>
		            <div class="filter fecha">
		                <label>${i18n['cione-module.templates.components.devoluciones-component.fecha-desde']}</label>
		                <input id="fechaIniG" name="fechaIni" class="inputfecha" type="text">
		            </div>
		            <div class="filter fecha">
		                <label>${i18n['cione-module.templates.components.devoluciones-component.fecha-hasta']}</label>
		                <input id="fechaFinG" name="fechaFin" class="inputfecha" type="text" value="">
		            </div>
		            <div class="filter">
		                <label>${i18n['cione-module.templates.components.devoluciones-component.referencia-socio']}</label>
		                <input class="" id="refSocio" name="refSocio" type="text" value="">
		            </div>
		            <div class="filter">
		                <label>${i18n['cione-module.templates.components.devoluciones-component.referencia-web']}</label>
		                <input class="" id=refWeb" name="refWeb" type="text" value="">
		            </div>
		            <div class="filter">
		                <label>${i18n['cione-module.templates.components.devoluciones-component.descripcion']}</label>
		                <input class="" id="descripcion" name="descripcion" type="text" value="">
		            </div>
		            <div class="filter hide-mobile" style="display: none">
						<input class="" id="pagina" name="pagina" type="hidden" value="0">
					</div>
		        </div>
		        <div class="panelbuttons">
					<button id="devoluciones-gestion-component-search-btn"
							class="btn-blue icon-search" type="submit"
							onclick="searchDevoluciones(1); return false">${i18n['cione-module.templates.components.pedidos-component.search.btn-buscar']}</button>
				</div>
		        
		    </form>

    
		    <div class="panel-table">
		        <table class="table" id="table-devolucion-gestion">
		            <thead>
		                <tr>
		                	<th>${i18n['cione-module.templates.components.devoluciones-component.articulo']}</th>
		                	<th>${i18n['cione-module.templates.components.devoluciones-component.unidades-devolucion']}</th>
		                	<th>${i18n['cione-module.templates.components.devoluciones-component.importe']}</th>
		                	<th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.fecha-albaran']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.keyalbaran']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.numRMA']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.ref-web']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.ref-socio']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.estado']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.albaran-abono']}</th>
		                    <th class="hide-mobile">${i18n['cione-module.templates.components.devoluciones-component.fecha-abono']}</th>
		                </tr>
		            </thead>
		            <tbody id="table-devolucion-gestion-data"></tbody>
		        </table>
		        <div id="footGestion" class="foot">
		        	<div id="footDevolucion" class="foot"></div>
		        </div>
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

<div id="modalCondiciones" class="modal modal-anuncio" tabindex="-1" role="dialog" style="overflow: auto; display:none">
    <div class="modal-dialog"  role="document">
      <div class="modal-content" style="max-height: 600px; !important">
        <div class="modal-body">   
        	 	
        	<div style="display: flex;flex-direction: column;">
        		<div class="logo">
        			<img align="right" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/logo_cione.jpg" alt="logotipo">
				</div>
   				<div class="panel-button">
   					<div>
   						<p style="font-weight:bold; text-align:center; padding-top:25px;">${i18n['cione-module.templates.components.devoluciones-component.parrafo1']}</p>
   					</div>
	                <div>
	                	<p class="devolucion">${i18n['cione-module.templates.components.devoluciones-component.parrafo2']} <span id="idAbono" style="font-size: 1.225rem;"></span>
	                	  ${i18n['cione-module.templates.components.devoluciones-component.parrafo3']}</p>
	                </div>
	                <div>
	                	<p class="devolucion">${i18n['cione-module.templates.components.devoluciones-component.parrafo4']}</p>
	                </div>
	                [#if content.showMessage?has_content && content.showMessage]
						<div>
						    <p class="devolucion" style="font-weight:bold;">${content.messageText!""}</p>
						</div>
					[/#if]
	                <div>
	                	<p class="devolucion">${i18n['cione-module.templates.components.devoluciones-component.parrafo5']}</p>
	                </div> 
	                <div>
	                	<p class="devolucion">${i18n['cione-module.templates.components.devoluciones-component.parrafo6']}</p>
	                </div> 
	                <div class="wrapper" style="padding-top:20px;">
		                <a id="btn-aceptar" href="#" onclick="closeModalAbono();">${i18n['cione-module.templates.components.condiciones-comerciales.aceptar']}</a>
			        </div>
	            </div>
	            
	            <div class="b2b-msg-validation"></div>
        		
       		    
        	</div>
        </div>
        
        
      </div>
    </div>
</div>

<script>
	var page = 0;
	var nPages = 0;
	var nreg = 0;
	var filter = {};
	var indexReg = 0;
	var devolucionesLoaded = {};
	var motivos = [];
	var devolucionesSaved = {};
	
	function initPage(){
		searchDevoluciones(0);
	}
	
	function searchDevoluciones(tab){
		page = 1;
		nreg = 0;
		devolucionesLoaded = {};

		clearErrorMessages();
		
		if ((tab == 0) || (tab == 2) || (tab == 3) || (tab == 4)) {
			var oForm = document.forms["formDevoluciones"];
			var vForm = validateForm(oForm);
			if(vForm){
				$("#table-devolucion-pedido-data").html("");		
				$("#formDevoluciones input[name=pagina]").val(page);
				filter = getFormData($("#formDevoluciones"));
				getDevoluciones(tab);
			}

		} else {
			var oForm = document.forms["formDevolucionesGestion"];
			var vForm = validateForm(oForm);			
			if (vForm) {
				$("#table-devolucion-gestion-data").html("");
				$("#formDevolucionesGestion input[name=pagina]").val(page);
				filter = getFormData($("#formDevolucionesGestion"));
				getDevoluciones(tab);
			}
		}
	}
	
	function showMoreDevoluciones(tab){
		page = page+1;

		if ((tab == 0) || (tab == 2) || (tab == 3) || (tab == 4)) {
			$("#formDevoluciones input[name=pagina]").val(page);
			filter = getFormData($("#formDevoluciones"));
		} else {
			$("#formDevolucionesGestion input[name=pagina]").val(page);
			filter = getFormData($("#formDevolucionesGestion"));
		}

		getDevoluciones(tab);		
	}
	
	function getDevoluciones(tab) {
		$("#loading").show();
		var url = "";
		
		switch (tab) {
			case (0) :
				url = PATH_API + "/private/pedidos/v1/devoluciones";
				$("#devoluciones-component-search-btn").attr("disabled", "disabled");	
				filter = getFormData($("#formDevoluciones"));
				break;
			case (1) :
				url = PATH_API + "/private/pedidos/v1/gestion-devoluciones";
				$("#devoluciones-gestion-component-search-btn").attr("disabled", "disabled");
				filter = getFormData($("#formDevolucionesGestion"));
				break;
			case (2) :
				url = PATH_API + "/private/pedidos/v1/devoluciones-avanzadas";
				$("#devoluciones-ultimos-component-search-btn").attr("disabled", "disabled");
				filter = getFormData($("#formDevoluciones"));
				filter["tipo"] = "ultimosProductos";
				break;
			case (3) :
				url = PATH_API + "/private/pedidos/v1/devoluciones-avanzadas";
				$("#devoluciones-multifocales-component-search-btn").attr("disabled", "disabled");
				filter = getFormData($("#formDevoluciones"));
				filter["tipo"] = "multifocales";
				break;
			case (4) :
				url = PATH_API + "/private/pedidos/v1/devoluciones-avanzadas";
				$("#devoluciones-audifonos-component-search-btn").attr("disabled", "disabled");	
				filter = getFormData($("#formDevoluciones"));
				filter["tipo"] = "audifonos";
				break;
		}
		
		$.ajax({
			url : url,
			type : "POST",
			data : JSON.stringify(filter),
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			success : function(response) {
				var count = indexReg;
				var listResult = [];
				var cPage = response.pagina;
				var regTotales = response.numRegistros;
				nPages = response.ultimaPagina;
				motivos = response.motivos;

				if (regTotales > 0){
		        	response.devoluciones.forEach(function(devolucion){
		        		count++;
		        		if ((tab == 0) || (tab == 2) || (tab == 3) || (tab == 4)) {
		        			listResult.push(templateDevolucion(devolucion, response.motivos, count));
		        		} else {
		        			listResult.push(templateGestionDevolucion(devolucion, count));
		        		}
		        	});		
		        	indexReg = count;			
				} else {
					listResult.push(templateNoRecordsFound(tab));
				}

	        	if ((tab == 0) || (tab == 2) || (tab == 3) || (tab == 4)) {
	        		$("#table-devolucion-pedido-data").append(listResult.join(" "));
	        		$("#footDevolucion").empty();
	        		var registros = 0;
	        		if (response.devoluciones != null)
	        			registros = response.devoluciones.length;
	        		if (regTotales > 0) {
	        			$("#footDevolucion").append(templateShowMore(tab,cPage,nPages,registros,regTotales));	
	        		}				    	
	        	} else{
	        		$("#table-devolucion-gestion-data").append(listResult.join(" "));
					$("#footGestion").empty();
					var registros = response.devoluciones.length;
					if (regTotales > 0) {
						$("#footGestion").append(templateShowMore(tab,cPage,nPages,registros,regTotales));
					}
	        	}
			},
			error : function(response) {
				alert("error");				
				//$("#change-pwd-result").html("<p>" +  response.responseJSON.error.message + "</p>");
			},
			complete : function(response) {
				$("#loading").hide();

				if ((tab == 0) || (tab == 2) || (tab == 3) || (tab == 4)) {
					$("#tab-devolucion").addClass("current");
					$("#tab-pendiente").addClass("current");
					$("#devoluciones-component-search-btn").removeAttr("disabled");
					$("#devoluciones-ultimos-component-search-btn").removeAttr("disabled");
					$("#devoluciones-multifocales-component-search-btn").removeAttr("disabled");
					$("#devoluciones-audifonos-component-search-btn").removeAttr("disabled");
				} else {
					$("#tab-gestion").addClass("current");
					$("#tab-historial").addClass("current");
					$("#devoluciones-gestion-component-search-btn").removeAttr("disabled");
				}

				if (page >= nPages) {
					$(".vermas").prop("onclick", null).off("click");
				} else{
					$(".vermas").attr("onclick","showMoreDevoluciones(" + tab + ")");
				}

				retailerZebra();

			}

		});

		return false;
	}
	
	function templateDevolucion(devolucion, motivos, index) {
		
		var html = "";
		
			html += "<tr data-id='" + index + "'>";
			html += "<td id='descripcion-" + index + "'>" + devolucion.descripcion + "</td>";
			html += "<td style='text-align: right;' id='unidades-" + index + "'>" + devolucion.unidadesView + "</td>";
			html += "<td style='text-align: right;' id='importe-" + index + "'>" + devolucion.importeVenta + "</td>";
			html += "<td class='hide-mobile' id='fecha-" + index + "'>" + devolucion.fechaView + "</td>";
			html += "<td class='hide-mobile' id='keyAlbaran-" + index + "'>" + devolucion.keyAlbaran + "</td>";
			html += "<td class='hide-mobile' id='refweb-" + index + "'>" + devolucion.refweb + "</td>";
			html += "<td class='hide-mobile' id='refsocio-" + index + "'>" + devolucion.refsocio + "</td>";
			
			html += "<td class='hide-mobile'> <input class='styled-checkbox' id='checkdevolucion-" + index + "' name='checkdevolucion-" + index + "' type='checkbox' >";
			html += "<label for='checkdevolucion-" + index + "'></label></td>";
			html += "<td class='hide-mobile'> <input class='styled-input' id='unidades-devolucion-" + index+ "'  name='unidades-devolucion-" + index+ "' type='number' min='1' max='" + devolucion.unidadesView + "' value='" + devolucion.unidadesView + "'></td>";
			html += "<td class='hide-mobile'> <select class='styled-input' id='motivo-" + index + "'>";
			
			html += "<option value='' selected></option>";
			$.each(motivos, function( index, motivo ) {
				html += "<option value='" + motivo.codMotivo + "'>" + motivo.descMotivo + "</option>";
			});
			
			
			html += "</select></td>";
			html += "<td class='hide-mobile'> <textarea class='styled-input' name='observaciones-" + index + "' id='observaciones-" + index + "' autocomplete='off' rows='1'></textarea></td>";
		    
		    
		    html += "<td class='hide-mobile' style='display:none'> <input id='aliasEkon-" + index+ "'  name='aliasEkon-" + index+ "' type='hidden' value=" + devolucion.aliasEkon + "> </td>";
		    html += "<td class='hide-mobile' style='display:none'> <input id='nlinAlbaran-" + index+ "'  name='nlinAlbaran-" + index+ "' type='hidden' value=" + devolucion.nlinAlbaran + "> </td>";
		    
			html += "</tr>";
				
		return html;
	}

	function templateAddDevolucion(devolucion, motivos, index, id) {
		
		var html = "";
		
			html += "<tr data-id-add='" + index + "'>";
			html += "<td id='descripcion-add-" + index + "'>" + devolucion.descripcion + "</td>";
			html += "<td style='text-align: right;' id='unidades-add-" + index + "'>" + devolucion.unidadesView + "</td>";
			html += "<td style='text-align: right;' id='importe-add-" + index + "'>" + devolucion.importeVenta + "</td>";
			html += "<td class='hide-mobile' id='fecha-add-" + index + "'>" + devolucion.fechaView + "</td>";
			html += "<td class='hide-mobile' id='keyAlbaran-add-" + index + "'>" + devolucion.keyAlbaran + "</td>";
			html += "<td class='hide-mobile' id='refweb-add-" + index + "'>" + devolucion.refweb + "</td>";
			html += "<td class='hide-mobile' id='refsocio-add-" + index + "'>" + devolucion.refsocio + "</td>";
			
			html += "<td class='hide-mobile'> <input class='styled-checkbox' checked id='checkdevolucion-add-" + index + "' name='checkdevolucion-" + index + "' type='checkbox' >";
			html += "<label for='checkdevolucion-add-" + index + "'></label></td>";
			html += "<td class='hide-mobile'> <input class='styled-input' id='unidades-devolucion-add-" + index+ "'  name='unidades-devolucion-" + index+ "' type='number' min='1' max='" + devolucion.unidadesView + "' value='" + devolucion.unidades + "'></td>";
			html += "<td class='hide-mobile'> <select class='styled-input' id='motivo-add-" + index + "'>";
			
			if (devolucion.motivo == '') {
				html += "<option value='' selected></option>";
			} else {
				html += "<option value=''></option>";
			}
			$.each(motivos, function( index, motivo ) {
				if (motivo.codMotivo == devolucion.motivo) {
					html += "<option value='" + motivo.codMotivo + "' selected>" + motivo.descMotivo + "</option>";
				} else {
					html += "<option value='" + motivo.codMotivo + "'>" + motivo.descMotivo + "</option>";
				}
				
			});
			
			
			html += "</select></td>";
			html += "<td class='hide-mobile'> <textarea class='styled-input' name='observaciones-" + index + "' id='observaciones-add-" + index + "' autocomplete='off' rows='1'>" + devolucion.observaciones + "</textarea></td>";
		    html += "<td> <img class='b2b-icon-delete' src='${resourcesURL}/img/myshop/icons/bi-trash.svg' style='padding-left: 17px;' onclick='deleteRow(\"" + id + "\")'></td>";
		    
		    html += "<td class='hide-mobile' style='display:none'> <input id='aliasEkon-add-" + index+ "'  name='aliasEkon-" + index+ "' type='hidden' value=" + devolucion.aliasEkon + "> </td>";
		    html += "<td class='hide-mobile' style='display:none'> <input id='nlinAlbaran-add-" + index+ "'  name='nlinAlbaran-" + index+ "' type='hidden' value=" + devolucion.nlinAlbaran + "> </td>";
		    
			html += "</tr>";
				
		return html;
	}
	
	function templateRecoveryDevolucion(devolucion, motivos) {
		
		var html = "";
			var index = devolucion.indexLinea;
			html += "<tr data-id='" + index + "'>";
			html += "<td id='descripcion-" + index + "'>" + devolucion.descripcion + "</td>";
			html += "<td style='text-align: right;' id='unidades-" + index + "'>" + devolucion.unidadesView + "</td>";
			html += "<td style='text-align: right;' id='importe-" + index + "'>" + devolucion.importeVenta + "</td>";
			html += "<td class='hide-mobile' id='fecha-" + index + "'>" + devolucion.fechaView + "</td>";
			html += "<td class='hide-mobile' id='keyAlbaran-" + index + "'>" + devolucion.keyAlbaran + "</td>";
			html += "<td class='hide-mobile' id='refweb-" + index + "'>" + devolucion.refweb + "</td>";
			html += "<td class='hide-mobile' id='refsocio-" + index + "'>" + devolucion.refsocio + "</td>";
			
			html += "<td class='hide-mobile'> <input class='styled-checkbox' id='checkdevolucion-" + index + "' name='checkdevolucion-" + index + "' type='checkbox' >";
			html += "<label for='checkdevolucion-" + index + "'></label></td>";
			html += "<td class='hide-mobile'> <input class='styled-input' id='unidades-devolucion-" + index+ "'  name='unidades-devolucion-" + index+ "' type='number' min='1' max='" + devolucion.unidadesView + "' value='" + devolucion.unidadesView + "'></td>";
			html += "<td class='hide-mobile'> <select class='styled-input' id='motivo-" + index + "'>";
			
			html += "<option value='' selected></option>";
			$.each(motivos, function( index, motivo ) {
				html += "<option value='" + motivo.codMotivo + "'>" + motivo.descMotivo + "</option>";
			});
			
			
			html += "</select></td>";
			html += "<td class='hide-mobile'> <textarea class='styled-input' name='observaciones-" + index + "' id='observaciones-" + index + "' autocomplete='off' rows='1'></textarea></td>";
		    
		    
		    html += "<td class='hide-mobile' style='display:none'> <input id='aliasEkon-" + index+ "'  name='aliasEkon-" + index+ "' type='hidden' value=" + devolucion.aliasEkon + "> </td>";
		    html += "<td class='hide-mobile' style='display:none'> <input id='nlinAlbaran-" + index+ "'  name='nlinAlbaran-" + index+ "' type='hidden' value=" + devolucion.nlinAlbaran + "> </td>";
		    
			html += "</tr>";
				
		return html;
	}

	function deleteRow(id) {
	
		var deleteDevolucion = devolucionesSaved[id];
		
		var child = deleteDevolucion.indexLinea -1;
		var recovery = templateRecoveryDevolucion(deleteDevolucion, motivos);
		if ($("#table-devolucion-pedido-data tr[data-id='" + child + "']").length != 0) {
			$("#table-devolucion-pedido-data tr[data-id='" + child + "']").after(recovery);
		} else {
			//primer elemento
			$("#table-devolucion-pedido-data").prepend(recovery);
		}
	
	
		delete devolucionesSaved[id];

		$("#table-devolucion-pedido-data-added").html("");
		index = 1;
		$.each(devolucionesSaved, function(key, value) {
			$("#table-devolucion-pedido-data-added").append(templateAddDevolucion(value, motivos, index, key));
			index++;
		});
		if (Object.keys(devolucionesSaved).length > 0) {
			$("#table-added").removeClass("d-none");
			$('#generar-devolucion-btn').detach().appendTo('#buttons-under-table');
		} else {
			$("#table-added").addClass("d-none");
			$('#generar-devolucion-btn').detach().appendTo('#buttons-above-table');
		}
		
		
		
		retailerZebra();
	}
	
	function templateGestionDevolucion(devolucion, index) {
		
		var html = "";
		
			html += "<tr data-id='" + index + "'>";
			html += "<td>" + devolucion.descripcion + "</td>";
			html += "<td>" + devolucion.unidadesView + "</td>";
			html += "<td>" + devolucion.importeVenta + "</td>";
			html += "<td class='hide-mobile'>" + devolucion.fechaView + "</td>";
			html += "<td class='hide-mobile'>" + devolucion.keyAlbaran + "</td>";
			html += "<td class='hide-mobile'>" + devolucion.numRMA + "</td>";
			html += "<td class='hide-mobile'>" + devolucion.refweb + "</td>";
			html += "<td class='hide-mobile'>" + devolucion.refsocio + "</td>";
			html += "<td class='hide-mobile'>" + devolucion.estadoDevolucion + "</td>";
			html += "<td class='hide-mobile'>" + devolucion.albaranAbono + "</td>";
			html += "<td class='hide-mobile'>" + devolucion.fechaAbonoView + "</td>";
			html += "</tr>";
				
		return html;
	}
	
	function templateNoRecordsFound(tab){
		
		var columns;

		if ((tab == 0) || (tab == 2) || (tab == 3) || (tab == 4)) {
			columns = $("#table-devolucion-pedido").find('tr')[0].cells.length;
		} else {
			columns = $("#table-devolucion-gestion").find('tr')[0].cells.length;
		}
		
		var html = templateNoRecordsFoundForTable(columns);
		
		return html;
	}
	
	function templateShowMore(tab,cPage,nPages,reg,regTotales){

		var accReg = 0;

		if (cPage == nPages) {
			accReg = regTotales - (cPage - 1)*reg;
		} else {
			accReg = reg;
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
			html += "<span class='vermas' onclick='showMoreDevoluciones(" + tab + ")'>";
			html += "${i18n['cione-module.templates.components.pedidos-component.search.show-more']}";
		}

		html += "</span>";
		html += " ";
		html += "<span></span>";

		return html;
	}
	
	function addDevolucion() {
		
		rows = $("input[id^='checkdevolucion']:checked");
		//rows.each(function() {
			//id = $(this).attr("id");
			//index = id.split("checkdevolucion-").pop();
		//});
		
		var validate = true;
		$("#table-devolucion-pedido-data tr").each(function(){
			
			//validacion si tiene marcada la fila pero no ha seleccionado el motivo
			var indexf = $(this).data("id");
			if ($("#checkdevolucion-" +  indexf).prop("checked")) {
				if ($( "#motivo-" + indexf + " option:checked" ).val() != null && $( "#motivo-" + indexf + " option:checked" ).val() != "") {
					$("#motivo-" + indexf).removeClass("validation-error");
				} else {
					$("#motivo-" + indexf).addClass("validation-error");
					validate = false;
				}
			}
			//validacion si tiene seleccionado el motivo pero no ha marcado el check
			if ($( "#motivo-" + indexf + " option:checked" ).val() != null && $( "#motivo-" + indexf + " option:checked" ).val() != "") {
				if (!$("#checkdevolucion-" +  indexf).prop("checked")) {
					$("#checkdevolucion-" + indexf).addClass("validation-error");
					validate = false;
				} else {
					$("#checkdevolucion-" + indexf).removeClass("validation-error");
				}
			}
			
		});
		
		
		
		if (validate) {
			rows.each(function() {
				id = $(this).attr("id");
				index = id.split("checkdevolucion-").pop();
	
				var linea = {};
				var indexclear = index.split("add-").pop();
				linea["indexLinea"] = indexclear;
				linea["descripcion"] = $("#descripcion-" + index).text();
				linea["unidadesView"] = $("#unidades-" + index).text();
				linea["importeVenta"] = $("#importe-" + index).text();
				linea["fechaView"] = $("#fecha-" + index).text();
				linea["keyAlbaran"] = $("#keyAlbaran-" + index).text();
				linea["refweb"] = $("#refweb-" + index).text();
				linea["refsocio"] = $("#refsocio-" + index).text();
				linea["unidades"] = $("#unidades-devolucion-" + index).val();
				linea["observaciones"] = $("#observaciones-" + index).val();
				linea["aliasEkon"] = $("#aliasEkon-" + index).val();
				linea["nlinAlbaran"] = $("#nlinAlbaran-" + index).val();
				linea["motivo"] = $( "#motivo-" + index + " option:checked" ).val();
	
				id = linea.keyAlbaran + "-" + linea.nlinAlbaran;
				devolucionesSaved[id] = linea;
				
				
				$("#table-devolucion-pedido-data tr[data-id='" + index + "']").remove();
	
			});
			$("#table-devolucion-pedido-data-added").html("");
			index = 1;
			$.each(devolucionesSaved, function(key, value) {
				$("#table-devolucion-pedido-data-added").append(templateAddDevolucion(value, motivos, index, key));
				index++;
			});
			
			$("#table-added").removeClass("d-none");
			$('#generar-devolucion-btn').detach().appendTo('#buttons-under-table');
			retailerZebra();
		} else {
			alert("revise la información del formulario");
		}
	}
	
	function generarDevolucion() {
		if (Object.keys(devolucionesSaved).length > 0) {
			generarDevolucionSaved();
		} else {
			generarDevolucionChecked();
		}
	}

	function generarDevolucionSaved() {
		$("#generar-devolucion-btn").attr("disabled", "disabled");
		$("#validate-error-msg").css("display", "none");
		$("#validate-error-msg-added").css("display", "none");
		var devolucion = "";
		var validate = true;
		var oneregcheck = false;
		var array_line_selected = [];
		rows = $("input[id^='checkdevolucion-add-']:checked");
		rows.each(function() {
			i = $(this).attr('id').split('checkdevolucion-add-').pop();
			
			oneregcheck = true;
			var linea = {};
			
			var pvo = $("#importe-add-" + i).text();
			var unidades = $("#unidades-add-" + i).text();
			var pvo_double = parseFloat(pvo)/parseFloat(unidades);
			
			linea["unidadesTotalesLinea"] = $("#unidades-add-" + i).text();
			linea["pvo"] = $("#importe-add-" + i).text();
			linea["keyAlbaran"] = $("#keyAlbaran-add-" + i).text();
			linea["descripcion"] = $("#descripcion-add-" + i).text();
			if ($("#unidades-devolucion-add-" + i).val() == "") {
				$("#unidades-devolucion-add-" + i).addClass("validation-error");
				validate = false;
			} else {
				var unidades = $("#unidades-devolucion-add-" + i).val();
				if ((unidades > $("#unidades-add-" + i).text()) || (unidades < 1)){
					$("#unidades-devolucion-add-" + i).addClass("validation-error");
					validate = false;
				} else {
					linea["unidades"] = $("#unidades-devolucion-add-" + i).val();
				}
			}
			if ($( "#motivo-add-" + i + " option:checked" ).val() != null && $( "#motivo-add-" + i + " option:checked" ).val() != "") {
				linea["motivo"] = $( "#motivo-add-" + i + " option:checked" ).val();
			} else {
				$("#motivo-add-" + i).addClass("validation-error");
				validate = false;
			}
			linea["observaciones"] = $("#observaciones-add-" + i).val();
			
			linea["aliasEkon"] = $("#aliasEkon-add-" + i).val();
			linea["nlinAlbaran"] = $("#nlinAlbaran-add-" + i).val();
			
			console.log(linea);
			if (devolucion == "") {
				devolucion = "{'devoluciones':[" + JSON.stringify(linea);
			} else {
				devolucion += ", " + JSON.stringify(linea);
			}

		});

		for (var i=1; i<=indexReg; i++) {
			key = $("#keyAlbaran-" + i).text();
			nlin = $("#nlinAlbaran-" + i).val();
			id = key + "-" + nlin;
			if (devolucionesSaved[id] != undefined) {
				array_line_selected.push(i);
			}
		}

		if (devolucion != "") {
			devolucion += "]}";
		}
		
		devolucion = devolucion.replaceAll("'", "\"");
		console.log(devolucion);

		if (validate && oneregcheck) {
			$("#loading").show();
			$.ajax({
	            url : "${ctx.contextPath}/.rest/private/carrito/v1/carts-addItemReturn",
	            type : "POST",
	            data : devolucion,
	            contentType : 'application/json; charset=utf-8',
	            cache : false,
	            dataType : "json",
	            success : function(response) {
	            	$("span#idAbono").text(response.custom.fields.numRMA);
	            	$("#modalCondiciones").show();
	            	removeItems();
	            	removeLines(array_line_selected);
					$('#table-devolucion-pedido-added tbody').html('');
					$("#table-added").addClass("d-none");
					$('#generar-devolucion-btn').detach().appendTo('#buttons-above-table');
					devolucionesSaved = {};
	            },
	            error : function(response) {
	                alert("error"); 
	            },
	            complete : function(response) {
	                $("#loading").hide();
	            }
	        });
		} else {
			$("#validate-error-msg-added").css("display", "block");
		}
		$("#generar-devolucion-btn").removeAttr("disabled");
		
	}

	function generarDevolucionChecked() {
		$("#generar-devolucion-btn").attr("disabled", "disabled");
		$("#validate-error-msg").css("display", "none");
		$("#validate-error-msg-added").css("display", "none");
		var devolucion = "";
		var validate = true;
		var oneregcheck = false;
		var array_line_selected = [];
		for (var i=1; i<=indexReg; i++) {
			if ($("#checkdevolucion-" +  i).prop("checked")) {
				array_line_selected.push(i);
				var oneregcheck = true;
				var linea = {};
				
				var pvo = $("#importe-" + i).text();
				var unidades = $("#unidades-" + i).text();
				var pvo_double = parseFloat(pvo)/parseFloat(unidades);
				
				linea["unidadesTotalesLinea"] = $("#unidades-" + i).text();
				linea["pvo"] = $("#importe-" + i).text();
				linea["keyAlbaran"] = $("#keyAlbaran-" + i).text();
				linea["descripcion"] = $("#descripcion-" + i).text();
				if ($("#unidades-devolucion-" + i).val() == "") {
					$("#unidades-devolucion-" + i).addClass("validation-error");
					validate = false;
				} else {
					var unidades = $("#unidades-devolucion-" + i).val();
					if ((unidades > $("#unidades-" + i).text()) || (unidades < 1)){
						$("#unidades-devolucion-" + i).addClass("validation-error");
						validate = false;
					} else {
						linea["unidades"] = $("#unidades-devolucion-" + i).val();
					}
				}
				if ($( "#motivo-" + i + " option:checked" ).val() != null && $( "#motivo-" + i + " option:checked" ).val() != "") {
					linea["motivo"] = $( "#motivo-" + i + " option:checked" ).val();
				} else {
					$("#motivo-" + i).addClass("validation-error");
					validate = false;
				}
				linea["observaciones"] = $("#observaciones-" + i).val();
				
				linea["aliasEkon"] = $("#aliasEkon-" + i).val();
				linea["nlinAlbaran"] = $("#nlinAlbaran-" + i).val();
				
				console.log(linea);
				if (devolucion == "") {
					devolucion = "{'devoluciones':[" + JSON.stringify(linea);
				} else {
					devolucion += ", " + JSON.stringify(linea);
				}
			}
		}
		if (devolucion != "") {
			devolucion += "]}";
		}
		
		devolucion = devolucion.replaceAll("'", "\"");
		console.log(devolucion);
		if (validate && oneregcheck) {
			$("#loading").show();
			$.ajax({
	            url : "${ctx.contextPath}/.rest/private/carrito/v1/carts-addItemReturn",
	            type : "POST",
	            data : devolucion,
	            contentType : 'application/json; charset=utf-8',
	            cache : false,
	            dataType : "json",
	            success : function(response) {
	            	$("span#idAbono").text(response.custom.fields.numRMA);
	            	$("#modalCondiciones").show();
	            	removeItems();
	            	removeLines(array_line_selected);
					$("#table-added").addClass("d-none");
	            },
	            error : function(response) {
	                alert("error"); 
	            },
	            complete : function(response) {
	                $("#loading").hide();
	            }
	        });
		} else {
			$("#validate-error-msg").css("display", "block");
			alert("revise la información del formulario");
		}
		$("#generar-devolucion-btn").removeAttr("disabled");
	} 
	
	function removeLines(array_line_selected) {
		array_line_selected.forEach(function(line) {
		    $("#table-devolucion-pedido-data tr[data-id='" + line + "']").remove();
		    //$("#table-devolucion-pedido-data tr").data("id", line).remove();
		})
	}
	
	function closeModalAbono(){
		$("#modalCondiciones").hide();
	}
	
	function removeItems() {
		for (var i=1; i<=indexReg; i++) {
			$("#checkdevolucion-" +  i).prop('checked', false);
			var unidades = $("#unidades-" + i).text()
			$("#unidades-devolucion-" + i).val(unidades);
			$("#motivo-" + i).val('');
			$("#observaciones-" + i).val('');
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
		var temp = document.getElementById("table-devolucion-pedido-data").parentNode;
		var child = temp.children;
		if (child.length > 0){
			searchDevoluciones(0);
		}  	
  	} else {
  		var temp = document.getElementById("table-devolucion-gestion-data").parentNode;
  		var child = temp.children;
		if (child.length > 0){
			searchDevoluciones(1);
		}
  	}
  }

  bindAll();
}

var connectTabs = new Tabs();

</script>