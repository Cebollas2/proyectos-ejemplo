package com.magnolia.cione.service;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.LineaTallerDTO;
import com.magnolia.cione.dto.PaginaTalleresDTO;
import com.magnolia.cione.dto.TallerQueryParamsDTO;

@ImplementedBy(TallerServiceImpl.class)
public interface TallerService {

	public PaginaTalleresDTO getTalleres(TallerQueryParamsDTO params);

	public List<LineaTallerDTO> getLineasTaller(String idServicio, String numPedido, String historico);

}
