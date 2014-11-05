/**
 * 
 */
package com.conflux.ratings.dto;

/**
 * @author Ashok
 *
 */
public class IncomeStabilityDTO {

	//Percentage of Income generated from High stability occupation
	private float highStability = -1;
	//Percentage of Income generated from Medium stability occupation
	private float mediumStability = -1;
	//Percentage of Income generated from Low stability occupation
	private float lowStability = -1;
	/**
	 * @return the highStability
	 */
	public float getHighStability() {
		return highStability;
	}
	/**
	 * @param highStability the highStability to set
	 */
	public void setHighStability(float highStability) {
		this.highStability = highStability;
	}
	/**
	 * @return the mediumStability
	 */
	public float getMediumStability() {
		return mediumStability;
	}
	/**
	 * @param mediumStability the mediumStability to set
	 */
	public void setMediumStability(float mediumStability) {
		this.mediumStability = mediumStability;
	}
	/**
	 * @return the lowStability
	 */
	public float getLowStability() {
		return lowStability;
	}
	/**
	 * @param lowStability the lowStability to set
	 */
	public void setLowStability(float lowStability) {
		this.lowStability = lowStability;
	}
	
}
