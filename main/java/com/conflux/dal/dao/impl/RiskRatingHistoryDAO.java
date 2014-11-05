package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.RiskRatingHistory;
import com.conflux.dal.dao.IRiskRatingHistoryDAO;
@Repository 
public class RiskRatingHistoryDAO extends BaseDAO implements IRiskRatingHistoryDAO { 

	@Override 
	public void persist(RiskRatingHistory riskRatingHistory) { 
		getSessionFactory().getCurrentSession().persist(riskRatingHistory);
	}

	@Override 
	public void merge(RiskRatingHistory riskRatingHistory) { 
		getSessionFactory().getCurrentSession().merge(riskRatingHistory); 

	}

	@Override
	public void delete(RiskRatingHistory riskRatingHistory) {
		getSessionFactory().getCurrentSession().delete(riskRatingHistory);
	}

	@Override
	public RiskRatingHistory findById(int riskRatingHistoryId) {
		RiskRatingHistory riskRatingHistory = (RiskRatingHistory) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.RiskRatingHistory", riskRatingHistoryId);
		return riskRatingHistory;
	}

	@Override
	public List<RiskRatingHistory> findAll() {
		List<RiskRatingHistory> riskRatingHistorys = (List<RiskRatingHistory>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.RiskRatingHistory").list();
		return riskRatingHistorys;
	}
}
