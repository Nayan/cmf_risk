package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.OfficeType;
import com.conflux.dal.dao.IOfficeTypeDAO;
@Repository 
public class OfficeTypeDAO extends BaseDAO implements IOfficeTypeDAO { 

	@Override 
	public void persist(OfficeType officeType) { 
		getSessionFactory().getCurrentSession().persist(officeType);
	}

	@Override 
	public void merge(OfficeType officeType) { 
		getSessionFactory().getCurrentSession().merge(officeType); 

	}

	@Override
	public void delete(OfficeType officeType) {
		getSessionFactory().getCurrentSession().delete(officeType);
	}

	@Override
	public OfficeType findById(int officeTypeId) {
		OfficeType officeType = (OfficeType) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.OfficeType", officeTypeId);
		return officeType;
	}

	@Override
	public List<OfficeType> findAll() {
		List<OfficeType> officeTypes = (List<OfficeType>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.OfficeType").list();
		return officeTypes;
	}
}
