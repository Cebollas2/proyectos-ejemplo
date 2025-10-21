[#assign maxLevels = content.maxLevels!3]
[#assign targetNode = cmsfn.contentById(content.targetNode, "website")! /]
[#assign targetNodeDepth = targetNode.@depth!1]
[#assign currentLink = ctx.contextPath + state.originalBrowserURI] 
[#assign currentLink = currentLink?replace("//", "/")]
[#assign currentUser = ctx.user.name!]
[#assign userERP = model.getUserFromERP()!]

[#-- 
<div style="background:red">
	${cmsfn.page(content).title}	
	${currentLink}
</div>
 --]
 

 <div class="cmp-menuacordeon">
    <div class="contenedor-menu">
    	<a href="#" class="btn-menu">Menu<i class="icono fa fa-bars"></i></a>
		[@linkChildren targetNode targetNodeDepth maxLevels /]
		
	</div>	
</div>

[#--  The macro getting all the childNode nodes and calling itself recursive. --]
[#macro linkChildren node startNodeDepth maxLevels]
    [#list cmsfn.children(node, "mgnl:page") as childNode]
        
    <ul class="menu" style="">        
            	[#if cmsfn.children(childNode, "mgnl:page")?has_content]
            		<li class="isParent">
					[#-- tiene hijos --]
					<a href="#">						
						[#if childNode.iconMenu?has_content]
    						[#assign asset = damfn.getAsset(childNode.iconMenu)!]
							[#if asset?has_content && asset.link?has_content]        					
    							<img class="icono izquierda" src="${asset.link}" class="icon-menu-desktop" alt="">
							[/#if]
												        					
	      				[/#if]
		            	
		            	[#if state.locale=="es"]
	      					${childNode.title!childNode.@name}
	      				[#else]
	      					${childNode.title_pt!childNode.title!childNode.@name}			
	      				[/#if]
		            	
		            	[#if childNode.@depth < 6]
		            		<i class="icono derecha fa fa-chevron-down"></i>
		            	[#else] [#-- a partir del tercer nivel ocultamosel menu --]
					    	<i class="icono derecha fa fa-chevron-down"></i>
					    [/#if] 		            	     		            	      	 		            					
		            </a>		
				[#else]
					[#assign ocultarHija = false]
			  		[#if childNode.hideInNav?? && childNode.hideInNav?has_content && childNode.hideInNav]
			  			[#-- ocultamos la hija --]
			  		[#else]
					
						[#assign hidePageClass = '']
		        		[#if (childNode.@name=="simular-usuario")]
		        			[#assign hidePageClass = 'hidePage']
		        			[#if (ctx.getUser().hasRole('cione_superuser') || ctx.getUser().hasRole('optofive_superuser'))]
		        				[#assign hidePageClass = '']
		        			[/#if]
		        		[/#if]
		        		
		        		[#if (childNode.@name=="datos-satisfaccion") ]
		        			[#assign hidePageClass = 'hidePage']
		        			[#if (ctx.getUser().hasRole('cione_superuser'))]
		        				[#assign hidePageClass = '']
		        			[/#if]
		        		[/#if]
						
						<li class="${hidePageClass}">
						
						[#-- ${hidePageClass}  --] 
						[#assign activePageClass = (currentLink == cmsfn.link(childNode))?string('active','')]					
						[#-- ${activePageClass}  --]
											
						[#if childNode.subPageLink?has_content]
							[#if (currentLink == cmsfn.link("website", childNode.subPageLink))]
								[#assign activePageClass = 'active']
							[/#if]							        	 		
		        	 	[/#if]
						
						[#-- no tiene hijos --]
						[#assign link = cmsfn.link(childNode)!]
						
						[#assign target = ""]
						[#assign codUser = ""]
						[#if childNode.externalLink??][#if childNode.externalLink?has_content && model.getUserId()?has_content]
							[#assign link = childNode.externalLink!]
							[#assign link = link?replace("usercode", model.getUserId()!"")!]
							[#assign target = "_blank"]
						[/#if][/#if]
						
						[#if childNode.especialLink?? && childNode.especialLink?has_content 
							&& childNode.especialLink.field?? && childNode.especialLink.field?has_content
							&& childNode.especialLink.field == "enlaceForo"]
							[#if !ctx.getUser().hasRole("empleado_cione_comunidad")
								&& !ctx.getUser().hasRole("TALLERMAD")
					        	&& !ctx.getUser().hasRole("OPTCAN")
					        	&& !ctx.getUser().hasRole("cliente_monturas")
								&& !ctx.getUser().hasRole("OPTMAD")] 
								[#assign href = childNode.especialLink.linkName]
								[#assign target = "_blank"]
								
								<a href="${href}/auth.php?userid=${model.getNumSocioEncrypt()!}" target="${target}" class="${activePageClass} ${hidePageClass}"
									[#if childNode.colorfont?? && childNode.colorfont?has_content
									&& childNode.colorbackground?? && childNode.colorbackground?has_content] style="color: ${childNode.colorfont}; background-color: ${childNode.colorbackground}" [/#if] 
								
								> 
									[#-- <i class="icono izquierda fa fa-star"></i>  --]
									[#if childNode.iconMenu?has_content]
			    						[#assign asset = damfn.getAsset(childNode.iconMenu)!]
										[#if asset?has_content && asset.link?has_content]        					
			    							<img class="icono izquierda" src="${asset.link}" class="icon-menu-desktop" alt="">
										[/#if]		      					        					
				      				[/#if]		   
				      				
				      				[#if state.locale=="es"]
				      					${childNode.title!childNode.@name} 
				      				[#else]
				      					${childNode.title_pt!childNode.title!childNode.@name}			
				      				[/#if]     		      					      										 																
								</a>
							[/#if] 
						[#else]								
							<a href="${link!}" ${target!} class="${activePageClass} ${hidePageClass}"
								[#if childNode.colorfont?? && childNode.colorfont?has_content
								&& childNode.colorbackground?? && childNode.colorbackground?has_content] style="color: ${childNode.colorfont}; background-color: ${childNode.colorbackground}" [/#if] 
							
							>
								[#-- <i class="icono izquierda fa fa-star"></i>  --]
								[#if childNode.iconMenu?has_content]
		    						[#assign asset = damfn.getAsset(childNode.iconMenu)!]
									[#if asset?has_content && asset.link?has_content]        					
		    							<img class="icono izquierda" src="${asset.link}" class="icon-menu-desktop" alt="">
									[/#if]		      					        					
			      				[/#if]		   
			      				
			      				[#if state.locale=="es"]
			      					${childNode.title!childNode.@name} 
			      				[#else]
			      					${childNode.title_pt!childNode.title!childNode.@name}			
			      				[/#if]     		      					      										 																
							</a>
						[/#if]
					[/#if]
				[/#if]
    
            	[#assign childNodeDepth = childNode.@depth!2]
	            [#assign doRecursion = (childNodeDepth - startNodeDepth) < maxLevels]
	            [#if doRecursion!false]
	                [#--  The recursive call on the childNode again. --]
	                [@linkChildren childNode startNodeDepth maxLevels /]
	            [/#if]
            </li>
       
    </ul>
    [/#list]
[/#macro]


[#function showPage page] 
 	[#if childNode.@name == "simular-usuario" ]
 		[#return false]
 	[#else]
 		[#return true]
 	[/#if]	     
[/#function]

[#--

==========
  
<div class="cmp-menuacordeon">
    <div class="contenedor-menu">
		<a href="#" class="btn-menu">Menu<i class="icono fa fa-bars"></i></a>

		<ul class="menu">
			
			<li><a href="#">Pedido<i class="icono derecha fa fa-chevron-down"></i></a>
				<ul>
					<li><a href="#"><i class="icono izquierda fa fa-star"></i>Consulta de abono</a></li>
					<li><a href="#"><i class="icono izquierda fa fa-star"></i>Consulta de albaranes</a></li>
					<li><a href="#"><i class="icono izquierda fa fa-home"></i>Consulta de facturas</a></li>
					<li><a href="#"><i class="icono izquierda fa fa-star"></i><i class="icono derecha fa"></i>Consulta de envios</a></li>
				</ul>
            </li>
            
            <!--            
            <li><a href="#">Mis consumos</a>
            <li><a href="#">Kits y blister</a></li>
			<li><a href="#">Mis abonos</a></li>
            <li><a href="#">Ahorro</a></li>
            <li><a href="#"><i class="icono derecha fa fa-chevron-down"></i>otros facturacion</a>
                <ul>
					<li><a href="#"><i class="icono izquierda fa fa-envelope"></i>-----</a></li>
					<li><a href="#"><i class="icono izquierda fa fa-envelope"></i>-----</a></li>
					<li><a href="#"><i class="icono izquierda fa fa-share-alt"></i>-----</a></li>
					<li><a href="#"><i class="icono izquierda fa fa-share-alt"></i>-----</a></li>
				</ul>
            </li>
            -->
		</ul>
	</div>
</div>

 --]
