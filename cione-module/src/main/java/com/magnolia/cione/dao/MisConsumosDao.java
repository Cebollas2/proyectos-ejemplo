package com.magnolia.cione.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.naming.NamingException;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.MisConsumosDTO;
import com.magnolia.cione.dto.MisConsumosDashBoardDTO;

@ImplementedBy(MisConsumosDaoImpl.class)
public interface MisConsumosDao {
	public MisConsumosDTO getMisConsumos(String codSocio, Date fechaDesde, Date fechaHasta) throws NamingException;
	
	public ArrayList<MisConsumosDashBoardDTO> getMisConsumoMensualDashBoard(String codSocio, Locale locale);
	
	public ArrayList<MisConsumosDashBoardDTO> getMisConsumosAcumuladoAnualDashBoard(String codSocio, Locale locale);
	
	public ArrayList<MisConsumosDashBoardDTO> getMisConsumosUltimosDoceMesesDashBoard(String codSocio, Locale locale);
	
	public ArrayList<MisConsumosDashBoardDTO> getMisConsumosPorTipo(String codSocio, Locale locale);
	
}
