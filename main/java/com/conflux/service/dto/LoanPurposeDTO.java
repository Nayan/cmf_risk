package com.conflux.service.dto;



public class LoanPurposeDTO     {

	private int loanPurposeId;
	private String name;
	private String description;
	private String externalId;
	private String loanPurposeType;
	public int getLoanPurposeId() {
		return loanPurposeId;
	}
	public void setLoanPurposeId(int loanPurposeId) {
		this.loanPurposeId = loanPurposeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	/**
	 * @return the loanPurposeType
	 */
	public String getLoanPurposeType() {
		return loanPurposeType;
	}
	/**
	 * @param loanPurposeType the loanPurposeType to set
	 */
	public void setLoanPurposeType(String loanPurposeType) {
		this.loanPurposeType = loanPurposeType;
	}
	
} 
