[#if cmsfn.editMode]

	<section class="cmp-table-usuarios">
	    <div class="panel-table">
	        <table class="table">
	            <thead>
	                <tr>
	                    <th>${i18n['cione-module.templates.components.list-employee-component.name']}</th>
	                    <th>${i18n['cione-module.templates.components.register-update-employee-component.code']}</th>
	                    <th>${i18n['cione-module.templates.components.register-update-employee-component.profile']}</th>
	                    <th></th>
	                </tr>
	            </thead>
	            <tbody>

	            </tbody>
	        </table>
	    </div>
	</section>

[#else]

	[#assign employees = model.employees!]
	
	<section class="cmp-table-usuarios">
	
	    <div class="panel-table">
	    
	        <table class="table">
	        
	        	[#-- cabecera --]
	            <thead>
	                <tr>
	                    <th>${i18n['cione-module.templates.components.list-employee-component.name']}</th>
	                    <th>${i18n['cione-module.templates.components.register-update-employee-component.code']}</th>
	                    <th>${i18n['cione-module.templates.components.register-update-employee-component.profile']}</th>
	                    <th></th>
	                </tr>
	            </thead>
	            
	            [#-- lista de empleados --]
	            <tbody>
	            
	                [#if employees?has_content]
		                [#list employees as employee]
		                
			                [#if employee.isEnabled()]
								[#assign icon = ctx.contextPath + "/.resources/cione-theme/webresources/img/iconos/heroicons-outline-lock-open.png"]
							[#else]
								[#assign icon = ctx.contextPath + "/.resources/cione-theme/webresources/img/iconos/heroicons-outline-lock-closed.png"]
							[/#if]
						
							<tr>
			                    <td><a href="./alta-empleado.html?id=${employee.name!""}"> ${employee.title!""}</a></td>
			                    <td> ${employee.name!}</td>
			                    <td> [#if employee.hasRole("empleado_cione_perfil_admin")]Administrador[#else][#if employee.hasRole("empleado_cione_perfil_employee")]Empleado[/#if][/#if]</td>
			                    <td class="masinfo">
			                        <a class="desactivar" href="#" onClick="removeUser('${employee.name!""}')"> <img src="${icon!""}" alt="Desactivar"></a>
			                    </td>
		                	</tr>
						[/#list]
	                [/#if]
	                
	            </tbody>
	        </table>
	        
	        <form action="./alta-empleado.html">
	            <div class="panelbuttons">
	                <button class="btn-blue" type="submit" style="cursor: pointer;">${i18n['cione-module.templates.components.register-update-employee-component.newuser']}</button>
	            </div>
	        </form> 
	
	    </div>
	    
	</section>
	
	<script>
		function removeUser(id){
		
			var data = {};
			data['id'] = id;
			data['token'] = '${model.token!}';
		
			$.ajax({
				type: 'POST',
				url: '${ctx.contextPath}/.rest/auth/v1/employee/remove',
			  	data: JSON.stringify(data),
			  	beforeSend: function() {
			  		$("#loading").show();
	        		$(".desactivar").css("pointer-events", "none");
	        		$(".desactivar").css("cursor", "default");
	    		},
			  	error: function( jqXHR, textStatus, errorThrown ) {
	        		alert("${i18n['cione-module.templates.components.register-update-employee-component.error']}");
	    		},
			  	success: function(response){
			  		location.reload();
			  	},
			  	contentType: 'application/json; charset=utf-8'
			});
		}
	</script>

[/#if]
