package com.conflux.service.dto;

import java.util.Date;

public class OccupationDTO {

	private int occupationId;
	private float multiplierValue;
	private String additionalFieldValue;
	private String createdBy;
	private String updatedBy;
	private Date createdDate;
	private Date updatedDate;
	private float calculatedIncome;
	private float userDefinedIncome;
	private boolean multiplierPresent;
	

	/** custom properties **/
	private int occupationTypeId;
	private int occupationSubTypeId;
	private OccupationTypeDTO occupationTypeDTO = new OccupationTypeDTO();
	private OccupationSubTypeDTO occupationSubTypeDTO = new OccupationSubTypeDTO();
	private float currentCalculatedIncome;
	private IncomeLumpingDTO incomeLumpingDTO = new IncomeLumpingDTO();

	public int getOccupationTypeId() {
		return occupationTypeId;
	}

	public void setOccupationTypeId(int occupationTypeId) {
		this.occupationTypeId = occupationTypeId;
	}

	public int getOccupationSubTypeId() {
		return occupationSubTypeId;
	}

	public void setOccupationSubTypeId(int occupationSubTypeId) {
		this.occupationSubTypeId = occupationSubTypeId;
	}

	public OccupationTypeDTO getOccupationTypeDTO() {
		return occupationTypeDTO;
	}

	public void setOccupationTypeDTO(OccupationTypeDTO occupationTypeDTO) {
		this.occupationTypeDTO = occupationTypeDTO;
	}

	public OccupationSubTypeDTO getOccupationSubTypeDTO() {
		return occupationSubTypeDTO;
	}

	public void setOccupationSubTypeDTO(
			OccupationSubTypeDTO occupationSubTypeDTO) {
		this.occupationSubTypeDTO = occupationSubTypeDTO;
	}

	public float getCurrentCalculatedIncome() {
		return currentCalculatedIncome;
	}

	public void setCurrentCalculatedIncome(float currentCalculatedIncome) {
		this.currentCalculatedIncome = currentCalculatedIncome;
	}

	public int getOccupationId() {
		return occupationId;
	}

	public void setOccupationId(int occupationId) {
		this.occupationId = occupationId;
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

	public boolean isMultiplierPresent() {
		return multiplierPresent;
	}

	public void setMultiplierPresent(boolean multiplierPresent) {
		this.multiplierPresent = multiplierPresent;
	}

	public IncomeLumpingDTO getIncomeLumpingDTO() {
		return incomeLumpingDTO;
	}

	public void setIncomeLumpingDTO(IncomeLumpingDTO incomeLumpingDTO) {
		this.incomeLumpingDTO = incomeLumpingDTO;
	}
	
}
