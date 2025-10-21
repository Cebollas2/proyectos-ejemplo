<section class="cmp-datoslooktic">
    <div class="wrapper">
        <div class="title">        
        	${i18n['cione-module.templates.components.looktic.looktic']}
        </div>
        <form class="data-panel">
            <div class="item half">
                <label>${i18n['cione-module.templates.components.looktic.id-socio']}</label>
                <input class="form-control" type="text" value="${ctx.user.name}" disabled>
            </div>
        </form>

        <div class="panel-table">
            <table class="table">
                <thead>
                    <tr>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.looktic.codigo']}</th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.looktic.nombre']}</th>
                        <th>${i18n['cione-module.templates.components.looktic.direccion-envio']}</th>
                        <th>${i18n['cione-module.templates.components.looktic.agencia']}</th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.looktic.email']} <small>(${i18n['cione-module.templates.components.looktic.usuario-dashboard']})</small></th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.looktic.nivel-adhesion']}</th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.looktic.fecha-adhesion']}</th>
                    </tr>
                </thead>
                <tbody>
                	<#list model.getLooktics() as looktic>
						<tr>							
	                    	<td class="hide-mobile">${looktic.getCodigo()!}</td>
	                    	<td class="hide-mobile">${looktic.getNombre()!}</td>
	                    	<td>${looktic.getDireccionEnvio()!}</td>
	                    	<td>${looktic.getAgencia()!}</td>
	                    	<td class="hide-mobile">${looktic.getEmailDashboard()!}</td>
	                    	<td class="hide-mobile">${looktic.getNivelAdhesion()!}</td>
	                    	<td class="hide-mobile">${looktic.getFechaAdhesion()!}</td>
	                	</tr>	                	
					</#list>        
                </tbody>
            </table>
        </div>
        
        <div style="background: #00609c;text-align:center;margin-top:40px;">
        	<img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/pie-looktic.jpg">
        </div>

		<form class="data-panel">
			<@cms.area name="documents" />
        </form>       
    </div>

</section>
