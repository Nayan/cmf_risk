/**
 * 
 */
package com.conflux.ratings;

import com.conflux.ratings.dto.RiskFactorDTO;

/**
 * @author Ashok
 *
 */
public class AuditRiskFactor implements IRiskFactorType {

	private double auditRating;
			
	/**
	 * @param auditRating : audit rating should be between 0 and 5
	 */
	public AuditRiskFactor(double auditRating) {
		super();
		this.auditRating = auditRating;
	}

	/**
	 * @return the auditRating
	 */
	public double getAuditRating() {
		return auditRating;
	}

	/**
	 * @param auditRating the auditRating to set
	 */
	public void setAuditRating(double auditRating) {
		this.auditRating = auditRating;
	}

	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#calculateRiskFactor()
	 */
	@Override
	public int calculateRiskFactor() {
		for (AuditRiskFactorEnum bracket : AuditRiskFactorEnum.values()) {
			if (auditRating >= bracket.minPer && auditRating <= bracket.maxPer) {
				return bracket.getRiskFactor();
			}
		}
		return 3;//Default it to 3
	}

	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#getRiskFactorDTO()
	 */
	@Override
	public RiskFactorDTO getRiskFactorDTO() {
		RiskFactorDTO riskFactorDTO = new RiskFactorDTO();
		riskFactorDTO.setRiskFactor(calculateRiskFactor());
		riskFactorDTO.setRiskFactorType(RISK_FACTOR_TYPE.AUDIT_FEEDBACK.name());
		return riskFactorDTO;
	}
	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#getRatingFactorType()
	 */
	@Override
	public String getRatingFactorType() {
		// TODO Auto-generated method stub
		return RISK_FACTOR_TYPE.AUDIT_FEEDBACK.getDescription();
	}

	public enum AuditRiskFactorEnum {

		ONE(0.01, 1.50, 1), TWO(1.50, 2.50, 2), THREE(2.50, 3.50, 3), FOUR(3.50, 4.50, 4), FIVE(4.50, 5.00, 5);

		private double minPer;
		private double maxPer;
		private int riskFactor;

		private AuditRiskFactorEnum(double min, double max, int rf) {
			this.minPer = min;
			this.maxPer = max;
			this.riskFactor = rf;
		}
		
		public int getRiskFactor(){
			return riskFactor;
		}
	}

}
