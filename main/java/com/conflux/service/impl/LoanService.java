package com.conflux.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.conflux.dal.bo.Customer;
import com.conflux.dal.bo.LoanAccount;
import com.conflux.dal.bo.LoanPurpose;
import com.conflux.dal.bo.LoanUtilizationCheck;
import com.conflux.service.AbstractBaseService;
import com.conflux.service.ILoanService;
import com.conflux.service.dto.CustomerDTO;
import com.conflux.service.dto.LoanAccountDTO;
import com.conflux.service.dto.LoanUtilizationCheckDTO;

@Service
public class LoanService extends AbstractBaseService implements ILoanService {

	@Override
	public List<CustomerDTO> centersPendingLUC(int officeId) {
		List<Customer> centers = getCustomerDAO().findByPendingLoanUtilChecks(
				officeId);
		List<CustomerDTO> centerDTOs = new ArrayList<CustomerDTO>();
		for (Customer center : centers) {
			centerDTOs.add(getHelper().createCustomerDTO(center));
		}
		return centerDTOs;
	}

	@Override
	public List<LoanUtilizationCheckDTO> getPendingLUCs(int centreId) {
		List<LoanAccount> loanAccounts = getLoanAccountDAO().findAllPendingLUC(
				centreId);
		List<LoanUtilizationCheckDTO> loanUtilizationCheckDTOs = new ArrayList<LoanUtilizationCheckDTO>();
		for (LoanAccount loanAccount : loanAccounts) {
			String firstName = loanAccount.getCustomer().getFirstName();
			String lastName = loanAccount.getCustomer().getLastName();
			if(null == firstName) firstName = " ";
			if(null == lastName) lastName = " ";
			LoanUtilizationCheckDTO loanUtilizationCheckDTO = new LoanUtilizationCheckDTO();
			loanUtilizationCheckDTO.setBranchName(loanAccount.getCustomer()
					.getOffice().getName());
			loanUtilizationCheckDTO.setCentreName(loanAccount.getCustomer()
					.getCustomer().getDisplayName());
			loanUtilizationCheckDTO.setCreatedDate(new Date());
			loanUtilizationCheckDTO.setCustomerName(firstName + " " + lastName );
			loanUtilizationCheckDTO.setLoanAccountId(loanAccount
					.getLoanAccountId());
			loanUtilizationCheckDTO.setLoanAmount(loanAccount.getAmount());
			loanUtilizationCheckDTO.setLoanProductName(loanAccount
					.getLoanProduct().getProductName());
			loanUtilizationCheckDTO.setLoanPurpose(loanAccount.getLoanPurpose()
					.getName());

			loanUtilizationCheckDTOs.add(loanUtilizationCheckDTO);
		}
		return loanUtilizationCheckDTOs;
	}

	@Override
	public void saveLoanUtilizationCheck(
			LoanUtilizationCheckDTO loanUtilizationCheckDTO) {
		LoanUtilizationCheck loanUtilizationCheck = getHelper()
				.createLoanUtilizationCheck(loanUtilizationCheckDTO);
		// get associated loan account
		LoanAccount loanAccount = getLoanAccountDAO().findById(
				loanUtilizationCheckDTO.getLoanAccountId());
		loanUtilizationCheck.setLoanAccount(loanAccount);

		// associate all applicable purposes
		LoanPurpose loanPurpose = getLoanPurposeDAO().findById(
				loanUtilizationCheckDTO.getLoanPurposeId1());
		loanUtilizationCheck.setLoanPurposeByLoanPurposeId(loanPurpose);
		// save loan purpose
		getLoanUtilizationCheckDAO().persist(loanUtilizationCheck);
		loanPurpose.getLoanUtilizationChecksForLoanPurposeId().add(
				loanUtilizationCheck);
		getLoanPurposeDAO().merge(loanPurpose);

		if (loanUtilizationCheckDTO.getLoanPurposeId2() != 0) {
			LoanPurpose loanPurpose1 = getLoanPurposeDAO().findById(
					loanUtilizationCheckDTO.getLoanPurposeId2());
			loanUtilizationCheck.setLoanPurposeByLoanPurposeId1(loanPurpose1);
			loanPurpose1.getLoanUtilizationChecksForLoanPurposeId1().add(
					loanUtilizationCheck);
			getLoanPurposeDAO().merge(loanPurpose1);
			getLoanUtilizationCheckDAO().merge(loanUtilizationCheck);
		}
		if (loanUtilizationCheckDTO.getLoanPurposeId2() != 0) {
			LoanPurpose loanPurpose2 = getLoanPurposeDAO().findById(
					loanUtilizationCheckDTO.getLoanPurposeId2());
			loanUtilizationCheck.setLoanPurposeByLoanPurposeId1(loanPurpose2);
			loanPurpose2.getLoanUtilizationChecksForLoanPurposeId2().add(
					loanUtilizationCheck);
			getLoanPurposeDAO().merge(loanPurpose2);
			getLoanUtilizationCheckDAO().merge(loanUtilizationCheck);
		}

		// set pending plan in Loan account to false
		loanAccount.getLoanUtilizationChecks().add(loanUtilizationCheck);
		loanAccount.setPendingLuc(false);
		getLoanAccountDAO().merge(loanAccount);

	}

	@Override
	public LoanAccountDTO getLoanDetails(int loanAccountId) {
		LoanAccount loanAccount = getLoanAccountDAO().findById(loanAccountId);
		return getHelper().createCompleteLoanAccountDTO(loanAccount);
	}

}
