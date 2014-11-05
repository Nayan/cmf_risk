package com.conflux.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conflux.common.ApplicationConstants;
import com.conflux.common.ApplicationConstants.LOAN_STATUS;
import com.conflux.common.ApplicationConstants.USER_ROLE;
import com.conflux.ratings.AttendanceRiskFactor;
import com.conflux.ratings.AuditRiskFactor;
import com.conflux.ratings.CashFlowAnalysisRiskFactor;
import com.conflux.ratings.CustomerFamilyMemberWorkingRationRiskFactor;
import com.conflux.ratings.CustomerInterestExpenseRationRiskFactor;
import com.conflux.ratings.CustomerOccupationStabilityRiskFactor;
import com.conflux.ratings.LoanPurposeUtilizationRiskFactor;
import com.conflux.ratings.LoanUtilizationRiskFactor;
import com.conflux.ratings.MonthlySurplusRepaymentRatioRiskFactor;
import com.conflux.ratings.RiskRatingConstants.LOAN_PURPOSE_TYPES;
import com.conflux.ratings.RiskRatingConstants.OCCUPATION_STABILITY_FACTORS;
import com.conflux.ratings.RiskRatingConstants.RISK_RATINGS;
import com.conflux.ratings.dto.IncomeStabilityDTO;
import com.conflux.ratings.dto.LoanUtilisationDTO;
import com.conflux.ratings.dto.RiskFactorDTO;
import com.conflux.service.AbstractBaseService;
import com.conflux.service.IPortalUserService;
import com.conflux.service.IRiskRatingService;
import com.conflux.service.dto.AuditDTO;
import com.conflux.service.dto.CustomerDTO;
import com.conflux.service.dto.CustomerFamilyOccupationDTO;
import com.conflux.service.dto.ExternalLoanDTO;
import com.conflux.service.dto.IncomeLumpingDTO;
import com.conflux.service.dto.LoanAccountDTO;
import com.conflux.service.dto.LoanUtilizationCheckDTO;
import com.conflux.service.dto.MeetingDTO;
import com.conflux.service.dto.OccupationDTO;
import com.conflux.service.dto.OccupationSubTypeDTO;

@Service
public class RiskRatingService extends AbstractBaseService implements
		IRiskRatingService {

	@Autowired
	IPortalUserService userService;
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.conflux.service.IRiskRatingService#getCustomerRiskFactors(com.conflux
	 * .service.dto.CustomerDTO)
	 */
	@Override
	public List<RiskFactorDTO> getCustomerRiskFactors(CustomerDTO customer) {
		List<RiskFactorDTO> customerRiskFactors = new ArrayList<RiskFactorDTO>();
		LoanUtilisationDTO luDTO = getLoanUtilisation(customer);
		IncomeStabilityDTO incomeStabilityDTO = getOccupationStability(customer);
		float totalLoanAmount = getTotalLoanAmount(customer);
		float monthlyRepayment = getMonthlyRepayment(totalLoanAmount);
		float totalIncome = getTotalIncome(customer);
		float surplusRatio = getMonthlySurplusRepaymentRatio(totalIncome,
				monthlyRepayment);
		int cashFlowAnalysis = getCashflowAnalisis(customer, monthlyRepayment);
		float annualSurplusInterestRatio = getAnnualSurplusInterestRatio(totalLoanAmount);
		float workingMemberRatio = getWorkingMemberRatio(customer);

		// 1. Get Loan Utilisation RF
		LoanUtilizationRiskFactor luRF = new LoanUtilizationRiskFactor(
				luDTO.getStatedPurposePU());
		customerRiskFactors.add(luRF.getRiskFactorDTO());

		// 2. Get Loan Purpose Utilisation RF
		LoanPurposeUtilizationRiskFactor lpuRF = new LoanPurposeUtilizationRiskFactor(
				luDTO.getIncomeGeneratingPU(), luDTO.getConsumptionPU());
		customerRiskFactors.add(lpuRF.getRiskFactorDTO());

		// 3. Get Customer Occupation Stability RF
		CustomerOccupationStabilityRiskFactor cosRF = new CustomerOccupationStabilityRiskFactor(
				incomeStabilityDTO.getHighStability(),
				incomeStabilityDTO.getMediumStability(),
				incomeStabilityDTO.getLowStability());
		customerRiskFactors.add(cosRF.getRiskFactorDTO());

		// 4. Get Monthly surplus vs repayment ratio RF
		MonthlySurplusRepaymentRatioRiskFactor msrRF = new MonthlySurplusRepaymentRatioRiskFactor(
				surplusRatio);
		customerRiskFactors.add(msrRF.getRiskFactorDTO());

		// 5. Get Cashflow analysis RF
		CashFlowAnalysisRiskFactor cfaRF = new CashFlowAnalysisRiskFactor(
				cashFlowAnalysis);
		customerRiskFactors.add(cfaRF.getRiskFactorDTO());

		// 6. Get Customer Interest Expense ration RF
		CustomerInterestExpenseRationRiskFactor cieRF = new CustomerInterestExpenseRationRiskFactor(
				annualSurplusInterestRatio);
		customerRiskFactors.add(cieRF.getRiskFactorDTO());

		// 7. Get Customer family working members ration
		CustomerFamilyMemberWorkingRationRiskFactor cfmRF = new CustomerFamilyMemberWorkingRationRiskFactor(
				workingMemberRatio);
		customerRiskFactors.add(cfmRF.getRiskFactorDTO());

		return customerRiskFactors;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.conflux.service.IRiskRatingService#getCenterRiskFactors(com.conflux
	 * .service.dto.CustomerDTO)
	 */
	@Override
	public List<RiskFactorDTO> getCenterRiskFactors(CustomerDTO center, Map<String, ApplicationConstants.USER_ROLE> users) {
		List<RiskFactorDTO> riskFactors = new ArrayList<RiskFactorDTO>();
		List<CustomerDTO> customers = center.getCustomerDTOs();
		riskFactors.addAll(getCustomerDependentRiskFactors(customers));
		// Calculate attendance percentage
		float attenancePercentage = getCenterAttendancePercentage(center);
		riskFactors.add((new AttendanceRiskFactor(attenancePercentage))
				.getRiskFactorDTO());

		float auditScore = getAuditScore(center, users);
		riskFactors.add((new AuditRiskFactor(auditScore)).getRiskFactorDTO());

		RiskFactorDTO bmManualRatingRF = new RiskFactorDTO();
		String bmRatingStr = center.getBmRating();
		int bmRating = 3; // default it to 3
		if (null != bmRatingStr) {
			try {
				bmRating = Integer.parseInt(bmRatingStr);
			} catch (NumberFormatException ne) {
				bmRating = 3;// Default value
			}
		}
		bmManualRatingRF.setRiskFactor(bmRating);
		riskFactors.add(bmManualRatingRF);

		return riskFactors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.conflux.service.IRiskRatingService#calculateRiskRating(java.util.
	 * List)
	 */
	@Override
	public int calculateRiskRating(List<RiskFactorDTO> riskFactors) {
		int[] count = getRatingsCount(riskFactors);
		int oneThirdCount = riskFactors.size() / 3;
		int halfCount = riskFactors.size() / 2;

		if ((count[0] + count[1]) >= halfCount || count[0] >= 2) {
			return RISK_RATINGS.ONE.getRiskRating();
		}

		if ((count[0] + count[1]) >= oneThirdCount) {
			return RISK_RATINGS.TWO.getRiskRating();
		}

		if (count[4] >= oneThirdCount
				&& ((count[0] + count[1] + count[2]) == 0)) {
			return RISK_RATINGS.FIVE.getRiskRating();
		}

		if ((count[0] + count[1]) == 0) {
			return RISK_RATINGS.FOUR.getRiskRating();
		}

		if ((count[0] + count[1]) <= 1) {
			return RISK_RATINGS.THREE.getRiskRating();
		}

		return 3; // default rating
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

	/**
	 * This method calculates the actual utilisation of given loan
	 * 
	 * @param customer
	 * @return returns LoanUtilisationDTO if there is a loan
	 */
	private LoanUtilisationDTO getLoanUtilisation(CustomerDTO customer) {
		List<LoanAccountDTO> laDTOs = customer.getLoanAccountDTOs();
		float[] percentageUtilisation = new float[4];
		LoanUtilisationDTO luDTO = new LoanUtilisationDTO();
		int totalLoanAmount = 0;
		if (laDTOs != null && !laDTOs.isEmpty()) {
			List<LoanUtilizationCheckDTO> lucDTOs;
			int olpId;
			boolean lucExist = false;
			for (LoanAccountDTO laDTO : laDTOs) {
				// Skip 1. if Loan Utilisation check is pending
				// 2. Loan repayment on time
				if (!LOAN_STATUS.LOAN_CLOSED.name().equals(
						laDTO.getLoanCurrentStatus())
						&& !laDTO.isPendingLuc()) {

					// Get LoanUtilization details
					lucDTOs = laDTO.getLoanUtilizationCheckDTOs();
					// Calculate loan utilisation only if LUC is completed and
					// LUC record exists
					if (null != lucDTOs && !lucDTOs.isEmpty()) {
						lucExist = true;
						// Original Loan purpose id
						olpId = laDTO.getLoanOriginalPurposeId();
						// Only one loan utilisation object is associated with a
						// loan.
						LoanUtilizationCheckDTO lucDTO = lucDTOs.get(0);
						calculateLoanPurposeUtilisation(olpId,
								lucDTO.getLoanPurposeId1(),
								lucDTO.getUtilizationPercentage1(),
								lucDTO.getLoanPurposeType1(),
								lucDTO.getLoanAmount(), percentageUtilisation);
						calculateLoanPurposeUtilisation(olpId,
								lucDTO.getLoanPurposeId2(),
								lucDTO.getUtilizationPercentage2(),
								lucDTO.getLoanPurposeType2(),
								lucDTO.getLoanAmount(), percentageUtilisation);
						calculateLoanPurposeUtilisation(olpId,
								lucDTO.getLoanPurposeId3(),
								lucDTO.getUtilizationPercentage3(),
								lucDTO.getLoanPurposeType3(),
								lucDTO.getLoanAmount(), percentageUtilisation);
						// Add to total loan amount
						totalLoanAmount += lucDTO.getLoanAmount();
					}
				}
			}

			if (lucExist) {
				// recalculate the loan utilisation percentage
				// This calculation is to distribute loan utilisation
				// equally based on loan amount
				if (totalLoanAmount <= 0)
					totalLoanAmount = 1;
				for (int i = 0; i < percentageUtilisation.length; i++) {
					percentageUtilisation[i] = percentageUtilisation[i] * 100
							/ totalLoanAmount;
				}
				luDTO.setStatedPurposePU(percentageUtilisation[0]);
				luDTO.setIncomeGeneratingPU(percentageUtilisation[1]);
				luDTO.setNonIncomeGeneratingPU(percentageUtilisation[2]);
				luDTO.setConsumptionPU(percentageUtilisation[3]);
			}

		}

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
			float luPercentage, String lpType, float loanAmount,
			float[] percentageUtilisation) {
		// stated loan purpose id = actual loan utilised purpose id
		if (olpId == alpId) {
			percentageUtilisation[0] += luPercentage; //luPercentage is a amount utilised (not percentage)
		}

		for (LOAN_PURPOSE_TYPES bracket : LOAN_PURPOSE_TYPES.values()) {
			if (lpType != null && lpType.equals(bracket.getDescription())) {
				percentageUtilisation[bracket.getIndex()] += luPercentage;
				break;
			}
		}
	}

	/**
	 * @param customer
	 * @return
	 * 
	 */
	private IncomeStabilityDTO getOccupationStability(CustomerDTO customer) {
		OccupationDTO customerOccDTO = customer.getOccupationDTO();
		List<CustomerFamilyOccupationDTO> familyOccDTOs = customer
				.getFamilyOccupationDTOs();

		/*
		 * The float array index is represents
		 * OCCUPATION_STABILITY_FACTORS.index OCCUPATION_STABILITY_FACTORS.HIGH
		 * OCCUPATION_STABILITY_FACTORS.MEDIUM OCCUPATION_STABILITY_FACTORS.LOW
		 */
		float[] occuStabilityIncome = new float[3];
		IncomeStabilityDTO incomeStabilityDTO = new IncomeStabilityDTO();
		boolean incomeExists = false;
		// Calculate customer occupation stability factor only if Customer
		// Occupation is not empty
		if (null != customerOccDTO) {
			calculateOccuStabilityIncome(customerOccDTO.getCalculatedIncome(),
					customerOccDTO.getOccupationSubTypeDTO(),
					occuStabilityIncome);
			incomeExists = true;
		}
		// Calculate occupation stability based on family occupation details
		for (CustomerFamilyOccupationDTO familyOccDTO : familyOccDTOs) {
			calculateOccuStabilityIncome(familyOccDTO.getCalculatedIncome(),
					familyOccDTO.getOccupationSubTypeDTO(), occuStabilityIncome);
			incomeExists = true;
		}

		if (incomeExists) {
			// Calculate occupation stability percentage
			float[] incomeStabilityPercentage = calculateOccuStabilityPercentage(occuStabilityIncome);
			incomeStabilityDTO.setHighStability(incomeStabilityPercentage[0]);// OCCUPATION_STABILITY_FACTORS.HIGH
			incomeStabilityDTO.setMediumStability(incomeStabilityPercentage[1]);// OCCUPATION_STABILITY_FACTORS.MEDIUM
			incomeStabilityDTO.setLowStability(incomeStabilityPercentage[2]);// OCCUPATION_STABILITY_FACTORS.LOW
		}
		return incomeStabilityDTO;
	}

	/**
	 * The income is segregated and added to specified slab (High, Medium, Low)
	 * based on stability factor The occuStability array index is represented by
	 * enum OCCUPATION_STABILITY_FACTORS.index
	 * 
	 * @param income
	 * @param occuSubTypeDTO
	 * @param occuStability
	 * 
	 * 
	 */
	private void calculateOccuStabilityIncome(float income,
			OccupationSubTypeDTO occuSubTypeDTO, float[] occuStability) {
		// do not proceed if occupation subtype is null
		if (null == occuSubTypeDTO)
			return;
		String stabilityFactor = occuSubTypeDTO.getStabilityFactor();
		if (null == stabilityFactor)
			return;
		for (OCCUPATION_STABILITY_FACTORS bracket : OCCUPATION_STABILITY_FACTORS
				.values()) {

			if (occuSubTypeDTO.getStabilityFactor().equals(
					bracket.getDescription())) {
				occuStability[bracket.getIndex()] += income;
				break;
			}
		}
	}

	/**
	 * This method calculates the percentage of stability factor based on the
	 * income generated by specified stability factor.
	 * 
	 * @param occuStabilityIncome
	 * @return float array income stability factor percentage. The array index
	 *         represents enum OCCUPATION_STABILITY_FACTORS.index
	 */
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

	/**
	 * Calculates Monthly Surplus vs repayment ratio
	 * 
	 * @param customer
	 * @return surplus / repayment ratio
	 */
	private float getMonthlySurplusRepaymentRatio(float totalIncome,
			float monthlyRepayment) {
		monthlyRepayment = monthlyRepayment <= 0 ? 1 : monthlyRepayment;
		float monthlySurplus = getMonthlySurplus(totalIncome);
		return monthlySurplus / monthlyRepayment;
	}

	/**
	 * Monthly repayment is ten (10) percentage of total loan amount taken by
	 * the customer.
	 * 
	 * @param customer
	 * @return monthly repayment amount.
	 */
	private float getMonthlyRepayment(float totalLoanAmount) {
		// Ten (10) % of total loan amount
		return totalLoanAmount * 10 / 100;
	}

	/**
	 * Sum all loan amounts (includes internal & external MFI loans) taken by
	 * the customer in the year at any time.
	 * 
	 * @param customer
	 * @return total loan amount taken by the customer in the year at any time.
	 */
	@SuppressWarnings("deprecation")
	private float getTotalLoanAmount(CustomerDTO customer) {
		float totalLoanAmount = 0;
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(new Date());
		//Calendar fromDate = Calendar.getInstance();

		List<LoanAccountDTO> laDTOs = customer.getLoanAccountDTOs();
		List<ExternalLoanDTO> elaDTOs = customer.getExternalLoanDTOs();

		// Calculate external loan amount
		for (ExternalLoanDTO elDTO : elaDTOs) {
			
			if(elDTO.isActive()){
				totalLoanAmount += elDTO.getLoanAmount();
			}//This is added after removing from date and to date of external loan.
			
			//fromDate.setTime(elDTO.getEffectiveFrom());
			// Loan disbursement date should fall in current year
			//if (currentDate.get(Calendar.YEAR) == currentDate
			//		.get(Calendar.YEAR)) {
			//	totalLoanAmount += elDTO.getLoanAmount();
			//}
		}

		// Calculate internal loan amount
		for (LoanAccountDTO laDTO : laDTOs) {
			//fromDate.setTime(laDTO.getCreatedDate());
			// Loan disbursement date should fall in current year
			if (currentDate.get(Calendar.YEAR) == laDTO.getCreatedDate().getYear()) {
				totalLoanAmount += laDTO.getAmount();
			}
		}

		return totalLoanAmount;
	}

	/**
	 * The monthly surplus = The sum of customer income and family income
	 * divided by twelve months
	 * 
	 * @param customer
	 * @return monthly surplus amount
	 */
	private float getMonthlySurplus(float totalIncome) {

		return totalIncome / 12;
	}

	private float getTotalIncome(CustomerDTO customer) {
		float totalIncome = 0;
		OccupationDTO customerOccDTO = customer.getOccupationDTO();
		List<CustomerFamilyOccupationDTO> familyOccDTOs = customer
				.getFamilyOccupationDTOs();

		// Sum customer income and family income
		totalIncome += customerOccDTO.getCalculatedIncome();
		for (CustomerFamilyOccupationDTO cfoDTO : familyOccDTOs) {
			totalIncome += cfoDTO.getCalculatedIncome();
		}
		return totalIncome;
	}

	/**
	 * The Annual Surplus interest ratio is calculated as fifteen(15) % of total
	 * loan amount taken by the customer
	 * 
	 * @param totalLoanAmount
	 * @return annual surplus interest ratio
	 */
	private float getAnnualSurplusInterestRatio(float totalLoanAmount) {
		// 15% of total loan disbursed
		return totalLoanAmount * 15 / 100;
	}

	private int getCashflowAnalisis(CustomerDTO customer, float monthlyRepayment) {
		OccupationDTO occupationDTO = customer.getOccupationDTO();
		if (null != occupationDTO) {
			OccupationSubTypeDTO occupationSubTypeDTO = occupationDTO
					.getOccupationSubTypeDTO();
			if (occupationSubTypeDTO.getIncomeLumpingRequired()) {
				IncomeLumpingDTO incomeLumping = occupationDTO
						.getIncomeLumpingDTO();
				if (null != incomeLumping) {
					int negetiveMonths = 0;
					if (incomeLumping.getJan() < monthlyRepayment)
						negetiveMonths++;
					if (incomeLumping.getFeb() < monthlyRepayment)
						negetiveMonths++;
					if (incomeLumping.getMarch() < monthlyRepayment)
						negetiveMonths++;
					if (incomeLumping.getApril() < monthlyRepayment)
						negetiveMonths++;
					if (incomeLumping.getMay() < monthlyRepayment)
						negetiveMonths++;
					if (incomeLumping.getJune() < monthlyRepayment)
						negetiveMonths++;
					if (incomeLumping.getJuly() < monthlyRepayment)
						negetiveMonths++;
					if (incomeLumping.getAugust() < monthlyRepayment)
						negetiveMonths++;
					if (incomeLumping.getSept() < monthlyRepayment)
						negetiveMonths++;
					if (incomeLumping.getOctober() < monthlyRepayment)
						negetiveMonths++;
					if (incomeLumping.getNov() < monthlyRepayment)
						negetiveMonths++;
					if (incomeLumping.getDecember() < monthlyRepayment)
						negetiveMonths++;

					return negetiveMonths;
				}
			}
		}

		return 3;// Default value
	}

	private float getWorkingMemberRatio(CustomerDTO customer) {
		int numOfMembers = customer.getNumFamilyMembers();
		int numOfDependents = customer.getNumMinDependents()
				+ customer.getNumSeniorDependents();
		float ratio = -1;// Set to a default value.
		if (numOfMembers == 0) {
			return ratio;// Do not calculate return a default value.

		}
		ratio = 100 * (numOfMembers - numOfDependents) / numOfMembers;
		return ratio;// Default value
	}

	private float getCenterAttendancePercentage(CustomerDTO center) {
		// Calculate attendance taken over the last one year
		List<MeetingDTO> meetingDTOs = center.getMeetingDTOs();
		float totalPerctange = 0;
		int totalMeetings = 0;
		float attenancePercentage = 0;
		for (MeetingDTO meetingDTO : meetingDTOs) {
			if (meetingDTO.isDataCaptured()) {
				int totalMembersTmp = meetingDTO.getTotalMembers();
				if (totalMembersTmp == 0) {
					totalPerctange += meetingDTO.getMembersPresent() * 100
							/ totalMembersTmp;
					totalMeetings++;
				}
			}
		}

		// The actual attendance Percentage
		attenancePercentage = totalPerctange / totalMeetings;
		return attenancePercentage;
	}

	private float getAuditScore(CustomerDTO center, Map<String, USER_ROLE> users) {
		List<AuditDTO> auditDTOs = center.getAuditDTOs();
		float rfScore = 0;
		int totalScore = 0;
		int totalWeightage = 0;
		int score = 0;
		// If there are only two audits then take average
		if (auditDTOs.size() == 2) {
			
			for (AuditDTO auditDTO : auditDTOs) {
				if (!auditDTO.isActive()) {
					score += Integer.parseInt(auditDTO.getRatingBucketDTO()
							.getDisplayValue());
				}
			}
			rfScore = score / 2;
		} else {
			for (AuditDTO auditDTO : auditDTOs) {
				score = 0;
				if (!auditDTO.isActive()) {
					score = Integer.parseInt(auditDTO.getRatingBucketDTO()
							.getDisplayValue());
					int weightage = 0;
					if(ApplicationConstants.USER_ROLE.ADMIN == users.get(auditDTO.getUpdatedBy())){
						weightage = 5;
					}else if(ApplicationConstants.USER_ROLE.AUDITOR == users.get(auditDTO.getUpdatedBy())){
						weightage = 5;
					}else if(ApplicationConstants.USER_ROLE.JUNIOR_AUDITOR == users.get(auditDTO.getUpdatedBy())){
						weightage = 3;
					}
					
					totalWeightage += weightage;
					totalScore += score * weightage; 
				}

			}
			totalWeightage = totalWeightage == 0 ? 1 : totalWeightage;
			rfScore = totalScore / totalWeightage;
		}

		return rfScore;
	}

	private RiskFactorDTO getAverageRiskFactor(List<RiskFactorDTO> riskFactors) {
		int sumRiskFactors = 0;
		String riskFactorType = null;
		RiskFactorDTO riskFactor = new RiskFactorDTO();
		if (riskFactors == null || riskFactors.isEmpty()) {
			riskFactor.setRiskFactor(3);// Default to 3
			return riskFactor;
		}

		for (RiskFactorDTO riskFactorDTO : riskFactors) {
			sumRiskFactors += riskFactorDTO.getRiskFactor();
			riskFactorType = riskFactorDTO.getRiskFactorType();
		}

		riskFactor.setRiskFactor(sumRiskFactors / riskFactors.size());
		riskFactor.setRiskFactorType(riskFactorType);
		return riskFactor;
	}

	private List<RiskFactorDTO> getCustomerDependentRiskFactors(
			List<CustomerDTO> customers) {

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
			float totalCustomerLoanAmount = getTotalLoanAmount(customerDTO);
			float totalCustomerIncome = getTotalIncome(customerDTO);
			surplusRatio = getMonthlySurplusRepaymentRatio(totalCustomerIncome,
					totalCustomerLoanAmount);
			loanUtilisationRFs.add(new LoanUtilizationRiskFactor(luDTO
					.getStatedPurposePU()).getRiskFactorDTO());
			loanPurposeUtilisationRFs.add(new LoanPurposeUtilizationRiskFactor(
					luDTO.getIncomeGeneratingPU(), luDTO.getConsumptionPU())
					.getRiskFactorDTO());
			monthlySurplusRepaymentRatioRFs
					.add(new MonthlySurplusRepaymentRatioRiskFactor(
							surplusRatio).getRiskFactorDTO());
		}

		// Calculate loan utilisation
		riskFactors.add(getAverageRiskFactor(loanPurposeUtilisationRFs));
		// Calculate loan purpose utilisation risk factor
		riskFactors.add(getAverageRiskFactor(loanPurposeUtilisationRFs));
		// Calculate Monthly surplus vs repayment risk factor
		riskFactors.add(getAverageRiskFactor(monthlySurplusRepaymentRatioRFs));

		return riskFactors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.conflux.service.IRiskRatingService#calculateVillateRiskRating(java
	 * .util.List)
	 */
	@Override
	public int calculateVillateRiskRating(List<CustomerDTO> centers) {
		// TODO Auto-generated method stub
		
		if(null == centers || centers.isEmpty()) return 3;// Default value
		int ratingPercentage[] = new int[5];

		for (CustomerDTO center : centers) {
			int rating = Integer.parseInt(center.getRiskRating());
			switch (rating) {
			case 1:
				ratingPercentage[0] += 1;
				break;
			case 2:
				ratingPercentage[1] += 1;
				break;
			case 3:
				ratingPercentage[2] += 1;
				break;
			case 4:
				ratingPercentage[3] += 1;
				break;
			case 5:
				ratingPercentage[4] += 1;
				break;
			}
		}

		if (ratingPercentage[0] >= 1)
			return 1;// one or more centers rating below 2
		if (ratingPercentage[1] > 1)
			return 2;// One or more centers rating below 3
		if (ratingPercentage[1] == 1)
			return 3; //Exactly one center below three
		if ((ratingPercentage[0] + ratingPercentage[1] + ratingPercentage[2]) <= 0){
			return 4;
		}
		if(ratingPercentage[4] >= centers.size()) return 5;

		return 3;// Default value
	}

}
