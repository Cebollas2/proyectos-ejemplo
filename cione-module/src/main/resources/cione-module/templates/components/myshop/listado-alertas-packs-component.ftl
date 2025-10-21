[#if !cmsfn.editMode]

[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]
[#assign href = cmsfn.link("website", content.internalLink!)!]

[#assign listadoPacks = model.listPacksFilter!]
<section class="b2b-form-alertas-packs">

	<p id="hasPack" class="b2b-form-subtitle" >[#if listadoPacks?size > 0]${i18n['cione-module.templates.components.listado-alertas-packs-component.title']} [#else] ${i18n['cione-module.templates.components.listado-alertas-packs-component.title-no-packs']} [/#if]</p>
	
	<div class="panel-table-alerts mt-5">
        <table class="table" id="packstable" style="table-layout: fixed;">
            <thead>
                <tr>
                    <th>${i18n['cione-module.templates.components.listado-alertas-packs-component.producto']}</th>
                    <th>${i18n['cione-module.templates.components.listado-alertas-packs-component.descripcion']}</th>
                    <th></th>
                </tr>
            </thead>
            <tbody class="nozebra">
				[#list listadoPacks as pack]
	            <tr id="${pack.sku!}">
	            	[#assign imagen = resourcesURL + "/img/myshop/common/oops.jpg"]
	            	[#if pack.getImages()?? && pack.getImages()?has_content]
	            		[#assign imagen = pack.getImages()[0].getUrl()]
	            	[/#if]
	            	<td class="container"><a style="margin: 20px auto;" href="${href}?skuPackMaster=${pack.sku!}"> <img class="b2b-detalle-miniaturas-imagen" src="${imagen}" alt=""></a></td>
	            	<td>${pack.descripcionPack!}</td>
	            	<td>
	            		<div class="b2b-button-wrapper">
			                <button type="button" class="b2b-button b2b-button-filter" onclick="removePackGenerico('${pack.sku!}', '${pack.idCarritoOculto!}'); return false">
			                    ${i18n['cione-module.templates.components.listado-alertas-packs-component.eliminar']}	
			                </button>
			                <button type="button" class="b2b-button b2b-button-filter" onclick="redirectPack('${pack.sku!}'); return false">
			                    ${i18n['cione-module.templates.components.listado-alertas-packs-component.ver']}
			                </button>
			            </div>
	            	</td>
	            </tr>
	            [/#list]
            </tbody>
        </table>
	</div>
 
</section>

<script>

	function removePackGenerico(skuPackMaster, idCarritoOculto) {
		$(this).attr("disabled", "disabled");
		$("#loading").show();
		
		var definitionName = $("#formDetalleProducto input[name=definitionName]").val();
		var connectionName = $("#formDetalleProducto input[name=connectionName]").val();
	
    	var filter = JSON.stringify({
        	"skuPackMaster": skuPackMaster,
        	"idCarritoOculto": idCarritoOculto,
        	"definitionName": definitionName,
        	"connectionName": connectionName
    	});
		
		$.ajax({
            url : "${ctx.contextPath}/.rest/private/carrito/v1/carts-deleteAllPack",
            type : "POST",
            data : filter,
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            success : function(response) {
            	$("#"+skuPackMaster).remove();
            	if ( $("#packstable tbody tr").length == 0 ) {
            		$("#alertsLink").remove();
            		$("#hasPack").text("${i18n['cione-module.templates.components.listado-alertas-packs-component.title-no-packs']}");
            	}
            	
            },
            error : function(response) {
                console.log("error al borrar el pack");
            },
            complete : function(response) {
            	
            }
		});
		$("#loading").hide();
	}
	
	function redirectPack(sku) {
		window.location = "${href}?skuPackMaster="+sku;
	}
	
	
</script>



[/#if]