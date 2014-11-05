package com.conflux.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conflux.common.ApplicationConstants.ENTITY_TYPE;
import com.conflux.common.ApplicationConstants.USER_ROLE;
import com.conflux.dal.bo.Address;
import com.conflux.dal.bo.Asset;
import com.conflux.dal.bo.AssetSubType;
import com.conflux.dal.bo.AssetType;
import com.conflux.dal.bo.Audit;
import com.conflux.dal.bo.AuditQuestion;
import com.conflux.dal.bo.AuditType;
import com.conflux.dal.bo.AuditTypeScoring;
import com.conflux.dal.bo.AuditTypeScoringId;
import com.conflux.dal.bo.Customer;
import com.conflux.dal.bo.CustomerFamilyMember;
import com.conflux.dal.bo.CustomerFamilyOccupation;
import com.conflux.dal.bo.CustomerType;
import com.conflux.dal.bo.Employee;
import com.conflux.dal.bo.ExternalLoan;
import com.conflux.dal.bo.ExternalOrganization;
import com.conflux.dal.bo.IncomeLumping;
import com.conflux.dal.bo.LoanAccount;
import com.conflux.dal.bo.LoanProduct;
import com.conflux.dal.bo.LoanPurpose;
import com.conflux.dal.bo.LoanStatus;
import com.conflux.dal.bo.LoanUtilizationCheck;
import com.conflux.dal.bo.MailSender;
import com.conflux.dal.bo.Meeting;
import com.conflux.dal.bo.Occupation;
import com.conflux.dal.bo.OccupationSubType;
import com.conflux.dal.bo.OccupationType;
import com.conflux.dal.bo.Office;
import com.conflux.dal.bo.OfficeType;
import com.conflux.dal.bo.OrgEntity;
import com.conflux.dal.bo.PortalUser;
import com.conflux.dal.bo.Question;
import com.conflux.dal.bo.RatingBucket;
import com.conflux.dal.bo.RelationshipType;
import com.conflux.dal.bo.RiskRatingHistory;
import com.conflux.dal.bo.UserRole;
import com.conflux.dal.dao.IOfficeDAO;
import com.conflux.dal.dao.IUserRoleDAO;
import com.conflux.service.dto.AddressDTO;
import com.conflux.service.dto.AssetDTO;
import com.conflux.service.dto.AssetSubTypeDTO;
import com.conflux.service.dto.AssetTypeDTO;
import com.conflux.service.dto.AuditDTO;
import com.conflux.service.dto.AuditQuestionDTO;
import com.conflux.service.dto.AuditTypeDTO;
import com.conflux.service.dto.AuditTypeScoringDTO;
import com.conflux.service.dto.CustomerDTO;
import com.conflux.service.dto.CustomerFamilyMemberDTO;
import com.conflux.service.dto.CustomerFamilyOccupationDTO;
import com.conflux.service.dto.CustomerTypeDTO;
import com.conflux.service.dto.EmployeeDTO;
import com.conflux.service.dto.ExternalLoanDTO;
import com.conflux.service.dto.ExternalOrganizationDTO;
import com.conflux.service.dto.IncomeLumpingDTO;
import com.conflux.service.dto.LoanAccountDTO;
import com.conflux.service.dto.LoanProductDTO;
import com.conflux.service.dto.LoanPurposeDTO;
import com.conflux.service.dto.LoanStatusDTO;
import com.conflux.service.dto.LoanUtilizationCheckDTO;
import com.conflux.service.dto.MailSenderDTO;
import com.conflux.service.dto.MeetingDTO;
import com.conflux.service.dto.OccupationDTO;
import com.conflux.service.dto.OccupationSubTypeDTO;
import com.conflux.service.dto.OccupationTypeDTO;
import com.conflux.service.dto.OfficeDTO;
import com.conflux.service.dto.OfficeTypeDTO;
import com.conflux.service.dto.OrgEntityDTO;
import com.conflux.service.dto.PortalUserDTO;
import com.conflux.service.dto.QuestionDTO;
import com.conflux.service.dto.RatingBucketDTO;
import com.conflux.service.dto.RelationshipTypeDTO;
import com.conflux.service.dto.RiskRatingHistoryDTO;
import com.conflux.service.dto.UserRoleDTO;
import com.conflux.service.dto.UserT;

@Service
public class ServiceHelper {

	@Autowired
	IUserRoleDAO userRoleDAO;

	@Autowired
	IOfficeDAO officeDAO;

	public PortalUser createUser(UserT userT) {
		PortalUser portalUser = new PortalUser();
		BeanUtils.copyProperties(userT, portalUser);

		UserRole userRole = getUserRoleDAO().findById(userT.getUserRoleId());
		portalUser.setUserRole(userRole);

		Office office = getOfficeDAO().findById(
				userT.getOfficeDTO().getOfficeId());
		portalUser.setOffice(office);

		// ensure id is set properly
		if (userT.getId() == 0) {
			portalUser.setPortalUserId(null);
		} else {
			portalUser.setPortalUserId(userT.getId());
		}

		return portalUser;
	}

	public UserT createUserDTO(PortalUser portalUser) {
		UserT userT = new UserT();
		BeanUtils.copyProperties(portalUser, userT);

		userT.setId(portalUser.getPortalUserId());
		// create the role
		USER_ROLE userRole;
		if (portalUser.getUserRole().getUserRoleId() == USER_ROLE.ADMIN
				.getRoleId()) {
			userRole = USER_ROLE.ADMIN;
			userT.setAdmin(true);
		} else if (portalUser.getUserRole().getUserRoleId() == USER_ROLE.AUDITOR
				.getRoleId()) {
			userRole = USER_ROLE.AUDITOR;
			userT.setAuditor(true);
		} else if (portalUser.getUserRole().getUserRoleId() == USER_ROLE.JUNIOR_AUDITOR
				.getRoleId()) {
			userRole = USER_ROLE.JUNIOR_AUDITOR;
		} else {
			userRole = USER_ROLE.REGULAR_USER;
		}
		userT.setUserRoleT(userRole);
		userT.setUserRoleId(portalUser.getUserRole().getUserRoleId());

		userT.setOfficeDTO(createOfficeDTO(portalUser.getOffice()));
		return userT;
	}

	public Address createAddress(AddressDTO addressDTO) {
		Address address = new Address();
		BeanUtils.copyProperties(addressDTO, address);

		// ensure id is set properly
		if (addressDTO.getAddressId() == 0) {
			address.setAddressId(null);
		}

		return address;
	}

	public AddressDTO createAddressDTO(Address address) {
		AddressDTO addressDTO = new AddressDTO();
		BeanUtils.copyProperties(address, addressDTO);
		return addressDTO;
	}

	public Asset createAsset(AssetDTO assetDTO) {
		Asset asset = new Asset();
		BeanUtils.copyProperties(assetDTO, asset);

		// ensure id is set properly
		if (assetDTO.getAssetId() == 0) {
			asset.setAssetId(null);
		}

		return asset;
	}

	public AssetDTO createAssetDTO(Asset asset) {
		AssetDTO assetDTO = new AssetDTO();
		BeanUtils.copyProperties(asset, assetDTO);
		assetDTO.setAssetTypeId(asset.getAssetSubType().getAssetType()
				.getAssetTypeId());
		assetDTO.setAssetSubTypeId(asset.getAssetSubType().getAssetSubTypeId());
		return assetDTO;
	}

	public AssetDTO createCompleteAssetDTO(Asset asset) {
		AssetDTO assetDTO = createAssetDTO(asset);
		assetDTO.setAssetSubTypeDTO(createAssetSubTypeDTO((asset
				.getAssetSubType())));
		assetDTO.setAssetTypeDTO(createAssetTypeDTO(asset.getAssetSubType()
				.getAssetType()));
		return assetDTO;
	}

	public AssetSubType createAssetSubType(AssetSubTypeDTO assetSubTypeDTO) {
		AssetSubType assetSubType = new AssetSubType();
		BeanUtils.copyProperties(assetSubTypeDTO, assetSubType);

		// ensure id is set properly
		if (assetSubTypeDTO.getAssetSubTypeId() == 0) {
			assetSubType.setAssetSubTypeId(null);
		}

		return assetSubType;
	}

	public AssetSubTypeDTO createAssetSubTypeDTO(AssetSubType assetSubType) {
		AssetSubTypeDTO assetSubTypeDTO = new AssetSubTypeDTO();
		BeanUtils.copyProperties(assetSubType, assetSubTypeDTO);
		assetSubTypeDTO.setBranchId(assetSubType.getOffice().getOfficeId());
		return assetSubTypeDTO;
	}

	public AssetType createAssetType(AssetTypeDTO assetTypeDTO) {
		AssetType assetType = new AssetType();
		BeanUtils.copyProperties(assetTypeDTO, assetType);

		// ensure id is set properly
		if (assetTypeDTO.getAssetTypeId() == 0) {
			assetType.setAssetTypeId(null);
		}

		return assetType;
	}

	public AssetTypeDTO createAssetTypeDTO(AssetType assetType) {
		AssetTypeDTO assetTypeDTO = new AssetTypeDTO();
		BeanUtils.copyProperties(assetType, assetTypeDTO);
		return assetTypeDTO;
	}

	public Audit createAudit(AuditDTO auditDTO) {
		Audit audit = new Audit();
		BeanUtils.copyProperties(auditDTO, audit);

		// ensure id is set properly
		if (auditDTO.getAuditId() == 0) {
			audit.setAuditId(null);
		}

		return audit;
	}

	public AuditDTO createAuditDTO(Audit audit) {
		AuditDTO auditDTO = new AuditDTO();
		BeanUtils.copyProperties(audit, auditDTO);
		/** set the entity type **/
		ENTITY_TYPE entityType;
		if (audit.getAuditType().getOrgEntity().getOrgEntityId() == 1) {
			entityType = ENTITY_TYPE.LOAN_OFFICER;
		} else if (audit.getAuditType().getOrgEntity().getOrgEntityId() == 2) {
			entityType = ENTITY_TYPE.BRANCH_MANAGER;
		} else {
			entityType = ENTITY_TYPE.CENTER;
		}
		auditDTO.setEntityType(entityType);
		String entityName = "";
		int entityId = 0;
		String officeName = audit.getOffice().getName();
		int officeId = audit.getOffice().getOfficeId();
		// get the entity being audited
		if (audit.getEmployees() != null && audit.getEmployees().size() > 0) {
			Employee employee = audit.getEmployees().iterator().next();
			entityName = employee.getFirstName() + " " + employee.getLastName();
			entityId = employee.getEmployeeId();
		} else if (audit.getCustomers() != null
				&& audit.getCustomers().size() > 0) {
			Customer customer = audit.getCustomers().iterator().next();
			entityName = customer.getDisplayName();
			entityId = customer.getCustomerId();
		}
		auditDTO.setEntityName(entityName);
		auditDTO.setEntityId(entityId);
		auditDTO.setOfficeId(officeId);
		auditDTO.setOfficeName(officeName);
		// get owner of audit action plan
		if (audit.getEmployee() != null) {
			auditDTO.setActionOwner(audit.getEmployee().getFirstName() + " "
					+ audit.getEmployee().getLastName());
			auditDTO.setActionOwnerId(audit.getEmployee().getEmployeeId());
		}
		return auditDTO;
	}

	public AuditQuestion createAuditQuestion(AuditQuestionDTO auditQuestionDTO) {
		AuditQuestion auditQuestion = new AuditQuestion();
		BeanUtils.copyProperties(auditQuestionDTO, auditQuestion);

		// ensure id is set properly
		if (auditQuestionDTO.getAuditQuestionId() == 0) {
			auditQuestion.setAuditQuestionId(null);
		}

		return auditQuestion;
	}

	public AuditQuestionDTO createAuditQuestionDTO(AuditQuestion auditQuestion) {
		AuditQuestionDTO auditQuestionDTO = new AuditQuestionDTO();
		BeanUtils.copyProperties(auditQuestion, auditQuestionDTO);
		/** Populate the applicable score range **/
		Map<String, Integer> applicableScoreRange = new LinkedHashMap<String, Integer>();
		for (Integer i = auditQuestion.getMinScore(); i <= auditQuestion
				.getMaxScore(); i++) {
			applicableScoreRange.put(i.toString(), i);
		}
		auditQuestionDTO.setApplicableScoreRange(applicableScoreRange);
		// populate negative question ticked flag
		if (auditQuestion.getNegativeScore()) {
			if (auditQuestion.getObtainedScore() == 0) {
				auditQuestionDTO.setNegativeQuestionTicked(false);
			} else {
				auditQuestionDTO.setNegativeQuestionTicked(true);
			}
		}
		return auditQuestionDTO;
	}

	public AuditType createAuditType(AuditTypeDTO auditTypeDTO) {
		AuditType auditType = new AuditType();
		BeanUtils.copyProperties(auditTypeDTO, auditType);

		// ensure id is set properly
		if (auditTypeDTO.getAuditTypeId() == 0) {
			auditType.setAuditTypeId(null);
		}

		return auditType;
	}

	public AuditTypeDTO createAuditTypeDTO(AuditType auditType) {
		AuditTypeDTO auditTypeDTO = new AuditTypeDTO();
		BeanUtils.copyProperties(auditType, auditTypeDTO);
		auditTypeDTO.setOrgEntityId(auditType.getOrgEntity().getOrgEntityId());
		/** set the entity type **/
		ENTITY_TYPE entityType;
		if (auditType.getOrgEntity().getOrgEntityId() == 1) {
			entityType = ENTITY_TYPE.LOAN_OFFICER;
		} else if (auditType.getOrgEntity().getOrgEntityId() == 2) {
			entityType = ENTITY_TYPE.BRANCH_MANAGER;
		} else {
			entityType = ENTITY_TYPE.CENTER;
		}
		auditTypeDTO.setEntityType(entityType);
		return auditTypeDTO;
	}

	public AuditTypeScoring createAuditTypeScoring(
			AuditTypeScoringDTO auditTypeScoringDTO) {
		AuditTypeScoring auditTypeScoring = new AuditTypeScoring();
		BeanUtils.copyProperties(auditTypeScoringDTO, auditTypeScoring);

		// ensure id is set properly
		if (auditTypeScoringDTO.getRatingBucketId() != 0
				&& auditTypeScoringDTO.getAuditTypeId() != 0) {
			AuditTypeScoringId auditTypeScoringId = new AuditTypeScoringId();
			auditTypeScoringId.setAuditTypeId(auditTypeScoringDTO
					.getAuditTypeId());
			auditTypeScoringId.setRatingBucketId(auditTypeScoringDTO
					.getRatingBucketId());
		}

		return auditTypeScoring;
	}

	public AuditTypeScoringDTO createAuditTypeScoringDTO(
			AuditTypeScoring auditTypeScoring) {
		AuditTypeScoringDTO auditTypeScoringDTO = new AuditTypeScoringDTO();
		BeanUtils.copyProperties(auditTypeScoring, auditTypeScoringDTO);
		auditTypeScoringDTO.setRatingBucketId(auditTypeScoring
				.getRatingBucket().getRatingBucketId());
		auditTypeScoringDTO.setAuditTypeId(auditTypeScoring.getAuditType()
				.getAuditTypeId());
		return auditTypeScoringDTO;
	}

	public Customer createCustomer(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDTO, customer);

		// ensure id is set properly
		if (customerDTO.getCustomerId() == 0) {
			customer.setCustomerId(null);
		}

		return customer;
	}

	public CustomerDTO createCustomerDTO(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO();
		BeanUtils.copyProperties(customer, customerDTO);
		// Set customer type id
		customerDTO.setCustomerTypeId(customer.getCustomerType()
				.getCustomerTypeId());
		customerDTO.setOfficeId(customer.getOffice().getOfficeId());
		customerDTO.setOfficeName(customer.getOffice().getName());
		customerDTO.setLoanOfficerName(customer.getEmployee().getFirstName()
				+ " " + customer.getEmployee().getLastName());
		return customerDTO;
	}

	public CustomerFamilyMember createCustomerFamilyMember(
			CustomerFamilyMemberDTO customerFamilyMemberDTO) {
		CustomerFamilyMember customerFamilyMember = new CustomerFamilyMember();
		BeanUtils.copyProperties(customerFamilyMemberDTO, customerFamilyMember);

		// ensure id is set properly
		if (customerFamilyMemberDTO.getCustomerFamilyMemberId() == 0) {
			customerFamilyMember.setCustomerFamilyMemberId(null);
		}

		return customerFamilyMember;
	}

	public CustomerFamilyMemberDTO createCustomerFamilyMemberDTO(
			CustomerFamilyMember customerFamilyMember) {
		CustomerFamilyMemberDTO customerFamilyMemberDTO = new CustomerFamilyMemberDTO();
		BeanUtils.copyProperties(customerFamilyMember, customerFamilyMemberDTO);
		customerFamilyMemberDTO.setRelationshipId(customerFamilyMember
				.getRelationshipType().getRelationshipTypeId());
		customerFamilyMemberDTO.setRelationship(customerFamilyMember
				.getRelationshipType().getName());
		return customerFamilyMemberDTO;
	}

	public CustomerFamilyOccupation createCustomerFamilyOccupation(
			CustomerFamilyOccupationDTO customerFamilyOccupationDTO) {
		CustomerFamilyOccupation customerFamilyOccupation = new CustomerFamilyOccupation();
		BeanUtils.copyProperties(customerFamilyOccupationDTO,
				customerFamilyOccupation);

		// ensure id is set properly
		if (customerFamilyOccupationDTO.getCustomerFamilyOccupationId() == 0) {
			customerFamilyOccupation.setCustomerFamilyOccupationId(null);
		}

		return customerFamilyOccupation;
	}

	public CustomerFamilyOccupationDTO createCustomerFamilyOccupationDTO(
			CustomerFamilyOccupation customerFamilyOccupation) {
		CustomerFamilyOccupationDTO customerFamilyOccupationDTO = new CustomerFamilyOccupationDTO();
		BeanUtils.copyProperties(customerFamilyOccupation,
				customerFamilyOccupationDTO);
		customerFamilyOccupationDTO
				.setOccupationTypeId(customerFamilyOccupation
						.getOccupationSubType().getOccupationType()
						.getOccupationTypeId());
		customerFamilyOccupationDTO
				.setOccupationSubTypeId(customerFamilyOccupation
						.getOccupationSubType().getOccupationSubTypeId());
		return customerFamilyOccupationDTO;
	}

	public CustomerFamilyOccupationDTO createCompleteCustomerFamilyOccupationDTO(
			CustomerFamilyOccupation customerFamilyOccupation) {
		CustomerFamilyOccupationDTO customerFamilyOccupationDTO = createCustomerFamilyOccupationDTO(customerFamilyOccupation);
		customerFamilyOccupationDTO
				.setOccupationSubTypeDTO(createOccupationSubTypeDTO(customerFamilyOccupation
						.getOccupationSubType()));
		customerFamilyOccupationDTO
				.setOccupationTypeDTO(createOccupationTypeDTO(customerFamilyOccupation
						.getOccupationSubType().getOccupationType()));
		customerFamilyOccupationDTO
				.setIncomeLumpingDTO(createIncomeLumpingDTO(customerFamilyOccupation
						.getIncomeLumping()));
		return customerFamilyOccupationDTO;
	}

	public CustomerType createCustomerType(CustomerTypeDTO customerTypeDTO) {
		CustomerType customerType = new CustomerType();
		BeanUtils.copyProperties(customerTypeDTO, customerType);

		// ensure id is set properly
		if (customerTypeDTO.getCustomerTypeId() == 0) {
			customerType.setCustomerTypeId(null);
		}

		return customerType;
	}

	public CustomerTypeDTO createCustomerTypeDTO(CustomerType customerType) {
		CustomerTypeDTO customerTypeDTO = new CustomerTypeDTO();
		BeanUtils.copyProperties(customerType, customerTypeDTO);
		return customerTypeDTO;
	}

	public Employee createEmployee(EmployeeDTO employeeDTO) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeDTO, employee);

		// ensure id is set properly
		if (employeeDTO.getEmployeeId() == 0) {
			employee.setEmployeeId(null);
		}

		return employee;
	}

	public EmployeeDTO createEmployeeDTO(Employee employee) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		BeanUtils.copyProperties(employee, employeeDTO);
		employeeDTO.setOfficeId(employee.getOffice().getOfficeId());
		employeeDTO.setOfficeName(employee.getOffice().getName());
		// set supervisor properties
		if (employee.getEmployee() != null) {
			employeeDTO.setSupervisorName(employee.getEmployee().getFirstName()
					+ " " + employee.getEmployee().getLastName());
			employeeDTO.setSupervisorId(employee.getEmployeeId());
		}
		return employeeDTO;
	}

	public ExternalLoan createExternalLoan(ExternalLoanDTO externalLoanDTO) {
		ExternalLoan externalLoan = new ExternalLoan();
		BeanUtils.copyProperties(externalLoanDTO, externalLoan);

		// ensure id is set properly
		if (externalLoanDTO.getExternalLoanId() == 0) {
			externalLoan.setExternalLoanId(null);
		}

		return externalLoan;
	}

	public ExternalLoanDTO createExternalLoanDTO(ExternalLoan externalLoan) {
		ExternalLoanDTO externalLoanDTO = new ExternalLoanDTO();
		BeanUtils.copyProperties(externalLoan, externalLoanDTO);
		if (externalLoan.getExternalOrganization().getName()
				.equalsIgnoreCase("other")) {
			externalLoanDTO.setOtherOrgName(externalLoan.getOtherOrgName());
			externalLoanDTO.setExternalOrganizationId(externalLoan
					.getExternalOrganization().getExternalOrganizationId());
		} else {
			externalLoanDTO.setExternalOrganizationName(externalLoan
					.getExternalOrganization().getName());
			externalLoanDTO.setExternalOrganizationId(externalLoan
					.getExternalOrganization().getExternalOrganizationId());
		}
		return externalLoanDTO;
	}

	public ExternalOrganization createExternalOrganization(
			ExternalOrganizationDTO externalOrganizationDTO) {
		ExternalOrganization externalOrganization = new ExternalOrganization();
		BeanUtils.copyProperties(externalOrganizationDTO, externalOrganization);

		// ensure id is set properly
		if (externalOrganizationDTO.getExternalOrganizationId() == 0) {
			externalOrganization.setExternalOrganizationId(null);
		}

		return externalOrganization;
	}

	public ExternalOrganizationDTO createExternalOrganizationDTO(
			ExternalOrganization externalOrganization) {
		ExternalOrganizationDTO externalOrganizationDTO = new ExternalOrganizationDTO();
		BeanUtils.copyProperties(externalOrganization, externalOrganizationDTO);
		return externalOrganizationDTO;
	}

	public LoanAccount createLoanAccount(LoanAccountDTO loanAccountDTO) {
		LoanAccount loanAccount = new LoanAccount();
		BeanUtils.copyProperties(loanAccountDTO, loanAccount);

		// ensure id is set properly
		if (loanAccountDTO.getLoanAccountId() == 0) {
			loanAccount.setLoanAccountId(null);
		}

		return loanAccount;
	}

	public LoanAccountDTO createLoanAccountDTO(LoanAccount loanAccount) {
		System.out.println("The Loan id:" + loanAccount.getLoanAccountId());
		LoanAccountDTO loanAccountDTO = new LoanAccountDTO();
		BeanUtils.copyProperties(loanAccount, loanAccountDTO);
		loanAccountDTO.setClientId(loanAccount.getCustomer().getCustomerId());
		loanAccountDTO.setClientName(loanAccount.getCustomer().getFirstName()
				+ " " + loanAccount.getCustomer().getLastName());
		loanAccountDTO.setCenterId(loanAccount.getCustomer().getCustomer()
				.getCustomerId());
		loanAccountDTO.setCenterName(loanAccount.getCustomer().getCustomer()
				.getDisplayName());
		loanAccountDTO.setOfficeId(loanAccount.getCustomer().getOffice()
				.getOfficeId());
		loanAccountDTO.setOfficeName(loanAccount.getCustomer().getOffice()
				.getName());
		loanAccountDTO.setLoanProductName(loanAccount.getLoanProduct()
				.getProductName());
		loanAccountDTO.setLoanOriginalPurpose(loanAccount.getLoanPurpose()
				.getName());
		loanAccountDTO.setLoanOriginalPurposeId(loanAccount.getLoanPurpose()
				.getLoanPurposeId());
		loanAccountDTO.setLoanCurrentStatus(loanAccount.getLoanStatus()
				.getName());
		return loanAccountDTO;
	}

	public LoanProduct createLoanProduct(LoanProductDTO loanProductDTO) {
		LoanProduct loanProduct = new LoanProduct();
		BeanUtils.copyProperties(loanProductDTO, loanProduct);

		// ensure id is set properly
		if (loanProductDTO.getLoanProductId() == 0) {
			loanProduct.setLoanProductId(null);
		}

		return loanProduct;
	}

	public LoanProductDTO createLoanProductDTO(LoanProduct loanProduct) {
		LoanProductDTO loanProductDTO = new LoanProductDTO();
		BeanUtils.copyProperties(loanProduct, loanProductDTO);
		return loanProductDTO;
	}

	public LoanPurpose createLoanPurpose(LoanPurposeDTO loanPurposeDTO) {
		LoanPurpose loanPurpose = new LoanPurpose();
		BeanUtils.copyProperties(loanPurposeDTO, loanPurpose);

		// ensure id is set properly
		if (loanPurposeDTO.getLoanPurposeId() == 0) {
			loanPurpose.setLoanPurposeId(null);
		}

		return loanPurpose;
	}

	public LoanPurposeDTO createLoanPurposeDTO(LoanPurpose loanPurpose) {
		LoanPurposeDTO loanPurposeDTO = new LoanPurposeDTO();
		BeanUtils.copyProperties(loanPurpose, loanPurposeDTO);

		return loanPurposeDTO;
	}

	public LoanStatus createLoanStatus(LoanStatusDTO loanStatusDTO) {
		LoanStatus loanStatus = new LoanStatus();
		BeanUtils.copyProperties(loanStatusDTO, loanStatus);

		// ensure id is set properly
		if (loanStatusDTO.getLoanStatusId() == 0) {
			loanStatus.setLoanStatusId(null);
		}

		return loanStatus;
	}

	public LoanStatusDTO createLoanStatusDTO(LoanStatus loanStatus) {
		LoanStatusDTO loanStatusDTO = new LoanStatusDTO();
		BeanUtils.copyProperties(loanStatus, loanStatusDTO);
		return loanStatusDTO;
	}

	public LoanUtilizationCheck createLoanUtilizationCheck(
			LoanUtilizationCheckDTO loanUtilizationCheckDTO) {
		LoanUtilizationCheck loanUtilizationCheck = new LoanUtilizationCheck();
		BeanUtils.copyProperties(loanUtilizationCheckDTO, loanUtilizationCheck);

		// ensure id is set properly
		if (loanUtilizationCheckDTO.getLoanUtilizationCheckId() == 0) {
			loanUtilizationCheck.setLoanUtilizationCheckId(null);
		}

		return loanUtilizationCheck;
	}

	public LoanUtilizationCheckDTO createLoanUtilizationCheckDTO(
			LoanUtilizationCheck loanUtilizationCheck) {
		String firstName = loanUtilizationCheck.getLoanAccount().getCustomer()
				.getFirstName();
		String lastName = loanUtilizationCheck.getLoanAccount().getCustomer()
				.getLastName();
		LoanUtilizationCheckDTO loanUtilizationCheckDTO = new LoanUtilizationCheckDTO();
		BeanUtils.copyProperties(loanUtilizationCheck, loanUtilizationCheckDTO);
		loanUtilizationCheckDTO.setBranchName(loanUtilizationCheck
				.getLoanAccount().getCustomer().getOffice().getName());
		loanUtilizationCheckDTO.setCentreName(loanUtilizationCheck
				.getLoanAccount().getCustomer().getCustomer().getDisplayName());
		loanUtilizationCheckDTO.setCustomerName(firstName == null ? " "
				: firstName + " " + lastName == null ? " " : lastName);
		loanUtilizationCheckDTO.setLoanProductName(loanUtilizationCheck
				.getLoanAccount().getLoanProduct().getProductName());
		loanUtilizationCheckDTO.setLoanAmount(loanUtilizationCheck
				.getLoanAccount().getAmount());
		loanUtilizationCheckDTO.setLoanPurpose(loanUtilizationCheck
				.getLoanAccount().getLoanPurpose().getName());
		loanUtilizationCheckDTO.setLoanAccountId(loanUtilizationCheck
				.getLoanAccount().getLoanAccountId());

		// more luc properties

		if (loanUtilizationCheck.getLoanPurposeByLoanPurposeId() != null) {
			loanUtilizationCheckDTO.setLoanPurposeId1(loanUtilizationCheck
					.getLoanPurposeByLoanPurposeId().getLoanPurposeId());
			loanUtilizationCheckDTO.setLoanPurposeName1(loanUtilizationCheck
					.getLoanPurposeByLoanPurposeId().getName());
			loanUtilizationCheckDTO.setLoanPurposeType1(loanUtilizationCheck
					.getLoanPurposeByLoanPurposeId().getLoanPurposeType());
		}
		if (loanUtilizationCheck.getLoanPurposeByLoanPurposeId1() != null) {
			loanUtilizationCheckDTO.setLoanPurposeId2(loanUtilizationCheck
					.getLoanPurposeByLoanPurposeId1().getLoanPurposeId());
			loanUtilizationCheckDTO.setLoanPurposeName2(loanUtilizationCheck
					.getLoanPurposeByLoanPurposeId1().getName());
			loanUtilizationCheckDTO.setLoanPurposeType2(loanUtilizationCheck
					.getLoanPurposeByLoanPurposeId1().getLoanPurposeType());
		}
		if (loanUtilizationCheck.getLoanPurposeByLoanPurposeId2() != null) {
			loanUtilizationCheckDTO.setLoanPurposeId3(loanUtilizationCheck
					.getLoanPurposeByLoanPurposeId2().getLoanPurposeId());
			loanUtilizationCheckDTO.setLoanPurposeName3(loanUtilizationCheck
					.getLoanPurposeByLoanPurposeId2().getName());
			loanUtilizationCheckDTO.setLoanPurposeType3(loanUtilizationCheck
					.getLoanPurposeByLoanPurposeId2().getLoanPurposeType());
		}
		return loanUtilizationCheckDTO;
	}

	public MailSender createMailSender(MailSenderDTO mailSenderDTO) {
		MailSender mailSender = new MailSender();
		BeanUtils.copyProperties(mailSenderDTO, mailSender);

		// ensure id is set properly
		if (mailSenderDTO.getMailSenderId() == 0) {
			mailSender.setMailSenderId(null);
		}

		return mailSender;
	}

	public MailSenderDTO createMailSenderDTO(MailSender mailSender) {
		MailSenderDTO mailSenderDTO = new MailSenderDTO();
		BeanUtils.copyProperties(mailSender, mailSenderDTO);
		return mailSenderDTO;
	}

	public Meeting createMeeting(MeetingDTO meetingDTO) {
		Meeting meeting = new Meeting();
		BeanUtils.copyProperties(meetingDTO, meeting);

		// ensure id is set properly
		if (meetingDTO.getMeetingId() == 0) {
			meeting.setMeetingId(null);
		}

		return meeting;
	}

	public MeetingDTO createMeetingDTO(Meeting meeting) {
		MeetingDTO meetingDTO = new MeetingDTO();
		BeanUtils.copyProperties(meeting, meetingDTO);
		meetingDTO.setCenterId(meeting.getCustomer().getCustomerId());
		meetingDTO.setCenterName(meeting.getCustomer().getDisplayName());
		return meetingDTO;
	}

	public Occupation createOccupation(OccupationDTO occupationDTO) {
		Occupation occupation = new Occupation();
		BeanUtils.copyProperties(occupationDTO, occupation);

		// ensure id is set properly
		if (occupationDTO.getOccupationId() == 0) {
			occupation.setOccupationId(null);
		}

		return occupation;
	}

	public OccupationDTO createOccupationDTO(Occupation occupation) {
		OccupationDTO occupationDTO = new OccupationDTO();
		BeanUtils.copyProperties(occupation, occupationDTO);
		occupationDTO.setOccupationTypeId(occupation.getOccupationSubType()
				.getOccupationType().getOccupationTypeId());
		occupationDTO.setOccupationSubTypeId(occupation.getOccupationSubType()
				.getOccupationSubTypeId());
		return occupationDTO;
	}

	public OccupationDTO createCompleteOccupationDTO(Occupation occupation) {
		OccupationDTO occupationDTO = createOccupationDTO(occupation);
		occupationDTO
				.setOccupationSubTypeDTO(createOccupationSubTypeDTO(occupation
						.getOccupationSubType()));
		occupationDTO.setOccupationTypeDTO(createOccupationTypeDTO(occupation
				.getOccupationSubType().getOccupationType()));
		occupationDTO.setIncomeLumpingDTO(createIncomeLumpingDTO(occupation
				.getIncomeLumping()));
		return occupationDTO;
	}

	private IncomeLumpingDTO createIncomeLumpingDTO(IncomeLumping incomeLumping) {
		IncomeLumpingDTO incomeLumpingDTO = new IncomeLumpingDTO();
		if (null == incomeLumping) {
			return incomeLumpingDTO;
		}
		BeanUtils.copyProperties(incomeLumping, incomeLumpingDTO);
		return incomeLumpingDTO;
	}

	public IncomeLumping createIncomeLumping(IncomeLumpingDTO incomeLumpingDTO) {
		IncomeLumping incomeLumping = new IncomeLumping();
		BeanUtils.copyProperties(incomeLumpingDTO, incomeLumping);
		if (incomeLumping.getIncomeLumpingId() == 0) {
			incomeLumping.setIncomeLumpingId(null);
		}
		return incomeLumping;
	}

	public OccupationSubType createOccupationSubType(
			OccupationSubTypeDTO occupationSubTypeDTO) {
		OccupationSubType occupationSubType = new OccupationSubType();
		BeanUtils.copyProperties(occupationSubTypeDTO, occupationSubType);

		// ensure id is set properly
		if (occupationSubTypeDTO.getOccupationSubTypeId() == 0) {
			occupationSubType.setOccupationSubTypeId(null);
		}

		return occupationSubType;
	}

	public OccupationSubTypeDTO createOccupationSubTypeDTO(
			OccupationSubType occupationSubType) {
		OccupationSubTypeDTO occupationSubTypeDTO = new OccupationSubTypeDTO();
		BeanUtils.copyProperties(occupationSubType, occupationSubTypeDTO);
		occupationSubTypeDTO.setBranchId(occupationSubType.getOffice()
				.getOfficeId());
		return occupationSubTypeDTO;
	}

	public OccupationType createOccupationType(
			OccupationTypeDTO occupationTypeDTO) {
		OccupationType occupationType = new OccupationType();
		BeanUtils.copyProperties(occupationTypeDTO, occupationType);

		// ensure id is set properly
		if (occupationTypeDTO.getOccupationTypeId() == 0) {
			occupationType.setOccupationTypeId(null);
		}

		return occupationType;
	}

	public OccupationTypeDTO createOccupationTypeDTO(
			OccupationType occupationType) {
		OccupationTypeDTO occupationTypeDTO = new OccupationTypeDTO();
		BeanUtils.copyProperties(occupationType, occupationTypeDTO);
		return occupationTypeDTO;
	}

	public Office createOffice(OfficeDTO officeDTO) {
		Office office = new Office();
		BeanUtils.copyProperties(officeDTO, office);

		// ensure id is set properly
		if (officeDTO.getOfficeId() == 0) {
			office.setOfficeId(null);
		}

		return office;
	}

	public OfficeDTO createOfficeDTO(Office office) {
		OfficeDTO officeDTO = new OfficeDTO();
		BeanUtils.copyProperties(office, officeDTO);
		if (office.getOfficeType().getOfficeTypeId() == 1) {
			officeDTO.setBranchOffice(false);
		} else {
			officeDTO.setBranchOffice(true);
		}
		return officeDTO;
	}

	public OfficeType createOfficeType(OfficeTypeDTO officeTypeDTO) {
		OfficeType officeType = new OfficeType();
		BeanUtils.copyProperties(officeTypeDTO, officeType);

		// ensure id is set properly
		if (officeTypeDTO.getOfficeTypeId() == 0) {
			officeType.setOfficeTypeId(null);
		}

		return officeType;
	}

	public OfficeTypeDTO createOfficeTypeDTO(OfficeType officeType) {
		OfficeTypeDTO officeTypeDTO = new OfficeTypeDTO();
		BeanUtils.copyProperties(officeType, officeTypeDTO);
		return officeTypeDTO;
	}

	public OrgEntity createOrgEntity(OrgEntityDTO orgEntityDTO) {
		OrgEntity orgEntity = new OrgEntity();
		BeanUtils.copyProperties(orgEntityDTO, orgEntity);

		// ensure id is set properly
		if (orgEntityDTO.getOrgEntityId() == 0) {
			orgEntity.setOrgEntityId(null);
		}

		return orgEntity;
	}

	public OrgEntityDTO createOrgEntityDTO(OrgEntity orgEntity) {
		OrgEntityDTO orgEntityDTO = new OrgEntityDTO();
		BeanUtils.copyProperties(orgEntity, orgEntityDTO);
		return orgEntityDTO;
	}

	public PortalUser createPortalUser(PortalUserDTO portalUserDTO) {
		PortalUser portalUser = new PortalUser();
		BeanUtils.copyProperties(portalUserDTO, portalUser);

		// ensure id is set properly
		if (portalUserDTO.getPortalUserId() == 0) {
			portalUser.setPortalUserId(null);
		}

		return portalUser;
	}

	public PortalUserDTO createPortalUserDTO(PortalUser portalUser) {
		PortalUserDTO portalUserDTO = new PortalUserDTO();
		BeanUtils.copyProperties(portalUser, portalUserDTO);
		return portalUserDTO;
	}

	public Question createQuestion(QuestionDTO questionDTO) {
		Question question = new Question();
		BeanUtils.copyProperties(questionDTO, question);

		// ensure id is set properly
		if (questionDTO.getQuestionId() == 0) {
			question.setQuestionId(null);
		}

		return question;
	}

	public QuestionDTO createQuestionDTO(Question question) {
		QuestionDTO questionDTO = new QuestionDTO();
		BeanUtils.copyProperties(question, questionDTO);
		return questionDTO;
	}

	public RatingBucket createRatingBucket(RatingBucketDTO ratingBucketDTO) {
		RatingBucket ratingBucket = new RatingBucket();
		BeanUtils.copyProperties(ratingBucketDTO, ratingBucket);

		// ensure id is set properly
		if (ratingBucketDTO.getRatingBucketId() == 0) {
			ratingBucket.setRatingBucketId(null);
		}

		return ratingBucket;
	}

	public RatingBucketDTO createRatingBucketDTO(RatingBucket ratingBucket) {
		RatingBucketDTO ratingBucketDTO = new RatingBucketDTO();
		BeanUtils.copyProperties(ratingBucket, ratingBucketDTO);
		return ratingBucketDTO;
	}

	public RelationshipType createRelationshipType(
			RelationshipTypeDTO relationshipTypeDTO) {
		RelationshipType relationshipType = new RelationshipType();
		BeanUtils.copyProperties(relationshipTypeDTO, relationshipType);

		// ensure id is set properly
		if (relationshipTypeDTO.getRelationshipTypeId() == 0) {
			relationshipType.setRelationshipTypeId(null);
		}

		return relationshipType;
	}

	public RelationshipTypeDTO createRelationshipTypeDTO(
			RelationshipType relationshipType) {
		RelationshipTypeDTO relationshipTypeDTO = new RelationshipTypeDTO();
		BeanUtils.copyProperties(relationshipType, relationshipTypeDTO);
		return relationshipTypeDTO;
	}

	public RiskRatingHistory createRiskRatingHistory(
			RiskRatingHistoryDTO riskRatingHistoryDTO) {
		RiskRatingHistory riskRatingHistory = new RiskRatingHistory();
		BeanUtils.copyProperties(riskRatingHistoryDTO, riskRatingHistory);

		// ensure id is set properly
		if (riskRatingHistoryDTO.getRiskRatingHistoryId() == 0) {
			riskRatingHistory.setRiskRatingHistoryId(null);
		}

		return riskRatingHistory;
	}

	public RiskRatingHistoryDTO createRiskRatingHistoryDTO(
			RiskRatingHistory riskRatingHistory) {
		RiskRatingHistoryDTO riskRatingHistoryDTO = new RiskRatingHistoryDTO();
		BeanUtils.copyProperties(riskRatingHistory, riskRatingHistoryDTO);
		return riskRatingHistoryDTO;
	}

	public UserRole createUserRole(UserRoleDTO userRoleDTO) {
		UserRole userRole = new UserRole();
		BeanUtils.copyProperties(userRoleDTO, userRole);

		// ensure id is set properly
		if (userRoleDTO.getUserRoleId() == 0) {
			userRole.setUserRoleId(null);
		}

		return userRole;
	}

	/*** Custom methods start here ***/
	public AuditTypeScoringDTO createCompleteAuditTypeScoringDTO(
			AuditTypeScoring auditTypeScoring) {
		AuditTypeScoringDTO auditTypeScoringDTO = createAuditTypeScoringDTO(auditTypeScoring);
		/** populate additional properties **/
		auditTypeScoringDTO
				.setRatingBucketDTO(createRatingBucketDTO(auditTypeScoring
						.getRatingBucket()));
		return auditTypeScoringDTO;
	}

	public AuditTypeDTO createCompleteAuditTypeDTO(AuditType auditType) {
		AuditTypeDTO auditTypeDTO = createAuditTypeDTO(auditType);
		int questionsSize = 0;
		if(null != auditType.getQuestions()) questionsSize = auditType.getQuestions().size();
		/** populate additional properties **/
		List<QuestionDTO> questionDTOs = new ArrayList<QuestionDTO>(questionsSize);
		for (Question question : auditType.getQuestions()) {
			QuestionDTO questionDTO = createQuestionDTO(question);
			//questionDTOs.add(question.getPosition().intValue(), questionDTO);
			questionDTOs.add(questionDTO);
		}
		//Sort Questions based on question position
		Collections.sort(questionDTOs);
		List<AuditTypeScoringDTO> auditTypeScoringDTOs = new ArrayList<AuditTypeScoringDTO>();
		TreeMap<Integer, AuditTypeScoringDTO> auditTypeScoringTreeMap = new TreeMap<Integer, AuditTypeScoringDTO>();
		for (AuditTypeScoring auditTypeScoring : auditType
				.getAuditTypeScorings()) {
			AuditTypeScoringDTO auditTypeScoringDTO = createCompleteAuditTypeScoringDTO(auditTypeScoring);
			auditTypeScoringTreeMap.put(auditTypeScoringDTO.getMinScore(),
					auditTypeScoringDTO);
		}
		for (int auditScoringKey : auditTypeScoringTreeMap.keySet()) {
			auditTypeScoringDTOs.add(auditTypeScoringTreeMap
					.get(auditScoringKey));
		}
		auditTypeDTO.setQuestionDTOs(questionDTOs);
		auditTypeDTO.setAuditTypeScoringDTOs(auditTypeScoringDTOs);
		return auditTypeDTO;
	}

	public CustomerDTO createCompleteCustomerDTO(Customer customer) {
		CustomerDTO customerDTO = createCustomerDTO(customer);
		/** populate additional properties **/
		customerDTO
				.setIncomeGeneratingAssetDTOs(createCompleteActiveAssetDTOs(customer
						.getAssets()));
		if (customer.getOccupations() != null
				&& customer.getOccupations().size() > 0) {
			customerDTO.setOccupationDTO(createCompleteOccupationDTO(customer
					.getOccupations().iterator().next()));
		}
		List<CustomerFamilyMemberDTO> customerFamilyMemberDTOs = createCompleteActiveCustomerFamilyMemberDTOs(customer
				.getCustomerFamilyMembers());
		String unwellFamilyMembers = "";
		boolean familyMemberSick = false;
		for (CustomerFamilyMemberDTO customerFamilyMemberDTO : customerFamilyMemberDTOs) {
			if (customerFamilyMemberDTO.isUnwell()) {
				familyMemberSick = true;
				unwellFamilyMembers = unwellFamilyMembers
						+ customerFamilyMemberDTO.getFirstName() + " "
						+ customerFamilyMemberDTO.getLastName();
			}
		}
		customerDTO.setFamilyMemberDTOs(customerFamilyMemberDTOs);
		List<CustomerFamilyOccupationDTO> customerFamilyOccupationDTOs = createCompleteActiveCustomerFamilyOccupationDTOs(customer
				.getCustomerFamilyOccupations());
		customerDTO.setFamilyOccupationDTOs(customerFamilyOccupationDTOs);
		List<ExternalLoanDTO> externalLoanDTOs = new ArrayList<ExternalLoanDTO>();
		for (ExternalLoan externalLoan : customer.getExternalLoans()) {
			ExternalLoanDTO externalLoanDTO = createExternalLoanDTO(externalLoan);
			externalLoanDTOs.add(externalLoanDTO);
		}
		customerDTO.setExternalLoanDTOs(externalLoanDTOs);
		List<LoanAccountDTO> loanAccountDTOs = new ArrayList<LoanAccountDTO>();
		for (LoanAccount loanAccount : customer.getLoanAccounts()) {
			if (loanAccount != null) {
				LoanAccountDTO loanAccountDTO = createCompleteLoanAccountDTO(loanAccount);
				loanAccountDTOs.add(loanAccountDTO);
			}
		}
		customerDTO.setLoanAccountDTOs(loanAccountDTOs);
		customerDTO.setNoOfFamilyMembers(customer.getCustomerFamilyMembers()
				.size());
		customerDTO.setUnwellMembersConcat(unwellFamilyMembers);
		customerDTO.setFamilyMemberSick(familyMemberSick);
		customerDTO.setMeetingDTOs(createMeetingDTOs(customer.getMeetings()));

		List<RiskRatingHistoryDTO> riskRatingHistoryDTOs = new ArrayList<RiskRatingHistoryDTO>();
		for (RiskRatingHistory riskRatingHistory : customer
				.getRiskRatingHistories()) {
			RiskRatingHistoryDTO riskRatingHistoryDTO = createRiskRatingHistoryDTO(riskRatingHistory);
			riskRatingHistoryDTOs.add(riskRatingHistoryDTO);
		}
		customerDTO.setPreviousRiskRatingDTOs(riskRatingHistoryDTOs);
		if (customer.getAddress() != null) {
			customerDTO.setAddressDTO(createAddressDTO(customer.getAddress()));
		}

		return customerDTO;
	}

	public AuditDTO createCompleteAuditDTO(Audit audit) {
		AuditDTO auditDTO = createAuditDTO(audit);
		/** populate additional properties **/
		List<AuditQuestionDTO> auditQuestionDTOs = new ArrayList<AuditQuestionDTO>();
		for (AuditQuestion auditQuestion : audit.getAuditQuestions()) {
			AuditQuestionDTO auditQuestionDTO = createAuditQuestionDTO(auditQuestion);
			auditQuestionDTOs.add(auditQuestionDTO);
		}
		auditDTO.setAuditQuestionDTOs(auditQuestionDTOs);
		if (audit.getRatingBucket() != null) {
			auditDTO.setRatingBucketDTO(createRatingBucketDTO(audit
					.getRatingBucket()));
		}
		auditDTO.setAuditTypeDTO(createAuditTypeDTO(audit.getAuditType()));
		return auditDTO;
	}

	public CustomerDTO createCompleteCenterDTO(Customer customer) {
		CustomerDTO centerDTO = createCustomerDTO(customer);
		/** Populate additional properties **/
		centerDTO.setLoanOfficerDTO(createEmployeeDTO(customer.getEmployee()));
		List<AuditDTO> auditDTOs = new ArrayList<AuditDTO>();
		for (Audit audit : customer.getAudits()) {
			AuditDTO auditDTO = createAuditDTO(audit);
			auditDTOs.add(auditDTO);
		}
		centerDTO.setAuditDTOs(auditDTOs);
		List<CustomerDTO> customerDTOs = new ArrayList<CustomerDTO>();
		for (Customer client : customer.getCustomers()) {
			CustomerDTO customerDTO = createCustomerDTO(client);
			customerDTOs.add(customerDTO);
		}
		centerDTO.setCustomerDTOs(customerDTOs);
		List<RiskRatingHistoryDTO> riskRatingHistoryDTOs = new ArrayList<RiskRatingHistoryDTO>();
		for (RiskRatingHistory riskRatingHistory : customer
				.getRiskRatingHistories()) {
			RiskRatingHistoryDTO riskRatingHistoryDTO = createRiskRatingHistoryDTO(riskRatingHistory);
			riskRatingHistoryDTOs.add(riskRatingHistoryDTO);
		}
		centerDTO.setPreviousRiskRatingDTOs(riskRatingHistoryDTOs);
		return centerDTO;
	}

	/**
	 * Create complete Center DTO along with complete Customer DTO for each
	 * client.
	 * 
	 * @param customer
	 * @return
	 */
	public CustomerDTO createCompleteCenterDTOForRiskRating(Customer customer) {
		CustomerDTO centerDTO = createCustomerDTO(customer);
		/** Populate additional properties **/
		centerDTO.setLoanOfficerDTO(createEmployeeDTO(customer.getEmployee()));
		List<AuditDTO> auditDTOs = new ArrayList<AuditDTO>();
		for (Audit audit : customer.getAudits()) {
			AuditDTO auditDTO = createCompleteAuditDTO(audit);
			auditDTOs.add(auditDTO);
		}
		centerDTO.setAuditDTOs(auditDTOs);
		List<CustomerDTO> customerDTOs = new ArrayList<CustomerDTO>();
		for (Customer client : customer.getCustomers()) {
			CustomerDTO customerDTO = createCompleteCustomerDTO(client);
			customerDTOs.add(customerDTO);
		}
		centerDTO.setCustomerDTOs(customerDTOs);
		List<RiskRatingHistoryDTO> riskRatingHistoryDTOs = new ArrayList<RiskRatingHistoryDTO>();
		for (RiskRatingHistory riskRatingHistory : customer
				.getRiskRatingHistories()) {
			RiskRatingHistoryDTO riskRatingHistoryDTO = createRiskRatingHistoryDTO(riskRatingHistory);
			riskRatingHistoryDTOs.add(riskRatingHistoryDTO);
		}
		centerDTO.setPreviousRiskRatingDTOs(riskRatingHistoryDTOs);
		return centerDTO;
	}

	public LoanAccountDTO createCompleteLoanAccountDTO(LoanAccount loanAccount) {
		LoanAccountDTO loanAccountDTO = createLoanAccountDTO(loanAccount);
		/** populate additional properties **/
		List<LoanUtilizationCheckDTO> loanUtilizationCheckDTOs = new ArrayList<LoanUtilizationCheckDTO>();
		for (LoanUtilizationCheck loanUtilizationCheck : loanAccount
				.getLoanUtilizationChecks()) {
			LoanUtilizationCheckDTO loanUtilizationCheckDTO = createLoanUtilizationCheckDTO(loanUtilizationCheck);
			loanUtilizationCheckDTOs.add(loanUtilizationCheckDTO);
		}
		loanAccountDTO.setLoanUtilizationCheckDTOs(loanUtilizationCheckDTOs);
		return loanAccountDTO;
	}

	public List<AssetDTO> createCompleteActiveAssetDTOs(Collection<Asset> assets) {
		List<AssetDTO> assetDTOs = new ArrayList<AssetDTO>();
		for (Asset asset : assets) {
			if (asset.getActive()) {
				AssetDTO assetDTO = createCompleteAssetDTO(asset);
				assetDTOs.add(assetDTO);
			}
		}
		return assetDTOs;
	}

	public List<CustomerFamilyOccupationDTO> createCompleteActiveCustomerFamilyOccupationDTOs(
			Collection<CustomerFamilyOccupation> customerFamilyOccupations) {
		List<CustomerFamilyOccupationDTO> customerFamilyOccupationDTOs = new ArrayList<CustomerFamilyOccupationDTO>();
		for (CustomerFamilyOccupation customerFamilyOccupation : customerFamilyOccupations) {
			if (customerFamilyOccupation.getActive()) {
				// CustomerFamilyOccupationDTO customerFamilyOccupationDTO =
				// createCustomerFamilyOccupationDTO(customerFamilyOccupation);
				CustomerFamilyOccupationDTO customerFamilyOccupationDTO = createCompleteCustomerFamilyOccupationDTO(customerFamilyOccupation);
				customerFamilyOccupationDTOs.add(customerFamilyOccupationDTO);
			}
		}
		return customerFamilyOccupationDTOs;
	}

	public List<ExternalLoanDTO> createCompleteActiveExternalLoanDTOs(
			Collection<ExternalLoan> externalLoans) {
		List<ExternalLoanDTO> externalLoanDTOs = new ArrayList<ExternalLoanDTO>();
		for (ExternalLoan externalLoan : externalLoans) {
			if (externalLoan.getActive()) {
				ExternalLoanDTO externalLoanDTO = createExternalLoanDTO(externalLoan);
				externalLoanDTOs.add(externalLoanDTO);
			}
		}
		return externalLoanDTOs;
	}

	public List<CustomerFamilyMemberDTO> createCompleteActiveCustomerFamilyMemberDTOs(
			Collection<CustomerFamilyMember> customerFamilyMembers) {
		List<CustomerFamilyMemberDTO> customerFamilyMemberDTOs = new ArrayList<CustomerFamilyMemberDTO>();
		for (CustomerFamilyMember customerFamilyMember : customerFamilyMembers) {
			if (customerFamilyMember.getActive()) {
				CustomerFamilyMemberDTO customerFamilyMemberDTO = createCustomerFamilyMemberDTO(customerFamilyMember);
				customerFamilyMemberDTOs.add(customerFamilyMemberDTO);
			}
		}
		return customerFamilyMemberDTOs;
	}

	public EmployeeDTO createCompleteEmployeeDTO(Employee employee) {
		EmployeeDTO employeeDTO = createEmployeeDTO(employee);
		if (employee.getAddressByPermanentAddressId() != null) {
			employeeDTO.setPermanentAddress(createAddressDTO(employee
					.getAddressByPermanentAddressId()));
		}
		if (employee.getAddressByCurrentAddressId() != null) {
			employeeDTO.setCurrentAddress(createAddressDTO(employee
					.getAddressByCurrentAddressId()));
		}
		if (employee.getAudits() != null && employee.getAudits().size() >= 1) {
			List<AuditDTO> auditDTOs = new ArrayList<AuditDTO>();
			for (Audit audit : employee.getAudits()) {
				AuditDTO auditDTO = createAuditDTO(audit);
				auditDTOs.add(auditDTO);
			}
			employeeDTO.setAuditDTOs(auditDTOs);
		}
		return employeeDTO;
	}

	public List<MeetingDTO> createMeetingDTOs(Collection<Meeting> meetings) {
		List<MeetingDTO> meetingDTOs = new ArrayList<MeetingDTO>();
		for (Meeting meeting : meetings) {
			meetingDTOs.add(createMeetingDTO(meeting));
		}
		return meetingDTOs;
	}

	/*** private methods start here **/

	public UserRoleDTO createUserRoleDTO(UserRole userRole) {
		UserRoleDTO userRoleDTO = new UserRoleDTO();
		BeanUtils.copyProperties(userRole, userRoleDTO);
		return userRoleDTO;
	}

	public IUserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
	}

	public void setUserRoleDAO(IUserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}

	public IOfficeDAO getOfficeDAO() {
		return officeDAO;
	}

	public void setOfficeDAO(IOfficeDAO officeDAO) {
		this.officeDAO = officeDAO;
	}

}
