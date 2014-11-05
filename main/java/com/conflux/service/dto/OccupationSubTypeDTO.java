package com.conflux.service.dto;

import java.util.Date;

public class OccupationSubTypeDTO {

	private int occupationSubTypeId;
	private String name;
	private String description;
	private boolean multiplierPresent;
	private boolean additionalFieldPresent;
	private String multiplierLabel;
	private String additionalFieldLabel;
	private float annualIncome;
	private boolean active;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private boolean incomeLumpingRequired;
	private String stabilityFactor;

	/** custom properties **/
	private int branchId;

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public int getOccupationSubTypeId() {
		return occupationSubTypeId;
	}

	public void setOccupationSubTypeId(int occupationSubTypeId) {
		this.occupationSubTypeId = occupationSubTypeId;
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

	public boolean isMultiplierPresent() {
		return multiplierPresent;
	}

	public void setMultiplierPresent(boolean multiplierPresent) {
		this.multiplierPresent = multiplierPresent;
	}

	public boolean isAdditionalFieldPresent() {
		return additionalFieldPresent;
	}

	public void setAdditionalFieldPresent(boolean additionalFieldPresent) {
		this.additionalFieldPresent = additionalFieldPresent;
	}

	public String getMultiplierLabel() {
		return multiplierLabel;
	}

	public void setMultiplierLabel(String multiplierLabel) {
		this.multiplierLabel = multiplierLabel;
	}

	public String getAdditionalFieldLabel() {
		return additionalFieldLabel;
	}

	public void setAdditionalFieldLabel(String additionalFieldLabel) {
		this.additionalFieldLabel = additionalFieldLabel;
	}

	public float getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(float annualIncome) {
		this.annualIncome = annualIncome;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public boolean getIncomeLumpingRequired() {
		return incomeLumpingRequired;
	}

	public void setIncomeLumpingRequired(boolean incomeLumpingRequired) {
		this.incomeLumpingRequired = incomeLumpingRequired;
	}

	public String getStabilityFactor() {
		return stabilityFactor;
	}

	public void setStabilityFactor(String stabilityFactor) {
		this.stabilityFactor = stabilityFactor;
	}

}
