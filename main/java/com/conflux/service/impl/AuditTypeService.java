package com.conflux.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.conflux.dal.bo.AuditType;
import com.conflux.dal.bo.AuditTypeScoring;
import com.conflux.dal.bo.AuditTypeScoringId;
import com.conflux.dal.bo.OrgEntity;
import com.conflux.dal.bo.Question;
import com.conflux.dal.bo.RatingBucket;
import com.conflux.service.AbstractBaseService;
import com.conflux.service.IAuditTypeService;
import com.conflux.service.dto.AuditTypeDTO;
import com.conflux.service.dto.AuditTypeScoringDTO;
import com.conflux.service.dto.QuestionDTO;

@Service
public class AuditTypeService extends AbstractBaseService implements
		IAuditTypeService {

	@Override
	public boolean existsAuditTypeByName(String name, int entityTypeId) {
		AuditType auditType = getAuditTypeDAO().find(name, entityTypeId);
		if (auditType == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public List<AuditTypeDTO> getAllAuditTypes() {
		List<AuditType> auditTypes = getAuditTypeDAO().findAll();
		return populateAuditTypeDTOs(auditTypes);
	}

	@Override
	public List<AuditTypeDTO> getAllAuditTypes(int entityTypeId) {
		List<AuditType> auditTypes = getAuditTypeDAO().find(entityTypeId);
		return populateAuditTypeDTOs(auditTypes);
	}

	@Override
	public AuditTypeDTO getAuditType(int auditTypeId) {
		AuditType auditType = getAuditTypeDAO().findById(auditTypeId);
		AuditTypeDTO auditTypeDTO = getHelper().createCompleteAuditTypeDTO(
				auditType);
		return auditTypeDTO;
	}

	@Override
	public void createAuditType(AuditTypeDTO auditTypeDTO) {
		AuditType auditType = getHelper().createAuditType(auditTypeDTO);
		Date todaysDate = new Date();
		// get associated org entity and save the audit type
		OrgEntity orgEntity = getOrgEntityDAO().findById(
				auditTypeDTO.getOrgEntityId());
		auditType.setOrgEntity(orgEntity);
		auditType.setCreatedDate(todaysDate);
		auditType.setUpdatedDate(todaysDate);
		getAuditTypeDAO().persist(auditType);
		int order = 0;
		/*** Save the list of questions associated with an audit type **/
		for (QuestionDTO questionDTO : auditTypeDTO.getQuestionDTOs()) {
			questionDTO.setPosition(order++);
			Question question = getHelper().createQuestion(questionDTO);
			question.setCreatedBy(auditTypeDTO.getCreatedBy());
			question.setCreatedDate(todaysDate);
			question.setUpdatedBy(auditTypeDTO.getUpdatedBy());
			question.setUpdatedDate(todaysDate);
			question.setAuditType(auditType);
			getQuestionDAO().persist(question);
			auditType.getQuestions().add(question);
		}

		/*** Save audit type scoring objects **/
		for (int i = 0; i < 5; i++) {
			AuditTypeScoring auditTypeScoring = getHelper()
					.createAuditTypeScoring(
							auditTypeDTO.getAuditTypeScoringDTOs().get(i));
			RatingBucket ratingBucket = getRatingBucketDAO().findById(i + 1);
			auditTypeScoring.setRatingBucket(ratingBucket);
			auditTypeScoring.setAuditType(auditType);
			AuditTypeScoringId auditTypeScoringId = new AuditTypeScoringId();
			auditTypeScoringId.setAuditTypeId(auditType.getAuditTypeId());
			auditTypeScoringId.setRatingBucketId(ratingBucket
					.getRatingBucketId());
			auditTypeScoring.setId(auditTypeScoringId);
			getAuditTypeScoringDAO().persist(auditTypeScoring);
			auditType.getAuditTypeScorings().add(auditTypeScoring);
			ratingBucket.getAuditTypeScorings().add(auditTypeScoring);
		}

		getAuditTypeDAO().merge(auditType);
	}

	@Override
	public AuditTypeDTO updateAuditType(AuditTypeDTO auditTypeDTO,
			String username) {

		// populate audit questions map
		Map<Integer, QuestionDTO> updatedQuestionsMap = new HashMap<Integer, QuestionDTO>();
		// new questions to be added to this audit
		List<QuestionDTO> newQuestions = new ArrayList<QuestionDTO>();
		// Audit Type
		Date todaysDate = new Date();
		AuditType auditType = getAuditTypeDAO().findById(
				auditTypeDTO.getAuditTypeId());
		BeanUtils.copyProperties(auditTypeDTO, auditType);
		auditType.setUpdatedBy(username);
		auditType.setUpdatedDate(todaysDate);
		int order = 0;
		for (QuestionDTO questionDTO : auditTypeDTO.getQuestionDTOs()) {
			questionDTO.setPosition(order++);
			updatedQuestionsMap.put(questionDTO.getQuestionId(), questionDTO);
			if (questionDTO.getQuestionId() == 0) {
				newQuestions.add(questionDTO);
			}
		}

		// update all existing and deleted questions
		for (Question question : auditType.getQuestions()) {
			if (updatedQuestionsMap.containsKey(question.getQuestionId())) {
				QuestionDTO questionDTO = updatedQuestionsMap.get(question
						.getQuestionId());
				BeanUtils.copyProperties(questionDTO, question);
				question.setUpdatedBy(username);
				question.setUpdatedDate(todaysDate);
				getQuestionDAO().merge(question);
			} else {
				// delete this loan
				auditType.getQuestions().remove(question);
				getQuestionDAO().delete(question);
			}
		}

		// add all new questions
		for (QuestionDTO questionDTO : newQuestions) {
			Question question = new Question();
			BeanUtils.copyProperties(questionDTO, question);
			question.setQuestionId(null);
			question.setCreatedDate(todaysDate);
			question.setUpdatedDate(todaysDate);
			question.setCreatedBy(username);
			question.setUpdatedBy(username);
			question.setAuditType(auditType);
			getQuestionDAO().persist(question);
			auditType.getQuestions().add(question);
		}

		// update audit type scoring
		// populate audit questions map
		Map<Integer, AuditTypeScoringDTO> updatedAuditTypeScoringMap = new HashMap<Integer, AuditTypeScoringDTO>();
		for (AuditTypeScoringDTO auditTypeScoringDTO : auditTypeDTO
				.getAuditTypeScoringDTOs()) {
			// as each audit type scoring is associated with a different rating
			// bucket
			updatedAuditTypeScoringMap.put(
					auditTypeScoringDTO.getRatingBucketId(),
					auditTypeScoringDTO);
		}
		for (AuditTypeScoring auditTypeScoring : auditType
				.getAuditTypeScorings()) {
			AuditTypeScoringDTO auditTypeScoringDTO = updatedAuditTypeScoringMap
					.get(auditTypeScoring.getRatingBucket().getRatingBucketId());
			if (auditTypeScoringDTO.getMaxScore() != auditTypeScoring
					.getMaxScore()
					|| auditTypeScoringDTO.getMinScore() != auditTypeScoring
							.getMinScore()) {
				auditTypeScoring.setMaxScore(auditTypeScoringDTO.getMaxScore());
				auditTypeScoring.setMinScore(auditTypeScoringDTO.getMinScore());
				getAuditTypeScoringDAO().merge(auditTypeScoring);
			}

		}

		getAuditTypeDAO().merge(auditType);
		return getHelper().createCompleteAuditTypeDTO(auditType);

	}

	private List<AuditTypeDTO> populateAuditTypeDTOs(List<AuditType> auditTypes) {
		List<AuditTypeDTO> auditTypeDTOs = new ArrayList<AuditTypeDTO>();
		for (AuditType auditType : auditTypes) {
			AuditTypeDTO auditTypeDTO = getHelper().createAuditTypeDTO(
					auditType);
			auditTypeDTOs.add(auditTypeDTO);
		}
		return auditTypeDTOs;
	}

}
