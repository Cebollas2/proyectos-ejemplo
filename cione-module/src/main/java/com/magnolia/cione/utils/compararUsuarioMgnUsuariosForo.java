package com.magnolia.cione.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class compararUsuarioMgnUsuariosForo {
	public static void main (String [ ] args) {
		try {
			List<String> listaMagnolia = new ArrayList<>();
			List<String> listaForo = new ArrayList<>();
			FileReader fileMagnolia = new FileReader("C:\\Users\\msanchezp\\Documents\\dumps\\usuariosMagnolia.log");
			FileReader fileforo = new FileReader("C:\\Users\\msanchezp\\Documents\\dumps\\usuarios-Foro2.xml");
			
			List<String> listaMagnoliaFinal = new ArrayList<>();
			
			BufferedReader brMagnolia = new BufferedReader(fileMagnolia);
			BufferedReader brForo = new BufferedReader(fileforo);
			
			String lineForo;
			while ((lineForo =brForo.readLine()) != null) {
				listaForo.add(lineForo);
			}
			
			String lineMagnolia;
			while ((lineMagnolia =brMagnolia.readLine()) != null) {
				if (lineMagnolia.length() > 9) {
					String usernameMagnolia = lineMagnolia.substring(0, 9);
					for (String lineaForo : listaForo) {
						if (lineaForo.contains(usernameMagnolia)) {
							listaMagnoliaFinal.add(lineaForo + "\n");
							break;
						}
					}
				}
			}
			
			FileWriter fw = new FileWriter("C:\\Users\\msanchezp\\Documents\\dumps\\usuariosMagnoliaLimpio.log");
			BufferedWriter writer = new BufferedWriter(fw);
			
			//writer.write(listaMagnoliaFinal.toString());
			System.out.println("tamaño " + listaMagnoliaFinal.size());
			System.out.println("tamaño " + listaMagnoliaFinal.toString());
			for (String lineaMagnoliaF: listaMagnoliaFinal) {
				writer.write(lineaMagnoliaF);
				writer.newLine();
				
				
			}
			fw.flush();
			fw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		

	}
	


}
