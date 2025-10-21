<section class="cmp-actualidad">
    <h2 class="title">${i18n['cione-module.templates.components.actualidad.actualidad']}</h2>
    <p>${i18n['cione-module.templates.components.actualidad.descripcion']}</p>
    <div class="wrapper-actualidad">
        <ul>
            <li>
                <div class="wrapper-img">
                    <img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/actividad/total-pedidos.svg" alt="icono">
                </div>
                <span class="counter"  data-count="${model.getKpis().getPorcPedidosPlazo()!}">0</span>
                <p>${i18n['cione-module.templates.components.actualidad.pedidos-en-plazo']}</p>
            </li>
            <#-- 
         	<li>
                <div class="wrapper-img">
                    <img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/actividad/lineas-pedidos.svg" alt="icono">
                </div>
                <span class="counter" data-count="${model.getKpis().getPorcPedidosPendientes()!}">0</span>
                <p>${i18n['cione-module.templates.components.actualidad.pedidos-servidos']}</p>
            </li>
            -->
            <li>
                <div class="wrapper-img">
                    <img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/actividad/lineas-pedidos-servidos.svg" alt="icono">
                </div>
                <span class="counter" data-count="${model.getKpis().getPorcInteracciones()!}">0</span>
                <p>${i18n['cione-module.templates.components.actualidad.pedidos-sin-incidencias']}</p>
            </li>
            <#-- 
            <li>
                <div class="wrapper-img">
                    <img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/actividad/albaranes.svg" alt="icono">
                </div>
                <span class="counter"data-count="${model.getKpis().getNumRepuestosServidos()!}">0</span>
                <p>${i18n['cione-module.templates.components.actualidad.repuestos-servidos']}</p>
            </li>
             <li>
                <div class="wrapper-img">
                    <img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/actividad/expediciones.svg" alt="icono">
                </div>
                <span class="counter" data-count="${model.getKpis().getPorcRepuestosAlter()!}">0</span>
                <p>${i18n['cione-module.templates.components.actualidad.alternativas-repuestos-servidos']}</p>
            </li>
            -->
        </ul>
    </div>
</section>


 <script>
	function initPage(){ 	
		initCounters();
	}
	
	function initCounters(){
		$('.counter').each(function () {
		    var $this = $(this),
		        countTo = $this.attr('data-count');

		    $({ countNum: $this.text() }).animate({
		        countNum: countTo
		    },

		        {

		            duration: 3000,
		            easing: 'linear',
		            step: function () {
		                $this.text(Math.floor(this.countNum));
		            },
		            complete: function () {
		                $this.text(this.countNum.toLocaleString());
		                //alert('finished');
		            }
		        });
		});
	}
	
 </script>
 