function addToUrl(url, param) {
	var separator = url.indexOf('?') < 0 ? '?' : '&';
	return url + separator + param;
}

function getFormData($form) {
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};
	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});
	return indexed_array;
}

function initApp() {	
	setTimeout(function(){renderSubmenu();}, 0);
	if (typeof initPage === "function") {
		initPage();		
	}	
}

$(document).ready(function() {
	initApp();
	
	$("li.tab-link").click(function() {
		retailerZebra();
	});

//	$("li#tabHs").click(function() {
//		retailerZebra();
//	});
	
//	$("li#tabPedidosPn").click(function() {
//		retailerZebra();
//	});

	$('.inputfecha').datepicker({
		dateFormat : "dd-mm-yy"
	});

	$('.inputfecha').datepicker("option", "dateFormat", "dd-mm-yy");
	
	
	$('.inputfecha').mask('00-00-0000');

//	$('.inputfecha').click(function(e) {
//		e.preventDefault();
//		$('.inputfecha').datepicker();
//	});
	
	
	$('.formSearch input').click(function(e) {
		e.preventDefault();
		clearErrorMessages();
	});

	$('#formPedidos input').click(function(e) {
		e.preventDefault();
		clearErrorMessages();
	});

	$('#formPedidosHis input').click(function(e) {
		e.preventDefault();
		clearErrorMessages();
	});
	
	$('#formAbonos input').click(function(e) {
		e.preventDefault();
		clearErrorMessages();
	});

	$('#formAbonosHistorico input').click(function(e) {
		e.preventDefault();
		clearErrorMessages();
	});
	
	$('#formEnvios input').click(function(e) {
		e.preventDefault();
		clearErrorMessages();
	});
	
	$('#formAlbaranes input').click(function(e) {
		e.preventDefault();
		clearErrorMessages();
	});
	
	$('#tablaModal').on('hidden.bs.modal', function () {
		$(this).find(".modal-body").html("");
	});	
	
	retailerZebra();

});


function templateErrorField(txt){
	return "<span class='error errorField'>" + txt + "</span>";	
}

function isValidDate(date,format){
	//console.log(date + "," + format);
	if(!format){
		format = "DD-MM-YYYY";
	}
	return moment(date,format).isValid();
}

function checkEnteredDates(fechaIni,fechaIniFormat,fechaFin,fechaFinFormat){
	//console.log(fechaIni + "," + fechaIniFormat + "," + fechaFin + "," + fechaFinFormat);
	if(!fechaIniFormat){
		fechaIniFormat = "DD-MM-YYYY";
	}
	if(!fechaFinFormat){
		fechaFinFormat = "DD-MM-YYYY";
	}	
	var f1 = moment(fechaIni,fechaIniFormat);
	var f2 = moment(fechaFin,fechaFinFormat);
	return f1<=f2?true:false;
}

function validateForm(oForm) {	
	var oFechaIni = oForm.elements["fechaIni"];
	var fecIni = oFechaIni.value;
	var fecIniFormat = $(oFechaIni).data("date-format") || null; 
	var oFechaFin = oForm.elements["fechaFin"];
	var fecFin = oFechaFin.value;
	var fecFinFormat = $(oFechaFin).data("date-format") || null;

	var valid = true;
	if (fecIni !== null && fecIni !== "" && isValidDate(fecIni,fecIniFormat) == false) {		
		$(oFechaIni).after(templateErrorField(i18n_errorFechaIncorrecta));
		valid = false;
	}

	if (fecFin !== null && fecFin !== "" && isValidDate(fecFin,fecFinFormat) == false) {
		$(oFechaFin).after(templateErrorField(i18n_errorFechaIncorrecta));
		valid = false;
	}

	// Validate end date to find out if it is prior to start date
	if (fecIni !== null && fecIni !== "" && fecFin !== null && fecFin !== ""
			&& checkEnteredDates(fecIni,fecIniFormat,fecFin,fecFinFormat) == false) {		
		$(oFechaIni).after(templateErrorField(i18n_errorFechaDesdeHasta));
		valid = false;
	}

	return valid;
}



/*
function isValidDate(dateStr) {

	var datePat = /^([0-2][0-9]|(3)[0-1])(-)(((0)[0-9])|((1)[0-2]))(-)\d{4}$/;

	var matchArray = dateStr.match(datePat);
	if (matchArray == null) {
		//alert("La fecha debe estar en el formato dd-mm-yyyy");
		return false;
	}

	var splitArray = dateStr.split("-");

	day = splitArray[0];
	month = splitArray[1];
	year = splitArray[2];

	if (month < 1 || month > 12) {
		//alert("El mes debe estar entre 1 y 12");
		return false;
	}

	if (day < 1 || day > 31) {
		//alert("El día debe estar entre 1 y 31");
		return false;
	}

	if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
		//alert("El mes "+month+" no tiene 31 días!")
		return false;
	}

	if (month == 2) { // check for february 29th
		var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
		if (day > 29 || (day == 29 && !isleap)) {
			//alert("Febrero del año " + year + " no tiene " + day + " días!");
			return false;
		}
	}

	return true; // date is valid

}*/

/*
function checkEnteredDates(fecIni, fecFin) {

	if (fecIni.localeCompare(fecFin) !== 0) {
		var stryear1 = fecIni.substring(6);
		var strmth1 = fecIni.substring(5, 3);
		var strday1 = fecIni.substring(0, 2);
		var date1 = new Date(stryear1, strmth1, strday1);

		var stryear2 = fecFin.substring(6);
		var strmth2 = fecFin.substring(5, 3);
		var strday2 = fecFin.substring(0, 2);
		var date2 = new Date(stryear2, strmth2, strday2);
		
		var datediffval = (date2 - date1)/1000/60/60/24;

		if (datediffval < 0) {
			//alert("Fecha desde debe ser menor que la fecha hasta");
			return false;
		}
	}

	return true;
}*/

function clearErrorMessages() {

	if ($(".error").length > 0) {
		//$("span.error").each(function() {
		$("input.error").each(function() {
			//$(this).empty();
			$(this).removeClass("error");
		});
		
		$("span.error").each(function() {
			$(this).empty();
			//$(this).removeClass("error");
		});
		
	}
	
	if($("#trNoRecords").length){
		$('#trNoRecords').remove();
	}
}

if(typeof String.prototype.trim !== 'function') {
  String.prototype.trim = function() {
    return this.replace(/^\s+|\s+$/g, ''); 
  }
}


$.datepicker.regional['es'] = {
		closeText: 'Cerrar',
		prevText: '< Ant',
		nextText: 'Sig >',
		currentText: 'Hoy',
		monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
		monthNamesShort: ['Ene','Feb','Mar','Abr', 'May','Jun','Jul','Ago','Sep', 'Oct','Nov','Dic'],
		dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
		dayNamesShort: ['Dom','Lun','Mar','Mié','Juv','Vie','Sáb'],
		dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','Sá'],
		weekHeader: 'Sm',
		dateFormat: 'dd/mm/yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''
};

$.datepicker.regional['pt'] = {
		closeText: 'Cerrar',
		prevText: '< Ant',
		nextText: 'Sig >',
		currentText: 'Hoy',
		monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
		monthNamesShort: ['Ene','Feb','Mar','Abr', 'May','Jun','Jul','Ago','Sep', 'Oct','Nov','Dic'],
		dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
		dayNamesShort: ['Dom','Lun','Mar','Mié','Juv','Vie','Sáb'],
		dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','Sá'],
		weekHeader: 'Sm',
		dateFormat: 'dd/mm/yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''
};


$.datepicker.setDefaults($.datepicker.regional[LANG]);


function templateNoRecordsFoundForTable(colspan) {
	var html = "";
	html += "<tr id='trNoRecords'>";
	html += "<td class='text-center' colspan='"+ colspan +"'>";
	html += "<span class='text-muted'>" + i18n_noRecordsFound + "</span>";
	html += "</td>";
	html += "</tr>";
	return html;
}

function exportToExcel(tableId){
	
	var table = tableId || "tableExport";
	var url = PATH_API + "/private/export/v1/xls";
	$("#form-export-data")[0].action=url;						
	$("#export-data").val(JSON.stringify(getTableToExport(table)));
	$("#form-export-data")[0].submit();		
}

function exportToExcelShipping(tableId){
	
	var table = tableId || "tableExport";
	var url = PATH_API + "/private/export/v1/xls";
	$("#form-export-data")[0].action=url;
	var expdata = getTableToExport(table);
	let header = ["Art\u00EDculo", "Unidades", "Importe Bruto", "Descuento","Importe Venta","Referencia Web","Referencia Taller","Albar\u00E1n","Fecha"];
	expdata.pop();
	expdata.unshift(header);
	var jsondata = JSON.stringify(expdata);
	$("#export-data").val(jsondata);
	$("#form-export-data")[0].submit();			
}

function getTableToExport(idTable){
	var result = [];	
	var filas = $("#" + idTable + " tr");
			
	for(i=0; i< filas.length; i++){
		//console.log("fila > " +  i);
		if (filas[i].className == "subtabla"){
			//
		}else{
			var columnas = filas[i].children;			
			var resultFilas = [];
			for(j=0; j<columnas.length; j++){
				//console.log("-- columna >" + j);
				var columna = columnas[j];				
				//console.log(columna);
				if(columna.nodeName === "TH" || columna.nodeName === "TD"){
					resultFilas.push(columna.innerText);
				}
			}
			result.push(resultFilas);	
		}						
	}
	return result;
}

function renderSubmenu(){	
	var activeElement = $(".cmp-menuacordeon .active");
	var parent = activeElement.parent(); 
	var i= 0;
	var enc = false;
	while(i<50 && enc == false){
		if((parent).hasClass("contenedor-menu")){
			enc = true;
		}else if(parent.hasClass("isParent")){					
			parent.trigger("click");
		}
		parent = parent.parent();
		i++;
	}	
}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

//funcion heredada que en principio no hace nada
function verifyTest(){
	
}

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}
