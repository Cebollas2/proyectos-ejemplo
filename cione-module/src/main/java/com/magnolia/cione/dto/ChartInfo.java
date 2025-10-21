package com.magnolia.cione.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChartInfo {
	private String name;
	private String id;
	private ArrayList<String> labels;
	private ArrayList<Dataset> datasets; 
    
	private String descuentoActual;
	private String sigDescuento;
	private String proxRappel;
    
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList<String> getLabels() {
		return labels;
	}
	public String getLabelsJson() {
		String labelsJson = "";
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			labelsJson = mapper.writeValueAsString(labels);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return labelsJson;
	}	
	public String getDataSetJson() {
		String labelsJson = "";
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			labelsJson = mapper.writeValueAsString(datasets);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return labelsJson;
	}	
	public void setLabels(ArrayList<String> labels) {
		this.labels = labels;
	}
	public ArrayList<Dataset> getDatasets() {
		return datasets;
	}
	public void setDatasets(ArrayList<Dataset> datasets) {
		this.datasets = datasets;
	}
	public String getDescuentoActual() {
		return descuentoActual;
	}
	public void setDescuentoActual(String descuentoActual) {
		this.descuentoActual = descuentoActual;
	}
	public String getSigDescuento() {
		return sigDescuento;
	}
	public void setSigDescuento(String sigDescuento) {
		this.sigDescuento = sigDescuento;
	}
	public String getProxRappel() {
		return proxRappel;
	}
	public void setProxRappel(String proxRappel) {
		this.proxRappel = proxRappel;
	}
	
}
