package com.conflux.dal.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.AssetSubType;
import com.conflux.dal.bo.OccupationSubType;
import com.conflux.dal.dao.IAssetSubTypeDAO;

@Repository
public class AssetSubTypeDAO extends BaseDAO implements IAssetSubTypeDAO {

	@Override
	public void persist(AssetSubType assetSubType) {
		getSessionFactory().getCurrentSession().persist(assetSubType);
	}

	@Override
	public void merge(AssetSubType assetSubType) {
		getSessionFactory().getCurrentSession().merge(assetSubType);

	}

	@Override
	public void delete(AssetSubType assetSubType) {
		getSessionFactory().getCurrentSession().delete(assetSubType);
	}

	@Override
	public AssetSubType findById(int assetSubTypeId) {
		AssetSubType assetSubType = (AssetSubType) getSessionFactory()
				.getCurrentSession().get("com.conflux.dal.bo.AssetSubType",
						assetSubTypeId);
		return assetSubType;
	}

	@Override
	public List<AssetSubType> findAll() {
		List<AssetSubType> assetSubTypes = (List<AssetSubType>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.AssetSubType").list();
		return assetSubTypes;
	}

	@Override
	public AssetSubType find(String name, int officeId, int assetTypeId) {
		Query query = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from com.conflux.dal.bo.AssetSubType assetSubType where assetSubType.name =:name and assetSubType.office.officeId =:officeId and assetSubType.assetType.assetTypeId =:assetTypeId");
		query.setParameter("name", name);
		query.setParameter("officeId", officeId);
		query.setParameter("assetTypeId", assetTypeId);
		return (AssetSubType) query.uniqueResult();
	}

	@Override
	public List<AssetSubType> find(int officeId, int assetTypeId) {
		Query query = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from com.conflux.dal.bo.AssetSubType assetSubType where assetSubType.office.officeId =:officeId and assetSubType.assetType.assetTypeId =:assetTypeId order by assetSubType.name");
		query.setParameter("officeId", officeId);
		query.setParameter("assetTypeId", assetTypeId);
		return query.list();
	}

}
