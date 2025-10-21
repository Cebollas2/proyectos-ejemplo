<!-- VIDEO -->

<p>VIDEO</p>
[#assign resourcesURL = "${ctx.contextPath}/.resources/cione-theme/webresources"]
[#assign urlVideo = "https://www.youtube.com/embed/egFm6MHLvYk"]

 <div class="b2b-detail-images-wrapper">
  <div class="b2b-detalle-imagen">
    <div class="b2b-detalle-imagen-principal">
        <!-- SI EL VIDEO  ES EL PRIMERO QUITARLE EL STYLE DISPLAY NONE Y AÑADIRSELO AL CONTENEDOR DE ARRIBA-->
        <iframe src="${urlVideo}" class="b2b-detalle-video" style="display:none" width="560" height="315" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
    </div>

    <ul class="b2b-detalle-miniaturas">
        <li class="b2b-detalle-miniaturas-item  b2b-detalle-miniaturas-video">
        	[#assign playIcon = resourcesURL + "/img/myshop/common/play.svg"!]
            <img class="b2b-detalle-miniaturas-imagen" src="${playIcon}" alt="">
        </li>
    </ul>
    
  </div>
</div>