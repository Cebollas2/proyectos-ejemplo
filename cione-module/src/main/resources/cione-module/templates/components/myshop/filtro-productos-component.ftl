[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]
[#include "../../includes/macros/ct-utils.ftl"]
[#include "../../includes/macros/cione-utils.ftl"]
[#assign facets = model.facets!]
[#assign filtrosFront = model.filtrosFront!]
[#assign pageNode = cmsfn.page(content)!"unknown"]
[#assign atributosFiltro = pageNode.attribute!]
[#assign filtrosObligatorios = model.getFiltrosObligatorios()!]

[#if facets?has_content && model.hasFacets()!false]

[#assign agrupadores = model.getAgrupadores()!]
[#assign step = model.getStep()!]

[#assign filtersMandatory= model.getFiltersMandatory()!]




	<section class="b2b-filtro">
	    
	    [#-- aplicar filtro boton --]         
		<div class="hide-in-mobile">
		  <div class="b2b-button-wrapper">
		    <button type="button" class="b2b-button b2b-button-filter">
		      ${i18n['cione-module.templates.myshop.filtro-productos-component.apply']}
		    </button>
		  </div>
		</div>
		
		[#-- orden de productos --]
		<div class="b2b-mobile-filter hide-in-desktop">
			<i id="close-modal-filter" class="fas fa-times icon-close-filter"></i>
			<div class="b2b-listado-orden-text">Ordenar y filtrar</div>
			<div class="b2b-listado-orden-text">
				<select id="selectorder" class="selectorder">
	            <option value="cleanorder">${i18n['cione-module.templates.myshop.listado-productos-filter-component.sortbyselect']}</option>
	            <option value="new">${i18n['cione-module.templates.myshop.listado-productos-filter-component.sortnew']}</option>
	            <option value="lowtohigh">${i18n['cione-module.templates.myshop.listado-productos-filter-component.lowtohigh']}</option>
	            <option value="hightolow">${i18n['cione-module.templates.myshop.listado-productos-filter-component.hightolow']}</option>
				</select>
			</div>
		</div>
	    
	    [#-- filtros aplicados --]
	    <div class="filtros-wrapper">
	      <div class="filtros-header">
	        <span class="filtros-header-text">${i18n['cione-module.templates.myshop.filtro-productos-component.filterby']}</span>
	        <span class="filtros-header-clean">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
	      </div>
	      <div class="filtros-pills-wrapper" id="filtros-pills-wrapper"></div>
	    </div>
	        
		[#-- conjunto de filtros --]        
	    <form>
			<div class="tabs">
				[#-----------------------------------]
				[#------- Filtro seleccionado -------]
				[#-----------------------------------]
				[#if content.filterSelect?has_content && content.filterSelect??]
					[#switch content.filterSelect]
						[#case "generico"]
							[@macrofiltrogenerico content/]
							[#break]
						[#case "monturas"]
							[@macrofiltromonturas /]
							[#break]
						[#case "soluciones"]
							[@macrofiltrosoluciones /]
							[#break]
						[#case "accesorios"]
							[@macrofiltroaccesorios /]
							[#break]
						[#case "marketing"]
							[@macrofiltromarketing /]
							[#break]
						[#case "contactologia"]
							[@macrofiltrocontacto /]
							[#break]
						[#case "audiologia"]
							[@macrofiltroaudiologia /]
							[#break]
						[#case "audiologiaamedida"]
							[@macrofiltroaudiologia /]
							[#break]
						[#case "audiologiastock"]
							[@macrofiltroaudiologia /]
							[#break]
						[#case "audiologiacompleta"]
							[@macrofiltroaudiologia /]
							[#break]
						[#case "tapones"]
							[@macrofiltrotapones /]
							[#break]
						[#case "baterias"]
							[@macrofiltrobaterias /]
							[#break]
						[#break]
						[#case "complementos"]
							[@macrofiltrocomplementos /]
							[#break]
						[#case "accesoriosinalambricos"]
							[@macrofiltroaccesoriosinalambricos /]
							[#break]
						[#default]
							[@macrofiltromonturas /]
					[/#switch]
				[/#if]
	    		
			</div>
		</form>
	    
	    <div class="b2b-button-wrapper">
	      <button type="button" class="b2b-button b2b-button-filter">
	        APLICAR FILTROS
	      </button>
	    </div>
	    
	</section>
	  
	  
	[#------------------------------------------------------------------]
	[#----------------------------- SCRIPT -----------------------------]
	[#------------------------------------------------------------------]
	
	<script>
	
		$(document).ready(function(){

		$('.tab-label').click(function (e) {
		    // Toggle More & Minus icon
		    var imgicon = $(this).find('.more-icon');
		    var tabtext = $(this).find(".tab-label-text");
		    if (imgicon.attr("src") === "${resourcesURL}/img/myshop/icons/more.svg") {
		        // SetTimeout to syncronized with css transitions
		        setTimeout(
		            function () {
		                imgicon.attr("src", "${resourcesURL}/img/myshop/icons/minus.svg");
		                tabtext.css("color", "#00609c");
		            }, 1);
		        // $(this).parents('.tab').find('.tab-label-clean-text').css('display','flex');
		    }
		    else {
		        setTimeout(
		            function () {
		                imgicon.attr("src", "${resourcesURL}/img/myshop/icons/more.svg");
		                tabtext.css("color", "#333333");
		            }, 1);
		        // $(this).parents('.tab').find('.tab-label-clean-text').css('display','none');
		    }
		});
		
			function getURL(){
				
				[#-- if agrupadores?? && agrupadores?has_content]
					alert('${agrupadores}');
					var url = (window.location.href).split('?')[0];
					//url += '?agrupadores=' + encodeURIComponent('${agrupadores}');
					url += '?agrupadores=${agrupadores}';
					if($('#PRICEACTIVE').data('value')){
						var pricefilter = '&variants.price.centAmount=range( '+ ($('#PVPMIN').data('value')*100) + ' to ' + ($('#PVPMAX').data('value')*100) +' )';
						url += pricefilter;
					}
					
					$('.b2b-tab-check:checkbox:checked').each(function () {
				    	aux = ( $(this).data('value') + '%3D' + encodeURIComponent($(this).data('label')));
				    	
				    	alert(url + " contiene " + aux);
				    	
				    	if (!url.includes(aux)) {
				    		url += '&' +aux;
				    	}
					});
					
				[#else]
				[/#if--]
					var getUrlParameter = function getUrlParameter(sParam) {
					    var sPageURL = window.location.search.substring(1),
					        sURLVariables = sPageURL.split('&'),
					        sParameterName,
					        i;
					
					    for (i = 0; i < sURLVariables.length; i++) {
					        sParameterName = sURLVariables[i].split('=');
					
					        if (sParameterName[0] === sParam) {
					            return typeof sParameterName[1] === undefined ? true : encodeURIComponent(sParameterName[1]);
					        }
					    }
					    return false;
					};
					var categoryParam = getUrlParameter('category');
					if ((categoryParam == undefined) || categoryParam == '') {
					
						[#if content.category?has_content && content.category?? && content.internalLink?has_content && content.internalLink??]
							[#assign link = cmsfn.link("website", content.internalLink!)!]
							[#assign categoria = '#']
							[#list content.category?split("/") as sValue]
								[#if sValue?is_last]
							  		[#assign categoria = sValue]
							  	[/#if]
							[/#list] 
							[#assign link = link + "?category=" + categoria]
							var url = "${link!""}";
						[/#if]
					} else {
						var url = (window.location.href).split('?')[0];
						url += '?category=' + getUrlParameter('category');
					}
					
					if (getUrlParameter('skuPackMaster')){
						url += '&skuPackMaster=' + getUrlParameter('skuPackMaster');
					} 
					
					if($('#PRICEACTIVE').data('value')){
						var pricefilter = '&variants.price.centAmount=range( '+ ($('#PVPMIN').data('value')*100) + ' to ' + ($('#PVPMAX').data('value')*100) +' )';
						url += pricefilter;
					}
					[#list atributosFiltro as atributo]
						if (!url.includes('${atributo}')) {
							url += "&${atributo}";
						}
					[/#list]
					[#-- codificacion de la url:
					
						 local: url += ('&' + $(this).data('value') + '=' + escape($(this).data('label')));
						 dev: url += ('&' + $(this).data('value') + '=' + $(this).data('label'));
					--]
					
				    $('.b2b-tab-check:checkbox:checked').each(function () {
				    	aux = ('&' + $(this).data('value') + '=' + encodeURIComponent($(this).data('label')));
				    	if (!url.includes(aux)) {
				    		url += aux;
				    	}
					});
					
					[#if filtersMandatory??]
						[#list filtersMandatory?keys as key]
							[#list filtersMandatory[key] as value]
								aux = '${key}=${value}';
								if (!url.includes(aux)) {
						    		url += '&' + aux;
						    	}
					    	[/#list]
						[/#list]
					[/#if]
					
					[#if filtrosObligatorios??]
						[#list filtrosObligatorios?keys as key]
							[#list filtrosObligatorios[key] as value]
								aux = '${key}=${value}';
								if (!url.includes(aux)) {
						    		url += '&' + aux + '&mandatory=true';
						    	}
					    	[/#list]
						[/#list]
					[/#if]
					
					[#if step?? && step?has_content]
						url += '&step=${step}';
					[/#if]
									
					
				
				
				return url;
			}
		
			[#-- BEGIN: APLICAR FILTROS --]
			$(".b2b-filtro .b2b-button").on("click",function(){
			    window.location.href = getURL();
			    
			    
			});
			[#-- END: APLICAR FILTROS --]
		
			$(".b2b-detail-tab-title").on("click", function () {
		
			    $(".b2b-detail-tab-title").removeClass('selected');
			    $(this).addClass('selected');
			
			    var tabId = $(this).data("id");
			    $('.b2b-detail-tab-body-content').removeClass("active");
			    $('.b2b-detail-tab-body-content[id="' + tabId + '"').addClass("active");
		
			});
			
			// Remove one pill
			$('#filtros-pills-wrapper').on("click", ".filtros-pill .icon-close", function () {
				
				
			    var pill = $(this).parent();
			    var idcheck = $(this).parent().attr('idcheck');
			
			    $('.b2b-tab-check[id=' + idcheck + ']').click();
			    pill.remove();
			
			    // Remove Group Clean Label if all pills were deleted
			    setTimeout(function () {
			        $('.b2b-tab-items').each(function (index) {
			            that = $(this);
			  
			            var checkFlag = false;
			
			            $.map($(this).find('.b2b-tab-check'), function (n, i) {
			                //console.log(n.id);
			
			                if ($('.b2b-tab-check[id=' + n.id + ']').is(':checked') == true) {
			                    checkFlag = true;
			                }
			            });
			   
			            if (checkFlag == false) {
			                that.parents('.tab').find('.tab-label-clean-text').css('display', 'none');
			                // that.parent().css('max-height','0');
			                // imgicon.attr("src", "${resourcesURL}/img/myshop/icons/more.svg");
			            }
			
			        }, 100);
			
			    });
			
			
			});
			
			[#-- LIMPIAR TODO --]
			$(".filtros-header-clean").on("click", function () {
				
				$('#PRICEACTIVE').data('value', false);
				
			    // Unchecked all checks
			    $('.b2b-tab-check').prop("checked", false);
			
			    // Remove all filters-pills
			    $('.filtros-pill').remove();
			
			    // Hide Clean Group Labels
			    $(".tab-label-clean-text").css('display', 'none');
			
			});
			
			// Group clean
			$(".tab-label-clean-text").on("click", function (e) {
			    var thisChecks = $(this).parents(".tab").find('.b2b-tab-check');
			
			    $.map(thisChecks, function (n, i) {
			        $('.filtros-pill[idcheck=' + n.id + ']').remove();
			    });
			
			    thisChecks.prop("checked", false);
			    $(this).css('display', 'none');
			});
			
			function checkFilters() {
	
		    $('.b2b-tab-check').each(function (index) {
		        
		        if ($(this).prop("checked") == true) {
		
		
		            // Create new Pill
		            var newPill = '<div class="filtros-pill" idcheck="'
		                + $(this).attr('id')
		                + '">'
		                + '<span class="filtros-pill-text">'
		                + $(this).siblings(".b2b-tab-label").text()
		                + '</span>'
		                + '<i class="fas fa-times icon-close"></i>'
		                + '</div>';
		
		            // Add the new Pill
		            $("#filtros-pills-wrapper").append(newPill);
		
		
		            var tabContent = $(this).parents(".tab-content");
		            var tabLabel = tabContent.siblings(".tab-label");
		            var imgicon = tabLabel.find('.more-icon');
		            var tabtext = tabLabel.find(".tab-label-text");
		
		            if (imgicon.attr("src") === "${resourcesURL}/img/myshop/icons/more.svg") {
		                // SetTimeout to syncronized with css transitions
		                setTimeout(
		                    function () {
		                        imgicon.attr("src", "${resourcesURL}/img/myshop/icons/minus.svg");
		                        tabtext.css("color", "#00609c");
		                    }, 1);
		
		            }
		            else {
		                setTimeout(
		                    function () {
		                        imgicon.attr("src", "${resourcesURL}/img/myshop/icons/more.svg");
		                        tabtext.css("color", "#333333");
		                    }, 1);
		                // $(this).parents('.tab').find('.tab-label-clean-text').css('display','none');
		            }
		
		            tabLabel.siblings(".b2b-hidden-check").prop("checked", true).trigger("change");
		        }
		    });
		
		}
		
		checkFilters();
		
		function removeLiByAttributes(label, value) { //label = UNISEX; value="variants.attributes.target.es"
		    // Seleccionar todos los elementos <li>
		    const listItems = document.querySelectorAll('.b2b-tab-item');
		
		    // Busca sobre el listado los li con label = UNISEX; value="variants.attributes.target.es"
		    listItems.forEach(li => {
		        const input = li.querySelector('input');
		        if (input && input.getAttribute('data-value') === value) { //cumple value="variants.attributes.target.es"
		        	if (!label.includes(input.getAttribute('data-label'))) {
		        		li.remove(); // Eliminar el <li> que cumple las condiciones
		        	}
		        
		        }
		        //if (input && input.getAttribute('data-label') === label && input.getAttribute('data-value') === value) {
		          //  li.remove(); // Eliminar el <li> que cumple las condiciones
		        //}
		    });
		}
		[#if filtersMandatory?? && filtersMandatory?has_content]
			[#list filtersMandatory?keys as value]
				[#assign label_list = ""]
				[#list filtersMandatory[value] as label ]
					[#assign label_list = label_list + "-" + label]
				[/#list]
				removeLiByAttributes('${label_list}','${value}');
			[/#list]
		[/#if]
		
		
		});
		
	</script>

[/#if]


[#-------------------------------------------------------------------------------------------------]
[#----------------------------- BEGIN: MACROS CON CADA FILTROS A USAR -----------------------------]
[#-------------------------------------------------------------------------------------------------]


[#macro macrofiltrolistado content]

	[#assign checked = ""]
	[#if filtrosFront?has_content]
		[#list filtrosFront as faceta]
			[#assign attributes = faceta.attributes!]
			[#if attributes?? && attributes?has_content]
				[#assign terms = attributes.getTerms()!]
				[#if terms?? && terms?has_content]
					<div class="tab">
						<input class="b2b-hidden-check" type="checkbox" id="chckgen${faceta?index}">
						<label class="tab-label" for="chckgen${faceta?index}">
							<span class="tab-label-text">${faceta.name!}</span>
							<span class="tab-label-clean">
							<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
							<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
						</label>
					
						<div class="tab-content">
				  
							  <ul class="b2b-tab-items">
								  [#list terms?sort_by("term") as term]
								  	  [#assign checked = ""]
								  	  [#if term.selected]
								  	  	[#assign checked = "checked"]
								  	  [/#if]
								  	  
								  	  [#assign valor = term.term]
								  	  [#if valor == "T"]
								  	  	[#assign valor = i18n['cione-module.templates.myshop.filtro-productos-component.si']]
								  	  [#elseif valor == "F"]
								  	  	[#assign valor = i18n['cione-module.templates.myshop.filtro-productos-component.no']]
								  	  [/#if]
								  	<li class="b2b-tab-item">
								      <input class="b2b-tab-check styled-checkbox" id="${faceta.name!?replace(" ", "")}-${term?index}" type="checkbox" data-value="${faceta.key!}" data-label="${term.term!""}" ${checked!""}>
								      <label class="b2b-tab-label" for="${faceta.name!?replace(" ", "")}-${term?index}">${valor!""}</label>
								    </li>
								  [/#list]
							  </ul>
						  
						</div>
					</div>
				[/#if]
			[/#if]
		[/#list]
	[/#if]
[/#macro]

[#-----------------------]
[#-------- marca --------]
[#-----------------------]
[#macro macrofiltromarca titlemacro]

	[#assign checked = ""]
	[#if facets.variantsAttributesMarca?has_content]
	     
	    [#assign marcas = facets.variantsAttributesMarca.getTerms()!]
	    [#if marcas?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck1">
				<label class="tab-label" for="chck1">
					<span class="tab-label-text">${titlemacro!i18n['cione-module.templates.myshop.filtro-productos-component.brand']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list marcas?sort_by("term") as marca]
						  	  [#assign checked = ""]
						  	  [#if marca.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="marca1${marca?index}" type="checkbox" data-value="variants.attributes.marca" data-label="${marca.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="marca1${marca?index}">${marca.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]
		
[/#macro]
				
[#-----------------------]
[#-------- tipo ---------]
[#-----------------------]
[#macro macrofiltrotipo titlemacro]

	[#assign checked = ""]
	[#if facets.variantsAttributesTipoProductoEs?has_content]
	     
	    [#assign tipos = facets.variantsAttributesTipoProductoEs.getTerms()!]
	    [#if tipos?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck2">
				<label class="tab-label" for="chck2">
					
					<span class="tab-label-text">${titlemacro!i18n['cione-module.templates.myshop.filtro-productos-component.types']}</span>
					
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list tipos?sort_by("term") as tipo]
						  	  [#assign checked = ""]
						  	  [#if tipo.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="tipo2${tipo?index}" type="checkbox" data-value="variants.attributes.tipoProducto.es" data-label="${tipo.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="tipo2${tipo?index}">${tipo.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]

[/#macro]
		
[#-----------------------]
[#------ Coleccion ------]
[#-----------------------]
[#macro macrofiltrocoleccion]

	[#assign checked = ""]
	[#if facets.variantsAttributesColeccionEs?has_content]
	     
	    [#assign colecciones = facets.variantsAttributesColeccionEs.getTerms()!]
	    [#if colecciones?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck4">
				<label class="tab-label" for="chck4">
					[#if content.filterSelect=="marketing"]
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.campanas']}</span>
					[#else]
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.collection']}</span>
					[/#if]
					
					
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list colecciones?sort_by("term") as coleccion]
						  	  [#assign checked = ""]
						  	  [#if coleccion.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="coleccion4${coleccion?index}" type="checkbox" data-value="variants.attributes.coleccion.es" data-label="${coleccion.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="coleccion4${coleccion?index}">${coleccion.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]
				
[/#macro]
				
[#-----------------------]
[#-------- Target -------]
[#-----------------------]
[#macro macrofiltrotarget]

	[#assign checked = ""]
	[#if facets.variantsAttributesTargetEs?has_content]
	     
	    [#assign targets = facets.variantsAttributesTargetEs.getTerms()!]
	    [#if targets?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck5">
				<label class="tab-label" for="chck5">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.target']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list targets?sort_by("term") as target]
						  	[#assign checked = ""]
						  	[#if target.selected]
						  	  	[#assign checked = "checked"]
						  	[/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="target5${target?index}" type="checkbox" data-value="variants.attributes.target.es" data-label="${target.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="target5${target?index}">${target.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]
				
[/#macro]
				
[#-----------------------]
[#------ Material -------]
[#-----------------------]
[#macro macrofiltromaterial]

	[#assign checked = ""]
	[#if facets.variantsAttributesMaterial?has_content]
	     
	    [#assign materiales = facets.variantsAttributesMaterial.getTerms()!]
	    [#if materiales?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck6">
				<label class="tab-label" for="chck6">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.material']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list materiales?sort_by("term") as material]
						  	  [#assign checked = ""]
						  	  [#if material.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="material6${material?index}" type="checkbox" data-value="variants.attributes.material" data-label="${material.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="material6${material?index}">${material.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]
				
[/#macro]

[#-----------------------]
[#-------- Color --------]
[#-----------------------]
[#macro macrofiltrogamacolormontura]

	[#assign checked = ""]
	[#if facets.variantsAttributesGamaColorMonturaEs?has_content]
	     
	    [#assign colores = facets.variantsAttributesGamaColorMonturaEs.getTerms()!]
	    [#if colores?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck7">
				<label class="tab-label" for="chck7">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.color']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list colores?sort_by("term") as color]
						  	  [#assign checked = ""]
						  	  [#if color.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="color7${color?index}" type="checkbox" data-value="variants.attributes.gamaColorMontura.es" data-label="${color.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="color7${color?index}">${color.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]
				
[/#macro]

[#-----------------------]
[#------- Calibre -------]
[#-----------------------]
[#macro macrofiltrocalibre]

	[#assign checked = ""]
	[#if facets.variantsAttributesDimensionesAnchoOjo?has_content]
	     
	    [#assign calibres = facets.variantsAttributesDimensionesAnchoOjo.getTerms()!]
	    [#if calibres?has_content]
			<div class="tab">
	
				<input class="b2b-hidden-check" type="checkbox" id="chck9">
				<label class="tab-label" for="chck9">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.caliber']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
	        
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list calibres?sort_by("term") as calibre]
						  	  [#assign checked = ""]
						  	  [#if calibre.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="calibre9${calibre?index}" type="checkbox" data-value="variants.attributes.dimensiones_ancho_ojo" data-label="${calibre.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="calibre9${calibre?index}">${calibre.term?split(".")[0]!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
	        
			</div>
		[/#if]
	[/#if]
				
[/#macro]
				
[#-----------------------]
[#------- Longitud ------]
[#-----------------------]
[#macro macrofiltrolongitud]

	[#assign checked = ""]
	[#if facets.variantsAttributesDimensionesLargoVarilla?has_content]
	     
	    [#assign logitudes = facets.variantsAttributesDimensionesLargoVarilla.getTerms()!]
	    [#if logitudes?has_content]
			<div class="tab">
	
				<input class="b2b-hidden-check" type="checkbox" id="chck10">
				<label class="tab-label" for="chck10">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.length']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
	        
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list logitudes?sort_by("term") as logitud]
						  	  [#assign checked = ""]
						  	  [#if logitud.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="logitud10${logitud?index}" type="checkbox" data-value="variants.attributes.dimensiones_largo_varilla" data-label="${logitud.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="logitud10${logitud?index}">${logitud.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
	        
			</div>
		[/#if]
	[/#if]

[/#macro]
				
[#-----------------------]
[#-------- Estado -------]
[#-----------------------]
[#macro macrofiltroestado]

	[#assign checked = ""]
	[#if facets.variantsAttributesStatusEkon?has_content]
	     
	    [#assign estados = facets.variantsAttributesStatusEkon.getTerms()!]
	    [#if estados?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck12">
				<label class="tab-label" for="chck12">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.edition']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list estados?sort_by("term") as estado]
						  	  [#assign checked = ""]
						  	  [#if estado.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="estado12${estado?index}" type="checkbox" data-value="variants.attributes.statusEkon" data-label="${estado.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="estado12${estado?index}">${estado.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]

[/#macro]

[#------------------------------]
[#------- Prueba Virtual -------]
[#------------------------------]
[#macro macrofiltropruebavirtual]

	[#assign checked = ""]
	[#if facets.variantsAttributesPruebaVirtual?has_content]
	     
	    [#assign pruebas = facets.variantsAttributesPruebaVirtual.getTerms()!]
	    [#if pruebas?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck37">
				<label class="tab-label" for="chck37">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.prueba-virtual']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list pruebas?sort_by("term") as prueba]
						  	  [#assign checked = ""]
						  	  [#if prueba.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	  [#assign valor = prueba.term]
						  	  [#assign tienePruebaVirtual = "false"]
						  	  [#if valor == "T"]
						  	  	[#assign tienePruebaVirtual = "true"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="prueba36${prueba?index}" type="checkbox" data-value="variants.attributes.pruebaVirtual" data-label="${tienePruebaVirtual!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="prueba36${prueba?index}">${tienePruebaVirtual?boolean?string(i18n['cione-module.templates.myshop.filtro-productos-component.con-prueba-virtual'], i18n['cione-module.templates.myshop.filtro-productos-component.sin-prueba-virtual'])}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]

[/#macro]


[#-----------------------]
[#------- Precios -------]
[#-----------------------]
[#macro macrofiltroprecios]

	<div class="tab">
		<input class="b2b-hidden-check" type="checkbox" id="chck11">
			<label class="tab-label tab-label-PVP" for="chck11">
				<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.pvo']}</span>
				<span class="tab-label-clean">
				<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
  				<img class="more-icon" src="${resourcesURL}/img/myshop/icons/minus.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
			</label>
			
		<div class="tab-content">

			<ul class="b2b-tab-items">
  				<li class="b2b-tab-item">
    				<div class="b2b-tab-range-wrapper">
    
						<script>
						  $(function () {
						  	var maxran = parseInt("${content.maxprice!"300"}".replace(".", ""));
						    $("#PVP01").slider({
						      range: true,
						      min: 0,
						      max: maxran,
						      values: [${(facets.min/100)?string.computer!""}, ${(facets.max/100)?string.computer!""}],
						      slide: function (event, ui) {
						        $("#amount01").val("Min:   " + ui.values[0] + " €   -   Max:   " + ui.values[1] + " €");
						        $('#PVPMIN').data('value', ui.values[0]);
						    	$('#PVPMAX').data('value', ui.values[1]);
						    	 $('#PRICEACTIVE').data('value', true);
						      }
						    });
						    $("#amount01").val("Min:   " + $("#PVP01").slider("values", 0) + " €   -   Max:   " +
						    + $("#PVP01").slider("values", 1) + " €");
						    $('#PVPMIN').data('value', $("#PVP01").slider("values", 0));
						    $('#PVPMAX').data('value', $("#PVP01").slider("values", 1));
						    
						  });
						</script>
      
      					<div class="b2b-tab-range-input">
        					<input type="text" id="amount01" readonly>
      					</div>
      					<div id="PVP01"></div>
      					<div id="PVPMAX" data-value="0" style="display:block"></div>
      					<div id="PVPMIN" data-value="0" style="display:block"></div>
      					<div id="PRICEACTIVE" data-value="${facets.activeprice?c!"false"}" style="display:block"></div>
    				</div>
				</li>
			</ul>
		</div>
	</div>

[/#macro]

[#------------------------------]
[#-------- linea negocio -------]
[#------------------------------]
[#macro macrofiltrolineanegocio]

	[#assign checked = ""]
	[#if facets.variantsAttributesLineaNegocio?has_content]
	     
	    [#assign lineasdenegocio = facets.variantsAttributesLineaNegocio.getTerms()!]
	    [#if lineasdenegocio?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck13">
				<label class="tab-label" for="chck13">
					[#if content.filterSelect=="contactologia"]
						<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.marca-cione']}</span>
					[#else]
						<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.lineanegocio']}</span>
					[/#if]
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list lineasdenegocio?sort_by("term") as lineadenegocio]
						  	  [#assign checked = ""]
						  	  [#if lineadenegocio.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="lineadenegocio13${lineadenegocio?index}" type="checkbox" data-value="variants.attributes.lineaNegocio" data-label="${lineadenegocio.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="lineadenegocio13${lineadenegocio?index}">${lineadenegocio.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]

[/#macro]

[#--------------------------]
[#-------- tipo pack -------]
[#--------------------------]
[#macro macrofiltrotipopack]

	[#assign checked = ""]
	[#if facets.variantsAttributesTipoPack?has_content]
	     
	    [#assign tipopacks = facets.variantsAttributesTipoPack.getTerms()!]
	    [#if tipopacks?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck14">
				<label class="tab-label" for="chck14">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.tipopack']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list tipopacks?sort_by("term") as tipopack]
						  	  [#assign checked = ""]
						  	  [#if tipopack.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="tipopack14${tipopack?index}" type="checkbox" data-value="variants.attributes.tipoPack" data-label="${tipopack.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="tipopack14${tipopack?index}">${tipopack.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]

[/#macro]

[#----------------------------------]
[#-------- Tiene Promociones -------]
[#----------------------------------]
[#macro macrofiltrotienePromociones titlemacro]

	[#assign checked = ""]
	[#if facets.variantsAttributesTienePromociones?has_content]
	     
	    [#assign promociones = facets.variantsAttributesTienePromociones.getTerms()!]
	    
	    [#if promociones?has_content]
	    
	    [#assign showit = false]
		  [#list promociones?sort_by("term") as promo]
			  [#if promo.term?has_content]
			  	[#assign valor = promo.term]
			  	[#assign tienePromo = "false"]
			  	[#if valor == "T"]
			  	  [#assign tienePromo = "true"]
			  	[/#if]
			  	[#if tienePromo == "true"]
				  	  	[#assign showit = true]
			  	  [/#if]
		  	  [/#if]
	  	  [/#list]
	  	  
	  	  [#if showit]
			<div class="tab">
				
				<input class="b2b-hidden-check" type="checkbox" id="chck15">
				<label class="tab-label" for="chck15">
					<span class="tab-label-text">${titlemacro!i18n['cione-module.templates.myshop.filtro-productos-component.tienePromociones']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
					  <ul class="b2b-tab-items">
					  	  [#assign checked = ""]
						  [#list promociones?sort_by("term") as promo]
							  [#if promo.term?has_content]
							  	[#assign valor = promo.term]
							  	[#assign tienePromo = "false"]
							  	[#if valor == "T"]
							  	  [#assign tienePromo = "true"]
							  	[/#if]
							  	[#if tienePromo == "true"]
								  	  [#if promo.selected]
								  	  	[#assign checked = "checked"]
								  	  [/#if]
							  	  [/#if]
							  [/#if]
					  	  [/#list]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="promo15" type="checkbox" data-value="variants.attributes.tienePromociones" data-label="true" ${checked!""}>
						      <label class="b2b-tab-label" for="promo15">${i18n['cione-module.templates.myshop.filtro-productos-component.tienePromociones.label']}</label>
						    </li>
					  </ul>
				</div>
			</div>
			[/#if]
		[/#if]
	[/#if]

[/#macro]

[#------------------------------------]
[#-------- subtipo (categoria) -------]
[#------------------------------------]
[#macro macrofiltrosubtipo titlemacro]

	[#assign checked = ""]
	[#if facets.variantsAttributesSubTipoProducto?has_content]
	     
	    [#assign subtiposProductos = facets.variantsAttributesSubTipoProducto.getTerms()!]
	    
	    [#if subtiposProductos?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck16">
				<label class="tab-label" for="chck16">

					[#if content.filterSelect=="marketing"]
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.material']}</span>
					[#else]
					<span class="tab-label-text">${titlemacro!i18n['cione-module.templates.myshop.filtro-productos-component.types']}</span>
					[/#if]

					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list subtiposProductos?sort_by("term") as subtipo]
						  	  [#assign checked = ""]
						  	  [#if subtipo.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="subtipo16${subtipo?index}" type="checkbox" data-value="variants.attributes.subTipoProducto" data-label="${subtipo.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="subtipo16${subtipo?index}">${subtipo.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]

[/#macro]

[#----------------------------------------]
[#-------- material (materialbase) -------]
[#----------------------------------------]
[#macro macrofiltromaterialbase]

	[#assign checked = ""]
	[#if facets.variantsAttributesMaterialbase?has_content]
	     
	    [#assign materialesbase = facets.variantsAttributesMaterialbase.getTerms()!]
	    
	    [#if materialesbase?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck17">
				<label class="tab-label" for="chck17">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.material']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list materialesbase?sort_by("term") as materialbase]
						  	  [#assign checked = ""]
						  	  [#if materialbase.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="materialbase17${materialbase?index}" type="checkbox" data-value="variants.attributes.materialBase" data-label="${materialbase.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="materialbase17${materialbase?index}">${materialbase.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]

[/#macro]

[#---------------------------]
[#-------- geometria --------]
[#---------------------------]
[#macro macrofiltrogeometria]

	[#assign checked = ""]
	[#if facets.variantsAttributesGeometria?has_content]
	     
	    [#assign geometrias = facets.variantsAttributesGeometria.getTerms()!]
	    
	    [#if geometrias?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck18">
				<label class="tab-label" for="chck18">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.geometria']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list geometrias?sort_by("term") as geometria]
						  	  [#assign checked = ""]
						  	  [#if geometria.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="geometria18${geometria?index}" type="checkbox" data-value="variants.attributes.geometria" data-label="${geometria.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="geometria18${geometria?index}">${geometria.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]

[/#macro]

[#--------------------------]
[#---------- gama ----------]
[#--------------------------]
[#macro macrofiltrogama]

	[#assign checked = ""]
	[#if facets.variantsAttributesGama?has_content]
	     
	    [#assign gamas = facets.variantsAttributesGama.getTerms()!]
	    
	    [#if gamas?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck19">
				<label class="tab-label" for="chck19">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.gama']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list gamas?sort_by("term") as gama]
						  	  [#assign checked = ""]
						  	  [#if gama.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="gama19${gama?index}" type="checkbox" data-value="variants.attributes.gama" data-label="${gama.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="gama19${gama?index}">${gama.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]

[/#macro]

[#-------------------------------]
[#---------- proveedor ----------]
[#-------------------------------]
[#macro macrofiltroproveedor]

	[#assign checked = ""]
	[#if facets.variantsAttributesProveedor?has_content]
	     
	    [#assign proveedores = facets.variantsAttributesProveedor.getTerms()!]
	    
	    [#if proveedores?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck20">
				<label class="tab-label" for="chck20">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.proveedor']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list proveedores?sort_by("term") as proveedor]
						  	  [#assign checked = ""]
						  	  [#if proveedor.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="proveedor20${proveedor?index}" type="checkbox" data-value="variants.attributes.proveedor" data-label="${proveedor.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="proveedor20${proveedor?index}">${proveedor.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]

[/#macro]

[#--------------------------------------------------]
[#---------- filtro UV (bproteccionSolar) ----------]
[#--------------------------------------------------]
[#macro macrofiltrouv]

	[#assign checked = ""]
	[#if facets.variantsAttributesBproteccionSolar?has_content]
	     
	    [#assign protecciones = facets.variantsAttributesBproteccionSolar.getTerms()!]
	    
	    [#if protecciones?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck21">
				<label class="tab-label" for="chck21">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.proteccion']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list protecciones?sort_by("term") as proteccion]
						  	  [#assign checked = ""]
						  	  [#if proteccion.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	  
						  	  [#assign checked = ""]
						  	  [#if proteccion.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	  [#assign valor = proteccion.term]
						  	  [#assign tieneProteccion = "false"]
						  	  [#if valor == "T"]
						  	  	[#assign tieneProteccion = "true"]
						  	  [/#if]

						  	  	<li class="b2b-tab-item">
							      <input class="b2b-tab-check styled-checkbox" id="proteccion21${proteccion?index}" type="checkbox" data-value="variants.attributes.bproteccionSolar" data-label="${tieneProteccion!""}" ${checked!""}>
							      <label class="b2b-tab-label" for="proteccion21${proteccion?index}">${tieneProteccion?boolean?string(i18n['cione-module.templates.myshop.filtro-productos-component.conproteccion'], i18n['cione-module.templates.myshop.filtro-productos-component.sinproteccion'])}</label>
							    </li>

						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]

[/#macro]

[#-------------------------------]
[#---------- reemplazo ----------]
[#-------------------------------]
[#macro macrofiltroreemplazo]

	[#assign checked = ""]
	[#if facets.variantsAttributesReemplazo?has_content]
	     
	    [#assign reemplazos = facets.variantsAttributesReemplazo.getTerms()!]
	    
	    [#if reemplazos?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck22">
				<label class="tab-label" for="chck22">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.reemplazo']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list reemplazos?sort_by("term") as reemplazo]
						  	  [#assign checked = ""]
						  	  [#if reemplazo.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="reemplazo22${reemplazo?index}" type="checkbox" data-value="variants.attributes.reemplazo" data-label="${reemplazo.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="reemplazo22${reemplazo?index}">${reemplazo.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]

[/#macro]

[#------------------------------------]
[#---------- disponibilidad ----------]
[#------------------------------------]
[#macro macrofiltrodisponibilidad]

	[#assign checked = ""]
	[#if facets.variantsAttributesDisponibilidad?has_content]
	     
	    [#assign disponibilidades = facets.variantsAttributesDisponibilidad.getTerms()!]
	    
	    [#if disponibilidades?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck23">
				<label class="tab-label" for="chck23">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.disponibilidad']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list disponibilidades?sort_by("term") as disponibilidad]
						  	  [#assign checked = ""]
						  	  [#if disponibilidad.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="disponibilidad23${disponibilidad?index}" type="checkbox" data-value="variants.attributes.disponibilidad" data-label="${disponibilidad.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="disponibilidad23${disponibilidad?index}">${disponibilidad.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]

[/#macro]

[#--------------------------------------------]
[#---------- blister (blisterocaja) ----------]
[#--------------------------------------------]
[#macro macrofiltroblisterocaja]

	[#assign checked = ""]
	[#if facets.variantsAttributesBlisterocaja?has_content]
	     
	    [#assign blisterocajas = facets.variantsAttributesBlisterocaja.getTerms()!]
	    
	    [#if blisterocajas?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck24">
				<label class="tab-label" for="chck24">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.blisterocaja']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list blisterocajas?sort_by("term") as blisterocaja]
						  	  [#assign checked = ""]
						  	  [#if blisterocaja.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="blisterocaja24${blisterocaja?index}" type="checkbox" data-value="variants.attributes.blisterocaja.key" data-label="${blisterocaja.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="blisterocaja24${blisterocaja?index}">${blisterocaja.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]

[/#macro]

[#--------------------------------------------]
[#---------- tamanios (tamanio) ----------]
[#--------------------------------------------]
[#macro macrofiltrotamanios]

	[#assign checked = ""]
	[#if facets.variantsAttributesTamaniosEs?has_content]
	     
	    [#assign tamanios = facets.variantsAttributesTamaniosEs.getTerms()!]
	    
	    [#if tamanios?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck25">
				<label class="tab-label" for="chck25">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.size']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list tamanios?sort_by("term") as tamanio]
						  	  [#assign checked = ""]
						  	  [#if tamanio.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="tamanios25${tamanio?index}" type="checkbox" data-value="variants.attributes.tamanios.es" data-label="${tamanio.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="tamanios25${tamanio?index}">${tamanio.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]

[/#macro]

[#-----------------------]
[#-------- Color --------]
[#-----------------------]
[#macro macrofiltrocolormontura]

	[#assign checked = ""]
	[#if facets.variantsAttributesColorMonturaEs?has_content]
	
	    [#assign colores = facets.variantsAttributesColorMonturaEs.getTerms()!]
	    
	    [#if colores?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck26">
				<label class="tab-label" for="chck26">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.color']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list colores?sort_by("term") as color]
						  	  [#assign checked = ""]
						  	  [#if color.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="color26${color?index}" type="checkbox" data-value="variants.attributes.colorMontura.es" data-label="${color.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="color26${color?index}">${color.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]
				
[/#macro]

[#------------------------]
[#-------- Modelo --------]
[#------------------------]
[#macro macrofiltromodelo]

	[#assign checked = ""]
	[#if facets.variantsAttributesModelo?has_content]
	
	    [#assign modelos = facets.variantsAttributesModelo.getTerms()!]
	    
	    [#if modelos?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck27">
				<label class="tab-label" for="chck27">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.modelo']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list modelos?sort_by("term") as modelo]
						  	  [#assign checked = ""]
						  	  [#if modelo.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="color27${modelo?index}" type="checkbox" data-value="variants.attributes.modelo" data-label="${modelo.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="color27${modelo?index}">${modelo.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]
				
[/#macro]

[#-----------------------------]
[#-------- composicion --------]
[#-----------------------------]
[#macro macrofiltrocomposicion]

	[#assign checked = ""]
	[#if facets.variantsAttributescComposicion?has_content]
	
	    [#assign modelos = facets.variantsAttributescComposicion.getTerms()!]
	    
	    [#if modelos?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck27">
				<label class="tab-label" for="chck27">
					<span class="tab-label-text">Tipo Articulo</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list modelos?sort_by("term") as modelo]
						  	  [#assign checked = ""]
						  	  [#if modelo.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="composicion28${modelo?index}" type="checkbox" data-value="variants.attributes.composicion" data-label="${modelo.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="composicion28${modelo?index}">${modelo.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]
				
[/#macro]


[#-------------------------]
[#-------- familia --------]
[#-------------------------]
[#macro macrofiltrofamiliaaudio]

	[#assign checked = ""]
	[#if facets.variantsAttributesFamiliaAudio?has_content]
	     
	    [#assign audiofamilias = facets.variantsAttributesFamiliaAudio.getTerms()!]
	    [#if audiofamilias?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck28">
				<label class="tab-label" for="chck28">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.familia']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list audiofamilias?sort_by("term") as audiofamilia]
						  	  [#assign checked = ""]
						  	  [#if audiofamilia.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="audiofamilia28${audiofamilia?index}" type="checkbox" data-value="variants.attributes.familiaAudio" data-label="${audiofamilia.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="audiofamilia28${audiofamilia?index}">${audiofamilia.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]
		
[/#macro]

[#--------------------------]
[#-------- segmento --------]
[#--------------------------]
[#macro macrofiltrosegmento]

	[#assign checked = ""]
	[#if facets.variantsAttributesSegmento?has_content]
	     
	    [#assign segmentos = facets.variantsAttributesSegmento.getTerms()!]
	    [#if segmentos?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck29">
				<label class="tab-label" for="chck29">
					<span class="tab-label-text">Segmento</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list segmentos?sort_by("term") as segmento]
						  	  [#assign checked = ""]
						  	  [#if segmento.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="segmento29${segmento?index}" type="checkbox" data-value="variants.attributes.segmento" data-label="${segmento.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="segmento29${segmento?index}">${segmento.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]
		
[/#macro]

[#------------------------------]
[#-------- prestaciones --------]
[#------------------------------]
[#macro macrofiltroprestaciones]

	[#assign checked = ""]
	[#if facets.variantsAttributesPrestaciones?has_content]
	     
	    [#assign prestaciones = facets.variantsAttributesPrestaciones.getTerms()!]
	    [#if prestaciones?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck30">
				<label class="tab-label" for="chck30">
					<span class="tab-label-text">Prestaciones</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list prestaciones?sort_by("term") as prestacion]
						  	  [#assign checked = ""]
						  	  [#if prestacion.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="prestacion30${prestacion?index}" type="checkbox" data-value="variants.attributes.prestaciones" data-label="${prestacion.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="prestacion30${prestacion?index}">${prestacion.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]
		
[/#macro]

[#------------------------------]
[#-------- pila --------]
[#------------------------------]
[#macro macrofiltropila]

	[#assign checked = ""]
	[#if facets.variantsAttributesPila?has_content]
	     
	    [#assign pilas = facets.variantsAttributesPila.getTerms()!]
	    [#if pilas?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck31">
				<label class="tab-label" for="chck31">
					<span class="tab-label-text">Pila</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list pilas?sort_by("term") as pila]
						  	  [#assign checked = ""]
						  	  [#if pila.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="pila31${pila?index}" type="checkbox" data-value="variants.attributes.pila" data-label="${pila.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="pila31${pila?index}">${pila.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]
		
[/#macro]

[#---------------------------------]
[#-------- tamaño audifono --------]
[#---------------------------------]
[#macro macrofiltrotamanioaudio]

	[#assign checked = ""]
	[#if facets.variantsAttributesSize?has_content]
	     
	    [#assign sizes = facets.variantsAttributesSize.getTerms()!]
	    [#if sizes?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck33">
				<label class="tab-label" for="chck33">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.tamanio']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list sizes?sort_by("term") as size]
						  	  [#assign checked = ""]
						  	  [#if size.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="size33${size?index}" type="checkbox" data-value="variants.attributes.size" data-label="${size.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="size33${size?index}">${size.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]
		
[/#macro]

[#---------------------------------]
[#-------- color audifono --------]
[#---------------------------------]
[#macro macrofiltrocoloraudio]

	[#assign checked = ""]
	[#if facets.variantsAttributesColorAudio?has_content]
	     
	    [#assign colores = facets.variantsAttributesColorAudio.getTerms()!]
	    [#if colores?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck34">
				<label class="tab-label" for="chck34">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.color']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list colores?sort_by("term") as color]
						  	  [#assign checked = ""]
						  	  [#if color.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="color34${color?index}" type="checkbox" data-value="variants.attributes.color" data-label="${color.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="color34${color?index}">${color.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]
		
[/#macro]

[#---------------------------------]
[#-------- formato audifono --------]
[#---------------------------------]
[#macro macrofiltroformato]

	[#assign checked = ""]
	[#if facets.variantsAttributesFormatosAudio?has_content]
	     
	    [#assign formatosAudio = facets.variantsAttributesFormatosAudio.getTerms()!]
	    [#if formatosAudio?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck35">
				<label class="tab-label" for="chck35">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.formato']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list formatosAudio?sort_by("term") as formato]
						  	  [#assign checked = ""]
						  	  [#if formato.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="color35${formato?index}" type="checkbox" data-value="variants.attributes.formatoAudio" data-label="${formato.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="color35${formato?index}">${formato.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]
		
[/#macro]

[#-----------------------]
[#----- graduacion ------]
[#-----------------------]
[#macro macrofiltrograduacion]

	[#assign checked = ""]
	[#if facets.variantsAttributesGraduacion?has_content]
	     
	    [#assign graduaciones = facets.variantsAttributesGraduacion.getTerms()!]
	    [#if graduaciones?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck36">
				<label class="tab-label" for="chck36">
					<span class="tab-label-text">${i18n['cione-module.templates.myshop.filtro-productos-component.prescription']}</span>
					<span class="tab-label-clean">
					<span class="tab-label-clean-text">${i18n['cione-module.templates.myshop.filtro-productos-component.clear']}</span>
					<img class="more-icon" src="${resourcesURL}/img/myshop/icons/more.svg" alt="${i18n['cione-module.templates.myshop.filtro-productos-component.expand']}"></span>
				</label>
            
				<div class="tab-content">
				  
					  <ul class="b2b-tab-items">
						  [#list graduaciones?sort_by("term") as graduacion]
						  	  [#assign checked = ""]
						  	  [#if graduacion.selected]
						  	  	[#assign checked = "checked"]
						  	  [/#if]
						  	<li class="b2b-tab-item">
						      <input class="b2b-tab-check styled-checkbox" id="graduacion36${graduacion?index}" type="checkbox" data-value="variants.attributes.graduacion" data-label="${graduacion.term!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="graduacion36${graduacion?index}">${graduacion.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]
		
[/#macro]


[#-----------------------------------------------------------------------------------------------]
[#----------------------------- END: MACROS CON CADA FILTROS A USAR -----------------------------]
[#-----------------------------------------------------------------------------------------------]

[#----------------------------------------------------------------------------------------------------]
[#----------------------------- BEGIN: MACROS PARA CADA TIPO DE PRODUCTO -----------------------------]
[#----------------------------------------------------------------------------------------------------]


[#macro macrofiltrogenerico content]
	[@macrofiltrolistado  content/]
	[#if content.showpvo?? && content.showpvo]
		[@macrofiltroprecios /]
	[/#if]
[/#macro]

[#--------------------------------]
[#-------- Filtro monturas -------]
[#--------------------------------]
[#macro macrofiltromonturas]

	[@macrofiltromarca i18n['cione-module.templates.myshop.filtro-productos-component.brand'] /]
	
	[@macrofiltrotipo i18n['cione-module.templates.myshop.filtro-productos-component.types'] /]
	
	[@macrofiltrocoleccion /]
	
	[@macrofiltrotarget /]
	
	[@macrofiltromaterial /]
	
	[@macrofiltrogamacolormontura /]
	
	[@macrofiltrocalibre /]
	
	[@macrofiltrograduacion /]
	
	[@macrofiltrolongitud /]
	
	[@macrofiltroestado /]
	
	[#-- [@macrofiltropruebavirtual /] --]
	
	[@macrofiltroprecios /]
	
[/#macro]

[#----------------------------------]
[#-------- Filtro soluciones -------]
[#----------------------------------]
[#macro macrofiltrosoluciones]

	[@macrofiltroproveedor /]

	[@macrofiltrosubtipo i18n['cione-module.templates.myshop.filtro-productos-component.types'] /]	
	
	[@macrofiltrolineanegocio /]
	
	[@macrofiltrotipopack /]
	
	[@macrofiltrotienePromociones i18n['cione-module.templates.myshop.filtro-productos-component.tienePromociones'] /]
		
[/#macro]

[#-------------------------------------]
[#-------- Filtro de accesorios -------]
[#-------------------------------------]
[#macro macrofiltroaccesorios]

	[@macrofiltrotipo i18n['cione-module.templates.myshop.filtro-productos-component.tipoproducto'] /]
	
	[@macrofiltrosubtipo i18n['cione-module.templates.myshop.filtro-productos-component.category'] /]	

	[@macrofiltrocomposicion /]
	
	[@macrofiltrocolormontura /]
		
[/#macro]

[#------------------------------------]
[#-------- Filtro de marketing -------]
[#------------------------------------]
[#macro macrofiltromarketing]

	[@macrofiltrotipo i18n['cione-module.templates.myshop.filtro-productos-component.tipoproducto'] /]
	
	[@macrofiltrocoleccion /]
	
	[@macrofiltromarca i18n['cione-module.templates.myshop.filtro-productos-component.brand'] /]
	
	[@macrofiltromodelo /]
	
	[@macrofiltrosubtipo i18n['cione-module.templates.myshop.filtro-productos-component.material']/]
	
	[@macrofiltrotarget /]
	
	[@macrofiltrocolormontura /]
	
	[@macrofiltrotamanios /]
		
[/#macro]

[#----------------------------------------]
[#-------- Filtro de contactologia -------]
[#----------------------------------------]
[#macro macrofiltrocontacto]

	[@macrofiltroblisterocaja /]
	
	[@macrofiltroreemplazo /]
	
	[@macrofiltromaterialbase /] [#-- no test --]
	
	[@macrofiltrogeometria /]
	
	[@macrofiltrolineanegocio /]
	
	[@macrofiltrogama /]
	
	[@macrofiltroproveedor /]
	
	[@macrofiltrouv /]
	
	[@macrofiltrodisponibilidad /]
		
[/#macro]


[#-----------------------------------------]
[#-------- Filtro de audiologia -----------]
[#-----------------------------------------]
[#macro macrofiltroaudiologia]
	
	[@macrofiltromarca i18n['cione-module.templates.myshop.filtro-productos-component.brand'] /]

	[@macrofiltrofamiliaaudio /]
	
	[@macrofiltrosegmento /]
	
	[#-- PEndiente de validar cuando podemos subirlo [@macrofiltroformato /] --]
	
	[@macrofiltrosubtipo i18n['cione-module.templates.myshop.filtro-productos-component.formato'] /]
	
	[@macrofiltrotamanioaudio /]
	
	[@macrofiltrocoloraudio /]
	
	[@macrofiltromodelo /]
	
	[@macrofiltrogama /]
	
	[@macrofiltrotienePromociones i18n['cione-module.templates.myshop.filtro-productos-component.promo'] /]
	
	[@macrofiltroprecios /]
	
	
		
[/#macro]

[#--------------------------------]
[#-------- Filtro tapones --------]
[#--------------------------------]
[#macro macrofiltrotapones]

	[@macrofiltrotipo i18n['cione-module.templates.myshop.filtro-productos-component.tipoproducto'] /]
	
	[@macrofiltromarca i18n['cione-module.templates.myshop.filtro-productos-component.brand'] /]
	
	[@macrofiltrosubtipo i18n['cione-module.templates.myshop.filtro-productos-component.tipoarticulo'] /]	
	
	[@macrofiltrotienePromociones i18n['cione-module.templates.myshop.filtro-productos-component.promo'] /]
	
	[@macrofiltroprecios /]
	
[/#macro]

[#---------------------------------]
[#-------- Filtro baterias --------]
[#---------------------------------]
[#macro macrofiltrobaterias]

	[@macrofiltrotipo i18n['cione-module.templates.myshop.filtro-productos-component.tipoproducto'] /]
	
	[@macrofiltromarca i18n['cione-module.templates.myshop.filtro-productos-component.brand'] /]
	
	[@macrofiltrosubtipo i18n['cione-module.templates.myshop.filtro-productos-component.tipoarticulo'] /]
	
	[@macrofiltrotienePromociones i18n['cione-module.templates.myshop.filtro-productos-component.promo'] /]
	
	[@macrofiltroprecios /]
	
[/#macro]

[#-------------------------------------]
[#-------- Filtro complementos --------]
[#-------------------------------------]
[#macro macrofiltrocomplementos]

	[@macrofiltrotipo i18n['cione-module.templates.myshop.filtro-productos-component.tipoproducto'] /]
	
	[@macrofiltromarca i18n['cione-module.templates.myshop.filtro-productos-component.brand'] /]
	
	[@macrofiltrosubtipo i18n['cione-module.templates.myshop.filtro-productos-component.tipoarticulo'] /]
	
	[@macrofiltrotienePromociones i18n['cione-module.templates.myshop.filtro-productos-component.promo'] /]
	
	[@macrofiltroprecios /]
	
[/#macro]

[#------------------------------------------------]
[#-------- Filtro accesorios inalambricos --------]
[#------------------------------------------------]
[#macro macrofiltroaccesoriosinalambricos]

	[@macrofiltrotipo i18n['cione-module.templates.myshop.filtro-productos-component.tipoproducto'] /]
	
	[@macrofiltromarca i18n['cione-module.templates.myshop.filtro-productos-component.brand'] /]
	
	[@macrofiltrosubtipo i18n['cione-module.templates.myshop.filtro-productos-component.tipoarticulo'] /]
	
	[@macrofiltrotienePromociones i18n['cione-module.templates.myshop.filtro-productos-component.promo'] /]
	
	[@macrofiltroprecios /]

[/#macro]

[#--------------------------------------------------------------------------------------------------]
[#----------------------------- END: MACROS PARA CADA TIPO DE PRODUCTO -----------------------------]
[#--------------------------------------------------------------------------------------------------]