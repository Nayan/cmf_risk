package com.conflux.service.dto;


import java.util.Date;

public class QuestionDTO implements Comparable<QuestionDTO>    {

	private int questionId;
	private String questionString;
	private int position;
	private boolean negativeScore;
	private int minScore;
	private int maxScore;
	private int defaultScore;
	private boolean active;
	private String createdBy;
	private String updatedBy;
	private Date createdDate;
	private Date updatedDate;
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
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
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(QuestionDTO o) {
		return(position - o.position);
	}

} 
