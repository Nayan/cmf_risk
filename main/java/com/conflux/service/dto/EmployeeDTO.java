package com.conflux.service.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeDTO {

	private int employeeId;
	private String employeeRole;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String externalId;
	private boolean active;
	private String mobileNumber;
	private String governmentId;
	private String gender;
	private Date dateOfBirth;
	private String createdBy;
	private String updatedBy;
	private Date createdDate;
	private Date updatedDate;
	private String bloodGroup;
	private String contactName;
	private String contactPhNo;
	private String educationLevel;
	private String university;

	// additional properties
	private int officeId;
	private String officeName;
	private String supervisorName;
	private int supervisorId;
	private AddressDTO permanentAddress = new AddressDTO();
	private AddressDTO currentAddress = new AddressDTO();
	private List<AuditDTO> auditDTOs = new ArrayList<AuditDTO>();

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getOfficeId() {
		return officeId;
	}

	public void setOfficeId(int officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getEmployeeRole() {
		return employeeRole;
	}

	public void setEmployeeRole(String employeeRole) {
		this.employeeRole = employeeRole;
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

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getGovernmentId() {
		return governmentId;
	}

	public void setGovernmentId(String governmentId) {
		this.governmentId = governmentId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhNo() {
		return contactPhNo;
	}

	public void setContactPhNo(String contactPhNo) {
		this.contactPhNo = contactPhNo;
	}

	public String getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public int getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(int supervisorId) {
		this.supervisorId = supervisorId;
	}

	public AddressDTO getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(AddressDTO permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public AddressDTO getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(AddressDTO currentAddress) {
		this.currentAddress = currentAddress;
	}

	public List<AuditDTO> getAuditDTOs() {
		return auditDTOs;
	}

	public void setAuditDTOs(List<AuditDTO> auditDTOs) {
		this.auditDTOs = auditDTOs;
	}

}
