

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
  		width: 750px;
	}

	body, table, td, p, a, li, blockquote {
	-ms-text-size-adjust: 100%;
	-webkit-text-size-adjust: 100%;
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
<body style="margin: 0; padding: 10px; 10px; 10px; 20px">
<div>

	<img style="height: 62px; width: 170px;" src="${logoHeader}" />
  <p></p>
	<p>${fecha}</p>
  

	
	<h3 style="padding-top: 30px; font-family: Lato, helvetica ,Helvetica, Arial, sans-serif; color: #00609c; line-height: 16px;">${saludation} ${nombreUsuario} </h3>
	<p>${info1}</p>
	<p>${info2}</p>
  	<p></p>
	<table border="0" cellpadding="0" cellspacing="0" width="100%" style="font-family: Lato, helvetica ,Helvetica, Arial, sans-serif; font-size:13px; color:#000000; line-height: 16px; border-collapse:collapse;mso-table-lspace:0pt; mso-table-rspace:0pt;">
		<tbody>
		
			<tr>
				<td>
				<table border="2" cellpadding="0" cellspacing="0" width="40%">
		        <thead style="background-color: #d9e7f0; color: #00609c">
		          <tr>
		            <th scope="col">${infoNumSocio} : ${numSocio}</th>
		          </tr>
		        </thead>
					<tbody>
						<tr>
							<td style="padding-left: 10px;">							
								<p><b>${nombreUsuario}</b></p>
							</td>
						</tr>
						<tr>
							<td style="padding-left: 10px;">							
								<p><b>${direccion}</b></p>
							</td>
						</tr>
						<tr>
							<td style="padding-left: 10px;">							
								<p><b>${codigoPostal} ${poblacion}</b></p>
							</td>
						</tr>
						<tr>
							<td style="padding-left: 10px;">							
								<p><b>${provincia}</b></p>
							</td>
						</tr>
					</tbody>
				</table>
				</td>
			</tr>
		</tbody>
	</table>
  	<p></p>
	<p>${info3}</p>
	<p style="color: #00609c; font-weight: bold;">${info4}</p>
</div>
</body>
</html>