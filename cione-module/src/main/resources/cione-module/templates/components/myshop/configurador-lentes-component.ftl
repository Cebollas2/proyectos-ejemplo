[#include "../../includes/macros/cione-utils-impersonate.ftl"]
[#assign uuid = model.getUuid()!]
[#assign username = model.getUserName()!]
[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]
[#include "../../includes/macros/ct-utils.ftl"]

[#assign cilindros = model.cilindros!]

[#-- estilos para en caso de que no existan cilindros --]
<style>.validation-error {box-shadow: 0 0 0px 1px #EE0000;}</style>

[#-- componente configurador --]
<div class="b2b-lentes-tabla container-fluid">

    <div class="b2b-lentes-tabla-header row">
        <div class="col-xxl-1"></div>
        <div class="col-xxl-1">${i18n['cione-module.templates.myshop.configurador-lentes-component.cilindro']}</div>
        <div class="col-xxl-1">${i18n['cione-module.templates.myshop.configurador-lentes-component.esfera']}</div>
        <div class="col-xxl-2">${i18n['cione-module.templates.myshop.configurador-lentes-component.lente']}</div>
        <div class="col-xxl-1">${i18n['cione-module.templates.myshop.configurador-lentes-component.diametro']}</div>
        <div class="col-xxl-1">${i18n['cione-module.templates.myshop.configurador-lentes-component.unidades']}</div>
        <div class="col-xxl-2"><span>${i18n['cione-module.templates.myshop.configurador-lentes-component.taller']}</span><span class="b2b-lentes-tabla__head-Ref">${i18n['cione-module.templates.myshop.configurador-lentes-component.reftaller']}</span></div>
        <div class="col-xxl-1">${i18n['cione-module.templates.myshop.configurador-lentes-component.refcliente']}</div>
        <div class="col-xxl-1">${i18n['cione-module.templates.components.detalle-producto-component.pvo']}</div>
        <div class="col-xxl-1">${i18n['cione-module.templates.components.detalle-producto-component.pvp']}</div>
    </div>
    
    [#-------------------------------------------------]
    [#----------------- OJO DERECHO -------------------]
    [#-------------------------------------------------]
    <div class="b2b-lentes-tabla-info row">
    
        <div class="col-xxl-1 b2b-lentes-tabla-text b2b-lentes-tabla-text-OD">
            <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.od']}</span>
            <span class="b2b-lentes-more">-</span>
        </div>
        
        [#-- CILINDRO --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.cilindro']}</label>
            <select name="" id="cilojodrch">
            	<option value="" selected></option>
	            [#if cilindros?has_content]
	            	[#list cilindros as cilindro]
		                <option value="${cilindro!""}">${cilindro!""}</option>
	                [/#list]
	            [/#if]
            </select>
        </div>
        
        [#-- ESFERA --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.esfera']}</label>
            <select name="" id="esfojodrch" disabled>
            </select>
        </div>
        
        [#-- LENTE --]
        <div class="col-xxl-2">
        	<label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.lente']}</label>
        	<select name="" id="lenojodrch" disabled>
            </select>
        </div>
        
        [#-- DIAMETRO --]
        <div class="col-xxl-1">
        	<label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.diametro']}</label>
        	<select id="diaojodrch" disabled>
            </select>
        </div>
        
        [#-- CANTIDAD --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.unidades']}</label>
            <div class="product-amount">
                <div class="product-amount-button-wrapper">
                    <button class="product-amount-button product-amount-button-minus" type="button">
                        -
                    </button>
                    <input id="cantojodrch" class="product-amount-input" value="0" readonly>
                    <button class="product-amount-button product-amount-button-plus" type="button">
                        +
                    </button>
                </div>
            </div>
        </div>
        
		<div class="col-xxl-2 b2b-lentes-tabla_wrapper">
        	[#-- TALLER --]
	        <div class="b2b-lentes-tabla__taller-wrapper">
	            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.taller']}</label>
	            <div class="b2b-lentes-tabla-chek-wrapper">
	                <input class="b2b-lentes-tab-check styled-checkbox" id="tallerojodrch" type="checkbox" checked="false">
	                <label class="b2b-label" for="tallerojodrch"></label>
	            </div>
			</div>
		
			[#-- REF. TALLER --]
            <div class="b2b-lentes-tabla__reftaller-wrapper">
                <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.reftaller']}</label>
                <input type="text" id="reftallerojodrch" style="background-color: #d1d1d1;" disabled>
            </div>
        </div>
        
		
		[#-- REF. CLIENTE --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.refcliente']}</label>
            <input type="text" id="refclienteojodrch">
        </div>
		
		[#-- PVO --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.components.detalle-producto-component.pvo']}</label>
            <input type="text" id="pvoojodrch" readonly autocomplete="off">
        </div>
		
		[#-- PVP --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.components.detalle-producto-component.pvp']}</label>
            <input type="text" id="pvpojodrch" readonly autocomplete="off">
        </div>
        
    </div>
	
    [#---------------------------------------------------]
    [#----------------- OJO IZQUIERDO -------------------]
    [#---------------------------------------------------]
    <div class="b2b-lentes-tabla-info row">
    
        <div class="col-xxl-1 b2b-lentes-tabla-text b2b-lentes-tabla-text-OI">
            <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.oi']}</span>
            <span class="b2b-lentes-more">-</span>
        </div>
        
        [#-- CILINDRO --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.cilindro']}</label>
            <select name="" id="cilojoizq">
            	<option value="" selected></option>
            	[#if cilindros?has_content]
	            	[#list cilindros as cilindro]
		                <option value="${cilindro!""}">${cilindro!""}</option>
	                [/#list]
                [/#if]
            </select>
        </div>
        
        [#-- ESFERA --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.esfera']}</label>
            <select name="" id="esfojoizq" disabled>
            </select>
        </div>
        
        [#-- LENTE --]
        <div class="col-xxl-2">
        	<label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.lente']}</label>
        	<select name="" id="lenojoizq" disabled>
            </select>
        </div>
        
        [#-- DIAMETRO --]  
        <div class="col-xxl-1">
        	<label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.diametro']}</label>
        	<select name="" id="diaojoizq" disabled>
            </select>
        </div>
         
        [#-- CANTIDAD --]   
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.unidades']}</label>
            <div class="product-amount">
                <div class="product-amount-button-wrapper">
                    <button class="product-amount-button product-amount-button-minus" type="button">
                        -
                    </button>
                    <input id="cantojoizq" class="product-amount-input" value="0" readonly>
                    <button class="product-amount-button product-amount-button-plus" type="button">
                        +
                    </button>
                </div>
            </div>
        </div>
        
        <div class="col-xxl-2 b2b-lentes-tabla_wrapper">
	        [#-- TALLER --]
	        <div class="b2b-lentes-tabla__taller-wrapper">
	            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.taller']}</label>
	            <div class="b2b-lentes-tabla-chek-wrapper">
	                <input class="b2b-lentes-tab-check styled-checkbox" id="tallerojoizq" type="checkbox" checked="false">
	                <label class="b2b-label" for="tallerojoizq"></label>
	            </div>
	        </div>
	
			[#-- REF. TALLER --]
	        <div class="b2b-lentes-tabla__reftaller-wrapper">
	            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.reftaller']}</label>
	            <input id="reftallerojoizq" type="text" style="background-color: #d1d1d1;" disabled>
	        </div>
		</div>
        
        [#-- REF. CLIENTE --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.refcliente']}</label>
            <input id="refclienteojoizq" type="text">
        </div>
		
		[#-- PVO --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.components.detalle-producto-component.pvo']}</label>
            <input type="text" id="pvoojoizq" readonly autocomplete="off">
        </div>
		
		[#-- PVP --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.components.detalle-producto-component.pvp']}</label>
            <input type="text" id="pvpojoizq" readonly autocomplete="off">
        </div>
        
       <input id="descripciondrch" type="hidden" autocomplete="off">
       <input id="namedrch" type="hidden" autocomplete="off">
       <input id="nivel1drch" type="hidden" autocomplete="off">
       <input id="nivel2drch" type="hidden" autocomplete="off">
       <input id="pvodrch" type="hidden" autocomplete="off">
       <input id="pvofrontdrch" type="hidden" autocomplete="off">
       <input id="pvpfrontdrch" type="hidden" autocomplete="off">
       <input id="skudrch" type="hidden" autocomplete="off">
       <input id="lmattypedrch" type="hidden" autocomplete="off">
        
       <input id="descripcionizq" type="hidden" autocomplete="off">
       <input id="nameizq" type="hidden" autocomplete="off">
       <input id="nivel1izq" type="hidden" autocomplete="off">
       <input id="nivel2izq" type="hidden" autocomplete="off">
       <input id="pvoizq" type="hidden" autocomplete="off">
       <input id="pvofrontizq" type="hidden" autocomplete="off">
       <input id="pvpfrontizq" type="hidden" autocomplete="off">
       <input id="skuizq" type="hidden" autocomplete="off">
       <input id="lmattypeizq" type="hidden" autocomplete="off">
        
    </div>

	<div class="b2b-lentes-button-wrapper d-flex justify-content-end mt-4 mb-5">

		<div class="b2b-lentes-sotck-wrapper">
			<div>
				<span class="b2b-lentes-stock-info" id="stockinfonombredrch"></span> <span class="b2b-lentes-stock-amount" id="stockinfounidadesdrch"></span>
			</div>
			<div>
				<span class="b2b-lentes-stock-info" id="stockinfonombreizq"></span> <span class="b2b-lentes-stock-amount" id="stockinfounidadesizq"></span>
			</div>
		</div>
	</div>

	[#---------------------------------------------------]
    [#----------------- OJO DERECHO 2 -------------------]
    [#---------------------------------------------------]
    <div class="b2b-lentes-tabla-info row">
    
        <div class="col-xxl-1 b2b-lentes-tabla-text b2b-lentes-tabla-text-OD">
            <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.od']}</span>
            <span class="b2b-lentes-more">-</span>
        </div>
        
        [#-- CILINDRO --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.cilindro']}</label>
            <select name="" id="cilojodrch-2">
            	<option value="" selected></option>
	            [#if cilindros?has_content]
	            	[#list cilindros as cilindro]
		                <option value="${cilindro!""}">${cilindro!""}</option>
	                [/#list]
	            [/#if]
            </select>
        </div>
        
        [#-- ESFERA --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.esfera']}</label>
            <select name="" id="esfojodrch-2" disabled>
            </select>
        </div>
        
        [#-- LENTE --]
        <div class="col-xxl-2">
        	<label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.lente']}</label>
        	<select name="" id="lenojodrch-2" disabled>
            </select>
        </div>
        
        [#-- DIAMETRO --]
        <div class="col-xxl-1">
        	<label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.diametro']}</label>
        	<select id="diaojodrch-2" disabled>
            </select>
        </div>
        
        [#-- CANTIDAD --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.unidades']}</label>
            <div class="product-amount">
                <div class="product-amount-button-wrapper">
                    <button class="product-amount-button product-amount-button-minus" type="button">
                        -
                    </button>
                    <input id="cantojodrch-2" class="product-amount-input" value="0" readonly>
                    <button class="product-amount-button product-amount-button-plus" type="button">
                        +
                    </button>
                </div>
            </div>
        </div>
        
		<div class="col-xxl-2 b2b-lentes-tabla_wrapper">
        	[#-- TALLER --]
	        <div class="b2b-lentes-tabla__taller-wrapper">
	            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.taller']}</label>
	            <div class="b2b-lentes-tabla-chek-wrapper">
	                <input class="b2b-lentes-tab-check styled-checkbox" id="tallerojodrch-2" type="checkbox" checked="false">
	                <label class="b2b-label" for="tallerojodrch-2"></label>
	            </div>
			</div>
		
			[#-- REF. TALLER --]
            <div class="b2b-lentes-tabla__reftaller-wrapper">
                <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.reftaller']}</label>
                <input type="text" id="reftallerojodrch-2" style="background-color: #d1d1d1;" disabled>
            </div>
        </div>
        
		
		[#-- REF. CLIENTE --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.refcliente']}</label>
            <input type="text" id="refclienteojodrch-2">
        </div>
		
		[#-- PVO --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.components.detalle-producto-component.pvo']}</label>
            <input type="text" id="pvoojodrch-2" readonly autocomplete="off">
        </div>
		
		[#-- PVP --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.components.detalle-producto-component.pvp']}</label>
            <input type="text" id="pvpojodrch-2" readonly autocomplete="off">
        </div>
        
    </div>
	
    [#-----------------------------------------------------]
    [#----------------- OJO IZQUIERDO 2 -------------------]
    [#-----------------------------------------------------]
    <div class="b2b-lentes-tabla-info row">
    
        <div class="col-xxl-1 b2b-lentes-tabla-text b2b-lentes-tabla-text-OI">
            <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.oi']}</span>
            <span class="b2b-lentes-more">-</span>
        </div>
        
        [#-- CILINDRO --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.cilindro']}</label>
            <select name="" id="cilojoizq-2">
            	<option value="" selected></option>
            	[#if cilindros?has_content]
	            	[#list cilindros as cilindro]
		                <option value="${cilindro!""}">${cilindro!""}</option>
	                [/#list]
                [/#if]
            </select>
        </div>
        
        [#-- ESFERA --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.esfera']}</label>
            <select name="" id="esfojoizq-2" disabled>
            </select>
        </div>
        
        [#-- LENTE --]
        <div class="col-xxl-2">
        	<label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.lente']}</label>
        	<select name="" id="lenojoizq-2" disabled>
            </select>
        </div>
        
        [#-- DIAMETRO --]  
        <div class="col-xxl-1">
        	<label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.diametro']}</label>
        	<select name="" id="diaojoizq-2" disabled>
            </select>
        </div>
         
        [#-- CANTIDAD --]   
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.unidades']}</label>
            <div class="product-amount">
                <div class="product-amount-button-wrapper">
                    <button class="product-amount-button product-amount-button-minus" type="button">
                        -
                    </button>
                    <input id="cantojoizq-2" class="product-amount-input" value="0" readonly>
                    <button class="product-amount-button product-amount-button-plus" type="button">
                        +
                    </button>
                </div>
            </div>
        </div>
        
        <div class="col-xxl-2 b2b-lentes-tabla_wrapper">
	        [#-- TALLER --]
	        <div class="b2b-lentes-tabla__taller-wrapper">
	            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.taller']}</label>
	            <div class="b2b-lentes-tabla-chek-wrapper">
	                <input class="b2b-lentes-tab-check styled-checkbox" id="tallerojoizq-2" type="checkbox" checked="false">
	                <label class="b2b-label" for="tallerojoizq-2"></label>
	            </div>
	        </div>
	
			[#-- REF. TALLER --]
	        <div class="b2b-lentes-tabla__reftaller-wrapper">
	            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.reftaller']}</label>
	            <input id="reftallerojoizq-2" type="text" style="background-color: #d1d1d1;" disabled>
	        </div>
		</div>
        
        [#-- REF. CLIENTE --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.refcliente']}</label>
            <input id="refclienteojoizq-2" type="text">
        </div>
		
		[#-- PVO --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.components.detalle-producto-component.pvo']}</label>
            <input type="text" id="pvoojoizq-2" readonly autocomplete="off">
        </div>
		
		[#-- PVP --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.components.detalle-producto-component.pvp']}</label>
            <input type="text" id="pvpojoizq-2" readonly autocomplete="off">
        </div>
        
       <input id="descripciondrch-2" type="hidden" autocomplete="off">
       <input id="namedrch-2" type="hidden" autocomplete="off">
       <input id="nivel1drch-2" type="hidden" autocomplete="off">
       <input id="nivel2drch-2" type="hidden" autocomplete="off">
       <input id="pvodrch-2" type="hidden" autocomplete="off">
       <input id="pvofrontdrch-2" type="hidden" autocomplete="off">
       <input id="pvpfrontdrch-2" type="hidden" autocomplete="off">
       <input id="skudrch-2" type="hidden" autocomplete="off">
       <input id="lmattypedrch-2" type="hidden" autocomplete="off">
        
       <input id="descripcionizq-2" type="hidden" autocomplete="off">
       <input id="nameizq-2" type="hidden" autocomplete="off">
       <input id="nivel1izq-2" type="hidden" autocomplete="off">
       <input id="nivel2izq-2" type="hidden" autocomplete="off">
       <input id="pvoizq-2" type="hidden" autocomplete="off">
       <input id="pvofrontizq-2" type="hidden" autocomplete="off">
       <input id="pvpfrontizq-2" type="hidden" autocomplete="off">
       <input id="skuizq-2" type="hidden" autocomplete="off">
       <input id="lmattypeizq-2" type="hidden" autocomplete="off">
        
    </div>

	<div class="b2b-lentes-button-wrapper d-flex justify-content-end mt-4 mb-5">

		<div class="b2b-lentes-sotck-wrapper">
			<div>
				<span class="b2b-lentes-stock-info" id="stockinfonombredrch-2"></span> <span class="b2b-lentes-stock-amount" id="stockinfounidadesdrch-2"></span>
			</div>
			<div>
				<span class="b2b-lentes-stock-info" id="stockinfonombreizq-2"></span> <span class="b2b-lentes-stock-amount" id="stockinfounidadesizq-2"></span>
			</div>
		</div>
	</div>

	[#---------------------------------------------------]
    [#----------------- OJO DERECHO 3 -------------------]
    [#---------------------------------------------------]
    <div class="b2b-lentes-tabla-info row">
    
        <div class="col-xxl-1 b2b-lentes-tabla-text b2b-lentes-tabla-text-OD">
            <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.od']}</span>
            <span class="b2b-lentes-more">-</span>
        </div>
        
        [#-- CILINDRO --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.cilindro']}</label>
            <select name="" id="cilojodrch-3">
            	<option value="" selected></option>
	            [#if cilindros?has_content]
	            	[#list cilindros as cilindro]
		                <option value="${cilindro!""}">${cilindro!""}</option>
	                [/#list]
	            [/#if]
            </select>
        </div>
        
        [#-- ESFERA --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.esfera']}</label>
            <select name="" id="esfojodrch-3" disabled>
            </select>
        </div>
        
        [#-- LENTE --]
        <div class="col-xxl-2">
        	<label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.lente']}</label>
        	<select name="" id="lenojodrch-3" disabled>
            </select>
        </div>
        
        [#-- DIAMETRO --]
        <div class="col-xxl-1">
        	<label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.diametro']}</label>
        	<select id="diaojodrch-3" disabled>
            </select>
        </div>
        
        [#-- CANTIDAD --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.unidades']}</label>
            <div class="product-amount">
                <div class="product-amount-button-wrapper">
                    <button class="product-amount-button product-amount-button-minus" type="button">
                        -
                    </button>
                    <input id="cantojodrch-3" class="product-amount-input" value="0" readonly>
                    <button class="product-amount-button product-amount-button-plus" type="button">
                        +
                    </button>
                </div>
            </div>
        </div>
        
		<div class="col-xxl-2 b2b-lentes-tabla_wrapper">
        	[#-- TALLER --]
	        <div class="b2b-lentes-tabla__taller-wrapper">
	            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.taller']}</label>
	            <div class="b2b-lentes-tabla-chek-wrapper">
	                <input class="b2b-lentes-tab-check styled-checkbox" id="tallerojodrch-3" type="checkbox" checked="false">
	                <label class="b2b-label" for="tallerojodrch-3"></label>
	            </div>
			</div>
		
			[#-- REF. TALLER --]
            <div class="b2b-lentes-tabla__reftaller-wrapper">
                <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.reftaller']}</label>
                <input type="text" id="reftallerojodrch-3" style="background-color: #d1d1d1;" disabled>
            </div>
        </div>
        
		
		[#-- REF. CLIENTE --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.refcliente']}</label>
            <input type="text" id="refclienteojodrch-3">
        </div>
		
		[#-- PVO --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.components.detalle-producto-component.pvo']}</label>
            <input type="text" id="pvoojodrch-3" readonly autocomplete="off">
        </div>
		
		[#-- PVP --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.components.detalle-producto-component.pvp']}</label>
            <input type="text" id="pvpojodrch-3" readonly autocomplete="off">
        </div>
        
    </div>
	
    [#-----------------------------------------------------]
    [#----------------- OJO IZQUIERDO 3 -------------------]
    [#-----------------------------------------------------]
    <div class="b2b-lentes-tabla-info row">
    
        <div class="col-xxl-1 b2b-lentes-tabla-text b2b-lentes-tabla-text-OI">
            <span>${i18n['cione-module.templates.myshop.configurador-lentes-component.oi']}</span>
            <span class="b2b-lentes-more">-</span>
        </div>
        
        [#-- CILINDRO --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.cilindro']}</label>
            <select name="" id="cilojoizq-3">
            	<option value="" selected></option>
            	[#if cilindros?has_content]
	            	[#list cilindros as cilindro]
		                <option value="${cilindro!""}">${cilindro!""}</option>
	                [/#list]
                [/#if]
            </select>
        </div>
        
        [#-- ESFERA --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.esfera']}</label>
            <select name="" id="esfojoizq-3" disabled>
            </select>
        </div>
        
        [#-- LENTE --]
        <div class="col-xxl-2">
        	<label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.lente']}</label>
        	<select name="" id="lenojoizq-3" disabled>
            </select>
        </div>
        
        [#-- DIAMETRO --]  
        <div class="col-xxl-1">
        	<label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.diametro']}</label>
        	<select name="" id="diaojoizq-3" disabled>
            </select>
        </div>
         
        [#-- CANTIDAD --]   
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.unidades']}</label>
            <div class="product-amount">
                <div class="product-amount-button-wrapper">
                    <button class="product-amount-button product-amount-button-minus" type="button">
                        -
                    </button>
                    <input id="cantojoizq-3" class="product-amount-input" value="0" readonly>
                    <button class="product-amount-button product-amount-button-plus" type="button">
                        +
                    </button>
                </div>
            </div>
        </div>
        
        <div class="col-xxl-2 b2b-lentes-tabla_wrapper">
	        [#-- TALLER --]
	        <div class="b2b-lentes-tabla__taller-wrapper">
	            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.taller']}</label>
	            <div class="b2b-lentes-tabla-chek-wrapper">
	                <input class="b2b-lentes-tab-check styled-checkbox" id="tallerojoizq-3" type="checkbox" checked="false">
	                <label class="b2b-label" for="tallerojoizq-3"></label>
	            </div>
	        </div>
	
			[#-- REF. TALLER --]
	        <div class="b2b-lentes-tabla__reftaller-wrapper">
	            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.reftaller']}</label>
	            <input id="reftallerojoizq-3" type="text" style="background-color: #d1d1d1;" disabled>
	        </div>
		</div>
        
        [#-- REF. CLIENTE --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.myshop.configurador-lentes-component.refcliente']}</label>
            <input id="refclienteojoizq-3" type="text">
        </div>
		
		[#-- PVO --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.components.detalle-producto-component.pvo']}</label>
            <input type="text" id="pvoojoizq-3" readonly autocomplete="off">
        </div>
		
		[#-- PVP --]
        <div class="col-xxl-1">
            <label class="b2b-lentes-tabla-label">${i18n['cione-module.templates.components.detalle-producto-component.pvp']}</label>
            <input type="text" id="pvpojoizq-3" readonly autocomplete="off">
        </div>
        
       <input id="descripciondrch-3" type="hidden" autocomplete="off">
       <input id="namedrch-3" type="hidden" autocomplete="off">
       <input id="nivel1drch-3" type="hidden" autocomplete="off">
       <input id="nivel2drch-3" type="hidden" autocomplete="off">
       <input id="pvodrch-3" type="hidden" autocomplete="off">
       <input id="pvofrontdrch-3" type="hidden" autocomplete="off">
       <input id="pvpfrontdrch-3" type="hidden" autocomplete="off">
       <input id="skudrch-3" type="hidden" autocomplete="off">
       <input id="lmattypedrch-3" type="hidden" autocomplete="off">
        
       <input id="descripcionizq-3" type="hidden" autocomplete="off">
       <input id="nameizq-3" type="hidden" autocomplete="off">
       <input id="nivel1izq-3" type="hidden" autocomplete="off">
       <input id="nivel2izq-3" type="hidden" autocomplete="off">
       <input id="pvoizq-3" type="hidden" autocomplete="off">
       <input id="pvofrontizq-3" type="hidden" autocomplete="off">
       <input id="pvpfrontizq-3" type="hidden" autocomplete="off">
       <input id="skuizq-3" type="hidden" autocomplete="off">
       <input id="lmattypeizq-3" type="hidden" autocomplete="off">
        
    </div>
    
    [#-- si no hay cilindros que cargar mostramos un mensaje de error --]
    [#if !cilindros?has_content]
    	<div class="msg-error-configurador" style="color: red;">${i18n['cione-module.templates.myshop.configurador-lentes-component.nocilindros']}</div>
    [/#if]
    
    <div id="msgerroraddtocart" class="msg-error-configurador" style="color: red;display:none">${i18n['cione-module.templates.myshop.configurador-lentes-component.noeyes']}</div>
    
</div>

<div class="b2b-lentes-button-wrapper d-flex justify-content-end mt-4 mb-5">

	<div class="b2b-lentes-sotck-wrapper">
	    <div>
			<span class="b2b-lentes-stock-info" id="stockinfonombredrch-3"></span> <span class="b2b-lentes-stock-amount" id="stockinfounidadesdrch-3"></span>
	    </div>
		<div>
			<span class="b2b-lentes-stock-info" id="stockinfonombreizq-3"></span> <span class="b2b-lentes-stock-amount" id="stockinfounidadesizq-3"></span>
		</div>
	</div>
</div>

[#-- boton de anadir al carrito --]
<div class="b2b-lentes-button-wrapper d-flex justify-content-end mt-5 mb-5">
                
	<div class="b2b-button-wrapper">
		<button id="addtocartbtn" type="button" disabled="" class="b2b-button b2b-button-filter" onclick="addtoCart(); return false" style="cursor: not-allowed;">
        	${i18n['cione-module.templates.myshop.configurador-lentes-component.anadiralcarrito']}
		</button>
	</div>
</div>

[#-- BEGIN: modal para el stock --]
[#assign closeimg = ctx.contextPath + "/.resources/cione-theme/webresources/img/myshop/icons/close-thin.svg"]
<div class="modal-purchase" id="modallentesdestock" style="display: none;">

    <div class="modal-purchase-box">
    
        <div class="modal-purchase-header">
            <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.nostock']}</p>
            <div class="modal-purchase-close">
                <img class="modal-purchase-close-img" src="${closeimg!""}" alt="cerrar" onclick="closeModal()">
            </div>
        </div>
        
        <div class="modal-purchase-info">
            <div>
                <p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.noproductlensstock']}</p>
            </div>
            <div id="modal-content">
                
            </div>
        </div>
        
        <div class="modal-purchase-footer">
            <button class="modal-purchase-button modal-purchase-button--transparent" type="button" onclick="closeModal()">
                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.close']}
            </button>
            <button class="modal-purchase-button" type="button" onclick="addToTheCartFromModal(modaldata)">
                ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.add']}
            </button>
        </div>

    </div>

</div> 
[#-- END: modal para el stock --] 

<script>

	ModalItem = ({ ojo, suffix }) => "<p>" + ojo + ": </p>" +
		"<p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.delivery']} ${i18n['cione-module.templates.components.plazo-proveedor']}</p>" +
		"<p>${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.for']} <span id='unitsselected" + suffix + "'></span> ${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.units']}<span id='unitscart'" + suffix + "></span></p>" +
		"<p class='stock_modal' id='stocklensstockunits" + suffix + "'></p>" +
		"<p class='stock_modal' id='stocklensstockunitscanar" + suffix + "'></p>" +
		"<br>";
	
	function setInputFilter(textbox, inputFilter) {
	  ["input", "keydown", "keyup", "mousedown", "mouseup", "select", "contextmenu", "drop"].forEach(function(event) {
	    textbox.addEventListener(event, function() {
	      if (inputFilter(this.value)) {
	        this.oldValue = this.value;
	        this.oldSelectionStart = this.selectionStart;
	        this.oldSelectionEnd = this.selectionEnd;
	      } else if (this.hasOwnProperty("oldValue")) {
	        this.value = this.oldValue;
	        this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
	      } else {
	        this.value = "";
	      }
	    });
	  });
	}
	
	function addtoCart(){
	
		removeValidations();
		
		if (validationForm()){
		
			$("#msgerroraddtocart").css("display", "none");
			
			items = {};
			error = false;

			error = getDataRow("drch", "D", items, error);
			error = getDataRow("izq", "I", items, error);
			error = getDataRow("drch-2", "D", items, error);
			error = getDataRow("izq-2", "I", items, error);
			error = getDataRow("drch-3", "D", items, error);
			error = getDataRow("izq-3", "I", items, error);
			if (!error) {
				needsConfirmation = false;
				Object.keys(items).forEach(function(key) {
					needsConfirmation = checkStock(items[key], key, needsConfirmation);
				});

				if (needsConfirmation) {
					modaldata = items;

				} else {
					Object.keys(items).forEach(function(key) {
						addToCartSingle(items[key], key);
					});

					$("#loading").hide();
					$(".product-button").removeAttr("disabled");
					resetAllFields();
					closeModal();   
				}
			}

		}else{
			addValidations();
			$("#msgerroraddtocart").css("display", "block");
		}
		
		return false;
	}

	function getDataRow(suffix, ojo, items, error) {

		if ($("#cilojo" + suffix).val() != null &&
			$("#esfojo" + suffix).val() != null &&
			$("#lenojo" + suffix).val() != null &&
			$("#diaojo" + suffix).val() && parseInt($("#cantojo" + suffix).val()) > 0){

			cilindro = $("#cilojo" + suffix);
			esfera = $("#esfojo" + suffix);
			lente = $("#lenojo" + suffix);
			diametro = $("#diaojo" + suffix);
			cantidad = $("#cantojo" + suffix);
			tallerCheck = $('#tallerojo' + suffix);
			tallerRef = $("#reftallerojo" + suffix);
			clienteRef = $("#refclienteojo" + suffix);
			descripcion = $("#descripcion" + suffix);
			nivel1 = $("#nivel1" + suffix);
			nivel2 = $("#nivel2" + suffix);
			nameItem = $("#name" + suffix);
			pvo = $("#pvo" + suffix);
			sku = $("#sku" + suffix);
			lmattype = $("#lmattype" + suffix);

			item = getData(cilindro, esfera, lente, diametro, cantidad, tallerCheck, tallerRef, clienteRef, descripcion, nivel1, nivel2, nameItem, pvo, sku, lmattype, ojo);
			items[suffix] = item;
			error = validateRowFields(item, error);
		}
		return error;
	}

	function getData(cilindro, esfera, lente, diametro, cantidad, tallerCheck, tallerRef, clienteRef, descripcion, nivel1, nivel2, name, pvo, sku, lmattype, ojo) {
		var sPageURL = window.location.search.substring(1);
		var url = "${ctx.contextPath}/.rest/private/lens/linedatainfo?cylinder=" + cilindro.val().replace("+", "") + "&sphere=" + esfera.val().replace("+", "") + "&lens=" + lente.val() + "&diameter=" + diametro.val();
		var indexed_array = {};
		if(sPageURL != null){
			url = url + "&" + sPageURL;
		}
	
		$("#loading").show();
		$(".product-button").attr("disabled"); 
		
		$.ajax({
            url : url,
            type : "GET",
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
			async: false, 
            success : function(response) {
            	
            	descripcion.val(response.descripcion);
            	nivel1.val(response.nivel1);
            	nivel2.val(response.nivel2);
            	name.val(response.name);
            	pvo.val(response.pvo);
            	sku.val(response.sku);
            	lmattype.val(response.lmattype);
            	
            },
            error : function(response) {
                console.log("Error al recuperar los datos de compra del ojo"); 
                $("#loading").hide();
            },
            complete : function(response) {
            
            	[#-- una vez obtenemos los datos que debemos enviar
            	al carrito entonces podemos proceder a realizar la llamada
            	al addcartcustomitem --]
            	 
            	indexed_array["sku"] = sku.val();
            	indexed_array["cantidad"] = cantidad.val();
            	indexed_array["ojo"] = ojo;
            	indexed_array["cyl"] = cilindro.val();
            	indexed_array["sph"] = esfera.val();
            	indexed_array["crib"] = diametro.val();
            	indexed_array["lente"] = lente.find(":selected").text();
            	indexed_array["nivel1"] = nivel1.val();
            	indexed_array["nivel2"] = nivel2.val();
            	indexed_array["reftaller"] = tallerRef.val();
            	indexed_array["refcliente"] = clienteRef.val();
            	indexed_array["description"] = descripcion.val();
            	indexed_array["pvo"] = pvo.val();
            	var refcheck = tallerCheck;
            	if (refcheck.is(":checked")) {
            		indexed_array["aTaller"] = true;
            	} else {
            		indexed_array["aTaller"] = false;
            	}
            	indexed_array["lmattype"] = lmattype.val();
            	let nameitem = name.val();
            	var slugitem = nameitem.replace(/ /g, "-");
            	slugitem = slugitem.concat(ojo == "D" ? "r" : "l");
            	if(tallerRef.val() != ""){slugitem = slugitem.concat("-" + tallerRef.val());}
            	if(clienteRef.val() != ""){slugitem = slugitem.concat("-" + clienteRef.val());}
            	slugitem = slugitem.concat("-" + sku.val());
            	indexed_array["name"] = nameitem;
            	indexed_array["slug"] = slugitem; 
            }
        });
		return indexed_array;
	}

	function validateRowFields(indexed_array, error) {
		if (!validateFields(indexed_array)){
			$("#loading").hide();
			resetAllFields();
			alert("No se ha podido añadir este producto al carrito, por favor contacte con el SAS");
			error = true;  
		}
		return error;
	}

	function checkStock(dataEye, suffix, needsConfirmation) {
		[#-- primero revisamos el stock y si todo esta OK
		     lanzamos anadir al carrito --]
		var urlstock = "${ctx.contextPath}/.rest/private/stock?sku=" + encodeURIComponent(dataEye['sku']);
		var unitscarturl = "${ctx.contextPath}/.rest/private/stock/unitscart?sku=" + encodeURIComponent(dataEye['sku']);
		var unitscart = 0;
		
		$.ajax({
			url : urlstock,
			type : "GET",
			cache : false,
			async: false,  
			success : function(response) {
				
				$.ajax({
					url : unitscarturl,
					type : "GET",
					cache : false, 
					async: false, 
					success : function(response) {
						unitscart = response.units;
					},
					error : function(response) {
						console.log(response); 
					}
				});
				
				if (response.stock <= 0 && parseInt($("#cantojo" + suffix).val()) + unitscart > response.stock){
					if (dataEye.ojo == "D") {
						$("#modal-content").append([
						  { ojo: "${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.modallefteye']}", suffix: suffix},
						].map(ModalItem));

					} else {
						$("#modal-content").append([
						  { ojo: "${i18n['cione-module.templates.myshop.listado-productos-home-component.modal.modalrighteye']}", suffix: suffix},
						].map(ModalItem));
					}

					$("#unitsselected" + suffix).text($("#cantojo" + suffix).val());
					
					if(unitscart > 0){
						$('span#unitscart' + suffix).text(" + " + unitscart + "${i18n['cione-module.templates.myshop.configurador-lentes-component.incart']}");
					}
					
					if (response.stock > 1 || response.stock == 0){
						if (response.almacen == 'stockCANAR'){
							$("#stocklensstockunits" + suffix).text("STOCK CANAR: " + response.stock + " unidades");
							$("#stocklensstockunitscanar" + suffix).text("STOCK CTRAL: " + response.stockCTRAL + " unidades");
						}else {
							$("#stocklensstockunits" + suffix).text("STOCK: " + response.stock + " unidades");
						}
					}else{
						$("#stocklensstockunits" + suffix).text("STOCK: 1 unidad");
					}
					$("#modallentesdestock").css("display","flex");
					//modaldata = dataEye;
					needsConfirmation = true;
				}
				
			},
			error : function(response) {
				console.log("Error al solicitar el stock");           
			}
		});

		return needsConfirmation;	
	}

	function addToCartSingle(dataEye, suffix) {
		$.ajax({
			url : "${ctx.contextPath}/.rest/private/carrito/v1/carts-addCustomItem",
			type : "POST",
			data : JSON.stringify(dataEye),
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			async: false, 
			success : function(response) {
			
				$("#fly-card").css("display","block");
				
				setTimeout(function(){ 
				$("#fly-card").css("display","none");
				}, 5000);
				
				var KK = response;
				//alert("producto añadido");
				
				//console.log(KK);
				
				//actualiza popup carrito
				refrescarPopupCarrito(response);
				
			},
			error : function(response) {
				alert("error"); 
				$("#loading").hide();           
			},
			complete : function(response) {
				           
			}
		});
	}
	
	function addToTheCartFromModal(items){
		
		Object.keys(items).forEach(function(key) {
			addToCartSingle(items[key], key);
		});

		modaldata = null;
		$("#loading").hide();
		$(".product-button").removeAttr("disabled");
		resetAllFields();  
		closeModal();             
	
	}
	
	function closeModal() {
		$("#modallentesdestock").css("display","none");
		$("#modal-content").html("");
		$("#loading").hide();
		modaldata = null;
	}

	function validateFields(arrayfields){
	
		var res = true;
		
		if (hasKeyAndNotEmpty(arrayfields,"sku")){
			res = false;
		}
		
		if (hasKeyAndNotEmpty(arrayfields,"cantidad")){
			res = false;
		}
		
		if (hasKeyAndNotEmpty(arrayfields,"ojo")){
			res = false;
		}
		
		if (hasKeyAndNotEmpty(arrayfields,"cyl")){
			res = false;
		}
		
		if (hasKeyAndNotEmpty(arrayfields,"sph")){
			res = false;
		}
		
		if (hasKeyAndNotEmpty(arrayfields,"crib")){
			res = false;
		}
		
		if (hasKeyAndNotEmpty(arrayfields,"lente")){
			res = false;
		}
		
		if (hasKeyAndNotEmpty(arrayfields,"nivel1")){
			res = false;
		}
		
		if (hasKeyAndNotEmpty(arrayfields,"nivel2")){
			res = false;
		}
		
		if (hasKeyAndNotEmpty(arrayfields,"name")){
			res = false;
		}
		
		if (hasKeyAndNotEmpty(arrayfields,"slug")){
			res = false;
		}
		
		if (hasKeyAndNotEmpty(arrayfields,"description")){
			res = false;
		}
		
		return res;
	}
	
	function hasKeyAndNotEmpty(array,key){
		return array[key] !== undefined && !array[key];
	}
	
	function removeValidations(){
	
		$('#cilojodrch').removeClass("validation-error");
		$("#esfojodrch").removeClass("validation-error");
		$("#lenojodrch").removeClass("validation-error");
		$("#diaojodrch").removeClass("validation-error");
		$("#cantojodrch").removeClass("validation-error");
		$("#reftallerojodrch").removeClass("validation-error");

		$('#cilojodrch-2').removeClass("validation-error");
		$("#esfojodrch-2").removeClass("validation-error");
		$("#lenojodrch-2").removeClass("validation-error");
		$("#diaojodrch-2").removeClass("validation-error");
		$("#cantojodrch-2").removeClass("validation-error");
		$("#reftallerojodrch-2").removeClass("validation-error");

		$('#cilojodrch-3').removeClass("validation-error");
		$("#esfojodrch-3").removeClass("validation-error");
		$("#lenojodrch-3").removeClass("validation-error");
		$("#diaojodrch-3").removeClass("validation-error");
		$("#cantojodrch-3").removeClass("validation-error");
		$("#reftallerojodrch-3").removeClass("validation-error");
		
		$('#cilojoizq').removeClass("validation-error");
		$("#esfojoizq").removeClass("validation-error");
		$("#lenojoizq").removeClass("validation-error");
		$("#diaojoizq").removeClass("validation-error");
		$("#cantojoizq").removeClass("validation-error");
		$("#reftallerojoizq").removeClass("validation-error");

		$('#cilojoizq-2').removeClass("validation-error");
		$("#esfojoizq-2").removeClass("validation-error");
		$("#lenojoizq-2").removeClass("validation-error");
		$("#diaojoizq-2").removeClass("validation-error");
		$("#cantojoizq-2").removeClass("validation-error");
		$("#reftallerojoizq-2").removeClass("validation-error");

		$('#cilojoizq-3').removeClass("validation-error");
		$("#esfojoizq-3").removeClass("validation-error");
		$("#lenojoizq-3").removeClass("validation-error");
		$("#diaojoizq-3").removeClass("validation-error");
		$("#cantojoizq-3").removeClass("validation-error");
		$("#reftallerojoizq-3").removeClass("validation-error");
	}
	
	function addValidations(){
		var ojoizq = false;
		var ojoizq2 = false;
		var ojoizq3 = false;
		var ojodrch = false;
		var ojodrch2 = false;
		var ojodrch3 = false;

		var tipoerror = 0;
		if ($("#esfojodrch").val() != null){
			ojodrch = true;
		}
		if ($("#esfojodrch-2").val() != null){
			ojodrch2 = true;
		}
		if ($("#esfojodrch-3").val() != null){
			ojodrch3 = true;
		}
		
		if($("#esfojoizq").val() != null){
			ojoizq = true;
		}
		if($("#esfojoizq-2").val() != null){
			ojoizq2 = true;
		}
		if($("#esfojoizq-3").val() != null){
			ojoizq3 = true;
		}

		if(ojodrch) {
			cilindro = $("#cilojodrch");
			esfera = $("#esfojodrch");
			lente = $("#lenojodrch");
			diametro = $("#diaojodrch");
			cantidad = $("#cantojodrch");
			tallerCheck = $("#tallerojodrch");
			tallerRef = $("#reftallerojodrch");
			tipoerror = validateRow(tipoerror, cilindro, esfera, lente, diametro, cantidad, tallerCheck, tallerRef);
	    }
		if(ojodrch2) {
			cilindro = $("#cilojodrch-2");
			esfera = $("#esfojodrch-2");
			lente = $("#lenojodrch-2");
			diametro = $("#diaojodrch-2");
			cantidad = $("#cantojodrch-2");
			tallerCheck = $("#tallerojodrch-2");
			tallerRef = $("#reftallerojodrch-2");
			tipoerror = validateRow(tipoerror, cilindro, esfera, lente, diametro, cantidad, tallerCheck, tallerRef);
	    }
		if(ojodrch3) {
			cilindro = $("#cilojodrch-3");
			esfera = $("#esfojodrch-3");
			lente = $("#lenojodrch-3");
			diametro = $("#diaojodrch-3");
			cantidad = $("#cantojodrch-3");
			tallerCheck = $("#tallerojodrch-3");
			tallerRef = $("#reftallerojodrch-3");
			tipoerror = validateRow(tipoerror, cilindro, esfera, lente, diametro, cantidad, tallerCheck, tallerRef);
	    }

	    if(ojoizq) {
			cilindro = $("#cilojoizq");
			esfera = $("#esfojoizq");
			lente = $("#lenojoizq");
			diametro = $("#diaojoizq");
			cantidad = $("#cantojoizq");
			tallerCheck = $("#tallerojoizq");
			tallerRef = $("#reftallerojoizq");
			tipoerror = validateRow(tipoerror, cilindro, esfera, lente, diametro, cantidad, tallerCheck, tallerRef);
		}
		if(ojoizq2) {
			cilindro = $("#cilojoizq-2");
			esfera = $("#esfojoizq-2");
			lente = $("#lenojoizq-2");
			diametro = $("#diaojoizq-2");
			cantidad = $("#cantojoizq-2");
			tallerCheck = $("#tallerojoizq-2");
			tallerRef = $("#reftallerojoizq-2");
			tipoerror = validateRow(tipoerror, cilindro, esfera, lente, diametro, cantidad, tallerCheck, tallerRef);
		}
		if(ojoizq3) {
			cilindro = $("#cilojoizq-3");
			esfera = $("#esfojoizq-3");
			lente = $("#lenojoizq-3");
			diametro = $("#diaojoizq-3");
			cantidad = $("#cantojoizq-3");
			tallerCheck = $("#tallerojoizq-3");
			tallerRef = $("#reftallerojoizq-3");
			tipoerror = validateRow(tipoerror, cilindro, esfera, lente, diametro, cantidad, tallerCheck, tallerRef);
		}

		if (tipoerror === 1) {
			$("#msgerroraddtocart").text("${i18n['cione-module.templates.myshop.configurador-lentes-component.noeyes']}");
		} else if (tipoerror === 2) {
			$("#msgerroraddtocart").text("${i18n['cione-module.templates.myshop.configurador-lentes-component.reftaller-error']}");
		}
		
	}

	function validateRow(tipoerror, cilindro, esfera, lente, diametro, cantidad, tallerCheck, tallerRef) {
		if ((cilindro.val() == null) || (cilindro.val() == '')){
				cilindro.addClass("validation-error");
				tipoerror = 1;
		}
		if ((esfera.val() == null) || (esfera.val() == '')){
			esfera.addClass("validation-error");
			tipoerror = 1;
		}
		if ((lente.val() == null) || (lente.val() == '')){
			lente.addClass("validation-error");
			tipoerror = 1;
		}
		if ((diametro.val() == null) || (diametro.val() == '')){
			diametro.addClass("validation-error");
			tipoerror = 1;
		}
		if (parseInt(cantidad.val()) == 0){
			cantidad.addClass("validation-error");
			tipoerror = 1;
		}
		if (tallerCheck.is(":checked")) {
			if (tallerRef.val() == ''){
				tallerRef.addClass("validation-error");
				tipoerror = 2;
				
			}
		} 
		return tipoerror;
	}
	
	function validationForm(){
		
		var res = false;
		if ($("#tallerojodrch").is(":checked")) {
    		if ($("#reftallerojodrch").val() == ''){
    			return false;
    		}
    	}
		if ($("#cilojodrch").val() != null &&
		$("#cilojodrch").val() != null &&
		$("#esfojodrch").val() != null &&
		$("#lenojodrch").val() != null &&
		$("#diaojodrch").val() && parseInt($("#cantojodrch").val()) > 0){
			res = true;
		}

		if ($("#tallerojodrch-2").is(":checked")) {
    		if ($("#reftallerojodrch-2").val() == ''){
    			return false;
    		}
    	}
		if ($("#cilojodrch-2").val() != null &&
		$("#cilojodrch-2").val() != null &&
		$("#esfojodrch-2").val() != null &&
		$("#lenojodrch-2").val() != null &&
		$("#diaojodrch-2").val() && parseInt($("#cantojodrch-2").val()) > 0){
			res = true;
		}

		if ($("#tallerojodrch-3").is(":checked")) {
    		if ($("#reftallerojodrch-3").val() == ''){
    			return false;
    		}
    	}
		if ($("#cilojodrch-3").val() != null &&
		$("#cilojodrch-3").val() != null &&
		$("#esfojodrch-3").val() != null &&
		$("#lenojodrch-3").val() != null &&
		$("#diaojodrch-3").val() && parseInt($("#cantojodrch-3").val()) > 0){
			res = true;
		}
		
    	
    	if ($("#tallerojoizq").is(":checked")) {
    		if ($("#reftallerojoizq").val() == ''){
    			return false;
    		}
    	}	
		if($("#cilojoizq").val() != null &&
		$("#esfojoizq").val() != null &&
		$("#lenojoizq").val() != null &&
		$("#pvoojoizq").val() != '' &&
		$("#diaojoizq").val() && parseInt($("#cantojoizq").val()) > 0){
			res = true;
		}

		if ($("#tallerojoizq-2").is(":checked")) {
    		if ($("#reftallerojoizq-2").val() == ''){
    			return false;
    		}
    	}	
		if($("#cilojoizq-2").val() != null &&
		$("#esfojoizq-2").val() != null &&
		$("#lenojoizq-2").val() != null &&
		$("#pvoojoizq-2").val() != '' &&
		$("#diaojoizq-2").val() && parseInt($("#cantojoizq-2").val()) > 0){
			res = true;
		}

		if ($("#tallerojoizq-3").is(":checked")) {
    		if ($("#reftallerojoizq-3").val() == ''){
    			return false;
    		}
    	}	
		if($("#cilojoizq-3").val() != null &&
		$("#esfojoizq-3").val() != null &&
		$("#lenojoizq-3").val() != null &&
		$("#pvoojoizq-3").val() != '' &&
		$("#diaojoizq-3").val() && parseInt($("#cantojoizq-3").val()) > 0){
			res = true;
		}
		
		return res;
	}
	
	function resetAllFields(){
	
		$('#cilojodrch').val('');
		cilojodrchtemp = 'undefined';
		$('#esfojodrch').empty();
		esfojodrchtemp = 'undefined';
		$('#lenojodrch').empty();
		lenojodrchtemp = 'undefined';
		$('#diaojodrch').empty();
		diaojodrchtemp = 'undefined';
		$('#cantojodrch').val('0');
		$('#tallerojodrch').prop('checked',false);
		$('#reftallerojodrch').val('');
		$('#refclienteojodrch').val('');
		$('#reftallerojodrch').prop('disabled', true);
		$('#reftallerojodrch').css('background-color', '#d1d1d1');
		$('#pvoojodrch').val('');
		$('#pvpojodrch').val('');
		$("span#stockinfonombredrch").text('');
    	$("span#stockinfounidadesdrch").text('');

		$('#cilojodrch-2').val('');
		cilojodrchtemp2 = 'undefined';
		$('#esfojodrch-2').empty();
		esfojodrchtemp2 = 'undefined';
		$('#lenojodrch-2').empty();
		lenojodrchtemp2 = 'undefined';
		$('#diaojodrch-2').empty();
		diaojodrchtemp2 = 'undefined';
		$('#cantojodrch-2').val('0');
		$('#tallerojodrch-2').prop('checked',false);
		$('#reftallerojodrch-2').val('');
		$('#refclienteojodrch-2').val('');
		$('#reftallerojodrch-2').prop('disabled', true);
		$('#reftallerojodrch-2').css('background-color', '#d1d1d1');
		$('#pvoojodrch-2').val('');
		$('#pvpojodrch-2').val('');
		$("span#stockinfonombredrch-2").text('');
    	$("span#stockinfounidadesdrch-2").text('');

		$('#cilojodrch-3').val('');
		cilojodrchtemp3 = 'undefined';
		$('#esfojodrch-3').empty();
		esfojodrchtemp3 = 'undefined';
		$('#lenojodrch-3').empty();
		lenojodrchtemp3 = 'undefined';
		$('#diaojodrch-3').empty();
		diaojodrchtemp3 = 'undefined';
		$('#cantojodrch-3').val('0');
		$('#tallerojodrch-3').prop('checked',false);
		$('#reftallerojodrch-3').val('');
		$('#refclienteojodrch-3').val('');
		$('#reftallerojodrch').prop('disabled', true);
		$('#reftallerojodrch-3').css('background-color', '#d1d1d1');
		$('#pvoojodrch-3').val('');
		$('#pvpojodrch-3').val('');
		$("span#stockinfonombredrch-3").text('');
    	$("span#stockinfounidadesdrch-3").text('');
		
		$('#cilojoizq').val('');
		cilojoizqtemp = 'undefined';
		$('#esfojoizq').empty();
        esfojoizqtemp = 'undefined';
		$('#lenojoizq').empty();
		lenojoizqtemp = 'undefined';
		$('#diaojoizq').empty();
		diaojoizqtemp = 'undefined';
		$('#cantojoizq').val('0');
		$('#tallerojoizq').prop('checked',false);
		$('#reftallerojoizq').val('');
		$('#refclienteojoizq').val('');
		$('#reftallerojoizq').prop('disabled', true);
		$('#reftallerojoizq').css('background-color', '#d1d1d1');
		$('#pvoojoizq').val('');
		$('#pvpojoizq').val('');
    	$("span#stockinfonombreizq").text('');
    	$("span#stockinfounidadesizq").text('');

		$('#cilojoizq-2').val('');
		cilojoizqtemp2 = 'undefined';
		$('#esfojoizq-2').empty();
        esfojoizqtemp2 = 'undefined';
		$('#lenojoizq-2').empty();
		lenojoizqtemp2 = 'undefined';
		$('#diaojoizq-2').empty();
		diaojoizqtemp2 = 'undefined';
		$('#cantojoizq-2').val('0');
		$('#tallerojoizq-2').prop('checked',false);
		$('#reftallerojoizq-2').val('');
		$('#refclienteojoizq-2').val('');
		$('#reftallerojoizq-2').prop('disabled', true);
		$('#reftallerojoizq-2').css('background-color', '#d1d1d1');
		$('#pvoojoizq-2').val('');
		$('#pvpojoizq-2').val('');
    	$("span#stockinfonombreizq-2").text('');
    	$("span#stockinfounidadesizq-2").text('');

		$('#cilojoizq-3').val('');
		cilojoizqtemp3 = 'undefined';
		$('#esfojoizq-3').empty();
        esfojoizqtemp3 = 'undefined';
		$('#lenojoizq-3').empty();
		lenojoizqtemp3 = 'undefined';
		$('#diaojoizq-3').empty();
		diaojoizqtemp3 = 'undefined';
		$('#cantojoizq-3').val('0');
		$('#tallerojoizq-3').prop('checked',false);
		$('#reftallerojoizq-3').val('');
		$('#refclienteojoizq-3').val('');
		$('#reftallerojoizq-3').prop('disabled', true);
		$('#reftallerojoizq-3').css('background-color', '#d1d1d1');
		$('#pvoojoizq-3').val('');
		$('#pvpojoizq-3').val('');
    	$("span#stockinfonombreizq-3").text('');
    	$("span#stockinfounidadesizq-3").text('');

    	$("#addtocartbtn").prop('disabled', true);
    	$("#addtocartbtn").css("cursor","not-allowed");
	}
	
	$(document).ready(function(){
		
		[#-- control numerico para cantidades --]
		setInputFilter(document.getElementById("cantojoizq"), function(value) {
  			return /^\d*$/.test(value); });
		setInputFilter(document.getElementById("cantojoizq-2"), function(value) {
  			return /^\d*$/.test(value); });
		setInputFilter(document.getElementById("cantojoizq-3"), function(value) {
  			return /^\d*$/.test(value); });
  		
  		setInputFilter(document.getElementById("cantojodrch"), function(value) {
  			return /^\d*$/.test(value); });
		setInputFilter(document.getElementById("cantojodrch-2"), function(value) {
		return /^\d*$/.test(value); });
		setInputFilter(document.getElementById("cantojodrch-3"), function(value) {
		return /^\d*$/.test(value); });
		
		[#-- control del check de taller y establecemos
			 valor previo de falso al cargar --]
		
		$("#cilojodrch").val("").change();
		$("#cilojodrch-2").val("").change();
		$("#cilojodrch-3").val("").change();
		$("#cilojoizq").val("").change();
		$("#cilojoizq-2").val("").change();
		$("#cilojoizq-3").val("").change();
		
    	$("#refclienteojodrch").val("");
		$("#refclienteojodrch-2").val("");
		$("#refclienteojodrch-3").val("");
    	$("#refclienteojoizq").val("");
		$("#refclienteojoizq-2").val("");
		$("#refclienteojoizq-3").val("");

    	$("#reftallerojodrch").val("");
		$("#reftallerojodrch-2").val("");
		$("#reftallerojodrch-3").val("");
    	$("#reftallerojoizq").val("");
		$("#reftallerojoizq-2").val("");
		$("#reftallerojoizq-3").val("");

    	$("#cantojodrch").val(0);
		$("#cantojodrch-2").val(0);
		$("#cantojodrch-3").val(0);
    	$("#cantojoizq").val(0);
		$("#cantojoizq-2").val(0);
		$("#cantojoizq-3").val(0);
    	
		$('#tallerojodrch').prop('checked', false);
		$('#tallerojodrch-2').prop('checked', false);
		$('#tallerojodrch-3').prop('checked', false);
		$('#tallerojoizq').prop('checked', false);
		$('#tallerojoizq-2').prop('checked', false);
		$('#tallerojoizq-3').prop('checked', false);
		
	    $('#tallerojodrch').change(function() {
			refTaller = $('#reftallerojodrch');
	        activateTaller(this, refTaller);
	    });

		$('#tallerojodrch-2').change(function() {
			refTaller = $('#reftallerojodrch-2');
	        activateTaller(this, refTaller);
	    });

		$('#tallerojodrch-3').change(function() {
			refTaller = $('#reftallerojodrch-3');
	        activateTaller(this, refTaller);
	    });
		
	    $('#tallerojoizq').change(function() {
			refTaller = $('#reftallerojoizq');
	        activateTaller(this, refTaller);
	    });

		$('#tallerojoizq-2').change(function() {
			refTaller = $('#reftallerojoizq-2');
	        activateTaller(this, refTaller);
	    });

		$('#tallerojoizq-3').change(function() {
			refTaller = $('#reftallerojoizq-3');
	        activateTaller(this, refTaller);
	    });

		function activateTaller(current, refTaller) {
			if(current.checked) {
	            refTaller.prop('disabled', false);
	            refTaller.css('background-color', '#fff');
	        }else{
	        	refTaller.prop('disabled', true);
	        	refTaller.css('background-color', '#d1d1d1');
	        }
		}
		
		
		[#-- en firefox se almacena la cantidad en los formularios asi que
			 las establecemos al cargar --]
		if ($("#cantojodrch").val() > 0 || $("#cantojoizq").val() > 0
				|| $("#cantojodrch-2").val() > 0 || $("#cantojoizq-2").val() > 0
				|| $("#cantojodrch-3").val() > 0 || $("#cantojoizq-3").val() > 0) {
	    	$("#addtocartbtn").prop('disabled', false);
	    	$("#addtocartbtn").css("cursor","pointer");
	    }else{
	    	$("#addtocartbtn").prop('disabled', true);
	    	$("#addtocartbtn").css("cursor","not-allowed");
	    }

		[#-- si no hay cilindros mostramos en rojo las cajas de cilindro --]
		[#if !cilindros?has_content]
			$('#cilojodrch').addClass("validation-error");
			$('#cilojoizq').addClass("validation-error");
    	[/#if]
    	
    	[#-- control de llamadas extra al back --]
		var cilojodrchtemp;
		var cilojodrchtemp2;
		var cilojodrchtemp3;
		var cilojoizqtemp;
		var cilojoizqtemp2;
		var cilojoizqtemp3;
		
		var esfojodrchtemp;
		var esfojodrchtemp2;
		var esfojodrchtemp3;
		var esfojoizqtemp;
		var esfojoizqtemp2;
		var esfojoizqtemp3;
		
		var lenojodrchtemp;
		var lenojodrchtemp2;
		var lenojodrchtemp3;
		var lenojoizqtemp;
		var lenojoizqtemp2;
		var lenojoizqtemp3;
		
		var diaojodrchtemp;
		var diaojodrchtemp2;
		var diaojodrchtemp3;
		var diaojoizqtemp;
		var diaojoizqtemp2;
		var diaojoizqtemp3;
		
		var modaldata;
		var modaldataRE;
		var modaldataLE;
		
		[#-- CLICK EN EL CILINDRO DEL OJO DERECHO --]
		$("#cilojodrch").change(function() {
			cilindro = $("#cilojodrch");
			esfera = $('#esfojodrch');
			lente = $('#lenojodrch');
			diametro = $("#diaojodrch");
			nombreStock = $('span#stockinfonombredrch');
			unidadesStock = $('span#stockinfounidadesdrch');
			cilindroOnChange(cilindro, esfera, lente, diametro, nombreStock, unidadesStock, cilojodrchtemp, esfojodrchtemp, lenojodrchtemp, diaojodrchtemp);
			
		});

		$("#cilojodrch-2").change(function() {
			cilindro = $("#cilojodrch-2");
			esfera = $('#esfojodrch-2');
			lente = $('#lenojodrch-2');
			diametro = $("#diaojodrch-2");
			nombreStock = $('span#stockinfonombredrch-2');
			unidadesStock = $('span#stockinfounidadesdrch-2');
			cilindroOnChange(cilindro, esfera, lente, diametro, nombreStock, unidadesStock, cilojodrchtemp2, esfojodrchtemp2, lenojodrchtemp2, diaojodrchtemp2);
			
		});

		$("#cilojodrch-3").change(function() {
			cilindro = $("#cilojodrch-3");
			esfera = $('#esfojodrch-3');
			lente = $('#lenojodrch-3');
			diametro = $("#diaojodrch-3");
			nombreStock = $('span#stockinfonombredrch-3');
			unidadesStock = $('span#stockinfounidadesdrch-3');
			cilindroOnChange(cilindro, esfera, lente, diametro, nombreStock, unidadesStock, cilojodrchtemp3, esfojodrchtemp3, lenojodrchtemp3, diaojodrchtemp3);
			
		});

		[#-- CLICK EN EL CILINDRO DEL OJO IZQUIERDO --]
		$("#cilojoizq").change(function() {
			cilindro = $("#cilojoizq");
			esfera = $('#esfojoizq');
			lente = $('#lenojoizq');
			diametro = $('#diaojoizq');
			nombreStock = $('span#stockinfonombreizq');
			unidadesStock = $('span#stockinfounidadesizq');
			cilindroOnChange(cilindro, esfera, lente, diametro, nombreStock, unidadesStock, cilojoizqtemp, esfojoizqtemp, lenojoizqtemp, diaojoizqtemp);

		});

		$("#cilojoizq-2").change(function() {
			cilindro = $("#cilojoizq-2");
			esfera = $('#esfojoizq-2');
			lente = $('#lenojoizq-2');
			diametro = $('#diaojoizq-2');
			nombreStock = $('span#stockinfonombreizq-2');
			unidadesStock = $('span#stockinfounidadesizq-2');
			cilindroOnChange(cilindro, esfera, lente, diametro, nombreStock, unidadesStock, cilojoizqtemp2, esfojoizqtemp2, lenojoizqtemp2, diaojoizqtemp2);

		});

		$("#cilojoizq-3").change(function() {
			cilindro = $("#cilojoizq-3");
			esfera = $('#esfojoizq-3');
			lente = $('#lenojoizq-3');
			diametro = $('#diaojoizq-3');
			nombreStock = $('span#stockinfonombreizq-3');
			unidadesStock = $('span#stockinfounidadesizq-3');
			cilindroOnChange(cilindro, esfera, lente, diametro, nombreStock, unidadesStock, cilojoizqtemp3, esfojoizqtemp3, lenojoizqtemp3, diaojoizqtemp3);

		});

		function cilindroOnChange(cilindro, esfera, lente, diametro, nombreStock, unidadesStock, cilindroTemp, esferaTemp, lenteTemp, diametroTemp) {
			if (cilindro.val() != "" && cilindro.val() != null){
				
				var sPageURL = window.location.search.substring(1);
				
				var url = "${ctx.contextPath}/.rest/private/lens/spheres?cylinder=" + cilindro.val().replace("+", "");
				
				if(sPageURL != null){
					url = url + "&" + sPageURL;
				}
				
				$("#loading").show(); 
				
				$.ajax({
		            url : url,
		            type : "GET",
		            contentType : 'application/json; charset=utf-8',
		            cache : false,
		            dataType : "json",
		            success : function(response) {
		            	esfera.empty();
		            	esferaTemp = 'undefined';
		            	esfera.append(new Option("", ""));
						$.each(response.spheres, function( index, value ) {
							esfera.append(new Option(value, value));
						});
		            },
		            error : function(response) {
		                console.log("Error al recuperar las esferas del ojo derecho"); 
		                $("#loading").hide();  
		                cilindro.prop( "disabled", false );
		                esfera.prop( "disabled", false );         
		            },
		            complete : function(response) {
		            	$("#loading").hide();
		            	cilindro.prop( "disabled", false );
		            	esfera.prop( "disabled", false );
		            	lente.empty();
		            	lenteTemp = 'undefined';
		            	lente.prop( "disabled", false );
		            	diametro.empty();
		            	diametroTemp = 'undefined';
		            	diametro.prop( "disabled", false );
		            	nombreStock.empty();
		            	unidadesStock.empty();
		            }
		        });
		        
	        }else{
            	esfera.empty();
            	esferaTemp = 'undefined';
            	lente.empty();
            	lenteTemp = 'undefined';
	        	diametro.empty();
	        	diametroTemp = 'undefined';
	        }
	        
	        cilindroTemp = cilindro.val();
		}

		[#-- CLICK EN LA ESFERA DEL OJO DERECHO --]
		$("#esfojodrch").change(function() {
			cilindro = $("#cilojodrch");
			esfera = $('#esfojodrch');
			lente = $('#lenojodrch');
			diametro = $("#diaojodrch");
			cantidad = $("#cantojodrch");
			pvoFront = $("#pvofrontdrch");
			pvo = $("#pvoojodrch");
			pvpFront = $("#pvpfrontdrch");
			pvp = $("#pvpojodrch");
			sku = $("#skudrch");
			nombreStock = $('span#stockinfonombredrch');
			unidadesStock = $('span#stockinfounidadesdrch');
			esferaOnChange(cilindro, esfera, lente, diametro, nombreStock, unidadesStock, cilojodrchtemp, esfojodrchtemp, lenojodrchtemp, diaojodrchtemp);
	        
		});

		$("#esfojodrch-2").change(function() {
			cilindro = $("#cilojodrch-2");
			esfera = $('#esfojodrch-2');
			lente = $('#lenojodrch-2');
			diametro = $("#diaojodrch-2");
			cantidad = $("#cantojodrch-2");
			pvoFront = $("#pvofrontdrch-2");
			pvo = $("#pvoojodrch-2");
			pvpFront = $("#pvpfrontdrch-2");
			pvp = $("#pvpojodrch-2");
			sku = $("#skudrch-2");
			nombreStock = $('span#stockinfonombredrch-2');
			unidadesStock = $('span#stockinfounidadesdrch-2');
			esferaOnChange(cilindro, esfera, lente, diametro, nombreStock, unidadesStock, cilojodrchtemp2, esfojodrchtemp2, lenojodrchtemp2, diaojodrchtemp2);
			
		});

		$("#esfojodrch-3").change(function() {
			cilindro = $("#cilojodrch-3");
			esfera = $('#esfojodrch-3');
			lente = $('#lenojodrch-3');
			diametro = $("#diaojodrch-3");
			cantidad = $("#cantojodrch-3");
			pvoFront = $("#pvofrontdrch-3");
			pvo = $("#pvoojodrch-3");
			pvpFront = $("#pvpfrontdrch-3");
			pvp = $("#pvpojodrch-3");
			sku = $("#skudrch-3");
			nombreStock = $('span#stockinfonombredrch-3');
			unidadesStock = $('span#stockinfounidadesdrch-3');
			esferaOnChange(cilindro, esfera, lente, diametro, nombreStock, unidadesStock, cilojodrchtemp3, esfojodrchtemp3, lenojodrchtemp3, diaojodrchtemp3);
			
		});
		
		[#-- CLICK EN LA ESFERA DEL OJO IZQUIERDO --]
		$("#esfojoizq").change(function() {

			cilindro = $("#cilojoizq");
			esfera = $('#esfojoizq');
			lente = $('#lenojoizq');
			diametro = $('#diaojoizq');
			cantidad = $("#cantojoizq");
			pvoFront = $("#pvofrontizq");
			pvo = $("#pvoojoizq");
			pvpFront = $("#pvpfrontizq");
			pvp = $("#pvpojoizq");
			sku = $("#skuizq");
			nombreStock = $('span#stockinfonombreizq');
			unidadesStock = $('span#stockinfounidadesizq');
			esferaOnChange(cilindro, esfera, lente, diametro, nombreStock, unidadesStock, cilojoizqtemp, esfojoizqtemp, lenojoizqtemp, diaojoizqtemp);
			
		});

		$("#esfojoizq-2").change(function() {
			cilindro = $("#cilojoizq-2");
			esfera = $('#esfojoizq-2');
			lente = $('#lenojoizq-2');
			diametro = $('#diaojoizq-2');
			cantidad = $("#cantojoizq-2");
			pvoFront = $("#pvofrontizq-2");
			pvo = $("#pvoojoizq-2");
			pvpFront = $("#pvpfrontizq-2");
			pvp = $("#pvpojoizq-2");
			sku = $("#skuizq-2");
			nombreStock = $('span#stockinfonombreizq-2');
			unidadesStock = $('span#stockinfounidadesizq-2');
			esferaOnChange(cilindro, esfera, lente, diametro, nombreStock, unidadesStock, cilojoizqtemp2, esfojoizqtemp2, lenojoizqtemp2, diaojoizqtemp2);

		});

		$("#esfojoizq-3").change(function() {
			cilindro = $("#cilojoizq-3");
			esfera = $('#esfojoizq-3');
			lente = $('#lenojoizq-3');
			diametro = $('#diaojoizq-3');
			cantidad = $("#cantojoizq-3");
			pvoFront = $("#pvofrontizq-3");
			pvo = $("#pvoojoizq-3");
			pvpFront = $("#pvpfrontizq-3");
			pvp = $("#pvpojoizq-3");
			sku = $("#skuizq-3");
			nombreStock = $('span#stockinfonombreizq-3');
			unidadesStock = $('span#stockinfounidadesizq-3');
			esferaOnChange(cilindro, esfera, lente, diametro, nombreStock, unidadesStock, cilojoizqtemp3, esfojoizqtemp3, lenojoizqtemp3, diaojoizqtemp3);

		});

		function esferaOnChange(cilindro, esfera, lente, diametro, nombreStock, unidadesStock, cilindroTemp, esferaTemp, lenteTemp, diametroTemp) {
			if (esfera.val() != "" && esfera.val() != null && esfojodrchtemp != esfera.val()){
				
				var sPageURL = window.location.search.substring(1);
				
				var url = "${ctx.contextPath}/.rest/private/lens/lens?cylinder=" + cilindro.val().replace("+", "") + "&sphere=" + esfera.val().replace("+", "");
				
				if(sPageURL != null){
					url = url + "&" + sPageURL;
				}
			
				$("#loading").show(); 
				
				$.ajax({
		            url : url,
		            type : "GET",
		            contentType : 'application/json; charset=utf-8',
		            cache : false,
		            dataType : "json",
		            success : function(response) {
		            	lente.empty();
		            	lenteTemp = 'undefined';
		            	lente.append(new Option("", ""));
		            	
		            	var bucle = response.lens;
		            	for (var key in bucle) {
						    if (bucle.hasOwnProperty(key)) {
						        lente.append(new Option(key, bucle[key]));
						    }
						}
		            },
		            error : function(response) {
		                console.log("Error al recuperar las lentes del ojo derecho"); 
		                $("#loading").hide();  
		                lente.prop( "disabled", false );
		                esfera.prop( "disabled", false );         
		            },
		            complete : function(response) {
		            	$("#loading").hide(); 
		            	esfera.prop( "disabled", false );
		            	lente.prop( "disabled", false );
		            	diametro.empty();
		            	diametroTemp = 'undefined';
		            	diametro.prop( "disabled", true );
		            	nombreStock.empty();
		            	unidadesStock.empty();
		            }
		        });
		        
	        }else{
	        	lente.empty();
	        	lenteTemp = 'undefined';
	        	diametro.empty();
	        	diametroTemp = 'undefined';
	        }
	        
	        esferaTemp = esfera.val();
		}
		
		[#-- CLICK EN LA LENTE DEL OJO DERECHO --]
		$("#lenojodrch").change(function() {
			cilindro = $("#cilojodrch");
			esfera = $('#esfojodrch');
			lente = $('#lenojodrch');
			diametro = $("#diaojodrch");
			nombreStock = $('span#stockinfonombredrch');
			unidadesStock = $('span#stockinfounidadesdrch');
			lenteOnChange(cilindro, esfera, lente, diametro, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, cilojodrchtemp, esfojodrchtemp, lenojodrchtemp, diaojodrchtemp, "OD");
	        
		});

		$("#lenojodrch-2").change(function() {
			cilindro = $("#cilojodrch-2");
			esfera = $('#esfojodrch-2');
			lente = $('#lenojodrch-2');
			diametro = $("#diaojodrch-2");
			nombreStock = $('span#stockinfonombredrch-2');
			unidadesStock = $('span#stockinfounidadesdrch-2');
			lenteOnChange(cilindro, esfera, lente, diametro, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, cilojodrchtemp2, esfojodrchtemp2, lenojodrchtemp2, diaojodrchtemp2, "OD");
			
		});

		$("#lenojodrch-3").change(function() {
			cilindro = $("#cilojodrch-3");
			esfera = $('#esfojodrch-3');
			lente = $('#lenojodrch-3');
			diametro = $("#diaojodrch-3");
			nombreStock = $('span#stockinfonombredrch-3');
			unidadesStock = $('span#stockinfounidadesdrch-3');
			lenteOnChange(cilindro, esfera, lente, diametro, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, cilojodrchtemp3, esfojodrchtemp3, lenojodrchtemp3, diaojodrchtemp3, "OD");
			
		});
		
		[#-- CLICK EN LA LENTE DEL OJO IZQUIERDO --]
		$("#lenojoizq").change(function() {
			cilindro = $("#cilojoizq");
			esfera = $('#esfojoizq');
			lente = $('#lenojoizq');
			diametro = $('#diaojoizq');
			nombreStock = $('span#stockinfonombreizq');
			unidadesStock = $('span#stockinfounidadesizq');
			lenteOnChange(cilindro, esfera, lente, diametro, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, cilojoizqtemp, esfojoizqtemp, lenojoizqtemp, diaojoizqtemp, "OI");
	        
		});

		$("#lenojoizq-2").change(function() {
			cilindro = $("#cilojoizq-2");
			esfera = $('#esfojoizq-2');
			lente = $('#lenojoizq-2');
			diametro = $('#diaojoizq-2');
			nombreStock = $('span#stockinfonombreizq-2');
			unidadesStock = $('span#stockinfounidadesizq-2');
			lenteOnChange(cilindro, esfera, lente, diametro, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, cilojoizqtemp2, esfojoizqtemp2, lenojoizqtemp2, diaojoizqtemp2, "OI");

		});

		$("#lenojoizq-3").change(function() {
			cilindro = $("#cilojoizq-3");
			esfera = $('#esfojoizq-3');
			lente = $('#lenojoizq-3');
			diametro = $('#diaojoizq-3');
			nombreStock = $('span#stockinfonombreizq-3');
			unidadesStock = $('span#stockinfounidadesizq-3');
			lenteOnChange(cilindro, esfera, lente, diametro, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, cilojoizqtemp3, esfojoizqtemp3, lenojoizqtemp3, diaojoizqtemp3, "OI");

		});

		function lenteOnChange(cilindro, esfera, lente, diametro, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, cilindroTemp, esferaTemp, lenteTemp, diametroTemp, ojo) {

			if (lente.val() != "" && lente.val() != null && lenteTemp != lente.val()){
				
				var sPageURL = window.location.search.substring(1);
				
				var url = "${ctx.contextPath}/.rest/private/lens/diameters?cylinder=" + cilindro.val().replace("+", "") + "&sphere=" + esfera.val().replace("+", "") + "&lens=" + lente.val();
				
				if(sPageURL != null){
					url = url + "&" + sPageURL;
				}
			
				$("#loading").show(); 
				
				$.ajax({
		            url : url,
		            type : "GET",
		            contentType : 'application/json; charset=utf-8',
		            cache : false,
		            dataType : "json",
		            success : function(response) {
		            	diametro.empty();
		            	diametroTemp = 'undefined';
		            	diametro.append(new Option("", ""));
						$.each(response.diameters, function( index, value ) {
							diametro.append(new Option(value, value));
						});
						
						if (response.diameters.length == 1){
							diametro.val(response.diameters[0]);
		
							if (diametro.val() != "" && diametro.val() != null && diametroTemp != diametro.val()){
								getPrice(cilindro, esfera, lente, diametro, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, ojo)
							}
							
							diametroTemp = diametro.val();
						}
						if (response.diameters.length == 0){
							$("#msgerroraddtocart").css("display", "block");
							$("#msgerroraddtocart").text("${i18n['cione-module.templates.myshop.configurador-lentes-component.nodisponible']}");
						}
		            },
		            error : function(response) {
		                console.log("Error al recuperar los diametros del ojo derecho"); 
		                $("#loading").hide(); 
		                lente.prop( "disabled", false ); 
		                diametro.prop( "disabled", false );
		            },
		            complete : function(response) {
		            	$("#loading").hide();
		            	lente.prop( "disabled", false ); 
		            	diametro.prop( "disabled", false );
		            }
		        });
		        
	        }else{
	        	diametro.empty();
	        	diametroTemp = 'undefined';
	        }
	        
	        lenteTemp = lente.val();
		}
		
		$("#diaojodrch").change(function() {
			cilindro = $("#cilojodrch");
			esfera = $('#esfojodrch');
			lente = $('#lenojodrch');
			diametro = $('#diaojodrch');
			cantidad = $("#cantojodrch");
			pvoFront = $("#pvofrontdrch");
			pvo = $("#pvoojodrch");
			pvpFront = $("#pvpfrontdrch");
			pvp = $("#pvpojodrch");
			sku = $("#skudrch");
			nombreStock = $('span#stockinfonombredrch');
			unidadesStock = $('span#stockinfounidadesdrch');
			diametroOnChange(cilindro, esfera, lente, diametro, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, "OD", diaojodrchtemp)
		
		});

		$("#diaojodrch-2").change(function() {
			cilindro = $("#cilojodrch-2");
			esfera = $('#esfojodrch-2');
			lente = $('#lenojodrch-2');
			diametro = $('#diaojodrch-2');
			cantidad = $("#cantojodrch-2");
			pvoFront = $("#pvofrontdrch-2");
			pvo = $("#pvoojodrch-2");
			pvpFront = $("#pvpfrontdrch-2");
			pvp = $("#pvpojodrch-2");
			sku = $("#skudrch-2");
			nombreStock = $('span#stockinfonombredrch-2');
			unidadesStock = $('span#stockinfounidadesdrch-2');
			diametroOnChange(cilindro, esfera, lente, diametro, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, "OD", diaojodrchtemp2)
		
		});

		$("#diaojodrch-3").change(function() {
			cilindro = $("#cilojodrch-3");
			esfera = $('#esfojodrch-3');
			lente = $('#lenojodrch-3');
			diametro = $('#diaojodrch-3');
			cantidad = $("#cantojodrch-3");
			pvoFront = $("#pvofrontdrch-3");
			pvo = $("#pvoojodrch-3");
			pvpFront = $("#pvpfrontdrch-3");
			pvp = $("#pvpojodrch-3");
			sku = $("#skudrch-3");
			nombreStock = $('span#stockinfonombredrch-3');
			unidadesStock = $('span#stockinfounidadesdrch-3');
			diametroOnChange(cilindro, esfera, lente, diametro, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, "OD", diaojodrchtemp3)
		
		});
		
		$("#diaojoizq").change(function() {
			cilindro = $("#cilojoizq");
			esfera = $('#esfojoizq');
			lente = $('#lenojoizq');
			diametro = $('#diaojoizq');
			cantidad = $("#cantojoizq");
			pvoFront = $("#pvofrontizq");
			pvo = $("#pvoojoizq");
			pvpFront = $("#pvpfrontizq");
			pvp = $("#pvpojoizq");
			sku = $("#skuizq");
			nombreStock = $('span#stockinfonombreizq');
			unidadesStock = $('span#stockinfounidadesizq');
			diametroOnChange(cilindro, esfera, lente, diametro, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, "OI", diaojoizqtemp)

		});

		$("#diaojoizq-2").change(function() {
			cilindro = $("#cilojoizq-2");
			esfera = $('#esfojoizq-2');
			lente = $('#lenojoizq-2');
			diametro = $('#diaojoizq-2');
			cantidad = $("#cantojoizq-2");
			pvoFront = $("#pvofrontizq-2");
			pvo = $("#pvoojoizq-2");
			pvpFront = $("#pvpfrontizq-2");
			pvp = $("#pvpojoizq-2");
			sku = $("#skuizq-2");
			nombreStock = $('span#stockinfonombreizq-2');
			unidadesStock = $('span#stockinfounidadesizq-2');
			diametroOnChange(cilindro, esfera, lente, diametro, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, "OI", diaojoizqtemp2)

		});

		$("#diaojoizq-3").change(function() {
			cilindro = $("#cilojoizq-3");
			esfera = $('#esfojoizq-3');
			lente = $('#lenojoizq-3');
			diametro = $('#diaojoizq-3');
			cantidad = $("#cantojoizq-3");
			pvoFront = $("#pvofrontizq-3");
			pvo = $("#pvoojoizq-3");
			pvpFront = $("#pvpfrontizq-3");
			pvp = $("#pvpojoizq-3");
			sku = $("#skuizq-3");
			nombreStock = $('span#stockinfonombreizq-3');
			unidadesStock = $('span#stockinfounidadesizq-3');
			diametroOnChange(cilindro, esfera, lente, diametro, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, "OI", diaojoizqtemp3)

		});

		function diametroOnChange(cilindro, esfera, lente, diametro, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, ojo, diametroTemp) {
			if (diametro.val() != "" && diametro.val() != null && diametroTemp != diametro.val()){
				getPrice(cilindro, esfera, lente, diametro, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, ojo)
			}
			
			diametroTemp = diametro.val();
			
		}
		
		$("#cantojodrch").change(function() {
			lente = $("#lenojodrch");
			cantidad = $("#cantojodrch");
			pvoFront = $("#pvofrontdrch");
			pvo = $("#pvoojodrch");
			pvpFront = $("#pvpfrontdrch");
			pvp = $("#pvpojodrch");
			sku = $("#skudrch");
			nombreStock = $('span#stockinfonombredrch');
			unidadesStock = $('span#stockinfounidadesdrch');
			cantidadOnChange(lente, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, "OD");
		});

		$("#cantojodrch-2").change(function() {
			lente = $("#lenojodrch-2");
			cantidad = $("#cantojodrch-2");
			pvoFront = $("#pvofrontdrch-2");
			pvo = $("#pvoojodrch-2");
			pvpFront = $("#pvpfrontdrch-2");
			pvp = $("#pvpojodrch-2");
			sku = $("#skudrch-2");
			nombreStock = $('span#stockinfonombredrch-2');
			unidadesStock = $('span#stockinfounidadesdrch-2');
			cantidadOnChange(lente, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, "OD");
		});

		$("#cantojodrch-3").change(function() {
			lente = $("#lenojodrch-3");
			cantidad = $("#cantojodrch-3");
			pvoFront = $("#pvofrontdrch-3");
			pvo = $("#pvoojodrch-3");
			pvpFront = $("#pvpfrontdrch-3");
			pvp = $("#pvpojodrch-3");
			sku = $("#skudrch-3");
			nombreStock = $('span#stockinfonombredrch-3');
			unidadesStock = $('span#stockinfounidadesdrch-3');
			cantidadOnChange(lente, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, "OD");
		});
		
		$("#cantojoizq").change(function() {
			lente = $("#lenojoizq");
			cantidad = $("#cantojoizq");
			pvoFront = $("#pvofrontizq");
			pvo = $("#pvoojoizq");
			pvpFront = $("#pvpfrontizq");
			pvp = $("#pvpojoizq");
			sku = $("#skuizq");
			nombreStock = $('span#stockinfonombreizq');
			unidadesStock = $('span#stockinfounidadesizq');
			cantidadOnChange(lente, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, "OI");
		});

		$("#cantojoizq-2").change(function() {
			lente = $("#lenojoizq-2");
			cantidad = $("#cantojoizq-2");
			pvoFront = $("#pvofrontizq-2");
			pvo = $("#pvoojoizq-2");
			pvpFront = $("#pvpfrontizq-2");
			pvp = $("#pvpojoizq-2");
			sku = $("#skuizq-2");
			nombreStock = $('span#stockinfonombreizq-2');
			unidadesStock = $('span#stockinfounidadesizq-2');
			cantidadOnChange(lente, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, "OI");
		});

		$("#cantojoizq-3").change(function() {
			lente = $("#lenojoizq-3");
			cantidad = $("#cantojoizq-3");
			pvoFront = $("#pvofrontizq-3");
			pvo = $("#pvoojoizq-3");
			pvpFront = $("#pvpfrontizq-3");
			pvp = $("#pvpojoizq-3");
			sku = $("#skuizq-3");
			nombreStock = $('span#stockinfonombreizq-3');
			unidadesStock = $('span#stockinfounidadesizq-3');
			cantidadOnChange(lente, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, "OI");
		});

		function cantidadOnChange(lente, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, ojo) {
			if (parseInt(cantidad.val()) > 0){
				cantidad.removeClass("validation-error");
			}
			
			updatePVOAndPVP(lente, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, ojo);
		}

		function updatePVOAndPVP(lente, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, ojo) {
			if (cantidad.val() > 0) {
			if (pvoFront.val() != null && pvoFront.val() > 0){
			    [#if showPVO(ctx.getUser(), uuid, username)]    
	        	var cantpvodrch = parseFloat(pvoFront.val());
	        	var count = parseInt(cantidad.val());
	        	cantpvodrch = cantpvodrch * count;
	    		pvo.val(cantpvodrch.toFixed(2).toString().replace(' ','').replace('.',',') + ' \u20AC');
	    		[/#if]
	        }
	        
	        if (pvpFront.val() != null && pvpFront.val() > 0){
	        	[#if showPVP(ctx.getUser(), uuid, username)]
	        	var cantpvpdrch = parseFloat(pvpFront.val());
	        	var count = parseInt(cantidad.val());
	        	cantpvpdrch = cantpvpdrch * count;
	        	pvp.val(cantpvpdrch.toFixed(2).toString().replace(' ','').replace('.',',') + ' \u20AC');
	        	[/#if]
	        }
	        
	        if (sku.val()){
	        
				var urlstockRE = "${ctx.contextPath}/.rest/private/stock?sku=" + encodeURIComponent(sku.val());
				
		        $.ajax({
					url : urlstockRE,
					type : "GET",
					cache : false, 
					async: false, 
					success : function(response) {
					
						var stockreallabel = "${i18n['cione-module.templates.myshop.configurador-lentes-component.stock']} ";
						var stockcanariaslabel = "${i18n['cione-module.templates.myshop.listado-productos-home-component.canar']}";
						var stockcentrallabel = "${i18n['cione-module.templates.myshop.listado-productos-home-component.ctral']}";
						var stockunidadeslabel = " ${i18n['cione-module.templates.myshop.listado-productos-home-component.units']}";

						if (ojo == "OD") {
							nombreStock.text("${i18n['cione-module.templates.myshop.configurador-lentes-component.stockod']}" + lente.find( "option:selected" ).text() );
						} else {
							nombreStock.text("${i18n['cione-module.templates.myshop.configurador-lentes-component.stockoi']}" + lente.find( "option:selected" ).text() );
						}
	    				
	    				if (response.almacen == 'stockCANAR'){
							unidadesStock.html(stockcanariaslabel + response.stock + stockunidadeslabel + "<br\>" + stockcentrallabel+ response.stockCTRAL + stockunidadeslabel);
						}else{
							unidadesStock.text(stockreallabel + response.stock + stockunidadeslabel);
						}
					},
					error : function(response) {
						console.log(response); 
					}
				});
	        }
	        
		}else{
			pvo.val('');
	    	pvp.val('');
	    	nombreStock.text('');
	    	unidadesStock.text('');
		}
		}
		
		[#-- CLICK AUMENTAR LA CANTIDAD --]
		$('.product-amount-button-plus').on("click", function () {
		
			parent = $(this).parents('.b2b-lentes-tabla-info.row');
			id = parent.find("select[id^='cilojo']").attr('id').split('cilojo');
			suffix = "";
			if (id.length > 1) {
				suffix = id[1]
			}

			lente = $("#lenojo" + suffix);
			cantidad = $("#cantojo" + suffix);
			pvoFront = $("#pvofront" + suffix);
			pvo = $("#pvoojo" + suffix);
			pvpFront = $("#pvpfront" + suffix);
			pvp = $("#pvpojo" + suffix);
			sku = $("#sku" + suffix);
			nombreStock = $('span#stockinfonombre' + suffix);
			unidadesStock = $('span#stockinfounidades' + suffix);
			ojo = "";
			if (suffix.includes("drch")) {
				ojo = "OD";
			} else {
				ojo = "OI";
			}

		    var inputAmount = $(this).parent().find('.product-amount-input');
		    count = parseInt(inputAmount.val());
		    count = parseInt(count) + 1;
		    inputAmount.val(parseInt(count));
		    
		    if ($("#cantojodrch").val() > 0 || $("#cantojoizq").val() > 0 
				|| $("#cantojodrch-2").val() > 0 || $("#cantojoizq-2").val() > 0 
				|| $("#cantojodrch-3").val() > 0 || $("#cantojoizq-3").val() > 0) {
		        $("#addtocartbtn").prop('disabled', false);
		        $("#addtocartbtn").css("cursor","pointer");
		        updatePVOAndPVP(lente, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, ojo);
		    }else{
		    	$("#addtocartbtn").prop('disabled', true);
		    	$("#addtocartbtn").css("cursor","not-allowed");
		        updatePVOAndPVP(lente, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, ojo);
		    }
		    
		});
	
		[#-- CLICK DISMINUIR LA CANTIDAD --]
		$('.product-amount-button-minus').on("click", function () {

			parent = $(this).parents('.b2b-lentes-tabla-info.row');
			id = parent.find("select[id^='cilojo']").attr('id').split('cilojo');
			suffix = "";
			if (id.length > 1) {
				suffix = id[1]
			}

			lente = $("#lenojo" + suffix);
			cantidad = $("#cantojo" + suffix);
			pvoFront = $("#pvofront" + suffix);
			pvo = $("#pvoojo" + suffix);
			pvpFront = $("#pvpfront" + suffix);
			pvp = $("#pvpojo" + suffix);
			sku = $("#sku" + suffix);
			nombreStock = $('span#stockinfonombre' + suffix);
			unidadesStock = $('span#stockinfounidades' + suffix);
			ojo = "";
			if (suffix.includes("drch")) {
				ojo = "OD";
			} else {
				ojo = "OI";
			}
		
		    var inputAmount = $(this).parent().find('.product-amount-input');
		    count = parseInt(inputAmount.val());
		    count = parseInt(count) - 1;
	
		    if (count >= 0) {
		        inputAmount.val(count);
		    }
		    
		     if ($("#cantojodrch").val() > 0 || $("#cantojoizq").val() > 0 
				|| $("#cantojodrch-2").val() > 0 || $("#cantojoizq-2").val() > 0 
				|| $("#cantojodrch-3").val() > 0 || $("#cantojoizq-3").val() > 0) {
		    	$("#addtocartbtn").prop('disabled', false);
		    	$("#addtocartbtn").css("cursor","pointer");
		        updatePVOAndPVP(lente, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, ojo);
		    }else{
		    	$("#addtocartbtn").prop('disabled', true);
		    	$("#addtocartbtn").css("cursor","not-allowed");
		        updatePVOAndPVP(lente, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, ojo);
		    }
		
		});

		function getPrice(cilindro, esfera, lente, diametro, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, ojo){
			var sPageURL = window.location.search.substring(1);
			var url = "${ctx.contextPath}/.rest/private/lens/linedatainfo?cylinder=" + cilindro.val().replace("+", "") + "&sphere=" + esfera.val().replace("+", "") + "&lens=" + lente.val() + "&diameter=" + diametro.val();
			
			if(sPageURL != null){
				url = url + "&" + sPageURL;
			}
		
			$("#loading").show();
			
			$.ajax({
				url : url,
				type : "GET",
				contentType : 'application/json; charset=utf-8',
				cache : false,
				dataType : "json",
				success : function(response) {
					if ((response.pvo !== undefined) && (response.pvp !== undefined)) {
						var pvopriceresponse = parseFloat(response.pvo).toFixed(2);
						var pvppriceresponse = parseFloat(response.pvp).toFixed(2);
						pvoFront.val(pvopriceresponse);
						pvpFront.val(pvppriceresponse);
						sku.val(response.sku);
						$("#msgerroraddtocart").css("display", "none");
					} else {
						pvo.val('');
						pvp.val('');
						pvoFront.val('');
						pvpFront.val('');
						$("#msgerroraddtocart").text("${i18n['cione-module.templates.myshop.configurador-lentes-component.nodisponible']}");
						$("#msgerroraddtocart").css("display", "block");
					}
				},
				error : function(response) {
					console.log("Error al recuperar los datos de compra del ojo"); 
					$("#loading").hide();  
				},
				complete : function(response) {
					updatePVOAndPVP(lente, cantidad, pvoFront, pvo, pvpFront, pvp, sku, nombreStock, unidadesStock, ojo); 
					$("#loading").hide(); 
				}
			});
		}
		
	});
</script>
