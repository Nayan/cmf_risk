/**
 * 
 */
package com.conflux.ratings;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.conflux.ratings.dto.IncomeStabilityDTO;
import com.conflux.ratings.dto.LoanUtilisationDTO;
import com.conflux.ratings.dto.RiskFactorDTO;
import com.conflux.service.dto.AuditDTO;
import com.conflux.service.dto.CustomerDTO;
import com.conflux.service.dto.CustomerFamilyOccupationDTO;
import com.conflux.service.dto.ExternalLoanDTO;
import com.conflux.service.dto.LoanAccountDTO;
import com.conflux.service.dto.LoanUtilizationCheckDTO;
import com.conflux.service.dto.MeetingDTO;
import com.conflux.service.dto.OccupationDTO;
import com.conflux.service.dto.OccupationSubTypeDTO;

/**
 * @author Ashok
 * 
 */
public class CopyOfRiskRatingConstants {

	/*public static void main(String[] args) {
		List<IRiskFactor> riskFactors = new ArrayList<IRiskFactor>();
		riskFactors.add(new LoanUtilizationRiskFactor(50));
		riskFactors.add(new LoanPurposeUtilizationRiskFactor(78, 22));
		riskFactors.add(new CustomerOccupationStabilityRiskFactor(40, 30, 30));
		riskFactors.add(new MonthlySurplusRepaymentRatioRiskFactor(1.4));
		riskFactors.add(new CashFlowAnalysisRiskFactor(3));
		riskFactors.add(new CustomerInterestExpenseRationRiskFactor(4.8));
		riskFactors.add(new CustomerFamilyMemberWorkingRationRiskFactor(65));

		RiskRating riskRating = new RiskRating();
		// System.out.println(riskRating.calculateRiskRating(riskFactors));
	}
*/
	private List<RiskFactorDTO> getCustomerRiskFactors(CustomerDTO customer) {
		List<RiskFactorDTO> customerRiskFactors = new ArrayList<RiskFactorDTO>();
		LoanUtilisationDTO luDTO = getLoanUtilisation(customer);
		IncomeStabilityDTO incomeStabilityDTO = getOccupationStability(customer);
		float surplusRatio = getMonthlySurplusRepaymentRatio(customer);
		int cashFlowAnalysis = getCashflowAnalisis(customer);
		float annualSurplusInterestRatio = getAnnualSurplusInterestRatio(customer);
		float workingMemberRatio = getWorkingMemberRatio(customer);
		//Get Loan utilisation RF
		customerRiskFactors.add(new LoanUtilizationRiskFactor(luDTO
				.getStatedPurposePU()).getRiskFactorDTO());
		//Get Loan Purpose Utilisation RF
		customerRiskFactors.add(new LoanPurposeUtilizationRiskFactor(luDTO
				.getIncomeGeneratingPU(), luDTO.getConsumptionPU()).getRiskFactorDTO());
		//Get Customer Occupation Stability RF
		customerRiskFactors.add(new CustomerOccupationStabilityRiskFactor(
				incomeStabilityDTO.getHighStability(), incomeStabilityDTO
						.getMediumStability(), incomeStabilityDTO
						.getLowStability()).getRiskFactorDTO());
		//Get Monthly surplus vs repayment ratio RF
		customerRiskFactors.add(new MonthlySurplusRepaymentRatioRiskFactor(
				surplusRatio).getRiskFactorDTO());
		//Get Cashflow analysis RF
		customerRiskFactors
				.add(new CashFlowAnalysisRiskFactor(cashFlowAnalysis).getRiskFactorDTO());
		//Get Customer Interest Expense ration RF
		customerRiskFactors.add(new CustomerInterestExpenseRationRiskFactor(
				annualSurplusInterestRatio).getRiskFactorDTO());
		//Get Customer family working members ration
		customerRiskFactors
				.add(new CustomerFamilyMemberWorkingRationRiskFactor(
						workingMemberRatio).getRiskFactorDTO());
		
		return customerRiskFactors;
	}

	private int calculateRiskRating(List<RiskFactorDTO> riskFactors) {
		int[] count = getRatingsCount(riskFactors);
		int oneThirdCount = riskFactors.size() / 3;
		int halfCount = riskFactors.size() / 2;

		if ((count[0] + count[1]) >= halfCount || count[0] >= 2) {
			return RISK_RATINGS.ONE.rating;
		}

		if ((count[0] + count[1]) >= oneThirdCount) {
			return RISK_RATINGS.TWO.rating;
		}

		if (count[4] >= oneThirdCount
				&& ((count[0] + count[1] + count[2]) == 0)) {
			return RISK_RATINGS.FIVE.rating;
		}

		if ((count[0] + count[1]) == 0) {
			return RISK_RATINGS.FOUR.rating;
		}

		if ((count[0] + count[1]) <= 1) {
			return RISK_RATINGS.THREE.rating;
		}

		return 0;
	}

	private int[] getRatingsCount(List<RiskFactorDTO> riskFactors) {
		int count[] = new int[5];

		for (RiskFactorDTO rf : riskFactors) {
			switch (rf.getRiskFactor()) {
			case 1:
				count[0]++;
				break;
			case 2:
				count[1]++;
				break;
			case 3:
				count[2]++;
				break;
			case 4:
				count[3]++;
				break;
			case 5:
				count[4]++;
				break;

			default:
				break;

			}

		}
		return count;
	}

	public enum RISK_RATINGS {
		ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);
		private int rating;

		private RISK_RATINGS(int rating) {
			this.rating = rating;
		}
	}

	public enum LOAN_PURPOSE_TYPES {
		INCOME_GENERATING("Income Generating", 1), NON_INCOME_GENERATING(
				"Non Income Generating", 2), CONSUMPTION("Consumption", 3);
		private String name;
		private int index;

		private LOAN_PURPOSE_TYPES(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public String getName() {
			return name;
		}

		public int getIndex() {
			return index;
		}
	}

	public enum OCCUPATION_STABILITY_FACTORS {
		HIGH("HIGH", 0), MEDIUM("MEDIUM", 1), LOW("LOW", 2);
		private String name;
		private int index;

		private OCCUPATION_STABILITY_FACTORS(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public String getName() {
			return name;
		}

		public int getIndex() {
			return index;
		}
	}

	private LoanUtilisationDTO getLoanUtilisation(CustomerDTO customer) {
		List<LoanAccountDTO> laDTOs = customer.getLoanAccountDTOs();
		float[] percentageUtilisation = new float[4];
		LoanUtilisationDTO luDTO = new LoanUtilisationDTO();
		if (laDTOs != null && !laDTOs.isEmpty()) {
			LoanAccountDTO laDTO = laDTOs.get(0);
			// Original Loan purpose id
			int olpId = laDTO.getLoanOriginalPurposeId();
			// Get LoanUtilization details
			List<LoanUtilizationCheckDTO> lucDTOs = laDTO
					.getLoanUtilizationCheckDTOs();
			if (null != lucDTOs && !lucDTOs.isEmpty()) {
				LoanUtilizationCheckDTO lucDTO = lucDTOs.get(0);
				calculateLoanPurposeUtilisation(olpId,
						lucDTO.getLoanPurposeId1(),
						lucDTO.getUtilizationPercentage1(),
						lucDTO.getLoanPurposeType1(), percentageUtilisation);
				calculateLoanPurposeUtilisation(olpId,
						lucDTO.getLoanPurposeId2(),
						lucDTO.getUtilizationPercentage2(),
						lucDTO.getLoanPurposeType2(), percentageUtilisation);
				calculateLoanPurposeUtilisation(olpId,
						lucDTO.getLoanPurposeId3(),
						lucDTO.getUtilizationPercentage3(),
						lucDTO.getLoanPurposeType3(), percentageUtilisation);
			}

		}
		luDTO.setStatedPurposePU(percentageUtilisation[0]);
		luDTO.setIncomeGeneratingPU(percentageUtilisation[1]);
		luDTO.setNonIncomeGeneratingPU(percentageUtilisation[2]);
		luDTO.setConsumptionPU(percentageUtilisation[3]);
		return luDTO;
	}

	/**
	 * @param olpId
	 *            : Original Loan purpose Id
	 * @param alpId
	 *            : Actual Loan Purpose Id
	 * @param luPercentage
	 *            : Loan Utilization percentage
	 * @param lpType
	 *            : Loan Purpose Type
	 * @return Array of float values
	 * 
	 *         percentageUtilisation [0] = Stated Loan Purpose Utilisation
	 * 
	 *         percentageUtilisation [1] = Utilisation for Income Generating
	 * 
	 *         percentageUtilisation [2] = Utilisation for Non Income Generating
	 * 
	 *         percentageUtilisation [3] = Utilisation for Consumption
	 * 
	 */
	private void calculateLoanPurposeUtilisation(int olpId, int alpId,
			float luPercentage, String lpType, float[] percentageUtilisation) {

		if (olpId == alpId) {
			percentageUtilisation[0] = luPercentage;
		}
		for (LOAN_PURPOSE_TYPES bracket : LOAN_PURPOSE_TYPES.values()) {
			if (lpType.equals(bracket.getName())) {
				percentageUtilisation[bracket.getIndex()] += luPercentage;
				break;
			}
		}
	}

	/**
	 * @param customer
	 * @return Array of float
	 * 
	 *         occuStabilityIncome[0] = Percentage of Income from High stability
	 * 
	 *         occuStabilityIncome[1] = Percentage of Income from Medium
	 *         stability
	 * 
	 *         occuStabilityIncome[2] = Percentage of Income from Low stability
	 * 
	 */
	private IncomeStabilityDTO getOccupationStability(CustomerDTO customer) {
		OccupationDTO customerOccDTO = customer.getOccupationDTO();
		List<CustomerFamilyOccupationDTO> familyOccDTOs = customer
				.getFamilyOccupationDTOs();
		float[] occuStabilityIncome = new float[3];
		IncomeStabilityDTO incomeStabilityDTO = new IncomeStabilityDTO();

		calculateOccuStabilityIncome(customerOccDTO.getCalculatedIncome(),
				customerOccDTO.getOccupationSubTypeDTO(), occuStabilityIncome);

		for (CustomerFamilyOccupationDTO familyOccDTO : familyOccDTOs) {
			calculateOccuStabilityIncome(familyOccDTO.getCalculatedIncome(),
					familyOccDTO.getOccupationSubTypeDTO(), occuStabilityIncome);
		}
		float[] incomeStabilityPercentage = calculateOccuStabilityPercentage(occuStabilityIncome);
		incomeStabilityDTO.setHighStability(incomeStabilityPercentage[0]);
		incomeStabilityDTO.setMediumStability(incomeStabilityPercentage[1]);
		incomeStabilityDTO.setLowStability(incomeStabilityPercentage[2]);
		return incomeStabilityDTO;
	}

	/**
	 * Based on the stability factor income is added
	 * 
	 * @param income
	 * @param occuSubTypeDTO
	 * @param occuStability
	 */
	private void calculateOccuStabilityIncome(float income,
			OccupationSubTypeDTO occuSubTypeDTO, float[] occuStability) {
		for (OCCUPATION_STABILITY_FACTORS bracket : OCCUPATION_STABILITY_FACTORS
				.values()) {
			if (occuSubTypeDTO.getStabilityFactor().equals(bracket.getName())) {
				occuStability[bracket.getIndex()] += income;
				break;
			}
		}
	}

	private float[] calculateOccuStabilityPercentage(float[] occuStabilityIncome) {
		float[] occStabPercentage = new float[3];
		float totalIncome = occuStabilityIncome[0] + occuStabilityIncome[1]
				+ occuStabilityIncome[2];
		if (totalIncome <= 0)
			totalIncome = 1;
		for (int i = 0; i < occuStabilityIncome.length; i++) {
			occStabPercentage[i] = occuStabilityIncome[i] / totalIncome * 100;
		}
		return occStabPercentage;
	}

	private float getMonthlySurplusRepaymentRatio(CustomerDTO customer) {
		float monthlyRepayment = getMonthlyRepayment(customer);
		float monthlySurplus = getMonthlySurplus(customer);
		return monthlySurplus / monthlyRepayment;
	}

	private float getMonthlyRepayment(CustomerDTO customer) {
		// 10% of total loan
		float monthlyRepayment = getTotalLoanAmount(customer) * 10 / 100; 
		return monthlyRepayment;
	}

	private float getTotalLoanAmount(CustomerDTO customer) {
		float totalLoanAmount = 0;
		int year = Calendar.getInstance().get(Calendar.YEAR);
		List<LoanAccountDTO> laDTOs = customer.getLoanAccountDTOs();
		List<ExternalLoanDTO> elaDTOs = customer.getExternalLoanDTOs();

		// Calculate external loan amount
		for (ExternalLoanDTO elDTO : elaDTOs) {
			if (elDTO.getEffectiveFrom().getYear() == year) {
				totalLoanAmount += elDTO.getLoanAmount();
			}
		}

		// Calculate internal loan amount
		for (LoanAccountDTO laDTO : laDTOs) {
			if (laDTO.getCreatedDate().getYear() == year) {
				totalLoanAmount += laDTO.getAmount();
			}
		}

		return totalLoanAmount;
	}

	private float getMonthlySurplus(CustomerDTO customer) {
		float totalIncome = 0;
		OccupationDTO customerOccDTO = customer.getOccupationDTO();
		List<CustomerFamilyOccupationDTO> familyOccDTOs = customer
				.getFamilyOccupationDTOs();

		// Calculate total income
		totalIncome += customerOccDTO.getCalculatedIncome();
		for (CustomerFamilyOccupationDTO cfoDTO : familyOccDTOs) {
			totalIncome += cfoDTO.getCalculatedIncome();
		}

		return totalIncome / 12;
	}

	private float getAnnualSurplusInterestRatio(CustomerDTO customer) {
		float totalLoanDisbursed = getTotalLoanAmount(customer);
		float annualSurplusInterestRatio = totalLoanDisbursed * 15 / 100; // 15%
																			// of
																			// total
																			// loan
																			// disbursed
		return annualSurplusInterestRatio;
	}

	private int getCashflowAnalisis(CustomerDTO customer) {

		return 0;
	}

	private float getWorkingMemberRatio(CustomerDTO customer) {

		return 0;
	}

	private List<RiskFactorDTO> getCenterRiskFactors(CustomerDTO center) {
		List<RiskFactorDTO> riskFactors = new ArrayList<RiskFactorDTO>();
		List<CustomerDTO> customers = center.getCustomerDTOs();
		riskFactors.addAll(getCustomerDependentRiskFactors(customers));
		// Calculate attendance percentage
		float attenancePercentage = getCenterAttendancePercentage(center);
		riskFactors.add((new AttendanceRiskFactor(attenancePercentage)).getRiskFactorDTO());

		float auditScore = getAuditScore(center);
		riskFactors.add((new AuditRiskFactor(auditScore)).getRiskFactorDTO());
				
		return riskFactors;
	}

	private List<RiskFactorDTO> getCustomerDependentRiskFactors(List<CustomerDTO> customers){
		
		List<RiskFactorDTO> riskFactors = new ArrayList<RiskFactorDTO>();
		List<RiskFactorDTO> loanUtilisationRFs = new ArrayList<RiskFactorDTO>();
		List<RiskFactorDTO> loanPurposeUtilisationRFs = new ArrayList<RiskFactorDTO>();
		List<RiskFactorDTO> monthlySurplusRepaymentRatioRFs = new ArrayList<RiskFactorDTO>();

		LoanUtilisationDTO luDTO;
		float surplusRatio;
		// Get loan utilisation, loan purpose utilisation and monthly repayment
		// ratio for all customers in a center for average calculation
		for (CustomerDTO customerDTO : customers) {
			luDTO = getLoanUtilisation(customerDTO);
			surplusRatio = getMonthlySurplusRepaymentRatio(customerDTO);
			loanUtilisationRFs.add(new LoanUtilizationRiskFactor(luDTO
					.getStatedPurposePU()).getRiskFactorDTO());
			loanPurposeUtilisationRFs.add(new LoanPurposeUtilizationRiskFactor(
					luDTO.getIncomeGeneratingPU(), luDTO.getConsumptionPU()).getRiskFactorDTO());
			monthlySurplusRepaymentRatioRFs
					.add(new MonthlySurplusRepaymentRatioRiskFactor(
							surplusRatio).getRiskFactorDTO());
		}

		//Calculate loan utilisation
		riskFactors.add(getAverageRiskFactor(loanPurposeUtilisationRFs));
		//Calculate loan purpose utilisation risk factor
		riskFactors.add(getAverageRiskFactor(loanPurposeUtilisationRFs));
		//Calculate Monthly surplus vs repayment risk factor 
		riskFactors.add(getAverageRiskFactor(monthlySurplusRepaymentRatioRFs));

		return riskFactors;
	}
	
	private float getCenterAttendancePercentage(CustomerDTO center) {
		// Calculate attendance taken over the last one year
		List<MeetingDTO> meetingDTOs = center.getMeetingDTOs();
		float totalPerctange = 0;
		int totalMeetings = 0;
		float attenancePercentage = 0;
		for (MeetingDTO meetingDTO : meetingDTOs) {
			if (meetingDTO.isDataCaptured()) {
				totalPerctange += meetingDTO.getMembersPresent() * 100
						/ meetingDTO.getTotalMembers();
				totalMeetings++;
			}
		}

		// The actual attendance Percentage
		return attenancePercentage = totalPerctange / totalMeetings;
	}

	private float getAuditScore(CustomerDTO center) {
		List<AuditDTO> auditDTOs = center.getAuditDTOs();
		int score = 0;
		int totalScore = 0;
		int totalWeightage = 0;
		// If there are only two audits then take average
		if (auditDTOs.size() == 2) {

			for (AuditDTO auditDTO : auditDTOs) {
				if (!auditDTO.isActive()) {
					score += Integer.parseInt(auditDTO.getRatingBucketDTO()
							.getDisplayValue());
				}
			}
			score = score / 2;
		} else {
			for (AuditDTO auditDTO : auditDTOs) {
				if (!auditDTO.isActive()) {
					totalScore = Integer.parseInt(auditDTO.getRatingBucketDTO()
							.getDisplayValue());
					totalWeightage++; // This logic needs to be changes as per
										// requirements
				}

			}
			score = totalScore / totalWeightage;
		}

		return score;
	}

	private RiskFactorDTO getAverageRiskFactor(List<RiskFactorDTO> riskFactors){
		int sumRiskFactors = 0;
		String riskFactorType;
		for (RiskFactorDTO riskFactorDTO : riskFactors) {
			sumRiskFactors += riskFactorDTO.getRiskFactor();
			riskFactorType = riskFactorDTO.getRiskFactorType();
		}
		RiskFactorDTO riskFactorDTO = new RiskFactorDTO();
		riskFactorDTO.setRiskFactor(sumRiskFactors/riskFactors.size());
		return riskFactorDTO;
	}
}
