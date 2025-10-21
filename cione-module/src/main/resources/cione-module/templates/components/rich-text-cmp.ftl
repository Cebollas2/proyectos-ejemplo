<section>
	[#if content.text?? && content.text?has_content]
		${cmsfn.decode(content).text!}
	[/#if]
<section>	  
