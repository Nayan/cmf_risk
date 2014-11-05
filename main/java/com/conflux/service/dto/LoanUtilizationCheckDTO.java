package com.conflux.service.dto;

import java.util.Date;

public class LoanUtilizationCheckDTO {

	private int loanUtilizationCheckId;
	private float utilizationPercentage1;
	private float utilizationPercentage2;
	private float utilizationPercentage3;

	private String createdBy;
	private Date createdDate;

	/** custom properties **/
	private String branchName;
	private String centreName;
	private String customerName;
	private String loanProductName;
	private Float loanAmount;
	private String loanPurpose;
	private int loanAccountId;

	private int loanPurposeId1;
	private int loanPurposeId2;
	private int loanPurposeId3;
	private String loanPurposeName1;
	private String loanPurposeName2;
	private String loanPurposeName3;
	private String loanPurposeType1;
	private String loanPurposeType2;
	private String loanPurposeType3;
	
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCentreName() {
		return centreName;
	}

	public int getLoanPurposeId1() {
		return loanPurposeId1;
	}

	public void setLoanPurposeId1(int loanPurposeId1) {
		this.loanPurposeId1 = loanPurposeId1;
	}

	public int getLoanPurposeId2() {
		return loanPurposeId2;
	}

	public void setLoanPurposeId2(int loanPurposeId2) {
		this.loanPurposeId2 = loanPurposeId2;
	}

	public int getLoanPurposeId3() {
		return loanPurposeId3;
	}

	public void setLoanPurposeId3(int loanPurposeId3) {
		this.loanPurposeId3 = loanPurposeId3;
	}

	public String getLoanPurposeName1() {
		return loanPurposeName1;
	}

	public void setLoanPurposeName1(String loanPurposeName1) {
		this.loanPurposeName1 = loanPurposeName1;
	}

	public String getLoanPurposeName2() {
		return loanPurposeName2;
	}

	public void setLoanPurposeName2(String loanPurposeName2) {
		this.loanPurposeName2 = loanPurposeName2;
	}

	public String getLoanPurposeName3() {
		return loanPurposeName3;
	}

	public void setLoanPurposeName3(String loanPurposeName3) {
		this.loanPurposeName3 = loanPurposeName3;
	}

	public void setCentreName(String centreName) {
		this.centreName = centreName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getLoanProductName() {
		return loanProductName;
	}

	public void setLoanProductName(String loanProductName) {
		this.loanProductName = loanProductName;
	}

	public Float getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Float loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getLoanPurpose() {
		return loanPurpose;
	}

	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}

	public int getLoanAccountId() {
		return loanAccountId;
	}

	public void setLoanAccountId(int loanAccountId) {
		this.loanAccountId = loanAccountId;
	}

	public int getLoanUtilizationCheckId() {
		return loanUtilizationCheckId;
	}

	public void setLoanUtilizationCheckId(int loanUtilizationCheckId) {
		this.loanUtilizationCheckId = loanUtilizationCheckId;
	}

	public float getUtilizationPercentage1() {
		return utilizationPercentage1;
	}

	public void setUtilizationPercentage1(float utilizationPercentage1) {
		this.utilizationPercentage1 = utilizationPercentage1;
	}

	public float getUtilizationPercentage2() {
		return utilizationPercentage2;
	}

	public void setUtilizationPercentage2(float utilizationPercentage2) {
		this.utilizationPercentage2 = utilizationPercentage2;
	}

	public float getUtilizationPercentage3() {
		return utilizationPercentage3;
	}

	public void setUtilizationPercentage3(float utilizationPercentage3) {
		this.utilizationPercentage3 = utilizationPercentage3;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the loanPurposeType1
	 */
	public String getLoanPurposeType1() {
		return loanPurposeType1;
	}

	/**
	 * @param loanPurposeType1 the loanPurposeType1 to set
	 */
	public void setLoanPurposeType1(String loanPurposeType1) {
		this.loanPurposeType1 = loanPurposeType1;
	}

	/**
	 * @return the loanPurposeType2
	 */
	public String getLoanPurposeType2() {
		return loanPurposeType2;
	}

	/**
	 * @param loanPurposeType2 the loanPurposeType2 to set
	 */
	public void setLoanPurposeType2(String loanPurposeType2) {
		this.loanPurposeType2 = loanPurposeType2;
	}

	/**
	 * @return the loanPurposeType3
	 */
	public String getLoanPurposeType3() {
		return loanPurposeType3;
	}

	/**
	 * @param loanPurposeType3 the loanPurposeType3 to set
	 */
	public void setLoanPurposeType3(String loanPurposeType3) {
		this.loanPurposeType3 = loanPurposeType3;
	}

}
