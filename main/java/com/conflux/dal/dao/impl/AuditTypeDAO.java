package com.conflux.dal.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.AuditType;
import com.conflux.dal.dao.IAuditTypeDAO;

@Repository
public class AuditTypeDAO extends BaseDAO implements IAuditTypeDAO {

	@Override
	public void persist(AuditType auditType) {
		getSessionFactory().getCurrentSession().persist(auditType);
	}

	@Override
	public void merge(AuditType auditType) {
		getSessionFactory().getCurrentSession().merge(auditType);

	}

	@Override
	public void delete(AuditType auditType) {
		getSessionFactory().getCurrentSession().delete(auditType);
	}

	@Override
	public AuditType findById(int auditTypeId) {
		AuditType auditType = (AuditType) getSessionFactory()
				.getCurrentSession().get("com.conflux.dal.bo.AuditType",
						auditTypeId);
		return auditType;
	}

	@Override
	public List<AuditType> findAll() {
		List<AuditType> auditTypes = (List<AuditType>) getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from com.conflux.dal.bo.AuditType auditType order by auditType.name")
				.list();
		return auditTypes;
	}

	@Override
	public AuditType find(String name, int entityTypeId) {
		Query query = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from com.conflux.dal.bo.AuditType auditType where auditType.name =:name and auditType.orgEntity.orgEntityId =:entityTypeId");
		query.setParameter("name", name);
		query.setParameter("entityTypeId", entityTypeId);
		return (AuditType) query.uniqueResult();
	}

	@Override
	public List<AuditType> find(int entityTypeId) {
		Query query = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from com.conflux.dal.bo.AuditType auditType where auditType.orgEntity.orgEntityId =:entityTypeId order by auditType.name");
		query.setParameter("entityTypeId", entityTypeId);
		return query.list();
	}
}
