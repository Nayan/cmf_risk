package com.conflux.dal.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.conflux.dal.bo.Employee;
import com.conflux.dal.bo.LoanAccount;
import com.conflux.dal.dao.ILoanAccountDAO;

@Repository
public class LoanAccountDAO extends BaseDAO implements ILoanAccountDAO {

	@Override
	public void persist(LoanAccount loanAccount) {
		getSessionFactory().getCurrentSession().persist(loanAccount);
	}

	@Override
	public void merge(LoanAccount loanAccount) {
		getSessionFactory().getCurrentSession().merge(loanAccount);

	}

	@Override
	public void delete(LoanAccount loanAccount) {
		getSessionFactory().getCurrentSession().delete(loanAccount);
	}

	@Override
	public LoanAccount findById(int loanAccountId) {
		LoanAccount loanAccount = (LoanAccount) getSessionFactory()
				.getCurrentSession().get("com.conflux.dal.bo.LoanAccount",
						loanAccountId);
		return loanAccount;
	}

	@Override
	public List<LoanAccount> findAll() {
		List<LoanAccount> loanAccounts = (List<LoanAccount>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.LoanAccount").list();
		return loanAccounts;
	}

	@Override
	public List<LoanAccount> findAllPendingLUC(int centreId) {
		Query query = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from com.conflux.dal.bo.LoanAccount loanAccount where loanAccount.customer.customer.customerId =:centreId and loanAccount.pendingLuc =true order by loanAccount.customer.displayName");
		query.setParameter("centreId", centreId);
		return query.list();
	}
}
