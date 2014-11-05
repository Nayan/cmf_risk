/**
 * 
 */
package com.conflux.ratings.dto;

/**
 * @author Ashok
 *
 */
public class LoanUtilisationDTO {

	//Loan utilisation for Stated loan purpose 
	private float statedPurposePU = -1;//To represent no loan is disbursed
	//Loan utilisation for income generating purpose
	private float incomeGeneratingPU = -1;
	//Loan utilisation for non-income generating
	private float nonIncomeGeneratingPU = -1;
	//Loan utilisation for Consumption
	private float consumptionPU = -1;
	/**
	 * @return the statedPurposePU
	 */
	public float getStatedPurposePU() {
		return statedPurposePU;
	}
	/**
	 * @param statedPurposePU the statedPurposePU to set
	 */
	public void setStatedPurposePU(float statedPurposePU) {
		this.statedPurposePU = statedPurposePU;
	}
	/**
	 * @return the incomeGeneratingPU
	 */
	public float getIncomeGeneratingPU() {
		return incomeGeneratingPU;
	}
	/**
	 * @param incomeGeneratingPU the incomeGeneratingPU to set
	 */
	public void setIncomeGeneratingPU(float incomeGeneratingPU) {
		this.incomeGeneratingPU = incomeGeneratingPU;
	}
	/**
	 * @return the nonIncomeGeneratingPU
	 */
	public float getNonIncomeGeneratingPU() {
		return nonIncomeGeneratingPU;
	}
	/**
	 * @param nonIncomeGeneratingPU the nonIncomeGeneratingPU to set
	 */
	public void setNonIncomeGeneratingPU(float nonIncomeGeneratingPU) {
		this.nonIncomeGeneratingPU = nonIncomeGeneratingPU;
	}
	/**
	 * @return the consumptionPU
	 */
	public float getConsumptionPU() {
		return consumptionPU;
	}
	/**
	 * @param consumptionPU the consumptionPU to set
	 */
	public void setConsumptionPU(float consumptionPU) {
		this.consumptionPU = consumptionPU;
	}
		
}
