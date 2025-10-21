[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]
[#include "../../includes/macros/ct-utils.ftl"]
[#include "../../includes/macros/cione-utils.ftl"]
[#assign facets = model.facets!]

[#if facets?has_content && model.hasFacets()!false]

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
				
				[@macrofiltrolentes /]
	    		
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
			
				var getUrlParameter = function getUrlParameter(sParam) {
				    var sPageURL = window.location.search.substring(1),
				        sURLVariables = sPageURL.split('&'),
				        sParameterName,
				        i;
				
				    for (i = 0; i < sURLVariables.length; i++) {
				        sParameterName = sURLVariables[i].split('=');
				
				        if (sParameterName[0] === sParam) {
				            return typeof sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
				        }
				    }
				    return false;
				};
				
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
				[#else]
					var url = (window.location.href).split('?')[0];
					url += '?category=' + getUrlParameter('category');
				[/#if]
				
				if($('#PRICEACTIVE').data('value')){
					var pricefilter = '&variants.price.centAmount=range( '+ $('#PVPMIN').data('value') + ' to ' + $('#PVPMAX').data('value') +' )';
					url += pricefilter;
				}
				
				[#-- codificacion de la url:
				
					 local: url += ('&' + $(this).data('value') + '=' + escape($(this).data('label')));
					 dev: url += ('&' + $(this).data('value') + '=' + $(this).data('label'));
				--]
				
			    $('.b2b-tab-check:checkbox:checked').each(function () {
			    	url += ('&' + $(this).data('value') + '=' + encodeURIComponent($(this).data('label')));
				});
				
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
			                console.log(n.id);
			
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
		
		});
		
	</script>

[/#if]


[#-------------------------------------------------------------------------------------------------]
[#----------------------------- BEGIN: MACROS CON CADA FILTROS A USAR -----------------------------]
[#-------------------------------------------------------------------------------------------------]

[#-------------------------------]
[#---------- proveedor ----------]
[#-------------------------------]
[#macro macrofiltroproveedor]

	[#assign checked = ""]
	[#if facets.proveedores?has_content]
	     
	    [#assign proveedores = facets.proveedores.getTerms()!]
	    
	    [#if proveedores?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck10">
				<label class="tab-label" for="chck10">
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
						      <input class="b2b-tab-check styled-checkbox" id="proveedor10${proveedor?index}" type="checkbox" data-value="variants.attributes.proveedor" data-label="${proveedor.value?trim!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="proveedor10${proveedor?index}">${proveedor.term!""}</label>
						    </li>
						  [/#list]
					  </ul>
				  
				</div>
            
			</div>
		[/#if]
	[/#if]

[/#macro]

[#-------------------------------]
[#---------- material ----------]
[#-------------------------------]
[#macro macrofiltromaterial]

	[#assign checked = ""]
	[#if facets.materiales?has_content]
	     
	    [#assign materiales = facets.materiales.getTerms()!]
	    
	    [#if materiales?has_content]
			<div class="tab">

				<input class="b2b-hidden-check" type="checkbox" id="chck20">
				<label class="tab-label" for="chck20">
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
						      <input class="b2b-tab-check styled-checkbox" id="material20${material?index}" type="checkbox" data-value="variants.attributes.material" data-label="${material.value?trim!""}" ${checked!""}>
						      <label class="b2b-tab-label" for="material20${material?index}">${material.term!""}</label>
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

[#--------------------------------]
[#-------- Filtro lentes -------]
[#--------------------------------]
[#macro macrofiltrolentes]

	[#--[@macrofiltroproveedor /]--]
	
	[@macrofiltromaterial /]
	
[/#macro]

[#--------------------------------------------------------------------------------------------------]
[#----------------------------- END: MACROS PARA CADA TIPO DE PRODUCTO -----------------------------]
[#--------------------------------------------------------------------------------------------------]