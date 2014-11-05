package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.OccupationType;
import com.conflux.dal.dao.IOccupationTypeDAO;
@Repository 
public class OccupationTypeDAO extends BaseDAO implements IOccupationTypeDAO { 

	@Override 
	public void persist(OccupationType occupationType) { 
		getSessionFactory().getCurrentSession().persist(occupationType);
	}

	@Override 
	public void merge(OccupationType occupationType) { 
		getSessionFactory().getCurrentSession().merge(occupationType); 

	}

	@Override
	public void delete(OccupationType occupationType) {
		getSessionFactory().getCurrentSession().delete(occupationType);
	}

	@Override
	public OccupationType findById(int occupationTypeId) {
		OccupationType occupationType = (OccupationType) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.OccupationType", occupationTypeId);
		return occupationType;
	}

	@Override
	public List<OccupationType> findAll() {
		List<OccupationType> occupationTypes = (List<OccupationType>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.OccupationType ot where ot.active=true").list();
		return occupationTypes;
	}
}
