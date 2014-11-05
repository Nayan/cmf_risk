/**
 * 
 */
package com.conflux.ratings;

import com.conflux.ratings.dto.RiskFactorDTO;

/**
 * @author Ashok
 *
 */
public class ManualRatingRiskFactor implements IRiskFactorType {

	private double manualRating;
		
	/**
	 * @param manualRating
	 */
	public ManualRatingRiskFactor(double manualRating) {
		super();
		this.manualRating = manualRating;
	}

	/**
	 * @return the manualRating
	 */
	public double getManualRating() {
		return manualRating;
	}

	/**
	 * @param manualRating the manualRating to set
	 */
	public void setManualRating(double manualRating) {
		this.manualRating = manualRating;
	}

	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#calculateRiskFactor()
	 */
	@Override
	public int calculateRiskFactor() {
		for (ManualRatingRiskFactorEnum bracket : ManualRatingRiskFactorEnum.values()) {
			if (manualRating >= bracket.minPer && manualRating <= bracket.maxPer) {
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
		riskFactorDTO.setRiskFactorType(RISK_FACTOR_TYPE.MANUAL_RATING.name());
		return riskFactorDTO;
	}
	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#getRatingFactorType()
	 */
	@Override
	public String getRatingFactorType() {
		// TODO Auto-generated method stub
		return RISK_FACTOR_TYPE.MANUAL_RATING.getDescription();
	}
	
	public enum ManualRatingRiskFactorEnum {

		ONE(0.0, 1, 1), TWO(1, 2, 2), THREE(2, 3, 3), FOUR(3, 4, 4), FIVE(4, 5, 5);

		private double minPer;
		private double maxPer;
		private int riskFactor;

		private ManualRatingRiskFactorEnum(double min, double max, int rf) {
			this.minPer = min;
			this.maxPer = max;
			this.riskFactor = rf;
		}
		
		public int getRiskFactor(){
			return riskFactor;
		}
	}

}
