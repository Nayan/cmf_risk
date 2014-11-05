package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.LoanUtilizationCheck;
import com.conflux.dal.dao.ILoanUtilizationCheckDAO;
@Repository 
public class LoanUtilizationCheckDAO extends BaseDAO implements ILoanUtilizationCheckDAO { 

	@Override 
	public void persist(LoanUtilizationCheck loanUtilizationCheck) { 
		getSessionFactory().getCurrentSession().persist(loanUtilizationCheck);
	}

	@Override 
	public void merge(LoanUtilizationCheck loanUtilizationCheck) { 
		getSessionFactory().getCurrentSession().merge(loanUtilizationCheck); 

	}

	@Override
	public void delete(LoanUtilizationCheck loanUtilizationCheck) {
		getSessionFactory().getCurrentSession().delete(loanUtilizationCheck);
	}

	@Override
	public LoanUtilizationCheck findById(int loanUtilizationCheckId) {
		LoanUtilizationCheck loanUtilizationCheck = (LoanUtilizationCheck) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.LoanUtilizationCheck", loanUtilizationCheckId);
		return loanUtilizationCheck;
	}

	@Override
	public List<LoanUtilizationCheck> findAll() {
		List<LoanUtilizationCheck> loanUtilizationChecks = (List<LoanUtilizationCheck>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.LoanUtilizationCheck").list();
		return loanUtilizationChecks;
	}
}
