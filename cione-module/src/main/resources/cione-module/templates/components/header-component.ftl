[#if cmsfn.children(content)?has_content]
	[#list cmsfn.children(content) as image]
		[#assign hasPermissions = false]
		[#if image.permisos?? && image.permisos?has_content]
			[#assign field = image.permisos.field!]
			[#switch field]
				[#case "roles"]
					[#if model.hasPermissionsRoles(image.permisos.roles!)]
						[#assign content = image]
						[#break]
					[/#if]				
				[#case "campaing"]
					[#if model.hasPermissionsCampana(image.permisos.campaing!)]
						[#assign content = image]
						[#break]
					[/#if]
					
				[#default]
    				[#break]
    		[/#switch]
    	[#else]
    		[#assign content = image]
		[/#if]
	[/#list]
[/#if]

[#assign extendidoClass = ""]
[#if content.patrocinioList?? && content.patrocinioList?has_content]
	[#assign extendidoClass = "extendido"]	
[/#if]

[#--  --assign imgPatrocinio = content.patrocinioImage!]


[#assign hayPatrocinio = (imgPatrocinio?has_content || content.patrocinioTexto?has_content)]
[#assign extendidoClass = ""]
[#if hayPatrocinio]
	[#assign extendidoClass = "extendido"]	
[/#if --]


[#assign pageNode = cmsfn.page(content)!"unknown"]
[#assign noticiasLink = model.getNoticiasLink(cmsfn.asJCRNode(pageNode))]



<style>
a.flag:before {text-decoration:underline; display:inline-block;}
a.flag:before,
a.flagmenu:before {text-decoration:underline; display:inline-block;}
a.flagmenu:before,

a:hover:before {text-decoration:none;}

a.flag:before {
  margin-right: 5px;
  padding: 2px 3px 1px 3px;
  color: white;
  font-size: 13px;
  border-radius: 3px;
  font-weight: normal;
  position:absolute;
  margin: 10px;
  z-index: 1;
}

a.flagmenu:before {
  padding: 2px 3px 1px 3px;
  color: white;
  font-size: 13px;
  border-radius: 3px;
  font-weight: normal;
  z-index: 1;
}

a.flag.pdf:before {
  content: "PDF";
  background-color: #db090a;
  color: white;
  text-decoration: none !important;
}

a.flag.new:before {
  content: "NEW";
  background-color: red;
  color: white;
}

a.flagmenu.new:before {
  content: "NEW";
  background-color: red;
  color: white;
}
</style>

<section class="cmp-header ${extendidoClass}">
    <div class="container-fluid">
        <div class="d-flex justify-content-between">
             <div class="btn-menumobile">
                <span class="menu-global menu-top"></span>
                <span class="menu-global menu-middle"></span>
                <span class="menu-global menu-bottom"></span>
             </div>
            <div class="logo">
            	[#-- Imaging --]
				[#if content.logo?? && content.logo?has_content]
					[#assign link = damfn.getAssetLink("jcr:" + content.logo)!""]
					[#assign logoLink = "#"]
					[#if content.logoLink?has_content]
						[#assign logoLink = cmsfn.link("website", content.logoLink!)!]
					[/#if]
					<div class="banner-dockbar">
						<a href="${logoLink}">
							<img src="${link}" alt="${content.logoAlt!''}"/>
						</a>
					</div>
				[/#if]
				
				[#if content.patrocinioList?? && content.patrocinioList?has_content]
					[#list cmsfn.children(content.patrocinioList) as patrocinador]
						[#assign imgPatrocinio = patrocinador.image!]
						[#if patrocinador.image?? && patrocinador.image?has_content]
							[#assign imagenPatrocinio = damfn.getAssetLink("jcr:" + imgPatrocinio)!]
							[#assign enlacePatrocinio = ""]
							[#if patrocinador.linkPatrocinio?? && patrocinador.linkPatrocinio?has_content]
								[#assign fieldPatrocinio = patrocinador.linkPatrocinio.field]
								[#switch fieldPatrocinio]
									[#case "linkPdf"]
										[#assign documentAsset = cmsfn.contentById(patrocinador.linkPatrocinio.linkPdf, "dam")]
										[#assign link = cmsfn.link(documentAsset)!""]
										[#assign enlacePatrocinio = link!]
									[#break]
									[#case "linkUrl"]
										[#assign enlacePatrocinio = patrocinador.linkPatrocinio.linkUrl!]
									[#break]
									[#case "linkInternal"]
										[#assign enlacePatrocinio = cmsfn.link("website", patrocinador.linkPatrocinio.linkPdf!)!]
									[#break]
								[/#switch]
							[/#if]
							<div class="banner-dockbar">
								[#if enlacePatrocinio?has_content]
			            			<a href="${enlacePatrocinio!}" target="_blank">
			            				<img src="${imagenPatrocinio}" alt="">
			            			</a>
			            		[#else]
			            			<img src="${imagenPatrocinio}" alt="">
			            		[/#if]
							</div>
						[/#if]
					[/#list]
				[/#if]
					
				[#--  --if hayPatrocinio]
					[#assign enlacePatrocinio = ""]
					[#if content.linkPatrocinio?has_content]
						[#if content.linkPatrocinio.field?has_content && content.linkPatrocinio.field == "linkPdf"]
							[#if content.linkPatrocinio.linkPdf?has_content]
								[#assign documentAsset = cmsfn.contentById(content.linkPatrocinio.linkPdf, "dam")]
								[#assign link = cmsfn.link(documentAsset)!""]
								[#assign enlacePatrocinio = link!]
							[/#if]
						[/#if]
						[#if content.linkPatrocinio.field?has_content && content.linkPatrocinio.field == "linkUrl"]
							[#assign enlacePatrocinio = content.linkPatrocinio.linkUrl!]
						[/#if]
					[/#if]
					<div class="banner-dockbar">
		            	[#if imgPatrocinio?has_content]
		            		[#assign asset = damfn.getAssetLink("jcr:" + imgPatrocinio)!]
		            		[#if enlacePatrocinio?has_content]
		            			<a href="${enlacePatrocinio!}" target="_blank">
		            				<img class="img-responsive" src="${asset}" alt="${content.patrocinioTexto!}">
		            			</a>
		            		[#else]
		            			<img class="img-responsive" src="${asset}" alt="${content.patrocinioTexto!}">
		            		[/#if]	
						[#else]
							[#if content.patrocinioTexto?has_content]
								[#if enlacePatrocinio?has_content]
			            			<a href="${enlacePatrocinio!}" target="_blank">
			            				${content.patrocinioTexto}
			            			</a>
			            		[#else]
			            			<p>${content.patrocinioTexto}</p>
			            		[/#if]	
							[/#if]					            		            		
		        		[/#if]					
		            </div>
	            [/#if--]
            </div>
            
            
                        
            <div class="panel-header justify-content-end">
	            [#if !model.hasRoleComunication()]
	            	[#assign notificacion = model.hasUserReadRoles(ctx.getUser().getIdentifier())]
		            [#if notificacion > 0]
		            	<div class="panel-header_notifications"><a href="${noticiasLink!""}"><span class="icon-notifications"></span><span class="count">${notificacion}</span></a></div>
		            [/#if]
	            [/#if]
            	
            	[#-- SUPLANTACION --]
            	[#if (ctx.getUser().hasRole("cione_superuser") || ctx.getUser().hasRole("optofive_superuser")) && ctx.user.getProperty("impersonateIdSocio")?has_content]

                    <div class="panel-sup-wrapper hidden-lg-down">

                        <div class="panel-sup-info">

                            Suplantando a ${ctx.user.getProperty("impersonateNameSocio")} (${ctx.user.getProperty("impersonateIdSocio")})                        

                            <a href="#" onclick="logoutSimulateUser()">[${i18n.get('cione-module.global.salir')}]</a>

                        </div>                 

                        <div class="name">${i18n.get('cione-module.templates.header-component.login.welcome')}, ${ctx.user.getProperty("title")}</div>

                    </div>

                [#else]

                    <div class="name">${i18n.get('cione-module.templates.header-component.login.welcome')}, ${ctx.user.getProperty("title")}</div>

                [/#if]
            	
                <div class="status d-flex align-items-center">${i18n.get('cione-module.templates.header-component.login.logout')}
                    <span>
                        <a href="${cmsfn.link(cmsfn.page(content))!}?mgnlLogout=true">
                    	   <img src="${ctx.contextPath}/.resources/cione-theme/webresources/img/iconos/logout.svg" alt="status">
                        </a>                   	      	
                    </span>
                </div>
                <div class="idioma d-flex">
                    <a href="#" onclick="changeLang('es')">ES</a>
                    <a href="#" onclick="changeLang('pt')">PT</a>
                     
                </div>
            </div>
            
            <div class="panel-header-mobile">
                <span class="abrir-menu"></span>
                <ul>
                    <li class="icon-onoff"></li>
                    <li class="icon-telefono"></li>
                    <li class="icon-mail"></li>
                    <li class="icon-idioma" data-toggle="modal" data-target="#modalidioma"></li>
                </ul>
            </div>
        </div>
    </div>
    
    <div class="content-menumobile"></div>

    <section class="modal-idioma">
        <!-- The Modal -->
        <!-- Button trigger modal -->

        <!-- Modal -->
        <div class="modal fade" id="modalidioma" tabindex="-1" role="dialog" aria-labelledby="modalidiomaLabel"
            aria-hidden="true">
            <div class="modal-dialog modal-sm" role="document">
                <div class="modal-content">
                    <div class="title">${i18n.get('cione-module.templates.header-component.modallanguage.choose')}</div>

                    <div class="content-radio">
                        <label class="container">${i18n.get('cione-module.templates.header-component.modallanguage.spanish')} 
                            <input type="radio" id="r1" [#if cmsfn.isCurrentLocale("es")] checked="checked" [/#if] name="radio" value="es">
                            <span class="checkmark"></span>
                        </label>

                        <label class="container">${i18n.get('cione-module.templates.header-component.modallanguage.portuguese')} 
                            <input type="radio" id="r2" [#if cmsfn.isCurrentLocale("pt")] checked="checked" [/#if] name="radio" value="pt">
                            <span class="checkmark"></span>
                        </label>
                    </div>

                    <div class="modal-footer">
                        <div type="button" id="bclose" class="btn-modal-cione" data-dismiss="modal">${i18n.get('cione-module.templates.header-component.modallanguage.close')}</div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</section>

[#assign userERP = model.getUserFromERP()!]
[#if content.targetNode?has_content]
	[#assign maxLevels = content.maxLevels!4]
	[#assign targetNode = cmsfn.contentById(content.targetNode, "website")! /]
	[#assign targetNodeDepth = targetNode.@depth!1]
	[#assign currentLink = ctx.contextPath + state.originalBrowserURI]
	
	[#assign hideMenu = cmsfn.page(content).hideMenu!false]
	[#if !hideMenu]
	
	<section class="cmp-menu">
  		<nav class="navbar">
	  	<ul>
	  	[#assign cumpleMinOpcion = false]
	  	
	  	[#list cmsfn.children(targetNode, "mgnl:page") as node]
	  		[#assign mostrar = true]
	  		[#if node.hideInNav?? && node.hideInNav?has_content && node.hideInNav]
	  			[#assign mostrar = false]
	  		[/#if]
	  		[#if !mostrar]  			
	  			[#-- la página no se muestra en el menu --]
	  		[#else]
	  			[#assign cumpleMinOpcion = true]
			  	<li class="nav-item dropdown" >
			  		[#assign href = "#"]
			  		[#assign target = ""]
			  		[#assign flag = false]
			  		
			  		[#assign field = ""]
			  		[#if node.especialLink?? && node.especialLink?has_content && node.especialLink.field??]
			  			[#assign field = node.especialLink.field!]
			  			[#assign flag = true]
			  		[/#if]	
			  		[#if node.externalLink?has_content]
						[#assign href = node.externalLink!]
						[#assign target = "_blank"]
					[#elseif node.subPageLink?has_content]
						[#if cmsfn.contentById(node.subPageLink)?? && cmsfn.contentById(node.subPageLink)?has_content]
							[#assign href = cmsfn.link(cmsfn.contentById(node.subPageLink)!)!""]
						[/#if]
					[/#if]	
					
					[#if flag]
						[#switch field]
							[#case "enlaceUniversity"]
								[#if !ctx.getUser().hasRole("empleado_cione_cione_university")]
									[#assign href = node.especialLink.linkName]
						            [#assign target = "_blank"]
						            
									[#if userERP?? && userERP?has_content]
										<a class="nav-link dropdown-toggle" href="#" onClick="updateUniversityUser('${href}')" id="navbardrop" data-toggle="dropdown">
											[#assign asset = ""]
								      		[#if node.iconMenu?? && node.iconMenu?has_content]
							      				[#if node.iconMenu?contains("jcr:")]
							      					[#assign asset = damfn.getAssetLink(node.iconMenu)!]
							      				[#else]
							      					[#assign asset = damfn.getAssetLink("jcr:" + node.iconMenu)!""]
							      				[/#if]
								      		[/#if]
								      		<img src="${asset}" class="icon-menu-desktop" alt="">
								      		
								      		[#assign asset = ""]
								      		[#if node.iconMenuMovil?? && node.iconMenuMovil?has_content]
							      				[#if node.iconMenuMovil?contains("jcr:")]
							      					[#assign asset = damfn.getAssetLink(node.iconMenuMovil)!]
							      				[#else]
							      					[#assign asset = damfn.getAssetLink("jcr:" + node.iconMenuMovil)!""]
							      				[/#if]
								      		[/#if]
								      		<img src="${asset}" class="icon-menu-movile" alt="">
											
											[#if state.locale=="es"]
												${node.title!childNode.@name}
											[#else]
												[#if node.title_pt?has_content]
													${node.title_pt!childNode.title!childNode.@name}
												[#else]
													${node.title!childNode.@name}
												[/#if]
											[/#if]
										</a>
									[/#if]
									
								[/#if]
								[#break]
							[#case "enlaceAudio"]
								[#if !ctx.getUser().hasRole("empleado_cione_myom")]
									[#assign key = node.especialLink.key]
					            	[#assign href = node.especialLink.linkName]
						            [#assign target = "_blank"]
						            [#if userERP?? && userERP?has_content]
						            	<a class="nav-link dropdown-toggle" href="#" onClick="updateUser('${href}', '${key}', '${userERP.numSocio!}')" id="navbardrop" data-toggle="dropdown">
								      		[#assign asset = ""]
								      		[#if node.iconMenu?? && node.iconMenu?has_content]
							      				[#if node.iconMenu?contains("jcr:")]
							      					[#assign asset = damfn.getAssetLink(node.iconMenu)!]
							      				[#else]
							      					[#assign asset = damfn.getAssetLink("jcr:" + node.iconMenu)!""]
							      				[/#if]
								      		[/#if]
								      		<img src="${asset}" class="icon-menu-desktop" alt="">
								      		
								      		[#assign asset = ""]
								      		[#if node.iconMenuMovil?? && node.iconMenuMovil?has_content]
							      				[#if node.iconMenuMovil?contains("jcr:")]
							      					[#assign asset = damfn.getAssetLink(node.iconMenuMovil)!]
							      				[#else]
							      					[#assign asset = damfn.getAssetLink("jcr:" + node.iconMenuMovil)!""]
							      				[/#if]
								      		[/#if]
								      		<img src="${asset}" class="icon-menu-movile" alt="">
								      		
								      		[#if state.locale=="es"]
						      					${node.title!childNode.@name}
						      				[#else]
						      					[#if node.title_pt?has_content]
						      						${node.title_pt!childNode.title!childNode.@name}
						      					[#else]
						      						${node.title!childNode.@name}
						      					[/#if]
						      				[/#if]
								        </a>
						            [/#if]
								[/#if]
								[#break]
							[#case "enlaceForo"]
								[#if !ctx.getUser().hasRole("empleado_cione_comunidad")]
									[#assign href = node.especialLink.linkName]
						            [#assign target = "_blank"]
							    	<a class="nav-link dropdown-toggle" href="${href}/auth.php?userid=${model.getNumSocioEncrypt()!}" target="${target}">
							    		[#if state.locale=="es"]
					      					${node.title!childNode.@name}
					      				[#else]
					      					[#if node.title_pt?has_content]
					      						${node.title_pt!childNode.title!childNode.@name}
					      					[#else]
					      						${node.title!childNode.@name}
					      					[/#if]
					      				[/#if]
							    	</a>
								[/#if]
								[#break]
							[#case "enlaceServicios"]
								[#if !ctx.getUser().hasRole("empleado_cione_servicios")]
									<a class="nav-link dropdown-toggle" href="#" onclick="sooServicios()">
							    		[#assign asset = ""]
							      		[#if node.iconMenu?? && node.iconMenu?has_content]
						      				[#if node.iconMenu?contains("jcr:")]
						      					[#assign asset = damfn.getAssetLink(node.iconMenu)!]
						      				[#else]
						      					[#assign asset = damfn.getAssetLink("jcr:" + node.iconMenu)!""]
						      				[/#if]
							      		[/#if]
							      		<img src="${asset}" class="icon-menu-desktop" alt="">
							      		
							      		[#assign asset = ""]
							      		[#if node.iconMenuMovil?? && node.iconMenuMovil?has_content]
						      				[#if node.iconMenuMovil?contains("jcr:")]
						      					[#assign asset = damfn.getAssetLink(node.iconMenuMovil)!]
						      				[#else]
						      					[#assign asset = damfn.getAssetLink("jcr:" + node.iconMenuMovil)!""]
						      				[/#if]
							      		[/#if]
							      		<img src="${asset}" class="icon-menu-movile" alt="">
										
										[#if state.locale=="es"]
											${node.title!childNode.@name}
										[#else]
											[#if node.title_pt?has_content]
												${node.title_pt!childNode.title!childNode.@name}
											[#else]
												${node.title!childNode.@name}
											[/#if]
										[/#if]
							    	</a>
								[/#if]
								[#break]
							[#default]
			    				[#break]
			    		[/#switch]
						
				    [#else]	
						<a class="nav-link dropdown-toggle" href="${href}" id="navbardrop" data-toggle="dropdown" target="${target}">
				      		[#assign asset = ""]
				      		[#if node.iconMenu?? && node.iconMenu?has_content]
			      				[#if node.iconMenu?contains("jcr:")]
			      					[#assign asset = damfn.getAssetLink(node.iconMenu)!]
			      				[#else]
			      					[#assign asset = damfn.getAssetLink("jcr:" + node.iconMenu)!""]
			      				[/#if]
				      		[/#if]
				      		<img src="${asset}" class="icon-menu-desktop" alt="">
				      		
				      		[#assign asset = ""]
				      		[#if node.iconMenuMovil?? && node.iconMenuMovil?has_content]
			      				[#if node.iconMenuMovil?contains("jcr:")]
			      					[#assign asset = damfn.getAssetLink(node.iconMenuMovil)!]
			      				[#else]
			      					[#assign asset = damfn.getAssetLink("jcr:" + node.iconMenuMovil)!""]
			      				[/#if]
				      		[/#if]
				      		<img src="${asset}" class="icon-menu-movile" alt="">
				      		
				      		[#if state.locale=="es"]
		      					${node.title!childNode.@name}
		      				[#else]
		      					[#if node.title_pt?has_content]
		      						${node.title_pt!childNode.title!childNode.@name}
		      					[#else]
		      						${node.title!childNode.@name}
		      					[/#if]
		      				[/#if]
				        </a>
				    [/#if]
					[@linkMenuChildren node targetNodeDepth maxLevels /]
			    </li>
		    [/#if]
	  	[/#list]
	  	[#if !cumpleMinOpcion]
	  		<li class="nav-item dropdown" ></li>
	  	[/#if]
	  	</ul>
	  </nav>
	</section>
	[/#if]

	[#macro linkMenuChildren node startNodeDepth maxLevels]
	    [#list cmsfn.children(node, "mgnl:page")]
	    <ul class="dropdown-menu element-animation">
	        [#items as childNode]
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
	        		
	        		[#assign ocultarHija = false]
			  		[#if childNode.hideInNav?? && childNode.hideInNav?has_content && childNode.hideInNav]
			  			[#-- ocultamos la hija --]
			  		[#else]
			  			<li class="${hidePageClass}">              
		            	[#if cmsfn.children(childNode, "mgnl:page")?has_content]
							[#-- tiene hijos --]
							<a class="dropdown-item dropdown-toggle" href="javascript:void(0)">
								[#if childNode.iconMenu?has_content]
		        				[#assign asset = damfn.getAsset(childNode.iconMenu)!]
			        				[#if asset?has_content && asset.link?has_content]     
										<img src="${asset.link}" class="icon-menu-desktop" alt="">
									[#else]
										[#assign asset_new = damfn.getAssetLink("jcr:" + node.iconMenu)!""]
										<img src="${asset_new}" class="icon-menu-desktop" alt="">
									[/#if]
				      			[/#if]			      			
								[#if state.locale=="es"]
			      					${childNode.title!childNode.@name}
			      				[#else]
			      					[#if childNode.title_pt?has_content]
			      						${childNode.title_pt!childNode.title!childNode.@name}
			      					[#else]
			      						${childNode.title!childNode.@name}
			      					[/#if]
			      				[/#if]
							</a>	
							
							[#assign childNodeDepth = childNode.@depth!2]
			            	[#assign doRecursion = (childNodeDepth - startNodeDepth) < maxLevels]
			            	[#if doRecursion!false]
			                	[#--  The recursive call on the childNode again. --]
			                	[@linkMenuChildren childNode startNodeDepth maxLevels /]
			            	[/#if]
																						
					    [#else]
							[#assign target = ""]
					  		[#if childNode.externalLink?has_content]
								[#assign href = childNode.externalLink!]
								[#assign href = href?replace("usercode", model.getUserId()!"")!]
								[#assign target = "_blank"]
							[#else]
								[#assign href = cmsfn.link(childNode)!""]
							[/#if]
							
							[#if childNode.especialLink?? && childNode.especialLink?has_content 
								&& childNode.especialLink.field?? && childNode.especialLink.field?has_content
								&& childNode.especialLink.field == "enlaceForo"]
								
								[#if !ctx.getUser().hasRole("empleado_cione_comunidad")]
						            [#assign href = childNode.especialLink.linkName]
						            [#assign target = "_blank"]
							    	<a class="dropdown-item" href="${href}/auth.php?userid=${model.getNumSocioEncrypt()!}" target="${target}">
							    		[#if state.locale=="es"]
					      					${childNode.title!childNode.@name}
					      				[#else]
					      					[#if childNode.title_pt?has_content]
					      						${childNode.title_pt!childNode.title!childNode.@name}
					      					[#else]
					      						${childNode.title!childNode.@name}
					      					[/#if]
					      				[/#if]
							    	</a>
							    [/#if]
				    		[#else]
								<a class="dropdown-item" href="${href}" target="${target}">
									[#if childNode.iconMenu?has_content]
			        				[#assign asset = damfn.getAsset(childNode.iconMenu)!]
				        				[#if asset?has_content && asset.link?has_content]     
											<img src="${asset.link}" class="icon-menu-desktop" alt="">
										[#else]
											[#assign asset_new = damfn.getAssetLink("jcr:" + node.iconMenu)!""]
											<img src="${asset_new}" class="icon-menu-desktop" alt="">
										[/#if]
					      			[/#if]			      			
								  	[#if state.locale=="es"]
				      					${childNode.title!childNode.@name}
				      				[#else]
				      					[#if childNode.title_pt?has_content]
				      						${childNode.title_pt!childNode.title!childNode.@name}
				      					[#else]
				      						${childNode.title!childNode.@name}
				      					[/#if]
				      				[/#if]
								</a>
							[/#if]
					    [/#if]
						</li> 
			  	[/#if]
	        [/#items]      
	    </ul>
	    [/#list]
	[/#macro]
[#else]
	<section class="cmp-menu" style="height: 65px;"></section>
[/#if]


[#assign isHome = state.originalBrowserURI?ends_with("home.html")]
[#if isHome]
	<style>
		.cmp-menu{
			opacity: 0.0;			
		}
	</style>
[/#if]

    <div id="modalAudio" class="modal modal-anuncio" tabindex="-1" role="dialog">
	    <div class="modal-dialog" role="document">
	      <div class="modal-content" style="height: 190px;">
	        <div class="modal-body">
	        	<div id="modal-anuncio">
	        		<div id="modal-anuncio-text"></div>
	        		<div class="panelbuttonAudio" style="padding-top: 30px;">
            			<a href="javascript:closeModalAudio()" class="closemodal">${i18n['cione-module.templates.components.menu-home-component.close']}</a>
            		</div>
            	</div>
	        </div>
	      </div>
	    </div>
	</div>


<script>
    var btn = document.getElementById('bclose');
    btn.onclick = function() {
        if (document.getElementById('r1').checked) {
            changeLang('es');
        } else if (document.getElementById('r2').checked) {
            changeLang('pt');   
        }
    };

    var listItems = document.querySelectorAll("ul li");
    listItems[0].onclick = function(e) {
        window.location.replace("${cmsfn.link(cmsfn.page(content))!}?mgnlLogout=true"); 
    }
    listItems[1].onclick = function(e) {
        window.location.replace("tel:+34916402970"); 
    }
    listItems[2].onclick = function(e) {
        window.location.replace("mailto:info@cione.es"); 
    }
    
    CURRENT_USER = "${ctx.user.name!}";
    
    function logoutSimulateUser() {		
		//alert("simular socio" + numSocio);
		var url = PATH_API + "/private/impersonate/v1/delete-impersonate" + "?lang=" + LANG + "&usernameImpersonator=" + CURRENT_USER;
		
		$("#loading").show();		
		$.ajax({
			url : url,
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			success : function(response) {	
				//KK = response
				//alert("OK");
				window.location = "${cmsfn.link(cmsfn.page(content))!}?mgnlLogout=true";
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
	
	var jwtLoginUniversity = '';
	
	function updateUniversityUser(url) {
		getTokenUniversity(url);
		if (jwtLoginUniversity != '') {
			loginUniversity(url, jwtLoginUniversity);
		}
	}

	function getTokenUniversity(url) {
		
		var urlRest = PATH_API + "/private/login-university/v1/get_token?url=" + url;

		$.ajax({
			type: 'GET',
			url: urlRest,
			async: false,
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			error : function(response) {
				getUserUniversity(url, idUsuario)
			},
			success: function(response){
				if (response.success) {
					var jwt = response.data.jwt;
					//loginUniversity(url, jwt);
					jwtLoginUniversity = jwt;
				} else {
					getUserUniversity(url);
				}
			}
		});
	}

	function loginUniversity(url, jwt) {
		
		var urlRest = url + "/auth/autologin/login.php?token=" + jwt;
		window.open(
		  urlRest,
		  '_blank' 
		);
	}

	function getUserUniversity(url) {
		var urlRest = PATH_API + "/private/login-university/v1/get_user?url=" + url;

		$.ajax({
			type: 'POST',
			url: urlRest,
			async: false,
			cache : false,
			dataType : "xml",
			error : function(response) {
				$("#modal-anuncio-text").html("<p>${i18n['cione-module.templates.components.menu-home-component.error-access-university']} </p>");
				openModalAudio();
			},
			success: function(response){
				//success = $(response).find("KEY[name='success']");
                //if (success.text().trim() == "0") {
					registerUserUniversity(url)
				//}
			}
		});
	}

	function registerUserUniversity(url) {
		[#if userERP?? && userERP?has_content]
			var urlRest = PATH_API + "/private/login-university/v1/create_user?url=" + url;

			$.ajax({
				type: 'POST',
				url: urlRest,
				async: false,
				cache : false,
				dataType : "xml",
				error : function(response) {
					$("#modal-anuncio-text").html("<p>${i18n['cione-module.templates.components.menu-home-component.error-access-university']} </p>");
					openModalAudio();
				},
				success: function(response){
					success = $(response).find("KEY[name='success']");
	                if (success.text().trim() == "1") {
						getTokenUniversity(url)
					} else {
						$("#modal-anuncio-text").html("<p>${i18n['cione-module.templates.components.menu-home-component.error-access-university']} </p>");
						openModalAudio();
					}
					
				}
			});
		[/#if]
	}
	
	function loginForum(url, idUsuario) {
		[#if userERP?? && userERP?has_content]
			var urlRest = PATH_API + "/private/my-data/v1/login_foro?url=" + url+ "&user="+idUsuario;
			//var urlRest = "https://precomunidad.cione.es/auth.php?userid="+idUsuario;
			var data = {}
			data['url'] = url;
			data['user'] = idUsuario;
			
			// llamada al endpoint para el formulario de login
			$.ajax({
				type: 'POST',
				url: urlRest,
				async: false,
				contentType : 'application/json; charset=utf-8',
				cache : false,
	            dataType : "json",
				data: JSON.stringify(data),
			  	error : function(response) {
			  		//el usuario no puede acceder a myom
	                console.log('Error al acceder al foro');
	    		},
			  	success: function(response){
			  		//usuario actualizado, obtenemos el token para poder acceder
			  		window.location.href = url+"/auth.php?userid="+response.token;
			  	}
			});
		[/#if]
	}

	function updateUser(url, key, idUsuario) {
	
	//pasos 1- actualiza usuario (existe (actualiza) no existe (se da de alta), obtiene el token, se loga  accede)
	//		 - no puede actualizar el usuario  (no existe, se revisa si se puede dar de alta, se da de alta, se loga y accede)
	
	[#if userERP?? && userERP?has_content]
		[#if userERP.nivelOM?? && userERP.nivelOM!=""]
			var key2 = encodeURIComponent(key);
			var urlRest = url + "/wp-json/om_ws/v1/replicate_user";
			
			var data = {}
				data['username'] = idUsuario;
				data['AUTH_KEY'] = key2;
				data['first_name'] = '${userERP.personaContacto!}';
				if (data['first_name'] =='') {
					data['first_name'] = '${userERP.nombreComercial!}';
				}
				//data['email'] = '${userERP.email!}';
				data['email'] = idUsuario + '@cione.es';
				data['auth'] = '${userERP.nivelOM!}';
				data['contacto'] = '${userERP.nombreComercial!}'; 
			
			// llama al endpoint para alta de usuario
			$.ajax({
				type: 'POST',
				url: urlRest,
				async: false,
				contentType : 'application/json; charset=utf-8',
				cache : false,
	            dataType : "json",
				data: JSON.stringify(data),
			  	error : function(response) {
			  		//el usuario no puede acceder a myom
	                $("#modal-anuncio-text").html("<p>${i18n['cione-module.templates.components.menu-home-component.error-access']} </p>");
	    		},
			  	success: function(response){
			  		//usuario actualizado, obtenemos el token para poder acceder
			  		if (response.status == 'ERR') {
			  			$("#modal-anuncio-text").html("<p>${i18n['cione-module.templates.components.menu-home-component.error-access']} </p>");
			  			openModalAudio();
			  		} else {
			  			getToken(url, key, idUsuario)
			  		}
			  	}
			});
		[#else]
			$("#modal-anuncio-text").html("<p>${i18n['cione-module.templates.components.menu-home-component.error-access']} </p>");
			openModalAudio();
		[/#if]
	[#else]
		$("#modal-anuncio-text").html("<p>${i18n['cione-module.templates.components.menu-home-component.error-access']} </p>");
		openModalAudio();
	[/#if]
	}
            
    function getToken(url, key, idUsuario) {
		
		var key2 = encodeURIComponent(key);
		
		var urlRest = url + "?rest_route=/mycione/login/auth&username=" + idUsuario + "&AUTH_KEY=" + key2;
		
		$.ajax({
			type: 'POST',
			url: urlRest,
			contentType : 'application/json; charset=utf-8',
			cache : false,
            dataType : "json",
		  	error : function(response) {
		  		//credenciales incorrectas
		  		//getRegister(url, key, idUsuario); este metodo era por el flujo antiguo
		  		$("#modal-anuncio-text").html("<p>${i18n['cione-module.templates.components.menu-home-component.error-access']} </p>");
                openModalAudio();
    		},
		  	success: function(response){
		  		var jwt = response.data.jwt;
		  		//el token existe procede y es valido para el usuario, hacemos el login
		  		callAudio(url, jwt);
		  		
		  	}
		});
	}
	
    function getRegister(url, key, idUsuario) {
    	[#if userERP?? && userERP?has_content]
	    	var register = false;
			
			var key2 = encodeURIComponent(key);
			var urlRest = url + "/wp-json/om_ws/v1/get_user";
			var data = {}
			data['username'] = idUsuario;
			data['AUTH_KEY'] = key2;
			data['first_name'] = '${userERP.personaContacto!}';
			//data['last_name'] = '';
			data['email'] = '${userERP.email!}';
			data['auth'] = '${userERP.nivelOM!}';
			data['contacto'] = '${userERP.nombreComercial!}'; 
			
			
			//primero hacemos las peticion para saber si el usuario puede ser dado de alta en myom
			$.ajax({
				type: 'POST',
				url: urlRest,
				async: false,
				dataType : "json",
				data: JSON.stringify(data),
				contentType : 'application/json; charset=utf-8',
				cache : false,
	            dataType : "json",
			  	error : function(response) {
			  		
	                $("#modal-anuncio-text").html("<p>${i18n['cione-module.templates.components.menu-home-component.error-access']} </p>");
	    		},
			  	success: function(response){
			  		registerUser(url, key, idUsuario);
			  	}
			});
		[/#if]
	}
	
    function registerUser(url, key, idUsuario) {
    	
		[#if userERP?? && userERP?has_content]
		var key2 = encodeURIComponent(key);
		var urlRest = url + "/wp-json/om_ws/v1/create_user";
		
		//var urlRest = url + "?username=" + idUsuario + "&AUTH_KEY=" + key2;
		var data = {}
			data['username'] = idUsuario;
			data['AUTH_KEY'] = key2;
			data['first_name'] = '${userERP.nombreComercial!}';
			data['last_name'] = '';
			data['email'] = '${userERP.email!}';
			data['auth'] = '${userERP.nivelOM!}';
			data['contacto'] = '${userERP.personaContacto!}';
		
		
		$.ajax({
			type: 'POST',
			url: urlRest,
			async: false,
			contentType : 'application/json; charset=utf-8',
			cache : false,
            dataType : "json",
			data: JSON.stringify(data),
		  	error : function(response) {
                $("#modal-anuncio-text").html("<p>${i18n['cione-module.templates.components.menu-home-component.error-access']} </p>");
    		},
		  	success: function(response){
		  		if(response.status == 'ERR') {
		  	        $("#modal-anuncio-text").html("<p>${i18n['cione-module.templates.components.menu-home-component.error-access']} </p>");
		  	    } else {
		  	    	$("#modal-anuncio-text").html("<p>${i18n['cione-module.templates.components.menu-home-component.success']} </p>");
		  	    }
		  	}
		});
		[#else]
			$("#modal-anuncio-text").html("<p>${i18n['cione-module.templates.components.menu-home-component.error-access']} </p>");
		[/#if]
	}
	
	function callAudio(url, jwt) {
		//hacemos login con el jwt token del usuario y abrimos myom
		var urlRest = url + "?rest_route=/mycione/login/autologin&token=" + jwt;
		window.open(
		  urlRest,
		  '_blank' 
		);
	}
	
    function closeModalAudio(){
		$("#modalAudio").hide();
	}
	
    function openModalAudio(){
		$("#modalAudio").show();
	}
	
	function sooServicios() {
		var urlRest = PATH_API + "/auth/v1/ssoServicios";
		var urlSSO = "";
		$.ajax({
			type: 'GET',
			url: urlRest,
			async: false,
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			error : function(response) {
				alert("Ha ocurrido un problema al acceder a la plataforma, contacte con el administrador");
			},
			success: function(response){
				urlSSO = response.url;
			}
		});
		
		if (urlSSO != "") {
			window.open(
			  urlSSO,
			  '_blank' 
			);
		}
			
	}

</script>

