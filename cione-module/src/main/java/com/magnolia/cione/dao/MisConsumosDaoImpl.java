package com.magnolia.cione.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.CategoriaConsumoDTO;
import com.magnolia.cione.dto.ConsumoDTO;
import com.magnolia.cione.dto.DetalleConsumoDTO;
import com.magnolia.cione.dto.MisConsumosDTO;
import com.magnolia.cione.dto.MisConsumosDashBoardDTO;
import com.magnolia.cione.utils.CioneUtils;

public class MisConsumosDaoImpl implements MisConsumosDao {

	private static final Logger log = LoggerFactory.getLogger(MisConsumosDaoImpl.class);

	public MisConsumosDTO getMisConsumos(String codSocio, Date fechaDesde, Date fechaHasta) throws NamingException {
		
		// Primero obtenemos los datos para el periodo B, que comprende el rango de fechas introducidas por el usuario
		MisConsumosDTO periodoB = getMisConsumosForPeriodo(codSocio, fechaDesde, fechaHasta);
		
		// Si no se ha introducido fecha de fin o inicio en el buscador, utilizamos las encontradas en el periodo B menos un anio para el anterior
		if(fechaDesde == null) {
			fechaDesde = periodoB.getMinDate();
		}
		if(fechaHasta == null) {
			fechaHasta = periodoB.getMaxDate();
		}
		
		// Calculamos las fechas 1 anio antes para el periodo A
		Calendar cDesde = Calendar.getInstance();
		Calendar cHasta = Calendar.getInstance();
		cDesde.setTime(fechaDesde);
		cDesde.add(Calendar.YEAR, -1);
		cHasta.setTime(fechaHasta);
		cHasta.add(Calendar.YEAR, -1);

		// Obtenemos los datos del periodo A, el cual es un anio antes que las fechas introducidas en el B
		MisConsumosDTO periodoA = getMisConsumosForPeriodo(codSocio, cDesde.getTime(), cHasta.getTime());
		
		// Una vez tenemos los datos de los periodos A y B, se combinan para formar el objeto final
		MisConsumosDTO resultadoMisConsumos = new MisConsumosDTO();
		resultadoMisConsumos.setCantidadTotal(periodoB.getCantidadTotal());
		resultadoMisConsumos.setImporteTotal(periodoB.getImporteTotal());
		resultadoMisConsumos.setMaxDate(periodoB.getMaxDate());
		resultadoMisConsumos.setMinDate(periodoB.getMinDate());
		resultadoMisConsumos.getCategorias().addAll(periodoB.getCategorias());
		
		resultadoMisConsumos.setCantidadTotalAnterior(periodoA.getCantidadTotal());
		resultadoMisConsumos.setImporteTotalAnterior(periodoA.getImporteTotal());
		resultadoMisConsumos.setMinDateAnterior(cDesde.getTime());
		resultadoMisConsumos.setMaxDateAnterior(cHasta.getTime());
		
		// Iteramos sobre las categorias, consumos y detalles para combinar los datos y aniadir las que esten en un periodo pero no en otro
		for(CategoriaConsumoDTO categoriaPeriodoA : periodoA.getCategorias()) {
			
			// Comprobamos si esta categoria ya existe en el listado de categorias resultado
			CategoriaConsumoDTO categoriaPeriodoB = getCategoriaByLevel(resultadoMisConsumos.getCategorias(), categoriaPeriodoA.getLevel());
			if(categoriaPeriodoB != null) {
				categoriaPeriodoB.setCantidadAnterior(categoriaPeriodoA.getCantidad());
				categoriaPeriodoB.setImporteAnterior(categoriaPeriodoA.getImporte());
				
				// Iteramos sobre los consumos de la categoria
				for(ConsumoDTO consumoPeriodoA : categoriaPeriodoA.getConsumos()) {
					
					// Comprobamos si este consumo ya existe en el listado de consumos resultado
					ConsumoDTO consumoPeriodoB = getConsumoByLevel(categoriaPeriodoB.getConsumos(), consumoPeriodoA.getLevel());
					if(consumoPeriodoB != null) {
						consumoPeriodoB.setCantidadAnterior(consumoPeriodoA.getCantidad());
						consumoPeriodoB.setImporteAnterior(consumoPeriodoA.getImporte());
						
						// Iteramos sobre los detalles del consumo
						for(DetalleConsumoDTO detallePeriodoA : consumoPeriodoA.getDetalles()) {
							
							// Comprobamos si este detalle ya existe en el listado de detalles resultado
							DetalleConsumoDTO detallePeriodoB = getDetalleByLevel(consumoPeriodoB.getDetalles(), detallePeriodoA.getLevel());
							if(detallePeriodoB != null) {
								detallePeriodoB.setCantidadAnterior(detallePeriodoA.getCantidad());
								detallePeriodoB.setImporteAnterior(detallePeriodoA.getImporte());
								
							} else {
								// Si no existe ese detalle en el resultado, significa que solo esta en el periodo anterior
								transformDetalleToPrevious(detallePeriodoA);
								consumoPeriodoB.getDetalles().add(detallePeriodoA);
							}
						}
						
					} else {
						// Si no existe ese consumo en el resultado, significa que solo esta en el periodo anterior
						transformConsumoToPrevious(consumoPeriodoA);
						categoriaPeriodoB.getConsumos().add(consumoPeriodoA);
					}
				}
				
			} else {
				// Si no existe esa categoria en el resultado, significa que solo esta en el periodo anterior
				transformCategoryToPrevious(categoriaPeriodoA);
				resultadoMisConsumos.getCategorias().add(categoriaPeriodoA);
			}
			
		}
		
		return resultadoMisConsumos;
	}
	
	/**
	 * Realiza una consulta a base de datos para generar objeto MisConsumosDTO con las categorias, consumos y detalles de consumos para
	 * las que se ha realizado una consulta.
	 * @param codSocio Codigo del socio actual que realiza la consulta
	 * @param fechaDesde Fecha de inicio
	 * @param fechaHasta Fecha de fin
	 * @param minDate Fecha minima encontrada
	 * @param maxDate Fecha maxima encontrada
	 * @return Mapa con las categorias, consumos y detalles de consumo
	 * @throws NamingException
	 */
	private MisConsumosDTO getMisConsumosForPeriodo(String codSocio, Date fechaDesde, Date fechaHasta) throws NamingException {
		
		MisConsumosDTO misConsumos = new MisConsumosDTO();
		Context initContext = new InitialContext();	    
		DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
		
		if (fechaHasta == null) {
			fechaHasta = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(fechaHasta);
			int lastDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			c.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
			fechaHasta = c.getTime();
			
			//fechaHasta.setDate(lastDayOfMonth);
			
			
//			Calendar c = Calendar.getInstance();
//			c.setTime(new Date());
//			int lastDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
//			fechaHasta = new Date(lastDayOfMonth);
		}
		
		try (Connection con = ds.getConnection();
				PreparedStatement ps = createPreparedStatement(con, codSocio, fechaDesde, fechaHasta);
				ResultSet rs = ps.executeQuery()) {
			
			Map<String, CategoriaConsumoDTO> mapCategorias = new HashMap<>();
			Map<String, ConsumoDTO> mapConsumos = new HashMap<>();
			Map<String, DetalleConsumoDTO> mapDetalles = new HashMap<>();
			
			while (rs.next()) {
				
				Integer id = rs.getInt("id");
				
				String level1 = rs.getString("level1");
				String level2 = rs.getString("level2");
				String level3 = rs.getString("level3");
				
				String agrupacion1 = rs.getString("level1_desc");
				String agrupacion2 = rs.getString("level2_desc");
				String agrupacion3 = rs.getString("level3_desc");
											
				Double unit = rs.getDouble("unit");
				Double value = rs.getDouble("value");
				Date fecha = rs.getDate("date_consumo");

				// Primer nivel de la tabla. Categoria padre. 
				if (unit != 0) {
					CategoriaConsumoDTO categoria = mapCategorias.get(level1);
					
						if (categoria == null) {
							categoria = new CategoriaConsumoDTO();
							categoria.setNombre(agrupacion1);
							categoria.setId(id);
							categoria.setCantidad(unit);
							categoria.setImporte(value);
							categoria.setLevel(level1);
							mapCategorias.put(level1, categoria);
						} else {
							categoria.setCantidad(categoria.getCantidad() + unit);
							categoria.setImporte(categoria.getImporte() + value);
						}
					
					
					// Segundo nivel de la tabla. Consumo (o subcategoria)
					ConsumoDTO consumo = mapConsumos.get(level1+"-"+level2);
					if(consumo == null) {
						consumo = new ConsumoDTO();
						consumo.setNombre(agrupacion2);
						consumo.setId(id);
						consumo.setCantidad(unit);
						consumo.setImporte(value);
						consumo.setLevel(level2);
						consumo.setParent(level1);
						addConsumoToCategory(categoria, consumo);
						mapCategorias.replace(level1, categoria);
						mapConsumos.put(level1+"-"+level2, consumo);
					} else {
						consumo.setCantidad(consumo.getCantidad() + unit);
						consumo.setImporte(consumo.getImporte() + value);
					}
					
					// Tercer nivel de la tabla. Detalle del consumo
					DetalleConsumoDTO detalle = mapDetalles.get(level1+"-"+level2+"-"+level3);
					if(detalle == null) {
						detalle = new DetalleConsumoDTO();
						detalle.setNombre(agrupacion3);
						detalle.setCantidad(unit);
						detalle.setImporte(value);
						detalle.setLevel(level3);
						detalle.setParent(level2);
						detalle.setId(id);
						consumo.getDetalles().add(detalle);
						mapDetalles.put(level1+"-"+level2+"-"+level3, detalle);
					} else {
						detalle.setCantidad(detalle.getCantidad() + unit);
						detalle.setImporte(detalle.getImporte() + value);
					}
					
					//obtener fecha mínima y máxima
					if(misConsumos.getMinDate() != null) {
						if(fecha.before(misConsumos.getMinDate())) {
							misConsumos.setMinDate(fecha);
						}					
					}else {
						misConsumos.setMinDate(fecha);
					}
					if(misConsumos.getMaxDate() != null) {
						if(fecha.after(misConsumos.getMaxDate())) {
							misConsumos.setMaxDate(fecha);
						}
					}else {
						misConsumos.setMaxDate(fecha);
					}
				}
	        }
			
			Double importeTotal = 0.0;
			Double cantidadTotal = 0d;
			List<CategoriaConsumoDTO> list = new ArrayList<>();
			for (Map.Entry<String, CategoriaConsumoDTO> entry : mapCategorias.entrySet()) {
				List<ConsumoDTO> consumos = entry.getValue().getConsumos();
				Double importeCategoria = 0.0;
				Double cantidadCategoria = 0d;
				for (ConsumoDTO consumo : consumos) {
					importeCategoria += consumo.getImporte();
					cantidadCategoria += consumo.getCantidad();
				}
				entry.getValue().setImporte(importeCategoria);
				entry.getValue().setCantidad(cantidadCategoria);
				
				importeTotal += entry.getValue().getImporte();
				cantidadTotal += entry.getValue().getCantidad();
				list.add(entry.getValue());
			}
			
			misConsumos.setCategorias(list);
			misConsumos.setCantidadTotal(cantidadTotal);
			misConsumos.setImporteTotal(importeTotal);
			
			if(fechaDesde != null) {
				misConsumos.setMinDate(fechaDesde);
			}else {
				misConsumos.setMinDate(new Date());
			}
			if(fechaHasta != null) {
				misConsumos.setMaxDate(fechaHasta);
			}
			
		} catch (SQLException e) {
			log.error("Error al obtener los consumos en la BBDD", e);
		}
		
		return misConsumos;
	}
	
	/**
	 * Aniade un consumo a una categoria padre.
	 * <ul><li>Categoria padre<ul><li>Consumo 1 (o subcategoria 1)</li><li>Consumo 2 (o subcategoria 2)</li></ul></li></ul>
	 * @param categoria Categoria padre a la que se aniadira un consumo (o subcategoria)
	 * @param consumo Subcategoria que sera aniadida a una categoria padre
	 */
	private void addConsumoToCategory(CategoriaConsumoDTO categoria, ConsumoDTO consumo) {
		//si existe el consumo sumamos importe y cantidad		
		ConsumoDTO consumoExistente = getConsumoFromCategory(categoria.getConsumos(), consumo.getLevel());
		if(consumoExistente != null) {
			consumoExistente.setCantidad(consumoExistente.getCantidad() + consumo.getCantidad());
			consumoExistente.setImporte(consumoExistente.getImporte() + consumo.getImporte());
		}else {
			categoria.getConsumos().add(consumo);
		}		
	}
	
	private ConsumoDTO getConsumoFromCategory(List<ConsumoDTO> consumos, String level) {
		ConsumoDTO result = null;
		for (ConsumoDTO consumoDTO : consumos) {
			if(consumoDTO.getLevel().equals(level)) {
				result = consumoDTO;
				break;
			}
		}		
		return result;
	}

	/**
	 * Prepara una consulta sql para recuperar los consumos existentes en las tablas tbl_mis_consumos y tbl_agrupaciones
	 * @param con Conexion con la base de datos
	 * @param codSocio Codigo de socio que realiza la consulta
	 * @param fechaDesde Fecha de inicio
	 * @param fechaHasta Fecha de fin
	 * @return Sentencia para consulta con base de datos
	 * @throws SQLException
	 */
	private PreparedStatement createPreparedStatement(Connection con, String codSocio, Date fechaDesde, Date fechaHasta)
			throws SQLException {
		String sql = "select * from tbl_mis_consumos c"
				+ " left join tbl_agrupaciones a on c.level2 = a.level2 and c.level1 = a.level1 and c.level3 = a.level3"
				+ " where c.level1 is not null and id_socio = ?";
		
		
		if (fechaDesde != null) {
			sql += " and c.date_consumo >= ?";
		}
		if (fechaHasta != null) {
			sql += " and c.date_consumo <= ?";
		}
		sql += " order by c.level1,c.level2,c.level3";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, codSocio);
		
		
		if (fechaDesde != null) {
			ps.setDate(2, new java.sql.Date(fechaDesde.getTime()));
		}

		if (fechaHasta != null) {
			int pos = fechaDesde == null ? 2 : 3;
			ps.setDate(pos, new java.sql.Date(fechaHasta.getTime()));
		}
		//log.debug("createPreparedStatement() " + ps.toString());
		return ps;
	}
	
	/**
	 * Crea valor de cantidad e importe de una categoria, como cantidad e importe anteriores y deja en 0 los reales.
	 * Estos es para que una categoria sea considerada como del periodo anterior 
	 * @param categoria Categoria que se va a transformar
	 */
	private void transformCategoryToPrevious(CategoriaConsumoDTO categoria) {
		
		categoria.setCantidadAnterior(categoria.getCantidad());
		categoria.setImporteAnterior(categoria.getImporte());
		categoria.setCantidad(0d);
		categoria.setImporte(0d);
		
		for(ConsumoDTO consumo : categoria.getConsumos()) {
			consumo.setCantidadAnterior(consumo.getCantidad());
			consumo.setImporteAnterior(consumo.getImporte());
			consumo.setCantidad(0d);
			consumo.setImporte(0d);
			
			for(DetalleConsumoDTO detalle : consumo.getDetalles()) {
				detalle.setCantidadAnterior(detalle.getCantidad());
				detalle.setImporteAnterior(detalle.getImporte());
				detalle.setCantidad(0d);
				detalle.setImporte(0d);
			}
		}
	}
	
	private void transformConsumoToPrevious(ConsumoDTO consumo) {
		consumo.setCantidadAnterior(consumo.getCantidad());
		consumo.setImporteAnterior(consumo.getImporte());
		consumo.setCantidad(0d);
		consumo.setImporte(0d);
		
		for(DetalleConsumoDTO detalle : consumo.getDetalles()) {
			detalle.setCantidadAnterior(detalle.getCantidad());
			detalle.setImporteAnterior(detalle.getImporte());
			detalle.setCantidad(0d);
			detalle.setImporte(0d);
		}
	}
	
	private void transformDetalleToPrevious(DetalleConsumoDTO detalle) {
		detalle.setCantidadAnterior(detalle.getCantidad());
		detalle.setImporteAnterior(detalle.getImporte());
		detalle.setCantidad(0d);
		detalle.setImporte(0d);
	}
	
	/** 
	 * Busca una categoria dentro de un listado a partir de el campo level
	 * @param categorias Listado de categorias donde se buscara
	 * @param level Campo por el que se busca
	 * @return La categoria encontrada o null
	 */
	private CategoriaConsumoDTO getCategoriaByLevel(List<CategoriaConsumoDTO> categorias, String level) {
		CategoriaConsumoDTO result = null;
		for(CategoriaConsumoDTO categoria : categorias) {
			if(categoria.getLevel().equals(level))
				return categoria;
		}
		
		return result;
	}
	
	/**
	 * Busca un consumo dentro de un listado a partir de el campo level
	 * @param consumos Listado de consumos de una categoria
	 * @param level Campo por el que se busca
	 * @return El consumo encontrado o null
	 */
	private ConsumoDTO getConsumoByLevel(List<ConsumoDTO> consumos, String level) {
		ConsumoDTO result = null;
		for(ConsumoDTO consumo : consumos) {
			if(consumo.getLevel().equals(level))
				return consumo;
		}
		
		return result;
	}
	
	private DetalleConsumoDTO getDetalleByLevel(List<DetalleConsumoDTO> detalles, String level) {
		DetalleConsumoDTO result = null;
		for(DetalleConsumoDTO detalle : detalles) {
			if(detalle.getLevel().equals(level))
				return detalle;
		}
		
		return result;
	}
	
	@Override
	public ArrayList<MisConsumosDashBoardDTO> getMisConsumoMensualDashBoard(String codSocio, Locale locale) {
		
		ArrayList<MisConsumosDashBoardDTO> misConsumosDashBoardDTO = new ArrayList<>();
		
		Context initContext;
		try {
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
			try (Connection con = ds.getConnection();
				PreparedStatement ps = createPreparedStatementDashBoard(con, codSocio);
				ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					MisConsumosDashBoardDTO miConsumo = new MisConsumosDashBoardDTO();
					miConsumo.setLevel(rs.getInt("level1"));
					miConsumo.setDescription(rs.getString("descripcion"));
					miConsumo.setAnio(rs.getString("anio"));
					miConsumo.setMes(rs.getInt("mes"));
					
					Month month = Month.of(miConsumo.getMes());
					miConsumo.setMes_str(month.getDisplayName(TextStyle.FULL, locale));
					
					miConsumo.setConsumo_mes(rs.getDouble("consumo_mes"));
					miConsumo.setConsumo_mes_str(String.valueOf(miConsumo.getConsumo_mes()));
					misConsumosDashBoardDTO.add(miConsumo);
					
					/*if (consumosAgrupado.get(miConsumo.getLevel()) != null) {
						Double value = consumosAgrupado.get(miConsumo.getLevel());
						value += miConsumo.getConsumo_mes();
						consumosAgrupado.put(miConsumo.getLevel(), value);
						
						miConsumo.setConsumo_mes(value);
						miConsumo.setConsumo_mes_str(String.valueOf(miConsumo.getConsumo_mes()));
						
						//lo seteo en el listado
						for (MisConsumosDashBoardDTO consumo: misConsumosDashBoardDTOAux) {
							if (consumo.getLevel() == consumo.getLevel()) {
								consumo.setConsumo_mes(value);
								miConsumo.setConsumo_mes_str(String.valueOf(miConsumo.getConsumo_mes()));
								break;
							}
						}
					} else {
						misConsumosDashBoardDTOAux.add(miConsumo);
					}*/
				}
			}catch (SQLException e) {
				log.error("Error al obtener los consumos en la BBDD", e);
			}
		} catch (NamingException e) {
			log.error(e.getMessage(), e);
		}

		return misConsumosDashBoardDTO;
	}
	
	@Override
	public ArrayList<MisConsumosDashBoardDTO> getMisConsumosAcumuladoAnualDashBoard(String codSocio, Locale locale) {
		ArrayList<MisConsumosDashBoardDTO> misConsumosDashBoardDTO = new ArrayList<>();
		HashMap<String, Double> consumosAgrupado = new HashMap<>();
		
		Context initContext;
		try {
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
			double totalAnioAnterior =0.0;
			double totalAnio =0.0;
			String anioAnterior="";
			String anio="";
			try (Connection con = ds.getConnection();
				PreparedStatement ps = createPreparedStatementDashBoardAcumulado(con, codSocio);
				ResultSet rs = ps.executeQuery()) {
				//como va ordenado por anio, el primero sera el año anterior
				if (rs.first()) {
					anioAnterior = rs.getString("anio");
				}
				if (rs.last()) {
					anio = rs.getString("anio");
				    rs.beforeFirst(); 
				}
				
				while (rs.next()) {
					
					
					/*if (i==0) //como va ordenado por anio, el primero sera el año anterior
						anioAnterior = rs.getString("anio");
					if (!anioAnterior.equals(rs.getString("anio")))
						anio = rs.getString("anio");
					i++;*/
					MisConsumosDashBoardDTO miConsumo = new MisConsumosDashBoardDTO();
					miConsumo.setLevel(rs.getInt("level1"));
					miConsumo.setDescription(rs.getString("descripcion"));
					miConsumo.setAnio(rs.getString("anio"));
					miConsumo.setMes(rs.getInt("mes"));
					
					Month month = Month.of(miConsumo.getMes());
					miConsumo.setMes_str(month.getDisplayName(TextStyle.FULL, locale));
					
					miConsumo.setConsumo_mes(rs.getDouble("consumo_mes"));
					miConsumo.setConsumo_mes_str(String.valueOf(miConsumo.getConsumo_mes()));
					
					if ((consumosAgrupado.get(miConsumo.getDescription()+miConsumo.getAnio()) == null) ) {
						consumosAgrupado.put(miConsumo.getDescription()+miConsumo.getAnio(), miConsumo.getConsumo_mes());
						misConsumosDashBoardDTO.add(miConsumo);
					} else {
						/*Double consumoacumulado = consumosAgrupado.get(miConsumo.getDescription()+miConsumo.getAnio());
						log.debug("consumoacumulado = " + consumoacumulado);
						log.debug("+ miConsumo.getConsumo_mes() = " + miConsumo.getConsumo_mes());
						consumoacumulado += miConsumo.getConsumo_mes();
						log.debug("consumoacumulado = " + consumoacumulado);*/
						
						
						BigDecimal acumulado = BigDecimal.valueOf(consumosAgrupado.get(miConsumo.getDescription() + miConsumo.getAnio()));
						//log.debug("acumulado = " + acumulado);
						BigDecimal nuevoValor = BigDecimal.valueOf(miConsumo.getConsumo_mes());
						//log.debug("nuevoValor = " + nuevoValor);
						acumulado = acumulado.add(nuevoValor).setScale(2, RoundingMode.HALF_UP);
						
						double consumoacumulado = acumulado.doubleValue();
						//log.debug("total acumulado = " + consumoacumulado);
						consumosAgrupado.put(miConsumo.getDescription()+miConsumo.getAnio(), consumoacumulado);
						
						miConsumo.setConsumo_mes(consumoacumulado);
						miConsumo.setConsumo_mes_str(String.valueOf(miConsumo.getConsumo_mes()));
						
						//lo seteo en el listado
						for (MisConsumosDashBoardDTO consumo: misConsumosDashBoardDTO) {
							if ((consumo.getLevel() == miConsumo.getLevel()) && (consumo.getAnio().equals(miConsumo.getAnio()))) {
								consumo.setConsumo_mes(consumoacumulado);
								consumo.setConsumo_mes_str(String.valueOf(miConsumo.getConsumo_mes()));
								break;
							}
						}
					}
					
//					if (anioAnterior.equals(miConsumo.getAnio()))
//						totalAnioAnterior += miConsumo.getConsumo_mes();
//					else
//						totalAnio += miConsumo.getConsumo_mes();
					
				}
				
				for (MisConsumosDashBoardDTO consumo: misConsumosDashBoardDTO) {
					//log.debug(consumo.getAnio() + " -> " + CioneUtils.doubletoString(consumo.getConsumo_mes()));
					if (anioAnterior.equals(consumo.getAnio()))
						
						totalAnioAnterior += consumo.getConsumo_mes();
					else
						totalAnio += consumo.getConsumo_mes();
				}
				
				
				//incluimos el total
				MisConsumosDashBoardDTO miConsumoAnterior = new MisConsumosDashBoardDTO();
				miConsumoAnterior.setDescription("TOTAL");
				miConsumoAnterior.setAnio(anioAnterior);
				
				
				miConsumoAnterior.setConsumo_mes(CioneUtils.roundDouble(totalAnioAnterior));
				misConsumosDashBoardDTO.add(miConsumoAnterior);
				
				MisConsumosDashBoardDTO miConsumoAnio = new MisConsumosDashBoardDTO();
				miConsumoAnio.setDescription("TOTAL");
				miConsumoAnio.setAnio(anio);
				miConsumoAnio.setConsumo_mes(CioneUtils.roundDouble(totalAnio));
				misConsumosDashBoardDTO.add(miConsumoAnio);
				
				
			}catch (SQLException e) {
				log.error("Error al obtener los consumos en la BBDD", e);
			}
		} catch (NamingException e) {
			log.error(e.getMessage(), e);
		}

		return misConsumosDashBoardDTO;
	}
	
	@Override
	public ArrayList<MisConsumosDashBoardDTO> getMisConsumosUltimosDoceMesesDashBoard(String codSocio, Locale locale){
		ArrayList<MisConsumosDashBoardDTO> misConsumosDashBoardDTO = new ArrayList<>();
		Context initContext;
		try {
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
			
			try (Connection con = ds.getConnection();
				PreparedStatement ps = createPreparedStatementDashBoardUltimosDoceMesesDashBoard(con, codSocio);
				ResultSet rs = ps.executeQuery()) {
				
				while (rs.next()) {
					MisConsumosDashBoardDTO miConsumo = new MisConsumosDashBoardDTO();
					String mesAnio = rs.getString("mes"); //formato aaaa-mm
					if ((mesAnio!= null) && (mesAnio.split("-").length > 1)) { 
						String mes = mesAnio.split("-")[1];
						miConsumo.setAnio(mesAnio.split("-")[0]);
						miConsumo.setMes(Integer.valueOf(mes));
						
						Month month = Month.of(miConsumo.getMes());
						miConsumo.setMes_str(month.getDisplayName(TextStyle.FULL, locale));
						
						miConsumo.setConsumo_mes(rs.getDouble("consumo_mes"));
						miConsumo.setConsumo_mes_str(String.valueOf(miConsumo.getConsumo_mes()));
						misConsumosDashBoardDTO.add(miConsumo);
					}
				}
				
			}catch (SQLException e) {
				log.error("Error al obtener los consumos en la BBDD", e);
			}
		} catch (NamingException e) {
			log.error(e.getMessage(), e);
		}
		
		return misConsumosDashBoardDTO;
	}
	
	@Override
	public ArrayList<MisConsumosDashBoardDTO> getMisConsumosPorTipo(String codSocio, Locale locale){
		ArrayList<MisConsumosDashBoardDTO> misConsumosDashBoardDTO = new ArrayList<>();
		Context initContext;
		try {
			initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/Cione");
			
			try (Connection con = ds.getConnection();
				PreparedStatement ps = createPreparedStatementDashBoardPorTipo(con, codSocio);
				ResultSet rs = ps.executeQuery()) {
				
				while (rs.next()) {
					MisConsumosDashBoardDTO miConsumo = new MisConsumosDashBoardDTO();
					miConsumo.setLevel(rs.getInt("level1"));
					miConsumo.setDescription(rs.getString("descripcion"));
					miConsumo.setAnio(rs.getString("anio"));
					miConsumo.setMes(rs.getInt("mes"));
					Month month = Month.of(miConsumo.getMes());
					miConsumo.setMes_str(month.getDisplayName(TextStyle.FULL, locale));
					miConsumo.setConsumo_mes(rs.getDouble("consumo_mes"));
					miConsumo.setConsumo_mes_str(String.valueOf(miConsumo.getConsumo_mes()));
					misConsumosDashBoardDTO.add(miConsumo);
				}
				
			}catch (SQLException e) {
				log.error("Error al obtener los consumos en la BBDD", e);
			}
		} catch (NamingException e) {
			log.error(e.getMessage(), e);
		}
		
		return misConsumosDashBoardDTO;
	}
	
	private PreparedStatement createPreparedStatementDashBoardPorTipo(Connection con, String codSocio)
			throws SQLException {
		String sql = "SELECT 	c.level1, a.level1_desc AS descripcion, YEAR(c.date_consumo) AS anio, MONTH(c.date_consumo) AS mes,	"
				+ "	CAST(SUM(value) AS DECIMAL(10,2)) AS consumo_mes	"
				+ "	FROM mycione.tbl_mis_consumos AS c	"
				+ "    JOIN ("
				+ "		SELECT DISTINCT level1, level1_desc		"
				+ "			FROM mycione.tbl_agrupaciones	"
				+ "		) AS a ON c.level1 = a.level1		"
				+ "	WHERE c.id_socio = ?		"
				+ "    AND c.level1 IN ('1','2','3','4')		"
				+ "    AND ( "
				+ "			(c.date_consumo BETWEEN DATE_FORMAT(CURDATE(), '%Y-01-01') AND LAST_DAY(CURDATE()) + INTERVAL 1 DAY - INTERVAL 1 SECOND	)"
				+ "		)		"
				+ "	GROUP BY "
				+ "		c.level1,		"
				+ "        a.level1_desc,"
				+ "        anio,		"
				+ "        mes,"
				+ "        level1"
				+ "	ORDER BY anio, level1, mes;";
		
		PreparedStatement ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ps.setString(1, codSocio);
		//log.debug("createPreparedStatementDashBoardPorTipo() " + ps.toString());
		return ps;
	}
	
	private PreparedStatement createPreparedStatementDashBoardUltimosDoceMesesDashBoard(Connection con, String codSocio)
			throws SQLException {
		String sql = "SELECT"
				+ "		DATE_FORMAT(c.date_consumo, '%Y-%m') AS mes,"
				+ "		CAST(SUM(value) AS DECIMAL(10,2)) AS consumo_mes"
				+ "	FROM mycione.tbl_mis_consumos AS c"
				+ "	WHERE c.id_socio = ? "
				+ "		AND c.date_consumo >= DATE_SUB(CURDATE(), INTERVAL 24 MONTH)"
				+ "	GROUP BY mes"
				+ "	ORDER BY mes;";
		
		PreparedStatement ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ps.setString(1, codSocio);
		//log.debug("createPreparedStatementDashBoardUltimosDoceMesesDashBoard() " + ps.toString());
		return ps;
	}
	
	
	private PreparedStatement createPreparedStatementDashBoardAcumulado(Connection con, String codSocio)
			throws SQLException {
		String sql = "SELECT "
				+ "		c.level1,"
				+ "		a.level1_desc AS descripcion,"
				+ "		YEAR(c.date_consumo) AS anio,"
				+ "		MONTH(c.date_consumo) AS mes,"
				+ "		CAST(SUM(value) AS DECIMAL(10,2)) AS consumo_mes"
				+ "	FROM mycione.tbl_mis_consumos AS c"
				+ "	JOIN ("
				+ "		SELECT DISTINCT level1, level1_desc"
				+ "		FROM mycione.tbl_agrupaciones"
				+ "	) AS a ON c.level1 = a.level1"
				+ "	"
				+ "	WHERE c.id_socio = ?"
				+ "		AND c.level1 IN ('1','2','3','4')"
				+ "		AND ("
				+ "			(c.date_consumo BETWEEN "
				+ "				DATE_FORMAT(CURDATE(), '%Y-01-01') AND LAST_DAY(CURDATE()) + INTERVAL 1 DAY - INTERVAL 1 SECOND"
				+ "			) OR "
				+ "			(c.date_consumo BETWEEN "
				+ "				DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 YEAR), '%Y-01-01')"
				+ "					AND DATE_SUB(LAST_DAY(CURDATE()) + INTERVAL 1 DAY - INTERVAL 1 SECOND, INTERVAL 1 YEAR)"
				+ "			)"
				+ "		)"
				+ "	"
				+ "	GROUP BY"
				+ "		c.level1,"
				+ "		a.level1_desc,"
				+ "		anio,"
				+ "		mes"
				+ "	"
				+ "	ORDER BY anio, level1";
		
				//+ ResultSet.TYPE_SCROLL_INSENSITIVE;
			    //+ ResultSet.CONCUR_READ_ONLY;
		
		PreparedStatement ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ps.setString(1, codSocio);
		//log.debug("createPreparedStatementDashBoardAcumulado() " + ps.toString());
		return ps;
	}
	
	private PreparedStatement createPreparedStatementDashBoard(Connection con, String codSocio)
			throws SQLException {
		String sql = "SELECT "
				+ "		c.level1,"
				+ "		a.level1_desc AS descripcion,"
				+ "		YEAR(c.date_consumo) AS anio,"
				+ "		MONTH(c.date_consumo) AS mes,"
				+ "		CAST(SUM(value) AS DECIMAL(10,2)) AS consumo_mes"
				+ "	FROM mycione.tbl_mis_consumos AS c"
				+ "	JOIN ("
				+ "		SELECT DISTINCT level1, level1_desc"
				+ "		FROM mycione.tbl_agrupaciones"
				+ "	) AS a ON c.level1 = a.level1"
				+ "	"
				+ "	WHERE c.id_socio = ?"
				+ "		AND c.level1 IN ('1','2','3','4')"
				+ "		AND ("
				+ "			(YEAR(c.date_consumo) = YEAR(CURDATE())   AND MONTH(c.date_consumo) = MONTH(CURDATE())-1)"
				+ "			OR"
				+ "			(YEAR(c.date_consumo) = YEAR(CURDATE())-1 AND MONTH(c.date_consumo) = MONTH(CURDATE())-1)"
				+ "		)"
				+ "	"
				+ "	GROUP BY"
				+ "		c.level1,"
				+ "		a.level1_desc,"
				+ "		anio,"
				+ "		mes"
				+ "	"
				+ "	ORDER BY anio, level1;";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, codSocio);
		
		//log.debug("getCionesAcumuladosGrupoTotalView() " + ps.toString());
		return ps;
	}

}
