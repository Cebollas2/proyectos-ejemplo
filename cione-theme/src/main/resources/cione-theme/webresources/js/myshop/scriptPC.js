$(document).ready(function(){

$('.b2b-owl-carousel-pack').owlCarousel({
    loop:false,
    margin:10,
    nav:true,
    dots: false,
    responsive:{
        0:{
            items:1
        },
        600:{
            items:2
        },
        1000:{
            items:2
        }
    }
}) 
  

$("#myChart1").on("click", function(e){
    x = getMousePos($(this),e);
    console.log(x)
})

function getMousePos(canvas, evt) {
    var rect = canvas.getBoundingClientRect();
    return {
        x: (evt.clientX - rect.left) / (rect.right - rect.left) * canvas.width,
        y: (evt.clientY - rect.top) / (rect.bottom - rect.top) * canvas.height
    };
}


$('.owl-carousel-products').owlCarousel({
    loop:true,
    margin:10,
    dots: false,
    nav:true,
    autoWidth:true,
    mouseDrag:false,
    responsive:{
        0:{
            items:1,
            autoWidth: false,
            nav: false,
            margin: 10,
            center: true
        },
        410:{
            items:8,
           
        },
        1000:{
            items:10,
          
        }
    } 
});

$('.owl-carousel-demo').owlCarousel({
    loop:true,
    margin:10,
    dots: false,
    nav:true,
    autoWidth:true,
    responsive:{
        0:{
            items:1,
            autoWidth: false,
        },
        410:{
            items:6,
           
        },
        1000:{
            items:10,
          
        }
    }
})

// EN MAGNOLIA LO HAN PUESTO EN LA PLALNTILLA

// // CHANGE CALIBRE

// $(".product-calibre").on("click",function(e){

//     $(this).parent().find(".product-calibre").removeClass("selected");
//     $(this).addClass("selected");
//     e.stopPropagation();
// });

// // SELECT COLOR

// $(".product-color-circle").on("click",function(){

//     $(".product-color-circle").removeClass("selected")
//     $(this).addClass("selected");    

// });







// $(".product-stock").on("click",function(){
    

//     $(this).toggleClass("selected")
   

// });

$(".item-back").mouseover(function() {
    $(".owl-carrusel-layer").hide();
});

$(".item-back").mouseout(function() {
    $(".owl-carrusel-layer").show();
});


// DELETE ITEM IN CART PAGE

//$('.b2b-cart-item-delete').on("click", function () {
//    $(this).parent().remove();
//});



// ADD OR QUIT AMOUNT
// $('.product-amount-button-minus').on("click", function () {

//     var inputAmount = $(this).parent().find('.product-amount-input');
//     count = parseInt(inputAmount.val());
//     count = parseInt(count) - 1;

//     if (count >= 0) {
//         inputAmount.val(count);
//     }



// });

// $('.product-amount-button-plus').on("click", function () {

//     var inputAmount = $(this).parent().find('.product-amount-input');
//     count = parseInt(inputAmount.val());
//     count = parseInt(count) + 1;
//     inputAmount.val(count);



// });

// OPEN FLOATING REFERENCE - DETAIL

$('.open-reference').on("click", function () {

    var dataReference = $(this).data("reference");
    $(".floating-col").css("display", "none");
    $(".floating-col[data-reference='" + dataReference + "']").css("display", "block");
});


$('.b2b-cart-item-col-delete').on("click", function () {
$(this).parent().css("display","none");
  
});


function closeCart() {
    var pathname = window.location.pathname;
    var origin = window.location.origin; 
    var path = origin + '/.resources/cione-theme/webresources/img/myshop/common/shopping-bag.svg';
    if (pathname.includes('magnoliaAuthor')){
        path = origin + '/magnoliaAuthor/.resources/cione-theme/webresources/img/myshop/common/shopping-bag.svg';
    }
    
    $('.b2b-shopping-bag-img').attr('src', path);
    $('.b2b-floating-cart').removeClass('dblock')
    $('.layer-opacity-cart').removeClass('dblock');
    $('body').removeClass('overflow-hidden');

 

}

 

function openCart() {
    var pathname = window.location.pathname;
    var origin = window.location.origin;
    var path = origin + '/.resources/cione-theme/webresources/img/myshop/common/shopping-bag-solid.svg';
    if (pathname.includes('magnoliaAuthor')){
        path = origin + '/magnoliaAuthor/.resources/cione-theme/webresources/img/myshop/common/shopping-bag-solid.svg';
    }

 

    $('.b2b-shopping-bag-img').attr('src', path);
    $('.layer-opacity-cart').addClass('dblock');
    $('.b2b-floating-cart').addClass('dblock')
    $('body').addClass('overflow-hidden');
}
// Add the following code if you want the name of the file appear on select
$(".custom-file-input").on("change", function() {
  var fileName = $(this).val().split("\\").pop();
  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});





// $(".cmp-configurador .selector-img").on("click",function(){

//   $(".selector-img").removeClass("selected");
//   $(this).addClass("selected");
// });

$(".cmp-configurador .selector-img").on("click",function(){
  tabs_fields_disabled_id = ["potencia", "lon", "telebobina","filtro","hilo","pulsador", 
    "volumen","tipoventing","modventing","numSerie","instrucciones"];
  tabs_fields_disabled_name = ["carcasa", "plato","codo"];
  $(".selector-img").removeClass("selected");
  $(this).addClass("selected");
  var idSel = $(this).attr('id');
  if (idSel == "derecho") {
    //desabilita
    tabs_fields_disabled_id.forEach(element => {
        $("#l" + element).attr('disabled', true);
        $("#r" + element).attr('disabled', false);
    });
    tabs_fields_disabled_name.forEach(element => {
        //$("input[name$='lcarcasa']").attr("disabled", true);
        $("input[name$='l" + element + "']").attr("disabled", true);
        $("input[name$='r" + element + "']").attr("disabled", false);
    }); 
    $("input[name$='ear-right']").each(function(index){
        $("input[name$='ear-right']")[index].disabled = false;
    });
    $("input[name$='ear-left']").each(function(index){
        $("input[name$='ear-left']")[index].disabled = true;
    });
    $("#myChart1").css('pointer-events', 'initial');
    $("#myChart2").css('pointer-events', 'none');
    $("#myFileIzq").attr('disabled', true);
    $("#myFileDrch").attr('disabled', false);
    //reset del lado izquierdo
    removeitemsIzq();
  }
  if (idSel == "izquierdo") {
    tabs_fields_disabled_id.forEach(element => {
        $("#l" + element).attr('disabled', false);
        $("#r" + element).attr('disabled', true);
    }); 
    tabs_fields_disabled_name.forEach(element => {
        $("input[name$='l" + element + "']").attr("disabled", false);
        $("input[name$='r" + element + "']").attr("disabled", true);
    }); 
    $("input[name$='ear-right']").each(function(index){
        $("input[name$='ear-right']")[index].disabled = true;
    });
    $("input[name$='ear-left']").each(function(index){
        $("input[name$='ear-left']")[index].disabled = false;
    });
    $("#myChart1").css('pointer-events', 'none');
    $("#myChart2").css('pointer-events', 'initial');
    $("#myFileIzq").attr('disabled', false);
    $("#myFileDrch").attr('disabled', true);
    //reset del lado derecho
    removeitemsDrch();
  }
  if (idSel == "binaural") {
    tabs_fields_disabled_id.forEach(element => {
        $("#l" + element).attr('disabled', false);
        $("#r" + element).attr('disabled', false);
    }); 
    tabs_fields_disabled_name.forEach(element => {
        $("input[name$='l" + element + "']").attr("disabled", false);
        $("input[name$='r" + element + "']").attr("disabled", false);
    }); 
    $("input[name$='ear-right']").each(function(index){
        $("input[name$='ear-right']")[index].disabled = false;
    });
    $("input[name$='ear-left']").each(function(index){
        $("input[name$='ear-left']")[index].disabled = false;
    });
    $("#myChart1").css('pointer-events', 'initial');
    $("#myChart2").css('pointer-events', 'initial');
    $("#myFileIzq").attr('disabled', false);
    $("#myFileDrch").attr('disabled', false);
  }
});


// Copy form to other
tabs_fields = [ ["potencia"],
["direccionalidad","conectividad","media"],
["pila","lon","telebobina","filtro","pulsador","volumen","hilo","tipoventing","modventing"],
["carcasa","plato","codo"],
["instrucciones"]
];



function configuradorCopyForm(container, side){

  index = container.attr("number");
  
  if ( side === "toRight"){

    origen = "l";
    destino = "r";
  }
  else{
    origen = "r";
    destino = "l";
  }

  

  tabs_fields[index].forEach(element => {
    

    // COPY CHECKBOX
    if ( $("#" + origen + element).is(':checkbox')){
      $("#" + origen + element).prop("checked",$("#" + destino + element).is(":checked") )
    }
    // COPY RADIOBUTTONS
    else if ($("[type='radio'][name='" + origen + element + "']").is(":radio") == true){
      $("[type='radio'][name='" + origen + element + "']").each(function(index) {
        $(this).prop("checked",$("[type='radio'][name='" + destino + element + "']")[index].checked )
      });
    }
    // COPY INPUTS - TEXTAREAS - SELECTS
    else{
      $("#" + origen + element).val( $("#" + destino + element ).val());
    }
  }); 



}


$(".configurador-copy-check").on("click",function(){

  configuradorCopyForm( $(this).parents(".container"), $(this).data("side")); 


});


$(".accordion-group.card .accordion-toggle.card-header").on("click", function(){

  $(this).addClass("complete");


});
$('.owl-carousel-banner').owlCarousel({
    loop:true,
    autoplay:true,
    autoplayTimeout:5000,
    autoplayHoverPause:true,
    margin:10,
    dots:true,
    nav:false,
    autoHeight: true,
    responsive:{
        0:{
            items:1
        }
    }
})

// COPY EYE - Contactologia detalle

// configurador_izquierdo = ["iesfera","icilindro","ieje","idiametro","icurva","iadicion","icolor","icantidad","ireferencia"];
// configurador_derecho = ["desfera","dcilindro","deje","ddiametro","dcurva","dadicion","dcolor","dcantidad","dreferencia"];


configurador_izquierdo = ["esferaizq","cilindroizq","ejeizq","diametroizq","curvabaseizq","adicionizq","colorlenteizq","cantidadizq","ireferencia"];

configurador_derecho = ["esferadrch","cilindrodrch","ejedrch","diametrodrch","curvabasedrch","adiciondrch","colorlentedrch","cantidaddrch","dreferencia"];

function copiarConfigurador(elementoOrigen,elementoDestino){
    elementoDestino.forEach(function(element,index){
        $('#' + elementoDestino[index]).val($('#' + elementoOrigen[index]).val());
    });
}


$(".copy-to-right").on("click",function(){
    copiarConfigurador(configurador_izquierdo,configurador_derecho); 
});

$(".copy-to-left").on("click",function(){
    copiarConfigurador(configurador_derecho,configurador_izquierdo);
});


if(navigator.userAgent.match(/iPhone/)) {
    $('html').addClass('b2b-iphone');
}

// DETALLE MINIATURA

$('.b2b-detalle-miniaturas-item').click(function (e) {

    $('.b2b-detalle-miniaturas-item').removeClass('selected');
    $(this).addClass('selected');


    if ($(this).hasClass('b2b-detalle-miniaturas-video')) {
        $(".b2b-detalle-imagen-full").css("display", "none");
        $(".b2b-detalle-video").css("display", "block");
        console.log("video")
    }
    else {
        console.log("img")
        $(".b2b-detalle-imagen-full").css("display", "block");
        $(".b2b-detalle-video").css("display", "none");
        var currentSrc = $(this).find('.b2b-detalle-miniaturas-imagen').attr('src');
        $(this).attr($('.b2b-detalle-imagen-full').attr("src"));
        $('.b2b-detalle-imagen-full').attr("src", currentSrc);
    }


});




$(".b2b-detalle-full-screen").on("click", function () {
    $(".detalle-modal-zoom").css("display", "flex");
    $('.owl-carousel-zoom').owlCarousel({
        loop: true,
        margin: 10,
        stagePadding: 0,
        dots: true,
        nav: true,
        items: 1
    });

});


$(".detalle-modal-zoom, .detalle-modal-zoom-close").on("click", function () {
    $(".detalle-modal-zoom").css("display", "none");


});


$(".b2b-detalle-virtual-button").on("click", function () {
    $(".detalle-virtual-modal").css("display", "flex");
  
 

});

$(".detalle-virtual-modal, .detalle-virtual-modal-close").on("click", function () {
    $(".detalle-virtual-modal").css("display", "none");

});


$(".owl-carousel-zoom").on("click", function (e) {
    e.stopPropagation();


});


// var params = {
//     apiKey: 'TBVAcXitApiZPVH791yxdHbAc8AKzBwtCnjtv6Xn',
// };


// // This function will be called when the page is loaded and ready
// window.onload = function () {

//     // create fitmix only once 
//     fitmixInstance = FitMix.createWidget('fitmixContainer', params, function () {
//         console.log('fitmixInstance ready.');
//         fitmixInstance.setFrame('1703-433490'); // this will be the default frame loaded (optionnal)
//     });

// };


// /* LUPA */

// var native_width = 0;
// var native_height = 0;
// var mouse = { x: 0, y: 0 };
// var magnify;
// var cur_img;

// var ui = {
//     magniflier: $('.magniflier')
// };

// // Add the magnifying glass
// if (ui.magniflier.length) {
//     var div = document.createElement('div');
//     div.setAttribute('class', 'glass');
//     ui.glass = $(div);

//     $('body').append(div);
// }


// // All the magnifying will happen on "mousemove"

// var mouseMove = function (e) {
//     var $el = $(this);

//     // Container offset relative to document
//     var magnify_offset = cur_img.offset();

//     // Mouse position relative to container
//     // pageX/pageY - container's offsetLeft/offetTop
//     mouse.x = e.pageX - magnify_offset.left;
//     mouse.y = e.pageY - magnify_offset.top;

//     // The Magnifying glass should only show up when the mouse is inside
//     // It is important to note that attaching mouseout and then hiding
//     // the glass wont work cuz mouse will never be out due to the glass
//     // being inside the parent and having a higher z-index (positioned above)
//     if (
//         mouse.x < cur_img.width() &&
//         mouse.y < cur_img.height() &&
//         mouse.x > 0 &&
//         mouse.y > 0
//     ) {

//         magnify(e);
//     }
//     else {
//         ui.glass.fadeOut(100);
//     }

//     return;
// };

// var magnify = function (e) {

//     // The background position of div.glass will be
//     // changed according to the position
//     // of the mouse over the img.magniflier
//     //
//     // So we will get the ratio of the pixel
//     // under the mouse with respect
//     // to the image and use that to position the
//     // large image inside the magnifying glass

//     var rx = Math.round(mouse.x / cur_img.width() * native_width - ui.glass.width() / 2) * -1;
//     var ry = Math.round(mouse.y / cur_img.height() * native_height - ui.glass.height() / 2) * -1;
//     var bg_pos = rx + "px " + ry + "px";

//     // Calculate pos for magnifying glass
//     //
//     // Easy Logic: Deduct half of width/height
//     // from mouse pos.

//     // var glass_left = mouse.x - ui.glass.width() / 2;
//     // var glass_top  = mouse.y - ui.glass.height() / 2;
//     var glass_left = e.pageX - ui.glass.width() / 2;
//     var glass_top = e.pageY - ui.glass.height() / 2;
//     //console.log(glass_left, glass_top, bg_pos)
//     // Now, if you hover on the image, you should
//     // see the magnifying glass in action
//     ui.glass.css({
//         left: glass_left,
//         top: glass_top,
//         backgroundPosition: bg_pos
//     });

//     return;
// };

// $('.magniflier').on('mousemove', function () {
//     ui.glass.fadeIn(200);

//     cur_img = $(this);

//     var large_img_loaded = cur_img.data('large-img-loaded');
//     var src = cur_img.data('large') || cur_img.attr('src');

//     // Set large-img-loaded to true
//     // cur_img.data('large-img-loaded', true)

//     if (src) {
//         ui.glass.css({
//             'background-image': 'url(' + src + ')',
//             'background-repeat': 'no-repeat'
//         });
//     }

//     // When the user hovers on the image, the script will first calculate
//     // the native dimensions if they don't exist. Only after the native dimensions
//     // are available, the script will show the zoomed version.
//     //if(!native_width && !native_height) {

//     if (!cur_img.data('native_width')) {
//         // This will create a new image object with the same image as that in .small
//         // We cannot directly get the dimensions from .small because of the
//         // width specified to 200px in the html. To get the actual dimensions we have
//         // created this image object.
//         var image_object = new Image();

//         image_object.onload = function () {
//             // This code is wrapped in the .load function which is important.
//             // width and height of the object would return 0 if accessed before
//             // the image gets loaded.
//             native_width = image_object.width;
//             native_height = image_object.height;

//             cur_img.data('native_width', native_width);
//             cur_img.data('native_height', native_height);

//             //console.log(native_width, native_height);

//             mouseMove.apply(this, arguments);

//             ui.glass.on('mousemove', mouseMove);
//         };


//         image_object.src = src;

//         return;
//     } else {

//         native_width = cur_img.data('native_width');
//         native_height = cur_img.data('native_height');
//     }
//     //}
//     //console.log(native_width, native_height);

//     mouseMove.apply(this, arguments);

//     ui.glass.on('mousemove', mouseMove);
// });

// ui.glass.on('mouseout', function () {
//     ui.glass.off('mousemove', mouseMove);
// });


$(".b2b-detail-tab-title").on("click", function () {

    $(".b2b-detail-tab-title").removeClass('selected');
    $(this).addClass('selected');

    var tabId = $(this).data("id");
    $('.b2b-detail-tab-body-content').removeClass("active");
    $('.b2b-detail-tab-body-content[id="' + tabId + '"').addClass("active");



});
// $('.tab-label').click(function (e) {
//     // Toggle More & Minus icon
//     var imgicon = $(this).find('.more-icon');
//     var tabtext = $(this).find(".tab-label-text");
//     if (imgicon.attr("src") === "../../../assets/Img/icons/more.svg") {
//         // SetTimeout to syncronized with css transitions
//         setTimeout(
//             function () {
//                 imgicon.attr("src", "../../../assets/Img/icons/minus.svg");
//                 tabtext.css("color", "#00609c");
//             }, 1);
//         // $(this).parents('.tab').find('.tab-label-clean-text').css('display','flex');
//     }
//     else {
//         setTimeout(
//             function () {
//                 imgicon.attr("src", "../../../assets/Img/icons/more.svg");
//                 tabtext.css("color", "#333333");
//             }, 1);
//         // $(this).parents('.tab').find('.tab-label-clean-text').css('display','none');
//     }
// });


$('.b2b-tab-label').click(function (e) {

    var that = $(this);
    var filterCheck = $(this).siblings('.b2b-tab-check');

    // If is already checked
    if ((filterCheck).is(':checked')) {
        // Remove its pills
        $(".filtros-pill[idcheck=" + filterCheck.attr('id') + "]").remove();
    }
    // If is not already checked
    else {
        // Create new Pill
        var newPill = '<div class="filtros-pill" idcheck="'
            + filterCheck.attr('id')
            + '">'
            + '<span class="filtros-pill-text">'
            + $(this).text()
            + '</span>'
            + '<i class="fas fa-times icon-close"></i>'
            + '</div>';

        // Add the new Pill
        $("#filtros-pills-wrapper").append(newPill);
    }

    setTimeout(function () {
        var checkFlag = false;
        $(that.parents('.tab').find('.b2b-tab-check')).each(function (index) {
            if ($(this).prop("checked") == true) {
                checkFlag = true;
            }
        });
        if (checkFlag) {
            that.parents('.tab').find('.tab-label-clean-text').css('display', 'flex');
        }
        else {
            that.parents('.tab').find('.tab-label-clean-text').css('display', 'none');
        }
    }, 100);
});

// Remove one pill
// $('#filtros-pills-wrapper').on("click", ".filtros-pill .icon-close", function () {

//     var pill = $(this).parent();
//     var idcheck = $(this).parent().attr('idcheck');

//     $('.b2b-tab-check[id=' + idcheck + ']').click();
//     pill.remove();

//     // Remove Group Clean Label if all pills were deleted
//     setTimeout(function () {
//         $('.b2b-tab-items').each(function (index) {
//             that = $(this);

//             var checkFlag = false;

//             $.map($(this).find('.b2b-tab-check'), function (n, i) {
//                 console.log(n.id);

//                 if ($('.b2b-tab-check[id=' + n.id + ']').is(':checked') == true) {
//                     checkFlag = true;
//                 }
//             });

//             if (checkFlag == false) {
//                 that.parents('.tab').find('.tab-label-clean-text').css('display', 'none');
//                 // that.parent().css('max-height','0');
//                 // imgicon.attr("src", "../../../assets/Img/icons/more.svg");
//             }

//         }, 100);

//     });


// });

// Clean All
$(".filtros-header-clean").on("click", function () {

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


$("#b2b-button-filter-open-modal").on("click", function (e) {
    $(".b2b-container-filter ").css("display", "block");
    $("body").css("overflow", "hidden");

});
$("#close-modal-filter,.b2b-container-modal-layer").on("click", function (e) {
    $(".b2b-container-filter ").css("display", "none");
    $("body").css("overflow", "auto");
});



// PREVENT CACHE
if ( !$( ".b2b-hidden-check").is(':checked')){
    $( ".tab-label-PVP" ).trigger( "click" );
}




// function checkFilters() {

//     $('.b2b-tab-check').each(function (index) {
//         console.log("---")
//         if ($(this).prop("checked") == true) {


//             // Create new Pill
//             var newPill = '<div class="filtros-pill" idcheck="'
//                 + $(this).attr('id')
//                 + '">'
//                 + '<span class="filtros-pill-text">'
//                 + $(this).siblings(".b2b-tab-label").text()
//                 + '</span>'
//                 + '<i class="fas fa-times icon-close"></i>'
//                 + '</div>';

//             // Add the new Pill
//             $("#filtros-pills-wrapper").append(newPill);


//             var tabContent = $(this).parents(".tab-content");
//             var tabLabel = tabContent.siblings(".tab-label");
//             var imgicon = tabLabel.find('.more-icon');
//             var tabtext = tabLabel.find(".tab-label-text");

//             if (imgicon.attr("src") === "../../../assets/Img/icons/more.svg") {
//                 // SetTimeout to syncronized with css transitions
//                 setTimeout(
//                     function () {
//                         imgicon.attr("src", "../../../assets/Img/icons/minus.svg");
//                         tabtext.css("color", "#00609c");
//                     }, 1);

//             }
//             else {
//                 setTimeout(
//                     function () {
//                         imgicon.attr("src", "../../../assets/Img/icons/more.svg");
//                         tabtext.css("color", "#333333");
//                     }, 1);
//                 // $(this).parents('.tab').find('.tab-label-clean-text').css('display','none');
//             }

//             tabLabel.siblings(".b2b-hidden-check").prop("checked", true).trigger("change");
//         }
//     });

// }

// checkFilters();
// Initialize screen size
// var windowHeight;
// var bodyHeight;

// function checkBodyHeight() {

//     windowHeight = $(window).height();
//     bodyHeight = $("body").height();

//     if (bodyHeight < windowHeight) {
//         $('.b2b-footer').css('z-index', '20').css('position', 'fixed').css('bottom', '0').css('width', '100%');
//     }
//     else{
//         $('.b2b-footer').css('position', 'static');
//     }
// }

// // Execute on load
// checkBodyHeight();
// // Bind event listener
// $(window).resize(checkBodyHeight);


// // Observer to body tag

// let resizeObserver = new ResizeObserver(() => { 
//     checkBodyHeight();
// }); 
  
// resizeObserver.observe($("body")[0]); 

$(".b2b-open-fly-card").on("click",function(){


    $("#fly-card").css("display","block");

    setTimeout(function(){ 
        
        $("#fly-card").css("display","none");

     }, 5000);


});



var radioDisabled = false;

$(".b2b-tab-label-sending").on("click", function () {

    if ($(this).parent().find(".b2b-tab-check").prop('checked')) {
        $(".b2b-form-sending").css("display", "none");
        $(':radio').attr('disabled', false);
        $(':radio').parent().css("opacity", "1");
        radioDisabled = false;

    }
    else {
        $(".b2b-form-sending").css("display", "block");
        $(':radio').attr('disabled', true);
        $(':radio').parent().css("opacity", ".5");
        radioDisabled = true;


    }

});


// $(".b2b-form-row-radio").on("click", function () {


//     if (!radioDisabled) {
//         $(".b2b-form-row-radio").find(".b2b-radio-default").addClass("d-none");
//         $(this).find(".b2b-radio-default").removeClass("d-none");
//     }

// }); 

function initApp() {
    if (typeof initPage === "function") {
        initPage();        
    }    
}
  
initApp();  

// Initialize screen size

var windowsize;
var breakpointCard = 1024;



function checkWidth() {
    windowsize = $(window).innerWidth();

}


// Execute on load
checkWidth();
// Bind event listener
$(window).resize(checkWidth);



// MENU

$('#nav-icon3').click(function (e) {
    closeCart();
    $(this).toggleClass('open');
    if ($(this).hasClass('open')) {       
        $('body').addClass('overflow-hidden');
    } 
    else {
        $('body').removeClass('overflow-hidden');
    }
    $('.layer-opacity-menu').toggleClass('dblock');
    $('.b2b-menu-wrapper').toggleClass('dblock');
    $('#b2b-dropdown-button + .b2b-dropdown-items').removeClass('b2b-dropdown-items-visible');
 
    e.stopPropagation();
    e.preventDefault();
});


$('.layer-opacity-menu').click(function () {
    resetMenu();
});



// DROPDOWN MENU


$('#b2b-dropdown-button').click(function (e) {
    closeCart();
    $('#b2b-dropdown-button + .b2b-dropdown-items').toggleClass('b2b-dropdown-items-visible');
    $('body').removeClass('overflow-hidden');
    $('.layer-opacity-menu').removeClass('dblock');
    $('.b2b-menu-wrapper').removeClass('dblock');
    $('#nav-icon3').removeClass('open');
    $('.b2b-menu1-item').removeClass('b2b-menu-underline');
    $('.b2b-menu2-items,.b2b-menu3-items').hide();
    $('.b2b-menu2-item').removeClass('b2b-menu-underline');
    e.stopPropagation();
    e.preventDefault();
});


7
$(document).click(function (e) {
    $('#b2b-dropdown-button + .b2b-dropdown-items').removeClass('b2b-dropdown-items-visible');
});


$('.b2b-header').click(function (e) {
    resetMenu();
    closeCart();
});

// START MENU NAVIGATION

$('.b2b-menu1-item').hover(function (e) {
    if (windowsize >= breakpointCard) {
        $('.b2b-menu1-item').removeClass('b2b-menu-underline');
        $('.b2b-menu2-items,.b2b-menu3-items').hide();
        $(this).find('.b2b-menu2-items').show();
        $(this).addClass('b2b-menu-underline');
    }

});

$('.b2b-menu2-item').hover(function (e) {
    if (windowsize >= breakpointCard) {
        $('.b2b-menu2-item').removeClass('b2b-menu-underline');
        $('.b2b-menu3-items').hide();
        $(this).find('.b2b-menu3-items').show();
        $(this).addClass('b2b-menu-underline');
    }
});


$('.b2b-menu1-item > .b2b-menu-item-more').click(function (e) {

    if (windowsize < breakpointCard) {
        $('.b2b-menu1-item').removeClass('b2b-menu-underline');
        $('.b2b-menu2-items,.b2b-menu3-items').css("display", "none");
        $(this).parent().find('.b2b-menu2-items').show();

    }

});

$('.b2b-menu1-item  .b2b-menu2-item .b2b-menu-item-more').click(function (e) {
    if (windowsize < breakpointCard) {
        $('.b2b-menu2-item').removeClass('b2b-menu-underline');
        $('.b2b-menu3-items').css("display", "none");
        $(this).parent().find('.b2b-menu3-items').addClass("open");

    }
});





$('.b2b-menu-back').click(function (e) {

    if (windowsize < breakpointCard) {
        
        $('.b2b-menu2-items,.b2b-menu3-items').css("display", "none");
        $('.b2b-menu3-items').removeClass("open");
        $(this).parent().parent().parent().show();

    }

});


// END MENU NAVIGATION

// START SHOPPING CART

$("#b2b-shooping-bag-wrapper").click(function (e) {


    changeBag();
    resetMenu(false);
    e.stopPropagation();

});
 
$(".layer-opacity-cart").click(function (e) {
    resetMenu();
    closeCart(); 
    e.stopPropagation();
});


// END SHOPPING CART


$(".b2b-floating-cart-icon-close-all").on("click", function () {
    changeBag();
});

$(".b2b-floating-cart-close").click(function (e) {

    //   $(this).parent().remove();
    $(this).parent().addClass('d-none');
});


// FUNCTIONS

function changeBag() {
    if ($('.b2b-floating-cart').hasClass('dblock')) {
        closeCart();


    } else {
        openCart();
    }

}


// function closeCart() {
//     $('body').removeClass('overflow-hidden');
//     $('.b2b-shopping-bag-img').attr('src', '../../assets/Img/common/shopping-bag.svg');
//     $('.b2b-floating-cart').removeClass('dblock')
//     $('.layer-opacity-cart').removeClass('dblock');
 

// }

// function openCart() {
//     $('body').addClass('overflow-hidden');
//     $('.b2b-shopping-bag-img').attr('src', '../../assets/Img/common/shopping-bag-solid.svg');
//     $('.layer-opacity-cart').addClass('dblock');
//     $('.b2b-floating-cart').addClass('dblock');
    
   
// }
 
function resetMenu(booleano = true) {

    $('#b2b-dropdown-button + .b2b-dropdown-items').removeClass('b2b-dropdown-items-visible');
    $('#nav-icon3').removeClass('open');
    if(booleano){
        $('body').removeClass('overflow-hidden');
    }
   
    $('.layer-opacity-menu').removeClass('dblock');
    $('.b2b-menu-wrapper').removeClass('dblock');
    $('.b2b-menu1-item').removeClass('b2b-menu-underline');
    $('.b2b-menu2-items,.b2b-menu3-items').hide();
    $('.b2b-menu2-item').removeClass('b2b-menu-underline');

}

function resetAll() {
    closeCart();
    resetMenu();
}


function changeIcon(element) {
    text = element.text();
    if (text == '+') {
        element.text('-');
    }
    else {
        element.text('+');
    }
}


$(".b2b-lentes-tabla-text-OD").on("click", function () {

    if ($(document).width() < 1200) {
        $(this).siblings().slideToggle('slow');
        changeIcon($(this).children(".b2b-lentes-more"));
    }


});

$(".b2b-lentes-tabla-text-OI").on("click", function () {
    if ($(document).width() < 1200) {
        $(this).siblings().slideToggle('slow');
        changeIcon($(this).children(".b2b-lentes-more"));
    }
});





function closeModalPosponer(){
    $(".b2b-modal-posponer").css("display","none");
    $("body").css("overflow", "auto");

}

function openModalposponer(){
    console.log("Estoy Dentro")
    $(".b2b-modal-posponer").css("display","flex");
    $("body").css("overflow", "hidden");
}

$(".b2b-button-openModalposponer").on("click", function(){  
    openModalposponer(); 
});


$(".b2b-modal-posponer .b2b-modal-close").on("click", function(){  
    closeModalPosponer(); 
});

$(".b2b-modal-container").on("click", function(e){  
    e.stopPropagation();
});

$(".b2b-modal-posponer").on("click", function(){
    closeModalPosponer();
});






function closeModalPerioidca(){
    $(".b2b-modal-periodica").css("display","none");
    $("body").css("overflow", "auto");

}

function openModalPeriodica(){
    console.log("Estoy Dentro")
    $(".b2b-modal-periodica").css("display","flex");
    $("body").css("overflow", "hidden");
}

$(".b2b-button-openModalPeriodica").on("click", function(){  
    openModalPeriodica(); 
});


$(".b2b-modal-periodica .b2b-modal-close").on("click", function(){  
    closeModalPerioidca(); 
});

$(".b2b-modal-container").on("click", function(e){  
    e.stopPropagation();
});

$(".b2b-modal-periodica").on("click", function(){
    closeModalPerioidca();
});
// NO BORRAR EN MAGNOLIA SE PASA A LA PLANTILLA

// $(".product-button-purchase").on("click",function(){
//     $('body').addClass('overflow-hidden');
//     $(".modal-purchase").css("display","flex");

// });

// $(".modal-purchase, .modal-purchase .modal-purchase-close").on("click",function(){
//     $('body').removeClass('overflow-hidden');
//     $('.modal-purchase').css("display","none");

// });


$('.b2b-product-card .item').hover(function (e) {
  if ($(window).width() > 1023) {

    $('.b2b-product-card .item').css("opacity", "0.4");
    $(this).css("opacity", "1");
     
  }
});


$('.b2b-product-card .item-back select').mouseleave(function (e) {

  $('.b2b-product-card .item').css("opacity", "0.4");
  $(this).closest('.item').css("opacity", "1");
});

$(".b2b-product-card .item").mouseleave(function () {

    if ($(window).width() > 1023) {
      $('.b2b-product-card .item').css("opacity", "1");
    }

});


$(window).resize(resetHover);

function resetHover() {

  $('.b2b-product-card .item').css("opacity", "1");

}
$('.product-combo').on("click", function(e){
  $(".item").addClass("opacity04");
  $(this).parents(".item").removeClass("opacity04");
  $(this).parents(".item-back").addClass("item-back-hover");
});
$('.product-combo select').on("change", function(e){
    setTimeout(function(){ 
        $(".item-back").removeClass("item-back-hover");
    }, 50);
});
$(".item-back").mousemove(function(e){
  e.stopPropagation();
  var parentOffset = $(this).parent().offset(); 
  //or $(this).offset(); if you really just want the current element's offset
  var relX = parseInt(e.pageX - parentOffset.left);
  var relY = parseInt(e.pageY - parentOffset.top);
  h = $(this).height();
  w = $(this).width();
  if(relX < 20 || relY < 20 || relX > (w - 20) || relY > (h - 100) ){
    $(this).removeClass("item-back-hover")
    $(".item").removeClass("opacity04");
  }
});


});



