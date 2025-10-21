package com.magnolia.cione.service;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.PepperAccessTokenDTO;
import com.magnolia.cione.dto.PepperUrlDTO;

@ImplementedBy(PepperServiceImpl.class)
public interface PepperService {

	public PepperAccessTokenDTO getAccessToken(String usuarioPepper); 
	
	public PepperUrlDTO getUrl(String accessToken);
	
}
