package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.Question;
import com.conflux.dal.dao.IQuestionDAO;
@Repository 
public class QuestionDAO extends BaseDAO implements IQuestionDAO { 

	@Override 
	public void persist(Question question) { 
		getSessionFactory().getCurrentSession().persist(question);
	}

	@Override 
	public void merge(Question question) { 
		getSessionFactory().getCurrentSession().merge(question); 

	}

	@Override
	public void delete(Question question) {
		getSessionFactory().getCurrentSession().delete(question);
	}

	@Override
	public Question findById(int questionId) {
		Question question = (Question) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.Question", questionId);
		return question;
	}

	@Override
	public List<Question> findAll() {
		List<Question> questions = (List<Question>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.Question").list();
		return questions;
	}
}
