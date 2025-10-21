
[#-- tiene imagen de fondo --]
[#if content.imageLink?has_content]
	[#assign asset = damfn.getAsset(content.imageLink)!]
	[#if asset?has_content && asset.link?has_content]		
		<section class="cmp-bannersection" style="background-image: url('${asset.link}')">  
			<h1 class="title">
	    		${content.title!}
			</h1>
		</section>
	[/#if]
[#else]
[#-- no tiene imagen de fondo --]
	<section class="cmp-bannersection" style="background:#B2C8D6">  
		<h1 class="title">
	    	${content.title!}
			</h1>
	</section>	
[/#if]