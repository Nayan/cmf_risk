package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.ExternalOrganization;
import com.conflux.dal.dao.IExternalOrganizationDAO;
@Repository 
public class ExternalOrganizationDAO extends BaseDAO implements IExternalOrganizationDAO { 

	@Override 
	public void persist(ExternalOrganization externalOrganization) { 
		getSessionFactory().getCurrentSession().persist(externalOrganization);
	}

	@Override 
	public void merge(ExternalOrganization externalOrganization) { 
		getSessionFactory().getCurrentSession().merge(externalOrganization); 

	}

	@Override
	public void delete(ExternalOrganization externalOrganization) {
		getSessionFactory().getCurrentSession().delete(externalOrganization);
	}

	@Override
	public ExternalOrganization findById(int externalOrganizationId) {
		ExternalOrganization externalOrganization = (ExternalOrganization) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.ExternalOrganization", externalOrganizationId);
		return externalOrganization;
	}

	@Override
	public List<ExternalOrganization> findAll() {
		List<ExternalOrganization> externalOrganizations = (List<ExternalOrganization>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.ExternalOrganization").list();
		return externalOrganizations;
	}
}
