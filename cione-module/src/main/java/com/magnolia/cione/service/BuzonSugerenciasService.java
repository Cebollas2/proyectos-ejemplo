package com.magnolia.cione.service;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.FileUploadForm;

@ImplementedBy(BuzonSugerenciasServiceImpl.class)
public interface BuzonSugerenciasService {

	public void procesarFormulario(FileUploadForm form);
	
	

}
