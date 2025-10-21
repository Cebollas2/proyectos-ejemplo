<!DOCTYPE html>

<html>
<style>
	body{
		text-align: center;
	}

	table{
		margin-left: auto;
		margin-right: auto;
		text-align: center;
		width: 600px;
	}

	td{
		text-align: center;
	}

	#logo-container{
		width: 600px; 
		mso-height-alt: 93px;
		display: inline-block;
		text-align: center;
	}

	.logo1{
		text-align: center;
		width: 100%;
	}

	#banner-container{
		width: 600px; 
		height: 262px; 
		mso-height-alt: 262px;
		display: inline-block;
		text-align: center;
	}

	.img-banner{
		width: 100%;
		text-align: center;
	}

	#text-hello-container{
		width: 600px; 
		display: inline; 
		height: 106px; 
		text-align: center;
	}

	.texto-hola{
		color: #00609c; 
		font-family: Lato, helvetica, Helvetica;  
		font-size: 18px; 
		margin-bottom: 0px;
		text-align: center;
	}

	.texto-nombre-optico{
		font-weight: bold;
		text-align: center;
	}

	.texto-solicitud-password{
		font-weight: bold; 
		text-align: center; 
		margin-top: 5px;
		text-align: center;
	}

	#text-password-container{
		padding-bottom: 16px; 
		width: 600px; 
		height: 65px; 
		display: inline-block;
		text-align: center;
	}

	#text-password-subcontainer{
		margin: 0px 0px; 
		padding: 8px 0px 16px 0px; 
		background-color: #00609c;
		text-align: center;
	}

	#text-password-subcontainer p{
		color: #fffffe; 
		font-family: lato, helvetica, Helvetica; 
		font-size: 16px; 
		margin-bottom: 3px; 
		margin-top: 0px;
		text-align: center;
	}

	#text-password-subcontainer span{
		font-weight: bold; 
		color: #fffffe;
		text-align: center;
	}

	#text-advice-container{
		width: 600px; 
		display: inline;
		text-align: center;
	}

	#text-advice-container .linea1{
		font-family: Lato, helvetica, Helvetica;  
		font-size: 13px; 
		color: #1a1919; 
		margin-bottom: 1px; 
		margin-top: 0px;
		text-align: center;
	}

	#text-advice-container .linea2{
		font-family: Lato, helvetica, Helvetica;  
		font-size: 13px; 
		color: #1a1919; 
		margin-top: 1px;
		text-align: center;
	}

	#text-advice-container .linea3{
		font-family: Lato, helvetica, Helvetica;  
		font-size: 15px; 
		color: #00609c; 
		font-weight: bold; 
		margin-top: 7px;
		text-align: center;
	}

	#sublogo-container{
		width: 600px; 
		height: 37px; 
		display: inline;
		text-align: center;
	}

	#sublogo-container img{
		width: 207px; 
		height: 37px; 
		padding: 0px 197px 0px 197px;
		text-align: center;
	}

	#button-container{
		width: 600px; 
		height: 26px; 
		display: inline-block; 
		margin-bottom: 18px;
		text-align: center;
	}

	#boton-entrar{
		padding: 6px 0px 7px 0px; 
		margin: 0px 201px;  
		font-family: Lato, helvetica, Helvetica;  
		font-size: 11.6px; 
		background-color: #00609c; 
		text-align: center;
		color: #fffffe; 
		border-radius: 5px;
		mso-height-alt: 10;
		mso-width-rule: exactly;
	}

	#footer-container{ 
		display: inline-block;
		background-color: #d9e7f0; 
		color: #00609c;
		font-family: Lato, helvetica, Helvetica;  
		font-size: 12px; 
		padding: 0px 36px;
	}

	.margen-central{
		margin-left: 246px;
	}

</style>


<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<style>
		@import url('https://fonts.googleapis.com/css?family=Lato:400,700');
	</style>
</head>
<body>
	<table>
		<tr>
			<td id="logo-container">
				<img class="logo1" src="${logomargenes}" />
			</td>
		</tr>
		<tr>
			<td id="banner-container">
				<img class="img-banner" src="${banner}" />
			</td>
		</tr>
		<tr>
			<td id="text-hello-container">
				<p>&nbsp;</p>
				<h1 class="texto-hola">${line1} <span class="texto-nombre-optico">${name}</span></h1>
				<p class="texto-solicitud-password">${line2}</p>
				<p>&nbsp;</p>
			</td>
		</tr>
		<tr>
			<td id="text-password-container">
				<div id="text-password-subcontainer">
					<p>${line3}</p>
					<p>${pwd}</span></p>
				</div>
			</td>
		</tr>
		<tr>
			<td id="text-advice-container">
				<p class="linea1">${line4}</p>
				<p class="linea2">${line5}</p>
				<p class="linea3">${line6}</p>
			</td>
		</tr>
		<tr>
			<td id="sublogo-container">
				<img src="${logo}" />
			</td>
		</tr>
		<tr>
			<td id="button-container">
				<a href="${pathLogin}" target="_blank">
					<div id="boton-entrar">
						${line12}
					</div>
				</a>
			</td>
		</tr>
		<tr id="footer-container">
			<td>
				<div class="footer-content">
					<p>${line16} <img src="${margencentral}" /><strong>91 640 29 80 · </strong>${line15}</p>
				</div>
			</td>
		</tr>
	</table>
</body>



</html>