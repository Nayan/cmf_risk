package com.conflux.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.conflux.common.ApplicationConstants.ENTITY_TYPE;
import com.conflux.dal.bo.Audit;
import com.conflux.dal.bo.AuditQuestion;
import com.conflux.dal.bo.AuditType;
import com.conflux.dal.bo.AuditTypeScoring;
import com.conflux.dal.bo.Customer;
import com.conflux.dal.bo.Employee;
import com.conflux.dal.bo.RatingBucket;
import com.conflux.service.AbstractBaseService;
import com.conflux.service.IAuditService;
import com.conflux.service.dto.AuditDTO;
import com.conflux.service.dto.AuditQuestionDTO;

@Service
public class AuditService extends AbstractBaseService implements IAuditService {

	@Override
	public void save(AuditDTO auditDTO, Boolean complete) {
		// set audit trail
		Date todaysDate = new Date();
		auditDTO.setStartDate(todaysDate);
		auditDTO.setUpdatedDate(todaysDate);
		Audit audit = getHelper().createAudit(auditDTO);
		// get associated audit typeT
		AuditType auditType = getAuditTypeDAO().findById(
				auditDTO.getAuditTypeDTO().getAuditTypeId());
		audit.setAuditType(auditType);
		// save audit and associated audit Type
		getAuditDAO().persist(audit);
		// update audit type
		getAuditTypeDAO().merge(auditType);

		/*** save all audit questions ***/
		for (AuditQuestionDTO auditQuestionDTO : auditDTO
				.getAuditQuestionDTOs()) {
			AuditQuestion auditQuestion = getHelper().createAuditQuestion(
					auditQuestionDTO);
			auditQuestion.setAudit(audit);
			// check for negative scores
			if (auditQuestionDTO.isNegativeScore()) {
				if (auditQuestionDTO.isNegativeQuestionTicked()) {
					auditQuestion.setObtainedScore(auditQuestionDTO
							.getDefaultScore());
				} else {
					auditQuestion.setObtainedScore(0);
				}
			}
			getAuditQuestionDAO().persist(auditQuestion);
			audit.getAuditQuestions().add(auditQuestion);
		}

		/** set action plan owner **/
		if (auditDTO.getActionOwnerId() != 0) {
			Employee employee = getEmployeeDAO().findById(
					auditDTO.getActionOwnerId());
			audit.setEmployee(employee);
			employee.getAudits_1().add(audit);
			getEmployeeDAO().merge(employee);
		}

		/** map audit to an entity **/
		if (auditDTO.getEntityType() == ENTITY_TYPE.BRANCH_MANAGER
				|| auditDTO.getEntityType() == ENTITY_TYPE.LOAN_OFFICER) {
			Employee employee = getEmployeeDAO().findById(
					auditDTO.getEntityId());
			audit.getEmployees().add(employee);
			employee.getAudits().add(audit);
			getEmployeeDAO().merge(employee);
			audit.setOffice(employee.getOffice());
			getAuditDAO().merge(audit);
		} else {
			Customer customer = getCustomerDAO().findById(
					auditDTO.getEntityId());
			audit.getCustomers().add(customer);
			customer.getAudits().add(audit);
			getCustomerDAO().merge(customer);
			audit.setOffice(customer.getOffice());
			getAuditDAO().merge(audit);
		}

		/*** optionally mark audit as complete **/
		if (complete) {
			audit.setEndDate(todaysDate);
			audit.setUpdatedBy(audit.getCreatedBy());

			saveAuditScore(auditDTO, audit, auditType);

		}

	}

	@Override
	public List<AuditDTO> getAuditsInProgress(int officeId) {
		List<Audit> audits = getAuditDAO().findAllActive(officeId);
		List<AuditDTO> auditDTOs = new ArrayList<AuditDTO>();
		for (Audit audit : audits) {
			AuditDTO auditDTO = getHelper().createAuditDTO(audit);
			auditDTOs.add(auditDTO);
		}
		return auditDTOs;
	}

	@Override
	public AuditDTO getAudit(int auditId) {
		Audit audit = getAuditDAO().findById(auditId);
		return getHelper().createCompleteAuditDTO(audit);
	}

	@Override
	public void update(AuditDTO auditDTO, Boolean complete) {
		// set audit trail
		Date todaysDate = new Date();
		Audit audit = getAuditDAO().findById(auditDTO.getAuditId());
		BeanUtils.copyProperties(auditDTO, audit);
		audit.setUpdatedDate(todaysDate);
		// update audit
		getAuditDAO().merge(audit);

		// get associated audit typeT
		AuditType auditType = getAuditTypeDAO().findById(
				auditDTO.getAuditTypeDTO().getAuditTypeId());

		/*** merge all audit questions ***/
		for (AuditQuestionDTO auditQuestionDTO : auditDTO
				.getAuditQuestionDTOs()) {
			AuditQuestion auditQuestion = getAuditQuestionDAO().findById(
					auditQuestionDTO.getAuditQuestionId());
			BeanUtils.copyProperties(auditQuestionDTO, auditQuestion);
			// check for negative scores
			if (auditQuestionDTO.isNegativeScore()) {
				if (auditQuestionDTO.isNegativeQuestionTicked()) {
					auditQuestion.setObtainedScore(auditQuestionDTO
							.getDefaultScore());
				} else {
					auditQuestion.setObtainedScore(0);
				}
			}
			getAuditQuestionDAO().merge(auditQuestion);
		}

		/*** optionally mark audit as complete **/
		if (complete) {
			audit.setEndDate(todaysDate);

			saveAuditScore(auditDTO, audit, auditType);

		}
	}

	private void saveAuditScore(AuditDTO auditDTO, Audit audit,
			AuditType auditType) {
		int auditScore = 0;
		// calculate the audit score
		for (AuditQuestionDTO auditQuestionDTO : auditDTO
				.getAuditQuestionDTOs()) {
			auditScore = auditScore + auditQuestionDTO.getObtainedScore();
		}

		// calculate the rating bucket
		RatingBucket ratingBucket = new RatingBucket();
		for (AuditTypeScoring auditTypeScoring : auditType
				.getAuditTypeScorings()) {
			if ((auditTypeScoring.getMaxScore() >= auditScore)
					&& (auditScore >= auditTypeScoring.getMinScore())) {
				ratingBucket = auditTypeScoring.getRatingBucket();
				break;
			}
		}

		audit.setAuditTotalScore(auditScore);
		audit.setActive(false);
		audit.setRatingBucket(ratingBucket);
		ratingBucket.getAudits().add(audit);

		getAuditDAO().merge(audit);
		getRatingBucketDAO().merge(ratingBucket);
	}

}
