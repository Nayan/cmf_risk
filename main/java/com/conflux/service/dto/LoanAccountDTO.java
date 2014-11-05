package com.conflux.service.dto;

import java.util.Date;
import java.util.List;

public class LoanAccountDTO {

	private int loanAccountId;
	private boolean pendingLuc;
	private boolean updateNotification;
	private float amount;
	private String externalId;
	private Date createdDate;
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;
	private Date targetCompletionDate;

	/** custom properties **/
	private List<LoanUtilizationCheckDTO> loanUtilizationCheckDTOs;
	private String clientName;
	private int clientId;
	private String centerName;
	private int centerId;
	private String officeName;
	private int officeId;
	private String loanOriginalPurpose;
	private String loanProductName;
	private String loanCurrentStatus;
	private int loanOriginalPurposeId;

	public List<LoanUtilizationCheckDTO> getLoanUtilizationCheckDTOs() {
		return loanUtilizationCheckDTOs;
	}

	public void setLoanUtilizationCheckDTOs(
			List<LoanUtilizationCheckDTO> loanUtilizationCheckDTOs) {
		this.loanUtilizationCheckDTOs = loanUtilizationCheckDTOs;
	}

	public int getLoanAccountId() {
		return loanAccountId;
	}

	public void setLoanAccountId(int loanAccountId) {
		this.loanAccountId = loanAccountId;
	}

	public boolean isPendingLuc() {
		return pendingLuc;
	}

	public void setPendingLuc(boolean pendingLuc) {
		this.pendingLuc = pendingLuc;
	}

	public boolean isUpdateNotification() {
		return updateNotification;
	}

	public void setUpdateNotification(boolean updateNotification) {
		this.updateNotification = updateNotification;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getTargetCompletionDate() {
		return targetCompletionDate;
	}

	public void setTargetCompletionDate(Date targetCompletionDate) {
		this.targetCompletionDate = targetCompletionDate;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public int getCenterId() {
		return centerId;
	}

	public void setCenterId(int centerId) {
		this.centerId = centerId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public int getOfficeId() {
		return officeId;
	}

	public void setOfficeId(int officeId) {
		this.officeId = officeId;
	}

	
	public String getLoanOriginalPurpose() {
		return loanOriginalPurpose;
	}

	public void setLoanOriginalPurpose(String loanOriginalPurpose) {
		this.loanOriginalPurpose = loanOriginalPurpose;
	}

	public String getLoanCurrentStatus() {
		return loanCurrentStatus;
	}

	public void setLoanCurrentStatus(String loanCurrentStatus) {
		this.loanCurrentStatus = loanCurrentStatus;
	}

	public String getLoanProductName() {
		return loanProductName;
	}

	public void setLoanProductName(String loanProductName) {
		this.loanProductName = loanProductName;
	}

	/**
	 * @return the loanOriginalPurposeId
	 */
	public int getLoanOriginalPurposeId() {
		return loanOriginalPurposeId;
	}

	/**
	 * @param loanOriginalPurposeId the loanOriginalPurposeId to set
	 */
	public void setLoanOriginalPurposeId(int loanOriginalPurposeId) {
		this.loanOriginalPurposeId = loanOriginalPurposeId;
	}

	
}
