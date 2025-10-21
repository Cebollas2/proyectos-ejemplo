[#assign idNoticia = ctx.getParameter('id')!?html]
[#assign noticiasRoot = cmsfn.contentById(idNoticia, "noticias")!]
[#assign carpetas = cmsfn.children(noticiasRoot,"mgnl:folder")!]
<script>
	idNoticia = "${idNoticia}";
</script>

[#assign userId = ctx.getUser().getIdentifier()]

[#assign userNode = cmsfn.contentById(userId, "users")]


<section id="cmp-noticias" class="cmp-noticias">
	<h2 class="title">${i18n['cione-module.templates.components.bloque-noticias.bloque-noticias']}</h2>
	<h2 class="title" id="cmp-noticias-title"></h2> 	
	
	<div id="panel-table" class="panel-table">
		<table class="table" id="tableExport">
			<thead>
				
				<tr>
					<th>${i18n['cione-module.templates.components.bloque-noticias.asunto']}</th>
					<th>${i18n['cione-module.templates.components.bloque-noticias.fecha']}</th>
					<th>${i18n['cione-module.templates.components.bloque-noticias.enlace']}</th>
				</tr>
			</thead>
			<tbody id="table-data"></tbody>
		</table>
		<div class="foot" id="footPen">
		</div>
	</div>	
</section>

 <script> 
 	page = 0;
 	pageSize = 12;
 	total = 0;
 	mostrados = 0;
 	numPages = 0;
 	noticias = [];
 	
 	bloquesNoticia = [];
	
 	function loadNoticias(id){ 
 		//eliminado por la ñapa al no meter idioma por defecto
 		//var path = PATH_API + "/private/noticias?@jcr:uuid=" + id + "&lang=" + LANG;
 		var path = PATH_API + "/private/noticias?@jcr:uuid=" + id + "&lang=all";
 		$("#loading").show();        
        $.ajax({
            url : path,
            type : "GET",            
            contentType : 'application/json; charset=utf-8',
            //cache : false,
            dataType : "json",
            success : function(response) {        
            	//KK = response;
            	
            	$("#cmp-noticias-title").html(response.results[0].nombreBloque);
            	let years = response.results[0];
            	for (var i = 0; i < years['@nodes'].length; i++) {
            		let news = years[years['@nodes'][i]];
				    console.log(i); // Más instrucciones aquí
				    news = news[news['@nodes']].noticias
				
	            	//var news = response.results[0].noticias; 
	            	console.log(news);
	            	 	
	            	var nodeNews = Object.keys(news).filter(function(key){return !isNaN(key);});
	            	nodeNews.forEach(function(nodeNew){
	            		noticias.push(news[nodeNew]);
	            	}); 
	            	
	            	total = noticias.length;
	            	console.log(total);
	            	numPages = Math.ceil(total/pageSize);
	            	console.log("Aqui" + noticias);
	            	
	            	noticias.sort(function(a,b){            		              		  
	            		  return new Date(b.fecha) - new Date(a.fecha);
	            	});
            	}
              	showNoticias();       	                              
            },
            error : function(response) {
                alert("error");                             
            },
            complete : function(response) {
                $("#loading").hide();
                
                /*
                if (page >= nPages) {
                    $(".vermas").prop("onclick", null).off("click");
                } else{
                    $(".vermas").attr("onclick","showMoreEnvios()");
                }
                retailerZebra();
                */
            }
        });
        
 	}
 	
 	
 	function showNoticias(){ 		
 		results = []; 		
 		if(noticias.length > 0){
 			for(var i=page*pageSize;i<(page*pageSize) + pageSize; i++){
 				if(i<total){
 					mostrados ++;
 	 				var noticia = noticias[i];
 	 				noticia.fechaView = moment(noticia.fecha).format('DD/MM/YYYY');
 	 				results.push(templateNoticia(noticia)); 	 				
 	 				renderSummary(mostrados,total); 	 				
 				} 				
 			}
 			/*
		  	noticias.forEach(function(noticia){
		  		noticia.fechaView = moment(noticia.fecha).format('DD/MM/YYYY');
 				results.push(templateNoticia(noticia));
            });*/               			
 		}else{
 			results.push(templateNoRecordsFoundForTable(3));
 		} 		
 		$("#table-data").append(results.join(" "));
 		
 	}
 	
 	function showMoreNoticias(){
 		page = page+1;
 		showNoticias();
 	}
 	
 	
 	function renderSummary(num,total){
 		var html = ""; 
 		html += "<span>${i18n['cione-module.global.search.mostrando']} " + num + " ${i18n['cione-module.global.search.de']} <span class='cantrow'>" + total + "</span> ${i18n['cione-module.global.search.resultados']}</span>";
 		if(num != total){
 			html += "<span class='vermas' onclick='showMoreNoticias()'>${i18n['cione-module.global.search.ver-mas']}</span>";	
 		} 		
 	    html += "<span></span>"; 	    
 	    $("#footPen").html(html); 	    
 	}
 	
	function templateNoticia(noticia) {		
		MM = noticia;
		var html = "";
		html += "<tr>";
		
		var noticiaId = noticia["@id"];
		//esta ñapa es porque no quieren meter el idioma por defecto (es) 
		// y magnolia, si no lo metes, tampoco filtra por el lang=pt aunque este contribuido
		var asunto = noticia.asunto;
		if (asunto === undefined) {
			asunto = noticia.asunto_pt;
		}
		if(noticia.notificar !== null && noticia.notificar === 'true' && checkIfReadNew('${userId}', noticiaId) === 'false'){
			html += "<td id="+noticiaId+"><strong>" + asunto + "</strong></td>";
		} else {
			html += "<td id="+noticiaId+">" + asunto + "</td>";
		}
		html += "<td>" + noticia.fechaView + "</td>";
		html += "<td align='center'> <a onclick='readNew(\"${userId}\",\""+noticiaId+"\",\""+noticia.enlace+"\"); return false;'" + " href='#' target='_blank'><img src='${ctx.contextPath}/.resources/cione-theme/webresources/img/iconos/icon-link.svg' alt='ver'></a></td>";
		/*
		html += "<td class='masinfo hide-mobile'><a class='masinforesponsive' href='javascript:void(0)' onclick='getLineasPedido(\"" + pedido.numPedido + "\",\"" + pedido.historico + 
		"\",\"" + tab + "\",this)'>${i18n['cione-module.templates.components.pedidos-component.more-info']}</a></td>";
		*/
		html += "</tr>";
		
		return html;
	}
 	
 	function initPage(){
 		loadNoticias('${idNoticia}');
 	}
 	
 	function readNew(userId, newId, finalLocation){
 		$.get("${ctx.contextPath}/.rest/users/news/v1/read?userId="+userId+"&newsId="+newId, function(data, status){
		   
 		console.log($('#' + newId).html());
 		window.open(finalLocation);
 		$('#' + newId).html($('#' + newId).html().replace("<strong>", "").replace("</strong>", ""));
 		
 		
		});
 		
 		
 		return false;
 	}
 	
 	function checkIfReadNew(userId, newId){
 		
 		var remote = $.ajax({
		    type: "GET",
		    url: "${ctx.contextPath}/.rest/users/news/v1/isRead?userId="+userId+"&newsId="+newId,
		    async: false
		}).responseText;
		
 		return remote;
 	}
 	  
 </script>	