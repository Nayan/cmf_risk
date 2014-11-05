package com.conflux.dal.bo;

// Generated 17 Jan, 2013 8:44:06 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * AuditQuestion generated by hbm2java
 */
@Entity
@Table(name = "audit_question", catalog = "risk_module")
public class AuditQuestion implements java.io.Serializable {

	private Integer auditQuestionId;
	private Audit audit;
	private String questionString;
	private Integer position;
	private Boolean negativeScore;
	private Integer minScore;
	private Integer maxScore;
	private Integer defaultScore;
	private Integer obtainedScore;

	public AuditQuestion() {
	}

	public AuditQuestion(Audit audit) {
		this.audit = audit;
	}

	public AuditQuestion(Audit audit, String questionString, Integer position,
			Boolean negativeScore, Integer minScore, Integer maxScore,
			Integer defaultScore, Integer obtainedScore) {
		this.audit = audit;
		this.questionString = questionString;
		this.position = position;
		this.negativeScore = negativeScore;
		this.minScore = minScore;
		this.maxScore = maxScore;
		this.defaultScore = defaultScore;
		this.obtainedScore = obtainedScore;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "audit_question_id", unique = true, nullable = false)
	public Integer getAuditQuestionId() {
		return this.auditQuestionId;
	}

	public void setAuditQuestionId(Integer auditQuestionId) {
		this.auditQuestionId = auditQuestionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "audit_id", nullable = false)
	public Audit getAudit() {
		return this.audit;
	}

	public void setAudit(Audit audit) {
		this.audit = audit;
	}

	@Column(name = "question_string", length = 500)
	public String getQuestionString() {
		return this.questionString;
	}

	public void setQuestionString(String questionString) {
		this.questionString = questionString;
	}

	@Column(name = "position")
	public Integer getPosition() {
		return this.position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	@Column(name = "negative_score")
	public Boolean getNegativeScore() {
		return this.negativeScore;
	}

	public void setNegativeScore(Boolean negativeScore) {
		this.negativeScore = negativeScore;
	}

	@Column(name = "min_score")
	public Integer getMinScore() {
		return this.minScore;
	}

	public void setMinScore(Integer minScore) {
		this.minScore = minScore;
	}

	@Column(name = "max_score")
	public Integer getMaxScore() {
		return this.maxScore;
	}

	public void setMaxScore(Integer maxScore) {
		this.maxScore = maxScore;
	}

	@Column(name = "default_score")
	public Integer getDefaultScore() {
		return this.defaultScore;
	}

	public void setDefaultScore(Integer defaultScore) {
		this.defaultScore = defaultScore;
	}

	@Column(name = "obtained_score")
	public Integer getObtainedScore() {
		return this.obtainedScore;
	}

	public void setObtainedScore(Integer obtainedScore) {
		this.obtainedScore = obtainedScore;
	}

}
