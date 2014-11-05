/**
 * 
 */
package com.conflux.ratings;

import com.conflux.ratings.dto.RiskFactorDTO;

/**
 * @author Ashok
 *
 */
public class CustomerInterestExpenseRationRiskFactor implements IRiskFactorType {

	private double interestExpenseRation;
	
	
	/**
	 * @param interestExpenseRation
	 */
	public CustomerInterestExpenseRationRiskFactor(double interestExpenseRation) {
		super();
		this.interestExpenseRation = interestExpenseRation;
	}


	/**
	 * @return the interestExpenseRation
	 */
	public double getInterestExpenseRation() {
		return interestExpenseRation;
	}


	/**
	 * @param interestExpenseRation the interestExpenseRation to set
	 */
	public void setInterestExpenseRation(double interestExpenseRation) {
		this.interestExpenseRation = interestExpenseRation;
	}


	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#calculateRiskFactor()
	 */
	@Override
	public int calculateRiskFactor() {
		for (INTEREST_EXPENSE_RATIO bracket : INTEREST_EXPENSE_RATIO.values()) {
			if (interestExpenseRation > bracket.maxPer) {
				return bracket.riskFactor;
			}
		}
		return 3;//Default it to three
	}

	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#getRiskFactorDTO()
	 */
	@Override
	public RiskFactorDTO getRiskFactorDTO() {
		RiskFactorDTO riskFactorDTO = new RiskFactorDTO();
		riskFactorDTO.setRiskFactor(calculateRiskFactor());
		riskFactorDTO.setRiskFactorType(RISK_FACTOR_TYPE.ANNUAL_INTEREST_EXPENSE_RATIO.name());
		return riskFactorDTO;
	}
	
	public enum INTEREST_EXPENSE_RATIO{
		FIVE(10, 5), FOUR(5, 4), THREE(3, 3), TWO(2, 2), ONE(0, 1);
		
		private double maxPer;
		private int riskFactor;
		
		private INTEREST_EXPENSE_RATIO(double max, int rf) {
			this.maxPer = max;
			this.riskFactor = rf;
		}
	}

	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#getRatingFactorType()
	 */
	@Override
	public String getRatingFactorType() {
		return RISK_FACTOR_TYPE.ANNUAL_INTEREST_EXPENSE_RATIO.getDescription();
	}
	
	
}
