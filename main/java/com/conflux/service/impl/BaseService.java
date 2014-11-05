package com.conflux.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.conflux.dal.bo.AssetType;
import com.conflux.dal.bo.Employee;
import com.conflux.dal.bo.ExternalOrganization;
import com.conflux.dal.bo.LoanPurpose;
import com.conflux.dal.bo.LoanStatus;
import com.conflux.dal.bo.OccupationType;
import com.conflux.dal.bo.Office;
import com.conflux.dal.bo.OrgEntity;
import com.conflux.dal.bo.RelationshipType;
import com.conflux.service.AbstractBaseService;
import com.conflux.service.IBaseService;
import com.conflux.service.dto.AssetTypeDTO;
import com.conflux.service.dto.EmployeeDTO;
import com.conflux.service.dto.ExternalOrganizationDTO;
import com.conflux.service.dto.LoanPurposeDTO;
import com.conflux.service.dto.LoanStatusDTO;
import com.conflux.service.dto.OccupationTypeDTO;
import com.conflux.service.dto.OfficeDTO;
import com.conflux.service.dto.OrgEntityDTO;
import com.conflux.service.dto.RelationshipTypeDTO;

@Service
public class BaseService extends AbstractBaseService implements IBaseService {

	@Override
	public List<OfficeDTO> getAllOffices() {
		List<Office> offices = getOfficeDAO().findAll();
		List<OfficeDTO> officeDTOs = new ArrayList<OfficeDTO>();
		for (Office office : offices) {
			officeDTOs.add(getHelper().createOfficeDTO(office));
		}
		return officeDTOs;
	}

	@Override
	public List<AssetTypeDTO> getAllAssetTypes() {
		List<AssetType> assetTypes = getAssetTypeDAO().findAll();
		List<AssetTypeDTO> assetTypeDTOs = new ArrayList<AssetTypeDTO>();
		for (AssetType assetType : assetTypes) {
			assetTypeDTOs.add(getHelper().createAssetTypeDTO(assetType));
		}
		return assetTypeDTOs;
	}

	@Override
	public List<OccupationTypeDTO> getAllOccupationTypes() {
		List<OccupationType> occupationTypes = getOccupationTypeDAO().findAll();
		List<OccupationTypeDTO> occupationTypeDTOs = new ArrayList<OccupationTypeDTO>();
		for (OccupationType occupationType : occupationTypes) {
			occupationTypeDTOs.add(getHelper().createOccupationTypeDTO(
					occupationType));
		}
		return occupationTypeDTOs;
	}

	@Override
	public List<RelationshipTypeDTO> getAllRelationshipTypes() {
		List<RelationshipType> relationshipTypes = getRelationshipTypeDAO()
				.findAll();
		List<RelationshipTypeDTO> relationshipTypeDTOs = new ArrayList<RelationshipTypeDTO>();
		for (RelationshipType relationshipType : relationshipTypes) {
			relationshipTypeDTOs.add(getHelper().createRelationshipTypeDTO(
					relationshipType));
		}
		return relationshipTypeDTOs;
	}

	@Override
	public List<ExternalOrganizationDTO> getAllExternalOrganizations() {
		List<ExternalOrganization> externalOrganizations = getExternalOrganizationDAO()
				.findAll();
		List<ExternalOrganizationDTO> externalOrganizationDTOs = new ArrayList<ExternalOrganizationDTO>();
		for (ExternalOrganization externalOrganization : externalOrganizations) {
			externalOrganizationDTOs.add(getHelper()
					.createExternalOrganizationDTO(externalOrganization));
		}
		return externalOrganizationDTOs;
	}

	@Override
	public List<OrgEntityDTO> getAllOrganizationalEntities() {
		List<OrgEntity> orgEntitys = getOrgEntityDAO().findAll();
		List<OrgEntityDTO> orgEntityDTOs = new ArrayList<OrgEntityDTO>();
		for (OrgEntity orgEntity : orgEntitys) {
			orgEntityDTOs.add(getHelper().createOrgEntityDTO(orgEntity));
		}
		return orgEntityDTOs;
	}

	@Override
	public List<LoanPurposeDTO> getAllLoanPurposes() {
		List<LoanPurpose> loanPurposes = getLoanPurposeDAO().findAll();
		List<LoanPurposeDTO> loanPurposeDTOs = new ArrayList<LoanPurposeDTO>();
		for (LoanPurpose loanPurpose : loanPurposes) {
			loanPurposeDTOs.add(getHelper().createLoanPurposeDTO(loanPurpose));
		}
		return loanPurposeDTOs;
	}

	@Override
	public List<EmployeeDTO> getAllEmployeesInScope(int officeId) {
		List<Employee> employees = getEmployeeDAO().find(officeId);
		List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();
		for (Employee employee : employees) {
			employeeDTOs.add(getHelper().createEmployeeDTO(employee));
		}
		return employeeDTOs;
	}

	@Override
	public EmployeeDTO getEmployeeById(int employeeId) {
		Employee employee = getEmployeeDAO().findById(employeeId);
		return getHelper().createEmployeeDTO(employee);
	}

	@Override
	public List<LoanStatusDTO> getAllLoanStatuses() {
		List<LoanStatus> loanStatuses = getLoanStatusDAO().findAll();
		List<LoanStatusDTO> loanStatuseDTOs = new ArrayList<LoanStatusDTO>();
		for (LoanStatus loanStatus : loanStatuses) {
			loanStatuseDTOs.add(getHelper().createLoanStatusDTO(loanStatus));
		}
		
		return loanStatuseDTOs;
	}
	
}
