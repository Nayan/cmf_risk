package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.LoanPurpose;
import com.conflux.dal.dao.ILoanPurposeDAO;
@Repository 
public class LoanPurposeDAO extends BaseDAO implements ILoanPurposeDAO { 

	@Override 
	public void persist(LoanPurpose loanPurpose) { 
		getSessionFactory().getCurrentSession().persist(loanPurpose);
	}

	@Override 
	public void merge(LoanPurpose loanPurpose) { 
		getSessionFactory().getCurrentSession().merge(loanPurpose); 

	}

	@Override
	public void delete(LoanPurpose loanPurpose) {
		getSessionFactory().getCurrentSession().delete(loanPurpose);
	}

	@Override
	public LoanPurpose findById(int loanPurposeId) {
		LoanPurpose loanPurpose = (LoanPurpose) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.LoanPurpose", loanPurposeId);
		return loanPurpose;
	}

	@Override
	public List<LoanPurpose> findAll() {
		List<LoanPurpose> loanPurposes = (List<LoanPurpose>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.LoanPurpose").list();
		return loanPurposes;
	}
}
