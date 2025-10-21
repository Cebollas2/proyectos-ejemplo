package com.magnolia.cione.service;

import java.io.File;

import com.google.inject.ImplementedBy;

@ImplementedBy(PDFGeneratorServiceImpl.class)
public interface PDFGeneratorService {
	public void createHtml(String tracking, String agencia, String tipoEnvio);
	
	public File getFileSending(String tracking, String agencia, String tipoEnvio);

}
