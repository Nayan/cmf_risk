package com.conflux.dal.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.conflux.dal.bo.Asset;
import com.conflux.dal.dao.IAssetDAO;

@Repository
public class AssetDAO extends BaseDAO implements IAssetDAO {

	@Override
	public void persist(Asset asset) {
		getSessionFactory().getCurrentSession().persist(asset);
	}

	@Override
	public void merge(Asset asset) {
		getSessionFactory().getCurrentSession().merge(asset);

	}

	@Override
	public void delete(Asset asset) {
		getSessionFactory().getCurrentSession().delete(asset);
	}

	@Override
	public Asset findById(int assetId) {
		Asset asset = (Asset) getSessionFactory().getCurrentSession().get(
				"com.conflux.dal.bo.Asset", assetId);
		return asset;
	}

	@Override
	public List<Asset> findAll() {
		List<Asset> assets = (List<Asset>) getSessionFactory()
				.getCurrentSession()
				.createQuery("from com.conflux.dal.bo.Asset").list();
		return assets;
	}
}
