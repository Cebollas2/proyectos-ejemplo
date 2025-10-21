<script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/Chart-2.5.0.min.js" defer></script>
<script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/dashboard.js"></script>
[#if !cmsfn.editMode]
[#assign dashBoardInfo = model.dashBoardDTO] 

<link rel="stylesheet" href="${ctx.contextPath}/.resources/cione-theme/webresources/css/dashboard.css" media="all">
<div class="container__body">
	<main class="wrapper__dashboard">
    	<h1>${model.getDashBoardName()!}</h1>
		<section class="section__row-status">
			<article class="container__current-state">
				
            	<h2>${i18n.get('cione-module.dashboard-component.dashboard.nivelActual')}: ${dashBoardInfo.actualLevel!}</h2>
            	<h3>${i18n.get('cione-module.dashboard-component.dashboard.consumos-nivelActual')}</h3>
            	<div class="section__graphic-cards 111">
            		[#if dashBoardInfo.consumptionActualLevelOwnProduct.name?? && dashBoardInfo.consumptionActualLevelOwnProduct.name?has_content]
	              		<div class="container__graphic container__graphic--smalll container__graphic-orange">
	                		<p class="title__graphic">${dashBoardInfo.consumptionActualLevelOwnProduct.name!}</p>
	                		<div class="container__outside-canvas container__outside-canvas--small">
		                  		<canvas id="consumptionActualLevelOwnProduct"></canvas>
		                  		<span class="text__graphic-value">${dashBoardInfo.consumptionActualLevelOwnProduct.descuentoActual!} %</span>
	                		</div>
	              		</div>
	              		<script>
	              			const chartConsumptionActualLevelOwnProduct = {
	              				labels: ${dashBoardInfo.consumptionActualLevelOwnProduct.getLabelsJson()},
		                		datasets: ${dashBoardInfo.consumptionActualLevelOwnProduct.getDataSetJson()}
				            };
	              		</script>
	              	[#else]
	              		<div class="container__graphic container__graphic--smalll container__graphic-orange">
	                		<p class="title__graphic">  </p>
	                		<div class="container__outside-canvas container__outside-canvas--small">
		                  		<canvas id="consumptionActualLevelOwnProduct"></canvas>
		                  		<span class="text__graphic-value"></span>
	                		</div>
	              		</div>
	              		<script>
	              			const chartConsumptionActualLevelOwnProduct = {
	              				labels: ${model.datosInicializadosConsumosLabels()!},
		                		datasets: ${model.datosInicializadosConsumosDataSet()!}
				            };
	              		</script>
              		[/#if]
              		
              		[#if dashBoardInfo.consumptionActualLevelSupplier.name?? && dashBoardInfo.consumptionActualLevelSupplier.name?has_content]
		              	<div class="container__graphic container__graphic--smalll container__graphic-orange">
			                <p class="title__graphic">${dashBoardInfo.consumptionActualLevelSupplier.name!}</p>
			                <div class="container__outside-canvas container__outside-canvas--small">
			                	<canvas id="consumptionActualLevelSupplier"></canvas>
			                  	<span class="text__graphic-value">${dashBoardInfo.consumptionActualLevelSupplier.descuentoActual!} %</span>
			                </div>
		              	</div>
		              	
		              	<script>
	              			const chartConsumptionActualLevelSupplier = {
	              				labels: ${dashBoardInfo.consumptionActualLevelSupplier.getLabelsJson()},
		                		datasets: ${dashBoardInfo.consumptionActualLevelSupplier.getDataSetJson()}
				            };
	              		</script>
	              	[#else]
	              		<div class="container__graphic container__graphic--smalll container__graphic-orange">
	                		<p class="title__graphic">  </p>
	                		<div class="container__outside-canvas container__outside-canvas--small">
		                  		<canvas id="consumptionActualLevelSupplier"></canvas>
		                  		<span class="text__graphic-value"></span>
	                		</div>
	              		</div>
	              		<script>
	              			const chartConsumptionActualLevelSupplier = {
	              				labels: ${model.datosInicializadosConsumosLabels()!},
		                		datasets: ${model.datosInicializadosConsumosDataSet()!}
				            };
	              		</script>
              		[/#if]
            	</div>
	            
          	</article>
          	<article class="container__next-state">
          		
            	<h2>${i18n.get('cione-module.dashboard-component.dashboard.siguienteNivel')}: ${dashBoardInfo.nextLevel!}</h2>
            	<h3>${i18n.get('cione-module.dashboard-component.dashboard.consumos-siguienteNivel')}</h3>
            	<div class="section__graphic-cards">
            		[#if dashBoardInfo.consumptionNextLevelOwnProduct.name?? && dashBoardInfo.consumptionNextLevelOwnProduct.name?has_content]
	              		<div class="container__graphic container__graphic--smalll container__graphic-white container__graphic-flex1">
	                		<p class="title__graphic">${dashBoardInfo.consumptionNextLevelOwnProduct.name!}</p>
		                	<div class="container__outside-canvas container__outside-canvas--small">
		                  		<canvas id="consumptionNextLevelOwnProduct"></canvas>
		                  		<span class="text__graphic-value">${dashBoardInfo.consumptionNextLevelOwnProduct.sigDescuento!} %</span>
		                	</div>
		              	</div>
		              	<script>
	              			const chartConsumptionNextLevelOwnProduct = {
	              				labels: ${dashBoardInfo.consumptionNextLevelOwnProduct.getLabelsJson()},
		                		datasets: ${dashBoardInfo.consumptionNextLevelOwnProduct.getDataSetJson()}
				            };
	              		</script>
	              [#else]
	              		<div class="container__graphic container__graphic--smalll container__graphic-orange">
	                		<p class="title__graphic">  </p>
	                		<div class="container__outside-canvas container__outside-canvas--small">
		                  		<canvas id="consumptionNextLevelOwnProduct"></canvas>
		                  		<span class="text__graphic-value"></span>
	                		</div>
	              		</div>
	              		<script>
	              			const chartConsumptionNextLevelOwnProduct = {
	              				labels: ${model.datosInicializadosConsumosLabels()!},
		                		datasets: ${model.datosInicializadosConsumosDataSet()!}
				            };
	              		</script>
              	  [/#if]
	              [#if dashBoardInfo.consumptionNextLevelSupplier.name?? && dashBoardInfo.consumptionNextLevelSupplier.name?has_content]
		              	<div class="container__graphic container__graphic--smalll container__graphic-white container__graphic-flex1">
		                	<p class="title__graphic">${dashBoardInfo.consumptionNextLevelSupplier.name!}</p>
		                	<div class="container__outside-canvas container__outside-canvas--small">
		                  		<canvas id="consumptionNextLevelSupplier"></canvas>
		                  		<span class="text__graphic-value">${dashBoardInfo.consumptionNextLevelSupplier.sigDescuento!}  %</span>
		                	</div>
		              	</div>
		              	<script>
	              			const chartConsumptionNextLevelSupplier = {
	              				labels: ${dashBoardInfo.consumptionNextLevelSupplier.getLabelsJson()},
		                		datasets: ${dashBoardInfo.consumptionNextLevelSupplier.getDataSetJson()}
				            };
	              		</script>
              	  [#else]
	              		<div class="container__graphic container__graphic--smalll container__graphic-orange">
	                		<p class="title__graphic">  </p>
	                		<div class="container__outside-canvas container__outside-canvas--small">
		                  		<canvas id="consumptionNextLevelSupplier"></canvas>
		                  		<span class="text__graphic-value"></span>
	                		</div>
	              		</div>
	              		<script>
	              			const chartConsumptionNextLevelSupplier = {
	              				labels: ${model.datosInicializadosConsumosLabels()!},
		                		datasets: ${model.datosInicializadosConsumosDataSet()!}
				            };
	              		</script>
              	  [/#if]
              		
	              	<div class="container__information">
	                	<p class="text__number">${dashBoardInfo.eurocioneDisponibles!}€</p>
	                	<p class="text__information">${i18n.get('cione-module.dashboard-component.dashboard.disponibles')}</p>
	              	</div>
	              	
            	</div>
          	</article>
        </section>

        <section class="section__big-graphic">
        	<div class="container__graphic container__graphic--big container__graphic-white">
	            <p class="title__graphic">Mi consumo mensual</p>
	            <div class="container__outside-canvas">
	              <canvas id="monthly"></canvas>
	            </div>
          	</div>
          	<script>
	          	const chartMonthly = {
	                labels: ${dashBoardInfo.monthly.getLabelsJson()},
	                datasets: ${dashBoardInfo.monthly.getDataSetJson()}
	            };
          	</script>
          	<div class="container__graphic container__graphic--big container__graphic-white">
	            <p class="title__graphic"> ${i18n.get('cione-module.dashboard-component.dashboard.consumo-acumulado-anual')} ${model.getMonth()}) </p>
	            <div class="container__outside-canvas">
	              <canvas id="annualConsumption"></canvas>
	            </div>
	        </div>
	        <script>
	          	const chartAnnualConsumption = {
	                labels: ${dashBoardInfo.annualConsumption.getLabelsJson()},
	                datasets: ${dashBoardInfo.annualConsumption.getDataSetJson()}
	            };
          	</script>
          	<div class="container__graphic container__graphic--big container__graphic-white">
            	<p class="title__graphic">${i18n.get('cione-module.dashboard-component.dashboard.consumo-ultimos-doce-meses')}</p>
	            <div class="container__outside-canvas">
	              <canvas id="lastTwelveMonthsConsumption"></canvas>
	            </div>
          	</div>
          	<script>
	          	const chartLastTwelveMonthsConsumption = {
	                labels: ${dashBoardInfo.lastTwelveMonthsConsumption.getLabelsJson()},
	                datasets: ${dashBoardInfo.lastTwelveMonthsConsumption.getDataSetJson()}
	            };
          	</script>
          	
          	<div class="container__graphic container__graphic--big container__graphic-white">
	            <p class="title__graphic">${i18n.get('cione-module.dashboard-component.dashboard.consumo-tipo-anio-actual')} ${dashBoardInfo.consumptionByType.name!}</p>
	            <div class="container__outside-canvas">
	              <canvas id="consumptionByType"></canvas>
	            </div>
          	</div>
          	<script>
	          	const chartConsumptionByType = {
	                labels: ${dashBoardInfo.consumptionByType.getLabelsJson()},
	                datasets: ${dashBoardInfo.consumptionByType.getDataSetJson()}
	            };
          	</script>
          	
        </section>

        <section class="section__horizontal-graphic">
        	<div class="container__graphic container__graphic--big container__graphic-white">
	          	<p class="title__graphic">${i18n.get('cione-module.dashboard-component.dashboard.mis-ahorros')}</p>
	            <div class="container__outside-canvas">
	              <canvas id="mySavings"></canvas>
	            </div>
        	</div>
        	<script>
	          	const chartMySavings = {
	                labels: ${dashBoardInfo.mySavings.getLabelsJson()},
	                datasets: ${dashBoardInfo.mySavings.getDataSetJson()}
	            };
          	</script>
        	
        </section>
		
        <script>
			var consumptionMap = [];

		</script>
        <section class="section__row-categories">
	        <div class="section__graphic-cards">
		        [#list dashBoardInfo.consumptionCharts as consumption]
	            	<div class="container__graphic container__graphic-white">
	              		<p class="title__graphic title__glasses">
		                	${consumption.name}
	              		</p>
						<div class="container__outside-canvas">
		                	<canvas id="${consumption.id}"></canvas>
		              	</div>
	              		<p class="text__prox-rappel">Prox. Rappel: ${consumption.proxRappel}</p>
	            	</div>
			        <script>
				        //START GRAPH Monturas
				        consumptionMap.push('${consumption.id}');
			            
			            var chart${consumption.id} = {
			                labels: ${consumption.getLabelsJson()},
			                datasets: ${consumption.getDataSetJson()}
			            };
			            //END GRAPH Monturas
		        	</script>
		        [/#list]
		        
        	</div>
		</section>
        
[/#if]