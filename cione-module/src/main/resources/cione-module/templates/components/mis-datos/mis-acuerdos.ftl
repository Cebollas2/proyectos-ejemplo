 <style>
 	.herramienta-no-activa{
 		display: none;
 	}
 	.bono-no-activo{
 		display: none;
 	}
 </style>
 
 <section class="cmp-datosmisacuerdos">
    <div class="wrapper">
    
        <div class="title">${i18n['cione-module.templates.components.mis-acuerdos.mis-acuerdos']}</div>
        <form class="data-panel">
            <div class="item half">
                <label>${i18n['cione-module.templates.components.mis-acuerdos.audiologia']}</label>
                <input class="form-control" type="text" value="${model.getAcuerdos().getAudiologia()?string('SI','NO')}" disabled>
            </div>            
            <div class="item half">
                <label>${i18n['cione-module.templates.components.mis-acuerdos.connecta']}</label>
                <input class="form-control" type="text" value="${model.getAcuerdos().getConnecta()?string('SI','NO')}" disabled>
            </div>
            <div class="item">
                <div class="title-check">${i18n['cione-module.templates.components.mis-acuerdos.herramientas']}</div>
                <input class="styled-checkbox" id="checkbox-herramientas-activas" type="checkbox" value="" onclick="showHerramientasActivas(this)" checked>
                <label for="checkbox-herramientas-activas">${i18n['cione-module.templates.components.mis-acuerdos.herramientas-activas']}</label>

                <input class="styled-checkbox" id="checkbox-herramientas-todas" type="checkbox" value="" onclick="showHerramientasTodas(this)">
                <label for="checkbox-herramientas-todas">${i18n['cione-module.templates.components.mis-acuerdos.herramientas-todas']}</label>
            </div>
        </form>
        <div class="panel-table">
            <table class="table">
                <thead>
                    <tr>
                        <th>${i18n['cione-module.templates.components.mis-acuerdos.herramienta']}</th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.mis-acuerdos.activo']}</th>
                        <th>${i18n['cione-module.templates.components.mis-acuerdos.compromiso-inicial']}</th>
                        <th>${i18n['cione-module.templates.components.mis-acuerdos.amortizado']}</th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.mis-acuerdos.fecha-inicio']}</th>
                        <th class="hide-mobile">${i18n['cione-module.templates.components.mis-acuerdos.fecha-fin']}</th>
                    </tr>
                </thead>
                <tbody>
                    <#list model.getHerramientas().getHerramientas() as herramienta>
                    	<#assign herramientaClass = herramienta.getActivo()?string('','herramienta-no-activa')>
						<tr class=${herramientaClass}>							
	                    	<td>${herramienta.getHerramienta()!}</td>
	                    	<td class="hide-mobile">${herramienta.getActivo()?string('SI','NO')}</td>
	                    	<td>${herramienta.getCompromiso()!}</td>
	                    	<td align="right">${herramienta.getConsumido()!}</td>
	                    	<td class="hide-mobile">${herramienta.getFechaIni()!}</td>
	                    	<td class="hide-mobile">${herramienta.getFechaFin()!}</td>
	                	</tr>	                	
					</#list>           
                </tbody>
            </table>

        </div>
    </div>
    <div class="wrapper">
            
            <form class="data-panel">
              
                <div class="item">
                    <div class="title-check">${i18n['cione-module.templates.components.mis-acuerdos.bonos-certificados']}</div>
                    <input class="styled-checkbox" id="checkbox-bonos-activos" type="checkbox" value="" checked onclick="showBonosActivos(this)">
                    <label for="checkbox-bonos-activos">${i18n['cione-module.templates.components.mis-acuerdos.bonos-certificados-activos']}</label>
    
                    <input class="styled-checkbox" id="checkbox-bonos-todos" type="checkbox" value="" onclick="showBonosTodos(this)">
                    <label for="checkbox-bonos-todos">${i18n['cione-module.templates.components.mis-acuerdos.bonos-certificados-todos']}</label>
                </div>
            </form>
            <div class="panel-table">
                <table class="table">
                    <thead>
                        <tr>
	                        <th>${i18n['cione-module.templates.components.mis-acuerdos.tipo']}</th>
	                        <th class="hide-mobile">${i18n['cione-module.templates.components.mis-acuerdos.activo']}</th>
	                        <th>${i18n['cione-module.templates.components.mis-acuerdos.importe-inicial']}</th>
	                        <th>${i18n['cione-module.templates.components.mis-acuerdos.saldo']}</th>
	                        <th class="hide-mobile">${i18n['cione-module.templates.components.mis-acuerdos.fecha-inicio']}</th>
	                        <th class="hide-mobile">${i18n['cione-module.templates.components.mis-acuerdos.fecha-fin']}</th>
                        </tr>
                    </thead>
                    <tbody>
	                      <#list model.getBonos().getBonos() as bono>
							<#assign bonoClass = bono.getActivo()?string('','bono-no-activo')>
							<tr class=${bonoClass}>												
		                    	<td>${bono.getTipo()!}</td>
		                    	<td class="hide-mobile">${bono.getActivo()?string('SI','NO')}</td>
		                    	<td align="right">${bono.getImporteInicial()!}</td>
		                    	<td align="right">${bono.getSaldo()!}</td>
		                    	<td class="hide-mobile">${bono.getFechaIni()!}</td>
		                    	<td class="hide-mobile">${bono.getFechaFin()!}</td>
		                	</tr>	                	
						</#list>
                    </tbody>
                </table>
    
            </div>
            <form class="data-panel">
				<@cms.area name="documents" />
        	</form>                   
        </div>
</section>

<script>
	function showHerramientasActivas(checkbox){
		if(checkbox.checked){
			$("#checkbox-herramientas-todas")[0].checked = false;
			$(".herramienta-no-activa").hide();
			retailerZebra();
		}else {
			checkbox.checked = true;
		}		
	}
	
	function showHerramientasTodas(checkbox){
		if(checkbox.checked){
			$("#checkbox-herramientas-activas")[0].checked = false;
			$(".herramienta-no-activa").show();
			retailerZebra();
		}else {
			checkbox.checked = true;
		}		
	}
	
	
	function showBonosActivos(checkbox){
		if(checkbox.checked){
			$("#checkbox-bonos-todos")[0].checked = false;
			$(".bono-no-activo").hide();
			retailerZebra();
		}else {
			checkbox.checked = true;
		}		
	}
	
	function showBonosTodos(checkbox){
		if(checkbox.checked){
			$("#checkbox-bonos-activos")[0].checked = false;
			$(".bono-no-activo").show();
			retailerZebra();
		}else {
			checkbox.checked = true;
		}		
	}
	
</script>