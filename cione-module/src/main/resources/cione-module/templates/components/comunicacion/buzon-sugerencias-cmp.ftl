[#assign success = ctx.getParameter('success')!?html]
<script>
	success = "${success}";
</script>

<section class="cmp-buzonsugerencia">
		<h2 class="title">
			 ${i18n['cione-module.templates.components.buzon-sugerencias.buzon-sugerencias']}
		</h2>
        <p>
        	${i18n['cione-module.templates.components.buzon-sugerencias.descripcion']}
        </p>
        
        <div id="result" class="result" style="display:none"></div>

        <div class="wrapper-form">
            <form id="buzon-form" method="post" action="${ctx.contextPath}/.rest/private/buzon-sugerencias/v1/submit" class="data-panel inbox" enctype="multipart/form-data" onsubmit="return validateBuzon()" accept-charset="UTF-8">
                <fieldset>
                    <div class="item">
                        <label for="tema">
                            ${i18n['cione-module.templates.components.buzon-sugerencias.tema']}
                            <!-- <dfn title="required">*</dfn> -->
                        </label>
                        <input id="tema" name="tema" type="text" value="" class="form-control required">
                        <span id="tema-error" class="error"></span>
                    </div>
                    <div class="item">
                        <label for="sugerencia">
                            ${i18n['cione-module.templates.components.buzon-sugerencias.sugerencia']}
                            <!-- <dfn title="required">*</dfn> -->
                        </label>
                        <textarea id="sugerencia" name="sugerencia" rows="10" class="required"></textarea>
                        <span id="sugerencia-error" class="error"></span>
                    </div>

                    <div class="panelbuttons">
                        <label for="subirimagen">
                            <span>${i18n['cione-module.templates.components.buzon-sugerencias.subir-imagen']} +</span>
                        </label>
                        <input type="file" name="file" id="subirimagen" value="" onchange="loadFile()"  accept="image/jpeg,image/png" data-max-size="5242880" class="max-size-file">
                       	<span id="subirimagen-error" class="error"></span>
                        <input type="submit" value="${i18n['cione-module.templates.components.buzon-sugerencias.enviar']} >>">                        
                    </div>
                    
                    <input type="hidden" name="to" value="${content.destinatarios}"/>
                    <input type="hidden" id="from" name="from" value="${ctx.user.getProperty('title')}"/>
                    <input type="hidden" name="checksum" value="${content.destinatarios?length}"/>
                    <input type="hidden" id="nameFile" name="nameFile" value=""/>
                   	${ctx.response.setHeader("Cache-Control", "no-cache")}
       				<input type="hidden" name="csrf" value="${ctx.getAttribute('csrf')!''}" />
                    <div class="file-help">${i18n['cione-module.templates.components.buzon-sugerencias.info-fichero']}</div>
                    <div id="file-name" class="file-name"></div>
        		</fieldset>
       	 	</form>
    	</div>
</section>

<script>

	function resetBuzon(){
		$("input.error").each(function() {			
			$(this).removeClass("error");
		});
		$("textarea.error").each(function() {			
			$(this).removeClass("error");
		});
		$("span.error").each(function() {
			$(this).empty();			
		});		
	}

	function validateBuzon(){
		resetBuzon();
		var result = true;
		$(".required").each(function(index,inputRequired){			
			if($(inputRequired).val().trim() == ""){
				$(inputRequired).addClass("error");				
				$("#"+ inputRequired.id + "-error").html("Campo obligatorio");
				result = false;
			}			
		})
		
		$(".max-size-file").each(function(index,input){
			KK = input
			if(input.files.length>0 && input.dataset.maxSize && input.files[0].size > parseInt(input.dataset.maxSize,10)){
				$("#"+ input.id + "-error").html("El tamaño del fichero supera el máximo indicado");
				result = false;
			}
		});
		
		if(result){
			var url = $("#buzon-form")[0].action;
			url += "?tema=" + $("#tema").val();
			url += "&sugerencia=" + $("#sugerencia").val();
			url += "&from=" + $("#from").val();
			$("#buzon-form")[0].action = url;
		}
		
		
		return result;
	}
	
	function loadFile(){		
		var tmp = $("#subirimagen").val().split("\\");
		var fileName = tmp[tmp.length-1];		
		$("#file-name").html(fileName);
		$("#nameFile").val(fileName);
	}
	
	function initPage(){
		if(success==="true"){
			var text = "<div class='success'>";
        	text += "<p>${i18n['cione-module.templates.components.buzon-sugerencias.resultado-ok.titulo']}</p>";
        	text += "<p>${i18n['cione-module.templates.components.buzon-sugerencias.resultado-ok.texto']}</p>";
        	text += "</div>";
        	$("#result").html(text);
        	$("#result").show();
		}else if (success==="false"){
			var text = "<div class='error'>";
        	text += "<p>${i18n['cione-module.templates.components.buzon-sugerencias.resultado-ko.titulo']}</p>";
        	text += "<p>${i18n['cione-module.templates.components.buzon-sugerencias.resultado-ko.texto']}</p>";
        	text += "</div>";
        	$("#result").html(text);
        	$("#result").show();
		}else{
			
		}
	}
	
</script>