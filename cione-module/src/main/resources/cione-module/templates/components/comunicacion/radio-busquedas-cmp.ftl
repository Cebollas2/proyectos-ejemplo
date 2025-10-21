<style>
	.cmp-radiobusqueda .wrapper-box .box {
	   height: 350px;
	}
	.cmp-radiobusqueda .wrapper-box .box address a {
	    word-break: break-word;
	}
	.cmp-radiobusqueda .wrapper-box .box .title-anuncio {
    	text-overflow: ellipsis;
   		overflow: hidden;
    	white-space: nowrap;
	}
</style>

<section class="cmp-radiobusqueda">
    <h2 class="title">${i18n['cione-module.templates.components.radio-busqueda.radio-busqueda']}</h2>
    <p> ${i18n['cione-module.templates.components.radio-busqueda.descripcion']} </p>
    
    <div class="panelbuttons">
        <button class="btn-blue" type="" onclick="window.location='${cmsfn.link("website", content.createPageLink!)!}'">        	
        	${i18n['cione-module.templates.components.radio-busqueda.crear-anuncio']}
        </button>
    </div> 

	<div id="main" class="wrapper-box">
		<!-- 
		 <div class="box">
            <time>Fecha de publicación: 10/10/2019</time>
            <div class="title-anuncio">Título anuncio</div>
            <address><a href="mailto:webmaster@example.com">email@email.com</a></address>
            <p>Lorem ipsum dolor sit amet consectetur
                adipiscing elit sollicitudin quis, parturient
                mauris praesent famem ipsum dolor sit amet consectetur
                adipiscing elit sollicitudin quis, parturient
                mauris praesent fames vel morbi mi nulla,
                nisi curabitur etiam euismod,es vel morbi mi nulla,
                nisi curabitur etiam euismod,.</p>
            <a href="#ex1" rel="modal:open" class="vermasanuncio">Ver</a>
        </div>
        --> 
	</div>
	
	<div id="pagination" class="pagination"></div>

	<div id="modalAnuncio" class="modal modal-anuncio" tabindex="-1" role="dialog">
	    <div class="modal-dialog"  role="document">
	      <div class="modal-content">
	        <div class="modal-body">
	        	<div id="modal-anuncio-radio"></div>
	        </div>
	      </div>
	    </div>
	</div>

 </section>
 
 <script>
 	page = 0;
 	pageSize = 12;
 	totalRegistros = 0;
 	count = 0;
 	anuncios = [];
 	
 	//get total de registro
 	function init(){
 		$("#loading").show();        
        $.ajax({
            url : PATH_API + "/private/radio-busqueda/v1/total",
            type : "GET",            
            contentType : 'application/json; charset=utf-8',
            //cache : false,
            dataType : "json",
            success : function(response) {
            	totalRegistros = response;
            	if(totalRegistros == 0){
            		$("#main").html("<div>${i18n['cione-module.templates.components.radio-busqueda.empty']}</div>");
            		$("#pagination").remove();
            	}else{
            		loadAnuncios();
            	}
            },
            error : function(response) {
                alert("error");             
            },
            complete : function(response) {
                $("#loading").hide();                               
            }
        }); 
 		
 	}
 	
 	function loadAnuncios(){ 		
 		$("#loading").show();        
        $.ajax({
            //url : PATH_API + "/private/radiobusquedas?mgnl:lastActivated[null]=false&limit=" + pageSize + "&offset=" + page + "&orderBy=mgnl:lastActivated desc",
            url : PATH_API + "/private/radiobusquedas?limit=" + pageSize + "&offset=" + page + "&orderBy=mgnl:lastModified desc",
            type : "GET",            
            contentType : 'application/json; charset=utf-8',
            //cache : false,
            dataType : "json",
            success : function(response) {
            	var results = [];
            	var adverts = response.results;            	
            	adverts.forEach(function(anuncio){            		
            		//anuncio.fechaView = moment(anuncio.fecha).format('DD/MM/YYYY');
            		anuncio.fechaView = anuncio["mgnl:lastModified"]?moment(anuncio["mgnl:lastModified"]).format('DD/MM/YYYY'):"<span style='background:red'>[SIN PUBLICAR]</span>";
            		anuncio.index = count;
                    results.push(template(anuncio)); 
                    anuncios.push(anuncio);
                    count ++;
                });            	
            	$("#main").append(results.join(" "));
            	renderSummary(count,totalRegistros);
                 
            },
            error : function(response) {
                alert("error");             
            },
            complete : function(response) {
                $("#loading").hide();                               
            }
        });
 	}
 	
 	
 	function commonTemplate(anuncio){
 		var html = "";
        html += "<time>${i18n['cione-module.templates.components.radio-busqueda.fecha-publicacion']} : " + anuncio.fechaView + "</time>";
        html += "<div class='title-anuncio'>" + anuncio.titulo + "</div>";
        html += "<address><a href='mailto:" + anuncio.email + "'>" + anuncio.email + "</a></address>";
        html += "<p>" + anuncio.texto + "</p>";
        return html;
 	}
 	
 	function template(anuncio){
 		var html = ""; 		
 		html += "<div class='box'>";
		html += commonTemplate(anuncio);
        html += "<a href='javascript:showDetalleAnuncio(" + anuncio.index + ")' class='vermasanuncio'>Ver</a>";
     	html += "</div>";		
		return html;
 	}
 	
 	function templateDetalle(anuncio){
 		var html = ""; 		 		
 		html += commonTemplate(anuncio);                             
        html += "<div class='panelbutton'>";
		html += "<a href='javascript:closeDetalleAnuncio()' class='closemodal'>cerrar</a>";
    	html += "</div>"; 
        
		return html;
 	}
 	
 	function showDetalleAnuncio(index){
 		$("#modal-anuncio-radio").html(templateDetalle(anuncios[index]));
 		$("#modalAnuncio").show();
 		//$("#modal-anuncio-radio").modal();
 	}
 	
 	function closeDetalleAnuncio(){
 		$("#modalAnuncio").hide();
 	}
 	
 	function renderSummary(num,total){
 		var html = ""; 
 		html += "<span>${i18n['cione-module.global.search.mostrando']} " + num + " ${i18n['cione-module.global.search.de']} <span class='cantrow'>" + total + "</span> ${i18n['cione-module.global.search.resultados']}</span>";
 		if(num != total){
 			html += "<span class='vermas' onclick='showMoreAnuncios()'>${i18n['cione-module.global.search.ver-mas']}</span>";	
 		} 		
 	    html += "<span></span>";  	    
 	    $("#pagination").html(html); 	    
 	}
 	
 	function showMoreAnuncios(){
 		page = page + 1;
 		loadAnuncios();
 	}
 	
 	function initPage(){
 		init(); 		
 	}
 	
 	String.prototype.trunc = 
      function(n){
          return this.substr(0,n-1)+(this.length>n?'&hellip;':'');
      };
 </script>