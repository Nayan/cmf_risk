package com.conflux.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.BeanUtils;

import com.conflux.common.ApplicationConstants.ENTITY_TYPE;
import com.conflux.common.ApplicationConstants.GENERIC_NAVIGATION;
import com.conflux.service.dto.AuditDTO;
import com.conflux.service.dto.AuditQuestionDTO;
import com.conflux.service.dto.AuditTypeDTO;
import com.conflux.service.dto.CustomerDTO;
import com.conflux.service.dto.EmployeeDTO;
import com.conflux.service.dto.QuestionDTO;

@ManagedBean(name = "auditBean")
@SessionScoped
public class AuditManagedBean extends AbstractManagedBean implements
		Serializable {
	private static final long serialVersionUID = -8204585501500254352L;

	@ManagedProperty(value = "#{appBean}")
	private ApplicationManagedBean appBean;

	private int entityId;
	private int branchId;
	private String branchName;
	private String entityName;
	private ENTITY_TYPE entityType;
	private Map<String, Integer> auditTypesMap;
	private int auditTypeId;
	private AuditDTO audit;
	private Map<String, Integer> employeesInScope;
	private List<AuditDTO> auditsInProgress;
	private boolean auditInProgress = false;

	public String attachAnAudit(long entityTypeId, int entityId) {
		// set session scoped variables
		this.entityId = entityId;
		if (entityTypeId == 1) {
			entityType = ENTITY_TYPE.LOAN_OFFICER;
			populateSessionPropsFromEmployeeEntity(entityId);
		} else if (entityTypeId == 2) {
			entityType = ENTITY_TYPE.BRANCH_MANAGER;
			populateSessionPropsFromEmployeeEntity(entityId);
		} else {
			entityType = ENTITY_TYPE.CENTER;
			CustomerDTO center = getCustomerService().getCenterById(entityId);
			branchId = center.getOfficeId();
			entityName = center.getDisplayName();
			branchName = center.getOfficeName();
		}

		List<AuditTypeDTO> applicableAuditTypes = getAuditTypeService()
				.getAllAuditTypes((int) entityTypeId);
		auditTypesMap = new LinkedHashMap<String, Integer>();
		for (AuditTypeDTO auditTypeDTO : applicableAuditTypes) {
			auditTypesMap.put(auditTypeDTO.getName(),
					auditTypeDTO.getAuditTypeId());
		}

		// reset session variables
		auditTypeId = 0;
		audit = new AuditDTO();
		populateEmployeesInScope();

		return GENERIC_NAVIGATION.ATTACH_AN_AUDIT.toString();
	}

	private void populateSessionPropsFromEmployeeEntity(int entityId) {
		EmployeeDTO employeeDTO = getBaseService().getEmployeeById(entityId);
		entityName = employeeDTO.getFirstName() + " "
				+ employeeDTO.getLastName();
		branchId = employeeDTO.getOfficeId();
		branchName = employeeDTO.getOfficeName();
	}

	public String startAuditProcess() {

		/*** Populate the initial audit properties ***/
		AuditTypeDTO auditTypeDTO = getAuditTypeService().getAuditType(
				auditTypeId);
		audit.setAuditTypeDTO(auditTypeDTO);
		audit.setEntityType(entityType);
		audit.setActive(true);
		audit.setEntityId(entityId);
		audit.setEntityName(entityName);
		audit.setName(auditTypeDTO.getName());
		audit.setCreatedBy(getLoginBean().getUserT().getUsername());
		Date todaysDate = new Date();
		audit.setStartDate(todaysDate);
		audit.setUpdatedDate(todaysDate);

		/*** Populate the audit questions and their default questions **/
		List<AuditQuestionDTO> auditQuestionDTOs = new ArrayList<AuditQuestionDTO>();
		for (QuestionDTO questionDTO : auditTypeDTO.getQuestionDTOs()) {
			AuditQuestionDTO auditQuestionDTO = new AuditQuestionDTO();
			BeanUtils.copyProperties(questionDTO, auditQuestionDTO);
			auditQuestionDTO.setObtainedScore(auditQuestionDTO
					.getDefaultScore());
			/** Populate the applicable score range **/
			Map<String, Integer> applicableScoreRange = new LinkedHashMap<String, Integer>();
			for (Integer i = questionDTO.getMinScore(); i <= questionDTO
					.getMaxScore(); i++) {
				applicableScoreRange.put(i.toString(), i);
			}
			auditQuestionDTO.setApplicableScoreRange(applicableScoreRange);
			auditQuestionDTOs.add(auditQuestionDTO);
		}
		audit.setAuditQuestionDTOs(auditQuestionDTOs);

		auditInProgress = false;

		return GENERIC_NAVIGATION.AUDIT_PROCESS.toString();
	}

	public String cancelAuditProcess() {
		this.attachAnAudit(entityType.getEntityId(), entityId);
		return GENERIC_NAVIGATION.ATTACH_AN_AUDIT.toString();
	}

	public String saveAuditForLater() {
		// set audit trail
		audit.setCreatedBy(getLoginBean().getUserT().getUsername());
		audit.setActive(true);

		// save the audit
		getAuditService().save(audit, false);

		return GENERIC_NAVIGATION.HOME.toString();
	}

	public String saveAudit() {
		// set audit trail
		audit.setCreatedBy(getLoginBean().getUserT().getUsername());
		audit.setActive(true);

		// save the audit
		getAuditService().save(audit, true);

		return GENERIC_NAVIGATION.HOME.toString();
	}

	public String viewAuditInProgress(int auditId) {
		auditInProgress = true;
		getAuditandSetSessionProps(auditId);
		return GENERIC_NAVIGATION.AUDIT_PROCESS.toString();
	}

	private void getAuditandSetSessionProps(int auditId) {
		audit = getAuditService().getAudit(auditId);
		// populate session properties
		entityId = audit.getEntityId();
		entityType = audit.getEntityType();
		entityName = audit.getEntityName();
		branchId = audit.getOfficeId();
		branchName = audit.getOfficeName();
		// populate employees in scope
		populateEmployeesInScope();
	}

	private void populateEmployeesInScope() {
		List<EmployeeDTO> employeesInScopeList = getBaseService()
				.getAllEmployeesInScope(branchId);
		employeesInScope = new HashMap<String, Integer>();
		for (EmployeeDTO employeeDTO : employeesInScopeList) {
			employeesInScope.put(
					employeeDTO.getFirstName() + " "
							+ employeeDTO.getLastName(),
					employeeDTO.getEmployeeId());
		}
	}

	public String viewClosedAudit(int auditId) {
		auditInProgress = false;
		getAuditandSetSessionProps(auditId);
		return GENERIC_NAVIGATION.CLOSED_AUDIT_PROCESS.toString();
	}

	public String updateAudit() {
		// set audit trail
		audit.setUpdatedBy(getLoginBean().getUserT().getUsername());
		audit.setActive(true);

		// save the audit
		getAuditService().update(audit, false);

		return GENERIC_NAVIGATION.AUDITS_IN_PROGRESS.toString();
	}

	public String updateAndCloseAudit() {
		// set audit trail
		audit.setUpdatedBy(getLoginBean().getUserT().getUsername());
		audit.setActive(true);

		// save the audit
		getAuditService().update(audit, true);

		return GENERIC_NAVIGATION.AUDITS_IN_PROGRESS.toString();
	}

	public void viewAllAuditsInProgress() {
		auditsInProgress = getAuditService().getAuditsInProgress(branchId);
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public ENTITY_TYPE getEntityType() {
		return entityType;
	}

	public void setEntityType(ENTITY_TYPE entityType) {
		this.entityType = entityType;
	}

	public Map<String, Integer> getAuditTypesMap() {
		return auditTypesMap;
	}

	public void setAuditTypesMap(Map<String, Integer> auditTypesMap) {
		this.auditTypesMap = auditTypesMap;
	}

	public int getAuditTypeId() {
		return auditTypeId;
	}

	public void setAuditTypeId(int auditTypeId) {
		this.auditTypeId = auditTypeId;
	}

	public AuditDTO getAudit() {
		return audit;
	}

	public void setAudit(AuditDTO audit) {
		this.audit = audit;
	}

	public Map<String, Integer> getEmployeesInScope() {
		return employeesInScope;
	}

	public void setEmployeesInScope(Map<String, Integer> employeesInScope) {
		this.employeesInScope = employeesInScope;
	}

	public void setAppBean(ApplicationManagedBean appBean) {
		this.appBean = appBean;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public List<AuditDTO> getAuditsInProgress() {
		return auditsInProgress;
	}

	public void setAuditsInProgress(List<AuditDTO> auditsInProgress) {
		this.auditsInProgress = auditsInProgress;
	}

	public boolean isAuditInProgress() {
		return auditInProgress;
	}

	public void setAuditInProgress(boolean auditInProgress) {
		this.auditInProgress = auditInProgress;
	}

}
