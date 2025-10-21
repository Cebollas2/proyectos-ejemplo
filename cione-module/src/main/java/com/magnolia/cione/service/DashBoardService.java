package com.magnolia.cione.service;

import java.util.Locale;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.DashBoardDTO;

@ImplementedBy(DashBoardServiceImpl.class)
public interface DashBoardService {
	
	public DashBoardDTO getDashBoard(Locale locale);

}
