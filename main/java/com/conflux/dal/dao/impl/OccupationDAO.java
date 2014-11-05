package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.Occupation;
import com.conflux.dal.dao.IOccupationDAO;
@Repository 
public class OccupationDAO extends BaseDAO implements IOccupationDAO { 

	@Override 
	public void persist(Occupation occupation) { 
		getSessionFactory().getCurrentSession().persist(occupation);
	}

	@Override 
	public void merge(Occupation occupation) { 
		getSessionFactory().getCurrentSession().merge(occupation); 

	}

	@Override
	public void delete(Occupation occupation) {
		getSessionFactory().getCurrentSession().delete(occupation);
	}

	@Override
	public Occupation findById(int occupationId) {
		Occupation occupation = (Occupation) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.Occupation", occupationId);
		return occupation;
	}

	@Override
	public List<Occupation> findAll() {
		List<Occupation> occupations = (List<Occupation>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.Occupation").list();
		return occupations;
	}
}
