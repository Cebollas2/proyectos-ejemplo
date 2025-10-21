package com.magnolia.cione.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

public class detectarduplicados {
	public static void main (String [ ] args) {
		String rutaArchivo  = "C:\\CIONE\\usuarios\\usuariosActivos.log";
		HashSet<String> nombresUsuarios = new HashSet<>();
		System.out.println("EMPIEZA");
		try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Intenta insertar el nombre de usuario en el HashSet
            	StringTokenizer st = new StringTokenizer(linea, "#");
            	String name = "";
            	String mail = "";
            	if (st.hasMoreElements()) {
            		name = st.nextToken();
            	}
            	if (st.hasMoreElements()) {
            		mail = st.nextToken();
            	}
            	String terminacion = "00";
	            try {
					terminacion = name.substring(7, name.length());
					
				} catch (Exception e) {
					System.out.println("ERROR" + name + "linea" + linea);
				}
                if ((terminacion.equals("00")) && (!nombresUsuarios.add(mail))) {
                    System.out.println("Usuario " + name + " tiene un mail duplicado " + mail);
                }
            }
        } catch (IOException e) {
            System.err.println("Ocurrió un error al leer el archivo: " + e.getMessage());
        }
	}
	


}
