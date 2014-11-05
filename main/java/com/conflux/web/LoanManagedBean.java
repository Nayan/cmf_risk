package com.conflux.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.RowEditEvent;

import com.conflux.common.ApplicationConstants;
import com.conflux.service.dto.CustomerDTO;
import com.conflux.service.dto.LoanAccountDTO;
import com.conflux.service.dto.LoanUtilizationCheckDTO;

@ManagedBean(name = "loanBean")
@SessionScoped
public class LoanManagedBean extends AbstractManagedBean implements
		Serializable {
	private static final long serialVersionUID = -8204585501500254352L;

	@ManagedProperty(value = "#{appBean}")
	private ApplicationManagedBean appBean;

	private int branchId;
	private List<CustomerDTO> centers;
	private List<LoanUtilizationCheckDTO> loanUtilizationChecks;
	private String centerName;
	private String branchName;
	private LoanAccountDTO loanAccount;

	private void reset(){
		centerName = "";
		branchName = "";
		loanAccount = new LoanAccountDTO();
		centers = new ArrayList<CustomerDTO>(0);
		loanUtilizationChecks = new ArrayList<LoanUtilizationCheckDTO>(0);
	}
	
	public void populateCentersWithPendingLUCs() {
		reset();
		centers = getLoanService().centersPendingLUC(branchId);
	}

	public String pendingLucs(){
		reset();
		branchId = 0;
		return ApplicationConstants.GENERIC_NAVIGATION.PENDING_LUCS.toString();
	}
		
	public String populatePendingLUCs(int centerId) {
		// get all pending LUC
		Integer intCentreId = new Integer(centerId);
		loanUtilizationChecks = getLoanService().getPendingLUCs(intCentreId);
		// set center and branch names
		CustomerDTO centerDTO = getCustomerService().getCenterById(intCentreId);
		centerName = centerDTO.getDisplayName();
		branchName = centerDTO.getOfficeName();
		return ApplicationConstants.GENERIC_NAVIGATION.SAVE_LUCS.toString();
	}
	
	public List<String> completeLoanPurposeName(String query) {
		Map<String, Integer> loanPurposes = appBean.getLoanPurposes();
		Set<String> keySet = loanPurposes.keySet();
		List<String> matchingLoanPurposes = new ArrayList<String>();
		for (String key : keySet) {
			if (key.toLowerCase().contains(query.toLowerCase())) {
				matchingLoanPurposes.add(key);
			}
		}
		return matchingLoanPurposes;
	}

	public void saveLoanUtilizationCheck(RowEditEvent event) {
		LoanUtilizationCheckDTO loanUtilizationCheckDTO = (LoanUtilizationCheckDTO) event
				.getObject();

		// validate atleast one purpose is filled up
		if (!(loanUtilizationCheckDTO.getLoanPurposeId1() != 0
				|| loanUtilizationCheckDTO.getLoanPurposeId2() != 0 || loanUtilizationCheckDTO
				.getLoanPurposeId3() != 0)) {
			addFacesError("Atleast one loan purpose must be entered");
			return;
		} else if (!(loanUtilizationCheckDTO.getUtilizationPercentage1() != 0f
				|| loanUtilizationCheckDTO.getUtilizationPercentage2() != 0f || loanUtilizationCheckDTO
				.getUtilizationPercentage3() != 0f)) {
			addFacesError("Please enter the loan utilization Amount for the selected loan purpose");
			return;
		} else if ((loanUtilizationCheckDTO.getUtilizationPercentage1()
				+ loanUtilizationCheckDTO.getUtilizationPercentage2() + loanUtilizationCheckDTO
				.getUtilizationPercentage3()) != loanUtilizationCheckDTO.getLoanAmount()) {
			addFacesError("Please ensure that the sum of amount utilizations adds to a loan amount " + loanUtilizationCheckDTO.getLoanAmount());
			return;
		}

		// set audit trail
		loanUtilizationCheckDTO.setCreatedBy(getLoginBean().getUserT()
				.getUsername());

		getLoanService().saveLoanUtilizationCheck(loanUtilizationCheckDTO);
		
		//remove saved LUC
		loanUtilizationChecks.remove(loanUtilizationCheckDTO);
	}

	public void viewLoanAccount(String loanAccountId) {
		loanAccount = getLoanService().getLoanDetails(
				Integer.parseInt(loanAccountId));
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public List<CustomerDTO> getCenters() {
		return centers;
	}

	public void setCenters(List<CustomerDTO> centers) {
		this.centers = centers;
	}

	public List<LoanUtilizationCheckDTO> getLoanUtilizationChecks() {
		return loanUtilizationChecks;
	}

	public void setLoanUtilizationChecks(
			List<LoanUtilizationCheckDTO> loanUtilizationChecks) {
		this.loanUtilizationChecks = loanUtilizationChecks;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public LoanAccountDTO getLoanAccount() {
		return loanAccount;
	}

	public void setLoanAccount(LoanAccountDTO loanAccount) {
		this.loanAccount = loanAccount;
	}

	public ApplicationManagedBean getAppBean() {
		return appBean;
	}

	public void setAppBean(ApplicationManagedBean appBean) {
		this.appBean = appBean;
	}

}
