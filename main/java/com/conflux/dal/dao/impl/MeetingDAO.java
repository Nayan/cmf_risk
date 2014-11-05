package com.conflux.dal.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.Meeting;
import com.conflux.dal.dao.IMeetingDAO;

@Repository
public class MeetingDAO extends BaseDAO implements IMeetingDAO {

	@Override
	public void persist(Meeting meeting) {
		getSessionFactory().getCurrentSession().persist(meeting);
	}

	@Override
	public void merge(Meeting meeting) {
		getSessionFactory().getCurrentSession().merge(meeting);

	}

	@Override
	public void delete(Meeting meeting) {
		getSessionFactory().getCurrentSession().delete(meeting);
	}

	@Override
	public Meeting findById(int meetingId) {
		Meeting meeting = (Meeting) getSessionFactory().getCurrentSession()
				.get("com.conflux.dal.bo.Meeting", meetingId);
		return meeting;
	}

	@Override
	public List<Meeting> findAll() {
		List<Meeting> meetings = (List<Meeting>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.Meeting").list();
		return meetings;
	}

	@Override
	public List<Meeting> find(int officeId, Date date) {
		Query query = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from com.conflux.dal.bo.Meeting meeting where meeting.customer.office.officeId =:officeId and meeting.meetingDate =:date order by meeting.customer.displayName");
		query.setParameter("officeId", officeId);
		query.setParameter("date", date);
		return query.list();
	}
}
