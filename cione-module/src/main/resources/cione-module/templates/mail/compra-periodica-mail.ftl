<html lang="es">

<head>

  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  
  <title>${idpurchase!""}</title>
  
  <style>
  
    @font-face {
      font-family: 'Lato';
      src: url('Lato-regular.ttf') format('truetype');
      font-weight: 100;
      font-style: normal;
      font-display: swap;
    }

    body {
      font-family: 'Lato';
      color: #3e3e3e;
      margin: 40px 65px;
      max-width: 1228px;
      margin: 0 auto;
      padding: 10px 40px 40px;
    }

    header {
      border-bottom: solid 1px #e5e5e5;
      padding-bottom: 10px;
    }

    .b2b-email-wrapper {
      margin-top: 30px;
      font-size: 15px;
      font-weight: normal;
      font-stretch: normal;
      font-style: normal;
      line-height: normal;
      letter-spacing: normal;
      color: #3e3e3e;
      display: flex;
    }

    .b2b-email-wrapper-column {
      margin-top: 10px;
      font-size: 15px;
      font-weight: normal;
      font-stretch: normal;
      font-style: normal;
      line-height: normal;
      letter-spacing: normal;
      color: #3e3e3e;
      display: flex;
      flex-direction: column;
      margin-top: 50px;
    }
    
    .b2b-email-title {
      font-size: 18px;
      font-weight: 900;
      font-stretch: normal;
      font-style: normal;
      line-height: normal;
      letter-spacing: normal;
      color: #333;
      margin-bottom: 15px;
    }

    .b2b-email-w-100 {
      width: 100%;
    }

    .b2b-email-container {
      padding: 10px 10px 30px;
      border-bottom: solid 2px #545567;
    }

    .b2b-email-subtitle {
      font-size: 14px;
      font-weight: 900;
      font-stretch: normal;
      font-style: normal;
      line-height: normal;
      letter-spacing: normal;
      color: #3e3e3e;
    }

    table tr {
      height: 30px;
    }

    table tr td {
      vertical-align: middle;
      padding-right: 10px;
    }

    .b2b-email-color{
      border-radius: 50%;
      width: 29px;
      height: 29px;
    }

    .b2b-mail-total{
      font-size: 17px;
      font-weight: 900;
      font-stretch: normal;
      font-style: normal;
      line-height: normal;
      letter-spacing: normal;
      color: #333;
      margin-top: 60px;
      display: block;
      flex-direction: column;
      align-items: flex-end;
      text-align:right;
      width:100%;
    }

    .b2b-mail-total > *{
      width: 100%;
      margin-bottom: 10px;
    }
  </style>
</head>

	<body>
	
		<div style="border-bottom: solid 1px #e5e5e5; padding-bottom: 10px;">
			<img src='${logoHeader!""}' />
		</div>
		
		<br>&nbsp;<br>
		
		<section class="b2b-email-wrapper">
			<div class="b2b-email-w-100">
				<div class="b2b-email-title">Estimado Socio</div>
				<div class="b2b-email-text">En 2 días vence tu <b>Compra periódica ${idpurchase!""}</b> que contiene los siguientes artículos*</div>
			</div>
		</section>
		
		<br>&nbsp;<br>
	  
	  	${listado!""}
	
		<section>
		  <div class="b2b-mail-total">
		    <div>TOTAL</div>
		    <div>${total!""} €</div>
		  </div>
		</section>
		<br>&nbsp;<br>
		<section class="b2b-email-wrapper">
		  <div class="b2b-email-w-100">
		    <div class="b2b-email-text">* El PVO reflejado se corresponde con el vigente a la fecha de creación de esta compra rápida y es susceptible de cambios o actualizaciones
		    </div>
		  </div>
		</section>
		<br>&nbsp;<br>
		<section class="b2b-email-wrapper">
		  <div class="b2b-email-w-100">
		    <div class="b2b-email-text">
		    </div>
		  </div>
		</section>
		
	</body>

</html>