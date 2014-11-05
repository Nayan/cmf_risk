package com.conflux.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conflux.dal.bo.Customer;
import com.conflux.dal.bo.Meeting;
import com.conflux.service.AbstractBaseService;
import com.conflux.service.IMeetingService;
import com.conflux.service.dto.MeetingDTO;

@Service
public class MeetingService extends AbstractBaseService implements
		IMeetingService {

	@Override
	public void updateMeetings(List<MeetingDTO> meetingDTOs) {
		for (MeetingDTO meetingDTO : meetingDTOs) {
			Meeting meeting = getMeetingDAO().findById(
					meetingDTO.getMeetingId());
			meeting.setUpdatedDate(new Date());
			meeting.setDataCaptured(true);
			meeting.setMembersPresent(meetingDTO.getMembersPresent());
			getMeetingDAO().merge(meeting);
		}
	}

	@Override
	public List<MeetingDTO> getMeetings(int officeId, Date date) {
		List<Meeting> meetings = getMeetingDAO().find(officeId, date);
		return extractMeeting(meetings);
	}

	@Override
	public List<MeetingDTO> getMeetings(int centerId) {
		Customer center = getCustomerDAO().findById(centerId);
		Set<Meeting> meetings = center.getMeetings();
		return extractMeeting(meetings);
	}

	@Transactional
	private List<MeetingDTO> extractMeeting(Collection<Meeting> meetings) {
		List<MeetingDTO> meetingDTOs = new ArrayList<MeetingDTO>();
		for (Meeting meeting : meetings) {
			meetingDTOs.add(getHelper().createMeetingDTO(meeting));
		}
		return meetingDTOs;
	}

}
