<!-- MIGAS DE PAN -->
<section class="b2b-breadcrumb">
	[#assign homeMyShop = cmsfn.contentById(content.myshopHome!)!]
	[#assign showItems = false]
	<ul class="b2b-breadcrumb-items">
		[#list cmsfn.ancestors(content, "mgnl:page") as ancestor]
			[#if showItems]
			
				[#assign categoria_menu = '']
				[#assign atributo_menu = '']
				[#if ancestor.category?? && ancestor.category?has_content]
					[#list ancestor.category?split("/") as sValue]
						[#if sValue?is_last]
					  		[#assign categoria_menu = '?category=' +sValue]
					  	[/#if]
					[/#list] 
				[/#if]
					
				[#if ancestor.attribute?? && ancestor.attribute?has_content]
					[#list ancestor.attribute as sValue]
						[#assign atributo_menu = atributo_menu +'&'+sValue]
					[/#list] 
				[/#if]
				[#assign sku = ""]
				[#if ancestor?is_last]
					[#if ctx.getParameter('sku')?? && ctx.getParameter('sku')?has_content]
						[#assign parameter = ctx.getParameter('sku')]
						[#assign sku = "?sku="+parameter]
					[/#if]
				[/#if]
				<li class="b2b-breadcrumb-item"><a href="${cmsfn.link(ancestor)!}${categoria_menu!}${atributo_menu!}${sku}">${ancestor.title!ancestor.@name}</a></li>
			[#else]
				[#if ancestor == homeMyShop]
					[#assign showItems = true]
					<li class="b2b-breadcrumb-item"><a href="${cmsfn.link(ancestor)!}">${i18n['cione-module.templates.myshop.breadcrumbs-component.Inicio']}</a></li>
				[/#if]
			[/#if]
		[/#list]
    </ul>
    
</section>