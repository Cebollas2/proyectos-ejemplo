[#function getAttributtes mainAttributes variants attr]

	[#assign attributtes = []]
	
	[#if mainAttributes?has_content]
		[#list mainAttributes as attribute]
			[#if attribute.name == attr]
				[#assign attributtes = attributtes + [ attribute.value?string! ]]
			[/#if]
		[/#list]
	[/#if]
	
	[#if variants?has_content]
		[#list variants as variant]
			[#assign attributes = variant.attributes!]
			[#if attributes?has_content]
				[#list attributes as attribute]
					[#if attribute.name == attr]
  						[#assign attributtes = attributtes + [ attribute.value?string! ]]
					[/#if]
				[/#list]
			[/#if]
		[/#list]
	[/#if]
	
	[#return attributtes]
	
[/#function]


[#function getMainAttribute mainAttributes attrname]

	[#assign attr = ""]
	
	[#if mainAttributes?has_content]
		[#list mainAttributes as attribute]
			[#if attribute.name == attrname]
				[#assign attr = attribute.value?string!]
			[/#if]
		[/#list]
	[/#if]
	
	[#return attr]
	
[/#function]

[#function getTruncate str max]

	[#assign res = str?truncate(max)]
	
	[#assign res = res?replace("[...]", "...")]
	
	[#return res]
	
[/#function]

[#function getCase colors calibers calibrations]
	
	[#--  --]
	[#if colors?has_content && calibers?has_content && calibrations?has_content]
		[#return 1]
	[/#if]
	
	[#if colors?has_content && calibers?has_content && !calibrations?has_content]
		[#return 2]
	[/#if]
	
	[#if colors?has_content && !calibers?has_content && calibrations?has_content]
		[#return 3]
	[/#if]
	
	[#if !colors?has_content && calibers?has_content && calibrations?has_content]
		[#return 4]
	[/#if]
	
	[#if colors?has_content && !calibers?has_content && !calibrations?has_content]
		[#return 5]
	[/#if]
	
	[#if !colors?has_content && calibers?has_content && !calibrations?has_content]
		[#return 6]
	[/#if]
	
	[#if !colors?has_content && !calibers?has_content && calibrations?has_content]
		[#return 7]
	[/#if]
	
[/#function]

[#function getId product variant]
	            
	[#if product.master.familiaproducto?has_content]
		[#switch product.master.familiaproducto]
		  [#case "liquidos"]
		     [#return product.getSkuByTamanio(variant.tamanio)]
		     [#break]
		  [#case "audifonos"]
		     [#return product.getSkuByTamanio(variant.tamanio)]
		     [#break]
		  [#default]
		     [#return getIdMonturas(product,variant)!'1']
		[/#switch]
	[#else]
		[#return getIdMonturas(product,variant)!'1']
    [/#if] 
	
[/#function]

[#function getIdMonturas product variant]
	
    [#if product.master.color?? && product.master.color?has_content]
    	 [#if  product.master.calibration?has_content]
    	 	[#-- color + graduacion --]
        	[#return product.getSkuByColorAndCalibration(variant.color,variant.calibration)]
    	 [#else]
        	 [#if product.master.calibre?has_content]
        	 	[#-- color + calibre --]
    	 	 	[#return product.getSkuByColorAndCaliber(variant.color,variant.calibre)]
        	 [#else]
        	 	[#-- color --]
        	 	[#return product.getSkuByColor(variant.color)]
        	 [/#if]
    	 [/#if]
    [#else]
    	[#if product.master.calibre?? && product.master.calibre?has_content]
    		[#-- calibre --]
    		[#return product.getSkuByCaliber(variant.calibre)]
		[#else]
			[#if product.master.calibration?? && product.master.calibration?has_content]
				[#-- calibration  --]
				[#return product.getSkuByCalibration(variant.calibration)]
			[#else]
				[#return '1']
			[/#if]
		[/#if]
    [/#if]
	
[/#function]


