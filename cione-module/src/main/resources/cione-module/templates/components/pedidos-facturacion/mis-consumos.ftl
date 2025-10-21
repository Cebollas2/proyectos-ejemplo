[#include "../../includes/macros/cione-utils.ftl"]

[#if model.misConsumos?has_content]
	[#assign misConsumos = model.misConsumos!]
[/#if]

<style>
   .ui-datepicker-calendar {
       display: none;
   }
</style>

<section class="cmp-mis-consumos mobile-wrapper">

	[#-- BUSCADOR --]
	<form id="form" name="form" method="post" class="formSearch">
		<ul class="accordion-mobile">
			<li>
				<a class="toggle" href="javascript:void(0);">
					<div class="title">
						${i18n['cione-module.global.btn-search']}<i
							class="fa fa-chevron-right"> </i>
					</div>
				</a>
				<ul class="inner show" style="display: block;">
					<li></li>
				</ul>
			</li>
		</ul>
		
		<div class="panel-filter">
			<div class="filter fecha order-1">
				<label>${i18n['cione-module.templates.components.mis-consumos.search.fecha-desde']}</label>
				<input class="inputfechaMY" id="fechaIni" name="fechaDesde" data-date-format="MM-YYYY" 
					type="text" autocomplete="off">
			</div>
			<div class="filter fecha order-2">
				<label>${i18n['cione-module.templates.components.mis-consumos.search.fecha-hasta']}</label>
				<input class="inputfechaMY" id="fechaFin" name="fechaHasta" data-date-format="MM-YYYY"
					type="text" value="" autocomplete="off">
			</div>
			<div class="filter fecha order-2">
				<label>${i18n['cione-module.templates.components.mis-consumos.search.fecha-hasta']}</label>
				<input type="checkbox" name="checks">
			</div>
		</div>
		<div class="panelbuttons">
			<h2 id="info-filter" class="title">
				[#if misConsumos?has_content]
					${i18n['cione-module.templates.components.mis-consumos.mis-consumos']} - [${misConsumos.minDateView!} - ${misConsumos.maxDateView!}]
				[/#if]
			</h2>
			<button class="btn-blue icon-search" type="submit"
				onclick="search(false); return false">${i18n['cione-module.global.btn-search']}</button>
		</div>
	</form>
	[#-- FIN BUSCADOR --]
	
	<div id="cmp-mis-consumos">
	[#if misConsumos?has_content]
	
		[#------------------------------ DIV VERSION DESKTOP -------------------------------]
		<div id="cmp-mis-consumos-desktop">
	
			[#-- TABLA MIS CONSUMOS --]
			<div class="panel-table">
				<table id="tableExport" class="table">
					
					[#-- CABECERA PERIODO --]
					[@templateTermHeader false /]
					
					[#-- NIVEL 1 : CABECERA CATEGORIAS --]
					[@templateSecondHeader false /]
					
					[#-- NIVEL 1 : CUERPO CATEGORIAS --]
					<tbody>
						[#list misConsumos.categorias as categoria]
						
							<tr [#if categoria?index % 2 == 0]class="odd"[/#if]>
								<td>${categoria.nombre}</td>
								<td align='right'>${categoria.cantidadAnteriorView}</td>
								<td align='right'>[#if showPrices(ctx.getUser())]${categoria.importeAnteriorView}[/#if]</td>
								<td align='right' >${categoria.cantidadView}</td>
								<td align='right' >[#if showPrices(ctx.getUser())]${categoria.importeView}[/#if]</td>
								<td align='right' >${categoria.diferenciaCantidadView}</td>
								<td align='right' >[#if showPrices(ctx.getUser())]${categoria.diferenciaImporteView}[/#if]</td>
								<td align='right' >${categoria.porcentajeCantidadView}</td>
								<td align='right' >[#if showPrices(ctx.getUser())]${categoria.porcentajeImporteView}[/#if]</td>
								<td class='masinfo hide-mobile'><a class='masinforesponsive' href='javascript:void(0)' onclick='toggleConsumo(this)'>${i18n['cione-module.templates.components.mis-consumos.more-info']}</a></td>
							</tr>
							
							[#-- NIVEL 2 --]
							<tr class='subtabla subtabla-consumo'>
								<td colspan='10'>
									<table class='table'>
										
										[#-- CABECERA PERIODO --]
										[@templateTermHeader false /]
										
										[#-- NIVEL 2 : CABECERA CONSUMOS --]
										[@templateSecondHeader false /]
										
										[#-- NIVEL 2 : CUERPO CONSUMOS --]
										<tbody id='${categoria.id?replace(".","")}'>
											
											[#list categoria.consumos as consumo]
												[#assign idDetalleConsumo = (categoria.id + "-" + consumo.id)?replace(".","")]
												
												<tr id='row-${idDetalleConsumo}' [#if consumo?index % 2 == 0]class='row-odd'[/#if]>
													<td>${consumo.nombre!}</td>
													<td align='right'>${consumo.cantidadAnteriorView}</td>
													<td align='right'>[#if showPrices(ctx.getUser())]${consumo.importeAnteriorView}[/#if]</td>
													<td align='right'>${consumo.cantidadView}</td>
													<td align='right'>[#if showPrices(ctx.getUser())]${consumo.importeView}[/#if]</td>
													<td align='right'>${consumo.diferenciaCantidadView}</td>
													<td align='right'>[#if showPrices(ctx.getUser())]${consumo.diferenciaImporteView}[/#if]</td>
													<td align='right'>${consumo.porcentajeCantidadView}</td>
													<td align='right'>[#if showPrices(ctx.getUser())]${consumo.porcentajeImporteView}[/#if]</td>
													<td class='masinfo'><a id="button-${idDetalleConsumo}" href='javascript:void(0)' onclick="toggleDetalleConsumo(this, '${idDetalleConsumo}')">+ ${i18n['cione-module.templates.components.mis-consumos.more-info']}</a></td>
												</tr>
												
												[#-- NIVEL 3 --]
												<tr id='detalle-${idDetalleConsumo}' class='subtabla subtabla-detalle' style='display:none'>
													<td colspan='10'>
														<table class='table'>
														
															[#-- CABECERA PERIODO --]
															[@templateTermHeader true /]
															
															[#-- NIVEL 3 : CABECERA DETALLE CONSUMOS --]
															[@templateSecondHeader true /]
															
															[#-- NIVEL 3 : CUERPO DETALLE CONSUMOS --]
															<tbody id='${consumo.id}'>
															
																[#list consumo.detalles as detalle]
																	<tr [#if detalle?index % 2 == 0]class='row-odd'[/#if]>
																		<td>${detalle.nombre!}</td>
																		<td align='right'>${detalle.cantidadAnteriorView}</td>
																		<td align='right'>[#if showPrices(ctx.getUser())]${detalle.importeAnteriorView}[/#if]</td>
																		<td align='right'>${detalle.cantidadView}</td>
																		<td align='right'>[#if showPrices(ctx.getUser())]${detalle.importeView}[/#if]</td>
																		<td align='right'>${detalle.diferenciaCantidadView}</td>
																		<td align='right'>[#if showPrices(ctx.getUser())]${detalle.diferenciaImporteView}[/#if]</td>
																		<td align='right'>${detalle.porcentajeCantidadView}</td>
																		<td align='right'>[#if showPrices(ctx.getUser())]${detalle.porcentajeImporteView}[/#if]</td>
																	</tr>
																[/#list]
																
																[#-- NIVEL 3 : LINEA TOTAL --]
																<tr class='total'>
																	<td>${i18n['cione-module.templates.components.mis-consumos.total']}</td>
																	<td align='right'>${consumo.cantidadAnteriorView}</td>
																	<td align='right'>[#if showPrices(ctx.getUser())]${consumo.importeAnteriorView}[/#if]</td>
																	<td align='right'>${consumo.cantidadView}</td>
																	<td align='right'>[#if showPrices(ctx.getUser())]${consumo.importeView}[/#if]</td>
																	<td align='right'>${consumo.diferenciaCantidadView}</td>
																	<td align='right'>[#if showPrices(ctx.getUser())]${consumo.diferenciaImporteView}[/#if]</td>
																	<td align='right'>${consumo.porcentajeCantidadView}</td>
																	<td align='right'>[#if showPrices(ctx.getUser())]${consumo.porcentajeImporteView}[/#if]</td>
																</tr>
																
															</tbody>
														</table>
													</td>
												</tr>
												
											[/#list]
										
											[#-- NIVEL 2 : LINEA TOTAL --]
											<tr class='total'>
												<td>${i18n['cione-module.templates.components.mis-consumos.total']}</td>
												<td align='right'>${categoria.cantidadAnteriorView}</td>
												<td align='right'>[#if showPrices(ctx.getUser())]${categoria.importeAnteriorView}[/#if]</td>
												<td align='right'>${categoria.cantidadView}</td>
												<td align='right'>[#if showPrices(ctx.getUser())]${categoria.importeView}[/#if]</td>
												<td align='right'>${categoria.diferenciaCantidadView}</td>
												<td align='right'>[#if showPrices(ctx.getUser())]${categoria.diferenciaImporteView}[/#if]</td>
												<td align='right'>${categoria.porcentajeCantidadView}</td>
												<td align='right'>[#if showPrices(ctx.getUser())]${categoria.porcentajeImporteView}[/#if]</td>
												<td></td>
											</tr>
								
										</tbody>
									</table>
								</td>
							</tr>
							
						[/#list]
						
						<tr class='total'>
							<td>${i18n['cione-module.templates.components.mis-consumos.total']}</td>
							<td align='right'>${misConsumos.cantidadTotalAnteriorView}</td>
							<td align='right'>[#if showPrices(ctx.getUser())]${misConsumos.importeTotalAnteriorView}[/#if]</td>
							<td align='right' >${misConsumos.cantidadTotalView}</td>
							<td align='right' >[#if showPrices(ctx.getUser())]${misConsumos.importeTotalView}[/#if]</td>
							<td align='right' >${misConsumos.diferenciaCantidadTotalView}</td>
							<td align='right' >[#if showPrices(ctx.getUser())]${misConsumos.diferenciaImporteTotalView}[/#if]</td>
							<td align='right' >${misConsumos.porcentajeCantidadTotalView}</td>
							<td align='right' >[#if showPrices(ctx.getUser())]${misConsumos.porcentajeImporteTotalView}[/#if]</td>
							<td></td>
						</tr>
						
					</tbody> [#-- FIN NIVEL 1 --]
				</table>
				
				[#-- Tabla a exportar --]
				<table id="tableExportAux" style="display:none">
					
					[#-- CABECERA PERIODO --]
					[@templateTermHeader false /]
					
					[#-- NIVEL 1 : CABECERA CATEGORIAS --]
					[@templateSecondHeader false /]
					
					[#-- NIVEL 1 : CUERPO CATEGORIAS --]
					<tbody>
						[#list misConsumos.categorias as categoria]
						
							<tr [#if categoria?index % 2 == 0]class="odd"[/#if]>
								<td>${categoria.nombre}</td>
								<td align='right'>${categoria.cantidadAnteriorView}</td>
								<td align='right'>[#if showPrices(ctx.getUser())]${categoria.importeAnteriorView}[/#if]</td>
								<td align='right' >${categoria.cantidadView}</td>
								<td align='right' >[#if showPrices(ctx.getUser())]${categoria.importeView}[/#if]</td>
								<td align='right' >${categoria.diferenciaCantidadView}</td>
								<td align='right' >[#if showPrices(ctx.getUser())]${categoria.diferenciaImporteView}[/#if]</td>
								<td align='right' >${categoria.porcentajeCantidadView}</td>
								<td align='right' >[#if showPrices(ctx.getUser())]${categoria.porcentajeImporteView}[/#if]</td>
								<td class='masinfo hide-mobile'><a class='masinforesponsive' href='javascript:void(0)' onclick='toggleConsumo(this)'>${i18n['cione-module.templates.components.mis-consumos.more-info']}</a></td>
							</tr>
							
						[/#list]
						
						<tr class='total'>
							<td>${i18n['cione-module.templates.components.mis-consumos.total']}</td>
							<td align='right'>${misConsumos.cantidadTotalAnteriorView}</td>
							<td align='right'>[#if showPrices(ctx.getUser())]${misConsumos.importeTotalAnteriorView}[/#if]</td>
							<td align='right' >${misConsumos.cantidadTotalView}</td>
							<td align='right' >[#if showPrices(ctx.getUser())]${misConsumos.importeTotalView}[/#if]</td>
							<td align='right' >${misConsumos.diferenciaCantidadTotalView}</td>
							<td align='right' >[#if showPrices(ctx.getUser())]${misConsumos.diferenciaImporteTotalView}[/#if]</td>
							<td align='right' >${misConsumos.porcentajeCantidadTotalView}</td>
							<td align='right' >[#if showPrices(ctx.getUser())]${misConsumos.porcentajeImporteTotalView}[/#if]</td>
							<td></td>
						</tr>
						
					</tbody> [#-- FIN NIVEL 1 --]
				</table>
			</div>
		
			<div>		
				<a id="btnExport" class="btnExport" href="javascript:exportToExcel()">${i18n['cione-module.global.btn-download']}</a>
			</div>
		 	<form id="form-export-data" method="post" action="">
		 		<input id="export-data" name="export-data"  value="" type="hidden"> 	
		 	</form>
 	
 		</div>
 		[#------------------------------ FIN DIV VERSION DESKTOP -------------------------------]
	
	
	
	
	
 		
 		[#------------------------------ DIV VERSION MOVIL -------------------------------------]
		<div id="cmp-mis-consumos-mobile">
		
			[#-- TABLA MIS CONSUMOS --]
			<div class="panel-table">
				<table class="table">
					
					[#-- CABECERA PERIODO --]
					<thead>
						<tr class="text-center">
							<th colspan="3">${i18n['cione-module.templates.components.mis-consumos.periodo']} 1</th>
						</tr>
					</thead>
					<tbody>
						<tr class="text-center tr-mis-consumos">
							<td colspan='3'>${misConsumos.minDateAnteriorView} - ${misConsumos.maxDateAnteriorView}</td>
						</tr>
					</tbody>
					
					[#-- NIVEL 1 : CABECERA CATEGORIAS --]
					<thead>
						<tr>
							<th>${i18n['cione-module.templates.components.mis-consumos.descripcion']}</th>
							<th>${i18n['cione-module.templates.components.mis-consumos.cantidad']}</th>
							<th>${i18n['cione-module.templates.components.mis-consumos.importe']}</th>
						</tr>
					</thead>
					
					[#-- NIVEL 1 : CUERPO CATEGORIAS --]
					<tbody>
						[#list misConsumos.categorias as categoria]
						
							<tr [#if categoria?index % 2 == 0]class="odd"[#else]class="even"[/#if] onclick='toggleConsumoMobile(this)'>
								<td>${categoria.nombre}</td>
								<td align='right'>${categoria.cantidadAnteriorView}</td>
								<td align='right'>[#if showPrices(ctx.getUser())]${categoria.importeAnteriorView}[/#if]</td>
							</tr>
							
							<tr class="tr-extend hide-mobile">
								<td class="td-extend" colspan="3">
									[#-- Periodo 2 --]
									<div>
										<div class="titulo-extend"><strong>${(i18n['cione-module.templates.components.mis-consumos.periodo'])?upper_case} 2</strong></div>
										<div class="">${misConsumos.minDateView} - ${misConsumos.maxDateView}</div>
									</div>
									<div>
										<div class="titulo-extend">${i18n['cione-module.templates.components.mis-consumos.descripcion']}</div>
										<div class="">${categoria.nombre}</div>
									</div>
									<div>
										<div class="titulo-extend">${i18n['cione-module.templates.components.mis-consumos.cantidad']}</div>
										<div class="">${categoria.cantidadView}</div>
									</div>
									<div>
										<div class="titulo-extend">${i18n['cione-module.templates.components.mis-consumos.importe']}</div>
										<div class="">[#if showPrices(ctx.getUser())]${categoria.importeView}[/#if]</div>
									</div>
									<hr/>
									[#-- Diferencia --]
									<div>
										<div class="titulo-extend"><strong>${(i18n['cione-module.templates.components.mis-consumos.diferencia'])?upper_case}</strong></div>
										<div class="">${i18n['cione-module.templates.components.mis-consumos.periodo']} 2 - ${i18n['cione-module.templates.components.mis-consumos.periodo']} 1</div>
									</div>
									<div>
										<div class="titulo-extend">${i18n['cione-module.templates.components.mis-consumos.descripcion']}</div>
										<div class="">${categoria.nombre}</div>
									</div>
									<div>
										<div class="titulo-extend">${i18n['cione-module.templates.components.mis-consumos.cantidad']}</div>
										<div class="">${categoria.diferenciaCantidadView}</div>
									</div>
									<div>
										<div class="titulo-extend">${i18n['cione-module.templates.components.mis-consumos.importe']}</div>
										<div class="">${categoria.diferenciaImporteView}</div>
									</div>
									<hr/>
									[#-- Porcentaje --]
									<div>
										<div class="titulo-extend"><strong>%</strong></div>
										<div class="">(${i18n['cione-module.templates.components.mis-consumos.diferencia']} X 100) / ${i18n['cione-module.templates.components.mis-consumos.periodo']} 1</div>
									</div>
									<div>
										<div class="titulo-extend">${i18n['cione-module.templates.components.mis-consumos.descripcion']}</div>
										<div class="">${categoria.nombre}</div>
									</div>
									<div>
										<div class="titulo-extend">${i18n['cione-module.templates.components.mis-consumos.cantidad']}</div>
										<div class="">${categoria.porcentajeCantidadView}</div>
									</div>
									<div>
										<div class="titulo-extend">${i18n['cione-module.templates.components.mis-consumos.importe']}</div>
										<div class="">[#if showPrices(ctx.getUser())]${categoria.porcentajeImporteView}[/#if]</div>
									</div>
									<div>
										<div class="titulo-extend"></div>
										<div class=""><a class="masinforesponsive" href="javascript:void(0)" onclick="loadsubtable(this)">info</a></div>
									</div>
								</td>
							</tr>
							
							[#-- NIVEL 2 --]
							<tr class='subtabla subtabla-consumo'>
								<td colspan='10'>
									<table class='table'>
										
										[#-- NIVEL 2 CABECERA --]
										<thead>
											<tr class='encabezado'>
												<th colspan="2"><strong>${(i18n['cione-module.templates.components.mis-consumos.periodo'])?upper_case} 1</strong></th>
												<th>${i18n['cione-module.templates.components.mis-consumos.descripcion']}</th>
												<th>${i18n['cione-module.templates.components.mis-consumos.cantidad']}</th>
												<th>${i18n['cione-module.templates.components.mis-consumos.importe']}</th>
												<th colspan="2"><strong>${(i18n['cione-module.templates.components.mis-consumos.periodo'])?upper_case} 2</strong></th>
												<th>${i18n['cione-module.templates.components.mis-consumos.descripcion']}</th>
												<th>${i18n['cione-module.templates.components.mis-consumos.cantidad']}</th>
												<th>${i18n['cione-module.templates.components.mis-consumos.importe']}</th>
												<th colspan="2"><strong>${(i18n['cione-module.templates.components.mis-consumos.diferencia'])?upper_case}</strong></th>
												<th>${i18n['cione-module.templates.components.mis-consumos.descripcion']}</th>
												<th>${i18n['cione-module.templates.components.mis-consumos.cantidad']}</th>
												<th>${i18n['cione-module.templates.components.mis-consumos.importe']}</th>
												<th colspan="2"><strong>%</strong></th>
												<th>${i18n['cione-module.templates.components.mis-consumos.descripcion']}</th>
												<th>${i18n['cione-module.templates.components.mis-consumos.cantidad']}</th>
												<th>${i18n['cione-module.templates.components.mis-consumos.importe']}</th>
											</tr>
										</thead>
										
										
										[#-- NIVEL 2 : CUERPO CONSUMOS --]
										<tbody id='${categoria.id?replace(".","")}-mobile'>
											
											[#list categoria.consumos as consumo]
												[#assign idDetalleConsumo = (categoria.id + "-" + consumo.id)?replace(".","")]
												
												<tr id='row-${idDetalleConsumo}-mobile' [#if consumo?index % 2 == 0]class='row-odd'[/#if]>
													<td>${misConsumos.minDateAnteriorView} - ${misConsumos.maxDateAnteriorView}</td>
													<td>${consumo.nombre!}</td>
													<td align='right'>${consumo.cantidadAnteriorView}</td>
													<td align='right'>[#if showPrices(ctx.getUser())]${consumo.importeAnteriorView}[/#if]</td>
													<td>${misConsumos.minDateView} - ${misConsumos.maxDateView}</td>
													<td>${consumo.nombre!}</td>
													<td align='right'>${consumo.cantidadView}</td>
													<td align='right'>[#if showPrices(ctx.getUser())]${consumo.importeView}[/#if]</td>
													<td>${i18n['cione-module.templates.components.mis-consumos.periodo']} 1 - ${i18n['cione-module.templates.components.mis-consumos.periodo']} 2</td>
													<td>${consumo.nombre!}</td>
													<td align='right'>${consumo.diferenciaCantidadView}</td>
													<td align='right'>[#if showPrices(ctx.getUser())]${consumo.diferenciaImporteView}[/#if]</td>
													<td>(${i18n['cione-module.templates.components.mis-consumos.diferencia']} X 100) / ${i18n['cione-module.templates.components.mis-consumos.periodo']} 2</td>
													<td>${consumo.nombre!}</td>
													<td align='right'>${consumo.porcentajeCantidadView}</td>
													<td align='right'>[#if showPrices(ctx.getUser())]${consumo.porcentajeImporteView}[/#if]</td>
												</tr>
												
											[/#list]
								
										</tbody>
									</table>
								</td>
							</tr>
							
						[/#list]
						
						<tr class='total tr-mis-consumos'>
							<td>${i18n['cione-module.templates.components.mis-consumos.total']}</td>
							<td align='right'>${misConsumos.cantidadTotalAnteriorView}</td>
							<td align='right'>[#if showPrices(ctx.getUser())]${misConsumos.importeTotalAnteriorView}[/#if]</td>
						</tr>
						
					</tbody> [#-- FIN NIVEL 1 --]
				</table>
			</div>
			
			[#-- BOTON EXPORTAR --]
			<div>		
				<a class="btnExport" href="javascript:exportToExcel()">${i18n['cione-module.global.btn-download']}</a>
			</div>
 	
 		</div>
 		[#------------------------------ FIN DIV VERSION MOVIL -------------------------------]
 		
 	[/#if]
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

[#-- MACROS --]
[#macro templateTermHeader isLast]
	<thead>
		<tr class="text-center">
			<th></th>
			<th colspan="2">${i18n['cione-module.templates.components.mis-consumos.periodo']} 1</th>
			<th style="display:none"></th>
			<th colspan="2">${i18n['cione-module.templates.components.mis-consumos.periodo']} 2</th>
			<th style="display:none"></th>
			<th colspan="2">${i18n['cione-module.templates.components.mis-consumos.diferencia']}</th>
			<th style="display:none"></th>
			<th colspan="2">%</th>
			<th style="display:none"></th>
			[#if !isLast]
				<th></th>
			[/#if]
		</tr>
	</thead>
	<tbody id="table-header">
		<tr class='text-center'>
			<td></td>
			<td colspan='2'>${misConsumos.minDateAnteriorView} - ${misConsumos.maxDateAnteriorView}</td>
			<th style="display:none"></th>
			<td colspan='2'>${misConsumos.minDateView} - ${misConsumos.maxDateView}</td>
			<th style="display:none"></th>
			<td colspan='2'>${i18n['cione-module.templates.components.mis-consumos.periodo']} 2 - ${i18n['cione-module.templates.components.mis-consumos.periodo']} 1</td>
			<th style="display:none"></th>
			<td colspan='2'>(${i18n['cione-module.templates.components.mis-consumos.diferencia']} X 100) / ${i18n['cione-module.templates.components.mis-consumos.periodo']} 1</td>
			<th style="display:none"></th>
		</tr>
	</tbody>
[/#macro]

[#macro templateSecondHeader isLast]
	<thead>
		<tr>
			<th>${i18n['cione-module.templates.components.mis-consumos.descripcion']}</th>
			<th>${i18n['cione-module.templates.components.mis-consumos.cantidad']}</th>
			<th>${i18n['cione-module.templates.components.mis-consumos.importe']}</th>
			<th>${i18n['cione-module.templates.components.mis-consumos.cantidad']}</th>
			<th>${i18n['cione-module.templates.components.mis-consumos.importe']}</th>
			<th>${i18n['cione-module.templates.components.mis-consumos.cantidad']}</th>
			<th>${i18n['cione-module.templates.components.mis-consumos.importe']}</th>
			<th>${i18n['cione-module.templates.components.mis-consumos.cantidad']}</th>
			<th>${i18n['cione-module.templates.components.mis-consumos.importe']}</th>
			[#if !isLast]
				<th></th>
			[/#if]
		</tr>
	</thead>
[/#macro]

[#-- SCRIPTS --]
<script>

	function search(isFirstTime){
		$("#loading").show();
		$.ajax({
			url: "${ctx.contextPath}${content.@handle}",
			data: {"isFirstTime": isFirstTime, "fechaDesde" : $("#fechaIni").val(), "fechaHasta" : $("#fechaFin").val()},
			success: function(response){
			  	$("#cmp-mis-consumos").html($(response).find("#cmp-mis-consumos").html());
			  	$("#info-filter").html($(response).find("#info-filter").html());
			},
			complete: function(){
				$("#loading").hide();
			}
		});
	}
	
	function toggleConsumo(element) {
		toggleRow(element);
	}
	
	function toggleConsumoMobile(element) {
	
		if($(element).hasClass("active"))
			$(element).removeClass("active");
		else
			$(element).addClass("active");
	
		var next = $(element).next();
		if(next.hasClass("hide-mobile"))
			next.removeClass("hide-mobile");
		else
			next.addClass("hide-mobile");
	}
	
	function toggleDetalleConsumo(button, id) {
		$("#detalle-" + id).toggle();
		var row = $("#row-" + id);
		var button = $("#button-" + id);
		
		if(button.html() == "+ ${i18n['cione-module.templates.components.mis-consumos.more-info']}"){
			button.html("- ${i18n['cione-module.templates.components.mis-consumos.more-info']}");
			row.addClass( "active" );			
		} else {
			button.html("+ ${i18n['cione-module.templates.components.mis-consumos.more-info']}");
			row.removeClass( "active" );
		}
	}
	
	function initPage() {
	
		$("#fechaIni").val("01-" + new Date().getFullYear());		
		
		search(true);
		
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
		//OVERRIDE FUNCTION
		exportToExcel = function(){
			var url = PATH_API + "/private/export/v1/consumo?fechaIni="+$("#fechaIni").val()+"&fechaFin="+$("#fechaFin").val();		
			$("#form-export-data")[0].action=url;
			$("#export-data").val(JSON.stringify(""));
			$("#form-export-data")[0].submit();		
		}	
	}
	
</script>