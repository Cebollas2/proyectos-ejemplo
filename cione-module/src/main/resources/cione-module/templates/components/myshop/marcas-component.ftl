<!-- MARCAS -->
[#if content.showNameTooltip?? && content.showNameTooltip?has_content]
<style>
.b2b-marcas-item .tooltiptext {
  visibility: hidden;
  width: 120px;
  background-color: #555;
  color: #fff;
  text-align: center;
  border-radius: 6px;
  padding: 5px 0;
  position: absolute;
  z-index: 1;
  bottom: 125%;
  left: 50%;
  margin-left: -60px;
  margin-bottom: 5px;
  opacity: 0;
  transition: opacity 0.3s;
}

.b2b-marcas-item .tooltiptext::after {
  content: "";
  position: absolute;
  top: 100%;
  left: 50%;
  margin-left: -5px;
  border-width: 5px;
  border-style: solid;
  border-color: #555 transparent transparent transparent;
}

.b2b-marcas-item:hover .tooltiptext {
  visibility: visible;
  opacity: 1;
}
</style>
[/#if]

<main class="b2b-main" role="main">
<h2 class="b2b-h2">${content.title!}</h2>
<div class="b2b-container">
  <div class="b2b-container-marcas-full">
	<section class="b2b-marcas-full">
	
	    <div class="b2b-marcas-full-header">
	        <h2 class="b2b-marcas-full-title">${content.categoryName!}</h2>
	    </div>

	    
	    	[#assign relacionada = content.categoryRelated!]
	    	[#assign limit = content.maxListCategories!20]
	    	[#assign marcas = model.getListMarcasRelMonturas(relacionada, limit)]
	    	[#if cmsfn.language() == 'pt']
            	[#assign defaultImage = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/common/oops-PT.jpg"]
            [#else]
            	[#assign defaultImage = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/common/oops.jpg"]
            [/#if]
			
			[#if marcas?has_content]
			<div class="b2b-marcas-full-main bg-gray">
				[#if content.listadoLink?has_content]
	    			[#assign linkListado = cmsfn.link("website", content.listadoLink!)!]
	    		[/#if]
	    		[#assign atributo = '']
	    		[#list content.attribute! as sValue]
	    			[#assign encodingValue = model.encodeURIComponent(sValue)]
					[#assign atributo = atributo +'&'+encodingValue]
				[/#list] 
				[#list marcas as marca]
					[#if linkListado?has_content]
						[#assign link = linkListado + "?category=" + marca.getId() + atributo]
					[#else]
						[#assign link = "#"]
					[/#if]
					<div class="b2b-marcas-item">
						<a href=${link}>
			                <img src="${marca.getLogoListado()}" onerror="this.onerror=null; this.src='${defaultImage}'" alt="">
			            </a>
			            [#if content.showNameTooltip??]
			            	<span class="tooltiptext">
			            		[#if cmsfn.language() == 'pt' && marca.getName().getPt()?has_content]
		                        	${marca.getName().getPt()}
		                        [#elseif cmsfn.language() == 'en' && marca.getName().getEn()?has_content]
		                        	${marca.getName().getEn()}
		                        [#else]
		                        	${marca.getName_es()}
		                        [/#if]
		                	</span>
			            [/#if]
					</div>
				[#else]
					<div class="b2b-marcas-item">
						No se ha podido encontrar ninguna marca.
					</div>
				[/#list]
				</div>
			[/#if]
	    
	    
	</section>
	[#if content.showReplacementNotFound??]
		[#if content.showReplacementNotFound.field??]
			[#if content.showReplacementNotFound.field == 'activo']
				[#assign link = cmsfn.link("website", content.showReplacementNotFound.activo!)!]
				<section class="b2b-no-repuesto">
				    <div class="b2b-no-repuesto-block">
				        <p class="b2b-no-repuesto-text">${i18n['cione-module.templates.myshop.listado-productos-filter-component.spare.nofoundquestion']}</p>
				        <a href="${link}" class="b2b-no-repuesto-link">${i18n['cione-module.templates.myshop.listado-productos-filter-component.spare.here']}</a>
				    </div>
				</section>
			[/#if]
		[/#if]
	[/#if]
  </div>
</div>
</main>