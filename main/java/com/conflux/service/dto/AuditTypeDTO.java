package com.conflux.service.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.conflux.common.ApplicationConstants.ENTITY_TYPE;


public class AuditTypeDTO {
	private int auditTypeId;
	private String name;
	private String description;
	private String createdBy;
	private String updatedBy;
	private Date createdDate;
	private Date updatedDate;
	private int orgEntityId;
	private ENTITY_TYPE entityType;
	private List<AuditTypeScoringDTO> auditTypeScoringDTOs = new ArrayList<AuditTypeScoringDTO>(
			5);
	private List<QuestionDTO> questionDTOs = new ArrayList<QuestionDTO>();

	public AuditTypeDTO() {
		for (int i = 0; i < 5; i++) {
			auditTypeScoringDTOs.add(new AuditTypeScoringDTO());
		}
	}

	public List<AuditTypeScoringDTO> getAuditTypeScoringDTOs() {
		return auditTypeScoringDTOs;
	}

	public void setAuditTypeScoringDTOs(
			List<AuditTypeScoringDTO> auditTypeScoringDTOs) {
		this.auditTypeScoringDTOs = auditTypeScoringDTOs;
	}

	public List<QuestionDTO> getQuestionDTOs() {
		return questionDTOs;
	}

	public void setQuestionDTOs(List<QuestionDTO> questionDTOs) {
		this.questionDTOs = questionDTOs;
	}

	public int getOrgEntityId() {
		return orgEntityId;
	}

	public void setOrgEntityId(int orgEntityId) {
		this.orgEntityId = orgEntityId;
	}

	public ENTITY_TYPE getEntityType() {
		return entityType;
	}

	public void setEntityType(ENTITY_TYPE entityType) {
		this.entityType = entityType;
	}

	public int getAuditTypeId() {
		return auditTypeId;
	}

	public void setAuditTypeId(int auditTypeId) {
		this.auditTypeId = auditTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

}
