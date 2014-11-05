package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.AuditTypeScoring;
import com.conflux.dal.dao.IAuditTypeScoringDAO;
@Repository 
public class AuditTypeScoringDAO extends BaseDAO implements IAuditTypeScoringDAO { 

	@Override 
	public void persist(AuditTypeScoring auditTypeScoring) { 
		getSessionFactory().getCurrentSession().persist(auditTypeScoring);
	}

	@Override 
	public void merge(AuditTypeScoring auditTypeScoring) { 
		getSessionFactory().getCurrentSession().merge(auditTypeScoring); 

	}

	@Override
	public void delete(AuditTypeScoring auditTypeScoring) {
		getSessionFactory().getCurrentSession().delete(auditTypeScoring);
	}

	@Override
	public AuditTypeScoring findById(int auditTypeScoringId) {
		AuditTypeScoring auditTypeScoring = (AuditTypeScoring) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.AuditTypeScoring", auditTypeScoringId);
		return auditTypeScoring;
	}

	@Override
	public List<AuditTypeScoring> findAll() {
		List<AuditTypeScoring> auditTypeScorings = (List<AuditTypeScoring>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.AuditTypeScoring").list();
		return auditTypeScorings;
	}
}
