<style>
   .ui-datepicker-calendar {
       display: none;
   }
</style>

<section class="cmp-albaranes mobile-wrapper">
    <form id="formSurveys" name="formAlbaranes" method="post">
    	
    	<ul class="accordion-mobile">
            <li><a class="toggle" href="javascript:void(0);">
                    <div class="title">${i18n['cione-module.templates.components.quality-surveys-admin.title']}<i class="fa fa-chevron-right"> </i></div>
                </a>
                <ul class="inner show" style="display: block;">
                    <li>
                     </li>
                </ul>
            </li>
        </ul>
        <div class="panel-filter">
            <div class="filter">
                <label>${i18n['cione-module.templates.components.quality-surveys-admin.socio']}</label>
                <input class="" id="" name="socio" type="text" value="" autocomplete="off">
            </div>
            <div class="filter">
                <label>${i18n['cione-module.templates.components.quality-surveys-admin.fecha-desde']}</label>
                <input id="fechaIni" class="inputfechaMY" data-date-format="MM-YYYY" type="text" name="fechaDesde" autocomplete="off">                
            </div>
            <div class="filter">
                <label>${i18n['cione-module.templates.components.quality-surveys-admin.fecha-hasta']}</label>
                <input id="fechaFin" class="inputfechaMY" data-date-format="MM-YYYY" type="text" name="fechaHasta" autocomplete="off">                
            </div>
            <div class="filter">
                <label>${i18n['cione-module.templates.components.quality-surveys-admin.puntuacion']}</label>
                <input class="" id="" name="puntuacion" type="text" value="" autocomplete="off">
            </div>                        
        </div>
        <div class="panelbuttons">
            <button id="albaranes-component-search-btn"
                    class="btn-blue icon-search" type="submit"
                    onclick="search(); return false">${i18n['cione-module.templates.components.albaranes-component.btn-buscar']}</button>
        </div>
        <!-- <div class="panelbuttons">
            <button id="albaranes-component-unpublish-btn"
                    class="btn-blue icon-search" type="submit"
                    onclick="despublicar(); return false">despublicar</button>
        </div>        
        <div class="panelbuttons">
            <button id="albaranes-component-delete-btn"
                    class="btn-blue icon-search" type="submit"
                    onclick="borrar(); return false">borrar</button>
        </div> -->
    </form>
    <div class="panel-table">
    	<div class="leyenda">
    		${i18n['cione-module.templates.components.quality-surveys-admin.leyenda']}
    	</div>
        <table id="tableQualitySurvey" class="table">
            <thead>
                <tr>
                    <th>${i18n['cione-module.templates.components.quality-surveys-admin.socio']}</th>
                    <th>${i18n['cione-module.templates.components.quality-surveys-admin.puntuacion']}</th>
                    <th>${i18n['cione-module.templates.components.quality-surveys-admin.fecha']}</th>                    
                    <th class="hide-mobile">${i18n['cione-module.templates.components.quality-surveys-admin.comentario']}</th>                    
                </tr>
            </thead>
            <tbody id="table-data"></tbody>
        </table>
        <div class="foot" id="foot"></div>
    </div>
    
    <div>		
		<a id="btnExport" class="btnExport" href="javascript:exportToExcel('tableQualitySurvey')">${i18n['cione-module.global.btn-download']}</a>
	</div>
 	<form id="form-export-data" method="post" action="">
 		<input id="export-data" name="export-data"  value="" type="hidden"> 	 	
 	</form>
</section>

<script>   
    var filter = {};
    var pagina = 0;
    var size = 15;

	//function cabecera
    function template(survey) {    	
        var html = "";
        html += "<tr>";
        html += "<td>" + survey.socio + " (" + survey.codSocio + ")</td>";
        html += "<td>" + survey.puntuacion + "</td>";
        html += "<td>" + survey.fecha + "</td>";
        html += "<td  class='hide-mobile'>" + survey.comentario + "</td>";        
        html += "</tr>";
        return html;
    }
	
	function search(){
		clearErrorMessages();		
		if(!validateForm(document.forms["formSurveys"])){
			return;
		}	
		
		$("#table-data").html("");
		filter = getFormData($("#formSurveys"));
		filter.pagina = pagina;
		filter.size = size + 1;
		getData();
	}
	

	
	function getData(){
	    $("#loading").show();        
        $.ajax({
            url : PATH_API + "/private/quality-surveys/v1/search",
            type : "POST",
            data : JSON.stringify(filter),
            contentType : 'application/json; charset=utf-8',
            cache : false,
            dataType : "json",
            success : function(response) {
				KK = response;				
				var results = [];
				if (response.length == 0 && pagina == 0) {
					results.push(templateNoRecordsFoundForTable(4));
				}else{					
					$("#foot").html("");
					if(response.length > size){						
						$("#foot").html(templateShowMore(true));						
					}else{
						$("#foot").html(templateShowMore());	
					}
					response.forEach(function(survey,index){
						if(index<size){
							results.push(template(survey));	
						}						
					});	
				}				
				$("#table-data").append(results.join(" "));
				 
            },
            error : function(response) {
                alert("error");             
            },
            complete : function(response) {
                $("#loading").hide();
                retailerZebra();
            }
        });

	}
	
	function despublicar(){       
		$.ajax({
			url : PATH_API + "/private/quality-surveys/v1/despublicar",
			type : "POST",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			success : function(response) {
				if(response == true){
					alert("ok");
				}
			},
			error : function(response) {
				alert("error");				
			},
			complete : function(response) {
				alert("ok");
			}
		});

	}
	
	function borrar(){       
		$.ajax({
			url : PATH_API + "/private/quality-surveys/v1/delete",
			type : "POST",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			dataType : "json",
			success : function(response) {
				if(response == true){
					alert("ok");
				}
			},
			error : function(response) {
				alert("error");				
			},
			complete : function(response) {
				alert("ok");
			}
		});

	}
	
	function showMore(){
		pagina = pagina + size;
		filter.pagina = pagina;
		getData();
	}
	
	function templateShowMore(thereIsMore){
		if(thereIsMore){
			var html = "<span class='vermas' onclick='showMore()'>";
	        html += "${i18n['cione-module.global.btn-more']}";
	        html += "</span>";
		}else{
			var html = "<span class='vermas'>";
	        html += "${i18n['cione-module.global.show-all-resutls']}";
	        html += "</span>";
		}
        return html;        
	}

    function initPage(){
    	$(".inputfechaMY").datepicker({ 
			dateFormat: 'mm-yy',
			changeMonth: true,
		    changeYear: true,
		    showButtonPanel: true,
		    onChangeMonthYear : function(year,month,inst){
		    	$(this).datepicker('setDate', new Date(inst.selectedYear, inst.selectedMonth, 1));
		    }
		});	
		$('.inputfechaMY').mask('00-0000');
    }

</script>