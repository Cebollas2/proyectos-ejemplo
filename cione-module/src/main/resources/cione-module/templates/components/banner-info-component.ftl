<style>
.text.richtext a{
	color: #fff;
}
</style>
<section class="cmp-bannerinfo">
	<div class="container">
		<div class="row banner [#if ctx.getUser().hasRole("OPTICAPRO")] green[/#if]">
		            
			<div class="info">
				
				<div class="owl-carousel owl-bannerinfo owl-theme">
				[#list cmsfn.children(content.carousel) as slide]
					[#--  [#if slide.roles?has_content]		 --]
					[#if slide.campana?has_content]
						[#assign fInicio = (slide.fechaInicio?date?string["dd/MM/yyyy HH:mm:ss"])!"null"]
						[#assign fFin = (slide.fechaFin?date?string["dd/MM/yyyy HH:mm:ss"])!"null"]			
						
						[#if model.hasPermissionsCampana(slide.campana) && model.checkFechaInicio(fInicio) && model.checkFechaFin(fFin)]
							<div class="item">
		                        <div class="text richtext">
									${cmsfn.decode(slide).text!""}
		                        </div>
		                    </div>
		                [/#if]
		            [#else]
		            	<div class="item">
	                        <div class="text richtext">
								${cmsfn.decode(slide).text!""}
	                        </div>
	                    </div>
		            [/#if]
                [/#list]
				</div>
			</div>
			
			<div class="logo"></div>
		</div>
	</div>
</section>