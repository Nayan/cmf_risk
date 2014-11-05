package com.conflux.service.dto;

import java.util.Date;
import java.util.List;

import com.conflux.common.ApplicationConstants.ENTITY_TYPE;

public class AuditDTO {

	private int auditId;
	private String name;
	private String description;
	private int auditTotalScore;
	private boolean active;
	private String auditorComments;
	private String actionPlan;
	private String actionTaken;
	private String createdBy;
	private String updatedBy;
	private Date followUpDate;
	private Date startDate;
	private Date endDate;
	private Date updatedDate;

	/** custom attributes **/
	private AuditTypeDTO auditTypeDTO;
	private ENTITY_TYPE entityType;
	private int entityId;
	private String entityName;
	private int officeId;
	private String officeName;
	private List<AuditQuestionDTO> auditQuestionDTOs;
	private RatingBucketDTO ratingBucketDTO;
	private String actionOwner;
	private int actionOwnerId;

	public AuditTypeDTO getAuditTypeDTO() {
		return auditTypeDTO;
	}

	public void setAuditTypeDTO(AuditTypeDTO auditTypeT) {
		this.auditTypeDTO = auditTypeT;
	}

	public List<AuditQuestionDTO> getAuditQuestionDTOs() {
		return auditQuestionDTOs;
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

	public void setAuditQuestionDTOs(List<AuditQuestionDTO> auditQuestionDTOs) {
		this.auditQuestionDTOs = auditQuestionDTOs;
	}

	public RatingBucketDTO getRatingBucketDTO() {
		return ratingBucketDTO;
	}

	public void setRatingBucketDTO(RatingBucketDTO ratingBucketDTO) {
		this.ratingBucketDTO = ratingBucketDTO;
	}

	public ENTITY_TYPE getEntityType() {
		return entityType;
	}

	public void setEntityType(ENTITY_TYPE entityType) {
		this.entityType = entityType;
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public int getAuditId() {
		return auditId;
	}

	public void setAuditId(int auditId) {
		this.auditId = auditId;
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

	public int getAuditTotalScore() {
		return auditTotalScore;
	}

	public void setAuditTotalScore(int auditTotalScore) {
		this.auditTotalScore = auditTotalScore;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getAuditorComments() {
		return auditorComments;
	}

	public void setAuditorComments(String auditorComments) {
		this.auditorComments = auditorComments;
	}

	public String getActionPlan() {
		return actionPlan;
	}

	public void setActionPlan(String actionPlan) {
		this.actionPlan = actionPlan;
	}

	public String getActionTaken() {
		return actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getActionOwner() {
		return actionOwner;
	}

	public void setActionOwner(String actionOwner) {
		this.actionOwner = actionOwner;
	}

	public Date getFollowUpDate() {
		return followUpDate;
	}

	public void setFollowUpDate(Date followUpDate) {
		this.followUpDate = followUpDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public int getActionOwnerId() {
		return actionOwnerId;
	}

	public void setActionOwnerId(int actionOwnerId) {
		this.actionOwnerId = actionOwnerId;
	}

}
