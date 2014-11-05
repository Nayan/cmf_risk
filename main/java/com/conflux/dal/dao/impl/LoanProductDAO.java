package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.LoanProduct;
import com.conflux.dal.dao.ILoanProductDAO;
@Repository 
public class LoanProductDAO extends BaseDAO implements ILoanProductDAO { 

	@Override 
	public void persist(LoanProduct loanProduct) { 
		getSessionFactory().getCurrentSession().persist(loanProduct);
	}

	@Override 
	public void merge(LoanProduct loanProduct) { 
		getSessionFactory().getCurrentSession().merge(loanProduct); 

	}

	@Override
	public void delete(LoanProduct loanProduct) {
		getSessionFactory().getCurrentSession().delete(loanProduct);
	}

	@Override
	public LoanProduct findById(int loanProductId) {
		LoanProduct loanProduct = (LoanProduct) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.LoanProduct", loanProductId);
		return loanProduct;
	}

	@Override
	public List<LoanProduct> findAll() {
		List<LoanProduct> loanProducts = (List<LoanProduct>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.LoanProduct").list();
		return loanProducts;
	}
}
