package com.conflux.dal.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.conflux.dal.bo.PortalUser;
import com.conflux.dal.dao.IPortalUserDAO;

@Repository
public class PortalUserDAO extends BaseDAO implements IPortalUserDAO {

	@Override
	public void persist(PortalUser portalUser) {
		getSessionFactory().getCurrentSession().persist(portalUser);
	}

	@Override
	public void merge(PortalUser portalUser) {
		getSessionFactory().getCurrentSession().merge(portalUser);

	}

	@Override
	public void delete(PortalUser portalUser) {
		getSessionFactory().getCurrentSession().delete(portalUser);
	}

	@Override
	public PortalUser findById(int portalUserId) {
		PortalUser portalUser = (PortalUser) getSessionFactory()
				.getCurrentSession().get("com.conflux.dal.bo.PortalUser",
						portalUserId);
		return portalUser;
	}

	@Override
	public List<PortalUser> findAll() {
		List<PortalUser> portalUsers = (List<PortalUser>) getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from com.conflux.dal.bo.PortalUser user where user.active=true")
				.list();
		return portalUsers;
	}

	@Override
	public PortalUser findByUsername(String username) {
		Query query = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from com.conflux.dal.bo.PortalUser user where user.username =:username");
		query.setParameter("username", username);
		PortalUser portalUser = (PortalUser) query.uniqueResult();
		return portalUser;
	}
}
