package com.conflux.service.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerDTO {

	private int customerId;
	private String meetingDay;
	private String externalId;
	private String displayName;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String gender;
	private String riskRating;
	private String bmRating;
	private String bmProblemDescription;
	private String bmActionTaken;
	private boolean active;
	private boolean employed;
	private String createdBy;
	private String updatedBy;
	private Date createdDate;
	private Date updatedDate;
	private boolean pendingUpdate;
	private String pendingUpdateNote;
	private int numFamilyMembers;
	private int numMinDependents;
	private int numSeniorDependents;
	private int numUnwellMembers;
	private String unwellMemberHealthDescription;
	private int customerTypeId;
	/** special attributes **/
	private EmployeeDTO loanOfficerDTO = new EmployeeDTO();
	private List<AuditDTO> auditDTOs = new ArrayList<AuditDTO>();
	private List<CustomerDTO> customerDTOs = new ArrayList<CustomerDTO>();
	private List<AssetDTO> incomeGeneratingAssetDTOs = new ArrayList<AssetDTO>();
	private List<RiskRatingHistoryDTO> previousRiskRatingDTOs = new ArrayList<RiskRatingHistoryDTO>();
	private List<LoanAccountDTO> loanAccountDTOs = new ArrayList<LoanAccountDTO>();
	private OccupationDTO occupationDTO = new OccupationDTO();
	private List<CustomerFamilyOccupationDTO> familyOccupationDTOs = new ArrayList<CustomerFamilyOccupationDTO>();
	private List<CustomerFamilyMemberDTO> familyMemberDTOs = new ArrayList<CustomerFamilyMemberDTO>();
	private List<ExternalLoanDTO> externalLoanDTOs = new ArrayList<ExternalLoanDTO>();
	private int noOfFamilyMembers;
	private String unwellMembersConcat;
	private boolean familyMemberSick;
	private int officeId;
	private String officeName;
	private String riskRatingChangeComment;
	private String loanOfficerName;
	private List<MeetingDTO> meetingDTOs = new ArrayList<MeetingDTO>();
	private AddressDTO addressDTO = new AddressDTO();
	
	public EmployeeDTO getLoanOfficerDTO() {
		return loanOfficerDTO;
	}

	public void setLoanOfficerDTO(EmployeeDTO loanOfficerDTO) {
		this.loanOfficerDTO = loanOfficerDTO;
	}

	public List<AuditDTO> getAuditDTOs() {
		return auditDTOs;
	}

	public void setAuditDTOs(List<AuditDTO> auditDTOs) {
		this.auditDTOs = auditDTOs;
	}

	public String getRiskRatingChangeComment() {
		return riskRatingChangeComment;
	}

	public void setRiskRatingChangeComment(String riskRatingChangeComment) {
		this.riskRatingChangeComment = riskRatingChangeComment;
	}

	public List<CustomerDTO> getCustomerDTOs() {
		return customerDTOs;
	}

	public void setCustomerDTOs(List<CustomerDTO> customers) {
		this.customerDTOs = customers;
	}

	public List<RiskRatingHistoryDTO> getPreviousRiskRatingDTOs() {
		return previousRiskRatingDTOs;
	}

	public void setPreviousRiskRatingDTOs(
			List<RiskRatingHistoryDTO> previousRiskRatingDTOs) {
		this.previousRiskRatingDTOs = previousRiskRatingDTOs;
	}

	public List<AssetDTO> getIncomeGeneratingAssetDTOs() {
		return incomeGeneratingAssetDTOs;
	}

	public void setIncomeGeneratingAssetDTOs(
			List<AssetDTO> incomeGeneratingAssetDTOs) {
		this.incomeGeneratingAssetDTOs = incomeGeneratingAssetDTOs;
	}

	public List<LoanAccountDTO> getLoanAccountDTOs() {
		return loanAccountDTOs;
	}

	public void setLoanAccountDTOs(List<LoanAccountDTO> loanAccountDTOs) {
		this.loanAccountDTOs = loanAccountDTOs;
	}

	public OccupationDTO getOccupationDTO() {
		return occupationDTO;
	}

	public void setOccupationDTO(OccupationDTO occupationDTO) {
		this.occupationDTO = occupationDTO;
	}

	public List<CustomerFamilyOccupationDTO> getFamilyOccupationDTOs() {
		return familyOccupationDTOs;
	}

	public void setFamilyOccupationDTOs(
			List<CustomerFamilyOccupationDTO> familyOccupationDTOs) {
		this.familyOccupationDTOs = familyOccupationDTOs;
	}

	public List<CustomerFamilyMemberDTO> getFamilyMemberDTOs() {
		return familyMemberDTOs;
	}

	public void setFamilyMemberDTOs(
			List<CustomerFamilyMemberDTO> familyMemberDTOs) {
		this.familyMemberDTOs = familyMemberDTOs;
	}

	public List<ExternalLoanDTO> getExternalLoanDTOs() {
		return externalLoanDTOs;
	}

	public void setExternalLoanDTOs(List<ExternalLoanDTO> externalLoanDTOs) {
		this.externalLoanDTOs = externalLoanDTOs;
	}

	public int getNoOfFamilyMembers() {
		return noOfFamilyMembers;
	}

	public void setNoOfFamilyMembers(int noOfFamilyMembers) {
		this.noOfFamilyMembers = noOfFamilyMembers;
	}

	public String getUnwellMembersConcat() {
		return unwellMembersConcat;
	}

	public void setUnwellMembersConcat(String unwellMembersConcat) {
		this.unwellMembersConcat = unwellMembersConcat;
	}

	public boolean isFamilyMemberSick() {
		return familyMemberSick;
	}

	public void setFamilyMemberSick(boolean familyMemberSick) {
		this.familyMemberSick = familyMemberSick;
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

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getMeetingDay() {
		return meetingDay;
	}

	public void setMeetingDay(String meetingDay) {
		this.meetingDay = meetingDay;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRiskRating() {
		return riskRating;
	}

	public void setRiskRating(String riskRating) {
		this.riskRating = riskRating;
	}
	
	/**
	 * @return the bmRating
	 */
	public String getBmRating() {
		return bmRating;
	}

	/**
	 * @param bmRating the bmRating to set
	 */
	public void setBmRating(String bmRating) {
		this.bmRating = bmRating;
	}
	
	public String getBmProblemDescription() {
		bmProblemDescription = (bmProblemDescription == null) ? "--" : bmProblemDescription;
		return bmProblemDescription;
	}

	public void setBmProblemDescription(String bmProblemDescription) {
		this.bmProblemDescription = bmProblemDescription;
	}

	public String getBmActionTaken() {
		bmActionTaken = (bmActionTaken == null) ? "--" : bmActionTaken;
		return bmActionTaken;
	}

	public void setBmActionTaken(String bmActionTaken) {
		this.bmActionTaken = bmActionTaken;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isEmployed() {
		return employed;
	}

	public void setEmployed(boolean employed) {
		this.employed = employed;
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

	public boolean isPendingUpdate() {
		return pendingUpdate;
	}

	public void setPendingUpdate(boolean pendingUpdate) {
		this.pendingUpdate = pendingUpdate;
	}

	public String getPendingUpdateNote() {
		return pendingUpdateNote;
	}

	public void setPendingUpdateNote(String pendingUpdateNote) {
		this.pendingUpdateNote = pendingUpdateNote;
	}

	public String getLoanOfficerName() {
		return loanOfficerName;
	}

	public void setLoanOfficerName(String loanOfficerName) {
		this.loanOfficerName = loanOfficerName;
	}

	public int getNumFamilyMembers() {
		return numFamilyMembers;
	}

	public void setNumFamilyMembers(int numFamilyMembers) {
		this.numFamilyMembers = numFamilyMembers;
	}

	public int getNumMinDependents() {
		return numMinDependents;
	}

	public void setNumMinDependents(int numMinDependents) {
		this.numMinDependents = numMinDependents;
	}

	public int getNumSeniorDependents() {
		return numSeniorDependents;
	}

	public void setNumSeniorDependents(int numSeniorDependents) {
		this.numSeniorDependents = numSeniorDependents;
	}
	
	
	/**
	 * @return the numUnwellMembers
	 */
	public int getNumUnwellMembers() {
		return numUnwellMembers;
	}

	/**
	 * @param numUnwellMembers the numUnwellMembers to set
	 */
	public void setNumUnwellMembers(int numUnwellMembers) {
		this.numUnwellMembers = numUnwellMembers;
	}

	
	/**
	 * @return the unwellMemberHealthDescription
	 */
	public String getUnwellMemberHealthDescription() {
		return unwellMemberHealthDescription;
	}

	/**
	 * @param unwellMemberHealthDescription the unwellMemberHealthDescription to set
	 */
	public void setUnwellMemberHealthDescription(
			String unwellMemberHealthDescription) {
		this.unwellMemberHealthDescription = unwellMemberHealthDescription;
	}

	/**
	 * @return the meetingDTOs
	 */
	public List<MeetingDTO> getMeetingDTOs() {
		return meetingDTOs;
	}

	/**
	 * @param meetingDTOs the meetingDTOs to set
	 */
	public void setMeetingDTOs(List<MeetingDTO> meetingDTOs) {
		this.meetingDTOs = meetingDTOs;
	}

	/**
	 * @return the customerTypeId
	 */
	public int getCustomerTypeId() {
		return customerTypeId;
	}

	/**
	 * @param customerTypeId the customerTypeId to set
	 */
	public void setCustomerTypeId(int customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	/**
	 * @return the addressDTO
	 */
	public AddressDTO getAddressDTO() {
		return addressDTO;
	}

	/**
	 * @param addressDTO the addressDTO to set
	 */
	public void setAddressDTO(AddressDTO addressDTO) {
		this.addressDTO = addressDTO;
	}
	
}
