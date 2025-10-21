package com.magnolia.cione.service;

import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.query.InvalidQueryException;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.QualitySurveyDTO;
import com.magnolia.cione.dto.QualitySurveyQueryParamsDTO;

@ImplementedBy(QualitySurveyServiceImpl.class)
public interface QualitySurveyService {

	public boolean doQuestion() throws InvalidQueryException, RepositoryException;

	public void saveSurvey(QualitySurveyDTO dto);
	public void unpublishSurveyLocal();
	public void deleteSurveyLocal();
	
	public List<QualitySurveyDTO> search(QualitySurveyQueryParamsDTO params);

}
