package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.AuditQuestion;
import com.conflux.dal.dao.IAuditQuestionDAO;
@Repository 
public class AuditQuestionDAO extends BaseDAO implements IAuditQuestionDAO { 

	@Override 
	public void persist(AuditQuestion auditQuestion) { 
		getSessionFactory().getCurrentSession().persist(auditQuestion);
	}

	@Override 
	public void merge(AuditQuestion auditQuestion) { 
		getSessionFactory().getCurrentSession().merge(auditQuestion); 

	}

	@Override
	public void delete(AuditQuestion auditQuestion) {
		getSessionFactory().getCurrentSession().delete(auditQuestion);
	}

	@Override
	public AuditQuestion findById(int auditQuestionId) {
		AuditQuestion auditQuestion = (AuditQuestion) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.AuditQuestion", auditQuestionId);
		return auditQuestion;
	}

	@Override
	public List<AuditQuestion> findAll() {
		List<AuditQuestion> auditQuestions = (List<AuditQuestion>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.AuditQuestion").list();
		return auditQuestions;
	}
}
