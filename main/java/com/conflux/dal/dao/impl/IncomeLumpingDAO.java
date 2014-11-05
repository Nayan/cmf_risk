package com.conflux.dal.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.Asset;
import com.conflux.dal.bo.IncomeLumping;
import com.conflux.dal.dao.IAssetDAO;
import com.conflux.dal.dao.IIncomeLumpingDAO;

@Repository
public class IncomeLumpingDAO extends BaseDAO implements IIncomeLumpingDAO {

	@Override
	public void persist(IncomeLumping incomeLumping) {
		getSessionFactory().getCurrentSession().persist(incomeLumping);		
	}

	@Override
	public void merge(IncomeLumping incomeLumping) {
		getSessionFactory().getCurrentSession().merge(incomeLumping);
	}

	@Override
	public void delete(IncomeLumping incomeLumping) {
		getSessionFactory().getCurrentSession().delete(incomeLumping);
	}

	@Override
	public List<IncomeLumping> findAll() {
		List<IncomeLumping> incomeLumpings = getSessionFactory().getCurrentSession().createQuery("from com.conflux.dal.bo.IncomeLumping").list();
		return incomeLumpings;
	}

	@Override
	public IncomeLumping findById(int incomeLumpingId) {
		IncomeLumping incomeLumping = (IncomeLumping) getSessionFactory().getCurrentSession().get("com.conflux.dal.bo.IncomeLumping", incomeLumpingId);
		return incomeLumping;
	}

}
