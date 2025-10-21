package com.magnolia.cione.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.MisConsumosDTO;

@ImplementedBy(ExportServiceImpl.class)
public interface ExportService {

	public HSSFWorkbook exportToExcel(List<List<String>> data);

	public HSSFWorkbook exportConsumoToExcel(MisConsumosDTO misConsumosDTO);
}
