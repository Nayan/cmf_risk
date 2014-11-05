/**
 * 
 */
package com.conflux.ratings;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Ashok
 * 
 */
public class RiskFactorCalculator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		// TODO Auto-generated method stub
		//System.out.println(LoanPurposeEnum.getRiskFactor(60.1));
		
		//int [] count = new int [5];
		//count = updateArray(count);
		//count = updateArray(count);
		/*updateArray(count);
		updateArray(count);
		updateArray(count);
		
		System.out.println(count[0]);
		System.out.println(count[1]);
		System.out.println(count[2]);
		System.out.println(count[3]);
		System.out.println(count[4]);*/
	}

	private static void updateArray(int[] count){
		count[0] += 1;
		count[1] += 2;
		count[2] += 3;
		count[3] += 4;
		count[4] += 5;
		//return count;
	}
	
	
}
