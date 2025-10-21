 
 <#assign fideliza=model.ocultarSecciones()>
 <section class="cmp-datoseurocione">
    <#if !fideliza>
	    <div class="wrapper">
	        <div class="title">
	        	${i18n['cione-module.templates.components.eurocione.eurocione-mes-anterior']}                	
	        </div>
	        <form class="data-panel">
	            <div class="formula">
	            	<#assign euroCione=model.getEurociones()>
	                <div class="item">
	                    <label>${i18n['cione-module.templates.components.eurocione.eurociones-generados-ano-pv']}</label>
	                    <input class="form-control" type="text" value="${euroCione.getEc_generados()!}" disabled>
	                </div> 
	                <span>-</span>
	                <div class="item">
	                    <label>${i18n['cione-module.templates.components.eurocione.eurociones-canjeados-ano']}</label>
	                    <input class="form-control" type="text" value="${euroCione.getEc_canjeados()!}" disabled>
	                </div> 
	                <span>=</span>
	                <div class="item">
	                    <label>${i18n['cione-module.templates.components.eurocione.eurociones-netos-ano']}</label>
	                    <input class="form-control" type="text" value="${euroCione.getEc_netos()!}" disabled>
	                </div>
	                <span>+</span>
	                <div class="item">
	                    <label>${i18n['cione-module.templates.components.eurocione.eurociones-anteriores-ano']}</label>
	                    <input class="form-control" type="text" value="${euroCione.getEc_anteriores()!}" disabled>
	                </div>
	                <span>=</span>
	                <div class="item">
	                    <label>${i18n['cione-module.templates.components.eurocione.eurociones-disponibles-mes-anterior']}</label>
	                    <input class="form-control" type="text" value="${euroCione.getEc_disponibles()!}" disabled>
	                </div>
	            </div>
	            <div class="comment" style="text-align:left;color:#00609c;">${i18n['cione-module.templates.components.eurocione.label']}</div>
	
	            <div class="item">
	                <@cms.area name="documents" />
	            </div>
	        </form>
	    </div>
    
    <div class="wrapper">
        <div class="title">${i18n['cione-module.templates.components.eurocione.plan-fidelizacion']}</div>
        <#assign planesFideliza = model.getPlanesFideliza()>
        <form class="data-panel">
        	<div class="item d-flex">
	            <div class="item">
	                <label>
	                	${i18n['cione-module.templates.components.eurocione.categoria']}                	
	                </label>
					<span class="comment">${i18n['cione-module.templates.components.eurocione.categoria-aviso']}</span>                                
	                <input class="form-control" type="text" value="${euroCione.getCategoria()!}" disabled>
	            </div>
	            <div class="item">
	                <label>${i18n['cione-module.templates.components.eurocione.eurociones-generados-grupo']}</label>
	                <span class="comment"></span>
	                <input class="form-control" type="text" value="${planesFideliza.getCionesAcumuladosGrupoTotalView()!}" disabled> 
	            </div>
	            <div class="item">
	                <label>
	                	${i18n['cione-module.templates.components.eurocione.eurociones-generados-punto-venta']}                	
	                </label>
					<span class="comment"></span>                                
	                <input class="form-control" type="text" value="${planesFideliza.getCionesAcumuladosTotalView()!}" disabled> 
	            </div>
	        </div>
        </form>

        <div class="panel-table">
            <table class="table">
                <thead>
                    <tr>
                        <th>${i18n['cione-module.templates.components.eurocione.tipo']}</th>
                        <th>${i18n['cione-module.templates.components.eurocione.consumo-actual-grupo']}</th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.consumo-actual-pventa']}</th>
                        <#-- <th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.descuento-actual']}</th> -->
                        <th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.rappel-actual']}</th>
                        <#-- <th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.eurociones-acumulados-grupo']}</th> -->
                        <#if !fideliza><th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.eurociones-generados-ano-curso-grupo']}</th></#if>
                        <#-- <th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.eurociones-acumulados-pventa']}</th> -->
                        <th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.eurociones-generados-ano-curso-pventa']}</th>
                        <#if !fideliza><th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.consumo-subir-categoria']}</th></#if>
                        <#-- <th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.descuento-siguiente']}</th> -->
                        <th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.rappel-siguiente']}</th>
                    </tr>
                </thead>
                 <tbody>
                	<#if planesFideliza.getTiposPlanFideliza()?size == 0>
                		<tr>
                			<td colspan="8" style="text-align:center">
                				${i18n['cione-module.global.no-records-found']}
                			</td>	                			
                		</tr>
					</#if>
                	<#list planesFideliza.getTiposPlanFideliza() as fideliza>
						<tr>							
	                    	<td align='right'>${fideliza.getTipo()!}</td>
	                    	<td align='right'>${fideliza.getConsumoActualGrupo()!}</td>
	                    	<td class="hide-mobile" align='right'>${fideliza.getConsumoActual()!}</td>
	                    	<td class="hide-mobile" align='right'>${fideliza.getDescuentoActual()!}</td>
	                    	<td class="hide-mobile" align='right'>${fideliza.getCionesAcumuladosGrupoView()!}</td>
	                    	<td class="hide-mobile" align='right'>${fideliza.getCionesAcumuladosView()!}</td>
	                    	<td class="hide-mobile" align='right'>${fideliza.getConsumoSigCategoria()!}</td>
	                    	<td class="hide-mobile" align='right'>${fideliza.getSigDescuento()!}</td>
	                	</tr>	                	
					</#list>                        
                </tbody>
            </table>

        </div>

        <form class="data-panel">
          	<@cms.area name="documents2" />
        </form>

    </div>
    </#if>
    <div class="wrapper">
        <div class="title">${i18n['cione-module.templates.components.eurocione.condiciones-comerciales']}  </div>
        <#assign condiciones = model.getCondiciones()>
        <form class="data-panel">
            <div class="item half">
                <label>
                	${i18n['cione-module.templates.components.eurocione.eurociones-generados-grupo']}                	
                </label>				                               
                <input class="form-control" type="text" value="${condiciones.getCionesAcumuladosGrupoTotalView()!}" disabled>
            </div>
            <div class="item half">
                <label>
                	${i18n['cione-module.templates.components.eurocione.eurociones-generados-punto-venta']}                	
                </label>				                               
                <input class="form-control" type="text" value="${condiciones.getCionesAcumuladosTotalView()!}" disabled>
            </div>
        </form>
        
        <#-- TABLA NUEVA -->
        <button class="button-expand" for="table-condiciones">Ver + columnas</button>
        <div class="panel-table">
            <table class="table" id="table-condiciones">
                <thead>
					<tr>
	                    <th>${i18n['cione-module.templates.components.eurocione.acuerdo']}</th>
	                    <#if !fideliza><th class="col-sliding col-collapsed">${i18n['cione-module.templates.components.eurocione.fact-bruta-grupo']}</th></#if>
	                    <th class="col-sliding col-collapsed">${i18n['cione-module.templates.components.eurocione.fact-bruta-pventa']}</th>
	                    <th class="col-sliding col-collapsed">${i18n['cione-module.templates.components.eurocione.devengo']}</th>
	                    <th class="col-sliding col-collapsed">${i18n['cione-module.templates.components.eurocione.fact-bruta-devenga']}</th>
	                    <th class="col-sliding col-collapsed">${i18n['cione-module.templates.components.eurocione.euro-cione-liquidados']}</th>
	                    <#if !fideliza><th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.fact-neta-grupo']}</th></#if>
	                    <th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.rappel-actual']}</th>
	                    <#if !fideliza><th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.generados-grupo']}</th></#if>
	                    <th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.generados-pventa']}</th>
	                    <#if !fideliza><th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.fact-neta-subir-categoria']}</th></#if>
	                    <th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.rappel-siguiente']}</th>
	                    <th class="hide-mobile"></th>
	                </tr>                	
                </thead>
                <tbody id="condiciones-table-data">
                	<#if condiciones.getCondiciones()?size == 0>
                		<tr>
                			<td colspan="13" style="text-align:center">
                				${i18n['cione-module.global.no-records-found']}
                			</td>	                			
                		</tr>
                	<#else>
                		<#list condiciones.getCondiciones() as condicion>
	                		<tr id="${condicion.idAcuerdo}" data-id="${condicion.idAcuerdo}" class="even">
	                			<td style="font-weight: bold;">${condicion.nombreAcuerdo}</td>
	                			<#if !fideliza><td class="col-sliding col-collapsed" style="font-weight: bold;">${condicion.factBrutaGrupo}</td></#if>
								<td class="col-sliding col-collapsed" style="font-weight: bold;">${condicion.factBrutaPVenta}</td>
								<#if condicion.porcDevengo == "">
									<td class="col-sliding col-collapsed" style="font-weight: bold;"><a class="masinforesponsive" href="javascript:void(0)" onclick="getCondicionesInfo('${condicion.idAcuerdo}', this)"> detalle</a></td>
								<#else>
									<td class="col-sliding col-collapsed" style="font-weight: bold;">${condicion.porcDevengo}</td>
								</#if>
								<td class="col-sliding col-collapsed" style="font-weight: bold;">${condicion.impDevengo}</td>
								<td class="col-sliding col-collapsed" style="font-weight: bold;">${condicion.impLiquidado}</td>
								<#if !fideliza><td class="hide-mobile" style="font-weight: bold;">${condicion.factNetaDevenga}</td></#if>
								<td class="hide-mobile" style="font-weight: bold;">${condicion.rappelActual}</td>
								<#if !fideliza><td class="hide-mobile" style="font-weight: bold;">${condicion.cionesAcumuladosGrupo}</td></#if>
								<td class="hide-mobile" style="font-weight: bold;">${condicion.cionesAcumuladosPVenta}</td>
								<#if !fideliza><td class="hide-mobile" style="font-weight: bold;">${condicion.facNetaGrupoSubirCateg}</td></#if>
								<td class="hide-mobile" style="font-weight: bold;">${condicion.sigRappel}</td>
								<#if condicion.showDetail>
									<td class="hide-mobile" style="font-weight: bold;"><a class="masinforesponsive" href="javascript:void(0)" onclick="getCondicionesInfo('${condicion.idAcuerdo}', this)">Ver detalle</a></td>
	                			<#else>
									<td class="hide-mobile"></td>
								</#if>
	                		</tr>
	                		<tr class="subtabla">
							  <td colspan="13">
							    <table class="table hide-mobile table-border">
							    	<thead style="font-size: xx-small;">
										<tr class="encabezado">
						                    <th>${i18n['cione-module.templates.components.eurocione.acuerdo']}</th>
						                    <#if !fideliza><th class="col-sliding col-collapsed">${i18n['cione-module.templates.components.eurocione.fact-bruta-grupo']}</th></#if>
						                    <th class="col-sliding col-collapsed">${i18n['cione-module.templates.components.eurocione.fact-bruta-pventa']}</th>
						                    <th class="col-sliding col-collapsed">${i18n['cione-module.templates.components.eurocione.devengo']}</th>
						                    <th class="col-sliding col-collapsed">${i18n['cione-module.templates.components.eurocione.fact-bruta-devenga']}</th>
						                    <th class="col-sliding col-collapsed">${i18n['cione-module.templates.components.eurocione.euro-cione-liquidados']}</th>
						                    <#if !fideliza><th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.fact-neta-grupo']}</th></#if>
						                    <th class="hide-mobile" style="color: #00609c;">${i18n['cione-module.templates.components.eurocione.rappel-actual']}</th>
						                    <#if !fideliza><th class="hide-mobile" style="color: #00609c;">${i18n['cione-module.templates.components.eurocione.generados-grupo']}</th></#if>
						                    <th class="hide-mobile" style="color: #00609c;">${i18n['cione-module.templates.components.eurocione.generados-pventa']}</th>
						                    <#if !fideliza><th class="hide-mobile" style="color: #00609c;">${i18n['cione-module.templates.components.eurocione.fact-neta-subir-categoria']}</th></#if>
						                    <th class="hide-mobile" style="color: #00609c;">${i18n['cione-module.templates.components.eurocione.rappel-siguiente']}</th>
						                    <th class="hide-mobile" style="color: #00609c;"> ver mas</th>
						                </tr>                	
					                </thead>
									<tbody id="subtabla-${condicion.idAcuerdo}">
							      	</tbody>
							    </table>
							  </td>
							</tr>
	                	</#list>
						<tr data-id="pie" class="pie">
                			<td></td>
                			<#if !fideliza> <td class="col-sliding col-collapsed"></td></#if>
							<td class="col-sliding col-collapsed"></td>
							<td class="col-sliding col-collapsed"></td>
							<td class="col-sliding col-collapsed">*Bruto x %Devengo</td>
							<td class="col-sliding col-collapsed"></td>
							<#if !fideliza> <td class="hide-mobile">* Bruto que devenga - €Ciones Liquidados</td></#if>
							<td class="hide-mobile">*Calculados sobre el NETO que DEVENGA</td>
							<#if !fideliza> <td class="hide-mobile">*Calculados sobre el NETO que DEVENGA</td></#if>
							<td class="hide-mobile">*Calculados sobre el NETO que DEVENGA</td>
							<#if !fideliza> <td class="hide-mobile">*Calculado sobre el NETO</td></#if>
							<td class="hide-mobile"></td>
							<td class="hide-mobile"></td>
                		</tr>
                	</#if>
					<#--<tbody id="albaranes-table-data">
							<tr data-id="1" class="even">
							  <td>AV21171496</td>
							  <td>08-09-2021</td>
							  <td class="hide-mobile col-sliding col-collapsed">ABONAR</td>
							  <td class="col-sliding col-collapsed">-46,00</td>
							  <td><a href="/magnoliaAuthor/.rest/private/albaranes/v1/pdf?url=/2021/09/1111112/CIOSA_Albaranes_V_1111112_20210908_21171496.pdf"><div class="icon documento"></div></a></td>
							  <td><a href="/magnoliaAuthor/cione/private/pedidos-facturacion/pedidos/consulta-envios.html?numAlbaran=AV21171496">Ver Envío	</a></td>
							  <td><a class="masinforesponsive" href="javascript:void(0)" onclick="('AV21171496', this)">info	</a></td>
							</tr>
							<tr class="subtabla">
							  <td colspan="9">
							    <table class="table hide-mobile">
							      <thead>
							        <tr class="encabezado">
							          <th>Nº PEDIDO	</th>
							          <th>DESCRIPCIÓN	</th>
							          <th>UNIDADES	</th>
							        </tr>
							      </thead>
							      <tbody id="AV21171496">
							        
							      </tbody>
							    </table>
							  </td>
							</tr> 
							
		        		</tbody>-->
        		</tbody>
        	</table>
        </div>
        
        
        
        <#-- TABLA CONDICIONES
        <div class="panel-table">
            <table class="table">
                <thead>
                    <tr>
                    	<th>${i18n['cione-module.templates.components.eurocione.tipo']}</th>
                        <th>${i18n['cione-module.templates.components.eurocione.facturacion-bruta-grupo']}</th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.facturacion-bruta-pventa']}</th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.facturacion-neta-grupo']}</th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.porcentaje-rappel']}</th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.eurociones-generados-ano-curso-grupo']}</th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.eurociones-generados-ano-curso-pventa']}</th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.eurociones-disponibles-ano-curso']}</th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.consumo-subir-categoria']}</th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.eurocione.rappel-siguiente']}</th>                                                
                    </tr>
                </thead>
                <tbody>                	
                	<#if model.getCondiciones().getCondiciones()?size == 0>
                		<tr>
                			<td colspan="7" style="text-align:center">
                				${i18n['cione-module.global.no-records-found']}
                			</td>	                			
                		</tr>
					</#if>
                	<#list model.getCondiciones().getCondiciones() as condicion>
						<tr>							
	                    	<td>${condicion.getTipo()!}</td>
	                    	<td>${condicion.getConsumoActualGrupo()!}</td>
	                    	<td class="hide-mobile" align='right'>${condicion.getConsumoActual()!}</td>
	                    	<td class="hide-mobile" align='right'>facturacion neta grupo</td>
	                    	<td class="hide-mobile" align='right'>${condicion.getRappelActual()!}</td>
	                    	<td class="hide-mobile" align='right'>${condicion.getCionesAcumuladosGrupoView()!}</td>
	                    	<td class="hide-mobile" align='right'>${condicion.getCionesAcumuladosView()!}</td>
	                    	<td class="hide-mobile" align='right'>${condicion.getDisponibleAnioCursoView()!}</td>
	                    	<td class="hide-mobile" align='right'>${condicion.getConsumoSigCategoria()!}</td>
	                    	<td class="hide-mobile" align='right'>${condicion.getSigRappel()!}</td>
	                	</tr>	                	
					</#list>           
				</tbody>
            </table>
            <#-- <div class="comment" style="text-align:right;color:#00609c">${i18n['cione-module.templates.components.eurocione.condiciones-aviso']}</div> -->

        <#-- </div> -->
        <form class="data-panel">
        	<@cms.area name="documents3" />        	
        </form>
    </div>

</section>

<!-- MODAL PARA RESPONSIVE-->
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
	var condicionesLoaded = {};
	
	function getCondicionesInfo(idFila, element){
		if(condicionesLoaded[idFila]){
            toggleCondiciones(element);
            if (!($(".panel-table").hasClass("mobile"))) {
                verifyTest(element);
                return;
            }
        }else{
            condicionesLoaded[idFila] = true;
        }
        
        $.ajax({
            url : PATH_API + "/private/facturas/v1/info-condiciones?linea=" + idFila,
            type : "GET",                   
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            success : function(response) {            
                var listResult = [];                
                response.forEach(function(lineaCondiciones){
                    listResult.push(templateLineaCondiciones(lineaCondiciones, idFila));
                })  
                $("#subtabla-" + idFila).empty().append(listResult.join(" "));

                if ($(".panel-table").hasClass("mobile")) {
                    loadsubtable(element); 
                }
                
                toggleCondiciones(element);
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
	
    function toggleCondiciones(element){         	
    	toggleRow(element);
        if ($(".panel-table").hasClass("mobile")) {
            loadsubtable(element); 
        }
    }
    
    function templateLineaCondiciones(lineaCondiciones, idFila) {
        var html = "";
        var styletable = "col-sliding col-collapsed";
        var element = $("#" + idFila + "").find(".col-sliding");
        if (element.hasClass("col-extended")) {
        	styletable = "col-sliding col-extended";
        }
        var elementsub = $("#subtabla" + idFila + "").find(".col-sliding");
        
        html += "<tr data-id='" + idFila + "' class='even'>";
        html += "<td >" + lineaCondiciones.nombreAcuerdo + "</td>";
        <#if !fideliza> html += "<td class='" + styletable + "'>" + lineaCondiciones.factBrutaGrupo + "</td>";</#if>
        html += "<td class='" + styletable + "'>" + lineaCondiciones.factBrutaPVenta + "</td>";
        html += "<td class='" + styletable + "'>" + lineaCondiciones.porcDevengo + "</td>";
        html += "<td class='" + styletable + "'>" + lineaCondiciones.impDevengo + "</td>";
        html += "<td class='" + styletable + "'>" + lineaCondiciones.impLiquidado + "</td>";
        <#if !fideliza> html += "<td class='hide-mobile'>" + lineaCondiciones.factNetaDevenga + "</td>";</#if>
        html += "<td class='hide-mobile'></td>";
        <#if !fideliza>html += "<td class='hide-mobile'></td>";</#if>
        html += "<td class='hide-mobile'></td>";
        <#if !fideliza>html += "<td class='hide-mobile'></td>";</#if>
        html += "<td class='hide-mobile'></td>";
        html += "<td class='hide-mobile'></td>";
        html += "</tr>";

        return html;
    }
</script>