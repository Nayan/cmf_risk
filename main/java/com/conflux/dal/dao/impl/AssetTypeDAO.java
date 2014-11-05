package com.conflux.dal.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.AssetType;
import com.conflux.dal.dao.IAssetTypeDAO;

@Repository
public class AssetTypeDAO extends BaseDAO implements IAssetTypeDAO {

	@Override
	public void persist(AssetType assetType) {
		getSessionFactory().getCurrentSession().persist(assetType);
	}

	@Override
	public void merge(AssetType assetType) {
		getSessionFactory().getCurrentSession().merge(assetType);

	}

	@Override
	public void delete(AssetType assetType) {
		getSessionFactory().getCurrentSession().delete(assetType);
	}

	@Override
	public AssetType findById(int assetTypeId) {
		AssetType assetType = (AssetType) getSessionFactory()
				.getCurrentSession().get("com.conflux.dal.bo.AssetType",
						assetTypeId);
		return assetType;
	}

	@Override
	public List<AssetType> findAll() {
		List<AssetType> assetTypes = (List<AssetType>) getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from com.conflux.dal.bo.AssetType assetType order by assetType.name")
				.list();
		return assetTypes;
	}
}
