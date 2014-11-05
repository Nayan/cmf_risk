package com.conflux.dal.dao.impl; 

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.RelationshipType;
import com.conflux.dal.dao.IRelationshipTypeDAO;
@Repository 
public class RelationshipTypeDAO extends BaseDAO implements IRelationshipTypeDAO { 

	@Override 
	public void persist(RelationshipType relationshipType) { 
		getSessionFactory().getCurrentSession().persist(relationshipType);
	}

	@Override 
	public void merge(RelationshipType relationshipType) { 
		getSessionFactory().getCurrentSession().merge(relationshipType); 

	}

	@Override
	public void delete(RelationshipType relationshipType) {
		getSessionFactory().getCurrentSession().delete(relationshipType);
	}

	@Override
	public RelationshipType findById(int relationshipTypeId) {
		RelationshipType relationshipType = (RelationshipType) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.RelationshipType", relationshipTypeId);
		return relationshipType;
	}

	@Override
	public List<RelationshipType> findAll() {
		List<RelationshipType> relationshipTypes = (List<RelationshipType>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.RelationshipType").list();
		return relationshipTypes;
	}
}
