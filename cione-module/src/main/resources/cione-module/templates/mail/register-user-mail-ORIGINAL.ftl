<!DOCTYPE html>

<html>

<style>
	body{
		text-align: center;
	}
	body{
		text-align: center;
	}

	table{
		margin-left: auto;
		margin-right: auto;
		text-align: center;
		width: 600px;
	}
	tr{
		display: inline-block;
	}

	td{
		text-align: center;
	}
	#logo-container{
		width: 601px; 
		height: 93px; 
		display: inline-block;
	}

	.logo1{
		width: 600px;
	}

	#banner-container{
		width: 601px; 
		display: inline-block;
	}

	.img-banner{
		width: 601px; 
		height: 332px;
	}

	#text-hello-container{
		width: 601px; 
		display: inline-block; 

		padding-right: 32px;
		padding-left: 32px;
	}

	.texto-hola{
		font-family: Lato, Arial, helvetica !important; 
		font-size: 18px; 
		margin-top: 39px; 
		margin-bottom: 0px;
	}

	.texto-nombre-optico{
		color: #00609c; 
		font-weight: bold;
	}

	.texto-bienvenida{
		font-size: 16px; 
		text-align: left; 
		font-family: Lato, Arial, helvetica !important; 
		padding-top: 0;
		padding-right: 32px;
		margin-bottom: 5px;
		padding-left: 32px;
	}

	#texto-credenciales{
		padding-bottom: 16px; 
		width: 601px; 
		height: 110px; 
		display: inline-block;
	}

	#text-password-subcontainer{
		/*	margin: 0px 0px; */
		padding: 16px 0px; 
		background-color: #00609c;
	}

	#text-password-subcontainer p{
		color: #fffffe; 
		font-family: lato, helvetica, Helvetica; 
		font-size: 16px; 
		margin-bottom: 3px; 
		margin-top: 0px;
	}

	#text-password-subcontainer .credencial{ 
		color: #fffffe;
	}

	#text-advice-container{
		width: 601px; 
		display: inline-block;
		font-weight: bold;
		margin-top: 5px;
	}

	#text-advice-container .linea1{
		font-family: Lato, Arial, helvetica !important; 
		font-size: 13px; 
		color: #1a1919; 
		margin-bottom: 1px; 
		margin-top: 0px;
	}


	#text-advice-container .linea2{
		font-family: Lato, Arial, helvetica !important; 
		font-size: 15px; 
		color: #00609c;  
		margin-top: 7px;
	}

	#sublogo-container{
		width: 601px; 
		height: 37px; 
		display: inline-block;
		margin-top: 14px;
	}

	#sublogo-container img{
		width: 207px; 
		height: 37px; 
		padding: 0px 197px 0px 197px;
	}

	#button-container{
		width: 601px; 
		height: 26px; 
		display: inline-block; 
		margin-bottom: 18px;
	}

	#boton-entrar{
		padding: 6px 0px 7px 0px; 
		margin: 0px 201px;  
		font-family: Lato, Arial, helvetica !important; 
		font-size: 11.6px;  
		color: #fffffe; 
		background-image: url('img/boton.png');
		background-repeat: no-repeat;
		background-position: center;
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

	.texto-azul{
		color: #00609c;
	}

	#texto-parrafo-2{
		width: 601px; 
		display: inline-block; 

		padding-right: 32px;
		padding-left: 32px;
	}

	#texto-parrafo-2 p{
		color: #202020;
		font-size: 13px;
		text-align: left; 
		font-family: Lato, Arial, helvetica !important; 
		padding-top: 0;
		padding-right: 32px;
		padding-left: 32px;
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
				<h1 class="texto-hola">${line1} <span class="texto-nombre-optico">${name}</span></h1>
				<p class="texto-bienvenida">${line2a} <span class="texto-azul">MyCione online,</span>${line2b} <strong>${line2c}</strong>!</p>
			</td>
		</tr>
		<tr>
			<td id="texto-parrafo-2">
				<ul>
					<li><p>${line3a} <strong>${line3b}</strong></p></li>
					<li><p>${line4}</p></li>
					<li><p>${line5}</p></li>
					<li><p>${line6}</p></li>
				</ul>
			</td>
		</tr>
		<tr>
			<td id="text-advice-container">
				<p class="linea1">${line7a} <span class="texto-azul">${line7b}</span></p>
				<p class="linea2">${line8}</p>
			</td>
		</tr>
		<tr>
			<td id="texto-credenciales">
				<div id="text-password-subcontainer">
					<p>
						${line9}
					</p> 
					<br>
					<p class="credencial"><strong>${line10}</strong>${idClient}</span></p>
					<p class="credencial"><strong>${line11}</strong>${pwd}</span></p>
				</div>
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
					<p>${line16}<img src="${margencentral}" /><strong>91 640 29 80 · </strong>${line15}</p>
				</div>
			</td>
		</tr>
	</table>
</body>

</html>