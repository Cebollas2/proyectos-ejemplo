package com.magnolia.cione.controller.secure;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.DashBoardDTO;
import com.magnolia.cione.service.DashBoardService;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.EndpointDefinition;

@Path("/private/dashboard/v1")
public class DashBoardEndPoint<D extends EndpointDefinition> extends AbstractEndpoint<D> {

	private static final Logger log = LoggerFactory.getLogger(InteraccionEndpoint.class);
	
	@Inject
	private DashBoardService dashBoardService;

	@Inject
	protected DashBoardEndPoint(D endpointDefinition) {
		super(endpointDefinition);
	}
	
	
	@GET
	@Path("/data-dashboard")
	@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
	public Response getDataDashBoard(){
		try {
			
			
			
			String datos = "{\"currentStatusDirect\":{\"datasets\":[{\"backgroundColor\":[\"#F3AE25\",\"#FEF2C8\"],\"data\":[1,9],\"hoverBackgroundColor\":[\"#F3AE25\",\"#FEF2C8\"]}],\"labels\":[\"Valor 1\",\"Valor 2\"]},\"contactLenses\":{\"datasets\":[{\"backgroundColor\":[\"#F3AE25\",\"#3FA9F5\"],\"data\":[1,9],\"hoverBackgroundColor\":[\"#F3AE25\",\"#3FA9F5\"]}],\"labels\":[\"Subir Categoría\",\"Facturado\"]},\"nextStateDirect\":{\"datasets\":[{\"backgroundColor\":[\"#3FA9F5\",\"#E1F3FF\"],\"data\":[1,9],\"hoverBackgroundColor\":[\"#3FA9F5\",\"#E1F3FF\"]}],\"labels\":[\"Valor 1\",\"Valor 2\"]},\"currentStatusProduct\":{\"datasets\":[{\"backgroundColor\":[\"#F3AE25\",\"#FEF2C8\"],\"data\":[7,3],\"hoverBackgroundColor\":[\"#F3AE25\",\"#FEF2C8\"]}],\"labels\":[\"Valor 1\",\"Valor 2\"]},\"monthly\":{\"datasets\":[{\"borderColor\":\"#F3AE25\",\"label\":\"Abril 2023\",\"backgroundColor\":\"#F3AE25\",\"data\":[65000,59000,25000,20000,130000],\"borderWidth\":1},{\"borderColor\":\"#3FA9F5\",\"label\":\"Abril 2024\",\"backgroundColor\":\"#3FA9F5\",\"data\":[69000,130000,15000,76000,28000],\"borderWidth\":1}],\"labels\":[\"Producto propio\",\"Producto terceros\",\"Directa\",\"Otros servicios\",\"TOTAL\"]},\"audiology\":{\"datasets\":[{\"backgroundColor\":[\"#F3AE25\",\"#3FA9F5\"],\"data\":[8,2],\"hoverBackgroundColor\":[\"#F3AE25\",\"#3FA9F5\"]}],\"labels\":[\"Subir Categoría\",\"Facturado\"]},\"annualConsumption\":{\"datasets\":[{\"borderColor\":\"#F3AE25\",\"label\":\"2023\",\"backgroundColor\":\"#F3AE25\",\"data\":[65000,59000,25000,20000,130000],\"borderWidth\":1},{\"borderColor\":\"#3FA9F5\",\"label\":\"2024\",\"backgroundColor\":\"#3FA9F5\",\"data\":[69000,130000,15000,76000,28000],\"borderWidth\":1}],\"labels\":[\"Producto propio\",\"Producto terceros\",\"Directa\",\"Otros servicios\",\"TOTAL\"]},\"consumptionByType\":{\"datasets\":[{\"label\":\"P. propio\",\"backgroundColor\":\"#F3AE25\",\"data\":[20000,30000,20000,40000,10000]},{\"label\":\"P. Terceros\",\"backgroundColor\":\"#3FA9F5\",\"data\":[15000,32000,24000,31000,19000]},{\"label\":\"Directa\",\"backgroundColor\":\"#00609C\",\"data\":[20000,30000,20000,40000,10000]},{\"label\":\"Otros\",\"backgroundColor\":\"#F959AB\",\"data\":[15000,32000,24000,31000,19000]}],\"labels\":[\"Enero\",\"Febrero\",\"Marzo\",\"Abril\",\"TOTAL\"]},\"eyeglassFrames\":{\"datasets\":[{\"backgroundColor\":[\"#F3AE25\",\"#3FA9F5\"],\"data\":[7,3],\"hoverBackgroundColor\":[\"#F3AE25\",\"#3FA9F5\"]}],\"labels\":[\"Subir Categoría\",\"Facturado\"]},\"lastMonthsConsumption\":{\"datasets\":[{\"borderColor\":\"#F3AE25\",\"label\":\"2023\",\"backgroundColor\":\"#F3AE25\",\"data\":[65000,59000,25000,20000,130000,65000,59000,25000,20000,130000,65000,59000],\"borderWidth\":1},{\"borderColor\":\"#3FA9F5\",\"label\":\"2024\",\"backgroundColor\":\"#3FA9F5\",\"data\":[69000,130000,15000,76000,28000,69000,130000,15000,76000,28000,69000,130000],\"borderWidth\":1}],\"labels\":[\"Mayo\",\"Junio\",\"Julio\",\"Agosto\",\"Sept.\",\"Oct.\",\"Nov.\",\"Dic.\",\"Enero\",\"Febrero\",\"Marzo\",\"Abril\"]},\"nextStateProduct\":{\"datasets\":[{\"backgroundColor\":[\"#3FA9F5\",\"#E1F3FF\"],\"data\":[7,3],\"hoverBackgroundColor\":[\"#3FA9F5\",\"#E1F3FF\"]}],\"labels\":[\"Valor 1\",\"Valor 2\"]},\"ophthalmicLens\":{\"datasets\":[{\"backgroundColor\":[\"#F3AE25\",\"#3FA9F5\"],\"data\":[4,6],\"hoverBackgroundColor\":[\"#F3AE25\",\"#3FA9F5\"]}],\"labels\":[\"Subir Categoría\",\"Facturado\"]},\"mySavings\":{\"datasets\":[{\"borderColor\":\"#3FA9F5\",\"label\":\"Importe €\",\"backgroundColor\":\"#3FA9F5\",\"data\":[6000,3000,100,2000,900,6500,8000],\"borderWidth\":1}],\"labels\":[\"Condiciones comerciales\",\"Fidelización\",\"Promociones\",\"Cuotas\",\"Portes\",\"TOTAL\"]}}";
			
			JSONObject json = new JSONObject(datos);
			
			ResponseBuilder rb= Response.ok(json);
			Response response = rb.build();
			return generateResponseBuilder(datos, Response.Status.OK).build();
		}catch(Exception e) {
			log.error(e.getMessage());			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	private ResponseBuilder generateResponseBuilder(String res, Status status) {

		return Response.status(status)
				.type(MediaType.APPLICATION_JSON + "; charset=utf-8")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8"))
				.header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
				.header(HttpHeaders.EXPIRES, "0")
				.entity(res);

	}
}
