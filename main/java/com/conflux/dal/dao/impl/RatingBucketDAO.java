package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.RatingBucket;
import com.conflux.dal.dao.IRatingBucketDAO;
@Repository 
public class RatingBucketDAO extends BaseDAO implements IRatingBucketDAO { 

	@Override 
	public void persist(RatingBucket ratingBucket) { 
		getSessionFactory().getCurrentSession().persist(ratingBucket);
	}

	@Override 
	public void merge(RatingBucket ratingBucket) { 
		getSessionFactory().getCurrentSession().merge(ratingBucket); 

	}

	@Override
	public void delete(RatingBucket ratingBucket) {
		getSessionFactory().getCurrentSession().delete(ratingBucket);
	}

	@Override
	public RatingBucket findById(int ratingBucketId) {
		RatingBucket ratingBucket = (RatingBucket) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.RatingBucket", ratingBucketId);
		return ratingBucket;
	}

	@Override
	public List<RatingBucket> findAll() {
		List<RatingBucket> ratingBuckets = (List<RatingBucket>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.RatingBucket").list();
		return ratingBuckets;
	}
}
