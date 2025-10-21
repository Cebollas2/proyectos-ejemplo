package com.magnolia.cione.service;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.AbonoQueryParamsDTO;
import com.magnolia.cione.dto.AcuerdosDTO;
import com.magnolia.cione.dto.AlbaranAudioQueryParamsDTO;
import com.magnolia.cione.dto.AudioProveedoresDTO;
import com.magnolia.cione.dto.AudioSubfamiliasDTO;
import com.magnolia.cione.dto.BonosDTO;
import com.magnolia.cione.dto.CompraMinimaDTO;
import com.magnolia.cione.dto.CondicionesDTO;
import com.magnolia.cione.dto.ConsumosCLDTO;
import com.magnolia.cione.dto.CuotasDTO;
import com.magnolia.cione.dto.DatosComercialesDTO;
import com.magnolia.cione.dto.DeudaQueryParamsDTO;
import com.magnolia.cione.dto.DeudasDTO;
import com.magnolia.cione.dto.DireccionesDTO;
import com.magnolia.cione.dto.EnvioQueryParamsDTO;
import com.magnolia.cione.dto.EurocioneDTO;
import com.magnolia.cione.dto.FormaPagoDTO;
import com.magnolia.cione.dto.GestorDTO;
import com.magnolia.cione.dto.HerramientasDTO;
import com.magnolia.cione.dto.InteraccionQueryParamsDTO;
import com.magnolia.cione.dto.KpisDTO;
import com.magnolia.cione.dto.LineaAbonoDTO;
import com.magnolia.cione.dto.LineaCondicionDTO;
import com.magnolia.cione.dto.LineaEnvioDTO;
import com.magnolia.cione.dto.LineaFacturaDTO;
import com.magnolia.cione.dto.LineaPedidoDTO;
import com.magnolia.cione.dto.LineaTallerDTO;
import com.magnolia.cione.dto.LineasAlbaranAudioDTO;
import com.magnolia.cione.dto.LookticDTO;
import com.magnolia.cione.dto.MotivosDTO;
import com.magnolia.cione.dto.PaginaAbonosDTO;
import com.magnolia.cione.dto.PaginaAlbaranesAudioDTO;
import com.magnolia.cione.dto.PaginaDevolucionesDTO;
import com.magnolia.cione.dto.PaginaEnviosDTO;
import com.magnolia.cione.dto.PaginaGestionDevolucionesDTO;
import com.magnolia.cione.dto.PaginaInteraccionesDTO;
import com.magnolia.cione.dto.PaginaPedidosDTO;
import com.magnolia.cione.dto.PaginaTalleresDTO;
import com.magnolia.cione.dto.PagosPendientesDTO;
import com.magnolia.cione.dto.PagosPendientesQueryParamsDTO;
import com.magnolia.cione.dto.PedidoQueryParamsDTO;
import com.magnolia.cione.dto.PlanesFidelizaDTO;
import com.magnolia.cione.dto.RutaLuzDTO;
import com.magnolia.cione.dto.StockMgnlDTO;
import com.magnolia.cione.dto.TallerQueryParamsDTO;
import com.magnolia.cione.dto.TipoAlbaranDTO;
import com.magnolia.cione.dto.TransporteDTO;
import com.magnolia.cione.dto.UserERPCioneDTO;

@ImplementedBy(MiddlewareServiceImpl.class)
public interface MiddlewareService {

	public UserERPCioneDTO getUserFromERP(String idClient); 
	
	public UserERPCioneDTO getUserFromERPForLogin(String idClient);
	
	public UserERPCioneDTO getUserFromERPEmployee(String idUser);
	
	public List<LineaAbonoDTO> getLineasAbono(String idClient, String numAbono, String historico);
	
	public PaginaAbonosDTO getAbonos(String idClient, AbonoQueryParamsDTO filter);

	public List<LineaPedidoDTO> getLineasPedido(String idClient, String numPedido, String historico);
	
	public List<LineaTallerDTO> getLineasTaller(String idClient, String idServicio, String numPedido, String historico);
	
	public List<LineaFacturaDTO> getLineasFactura(String idClient, String numFactura);
	
	public List<LineaCondicionDTO> getCondicionesLinea(String idClient, String linea);
	
	public PaginaPedidosDTO getPedidos(String idClient, PedidoQueryParamsDTO filter);
	
	public PaginaDevolucionesDTO getDevoluciones(String idClient, PedidoQueryParamsDTO filter);
	
	public PaginaDevolucionesDTO getDevolucionesAvanzadas(String idClient, PedidoQueryParamsDTO filter);
	
	public MotivosDTO getMotivos();
	
	public PaginaGestionDevolucionesDTO getGestionDevoluciones(String idClient, PedidoQueryParamsDTO filter);
	
	public PaginaTalleresDTO getTalleres(String idClient, TallerQueryParamsDTO filter);

	public List<LineaEnvioDTO> getLineasEnvio(String idClient, String numEnvio, String trackingPedido);
	
	public PaginaEnviosDTO getEnvios(String idClient, EnvioQueryParamsDTO filter);
	
	public DatosComercialesDTO getDatosComerciales(String idClient);
	
	public List<GestorDTO> getGestores(String idClient);
	
	public CuotasDTO getCuotas(String idClient);
	
	public FormaPagoDTO getFormaPago(String idClient);
	
	public List<TransporteDTO> getTransportes(String idClient);
	
	public List<LookticDTO> getLooktics(String idClient);
	
	public RutaLuzDTO getRutaLuz(String idClient);
	
	public DeudasDTO getDeudas(String idClient, DeudaQueryParamsDTO filter);
	
	public PagosPendientesDTO getPagosPendientes(String idClient, PagosPendientesQueryParamsDTO filter);
	
	public EurocioneDTO getEurociones(String idClient);
	
	public PlanesFidelizaDTO getPlanesFideliza(String idClient);
	
	public CondicionesDTO getCondiciones(String idClient);
	
	public BonosDTO getBonos(String idClient);
	
	public AcuerdosDTO getAcuerdos(String idClient);
	
	public HerramientasDTO getHerramientas(String idClient);
	
	public KpisDTO getKpis(); 
	
	public PaginaInteraccionesDTO getInteracciones(String idClient, InteraccionQueryParamsDTO filter);
	
	public DireccionesDTO getDirecciones(String idClient);
	
	public float getStock(String aliasEkon);

	StockMgnlDTO getStockDTO(String aliasEkon);
	
	public PaginaAlbaranesAudioDTO getAlbaranesAudio(AlbaranAudioQueryParamsDTO albaranAudioQueryParamsDTO);
	public LineasAlbaranAudioDTO getLineasAlbaranAudio(String numAlbaran);
	
	public AudioSubfamiliasDTO getAudioSubfamilias();
	
	public AudioProveedoresDTO getProveedores();
	
	public TipoAlbaranDTO getTipoAlbaran();
	
	/**
	 * 
	 * Metodo para obtener los consumos de Cione Lover
	 * 
	 * @param idClient del cliente (ERP se puede obtener con CioneUtils.getIdCurrentClientERP())
	 * @return ConsumosCLDTO que es una lista de ConsumoCLDTO
	 * 
	 */
	public ConsumosCLDTO getConsumosCioneLovers(String idClient);
	
	public ConsumosCLDTO getConsumosCioneLoversFilterByGroupTwo(String idClient, String groupname);
	
	/*
	 * Metodo que a partir de codSocio, grupoPrecio, marca, proveedor, familia devuelve un dto con la informacion de compra minima
	 * @param String codSocio
	 * @param String grupoPrecio
	 * @param String marca
	 * @param String proveedor
	 * @param String familia
	 * 
	 * @return CompraMinima con la info de las resticciones para compra minima
	 * */
	 public CompraMinimaDTO getCompraMinima(String codSocio, String grupoPrecio, String marca, String proveedor, String familia);
}
