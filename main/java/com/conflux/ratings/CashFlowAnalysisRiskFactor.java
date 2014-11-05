/**
 * 
 */
package com.conflux.ratings;

import com.conflux.ratings.dto.RiskFactorDTO;

/**
 * @author Ashok
 *
 */
public class CashFlowAnalysisRiskFactor implements IRiskFactorType {

	private int negetiveMonths;
		
	/**
	 * @param negetiveMonths
	 */
	public CashFlowAnalysisRiskFactor(int negetiveMonths) {
		super();
		this.negetiveMonths = negetiveMonths;
	}


	/**
	 * @return the negetiveMonths
	 */
	public int getNegetiveMonths() {
		return negetiveMonths;
	}


	/**
	 * @param negetiveMonths the negetiveMonths to set
	 */
	public void setNegetiveMonths(int negetiveMonths) {
		this.negetiveMonths = negetiveMonths;
	}


	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#calculateRiskFactor()
	 */
	@Override
	public int calculateRiskFactor() {
		for (CASH_FLOW bracket : CASH_FLOW.values()) {
			if (negetiveMonths >= bracket.minPer && negetiveMonths <= bracket.maxPer) {
				return bracket.riskFactor;
			}
		}
		return 1; //Default it to one
	}

	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#getRiskFactorDTO()
	 */
	@Override
	public RiskFactorDTO getRiskFactorDTO() {
		RiskFactorDTO riskFactorDTO = new RiskFactorDTO();
		riskFactorDTO.setRiskFactor(calculateRiskFactor());
		riskFactorDTO.setRiskFactorType(RISK_FACTOR_TYPE.CASH_FLOW_ANALYSIS.name());
		return riskFactorDTO;
	}
	
	public enum CASH_FLOW{
		FIVE(0, 0, 5), FOUR(1, 1, 4), THREE(2, 3, 3), TWO(3, 6, 2), ONE(6, 100, 1);
		
		private int minPer;
		private int maxPer;
		private int riskFactor;
		
		private CASH_FLOW(int min, int max, int rf) {
			this.minPer = min;
			this.maxPer = max;
			this.riskFactor = rf;
		}
	}

	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#getRatingFactorType()
	 */
	@Override
	public String getRatingFactorType() {
		return RISK_FACTOR_TYPE.CASH_FLOW_ANALYSIS.getDescription();
	}
	
	
}
