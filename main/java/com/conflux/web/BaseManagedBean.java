package com.conflux.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.conflux.common.ApplicationConstants;
import com.conflux.service.dto.CustomerDTO;
import com.conflux.service.dto.MeetingDTO;

@ManagedBean(name = "baseBean")
@SessionScoped
public class BaseManagedBean extends AbstractManagedBean implements
		Serializable {
	private static final long serialVersionUID = -8204585501500254352L;

	@ManagedProperty(value = "#{appBean}")
	ApplicationManagedBean appBean;

	private int branchId;

	private CustomerDTO selectedCenter = new CustomerDTO();
	private CustomerDTO selectedVillage = new CustomerDTO();
	private List<CustomerDTO> villageList = new ArrayList<CustomerDTO>();
	private List<CustomerDTO> centerList = new ArrayList<CustomerDTO>();
	private List<CustomerDTO> memberList = new ArrayList<CustomerDTO>();
	private List<MeetingDTO> meetingList;
	private Date meetingDate;

	@PostConstruct
	public void init() {
		if (getLoginBean().getUserT().getOfficeDTO().isBranchOffice()) {
			// villageList =
			// getCustomerService().getVillages(getLoginBean().getUserT().getOfficeDTO().getOfficeId());
			centerList = getCustomerService().getCentersByBranchId(
					getLoginBean().getUserT().getOfficeDTO().getOfficeId());
		} else {
			// villageList = new ArrayList<CustomerDTO>();
			centerList = new ArrayList<CustomerDTO>();
		}
	}

	public void populateVillagesList() {
		villageList = getCustomerService().getVillages(branchId);
		centerList = new ArrayList<CustomerDTO>();
		memberList = new ArrayList<CustomerDTO>();
		selectedCenter = new CustomerDTO();
		selectedVillage = new CustomerDTO();
	}

	public void populateCentersByBranchList() {
		centerList = getCustomerService().getCentersByBranchId(branchId);
		memberList = new ArrayList<CustomerDTO>();
		selectedCenter = new CustomerDTO();
		selectedVillage = new CustomerDTO();
	}

	public void populateCentersList(int villageId) {
		selectedVillage = getCustomerService().getBasicCustomerInformationById(
				villageId);
		centerList = getCustomerService().getCenters(villageId);
		memberList = new ArrayList<CustomerDTO>();
	}

	public void populateMembersList(int centerId) {
		selectedCenter = getCustomerService().getBasicCustomerInformationById(
				centerId);
		memberList = getCustomerService().getMembers(centerId);
	}

	public String navigateToEnterBulkAttendance() {
		meetingList = getMeetingService().getMeetings(branchId, meetingDate);
		return ApplicationConstants.GENERIC_NAVIGATION.SAVE_ATTENDANCE_SHEET
				.toString();
	}

	public String saveBulkAttendance() {
		// set audit trail
		for (MeetingDTO meetingDTO : meetingList) {
			meetingDTO.setUpdatedBy(getLoginBean().getUserT().getUsername());
		}
		
		MeetingDTO meetingDTO;
        for(Iterator<MeetingDTO> iterator = meetingList.iterator(); iterator.hasNext();)
        {
            meetingDTO = iterator.next();
            meetingDTO.setUpdatedBy(getLoginBean().getUserT().getUsername());
            if(meetingDTO.getMembersPresent() < 0)
            {
                addFacesError((new StringBuilder("The members preset should be greater than zero for center")).append(meetingDTO.getCenterName()).toString());
                return com.conflux.common.ApplicationConstants.GENERIC_NAVIGATION.SAVE_ATTENDANCE_SHEET.toString();
            }
            if(meetingDTO.getMembersPresent() > meetingDTO.getTotalMembers())
            {
                addFacesError((new StringBuilder("The members preset should not be greater than ")).append(meetingDTO.getTotalMembers()).append(" for center ").append(meetingDTO.getCenterName()).toString());
                return com.conflux.common.ApplicationConstants.GENERIC_NAVIGATION.SAVE_ATTENDANCE_SHEET.toString();
            }
        }

        
		getMeetingService().updateMeetings(meetingList);
		return ApplicationConstants.GENERIC_NAVIGATION.HOME.toString();
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public CustomerDTO getSelectedCenter() {
		return selectedCenter;
	}

	public void setSelectedCenter(CustomerDTO selectedCenter) {
		this.selectedCenter = selectedCenter;
	}

	public CustomerDTO getSelectedVillage() {
		return selectedVillage;
	}

	public void setSelectedVillage(CustomerDTO selectedVillage) {
		this.selectedVillage = selectedVillage;
	}

	public List<CustomerDTO> getVillageList() {
		return villageList;
	}

	public void setVillageList(List<CustomerDTO> villageList) {
		this.villageList = villageList;
	}

	public List<CustomerDTO> getCenterList() {
		return centerList;
	}

	public void setCenterList(List<CustomerDTO> centerList) {
		this.centerList = centerList;
	}

	public List<CustomerDTO> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<CustomerDTO> memberList) {
		this.memberList = memberList;
	}

	public List<MeetingDTO> getMeetingList() {
		return meetingList;
	}

	public void setMeetingList(List<MeetingDTO> meetingList) {
		this.meetingList = meetingList;
	}

	public Date getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
	}

	public ApplicationManagedBean getAppBean() {
		return appBean;
	}

	public void setAppBean(ApplicationManagedBean appBean) {
		this.appBean = appBean;
	}

}
