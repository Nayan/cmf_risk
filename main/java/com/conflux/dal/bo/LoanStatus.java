package com.conflux.dal.bo;

// Generated 17 Jan, 2013 8:44:06 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * LoanStatus generated by hbm2java
 */
@Entity
@Table(name = "loan_status", catalog = "risk_module")
public class LoanStatus implements java.io.Serializable {

	private Integer loanStatusId;
	private String name;
	private Set<LoanAccount> loanAccounts = new HashSet<LoanAccount>(0);

	public LoanStatus() {
	}

	public LoanStatus(String name, Set<LoanAccount> loanAccounts) {
		this.name = name;
		this.loanAccounts = loanAccounts;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "loan_status_id", unique = true, nullable = false)
	public Integer getLoanStatusId() {
		return this.loanStatusId;
	}

	public void setLoanStatusId(Integer loanStatusId) {
		this.loanStatusId = loanStatusId;
	}

	@Column(name = "name", length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "loanStatus")
	public Set<LoanAccount> getLoanAccounts() {
		return this.loanAccounts;
	}

	public void setLoanAccounts(Set<LoanAccount> loanAccounts) {
		this.loanAccounts = loanAccounts;
	}

}
