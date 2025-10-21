package com.magnolia.cione.service;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ContactLensServiceImpl.class)
public interface ContactLensService {
	
	/**
	 * 
	 * Metodo para obtener las esferas por codigo central de lente
	 * y por el diseno escogido por el usuario en el front
	 * 
	 * @param codigocentral codigo central de la lente de contacto [not null]
	 * @param diseno diseno seleccionado por el usuario en el front
	 * 
	 * @return List<String> lista de esferas
	 * 
	 */
	public List<String> getSpheresByCentralCodeAndDesign(String codigocentral, String diseno);
	
	/**
	 * 
	 * Metodo para obtener los cilindros a partir del codigocentral, diseno y esfera.
	 * 
	 * @param codigocentral codigo central de la lente de contacto [not null]
	 * @param diseno diseno seleccionado por el usuario en el front
	 * @param esfera esfera seleccionada por el usuario en el front
	 * @return List<String> lista de cilindros
	 * 
	 */
	public List<String> getCylinders(String codigocentral, String diseno, String esfera);
	
	/**
	 * 
	 * Metodo para obtener los ejes a partir del codigocentral, diseno, esfera y cilindro
	 * 
	 * @param codigocentral codigo central de la lente de contacto
	 * @param diseno diseno seleccionado por el usuario en el front
	 * @param esfera esfera seleccionada por el usuario en el front
	 * @param cylinder cilindo seleccionada por el usuario en el front
	 * @return  List<String> lista de ejes de la lente
	 * 
	 */
	public List<String> getAxis(String codigocentral, String diseno, String esfera, String cylinder);
	
	/**
	 * 
	 * Metodo para obtener los diametros a partir del codigocentral, diseno, esfera, cilindro y eje
	 * 
	 * @param codigocentral codigo central de la lente de contacto [not null]
	 * @param diseno diseno seleccionado por el usuario en el front
	 * @param esfera esfera seleccionada por el usuario en el front
	 * @param cylinder cilindo seleccionada por el usuario en el front
	 * @param eje eje seleccionado por el usuario en el front
	 * 
	 * @return List<String> lista de diametros de la lente
	 * 
	 */
	public List<String> getDiameters(String codigocentral, String diseno, String esfera, String cylinder, String eje);
	
	/**
	 * 
	 * Metodo para obtener los radios a partir del codigocentral, diseno, esfera, cilindro, eje y diametro
	 * 
	 * @param codigocentral codigo central de la lente de contacto [not null]
	 * @param diseno diseno seleccionado por el usuario en el front
	 * @param esfera esfera seleccionada por el usuario en el front
	 * @param cylinder cilindo seleccionada por el usuario en el front
	 * @param eje eje seleccionado por el usuario en el front
	 * @param diametro diametro seleccionado por el usuario en el front
	 * 
	 * @return List<String> lista de radios de la lente
	 */
	public List<String> getRadios(String codigocentral, String diseno, String esfera, String cylinder, String eje, String diametro);
	
	/**
	 * 
	 * Metodo para obtener las adiciones a partir del codigocentral, diseno, esfera, cilindro, eje, diametro y radio
	 * 
	 * @param codigocentral codigo central de la lente de contacto [not null]
	 * @param diseno diseno seleccionado por el usuario en el front
	 * @param esfera esfera seleccionada por el usuario en el front
	 * @param cylinder cilindo seleccionada por el usuario en el front
	 * @param eje eje seleccionado por el usuario en el front
	 * @param diametro diametro seleccionado por el usuario en el front
	 * @param radio radio seleccionado por el usuario en el front
	 * 
	 * @return List<String> lista de adiciones de la lente
	 */
	public List<String> getAdditions(String codigocentral, String diseno, String esfera, String cylinder, String eje, String diametro, String radio);
	
	/**
	 * 
	 *  Metodo para obtener los colores de una lente a partir del codigocentral, diseno, esfera, cilindro, eje, diametro, radio y adicion
	 * 
	 * @param codigocentral codigo central de la lente de contacto [not null]
	 * @param diseno diseno seleccionado por el usuario en el front
	 * @param esfera esfera seleccionada por el usuario en el front
	 * @param cylinder cilindo seleccionada por el usuario en el front
	 * @param eje eje seleccionado por el usuario en el front
	 * @param diametro diametro seleccionado por el usuario en el front
	 * @param radio radio seleccionado por el usuario en el front
	 * @param adicion adicion selecionada por el usuario en el fron
	 * 
	 * @return List<String> lista de colores de la lente
	 * 
	 */
	public List<String> getColors(String codigocentral, String diseno, String esfera, String cylinder, String eje, String diametro, String radio, String adicion);
	
	public String getSku(String codigocentral, String diseno, String esfera, String cylinder, String eje, String diametro, String radio, String adicion, String color);

}
