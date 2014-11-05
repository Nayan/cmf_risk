package com.conflux.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.conflux.common.ApplicationConstants;
import com.conflux.service.dto.CustomerDTO;
import com.conflux.service.dto.MeetingDTO;

@ManagedBean(name = "centerBean")
@SessionScoped
public class CenterManagedBean extends AbstractManagedBean implements
		Serializable {
	private static final long serialVersionUID = -8204585501500254352L;

	private CustomerDTO center;
	private List<MeetingDTO> meetings;

	public String viewCenter(int centerId) {
		center = getCustomerService().getCenterById(centerId);
		return ApplicationConstants.GENERIC_NAVIGATION.VIEW_CENTER.toString();
	}

	public String viewAttendanceHistory(int centerId) {
		meetings = getMeetingService().getMeetings(centerId);
		return ApplicationConstants.GENERIC_NAVIGATION.VIEW_CENTER_MEETINGS
				.toString();
	}

	public CustomerDTO getCenter() {
		return center;
	}

	public void setCenter(CustomerDTO center) {
		this.center = center;
	}

	public List<MeetingDTO> getMeetings() {
		return meetings;
	}

	public void setMeetings(List<MeetingDTO> meetings) {
		this.meetings = meetings;
	}

	public void updateRiskRating() {
		getCustomerService().updateNewRating(center.getCustomerId(),
				center.getRiskRating(),
				center.getRiskRatingChangeComment(),
				getLoginBean().getUserT().getUsername());
	}
	
	public void updateBMRating() {
		getCustomerService().updateNewBMRating(center.getCustomerId(),
				center.getBmRating(),
				getLoginBean().getUserT().getUsername());
	}
	
	public void updateBMProblemDescription() {
		getCustomerService().updateNewBMProblemDescription(center.getCustomerId(),
				center.getBmProblemDescription(),
				getLoginBean().getUserT().getUsername());
	}
	
	public void updateBMActionTaken() {
		getCustomerService().updateNewBMActionTaken(center.getCustomerId(),
				center.getBmActionTaken(),
				getLoginBean().getUserT().getUsername());
	}
}
