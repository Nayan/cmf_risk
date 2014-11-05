package com.conflux.service.dto;


import java.util.Date;

public class RiskRatingHistoryDTO     {

	private int riskRatingHistoryId;
	private String originalRating;
	private String updatedRating;
	private String comment;
	private String createdBy;
	private Date createdDate;
	public int getRiskRatingHistoryId() {
		return riskRatingHistoryId;
	}
	public void setRiskRatingHistoryId(int riskRatingHistoryId) {
		this.riskRatingHistoryId = riskRatingHistoryId;
	}
	public String getOriginalRating() {
		return originalRating;
	}
	public void setOriginalRating(String originalRating) {
		this.originalRating = originalRating;
	}
	public String getUpdatedRating() {
		return updatedRating;
	}
	public void setUpdatedRating(String updatedRating) {
		this.updatedRating = updatedRating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


















} 
