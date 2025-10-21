  [#assign formaPago = model.getFormaPago()]
   <section class="cmp-datosformapago">
    <div class="wrapper">
        <div class="title">${i18n['cione-module.templates.components.formas-pago.formas-pago']}</div>
        <form class="data-panel inbox">
        <div class="title-mobile">${i18n['cione-module.templates.components.formas-pago.formas-pago']}</div>
            <div class="item half">
                <label>${i18n['cione-module.templates.components.formas-pago.razon-social']}</label>
                <input class="form-control" type="text" value="${formaPago.getRazonSocial()!}" disabled>
            </div>
            <div class="item half">
                <label>${i18n['cione-module.templates.components.formas-pago.cif-nif']}</label>
                <input class="form-control" type="text" value="${formaPago.getNifcif()!}" disabled>
            </div>
            <div class="item">
                <label>${i18n['cione-module.templates.components.formas-pago.num-cuenta']}</label>
                <input class="form-control" type="text" value="${formaPago.getCuenta()!}" disabled>
            </div>
            <div class="item half">
                <label>${i18n['cione-module.templates.components.formas-pago.recargo-equivalencia']}</label>
                <input class="form-control" type="text" value="${formaPago.getRegEquivalencia()!}" disabled>
            </div> 
            <div class="item half">
                <label>${i18n['cione-module.templates.components.formas-pago.plazo-pago']}</label>
                <input class="form-control" type="text" value="${formaPago.getPlazoPago()!}" disabled>
            </div>                                    
            [@cms.area name="documents" /]         
        </form>
    </div>
  
</section>
