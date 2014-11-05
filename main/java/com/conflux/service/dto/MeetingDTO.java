package com.conflux.service.dto;

import java.util.Date;

public class MeetingDTO {

	private int meetingId;
	private int totalMembers;
	private int membersPresent;
	private Date meetingDate;
	private Date createdDate;
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;
	private boolean dataCaptured;

	/** custom properties **/
	String centerName;
	int centerId;

	public String getCenterName() {
		return centerName;
	}

	public boolean isDataCaptured() {
		return dataCaptured;
	}

	public void setDataCaptured(boolean dataCaptured) {
		this.dataCaptured = dataCaptured;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public int getCenterId() {
		return centerId;
	}

	public void setCenterId(int centerId) {
		this.centerId = centerId;
	}

	public int getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}

	public int getTotalMembers() {
		return totalMembers;
	}

	public void setTotalMembers(int totalMembers) {
		this.totalMembers = totalMembers;
	}

	public int getMembersPresent() {
		return membersPresent;
	}

	public void setMembersPresent(int membersPresent) {
		this.membersPresent = membersPresent;
	}

	public Date getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
