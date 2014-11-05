package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.CustomerFamilyOccupation;
import com.conflux.dal.dao.ICustomerFamilyOccupationDAO;
@Repository 
public class CustomerFamilyOccupationDAO extends BaseDAO implements ICustomerFamilyOccupationDAO { 

	@Override 
	public void persist(CustomerFamilyOccupation customerFamilyOccupation) { 
		getSessionFactory().getCurrentSession().persist(customerFamilyOccupation);
	}

	@Override 
	public void merge(CustomerFamilyOccupation customerFamilyOccupation) { 
		getSessionFactory().getCurrentSession().merge(customerFamilyOccupation); 

	}

	@Override
	public void delete(CustomerFamilyOccupation customerFamilyOccupation) {
		getSessionFactory().getCurrentSession().delete(customerFamilyOccupation);
	}

	@Override
	public CustomerFamilyOccupation findById(int customerFamilyOccupationId) {
		CustomerFamilyOccupation customerFamilyOccupation = (CustomerFamilyOccupation) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.CustomerFamilyOccupation", customerFamilyOccupationId);
		return customerFamilyOccupation;
	}

	@Override
	public List<CustomerFamilyOccupation> findAll() {
		List<CustomerFamilyOccupation> customerFamilyOccupations = (List<CustomerFamilyOccupation>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.CustomerFamilyOccupation").list();
		return customerFamilyOccupations;
	}
}
