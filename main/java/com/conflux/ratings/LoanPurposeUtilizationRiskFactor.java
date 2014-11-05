/**
 * 
 */
package com.conflux.ratings;

import com.conflux.ratings.dto.RiskFactorDTO;

/**
 * @author Ashok
 * 
 */
public class LoanPurposeUtilizationRiskFactor implements IRiskFactorType {

	// Income generating utilization
	private double incomeGeneratingPU;
	// Consumption utilization
	private double consumptionPU;

	public LoanPurposeUtilizationRiskFactor(double incomeGeneratingPU,
			double consumptionPU) {
		super();
		this.incomeGeneratingPU = incomeGeneratingPU;
		this.consumptionPU = consumptionPU;
	}

	/**
	 * @return the incomeGeneratingPU
	 */
	public double getIncomeGeneratingPU() {
		return incomeGeneratingPU;
	}

	/**
	 * @param incomeGeneratingPU
	 *            the incomeGeneratingPU to set
	 */
	public void setIncomeGeneratingPU(double incomeGeneratingPU) {
		this.incomeGeneratingPU = incomeGeneratingPU;
	}

	/**
	 * @return the consumptionPU
	 */
	public double getConsumptionPU() {
		return consumptionPU;
	}

	/**
	 * @param consumptionPU
	 *            the consumptionPU to set
	 */
	public void setConsumptionPU(double consumptionPU) {
		this.consumptionPU = consumptionPU;
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
		if (incomeGeneratingPU == -1 && consumptionPU == -1)
			return 3;
		
		int riskFactorTemp = 3;// Default it to 3
		
		for (INCOME_GENERATION bracket : INCOME_GENERATION.values()) {
			if (incomeGeneratingPU >= bracket.maxPer) {
				riskFactorTemp = bracket.riskFactor;
				return riskFactorTemp;
			}
		}

		for (CONSUMPTION bracket : CONSUMPTION.values()) {
			if (consumptionPU >= bracket.maxPer) {
				riskFactorTemp = bracket.riskFactor;
				return riskFactorTemp;
			}
		}

		return riskFactorTemp;
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
				.setRiskFactorType(RISK_FACTOR_TYPE.LOAN_PURPOSE_UTILIZATION
						.name());
		return riskFactorDTO;
	}

	public enum INCOME_GENERATION {

		FIVE(90.0, 5), FOUR(70.0, 4), THREE(33.0, 3);

		private double maxPer;
		private int riskFactor;

		private INCOME_GENERATION(double max, int rf) {
			this.maxPer = max;
			this.riskFactor = rf;
		}
	}

	public enum CONSUMPTION {

		ONE(100.0, 1), TWO(50.0, 2);

		private double maxPer;
		private int riskFactor;

		private CONSUMPTION(double max, int rf) {
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
		return RISK_FACTOR_TYPE.LOAN_PURPOSE_UTILIZATION.getDescription();
	}

}
