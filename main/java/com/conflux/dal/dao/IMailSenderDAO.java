package com.conflux.dal.dao;import java.util.List;import com.conflux.dal.bo.MailSender;public interface IMailSenderDAO {	public abstract void persist(MailSender mailSender);	public abstract void merge(MailSender mailSender);	public abstract void delete(MailSender mailSender);	public abstract List<MailSender> findAll();	public abstract MailSender findById(int mailSenderId);}