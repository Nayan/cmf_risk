package com.conflux.web;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.conflux.common.ApplicationConstants;
import com.conflux.common.ApplicationUtils;
import com.conflux.service.dto.AssetDTO;
import com.conflux.service.dto.AssetSubTypeDTO;
import com.conflux.service.dto.CustomerDTO;
import com.conflux.service.dto.CustomerFamilyMemberDTO;
import com.conflux.service.dto.CustomerFamilyOccupationDTO;
import com.conflux.service.dto.ExternalLoanDTO;
import com.conflux.service.dto.IncomeLumpingDTO;
import com.conflux.service.dto.OccupationDTO;
import com.conflux.service.dto.OccupationSubTypeDTO;

@ManagedBean(name = "customerBean")
@SessionScoped
public class CustomerManagedBean extends AbstractManagedBean implements
		Serializable {
	private static final long serialVersionUID = -8204585501500254352L;

	private CustomerDTO customer;
	private AssetDTO asset = new AssetDTO();
	private CustomerFamilyMemberDTO familyMember = new CustomerFamilyMemberDTO();
	private CustomerFamilyOccupationDTO familyOccupation = new CustomerFamilyOccupationDTO();
	private ExternalLoanDTO externalLoan = new ExternalLoanDTO();
	private Map<Integer, AssetSubTypeDTO> assetSubTypesMap;
	private Map<String, Integer> assetSubTypes;
	private Map<Integer, OccupationSubTypeDTO> occupationSubTypesMap;
	private Map<String, Integer> occupationSubTypes;
	private boolean editClientFlag = false;
	private boolean editAssetsFlag = false;
	private boolean editFamilyDetailsFlag = false;
	private boolean editFamilyOccupationDetailsFlag = false;
	private boolean editLoansFromOtherMFIFlag = false;
	private boolean editClientSaveFlag = false;
	private boolean editAssetsSaveFlag = false;
	private boolean editFamilyDetailsSaveFlag = false;
	private boolean editFamilyOccupationDetailsSaveFlag = false;
	private boolean editLoansFromOtherMFISaveFlag = false;
	private List<CustomerFamilyMemberDTO> sourceFamilyMembers;
	private List<CustomerDTO> customers;
	private List<CustomerDTO> centers;
	private int branchId;
	private String centerName;
	private String branchName;
	
	@ManagedProperty(value = "#{appBean}")
	private ApplicationManagedBean appBean;
	
	@ManagedProperty(value = "#{riskRatingBean}")
	private RiskRatingManagedBean riskRatingBean;
	

	public String viewClient(int clientId) {
		resetAllVariables();
		customer = getCustomerService().getCustomerById(clientId);
		if(customer.isPendingUpdate()){
			addFacesInfo("! Client Pending for Update.");
		}
		return "view_client";
	}

	public void editClient() {
		editClientFlag = true;
		handleCustomerOccupationTypeChange();
	}

	public void cancelEditClient() {
		editClientFlag = false;
		editClientSaveFlag = false;

		// refresh client occupation
		OccupationDTO occupationDTO = getCustomerService()
				.getCustomerOccupation(customer.getCustomerId());
		if (occupationDTO == null) {
			customer.setOccupationDTO(occupationDTO);
		} else {
			customer.setOccupationDTO(occupationDTO);
		}
		refreshOccupationRelatedVariables();
	}

	public void handleOccupationTypeChange(int occupationTypeId) {
		List<OccupationSubTypeDTO> occupationSubTypesList = getOccupationTypeService()
				.getOccupationSubTypes(customer.getOfficeId(), occupationTypeId);

		refreshOccupationRelatedVariables();

		for (OccupationSubTypeDTO occupationSubTypeDTO : occupationSubTypesList) {
			if(occupationSubTypeDTO.isActive()){
			occupationSubTypes.put(occupationSubTypeDTO.getName(),
					occupationSubTypeDTO.getOccupationSubTypeId());
			occupationSubTypesMap.put(
					occupationSubTypeDTO.getOccupationSubTypeId(),
					occupationSubTypeDTO);
			}
		}

	}

	public void handleFamilyOccupationTypeChange() {
		int occupationTypeId = familyOccupation.getOccupationTypeId();
		handleOccupationTypeChange(occupationTypeId);
	}

	public void handleCustomerOccupationTypeChange() {
		int occupationTypeId = customer.getOccupationDTO()
				.getOccupationTypeId();
		handleOccupationTypeChange(occupationTypeId);
		editAssetsSaveFlag = true;
	}

	public void calculateOccupationValue() {
		editAssetsSaveFlag = true;
		int occupationSubTypeId = customer.getOccupationDTO()
				.getOccupationSubTypeId();

		OccupationSubTypeDTO selectedOccupationSubTypeDTO = occupationSubTypesMap
				.get(occupationSubTypeId);

		customer.getOccupationDTO().setOccupationSubTypeDTO(
				selectedOccupationSubTypeDTO);

		if (selectedOccupationSubTypeDTO.isMultiplierPresent()) {
			float calculatedIncome = customer.getOccupationDTO()
					.getMultiplierValue()
					* selectedOccupationSubTypeDTO.getAnnualIncome();
			customer.getOccupationDTO().setCalculatedIncome(calculatedIncome);
			customer.getOccupationDTO().setUserDefinedIncome(
					customer.getOccupationDTO().getCalculatedIncome());
		} else {
			customer.getOccupationDTO().setCalculatedIncome(
					selectedOccupationSubTypeDTO.getAnnualIncome());
			customer.getOccupationDTO().setUserDefinedIncome(
					customer.getOccupationDTO().getCalculatedIncome());
		}

	}

	private void refreshOccupationRelatedVariables() {
		// refresh variables
		occupationSubTypes = new LinkedHashMap<String, Integer>();
		occupationSubTypesMap = new LinkedHashMap<Integer, OccupationSubTypeDTO>();
	}

	public void saveOrUpdateOccupation() {
		calculateOccupationValue();
		validateCustomerIncomeLumping();

		// set audit properties
		customer.getOccupationDTO().setUpdatedBy(
				getLoginBean().getUserT().getUsername());

		OccupationDTO occupationDTO = getCustomerService()
				.saveOrUpdateCustomerOccupation(customer);
		customer.setOccupationDTO(occupationDTO);
		refreshOccupationRelatedVariables();
		editClientFlag = false;
		editClientSaveFlag = false;
	}

	private void validateCustomerIncomeLumping() {
		OccupationDTO occupationDTO = customer.getOccupationDTO();
		IncomeLumpingDTO incomeLumpingDTO = occupationDTO.getIncomeLumpingDTO();
		int occupationSubTypeId = occupationDTO.getOccupationSubTypeId();

		OccupationSubTypeDTO selectedOccupationSubTypeDTO = occupationSubTypesMap
				.get(occupationSubTypeId);
		if (selectedOccupationSubTypeDTO.getIncomeLumpingRequired() == true) {
			if (occupationDTO.getCalculatedIncome() < incomeLumpingDTO
					.getTotalLumping()) {
				addFacesError("Total of monthly income is greater than Annual Income");
			}
		}
	}

	public void editAssets() {
		editAssetsFlag = true;
	}

	public void cancelEditAssets() {
		editAssetsFlag = false;
		List<AssetDTO> customerAssetsDTO = getCustomerService()
				.getCustomerAssets(customer.getCustomerId());
		customer.setIncomeGeneratingAssetDTOs(customerAssetsDTO);
	}

	public void handleAssetTypeChange() {
		int assetTypeId = asset.getAssetTypeId();

		List<AssetSubTypeDTO> assetSubTypesList = getAssetTypeService()
				.getAssetSubTypes(customer.getOfficeId(), assetTypeId);

		assetSubTypes = new LinkedHashMap<String, Integer>();
		assetSubTypesMap = new LinkedHashMap<Integer, AssetSubTypeDTO>();

		for (AssetSubTypeDTO assetSubTypeDTO : assetSubTypesList) {
			assetSubTypes.put(assetSubTypeDTO.getName(),
					assetSubTypeDTO.getAssetSubTypeId());
			assetSubTypesMap.put(assetSubTypeDTO.getAssetSubTypeId(),
					assetSubTypeDTO);
		}

		asset.setAssetSubTypeDTO(null);

	}

	public void calculateAssetValue() {
		setSelectedAssetSubType();
		populateCalculatedIncomeForAsset(asset);
		asset.setUserDefinedIncome(asset.getCalculatedIncome());

	}

	public void calculateAssetValue(int index) {
		List<AssetDTO> assetDTOs = customer.getIncomeGeneratingAssetDTOs();
		if (index >= 0 && assetDTOs != null && !assetDTOs.isEmpty()) {
			AssetDTO assetDto = assetDTOs.get(index);
			populateCalculatedIncomeForAsset(assetDto);
			assetDto.setUserDefinedIncome(assetDto.getCalculatedIncome());
		}
	}

	private void populateCalculatedIncomeForAsset(AssetDTO assetDto) {

		AssetSubTypeDTO selectedAssetSubTypeDTO = assetDto.getAssetSubTypeDTO();

		if (selectedAssetSubTypeDTO != null) {
			float calculatedIncome = assetDto.getMultiplierValue()
					* selectedAssetSubTypeDTO.getAnnualIncome();
			assetDto.setCalculatedIncome(calculatedIncome);
		}
	}

	public void addAsset() {
		setSelectedAssetSubType();
		populateCalculatedIncomeForAsset(asset);
		if (asset.getUserDefinedIncome() > asset.getCalculatedIncome()) {
			addFacesError("User Defined income is greater than system calculated Income");
			return;
		}
		if (asset.getUserDefinedIncome() == 0) {
			asset.setUserDefinedIncome(asset.getCalculatedIncome());
		}
		asset.setAssetTypeDTO(getAssetTypeService().getAssetTypeById(
				asset.getAssetTypeId()));
		customer.getIncomeGeneratingAssetDTOs().add(asset);
		asset = new AssetDTO();
	}

	private void setSelectedAssetSubType() {
		AssetSubTypeDTO selectedAssetSubTypeDTO = assetSubTypesMap.get(asset
				.getAssetSubTypeId());
		asset.setAssetSubTypeDTO(selectedAssetSubTypeDTO);
	}

	public void calculateUpdatedAssetValue(int listIndex) {
		AssetDTO selectedAsset = customer.getIncomeGeneratingAssetDTOs().get(
				listIndex);
		AssetSubTypeDTO selectedAssetSubTypeDTO = selectedAsset
				.getAssetSubTypeDTO();
		float calculatedIncome = selectedAsset.getMultiplierValue()
				* selectedAssetSubTypeDTO.getAnnualIncome();
		selectedAsset.setCalculatedIncome(calculatedIncome);
		selectedAsset.setUserDefinedIncome(calculatedIncome);
	}

	public void removeAsset(int listIndex) {
		customer.getIncomeGeneratingAssetDTOs().remove(listIndex);
	}

	public void saveIncomeGeneratingAssets() {
		List<AssetDTO> updatedIncomeGeneratingAssets = getCustomerService()
				.saveOrUpdateCustomerAssets(customer.getCustomerId(),
						customer.getIncomeGeneratingAssetDTOs(),
						getLoginBean().getUserT().getUsername());
		customer.setIncomeGeneratingAssetDTOs(updatedIncomeGeneratingAssets);
		editAssetsFlag = false;
	}

	public void editClientFamilyDetails() {
		editFamilyDetailsFlag = true;
	}

	public void cancelEditClientFamilyDetails() {
		customer.setFamilyMemberDTOs(getCustomerService()
				.getCustomerFamilyDetails(customer.getCustomerId()));
		editFamilyDetailsFlag = false;
	}

	public void addFamilyMember() {
		int relationshipId = familyMember.getRelationshipId();
		String relationship = ApplicationUtils.getKeyByValue(
				appBean.getRelationships(), relationshipId);
		familyMember.setRelationship(relationship);
		familyMember.setActive(true);
		customer.getFamilyMemberDTOs().add(familyMember);
		familyMember = new CustomerFamilyMemberDTO();
	}

	public void removeFamilyMember(int listIndex) {
		customer.getFamilyMemberDTOs().remove(listIndex);
	}

	public void saveFamilyMembers() {
		List<CustomerFamilyMemberDTO> customerFamilyMemberDTOs = customer
				.getFamilyMemberDTOs();
		boolean familyMemberSick = false;
		String unwellMembersConcat = "";
		for (CustomerFamilyMemberDTO customerFamilyMemberDTO : customerFamilyMemberDTOs) {
			// remove unnecessary health description
			if (!customerFamilyMemberDTO.isUnwell()) {
				customerFamilyMemberDTO.setHealthDescription("");
			} else {
				unwellMembersConcat = customerFamilyMemberDTO.getFirstName()
						+ " " + customerFamilyMemberDTO.getLastName() + ","
						+ unwellMembersConcat;
				familyMemberSick = true;
			}
		}
		List<CustomerFamilyMemberDTO> updatedCustomerFamilyDetails = getCustomerService()
				.saveOrUpdateCustomerFamilyDetails(customer,
						customerFamilyMemberDTOs,
						getLoginBean().getUserT().getUsername());
		customer.setFamilyMemberDTOs(updatedCustomerFamilyDetails);
		customer.setNoOfFamilyMembers(customerFamilyMemberDTOs.size());
		customer.setFamilyMemberSick(familyMemberSick);
		customer.setUnwellMembersConcat(unwellMembersConcat);

		/*** switch back to view mode ***/
		editFamilyDetailsFlag = false;
	}

	public void editFamilyOccupations() {
		editFamilyOccupationDetailsFlag = true;
	}

	public void cancelEditFamilyOccupations() {
		customer.setFamilyOccupationDTOs(getCustomerService()
				.getCustomerFamilyOccupations(customer.getCustomerId()));
		editFamilyOccupationDetailsFlag = false;
	}

	public void calculateFamilyOccupationValue() {
		setFamilyOccupationSubType();// To set occupation sub type based on
										// selected occupation type
		populateCalculatedIncomeForFamilyOccupation(familyOccupation);
		familyOccupation.setUserDefinedIncome(customer.getOccupationDTO()
				.getCalculatedIncome());
	}

	public void calculateFamilyOccupationValue(int index) {
		List<CustomerFamilyOccupationDTO> familyOccupationDTOs = customer
				.getFamilyOccupationDTOs();
		if (index >= 0 && null != familyOccupationDTOs
				&& !familyOccupationDTOs.isEmpty()) {
			CustomerFamilyOccupationDTO familyOccupationDto = familyOccupationDTOs
					.get(index);
			populateCalculatedIncomeForFamilyOccupation(familyOccupationDto);
			familyOccupationDto.setUserDefinedIncome(familyOccupationDto
					.getCalculatedIncome());
		}

	}

	private void populateCalculatedIncomeForFamilyOccupation(
			CustomerFamilyOccupationDTO familyOccupationDto) {

		OccupationSubTypeDTO selectedOccupationSubTypeDTO = familyOccupationDto
				.getOccupationSubTypeDTO();

		if (selectedOccupationSubTypeDTO != null) {

			if (selectedOccupationSubTypeDTO.isMultiplierPresent()) {
				float calculatedIncome = familyOccupationDto
						.getMultiplierValue()
						* selectedOccupationSubTypeDTO.getAnnualIncome();
				familyOccupationDto.setCalculatedIncome(calculatedIncome);

			} else {
				familyOccupationDto
						.setCalculatedIncome(selectedOccupationSubTypeDTO
								.getAnnualIncome());
			}
		}
	}

	public void addFamilyOccupation() {
		setFamilyOccupationSubType();// To set occupation sub type based on
										// selected occupation type
		populateCalculatedIncomeForFamilyOccupation(familyOccupation);
		if (familyOccupation.getUserDefinedIncome() > familyOccupation
				.getCalculatedIncome()) {
			addFacesError("User Defined income is greater than system calculated Income");
			return;
		}
		if (familyOccupation.getUserDefinedIncome() == 0) {
			familyOccupation.setUserDefinedIncome(familyOccupation
					.getCalculatedIncome());
		}
		familyOccupation.setOccupationTypeDTO(getOccupationTypeService()
				.getOccupationTypeById(familyOccupation.getOccupationTypeId()));
		customer.getFamilyOccupationDTOs().add(familyOccupation);
		familyOccupation = new CustomerFamilyOccupationDTO();
	}

	private void setFamilyOccupationSubType() {
		int occupationSubTypeId = familyOccupation.getOccupationSubTypeId();
		OccupationSubTypeDTO selectedOccupationSubTypeDTO = occupationSubTypesMap
				.get(occupationSubTypeId);
		familyOccupation.setMultiplierPresent(selectedOccupationSubTypeDTO
				.isMultiplierPresent());
		familyOccupation.setOccupationSubTypeDTO(selectedOccupationSubTypeDTO);
	}

	public void removeFamilyOccupation(int listIndex) {
		customer.getFamilyOccupationDTOs().remove(listIndex);
	}

	public void saveOrUpdateFamilyOccupations() {
		List<CustomerFamilyOccupationDTO> familyOccupationDTOs = customer
				.getFamilyOccupationDTOs();
		IncomeLumpingDTO incomeLumpingDTO = null;
		OccupationSubTypeDTO selectedOccupationSubTypeDTO = null;

		// verify there are no errors
		for (CustomerFamilyOccupationDTO familyOccupationDTO : familyOccupationDTOs) {
			if (familyOccupationDTO.isMultiplierPresent() == true) {
				float calculatedIncome = familyOccupationDTO
						.getMultiplierValue()
						* familyOccupationDTO.getOccupationSubTypeDTO()
								.getAnnualIncome();
				familyOccupationDTO.setCalculatedIncome(calculatedIncome);
				if (familyOccupationDTO.getUserDefinedIncome() > familyOccupationDTO
						.getCalculatedIncome()) {
					addFacesError("User Defined income is greater than system calculated Income for a family occupation type");
					return;
				}
			}
			if (familyOccupationDTO.getUserDefinedIncome() == 0) {
				familyOccupationDTO.setUserDefinedIncome(familyOccupationDTO
						.getCalculatedIncome());
			}
			selectedOccupationSubTypeDTO = familyOccupationDTO
					.getOccupationSubTypeDTO();
			if (selectedOccupationSubTypeDTO.getIncomeLumpingRequired() == true) {
				incomeLumpingDTO = familyOccupationDTO.getIncomeLumpingDTO();
				if (selectedOccupationSubTypeDTO.getAnnualIncome() < incomeLumpingDTO
						.getTotalLumping()) {
					addFacesError("Total monthly income is greater than Annual Income for a family occupation type");
					return;
				}
			}

		}
		List<CustomerFamilyOccupationDTO> updatedCustomerFamilyOccupations = getCustomerService()
				.saveOrUpdateCustomerFamilyOccupations(
						customer.getCustomerId(), familyOccupationDTOs,
						getLoginBean().getUserT().getUsername());
		customer.setFamilyOccupationDTOs(updatedCustomerFamilyOccupations);
		editFamilyOccupationDetailsFlag = false;
	}

	public void editExternalLoans() {
		editLoansFromOtherMFIFlag = true;
	}

	public void cancelEditExternalLoans() {
		customer.setExternalLoanDTOs(getCustomerService()
				.getCustomerExternalLoans(customer.getCustomerId()));
		editLoansFromOtherMFIFlag = false;
	}

	public void addExternalLoan() {
		int externalOrganizationId = externalLoan.getExternalOrganizationId();
		String externalOrganizationName = ApplicationUtils.getKeyByValue(
				appBean.getExternalOrganizations(), externalOrganizationId);
		externalLoan.setExternalOrganizationName(externalOrganizationName);
		externalLoan.setActive(true);
		customer.getExternalLoanDTOs().add(externalLoan);
		externalLoan = new ExternalLoanDTO();
	}

	public void removeExternalLoan(int listIndex) {
		customer.getExternalLoanDTOs().remove(listIndex);
	}

	public void saveExternalLoans() {
		List<ExternalLoanDTO> updatedCustomerExternalLoans = getCustomerService()
				.saveOrUpdateCustomerExternalLoans(customer.getCustomerId(),
						customer.getExternalLoanDTOs(),
						getLoginBean().getUserT().getUsername());
		customer.setExternalLoanDTOs(updatedCustomerExternalLoans);
		editLoansFromOtherMFIFlag = false;
	}

	public void populateCentersWithPendingUpdate(){
		centers = getCustomerService().getCentersWithPendingUpdate(branchId);
	}
	
	public String populateCustomersPendingUpdate(int centerId) {
		customers = getCustomerService().getCustomersPendingUpdate(centerId);
		// set center and branch names
		CustomerDTO centerDTO = getCustomerService().getCenterById(centerId);
		centerName = centerDTO.getDisplayName();
		branchName = centerDTO.getOfficeName();
		return ApplicationConstants.GENERIC_NAVIGATION.VIEW_CLIENTS_PENDING_UPDATE.toString();
	}

	/*public String pendingUpdates(){
		branchId = 0;
	}*/
	
	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}
	
	

	/**
	 * @return the centers
	 */
	public List<CustomerDTO> getCenters() {
		return centers;
	}

	/**
	 * @param centers the centers to set
	 */
	public void setCenters(List<CustomerDTO> centers) {
		this.centers = centers;
	}

	public AssetDTO getAsset() {
		return asset;
	}

	public void setAsset(AssetDTO asset) {
		this.asset = asset;
	}

	public CustomerFamilyMemberDTO getFamilyMember() {
		return familyMember;
	}

	public void setFamilyMember(CustomerFamilyMemberDTO familyMember) {
		this.familyMember = familyMember;
	}

	public CustomerFamilyOccupationDTO getFamilyOccupation() {
		return familyOccupation;
	}

	public void setFamilyOccupation(CustomerFamilyOccupationDTO familyOccupation) {
		this.familyOccupation = familyOccupation;
	}

	public ExternalLoanDTO getExternalLoan() {
		return externalLoan;
	}

	public void setExternalLoan(ExternalLoanDTO externalLoan) {
		this.externalLoan = externalLoan;
	}

	public Map<Integer, AssetSubTypeDTO> getAssetSubTypesMap() {
		return assetSubTypesMap;
	}

	public void setAssetSubTypesMap(
			Map<Integer, AssetSubTypeDTO> assetSubTypesMap) {
		this.assetSubTypesMap = assetSubTypesMap;
	}

	public Map<String, Integer> getAssetSubTypes() {
		return assetSubTypes;
	}

	public void setAssetSubTypes(Map<String, Integer> assetSubTypes) {
		this.assetSubTypes = assetSubTypes;
	}

	public Map<Integer, OccupationSubTypeDTO> getOccupationSubTypesMap() {
		return occupationSubTypesMap;
	}

	public void setOccupationSubTypesMap(
			Map<Integer, OccupationSubTypeDTO> occupationSubTypesMap) {
		this.occupationSubTypesMap = occupationSubTypesMap;
	}

	public Map<String, Integer> getOccupationSubTypes() {
		return occupationSubTypes;
	}

	public void setOccupationSubTypes(Map<String, Integer> occupationSubTypes) {
		this.occupationSubTypes = occupationSubTypes;
	}

	public boolean isEditClientFlag() {
		return editClientFlag;
	}

	public void setEditClientFlag(boolean editClientFlag) {
		this.editClientFlag = editClientFlag;
	}

	public boolean isEditAssetsFlag() {
		return editAssetsFlag;
	}

	public void setEditAssetsFlag(boolean editAssetsFlag) {
		this.editAssetsFlag = editAssetsFlag;
	}

	public boolean isEditFamilyDetailsFlag() {
		return editFamilyDetailsFlag;
	}

	public void setEditFamilyDetailsFlag(boolean editFamilyDetailsFlag) {
		this.editFamilyDetailsFlag = editFamilyDetailsFlag;
	}

	public boolean isEditFamilyOccupationDetailsFlag() {
		return editFamilyOccupationDetailsFlag;
	}

	public void setEditFamilyOccupationDetailsFlag(
			boolean editFamilyOccupationDetailsFlag) {
		this.editFamilyOccupationDetailsFlag = editFamilyOccupationDetailsFlag;
	}

	public boolean isEditLoansFromOtherMFIFlag() {
		return editLoansFromOtherMFIFlag;
	}

	public void setEditLoansFromOtherMFIFlag(boolean editLoansFromOtherMFIFlag) {
		this.editLoansFromOtherMFIFlag = editLoansFromOtherMFIFlag;
	}

	public List<CustomerFamilyMemberDTO> getSourceFamilyMembers() {
		return sourceFamilyMembers;
	}

	public void setSourceFamilyMembers(
			List<CustomerFamilyMemberDTO> sourceFamilyMembers) {
		this.sourceFamilyMembers = sourceFamilyMembers;
	}

	public List<CustomerDTO> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerDTO> customers) {
		this.customers = customers;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	
	/**
	 * @return the centerName
	 */
	public String getCenterName() {
		return centerName;
	}

	/**
	 * @param centerName the centerName to set
	 */
	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public ApplicationManagedBean getAppBean() {
		return appBean;
	}

	public void setAppBean(ApplicationManagedBean appBean) {
		this.appBean = appBean;
	}

	
	/**
	 * @return the riskRatingBean
	 */
	public RiskRatingManagedBean getRiskRatingBean() {
		return riskRatingBean;
	}

	/**
	 * @param riskRatingBean the riskRatingBean to set
	 */
	public void setRiskRatingBean(RiskRatingManagedBean riskRatingBean) {
		this.riskRatingBean = riskRatingBean;
	}

	private void resetAllVariables() {
		customer = null;
		asset = new AssetDTO();
		familyMember = new CustomerFamilyMemberDTO();
		familyOccupation = new CustomerFamilyOccupationDTO();
		externalLoan = new ExternalLoanDTO();
		assetSubTypesMap = null;
		assetSubTypes = null;
		occupationSubTypesMap = null;
		occupationSubTypes = null;
		editClientFlag = false;
		editAssetsFlag = false;
		editFamilyDetailsFlag = false;
		editFamilyOccupationDetailsFlag = false;
		editLoansFromOtherMFIFlag = false;
		editClientSaveFlag = false;
		editAssetsSaveFlag = false;
		editFamilyDetailsSaveFlag = false;
		editFamilyOccupationDetailsSaveFlag = false;
		editLoansFromOtherMFISaveFlag = false;
		sourceFamilyMembers = null;
		customers = null;
		branchId = 0;
		centers = null;
		centerName = null;
		branchName = null;
	}

	public boolean isEditClientSaveFlag() {
		return editClientSaveFlag;
	}

	public void setEditClientSaveFlag(boolean editClientSaveFlag) {
		this.editClientSaveFlag = editClientSaveFlag;
	}

	public boolean isEditAssetsSaveFlag() {
		return editAssetsSaveFlag;
	}

	public void setEditAssetsSaveFlag(boolean editAssetsSaveFlag) {
		this.editAssetsSaveFlag = editAssetsSaveFlag;
	}

	public boolean isEditFamilyDetailsSaveFlag() {
		return editFamilyDetailsSaveFlag;
	}

	public void setEditFamilyDetailsSaveFlag(boolean editFamilyDetailsSaveFlag) {
		this.editFamilyDetailsSaveFlag = editFamilyDetailsSaveFlag;
	}

	public boolean isEditFamilyOccupationDetailsSaveFlag() {
		return editFamilyOccupationDetailsSaveFlag;
	}

	public void setEditFamilyOccupationDetailsSaveFlag(
			boolean editFamilyOccupationDetailsSaveFlag) {
		this.editFamilyOccupationDetailsSaveFlag = editFamilyOccupationDetailsSaveFlag;
	}

	public boolean isEditLoansFromOtherMFISaveFlag() {
		return editLoansFromOtherMFISaveFlag;
	}

	public void setEditLoansFromOtherMFISaveFlag(
			boolean editLoansFromOtherMFISaveFlag) {
		this.editLoansFromOtherMFISaveFlag = editLoansFromOtherMFISaveFlag;
	}

	public void updateRiskRating() {
		getCustomerService().updateNewRating(customer.getCustomerId(),
				customer.getRiskRating(),
				customer.getRiskRatingChangeComment(),
				getLoginBean().getUserT().getUsername());
		customer = getCustomerService().getCustomerById(customer.getCustomerId());
	}
	
	public void updatePendingStatus() {
		getCustomerService().updatePendingStatus(customer.getCustomerId(),
				getLoginBean().getUserT().getUsername());
		customer = getCustomerService().getCustomerById(customer.getCustomerId());
		
	}

	public void calculateCustomerRiskRating(int customerId) {
		riskRatingBean.calculateCustomerRiskRating(customerId);
		customer = getCustomerService().getCustomerById(customer.getCustomerId());
	}
}
