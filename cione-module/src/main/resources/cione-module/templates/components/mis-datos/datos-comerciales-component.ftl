 <section class="cmp-datoscomerciales">
    <div class="wrapper">
        <div class="title">
        	${i18n['cione-module.templates.components.datos-comerciales.datos-comerciales']}
        </div>
        <form class="data-panel inbox">
        <div class="title-mobile">
        	${i18n['cione-module.templates.components.datos-comerciales.datos-comerciales']}
        </div>
            <div class="item">
                <label>${i18n['cione-module.templates.components.datos-comerciales.grupos-precio']}</label>
                <input class="form-control" type="text" value="${model.getDatosComerciales().getGrupoPrecio()!}" disabled>
            </div>
            <div class="item">
                <label>${i18n['cione-module.templates.components.datos-comerciales.fecha-alta']}</label>
                <input class="form-control" type="text" value="${model.getDatosComerciales().getFechaAlta()!}" disabled>
            </div>
            <div class="item half">
                <label>${i18n['cione-module.templates.components.datos-comerciales.antiguedad']}</label>
                <input class="form-control" type="text" value="${model.getDatosComerciales().getAntiguedad()!}" disabled>
            </div>
            <@cms.area name="documents" />
        </form>
    </div>

</section>