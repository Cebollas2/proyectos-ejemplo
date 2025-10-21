<!-- CIONE LAB -->
[#assign info = model.getInfo()!]


<section class="b2b-breadcrumb">
	
	
	[#-- [http://dominio(82.223.78.35)/cionelab/
	?uuid=id_usuario&
	lbusiness=line_business&
	profile=perfil_usuario& Si puede ver el PVO=1 PVP=2 ambos=3
	lang=idioma&
	cartid=id_carrito]--]
	 
	
	[#--<iframe src="http://talleropticopre.cione.es/cionelab/?uuid=126500000&lbusiness=1&profile=3&lang=1" class="iframecio" frameborder="0" 
	style="padding: 0px;position: relative;width: 1000px;left: -33px;"></iframe>--]

	
	[#if content.urlcionelab?? && content.urlcionelab?has_content]
		[#if info.pack?? && info.pack?has_content]
			
			<iframe src="${content.urlcionelab!}/cionelab/?uuid=${info.getIdSocio()!}&lbusiness=${info.getGrupoPrecio()!}&profile=${info.getVisibilidad()!}&lang=${cmsfn.language()!}&cartid=${info.getIdCarritoOculto()!}&pack=${info.getPack()!}&sku=${info.getSkuMontura()!}&skuPackMaster=${info.getSkuPackMaster()!}&step=1"
			class="iframecio" frameborder="0" style="padding: 0px;position: relative;width: 100%; height: 900px; left: -33px;">
			</iframe>
		[#else]
			<iframe src="${content.urlcionelab!}/cionelab/?uuid=${info.getIdSocio()!}&lbusiness=${info.getGrupoPrecio()!}&profile=${info.getVisibilidad()!}&lang=${cmsfn.language()!}&cartid=${info.getIdCarrito()!}" 
			class="iframecio" frameborder="0" style="padding: 0px;position: relative;width: 100%; height: 900px; left: -33px;">
			</iframe>
		[/#if]
	[/#if]
	
	[#--<iframe src="https://talleropticopre.cione.es/?uuid=${info.getIdSocio()!}&lbusiness=${info.getGrupoPrecio()!}&profile=${info.getVisibilidad()!}&lang=${info.getLang()!}&cartid=${info.getIdCarrito()!}" 
	class="iframecio" frameborder="0" style="padding: 0px;position: relative;width: 100%; height: 900px; left: -33px;">
	</iframe>--]
    
</section>

<script src="https://cdnjs.cloudflare.com/ajax/libs/socket.io/2.0.0/socket.io.js"></script>
<script>
[#if content.urlsocketio?? && content.urlsocketio?has_content]
    var socket = io.connect("${content.urlsocketio}:3000?userid=${info.getIdSocio()!}&from=magnolia", {secure: true});
[#else]
	var socket = io.connect("https://devmycione.cione.es:3000?userid=${info.getIdSocio()!}&from=magnolia", {secure: true});
[/#if]

  socket.on("UPDATE_CART", function (data) {
    setInterval(function(){
    	[#if info.pack?? && info.pack?has_content && content.internalLinkPacksDetail?? && content.internalLinkPacksDetail?has_content]
    		[#assign link = cmsfn.link("website", content.internalLinkPacksDetail!)!]
    		[#if link?? && link?has_content]
				var link ="${link}" + "?skuPackMaster=${info.skuPackMaster!}";
				//actualizarCarritoOculto(link); //añadimos el step
				refreshCache(link);
				window.location.href = link;
			[#else]
				link ="#";
			[/#if]
    	[#else]
    		location.reload()
    	[/#if]
    },2000);
    console.log("cione ha actualizado carrito");
  });
  
  function refreshCache(link) {
	$.ajax({
		url: "${ctx.contextPath}/.rest/private/carrito/v1/refreshCache?skuPackMaster=${info.skuPackMaster!}",
		type: "GET",
        contentType: 'application/json; charset=utf-8',
        cache: false,
    	async: false,
        dataType: "json",
        success : function(response) {
        	console.log("Eliminada la cache del pack");
        },
        error: function(response) { 
        	console.log("Error refreshCache"); 
        },
        complete : function(response) { 
        	console.log("actualizado"); 
        }
	});
  }
  
  
  function actualizarCarritoOculto(link) {
  	[#if info.pack?? && info.pack?has_content]
	  	var filter = JSON.stringify({
	    	"idCarritoOculto": '${info.getIdCarrito()!}',
	    	"step": '2',
	    	"skuPackMaster": '${info.getSkuPackMaster()!}'
		});
  		$.ajax({
			url: "${ctx.contextPath}/.rest/private/carrito/v1/cart/updateCustomFieldCLIPack/" + updatedField,
			type: "POST",
			data: filter,
	        contentType: 'application/json; charset=utf-8',
	        cache: false,
	    	async: false,
	        dataType: "json",
	        success : function(response) {
	        	window.location.href = link;
	        },
	        error: function(response) { 
	        	console.log("Error update refCliente/refTaller/descPack"); 
	        },
	        complete : function(response) { 
	        	console.log("actualizado"); 
	        }
		});
	[/#if]
   }
  
</script>