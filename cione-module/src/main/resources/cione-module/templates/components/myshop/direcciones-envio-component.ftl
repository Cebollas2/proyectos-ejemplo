[#include "../../includes/macros/cione-utils-impersonate.ftl"]

[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]
[#assign direcciones = model.getUserDirecciones()]

[#assign carrito = model.currentUserCart()!]
[#assign uuid = model.getUuid()!]
[#assign username = model.getUserName()!]

[#if carrito?has_content && (carrito.getLineItems()?size > 0 || carrito.getCustomLineItems()?size > 0)]
	<main class="b2b-main" role="main">
	    <div class="b2b-cart-container">
	        <div class="b2b-cart-items-wrapper">
	            <section class="b2b-form-send">
	
	                <span class="b2b-form-subtitle">${i18n['cione-module.templates.myshop.direcciones-envio-component.dirEnvio']}</span>
	
	                <div class="b2b-form-radio-wrapper">
	                    [#list direcciones.getTransportes() as dir]
	                    <div class="b2b-form-col">
	                        <label class="b2b-form-label-container b2b-form-row-radio">
	                            <div class="d-flex align-items-center">
	                                <input type="radio" [#if model.isDefaultAddress(dir.getId_localizacion()) ||
	                                    dir?index==0]checked="checked" [/#if] name="radio"
	                                    value="${dir.getId_localizacion()}" id="radio${dir?counter}"
	                                    form="direcciones_form">
	
	                                <span>${dir.getDireccion()} · ${dir.getCod_postal()} ${dir.getLocalidad()},
	                                    ${dir.getProvincia()}</span>
	                            </div>
	
	                            [#if model.isDefaultAddress(dir.getId_localizacion())]
	                            <span class="b2b-radio-default">
	                                <img src="${resourcesURL}/img/myshop/icons/bx-checked.svg" width="20" alt="">
	                                ${i18n['cione-module.templates.myshop.direcciones-envio-component.predeterminada']}
	                            </span>
	                            [/#if]
	                        </label>
	                    </div>
	                    <form id="${dir.getId_localizacion()}" name="${dir.getId_localizacion()}">
	                        <input type="hidden" name="direccion" value="${dir.getDireccion()}">
	                        <input type="hidden" name="poblacion" value="${dir.getLocalidad()}">
	                        <input type="hidden" name="provincia" value="${dir.getProvincia()}">
	                        <input type="hidden" name="cod_postal" value="${dir.getCod_postal()}">
	                        <input type="hidden" name="radio" value="${dir.getId_localizacion()}">
	                    </form>
	                    [#else]
	                    <div class="b2b-form-col">
	                        ${i18n['cione-module.templates.myshop.direcciones-envio-component.direccionesNotFound']}</div>
	                    [/#list]
	
	                    <div class="b2b-form-col">
	                        <div class="b2b-form-label-container">
	                            <input class="b2b-tab-check styled-checkbox" id="21" type="checkbox"
	                                form="direcciones_form">
	                            <label class="b2b-tab-label b2b-tab-label-sending"
	                                for="21">${i18n['cione-module.templates.myshop.direcciones-envio-component.enviarCliente']}</label>
	                        </div>
	                    </div>
	
	                    <div class="b2b-form-sending">
	
	                        <form id="direcciones_form" name="direcciones_form"></form>
	
	                        <div class="b2b-form-col">
	                            <div class="b2b-form-input-container">
	                                <label>${i18n['cione-module.templates.myshop.direcciones-envio-component.nombre']}
	                                    *</label>
	                                <input id="nombre" class="form-control" type="text" maxlength='50' name="nombre"
	                                    form="direcciones_form">
	                            </div>
	
	                            <div class="b2b-form-input-container">
	                                <label>${i18n['cione-module.templates.myshop.direcciones-envio-component.primer_apellido']}
	                                    *</label>
	                                <input id="primer_apellido" class="form-control" type="text" maxlength='50' name="primer_apellido"
	                                    form="direcciones_form">
	                            </div>
	
	                            <div class="b2b-form-input-container">
	                                <label>${i18n['cione-module.templates.myshop.direcciones-envio-component.segundo_apellido']}</label>
	                                <input id="segundo_apellido" class="form-control" type="text" maxlength='50' name="segundo_apellido"
	                                    form="direcciones_form">
	                            </div>
	                        </div>
	
	                        <div class="b2b-form-col b2b-form-col-w100">
	                            <div class="b2b-form-input-container">
	                                <label>${i18n['cione-module.templates.myshop.direcciones-envio-component.direccion']}
	                                    *</label>
	                                <input id="direccion" class="form-control" type="text" maxlength='50' required name="direccion"
	                                    form="direcciones_form">
	                            </div>
	                        </div>
	
	                        <div class="b2b-form-col">
	                            <div class="b2b-form-input-container">
	                                <label>${i18n['cione-module.templates.myshop.direcciones-envio-component.poblacion']}
	                                    *</label>
	                                <input id="poblacion" class="form-control" type="text" maxlength='50' name="poblacion"
	                                    form="direcciones_form">
	                            </div>
	
	                            <div class="b2b-form-input-container">
	                                <label>${i18n['cione-module.templates.myshop.direcciones-envio-component.provincia']}
	                                    *</label>
	                                <input id="provincia" class="form-control" type="text" maxlength='50' name="provincia"
	                                    form="direcciones_form">
	                            </div>
	
	                            <div class="b2b-form-input-container">
	                                <label>${i18n['cione-module.templates.myshop.direcciones-envio-component.cod_postal']}
	                                    *</label>
	                                <input id="cod_postal" class="form-control" type="text" maxlength='10' name="cod_postal"
	                                    form="direcciones_form">
	                            </div>
	                        </div>
	
	                        <div class="b2b-form-col b2b-form-col-w50">
	                            <div class="b2b-form-input-container">
	                                <label>${i18n['cione-module.templates.myshop.direcciones-envio-component.movil']}
	                                    *</label>
	                                <input id="movil" class="form-control" type="text" maxlength='13' name="movil" form="direcciones_form">
	                            </div>
	
	                            <div class="b2b-form-input-container">
	                                <label>Email *</label>
	                                <input id="email" class="form-control" type="text" maxlength='50' name="email" form="direcciones_form">
	                            </div>
	
	                        </div>
	
	                        <div class="b2b-msg-validation"></div>
	                        <div class="b2b-msg-validation-movil"></div>
	                        <div class="b2b-msg-validation-email"></div>
	
	                    </div>
	
	                </div>
	                
	                [#-- Nota para el SAS --]
					<div class="b2b-cart-notes">
					    <div class="b2b-cart-notes-title">
					    	<strong>${i18n['cione-module.templates.myshop.listado-productos-carrito-component.sastitle']}</strong> ${i18n['cione-module.templates.myshop.listado-productos-carrito-component.sascomment']}<br><br>
					    </div>
					    <div>
							[#assign notaSAS = ""]
							[#if carrito.getCustom()??]
							[#assign mapCustomFields = carrito.getCustom().getFields().values()]
								[#if mapCustomFields["notaSAS"]??]
									[#if mapCustomFields["notaSAS"]?has_content][#assign notaSAS = mapCustomFields["notaSAS"]?trim!""]
									[/#if]
								[/#if]
							[/#if]
					        <textarea class="b2b-cart-notes-comment" maxlength='500' rows="15" style="height: 7.5rem;" onchange="actualizarNotaSAS(this, '${carrito.id}'); return false">${notaSAS}</textarea>
					    </div>
					    <div class="b2b-cart-notes-footer">${i18n['cione-module.templates.myshop.listado-productos-carrito-component.maxchar']}</div>
					</div>
	
	            </section>
	
	        </div>
	    </div>
	    [#-- BEGIN: MODAL COMPRA MINIMA --]
	    [#assign closeimg = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/icons/close-thin.svg"]
	    <div id="modal-compraminima" class="modal-purchase">
		    <div class="modal-purchase-box">
		        <div class="modal-purchase-header">
		            <p id="modal-cabecera"></p>
		            <div class="modal-purchase-close">											
		                <img class="modal-purchase-close-img" src="${closeimg!""}" alt="cerrar" onclick='closeModalGeneric("#modal-compraminima");'>
		            </div>
		        </div>
		        
		        <div class="modal-purchase-info-cm">
		            <div id="modal-marcas" class="modal-container-text"></div>
		            <div id="modal-proveedor" class="modal-container-text"></div>
		        </div>
		        
		        <div class="modal-purchase-footer">
		            <button class="modal-purchase-button modal-purchase-button--transparent" type="button" onclick='closeModalGeneric("#modal-compraminima");'>
		                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}
		            </button>
		        </div>
		    </div>
		</div>
	    [#-- END: MODAL COMPRA MINIMA --]
	</main>
[/#if]


<script type="text/javascript">

function validarCompraMinima() {
	var cartvalidate = true;
	[#assign pageNode = model.getPagePath()]
	
	$.ajax({
		url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/validateCart?language=${cmsfn.language()}&contentNodePath=${pageNode}",
		type: "GET",
        contentType: 'application/json; charset=utf-8',
        cache: false,
        dataType: "json",
        success: function(response) {
        	cartvalidate = response.isValidCart; 
        	if (!cartvalidate) {
        		var jsonproveedor = response.proveedor;
        		var jsonmarca = response.marca;
        		$('#modal-cabecera').text(response.cabecera);
        		
        		$('#modal-marcas').empty();
        		response.marca.forEach(function(mensaje) {
        			$('#modal-marcas').append("<p>" + mensaje + "</p>");
        		});
        		
        		$('#modal-proveedor').empty();
        		response.proveedor.forEach(function(mensaje) {
        			$('#modal-proveedor').append("<p>" + mensaje + "</p>");
        		});
        		
        		$("#loading").hide();
				
				$('#modal-compraminima').css('display','flex');
    			$('#modal-compraminima').css('visibility','visible');
    			
    			return false;
        	} else {
        		tramitarPedido();
        	}
        },
        error: function(response) {
        	console.log("Error al calcular la compra minima");
        	alert("${i18n['cione-module.listado-productos-carrito.error-admin']}");
        	return false;
        }
	});
	
	
}

function tramitarPedido() {
	$("#cart-error-message").text();
	$("#loading").show();
	$(".product-button").prop("disabled", true);
	
	var oForm = document.forms["direcciones_form"];
    
    if(!document.getElementById('21').checked) { [#-- Si "Enviar al cliente" NO está activado... --]
    	var id_localizacion = $("input[name=radio]:checked").val();
    	
		filter = JSON.stringify(getFormData($("#" + id_localizacion)));
		
	} else if(validateForm(oForm)) [#-- Aquí ya sabemos que "Enviar al cliente" está activado --]
		filter = JSON.stringify(getFormData($("#direcciones_form")));
		
	else { [#-- Si "Enviar al cliente" está activado y el formulario de envío no está validado... --]
		$("#loading").hide();
		$(".product-button").prop("disabled", false);
		return;
	}
	
	addAddressToCart();
}

function addAddressToCart() {

	$.ajax({
		url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updateShippingAddress",
		type: "POST",
		data: filter,
        contentType: 'application/json; charset=utf-8',
        cache: false,
        dataType: "json",
        success: function(response) { prepareCartToOrder(); },
        error: function(response) {
        	$("#loading").hide();
        	console.log("Error addAddressToCart");
        	$(".product-button").prop("disabled", false);
        }
	});
}

function prepareCartToOrder() {
	
	$.ajax({
		url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/prepareCart",
		type: "POST",
		async: false,
        success: function(response) { 
        	$.ajax({
				url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/pvoFullCart",
				type: "GET",
				async: false,
		        success: function(response) { 
		        	sessionStorage.totalPedido = response["pvocart"]
		        },
		        error: function(response) {
		        	sessionStorage.totalPedido = '${model.getCarritoTotal()}';
		        }
			});
        	cartToOrder(); 
        },
        error: function(response) {
        	$("#loading").hide();
        	console.log("Error prepareCartToOrder");
        	$(".product-button").prop("disabled", false);
        	$("#cart-error-message").text(response.responseText);
        }
	});
}

function cartToOrder() {

	$.ajax({
		url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/toOrder",
		type: "POST",
        success: function(response) {
        
        	//Guardar info del pedido para la pagina de confirmacion
        	sessionStorage.streetName = response.shippingAddress.streetName;
        	sessionStorage.postalCode = response.shippingAddress.postalCode;
        	sessionStorage.city = response.shippingAddress.city;
        	sessionStorage.region = response.shippingAddress.region;
        	
        	//let totalCents = response.custom.fields.pvoFinalAcumulado.centAmount / 100;

        	//sessionStorage.totalPedido = '${model.getCarritoTotal()}';
        
        	//Redirect to confirmacion del pedido
			[#assign redirectLink = cmsfn.link("website", content.confirmacionPedidoLink!)!]
        	window.location.href = "${redirectLink}";
        	
        },
        error: function(response) {
        	console.log("Error cartToOrder");
        	$(".product-button").prop("disabled", false);
        },
        complete: function(response) { $("#loading").hide(); },
	});
}

function validateForm(oForm) {
	
	var isValid = true;
	var nombre = $('#nombre').val(); 
	var primer_apellido = $('#primer_apellido').val();
	var segundo_apellido = $('#segundo_apellido').val(); 
	
	var direccion = $('#direccion').val(); 
	var poblacion = $('#poblacion').val();
	var provincia = $('#provincia').val();
	var cod_postal = $('#cod_postal').val();
	
	var movil = $('#movil').val();
	var email = $('#email').val();
	var msg_obligatorio = "${i18n['cione-module.templates.myshop.direcciones-envio-component.form.msg-obligatorio']}";
	
	if(nombre == "") {
		$('.b2b-msg-validation').addClass("msg-error");
		$('.b2b-msg-validation').removeClass("msg-ok");
		$('.b2b-msg-validation').text(msg_obligatorio);
		$('#nombre').addClass("validation-error");
		isValid = false;
		
	} else {
		$('.b2b-msg-validation').addClass("msg-ok");
		$('.b2b-msg-validation').removeClass("msg-error");
		$('.b2b-msg-validation').text("");
		$('#nombre').removeClass("validation-error");
	}
	
	if(primer_apellido == "") {
		$('.b2b-msg-validation').addClass("msg-error");
		$('.b2b-msg-validation').removeClass("msg-ok");
		$('.b2b-msg-validation').text(msg_obligatorio);
		$('#primer_apellido').addClass("validation-error");
		isValid = false;
		
	} else {
		$('.b2b-msg-validation').addClass("msg-ok");
		$('.b2b-msg-validation').removeClass("msg-error");
		$('.b2b-msg-validation').text("");
		$('#primer_apellido').removeClass("validation-error");
	}
	
	if(direccion == "") {
		$('.b2b-msg-validation').addClass("msg-error");
		$('.b2b-msg-validation').removeClass("msg-ok");
		$('.b2b-msg-validation').text(msg_obligatorio);
		$('#direccion').addClass("validation-error");
		isValid = false;
		
	} else {
		$('.b2b-msg-validation').addClass("msg-ok");
		$('.b2b-msg-validation').removeClass("msg-error");
		$('.b2b-msg-validation').text("");
		$('#direccion').removeClass("validation-error");
	}
	
	if(poblacion == "") {
		$('.b2b-msg-validation').addClass("msg-error");
		$('.b2b-msg-validation').removeClass("msg-ok");
		$('.b2b-msg-validation').text(msg_obligatorio);
		$('#poblacion').addClass("validation-error");
		isValid = false;
		
	} else {
		$('.b2b-msg-validation').addClass("msg-ok");
		$('.b2b-msg-validation').removeClass("msg-error");
		$('.b2b-msg-validation').text("");
		$('#poblacion').removeClass("validation-error");
	}
	
	if(provincia == "") {
		$('.b2b-msg-validation').addClass("msg-error");
		$('.b2b-msg-validation').removeClass("msg-ok");
		$('.b2b-msg-validation').text(msg_obligatorio);
		$('#provincia').addClass("validation-error");
		isValid = false;
		
	} else {
		$('.b2b-msg-validation').addClass("msg-ok");
		$('.b2b-msg-validation').removeClass("msg-error");
		$('.b2b-msg-validation').text("");
		$('#provincia').removeClass("validation-error");
	}
	
	if(cod_postal == "") {
		$('.b2b-msg-validation').addClass("msg-error");
		$('.b2b-msg-validation').removeClass("msg-ok");
		$('.b2b-msg-validation').text(msg_obligatorio);
		$('#cod_postal').addClass("validation-error");
		isValid = false;
		
	} else {
		$('.b2b-msg-validation').addClass("msg-ok");
		$('.b2b-msg-validation').removeClass("msg-error");
		$('.b2b-msg-validation').text("");
		$('#cod_postal').removeClass("validation-error");
	}
	
	if(movil == "") {
		$('.b2b-msg-validation').addClass("msg-error");
		$('.b2b-msg-validation').removeClass("msg-ok");
		$('.b2b-msg-validation').text(msg_obligatorio);
		$('#movil').addClass("validation-error");
		isValid = false;
		
	} else {
		$('.b2b-msg-validation').addClass("msg-ok");
		$('.b2b-msg-validation').removeClass("msg-error");
		$('.b2b-msg-validation').text("");
		$('#movil').removeClass("validation-error");
	}
	
	var regex = /^([0-9]{9})$/;
	if(movil != "" && !movil.match(regex)) {
		$('.b2b-msg-validation-movil').addClass("msg-error");
		$('.b2b-msg-validation-movil').removeClass("msg-ok");
		$('.b2b-msg-validation-movil').text("${i18n['cione-module.templates.myshop.direcciones-envio-component.form.msg-movil']}");
		$('#movil').addClass("validation-error");
		isValid = false;
	} else {
		if(movil != ""){
			$('.b2b-msg-validation-movil').addClass("msg-ok");
			$('.b2b-msg-validation-movil').removeClass("msg-error");
			$('.b2b-msg-validation-movil').text("");
			$('#movil').removeClass("validation-error");
		}
	}
	
	if(email == "") {
		$('.b2b-msg-validation').addClass("msg-error");
		$('.b2b-msg-validation').removeClass("msg-ok");
		$('.b2b-msg-validation').text(msg_obligatorio);
		$('#email').addClass("validation-error");
		isValid = false;
		
	} else {
		$('.b2b-msg-validation').addClass("msg-ok");
		$('.b2b-msg-validation').removeClass("msg-error");
		$('.b2b-msg-validation').text("");
		$('#email').removeClass("validation-error");
	}
	
	
	if(email != "" && !emailIsValid(email)) {
		$('.b2b-msg-validation-email').addClass("msg-error");
		$('.b2b-msg-validation-email').removeClass("msg-ok");
		$('.b2b-msg-validation-email').text("${i18n['cione-module.templates.myshop.direcciones-envio-component.form.msg-email']}");
		$('#email').addClass("validation-error");
		isValid = false;
	} else {
		if(email != ""){
			$('.b2b-msg-validation-email').addClass("msg-ok");
			$('.b2b-msg-validation-email').removeClass("msg-error");
			$('.b2b-msg-validation-email').text("");
			$('#email').removeClass("validation-error");
		}
	}

	return isValid;
}

function emailIsValid (email) {
  return /\S+@\S+\.\S+/.test(email)
}

function getFormData($form) {
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};
	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});
	return indexed_array;
}
</script>

<style>
.validation-error {
	box-shadow: 0 0 0px 1px #EE0000;
}
</style>