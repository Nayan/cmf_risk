package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.UserRole;
import com.conflux.dal.dao.IUserRoleDAO;
@Repository 
public class UserRoleDAO extends BaseDAO implements IUserRoleDAO { 

	@Override 
	public void persist(UserRole userRole) { 
		getSessionFactory().getCurrentSession().persist(userRole);
	}

	@Override 
	public void merge(UserRole userRole) { 
		getSessionFactory().getCurrentSession().merge(userRole); 

	}

	@Override
	public void delete(UserRole userRole) {
		getSessionFactory().getCurrentSession().delete(userRole);
	}

	@Override
	public UserRole findById(int userRoleId) {
		UserRole userRole = (UserRole) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.UserRole", userRoleId);
		return userRole;
	}

	@Override
	public List<UserRole> findAll() {
		List<UserRole> userRoles = (List<UserRole>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.UserRole").list();
		return userRoles;
	}
}
