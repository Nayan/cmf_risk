/**
 * 
 */
package com.conflux.ratings;

import com.conflux.ratings.dto.RiskFactorDTO;

/**
 * @author Ashok
 * 
 */
public class LoanUtilizationRiskFactor implements IRiskFactorType {

	private double statedPurposePU;

	public LoanUtilizationRiskFactor(double statedPurposePU) {
		super();
		this.statedPurposePU = statedPurposePU;
	}

	/**
	 * @return the statedPurposePU
	 */
	public double getStatedPurposePU() {
		return statedPurposePU;
	}

	/**
	 * @param statedPurposePU
	 *            the statedPurposePU to set
	 */
	public void setStatedPurposePU(double statedPurposePU) {
		this.statedPurposePU = statedPurposePU;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.conflux.ratings.IRiskFactorType#calculateRiskFactor()
	 */
	@Override
	public int calculateRiskFactor() {
		// There are no active loans associated with customer
		// return default value
		if (statedPurposePU == -1)
			return 3;
		for (LOAN_UTILISATION bracket : LOAN_UTILISATION.values()) {
			if (statedPurposePU >= bracket.minPer
					&& statedPurposePU <= bracket.maxPer) {
				return bracket.riskFactor;
			}
		}
		return 3;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.conflux.ratings.IRiskFactorType#getRiskFactorDTO()
	 */
	@Override
	public RiskFactorDTO getRiskFactorDTO() {
		RiskFactorDTO riskFactorDTO = new RiskFactorDTO();
		riskFactorDTO.setRiskFactor(calculateRiskFactor());
		riskFactorDTO
				.setRiskFactorType(RISK_FACTOR_TYPE.ACTUAL_LOAN_UTILIZATION
						.name());
		return riskFactorDTO;
	}

	public enum LOAN_UTILISATION {

		FIVE(90.0, 100.0, 5), FOUR(75.0, 90.0, 4), THREE(60.0, 75.0, 3), TWO(50.0, 60.0, 2), ONE(0.0, 50.00, 1);

		private double minPer;
		private double maxPer;
		private int riskFactor;

		private LOAN_UTILISATION(double min, double max, int rf) {
			this.minPer = min;
			this.maxPer = max;
			this.riskFactor = rf;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.conflux.ratings.IRiskFactorType#getRatingFactorType()
	 */
	@Override
	public String getRatingFactorType() {
		return RISK_FACTOR_TYPE.ACTUAL_LOAN_UTILIZATION.getDescription();
	}

}
