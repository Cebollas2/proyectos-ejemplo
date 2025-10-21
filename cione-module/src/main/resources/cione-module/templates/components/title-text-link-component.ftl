<style>
	.note-text-link {
	    font-size: 14px;
		font-weight: normal;
		font-style: normal;
		font-stretch: normal;
		line-height: normal;
		letter-spacing: normal;
		color: #00609c;
		display: flex;
		padding: 25px 0px 0px 0px;
		max-width: 100%;
	}
	.icon-place {
	  width: 180px;
	  height: 180px;
	  margin: 40px 826px 407px 0px;
	  padding: 46px 42.6px 45.4px 47.9px;
	  border-radius: 8px;
	  background-color: rgba(0, 96, 156, 0.1);
	}
	img.icon-selected {
	  width: 89.5px;
	  height: 88.6px;
	  object-fit: contain;
	}
	
	@media screen and (max-width: 1023px) {
	  .note-text-link {
		    font-size: 14px;
			font-weight: normal;
			font-style: normal;
			font-stretch: normal;
			line-height: normal;
			letter-spacing: normal;
			color: #00609c;
			display: flex;
			padding: 25px 0px 0px 0px;
			max-width: 100%;
		}
	}
</style>

[#-- title --]
[#assign title = ""]
[#if content.title?? && content.title?has_content]
	[#assign title = content.title!""]
[/#if]

[#-- text --]
[#assign text = ""]
[#if content.text?? && content.text?has_content]
	[#assign text = content.text!""]
[/#if]

[#-- link --]
[#assign link = "#"]
[#assign target = ""]
[#if content.altlink?? && content.altlink?has_content]
	[#if content.altlink.field?? && content.altlink.field?has_content]
		[#if content.altlink.internal?? && content.altlink.internal?has_content && content.altlink.field=="internal"]
		    [#assign link = cmsfn.link(cmsfn.contentById(content.altlink.internal))!"#"]
		[#elseif content.altlink.external?? && content.altlink.external?has_content && content.altlink.field=="external"]
		    [#assign link = content.altlink.external!"#"]
		    [#assign link = link?replace("usercode", model.getUserId()!"")!]
		    [#assign target = "target='_blank'"]
		[/#if]
	[/#if]
[/#if]

[#-- icon --]
[#assign icon = ""]
[#if content.image?? && content.image?has_content]
	[#assign icon = damfn.getAssetLink("jcr:" + content.image)]
[/#if]

<section class="cmp-catalogos">

    <h2 class="title">${title!""}</h2>
    
	<div class="note-text-link">${text!""}</div>
	
	<div class="icon-place">
		<a href="${link!"#"}" ${target!"#"}>
			<img src="${icon!""}" class="icon-selected ">
		</a>
	</div>
	
</section>
				