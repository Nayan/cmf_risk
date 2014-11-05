package com.conflux.web;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;

import com.conflux.common.ApplicationConstants;
import com.conflux.service.IBaseService;
import com.conflux.service.dto.AssetTypeDTO;
import com.conflux.service.dto.ExternalOrganizationDTO;
import com.conflux.service.dto.LoanPurposeDTO;
import com.conflux.service.dto.LoanStatusDTO;
import com.conflux.service.dto.OccupationTypeDTO;
import com.conflux.service.dto.OfficeDTO;
import com.conflux.service.dto.OrgEntityDTO;
import com.conflux.service.dto.RelationshipTypeDTO;

@ManagedBean(name = "appBean")
@ApplicationScoped
public class ApplicationManagedBean implements Serializable {

	@ManagedProperty(value = "#{baseService}")
	private IBaseService baseService;

	private static final long serialVersionUID = 1L;

	private Map<String, Integer> assetTypes = new LinkedHashMap<String, Integer>();
	private Map<String, Integer> occupationTypes = new LinkedHashMap<String, Integer>();
	private Map<String, String> sexes = new LinkedHashMap<String, String>();
	private Map<String, Integer> relationships = new LinkedHashMap<String, Integer>();
	private Map<String, Integer> externalOrganizations = new LinkedHashMap<String, Integer>();
	private Map<String, String> riskRatings = new LinkedHashMap<String, String>();
	private Map<String, Integer> organizationalEntities = new LinkedHashMap<String, Integer>();
	private Map<String, Integer> loanPurposes = new LinkedHashMap<String, Integer>();
	private Map<String, Integer> offices = new LinkedHashMap<String, Integer>();
	private Map<String, Integer> roles = new LinkedHashMap<String, Integer>();
	private Map<String, Integer> loanStatuses = new LinkedHashMap<String, Integer>();

	@PostConstruct
	public void init() {
		/** Initialize pentaho reporting engine **/
		ClassicEngineBoot.getInstance().start();

		/** get all asset types **/
		List<AssetTypeDTO> assetTypeDTOs = getBaseService().getAllAssetTypes();
		for (AssetTypeDTO assetTypeDTO : assetTypeDTOs) {
			assetTypes.put(assetTypeDTO.getName(),
					assetTypeDTO.getAssetTypeId());
		}

		/** get all occupation types **/
		List<OccupationTypeDTO> occupationTypeDTOs = getBaseService()
				.getAllOccupationTypes();
		for (OccupationTypeDTO occupationTypeDTO : occupationTypeDTOs) {
			occupationTypes.put(occupationTypeDTO.getName(),
					occupationTypeDTO.getOccupationTypeId());
		}

		/** get all relationships **/
		List<RelationshipTypeDTO> relationshipTypeDTOs = getBaseService()
				.getAllRelationshipTypes();
		for (RelationshipTypeDTO relationshipTypeDTO : relationshipTypeDTOs) {
			relationships.put(relationshipTypeDTO.getName(),
					relationshipTypeDTO.getRelationshipTypeId());
		}

		/** get sex dropdown **/
		for (ApplicationConstants.SEX sex : ApplicationConstants.SEX.values()) {
			sexes.put(sex.toString(), sex.toString());
		}

		/** get risk rating dropdown **/
		for (ApplicationConstants.RISK_RATING rating : ApplicationConstants.RISK_RATING
				.values()) {
			riskRatings.put(rating.toString(), rating.toString());
		}

		/** get all external organizations **/
		List<ExternalOrganizationDTO> externalOrganizationDTOs = getBaseService()
				.getAllExternalOrganizations();
		for (ExternalOrganizationDTO externalOrganizationDTO : externalOrganizationDTOs) {
			externalOrganizations.put(externalOrganizationDTO.getName(),
					externalOrganizationDTO.getExternalOrganizationId());
		}

		/** get all loan puroses **/
		List<LoanPurposeDTO> loanPurposeDTOs = getBaseService()
				.getAllLoanPurposes();
		for (LoanPurposeDTO loanPurposeDTO : loanPurposeDTOs) {
			loanPurposes.put(loanPurposeDTO.getName(),
					loanPurposeDTO.getLoanPurposeId());
		}

		/** get all organizational entities **/
		List<OrgEntityDTO> orgEntityDTOs = getBaseService()
				.getAllOrganizationalEntities();
		for (OrgEntityDTO orgEntityDTO : orgEntityDTOs) {
			organizationalEntities.put(orgEntityDTO.getName(),
					orgEntityDTO.getOrgEntityId());
		}

		/** get office List **/
		List<OfficeDTO> officesList = getBaseService().getAllOffices();
		for (OfficeDTO officeDTO : officesList) {
			offices.put(officeDTO.getName(), officeDTO.getOfficeId());
		}

		/** get roles list **/
		for (ApplicationConstants.USER_ROLE userRole : ApplicationConstants.USER_ROLE
				.values()) {
			roles.put(userRole.toString(), userRole.getRoleId());
		}
		
		/** get all Loan Statuses**/
		List<LoanStatusDTO> loanStatusDTOs = getBaseService()
				.getAllLoanStatuses();
		for (LoanStatusDTO loanStatusDTO : loanStatusDTOs) {
			loanStatuses.put(loanStatusDTO.getName(),
					loanStatusDTO.getLoanStatusId());
		}
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public Map<String, Integer> getAssetTypes() {
		return assetTypes;
	}

	public void setAssetTypes(Map<String, Integer> assetTypes) {
		this.assetTypes = assetTypes;
	}

	public Map<String, Integer> getOccupationTypes() {
		return occupationTypes;
	}

	public void setOccupationTypes(Map<String, Integer> occupationTypes) {
		this.occupationTypes = occupationTypes;
	}

	public Map<String, String> getSexes() {
		return sexes;
	}

	public void setSexes(Map<String, String> sexes) {
		this.sexes = sexes;
	}

	public Map<String, Integer> getRelationships() {
		return relationships;
	}

	public void setRelationships(Map<String, Integer> relationships) {
		this.relationships = relationships;
	}

	public Map<String, Integer> getExternalOrganizations() {
		return externalOrganizations;
	}

	public void setExternalOrganizations(
			Map<String, Integer> externalOrganizations) {
		this.externalOrganizations = externalOrganizations;
	}

	public Map<String, String> getRiskRatings() {
		return riskRatings;
	}

	public void setRiskRatings(Map<String, String> riskRatings) {
		this.riskRatings = riskRatings;
	}

	public Map<String, Integer> getOrganizationalEntities() {
		return organizationalEntities;
	}

	public void setOrganizationalEntities(
			Map<String, Integer> organizationalEntities) {
		this.organizationalEntities = organizationalEntities;
	}

	public Map<String, Integer> getLoanPurposes() {
		return loanPurposes;
	}

	public void setLoanPurposes(Map<String, Integer> loanPurposes) {
		this.loanPurposes = loanPurposes;
	}

	public Map<String, Integer> getOffices() {
		return offices;
	}

	public void setOffices(Map<String, Integer> offices) {
		this.offices = offices;
	}

	public Map<String, Integer> getRoles() {
		return roles;
	}

	public void setRoles(Map<String, Integer> roles) {
		this.roles = roles;
	}

	public Map<String, Integer> getLoanStatuses() {
		return loanStatuses;
	}

	public void setLoanStatuses(Map<String, Integer> loanStatuses) {
		this.loanStatuses = loanStatuses;
	}

}