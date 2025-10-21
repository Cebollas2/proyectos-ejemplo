 <section class="cmp-datosgestores">
    <div class="wrapper">
        <div class="title">${i18n['cione-module.templates.components.gestores-cuenta.gestores-cuenta']}</div>
        <div class="panel-table">
            <table class="table">
                <thead>
                    <tr>
                        <th>${i18n['cione-module.templates.components.gestores-cuenta.nombre-apellidos']}</th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.gestores-cuenta.email']}</th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.gestores-cuenta.telefono']}</th>
                        <th>${i18n['cione-module.templates.components.gestores-cuenta.actividad']}</th>
                    </tr>
                </thead>
                <tbody>
                	<#list model.getGestoresCuenta() as gestor>
						<tr>
	                    	<td>${gestor.getNombreCompleto()!}</td>
	                    	<td class="hide-mobile">${gestor.getEmail()!}</td>
	                    	<td class="hide-mobile">${gestor.getTelefono()!}</td>
	                    	<td>${gestor.getActividad()!}</th>
	                	</tr>	                	
					</#list>                                
                </tbody>
            </table>
        </div>
    </div>
</section>