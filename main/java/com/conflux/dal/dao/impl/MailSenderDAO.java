package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.MailSender;
import com.conflux.dal.dao.IMailSenderDAO;
@Repository 
public class MailSenderDAO extends BaseDAO implements IMailSenderDAO { 

	@Override 
	public void persist(MailSender mailSender) { 
		getSessionFactory().getCurrentSession().persist(mailSender);
	}

	@Override 
	public void merge(MailSender mailSender) { 
		getSessionFactory().getCurrentSession().merge(mailSender); 

	}

	@Override
	public void delete(MailSender mailSender) {
		getSessionFactory().getCurrentSession().delete(mailSender);
	}

	@Override
	public MailSender findById(int mailSenderId) {
		MailSender mailSender = (MailSender) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.MailSender", mailSenderId);
		return mailSender;
	}

	@Override
	public List<MailSender> findAll() {
		List<MailSender> mailSenders = (List<MailSender>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.MailSender").list();
		return mailSenders;
	}
}
