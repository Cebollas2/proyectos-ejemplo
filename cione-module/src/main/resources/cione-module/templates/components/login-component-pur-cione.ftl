<section class="cmp-login">

    [#include "../includes/macros/public-logo.ftl"] 
    
    [#assign isOpticaPRO = false]
    [#assign site = sitefn.site(content)]
    [#if site.name == "opticapro"]
    	[#assign isOpticaPRO = true]
    [/#if]
    
    <form class="form-login" id="loginForm" method="post" enctype="application/x-www-form-urlencoded">
    	
        <input type="hidden" name="mgnlRealm" value="${content.realm!'public'}"/>
		

		<input type="hidden" name="mgnlModelExecutionUUID" value="${content.@uuid}"/>
		[#if content.targetPage?has_content]
            <input type="hidden" name="mgnlReturnTo" value="${cmsfn.link("website", content.targetPage)!}"/>
        [#elseif content.targetPageExternal?has_content]
        	<input type="hidden" name="mgnlReturnTo" value="${content.targetPageExternal}"/>
        [/#if]
		
		${ctx.response.setHeader("Cache-Control", "no-cache")}
        <input type="hidden" name="csrf" value="${ctx.getAttribute('csrf')!''}" />
        
        <h2 class="title [#if isOpticaPRO] opticapro[/#if]">${i18n['cione-module.templates.components.login-component-pur-cione.title']}</h2>
        <div class="item">
            <label class="d-flex" for="">${i18n['cione-module.templates.components.login-component-pur-cione.username']}</label>
            <input type="text" required="required" type="text" id="username" name="mgnlUserIdCione" value="" maxlength="50">
            <span class="error"></span>
        </div>
        <div class="item">
            <div class="label-pass">
            	[#if content.forgottenPasswordPage?has_content]
                	<label for="">${i18n['cione-module.templates.components.login-component-pur-cione.password']}</label>
                
                	<a href="${cmsfn.link("website", content.forgottenPasswordPage!)!}">${i18n['cione-module.templates.components.login-component-pur-cione.forget-password']}</a>
                [/#if]                
            </div>
            <input required="required" type="password" name="mgnlUserPSWD" id="mgnlUserPSWD" value="" >            
        </div>
    	<button class="[#if isOpticaPRO]btn-green[#else]btn-blue[/#if]">${i18n['cione-module.templates.components.login-component-pur-cione.login']}</button>
     
    	[#if ctx.mgnlLoginError.loginException?has_content]
        	<span class="error">
        		[#if ctx.mgnlLoginError.loginException?contains("2##user-inactive")]
					<p>${i18n['cione-module.templates.components.login-component-pur-cione.error-login-inactive']} </p>
				[#elseif ctx.mgnlLoginError.loginException?contains("3##user-not-registry")]
					<p>${i18n['cione-module.templates.components.login-component-pur-cione.error-user-not-register']} </p>        			
        		[#else]
        			<p>${i18n['cione-module.templates.components.login-component-pur-cione.error-login']} </p>	
        		[/#if]        		
        		[#-- <p>${ctx.mgnlLoginError.loginException.message}</p> --]             	
            	           	        
        	</span>
		[/#if]
	</form>
	<div class="create-account [#if isOpticaPRO] opticapro [/#if]">
        <span class="d-flex justify-content-left">${i18n['cione-module.templates.components.login-component-pur-cione.register-title']}</span>
        [#if content.accessText?has_content]
        	<p>${content.accessText!}</p>
        [#else]
        	<p>${i18n['cione-module.templates.components.login-component-pur-cione.register-text']}</p>
        [/#if]
        [#if content.registrationPage?has_content]
        <form [#if isOpticaPRO] style="padding: 0 0 0 0;" [/#if] action='${cmsfn.link("website", content.registrationPage)!}'>
        	<button class="btn-gray">${i18n['cione-module.templates.components.login-component-pur-cione.register-btn']}</button>
        </form>
        [/#if]
        [#if isOpticaPRO]
	        <div class="outstanding">
	        	<span >Profesionales con</span>
	    		<span style="color: #84bd00;">VISIÓN DE MODA</span>
	    	</div>
    	[/#if]
    </div>
	[#if ctx.getUser().hasRole("OPTICAPRO")] 
		<hr style="border: 3px solid #07355a; margin-top: 0.5rem;">
		<hr style="border: 3px solid #84bd00; margin-top: 0.5rem;">  
	[/#if]
</section>


<div class="form" style="display:none">	
    <div class="text">
    [#if content.title?has_content]<h1>${content.title}</h1>[/#if]
    </div>
    [#if "anonymous" == ctx.user.name || cmsfn.editMode]
        [#assign requiredLabel = content.requiredLabel!i18n['template.login.required']!]
        [#assign requiredCharacter = content.requiredCharacter!i18n['template.login.required.character']!]
        [#if content.text?has_content]<div class="text">${content.text}</div>[/#if]
        <br>
        <div class="form-wrapper">
            <form id="loginForm" method="post" enctype="application/x-www-form-urlencoded">
                [#if content.realm?has_content]
                    <input type="hidden" name="mgnlRealm" value="${content.realm!'public'}"/>
                [/#if]

                <input type="hidden" name="mgnlModelExecutionUUID" value="${content.@uuid}"/>
                [#if content.targetPage?has_content]
                    <input type="hidden" name="mgnlReturnTo" value="${cmsfn.link("website", content.targetPage)!}"/>
                [#elseif content.targetPageExternal?has_content]
                	<input type="hidden" name="mgnlReturnTo" value="${content.targetPageExternal}"/>
                [/#if]

                ${ctx.response.setHeader("Cache-Control", "no-cache")}
                <input type="hidden" name="csrf" value="${ctx.getAttribute('csrf')!''}" />

                <p class="required"><span>${requiredCharacter}</span> ${requiredLabel}</p>

                <fieldset>
                    <div class="form-row">
                        <label for="username"><span>${i18n['template.login.username.label']} <dfn
                                title="required">${requiredCharacter}</dfn></span></label>
                        <input required="required" type="text" id="username" name="mgnlUserId" value="" maxlength="50"/>
                    </div>
                    <div class="form-row">
                        <label class="" for="mgnlUserPSWD"><span>${i18n['template.login.password.label']} <dfn
                                title="required">${requiredCharacter}</dfn></span></label>
                        <input required="required" type="password" name="mgnlUserPSWD" id="mgnlUserPSWD" value=""/>
                    </div>

                    [#if content.registrationPage?has_content]<a href="${cmsfn.link("website", content.registrationPage)!}">${i18n['template.login.registrationPageLink']}</a> | [/#if]
                    [#if content.forgottenPasswordPage?has_content]<a href="${cmsfn.link("website", content.forgottenPasswordPage!)!}">${i18n['template.login.forgottenPasswordPageLink']}</a>[/#if]

                    <div class="button-wrapper">
                        <fieldset class="buttons">
                            <input type="submit" class="submit" accesskey="s"
                                   value="${i18n['template.login.submit.label']}"/>
                        </fieldset>
                    </div>

                </fieldset>
            </form>
        </div><!-- end form-wrapper -->
    [/#if]
    [#if "anonymous" != ctx.user.name || cmsfn.editMode]
        <div class="text">${i18n.get('template.login.welcome', [ctx.user.name])}</div>
        <a href="${cmsfn.link(cmsfn.page(content))!}?mgnlLogout=true">${i18n['template.login.submit.logout.label']}</a>
    [/#if]

    [#if ctx.mgnlLoginError.loginException?has_content]
        <div class="loginError">
            <p>${ctx.mgnlLoginError.loginException.message}</p>
        </div>
    [/#if]
</div>


<script type="text/javascript">
	//ñapa hasta que funcione la virtual uri mapping
	var URLactual = window.location.href;
	if (URLactual == 'https://devmyshop.cione.es/cione') {
		window.location.href = "https://devmyshop.cione.es/myshop";
	}
	if (URLactual == 'https://premyshop.cione.es/cione') {
		window.location.href = "https://premyshop.cione.es/myshop";
	}
	if (URLactual == 'https://myshop.cione.es/cione') {
		window.location.href = "https://myshop.cione.es/myshop";
	}
</script>