package com.magnolia.cione.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dao.MisAhorrosDao;
import com.magnolia.cione.dao.MisConsumosDao;
import com.magnolia.cione.dto.AhorroDTO;
import com.magnolia.cione.dto.ChartInfo;
import com.magnolia.cione.dto.CondicionDTO;
import com.magnolia.cione.dto.CondicionesDTO;
import com.magnolia.cione.dto.DashBoardDTO;
import com.magnolia.cione.dto.Dataset;
import com.magnolia.cione.dto.EurocioneDTO;
import com.magnolia.cione.dto.MisAhorrosDTO;
import com.magnolia.cione.dto.MisConsumosDashBoardDTO;
import com.magnolia.cione.dto.PlanFidelizaDTO;
import com.magnolia.cione.dto.PlanesFidelizaDTO;
import com.magnolia.cione.utils.CioneUtils;

public class DashBoardServiceImpl implements DashBoardService {
	
	private static final Logger log = LoggerFactory.getLogger(DashBoardServiceImpl.class);
	
	@Inject
	private MiddlewareService middlewareService;
	
	@Inject
	private MisConsumosDao misConsumosDao;
	
	@Inject
	private MisAhorrosDao misAhorrosDao;
	
	public DashBoardDTO getDashBoard(Locale locale) {
		DashBoardDTO jsonData = new DashBoardDTO();
		

		
		//grafico estado actual
		EurocioneDTO eurociones = middlewareService.getEurociones(CioneUtils.getIdCurrentClientERP());
		jsonData.setActualLevel(eurociones.getCategoria());
		
		if ((eurociones.getEc_siguientepromocion()==null) || (eurociones.getEc_siguientepromocion().isEmpty()))
			jsonData.setNextLevel("MAXIMO");
		else
			jsonData.setNextLevel(eurociones.getEc_siguientepromocion()); //eurociones.getCategoriaSig()
		
		jsonData.setEurocioneDisponibles(eurociones.getEc_disponibles());
		
		ChartInfo consumptionActualLevelOwnProduct = new ChartInfo();
		ChartInfo consumptionNextLevelOwnProduct = new ChartInfo();
		ChartInfo consumptionActualLevelSupplier = new ChartInfo();
		ChartInfo consumptionNextLevelSupplier = new ChartInfo();
		PlanesFidelizaDTO planesFideliza = middlewareService.getPlanesFideliza(CioneUtils.getIdCurrentClientERP());
		//log.debug("DashBoard Consumo mantener: planesFideliza =" +planesFideliza.toString());
		
		for (PlanFidelizaDTO planfideliza: planesFideliza.getTiposPlanFideliza()) {
			try {
				if (planfideliza.getTipo().equals("Producto propio")) {
					consumptionActualLevelOwnProduct.setName(planfideliza.getTipo());
					consumptionActualLevelOwnProduct.setDescuentoActual(planfideliza.getDescuentoActual());
					ArrayList<String> labels = new ArrayList<>();
					labels.add("Consumo actual");
					labels.add("Consumo mantener");
					consumptionActualLevelOwnProduct.setLabels(labels);
					ArrayList<Dataset> datasets = new ArrayList<>();
					Dataset dataset = new Dataset();
					ArrayList<Double> data = new ArrayList<>();	
					Double quesoentero = Double.valueOf(planfideliza.getConsumoMantener());
					Double primervalor = Double.valueOf(planfideliza.getConsumoActualGrupoOrigin());
					
					
					if ((quesoentero - primervalor) < 0) {
						data.add(CioneUtils.roundDouble(primervalor));
						data.add(0.0);
					} else {
						data.add(primervalor);
						data.add(CioneUtils.roundDouble(quesoentero - primervalor));
					}
					dataset.setData(data);
					ArrayList<String> backgroundColor = new ArrayList<>();
					backgroundColor.add("#F3AE25");
					backgroundColor.add("#FEF2C8");
					dataset.setBackgroundColor(backgroundColor);
					dataset.setHoverBackgroundColor(backgroundColor);
					datasets.add(dataset);
					
					consumptionActualLevelOwnProduct.setDatasets(datasets);
					
					
					consumptionNextLevelOwnProduct.setName(planfideliza.getTipo());
					consumptionNextLevelOwnProduct.setSigDescuento(planfideliza.getSigDescuento());
					ArrayList<String> labelsnext = new ArrayList<>();
					labelsnext.add("Consumo actual");
					labelsnext.add("Consumo subir nivel");
					consumptionNextLevelOwnProduct.setLabels(labelsnext);
					ArrayList<Dataset> datasetsNext = new ArrayList<>();
					Dataset datasetNext = new Dataset();
					ArrayList<Double> dataNext = new ArrayList<>();	
					Double quesoenteronext = Double.valueOf(planfideliza.getConsumoSigCategoriaOrigin());
					Double primervalornext = Double.valueOf(planfideliza.getConsumoActualGrupoOrigin());
					
					if ((quesoenteronext - primervalornext) < 0) {
						dataNext.add(CioneUtils.roundDouble(primervalornext));
						dataNext.add(0.0);
					} else {
						dataNext.add(primervalornext);
						dataNext.add(CioneUtils.roundDouble(quesoenteronext - primervalornext));
					}
					datasetNext.setData(dataNext);
					ArrayList<String> backgroundColorNext = new ArrayList<>();
					backgroundColorNext.add("#3FA9F5");
					backgroundColorNext.add("#E1F3FF");
					datasetNext.setBackgroundColor(backgroundColorNext);
					datasetNext.setHoverBackgroundColor(backgroundColorNext);
					datasetsNext.add(datasetNext);
					
					consumptionNextLevelOwnProduct.setDatasets(datasetsNext);
				} else {
					consumptionActualLevelSupplier.setName(planfideliza.getTipo());
					consumptionActualLevelSupplier.setDescuentoActual(planfideliza.getDescuentoActual());
					ArrayList<String> labels = new ArrayList<>();
					labels.add("Consumo actual");
					labels.add("Consumo mantener");
					consumptionActualLevelSupplier.setLabels(labels);
					ArrayList<Dataset> datasets = new ArrayList<>();
					Dataset dataset = new Dataset();
					ArrayList<Double> data = new ArrayList<>();	
					Double quesoentero = Double.valueOf(planfideliza.getConsumoMantener());
					Double primervalor = Double.valueOf(planfideliza.getConsumoActualOrigin());
					if ((quesoentero - primervalor) < 0) {
						data.add(CioneUtils.roundDouble(primervalor));
						data.add(0.0);
					} else {
						data.add(primervalor);
						data.add(CioneUtils.roundDouble(quesoentero - primervalor));
					}
					dataset.setData(data);
					ArrayList<String> backgroundColor = new ArrayList<>();
					backgroundColor.add("#F3AE25");
					backgroundColor.add("#FEF2C8");
					dataset.setBackgroundColor(backgroundColor);
					dataset.setHoverBackgroundColor(backgroundColor);
					datasets.add(dataset);
					
					consumptionActualLevelSupplier.setDatasets(datasets);
					
					
					consumptionNextLevelSupplier.setName(planfideliza.getTipo());
					consumptionNextLevelSupplier.setSigDescuento(planfideliza.getSigDescuento());
					ArrayList<String> labelsnext = new ArrayList<>();
					labelsnext.add("Consumo actual");
					labelsnext.add("Consumo subir nivel");
					consumptionNextLevelSupplier.setLabels(labelsnext);
					ArrayList<Dataset> datasetsNext = new ArrayList<>();
					Dataset datasetNext = new Dataset();
					ArrayList<Double> dataNext = new ArrayList<>();	
					Double quesoenteronext = Double.valueOf(planfideliza.getConsumoSigCategoriaOrigin());
					Double primervalornext = Double.valueOf(planfideliza.getConsumoActualOrigin());
					if ((quesoenteronext - primervalornext) < 0) {
						dataNext.add(CioneUtils.roundDouble(primervalornext));
						dataNext.add(0.0);
					} else {
						dataNext.add(primervalornext);
						dataNext.add(CioneUtils.roundDouble(quesoenteronext - primervalornext));
					}
					datasetNext.setData(dataNext);
					ArrayList<String> backgroundColorNext = new ArrayList<>();
					backgroundColorNext.add("#3FA9F5");
					backgroundColorNext.add("#E1F3FF");
					datasetNext.setBackgroundColor(backgroundColorNext);
					datasetNext.setHoverBackgroundColor(backgroundColorNext);
					datasetsNext.add(datasetNext);
					
					consumptionNextLevelSupplier.setDatasets(datasetsNext);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		jsonData.setConsumptionActualLevelOwnProduct(consumptionActualLevelOwnProduct);
		jsonData.setConsumptionActualLevelSupplier(consumptionActualLevelSupplier);
		jsonData.setConsumptionNextLevelOwnProduct(consumptionNextLevelOwnProduct);
		jsonData.setConsumptionNextLevelSupplier(consumptionNextLevelSupplier);
		
		//grafico mis consumos mensual
		jsonData.setMonthly(getInfoMont(locale));
		
		//grafico mis consumos acumulado anual
		jsonData.setAnnualConsumption(getInfoMontAnnualConsumption(locale));
		
		//Consumos de los ultimos 12 meses
		jsonData.setLastTwelveMonthsConsumption(getInfoLastTwelveMonthsConsumption(locale));
		
		
		//mis ahorros
		jsonData.setMySavings(getInfoMySavings(locale));
		
		//Consumos por tipo del año actual 
		jsonData.setConsumptionByType(getConsumptionByType(locale));
		
		
		//grafico de donnuts
		ArrayList<ChartInfo> consumptionCharts = getConsumptionCharts(locale);
		jsonData.setConsumptionCharts(consumptionCharts);
		
		
		return jsonData;
	}
	
	private ChartInfo getConsumptionByType(Locale locale) {
		
		ArrayList<String> labels = new ArrayList<>();
		ArrayList<Dataset> datasets = new ArrayList<>();
		ArrayList<MisConsumosDashBoardDTO> misConsumos = misConsumosDao.getMisConsumosPorTipo(CioneUtils.getIdCurrentClientERP(), locale);
		
		int iteracion = 0;
		HashMap<Integer, String> backgroundColorMap = new HashMap<>();
		backgroundColorMap.put(0, "#25f3ae");
		backgroundColorMap.put(1, "#f34725");
		backgroundColorMap.put(2, "#00609C");
		backgroundColorMap.put(3, "#F959AB");
		
		HashMap<String, String> procesados = new HashMap<>();
		
		for(MisConsumosDashBoardDTO consumoInit: misConsumos) {
			int mes = 1;
			if (procesados.get(consumoInit.getDescription()) == null) {
				Dataset dataset = new Dataset();
				dataset.setLabel(consumoInit.getDescription());
				ArrayList<Double> data = new ArrayList<>();
				double total = 0.0;
				ArrayList<String> backgroundColor = new ArrayList<>();
				
				for(MisConsumosDashBoardDTO consumo: misConsumos) {
					if (consumoInit.getDescription().equals(consumo.getDescription())) {
						if (mes != consumo.getMes()) {
							data.add(0.0);
							backgroundColor.add(backgroundColorMap.get(iteracion));
						}
						data.add(consumo.getConsumo_mes());
						backgroundColor.add(backgroundColorMap.get(iteracion));
						total += consumo.getConsumo_mes();
						mes++;
					}
				}
				
				int mesActual = LocalDate.now().getMonthValue(); //10
				if (mes < mesActual) {
					//completo con los que faltan
					for (int i=mes;  i<=mesActual; i++  ) {
						data.add(0.0);
						backgroundColor.add(backgroundColorMap.get(iteracion));
					}
				}
				
				data.add(total);
				backgroundColor.add(backgroundColorMap.get(iteracion));
				iteracion++;
				
				dataset.setData(data);
				dataset.setBackgroundColor(backgroundColor);
				
				datasets.add(dataset);
				procesados.put(dataset.getLabel(), dataset.getLabel());
			}
		}
		int mesActual = LocalDate.now().getMonthValue(); //10
		
		for (int i=1; i<= mesActual; i++) {
			Month month = Month.of(i);
			String month_str = month.getDisplayName(TextStyle.FULL, locale);
			labels.add(month_str.substring(0, 1).toUpperCase() + month_str.substring(1, month_str.length()));
		}
		labels.add("TOTAL");

		
		ChartInfo infoConsumptionByType = new ChartInfo();
		
		String month_january = Month.of(1).getDisplayName(TextStyle.FULL, locale);
		String month_last = Month.of(mesActual).getDisplayName(TextStyle.FULL, locale);
		
		String rango = month_january.substring(0, 1).toUpperCase() + month_january.substring(1, month_january.length()) 
			+ "-" + month_last.substring(0, 1).toUpperCase() + month_last.substring(1, month_last.length());
		
		
		infoConsumptionByType.setName("(" + rango +")");
		infoConsumptionByType.setLabels(labels);
		infoConsumptionByType.setDatasets(datasets);
		
		return infoConsumptionByType;
	}
	
	
	private ChartInfo getInfoMySavings(Locale locale) {
		ChartInfo chartInfo = new ChartInfo();
		ArrayList<String> labels = new ArrayList<>();
		ArrayList<Dataset> datasets = new ArrayList<>();
		ArrayList<Double> data = new ArrayList<>();
		ArrayList<String> backgroundColor = new ArrayList<>();
		
		MisAhorrosDTO misAhorros = misAhorrosDao.getMisAhorrosDashBoard(CioneUtils.getIdCurrentClientERP());
		
		if ((misAhorros!= null) && (misAhorros.getAhorros() != null)) {
			for (AhorroDTO ahorro: misAhorros.getAhorros()) {
				labels.add(ahorro.getConcepto());
				data.add(ahorro.getImporte());
				backgroundColor.add("#3FA9F5");
			}
		}
		
		
		Dataset dataset = new Dataset();
		dataset.setLabel("Importe");
		dataset.setData(data);
		dataset.setBackgroundColor(backgroundColor);
		dataset.setBorderColor("#3FA9F5");
		dataset.setBorderWidth(1);
		datasets.add(dataset);
		
		chartInfo.setLabels(labels);
		chartInfo.setDatasets(datasets);
		chartInfo.setName("mis ahorros");
		return chartInfo;
	}
	
	private ArrayList<ChartInfo> getConsumptionCharts(Locale locale) {
		ArrayList<ChartInfo> consumptionCharts = new ArrayList<>();
		CondicionesDTO condiciones = middlewareService.getCondiciones(CioneUtils.getIdCurrentClientERP());
		
		/*String logger = "DashBoard Consumo Subir Categoria y Facturado:";
		for (CondicionDTO condicion: condiciones.getCondiciones())
			logger+= condicion.toString();
		log.debug(logger);*/
		ResourceBundle rbundle = ResourceBundle.getBundle("cione-module/i18n/module-cione-module-messages", locale);
		
		for (CondicionDTO condicion: condiciones.getCondiciones()) {
			ChartInfo info = new ChartInfo();
			info.setName(condicion.getNombreAcuerdo());
			info.setId(condicion.getNombreAcuerdo().replaceAll("\\s+", ""));
			
			ArrayList<String> labels = new ArrayList<>();
			labels.add(rbundle.getString("cione-module.dashboard-component.dashboard.subircategoria"));
			labels.add(rbundle.getString("cione-module.dashboard-component.dashboard.facturado"));
			info.setLabels(labels);
			
			ArrayList<Dataset> datasets = new ArrayList<>();
			Dataset dataset = new Dataset();
			ArrayList<Double> data = new ArrayList<>();			
			data.add(parserStringtoDouble(condicion.getFacNetaGrupoSubirCateg()));
			data.add(parserStringtoDouble(condicion.getFactBrutaPVenta()));
			//data.add(parserStringtoDouble(condicion.getFactBrutaGrupo()));
			dataset.setData(data);
			
			ArrayList<String> backgroundColor = new ArrayList<>();
			backgroundColor.add("#F3AE25");
			backgroundColor.add("#3FA9F5");
			dataset.setBackgroundColor(backgroundColor);
			dataset.setHoverBackgroundColor(backgroundColor);
			
			datasets.add(dataset);
			info.setDatasets(datasets);
			
			info.setProxRappel(condicion.getSigRappel());
			
			consumptionCharts.add(info);
		}
		
		return consumptionCharts;
	}
	
	private ChartInfo getInfoMontAnnualConsumption(Locale locale) {
		//grafico mis consumos
		
		ArrayList<MisConsumosDashBoardDTO> misConsumos = misConsumosDao.getMisConsumosAcumuladoAnualDashBoard(CioneUtils.getIdCurrentClientERP(), locale);
		/*String logger = "DashBoard Consumo Acumulado Anual:";
		for (MisConsumosDashBoardDTO consumo: misConsumos)
			logger+= consumo.toString();
		log.debug(logger);*/
		ArrayList<String> labels = new ArrayList<>();
		ArrayList<Dataset> datasets = new ArrayList<>();
		String anio = "";
		String anioAnterior = "";
		Dataset consumosAgrupadosAnioAnterior = new Dataset();
		Dataset consumosAgrupadosAnio = new Dataset();
		
		for (MisConsumosDashBoardDTO consumo: misConsumos) {
			if (anio.isEmpty())
				anio = consumo.getAnio();
			else
				if (Integer.valueOf(anio) <  Integer.valueOf(consumo.getAnio())) {
					anioAnterior = anio;
					anio = consumo.getAnio();
				}
		}
		
		ArrayList<Double> dataAnioAnterior = new ArrayList<>();	
		ArrayList<Double> dataAnio = new ArrayList<>();	
		
		for (MisConsumosDashBoardDTO consumo: misConsumos) {
			if (!labels.contains(consumo.getDescription()))
				labels.add(consumo.getDescription());
			if (consumo.getAnio().equals(anioAnterior)) {
				dataAnioAnterior.add(consumo.getConsumo_mes());
			} else {
				dataAnio.add(consumo.getConsumo_mes());
			}
		}
		
		consumosAgrupadosAnioAnterior.setLabel(anioAnterior);
		consumosAgrupadosAnioAnterior.setData(dataAnioAnterior);
		int size = consumosAgrupadosAnioAnterior.getData().size();
		ArrayList<String> backgroundColorAnioAnterior = new ArrayList<>();
		for (int i=0; i<size; i++) {
			backgroundColorAnioAnterior.add("#F3AE25");
		}
		consumosAgrupadosAnioAnterior.setBackgroundColor(backgroundColorAnioAnterior);
		consumosAgrupadosAnioAnterior.setBorderColor("#F3AE25");
		consumosAgrupadosAnioAnterior.setBorderWidth(1);
		
		consumosAgrupadosAnio.setLabel(anio);
		consumosAgrupadosAnio.setData(dataAnio);
		size = consumosAgrupadosAnio.getData().size();
		ArrayList<String> backgroundColorAnio = new ArrayList<>();
		for (int i=0; i<size; i++) {
			backgroundColorAnio.add("#3FA9F5");
		}
		
		consumosAgrupadosAnio.setBackgroundColor(backgroundColorAnio);
		consumosAgrupadosAnio.setBorderColor("#3FA9F5");
		consumosAgrupadosAnio.setBorderWidth(1);
		
		datasets.add(consumosAgrupadosAnioAnterior);
		datasets.add(consumosAgrupadosAnio);
		
		
		ChartInfo infoMont = new ChartInfo();
		infoMont.setLabels(labels);
		infoMont.setDatasets(datasets);
		
		return infoMont;
	}
	
	private ChartInfo getInfoLastTwelveMonthsConsumption(Locale locale) {
		//grafico mis consumos ultimos 12 meses
		ArrayList<MisConsumosDashBoardDTO> misConsumos = misConsumosDao.getMisConsumosUltimosDoceMesesDashBoard(CioneUtils.getIdCurrentClientERP(), locale);
		/*String logger = "DashBoard Consumo Ultimos 12 meses:";
		for (MisConsumosDashBoardDTO consumo: misConsumos) {
			logger+= consumo.toString();
		}
		log.debug(logger);*/
		ArrayList<String> labels = new ArrayList<>();
		ArrayList<Dataset> datasets = new ArrayList<>();
		Dataset consumosAgrupadosAnioAnterior = new Dataset();
		Dataset consumosAgrupadosAnio = new Dataset();
		
		
		
		HashMap<Integer, Double> dataAnioAux = new HashMap<>();
		HashMap<Integer, Double> dataAnioAnteriorAux = new HashMap<>();
		
		//ArrayList<MisConsumosDashBoardDTO> misConsumosAux = (ArrayList<MisConsumosDashBoardDTO>) misConsumos.clone();
		
		HashMap<String, Double> consumoMap = new HashMap<>();
		for (MisConsumosDashBoardDTO item : misConsumos) {
		    String key = item.getAnio() + "-" + String.format("%02d", item.getMes());
		    consumoMap.put(key, item.getConsumo_mes());
		}
		
		int anioActual = Year.now().getValue();  //2025
		int anioAnterior = Year.now().getValue() -1;
		int anioAnteriorAnterior = Year.now().getValue() -2;
		int mesActual = LocalDate.now().getMonthValue(); //7
		
		for (int mes = 1; mes <= 12; mes++) {
		    String key;
		    if (mes <= mesActual) {
		        key = anioActual + "-" + String.format("%02d", mes);
		    } else {
		        key = anioAnterior + "-" + String.format("%02d", mes);
		    }
		    double consumo = consumoMap.getOrDefault(key, 0.0);
		    dataAnioAux.put(mes, consumo);
		}
		
		for (int mes = 1; mes <= 12; mes++) {
		    String key;
		    if (mes <= mesActual) {
		        key = anioAnterior + "-" + String.format("%02d", mes);
		    } else {
		        key = anioAnteriorAnterior + "-" + String.format("%02d", mes);
		    }
		    double consumo = consumoMap.getOrDefault(key, 0.0);
		    dataAnioAnteriorAux.put(mes, consumo);
		}
		
		ArrayList<Double> dataAnioAnterior = new ArrayList<>();	
		ArrayList<Double> dataAnio = new ArrayList<>();	
		for (int mes = 0; mes <= 11; mes++) {
			int aux = mesActual + mes;
			if (aux > 12) {
				dataAnio.add(dataAnioAux.get(aux-12));
				dataAnioAnterior.add(dataAnioAnteriorAux.get(aux-12));
			} else {
				dataAnio.add(dataAnioAux.get(aux));
				dataAnioAnterior.add(dataAnioAnteriorAux.get(aux));
			}
		}
		
		
		ResourceBundle rbundle = ResourceBundle.getBundle("cione-module/i18n/module-cione-module-messages", locale);
		
		consumosAgrupadosAnioAnterior.setData(dataAnioAnterior);
		ArrayList<String> backgroundColorAnioAnterior = new ArrayList<>();
		for (int i=0; i<12; i++) 
			backgroundColorAnioAnterior.add("#F3AE25");
		consumosAgrupadosAnioAnterior.setBackgroundColor(backgroundColorAnioAnterior);
		consumosAgrupadosAnioAnterior.setBorderColor("#F3AE25");
		consumosAgrupadosAnioAnterior.setBorderWidth(1);
		consumosAgrupadosAnioAnterior.setLabel(rbundle.getString("cione-module.dashboard-component.dashboard.anio-anterior"));
		
		
		
		consumosAgrupadosAnio.setData(dataAnio);
		ArrayList<String> backgroundColorAnio = new ArrayList<>();
		for (int i=0; i<12; i++) 
			backgroundColorAnio.add("#3FA9F5");
		consumosAgrupadosAnio.setBackgroundColor(backgroundColorAnio);
		consumosAgrupadosAnio.setBorderColor("#3FA9F5");
		consumosAgrupadosAnio.setBorderWidth(1);
		consumosAgrupadosAnio.setLabel(rbundle.getString("cione-module.dashboard-component.dashboard.anio-curso"));
		
		datasets.add(consumosAgrupadosAnioAnterior);
		datasets.add(consumosAgrupadosAnio);
		
		
		ChartInfo infoLastTwelveMont = new ChartInfo();
		
		for (MisConsumosDashBoardDTO consumo: misConsumos) {
			if (labels.size()<12) 
				labels.add(consumo.getMes_str());
			//{
				//labels.add(consumo.getMes_str() + " " +consumo.getAnio());
				//labels.add(consumo.getMes_str() + " " +consumo.getAnio());
			//}
		}
		
		infoLastTwelveMont.setLabels(labels);
		infoLastTwelveMont.setDatasets(datasets);
		
		return infoLastTwelveMont;
	}
	
	private ChartInfo getInfoMont(Locale locale) {
		//grafico mis consumos
		
		ArrayList<MisConsumosDashBoardDTO> misConsumos = misConsumosDao.getMisConsumoMensualDashBoard(CioneUtils.getIdCurrentClientERP(), locale);
		/*String logger = "DashBoard Consumo Mensual:";
		for (MisConsumosDashBoardDTO consumo: misConsumos) {
			logger+= consumo.toString();
		}
		log.debug(logger);*/
		ArrayList<String> labels = new ArrayList<>();
		ArrayList<Dataset> datasets = new ArrayList<>();
		String anio = "";
		String anioAnterior = "";
		Dataset consumosAgrupadosAnioAnterior = new Dataset();
		Dataset consumosAgrupadosAnio = new Dataset();
		
		for (MisConsumosDashBoardDTO consumo: misConsumos) {
			if (anio.isEmpty())
				anio = consumo.getAnio();
			else
				if (Integer.valueOf(anio) <  Integer.valueOf(consumo.getAnio())) {
					anioAnterior = anio;
					anio = consumo.getAnio();
				}
		}
		
		ArrayList<Double> dataAnioAnterior = new ArrayList<>();	
		ArrayList<Double> dataAnio = new ArrayList<>();	
		String mont = "";
		
		for (MisConsumosDashBoardDTO consumo: misConsumos) {
			mont = consumo.getMes_str();
			if (!labels.contains(consumo.getDescription()))
				labels.add(consumo.getDescription());
			if (consumo.getAnio().equals(anioAnterior)) {
				dataAnioAnterior.add(consumo.getConsumo_mes());
			} else {
				dataAnio.add(consumo.getConsumo_mes());
			}
		}
		
		consumosAgrupadosAnioAnterior.setLabel(mont + " " + anioAnterior);
		consumosAgrupadosAnioAnterior.setData(dataAnioAnterior);
		ArrayList<String> backgroundColorAnioAnterior = new ArrayList<>();
		backgroundColorAnioAnterior.add("#F3AE25");
		backgroundColorAnioAnterior.add("#F3AE25");
		backgroundColorAnioAnterior.add("#F3AE25");
		backgroundColorAnioAnterior.add("#F3AE25");
		consumosAgrupadosAnioAnterior.setBackgroundColor(backgroundColorAnioAnterior);
		consumosAgrupadosAnioAnterior.setBorderColor("#F3AE25");
		consumosAgrupadosAnioAnterior.setBorderWidth(1);
		
		consumosAgrupadosAnio.setLabel(mont + " " + anio);
		consumosAgrupadosAnio.setData(dataAnio);
		ArrayList<String> backgroundColorAnio = new ArrayList<>();
		backgroundColorAnio.add("#3FA9F5");
		backgroundColorAnio.add("#3FA9F5");
		backgroundColorAnio.add("#3FA9F5");
		backgroundColorAnio.add("#3FA9F5");
		consumosAgrupadosAnio.setBackgroundColor(backgroundColorAnio);
		consumosAgrupadosAnio.setBorderColor("#3FA9F5");
		consumosAgrupadosAnio.setBorderWidth(1);
		
		datasets.add(consumosAgrupadosAnioAnterior);
		datasets.add(consumosAgrupadosAnio);
		
		
		ChartInfo infoMont = new ChartInfo();
		infoMont.setLabels(labels);
		infoMont.setDatasets(datasets);
		
		return infoMont;
	}
	
	//recibe el String con valor 20.601,00
	/*private Integer parserStringtoInt(String value) {
		Integer integer = 0;
		try {
			String parseado = value.replace(".", ""); //elimina los puntos de los miles
			String parseDouble = parseado.replace(",", ".");
			Double d = Double.valueOf(parseDouble);
			integer = (int) Math.round(d);
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
		}
		
		return integer;
	}*/
	
	private Double parserStringtoDouble(String value) {
		Double d = 0.0;
		try {
			String parseado = value.replace(".", ""); //elimina los puntos de los miles
			String parseDouble = parseado.replace(",", ".");
			d = Double.valueOf(parseDouble);
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
		}
		
		return d;
	}

}
