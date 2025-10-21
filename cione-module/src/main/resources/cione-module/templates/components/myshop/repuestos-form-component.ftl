<!-- FORMULARIO REPUESTOS -->
[#assign aliasEkon = ctx.aliasEkon!]
[#assign sku = ctx.sku!]

[#if aliasEkon?has_content]
	[#assign variante = model.getVariantByAliasEkon(aliasEkon)!]
[/#if]

[#if sku?has_content]
	[#assign variante = model.getVariantBySku(sku)!]
[/#if]

[#assign marcaValue = ""]
[#assign modeloValue = ""]
[#assign dimensionesValue = ""]
[#assign colorPT = ""]
[#assign colorES = ""]
[#if variante?? && variante?has_content]
	[#list variante.getAttributes() as attribute]
	    [#if attribute.getName() == "marca"]
	        [#assign marcaValue = "value='" + attribute.getValue() + "'"]
	    [/#if]
	    [#if attribute.getName() == "modelo"]
	        [#assign modeloValue = "value='" + attribute.getValue() + "'"]
	    [/#if]
	    [#if attribute.getName() == "dimensiones_ancho_ojo"]
	        [#assign dimensionesValue = "value='" + attribute.getValue() + "'"]
	    [/#if]
	    [#if attribute.getName() == "colorMontura"]
	        [#assign colorPT = attribute.getValue().get("pt")!]
	        [#assign colorES = attribute.getValue().get("es")!]
	    [/#if]
	[/#list]
[/#if]
<section class="b2b-form-repuestos">
    <form id="repuestos-form">
        <h2 class="b2b-h2">${i18n.get('cione-module.templates.myshop.repuestos-form-component.title')}</h2>
         <span class="b2b-form-subtitle">${i18n.get('cione-module.templates.myshop.repuestos-form-component.type')}</span> 
        <div class="b2b-form-radio-wrapper">
            <div class="b2b-form-col">
                <div class="b2b-form-label-container">
                    <input class="b2b-tab-check styled-checkbox" id="21" type="checkbox">
                    <label class="b2b-tab-label" for="21">${i18n.get('cione-module.templates.myshop.repuestos-form-component.right-rod')}</label>
                </div>

                <div class="b2b-form-label-container">
                    <input class="b2b-tab-check styled-checkbox" id="22" type="checkbox">
                    <label class="b2b-tab-label" for="22">${i18n.get('cione-module.templates.myshop.repuestos-form-component.left-rod')}</label>
                </div>
                <div class="b2b-form-label-container">
                    <input class="b2b-tab-check styled-checkbox" id="23" type="checkbox">
                    <label class="b2b-tab-label" for="23">${i18n.get('cione-module.templates.myshop.repuestos-form-component.front-rod')}</label>
                </div>


            </div>
            <div class="b2b-form-col">
                <div class="b2b-form-input-container">
                    <label>${i18n.get('cione-module.templates.myshop.repuestos-form-component.brand')}</label>
                    <input id="marca" ${marcaValue}
                    		class="form-control" type="text">
                </div>

                <div class="b2b-form-input-container">
                    <label>${i18n.get('cione-module.templates.myshop.repuestos-form-component.model')}</label>
                    <input id="modelo" ${modeloValue}
                    		class="form-control" type="text">
                </div>

                <div class="b2b-form-input-container">
                    <label>${i18n.get('cione-module.templates.myshop.repuestos-form-component.color')}</label>
                    <input id="color"
                    	value="[#if cmsfn.language() == 'pt' && colorPT?has_content]${colorPT}[#else]${colorES}[/#if]"
                    		class="form-control" type="text">
                </div>
                
            </div>
			<div class="b2b-form-col">
                <div class="b2b-form-input-container">
                    <label>${i18n.get('cione-module.templates.myshop.repuestos-form-component.calibre')}</label>
                    <input id="calibre" ${dimensionesValue}
                    		class="form-control" type="text">
                </div>
            </div>
        </div>

        <div class="b2b-form-input-container">
            <label>${i18n.get('cione-module.templates.myshop.repuestos-form-component.comments')}</label>
            <textarea name="" id="comentarios" cols="30" rows="8"  class="form-control"></textarea>
         
        </div>
        
        <div class="b2b-msg-passowrd">
        </div>

		<div class="b2b-button-wrapper">
		    <button  class="b2b-button " type="sumbit">
		        ${i18n.get('cione-module.templates.configuracion-precios-component.accept')}
		    </button>
		</div>
		
		<div id="modalAudio" class="modal modal-anuncio" tabindex="-1" role="dialog">
	    	<div class="modal-dialog" role="document">
	      		<div class="modal-content" style="height: 190px;">
	        		<div class="modal-body">
	        			<div id="modal-anuncio">
	        				<div id="modal-anuncio-text" style="padding-top: 25px;padding-left: 40px;font-size: large;"></div>
        					<div class="panelbuttonAudio" style="padding-top: 50px;">
        						<a href="javascript:closeModalAudio()" class="closemodal">${i18n['cione-module.templates.components.menu-home-component.close']}</a>
        					</div>
            			</div>
	        		</div>
	      		</div>
	    	</div>
		</div>
    </form>
    
    <script type="text/javascript">
        
        $(document).ready(function () {
	        $("#repuestos-form").submit(function( event ) {
					if(event.isDefaultPrevented()){
					}
					else{
						 $("#loading").show();
						 $.ajax({
						  type: "POST",
						  url: "${ctx.contextPath}/.rest/repuestos/submitRepuestosForm",
						  data: formToJSON(),
						  headers: {
						    'Accept': 'application/json',
						    'Content-Type': 'application/json'
						  },
						  
						  	success: function(response) {
								$('.b2b-msg-passowrd').addClass("msg-ok");
								$('.b2b-msg-passowrd').removeClass("msg-error");
								$('.b2b-msg-passowrd').text(response.message);
								$('#modal-anuncio-text').text('${i18n.get('cione-module.templates.myshop.repuestos-form-component.success')}');
								openModalAudio();
								clearFormRepuesto();
						  	},
						  	error: function(response) { 
						  		$('.b2b-msg-passowrd').addClass("msg-error");
						  		$('.b2b-msg-passowrd').removeClass("msg-ok");
						  		$('.b2b-msg-passowrd').text(response.responseJSON.message);
						  	},
						  	complete : function(response) {
								$("#loading").hide();
							}
						});
					}
				return false;	
		         
			});
			function formToJSON() {
			
				varillaDerecha = document.getElementById('21').checked;
				varillaIzquierda = document.getElementById('22').checked;
				varillaFrente = document.getElementById('23').checked;
				marca = $("#marca").val();
				modelo = $("#modelo").val();
				color = $("#color").val();
				calibre = $("#calibre").val();
				comentarios = $("#comentarios").val();
				destinatarios = "";
				[#list cmsfn.children(content) as dest]
					[#if dest.destinatario?has_content]
						destinatarios += '${dest.destinatario};';
					[/#if]
				[/#list]
				destinatarios += '${ctx.user.getProperty('email')!""}';
				
				return JSON.stringify({
			        "varillaDerecha": varillaDerecha,
			        "varillaIzquierda": varillaIzquierda,
			        "varillaFrente": varillaFrente,
			        "marca": marca,
			        "modelo": modelo,
			        "color": color,
			        "calibre": calibre,
			        "comentarios": comentarios,
			        "destinatarios": destinatarios,
			        "usuario": "${ctx.user.getProperty('name')!ctx.user.getProperty('title')!""}",
			    });
			}
		});
		
		function closeModalAudio(){
			$("#modalAudio").hide();
		}
		
	    function openModalAudio(){
			$("#modalAudio").show();
		}
		function clearFormRepuesto() {
			$("#marca").val('');
			$("#modelo").val('');
			$("#color").val('');
			$("#comentarios").val('');
			$('#21').prop("checked", false);
			$('#22').prop("checked", false);
			$('#23').prop("checked", false);
		}
		</script>
</section>