/**
 * 
 */
package com.conflux.ratings;

import com.conflux.ratings.dto.RiskFactorDTO;

/**
 * @author Ashok
 *
 */
public interface IRiskFactorType {
	int calculateRiskFactor();
	String getRatingFactorType();
	RiskFactorDTO getRiskFactorDTO();
	
	public enum RISK_FACTOR_TYPE{
		ACTUAL_LOAN_UTILIZATION("Actual Loan Utilization"),
		LOAN_PURPOSE_UTILIZATION("Loan Purpose Utilization"),
		CUSTOMER_OCCUPATION_STABILITY("Customer Occupation Stability"),
		MONTHLY_SURPLUS_REPAYMENT_RATIO("Monthly Surplus / Repayment Ratio"),
		CASH_FLOW_ANALYSIS("Cash Flow Analysis"),
		ANNUAL_INTEREST_EXPENSE_RATIO("Annual Surplus By Interest Ratio"),
		CUSTOMER_FAMILY_WORKING_RATIO("Customer Family Members Working to Non Working Ratio"),
		ATTENDANCE("Attendance taken over the last one year"),
		AUDIT_FEEDBACK("Audit Feedback"),
		MANUAL_RATING("Manual Rating");
		
		private String description;
		
		private RISK_FACTOR_TYPE(String desc){
			description = desc;
		}
		
		public String getDescription(){
			return description;
		}
	}
}
