/**
 * 
 */
package com.conflux.ratings;


/**
 * @author Ashok
 * 
 */
public class RiskRatingConstants {

	public enum RISK_RATINGS {
		ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);
		private int rating;

		private RISK_RATINGS(int rating) {
			this.rating = rating;
		}
		
		public int getRiskRating(){
			return rating;
		}
	}

	public enum LOAN_PURPOSE_TYPES {
		INCOME_GENERATING("Income Generating", 1), NON_INCOME_GENERATING(
				"Non Income Generating", 2), CONSUMPTION("Consumption", 3);
		private String description;
		private int index;

		private LOAN_PURPOSE_TYPES(String desc, int index) {
			this.description = desc;
			this.index = index;
		}

		public String getDescription() {
			return description;
		}

		public int getIndex() {
			return index;
		}
	}

	public enum OCCUPATION_STABILITY_FACTORS {
		HIGH("HIGH", 0), MEDIUM("MEDIUM", 1), LOW("LOW", 2);
		private String description;
		private int index;

		private OCCUPATION_STABILITY_FACTORS(String desc, int index) {
			this.description = desc;
			this.index = index;
		}

		public String getDescription() {
			return description;
		}

		public int getIndex() {
			return index;
		}
	}
}
