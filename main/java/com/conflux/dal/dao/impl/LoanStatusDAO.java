package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.LoanStatus;
import com.conflux.dal.dao.ILoanStatusDAO;
@Repository 
public class LoanStatusDAO extends BaseDAO implements ILoanStatusDAO { 

	@Override 
	public void persist(LoanStatus loanStatus) { 
		getSessionFactory().getCurrentSession().persist(loanStatus);
	}

	@Override 
	public void merge(LoanStatus loanStatus) { 
		getSessionFactory().getCurrentSession().merge(loanStatus); 

	}

	@Override
	public void delete(LoanStatus loanStatus) {
		getSessionFactory().getCurrentSession().delete(loanStatus);
	}

	@Override
	public LoanStatus findById(int loanStatusId) {
		LoanStatus loanStatus = (LoanStatus) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.LoanStatus", loanStatusId);
		return loanStatus;
	}

	@Override
	public List<LoanStatus> findAll() {
		List<LoanStatus> loanStatuss = (List<LoanStatus>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.LoanStatus").list();
		return loanStatuss;
	}
}
