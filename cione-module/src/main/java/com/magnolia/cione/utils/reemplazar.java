package com.magnolia.cione.utils;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class reemplazar {
	public static void main (String [ ] args) {
		File dir = new File("C:\\facturas\\06");
		File[] fList = dir.listFiles();
		
		File dirbuenas = new File("C:\\facturas\\facturasJunio");
		File[] fListBuenas = dirbuenas.listFiles();

		for (File directorio : fList) {
			if (directorio.isDirectory()) {
				for (File file : directorio.listFiles()) {
				    if (file.isFile()) {
				    	if (file.getName().indexOf(".pdf") >0) {
				    		String ruta = file.getParent();
//				    		boolean ficheroborrado = file.delete();
//				    		if (ficheroborrado) {
				    			try {
									File ficheroACopiar = getFileBueno(file, fListBuenas);
									File destDir = new File(ruta);
									
									if (ficheroACopiar.getName().equals(destDir.getName())) {
										System.out.println("FICHERO ENCONTRADO " + ficheroACopiar.getName());
									} else {
										System.out.println("NO SON IGUALES");
									}
									
									FileUtils.copyFileToDirectory(ficheroACopiar, destDir);
									
									
								} catch (Exception e) {
									System.out.println("ERROR EN ficheroACopiar");
								}
				    			
//				    		} else {
//				    			System.out.println("El fichero " + file.getName() + " no se ha podido borrar");
//				    		}
				    		
				    	}
				    }
				}
			}
		}
	}
	
	public static File getFileBueno(File file, File[] fListBuenas) {
		boolean flag = false;
		File fileReturn = null;
		for (File fileDir : fListBuenas) {
			if (fileDir.getName().equals(file.getName())) {
				System.out.println("Encontrado fichero");
				fileReturn = fileDir;
				flag = true;
				break;
			}
		}
		if (!flag) {
			System.out.println("El fichero " + file.getName() + " no se ha encontrado");
		}
		return fileReturn;
	}

}
