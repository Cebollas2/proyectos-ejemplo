package com.magnolia.cione.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.naming.NamingException;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.dao.MisAhorrosDao;
import com.magnolia.cione.dto.AcuerdosDTO;
import com.magnolia.cione.dto.BonosDTO;
import com.magnolia.cione.dto.CondicionDTO;
import com.magnolia.cione.dto.CondicionesDTO;
import com.magnolia.cione.dto.CuotaDTO;
import com.magnolia.cione.dto.CuotasDTO;
import com.magnolia.cione.dto.DatosComercialesDTO;
import com.magnolia.cione.dto.EurocioneDTO;
import com.magnolia.cione.dto.FormaPagoDTO;
import com.magnolia.cione.dto.GestorDTO;
import com.magnolia.cione.dto.HerramientasDTO;
import com.magnolia.cione.dto.LookticDTO;
import com.magnolia.cione.dto.PlanFidelizaDTO;
import com.magnolia.cione.dto.PlanesFidelizaDTO;
import com.magnolia.cione.dto.RutaLuzDTO;
import com.magnolia.cione.dto.TransporteDTO;
import com.magnolia.cione.service.MiddlewareService;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.cms.security.SecuritySupport;
import info.magnolia.cms.security.User;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class MisDatosModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	// private final TemplatingFunctions templatingFunctions;

	@Inject
	private MiddlewareService middlewareService;
	
	@Inject
	private MisAhorrosDao misAhorrosDao;
	
	@Inject
	private SecuritySupport securitySupport;

	@Inject
	public MisDatosModel(Node content, RD definition, RenderingModel<?> parent,
			TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
		// this.templatingFunctions = templatingFunctions;
	}

	public DatosComercialesDTO getDatosComerciales() {
		DatosComercialesDTO data = middlewareService.getDatosComerciales(CioneUtils.getIdCurrentClientERP());
		if (data == null) {
			data = new DatosComercialesDTO();
		} else {
			if (!CioneUtils.isEmptyOrNull(data.getFechaAlta())) {
				// data.setFechaAlta(CioneUtils.changeDateFormat(data.getFechaAlta(),
				// "yyyy-MM-dd", "dd MMMM yyyy"));
				data.setFechaAlta(CioneUtils.changeDateFormat(data.getFechaAlta(), "yyyy-MM-dd", "dd/MM/yyyy"));
			}
		}
		return data;
	}

	public List<GestorDTO> getGestoresCuenta() {
		List<GestorDTO> data = middlewareService.getGestores(CioneUtils.getIdCurrentClientERP());
		if (data == null) {
			data = new ArrayList<>();
		}
		return data;
	}

	public FormaPagoDTO getFormaPago() {
		FormaPagoDTO data = middlewareService.getFormaPago(CioneUtils.getIdCurrentClientERP());
		if (data == null) {
			data = new FormaPagoDTO();
		}
		return data;
	}

	public CuotasDTO getCuotas() throws NamingException {
		CuotasDTO data = middlewareService.getCuotas(CioneUtils.getIdCurrentClientERP());
		if (data == null) {
			data = new CuotasDTO();
		} else {
			Double ahorro = misAhorrosDao.getAhorroEnCuotas(CioneUtils.getIdCurrentClientERP());
			for (CuotaDTO cuota : data.getCuotas()) {				
				if (!CioneUtils.isEmptyOrNull(cuota.getFechaFinCuota())) {
					cuota.setFechaFinCuota(
							CioneUtils.changeDateFormat(cuota.getFechaFinCuota(), "yyyy-MM-dd", "dd/MM/yyyy"));
				}
			}
			if(ahorro != null) {
				data.setAhorro(ahorro.toString());
			}			
		}
		return data;
	}

	public List<TransporteDTO> getTransportes() {
		List<TransporteDTO> data = middlewareService.getTransportes(CioneUtils.getIdCurrentClientERP());
		if (data == null) {
			data = new ArrayList<>();
		}
		return data;
	}

	public List<LookticDTO> getLooktics() {
		List<LookticDTO> data = middlewareService.getLooktics(CioneUtils.getIdCurrentClientERP());
		if (data == null) {
			data = new ArrayList<>();
		} else {
			for (LookticDTO lookticDTO : data) {
				if (!CioneUtils.isEmptyOrNull(lookticDTO.getFechaAdhesion())) {
					lookticDTO.setFechaAdhesion(
							CioneUtils.changeDateFormat(lookticDTO.getFechaAdhesion(), "yyyy-MM-dd", "dd/MM/yyyy"));
				}
			}
		}
		return data;
	}

	public RutaLuzDTO getRutaLuz() {
		RutaLuzDTO data = middlewareService.getRutaLuz(CioneUtils.getIdCurrentClientERP());
		if (data == null) {
			data = new RutaLuzDTO();
		} else {
			if (!CioneUtils.isEmptyOrNull(data.getCantidad())) {				
				data.setCantidad(CioneUtils.decimalToView(data.getCantidad()));
			}
		}
		return data;
	}

	public EurocioneDTO getEurociones() {
		EurocioneDTO data = middlewareService.getEurociones(CioneUtils.getIdCurrentClientERP());
		if (data == null) {
			data = new EurocioneDTO();
		}
		return data;
	}

	public PlanesFidelizaDTO getPlanesFideliza() {
		PlanesFidelizaDTO data = middlewareService.getPlanesFideliza(CioneUtils.getIdCurrentClientERP());
		if (data == null) {
			data = new PlanesFidelizaDTO();
		}else {
			Double cionesAcumuladosTotal = 0.0;
			Double cionesAcumuladosGrupoTotal = 0.0;
			for (PlanFidelizaDTO planFidelizaDTO: data.getTiposPlanFideliza()) {				
				if(planFidelizaDTO.getCionesAcumulados()!= null) {
					cionesAcumuladosTotal += Double.parseDouble(planFidelizaDTO.getCionesAcumulados());
				}
				if(planFidelizaDTO.getCionesAcumuladosGrupo()!= null) {
					cionesAcumuladosGrupoTotal += Double.parseDouble(planFidelizaDTO.getCionesAcumuladosGrupo());
				}
			}
			data.setCionesAcumuladosTotal(cionesAcumuladosTotal);
			data.setCionesAcumuladosGrupoTotal(cionesAcumuladosGrupoTotal);
		}
		return data;
	}

	public CondicionesDTO getCondiciones() {
		CondicionesDTO data = middlewareService.getCondiciones(CioneUtils.getIdCurrentClientERP());
		if (data == null) {
			data = new CondicionesDTO();
		}else {	
			Double cionesAcumuladosTotal = 0.0;
			Double cionesAcumuladosGrupoTotal = 0.0;
			for (CondicionDTO condicionDTO : data.getCondiciones()) {				
				if(condicionDTO.getCionesAcumuladosPVenta()!= null) {
					cionesAcumuladosTotal += Double.parseDouble(condicionDTO.getCionesAcumuladosPVentaDecimalFormat());
				}
				if(condicionDTO.getCionesAcumuladosGrupo()!= null) {
					cionesAcumuladosGrupoTotal += Double.parseDouble(condicionDTO.getCionesAcumuladosGrupoDecimalFormat());
				}
				
				boolean isFound = condicionDTO.getNombreAcuerdo().toUpperCase().indexOf("LENTES") !=-1? true: false;
				condicionDTO.setShowDetail(isFound);
			}
			data.setCionesAcumuladosTotal(cionesAcumuladosTotal);
			data.setCionesAcumuladosGrupoTotal(cionesAcumuladosGrupoTotal);

		}
		return data;
	}
	
	public BonosDTO getBonos() {
		BonosDTO data = middlewareService.getBonos(CioneUtils.getIdCurrentClientERP());
		if (data == null) {
			data = new BonosDTO();
		}
		return data;
	}
	
	public AcuerdosDTO getAcuerdos() {
		AcuerdosDTO data = middlewareService.getAcuerdos(CioneUtils.getIdCurrentClientERP());
		if (data == null) {
			data = new AcuerdosDTO();
		}
		return data;
	}
	
	public HerramientasDTO getHerramientas() {
		HerramientasDTO data = middlewareService.getHerramientas(CioneUtils.getIdCurrentClientERP());
		if (data == null) {
			data = new HerramientasDTO();
		}
		return data;
	}
	
	public boolean ocultarSecciones() {
		User currentUser = securitySupport.getUserManager("public").getUser(CioneUtils.getIdCurrentClient());
		if (currentUser!= null && currentUser.hasRole(CioneConstants.ROLE_FIDELIZACION)) {
			return true;
		} else {
			return false;
		}
	}

}
