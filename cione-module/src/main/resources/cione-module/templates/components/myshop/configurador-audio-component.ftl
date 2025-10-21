<style>
.cmp-configurador .card .card-header.complete .selector-arrow {
   background-image: url("${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/arrow-down-white.svg") !important; 
}

.cmp-configurador .card .card-header.collapsed .selector-arrow {
  -webkit-transform: rotate(0deg);
  transform: rotate(0deg);
}

.cmp-configurador .card .card-header .selector-arrow {
  background-image: url("${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/arrow-down-gray.svg") !important;
  width: 20px;
  height: 20px;
  background-repeat: no-repeat;
  -webkit-transform: rotate(180deg);
  transform: rotate(180deg);
}
</style>

[#include "../../includes/macros/cione-utils-impersonate.ftl"]
[#assign infoVariant = model.getVariant()!] 
[#assign familiaProducto = infoVariant.getFamiliaProducto()!]
[#switch familiaProducto]
	[#case "accesorios"]
		[#break]
[/#switch]

[#assign refTaller = model.getRefTaller()]

[#-- Flecha blanca que aparece cuando el configurador está en azul --]
[#--
<style>
	.cmp-configurador .card .card-header.complete .selector-arrow {
	  background-image: url("../../cione-theme/webresources/img/myshop/audio/arrow-down-white.svg");
	}
</style>
--]

[#-- Seleccion lado --]
<section class="cmp-configurador container">

  <div class="accordion" id="accordion2" data-collapse-type="manual">
    <div class="accordion-group card">
      <div class="accordion-heading ">
        <a class="accordion-toggle card-header dbasic collapsed" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne">
          <span class="title">${i18n['cione-module.templates.myshop.configurador-audio-component.datosbasicos']}</span>
          <span class="accicon">
            <div class="selector-arrow"></div> 
          </span>
        </a>
      </div>
      
      <div id="collapseOne" class="accordion-body dbasic collapse in card-container">
        <div class="accordion-inner card-body">
          <div class="container">
            <div class="selector-row">
              <div class="selector-item"> <img class="selector-img" id="derecho" name="side-right"
                  src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/ear-right.png" alt="" />
                  <p class="cmp-datos-drch">${i18n['cione-module.templates.myshop.configurador-audio-component.right']}</p>
              </div>
              <div class="selector-item">
                <img class="selector-img selected" id="binaural" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/ear-both.png" alt="" />
              	<p class="cmp-datos-bi">${i18n['cione-module.templates.myshop.configurador-audio-component.binaural']}</p>
              </div>
              <div class="selector-item"><img class="selector-img" id="izquierdo" name="side-left"
                  src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/ear-left.png" alt="" />
                  <p class="cmp-datos-izq">${i18n['cione-module.templates.myshop.configurador-audio-component.left']}</p>
              </div>
            </div>
            <p class="inner-title">${i18n['cione-module.templates.myshop.configurador-audio-component.datosAplicacion']}</p>
            <div class="row">
              <div class="col-12 col-lg-4">
                <label class="label" for="referencia">${i18n['cione-module.templates.myshop.configurador-audio-component.referencia']}</label>
                <input type="text" id="referencia" name="referencia">
              </div>
              <div class="col-12 col-lg-4">
                <label class="label" for="gabinete">${i18n['cione-module.templates.myshop.configurador-audio-component.infoGabinete']}</label>
                <input type="text" id="gabinete" name="gabinete">
              </div>
              
                [#if infoVariant.getFormatos()?? && infoVariant.getFormatos()?has_content]
                	<div class="col-12 col-lg-4">
		                <label class="label" for="gabinete">${i18n['cione-module.templates.myshop.configurador-audio-component.formato']}</label>
                		<select name="formato" id="formato">
		                    [#assign formatos = infoVariant.getFormatos()]
		            		<option value="" selected></option>
		            		[#list formatos as formato]
		                    	<option value="${formato!""}">${formato!""}</option>
		                    [/#list]
		                </select>
		            </div>
                [/#if]
                
              </div>
            </div>

            <div class="separator"></div>
            <p class="inner-text">
            	${content.textoSeleccionLado!""}
            </p>

          </div>
        </div>
      </div>
    </div>[#-- Fin Seleccion lado --]
    
    [#-- Audiograma --]
	<div class="accordion-group card">
      <div class="accordion-heading ">
        <a class="accordion-toggle card-header audiogram collapsed" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
          <span class="title">${i18n['cione-module.templates.myshop.configurador-audio-component.labelConfiguradorAudiograma']}</span>
          <span class="accicon">
            <div class="selector-arrow"></div> 
          </span>
        </a>
      </div>
      
      <div id="collapseTwo" class="accordion-body audiogram collapse in card-container">
        <div class="accordion-inner card-body card-chart">
		  <script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/myshop/chart.min.js"></script>
          <script src="${ctx.contextPath}/.resources/cione-theme/webresources/js/myshop/chartjs-plugin-dragdata.min.js"></script>
		<div class="container cmp-chart">
    	<div class="row">
        <div class="col-lg-5 col-12">
            <form action="">
                <div class="chart-title">${i18n['cione-module.templates.myshop.configurador-audio-component.audiogramaOido']} <span class="chart-color-right">${i18n['cione-module.templates.myshop.configurador-audio-component.dcho']}</span></div>
                <div id="validateAudioR" style="color: red;"></div>
                <div class="chart-inputs-container">

                    <div class="chart-input-item">
                        <label for="r250">250Hz</label>
                        <input name="ear-right" key="250Hz"  type="number" maxlength="3"  min="0" max="999"
                            onfocusout="updateChart(this)" onchange="updateChart(this)" value="0" id="r250">
                    </div>

                    <div class="chart-input-item">
                        <label for="r500">500Hz</label>
                        <input name="ear-right" key="500Hz"  type="number" maxlength="3"  min="0" max="999"
                            onfocusout="updateChart(this)" onchange="updateChart(this)" value="0" id="r500">
                    </div>

                    <div class="chart-input-item">
                        <label for="r1k">1kHz</label>
                        <input name="ear-right" key="1kHz"  type="number" maxlength="3"  min="0" max="999"
                            onfocusout="updateChart(this)" onchange="updateChart(this)" value="0" id="r1k">
                    </div>

                    <div class="chart-input-item">
                        <label for="r2k">2kHz</label>
                        <input name="ear-right" key="2kHz" type="number" maxlength="3"  min="0" max="999"
                            onfocusout="updateChart(this)" onchange="updateChart(this)" value="0" id="r2k">
                    </div>

                    <div class="chart-input-item">
                        <label for="r4k">4kHz</label>
                        <input name="ear-right" key="4kHz" type="number" maxlength="3"  min="0" max="999"
                            onfocusout="updateChart(this)" onchange="updateChart(this)" value="0" id="r4k">
                    </div>

                    <div class="chart-input-item">
                        <label for="r8k">8kHz</label>
                        <input name="ear-right" key="8kHz" type="number" maxlength="3"  min="0" max="999"
                            onfocusout="updateChart(this)" onchange="updateChart(this)" value="0" id="r8k">
                    </div>

                </div>
                <div style="width: auto;height:400px;border:1px solid black;padding:25px; background-color:white;">
                    <canvas id="myChart1"></canvas>
                </div>
                <div class="b2b-button-wrapper">
                    <button class="b2b-button b2b-button-filter" type="button" onclick="resetChart('ear-right')">
                        ${i18n['cione-module.templates.myshop.configurador-audio-component.BorrarMayusc']}
                    </button>
                </div>
            </form>
        </div>
        
        <div class="col-lg-2 col-12 chart-buttons-copy">

            <div class="b2b-button-wrapper b2b-button-copy">
                <button class="b2b-button b2b-button-filter" name="toRight" onclick="dataCopy(this.name)" type="button">
                    <span class="copy-arrow-right" style="background-image: url('${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/arrow-down-gray.svg')"></span>
                </button>
            </div>

            <div class="b2b-button-wrapper b2b-button-copy">
                <button class="b2b-button b2b-button-filter" name="toLeft" onclick="dataCopy(this.name)" type="button">
                    <span class="copy-arrow-left" style="background-image: url('${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/arrow-down-gray.svg')"></span>
                </button>
            </div>


        </div>
        <div class="col-lg-5 col-12">
            <form action="">
                <div class="chart-title">${i18n['cione-module.templates.myshop.configurador-audio-component.audiogramaOido']} <span class="chart-color-left">${i18n['cione-module.templates.myshop.configurador-audio-component.izdo']}</span></div>
                <div id="validateAudioL" style="color: red;"></div>
                <div class="chart-inputs-container">
                
                    <div class="chart-input-item">
                        <label for="l250">250Hz</label>
                        <input name="ear-left" key="250Hz" type="number" maxlength="3"  min="0" max="999"
                            onfocusout="updateChart(this)" onchange="updateChart(this)" value="0" id="l250">
                    </div>

                    <div class="chart-input-item">
                        <label for="l500">500Hz</label>
                        <input name="ear-left" key="500Hz" type="number" maxlength="3"  min="0" max="999"
                            onfocusout="updateChart(this)" onchange="updateChart(this)" value="0" id="l500">
                    </div>

                    <div class="chart-input-item">
                        <label for="l1k">1kHz</label>
                        <input name="ear-left" key="1kHz" type="number" maxlength="3"  min="0" max="999"
                            onfocusout="updateChart(this)" onchange="updateChart(this)" value="0" id="l1k">
                    </div>

                    <div class="chart-input-item">
                        <label for="l2k">2kHz</label>
                        <input name="ear-left" key="2kHz" type="number" maxlength="3"  min="0" max="999"
                            onfocusout="updateChart(this)" onchange="updateChart(this)" value="0" id="l2k">
                    </div>

                    <div class="chart-input-item">
                        <label for="l4k">4kHz</label>
                        <input name="ear-left" key="4kHz" type="number" maxlength="3"  min="0" max="999"
                            onfocusout="updateChart(this)" onchange="updateChart(this)" value="0" id="l4k">
                    </div>

                    <div class="chart-input-item">
                        <label for="l8k">8kHz</label>
                        <input name="ear-left" key="8kHz" type="number" maxlength="3"  min="0" max="999"
                            onfocusout="updateChart(this)" onchange="updateChart(this)" value="0" id="l8k">
                    </div>

                </div>
                <div style="width: auto;height:400px;border:1px solid black;padding:25px; background-color:white;">
                    <canvas id="myChart2"></canvas>
                </div>
                <div class="b2b-button-wrapper">
                    <button class="b2b-button b2b-button-filter" type="button" onclick="resetChart('ear-left')">
                        ${i18n['cione-module.templates.myshop.configurador-audio-component.BorrarMayusc']}
                    </button>
                </div>
            </form>
        </div>
    </div>
    
    <div class="row"></div>
        <p class="innerText">
        	${content.textoAudiograma!""}
        </p>
    </div>





<script>

    var ctx = document.getElementById('myChart1');
    var inputsRight = document.getElementsByName("ear-right");
    var imgChart1;
    var myChart1 = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['250', '500', '1k', '2k', '4k', '8k'],
            datasets: [{
                backgroundColor: 'rgba(255,255,255,1)', // Color del relleno punto
                label: '',
                data: [0, 0, 0, 0, 0, 0],
                fill: false, // Sombrear área
                borderColor: 'rgb(199,0,57)', // Color de la línea
                tension: 0.1, // Curva de la línea
                showLine: true, // Mostrar línea entre puntos
                pointStyle: 'circle', // Tipo de punto dibujado -- 'circle'
                // pointRotation: 45, // Rotar cross para que sea una X
                borderWidth: 1, // Ancho de la línea
                pointRadius: 5, // Tamaño de los puntos,
                // borderDash: [1],
                //borderDashOffset: 2,
                spanGaps: true, // Continuar línea sobre null points,
                order: 2

            }]
        },
        options: {
        	onClick: function(e,) {
                if (Chart.helpers.getRelativePosition(e, myChart1) != null){
                const canvasPosition = Chart.helpers.getRelativePosition(e, myChart1);

                // Substitute the appropriate scale IDs
                const dataX = myChart1.scales.x.getValueForPixel(canvasPosition.x);
                const dataY = myChart1.scales.y.getValueForPixel(canvasPosition.y);
                

                inputsRight[dataX].value = redondeocinco(dataY);
                myChart1.data.datasets[0].data[dataX] = redondeocinco(dataY);
                myChart1.update();
                }
            },
            animation: {
                onComplete: function () {
                    imgChart1 = myChart1.toBase64Image();
                    /* 
                       imgChart1 = $("#myChart1").get(0).toDataURL('image/jpeg');   
                    */

                }
            },
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false,
                },
            },

            scales: {
                y: {
                    min: -10,
                    max: 120,
                    zeroLineColor: '#00FF22',
                    reverse: true,
                    ticks: {
                        stepSize: 10,
                        color: '#222222'
                    },
                    grid: {
                        color: (line) => (line.index === 12 ? '#222222' : 'rgba(0, 0, 0, 0.1)'),
                        // tickBorderDash: [1] // Dash entre eje y y gráfica

                    }
                },
                x: {
                    position: 'top',
                    ticks: {
                        color: '#222222'
                    }
                }

            }
        },


    });



    var ctx2 = document.getElementById('myChart2');
    var inputsLeft = document.getElementsByName("ear-left");
    var imgChart2;
    var myChart2 = new Chart(ctx2, {
        type: 'line',
        data: {
            labels: ['250', '500', '1k', '2k', '4k', '8k'],
            datasets: [{
                backgroundColor: 'rgba(255,255,255,1)', // Color del relleno punto
                label: '',
                data: [0, 0, 0, 0, 0, 0],
                fill: false, // Sombrear área
                borderColor: 'rgb(6,16,99)', // Color de la línea
                tension: 0.1, // Curva de la línea
                showLine: true, // Mostrar línea entre puntos
                pointStyle: 'cross', // Tipo de punto dibujado -- 'circle'
                pointRotation: 45, // Rotar cross para que sea una X
                borderWidth: 1, // Ancho de la línea
                pointRadius: 5, // Tamaño de los puntos,
                //borderDash: [1],
                //borderDashOffset: 2,
                spanGaps: true, // Continuar línea sobre null points
                order: 1

            }]
        },
        options: {
			onClick: function(e,) {
	                if (Chart.helpers.getRelativePosition(e, myChart2) != null){
	
	               
	                const canvasPosition = Chart.helpers.getRelativePosition(e, myChart2);
	
	                // Substitute the appropriate scale IDs
	                const dataX = myChart2.scales.x.getValueForPixel(canvasPosition.x);
	                const dataY = myChart2.scales.y.getValueForPixel(canvasPosition.y);
	                
	
	                inputsLeft[dataX].value = redondeocinco(dataY);
	                myChart2.data.datasets[0].data[dataX] = redondeocinco(dataY);
	                myChart2.update();
            	}
            },
            animation: {
                onComplete: function () {
                    imgChart2 = myChart2.toBase64Image();

                }
            },
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false,
                },
            },
            scales: {
                y: {
                    min: -10,
                    max: 120,
                    reverse: true,
                    ticks: {
                        stepSize: 10,
                        color: '#222222'
                    },
                    grid: {
                        color: (line) => (line.index === 12 ? '#222222' : 'rgba(0, 0, 0, 0.1)'),
                        // tickBorderDash: [1] // Dash entre eje y y gráfica

                    }
                },
                x: {
                    position: 'top',
                    ticks: {
                        color: '#222222'
                    }
                }

            }
        },


    });






    /**********************************/
    /*  FUNCTION RESET CHARTS         */
    /**********************************/


    function resetChart(chart) {
    
        if (chart === "ear-right") {
            selectedChart = myChart1;
            var arrInputs = document.getElementsByName("ear-right");
            for (var i = 0; i < arrInputs.length; i++) {
            	var oCurInput = arrInputs[i];
            	oCurInput.value = oCurInput.defaultValue;
            }

        } else if (chart === "ear-left") {
            selectedChart = myChart2;
            var arrInputs = document.getElementsByName("ear-left");
            for (var i = 0; i < arrInputs.length; i++) {
            	var oCurInput = arrInputs[i];
            	oCurInput.value = oCurInput.defaultValue;
            }
        }

        selectedChart.data.datasets[0].data = [0, 0, 0, 0, 0, 0];
        selectedChart.update();
        
    }
    
    function redondeocinco(x) {
    	return Math.round(x / 5) * 5;
    	//return Math.ceil(x / 5) * 5;
    }

    /**********************************/
    /*  FUNCTION CHANGE CHART VALUES  */
    /**********************************/

    function updateChart(item) {
    	if ($("#"+item.id).val() != "") {
    		$("#"+item.id).removeClass("validation-error");
	        var name = item.getAttribute("name");
	        var input = document.getElementsByName(name);
			
			
			
	        if (name === "ear-right") {
	            selectedChart = myChart1;
	        } else if (name === "ear-left") {
	            selectedChart = myChart2;
	        }
	
			var minValue = -10;
			var maxValue = 120;
	        for (var i = 0; i < input.length; i++) {
	            input[i].value === "" ? newValue = null : newValue = input[i].value;
	            
	            newValue=redondeocinco(newValue);
	            input[i].value = newValue;
	            
	            
	            console.log(newValue);
	            if(newValue < minValue){
	            	selectedChart.data.datasets[0].data[i] = minValue;
	            	input[i].value = minValue;
	            }else if(newValue > maxValue){
	            	selectedChart.data.datasets[0].data[i] = maxValue;
	            	input[i].value = maxValue;
	            }else{
	            	selectedChart.data.datasets[0].data[i] = newValue;
	            }
	        }
	
	        selectedChart.update();
		} else {
			$("#"+item.id).addClass("validation-error");
		}
    }


    /**************************/
    /*  FUNCTION COPY BUTTON  */
    /**************************/

    function dataCopy(name) {

        var inputsLength = document.getElementsByName("ear-left").length;

        if (name === "toRight") {

            chartDestino = myChart2;
            chartOrigen = myChart1;
            inputOrigen = 'ear-left';
            inputDestino = 'ear-right';

        } else if (name === "toLeft") {

            chartDestino = myChart1;
            chartOrigen = myChart2;
            inputOrigen = 'ear-right';
            inputDestino = 'ear-left';

        }

        for (var i = 0; i < inputsLength; i++) {
            document.getElementsByName(inputOrigen)[i].value = document.getElementsByName(inputDestino)[i].value;
            chartDestino.data.datasets[0].data[i] = chartOrigen.data.datasets[0].data[i];

        }

        chartDestino.update();

    }
    
    
    /***********************************/
    /*  FUNCTION GET AUDIOGRAM VALUES  */
    /***********************************/
    
    function getAudiogramValues(nameRight, nameLeft){
		var audiogramValues = new Map();
		let jsonObject = {}; 
    	var inputRight = document.getElementsByName(nameRight);
    	var inputLeft = document.getElementsByName(nameLeft);
    	
    	/* ear-right */
    	if (nameRight != '') {
	    	for(var i = 0; i < inputRight.length; i++){
	    		console.log(inputRight[i].value);
	    		audiogramValues.set(inputRight[i].getAttribute('key'), inputRight[i].value);
	    		jsonObject[inputRight[i].getAttribute('key')] = inputRight[i].value;
	    	}
	    	
	    }
    	if (nameLeft != '') {
	    	/* ear-left */
	    	for(var i = 0; i < inputLeft.length; i++){
	    		audiogramValues.set(inputLeft[i].getAttribute('key'), inputLeft[i].value);
	    		jsonObject[inputLeft[i].getAttribute('key')] = inputLeft[i].value;
	    	}
	    }
    	return jsonObject;
    }
    
</script>


        </div>
      </div>
    </div>
    [#-- Fin Audiograma --]
    
    [#-- Nivel de potencia --]
    
    <div class="accordion-group card">
      <div class="accordion-heading ">
        <a class="accordion-toggle card-header cpotencia collapsed" data-toggle="collapse" data-parent="#accordion2"
          href="#collapse3">
          <span class="title">${i18n['cione-module.templates.myshop.configurador-audio-component.labelConfiguradorNivelPotencia']}</span>
          <span class="accicon">
            <div class="selector-arrow"></div> 
          </span>
        </a>
      </div>
      <div id="collapse3" class="accordion-body cpotencia collapse card-container">
        <div class="accordion-inner card-body ">

          <div class="container" name="tab-potencia" number="0">
            <div class="row">
            
            	[#if infoVariant.getPowerList()?? && infoVariant.getPowerList()?has_content]
		            <div class="col-12 col-lg-5">
		                <p class="ear-right-title">${i18n['cione-module.templates.myshop.configurador-audio-component.derecho']}</p>
                		<label class="label" for="rpotencia">${i18n['cione-module.templates.myshop.configurador-audio-component.potencia']}</label>
                		<select name="rpotencia" id="rpotencia">
		                    [#assign potencias = infoVariant.getPowerList()]
		            		<option value="" selected></option>
		            		[#list potencias as potencia]
		                    	<option value="${potencia!""}">${potencia!""}</option>
		                    [/#list]
		                </select>
		            </div>
		            
		            <div class="col-lg-2 col-12 chart-buttons-copy chart-button-copy-fix">
		                <div class="b2b-button-wrapper b2b-button-copy mt-3">
		                  <button class="b2b-button b2b-button-filter configurador-copy-check" data-side="toRight" type="button">
		                    <span class="copy-arrow-right" style="background-image:url('${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/arrow-down-gray.svg')"></span>
		                  </button>
		                </div>
		
		                <div class="b2b-button-wrapper b2b-button-copy mt-3">
		                  <button class="b2b-button b2b-button-filter configurador-copy-check" data-side="toLeft" type="button">
		                    <span class="copy-arrow-left" style="background-image:url('${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/arrow-down-gray.svg')"></span>
		                  </button>
		                </div>
		             </div>
		            
		            <div class="col-12 col-lg-5">
		                <p class="ear-left-title">${i18n['cione-module.templates.myshop.configurador-audio-component.izquierdo']}</p>
		                <label class="label" for="lpotencia">${i18n['cione-module.templates.myshop.configurador-audio-component.potencia']}</label>
		                <select name="lpotencia" id="lpotencia">
		                    [#assign potencias = infoVariant.getPowerList()]
		            		<option value="" selected></option>
		            		[#list potencias as potencia]
		                    	<option value="${potencia!""}">${potencia!""}</option>
		                    [/#list]
		                </select>
	              	</div>
	            [/#if]              
            </div>
          </div>
        </div>
      </div>
    </div>[#-- Fin Nivel de potencia --]
    
    [#-- Caracteristicas personalizadas --]
    <div class="accordion-group card">
      <div class="accordion-heading ">
        <a class="accordion-toggle card-header  collapsed" data-toggle="collapse" data-parent="#accordion2"
          href="#collapse4">
          <span class="title">${i18n['cione-module.templates.myshop.configurador-audio-component.labelConfiguradorCaracteristicasPersonalizadas']}</span>
          <span class="accicon">
            <div class="selector-arrow"></div> 
          </span>

        </a>
      </div>
      
      <div id="collapse4" class="accordion-body collapse card-container">
        <div class="accordion-inner card-body ">

          <div class="container" name="tab-caracteristicas" number="1">
            
            <div class="row">
              
              <div class="col-12 col-lg-6">
                <p class="ear-right-title">${i18n['cione-module.templates.myshop.configurador-audio-component.derecho']}</p>
            	<div>
	              	[#if infoVariant.direccionalidad?? && infoVariant.direccionalidad?has_content && infoVariant.direccionalidad]
	                	<img class="b2b-tick" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-green.svg" width="20" height="20" alt="">
	                	<input type="hidden" id="direccionalidad" name="direccionalidad" value="true">
						<label class="mt-4" for="rdireccionalidad">${i18n['cione-module.templates.myshop.configurador-audio-component.direccionalidad']}</label>	                
	                [#else]
	                	<img class="b2b-tick" style="padding-bottom:3px" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-red.svg" width="15" height="15" alt="">
	                	<input type="hidden" name="direccionalidad" value="false">
	                	<label class="mt-4" style="padding-left:5px" for="rdireccionalidad">${i18n['cione-module.templates.myshop.configurador-audio-component.direccionalidad']}</label>
	                [/#if]
                	
            	</div>
            	<div>
	              	[#if infoVariant.conectividad?? && infoVariant.conectividad?has_content && infoVariant.conectividad]
	                	<img class="b2b-tick" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-green.svg" width="20" height="20" alt="">
	                	<input type="hidden" name="conectividad" value="true">
	                	<label class="mt-4" for="rconectividad">${i18n['cione-module.templates.myshop.configurador-audio-component.conectividad']}</label>
	                [#else]
	                	<img class="b2b-tick" style="padding-bottom:3px" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-red.svg" width="15" height="15" alt="">
	                	<input type="hidden" style="padding-bottom:3px" name="conectividad" value="false">
	                	<label class="mt-4" style="padding-left:5px" for="rconectividad">${i18n['cione-module.templates.myshop.configurador-audio-component.conectividad']}</label>
	                [/#if]
            		
            	</div>
            	<div>
                  	[#if infoVariant.mediaconcha?? && infoVariant.mediaconcha?has_content && infoVariant.mediaconcha]
	                	<img class="b2b-tick" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-green.svg" width="20" height="20" alt="">
	                	<input type="hidden" name="mediaconcha" value="true">
	                	<label class="mt-4" for="rmedia">${i18n['cione-module.templates.myshop.configurador-audio-component.mediaConcha']}</label>
	                [#else]
	                	<img class="b2b-tick" style="padding-bottom:3px" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-red.svg" width="15" height="15" alt="">
	                	<input type="hidden" name="mediaconcha" value="false">
	                	<label class="mt-4" style="padding-left:5px" for="rmedia">${i18n['cione-module.templates.myshop.configurador-audio-component.mediaConcha']}</label>
	                [/#if]
              		
            	</div>
            	<div>
					[#if infoVariant.tinnitus?? && infoVariant.tinnitus?has_content && infoVariant.tinnitus]
	                	<img class="b2b-tick" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-green.svg" width="20" height="20" alt="">
	                	<input type="hidden" name="tinnitus" value="true">
	                [#else]
	                	<img class="b2b-tick" style="padding-bottom:3px" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-red.svg" width="15" height="15" alt="">
	                	<input type="hidden" name="tinnitus" value="false">
	                [/#if]
                	<label class="mt-4" style="padding-left:5px" for="venting">${i18n['cione-module.templates.myshop.configurador-audio-component.tinnitus']}</label>
				</div>

              </div>
              
              <div class="col-12 col-lg-6">
              	<p class="ear-left-title">${i18n['cione-module.templates.myshop.configurador-audio-component.izquierdo']}</p>
            	<div>
	              	[#if infoVariant.direccionalidad?? && infoVariant.direccionalidad?has_content && infoVariant.direccionalidad]
	                	<img class="b2b-tick" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-green.svg" width="20" height="20" alt="">
	                	<input type="hidden" id="direccionalidad" name="direccionalidad" value="true">
	                	<label class="mt-4" for="ldireccionalidad">${i18n['cione-module.templates.myshop.configurador-audio-component.direccionalidad']}</label>
	                [#else]
	                	<img class="b2b-tick" style="padding-bottom:3px" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-red.svg" width="15" height="15" alt="">
	                	<label class="mt-4" style="padding-left:5px" for="ldireccionalidad">${i18n['cione-module.templates.myshop.configurador-audio-component.direccionalidad']}</label>
	                [/#if]
            	</div>
            	<div>
	              	[#if infoVariant.conectividad?? && infoVariant.conectividad?has_content && infoVariant.conectividad]
	                	<img class="b2b-tick" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-green.svg" width="20" height="20" alt="">
	                	<label class="mt-4" for="lconectividad">${i18n['cione-module.templates.myshop.configurador-audio-component.conectividad']}</label>
	                [#else]
	                	<img class="b2b-tick" style="padding-bottom:3px" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-red.svg" width="15" height="15" alt="">
	                	<label class="mt-4" style="padding-left:5px" for="lconectividad">${i18n['cione-module.templates.myshop.configurador-audio-component.conectividad']}</label>
	                [/#if]
            	</div>
            	<div>
                  	[#if infoVariant.mediaconcha?? && infoVariant.mediaconcha?has_content && infoVariant.mediaconcha] 
	                	<img class="b2b-tick" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-green.svg" width="20" height="20" alt="">
	                	<label class="mt-4" for="lmedia">${i18n['cione-module.templates.myshop.configurador-audio-component.mediaConcha']}</label>
	                [#else]
	                	<img class="b2b-tick"style="padding-bottom:3px" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-red.svg" width="15" height="15" alt="">
	                	<label class="mt-4" style="padding-left:5px" for="lmedia">${i18n['cione-module.templates.myshop.configurador-audio-component.mediaConcha']}</label>
	                [/#if]
            	</div>
            	<div>
					[#if infoVariant.tinnitus?? && infoVariant.tinnitus?has_content && infoVariant.tinnitus]
	                	<img class="b2b-tick" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-green.svg" width="20" height="20" alt="">
	                	<input type="hidden" name="tinnitus" value="true">
	                [#else]
	                	<img class="b2b-tick" style="padding-bottom:3px" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-red.svg" width="15" height="15" alt="">
	                	<input type="hidden" name="tinnitus" value="false">
	                [/#if]
                	<label class="mt-4" style="padding-left:5px" for="venting">${i18n['cione-module.templates.myshop.configurador-audio-component.tinnitus']}</label>
				</div>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
    [#-- Fin Caracter�sticas personalizadas --]
    
    [#-- Caracter�sticas opcionales --]
    <div class="accordion-group card">
      <div class="accordion-heading ">
        <a class="accordion-toggle card-header copt collapsed" data-toggle="collapse" data-parent="#accordion2" href="#collapse5">
          <span class="title">${i18n['cione-module.templates.myshop.configurador-audio-component.labelConfiguradorCaracteristicasOpcionales']}</span>
          <span class="accicon">
            <div class="selector-arrow"></div> 
          </span>
        </a>
      </div>
      <div id="collapse5" class="accordion-body copt collapse card-container">
        <div class="accordion-inner card-body ">


          <div class="container" name="tab-opcionales" number="2">
            
            <div class="row">
            
              <div class="col-12 col-lg-5">
              	<div>
                	<p class="ear-right-title">${i18n['cione-module.templates.myshop.configurador-audio-component.derecho']}</p>
                	<label class="mt-4" for="rpila">${i18n['cione-module.templates.myshop.configurador-audio-component.tamanoPila']} : ${infoVariant.tamanoPila!""}</label>
                	<input type="hidden" name="rpila" value="${infoVariant.tamanoPila!''}">
                </div>
                <div class="configurador-check-wrapper">
	                [#if infoVariant.getLongitudCanal()?? && infoVariant.getLongitudCanal()?has_content]
	                	<label class="label mt-4" for="rlon">${i18n['cione-module.templates.myshop.configurador-audio-component.longitudCanal']}</label>
	            		<select name="rlon" id="rlon">
		                    [#assign longitudesCanal = infoVariant.getLongitudCanal()]
		            		<option value="" selected></option>
		            		[#list longitudesCanal as longitudCanal]
		                    	<option value="${longitudCanal!""}">${longitudCanal!""}</option>
		                    [/#list]
		                </select>
	                [/#if]
                </div>
                
                <div class="configurador-check-wrapper">
	                [#if infoVariant.telebobina?? && infoVariant.telebobina?has_content && infoVariant.telebobina]
	                	<input class="styled-checkbox" id="rtelebobina" type="checkbox">
	                [#else]
	                	<img class="b2b-tick" style="padding-bottom:3px" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-red.svg" width="15" height="15" alt="">
	                [/#if]
                	<label class="mt-4" for="rtelebobina">${i18n['cione-module.templates.myshop.configurador-audio-component.telebobina']}</label>
                </div>
				<div class="configurador-check-wrapper">    
	                [#if infoVariant.filtroCerumen?? && infoVariant.filtroCerumen?has_content]
	                	<label class="label mt-4" for="rfiltro">${i18n['cione-module.templates.myshop.configurador-audio-component.filtroAnticerumen']}</label>
	            		<select name="rfiltro" id="rfiltro" onchange="copyIfBinaural('filtro', 'r', 'l')">
		                    [#assign filtrosCerumen = infoVariant.filtroCerumen]
		            		<option value="" selected></option>
		            		[#list filtrosCerumen as filtroCerumen]
		                    	<option value="${filtroCerumen!""}">${filtroCerumen!""}</option>
		                    [/#list]
		                </select>
	                [/#if]
                </div>
                <div class="configurador-check-wrapper">
	            	[#if infoVariant.pulsador?? && infoVariant.pulsador?has_content && infoVariant.pulsador]
	                	<input class="styled-checkbox" id="rpulsador" type="checkbox">
	                [#else]
	                	<img class="b2b-tick" style="padding-bottom:3px" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-red.svg" width="15" height="15" alt="">
	                [/#if]
            		<label class="mt-4" for="rpulsador">${i18n['cione-module.templates.myshop.configurador-audio-component.pulsador']}</label>
				</div>
				<div class="configurador-check-wrapper">
					[#if infoVariant.controlVolumen?? && infoVariant.controlVolumen?has_content && infoVariant.controlVolumen]
	                	<input class="styled-checkbox" id="rvolumen" type="checkbox">
	                [#else]
	                	<img class="b2b-tick" style="padding-bottom:3px" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-red.svg" width="15" height="15" alt="">
	                [/#if]
                	<label class="mt-4" for="rvolumen">${i18n['cione-module.templates.myshop.configurador-audio-component.controlVolumen']}</label>
				</div>
				<div class="configurador-check-wrapper">
					[#if infoVariant.hiloExtractor?? && infoVariant.hiloExtractor?has_content && infoVariant.hiloExtractor]
	                	<input class="styled-checkbox" id="rhilo" type="checkbox">
	                [#else]
	                	<img class="b2b-tick" style="padding-bottom:3px" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-red.svg" width="15" height="15" alt="">
	                [/#if]
            		<label class="mt-4" for="rhilo">${i18n['cione-module.templates.myshop.configurador-audio-component.hiloExtractor']}</label>
            	</div>
				<div>
					[#if infoVariant.venting?? && infoVariant.venting?has_content && infoVariant.venting]
	                	<img class="b2b-tick" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-green.svg" width="20" height="20" alt="">
	                	<input type="hidden" name="venting" value="true">
	                [#else]
	                	<img class="b2b-tick" style="padding-bottom:3px" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-red.svg" width="15" height="15" alt="">
	                	<input type="hidden" name="venting" value="false">
	                [/#if]
                	<label class="mt-4" for="venting">${i18n['cione-module.templates.myshop.configurador-audio-component.venting']}</label>
				</div>
				<div>
	                [#if infoVariant.venting?? && infoVariant.venting?has_content && infoVariant.venting]
	                	[#if infoVariant.tipoVenting?? && infoVariant.tipoVenting?has_content]
		                	<label class="label mt-4" for="rtipoventing">${i18n['cione-module.templates.myshop.configurador-audio-component.tipoventing']}</label>
		            		<select name="rtipoventing" id="rtipoventing">
			                    [#assign tiposVenting = infoVariant.tipoVenting]
			            		<option value="" selected></option>
			            		[#list tiposVenting as tipoventing]
			                    	<option value="${tipoventing!""}">${tipoventing!""}</option>
			                    [/#list]
			                </select>
			            [/#if]
			            
			            [#if infoVariant.modVenting?? && infoVariant.modVenting?has_content]
		                	<label class="label mt-4" for="rmodventing">${i18n['cione-module.templates.myshop.configurador-audio-component.modificacionVenting']}</label>
		            		<select name="rmodventing" id="rmodventing">
			                    [#assign modificacionesVenting = infoVariant.modVenting]
			            		<option value="" selected></option>
			            		[#list modificacionesVenting as modventing]
			                    	<option value="${modventing!""}">${modventing!""}</option>
			                    [/#list]
			                </select>
			            [/#if]
	                [/#if]
				</div>
              </div>
              
              [#-- botones copiar en Caracteristicas opcionales --]
              <div class="col-lg-2 col-12 chart-buttons-copy">
                <div class="b2b-button-wrapper b2b-button-copy">
                  <button class="b2b-button b2b-button-filter configurador-copy-check" data-side="toRight" type="button">
                    <span class="copy-arrow-right" style="background-image:url('${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/arrow-down-gray.svg')"></span>
                  </button>
                </div>

                <div class="b2b-button-wrapper b2b-button-copy">
                  <button class="b2b-button b2b-button-filter configurador-copy-check" data-side="toLeft" type="button">
                    <span class="copy-arrow-left" style="background-image: url('${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/arrow-down-gray.svg')"></span>
                  </button>
                </div>
             </div>
             

              <div class="col-12 col-lg-5">
              	<div>
                	<p class="ear-left-title">${i18n['cione-module.templates.myshop.configurador-audio-component.izquierdo']}</p>
                	<label class="mt-4" for="lpila">${i18n['cione-module.templates.myshop.configurador-audio-component.tamanoPila']} : ${infoVariant.tamanoPila!""}</label>
                	<input type="hidden" name="lpila" value="${infoVariant.tamanoPila!''}">
                </div>
                <div class="configurador-check-wrapper">
                
	                [#if infoVariant.getLongitudCanal()?? && infoVariant.getLongitudCanal()?has_content]
	                	<label class="label mt-4" for="llon">${i18n['cione-module.templates.myshop.configurador-audio-component.longitudCanal']}</label>
	            		<select name="llon" id="llon">
		                    [#assign longitudesCanal = infoVariant.getLongitudCanal()]
		            		<option value="" selected></option>
		            		[#list longitudesCanal as longitudCanal]
		                    	<option value="${longitudCanal!""}">${longitudCanal!""}</option>
		                    [/#list]
		                </select>
	                [/#if]
				</div>
				
                <div class="configurador-check-wrapper">
	            	[#if infoVariant.telebobina?? && infoVariant.telebobina?has_content && infoVariant.telebobina]
	                	<input class="styled-checkbox" id="ltelebobina" type="checkbox">
	                [#else]
	                	<img class="b2b-tick" style="padding-bottom:3px" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-red.svg" width="15" height="15" alt="">
	                [/#if]
                	<label class="mt-4" for="ltelebobina">${i18n['cione-module.templates.myshop.configurador-audio-component.telebobina']}</label>
                </div>
                
                <div class="configurador-check-wrapper">
	                [#if infoVariant.filtroCerumen?? && infoVariant.filtroCerumen?has_content]
	                	<label class="label mt-4" for="lfiltro">${i18n['cione-module.templates.myshop.configurador-audio-component.filtroAnticerumen']}</label>
	            		<select name="lfiltro" id="lfiltro" onchange="copyIfBinaural('filtro', 'l', 'r')">
		                    [#assign filtrosCerumen = infoVariant.filtroCerumen]
		            		<option value="" selected></option>
		            		[#list filtrosCerumen as filtroCerumen]
		                    	<option value="${filtroCerumen!""}">${filtroCerumen!""}</option>
		                    [/#list]
		                </select>
	                [/#if]
                </div>
                
                <div class="configurador-check-wrapper">
	            	[#if infoVariant.pulsador?? && infoVariant.pulsador?has_content && infoVariant.pulsador]
	                	<input class="styled-checkbox" id="lpulsador" type="checkbox">
	                [#else]
	                	<img class="b2b-tick" style="padding-bottom:3px" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-red.svg" width="15" height="15" alt="">
	                [/#if]
            		<label class="mt-4" for="lpulsador">${i18n['cione-module.templates.myshop.configurador-audio-component.pulsador']}</label>
				</div>
				
                <div class="configurador-check-wrapper">
					[#if infoVariant.controlVolumen?? && infoVariant.controlVolumen?has_content && infoVariant.controlVolumen]
	                	<input class="styled-checkbox" id="lvolumen" type="checkbox">
	                [#else]
	                	<img class="b2b-tick" style="padding-bottom:3px" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-red.svg" width="15" height="15" alt="">
	                [/#if]
                	<label class="mt-4" for="lvolumen">${i18n['cione-module.templates.myshop.configurador-audio-component.controlVolumen']}</label>
				</div>
				
                <div class="configurador-check-wrapper">
					[#if infoVariant.hiloExtractor?? && infoVariant.hiloExtractor?has_content && infoVariant.hiloExtractor]
	                	<input class="styled-checkbox" id="lhilo" type="checkbox">
	                [#else]
	                	<img class="b2b-tick" style="padding-bottom:3px" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-red.svg" width="15" height="15" alt="">
	                [/#if]
            		<label class="mt-4" for="lhilo">${i18n['cione-module.templates.myshop.configurador-audio-component.hiloExtractor']}</label>
            	</div>
                <div>
					[#if infoVariant.venting?? && infoVariant.venting?has_content && infoVariant.venting]
	                	<img class="b2b-tick" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-green.svg" width="20" height="20" alt="">
	                [#else]
	                	<img class="b2b-tick" style="padding-bottom:3px" src="${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/tick-red.svg" width="15" height="15" alt="">
	                [/#if]
                	<label class="mt-4" for="lh">${i18n['cione-module.templates.myshop.configurador-audio-component.venting']}</label>
				</div>
				
                <div>
	                [#if infoVariant.venting?? && infoVariant.venting?has_content && infoVariant.venting]
	                	[#if infoVariant.tipoVenting?? && infoVariant.tipoVenting?has_content]
		                	<label class="label mt-4" for="ltipoventing">${i18n['cione-module.templates.myshop.configurador-audio-component.tipoventing']}</label>
		            		<select name="ltipoventing" id="ltipoventing">
			                    [#assign tiposVenting = infoVariant.tipoVenting]
			            		<option value="" selected></option>
			            		[#list tiposVenting as tipoventing]
			                    	<option value="${tipoventing!""}">${tipoventing!""}</option>
			                    [/#list]
			                </select>
			            [/#if]
			            
			            [#if infoVariant.modVenting?? && infoVariant.modVenting?has_content]
		                	<label class="label mt-4" for="lmodventing">${i18n['cione-module.templates.myshop.configurador-audio-component.modificacionVenting']}</label>
		            		<select name="lmodventing" id="lmodventing">
			                    [#assign modificacionesVenting = infoVariant.modVenting]
			            		<option value="" selected></option>
			            		[#list modificacionesVenting as modventing]
			                    	<option value="${modventing!""}">${modventing!""}</option>
			                    [/#list]
			                </select>
			            [/#if]
			            
	                [/#if]
				</div>
				
				
				
              </div>
            </div>
          </div>
        </div>
      </div>
    </div> [#-- Fin Caracteristicas opcionales --]
    
    [#-- Color --]
    <div class="accordion-group card">
      <div class="accordion-heading ">
        <a class="accordion-toggle card-header  collapsed" data-toggle="collapse" data-parent="#accordion2" href="#collapse6">
          <span class="title">${i18n['cione-module.templates.myshop.configurador-audio-component.labelConfiguradorColor']} </span>
          <span class="accicon">
            <div class="selector-arrow"></div> 
          </span>
        </a>
      </div>
      <div id="collapse6" class="accordion-body collapse card-container">
        <div class="accordion-inner card-body ">

          <div class="container" name="tab-color" number="3">
            <div class="row">
            
              <div class="col-12 col-lg-5">
                <p class="ear-right-title">${i18n['cione-module.templates.myshop.configurador-audio-component.derecho']}</p>
                [#assign listaAudioLab = infoVariant.colorAudifonoAudioLab!]
                [#if listaAudioLab?? && listaAudioLab?has_content]
	                <div>
	                  <label class="label" for="rcarcasa">${i18n['cione-module.templates.myshop.configurador-audio-component.labelColorCarcasa']}</label>
					  
					  
					  [#list listaAudioLab?keys as key]
		                  <div class="configurador-row-color mt-3">
		                    <label class="b2b-form-label-container">
		                    	[#if key?is_first]
		                    		<input type="radio" checked="checked" name="rcarcasa" class="rcarcasa" key="${key!}">
		                    	[#else]
		                    		<input type="radio" name="rcarcasa" class="rcarcasa" key="${key!}">
		                    	[/#if]
		                    	<span>${key!}</span>
		                    </label>
		                    [#assign colorClass = "configurador-color"]
		                    [#-- Si el color es blanco, hay que añadirle la clase 'white' para que pinte un borde azul al redondel blanco --]
		                    [#if key?has_content && ((listaAudioLab[key] == "#ffffff") || (listaAudioLab[key] == "#FFFFFF"))]
			                	[#assign colorClass = colorClass + " white"]
			                [/#if]
		                    <div class="${colorClass!"configurador-color"}" style="background-color:${listaAudioLab[key]!""}"></div>
		                  </div>
	                  [/#list]
	                </div>
                [/#if]
				[#assign listaPlato = infoVariant.colorPlatoAudioLab!]
				[#if listaPlato?? && listaPlato?has_content]
	                <div>
	                  <label class="label mt-5" for="rplato">${i18n['cione-module.templates.myshop.configurador-audio-component.labelColorPlato']}</label>
						
						[#list listaPlato?keys as key]
							<div class="configurador-row-color mt-3">
	                    		<label class="b2b-form-label-container">
	                      			[#if key?is_first]
			                    		<input type="radio" checked="checked" name="rplato" class="rplato" key="${key!}">
			                    	[#else]
			                    		<input type="radio" name="rplato" class="rplato" key="${key!}">
			                    	[/#if]
	                    			<span>${key!}</span>
						 		</label>
						 		[#assign colorClass = "configurador-color"]
						 		[#-- Si el color es blanco, hay que añadirle la clase 'white' para que pinte un borde azul al redondel blanco --]
						 		[#if key?has_content && ((listaPlato[key] == "#ffffff") || (listaPlato[key] == "#FFFFFF"))]
		                    		[#assign colorClass = colorClass + " white"]
		                   		[/#if]
		                   		<div class="${colorClass!"configurador-color"}" style="background-color:${listaPlato[key]!""}"></div>
		                   	</div>
	                  	[/#list]
	                </div>
	            [/#if]
                [#assign listaCodo = infoVariant.colorCodoAudioLab!]
                [#if listaCodo?? && listaCodo?has_content]
	                <div>
	                  <label class="label mt-5" for="rcodo">${i18n['cione-module.templates.myshop.configurador-audio-component.labelColorCodo']}</label>
					  
					  [#list listaCodo?keys as key]
		                  <div class="configurador-row-color mt-3">
		                    <label class="b2b-form-label-container">
		                      	[#if key?is_first]
		                    		<input type="radio" checked="checked" name="rcodo" class="rcodo" key="${key!}">
		                    	[#else]
		                    		<input type="radio" name="rcodo" class="rcodo" key="${key!}">
		                    	[/#if]
	                			<span>${key!}</span>
		                    </label>
		                    [#assign colorClass = "configurador-color"]
		                    [#-- Si el color es blanco, hay que añadirle la clase 'white' para que pinte un borde azul al redondel blanco --]
		                    [#if key?has_content && ((listaCodo[key] == "#ffffff") || (listaCodo[key] == "#FFFFFF"))]
		                    	[#assign colorClass = colorClass + " white"]
		                    [/#if]
		                    <div class="${colorClass!"configurador-color"}" style="background-color:${listaCodo[key]!""}"></div>
		                  </div>
	                  [/#list]
	                </div>
				[/#if]

              </div>
              
              <div class="col-lg-2 col-12 chart-buttons-copy">
                <div class="b2b-button-wrapper b2b-button-copy">
                  <button class="b2b-button b2b-button-filter configurador-copy-check" data-side="toRight" type="button">
                    <span class="copy-arrow-right" style="background-image: url('${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/arrow-down-gray.svg')"></span>
                  </button>
                </div>

                <div class="b2b-button-wrapper b2b-button-copy">
                  <button class="b2b-button b2b-button-filter configurador-copy-check" data-side="toLeft" type="button">
                    <span class="copy-arrow-left" style="background-image: url('${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/arrow-down-gray.svg')"></span>
                  </button>
                </div>
              </div>
              
              <div class="col-12 col-lg-5">
                <p class="ear-left-title">${i18n['cione-module.templates.myshop.configurador-audio-component.izquierdo']}</p>
	            [#assign listaAudioLab = infoVariant.colorAudifonoAudioLab!]
	            [#if listaAudioLab?? && listaAudioLab?has_content]
	            	<div>
	                  <label class="label" for="lcarcasa">${i18n['cione-module.templates.myshop.configurador-audio-component.labelColorCarcasa']}</label>
					  
					  [#list listaAudioLab?keys as key]
		                  <div class="configurador-row-color mt-3">
		                    <label class="b2b-form-label-container">
		                      	[#if key?is_first]
		                    		<input type="radio" checked="checked" name="lcarcasa" class="lcarcasa" key="${key!}">
		                    	[#else]
		                    		<input type="radio" name="lcarcasa" class="lcarcasa" key="${key!}">
		                    	[/#if]
		                    	<span>${key!}</span>
		                    </label>
		                    [#assign colorClass = "configurador-color"]
		                    [#-- Si el color es blanco, hay que añadirle la clase 'white' para que pinte un borde azul al redondel blanco --]
		                    [#if key?has_content && ((listaAudioLab[key] == "#ffffff") || (listaAudioLab[key] == "#FFFFFF"))]
		                    	[#assign colorClass = colorClass + " white"]
		                    [/#if]
		                    <div class="${colorClass!"configurador-color"}" style="background-color:${listaAudioLab[key]!""}"></div>
		                  </div>
	                  [/#list]
	                </div>
	            [/#if]
				[#assign listaPlato = infoVariant.colorPlatoAudioLab!]
				[#if listaPlato?? && listaPlato?has_content]
	                <div>
	                  <label class="label mt-5" for="lplato">${i18n['cione-module.templates.myshop.configurador-audio-component.labelColorPlato']}</label>
	                  
						[#list listaPlato?keys as key]
							<div class="configurador-row-color mt-3">
	                    		<label class="b2b-form-label-container">
	                      			[#if key?is_first]
			                    		<input type="radio" checked="checked" name="lplato" class="lplato" key="${key!}">
			                    	[#else]
			                    		<input type="radio" name="lplato" class="lplato" key="${key!}">
			                    	[/#if]
	                    			<span>${key!}</span>
						 		</label>
						 		[#assign colorClass = "configurador-color"]
						 		[#-- Si el color es blanco, hay que añadirle la clase 'white' para que pinte un borde azul al redondel blanco --]
						 		[#if key?has_content && ((listaPlato[key] == "#ffffff") || (listaPlato[key] == "#FFFFFF"))]
		                    		[#assign colorClass = colorClass + " white"]
		                   		[/#if]
		                   		<div class="${colorClass!"configurador-color"}" style="background-color:${listaPlato[key]!""}"></div>
		                   	</div>
	                  	[/#list]
	                </div>
	            [/#if]
	            
                [#assign listaCodo = infoVariant.colorCodoAudioLab!]
                [#if listaCodo?? && listaCodo?has_content]
	                <div>
	                  <label class="label mt-5" for="lcodo">${i18n['cione-module.templates.myshop.configurador-audio-component.labelColorCodo']}</label>
					  
					  [#list listaCodo?keys as key]
		                  <div class="configurador-row-color mt-3">
		                    <label class="b2b-form-label-container">
		                      	[#if key?is_first]
		                    		<input type="radio" checked="checked" name="lcodo" class="lcodo" key="${key!}">
		                    	[#else]
		                    		<input type="radio" name="lcodo" class="lcodo" key="${key!}">
		                    	[/#if]
	                			<span>${key!}</span>
		                    </label>
		                    [#assign colorClass = "configurador-color"]
		                    [#-- Si el color es blanco, hay que añadirle la clase 'white' para que pinte un borde azul al redondel blanco --]
		                    [#if key?has_content && ((listaCodo[key] == "#ffffff") || (listaCodo[key] == "#FFFFFF"))]
		                    	[#assign colorClass = colorClass + " white"]
		                    [/#if]
		                    <div class="${colorClass!"configurador-color"}" style="background-color:${listaCodo[key]!""}"></div>
		                  </div>
	                  [/#list]
	                </div>
				[/#if]
              </div>
            </div>
          </div>
        </div>
      </div>
    </div> [#-- Fin Color --]
    
    [#-- Impresiones --]
    <div class="accordion-group card">
      <div class="accordion-heading ">
        <a class="accordion-toggle card-header  collapsed" data-toggle="collapse" data-parent="#accordion2" href="#collapse7">
          <span class="title">${i18n['cione-module.templates.myshop.configurador-audio-component.labelConfiguradorImpresiones']}</span>
          <span class="accicon">
            <div class="selector-arrow"></div> 
          </span>
        </a>
      </div>
      <div id="collapse7" class="accordion-body collapse card-container">
        <div class="accordion-inner card-body ">

          <div class="container">
          
            <div class="row">
            	<div class="col-12 col-lg-6 mt-3">
	                <label class="label" for="selImpress">${i18n['cione-module.templates.myshop.configurador-audio-component.Seleccionar']}</label>
	                <select name="selImpress" id="selImpress">
	                  <option value="" selected></option>
	                  <option value="digitalOpt">${i18n['cione-module.templates.myshop.configurador-audio-component.impresion-digital-option']}</option>
	                  <option value="otScanOpt">${i18n['cione-module.templates.myshop.configurador-audio-component.otoScan']}</option>
	                  <option value="mailOpt">${i18n['cione-module.templates.myshop.configurador-audio-component.correo']}</option>
	                  <option value="scanOpt">${i18n['cione-module.templates.myshop.configurador-audio-component.escaneada']}</option>
	                </select>
            	</div>
            	<div class="col-12 col-lg-6 mt-3"></div>
            </div>
            
            <div class="row" id="numSerie" style="display:none">
            	<div class="col-12  col-lg-6 mt-3">
		          	<label class="label" for="rnumSerie">${i18n['cione-module.templates.myshop.configurador-audio-component.NumSerie']} <span class="color-right">${i18n['cione-module.templates.myshop.configurador-audio-component.dchoMinus']}</span></label>
		            <input type="text" id="rnumSerie" name="rnumSerie">
		        </div>
		        <div class="col-12 col-lg-6 mt-3">
		        	<label class="label" for="lnumSerie">${i18n['cione-module.templates.myshop.configurador-audio-component.NumSerie']} <span class="color-left">${i18n['cione-module.templates.myshop.configurador-audio-component.izdoMinus']}</span></label>
		            <input type="text" id="lnumSerie" name="lnumSerie">
		        </div>		        
		    </div>
		    
		    [#--  Impresiones escaneadas --]
		    <div class="row" id="scanImpress" style="display:none">	
		    	<div class="col-12 col-lg-6 mt-3">
	                <label class="label" >${i18n['cione-module.templates.myshop.configurador-audio-component.impresionEscaneada']} <span class="color-right">${i18n['cione-module.templates.myshop.configurador-audio-component.dchoMinus']}</span></label>
	                <div>
	                	[#assign claseLabel = "custom-file-label"]
                   		[#if cmsfn.language() == 'pt']
                    		[#assign claseLabel = "custom-file-label lang-pt"]
                    	[/#if ]	                	
	                	<label class="${claseLabel!"custom-file-label"}" for="myFileDrch"></label>
	                    <input type="file" class="custom-file-input" id="myFileDrch" 
	                    	onchange="loadFile('drch')" accept=".jpg,.jpeg,.png,.pdf,.stl" data-max-size="5242880">
	                	<span id="myFileDrch-error" class="error"></span>
	                	<input type="hidden" id="rpathscan" name="rpathscan" value=""/>
	                </div>
            	</div>
            	<div class="col-12 col-lg-6 mt-3">
	                <label class="label" >${i18n['cione-module.templates.myshop.configurador-audio-component.impresionEscaneada']} <span class="color-left">${i18n['cione-module.templates.myshop.configurador-audio-component.izdoMinus']}</span></label>
	                <div>                    
	                	<label class="${claseLabel!"custom-file-label"}" for="myFileIzq"></label>
	                	<input type="file" class="custom-file-input" name="myFileIzq" id="myFileIzq" 
	                		onchange="loadFile('izq')" accept=".jpg,.jpeg,.png,.pdf,.stl" data-max-size="5242880">
	             		<span id="myFileIzq-error" class="error"></span>
	             		<input type="hidden" id="lpathscan" name="lpathscan" value=""/>
	             	</div>
            	</div>
		    </div>
		    
            <div class="row" id="otoCloud" style="display:none">
              <div class="col-12  col-lg-6 mt-3">
                <label class="label" for="otoscan">${i18n['cione-module.templates.myshop.configurador-audio-component.otocloudID']}</label>
                <input type="text" id="otoscan" name="otoscan">
              </div>
            </div>
            
          </div>
        </div>
      </div>
    </div> [#-- Fin Impresiones --]
    
    [#-- Información adicional --]
    <div class="accordion-group card">
      <div class="accordion-heading ">
        <a class="accordion-toggle card-header  collapsed" data-toggle="collapse" data-parent="#accordion2" href="#collapse8">
          <span class="title">${i18n['cione-module.templates.myshop.configurador-audio-component.labelConfiguradorInformacionAdicional']}</span>
          <span class="accicon">
            <div class="selector-arrow"></div> 
          </span>
        </a>
      </div>
      
      <div id="collapse8" class="accordion-body collapse card-container">
        <div class="accordion-inner card-body ">
          <div class="container" name="tab-adicional" number="4">
            
            <div class="row">
            
              <div class="col-12 col-lg-5">
                <p class="ear-right-title">${i18n['cione-module.templates.myshop.configurador-audio-component.derecho']}</p>
                <div class="configurador-textarea-wrapper">
                  <label class="label" for="rinstrucciones">${i18n['cione-module.templates.myshop.configurador-audio-component.instruccionesEspeciales']}</label>
                  <textarea id="rinstrucciones" name="rinstrucciones" rows="10"></textarea>
                  <p class="configurador-textarea-footer">${i18n['cione-module.templates.myshop.configurador-audio-component.comentarioAdicional']}</p>
                </div>
              </div>
              
              <div class="col-lg-2 col-12 chart-buttons-copy">
                <div class="b2b-button-wrapper b2b-button-copy">
                  <button class="b2b-button b2b-button-filter configurador-copy-check" data-side="toRight" type="button">                   
                    <span class="copy-arrow-right" style="background-image: url('${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/arrow-down-gray.svg')"></span>
                  </button>
                </div>

                <div class="b2b-button-wrapper b2b-button-copy">
                  <button class="b2b-button b2b-button-filter configurador-copy-check" data-side="toLeft" type="button">
                    <span class="copy-arrow-left" style="background-image: url('${ctx.contextPath}/.resources/cione-theme/webresources/img/myshop/audio/arrow-down-gray.svg')"></span>
                  </button>
                </div>
              </div>
              
              <div class="col-12 col-lg-5">
                <p class="ear-left-title">${i18n['cione-module.templates.myshop.configurador-audio-component.izquierdo']}</p>
                <div class="configurador-textarea-wrapper">
                  <label class="label" for="linstrucciones">${i18n['cione-module.templates.myshop.configurador-audio-component.instruccionesEspeciales']}</label>
                  <textarea id="linstrucciones" name="linstrucciones" rows="10" value=""></textarea>
                  <p class="configurador-textarea-footer">${i18n['cione-module.templates.myshop.configurador-audio-component.comentarioAdicional']}</p>
                </div>
              </div>
              
            </div>

          </div>
        </div>
      </div>
    </div>  [#-- Fin Información adicional --]
    
    [#--  Complementos  --]
    <div class="accordion-group card">
      <div class="accordion-heading ">
        <a class="accordion-toggle card-header collapsed" data-toggle="collapse" data-parent="#accordion2"
          href="#collapse9">
          <span class="title">${i18n['cione-module.templates.myshop.configurador-audio-component.labelConfiguradorComplementos']}</span>
          <span class="accicon">
            <div class="selector-arrow"></div> 
          </span>

        </a>
      </div>
      <div id="collapse9" class="accordion-body collapse card-container">
        <div class="accordion-inner card-body ">
          <div class="container">

            <div class="row">
              
              	[@audifonos "auriculares" /]
              	[@audifonos "acopladores" /]
              	[@audifonos "cargadores" /]
              	[@audifonos "accesorios" /]
              	[@audifonos "tubosFinos" /]
              	[@audifonos "sujecionesDeportivas" /]
              	[@audifonos "filtros" /]
                [#assign garantias = infoVariant.getGarantia()!]
				[#if garantias?? && garantias?has_content]
					<div class="col-12 col-lg-6">
		                <label class="label" for="">${i18n['cione-module.templates.myshop.configurador-audio-component.superGarantia']}</label>
		                <select name="" id="garantia" autocomplete="off">
		                	<option value="" selected></option>
		            		[#list garantias?keys as key]
		                    	<option value="${key!""}">${garantias[key]!""}</option>
		                    [/#list]
		                </select>
		            </div>
	            [/#if]
            </div>
          </div>
        </div>
      </div>
    </div>
  </div> [#--  Fin Complementos  --]
  
[#-- MACROS --]
[#macro audifonos title]

	[#switch title]
		[#case "coloraudifono"]
			[#if infoVariant.getColoraudifonos()?has_content]
				<div class="col-12 col-lg-6">
		            <label class="label" for="coloraudifono">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.color-audifono']}</label>
		            <select name="" id="coloraudifono" autocomplete="off">
		                [#assign colores = infoVariant.getColoraudifonos()]
		        		[#list colores as val]
		                	<option value="${val!""}">${val!""}</option>
		                [/#list]
		            </select>
		    	</div>
			[/#if]
			[#break]
		[#case "colorcodo"]
			[#if infoVariant.getColorCodo()?has_content]
				<div class="col-12 col-lg-6">
		            <label class="label" for="colorcodo">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.color-codo']}</label>
		            <select name="" id="colorcodo" autocomplete="off">
		                [#assign colorescodo = infoVariant.getColorCodo()]
		        		[#list colorescodo as val]
		                	<option value="${val!""}">${val!""}</option>
		                [/#list]
		            </select>
		        </div>
			[/#if]
			[#break]
		[#case "auriculares"]
			[#if infoVariant.getAuriculares()?has_content]
				<div class="col-12 col-lg-6">
	                <label class="label" for="auriculares">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.auricular']}</label>
	                <select name="" id="auriculares" autocomplete="off">
	                    [#assign auriculares = infoVariant.getAuriculares()]
	            		<option value="" selected></option>
	            		[#list auriculares?keys as key]
	                    	<option value="${key!""}">${auriculares[key]!""}</option>
	                    [/#list]
	                </select>
	            </div>
            [/#if]
			[#break]
		[#case "acopladores"]
        	[#if infoVariant.getAcopladores()?has_content]
        		<div class="col-12 col-lg-6">
	                <label class="label" for="acopladores">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.acoplador']}</label>
	                <select name="" id="acopladores" autocomplete="off">
	                    [#assign acopladores = infoVariant.getAcopladores()]
	            		<option value="" selected></option>
	            		[#list acopladores?keys as key]
	                    	<option value="${key!""}">${acopladores[key]!""}</option>
	                    [/#list]
	                </select>
	            </div>
            [/#if]
			[#break]
		[#case "cargadores"]
			[#if infoVariant.getCargadores()?has_content]
				<div class="col-12 col-lg-6">
	                <label class="label" for="cargadores">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.cargador']}</label>
	                <select name="" id="cargadores" autocomplete="off">
	                    [#assign cargadores = infoVariant.getCargadores()]
	            		<option value="" selected></option>
	            		[#list cargadores?keys as key]
	                    	<option value="${key!""}">${cargadores[key]!""}</option>
	                    [/#list]
	                </select>
	            </div>
            [/#if]
			[#break]
		[#case "accesorios"]
			[#if infoVariant.getAccesorios()?has_content]
				<div class="col-12 col-lg-6">
					<label class="label" for="accesorios">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.inalambricos']}</label>
	                <select name="" id="accesorios" autocomplete="off">
		                [#assign accesorios = infoVariant.getAccesorios()]
		        		<option value="" selected></option>
		        		[#list accesorios?keys as key]
		                	<option value="${key!""}">${accesorios[key]!""}</option>
		                [/#list]
		            </select>
		        </div>
	        [/#if]
			[#break]
		[#case "tubosFinos"]
	        [#if infoVariant.getTubosFinos()?has_content]
	        	<div class="col-12 col-lg-6">
		        	<label class="label" for="tubosFinos">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.tfinos']}</label>
		            <select name="" id="tubosFinos" autocomplete="off">
		                [#assign tubosFinos = infoVariant.getTubosFinos()]
		        		<option value="" selected></option>
		        		[#list tubosFinos?keys as key]
		                	<option value="${key!""}">${tubosFinos[key]!""}</option>
		                [/#list]
		            </select>
		        </div>
	        [/#if]
			[#break]
		[#case "sujecionesDeportivas"]
			[#if infoVariant.getSujecionesDeportivas()?has_content]
				<div class="col-12 col-lg-6">
			        <label class="label" for="sujecionesDeportivas">${i18n['cione-module.templates.myshop.listado-productos-home-component.aud.sports']}</label>
			        <select name="" id="sujecionesDeportivas" autocomplete="off">
	                    [#assign sujecionesDeportivas = infoVariant.getSujecionesDeportivas()]
	            		<option value="" selected></option>
	            		[#list sujecionesDeportivas?keys as key]
	                    	<option value="${key!""}">${sujecionesDeportivas[key]!""}</option>
	                    [/#list]
	                </select>
	            </div>
			[/#if]
			[#break]
		[#case "filtros"]
			[#if infoVariant.getFiltros()?has_content]
				<div class="col-12 col-lg-6">
			        <label class="label" for="filtros">${i18n['cione-module.templates.myshop.listado-productos-home-component.filtros']}</label>
			        <select name="" id="filtros" autocomplete="off">
	                    [#assign filtros = infoVariant.getFiltros()]
	            		<option value="" selected></option>
	            		[#list filtros?keys as key]
	                    	<option value="${key!""}">${filtros[key]!""}</option>
	                    [/#list]
	                </select>
	            </div>
			[/#if]
			[#break]
	[/#switch]

[/#macro]

</section>

<script type="text/javascript">

function loadFile(fileside) {
	$("#loading").show();
	if (validateFile(fileside)) {
		var fileName;
		var files;
		var data = new FormData();
		//stop submit the form, we will post it manually.
	    event.preventDefault();
		if (fileside == "izq") {
			fileName = $("#myFileIzq").val().split("\\").pop();
	  		$("#myFileIzq").siblings(".custom-file-label").addClass("selected").html(fileName);
	  		// Get form
	    	//var form = $('#fileUploadForm')[0];
	    	files = $('#myFileIzq')[0].files[0];
	    	
	    	const reader = new FileReader();
	    	//var base64 = reader.readAsDataURL(files);
	    	//console.log(base64);
			// Create an FormData object 
			var extension = fileName.split(".").pop();
			
		    fileName = "-impresion_L." +extension;
		    
	    	data.append("nameFile", fileName);
	    	//data.append("baseFile", base64);
	    	data.append("myFile", files);
	    	data.append("refTaller", $("#refTaller").val());
	    	console.log(data);
		} else {
			fileName = $("#myFileDrch").val().split("\\").pop();
	  		$("#myFileDrch").siblings(".custom-file-label").addClass("selected").html(fileName);
	  		files = $('#myFileDrch')[0].files[0];
	    	
	    	var extension = fileName.split(".").pop();
	    	fileName = "-impresion_R." +extension;
	    	data.append("nameFile", fileName);
	    	data.append("myFile", files);
	    	data.append("refTaller", $("#refTaller").val());
	    	console.log(data);
		}
	
	 
	    $.ajax({
	        type: "POST",
	        enctype: 'multipart/form-data',
	        url: "${ctx.contextPath}/.rest/private/carrito/v1/loadFile",
	        data: data,
	        processData: false,
	        contentType: false,
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
	            console.log("SUCCESS : " + data.lpath);
	            if (fileside == "izq") {
	            	$("#lpathscan").val(data.path);
	            } else {
	            	$("#rpathscan").val(data.path);
	            }
	        },
	        error: function (e) {
	            console.log("ERROR : ", e);
	        }
	    });
  	}
  	$("#loading").hide();
}

function addtoCartEndPointAudioLab() {
	$(".product-button").attr("disabled", "disabled");
    $("#loading").show();
	filterDetail = getFormDataAudioLab($("#formDetalleProducto"));
	console.log(filterDetail);
	data = JSON.stringify(filterDetail);
	console.log(data);
	
	if (validateAudioForm()) {
	    $.ajax({
	        url : "${ctx.contextPath}/.rest/private/carrito/v1/carts-addItem",
	        type : "POST",
	        data : JSON.stringify(filterDetail),
	        contentType : 'application/json; charset=utf-8',
	        cache : false,
	        dataType : "json",
	        success : function(response) {
	        
	        	$("#fly-card").css("display","block");
				
				setTimeout(function(){ 
				$("#fly-card").css("display","none");
				}, 5000);
				
	            var KK = response;
	            //alert("producto añadido");
	            
	            console.log(KK);
	            
	            //actualiza popup carrito
	            refrescarPopupCarrito(response);
	            generatePDF(filterDetail);
	        },
	        error : function(response) {
	            alert("error"); 
	            $("#loading").hide();           
	        },
	        complete : function(response) {
	           $("#loading").hide();
	           $(".product-button").removeAttr("disabled"); 
	           removeitems();
	           
	        }
	    });
	} else {
		$("#loading").hide();
		$(".product-button").removeAttr("disabled"); 
	}
	
	return false;
}

function generatePDF(filterDetail) {
	$("#loading").show();
	$.ajax({
        url : "${ctx.contextPath}/.rest/private/carrito/v1/createPDF",
        type : "POST",
        data : JSON.stringify(filterDetail),
        contentType : 'application/json; charset=utf-8',
        cache : false,
        dataType : "json",
        success : function(response) {
        	//window.open('${ctx.contextPath}/.rest/private/carrito/v1/audiolabPDF?namefile='+response.namefile, '_blank');
        },
        error : function(response) {
            alert("error"); 
            $("#loading").hide();           
        },
        complete : function(response) {
           $("#loading").hide();
        }
    });
}

function getFormDataAudioLab($form) {

	var unindexed_array = $form.serializeArray();
	var indexed_array = {};
	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});
	
	
	//var side = $(".selector-img.selected").next().text();
	var side = $(".selector-img.selected").attr('id');
	indexed_array["side"] = side;
	if (side == "binaural") {
		indexed_array["cantidad"] = 2;
	} else {
		indexed_array["cantidad"] = 1;
	}
	if (($("#linstrucciones").val() != null) && ($("#linstrucciones").val() != "")) {
		var lins = $.trim($("#linstrucciones").val());
		indexed_array["linstrucciones"] = lins;
	}
	if (($("#rinstrucciones").val() != null) && ($("#rinstrucciones").val() != "")) {
		var rins = $.trim($("#rinstrucciones").val());
		indexed_array["rinstrucciones"] = rins;
	}
	
	indexed_array["ltelebobina"] = $("#ltelebobina").prop("checked");
	indexed_array["rtelebobina"] = $("#rtelebobina").prop("checked");
	
	indexed_array["lhilo"] = $("#lhilo").prop("checked");
	indexed_array["rhilo"] = $("#rhilo").prop("checked");
	indexed_array["tinnitus"] = $("#tinnitus").prop("checked");
	
	indexed_array["lpulsador"] = $("#lpulsador").prop("checked");
	indexed_array["rpulsador"] = $("#rpulsador").prop("checked");
	
	indexed_array["lvolumen"] = $("#lvolumen").prop("checked");
	indexed_array["rvolumen"] = $("#rvolumen").prop("checked");
	
	$.map($(".rcarcasa"), function(element,index){
  		if(element.checked) {
  			indexed_array["rcarcasa"] = element.getAttribute('key');
      		console.log(element.getAttribute('key'));
      	}
	});
	
	$.map($(".lcarcasa"), function(element,index){
  		if(element.checked) {
  			indexed_array["lcarcasa"] = element.getAttribute('key');
      		console.log(element.getAttribute('key'));
      	}
	});
	
	$.map($(".rplato"), function(element,index){
			
  		if(element.checked) {
  			indexed_array["rplato"] = element.getAttribute('key');
      		console.log(element.getAttribute('key'));
      	}
	});
	
	$.map($(".lplato"), function(element,index){
			
  		if(element.checked) {
  			indexed_array["lplato"] = element.getAttribute('key');
      		console.log(element.getAttribute('key'));
      	}
	});
	
	$.map($(".rcodo"), function(element,index){
			
  		if(element.checked) {
  			indexed_array["rcodo"] = element.getAttribute('key');
      		console.log(element.getAttribute('key'));
      	}
	});
	
	$.map($(".lcodo"), function(element,index){
			
  		if(element.checked) {
  			indexed_array["lcodo"] = element.getAttribute('key');
      		console.log(element.getAttribute('key'));
      	}
	});
	
	var audiogramR = getAudiogramValues('ear-right', '');
	var audiogramL = getAudiogramValues('', 'ear-left');
	
	indexed_array["raudiogram"] = audiogramR;
	indexed_array["laudiogram"] = audiogramL;
	
	if ($( "#auriculares option:checked" ).val() != null && $( "#auriculares option:checked" ).val() != ""){
		indexed_array["auricular"] = $( "#auriculares option:checked" ).val();
		indexed_array["auricularName"] = $( "#auriculares option:checked" ).text();
	}
	if ($( "#acopladores option:checked" ).val() != null && $( "#acopladores option:checked" ).val() != ""){
		indexed_array["acoplador"] = $( "#acopladores option:checked" ).val();
		indexed_array["acopladorName"] = $( "#acopladores option:checked" ).text();
	}
	if ($( "#cargadores option:checked" ).val() != null && $( "#cargadores option:checked" ).val() != ""){
		indexed_array["cargador"] = $( "#cargadores option:checked" ).val();
		indexed_array["cargadorName"] = $( "#cargadores option:checked" ).text();
	}
	
	if ($( "#accesorios option:checked" ).val() != null && $( "#accesorios option:checked" ).val() != ""){
		indexed_array["accesorioinalambrico"] = $( "#accesorios option:checked" ).val();
		indexed_array["accesorioinalambricoName"] = $( "#accesorios option:checked" ).text();
	}
	
	if ($( "#tubosFinos option:checked" ).val() != null && $( "#tubosFinos option:checked" ).val() != ""){
		indexed_array["tubosFinos"] = $( "#tubosFinos option:checked" ).val();
		indexed_array["tubosFinosName"] = $( "#tubosFinos option:checked" ).text();
	}
	
	if ($( "#sujecionesDeportivas option:checked" ).val() != null && $( "#sujecionesDeportivas option:checked" ).val() != ""){
		indexed_array["sujecionesDeportivas"] = $( "#sujecionesDeportivas option:checked" ).val();
		indexed_array["sujecionesDeportivasName"] = $( "#sujecionesDeportivas option:checked" ).text();
	}
	
	if ($( "#filtros option:checked" ).val() != null && $( "#filtros option:checked" ).val() != ""){
		indexed_array["filtros"] = $( "#filtros option:checked" ).val();
		indexed_array["filtrosName"] = $( "#filtros option:checked" ).text();
	}
	
	if ($( "#garantia option:checked" ).val() != null && $( "#garantia option:checked" ).val() != "") {
		indexed_array["garantia"] = $( "#garantia option:checked" ).val();
		indexed_array["garantiaName"] = $( "#garantia option:checked" ).text();
	}
		
	if ($("#selImpress").val() == "mailOpt") {
		indexed_array["envioCorreoOrdinario"] = true;
		if (side == "binaural") {
			indexed_array["renviocorreo"] = true;
			indexed_array["lenviocorreo"] = true;
		} else if (side == "derecho") {
			indexed_array["renviocorreo"] = true;
		} else if (side == "izquierdo") {
			indexed_array["lenviocorreo"] = true;
		}
	} 
	
	indexed_array["refTaller"] = $("#refTaller").val();
	
	return indexed_array;
}

function validateAudioForm() {
	var venting = "${infoVariant.venting?string('yes', 'no')}";
	var result = true;
	var side = $(".selector-img.selected").attr('id');
	var mapAcordeon = new Map();
	if (side == "binaural") {
		if ($("#ltipoventing").val() == ''){
			$("#ltipoventing").addClass("validation-error");
			result = false;
			mapAcordeon.set("4", "copt");
		} else {
			$("#ltipoventing").removeClass("validation-error");
		}
		
		if ($("#rtipoventing").val() == ''){
			$("#rtipoventing").addClass("validation-error");
			result = false;
			mapAcordeon.set("4", "copt");
		} else {
			$("#rtipoventing").removeClass("validation-error");
		}
		
		if ($("#rlon").val() == ''){
			$("#rlon").addClass("validation-error");
			result = false;
			mapAcordeon.set("4", "copt");
		} else {
			$("#rlon").removeClass("validation-error");
		}
		
		if ($("#llon").val() == ''){
			$("#llon").addClass("validation-error");
			result = false;
			mapAcordeon.set("4", "copt");
		} else {
			$("#llon").removeClass("validation-error");
		}
		
		if ($("#rlon").val() == ''){
			$("#rlon").addClass("validation-error");
			result = false;
			mapAcordeon.set("4", "copt");
		} else {
			$("#rlon").removeClass("validation-error");
		}
		if ($("#lfiltro").val() == ''){
			$("#lfiltro").addClass("validation-error");
			result = false;
			mapAcordeon.set("4", "copt");
		} else {
			$("#lfiltro").removeClass("validation-error");
		}
		if ($("#rfiltro").val() == ''){
			$("#rfiltro").addClass("validation-error");
			result = false;
			mapAcordeon.set("4", "copt");
		} else {
			$("#rfiltro").removeClass("validation-error");
		}
		
		if (!validateAudiogramValues('ear-right', '')) {
			result = false;
			mapAcordeon.set("1", "audiogram");
		}
		if (!validateAudiogramValues('', 'ear-left')) {
			result = false;
			mapAcordeon.set("1", "audiogram");
		}
		
		if ($("#lpotencia").val() == ''){
			$("#lpotencia").addClass("validation-error");
			result = false;
			mapAcordeon.set("2", "cpotencia");
		} else {
			$("#lpotencia").removeClass("validation-error");
		}
		if ($("#rpotencia").val() == ''){
			$("#rpotencia").addClass("validation-error");
			result = false;
			mapAcordeon.set("2", "cpotencia");
		} else {
			$("#rpotencia").removeClass("validation-error");
		}
		
	} 
	if (side == "izquierdo") {
		$("#rtipoventing").removeClass("validation-error");
		if ($("#ltipoventing").val() == ''){
			$("#ltipoventing").addClass("validation-error");
			mapAcordeon.set("4", "copt");
			result = false;
		} else {
			$("#ltipoventing").removeClass("validation-error");
		}
		$("#rlon").removeClass("validation-error");
		if ($("#llon").val() == ''){
			$("#llon").addClass("validation-error");
			mapAcordeon.set("4", "copt");
			result = false;
		} else {
			$("#llon").removeClass("validation-error");
		}
		$("#rfiltro").removeClass("validation-error");
		if ($("#lfiltro").val() == ''){
			$("#lfiltro").addClass("validation-error");
			mapAcordeon.set("4", "copt");
			result = false;
		} else {
			$("#lfiltro").removeClass("validation-error");
		}
		
	    $("#validateAudioR").text("");
		if (!validateAudiogramValues('', 'ear-left')) {
			result = false;
			mapAcordeon.set("1", "audiogram");
		}
		$("#rpotencia").removeClass("validation-error");
		if ($("#lpotencia").val() == ''){
			$("#lpotencia").addClass("validation-error");
			result = false;
			mapAcordeon.set("2", "cpotencia");
		} else {
			$("#lpotencia").removeClass("validation-error");
		}
	} 
	
	if (side == "derecho") {
		$("#ltipoventing").removeClass("validation-error");
		if ($("#rtipoventing").val() == ''){
			$("#rtipoventing").addClass("validation-error");
			mapAcordeon.set("4", "copt");
			result = false;
		} else {
			$("#rtipoventing").removeClass("validation-error");
		}
		$("#llon").removeClass("validation-error");
		if ($("#rlon").val() == ''){
			$("#rlon").addClass("validation-error");
			mapAcordeon.set("4", "copt");
			result = false;
		} else {
			$("#rlon").removeClass("validation-error");
		}
		$("#lfiltro").removeClass("validation-error");
		if ($("#rfiltro").val() == ''){
			$("#rfiltro").addClass("validation-error");
			mapAcordeon.set("4", "copt");
			result = false;
		} else {
			$("#rfiltro").removeClass("validation-error");
		}
	    $("#validateAudioL").text("");
		if (!validateAudiogramValues('ear-right', '')) {
			result = false;
			mapAcordeon.set("1", "audiogram");
		}
		$("#lpotencia").removeClass("validation-error");
		if ($("#rpotencia").val() == ''){
			$("#rpotencia").addClass("validation-error");
			result = false;
			mapAcordeon.set("2", "cpotencia");
		} else {
			$("#rpotencia").removeClass("validation-error");
		}
	}

	if ($("#formato").val() == ''){
		$("#formato").addClass("validation-error");
		result = false;
		mapAcordeon.set("0", "dbasic");
	} else {
		$("#formato").removeClass("validation-error");
	}
	
	if (!result) {
		if (($("#validate-error").text() == "undefined") || ($("#validate-error").text() == "")) {
			var html = "<div id='validate-error' style='color: red;'>*Se deben completar todos los campos</div>";
			$(".product-button-wrapper").parent().before().append(html);
		}
		
		for (let value of mapAcordeon.values()){
			$('.accordion-toggle.card-header.'+value+'').removeClass("collapsed").addClass("complete");
			$('.accordion-body.'+value+'').addClass("show");
		}
		
	} else {
		$( "#validate-error" ).remove();
	}
	
	return result;
}

    //lo damos por bueno en cuanto uno de los valores sea distinto de 0
    function validateAudiogramValues(nameRight, nameLeft){
    	var result = false;
    	var inputRight = document.getElementsByName(nameRight);
    	var inputLeft = document.getElementsByName(nameLeft);
    	
    	if (nameRight != '') {
	    	for(var i = 0; i < inputRight.length; i++){
	    		if (inputRight[i].value != "0") {
	    			result = true;
	    		}
	    	}
	    	if (!result) {
	    		$("#validateAudioR").text("*Debe completar los datos del audiograma");
	    	} else {
	    		$("#validateAudioR").text("");
	    	}
	    }
	     
    	if (nameLeft != '') {
	    	for(var i = 0; i < inputLeft.length; i++){
	    		if (inputLeft[i].value != "0") {
	    			result = true;
	    		}
	    	}
	    	if (!result) {
	    		$("#validateAudioL").text("*Debe completar los datos del audiograma");
	    	} else {
	    		$("#validateAudioL").text("");
	    	}
	    }
	    
    	return result;
    }

function validateFile(fileside) {
	var result = false;
	if (fileside == "izq") {
		//cambiar por lo comentado cuando subamos a la vez obviar el if (los subiremos a la vez)
		//$(".custom-file-input").each(function(index,input){
		$("#myFileIzq").each(function(index,input){
			KK = input
			if(input.files.length>0){
				if(input.dataset.maxSize && input.files[0].size > parseInt(input.dataset.maxSize,10)){
					$("#"+ input.id + "-error").html("El tama�o del fichero supera el m�ximo indicado");
					result = false;
				} else {
					$("#"+ input.id + "-error").html("");
					result = true;
				}
			}
		});
		
	} else {
		//cambiar por lo comentado cuando subamos a la vez
		//$(".custom-file-input").each(function(index,input){
		$("#myFileDrch").each(function(index,input){
			KK = input
			if(input.files.length>0){
				if(input.dataset.maxSize && input.files[0].size > parseInt(input.dataset.maxSize,10)){
					$("#"+ input.id + "-error").html("El tama�o del fichero supera el m�ximo indicado");
					result = false;
				} else {
					$("#"+ input.id + "-error").html("");
					result = true;
				}
			}
		});
	}

	
	return result;
}

function removeitems() {
	$("#lpathscan").val('');
	$("#rpathscan").val('');
	
	$(".selector-img").removeClass("selected");
	$("#binaural").addClass("selected");
	$("#referencia").val('');
	$("#gabinete").val('');
	
	resetChart('ear-right');
	resetChart('ear-left');
	
	$("#rpotencia").val('');
	$("#lpotencia").val('');
	
	$("#formato").val('');
	
	$("#numSerie").hide();
	$("#scanImpress").hide();
	$("#otoCloud").hide();
	
	$('#auriculares').prop('selectedIndex',0);
    $('#acopladores').prop('selectedIndex',0);
    $('#cargadores').prop('selectedIndex',0);
    $('#accesorios').prop('selectedIndex',0);
    $('#tubosFinos').prop('selectedIndex',0);
    $('#sujecionesDeportivas').prop('selectedIndex',0);
    $('#garantia').prop('selectedIndex',0);
    $('#radio2deposito').prop('checked', true);
    
    $('#formDetalleProducto')[0].reset();
    var refTaller = "";
    
    $.ajax({
        url : "${ctx.contextPath}/.rest/private/carrito/v1/refPack",
        type : "GET",
        contentType : 'application/json; charset=utf-8',
        cache : false,
        async: false,
        dataType : "json",
        success : function(response) {
        	refTaller = response["refPack"];
        },
        error : function(response) {
        	console.log("Error consultando el codigo autogenerado de refTaller.");
    	}
    });
    $('#refTaller').val(refTaller);
    $("#myFileIzq").siblings(".custom-file-label").removeClass("selected").html('');
	$("#myFileDrch").siblings(".custom-file-label").removeClass("selected").html('');
    $('.accordion-toggle.card-header').addClass("collapsed").removeClass("complete");
	$('.accordion-body').removeClass("show");
	$( "#validate-error" ).remove();
}

function removeitemsIzq() {
	resetChart('ear-left');
	$("#lpotencia").val('');
	$("#lpotencia").removeClass("validation-error");
	$("#lpathscan").val('');
	$("#llon").removeClass("validation-error");
	$("#llon").val('');
	$("#ltelebobina").prop("checked", false);
	$("#lfiltro").removeClass("validation-error");
	$("#lfiltro").val('');
	$("#lpulsador").prop("checked", false);
	$("#lvolumen").prop("checked", false);
	$("#lhilo").prop("checked", false);
	$("#ltipoventing").removeClass("validation-error");
	$("#ltipoventing").val('');
	$("#lmodventing").val('');
	$("#lnumSerie").val('');
	$("#linstrucciones").val('');
	$("#validateAudioL").text("");
    $("#myFileIzq").siblings(".custom-file-label").removeClass("selected").html('');
}

function removeitemsDrch() {
	resetChart('ear-right');
	$("#rpotencia").val('');
	$("#rpotencia").removeClass("validation-error");
	$("#rpathscan").val('');
	$("#rlon").removeClass("validation-error");
	$("#rlon").val('');
	$("#rtelebobina").prop("checked", false);
	$("#rfiltro").removeClass("validation-error");
	$("#rfiltro").val('');
	$("#rpulsador").prop("checked", false);
	$("#rvolumen").prop("checked", false);
	$("#rhilo").prop("checked", false);
	$("#rtipoventing").removeClass("validation-error");
	$("#rtipoventing").val('');
	$("#rmodventing").val('');
	$("#rnumSerie").val('');
	$("#rinstrucciones").val('');
	$("#validateAudioR").text("");
    $("#myFileDrch").siblings(".custom-file-label").removeClass("selected").html('');
}

function copyIfBinaural(elemento, origen, destino) {
	var side = $(".selector-img.selected").attr('id');
	if (side == "binaural") {
		$("#" + destino + elemento).val( $("#" + origen + elemento ).val());
	}
}

$(document).ready(function(){

	$("#selImpress").change(function() {
		if ($( "#selImpress option:checked" ).val() != null && $( "#selImpress option:checked" ).val() != ""){
			var optionSelected = $( "#selImpress option:checked" ).val();
			
			switch (optionSelected) {
				case 'digitalOpt':
					$("#numSerie").show();
					$("#scanImpress").hide();
					$("#otoCloud").hide();
					$("#lpathscan").val('');
					$("#rpathscan").val('');
					$("#otoscan").val('');
					$("#myFileIzq").val('');
					$("#myFileDrch").val('');
					$("#myFileIzq").siblings(".custom-file-label").removeClass("selected").html('');
					$("#myFileDrch").siblings(".custom-file-label").removeClass("selected").html('');
			    	break;
				case 'otScanOpt':
			    	$("#numSerie").hide();
					$("#scanImpress").hide();
					$("#otoCloud").show();
					$("#lpathscan").val('');
					$("#rpathscan").val('');
					$("#otoscan").val('');
					$("#lnumSerie").val('');
					$("#rnumSerie").val('');
					$("#myFileIzq").val('');
					$("#myFileDrch").val('');
					$("#myFileIzq").siblings(".custom-file-label").removeClass("selected").html('');
					$("#myFileDrch").siblings(".custom-file-label").removeClass("selected").html('');
			    	break;
				case 'mailOpt':
			    	$("#numSerie").hide();
					$("#scanImpress").hide();
					$("#otoCloud").hide();
					$("#lpathscan").val('');
					$("#rpathscan").val('');
					$("#otoscan").val('');
					$("#lnumSerie").val('');
					$("#rnumSerie").val('');
					$("#myFileIzq").val('');
					$("#myFileDrch").val('');
					$("#myFileIzq").siblings(".custom-file-label").removeClass("selected").html('');
					$("#myFileDrch").siblings(".custom-file-label").removeClass("selected").html('');
			    	break;
				case 'scanOpt':
			    	$("#numSerie").hide();
					$("#scanImpress").show();
					$("#otoCloud").hide();
					$("#otoscan").val('');
					$("#lnumSerie").val('');
					$("#rnumSerie").val('');
			    break;
			  default:
			    $("#numSerie").hide();
				$("#scanImpress").hide();
				$("#otoCloud").hide();
			}

		} else {
			$("#numSerie").hide();
			$("#scanImpress").hide();
			$("#otoCloud").hide();
		}		
	
	});
	
	$(".accordion-group.card .accordion-toggle.card-header").on("click", function(){
		$(this).addClass("complete");
	});

});


function pruebaPDF() {
	var data = {
		tipo: "impresion_L",
		codSocio: "0002601",
		refTaller: "CVDI70002601",
		key: "LuxYjFQqWyXdVKfArI+e8Q=="
	};
	
	var refTaller = "22AXR0200100";
	var key = "";
	$.ajax({
        url : "${ctx.contextPath}/.rest/private/carrito/v1/encript?refTaller="+refTaller,
        type : "GET",
        contentType : 'application/json; charset=utf-8',
        cache : false,
        async: false,
        dataType : "json",
        success : function(response) {
        	key = response["key"];
        },
        error : function(response) {
        	console.log("Error consultando el codigo autogenerado de refTaller.");
    	}
    });
	
	var key_encode = encodeURIComponent("G8QlSIQDOdUM2uYUSGNuaA==");
	var params = "?nameFile=0002601-PFDU00002601-impresion_R.png&refTaller=PFDU00002601&key=" + key_encode;
	window.open('${ctx.contextPath}/.rest/public/audiolabPDFGET' + params, '_blank');
}


</script>