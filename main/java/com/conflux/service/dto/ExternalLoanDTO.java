package com.conflux.service.dto;

import java.util.Date;

public class ExternalLoanDTO {

	private int externalLoanId;
	private String otherOrgName;
	private String status;
	private float loanAmount;
	private float pendingAmount;
	private Date effectiveFrom;
	private Date effectiveTo;
	private Date createdDate;
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;
	private boolean active;

	// Other properties
	private int externalOrganizationId;
	private String externalOrganizationName;

	public int getExternalLoanId() {
		return externalLoanId;
	}

	public void setExternalLoanId(int externalLoanId) {
		this.externalLoanId = externalLoanId;
	}

	public String getOtherOrgName() {
		return otherOrgName;
	}

	public void setOtherOrgName(String otherOrgName) {
		this.otherOrgName = otherOrgName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public float getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(float loanAmount) {
		this.loanAmount = loanAmount;
	}

	public float getPendingAmount() {
		return pendingAmount;
	}

	public void setPendingAmount(float pendingAmount) {
		this.pendingAmount = pendingAmount;
	}

	public Date getEffectiveFrom() {
		return effectiveFrom;
	}

	public void setEffectiveFrom(Date effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	public Date getEffectiveTo() {
		return effectiveTo;
	}

	public void setEffectiveTo(Date effectiveTo) {
		this.effectiveTo = effectiveTo;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getExternalOrganizationId() {
		return externalOrganizationId;
	}

	public void setExternalOrganizationId(int externalOrganizationId) {
		this.externalOrganizationId = externalOrganizationId;
	}

	public String getExternalOrganizationName() {
		return externalOrganizationName;
	}

	public void setExternalOrganizationName(String externalOrganizationName) {
		this.externalOrganizationName = externalOrganizationName;
	}

}
