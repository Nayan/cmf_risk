package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.CustomerFamilyMember;
import com.conflux.dal.dao.ICustomerFamilyMemberDAO;
@Repository 
public class CustomerFamilyMemberDAO extends BaseDAO implements ICustomerFamilyMemberDAO { 

	@Override 
	public void persist(CustomerFamilyMember customerFamilyMember) { 
		getSessionFactory().getCurrentSession().persist(customerFamilyMember);
	}

	@Override 
	public void merge(CustomerFamilyMember customerFamilyMember) { 
		getSessionFactory().getCurrentSession().merge(customerFamilyMember); 

	}

	@Override
	public void delete(CustomerFamilyMember customerFamilyMember) {
		getSessionFactory().getCurrentSession().delete(customerFamilyMember);
	}

	@Override
	public CustomerFamilyMember findById(int customerFamilyMemberId) {
		CustomerFamilyMember customerFamilyMember = (CustomerFamilyMember) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.CustomerFamilyMember", customerFamilyMemberId);
		return customerFamilyMember;
	}

	@Override
	public List<CustomerFamilyMember> findAll() {
		List<CustomerFamilyMember> customerFamilyMembers = (List<CustomerFamilyMember>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.CustomerFamilyMember").list();
		return customerFamilyMembers;
	}
}
