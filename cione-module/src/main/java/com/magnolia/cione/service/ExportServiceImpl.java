package com.magnolia.cione.service;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import com.magnolia.cione.constants.MyshopConstants;
import com.magnolia.cione.dto.CategoriaConsumoDTO;
import com.magnolia.cione.dto.ConsumoDTO;
import com.magnolia.cione.dto.DetalleConsumoDTO;
import com.magnolia.cione.dto.MisConsumosDTO;
import com.magnolia.cione.utils.CioneUtils;

import info.magnolia.cms.security.SecuritySupport;
import info.magnolia.cms.security.User;
import info.magnolia.cms.security.UserManager;
import info.magnolia.jcr.util.NodeUtil;

public class ExportServiceImpl implements ExportService {
	
	@Inject
	private SecuritySupport securitySupport;
	
	@Override
	public HSSFWorkbook exportToExcel(List<List<String>> data) {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("DATOS");

		int rowNum = 0;
		for (List<String> row : data) {
			HSSFRow file = sheet.createRow(rowNum++);
			int colNum = 0;
			for (String col : row) {
				file.createCell(colNum++).setCellValue(col);
			}
		}
		return workbook;

	}

	@Override
	public HSSFWorkbook exportConsumoToExcel(MisConsumosDTO misConsumosDTO) {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("DATOS");
		
		if(misConsumosDTO != null) {
			int rowCont = 0;
			
			// Cabecera superior
			//CellStyle styleHeader = getCellStyle(workbook, IndexedColors.LIGHT_ORANGE.getIndex(), HSSFColor.WHITE.index, Font.BOLDWEIGHT_BOLD, CellStyle.ALIGN_CENTER);
			HSSFColor.HSSFColorPredefined.WHITE.getIndex();
			
			CellStyle stringStyle = workbook.createCellStyle();
			stringStyle.setAlignment(HorizontalAlignment.CENTER);
			CellStyle stringStyleGeneral = workbook.createCellStyle();
			stringStyleGeneral.setAlignment(HorizontalAlignment.GENERAL);
			CellStyle stringStyleRight = workbook.createCellStyle();
			stringStyleRight.setAlignment(HorizontalAlignment.RIGHT);
			
			CellStyle styleHeader = getCellStyle(workbook, IndexedColors.LIGHT_ORANGE.getIndex(), 
					HSSFColor.HSSFColorPredefined.WHITE.getIndex(), (short)1, stringStyle);
			Row rowHeader = sheet.createRow(rowCont++);
			Cell cell = rowHeader.createCell(2);
			cell.setCellValue("Periodo 1");
			cell.setCellStyle(styleHeader);
			sheet.addMergedRegion(new CellRangeAddress(0,0,2,3));
			cell = rowHeader.createCell(4);
			cell.setCellValue("Periodo 2");
			cell.setCellStyle(styleHeader);
			sheet.addMergedRegion(new CellRangeAddress(0,0,4,5));
			cell = rowHeader.createCell(6);
			cell.setCellValue("Diferencia");
			cell.setCellStyle(styleHeader);
			sheet.addMergedRegion(new CellRangeAddress(0,0,6,7));
			cell = rowHeader.createCell(8);
			cell.setCellValue("%");
			cell.setCellStyle(styleHeader);
			sheet.addMergedRegion(new CellRangeAddress(0,0,8,9));
			
			// Linea periodos
			CellStyle styleTerms = getCellStyle(workbook, (short)-1, (short)-1, (short)-1, stringStyle);
			Row rowPeriodos = sheet.createRow(rowCont++);
			cell = rowPeriodos.createCell(2);
			cell.setCellValue(misConsumosDTO.getMinDateAnteriorView() + " - " + misConsumosDTO.getMaxDateAnteriorView());
			cell.setCellStyle(styleTerms);
			sheet.addMergedRegion(new CellRangeAddress(1,1,2,3));
			cell = rowPeriodos.createCell(4);
			cell.setCellValue(misConsumosDTO.getMinDateView() + " - " + misConsumosDTO.getMaxDateView());
			cell.setCellStyle(styleTerms);
			sheet.addMergedRegion(new CellRangeAddress(1,1,4,5));
			cell = rowPeriodos.createCell(6);
			cell.setCellValue("Periodo 2 - Periodo 1");
			sheet.addMergedRegion(new CellRangeAddress(1,1,6,7));
			cell.setCellStyle(styleTerms);
			cell = rowPeriodos.createCell(8);
			cell.setCellValue("(Diferencia X 100) / Periodo 1");
			sheet.addMergedRegion(new CellRangeAddress(1,1,8,9));
			cell.setCellStyle(styleTerms);
			
			// Segunda cabecera
			int colCont = 0;
			/*CellStyle styleHeaderNormal = getCellStyle(workbook, IndexedColors.LIGHT_ORANGE.getIndex(), 
					HSSFColor.HSSFColorPredefined.WHITE.getIndex(), Font.BOLDWEIGHT_BOLD, CellStyle.ALIGN_GENERAL);
			CellStyle styleHeaderRight = getCellStyle(workbook, IndexedColors.LIGHT_ORANGE.getIndex(), 
					HSSFColor.HSSFColorPredefined.WHITE.getIndex(), Font.BOLDWEIGHT_BOLD, CellStyle.ALIGN_RIGHT);*/
			
			CellStyle styleHeaderNormal = getCellStyle(workbook, IndexedColors.LIGHT_ORANGE.getIndex(), 
					HSSFColor.HSSFColorPredefined.WHITE.getIndex(), (short)1, stringStyleGeneral);
			CellStyle styleHeaderRight = getCellStyle(workbook, IndexedColors.LIGHT_ORANGE.getIndex(), 
					HSSFColor.HSSFColorPredefined.WHITE.getIndex(), (short)1, stringStyleRight);
			Row rowHeader2 = sheet.createRow(rowCont++);
			
			/* comentamos la columna agrupador*/
			cell = rowHeader2.createCell(colCont++);
			//cell.setCellValue("Agrupador");
			cell.setCellValue("");
			cell.setCellStyle(styleHeaderNormal);
			
			cell = rowHeader2.createCell(colCont++);
			cell.setCellValue("Descripción");
			cell.setCellStyle(styleHeaderNormal);
			for(int i=0; i<4; i++) {
				cell = rowHeader2.createCell(colCont++);
				cell.setCellValue("Cantidad");
				cell.setCellStyle(styleHeaderRight);
				cell = rowHeader2.createCell(colCont++);
				cell.setCellValue("Importe");
				cell.setCellStyle(styleHeaderRight);
			}
			
			// Iteramos sobre las categorias
			for(CategoriaConsumoDTO categoria : misConsumosDTO.getCategorias()) {
				
				createCategoryRow(categoria, sheet, rowCont++);
				
				// Iteramos sobre los consumos
				for(ConsumoDTO consumo : categoria.getConsumos()) {
					
					createConsumoRow(categoria, consumo, sheet, rowCont++);
					
					// Iteramos sobre los detalle de consumos
					for(DetalleConsumoDTO detalle : consumo.getDetalles()) {
						
						createDetalleConsumoRow(categoria, consumo, detalle, sheet, rowCont++);
						
					}
				}
			}
			
			// Total
			/*CellStyle styleTotalNormal = getCellStyle(workbook, IndexedColors.LIGHT_BLUE.getIndex(), HSSFColor.BLACK.index, Font.BOLDWEIGHT_BOLD, CellStyle.ALIGN_GENERAL);
			CellStyle styleTotalRight = getCellStyle(workbook, IndexedColors.LIGHT_BLUE.getIndex(), HSSFColor.BLACK.index, Font.BOLDWEIGHT_BOLD, CellStyle.ALIGN_RIGHT);*/
			CellStyle styleTotalNormal = getCellStyle(workbook, IndexedColors.LIGHT_BLUE.getIndex(), 
					HSSFColor.HSSFColorPredefined.BLACK.getIndex(), (short)1, stringStyleGeneral);
			CellStyle styleTotalRight = getCellStyle(workbook, IndexedColors.LIGHT_BLUE.getIndex(), 
					HSSFColor.HSSFColorPredefined.BLACK.getIndex(), (short)1, stringStyleRight);
			colCont = 0;
			Row rowTotal = sheet.createRow(rowCont++);
			cell = rowTotal.createCell(colCont++);
			cell.setCellStyle(styleTotalNormal);
			cell = rowTotal.createCell(colCont++);
			cell.setCellValue("TOTAL");
			cell.setCellStyle(styleTotalNormal);
			cell = rowTotal.createCell(colCont++);
			cell.setCellValue(misConsumosDTO.getCantidadTotalAnteriorView()); 	// Cantidad total periodo anterior
			cell.setCellStyle(styleTotalRight);
			cell = rowTotal.createCell(colCont++);
			cell.setCellValue(hasPermission() ? misConsumosDTO.getImporteTotalAnteriorView() : ""); 	// Importe total periodo anterior
			cell.setCellStyle(styleTotalRight);
			cell = rowTotal.createCell(colCont++);
			cell.setCellValue(misConsumosDTO.getCantidadTotalView()); 			// Cantidad total
			cell.setCellStyle(styleTotalRight);
			cell = rowTotal.createCell(colCont++);
			cell.setCellValue(hasPermission() ? misConsumosDTO.getImporteTotalView() : ""); 			// Importe total
			cell.setCellStyle(styleTotalRight);
			cell = rowTotal.createCell(colCont++);
			cell.setCellValue(misConsumosDTO.getDiferenciaCantidadTotalView());	// Diferencia cantidad total
			cell.setCellStyle(styleTotalRight);
			cell = rowTotal.createCell(colCont++);
			cell.setCellValue(hasPermission() ? misConsumosDTO.getDiferenciaImporteTotalView() : "");	// Importe importe total
			cell.setCellStyle(styleTotalRight);
			cell = rowTotal.createCell(colCont++);
			cell.setCellValue(misConsumosDTO.getPorcentajeCantidadTotalView());	// Porcentaje cantidad total
			cell.setCellStyle(styleTotalRight);
			cell = rowTotal.createCell(colCont++);
			cell.setCellValue(hasPermission() ? misConsumosDTO.getPorcentajeImporteTotalView() : "");	// Porcentaje importe total
			cell.setCellStyle(styleTotalRight);
		}
		
		return workbook;
	}
	
	/**
	 * Crea la informacion de una fila de categoria
	 * @param categoria Categoria donde se obtiene la informacion
	 * @param sheet Hoja de excel
	 * @param rowCont Numero de la fila que se creara
	 */
	private void createCategoryRow(CategoriaConsumoDTO categoria, HSSFSheet sheet, int rowCont) {
		
		int colCont = 0;
        
		HSSFPalette palette = sheet.getWorkbook().getCustomPalette();
		HSSFColor myColor = palette.findSimilarColor(0, 255, 0);
		
		CellStyle stringStyleGeneral = sheet.getWorkbook().createCellStyle();
		stringStyleGeneral.setAlignment(HorizontalAlignment.GENERAL);
		CellStyle stringStyleRight = sheet.getWorkbook().createCellStyle();
		stringStyleRight.setAlignment(HorizontalAlignment.RIGHT);
        /*CellStyle styleHeaderNormal = getCellStyle(sheet.getWorkbook(), myColor.getIndex(), IndexedColors.BLACK.getIndex(), Font.BOLDWEIGHT_BOLD, CellStyle.ALIGN_GENERAL);
        CellStyle styleHeaderRight = getCellStyle(sheet.getWorkbook(), myColor.getIndex(), IndexedColors.BLACK.getIndex(), Font.BOLDWEIGHT_BOLD, CellStyle.ALIGN_RIGHT);
        CellStyle styleHeaderRightRed = getCellStyle(sheet.getWorkbook(), myColor.getIndex(), IndexedColors.RED.getIndex(), Font.BOLDWEIGHT_BOLD, CellStyle.ALIGN_RIGHT);*/
        
        CellStyle styleHeaderNormal = getCellStyle(sheet.getWorkbook(), myColor.getIndex(), IndexedColors.BLACK.getIndex(), (short)1, stringStyleGeneral);
        CellStyle styleHeaderRight = getCellStyle(sheet.getWorkbook(), myColor.getIndex(), IndexedColors.BLACK.getIndex(), (short)1, stringStyleRight);
        CellStyle styleHeaderRightRed = getCellStyle(sheet.getWorkbook(), myColor.getIndex(), IndexedColors.RED.getIndex(), (short)1, stringStyleRight);
		
		Row row = sheet.createRow(rowCont++);
		Cell cell = row.createCell(colCont++);
		//cell.setCellValue(categoria.getLevel()); 					// Agrupador
		cell.setCellValue("");  //dejamos un espacion en vez del agrupador
		cell.setCellStyle(styleHeaderNormal);
		cell = row.createCell(colCont++);
		cell.setCellValue(categoria.getNombre()); 					// Descripcion
		cell.setCellStyle(styleHeaderNormal);
		cell = row.createCell(colCont++);
		cell.setCellValue(categoria.getCantidadAnteriorView()); 	// Cantidad periodo anterior
		cell.setCellStyle(categoria.getCantidadAnterior() >= 0 ? styleHeaderRight : styleHeaderRightRed);
		cell = row.createCell(colCont++);
		cell.setCellValue(hasPermission() ? categoria.getImporteAnteriorView() : ""); 		// Importe periodo anterior
		cell.setCellStyle(categoria.getImporteAnterior() >= 0 ? styleHeaderRight : styleHeaderRightRed);
		cell = row.createCell(colCont++);
		cell.setCellValue(categoria.getCantidadView()); 			// Cantidad
		cell.setCellStyle(categoria.getCantidad() >= 0 ? styleHeaderRight : styleHeaderRightRed);
		cell = row.createCell(colCont++);
		cell.setCellValue(hasPermission() ? categoria.getImporteView() : ""); 				// Importe
		cell.setCellStyle(categoria.getImporte() >= 0 ? styleHeaderRight : styleHeaderRightRed);
		cell = row.createCell(colCont++);
		cell.setCellValue(categoria.getDiferenciaCantidadView()); 	// Cantidad diferencia
		//cell.setCellStyle(categoria.getCantidadAnterior() - categoria.getCantidad() >= 0 ? styleHeaderRight : styleHeaderRightRed);
		cell.setCellStyle(categoria.getCantidad() - categoria.getCantidadAnterior() >= 0 ? styleHeaderRight : styleHeaderRightRed);
		cell = row.createCell(colCont++);
		cell.setCellValue(hasPermission() ? categoria.getDiferenciaImporteView() : ""); 	// Importe diferencia
		//cell.setCellStyle(categoria.getImporteAnterior() - categoria.getImporte() >= 0 ? styleHeaderRight : styleHeaderRightRed);
		cell.setCellStyle(categoria.getImporte() - categoria.getImporteAnterior() >= 0 ? styleHeaderRight : styleHeaderRightRed);
		cell = row.createCell(colCont++);
		cell.setCellValue(categoria.getPorcentajeCantidadView()); 	// Cantidad porcentaje
		cell.setCellStyle(getPorcentaje(categoria.getCantidadAnterior(), categoria.getCantidad()) >= 0 ? styleHeaderRight : styleHeaderRightRed);
		cell = row.createCell(colCont++);
		cell.setCellValue(hasPermission() ? categoria.getPorcentajeImporteView() : ""); 	// Importe porcentaje
		cell.setCellStyle(getPorcentaje(categoria.getImporteAnterior(), categoria.getImporte()) >= 0 ? styleHeaderRight : styleHeaderRightRed);
	}
	
	/**
	 * Crea la informacion de una fila de consumo
	 * @param categoria Categoria donde se obtiene el campo level para agrupar
	 * @param consumo Consumo donde se obtiene la informacion
	 * @param sheet Hoja de excel
	 * @param rowCont Numero de la fila que se creara
	 */
	private void createConsumoRow(CategoriaConsumoDTO categoria, ConsumoDTO consumo, HSSFSheet sheet, int rowCont) {
		
		int colCont = 0;
		CellStyle stringStyleGeneral = sheet.getWorkbook().createCellStyle();
		stringStyleGeneral.setAlignment(HorizontalAlignment.GENERAL);
		CellStyle stringStyleRight = sheet.getWorkbook().createCellStyle();
		stringStyleRight.setAlignment(HorizontalAlignment.RIGHT);
        
        CellStyle styleHeaderNormal = getCellStyle(sheet.getWorkbook(), IndexedColors.LIGHT_GREEN.getIndex(), (short)-1, (short)1, stringStyleGeneral);
        CellStyle styleHeaderRight = getCellStyle(sheet.getWorkbook(), IndexedColors.LIGHT_GREEN.getIndex(), (short)-1, (short)1, stringStyleRight);
        CellStyle styleHeaderRightRed = getCellStyle(sheet.getWorkbook(), IndexedColors.LIGHT_GREEN.getIndex(), IndexedColors.RED.getIndex(), (short)1, stringStyleRight);
		
		Row row = sheet.createRow(rowCont++);
		Cell cell = row.createCell(colCont++);
		//cell.setCellValue(categoria.getLevel() + "-" + consumo.getLevel()); // Agrupador
		cell.setCellValue("");  //dejamos un espacio en vez del Agrupador
		cell.setCellStyle(styleHeaderNormal);
		cell = row.createCell(colCont++);
		cell.setCellValue(consumo.getNombre()); 					// Descripcion
		cell.setCellStyle(styleHeaderNormal);
		cell = row.createCell(colCont++);
		cell.setCellValue(consumo.getCantidadAnteriorView()); 		// Cantidad periodo anterior
		cell.setCellStyle(consumo.getCantidadAnterior() >= 0 ? styleHeaderRight : styleHeaderRightRed);
		cell = row.createCell(colCont++);
		cell.setCellValue(hasPermission() ? consumo.getImporteAnteriorView() : ""); 		// Importe periodo anterior
		cell.setCellStyle(consumo.getImporteAnterior() >= 0 ? styleHeaderRight : styleHeaderRightRed);
		cell = row.createCell(colCont++);
		cell.setCellValue(consumo.getCantidadView()); 				// Cantidad
		cell.setCellStyle(consumo.getCantidad() >= 0 ? styleHeaderRight : styleHeaderRightRed);
		cell = row.createCell(colCont++);
		cell.setCellValue(hasPermission() ? consumo.getImporteView() : ""); 				// Importe
		cell.setCellStyle(consumo.getImporte() >= 0 ? styleHeaderRight : styleHeaderRightRed);
		cell = row.createCell(colCont++);
		cell.setCellValue(consumo.getDiferenciaCantidadView()); 	// Cantidad diferencia
		//cell.setCellStyle(consumo.getCantidadAnterior() - consumo.getCantidad() >= 0 ? styleHeaderRight : styleHeaderRightRed);
		cell.setCellStyle(consumo.getCantidad() - consumo.getCantidadAnterior() >= 0 ? styleHeaderRight : styleHeaderRightRed);
		cell = row.createCell(colCont++);
		cell.setCellValue(hasPermission() ? consumo.getDiferenciaImporteView() : ""); 		// Importe diferencia
		//cell.setCellStyle(consumo.getImporteAnterior() - consumo.getImporte() >= 0 ? styleHeaderRight : styleHeaderRightRed);
		cell.setCellStyle(consumo.getImporte() - consumo.getImporteAnterior() >= 0 ? styleHeaderRight : styleHeaderRightRed);
		cell = row.createCell(colCont++);
		cell.setCellValue(consumo.getPorcentajeCantidadView()); 	// Cantidad porcentaje
		cell.setCellStyle(getPorcentaje(consumo.getCantidadAnterior(), consumo.getCantidad()) >= 0 ? styleHeaderRight : styleHeaderRightRed);
		cell = row.createCell(colCont++);
		cell.setCellValue(hasPermission() ? consumo.getPorcentajeImporteView() : ""); 	// Importe porcentaje
		cell.setCellStyle(getPorcentaje(consumo.getImporteAnterior(), consumo.getImporte()) >= 0 ? styleHeaderRight : styleHeaderRightRed);
	}
	
  private boolean hasPermission() {
		
		boolean res = false;
		
		// TODO: mirar propiedades antes
		
		Node userNode;
		try {
			String idUser = CioneUtils.getIdCurrentClient();
			UserManager um = securitySupport.getUserManager("public");
			User user = um.getUser(idUser);
			//userNode = NodeUtil.getNodeByIdentifier("users", MgnlContext.getUser().getIdentifier());
			userNode = NodeUtil.getNodeByIdentifier("users", user.getIdentifier());
			Collection<String> userRoles = user.getRoles();
			if(userNode.hasNode(MyshopConstants.VIEWPRICES_USER_NODE_NAME)) {
				Node priceNode = userNode.getNode(MyshopConstants.VIEWPRICES_USER_NODE_NAME);
				if (priceNode.hasProperty(MyshopConstants.PRICE_CONFIGURATION_PROPERTY)) {
					if (priceNode.getProperty(MyshopConstants.PRICE_CONFIGURATION_PROPERTY).getString().equals("pvo") 
							|| priceNode.getProperty(MyshopConstants.PRICE_CONFIGURATION_PROPERTY).getString().equals("pvp-pvo")) {
						res = true;
					}
				} else {
					if (userRoles.contains(MyshopConstants.PVP_PVO_ROL)) {
						res = true;
					} else if (userRoles.contains(MyshopConstants.PVO_ROL)) {
						res = true;
					} else if (userRoles.contains(MyshopConstants.PVP_ROL)) {
						res = false;
					} else if (userRoles.contains(MyshopConstants.HIDDEN_PRICES_ROL)) {
						res = false;
					} else { 
						res = true;
					}
				}
			}else {
				if (userRoles.contains(MyshopConstants.PVP_PVO_ROL)) {
					res = true;
				} else if (userRoles.contains(MyshopConstants.PVO_ROL)) {
					res = true;
				} else if (userRoles.contains(MyshopConstants.PVP_ROL)) {
					res = false;
				} else if (userRoles.contains(MyshopConstants.HIDDEN_PRICES_ROL)) {
					res = false;
				} else { //si no tiene ninguno permitimos ver precio
					res = true;
				}
			}
				
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	private boolean isSocio(String user) {
		
		boolean res = false;
		
		if (!StringUtils.isEmpty(user)) {
			if (user.endsWith("00")) {
				res = true;
			}
		}
		
		return res;
	}

	/**
	 * Crea la informacion de una fila de detalle de consumo
	 * @param categoria Categoria donde se obtiene el campo level para agrupar
	 * @param consumo Consumo donde se obtiene la informacion
	 * @param categoria Categoria donde se obtiene el campo level para agrupar
	 * @param sheet Hoja de excel
	 * @param rowCont Numero de la fila que se creara
	 */
	private void createDetalleConsumoRow(CategoriaConsumoDTO categoria, ConsumoDTO consumo, DetalleConsumoDTO detalle, HSSFSheet sheet, int rowCont) {
		
		int colCont = 0;
		CellStyle stringStyleRight = sheet.getWorkbook().createCellStyle();
		stringStyleRight.setAlignment(HorizontalAlignment.RIGHT);
		
        CellStyle styleRight = getCellStyle(sheet.getWorkbook(), (short)-1, (short)-1, (short)-1, stringStyleRight);
        CellStyle styleRightRed = getCellStyle(sheet.getWorkbook(), (short)-1, IndexedColors.RED.getIndex(), (short)-1, stringStyleRight);
		
		Row row = sheet.createRow(rowCont++);
		Cell cell = row.createCell(colCont++);
		//cell.setCellValue(categoria.getLevel() + "-" + consumo.getLevel() + "-" + detalle.getLevel()); 	// Agrupador
		cell.setCellValue(""); //dejamos un espacio en vez de agrupador
		cell = row.createCell(colCont++);
		cell.setCellValue(detalle.getNombre()); 					// Descripcion
		cell = row.createCell(colCont++);
		cell.setCellStyle(styleRight);
		cell.setCellValue(detalle.getCantidadAnteriorView()); 		// Cantidad periodo anterior
		cell = row.createCell(colCont++);
		cell.setCellStyle(detalle.getImporteAnterior() >= 0 ? styleRight : styleRightRed);
		cell.setCellValue(hasPermission() ? detalle.getImporteAnteriorView() : ""); 		// Importe periodo anterior
		cell = row.createCell(colCont++);
		cell.setCellStyle(detalle.getCantidad() >= 0 ? styleRight : styleRightRed);
		cell.setCellValue(detalle.getCantidadView()); 				// Cantidad 
		cell = row.createCell(colCont++);
		cell.setCellStyle(detalle.getImporte() >= 0 ? styleRight : styleRightRed);
		cell.setCellValue(hasPermission() ? detalle.getImporteView() : ""); 				// Importe 
		cell = row.createCell(colCont++);
		//cell.setCellStyle(detalle.getCantidadAnterior() - detalle.getCantidad() >= 0 ? styleRight : styleRightRed);
		cell.setCellStyle(detalle.getCantidad() - detalle.getCantidadAnterior() >= 0 ? styleRight : styleRightRed);
		cell.setCellValue(detalle.getDiferenciaCantidadView()); 	// Cantidad diferencia 
		cell = row.createCell(colCont++);
		//cell.setCellStyle(detalle.getImporteAnterior() - detalle.getImporte() >= 0 ? styleRight : styleRightRed);
		cell.setCellStyle(detalle.getImporte() - detalle.getImporteAnterior() >= 0 ? styleRight : styleRightRed);
		cell.setCellValue(hasPermission() ? detalle.getDiferenciaImporteView() : ""); 		// Importe diferencia 
		cell = row.createCell(colCont++);
		cell.setCellValue(detalle.getPorcentajeCantidadView()); 	// Cantidad porcentaje
		cell.setCellStyle(getPorcentaje(detalle.getCantidadAnterior(), detalle.getCantidad()) >= 0 ? styleRight : styleRightRed);
		cell = row.createCell(colCont++);
		cell.setCellValue(hasPermission() ? detalle.getPorcentajeImporteView() : ""); 	// Importe porcentaje
		cell.setCellStyle(getPorcentaje(detalle.getImporteAnterior(), detalle.getImporte()) >= 0 ? styleRight : styleRightRed);
	}
	
	/**
	 * Genera el estilo que se aplicara a un celda a partir de los parametros que se le pasan
	 * @param workbook Fichero excel
	 * @param bgColor Color de fondo
	 * @param fontColor Color de fuente
	 * @param fontWeight Tipo de fuente
	 * @param align Alineacion
	 * @return El estilo generado
	 */
	//private CellStyle getCellStyle(HSSFWorkbook workbook, short bgColor, short fontColor, short fontWeight, short align) {
	private CellStyle getCellStyle(HSSFWorkbook workbook, short bgColor, short fontColor, short fontWeight, CellStyle align) {
		
		CellStyle style = workbook.createCellStyle(); 
		if(bgColor != -1) {
			style.setFillForegroundColor(bgColor);
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			//style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}
		
		style.setAlignment(align.getAlignment());
		/*if(align != -1) {
			style.setAlignment(align);
		}*/
		
		Font font = workbook.createFont();
		if(fontColor != -1) {
			font.setColor(fontColor);
	        style.setFont(font);
		}
		
		if(fontWeight != -1) {
			font.setBold(true);
			style.setFont(font);
		}
        
        return style;
	}
	
	private double getPorcentaje(double periodo2, double periodo1) {
		if(periodo2 != 0)
			return (periodo1 * 100) / periodo2;
		
		return 100d;
	}

}
