var $headerSlider;
var $newsSlider;
var $successSlider;
var $last4newsSlider;
var $newDetailSlider;
var $ourHistorySlider;
var $ourHistoryCaptions;
var $historyBrandYears;
var $historyBrandContent;
var $serviceImages;
var $categoriesSlider;
var $workingSlider;

function initSliders(){
    $headerSlider = $('#headerImageCarousel').lightSlider({
        item:1,
        loop:true,
        slideMargin:0,
        slideMove:1,
        controls:true,
        pager:false,
        adaptiveHeight:false,
        addClass:'headerSlider',
        pauseOnHover:true,
        auto: true,
        pause:4000,
        onSliderLoad: function() {
            $('#headerImageCarousel').find('li').each(function(){
                if ($(window).width() > 991){
                    var imgSRC = $(this).attr('data-src-desktop');
                }else{
                    if ($(window).width() >= 576){
                        var imgSRC = $(this).attr('data-src-tablet');
                    }else{
                        var imgSRC = $(this).attr('data-src-smartphone');
                    }
                }
                if (!$(this).hasClass('degraded')){
                    $(this).css('background-image', 'url("'+imgSRC+'")');
                }else{
                    var imgBack = $(this).attr('data-src-degraded');
                    $(this).find('img').first().attr("src",imgSRC);
                    $(this).css('background-image', 'url("'+imgSRC+'"), url("'+imgBack+'")');
                }
            });
        },
        onAfterSlide: function () {
            $('#headerImageCarousel iframe').remove();
            $('#headerImageCarousel .play').removeClass('hidden');
        }
    });
	
	$newsSlider = $('#carouselNewsLS').lightSlider({
        item:4,
        loop:false,
        slideMargin:0,
        slideMove:1,
        controls: false,
        pager: false,
        easing: 'cubic-bezier(0.25, 0, 0.25, 1)',
        speed:600,
        adaptiveHeight:true,
        onSliderLoad: function() {
            $('#carouselNewsLS').find('li').each(function(){
                var backSRC =  $(this).find('a').find('.imageNews').attr('data-img-src');
                $(this).find('a').find('.imageNews').css('background-image', 'url("'+backSRC+'")');
            });
        },
        responsive : [
            {
                breakpoint:992,
                settings: {
                    item:4,
                    slideMove:1,
                    slideMargin:0,
                    controls: false,
        			pager: false,
                    vertical:true,
                    verticalHeight:536,
                    adaptiveHeight:true
                  }
            },
            {
                breakpoint:576,
                settings: {
                    item:1,
                    slideMove:1,
                    slideMargin:0,
                    controls: false,
        			pager: true,
                    adaptiveHeight:true
                  }
            }
        ]
    }); 

    $successSlider = $('#carouselSucStoriesLS').lightSlider({
        item:3,
        loop:false,
        slideMargin:0,
        slideMove:1,
        controls: false,
        pager: false,
        easing: 'cubic-bezier(0.25, 0, 0.25, 1)',
        speed:600,
        onSliderLoad: function() {
            $('#carouselSucStoriesLS').find('li').each(function(){
                var backSRC =  $(this).find('a').find('.imageSS').attr('data-img-src');
                $(this).find('a').find('.imageSS').css('background-image', 'url("'+backSRC+'")');
            });
        },
        responsive : [
            {
                breakpoint:992,
                settings: {
                    item:3,
                    slideMove:1,
                    slideMargin:0,
                    controls: false,
        			pager: false,
                    vertical:true,
                    verticalHeight:536
                  }
            },
            {
                breakpoint:576,
                settings: {
                    item:1,
                    slideMove:1,
                    slideMargin:0,
                    controls: false,
        			pager: true
                  }
            }
        ]
    }); 

    $last4newsSlider = $('#carouselLast4NewsLS').lightSlider({
        item:4,
        loop:false,
        slideMargin:0,
        slideMove:1,
        controls: false,
        pager: false,
        easing: 'cubic-bezier(0.25, 0, 0.25, 1)',
        speed:600,
        adaptiveHeight:true,
        onSliderLoad: function() {
            $('#carouselLast4NewsLS').find('li').each(function(){
                var backSRC =  $(this).find('a').find('.imageNews').attr('data-img-src');
                $(this).find('a').find('.imageNews').css('background-image', 'url("'+backSRC+'")');
            });
        },
        responsive : [
            {
                breakpoint:992,
                settings: {
                    item:4,
                    slideMove:1,
                    slideMargin:0,
                    controls: false,
                    pager: false,
                    vertical:true,
                    verticalHeight:536,
                    adaptiveHeight:true
                }
            },
            {
                breakpoint:576,
                settings: {
                    item:1,
                    slideMove:1,
                    slideMargin:0,
                    controls: false,
                    pager: true,
                    adaptiveHeight:true
                  }
            }
        ]
    });

    $newDetailSlider = $('.carouselNewDetail').lightSlider({
        auto:true,
        loop:true,
        item:1,
        slideMargin:0,
        slideMove:1,
        speed:600,
        pause:3000,
        controls:false,
        onSliderLoad: function() {
            $('.carouselNewDetail').find('li').each(function(){
                var backSRC =  $(this).attr('data-back-src');
                $(this).css('background-image', 'url("'+backSRC+'")');
            });
        }
    });

    $ourHistorySlider = $('#ourhistoryList').lightSlider({
        item:1,
        loop:false,
        slideMargin:0,
        slideMove:1,
        controls:false,
        pager:false,
        enableTouch:false,
        adaptiveHeight:false,
        onSliderLoad: function() {
            $('#ourhistoryList').find('li').each(function(){
                if ($(window).width() > 991){
                    var imgSRC = $(this).find('.infoContainer').attr('data-src-image');
                    if (!$(this).find('.infoContainer').hasClass('degreed')){
                        $(this).find('.infoContainer').css('background-image', 'url("'+imgSRC+'")');
                    }else{
                        var imgBack = $(this).find('.infoContainer').attr('data-src-image-degreed');
                        $(this).find('.infoContainer').css('background-image', 'url("'+imgSRC+'"), url("'+imgBack+'")');
                    }
                }else{
                    if ($(window).width() >= 576){
                        var imgSRC = $(this).find('.infoContainer').attr('data-src-image-tablet');
                        $(this).find('.infoContainer').css('background-image', 'url("'+imgSRC+'")');
                    }else{
                        var imgSRC = $(this).find('.infoContainer').attr('data-src-image-smartphone');
                        $(this).find('.infoContainer').css('background-image', 'url("'+imgSRC+'")');
                    }
                }
            });
        }
    });

    $ourHistoryCaptions = $('.captionsOH').lightSlider({
        item:1,
        loop:false,
        slideMargin:0,
        slideMove:1,
        controls:false,
        pager:false,
        adaptiveHeight:true,
        enableTouch:false
    });

    $historyBrandYears = $('.listYears').lightSlider({
        loop: false,
        slideMargin:0,
        controls:false,
        pager:false,
        adaptiveHeight:false,
        enableTouch:true,
        autoWidth:false
    });

    $historyBrandContent = $('.listHistory').lightSlider({
        loop: false,
        slideMargin:0,
        slideMove:1,
        item:1,
        controls:false,
        pager:false,
        adaptiveHeight:true,
        enableTouch:true
    });

    $serviceImages = $('.listImages').lightSlider({
        auto:true,
        loop:true,
        item:1,
        slideMargin:0,
        slideMove:1,
        speed:600,
        pause:3000,
        controls:false,
        onSliderLoad: function() {
            $('.listImages').find('li').each(function(){
                if ($(window).width() > 991){
                    var imgSRC = $(this).attr('data-src-desktop');
                }else{
                    if ($(window).width() >= 576){
                        var imgSRC = $(this).attr('data-src-tablet');
                    }else{
                        var imgSRC = $(this).attr('data-src-smartphone');
                    }
                }
                $(this).css('background-image', 'url("'+imgSRC+'")');
            });
        }
    });

    $categoriesSlider = $('.categoriesList').lightSlider({
        item:1,
        loop:false,
        slideMargin:0,
        slideMove:1,
        controls:false,
        pager:true,
        enableTouch:false,
        adaptiveHeight:false,
        onSliderLoad: function() {
            $('.categoriesList').find('.imageCategory').each(function(){
                var backSRC =  $(this).attr('data-img-src');
                $(this).css('background-image', 'url("'+backSRC+'")');
            });
        }
    });

    $workingSlider = $('.carouselWorking').lightSlider({
        item:1,
        loop:false,
        slideMargin:0,
        slideMove:1,
        controls:false,
        pager:true,
        enableTouch:false,
        adaptiveHeight:false
    });
}

function disableArrows(){
    if (($historyBrandContent.getCurrentSlideCount() == 1) || ($historyBrandYears.getCurrentSlideCount() == 1)){
        $('.pagerCarousel .prev').addClass('disable');
    }else{
        $('.pagerCarousel .prev').removeClass('disable');
    }

    if (($historyBrandContent.getCurrentSlideCount() == ($('.listHistory li').length +1)) || ($historyBrandYears.getCurrentSlideCount() == ($('.listYears li').length +1))){
        $('.pagerCarousel .next').addClass('disable');
    }else{
        $('.pagerCarousel .next').removeClass('disable');
    }

    if ($categoriesSlider.getCurrentSlideCount() == 1){
        $('.pagerCarousel .prev').addClass('disable');
    }else{
        $('.pagerCarousel .prev').removeClass('disable');
    }

    if ($categoriesSlider.getCurrentSlideCount() == ($('.categoriesList li').length +1)){
        $('.pagerCarousel .next').addClass('disable');
    }else{
        $('.pagerCarousel .next').removeClass('disable');
    }

    if ($workingSlider.getCurrentSlideCount() == ($('.carouselWorking li').length +1)){
        $('.pagerCarousel .next').addClass('disable');
    }else{
        $('.pagerCarousel .next').removeClass('disable');
    }
}

$(document).ready(function() {
    var $numNews = $('#carouselNewsLS li').length;
	var $numPages = $numNews / 4;
	for (var i=0; i<$numPages; i++) {
		var $myNewElement = $('<li id="'+i+'" class="actLeft"><a ></a></li>');
		$myNewElement.appendTo('.selectorBloques ul');
		$(".selectorBloques ul li:first").addClass("active");
	}
	
	$(".actLeft").bind("click", function(){
		$newsSlider.goToPrevSlide();
		checkPage();
	}); 
	
    $(".actRight").bind("click", function(){
		$newsSlider.goToNextSlide();
		checkPage();
	}); 
	
	$(".selectorBloques ul").on('click','li',function(){
		$(".selectorBloques ul li").removeClass("active");
    	$(this).addClass("active");
    	$newsSlider.goToSlide(parseInt(this.id) * 4);
	});	

    if ($('.listYears').length > 0){
        $listaAnnos = $('.listYears').find('li').find('a');
        $listaAnnos.each(function(){
            $(this).on('click',function(){
                var eqListado = $(this).parents('li').index();
                $historyBrandContent.goToSlide(eqListado);
                $listaAnnos.not(this).parent().removeClass('active');
                $(this).parent().addClass('active');
                return false;
            });
        });
    }

    if ($(window).width() >= 992){
        if ($('.prevYearArrow').length > 0){
            $totalPrev = $('#ourhistoryList').find('.prevYearArrow');
            $totalPrev.each(function(){
                $(this).on('click', function(){
                    var itemListado = $(this).parents('li').index();
                    $ourHistorySlider.goToSlide(itemListado-1);
                    $('.yearSelector li:not(:eq('+(itemListado-1)+'))').removeClass('active');
                    $('.yearSelector li:eq('+(itemListado-1)+')').addClass('active');
                    return false;
                });
            });
        }

        if ($('.nextYearArrow').length > 0){
            $totalNext = $('#ourhistoryList').find('.nextYearArrow');
            $totalNext.each(function(){
                $(this).on('click', function(){
                    var itemListado = $(this).parents('li').index();
                    $ourHistorySlider.goToSlide(itemListado+1);
                    $('.yearSelector li:not(:eq('+(itemListado+1)+'))').removeClass('active');
                    $('.yearSelector li:eq('+(itemListado+1)+')').addClass('active');
                    return false;
                });
            });
        }

        if ($('.yearSelector').length > 0){
            $totalYears = $('.yearSelector').find('li').find('a');
            $totalLIs = $('.yearSelector').find('li');
            $totalYears.each(function(){
                $(this).on('click',function(){
                    $(this).parent().addClass('active');
                    $totalLIs.not($(this).parent()).removeClass('active');
                    var itemListado = $(this).parents('li').index();
                    $ourHistorySlider.goToSlide(itemListado);
                    return false;
                });
            });
        }

        if ($('.contentHistoryList').length > 0){
            $('.pagerCarousel').find('.prev').on('click',function(){
                $historyBrandContent.goToPrevSlide();
                $('.listYears li:not(:eq('+($historyBrandContent.getCurrentSlideCount() - 1)+'))').removeClass('active');
                $('.listYears li:eq('+($historyBrandContent.getCurrentSlideCount() - 1)+')').addClass('active');
            });
            $('.pagerCarousel').find('.next').on('click',function(){
                $historyBrandContent.goToNextSlide();
                $('.listYears li:not(:eq('+($historyBrandContent.getCurrentSlideCount() - 1)+'))').removeClass('active');
                $('.listYears li:eq('+($historyBrandContent.getCurrentSlideCount() - 1)+')').addClass('active');
            });
        }

        

    }else{
        if ($('.prevYearArrow').length > 0){
            $totalPrev = $('.captionsOH').find('.prevYearArrow');
            $totalPrev.each(function(){
                $(this).on('click', function(){
                    var itemListado = $(this).parents('li').index();
                    var indice = itemListado-1;
                    $ourHistoryCaptions.goToSlide(indice);
                    $ourHistorySlider.goToSlide(indice);
                    var textoDD = $('.filterYear ul.dropdownYears li.dropdown a').first().next('ul').find('li:eq('+indice+')').find('a').text();
                    $('.filterYear ul.dropdownYears li.dropdown a').first().find('span').text(textoDD);
                    $('.filterYear ul.dropdownYears li.dropdown a').first().next('ul').find('li:eq('+indice+')').addClass('active');
                    $('.filterYear ul.dropdownYears li.dropdown a').first().next('ul').find('li:not(:eq('+indice+'))').removeClass('active');
                    return false;
                });
            });
        }

        if ($('.nextYearArrow').length > 0){
            $totalNext = $('.captionsOH').find('.nextYearArrow');
            $totalNext.each(function(){
                $(this).on('click', function(){
                    var itemListado = $(this).parents('li').index();
                    var indice = itemListado + 1;
                    $ourHistoryCaptions.goToSlide(indice);
                    $ourHistorySlider.goToSlide(indice);
                    var textoDD = $('.filterYear ul.dropdownYears li.dropdown a').first().next('ul').find('li:eq('+indice+')').find('a').text();
                    $('.filterYear ul.dropdownYears li.dropdown a').first().find('span').text(textoDD);
                    $('.filterYear ul.dropdownYears li.dropdown a').first().next('ul').find('li:eq('+indice+')').addClass('active');
                    $('.filterYear ul.dropdownYears li.dropdown a').first().next('ul').find('li:not(:eq('+indice+'))').removeClass('active');
                    return false;
                });
            });
        }

        if (($('.filterYear').length > 0) && (($('.nextYearArrow').length > 0) || ($('.prevYearArrow').length > 0))){
            $totalLinks = $('.filterYear ul.dropdownYears li.dropdown a').first().next('ul').find('li').find('a');
            $totalLinks.each(function(){
                $(this).on('click', function(){
                    var indice = $(this).parent().index();
                    $(this).parent().parent().prev('a').find('span').text($(this).text());
                    $('.dropdownYears').find('li.dropdown.open').removeClass('open');
                    $totalLinks.parent().not($(this).parent()).removeClass('active');
                    $(this).parent().addClass('active');
                    $ourHistoryCaptions.goToSlide(indice);
                    $ourHistorySlider.goToSlide(indice);
                    return false;
                });
            });
        }
    }

    if ($('.categoriesList').length > 0){
        $('.pagerCarousel').find('.prev').on('click',function(){
            $categoriesSlider.goToPrevSlide();
        });
        $('.pagerCarousel').find('.next').on('click',function(){
            $categoriesSlider.goToNextSlide();
        });
    }

    if ($('.carouselWorking').length > 0){
        $('.pagerCarousel').find('.prev').on('click',function(){
            $workingSlider.goToPrevSlide();
        });
        $('.pagerCarousel').find('.next').on('click',function(){
            $workingSlider.goToNextSlide();
        });
    }
});
  
/* Resize function */
function resizeFunctionCarousel(){
    // Reiniciamos los carruseles
    if ($('#carouselNewsLS').length > 0){
        if (!$newsSlider){
    		$newsSlider = $('#carouselNewsLS').lightSlider();
    	}
        $newsSlider.destroy();
    }
    if ($('#carouselSucStoriesLS').length > 0){
        if (!$successSlider){
    		$successSlider = $('#carouselSucStoriesLS').lightSlider();
    	}
    	$successSlider.destroy();
    }
    if ($('#headerImageCarousel').length > 0){
        if (!$headerSlider){
            $headerSlider = $('#headerImageCarousel').lightSlider();
        }
        $headerSlider.destroy();
    }
    
    if ($('#carouselLast4NewsLS').length > 0){
        if (!$last4newsSlider){
            $last4newsSlider = $('#carouselLast4NewsLS').lightSlider();
        }
        $last4newsSlider.destroy();
    }

    if ($('.carouselNewDetail').length > 0){
        if (!$newDetailSlider){
            $newDetailSlider = $('.carouselNewDetail').lightSlider();
        }
        $newDetailSlider.destroy();
    }

    if ($('.carouselOurHistory').length > 0){
        if (!$ourHistorySlider){
            $ourHistorySlider = $('.carouselOurHistory').lightSlider();
        }
        $ourHistorySlider.destroy();
    }

    if (($(window).width() < 992) && ($('.captionsOurHistory').length > 0)){
        if (!$ourHistoryCaptions){
            $ourHistoryCaptions = $('.captionsOH').lightSlider();
        }
        $ourHistoryCaptions.destroy();
    }

    if ($('.contentHistoryList').length > 0){
        if (!$historyBrandYears){
            $historyBrandYears = $('.listYears').lightSlider();
        }
        $historyBrandYears.destroy();

        if (!$historyBrandContent){
            $historyBrandContent = $('.listHistory').lightSlider();
        }
        $historyBrandContent.destroy();
    }

    if ($('.listImages').length > 0){
        if (!$serviceImages){
            $serviceImages = $('.listImages').lightSlider();
        }
        $serviceImages.destroy();
    }

    if ($('.carouselCategories').length > 0){
        if (!$categoriesSlider){
            $categoriesSlider = $('.carouselCategories').lightSlider();
        }
        $categoriesSlider.destroy();
    }

    if ($('.carouselWorking').length > 0){
        if (!$workingSlider){
            $workingSlider = $('.carouselWorking').lightSlider();
        }
        $workingSlider.destroy();
    }
    
	initSliders();
}

function checkPage(){
	var currentIndex = $('#carouselNewsLS li.active').index();

	$(".selectorBloques ul li").removeClass("active");
	var item1 = $( ".selectorBloques ul li" )[ ~~(currentIndex/4) ];
	$(".selectorBloques ul" ).find( item1 ).addClass("active");
}

$(window).on('resize',function () {
    resizeFunctionCarousel();
}).resize();

// Reposition Overlay when resizing window or changing mobile device orientation
window.addEventListener("orientationchange", resizeFunctionCarousel, false);