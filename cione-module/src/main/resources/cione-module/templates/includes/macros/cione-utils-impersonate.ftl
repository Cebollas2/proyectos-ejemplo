[#function hasAttributte attributes name]

	[#if attributes??]
		[#list attributes as attribute]
			[#if attribute.name == name]
				[#return true]
			[/#if]
		[/#list]
	[/#if]
	[#return false]
	
[/#function]

[#function findAttributte attributes name]

	[#if attributes??]
		[#list attributes as attribute]
			[#if attribute.name == name]
				[#return attribute.value]
			[/#if]
		[/#list]
	[/#if]
	[#return false]
	
[/#function]



[#-- nombre generico usado en Cione --]
[#function showPrices user uuid username]
	
	[#return showPVO(user, uuid, username)]
	
[/#function]

[#function isSocio id]

	[#assign res = false]
	
	[#if id?has_content && id??]
		[#if id?ends_with("00")]
			[#assign res = true]
		[/#if]
	[/#if]
	
	[#return res]
	
[/#function]

[#function showShipping user]

	[#assign res = false]
	
	[#if !(user.hasRole("empleado_cione_fp_envios"))]
			[#assign res = true]
		[/#if]
	
	[#return res]
	
[/#function]

[#function showPVO user uuid username]
	
	[#assign res = false]
	
	[#assign userNode = cmsfn.contentById(uuid, "users")]
		
		[#if userNode.viewprices?? && userNode.viewprices?has_content 
			&& userNode.viewprices.priceConfiguration?? && userNode.viewprices.priceConfiguration?has_content]
			
			[#if userNode.viewprices.priceConfiguration=="pvo" || userNode.viewprices.priceConfiguration=="pvp-pvo"]
				[#assign res = true]
			[/#if]
			
		[#else]
		
			[#if (((user.hasRole("empleado_cione_precio_pvo") || user.hasRole("empleado_cione_precio_pvppvo")) 
				&& !user.hasRole("empleado_cione_precio_oculto")) || isSocio(username))]
				[#assign res = true]
			[/#if]
			
		[/#if]
	[#return res]
	
[/#function]

[#function showPVP user uuid username]
	
	[#assign res = false]
	
	[#assign userNode = cmsfn.contentById(uuid, "users")]
	
	[#if userNode.viewprices?? && userNode.viewprices?has_content
		&& userNode.viewprices.priceConfiguration?? && userNode.viewprices.priceConfiguration?has_content]]
		
		[#if userNode.viewprices.priceConfiguration=="pvp" || userNode.viewprices.priceConfiguration=="pvp-pvo"]
			[#assign res = true]
		[/#if]
		
	[#else]
	
		[#if (((user.hasRole("empleado_cione_precio_pvp") || user.hasRole("empleado_cione_precio_pvppvo")) 
			&& !user.hasRole("empleado_cione_precio_oculto")) || isSocio(username))]
			[#assign res = true]
		[/#if]
		
	[/#if]
	
	[#return res]
	
[/#function]

[#function getRealColor color]
	
	[#assign res = "background: linear-gradient(-45deg, white 12px, black 15px, black 15px, white 17px );"]
	
	[#assign codecolor = color?split("#")]
	
	[#if codecolor?size == 2 && !color?starts_with("#")]
		[#assign res = "background-color:#" + codecolor[1] + ";"]
	[/#if]
	
	[#if color?starts_with("#")]
		[#assign res = "background-color:" + color + ";"]
	[/#if]
	
	[#return res]
	
[/#function]