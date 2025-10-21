package com.magnolia.cione.dto;

import java.util.List;
import java.util.stream.Collectors;

public class ConsumosCLDTO {
	
	private List<ConsumoCLDTO> consumosCL;

	public List<ConsumoCLDTO> getConsumosCL() {
		return consumosCL;
	}

	public void setConsumosCL(List<ConsumoCLDTO> consumosCL) {
		this.consumosCL = consumosCL;
	}
	
	public List<ConsumoCLDTO> getByGroupTwo(String group){
		
		return consumosCL.stream().filter(x -> group.equals(x.getLevel2_desc())).collect(Collectors.toList());
	}

}
