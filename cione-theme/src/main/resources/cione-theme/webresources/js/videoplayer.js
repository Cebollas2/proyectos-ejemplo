/* Light YouTube Embeds by @labnol */
/* Web: http://labnol.org/?p=27941 */
 
document.addEventListener("DOMContentLoaded",
    function() {
        var div, n,
            v = document.getElementsByClassName("youtube-player");
        for (n = 0; n < v.length; n++) {
            div = document.createElement("div");
            div.setAttribute("data-id", v[n].dataset.id);
            if (v[n].dataset.thumbnail != undefined){
                div.setAttribute("data-thumbnail", v[n].dataset.thumbnail);
            }else{
                div.setAttribute("data-thumbnail", 'no-data');
            }
            div.innerHTML = labnolThumb(v[n].dataset.id,v[n].dataset.thumbnail);
            div.onclick = labnolIframe;
            v[n].appendChild(div);
        }

    });

function labnolThumb(id, thumbnail) {
    if (thumbnail != undefined){
        //var thumb = '<img src="'+thumbnail+'">',
        var thumb = '',
            vTitle = '<div class="overVideo"><div class="play small"></div><span class="subtitleText">Watch the video</span>';
            thumbToBack(thumbnail);
    }else{
        var thumb = '',
            vTitle = '<div class="overVideo"><span class="videoTitle"><span class="strongText">Applus+</span> corporate</span><div class="play"></div><span class="subtitleText">Watch the video</span>';
            thumbToBack(null);
    }
    return thumb.replace("ID", id) + vTitle;
}

function labnolIframe() {
    var iframe = document.createElement("iframe");
    var embed = "https://www.youtube.com/embed/ID?autoplay=1";
    iframe.setAttribute("src", embed.replace("ID", this.dataset.id));
    iframe.setAttribute("frameborder", "0");
    iframe.setAttribute("allowfullscreen", "1");
    this.parentNode.replaceChild(iframe, this);
}


/** Video Thumbanil to Background **/
function thumbToBack(thumbSRC){
    //$('.youtube-player').css('background-image','url('+thumbSRC+')');
    if ($(window).width() > 991){
        var imgSRC = $('.videoContainer').attr('data-src-desktop');
    }else{
        if ($(window).width() >= 576){
            var imgSRC = $('.videoContainer').attr('data-src-tablet');
        }else{
            var imgSRC = $('.videoContainer').attr('data-src-smartphone');
        }
    }
    $('.youtube-player').css('background-image', 'url("'+imgSRC+'")');
}
 