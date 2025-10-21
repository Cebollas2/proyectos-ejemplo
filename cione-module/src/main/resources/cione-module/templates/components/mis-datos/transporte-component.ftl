  
   <section class="cmp-datostransporte">
    <div class="wrapper">
        <div class="title">
        	${i18n['cione-module.templates.components.transporte-component.transporte']}
        </div>

        <div class="panel-table">
            <table class="table">
                <thead>
                    <tr>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.transporte-component.codigo']}</th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.transporte-component.nombre']}</th>
                        <th>${i18n['cione-module.templates.components.transporte-component.direccion-envio']}</th>
                        <th>${i18n['cione-module.templates.components.transporte-component.agencia']}</th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.transporte-component.agencia']} <small>${i18n['cione-module.templates.components.transporte-component.agencia-bolsa']}</small></th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.transporte-component.agencia']} <small>${i18n['cione-module.templates.components.transporte-component.agencia-paqueteria']}</small></th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.transporte-component.entrega-sabados']}</th>
                    </tr>
                </thead>
                <tbody>
                	<#if model.getTransportes()??>
	                	<#list model.getTransportes() as transporte>
							<tr>							
		                    	<td class="hide-mobile">${transporte.getCodigo()!}</td>
		                    	<td class="hide-mobile">${transporte.getNombre()!}</td>
		                    	<td>${transporte.getDireccionEnvio()!}</td>
		                    	<td>${transporte.getAgencia()!}</td>
		                    	<td class="hide-mobile">${transporte.getAgenciaBolsa()!}</td>
		                    	<td class="hide-mobile">${transporte.getAgenciaPaqueteria()!}</td>
		                    	<td class="hide-mobile">${transporte.getEntregaSabado()!}</td>
		                	</tr>
						</#list>
					</#if>            
                </tbody>
            </table>

        </div>
   		
   		<form class="data-panel">
			<@cms.area name="documents" />
        </form>
    </div>

</section>


