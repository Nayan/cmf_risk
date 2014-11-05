package com.conflux.service.dto;

public class AuditTypeScoringDTO {

	private int minScore;
	private int maxScore;
	private int ratingBucketId;
	private int auditTypeId;
	private RatingBucketDTO ratingBucketDTO;

	public int getRatingBucketId() {
		return ratingBucketId;
	}

	public void setRatingBucketId(int ratingBucketId) {
		this.ratingBucketId = ratingBucketId;
	}

	public int getAuditTypeId() {
		return auditTypeId;
	}

	public void setAuditTypeId(int auditTypeId) {
		this.auditTypeId = auditTypeId;
	}

	public RatingBucketDTO getRatingBucketDTO() {
		return ratingBucketDTO;
	}

	public void setRatingBucketDTO(RatingBucketDTO ratingBucketDTO) {
		this.ratingBucketDTO = ratingBucketDTO;
	}

	public int getMinScore() {
		return minScore;
	}

	public void setMinScore(int minScore) {
		this.minScore = minScore;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

}
