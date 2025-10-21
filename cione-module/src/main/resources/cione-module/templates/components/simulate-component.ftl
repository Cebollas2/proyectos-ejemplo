<section class="cmp-pedidosSuplantarUsuario">
	<form id="formSuplantarUsuario">
		<div class="panel-filter">
			<div class="filter">
				<label>Codigo socio</label><input id="idClient" name="idClient" type="text" value="">
			</div>
			<div class="filter">
				<label>Nombre</label> <input id="fullName" name="fullName" type="text" value="">
			</div>
		</div>
		<div class="panelbuttons">
			<button class="btn-blue icon-search" type="submit"
				onclick="searchUsers(); return false;">Buscar</button>
		</div>
	</form>
	<div id="simulate-result" class="text-success"></div>
	<div class="panel-table">
		<table class="table">
			<thead>
				<tr>
					<th class="cod">Codigo Socio</th>
					<th>Nombre</th>
				</tr>
			</thead>
			<tbody id="users-table-data">				
			</tbody>
		</table>
		<!-- 
		<div class="foot">
			<span>Mostrando 10 de <span class="cantrow">15</span>
				resultados
			</span>
		</div>
		 -->
	</div>
</section>

<script>
	function templateUser(user) {		
		var html = "";
		html += "<tr>";
		html += "<td class='cod'><a href='#' onclick='simulateUser(\"" + user.numSocio + "\"); return false;'>" + user.numSocio + "</a></td>";
		html += "<td>" + user.nombreComercial + "</td>";
		html += "</td>";
		  
		return html;
	}

	function searchUsers() {
		var url = PATH_API + "/private/impersonate/v1/users";		
		filter = getFormData($("#formSuplantarUsuario"));
		
		 if(filter.idClient == "" && filter.fullName == ""){
			alert("es necesario añadir un criterio de búsqueda");
			return;
		}
		 
		if(filter.idClient != ""){			
			url = addToUrl(url,"idClient=" + filter.idClient);
		}
		
		if(filter.fullName != ""){			
			url = addToUrl(url,"fullName=" + filter.fullName);
		}
		
		
		$("#users-table-data").html("");
		$("#loading").show();
		$.ajax({
			url : url,
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			success : function(response) {				
				var results = [];
				if (response.length == 0) {
					results.push(templateNoRecordsFoundForTable(4));
				}else{
					response.forEach(function(user){
						results.push(templateUser(user));
					});	
				}				
				$("#users-table-data").append(results.join(" "));				
			},
			error : function(response) {
				alert("error");
			},
			complete : function(response) {
				$("#loading").hide();
			}
		});
		
		return false;
	}

	function simulateUser(numSocio) {		
		//alert("simular socio" + numSocio);
		var url = PATH_API + "/private/impersonate/v1/impersonate?usernameToImpersonate=" + numSocio + "&usernameImpersonator=" + CURRENT_USER + "&lang=" + LANG;
		
		$("#loading").show();		
		$.ajax({
			url : url,
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			success : function(response) {							
				$("#simulate-result").html(response.txt);	   
			},
			error : function(response) {
				alert("Se ha producido un error");
			},
			complete : function(response) {
				$("#loading").hide();
			}
		});				
		return false;
	}
</script>