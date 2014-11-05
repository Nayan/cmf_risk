package com.conflux.service.dto;

import java.util.Map;

public class AuditQuestionDTO {

	private int auditQuestionId;
	private String questionString;
	private int position;
	private boolean negativeScore;
	private int minScore;
	private int maxScore;
	private int defaultScore;
	private int obtainedScore;
	private boolean negativeQuestionTicked;

	/** custom properties **/
	private Map<String, Integer> applicableScoreRange;

	public Map<String, Integer> getApplicableScoreRange() {
		return applicableScoreRange;
	}

	public void setApplicableScoreRange(
			Map<String, Integer> applicableScoreRange) {
		this.applicableScoreRange = applicableScoreRange;
	}

	public int getAuditQuestionId() {
		return auditQuestionId;
	}

	public void setAuditQuestionId(int auditQuestionId) {
		this.auditQuestionId = auditQuestionId;
	}

	public String getQuestionString() {
		return questionString;
	}

	public void setQuestionString(String questionString) {
		this.questionString = questionString;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean isNegativeScore() {
		return negativeScore;
	}

	public void setNegativeScore(boolean negativeScore) {
		this.negativeScore = negativeScore;
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

	public int getDefaultScore() {
		return defaultScore;
	}

	public void setDefaultScore(int defaultScore) {
		this.defaultScore = defaultScore;
	}

	public int getObtainedScore() {
		return obtainedScore;
	}

	public void setObtainedScore(int obtainedScore) {
		this.obtainedScore = obtainedScore;
	}

	public boolean isNegativeQuestionTicked() {
		return negativeQuestionTicked;
	}

	public void setNegativeQuestionTicked(boolean negativeQuestionTicked) {
		this.negativeQuestionTicked = negativeQuestionTicked;
	}

}
