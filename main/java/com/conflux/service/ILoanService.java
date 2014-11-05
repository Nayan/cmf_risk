package com.conflux.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.conflux.service.dto.CustomerDTO;
import com.conflux.service.dto.LoanAccountDTO;
import com.conflux.service.dto.LoanUtilizationCheckDTO;

public interface ILoanService {

	@Transactional(readOnly = true)
	public List<CustomerDTO> centersPendingLUC(int officeId);

	@Transactional(readOnly = true)
	public List<LoanUtilizationCheckDTO> getPendingLUCs(int centreId);

	@Transactional
	public void saveLoanUtilizationCheck(
			LoanUtilizationCheckDTO loanUtilizationCheckDTO);

	@Transactional(readOnly = true)
	public LoanAccountDTO getLoanDetails(int loanAccountId);
}
