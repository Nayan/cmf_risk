package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.OrgEntity;
import com.conflux.dal.dao.IOrgEntityDAO;
@Repository 
public class OrgEntityDAO extends BaseDAO implements IOrgEntityDAO { 

	@Override 
	public void persist(OrgEntity orgEntity) { 
		getSessionFactory().getCurrentSession().persist(orgEntity);
	}

	@Override 
	public void merge(OrgEntity orgEntity) { 
		getSessionFactory().getCurrentSession().merge(orgEntity); 

	}

	@Override
	public void delete(OrgEntity orgEntity) {
		getSessionFactory().getCurrentSession().delete(orgEntity);
	}

	@Override
	public OrgEntity findById(int orgEntityId) {
		OrgEntity orgEntity = (OrgEntity) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.OrgEntity", orgEntityId);
		return orgEntity;
	}

	@Override
	public List<OrgEntity> findAll() {
		List<OrgEntity> orgEntitys = (List<OrgEntity>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.OrgEntity").list();
		return orgEntitys;
	}
}
