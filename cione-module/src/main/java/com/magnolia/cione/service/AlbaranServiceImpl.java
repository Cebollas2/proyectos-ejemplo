package com.magnolia.cione.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.magnolia.cione.dto.AlbaranAudioQueryParamsDTO;
import com.magnolia.cione.dto.AlbaranQueryParamsDTO;
import com.magnolia.cione.dto.AudioProveedoresDTO;
import com.magnolia.cione.dto.AudioSubfamiliasDTO;
import com.magnolia.cione.dto.LineaAlbaranDTO;
import com.magnolia.cione.dto.LineasAlbaranAudioDTO;
import com.magnolia.cione.dto.PaginaAlbaranesAudioDTO;
import com.magnolia.cione.dto.PaginaAlbaranesDTO;
import com.magnolia.cione.dto.TipoAlbaranDTO;
import com.magnolia.cione.utils.CioneUtils;

public class AlbaranServiceImpl implements AlbaranService {

	@Inject
	private MiddlewareService middlewareService;
	
	@Inject
	private ElasticService elasticService;

	@Override
	public PaginaAlbaranesDTO getAlbaranes(AlbaranQueryParamsDTO filters) {
		return elasticService.getAlbaranes(CioneUtils.getIdCurrentClientERP(), filters);
	}

	@Override
	public List<LineaAlbaranDTO> getLineasAlbaran(String numAlbaran) {
		return elasticService.getLineaAlbaran(CioneUtils.getIdCurrentClientERP(), numAlbaran);
		//return elasticService.getLineasAlbaran(CioneUtils.getIdCurrentClientERP(), numAlbaran);
	}
	
	@Override
	public File getDocAlbaran(String url) {
		return elasticService.getDocAlbaran(url);
	}
	
	@Override
	public PaginaAlbaranesAudioDTO getAlbaranesAudio(AlbaranAudioQueryParamsDTO albaranAudioQueryParamsDTO) {
		return middlewareService.getAlbaranesAudio(albaranAudioQueryParamsDTO);
	}
	
	@Override
	public LineasAlbaranAudioDTO getLineasAlbaranAudio(String numAlbaran) {
		return middlewareService.getLineasAlbaranAudio(numAlbaran);
	}
	
	@Override
	public AudioSubfamiliasDTO getAudioSubfamilias(){
		return middlewareService.getAudioSubfamilias();
	}

	@Override
	public AudioProveedoresDTO getAudioProveedores() {
		return middlewareService.getProveedores();
	}

	@Override
	public TipoAlbaranDTO getTipoAlbaran() {
		return middlewareService.getTipoAlbaran();
	}

	@Override
	public ArrayList<String> getTipoVenta(){
		ArrayList<String> listTipoVenta = new ArrayList<>();
		listTipoVenta.add("VENTA");
		listTipoVenta.add("DEPOSITO");
		listTipoVenta.add("ABONO");
		
		return listTipoVenta;
	}

}
