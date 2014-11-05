/**
 * 
 */
package com.conflux.ratings;

import com.conflux.ratings.dto.RiskFactorDTO;

/**
 * @author Ashok
 *
 */
public class CustomerOccupationStabilityRiskFactor implements IRiskFactorType {

	private double highStability;
	private double mediumStability;
	private double lowStability;
		
	public CustomerOccupationStabilityRiskFactor(double highStability,
			double mediumStability, double lowStability) {
		super();
		this.highStability = highStability;
		this.mediumStability = mediumStability;
		this.lowStability = lowStability;
	}

	/**
	 * @return the highStability
	 */
	public double getHighStability() {
		return highStability;
	}


	/**
	 * @param highStability the highStability to set
	 */
	public void setHighStability(double highStability) {
		this.highStability = highStability;
	}


	/**
	 * @return the mediumStability
	 */
	public double getMediumStability() {
		return mediumStability;
	}


	/**
	 * @param mediumStability the mediumStability to set
	 */
	public void setMediumStability(double mediumStability) {
		this.mediumStability = mediumStability;
	}


	/**
	 * @return the lowStability
	 */
	public double getLowStability() {
		return lowStability;
	}


	/**
	 * @param lowStability the lowStability to set
	 */
	public void setLowStability(double lowStability) {
		this.lowStability = lowStability;
	}


	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#calculateRiskFactor()
	 */
	@Override
	public int calculateRiskFactor() {
		//Customer income and family income details are not available
		if(highStability == -1 && mediumStability == -1 && lowStability == -1)
			return 3;
		
		if(lowStability == OCCUPATION_STABILITY.ONE.maxPer){
			return OCCUPATION_STABILITY.ONE.riskFactor;
		}
		if(lowStability > OCCUPATION_STABILITY.TWO.maxPer){
			return OCCUPATION_STABILITY.TWO.riskFactor;
		}
		if(highStability > OCCUPATION_STABILITY.FIVE.maxPer){
			return OCCUPATION_STABILITY.FIVE.riskFactor;
		}
		if(mediumStability > OCCUPATION_STABILITY.FOUR.maxPer 
				&& lowStability < OCCUPATION_STABILITY.FOURA.maxPer){
			return OCCUPATION_STABILITY.FOUR.riskFactor;
		}
		if(highStability > OCCUPATION_STABILITY.THREE.maxPer){
			return OCCUPATION_STABILITY.THREE.riskFactor;
		}
		
		return 3;//Default risk factor
	}

	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#getRiskFactorDTO()
	 */
	@Override
	public RiskFactorDTO getRiskFactorDTO() {
		RiskFactorDTO riskFactorDTO = new RiskFactorDTO();
		riskFactorDTO.setRiskFactor(calculateRiskFactor());
		riskFactorDTO.setRiskFactorType(RISK_FACTOR_TYPE.CUSTOMER_OCCUPATION_STABILITY.name());
		return riskFactorDTO;
	}
	
	public enum OCCUPATION_STABILITY {

		ONE(100.0, 1), TWO(75.0, 2), THREE(25.0, 3), FOUR(50.0, 4), FOURA(25.0, 4), FIVE(50.0, 5);

		private double maxPer;
		private int riskFactor;

		private OCCUPATION_STABILITY(double max, int rf) {
			this.maxPer = max;
			this.riskFactor = rf;
		}
	}

	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#getRatingFactorType()
	 */
	@Override
	public String getRatingFactorType() {
		return RISK_FACTOR_TYPE.CUSTOMER_OCCUPATION_STABILITY.getDescription();
	}
	
}
