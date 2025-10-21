<div class="info">
	<#if content.row1?? && content.row1?has_content>
	    <span><h1>${content.row1}</h1></span>
	</#if>
	<#if content.row2?? && content.row2?has_content>
	    <span class="blue"><h1><#if content.row2?has_content>${content.row2}</#if></h1></span>
	</#if>
	<#if content.row3?? && content.row3?has_content>
	    <span style="font-size: small;width: 50%;"><#if content.row3?has_content>${content.row3}</#if></span>
    </#if>
    <#assign path = ctx.request.requestURL>
    <p style="display:none">${path}</p>
    <#if content.multiidioma?has_content && content.multiidioma>
	    <#if path != "http://myshop.cione.es/myshop">
		    <span>
		    	<a href="${cmsfn.localizedLinks()['es']!'#'}">ES</a> I 
		    	<a href="${cmsfn.localizedLinks()['pt']!'#'}">PT</a>
		    </span>
		</#if>
	</#if>
</div>
<#if content.row21?? && content.row21?has_content>
	<div class="info">
		<span><h1>${content.row21}</h1></span>
		<span class="blue"><h1><#if content.row22?has_content>${content.row22}</#if></h1></span>
	</div>
</#if>

<#if content.row31?has_content || content.row32?has_content>
    <div class="info">
	    <span><h1><#if content.row31?has_content>${content.row31}</#if></h1></span>
    	<span class="blue"><h1><#if content.row32?has_content>${content.row32}</#if></h1></span>
    </div>
</#if>   

<#assign isOpticaPRO = false>
<#if cmsfn.page(content) == "opticapro">
	<#assign isOpticaPRO = true>
</#if>
    
<div class="logo <#if !isOpticaPRO> reduce</#if>" >
	<#assign logoico = content.logo!>
	<#if logoico?? && logoico?has_content>
		<#assign link = damfn.getAssetLink("jcr:" + content.logo)!"">
		<img src="${link}" alt="logotipo">
	</#if>
</div>



