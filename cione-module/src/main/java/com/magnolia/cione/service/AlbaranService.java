package com.magnolia.cione.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.AlbaranAudioQueryParamsDTO;
import com.magnolia.cione.dto.AlbaranQueryParamsDTO;
import com.magnolia.cione.dto.AudioProveedoresDTO;
import com.magnolia.cione.dto.AudioSubfamiliasDTO;
import com.magnolia.cione.dto.LineaAlbaranDTO;
import com.magnolia.cione.dto.LineasAlbaranAudioDTO;
import com.magnolia.cione.dto.PaginaAlbaranesAudioDTO;
import com.magnolia.cione.dto.PaginaAlbaranesDTO;
import com.magnolia.cione.dto.TipoAlbaranDTO;

@ImplementedBy(AlbaranServiceImpl.class)
public interface AlbaranService {

	public PaginaAlbaranesDTO getAlbaranes(AlbaranQueryParamsDTO filters);
	public List<LineaAlbaranDTO> getLineasAlbaran(String numAlbaran);
	public File getDocAlbaran(String url);
	
	public PaginaAlbaranesAudioDTO getAlbaranesAudio(AlbaranAudioQueryParamsDTO albaranAudioQueryParamsDTO);
	public LineasAlbaranAudioDTO getLineasAlbaranAudio(String numAlbaran);
	
	public AudioSubfamiliasDTO getAudioSubfamilias();
	public AudioProveedoresDTO getAudioProveedores();
	public TipoAlbaranDTO getTipoAlbaran();
	public ArrayList<String> getTipoVenta();

}
