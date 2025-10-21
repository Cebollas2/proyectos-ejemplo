package com.magnolia.cione.dto;

import java.util.List;

public class AlbaranDTO {
	public CabeceraAlbDTO albaran;
	public List<LineaAlbaranDTO> lineas;
	
	public CabeceraAlbDTO getAlbaran() {
		return albaran;
	}
	public void setAlbaran(CabeceraAlbDTO albaran) {
		this.albaran = albaran;
	}
	public List<LineaAlbaranDTO> getLineas() {
		return lineas;
	}
	public void setLineas(List<LineaAlbaranDTO> lineas) {
		this.lineas = lineas;
	}
	
    @Override
    public String toString()
    {
        return "AlbaranDTO [lineas = "+lineas+", albaran = "+albaran+"]";
    }
}
