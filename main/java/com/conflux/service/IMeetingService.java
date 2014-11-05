package com.conflux.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.conflux.service.dto.MeetingDTO;

public interface IMeetingService {

	@Transactional
	public void updateMeetings(List<MeetingDTO> meetingDTOs);

	@Transactional(readOnly = true)
	public List<MeetingDTO> getMeetings(int officeId, Date date);

	@Transactional(readOnly = true)
	public List<MeetingDTO> getMeetings(int centerId);
}
