package com.magnolia.cione.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.constants.CioneConstants;
import com.magnolia.cione.dto.FacturaDocDTO;
import com.magnolia.cione.dto.FacturaDocQueryParamsDTO;
import com.magnolia.cione.dto.FacturaQueryParamsDTO;
import com.magnolia.cione.dto.LineaCondicionDTO;
import com.magnolia.cione.dto.LineaFacturaDTO;
import com.magnolia.cione.dto.PaginaFacturaDocDTO;
import com.magnolia.cione.dto.PaginaFacturasDTO;
import com.magnolia.cione.utils.CioneUtils;

public class FacturaServiceImpl implements FacturaService {
	
	private static final Logger log = LoggerFactory.getLogger(FacturaServiceImpl.class);

	@Inject
	private ConfigService configService;
	
	@Inject
	private ElasticService elasticService;
	
	@Inject
	private MiddlewareService middlewareService;

	@Override
	public PaginaFacturaDocDTO getDocumentos(FacturaDocQueryParamsDTO filter) {

		PaginaFacturaDocDTO paginaFacturaDocDTO = new PaginaFacturaDocDTO();
		List<FacturaDocDTO> listF = new ArrayList<>();
		filter.setCodSocio(CioneUtils.getIdCurrentClientERP());
		filter.setNifSocio(middlewareService.getUserFromERP(CioneUtils.getIdCurrentClientERP()).getNif());
		if(filter.getFechaDesde() == null || filter.getFechaDesde().isEmpty())
			filter.setFechaDesde("01-01-2018");

		

		String dirRaiz = configService.getConfig().getInvoiceDocsPath() + CioneConstants.PATH_SEPARATOR + filter.getTipoDocumento();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDesde = LocalDate.parse(filter.getFechaDesde(), formatter);
		
		LocalDate localHasta = LocalDate.now();
		if ((filter.getFechaHasta() != null) && !filter.getFechaHasta().isEmpty()) {
			localHasta = LocalDate.parse(filter.getFechaHasta(), formatter);
		}
		
		
		List<Path> lista = buscarArchivosUsuario(dirRaiz, localDesde, localHasta, filter);
		
		for (Path path: lista) {
			File file = path.toFile();
			FacturaDocDTO facturaDocDTO = new FacturaDocDTO();
			facturaDocDTO.setNombreDoc(file.getName());
			String dateAsString = extractDate(file.getName());
			facturaDocDTO.setFechaDoc(dateAsString);
			facturaDocDTO.setEnlaceDoc(file.toString());
			
			listF.add(facturaDocDTO);
		}
		
//		String cadena = buildPath(filter);
//		log.debug("cadena = " + cadena);
		
//		for (final String path : cadena.split(CioneConstants.STRING_SEMICOLON)) {
//			final File file = new File(path);
//			if (file.isDirectory()) {
//				log.debug("file PARENT= " + path);
//				recurse(file, facturaDocDTO, listF, filter);
//			}
//		}

		Collections.sort(listF);
		Collections.reverse(listF);

		paginaFacturaDocDTO.setPagina(filter.getPagina());
		paginaFacturaDocDTO.setUltimaPagina(getUltimaPagina(listF));
		paginaFacturaDocDTO.setNumRegistros(listF.size());
		paginaFacturaDocDTO.setDocumentos(getPage(listF, filter.getPagina(), 10));

		return paginaFacturaDocDTO;
	}
	
    public LocalDate convertirStringALocalDate(String fechaStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(fechaStr, formatter);
    }

	private <T> int getUltimaPagina(List<T> listF) {

		int paginas = listF.size() / 10;

		if (listF.size() % 10 > 0) {
			paginas = paginas + 1;
		}
		return paginas;
	}

	private static <T> List<T> getPage(List<T> sourceList, int page, int pageSize) {
		if (pageSize <= 0 || page <= 0) {
			throw new IllegalArgumentException("invalid page size: " + pageSize);
		}

		int fromIndex = (page - 1) * pageSize;
		if (sourceList == null || sourceList.size() < fromIndex) {
			return Collections.emptyList();
		}

		return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
	}
	


	private static void recurse(File f, FacturaDocDTO facturaDocDTO, List<FacturaDocDTO> listF, FacturaDocQueryParamsDTO facturaDocQueryParamsDTO) {

		File list[] = f.listFiles();
		
//		Date fechaDesde = CioneUtils.parseStringToDate(facturaDocQueryParamsDTO.getFechaDesde(), "dd-MM-yyyy");
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(fechaDesde);
//		String aaaa = String.valueOf(calendar.get(Calendar.YEAR));
//		String mm = String.valueOf(calendar.get(Calendar.MONTH) + 1);
//		if (mm.length() <2)
//			mm = "0"+mm;
		

		for (File file : list) {
			if (file.isDirectory()) {
				recurse(file, facturaDocDTO, listF, facturaDocQueryParamsDTO);
			} else {
				//log.debug("file = " + file.getAbsolutePath());
				if (filterByCodSocio(file, facturaDocQueryParamsDTO) || filterByNifSocio(file, facturaDocQueryParamsDTO)) {
					facturaDocDTO = new FacturaDocDTO();
					facturaDocDTO.setNombreDoc(file.getName());
					String dateAsString = extractDate(file.getName());
					facturaDocDTO.setFechaDoc(dateAsString);
					facturaDocDTO.setEnlaceDoc(file.toString());
					if(validFilterByDate(dateAsString, facturaDocQueryParamsDTO)) {
						log.debug("anandido = " + file.getName());
						listF.add(facturaDocDTO);
					}
					
				}
			}
		}
	}

	private static Date getLastDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int lastDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		date.setDate(lastDayOfMonth);
		return date;
	}
	
	private static boolean validFilterByDate(String dateAsString, FacturaDocQueryParamsDTO facturaDocQueryParamsDTO) {
		boolean result = true;
		Date date = CioneUtils.parseStringToDate(dateAsString, "yyyy-MM-dd");		
		if(!CioneUtils.isEmptyOrNull(facturaDocQueryParamsDTO.getFechaDesde())) {
			Date fechaDesde = CioneUtils.parseStringToDate(facturaDocQueryParamsDTO.getFechaDesde(), "dd-MM-yyyy");
			if(facturaDocQueryParamsDTO.getFechaDesde().length()==7) {
				fechaDesde = CioneUtils.parseStringToDate(facturaDocQueryParamsDTO.getFechaDesde(), "MM-yyyy");	
				fechaDesde = getLastDayOfMonth(fechaDesde);
			}			
			if(date.before(fechaDesde)) {
				result = false;
			}
		}
		if(!CioneUtils.isEmptyOrNull(facturaDocQueryParamsDTO.getFechaHasta())) {
			Date fechaHasta = CioneUtils.parseStringToDate(facturaDocQueryParamsDTO.getFechaHasta(), "dd-MM-yyyy");
			if(facturaDocQueryParamsDTO.getFechaHasta().length()==7) {
				fechaHasta = CioneUtils.parseStringToDate(facturaDocQueryParamsDTO.getFechaHasta(), "MM-yyyy");
				fechaHasta = getLastDayOfMonth(fechaHasta);
			}			
			if(date.after(fechaHasta)) {
				result = false;
			}
		}
		return result;
	}
	
	private static boolean filterByCodSocio(File fileOrig, FacturaDocQueryParamsDTO facturaDocQueryParamsDTO) {		
		String code = extractCodSocio(fileOrig);
		//code = "000560100";
		if (code != null && !code.isEmpty() && code.equals(facturaDocQueryParamsDTO.getCodSocio())) {
			return true;
		}
		return false;
	}
	
	private static boolean filterByCodSocioByName(String fileName, FacturaDocQueryParamsDTO facturaDocQueryParamsDTO) {
		String code = extractCodSocioStr(fileName);
		//code = "000560100";
		if (code != null && !code.isEmpty() && code.equals(facturaDocQueryParamsDTO.getCodSocio())) {
			return true;
		}
		return false;
	}
	
	private static boolean filterByNifSocioByName(String fileName, FacturaDocQueryParamsDTO facturaDocQueryParamsDTO) {
		String code = extractNifSocioStr(fileName);
		//code = "000560100";
		if (code != null && !code.isEmpty() && code.equals(facturaDocQueryParamsDTO.getNifSocio())) {
			return true;
		}
		return false;
	}
	
	private static boolean filterByNifSocio(File fileOrig, FacturaDocQueryParamsDTO facturaDocQueryParamsDTO) {		
		String code = extractNifSocio(fileOrig);
		//code = "000560100";
		if (code != null && !code.isEmpty() && code.equals(facturaDocQueryParamsDTO.getNifSocio())) {
			return true;
		}
		return false;
	}

	private static String extractCodSocio(File file) {
		String regex = CioneConstants.PATTERN_CODSOCIO;
		String textToReplace = "[a-zA-Z|_]+";
		
		final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
		final Matcher matcher = pattern.matcher(file.getName());

		String rawCode = CioneConstants.STRING_EMPTY;
		String strNew = CioneConstants.STRING_EMPTY;
		if (matcher.find()) {
			rawCode = matcher.group(0);
			strNew = rawCode.replaceAll(textToReplace, "");
		}

		return strNew;
	}
	
	private static String extractCodSocioStr(String fileName) {
		String regex = CioneConstants.PATTERN_CODSOCIO;
		String textToReplace = "[a-zA-Z|_]+";
		
		final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
		final Matcher matcher = pattern.matcher(fileName);

		String rawCode = CioneConstants.STRING_EMPTY;
		String strNew = CioneConstants.STRING_EMPTY;
		if (matcher.find()) {
			rawCode = matcher.group(0);
			strNew = rawCode.replaceAll(textToReplace, "");
		}

		return strNew;
	}

	private static String extractNifSocio(File file) {
		
		String regex = CioneConstants.PATTERN_NIFSOCIO;
		
		final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
		final Matcher matcher = pattern.matcher(file.getName());

		String rawCode = CioneConstants.STRING_EMPTY;
		if (matcher.find()) {
			rawCode = matcher.group(0);
		}

		return rawCode;
	}
	
	private static String extractNifSocioStr(String fileName) {
		
		String regex = CioneConstants.PATTERN_NIFSOCIO;
		
		final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
		final Matcher matcher = pattern.matcher(fileName);

		String rawCode = CioneConstants.STRING_EMPTY;
		if (matcher.find()) {
			rawCode = matcher.group(0);
		}

		return rawCode;
	}

	private static String extractDate(String fname) {

		// Pattern: yyyymmdd
		final String regex1 = CioneConstants.PATTERN_YYYYMMDD; //patron para informe de consumos
		final Pattern pattern = Pattern.compile(regex1);
		final Matcher matcher = pattern.matcher(fname);

		// Pattern: yyyy_mm
		String re1 = CioneConstants.PATTERN_YYYYMM_RE1;  
		String re2 = CioneConstants.PATTERN_YYYYMM_RE2; 
		String re3 = CioneConstants.PATTERN_YYYYMM_RE3;

		final Pattern pattern2 = Pattern.compile(re1 + re2 + re3, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		final Matcher matcher2 = pattern2.matcher(fname);
		
		final Pattern pattern3 = Pattern.compile(re1 + "-" + re3, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		final Matcher matcher3 = pattern3.matcher(fname);
		
		String rawdate = CioneConstants.STRING_EMPTY;
		String year = CioneConstants.STRING_EMPTY;
		String month = CioneConstants.STRING_EMPTY;
		String day = CioneConstants.STRING_EMPTY;
		String date = CioneConstants.STRING_EMPTY;
		
		if (matcher.find()) {
			rawdate = matcher.group(1);
			if (rawdate.length() == 8) {
				year = rawdate.substring(0, 4);
				month = rawdate.substring(4, 6);
				day = rawdate.substring(6);
				date = year.concat(CioneConstants.STRING_HYPHEN).concat(month).concat(CioneConstants.STRING_HYPHEN)
						.concat(day);
			}
		}

		if (matcher2.find()) {
			rawdate = matcher2.group(0);
			String rawday = matcher2.group(3);

			if (Integer.parseInt(rawday) <= 12) {
				String[] fec = rawdate.split(CioneConstants.STRING_UNDERSCORE);
				StringBuffer stb = new StringBuffer(fec[0]).append(CioneConstants.STRING_HYPHEN).append(fec[1])
						.append(CioneConstants.STRING_HYPHEN);
				int mes = Integer.parseInt(fec[1]);
				if (isLeapYear(Integer.parseInt(fec[0])) && mes == 2) {
					stb.append(CioneConstants.LAST_DAY_FEB_LEAP_YEAR);
				} else {
					if (mes == 2) {
						stb.append(CioneConstants.lAST_DAY_FEB_NOT_LEAP_YEAR);
					} else if ((mes == 4 || mes == 6 || mes == 9 || mes == 11)) {
						stb.append(CioneConstants.LAST_DAY_MONTH);
					} else {
						stb.append(CioneConstants.MAX_LAST_DAY_MONTH);
					}
				}

				date = stb.toString();
			}
		}
		
		if (matcher3.find()) {
			rawdate = matcher3.group(0);
			String rawday = matcher3.group(2);

			if (Integer.parseInt(rawday) <= 12) {
				String[] fec = rawdate.split("-");
				StringBuffer stb = new StringBuffer(fec[0]).append(CioneConstants.STRING_HYPHEN).append(fec[1])
						.append(CioneConstants.STRING_HYPHEN);
				int mes = Integer.parseInt(fec[1]);
				if (isLeapYear(Integer.parseInt(fec[0])) && mes == 2) {
					stb.append(CioneConstants.LAST_DAY_FEB_LEAP_YEAR);
				} else {
					if (mes == 2) {
						stb.append(CioneConstants.lAST_DAY_FEB_NOT_LEAP_YEAR);
					} else if ((mes == 4 || mes == 6 || mes == 9 || mes == 11)) {
						stb.append(CioneConstants.LAST_DAY_MONTH);
					} else {
						stb.append(CioneConstants.MAX_LAST_DAY_MONTH);
					}
				}

				date = stb.toString();
			}
		}
		
		if(date.isEmpty()) {
			
			rawdate = fname.replaceAll(".pdf","").substring(fname.replaceAll(".pdf","").length()-4);
			
			if (rawdate.length() == 4 && rawdate.matches("^[0-9]{4}$")) {
				year = rawdate;
				month = "01";
				day = "01";
				date = year.concat(CioneConstants.STRING_HYPHEN).concat(month).concat(CioneConstants.STRING_HYPHEN)
						.concat(day);
			}
		}

		return date;
	}

	private static boolean isLeapYear(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);

		return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
	}

	private String buildPath(FacturaDocQueryParamsDTO filter) {
		
		String spath = CioneConstants.STRING_EMPTY;

		String type = filter.getTipoDocumento();//consumos
		
//		Date fechaDesde = CioneUtils.parseStringToDate(filter.getFechaDesde(), "dd-MM-yyyy");
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(fechaDesde);
		
		
		String[] componentes = filter.getFechaDesde().split("-");
		
		String year = componentes[2];
		String mont = componentes[1];
		
		String pathConfig = configService.getConfig().getInvoiceDocsPath(); ///opt/data/otrosDocumentos
		
		spath = pathConfig + CioneConstants.PATH_SEPARATOR + type + CioneConstants.PATH_SEPARATOR + year + CioneConstants.PATH_SEPARATOR + mont;
		
		/*
		StringBuffer aStringBuffer = new StringBuffer(pathConfig);
		List<String> tipos = Arrays.asList(filter.getTiposDisponibles().split(CioneConstants.STRING_COMMA));
		

		if (!type.isEmpty()) {
			aStringBuffer.append(CioneConstants.PATH_SEPARATOR);
			aStringBuffer.append(type);
			aStringBuffer.append(CioneConstants.PATH_SEPARATOR);
			aStringBuffer.append(year);
			spath = aStringBuffer.toString();
		} else {
			spath = pathRecursive(pathConfig, tipos.size(), year, tipos);
		}*/

		return spath;
	}

	private String pathRecursive(String path, int options, String year, List<String> tipos) {

		if (options > 0) {
			StringBuffer aStringBuffer = new StringBuffer(path);
			aStringBuffer.append(CioneConstants.PATH_SEPARATOR);
			aStringBuffer.append(tipos.get(options - 1));
			aStringBuffer.append(CioneConstants.PATH_SEPARATOR);
			//aStringBuffer.append(year);
			aStringBuffer.append(CioneConstants.STRING_SEMICOLON).toString();
			return aStringBuffer + pathRecursive(path.toString(), options - 1, year, tipos);
		}

		return CioneConstants.STRING_EMPTY;
	}

	@Override
	public File getFileInPDFFormat(String fileName) {

		String pathConfig = configService.getConfig().getInvoiceDocsPath();

		List<File> fics = listf(pathConfig, new ArrayList<>());

		File file = fics.stream().filter(filePdf -> fileName.equals(filePdf.getName())).findAny().orElse(null);

		return file;
	}

	private static List<File> listf(String directoryName, List<File> files) {
		File directory = new File(directoryName);

		// Get all files from a directory.
		File[] fList = directory.listFiles();
		if (fList != null) {
			for (File file : fList) {
				if (file.isFile()) {
					files.add(file);
				} else if (file.isDirectory()) {
					listf(file.getAbsolutePath(), files);
				}
			}
		}
		return files;
	}
	
    public static List<Path> buscarArchivosUsuario(String directorioRaiz, LocalDate fechaDesde, LocalDate fechaHasta, FacturaDocQueryParamsDTO facturaDocQueryParamsDTO) {
        List<Path> archivosEncontrados = new ArrayList<>();
        Path pathInicio = Paths.get(directorioRaiz);

        try {
            Files.walkFileTree(pathInicio, EnumSet.noneOf(FileVisitOption.class), 3, new FileVisitor<Path>() {
            	
            	@Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    return FileVisitResult.CONTINUE;
                }

            	@Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                	
                    if (filterByCodSocioByName(file.getFileName().toString(), facturaDocQueryParamsDTO) || filterByNifSocioByName(file.getFileName().toString(), facturaDocQueryParamsDTO)) { //file.getFileName().toString().endsWith("usuarioA.pdf")
                    	//archivosEncontrados.add(file);
                    	
                    	String[] partesRuta = file.getParent().toString().split("\\\\"); //local
                    	if (partesRuta.length < 2) {
                    		partesRuta = file.getParent().toString().split("/"); //entorno
                    	}
                    	if (partesRuta.length >= 2) {
                    		if (facturaDocQueryParamsDTO.getTipoDocumento().equals("modelo347")) {
                    			String anioStr = partesRuta[partesRuta.length - 1];
                                String fecha = "01-01-" + anioStr;

                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                LocalDate fechaArchivo = LocalDate.parse(fecha, formatter);

                                if ((fechaArchivo.isAfter(fechaDesde) || fechaArchivo.isEqual(fechaDesde))
                                        && (fechaArchivo.isBefore(fechaHasta) || fechaArchivo.isEqual(fechaHasta))) {
                                    archivosEncontrados.add(file);
                                }
                        	} else {
	                            String anioStr = partesRuta[partesRuta.length - 2];
	                            String mesStr = partesRuta[partesRuta.length - 1];
	                            String fecha = "01-" + mesStr + "-" + anioStr;
	
	                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	                            LocalDate fechaArchivo = LocalDate.parse(fecha, formatter);
	
	                            if ((fechaArchivo.isAfter(fechaDesde) || fechaArchivo.isEqual(fechaDesde))
	                                    && (fechaArchivo.isBefore(fechaHasta) || fechaArchivo.isEqual(fechaHasta))) {
	                                archivosEncontrados.add(file);
	                            }
                        	}
                        } 
                    }
                    return FileVisitResult.CONTINUE;
                }
            	@Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            	@Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return archivosEncontrados;
    }
	
	@Override
	public PaginaFacturasDTO getFacturas(FacturaQueryParamsDTO params) {
		return elasticService.getFacturas(CioneUtils.getIdCurrentClientERP(), params);
	}
	
	@Override
	public List<LineaFacturaDTO> getLineasFactura(String numFactura){
		return middlewareService.getLineasFactura(CioneUtils.getIdCurrentClientERP(), numFactura);
	}
	
	@Override
	public List<LineaCondicionDTO> getLineasCondiciones(String linea){
		return middlewareService.getCondicionesLinea(CioneUtils.getIdCurrentClientERP(), linea);
	}
}
