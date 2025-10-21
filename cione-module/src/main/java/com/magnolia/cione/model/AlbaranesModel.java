package com.magnolia.cione.model;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.jcr.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.AudioProveedoresDTO;
import com.magnolia.cione.dto.AudioSubfamiliasDTO;
import com.magnolia.cione.dto.TipoAlbaranDTO;
import com.magnolia.cione.service.AlbaranService;

import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;

public class AlbaranesModel <RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	private static final Logger log = LoggerFactory.getLogger(AlbaranesModel.class);
	private final TemplatingFunctions templatingFunctions;
	private AudioSubfamiliasDTO audiosubfamilias;
	private AudioProveedoresDTO audioproveedores;
	private TipoAlbaranDTO listipoalbaran;
	private ArrayList<String> listipoventa;

	@Inject
	private AlbaranService albaranService;
	
	public AlbaranesModel(Node content, RD definition, RenderingModel<?> parent, TemplatingFunctions templatingFunctions) {
		super(content, definition, parent);
		this.templatingFunctions = templatingFunctions;
	}
	
	@Override
	public String execute() {
		audiosubfamilias = albaranService.getAudioSubfamilias();
		audioproveedores = albaranService.getAudioProveedores();
		listipoalbaran = albaranService.getTipoAlbaran();
		listipoventa = albaranService.getTipoVenta();
		return super.execute();
	}

	public AudioSubfamiliasDTO getAudiosubfamilias() {
		return audiosubfamilias;
	}

	public void setAudiosubfamilias(AudioSubfamiliasDTO audiosubfamilias) {
		this.audiosubfamilias = audiosubfamilias;
	}

	public AudioProveedoresDTO getAudioproveedores() {
		return audioproveedores;
	}

	public void setAudioproveedores(AudioProveedoresDTO audioproveedores) {
		this.audioproveedores = audioproveedores;
	}

	public TipoAlbaranDTO getListipoalbaran() {
		return listipoalbaran;
	}

	public void setListipoalbaran(TipoAlbaranDTO listipoalbaran) {
		this.listipoalbaran = listipoalbaran;
	}
	
	public ArrayList<String> getListipoventa() {
		return listipoventa;
	}
	
	
	

}
