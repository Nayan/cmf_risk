/**
 * 
 */
package com.conflux.ratings;

import com.conflux.ratings.dto.RiskFactorDTO;

/**
 * @author Ashok
 *
 */
public class CustomerFamilyMemberWorkingRationRiskFactor implements
		IRiskFactorType {

	private double workingRatio;
		
	/**
	 * @param workingRatio
	 */
	public CustomerFamilyMemberWorkingRationRiskFactor(double workingRatio) {
		super();
		this.workingRatio = workingRatio;
	}

	/**
	 * @return the workingRatio
	 */
	public double getWorkingRatio() {
		return workingRatio;
	}



	/**
	 * @param workingRatio the workingRatio to set
	 */
	public void setWorkingRatio(double workingRatio) {
		this.workingRatio = workingRatio;
	}



	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#calculateRiskFactor()
	 */
	@Override
	public int calculateRiskFactor() {
		for (WORKING_RATIO bracket : WORKING_RATIO.values()) {
			if (workingRatio >= bracket.maxPer) {
				return bracket.riskFactor;
			}
		}
		return 3;
	}

	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#getRiskFactorDTO()
	 */
	@Override
	public RiskFactorDTO getRiskFactorDTO() {
		RiskFactorDTO riskFactorDTO = new RiskFactorDTO();
		riskFactorDTO.setRiskFactor(calculateRiskFactor());
		riskFactorDTO.setRiskFactorType(RISK_FACTOR_TYPE.CUSTOMER_FAMILY_WORKING_RATIO.name());
		return riskFactorDTO;
	}
	
	public enum WORKING_RATIO{
		FIVE(100, 5), FOUR(75, 4), THREE(50, 3), TWO(33, 2), ONE(0, 1);
		
		private double maxPer;
		private int riskFactor;
		
		private WORKING_RATIO(double max, int rf) {
			this.maxPer = max;
			this.riskFactor = rf;
		}
	}

	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#getRatingFactorType()
	 */
	@Override
	public String getRatingFactorType() {
		return RISK_FACTOR_TYPE.CUSTOMER_FAMILY_WORKING_RATIO.getDescription();
	}
	
	
}
