package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.Office;
import com.conflux.dal.dao.IOfficeDAO;
@Repository 
public class OfficeDAO extends BaseDAO implements IOfficeDAO { 

	@Override 
	public void persist(Office office) { 
		getSessionFactory().getCurrentSession().persist(office);
	}

	@Override 
	public void merge(Office office) { 
		getSessionFactory().getCurrentSession().merge(office); 

	}

	@Override
	public void delete(Office office) {
		getSessionFactory().getCurrentSession().delete(office);
	}

	@Override
	public Office findById(int officeId) {
		Office office = (Office) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.Office", officeId);
		return office;
	}

	@Override
	public List<Office> findAll() {
		List<Office> offices = (List<Office>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.Office").list();
		return offices;
	}
}
