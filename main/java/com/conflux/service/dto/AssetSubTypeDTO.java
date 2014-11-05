package com.conflux.service.dto;

import java.util.Date;

public class AssetSubTypeDTO {

	private int assetSubTypeId;
	private String name;
	private int branchId;
	private boolean additionalFieldPresent;
	private String description;
	private String multiplierFieldLabel;
	private String additionalFieldLabel;
	private float annualIncome;
	private boolean active;
	private String createdBy;
	private String updatedBy;
	private Date createdDate;
	private Date updatedDate;

	public int getAssetSubTypeId() {
		return assetSubTypeId;
	}

	public void setAssetSubTypeId(int assetSubTypeId) {
		this.assetSubTypeId = assetSubTypeId;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAdditionalFieldPresent() {
		return additionalFieldPresent;
	}

	public void setAdditionalFieldPresent(boolean additionalFieldPresent) {
		this.additionalFieldPresent = additionalFieldPresent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMultiplierFieldLabel() {
		return multiplierFieldLabel;
	}

	public void setMultiplierFieldLabel(String multiplierFieldLabel) {
		this.multiplierFieldLabel = multiplierFieldLabel;
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

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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

}
