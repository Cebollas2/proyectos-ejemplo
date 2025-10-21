
$('.owl-carousel-banner').owlCarousel({
    loop:true,
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




// $('.owl-carousel-products').owlCarousel({
//     loop:false,
//     margin:10,
//     dots: false,
//     nav:true,
//     autoWidth:true,
//     mouseDrag:false,
//     responsive:{
//         0:{
//             items:1,
//             autoWidth: false,
//             nav: false,
//             margin: 10,
//             center: true
//         },
//         410:{
//             items:8,
           
//         },
//         1000:{
//             items:10,
          
//         }
//     }
// });

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

// DELETE ITEM IN CART PAGE

$('.b2b-cart-item-delete').on("click", function () {
    $(this).parent().remove();
});



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
$(".b2b-open-fly-card").on("click",function(){


    $("#fly-card").css("display","block");

    setTimeout(function(){ 
        
        $("#fly-card").css("display","none");

     }, 5000);


});
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
    $('body').toggleClass('overflow-hidden');
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
        console.log("a")
        $('.b2b-menu2-items,.b2b-menu3-items').css("display", "none");
        $('.b2b-menu3-items').removeClass("open");
        $(this).parent().parent().parent().show();

    }

});


// END MENU NAVIGATION

// START SHOPPING CART

$(".b2b-shooping-bag-wrapper").click(function (e) {


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

// NO BORRAR EN MAGNOLIA SE PASA A LA PLANTILLA

// $(".product-button-purchase").on("click",function(){
//     $('body').addClass('overflow-hidden');
//     $(".modal-purchase").css("display","flex");

// });

// $(".modal-purchase, .modal-purchase .modal-purchase-close").on("click",function(){
//     $('body').removeClass('overflow-hidden');
//     $('.modal-purchase').css("display","none");

// });


$('.b2b-product-card .item-back').hover(function (e) {
   if ($( window ).width() > 1023){

  
    $('.item-back').css("opacity","0.4");
    $('.item-front').css("opacity","0.4");
    $(this).css("opacity","1");
}
});


$( ".b2b-product-card .item-back" ).mouseleave(function() {
    if ($( window ).width() > 1023){
    $('.item-back').css("opacity","1");
    $('.item-front').css("opacity","1");
    }
  });


$(window).resize(resetHover);

function resetHover(){
    
    $('.item-back').css("opacity","1");
    $('.item-front').css("opacity","1");

}