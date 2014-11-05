package com.conflux.dal.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.conflux.dal.bo.OccupationSubType;
import com.conflux.dal.dao.IOccupationSubTypeDAO;

@Repository
public class OccupationSubTypeDAO extends BaseDAO implements
		IOccupationSubTypeDAO {

	@Override
	public void persist(OccupationSubType occupationSubType) {
		getSessionFactory().getCurrentSession().persist(occupationSubType);
	}

	@Override
	public void merge(OccupationSubType occupationSubType) {
		getSessionFactory().getCurrentSession().merge(occupationSubType);

	}

	@Override
	public void delete(OccupationSubType occupationSubType) {
		getSessionFactory().getCurrentSession().delete(occupationSubType);
	}

	@Override
	public OccupationSubType findById(int occupationSubTypeId) {
		OccupationSubType occupationSubType = (OccupationSubType) getSessionFactory()
				.getCurrentSession().get(
						"com.conflux.dal.bo.OccupationSubType",
						occupationSubTypeId);
		return occupationSubType;
	}

	@Override
	public List<OccupationSubType> findAll() {
		List<OccupationSubType> occupationSubTypes = (List<OccupationSubType>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.OccupationSubType")
				.list();
		return occupationSubTypes;
	}

	@Override
	public OccupationSubType find(String name, int officeId,
			int occupationTypeId) {
		Query query = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from com.conflux.dal.bo.OccupationSubType occupationSubType where occupationSubType.name =:name and occupationSubType.office.officeId =:officeId and occupationSubType.occupationType.occupationTypeId =:occupationTypeId");
		query.setParameter("name", name);
		query.setParameter("officeId", officeId);
		query.setParameter("occupationTypeId", occupationTypeId);
		return (OccupationSubType) query.uniqueResult();
	}

	@Override
	public List<OccupationSubType> find(int officeId, int occupationTypeId) {
		Query query = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from com.conflux.dal.bo.OccupationSubType occupationSubType where occupationSubType.office.officeId =:officeId and occupationSubType.occupationType.occupationTypeId =:occupationTypeId order by occupationSubType.name");
		query.setParameter("officeId", officeId);
		query.setParameter("occupationTypeId", occupationTypeId);
		return query.list();
	}
}
