var $b;

$(document).ready(function() {

	$(".button-expand").on("click",function(){
		text = $(this).text();
	    id = $(this).attr("for");
	    element = $("#" + id + "").find(".col-sliding");
	    $(this).text( text == "Ver + columnas" ? "Ver - columnas" : "Ver + columnas");
	    element.hasClass("col-collapsed") ? element.removeClass("col-collapsed").addClass("col-extended") : element.removeClass("col-extended").addClass("col-collapsed");
	});

	$ancho = $(window).width();
	// Comprobar existencia del menu
	if ($ancho < 992) {
		$b = false;
	} else {

		$b = true;
	}

	titlelogin();
	menumobile()
	$(window).resize(function() {
		titlelogin();
		menumobile()
	});

    acordeonmobile();
    $('.accordion-mobile').find('.toggle').click(function(e) {
        e.preventDefault();
        
      var $this = $(this);

      if ($this.next().hasClass('show')) {
        $(this).find(".fa").toggleClass("rotate");
          $this.next().removeClass('show');
          $this.next().slideUp(350);
      } else {
          $(this).find(".fa").toggleClass("rotate");
          $this.parent().parent().find('li .inner').removeClass('show');
          $this.parent().parent().find('li .inner').slideUp(350);
          $this.next().toggleClass('show');
          $this.next().slideToggle(350);
      }
    });
    function acordeonmobile(){
        
        var maxMobileSize = 991;
        var windowWidth = window.innerWidth;
        var isMobile;
        var h1mobile = $("#mobile-title").html();
        if(windowWidth < maxMobileSize){
          isMobile = true;
         
          $(".cmp-bannersection").append("<div class='h1mobile'>"+h1mobile+"</div>");
          $.each($(".mobile-wrapper"), function () { 
              $(this).find(".panel-filter").appendTo($(this).find(".accordion-mobile > li > .inner > li")); 
          });
          $(".indicadores").prependTo(".content-news");
          
        }else{
          isMobile = false;
        }
        console.log("Inicialization -> isMobile: "+isMobile);
      
        $(window).resize(function() {
          windowWidth = window.innerWidth;
      
          if(windowWidth < maxMobileSize && !isMobile){
            isMobile = true;
            $(".cmp-bannersection").find(".h1mobile").remove();
            $(".cmp-bannersection").append("<div class='h1mobile'>"+h1mobile+"</div>");
            //Mobile changes
            console.log("Changing -> isMobile: "+isMobile);
            $(".indicadores").prependTo(".content-news");

            $.each($(".mobile-wrapper"), function () { 
                $(this).find(".panel-filter").appendTo($(this).find(".accordion-mobile > li > .inner > li")); 
            });
      
          }else if(windowWidth >= maxMobileSize && isMobile){
        	
            isMobile = false;
      
            //Desktop changes
            console.log("Changing -> isMobile: "+isMobile);
            $.each($(".mobile-wrapper"), function () { 
                $(this).find(".panel-filter").insertAfter($(this).find(".accordion-mobile")); 
            });
            $(".indicadores").prependTo(".right-asied");
          }
        });
    }
	/* javascript Table */

	retailerZebra();

	var x = window.matchMedia("(max-width: 992px)")
	myFunction(x)
	x.addListener(myFunction)
	function myFunction(x) {
		if (x.matches) {
			if ($(".panel-table").hasClass("desk")) {
				location.reload();
			}
			$(".panel-table").removeClass("desk");
			$(".panel-table").addClass("mobile");
			/*
			 * $('.masinfo > a').parents('tr').removeClass("active");
			 * $('.subtabla').css("display", "none");
			 */
		} else {
			if ($(".panel-table").hasClass("mobile")) {
				location.reload();
			}
			$(".panel-table").addClass("desk");
			$(".tr-extend").remove();
		}
	}
	

	
	//extend nested menu
	   $('.dropdown-toggle').on("click", function (e) {	
		   console.log(this);
		   $(this).closest(".navbar").find(".selected").removeClass("selected");
		   $(this).closest(".nav-item").toggleClass("selected");
		   
		   
		   
	    	//e.preventDefault();
	    	$(this).closest(".dropdown").siblings().find(".show").removeClass("show");
	    	$(this).closest(".dropdown").siblings().find(".dropdown-toggle").removeClass("extended");
	    //	console.log($(this).next('ul').text());
	    	$(this).next().toggleClass("show");
	      e.stopPropagation();
	      
	    });	
	   
	   $('.dropdown-item.dropdown-toggle').on('click', function() {
		   $(this).toggleClass("rotate");
		   $(this).closest(".navbar").find(".selected2").removeClass("selected2");
		    $(this).closest("li").toggleClass("selected2");
		    });
	   
	    $(".cmp-menu").find(".dropdown-item").click(function (e) { 
	        //e.preventDefault();
	        $(this).toggleClass("extended");	        
	        e.stopPropagation();	        
	    });
	    
	    //Remove noscroll body
	    $('.modal-header').on("click", function (e) {
	    	$("body").removeClass("noscroll");
	    	});	
	
	//register-update-employee-component   	
	$('.pedidosenvios.facturacion').change(function(e) {
		$('#pedido_facturacion').prop('checked', true);
	});
	$('.pedidosenvios.myshop').change(function(e) {
		$('#myshop_seccion').prop('checked', true);
	});
	
});

function retailerZebra() {
	
	$('tbody tr:not(.subtabla)').removeClass('even odd');
	$('tbody tr:not(.subtabla):visible:even').addClass('even');
	$('tbody tr:not(.subtabla):visible:odd').addClass('odd');
	$('tbody').find('tr').find('pie').removeClass('odd');
	$('tbody').find('tr').find('pie').removeClass('even');
	$('.total').removeClass('even');
	$('.total').removeClass('odd');
	
	$('.nozebra').find('tr').removeClass('even');
	$('.nozebra').find('tr').removeClass('odd');
}

/*
 * $(document).on("click", ".desk .masinfo > a", function (e) {
 * e.preventDefault(); if ($(this).parents('tr').hasClass("active")) {
 * $(this).parents('tr').removeClass("active"); } else {
 * $(this).parents('tr').removeClass("active");
 * $(this).parents('tr').addClass("active"); }
 * 
 * var box = $(this).parents('tr').next(); if ($(box).hasClass("subtabla")) {
 * box.toggle(); } });
 */

$(document)
		.on(
				"click",
				".panel-table.mobile > .table > tbody:not('#otros-documentos-table-data') > tr",
				
				function(e) {

					if ($(this).next().hasClass("tr-extend")) {
						$(this).next().remove();
						$(this).removeClass("open");
					} else {
						$(this).addClass("open");
						var contenido_tr = [];
						var contenido_titulo = [];
						wrapper_responsive = "";
						var td = "";

						// contar numero de columnas visibles
						var columns_visible = $(this).closest("tbody")
								.siblings("thead").find("th").not(
										".hide-mobile").length;

						// titulo de columnas ocultas
						$(this)
								.closest("tbody")
								.siblings("thead")
								.find("th.hide-mobile")
								.each(
										function() {
											contenido_titulo
													.push("<div class='titulo-extend'>"
															+ $(this).html()
															+ "</div>");
										});

						// contenido de columnas oculta
						$(this).find("td.hide-mobile").each(
								function() {
									contenido_tr.push("<div class=''>"
											+ $(this).html() + "</div>");
								});

						$.each(contenido_tr, function(i) {
							wrapper_responsive += "<div >"
									+ contenido_titulo[i] + "  "
									+ contenido_tr[i] + "</div>";

						});
						// imprime si existe columnas ocultas
						if ((0 < $(this).closest("tbody").siblings("thead")
								.find(".hide-mobile").length)
								&& ($(this).closest("tr").hasClass("tr-extend")) == false) {
							$(
									"<tr data-id='"
											+ $(this).data("id")
											+ "' class='tr-extend'><td class='td-extend' colspan="
											+ columns_visible + ">"
											+ wrapper_responsive + "</td></tr>")
									.insertAfter($(this));

						}
					}
				});
// }

var arrayTituloSubtabla = [];
var arrayContenidoSubtabla = [];
var wrapper_responsiveSubtabla = [];

/*
 * $(document).on("click", ".mobile .td-extend .masinforesponsive", function () {
 * 
 * //aÃ±adir subtabla a modal if
 * ($(this).closest("tr").next().hasClass("subtabla")) {
 * 
 * //obtener titulo columna modal $($(this).closest("tr").next().find("thead >
 * .encabezado > th")).each(function () {
 * arrayTituloSubtabla.push($(this).html()); });
 * 
 * //obtener contenido columna modal $($(this).closest("tr").next().find("tbody >
 * tr > td")).each(function () { arrayContenidoSubtabla.push($(this).html());
 * 
 * }); //obtener titulo principal modal var modalTitleResponsive =
 * $(this).closest(".panel-table").find("thead > tr > th").html() + ": " +
 * $(this).closest(".panel-table").find("tbody > tr > td").html();;
 * 
 * 
 * //Obtener todo el contenido del modal for (let i = 0; i <
 * arrayContenidoSubtabla.length;) { for (let j = 0; j <
 * arrayTituloSubtabla.length; j++) { wrapper_responsiveSubtabla += "<div
 * class='titleModal'>" + arrayTituloSubtabla[j] + "</div><p>" +
 * arrayContenidoSubtabla[i] + "</p>"; i++; if (j ==
 * arrayTituloSubtabla.length) { j = 0; } } }
 * 
 * //Insertar contenido en modal $("#tablaModal").css("display",
 * "block").find(".modal-body").html(wrapper_responsiveSubtabla); //Insertar
 * titulo en modal $("#tablaModal").css("display", "block").find(".modal-header >
 * .modal-title").html(modalTitleResponsive);
 *  }
 * 
 * });
 */

$(document).on("click", ".modal .modal-header > .close ", function() {
	$(this).closest(".modal").css("display", "none");
	$(".modal").find(".modal-body").html("");
	arrayTituloSubtabla = [];
	arrayContenidoSubtabla = [];
	wrapper_responsiveSubtabla = [];
});

$(document).ready(function() {

	$('.inputfecha').click(function(e) {
		e.preventDefault();
		$(this).datepicker();
	});

	// introducir por defecto cantidad de fila en todas las tablas de la pÃ¡gina

	// Numero de filas de la tabla;
	// $(".vermas").click(function () {
	// var thisParent = $(this);

	// setTimeout(function(){
	// var copyTable =
	// thisParent.parents('.foot').siblings('.table').find('tr.odd,
	// tr.even').html();
	// thisParent.parents('.foot').siblings('.table').find(">
	// tbody").append("<tr>" + copyTable + "</tr>");
	// retailerZebra();

	// }, 1500);

	// });

	// ordenar columna
	/*
	 * $('th').click(function () { var table = $(this).parents('table').eq(0)
	 * var rows =
	 * table.find('tr:gt(0)').toArray().sort(comparer($(this).index())) this.asc =
	 * !this.asc if (!this.asc) { rows = rows.reverse() } for (var i = 0; i <
	 * rows.length; i++) { table.append(rows[i]) } setIcon($(this), this.asc); })
	 */

	/*
	 * function comparer(index) { return function (a, b) { var valA =
	 * getCellValue(a, index), valB = getCellValue(b, index) return
	 * $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB :
	 * valA.localeCompare(valB) } }
	 */

	/*
	 * function getCellValue(row, index) { return
	 * $(row).children('td').eq(index).html() }
	 */

	/*
	 * function setIcon(element, asc) { $("th").each(function (index) {
	 * $(this).removeClass("sorting"); $(this).removeClass("asc");
	 * $(this).removeClass("desc"); }); element.addClass("sorting"); if (asc)
	 * element.addClass("asc"); else element.addClass("desc"); }
	 */

});
$(document).ready(function() {

	$('.inputfecha').click(function(e) {
		e.preventDefault();
		$(this).datepicker();
	});

	// introducir por defecto cantidad de fila en todas las tablas de la pÃ¡gina

	// Numero de filas de la tabla;
	// $(".vermas").click(function () {
	// var thisParent = $(this);

	// setTimeout(function(){
	// var copyTable =
	// thisParent.parents('.foot').siblings('.table').find('tr.odd,
	// tr.even').html();
	// thisParent.parents('.foot').siblings('.table').find(">
	// tbody").append("<tr>" + copyTable + "</tr>");
	// retailerZebra();

	// }, 1500);

	// });

	// ordenar columna
	/*
	 * $('th').click(function () { var table = $(this).parents('table').eq(0)
	 * var rows =
	 * table.find('tr:gt(0)').toArray().sort(comparer($(this).index())) this.asc =
	 * !this.asc if (!this.asc) { rows = rows.reverse() } for (var i = 0; i <
	 * rows.length; i++) { table.append(rows[i]) } setIcon($(this), this.asc); })
	 */

	/*
	 * function comparer(index) { return function (a, b) { var valA =
	 * getCellValue(a, index), valB = getCellValue(b, index) return
	 * $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB :
	 * valA.localeCompare(valB) } }
	 */

	/*
	 * function getCellValue(row, index) { return
	 * $(row).children('td').eq(index).html() }
	 * 
	 * function setIcon(element, asc) { $("th").each(function (index) {
	 * $(this).removeClass("sorting"); $(this).removeClass("asc");
	 * $(this).removeClass("desc"); }); element.addClass("sorting"); if (asc)
	 * element.addClass("asc"); else element.addClass("desc"); }
	 */

});

$(document).ready(function() {
	var item_count = $('.owl-bannerinfo .item').length;

	$('.owl-carousel.owl-bannerinfo').owlCarousel({
		loop : true,
		autoplay : true,
		autoplayTimeout : 10000,
		navSpeed: 2,
		margin : 10,
		dots : false,
		nav : false,
		items : 1,
		touchDrag : true,
		onInitialize : function(event) {
			if (item_count <= 1) {
				this.settings.nav = false;
			} else {
				this.settings.nav = true;
			}
		},
		responsive : {
			0 : {

				nav : false
			},
			768 : {

				nav : true
			},

		}
	})
});
$(document).ready(function() {
	$(".toggle-password").click(function() {

		$(this).toggleClass("fa-eye fa-eye-slash");
		var input = $($(this).attr("toggle"));
		if (input.attr("type") == "password") {
			input.attr("type", "text");
		} else {
			input.attr("type", "password");
		}
	});
	$(".toggle-passwordnew").click(function() {

		$(this).toggleClass("fa-eye fa-eye-slash");
		var input = $($(this).attr("toggle"));
		if (input.attr("type") == "password") {
			input.attr("type", "text");
		} else {
			input.attr("type", "password");
		}
	});
	$(".toggle-passwordrepeat").click(function() {

		$(this).toggleClass("fa-eye fa-eye-slash");
		var input = $($(this).attr("toggle"));
		if (input.attr("type") == "password") {
			input.attr("type", "text");
		} else {
			input.attr("type", "password");
		}
	});
});

$closemenu = true;

$(".abrir-menu").click(function(e) {
	$('.abrir-menu + ul').slideToggle('500');
});

$(".btn-menumobile").click(function(e) {
	e.preventDefault();
	if ($closemenu) {
		$(".content-menumobile").show();
		$closemenu = false;

	} else {
		$closemenu = true;
		$(".content-menumobile").hide();
	}

});

function menumobile() {
	$ancho = $(window).width();
	// Comprobar existencia del menu

	if ($ancho < 992 && $b == false) {

		$b = true;
		if ($b) {
			$(".cmp-menu > .navbar").appendTo(
					".cmp-header > .content-menumobile");
		}
	} else if ($ancho >= 992 && $b == true) {
		$b = false;

		$(".cmp-header > .content-menumobile > .navbar").appendTo(".cmp-menu");

	}

}

$(document).ready(function() {
	var prueba = $('.cmp-menu').siblings('.cmp-home');

	if ($('.cmp-menu').siblings('.cmp-home').length == 1) {
		$('.cmp-menu').css("opacity", 0.0);
	}

});
$(document).ready(function() {
	$('.menu li').click(function(e) {		
		//e.preventDefault();
		$(this).toggleClass("activado");
		$(this).toggleClass("action");
		$(this).children('ul').slideToggle();		
		if($(this).children("a").attr("href") == "#"){			
			return false;
		}
		e.stopPropagation();		
	});

	$('.btn-menu').click(function() {
		$('.contenedor-menu .menu').slideToggle();
	});

	$(window).resize(function() {
		if ($(document).width() > 450) {
			$('.contenedor-menu .menu').css({
				'display' : 'block'
			});
		}

		if ($(document).width() < 450) {
			$('.contenedor-menu .menu').css({
				'display' : 'none'
			});
			$('.menu li ul').slideUp();
			$('.menu li').removeClass('activado');
		}
	});

	$('.menu li ul li a').click(function() {				
		if($(this).attr("href") != "#"){
			window.location.href = $(this).attr("href");
		}		
	});
});

$(document).ready(function() {
	// cebrado

	// datepicker
	$('.inputfecha').click(function(e) {
		e.preventDefault();
		$(this).datepicker();
	});

	// ordenar columna
	/*
	 * $('th').click(function () { var table = $(this).parents('table').eq(0)
	 * var rows =
	 * table.find('tr:gt(0)').toArray().sort(comparer($(this).index())) this.asc =
	 * !this.asc if (!this.asc) { rows = rows.reverse() } for (var i = 0; i <
	 * rows.length; i++) { table.append(rows[i]) } setIcon($(this), this.asc); })
	 */

	/*
	 * function comparer(index) { return function (a, b) { var valA =
	 * getCellValue(a, index), valB = getCellValue(b, index) return
	 * $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB :
	 * valA.localeCompare(valB) } }
	 */

	/*
	 * function getCellValue(row, index) { return
	 * $(row).children('td').eq(index).html() }
	 * 
	 * function setIcon(element, asc) { $("th").each(function (index) {
	 * $(this).removeClass("sorting"); $(this).removeClass("asc");
	 * $(this).removeClass("desc"); }); element.addClass("sorting"); if (asc)
	 * element.addClass("asc"); else element.addClass("desc"); }
	 */

});
$(document).ready(function() {

	$('.inputfecha').click(function(e) {
		e.preventDefault();
		$(this).datepicker();
	});

	// ordenar columna
	/*
	 * $('th').click(function () { var table = $(this).parents('table').eq(0)
	 * var rows =
	 * table.find('tr:gt(0)').toArray().sort(comparer($(this).index())) this.asc =
	 * !this.asc if (!this.asc) { rows = rows.reverse() } for (var i = 0; i <
	 * rows.length; i++) { table.append(rows[i]) } setIcon($(this), this.asc); })
	 */

	/*
	 * function comparer(index) { return function (a, b) { var valA =
	 * getCellValue(a, index), valB = getCellValue(b, index) return
	 * $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB :
	 * valA.localeCompare(valB) } }
	 */

	/*
	 * function getCellValue(row, index) { return
	 * $(row).children('td').eq(index).html() }
	 * 
	 * function setIcon(element, asc) { $("th").each(function (index) {
	 * $(this).removeClass("sorting"); $(this).removeClass("asc");
	 * $(this).removeClass("desc"); }); element.addClass("sorting"); if (asc)
	 * element.addClass("asc"); else element.addClass("desc"); }
	 */

});
$(document).ready(function() {

	$('.inputfecha').click(function(e) {
		e.preventDefault();
		$(this).datepicker();
	});

	// ordenar columna
	/*
	 * $('th').click(function () { var table = $(this).parents('table').eq(0)
	 * var rows =
	 * table.find('tr:gt(0)').toArray().sort(comparer($(this).index())) this.asc =
	 * !this.asc if (!this.asc) { rows = rows.reverse() } for (var i = 0; i <
	 * rows.length; i++) { table.append(rows[i]) } setIcon($(this), this.asc); })
	 */

	/*
	 * function comparer(index) { return function (a, b) { var valA =
	 * getCellValue(a, index), valB = getCellValue(b, index) return
	 * $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB :
	 * valA.localeCompare(valB) } }
	 */

	/*
	 * function getCellValue(row, index) { return
	 * $(row).children('td').eq(index).html() }
	 * 
	 * function setIcon(element, asc) { $("th").each(function (index) {
	 * $(this).removeClass("sorting"); $(this).removeClass("asc");
	 * $(this).removeClass("desc"); }); element.addClass("sorting"); if (asc)
	 * element.addClass("asc"); else element.addClass("desc"); }
	 */

});
$(document).ready(function() {

	$('.inputfecha').click(function(e) {
		e.preventDefault();
		$(this).datepicker();
	});

	// ordenar columna
	/*
	 * $('th').click(function () { var table = $(this).parents('table').eq(0)
	 * var rows =
	 * table.find('tr:gt(0)').toArray().sort(comparer($(this).index())) this.asc =
	 * !this.asc if (!this.asc) { rows = rows.reverse() } for (var i = 0; i <
	 * rows.length; i++) { table.append(rows[i]) } setIcon($(this), this.asc); })
	 */

	/*
	 * function comparer(index) { return function (a, b) { var valA =
	 * getCellValue(a, index), valB = getCellValue(b, index) return
	 * $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB :
	 * valA.localeCompare(valB) } }
	 */

	/*
	 * function getCellValue(row, index) { return
	 * $(row).children('td').eq(index).html() }
	 * 
	 * function setIcon(element, asc) { $("th").each(function (index) {
	 * $(this).removeClass("sorting"); $(this).removeClass("asc");
	 * $(this).removeClass("desc"); }); element.addClass("sorting"); if (asc)
	 * element.addClass("asc"); else element.addClass("desc"); }
	 */

});
$(document).ready(function() {

	$('ul.tabs li').click(function() {
		var tab_id = $(this).attr('data-tab');

		$('ul.tabs li').removeClass('current');
		$('.tab-content').removeClass('current');

		$(this).addClass('current');
		$("#" + tab_id).addClass('current');
	})

})
$key = false;

function titlelogin() {
	$ancho = $(window).width();

	if ($ancho >= 768 && $ancho <= 992 && $key == false) {
		$key = true;
		if ($key) {

			$(".cmp-bundles-loginback > .container > .title-login").appendTo(
					"section[class^=cmp-login]");
		}
	}
	if (($ancho < 768 && $key == true) || ($ancho > 992 && $key == true)) {
		$key = false;

		$("section[class^=cmp-login]>.title-login").prependTo(
				".cmp-bundles-loginback >.container");
	}
}

function loadsubtable(a) {
	// aniadir subtabla a modal
	if ($(a).closest("tr").next().hasClass("subtabla")) {
		
		// tabla mis consumos movil
		var misConsumosMobile = false;
		if($(a).closest("tr").next().hasClass("subtabla-consumo")){
			misConsumosMobile = true;
		}
		
		// obtener titulo columna modal
		$($(a).closest("tr").next().find("thead > .encabezado > th")).each(
				function() {
					arrayTituloSubtabla.push($(this).html());
				});

		// obtener contenido columna modal
		$($(a).closest("tr").next().find("tbody > tr > td")).each(function() {
			arrayContenidoSubtabla.push($(this).html());
		});

		// obtener titulo principal modal
		// var modalTitleResponsive = $(a).closest(".panel-table").find("thead >
		// tr > th").html() + ": " + $(a).closest(".panel-table").find("tbody >
		// tr > td").html();;
		var titleResponsiveElement = $(a).closest("tr").prev().find("td").html();
		if(titleResponsiveElement == undefined){
			titleResponsiveElement = $(a).closest("tr").find("td").html();
		}
		var modalTitleResponsive = $(a).closest(".panel-table").find(
				"thead > tr > th").html()
				+ ": " + titleResponsiveElement;

		// Obtener todo el contenido del modal
		for (var i = 0; i < arrayContenidoSubtabla.length;) {
			wrapper_responsiveSubtabla += "<div class='box'>";
			
			
			
				for (var j = 0; j < arrayTituloSubtabla.length; j++) {
					
						if (arrayContenidoSubtabla[i] != undefined) {
							if (arrayContenidoSubtabla[i].substring(0, 2) == '<i') {
								wrapper_responsiveSubtabla += "<div class='titleModal'></div><p>"
									+ arrayContenidoSubtabla[i] + "</p>";
							} else {
								wrapper_responsiveSubtabla += "<div class='titleModal'>"
									+ arrayTituloSubtabla[j] + "</div><p>"
									+ arrayContenidoSubtabla[i] + "</p>";
							}
						}
					
						// fix para mis consumos
						if(misConsumosMobile && (j == 3 || j == 7 || j == 11)){
							wrapper_responsiveSubtabla += "<hr/>";
						} 
						
						i++;
						if (j == arrayTituloSubtabla.length) {
							j = 0;
						}
					
				}
			wrapper_responsiveSubtabla += "</div>";
		}



	} 
		// Insertar contenido en modal
		$("#tablaModal").css("display", "block").find(".modal-body").empty()
				.html(wrapper_responsiveSubtabla);
		// Insertar titulo en modal
		$("#tablaModal").css("display", "block").find(
				".modal-header > .modal-title").empty().html(
				modalTitleResponsive);
		// bloquear scroll al body
		$("body").toggleClass("noscroll");

		arrayTituloSubtabla.length = 0;
		arrayContenidoSubtabla.length = 0;
		wrapper_responsiveSubtabla.length = 0;

}

$('.cmp-datoscuota').find('.toggle').click(function(e) {
	e.preventDefault();
	
var $this = $(this);

	if ($this.next().hasClass('show')) {
		$(this).find(".fa").toggleClass("rotate");
		$this.next().removeClass('show');
		$this.next().slideUp(350);
	} else {
		$(this).find(".fa").toggleClass("rotate");
		$this.parent().parent().find('li .inner').removeClass('show');
		$this.parent().parent().find('li .inner').slideUp(350);
		$this.next().toggleClass('show');
		$this.next().slideToggle(350);
	}
});


function toggleRow(element){  		
	if ($(element).parent().parent().hasClass("active")) {
        $(element).parent().parent().removeClass("active");
    } else {
        //$('.masinfo > a').parents('tr').removeClass("active");            
        $(element).parent().parent().addClass("active");
    }

    var box = $(element).parents('tr').next();
	    if ($(box).hasClass("subtabla")) {
	        box.toggle();
	    }
	}

var Menu = {

	el : {
		ham : $('.btn-menumobile'),
		menuTop : $('.menu-top'),
		menuMiddle : $('.menu-middle'),
		menuBottom : $('.menu-bottom')
	},

	init : function() {
		Menu.bindUIactions();
	},

	bindUIactions : function() {
		Menu.el.ham.on('click', function(event) {
			Menu.activateMenu(event);
			event.preventDefault();
		});
	},

	activateMenu : function() {
		Menu.el.menuTop.toggleClass('menu-top-click');
		Menu.el.menuMiddle.toggleClass('menu-middle-click');
		Menu.el.menuBottom.toggleClass('menu-bottom-click');
	}
};

Menu.init();
