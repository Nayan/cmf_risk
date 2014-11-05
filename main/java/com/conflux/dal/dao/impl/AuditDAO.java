package com.conflux.dal.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.Audit;
import com.conflux.dal.bo.OccupationSubType;
import com.conflux.dal.dao.IAuditDAO;

@Repository
public class AuditDAO extends BaseDAO implements IAuditDAO {

	@Override
	public void persist(Audit audit) {
		getSessionFactory().getCurrentSession().persist(audit);
	}

	@Override
	public void merge(Audit audit) {
		getSessionFactory().getCurrentSession().merge(audit);

	}

	@Override
	public void delete(Audit audit) {
		getSessionFactory().getCurrentSession().delete(audit);
	}

	@Override
	public Audit findById(int auditId) {
		Audit audit = (Audit) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.Audit", auditId);
		return audit;
	}

	@Override
	public List<Audit> findAll() {
		List<Audit> audits = (List<Audit>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.Audit").list();
		return audits;
	}

	@Override
	public List<Audit> findAllActive(int officeId) {
		Query query = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from com.conflux.dal.bo.Audit audit where audit.office.officeId =:officeId and audit.active = true order by audit.startDate");
		query.setParameter("officeId", officeId);
		return query.list();
	}
}
