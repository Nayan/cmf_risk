package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.ExternalLoan;
import com.conflux.dal.dao.IExternalLoanDAO;
@Repository 
public class ExternalLoanDAO extends BaseDAO implements IExternalLoanDAO { 

	@Override 
	public void persist(ExternalLoan externalLoan) { 
		getSessionFactory().getCurrentSession().persist(externalLoan);
	}

	@Override 
	public void merge(ExternalLoan externalLoan) { 
		getSessionFactory().getCurrentSession().merge(externalLoan); 

	}

	@Override
	public void delete(ExternalLoan externalLoan) {
		getSessionFactory().getCurrentSession().delete(externalLoan);
	}

	@Override
	public ExternalLoan findById(int externalLoanId) {
		ExternalLoan externalLoan = (ExternalLoan) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.ExternalLoan", externalLoanId);
		return externalLoan;
	}

	@Override
	public List<ExternalLoan> findAll() {
		List<ExternalLoan> externalLoans = (List<ExternalLoan>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.ExternalLoan").list();
		return externalLoans;
	}
}
