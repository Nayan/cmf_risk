/**
 * 
 */
package com.conflux.ratings;

import com.conflux.ratings.dto.RiskFactorDTO;

/**
 * @author Ashok
 *
 */
public class MonthlySurplusRepaymentRatioRiskFactor implements IRiskFactorType {

	private double surplusRatio;
	
	
	/**
	 * @param surplusRatio
	 */
	public MonthlySurplusRepaymentRatioRiskFactor(double surplusRatio) {
		super();
		this.surplusRatio = surplusRatio;
	}


	/**
	 * @return the surplusRatio
	 */
	public double getSurplusRatio() {
		return surplusRatio;
	}


	/**
	 * @param surplusRatio the surplusRatio to set
	 */
	public void setSurplusRatio(double surplusRatio) {
		this.surplusRatio = surplusRatio;
	}


	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#calculateRiskFactor()
	 */
	@Override
	public int calculateRiskFactor() {
		for (SURPLUS_RATIO bracket : SURPLUS_RATIO.values()) {
			if (surplusRatio >= bracket.maxPer) {
				return bracket.riskFactor;
			}
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
		riskFactorDTO.setRiskFactorType(RISK_FACTOR_TYPE.MONTHLY_SURPLUS_REPAYMENT_RATIO.name());
		return riskFactorDTO;
	}
	
	public enum SURPLUS_RATIO{
		FIVE(2.0, 5), FOUR(1.5, 4), THREE(1.0, 3), TWO(0.5, 2), ONE(0.0, 1);
		
		private double maxPer;
		private int riskFactor;
		
		private SURPLUS_RATIO(double max, int rf) {
			this.maxPer = max;
			this.riskFactor = rf;
		}
	}

	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#getRatingFactorType()
	 */
	@Override
	public String getRatingFactorType() {
		return RISK_FACTOR_TYPE.MONTHLY_SURPLUS_REPAYMENT_RATIO.getDescription();
	}
	
}
