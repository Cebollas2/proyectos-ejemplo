[#assign magazinesRoot = cmsfn.contentByPath("/", "magazines")]
[#assign magazines = cmsfn.children(magazinesRoot,"magazine")!]

<section class="cmp-catalogos">
    [#if content.title?has_content]
    	<h2 class="title">${content.title}</h2>
    [/#if]
	[#if content.text?has_content]
		<p style="font-size: 1.125em;">${content.text!}</p>
	[/#if]
	<div class="wrapper-box">
		[#list magazines as magazine]
			<div class="box">										
				<div class="content-img">						
    				[#if magazine.imagen?has_content]
						<img src="${damfn.getAsset(magazine.imagen!).link!}" class=""/>							    		
					[/#if]
				</div>
				<a href="${cmsfn.link(cmsfn.contentById(magazine.documento, "dam"))}" target="blank_">
		    		<div class="content-text">	            		
		    			<span>${magazine.title!}</span>
		    			<span class="icon documento"></span>	            		
		    		</div>
    			</a>	
			</div>				
		[/#list]
	</div>

	[#if magazines?size==0]
		<div>	
			${i18n['cione-module.templates.components.magazine.empty']}
		</div>	
	[/#if]
 </section>