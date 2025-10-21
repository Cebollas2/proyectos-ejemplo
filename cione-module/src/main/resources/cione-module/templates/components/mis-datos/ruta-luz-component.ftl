<section class="cmp-datosfundacionruta">
    <div class="wrapper">
        <div class="title">        
        	${i18n['cione-module.templates.components.ruta-luz.ruta-luz']}
        </div>
        <form class="data-panel inbox">
        <div class="title-mobile">${i18n['cione-module.templates.components.ruta-luz.ruta-luz']}</div>
            <div class="item half">
                <label>${i18n['cione-module.templates.components.ruta-luz.num-socio']}</label>
                <input class="form-control" type="text"  value="${model.getRutaLuz().getNumSocio()!}" disabled>
            </div>
            <div class="item half">
                <label>${i18n['cione-module.templates.components.ruta-luz.pago']}</label>
                <input class="form-control" type="text" value="${model.getRutaLuz().getPago()!}" disabled>
            </div>
            <div class="item half">
                <label>${i18n['cione-module.templates.components.ruta-luz.cantidad']}</label>
                <input class="form-control" type="text" value="${model.getRutaLuz().getCantidad()!}" disabled>
            </div>
            
            <@cms.area name="documents" />
                                
        </form>
    </div>

</section>
