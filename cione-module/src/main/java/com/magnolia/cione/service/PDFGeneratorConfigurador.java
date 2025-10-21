package com.magnolia.cione.service;

import java.io.File;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.CartParamsDTO;
import com.magnolia.cione.dto.VariantDTO;

@ImplementedBy(PDFGeneratorConfiguradorImpl.class)
public interface PDFGeneratorConfigurador {
	public void createHtml(CartParamsDTO cartQueryParamsDTO, String idClient);
	
	public File getFileSending(CartParamsDTO cartQueryParamsDTO);

	public VariantDTO getInfoVariant(String sku);

}
