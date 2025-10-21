
<section class="cmp-datoscuota">
	<div class="wrapper">
		<div class="title">
			${i18n['cione-module.templates.components.cuotas.cuotas']}</div>
		<form class="data-panel">
			<div class="item half">
				<label>${i18n['cione-module.templates.components.cuotas.ahorro-cuotas']}</label>
				<input class="form-control" type="text"
					value="${model.getCuotas().getAhorro()!}" disabled>
			</div>
		</form>
		<div class="title">${i18n['cione-module.templates.components.cuotas.cuotas-asignadas']}</div>
		<div class="panel-table">
			<table class="table">
				<thead>
					<tr>
						<th class="hide-mobile">${i18n['cione-module.templates.components.cuotas.cuota']}</th>
						<th>${i18n['cione-module.templates.components.cuotas.descripcion']}</th>
						<th class="hide-mobile">${i18n['cione-module.templates.components.cuotas.fecha-prox-factura']}</th>
						<th>${i18n['cione-module.templates.components.cuotas.importe']}</th>
						<th class="hide-mobile">${i18n['cione-module.templates.components.cuotas.fecha-fin-cuota']}</th>
					</tr>
				</thead>
				<tbody>
					<#list model.getCuotas().getCuotas() as cuota>
					<tr>
						<td class="hide-mobile">${cuota.getIdCuota()!}</td>
						<td>${cuota.getDescripcion()!}</td>
						<td class="hide-mobile">${cuota.getFechaProxFactura()!}</td>
						<td align="right">${cuota.getImporte()!}</td>
						<td class="hide-mobile">${cuota.getFechaFinCuota()!}</td>
					</tr>
					</#list>
				</tbody>
			</table>
		</div>

		<ul class="accordion">
			<li><a class="toggle" href="javascript:void(0);">
					<div class="title">
						${i18n['cione-module.templates.components.cuotas.reduccion-cuota-fija']}
						<i class="fa fa-chevron-right"> </i>
					</div>
			</a>
				<ul class="inner show" style="display: block;">
					<li>
						<div class="panel-table" style="max-width: 700px;">

							<table class="table">
								<thead>
									<tr>
									    <#list cmsfn.children(content.tabla) as column>
											<th>${column.title}</th>
										</#list>
									</tr>
								</thead>
								<tbody>
									<#list cmsfn.children(content.filas) as bloque>
										<tr>
										<#list cmsfn.children(bloque.column) as fila>
											
												<#if fila.linea1?? && fila.linea1?has_content>
													<td>
															<p>${fila.linea1!}</p>
														<#if fila.linea2?? && fila.linea2?has_content>
															<p>${fila.linea2!}</p>
														</#if>
														<#if fila.destacado?? && fila.destacado?has_content>
															<p>${fila.destacado!}</p>
														</#if>
													</td>
												</#if>
											
										</#list>
										</tr>
									</#list>
								</tbody>
							</table>

						</div>
					</li>

				</ul></li>
		</ul>


	</div>

</section>