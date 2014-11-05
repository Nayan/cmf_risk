package com.conflux.service.dto;

import java.util.Date;

public class CustomerFamilyMemberDTO {

	private int customerFamilyMemberId;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String gender;
	private boolean unwell;
	private String healthDescription;
	private boolean active;
	private String createdBy;
	private String updatedBy;
	private Date createdDate;
	private Date updatedDate;

	// custom fields
	private int relationshipId;
	private String relationship;

	public int getCustomerFamilyMemberId() {
		return customerFamilyMemberId;
	}

	public void setCustomerFamilyMemberId(int customerFamilyMemberId) {
		this.customerFamilyMemberId = customerFamilyMemberId;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isUnwell() {
		return unwell;
	}

	public void setUnwell(boolean unwell) {
		this.unwell = unwell;
	}

	public String getHealthDescription() {
		return healthDescription;
	}

	public void setHealthDescription(String healthDescription) {
		this.healthDescription = healthDescription;
	}

	public int getRelationshipId() {
		return relationshipId;
	}

	public void setRelationshipId(int relationshipId) {
		this.relationshipId = relationshipId;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

}
