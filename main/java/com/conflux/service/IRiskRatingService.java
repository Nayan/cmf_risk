package com.conflux.service;

import java.util.List;
import java.util.Map;

import com.conflux.common.ApplicationConstants;
import com.conflux.ratings.dto.RiskFactorDTO;
import com.conflux.service.dto.CustomerDTO;

public interface IRiskRatingService {
	public List<RiskFactorDTO> getCustomerRiskFactors(CustomerDTO customer);
	
	public List<RiskFactorDTO> getCenterRiskFactors(CustomerDTO center, Map<String, ApplicationConstants.USER_ROLE> users);
	
	public int calculateRiskRating(List<RiskFactorDTO> riskFactors);
	
	public int calculateVillateRiskRating(List<CustomerDTO> centers);
}
