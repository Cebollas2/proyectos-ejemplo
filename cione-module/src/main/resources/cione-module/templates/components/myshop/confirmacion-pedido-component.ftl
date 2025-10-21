[#include "../../includes/macros/cione-utils-impersonate.ftl"]
[#assign uuid = model.getUuid()!]
[#assign username = model.getUserName()!]

[#assign title = ""]
[#assign confirmationText = ""]

[#if cmsfn.children(content)?has_content]
	[#list cmsfn.children(content) as personalizationContent]
		[#assign hasPermissions = false]
		[#if personalizationContent.permisos?? && personalizationContent.permisos?has_content]
			[#assign field = personalizationContent.permisos.field!]
			[#switch field]
				[#case "roles"]
					[#if model.hasPermissionsRoles(personalizationContent.permisos.roles!)]
						[#assign hasPermissions = true]
					[/#if]
					[#break]
					
				
				[#case "campaing"]
					[#if model.hasPermissionsCampana(personalizationContent.permisos.campaing!)]
						[#assign hasPermissions = true]
					[/#if]
					[#break]
					
				[#default]
    				[#break]
    		[/#switch]
    		
    		[#if hasPermissions]
    			[#assign title = personalizationContent.titulo!]
    			[#assign confirmationText = personalizationContent]
    		[/#if]
    	
		[/#if]
	[/#list]
[/#if]

<section class="b2b-producto-enviado">
    <h2 class="b2b-h2">${i18n['cione-module.templates.myshop.listado-productos-carrito-component.CARRITO']}</h2>
	
    <div class="b2b-producto-enviado-block">
    
    <div id="pedido-ok">
    <p class="b2b-producto-enviado-text">${title}</p>
    <div class="b2b-msg-wrapper">
        <span>${i18n['cione-module.templates.myshop.confirmacion-pedido-component.recibir']}:</span>
        <span class="b2b-producto-enviado-strong" id="dirInfo"></span>
    </div>
    <div class="b2b-msg-wrapper" id="msg-precio">
    [#if showPVO(ctx.getUser(), uuid, username)]
        <span>${i18n['cione-module.templates.myshop.confirmacion-pedido-component.importe']}:</span>
        <span class="b2b-producto-enviado-strong" id="precioInfo"></span>
    [/#if]
    </div>
    </div>

	<div class="b2b-msg-wrapper d-none" id="pedido-none">
		<span class="b2b-producto-enviado-strong">${i18n['cione-module.templates.myshop.confirmacion-pedido-component.no-pedidos']}</span>
	</div>
	<div class="b2b-msg-wrapper">
		<div style="text-align: left; padding-left: 10%; font-family: inherit; font-size: 14px; line-height: 20px;">
		[#if confirmationText?? && confirmationText?has_content]
			${cmsfn.decode(confirmationText).letrapequeña!""}
		[/#if]
		</div>
	</div>
	
	<div class="b2b-cart-back-button">
		<div class="b2b-button-wrapper" style="margin: auto;">
		    <button  class="b2b-button b2b-button-filter" type="button" onclick="location.href='${ctx.contextPath}/cione/private/myshop'">
		       ${i18n['cione-module.templates.myshop.direcciones-envio-component.seguir-comprando']?upper_case}
		    </button>
		</div>
    </div>
	
    </div>
</section>

<script type="text/javascript">
	$(document).ready(function() {
		if((typeof sessionStorage.streetName !== 'undefined')) {
			$("#dirInfo").text(sessionStorage.streetName + " · " + sessionStorage.postalCode + " " + sessionStorage.city + ", " + sessionStorage.region);
			$("#precioInfo").text(sessionStorage.totalPedido + "€");
		} else {
			$("#pedido-ok").addClass("d-none");
			$("#pedido-none").removeClass("d-none");
		}
		
		//sessionStorage.clear();
	});
</script>
