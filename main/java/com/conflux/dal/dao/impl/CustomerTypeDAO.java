package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.CustomerType;
import com.conflux.dal.dao.ICustomerTypeDAO;
@Repository 
public class CustomerTypeDAO extends BaseDAO implements ICustomerTypeDAO { 

	@Override 
	public void persist(CustomerType customerType) { 
		getSessionFactory().getCurrentSession().persist(customerType);
	}

	@Override 
	public void merge(CustomerType customerType) { 
		getSessionFactory().getCurrentSession().merge(customerType); 

	}

	@Override
	public void delete(CustomerType customerType) {
		getSessionFactory().getCurrentSession().delete(customerType);
	}

	@Override
	public CustomerType findById(int customerTypeId) {
		CustomerType customerType = (CustomerType) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.CustomerType", customerTypeId);
		return customerType;
	}

	@Override
	public List<CustomerType> findAll() {
		List<CustomerType> customerTypes = (List<CustomerType>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.CustomerType").list();
		return customerTypes;
	}
}
