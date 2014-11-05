package com.conflux.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.conflux.service.dto.AssetTypeDTO;
import com.conflux.service.dto.EmployeeDTO;
import com.conflux.service.dto.ExternalOrganizationDTO;
import com.conflux.service.dto.LoanPurposeDTO;
import com.conflux.service.dto.LoanStatusDTO;
import com.conflux.service.dto.OccupationTypeDTO;
import com.conflux.service.dto.OfficeDTO;
import com.conflux.service.dto.OrgEntityDTO;
import com.conflux.service.dto.RelationshipTypeDTO;

public interface IBaseService {

	@Transactional(readOnly = true)
	public List<OfficeDTO> getAllOffices();

	@Transactional(readOnly = true)
	public List<AssetTypeDTO> getAllAssetTypes();

	@Transactional(readOnly = true)
	public List<OccupationTypeDTO> getAllOccupationTypes();

	@Transactional(readOnly = true)
	public List<RelationshipTypeDTO> getAllRelationshipTypes();

	@Transactional(readOnly = true)
	public List<ExternalOrganizationDTO> getAllExternalOrganizations();

	@Transactional(readOnly = true)
	public List<OrgEntityDTO> getAllOrganizationalEntities();

	@Transactional(readOnly = true)
	public List<LoanPurposeDTO> getAllLoanPurposes();

	@Transactional(readOnly = true)
	public List<EmployeeDTO> getAllEmployeesInScope(int officeId);

	@Transactional(readOnly = true)
	public EmployeeDTO getEmployeeById(int employeeId);
	
	@Transactional(readOnly = true)
	public List<LoanStatusDTO> getAllLoanStatuses();
}
