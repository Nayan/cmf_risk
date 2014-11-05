/**
 * 
 */
package com.conflux.ratings;

import com.conflux.ratings.dto.RiskFactorDTO;

/**
 * @author Ashok
 *
 */
public class AttendanceRiskFactor implements IRiskFactorType {

	private double attendance;
	
	
	/**
	 * @param attendance
	 */
	public AttendanceRiskFactor(double attendance) {
		super();
		this.attendance = attendance;
	}

	/**
	 * @return the attendance
	 */
	public double getAttendance() {
		return attendance;
	}

	/**
	 * @param attendance the attendance to set
	 */
	public void setAttendance(double attendance) {
		this.attendance = attendance;
	}

	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#calculateRiskFactor()
	 */
	@Override
	public int calculateRiskFactor() {
		for (AttendanceRiskFactorEnum bracket : AttendanceRiskFactorEnum.values()) {
			if (attendance >= bracket.minPer && attendance <= bracket.maxPer) {
				return bracket.riskFactor;
			}
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#getRiskFactorDTO()
	 */
	@Override
	public RiskFactorDTO getRiskFactorDTO() {
		RiskFactorDTO riskFactorDTO = new RiskFactorDTO();
		riskFactorDTO.setRiskFactor(calculateRiskFactor());
		riskFactorDTO.setRiskFactorType(RISK_FACTOR_TYPE.ATTENDANCE.name());
		return riskFactorDTO;
	}
	
	/* (non-Javadoc)
	 * @see com.conflux.ratings.IRiskFactorType#getRatingFactorType()
	 */
	@Override
	public String getRatingFactorType() {
		// TODO Auto-generated method stub
		return RISK_FACTOR_TYPE.ATTENDANCE.getDescription();
	}

	public enum AttendanceRiskFactorEnum {

		ONE(0.0, 49.99, 1), TWO(50.0, 66.0, 2), THREE(66.0, 80.0, 3), FOUR(80.0, 95.0, 4), FIVE(95.0, 100.0, 5);

		private double minPer;
		private double maxPer;
		private int riskFactor;

		private AttendanceRiskFactorEnum(double min, double max, int rf) {
			this.minPer = min;
			this.maxPer = max;
			this.riskFactor = rf;
		}
		
		public int getRiskFactor(){
			return riskFactor;
		}
	}

}
