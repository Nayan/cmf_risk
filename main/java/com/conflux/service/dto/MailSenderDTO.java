package com.conflux.service.dto;



public class MailSenderDTO     {

	private int mailSenderId;
	private String toAddress;
	private String fromAddress;
	private String subject;
	private String body;
	private boolean deliveryFlag;
	public int getMailSenderId() {
		return mailSenderId;
	}
	public void setMailSenderId(int mailSenderId) {
		this.mailSenderId = mailSenderId;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public boolean isDeliveryFlag() {
		return deliveryFlag;
	}
	public void setDeliveryFlag(boolean deliveryFlag) {
		this.deliveryFlag = deliveryFlag;
	}















} 
