package com.conflux.service.dto;

import java.util.Date;

public class AssetDTO {

	private int assetId;
	private float multiplierValue;
	private String additionalFieldValue;
	private String createdBy;
	private String updatedBy;
	private Date createdDate;
	private Date updatedDate;
	private float calculatedIncome;
	private float userDefinedIncome;
	private boolean active;
	private int assetTypeId;
	private int assetSubTypeId;
	private AssetTypeDTO assetTypeDTO;
	private AssetSubTypeDTO assetSubTypeDTO;
	private float currentCalculatedIncome;

	public int getAssetTypeId() {
		return assetTypeId;
	}

	public void setAssetTypeId(int assetTypeId) {
		this.assetTypeId = assetTypeId;
	}

	public int getAssetSubTypeId() {
		return assetSubTypeId;
	}

	public void setAssetSubTypeId(int assetSubTypeId) {
		this.assetSubTypeId = assetSubTypeId;
	}

	public AssetTypeDTO getAssetTypeDTO() {
		return assetTypeDTO;
	}

	public void setAssetTypeDTO(AssetTypeDTO assetTypeDTO) {
		this.assetTypeDTO = assetTypeDTO;
	}

	public AssetSubTypeDTO getAssetSubTypeDTO() {
		return assetSubTypeDTO;
	}

	public void setAssetSubTypeDTO(AssetSubTypeDTO assetSubTypeDTO) {
		this.assetSubTypeDTO = assetSubTypeDTO;
	}

	public float getCurrentCalculatedIncome() {
		return currentCalculatedIncome;
	}

	public void setCurrentCalculatedIncome(float currentCalculatedIncome) {
		this.currentCalculatedIncome = currentCalculatedIncome;
	}

	public int getAssetId() {
		return assetId;
	}

	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}

	public float getMultiplierValue() {
		return multiplierValue;
	}

	public void setMultiplierValue(float multiplierValue) {
		this.multiplierValue = multiplierValue;
	}

	public String getAdditionalFieldValue() {
		return additionalFieldValue;
	}

	public void setAdditionalFieldValue(String additionalFieldValue) {
		this.additionalFieldValue = additionalFieldValue;
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

	public float getCalculatedIncome() {
		return calculatedIncome;
	}

	public void setCalculatedIncome(float calculatedIncome) {
		this.calculatedIncome = calculatedIncome;
	}

	public float getUserDefinedIncome() {
		return userDefinedIncome;
	}

	public void setUserDefinedIncome(float userDefinedIncome) {
		this.userDefinedIncome = userDefinedIncome;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
