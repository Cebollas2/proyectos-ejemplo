

<html>
<head>	
	<style>
		@import url('https://fonts.googleapis.com/css?family=Lato:400,700');
	</style>
	
	<style type="text/css">/* Hotmail background & line height fixes */
		.ExternalClass {
			width:100% !important;
		}

		.ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font,
		.ExternalClass td, .ExternalClass div, .ExternalClass br {
			line-height: 100%;
		}

		div,button {
			margin:0 !important;
			padding:0 !important;
			display:block !important;
		}

		body, table, td, p, a, li, blockquote {
		-ms-text-size-adjust: 100%;
		-webkit-text-size-adjust: 100%;
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
		background-color: #d9e7f0; 
		color: #00609c;
		font-family: Lato, helvetica, Helvetica;  
		font-size: 12px; 		
	}

	.margen-central{
		margin-left: 246px;
	}
	
	b {
		color: #00609c;
	}	
		
	</style>
	
<style type="text/css">
span.Y { visibility: visible; }
span.N { visibility: hidden; }
</style>
</head>
<body style="margin: 0; padding: 0px;">
<h3 style="font-family: Lato, helvetica ,Helvetica, Arial, sans-serif; color: #00609c; line-height: 16px;">Petición de repuesto registrada de socio ${usuario} </h3>
<table align="center" border="0" cellpadding="0" cellspacing="0" style="font-family: Lato, helvetica ,Helvetica, Arial, sans-serif; font-size:13px; color:#000000; line-height: 16px; border-collapse:collapse;mso-table-lspace:0pt; mso-table-rspace:0pt;width: 100%;">
	<tbody>
	
		<tr>
			<td>
			<table align="center" border="0" cellpadding="15" cellspacing="0" width="100%">
				<tbody>
					<tr>
						<td>							
							<p><b>Marca:</b> ${marca}</p>
						</td>
					</tr>
					<tr>
						<td>							
							<p><b>Modelo:</b> ${modelo}</p>
						</td>
					</tr>
					<tr>
						<td>
							<p><b>Color:</b> ${color}</p>							
						</td>
					</tr>
					<tr>
						<td>
							<p><b>Calibre:</b> ${calibre}</p>							
						</td>
					</tr>
					<tr>
						<td>
							<p><b>Repuestos solicitados: </b></p>	
							<#if varillaDerecha?hasContent && varillaDerecha><p style="margin-left: 5%;">Varilla Derecha</p></#if>
							<#if varillaIzquierda?hasContent && varillaIzquierda><p style="margin-left: 5%;">Varilla Izquierda</p></#if>
							<#if varillaFrente?hasContent && varillaFrente><p style="margin-left: 5%;">Frente</p></#if>						
						</td>
					</tr>
					
					<tr>
						<td>
							<p><b>Comentario de ${usuario}:</b></p> 
							${comentarios}							
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		
		
	
	</tbody>
</table>


</html>